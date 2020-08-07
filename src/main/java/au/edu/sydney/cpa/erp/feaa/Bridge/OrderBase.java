package au.edu.sydney.cpa.erp.feaa.Bridge;

import au.edu.sydney.cpa.erp.feaa.Bridge.Critical.Critical;
import au.edu.sydney.cpa.erp.feaa.Visitor.Visitors;
import au.edu.sydney.cpa.erp.ordering.Order;
import au.edu.sydney.cpa.erp.ordering.Report;
import au.edu.sydney.cpa.erp.ordering.ScheduledOrder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/** Represents the basic order, without the critical, scheduled or order-type properties. */
public abstract class OrderBase implements Order {
  private Map<Report, Integer> reports = new HashMap<>();
  private final int id;
  private LocalDateTime date;
  private int client;
  private boolean finalised = false;
  private Critical critical;
  private ScheduledFacade scheduled;

  /**
   * A list of visitors that transform certain methods, depending on the order's critical, scheduled
   * or type properties.
   */
  protected final Visitors visitors = new Visitors();

  private final boolean isCritical;
  private final boolean isScheduled;

  public OrderBase(
      int id,
      LocalDateTime date,
      int client,
      Critical critical,
      ScheduledOrder scheduled,
      boolean isCritical,
      boolean isScheduled) {
    this.id = id;
    this.date = date;
    this.client = client;
    this.critical = critical;
    this.scheduled = new ScheduledFacade(scheduled);
    this.isCritical = isCritical;
    this.isScheduled = isScheduled;
  }

  @Override
  public int getOrderID() {
    return id;
  }

  @Override
  public LocalDateTime getOrderDate() {
    return date;
  }

  @Override
  public Set<Report> getAllReports() {
    return reports.keySet();
  }

  @Override
  public int getReportEmployeeCount(Report report) {
    // We can't rely on equal reports having the same object identity since they get
    // rebuilt over the network, so we have to check for presence and same values

    for (Report contained : reports.keySet()) {
      if (contained.equals(report)) {
        report = contained;
        break;
      }
    }

    Integer result = reports.get(report);
    return null == result ? 0 : result;
  }

  @Override
  public void setReport(Report report, int employeeCount) {
    if (finalised) throw new IllegalStateException("Order was already finalised.");

    // We can't rely on equal reports having the same object identity since they get
    // rebuilt over the network, so we have to check for presence and same values

    for (Report contained : reports.keySet()) {
      if (contained.equals(report)) {
        report = contained;
        break;
      }
    }

    reports.put(report, employeeCount);
  }

  @Override
  public void finalise() {
    this.finalised = true;
  }

  public boolean isFinalised() {
    return finalised;
  }

  @Override
  public int getClient() {
    return client;
  }

  public double getCriticalLoading() {
    return this.critical.getCriticalLoading();
  }

  public int getNumberOfQuarters() {
    return scheduled.getNumberOfQuarters();
  }

  public Critical getCritical() {
    return this.critical;
  }

  public ScheduledOrder getScheduled() {
    return this.scheduled.getScheduledOrder();
  }

  public Map<Report, Integer> getReports() {
    return this.reports;
  }

  public boolean isCritical() {
    return isCritical;
  }

  public boolean isScheduled() {
    return isScheduled;
  }
}
