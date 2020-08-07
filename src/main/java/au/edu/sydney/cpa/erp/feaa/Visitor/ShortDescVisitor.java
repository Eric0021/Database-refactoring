package au.edu.sydney.cpa.erp.feaa.Visitor;

import au.edu.sydney.cpa.erp.feaa.Bridge.OrderType.FirstType;
import au.edu.sydney.cpa.erp.feaa.Bridge.OrderType.SecondType;

/**
 * A visitor class for the ShortDesc() method for Orders, changes the behaviour of the method
 * depending on the order's type, critical and scheduled properties.
 */
public interface ShortDescVisitor {
  /** Changes the behaviour of the shortDesc() method for first type orders. */
  String shortDesc(FirstType type);

  /** Changes the behaviour of the shortDesc() method for second type orders. */
  String shortDesc(SecondType type);
}
