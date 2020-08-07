package au.edu.sydney.cpa.erp.feaa.Visitor;

import au.edu.sydney.cpa.erp.feaa.Bridge.Critical.Critical;
import au.edu.sydney.cpa.erp.feaa.Bridge.OrderType.FirstType;
import au.edu.sydney.cpa.erp.feaa.Bridge.OrderType.SecondType;
import au.edu.sydney.cpa.erp.ordering.Report;

import java.util.Map;

public class CommissionVisitorImpl implements GetTotalCommissionVisitor {
  @Override
  public double getTotalCommission(FirstType type) {
    double maxCountedEmployees = type.getMaxCountedEmployees();
    Map<Report, Integer> reports = type.getReports();
    double criticalLoading = type.getCriticalLoading();
    double numQuarters = type.getNumberOfQuarters();

    if (type.isCritical() && type.isScheduled()) {
      double cost = 0.0;
      for (Report report : reports.keySet()) {
        cost += report.getCommission() * Math.min(maxCountedEmployees, reports.get(report));
      }

      cost += cost * criticalLoading;
      return cost * numQuarters;
    } else if (type.isCritical() && !type.isScheduled()) {
      double cost = 0.0;
      for (Report report : reports.keySet()) {
        cost += report.getCommission() * Math.min(maxCountedEmployees, reports.get(report));
      }

      cost += cost * criticalLoading;
      return cost;
    } else if (!type.isCritical() && type.isScheduled()) {
      double cost = 0.0;
      for (Report report : reports.keySet()) {
        cost += report.getCommission() * Math.min(maxCountedEmployees, reports.get(report));
      }
      return cost * numQuarters;
    } else {
      double cost = 0.0;
      for (Report report : reports.keySet()) {
        cost += report.getCommission() * Math.min(maxCountedEmployees, reports.get(report));
      }
      return cost;
    }
  }

  @Override
  public double getTotalCommission(SecondType type) {
    Map<Report, Integer> reports = type.getReports();
    double numQuarters = type.getNumberOfQuarters();
    Critical critical = type.getCritical();

    if (type.isCritical() && type.isScheduled()) {
      double cost = 0.0;
      for (Report report : reports.keySet()) {
        cost += reports.get(report) * report.getCommission();
      }
      cost += cost * critical.getCriticalLoading();
      return cost * numQuarters;
    } else if (type.isCritical() && !type.isScheduled()) {
      double cost = 0.0;
      for (Report report : reports.keySet()) {
        cost += reports.get(report) * report.getCommission();
      }
      cost += cost * critical.getCriticalLoading();
      return cost;
    } else if (!type.isCritical() && type.isScheduled()) {
      double cost = 0.0;
      for (Report report : reports.keySet()) {
        cost += reports.get(report) * report.getCommission();
      }
      return cost * numQuarters;
    } else {
      double cost = 0.0;
      for (Report report : reports.keySet()) {
        cost += reports.get(report) * report.getCommission();
      }
      return cost;
    }
  }
}
