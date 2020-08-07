package au.edu.sydney.cpa.erp.feaa.Bridge;

import au.edu.sydney.cpa.erp.ordering.Order;
import au.edu.sydney.cpa.erp.ordering.Report;
import au.edu.sydney.cpa.erp.ordering.ScheduledOrder;

import java.time.LocalDateTime;
import java.util.Set;

/** A Non-Scheduled Order. Does not have the functionalities of a scheduled order. */
public class NonScheduledOrder implements ScheduledOrder {
  /**
   * This method is never used, instead, refer to its counterpart in classes in OrderType package.
   *
   * @return 0.
   */
  @Override
  public double getRecurringCost() {
    return 0;
  }

  @Override
  public int getNumberOfQuarters() {
    return 0;
  }

  @Override
  public int getOrderID() {
    return 0;
  }

  /**
   * This method is never used, instead, refer to its counterpart in classes in OrderType package.
   *
   * @return 0.
   */
  @Override
  public double getTotalCommission() {
    return 0;
  }

  @Override
  public LocalDateTime getOrderDate() {
    return null;
  }

  @Override
  public void setReport(Report report, int employeeCount) {}

  @Override
  public Set<Report> getAllReports() {
    return null;
  }

  @Override
  public int getReportEmployeeCount(Report report) {
    return 0;
  }

  @Override
  public String generateInvoiceData() {
    return null;
  }

  @Override
  public int getClient() {
    return 0;
  }

  @Override
  public void finalise() {}

  @Override
  public Order copy() {
    return null;
  }

  @Override
  public String shortDesc() {
    return null;
  }

  @Override
  public String longDesc() {
    return null;
  }
}
