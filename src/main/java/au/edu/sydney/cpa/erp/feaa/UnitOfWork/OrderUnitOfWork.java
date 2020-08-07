package au.edu.sydney.cpa.erp.feaa.UnitOfWork;

import au.edu.sydney.cpa.erp.ordering.Order;

import java.util.List;

/** A unit of work that caches any new orders or edited orders. */
public interface OrderUnitOfWork {
  /** Registers a new order made by the client. */
  void registerNew(Order o);

  /** Registers an edited order made by the client. */
  void registerDirty(Order o);

  /** Push the new orders and edited orders into the database. */
  void commit();

  /**
   * Removes a certain order from the cache.
   *
   * @return True if the removed cached order is a new order not in the database, False otherwise.
   */
  boolean remove(int orderID);

  Order getOrder(int orderId);

  List<Order> getNewOrders();

  List<Thread> getThreads();
}
