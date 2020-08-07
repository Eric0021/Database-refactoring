package au.edu.sydney.cpa.erp.feaa.Visitor;

import au.edu.sydney.cpa.erp.feaa.Bridge.OrderType.FirstType;
import au.edu.sydney.cpa.erp.ordering.Report;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class FirstTypeLongDesc {
  public String criticalScheduled(FirstType type) {
    double maxCountedEmployees = type.getMaxCountedEmployees();
    Map<Report, Integer> reports = type.getReports();
    int numQuarters = type.getNumberOfQuarters();

    double totalBaseCost = 0.0;
    double loadedCostPerQuarter = type.getTotalCommission() / numQuarters;
    double totalLoadedCost = type.getTotalCommission();
    StringBuilder reportSB = new StringBuilder();

    List<Report> keyList = new ArrayList<>(reports.keySet());
    keyList.sort(Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

    for (Report report : keyList) {
      double subtotal = report.getCommission() * Math.min(maxCountedEmployees, reports.get(report));
      totalBaseCost += subtotal;

      reportSB.append(
          String.format(
              "\tReport name: %s\tEmployee Count: %d\tCommission per employee: $%,.2f\tSubtotal: $%,.2f",
              report.getReportName(), reports.get(report), report.getCommission(), subtotal));

      if (reports.get(report) > maxCountedEmployees) {
        reportSB.append(" *CAPPED*\n");
      } else {
        reportSB.append("\n");
      }
    }

    return String.format(
        type.isFinalised()
            ? ""
            : "*NOT FINALISED*\n"
                + "Order details (id #%d)\n"
                + "Date: %s\n"
                + "Number of quarters: %d\n"
                + "Reports:\n"
                + "%s"
                + "Critical Loading: $%,.2f\n"
                + "Recurring cost: $%,.2f\n"
                + "Total cost: $%,.2f\n",
        type.getOrderID(),
        type.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
        numQuarters,
        reportSB.toString(),
        totalLoadedCost - (totalBaseCost * numQuarters),
        loadedCostPerQuarter,
        totalLoadedCost);
  }

  public String CriticalNonScheduled(FirstType type) {
    double maxCountedEmployees = type.getMaxCountedEmployees();
    Map<Report, Integer> reports = type.getReports();
    double baseCommission = 0.0;
    double loadedCommission = type.getTotalCommission();
    StringBuilder reportSB = new StringBuilder();

    List<Report> keyList = new ArrayList<>(reports.keySet());
    keyList.sort(Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

    for (Report report : keyList) {
      double subtotal = report.getCommission() * Math.min(maxCountedEmployees, reports.get(report));
      baseCommission += subtotal;

      reportSB.append(
          String.format(
              "\tReport name: %s\tEmployee Count: %d\tCommission per employee: $%,.2f\tSubtotal: $%,.2f",
              report.getReportName(), reports.get(report), report.getCommission(), subtotal));

      if (reports.get(report) > maxCountedEmployees) {
        reportSB.append(" *CAPPED*\n");
      } else {
        reportSB.append("\n");
      }
    }

    return String.format(
        type.isFinalised()
            ? ""
            : "*NOT FINALISED*\n"
                + "Order details (id #%d)\n"
                + "Date: %s\n"
                + "Reports:\n"
                + "%s"
                + "Critical Loading: $%,.2f\n"
                + "Total cost: $%,.2f\n",
        type.getOrderID(),
        type.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
        reportSB.toString(),
        loadedCommission - baseCommission,
        loadedCommission);
  }

  public String NonCriticalScheduled(FirstType type) {
    double maxCountedEmployees = type.getMaxCountedEmployees();
    Map<Report, Integer> reports = type.getReports();
    int numQuarters = type.getNumberOfQuarters();
    StringBuilder reportSB = new StringBuilder();

    List<Report> keyList = new ArrayList<>(reports.keySet());
    keyList.sort(Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

    for (Report report : keyList) {
      double subtotal = report.getCommission() * Math.min(maxCountedEmployees, reports.get(report));

      reportSB.append(
          String.format(
              "\tReport name: %s\tEmployee Count: %d\tCommission per employee: $%,.2f\tSubtotal: $%,.2f",
              report.getReportName(), reports.get(report), report.getCommission(), subtotal));

      if (reports.get(report) > maxCountedEmployees) {
        reportSB.append(" *CAPPED*\n");
      } else {
        reportSB.append("\n");
      }
    }

    return String.format(
        type.isFinalised()
            ? ""
            : "*NOT FINALISED*\n"
                + "Order details (id #%d)\n"
                + "Date: %s\n"
                + "Number of quarters: %d\n"
                + "Reports:\n"
                + "%s"
                + "Recurring cost: $%,.2f\n"
                + "Total cost: $%,.2f\n",
        type.getOrderID(),
        type.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
        numQuarters,
        reportSB.toString(),
        type.getTotalCommission() / numQuarters,
        type.getTotalCommission());
  }

  public String NonCriticalNonScheduled(FirstType type) {
    double maxCountedEmployees = type.getMaxCountedEmployees();
    Map<Report, Integer> reports = type.getReports();
    StringBuilder reportSB = new StringBuilder();

    List<Report> keyList = new ArrayList<>(reports.keySet());
    keyList.sort(Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

    for (Report report : keyList) {
      double subtotal = report.getCommission() * Math.min(maxCountedEmployees, reports.get(report));

      reportSB.append(
          String.format(
              "\tReport name: %s\tEmployee Count: %d\tCommission per employee: $%,.2f\tSubtotal: $%,.2f",
              report.getReportName(), reports.get(report), report.getCommission(), subtotal));

      if (reports.get(report) > maxCountedEmployees) {
        reportSB.append(" *CAPPED*\n");
      } else {
        reportSB.append("\n");
      }
    }

    return String.format(
        type.isFinalised()
            ? ""
            : "*NOT FINALISED*\n"
                + "Order details (id #%d)\n"
                + "Date: %s\n"
                + "Reports:\n"
                + "%s"
                + "Total cost: $%,.2f\n",
        type.getOrderID(),
        type.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
        reportSB.toString(),
        type.getTotalCommission());
  }
}
