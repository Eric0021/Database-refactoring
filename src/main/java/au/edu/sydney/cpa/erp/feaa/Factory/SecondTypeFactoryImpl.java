package au.edu.sydney.cpa.erp.feaa.Factory;

import au.edu.sydney.cpa.erp.feaa.Bridge.Critical.CriticalOrder;
import au.edu.sydney.cpa.erp.feaa.Bridge.Critical.RegularOrder;
import au.edu.sydney.cpa.erp.feaa.Bridge.NonScheduledOrder;
import au.edu.sydney.cpa.erp.feaa.Bridge.OrderType.SecondType;
import au.edu.sydney.cpa.erp.feaa.Bridge.ScheduledOrderBase;
import au.edu.sydney.cpa.erp.ordering.Order;

import java.time.LocalDateTime;

public class SecondTypeFactoryImpl implements SecondTypeFactory {
  @Override
  public Order createSecondType(
      int id,
      LocalDateTime date,
      int client,
      boolean isCritical,
      boolean isScheduled,
      double criticalLoading,
      int numQuarters) {
    if (isCritical) {
      if (isScheduled) {
        return createCriticalScheduled(id, date, client, criticalLoading, numQuarters);
      } else {
        return createCriticalNonScheduled(id, date, client, criticalLoading);
      }
    } else {
      if (isScheduled) {
        return createNonCriticalScheduled(id, date, client, numQuarters);
      } else {
        return createNonCriticalNonScheduled(id, date, client);
      }
    }
  }

  // Creates a critical, non-scheduled first type order.
  private Order createCriticalNonScheduled(
      int id, LocalDateTime date, int client, double criticalLoading) {
    return new SecondType(
        id, date, client, new CriticalOrder(criticalLoading), new NonScheduledOrder(), true, false);
  }

  // Creates a critical, scheduled first type order.
  private Order createCriticalScheduled(
      int id, LocalDateTime date, int client, double criticalLoading, int numQuarters) {
    return new SecondType(
        id,
        date,
        client,
        new CriticalOrder(criticalLoading),
        new ScheduledOrderBase(numQuarters),
        true,
        true);
  }

  // Creates a non-critical, scheduled first type order.
  private Order createNonCriticalScheduled(
      int id, LocalDateTime date, int client, int numQuarters) {
    return new SecondType(
        id, date, client, new RegularOrder(), new ScheduledOrderBase(numQuarters), false, true);
  }

  // Creates a non-critical, non-scheduled first type order.
  private Order createNonCriticalNonScheduled(int id, LocalDateTime date, int client) {
    return new SecondType(
        id, date, client, new RegularOrder(), new NonScheduledOrder(), false, false);
  }
}
