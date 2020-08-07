package au.edu.sydney.cpa.erp.feaa.Visitor;

import au.edu.sydney.cpa.erp.feaa.Bridge.OrderType.FirstType;
import au.edu.sydney.cpa.erp.feaa.Bridge.OrderType.SecondType;

/**
 * A visitor class for the LongDesc() method for Orders, changes the behaviour of the method
 * depending on the order's type, critical and scheduled properties.
 */
public interface LongDescVisitor {
  /** Changes the behaviour of the longDesc() method for first type orders. */
  String longDesc(FirstType type);

  /** Changes the behaviour of the longDesc() method for second type orders. */
  String longDesc(SecondType type);
}
