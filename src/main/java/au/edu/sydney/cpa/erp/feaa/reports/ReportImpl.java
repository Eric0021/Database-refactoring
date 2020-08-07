package au.edu.sydney.cpa.erp.feaa.reports;

import au.edu.sydney.cpa.erp.feaa.FlyWeight.ReportFactory;
import au.edu.sydney.cpa.erp.ordering.Report;

import java.util.Arrays;
import java.util.Objects;

/** A value object, its data is stored in flyweight factory. */
public class ReportImpl implements Report {
  // ReportImpl is a value object, equality is based on their value, not their identity.
  private int index;

  public ReportImpl(
      String name,
      double commissionPerEmployee,
      double[] legalData,
      double[] cashFlowData,
      double[] mergesData,
      double[] tallyingData,
      double[] deductionsData) {

    // ReportFactory.add returns the index of the existing report with the same data, or
    // caches the report and returns the index of the new cache.
    this.index =
        ReportFactory.add(
            name,
            commissionPerEmployee,
            legalData,
            cashFlowData,
            mergesData,
            tallyingData,
            deductionsData);
  }

  public ReportImpl(int index) {
    this.index = index;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Report)) {
      return false;
    }
    Report report = (Report) o;
    return this.getReportName().equals(report.getReportName())
        && this.getCommission() == report.getCommission()
        && Arrays.equals(getLegalData(), report.getLegalData())
        && Arrays.equals(this.getCashFlowData(), report.getCashFlowData())
        && Arrays.equals(getMergesData(), report.getMergesData())
        && Arrays.equals(getTallyingData(), report.getTallyingData())
        && Arrays.equals(getDeductionsData(), report.getDeductionsData());
  }

  @Override
  public int hashCode() {
    Report current_report = ReportFactory.getReport(this.index);
    String name = current_report.getReportName();
    Double commissionPerEmployee = current_report.getCommission();
    double[] legalData = current_report.getLegalData();
    double[] mergesData = current_report.getMergesData();
    double[] cashFlowData = current_report.getCashFlowData();
    double[] tallyingData = current_report.getTallyingData();
    double[] deductionsData = current_report.getDeductionsData();

    return Objects.hash(
        name,
        commissionPerEmployee,
        legalData,
        cashFlowData,
        mergesData,
        tallyingData,
        deductionsData);
  }

  @Override
  public String getReportName() {
    return ReportFactory.getReport(index).getReportName();
  }

  @Override
  public double getCommission() {
    return ReportFactory.getReport(index).getCommission();
  }

  @Override
  public double[] getLegalData() {
    return ReportFactory.getReport(index).getLegalData();
  }

  @Override
  public double[] getCashFlowData() {
    return ReportFactory.getReport(index).getCashFlowData();
  }

  @Override
  public double[] getMergesData() {
    return ReportFactory.getReport(index).getMergesData();
  }

  @Override
  public double[] getTallyingData() {
    return ReportFactory.getReport(index).getTallyingData();
  }

  @Override
  public double[] getDeductionsData() {
    return ReportFactory.getReport(index).getDeductionsData();
  }

  @Override
  public String toString() {
    return String.format("%s", this.getReportName());
  }
}
