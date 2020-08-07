package au.edu.sydney.cpa.erp.feaa.Factory;

import au.edu.sydney.cpa.erp.feaa.Bridge.Critical.CriticalOrder;
import au.edu.sydney.cpa.erp.feaa.Bridge.OrderType.FirstType;
import au.edu.sydney.cpa.erp.ordering.Order;

import java.time.LocalDateTime;

/** A factory interface to create first type Orders. */
public interface FirstTypeFactory {

  /**
   * Creates a first type Order with the appropriate critical, scheduled properties.
   *
   * @return A firs type Order.
   */
  Order createFirstType(
      int id,
      LocalDateTime date,
      int client,
      boolean isCritical,
      boolean isScheduled,
      int maxCountedEmployees,
      double criticalLoading,
      int numQuarters);
}
