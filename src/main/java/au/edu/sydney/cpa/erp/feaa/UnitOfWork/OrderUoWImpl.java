package au.edu.sydney.cpa.erp.feaa.UnitOfWork;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.database.TestDatabase;
import au.edu.sydney.cpa.erp.ordering.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class OrderUoWImpl implements OrderUnitOfWork {
  private List<Order> newOrders = new ArrayList<>();
  private List<Order> editedOrders = new ArrayList<>();
  private AuthToken token;
  private List<Thread> threads = new ArrayList<>();

  public OrderUoWImpl(AuthToken token) {
    this.token = token;
  }

  @Override
  public void registerNew(Order o) {
    newOrders.add(o);
  }

  @Override
  public void registerDirty(Order o) {
    //     The order is either edited, or a new/edited order was finalised.

    //     Order can already be in the newOrders or editedOrders array.
    //     If a cached order gets edited, it still stays in the newOrders.
    //     Remove these previous versions in edited orders.
    Thread thread =
        new Thread(
            () -> {
              synchronized (this) {
                for (int i = 0; i < newOrders.size(); i++) {
                  if (o.getOrderID() == newOrders.get(i).getOrderID()) {
                    newOrders.set(i, o);
                    return;
                  }
                }

                for (Order order : editedOrders) {
                  if (o.getOrderID() == order.getOrderID()) {
                    editedOrders.remove(o);
                  }
                }

                editedOrders.add(o);
              }
            });
    thread.start();
    threads.add(thread);
  }

  @Override
  public void commit() {
    // When committing, the orders should not be edited, removed.
    // Such as in remove() and registerDirty().
    Thread thread =
        new Thread(
            () -> {
              synchronized (this) {
                for (Order order : newOrders) {
                  TestDatabase.getInstance().saveOrder(token, order);
                }

                for (Order order : editedOrders) {
                  TestDatabase.getInstance().saveOrder(token, order);
                }

                // The cached orders are cleared after commit.
                newOrders.clear();
                editedOrders.clear();
              }
            });
    thread.start();
    threads.add(thread);
  }

  @Override
  public Order getOrder(int orderId) {
    Order o = null;
    for (Order order : newOrders) {
      if (order.getOrderID() == orderId) {
        o = order;
      }
    }

    for (Order order : editedOrders) {
      if (order.getOrderID() == orderId) {
        o = order;
      }
    }

    return o;
  }

  @Override
  public boolean remove(int orderID) {
    // If a new cached order was removed, return true.
    // If an order exists in the database (not in newOrders), return false.

    // if an order was edited, cached here, but is then removed,
    // it should be removed in the edited_orders array, as well as the database.

    final boolean[] ret = new boolean[1];
    Thread thread =
        new Thread(
            () -> {
              synchronized (this) {
                for (Order order : newOrders) {
                  if (order.getOrderID() == orderID) {
                    newOrders.remove(order);
                    ret[0] = true;
                    break;
                  }
                }

                // if the order was a newly cached order, it is already removed.
                // No need to go into this loop.
                if (!ret[0]) {
                  for (Order order : editedOrders) {
                    if (order.getOrderID() == orderID) {
                      editedOrders.remove(order);
                    }
                  }
                }
              }
            });

    // we need the child process to finish to get the return value.
    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return ret[0];
  }

  public List<Order> getNewOrders() {
    return newOrders;
  }

  public List<Thread> getThreads() {
    return threads;
  }
}
