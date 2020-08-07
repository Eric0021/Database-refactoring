package au.edu.sydney.cpa.erp.feaa.Visitor;

import au.edu.sydney.cpa.erp.feaa.Bridge.OrderType.FirstType;
import au.edu.sydney.cpa.erp.feaa.Bridge.OrderType.SecondType;

public class LongDescVisitorImpl implements LongDescVisitor {
  @Override
  public String longDesc(FirstType type) {
    FirstTypeLongDesc library = new FirstTypeLongDesc();

    if (type.isCritical() && type.isScheduled()) {
      return library.criticalScheduled(type);
    } else if (type.isCritical() && !type.isScheduled()) {
      return library.CriticalNonScheduled(type);
    } else if (!type.isCritical() && type.isScheduled()) {
      return library.NonCriticalScheduled(type);
    } else {
      return library.NonCriticalNonScheduled(type);
    }
  }

  @Override
  public String longDesc(SecondType type) {
    SecondTypeLongDesc library = new SecondTypeLongDesc();

    if (type.isCritical() && type.isScheduled()) {
      return library.criticalScheduled(type);
    } else if (type.isCritical() && !type.isScheduled()) {
      return library.CriticalNonScheduled(type);
    } else if (!type.isCritical() && type.isScheduled()) {
      return library.NonCriticalScheduled(type);
    } else {
      return library.NonCriticalNonScheduled(type);
    }
  }
}
