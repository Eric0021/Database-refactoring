package au.edu.sydney.cpa.erp.feaa.Bridge;

import au.edu.sydney.cpa.erp.ordering.ScheduledOrder;

/** A facade class to decouple OrderBase from ScheduledOrder. */
public class ScheduledFacade {
  private ScheduledOrder scheduledOrder;

  public ScheduledFacade(ScheduledOrder scheduledOrder) {
    this.scheduledOrder = scheduledOrder;
  }

  public int getNumberOfQuarters() {
    return scheduledOrder.getNumberOfQuarters();
  }

  public ScheduledOrder getScheduledOrder() {
    return scheduledOrder;
  }
}
