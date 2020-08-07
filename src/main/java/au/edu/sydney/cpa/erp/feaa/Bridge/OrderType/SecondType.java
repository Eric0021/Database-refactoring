package au.edu.sydney.cpa.erp.feaa.Bridge.OrderType;

import au.edu.sydney.cpa.erp.feaa.Bridge.OrderBase;
import au.edu.sydney.cpa.erp.feaa.Bridge.Critical.Critical;
import au.edu.sydney.cpa.erp.ordering.Order;
import au.edu.sydney.cpa.erp.ordering.ScheduledOrder;

import java.time.LocalDateTime;

/** Second Order type, or Audit Accounting Work. */
public class SecondType extends OrderBase {

  /** Creates a new SecondType order, using OrderBase's constructor. */
  public SecondType(
      int id,
      LocalDateTime date,
      int client,
      Critical critical,
      ScheduledOrder scheduled,
      boolean isCritical,
      boolean isScheduled) {
    super(id, date, client, critical, scheduled, isCritical, isScheduled);
  }

  @Override
  public Order copy() {
    return visitors.getCopyVisitor().copy(this);
  }

  @Override
  public double getTotalCommission() {
    return visitors.getCommissionVisitor().getTotalCommission(this);
  }

  @Override
  public String shortDesc() {
    return visitors.getShortDescVisitor().shortDesc(this);
  }

  @Override
  public String longDesc() {
    return visitors.getLongDescVisitor().longDesc(this);
  }

  public double getRecurringCost() {
    return visitors.getCommissionVisitor().getTotalCommission(this) / getNumberOfQuarters();
  }

  @Override
  public String generateInvoiceData() {
    return visitors.getGenerateInvoiceVisitor().generateInvoiceData(this);
  }
}
