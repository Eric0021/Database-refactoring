package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.auth.AuthModule;
import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.database.TestDatabase;
import au.edu.sydney.cpa.erp.feaa.Factory.OrderFactoryHandler;
import au.edu.sydney.cpa.erp.feaa.UnitOfWork.OrderUoWImpl;
import au.edu.sydney.cpa.erp.feaa.UnitOfWork.OrderUnitOfWork;
import au.edu.sydney.cpa.erp.feaa.reports.ReportDatabase;
import au.edu.sydney.cpa.erp.ordering.Client;
import au.edu.sydney.cpa.erp.ordering.Order;
import au.edu.sydney.cpa.erp.ordering.Report;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class FEAAFacade {
  private AuthToken token;
  private List<Thread> threads = new ArrayList<>();

  /** A unit of work used to cache new and edited orders. */
  private OrderUnitOfWork orderUOW;

  /** A handler for order factory, used to create new orders. */
  private OrderFactoryHandler orderFactoryHandler = new OrderFactoryHandler();

  /** Keeps track of how many order changes are cached, commits when reaches 10. */
  private int uowCount;

  public boolean login(String userName, String password) {
    token = AuthModule.login(userName, password);

    if (token != null) {
      orderUOW = new OrderUoWImpl(token);
    }

    return null != token;
  }

  public List<Integer> getAllOrders() {
    if (null == token) {
      throw new SecurityException();
    }

    if (orderUOW != null) {
      List<Thread> uow_threads = orderUOW.getThreads();
      for (Thread thread : uow_threads) {
        if (!this.threads.contains(thread)) {
          this.threads.add(thread);
        }
      }
    }

    // We want to wait for all commits/edits/add in UOW to finish before listing all orders.
    for (Thread thread : threads) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    TestDatabase database = TestDatabase.getInstance();

    List<Order> orders = database.getOrders(token);

    List<Integer> result = new ArrayList<>();

    // add all new cached orders into list, edited orders already exist in database
    // no need to add cached edited orders.
    // Use thread to put into a separate array first, then merge.
    List<Integer> result_UOW = new ArrayList<>();
    Thread thread =
        new Thread(
            () -> {
              for (Order newOrder : orderUOW.getNewOrders()) {
                result_UOW.add(newOrder.getOrderID());
              }
            });
    thread.start();

    for (Order order : orders) {
      result.add(order.getOrderID());
    }

    try {
      thread.join();
      result.addAll(result_UOW);
    } catch (Exception e) {
      System.out.println(e.toString());
    }

    return result;
  }

  public Integer createOrder(
      int clientID,
      LocalDateTime date,
      boolean isCritical,
      boolean isScheduled,
      int orderType,
      int criticalLoadingRaw,
      int maxCountedEmployees,
      int numQuarters) {
    if (null == token) {
      throw new SecurityException();
    }

    double criticalLoading = criticalLoadingRaw / 100.0;

    Order order;

    if (!TestDatabase.getInstance().getClientIDs(token).contains(clientID)) {
      throw new IllegalArgumentException("Invalid client ID");
    }

    int id = TestDatabase.getInstance().getNextOrderID();

    orderFactoryHandler
        .setId(id)
        .setClient(clientID)
        .setDate(date)
        .setCritical(isCritical)
        .setScheduled(isScheduled)
        .setCriticalLoading(criticalLoading)
        .setNumQuarters(numQuarters)
        .setOrderType(orderType)
        .setMaxCountedEmployees(maxCountedEmployees);

    if (orderFactoryHandler.dataIsComplete()) {
      order = orderFactoryHandler.createOrder();
      if (order == null) {
        return null;
      }
    } else {
      throw new IllegalArgumentException("Order factory handler data not complete");
    }

    if (orderUOW != null) {
      orderUOW.registerNew(order);
    }

    return order.getOrderID();
  }

  public List<Integer> getAllClientIDs() {
    if (null == token) {
      throw new SecurityException();
    }

    TestDatabase database = TestDatabase.getInstance();
    return database.getClientIDs(token);
  }

  public Client getClient(int id) {
    if (null == token) {
      throw new SecurityException();
    }

    return new ClientImpl(token, id);
  }

  public boolean removeOrder(int id) {
    if (null == token) {
      throw new SecurityException();
    }

    TestDatabase database = TestDatabase.getInstance();

    // If a cached new order is to be removed (whether brand new or has been edited), just remove
    // from UoW and return true.
    // If this order has cached edit, delete it from UOW.
    // Returning false means that this order is not a cached new order.
    if (!orderUOW.remove(id)) {
      return database.removeOrder(token, id);
    } else {
      return true;
    }
  }

  public List<Report> getAllReports() {
    if (null == token) {
      throw new SecurityException();
    }

    return new ArrayList<>(ReportDatabase.getTestReports());
  }

  public boolean finaliseOrder(int orderID, List<String> contactPriority) {
    if (null == token) {
      throw new SecurityException();
    }

    // Use separate thread to get contact as methods.
    GetContactPriority getContactPriority = new GetContactPriority(contactPriority);
    Thread thread = new Thread(getContactPriority);
    thread.start();

    // First finds if the order is still cached in the UOW.
    // If not, find it from the database.
    Order order = null;
    if (orderUOW != null) {
      order = orderUOW.getOrder(orderID);
    }
    if (order == null) {
      order = TestDatabase.getInstance().getOrder(token, orderID);
    }

    order.finalise();

    // finaliseOrder() changes an existing order.
    orderUOW.registerDirty(order);

    uowCount++;

    if (uowCount == 10) {
      if (orderUOW != null) {
        orderUOW.commit();

        // restart the counter once committed.
        uowCount = 0;
      }
    }

    try {
      thread.join();
    } catch (Exception e) {
      System.out.println(e.toString());
    }

    return ContactHandler.sendInvoice(
        token,
        getClient(order.getClient()),
        getContactPriority.getContactPriorityAsMethods(),
        order.generateInvoiceData());
  }

  public void logout() {
    if (orderUOW != null) {
      orderUOW.commit();
      List<Thread> uow_threads = orderUOW.getThreads();
      for (Thread thread : uow_threads) {
        if (!this.threads.contains(thread)) {
          this.threads.add(thread);
        }
      }
    }

    // All threads need to finish before logging out.
    for (Thread thread : threads) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    AuthModule.logout(token);
    token = null;
  }

  public double getOrderTotalCommission(int orderID) {
    if (null == token) {
      throw new SecurityException();
    }

    // First finds if the order is still cached in the UOW.
    Order order = null;
    if (orderUOW != null) {
      order = orderUOW.getOrder(orderID);
    }
    if (order == null) {
      order = TestDatabase.getInstance().getOrder(token, orderID);
    }

    if (null == order) {
      return 0.0;
    }

    return order.getTotalCommission();
  }

  public void orderLineSet(int orderID, Report report, int numEmployees) {
    if (null == token) {
      throw new SecurityException();
    }

    // First finds if the order is still cached in the UOW.
    Order order = null;
    if (orderUOW != null) {
      order = orderUOW.getOrder(orderID);
    }
    if (order == null) {
      order = TestDatabase.getInstance().getOrder(token, orderID);
    }

    if (null == order) {
      System.out.println("got here");
      return;
    }

    order.setReport(report, numEmployees);

    orderUOW.registerDirty(order);
  }

  public String getOrderLongDesc(int orderID) {
    if (null == token) {
      throw new SecurityException();
    }

    // First finds if the order is still cached in the UOW.
    Order order = null;
    if (orderUOW != null) {
      order = orderUOW.getOrder(orderID);
    }
    if (order == null) {
      order = TestDatabase.getInstance().getOrder(token, orderID);
    }

    if (null == order) {
      return null;
    }

    return order.longDesc();
  }

  public String getOrderShortDesc(int orderID) {
    if (null == token) {
      throw new SecurityException();
    }

    // First finds if the order is still cached in the UOW.
    Order order = null;
    if (orderUOW != null) {
      order = orderUOW.getOrder(orderID);
    }
    if (order == null) {
      order = TestDatabase.getInstance().getOrder(token, orderID);
    }

    if (null == order) {
      return null;
    }

    return order.shortDesc();
  }

  public List<String> getKnownContactMethods() {
    if (null == token) {
      throw new SecurityException();
    }
    return ContactHandler.getKnownMethods();
  }
}
