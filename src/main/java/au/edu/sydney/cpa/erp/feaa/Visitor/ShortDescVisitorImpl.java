package au.edu.sydney.cpa.erp.feaa.Visitor;

import au.edu.sydney.cpa.erp.feaa.Bridge.Critical.Critical;
import au.edu.sydney.cpa.erp.feaa.Bridge.OrderType.FirstType;
import au.edu.sydney.cpa.erp.feaa.Bridge.OrderType.SecondType;
import au.edu.sydney.cpa.erp.ordering.ScheduledOrder;

public class ShortDescVisitorImpl implements ShortDescVisitor {
  @Override
  public String shortDesc(FirstType type) {
    if (type.isCritical() && type.isScheduled()) {
      return String.format(
          "ID:%s $%,.2f per quarter, $%,.2f total",
          type.getOrderID(), type.getRecurringCost(), type.getTotalCommission());
    } else if (type.isCritical() && !type.isScheduled()) {
      return String.format("ID:%s $%,.2f", type.getOrderID(), type.getTotalCommission());
    } else if (!type.isCritical() && type.isScheduled()) {
      return String.format(
          "ID:%s $%,.2f per quarter, $%,.2f total",
          type.getOrderID(), type.getRecurringCost(), type.getTotalCommission());
    } else {
      return String.format("ID:%s $%,.2f", type.getOrderID(), type.getTotalCommission());
    }
  }

  @Override
  public String shortDesc(SecondType type) {
    if (type.isCritical() && type.isScheduled()) {
      return String.format(
          "ID:%s $%,.2f per quarter, $%,.2f total",
          type.getOrderID(), type.getRecurringCost(), type.getTotalCommission());
    } else if (type.isCritical() && !type.isScheduled()) {
      return String.format("ID:%s $%,.2f", type.getOrderID(), type.getTotalCommission());
    } else if (!type.isCritical() && type.isScheduled()) {
      return String.format(
          "ID:%s $%,.2f per quarter, $%,.2f total",
          type.getOrderID(), type.getRecurringCost(), type.getTotalCommission());
    } else {
      return String.format("ID:%s $%,.2f", type.getOrderID(), type.getTotalCommission());
    }
  }
}
