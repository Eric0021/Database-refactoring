package au.edu.sydney.cpa.erp.feaa.Visitor;

import au.edu.sydney.cpa.erp.feaa.Bridge.OrderType.FirstType;
import au.edu.sydney.cpa.erp.feaa.Bridge.OrderType.SecondType;

/**
 * A visitor class for the GenerateInvoice() method for Orders, changes the behaviour of the method
 * depending on the order's type, critical and scheduled properties.
 */
public interface GenerateInvoiceVisitor {
  /** Changes the behaviour of the generateInvoiceData() method for first type orders. */
  String generateInvoiceData(FirstType type);

  /** Changes the behaviour of the generateInvoiceData() method for second type orders. */
  String generateInvoiceData(SecondType type);
}
