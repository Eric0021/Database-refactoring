package au.edu.sydney.cpa.erp.feaa.Bridge.Critical;

/** Used by order classes. These orders can be critical or regular. */
public interface Critical {
  double getCriticalLoading();

  boolean isCritical();
}
