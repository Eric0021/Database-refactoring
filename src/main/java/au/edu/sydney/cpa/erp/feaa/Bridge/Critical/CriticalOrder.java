package au.edu.sydney.cpa.erp.feaa.Bridge.Critical;

/** A critical order. */
public class CriticalOrder implements Critical {
  private double criticalLoading;

  public CriticalOrder(double criticalLoading) {
    this.criticalLoading = criticalLoading;
  }

  public double getCriticalLoading() {
    return this.criticalLoading;
  }

  @Override
  public boolean isCritical() {
    return true;
  }
}
