package au.edu.sydney.cpa.erp.feaa.Visitor;

/** A handler class for all the visitor classes. */
public class Visitors {
  private GetTotalCommissionVisitor commissionVisitor = new CommissionVisitorImpl();
  private CopyVisitor copyVisitor = new CopyVisitorImpl();
  private LongDescVisitor longDescVisitor = new LongDescVisitorImpl();
  private ShortDescVisitor shortDescVisitor = new ShortDescVisitorImpl();
  private GenerateInvoiceVisitor generateInvoiceVisitor = new GenerateInvoiceVisitorImpl();

  public GetTotalCommissionVisitor getCommissionVisitor() {
    return commissionVisitor;
  }

  public CopyVisitor getCopyVisitor() {
    return copyVisitor;
  }

  public LongDescVisitor getLongDescVisitor() {
    return longDescVisitor;
  }

  public ShortDescVisitor getShortDescVisitor() {
    return shortDescVisitor;
  }

  public GenerateInvoiceVisitor getGenerateInvoiceVisitor() {
    return generateInvoiceVisitor;
  }
}
