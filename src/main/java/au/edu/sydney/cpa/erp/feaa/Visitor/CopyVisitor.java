package au.edu.sydney.cpa.erp.feaa.Visitor;

import au.edu.sydney.cpa.erp.feaa.Bridge.OrderType.FirstType;
import au.edu.sydney.cpa.erp.feaa.Bridge.OrderType.SecondType;
import au.edu.sydney.cpa.erp.ordering.Order;

/**
 * A visitor class for the Copy() method for Orders, changes the behaviour of the method depending
 * on the order's type, critical and scheduled properties.
 */
public interface CopyVisitor {
  /** Changes the behaviour of the copy() method for first type orders. */
  Order copy(FirstType type);

  /** Changes the behaviour of the copy() method for second type orders. */
  Order copy(SecondType type);
}
