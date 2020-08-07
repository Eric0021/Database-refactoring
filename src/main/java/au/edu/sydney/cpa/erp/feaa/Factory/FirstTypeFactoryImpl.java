package au.edu.sydney.cpa.erp.feaa.Factory;

import au.edu.sydney.cpa.erp.feaa.Bridge.Critical.CriticalOrder;
import au.edu.sydney.cpa.erp.feaa.Bridge.Critical.RegularOrder;
import au.edu.sydney.cpa.erp.feaa.Bridge.NonScheduledOrder;
import au.edu.sydney.cpa.erp.feaa.Bridge.OrderType.FirstType;
import au.edu.sydney.cpa.erp.feaa.Bridge.ScheduledOrderBase;
import au.edu.sydney.cpa.erp.ordering.Order;

import java.time.LocalDateTime;

public class FirstTypeFactoryImpl implements FirstTypeFactory {
  @Override
  public Order createFirstType(
      int id,
      LocalDateTime date,
      int client,
      boolean isCritical,
      boolean isScheduled,
      int maxCountedEmployees,
      double criticalLoading,
      int numQuarters) {
    if (isCritical) {
      if (isScheduled) {
        return createCriticalScheduled(
            id, date, client, maxCountedEmployees, criticalLoading, numQuarters);
      } else {
        return createCriticalNonScheduled(id, date, client, maxCountedEmployees, criticalLoading);
      }
    } else {
      if (isScheduled) {
        return createNonCriticalScheduled(id, date, client, maxCountedEmployees, numQuarters);
      } else {
        return createNonCriticalNonScheduled(id, date, client, maxCountedEmployees);
      }
    }
  }

  // Creates a critical, non-scheduled first type order.
  private Order createCriticalNonScheduled(
      int id, LocalDateTime date, int client, int maxCountedEmployees, double criticalLoading) {
    return new FirstType(
        id,
        date,
        client,
        new CriticalOrder(criticalLoading),
        new NonScheduledOrder(),
        maxCountedEmployees,
        true,
        false);
  }

  // Creates a critical, scheduled first type order.
  private Order createCriticalScheduled(
      int id,
      LocalDateTime date,
      int client,
      int maxCountedEmployees,
      double criticalLoading,
      int numQuarters) {
    return new FirstType(
        id,
        date,
        client,
        new CriticalOrder(criticalLoading),
        new ScheduledOrderBase(numQuarters),
        maxCountedEmployees,
        true,
        true);
  }

  // Creates a non-critical, scheduled first type order.
  private Order createNonCriticalScheduled(
      int id, LocalDateTime date, int client, int maxCountedEmployees, int numQuarters) {
    return new FirstType(
        id,
        date,
        client,
        new RegularOrder(),
        new ScheduledOrderBase(numQuarters),
        maxCountedEmployees,
        false,
        true);
  }

  // Creates a non-critical, non-scheduled first type order.
  private Order createNonCriticalNonScheduled(
      int id, LocalDateTime date, int client, int maxCountedEmployees) {
    return new FirstType(
        id,
        date,
        client,
        new RegularOrder(),
        new NonScheduledOrder(),
        maxCountedEmployees,
        false,
        false);
  }
}
