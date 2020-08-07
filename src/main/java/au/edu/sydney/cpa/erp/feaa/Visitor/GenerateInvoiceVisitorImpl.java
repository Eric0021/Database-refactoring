package au.edu.sydney.cpa.erp.feaa.Visitor;

import au.edu.sydney.cpa.erp.feaa.Bridge.OrderType.FirstType;
import au.edu.sydney.cpa.erp.feaa.Bridge.OrderType.SecondType;
import au.edu.sydney.cpa.erp.ordering.Report;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class GenerateInvoiceVisitorImpl implements GenerateInvoiceVisitor {

  @Override
  public String generateInvoiceData(FirstType type) {
    if (type.isCritical() && type.isScheduled()) {
      return String.format(
          "Your priority business account will be charged: $%,.2f each quarter for %d quarters, with a total overall cost of: $%,.2f"
              + "\nPlease see your internal accounting department for itemised details.",
          type.getRecurringCost(), type.getNumberOfQuarters(), type.getTotalCommission());
    } else if (type.isCritical() && !type.isScheduled()) {
      return String.format(
          "Your priority business account has been charged: $%,.2f"
              + "\nPlease see your internal accounting department for itemised details.",
          type.getTotalCommission());
    } else if (!type.isCritical() && type.isScheduled()) {
      StringBuilder sb = new StringBuilder();

      sb.append("Thank you for your Crimson Permanent Assurance accounting order!\n");
      sb.append("The cost to provide these services: $");
      sb.append(String.format("%,.2f", type.getRecurringCost()));
      sb.append(" each quarter, with a total overall cost of: $");
      sb.append(String.format("%,.2f", type.getTotalCommission()));
      sb.append("\nPlease see below for details:\n");

      Map<Report, Integer> reports = type.getReports();
      List<Report> keyList = new ArrayList<>(reports.keySet());
      keyList.sort(
          Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

      for (Report report : keyList) {
        sb.append("\tReport name: ");
        sb.append(report.getReportName());
        sb.append("\tEmployee Count: ");
        sb.append(reports.get(report));
        sb.append("\tCost per employee: ");
        sb.append(String.format("$%,.2f", report.getCommission()));
        if (reports.get(report) > type.getMaxCountedEmployees()) {
          sb.append("\tThis report cost has been capped.");
        }
        sb.append("\tSubtotal: ");
        sb.append(String.format("$%,.2f\n", report.getCommission() * reports.get(report)));
      }

      return sb.toString();
    } else {
      Map<Report, Integer> reports = type.getReports();

      StringBuilder sb = new StringBuilder();

      sb.append("Thank you for your Crimson Permanent Assurance accounting order!\n");
      sb.append("The cost to provide these services: $");
      sb.append(String.format("%,.2f", type.getTotalCommission()));
      sb.append("\nPlease see below for details:\n");
      List<Report> keyList = new ArrayList<>(reports.keySet());
      keyList.sort(
          Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

      for (Report report : keyList) {
        double subtotal =
            report.getCommission() * Math.min(type.getMaxCountedEmployees(), reports.get(report));

        sb.append("\tReport name: ");
        sb.append(report.getReportName());
        sb.append("\tEmployee Count: ");
        sb.append(reports.get(report));
        sb.append("\tCost per employee: ");
        sb.append(String.format("$%,.2f", report.getCommission()));
        if (reports.get(report) > type.getMaxCountedEmployees()) {
          sb.append("\tThis report cost has been capped.");
        }
        sb.append("\tSubtotal: ");
        sb.append(String.format("$%,.2f\n", subtotal));
      }
      return sb.toString();
    }
  }

  @Override
  public String generateInvoiceData(SecondType type) {
    if (type.isCritical() && type.isScheduled()) {
      return String.format(
          "Your priority business account will be charged: $%,.2f each quarter for %d quarters, with a total overall cost of: $%,.2f"
              + "\nPlease see your internal accounting department for itemised details.",
          type.getRecurringCost(), type.getNumberOfQuarters(), type.getTotalCommission());
    } else if (type.isCritical() && !type.isScheduled()) {
      return String.format(
          "Your priority business account has been charged: $%,.2f"
              + "\nPlease see your internal accounting department for itemised details.",
          type.getTotalCommission());
    } else if (!type.isCritical() && type.isScheduled()) {
      StringBuilder sb = new StringBuilder();

      sb.append("Thank you for your Crimson Permanent Assurance accounting order!\n");
      sb.append("The cost to provide these services: $");
      sb.append(String.format("%,.2f", type.getRecurringCost()));
      sb.append(" each quarter, with a total overall cost of: $");
      sb.append(String.format("%,.2f", type.getTotalCommission()));
      sb.append("\nPlease see below for details:\n");

      Map<Report, Integer> reports = type.getReports();
      List<Report> keyList = new ArrayList<>(reports.keySet());
      keyList.sort(
          Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

      for (Report report : keyList) {
        sb.append("\tReport name: ");
        sb.append(report.getReportName());
        sb.append("\tEmployee Count: ");
        sb.append(reports.get(report));
        sb.append("\tCost per employee: ");
        sb.append(String.format("$%,.2f", report.getCommission()));
        sb.append("\tSubtotal: ");
        sb.append(String.format("$%,.2f\n", report.getCommission() * reports.get(report)));
      }
      return sb.toString();
    } else {
      Map<Report, Integer> reports = type.getReports();

      double baseCommission = 0.0;
      double loadedCommission = type.getTotalCommission();

      StringBuilder sb = new StringBuilder();

      sb.append("Thank you for your Crimson Permanent Assurance accounting order!\n");
      sb.append("The cost to provide these services: $");
      sb.append(String.format("%,.2f", type.getTotalCommission()));
      sb.append("\nPlease see below for details:\n");
      List<Report> keyList = new ArrayList<>(reports.keySet());
      keyList.sort(
          Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

      for (Report report : keyList) {
        double subtotal = report.getCommission() * reports.get(report);
        baseCommission += subtotal;

        sb.append("\tReport name: ");
        sb.append(report.getReportName());
        sb.append("\tEmployee Count: ");
        sb.append(reports.get(report));
        sb.append("\tCost per employee: ");
        sb.append(String.format("$%,.2f", report.getCommission()));
        sb.append("\tSubtotal: ");
        sb.append(String.format("$%,.2f\n", subtotal));
      }
      return sb.toString();
    }
  }
}
