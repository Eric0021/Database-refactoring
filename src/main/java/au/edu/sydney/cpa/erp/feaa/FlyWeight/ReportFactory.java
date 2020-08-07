package au.edu.sydney.cpa.erp.feaa.FlyWeight;

import au.edu.sydney.cpa.erp.feaa.FlyWeight.ValueObject.Data;
import au.edu.sydney.cpa.erp.feaa.reports.ReportDatabase;
import au.edu.sydney.cpa.erp.feaa.reports.ReportImpl;
import au.edu.sydney.cpa.erp.ordering.Report;

import java.util.*;

/** A flyweight factory that caches unique Report data */
public class ReportFactory {
  /** Contains unique reports only. */
  private static final List<Report> data = new ArrayList<>();

  private static int num_unique_report = 0;

  /**
   * If report is unique, add into the array, if not unique, do nothing.
   *
   * @return index of the newly added unique report, if not unique, returns its index in the array.
   */
  public static int add(
      String name,
      double commissionPerEmployee,
      double[] legalData,
      double[] cashflowData,
      double[] mergesData,
      double[] tallyingData,
      double[] deductionsData) {
    // returns the index of the report data in the data array.
    // This method is only called when a new reportImpl is constructed.
    // adds the new reportImpl data to array, if it is not already cached.

    int index;
    if ((index =
            get_index(
                name,
                commissionPerEmployee,
                legalData,
                cashflowData,
                mergesData,
                tallyingData,
                deductionsData))
        != -1) {
      return index;
    } else {
      // if data is not cached yet.
      data.add(
          new Data(
              num_unique_report,
              name,
              commissionPerEmployee,
              legalData,
              cashflowData,
              mergesData,
              tallyingData,
              deductionsData));
      num_unique_report++;

      return num_unique_report - 1;
    }
  }

  /** @return The index of the report in the array, if report is not cached, return -1. */
  public static int get_index(
      String name,
      double commissionPerEmployee,
      double[] legalData,
      double[] cashflowData,
      double[] mergesData,
      double[] tallyingData,
      double[] deductionsData) {
    // returns index if exists in data array already, else, return -1.
    Data new_data =
        new Data(
            0,
            name,
            commissionPerEmployee,
            legalData,
            cashflowData,
            mergesData,
            tallyingData,
            deductionsData);
    for (int i = 0; i < data.size(); i++) {
      if (data.get(i).equals(new_data)) {
        return i;
      }
    }

    return -1;
  }

  public static Report getReport(int index) {
    return data.get(index);
  }

  public static List<Report> getReports() {
    List<Report> reportsList = new ArrayList<>();

    for (int i = 0; i < num_unique_report; i++) {
      reportsList.add(new ReportImpl(i));
    }
    return reportsList;
  }
}
