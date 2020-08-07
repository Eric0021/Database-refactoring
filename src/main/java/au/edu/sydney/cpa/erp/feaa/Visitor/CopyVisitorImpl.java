package au.edu.sydney.cpa.erp.feaa.Visitor;

import au.edu.sydney.cpa.erp.feaa.Bridge.OrderType.FirstType;
import au.edu.sydney.cpa.erp.feaa.Bridge.OrderType.SecondType;
import au.edu.sydney.cpa.erp.ordering.Order;
import au.edu.sydney.cpa.erp.ordering.Report;

public class CopyVisitorImpl implements CopyVisitor {

  @Override
  public Order copy(FirstType type) {
    Order copy =
        new FirstType(
            type.getOrderID(),
            type.getOrderDate(),
            type.getClient(),
            type.getCritical(),
            type.getScheduled(),
            type.getMaxCountedEmployees(),
            type.isCritical(),
            type.isScheduled());

    for (Report report : type.getReports().keySet()) {
      copy.setReport(report, type.getReports().get(report));
    }

    return copy;
  }

  @Override
  public Order copy(SecondType type) {
    Order copy =
        new SecondType(
            type.getOrderID(),
            type.getOrderDate(),
            type.getClient(),
            type.getCritical(),
            type.getScheduled(),
            type.isCritical(),
            type.isScheduled());

    for (Report report : type.getReports().keySet()) {
      copy.setReport(report, type.getReports().get(report));
    }

    return copy;
  }
}
