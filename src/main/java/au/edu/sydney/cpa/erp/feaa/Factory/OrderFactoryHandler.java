package au.edu.sydney.cpa.erp.feaa.Factory;

import au.edu.sydney.cpa.erp.ordering.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** A handler for all the different Order Factories. */
public class OrderFactoryHandler {
  private FirstTypeFactory first = new FirstTypeFactoryImpl();
  private SecondTypeFactory second = new SecondTypeFactoryImpl();
  private int orderType;
  private int id;
  private LocalDateTime date;
  private int client;
  private boolean isCritical;
  private boolean isScheduled;
  private int maxCountedEmployees;
  private double criticalLoading;
  private int numQuarters;

  /**
   * Used to check whether all relevant fields are set in this class. Contains integers from 0 to
   * (number of fields -1) if all fields are set.
   */
  private List<Integer> setFields = new ArrayList<>();

  /** The number of fields the handler class has. */
  private int numFields = 9;

  public Order createOrder() {
    switch (orderType) {
      case (1):
        return createFirstType();
      case (2):
        return createSecondType();
      default:
        return null;
    }
  }

  private Order createFirstType() {
    return first.createFirstType(
        id,
        date,
        client,
        isCritical,
        isScheduled,
        maxCountedEmployees,
        criticalLoading,
        numQuarters);
  }

  private Order createSecondType() {
    return second.createSecondType(
        id, date, client, isCritical, isScheduled, criticalLoading, numQuarters);
  }

  public OrderFactoryHandler setId(int id) {
    this.id = id;
    if (!setFields.contains(0)) {
      // we only want to set it once.
      setFields.add(0);
    }
    return this;
  }

  public OrderFactoryHandler setDate(LocalDateTime date) {
    this.date = date;
    if (!setFields.contains(1)) {
      setFields.add(1);
    }
    return this;
  }

  public OrderFactoryHandler setClient(int client) {
    this.client = client;
    if (!setFields.contains(2)) {
      setFields.add(2);
    }
    return this;
  }

  public OrderFactoryHandler setCritical(boolean critical) {
    isCritical = critical;
    if (!setFields.contains(3)) {
      setFields.add(3);
    }
    return this;
  }

  public OrderFactoryHandler setScheduled(boolean scheduled) {
    isScheduled = scheduled;
    if (!setFields.contains(4)) {
      setFields.add(4);
    }
    return this;
  }

  public OrderFactoryHandler setMaxCountedEmployees(int maxCountedEmployees) {
    this.maxCountedEmployees = maxCountedEmployees;
    if (!setFields.contains(5)) {
      setFields.add(5);
    }
    return this;
  }

  public OrderFactoryHandler setCriticalLoading(double criticalLoading) {
    this.criticalLoading = criticalLoading;
    if (!setFields.contains(6)) {
      setFields.add(6);
    }
    return this;
  }

  public OrderFactoryHandler setNumQuarters(int numQuarters) {
    this.numQuarters = numQuarters;
    if (!setFields.contains(7)) {
      setFields.add(7);
    }
    return this;
  }

  public OrderFactoryHandler setOrderType(int orderType) {
    this.orderType = orderType;
    if (!setFields.contains(8)) {
      setFields.add(8);
    }
    return this;
  }

  /**
   * Checks whether all relevant fields in this class are set.
   *
   * @return boolean of whether all relevant fields in this class are set.
   */
  public boolean dataIsComplete() {
    // All fields need to be set at least once.
    if (setFields.size() < numFields) {
      return false;
    }
    for (int i = 0; i < numFields; i++) {
      if (!setFields.contains(i)) {
        return false;
      }
    }

    return true;
  }
}
