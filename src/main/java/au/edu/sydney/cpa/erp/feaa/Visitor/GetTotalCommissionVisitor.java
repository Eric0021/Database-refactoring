package au.edu.sydney.cpa.erp.feaa.Visitor;

import au.edu.sydney.cpa.erp.feaa.Bridge.OrderType.FirstType;
import au.edu.sydney.cpa.erp.feaa.Bridge.OrderType.SecondType;

/**
 * A visitor class for the GetTotalCommission() method for Orders, changes the behaviour of the
 * method depending on the order's type, critical and scheduled properties.
 */
public interface GetTotalCommissionVisitor {
  /** Changes the behaviour of the getTotalCommission() method for first type orders. */
  double getTotalCommission(FirstType type);

  /** Changes the behaviour of the getTotalCommission() method for second type orders. */
  double getTotalCommission(SecondType type);
}
