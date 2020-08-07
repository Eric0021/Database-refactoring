package au.edu.sydney.cpa.erp.feaa.FlyWeight.ValueObject;

import au.edu.sydney.cpa.erp.ordering.Report;

import java.util.Arrays;

/** Value object class. Contains all data of a unique report. */
public class Data implements Report {
  /** The object's index in the data array in the report factory. */
  private final int index;

  private final String name;
  private final double commission;
  private final double[] legalData;
  private final double[] cashFlowData;
  private final double[] mergesData;
  private final double[] tallyingData;
  private final double[] deductionsData;

  public Data(
      int index,
      String name,
      double commissions,
      double[] legalData,
      double[] cashFlowData,
      double[] mergesData,
      double[] tallyingData,
      double[] deductionsData) {
    this.index = index;
    this.name = name;
    this.commission = commissions;
    this.legalData = legalData;
    this.cashFlowData = cashFlowData;
    this.mergesData = mergesData;
    this.tallyingData = tallyingData;
    this.deductionsData = deductionsData;
  }

  /**
   * Compares equality of value with the target Data object.
   *
   * @return True if this object is equal in value to the other Data object.
   */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Data)) {
      return false;
    }
    Data data = (Data) o;
    return this.getReportName().equals(data.getReportName())
        && this.getCommission() == data.getCommission()
        && Arrays.equals(getLegalData(), data.getLegalData())
        && Arrays.equals(this.getCashFlowData(), data.getCashFlowData())
        && Arrays.equals(getMergesData(), data.getMergesData())
        && Arrays.equals(getTallyingData(), data.getTallyingData())
        && Arrays.equals(getDeductionsData(), data.getDeductionsData());
  }

  @Override
  public int hashCode() {
    return (name.hashCode()
        + Double.hashCode(commission)
        + Arrays.hashCode(legalData)
        + Arrays.hashCode(cashFlowData)
        + Arrays.hashCode(mergesData)
        + Arrays.hashCode(tallyingData)
        + Arrays.hashCode(deductionsData));
  }

  public String getReportName() {
    return name;
  }

  public double getCommission() {
    return commission;
  }

  public double[] getLegalData() {
    return legalData;
  }

  public double[] getCashFlowData() {
    return cashFlowData;
  }

  public double[] getMergesData() {
    return mergesData;
  }

  public double[] getTallyingData() {
    return tallyingData;
  }

  public double[] getDeductionsData() {
    return deductionsData;
  }

  public int getIndex() {
    return index;
  }
}
