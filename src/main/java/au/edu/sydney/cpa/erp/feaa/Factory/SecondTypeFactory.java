package au.edu.sydney.cpa.erp.feaa.Factory;

import au.edu.sydney.cpa.erp.ordering.Order;

import java.time.LocalDateTime;

/** A factory interface to create second type Orders. */
public interface SecondTypeFactory {
  Order createSecondType(
      int id,
      LocalDateTime date,
      int client,
      boolean isCritical,
      boolean isScheduled,
      double criticalLoading,
      int numQuarters);
}
