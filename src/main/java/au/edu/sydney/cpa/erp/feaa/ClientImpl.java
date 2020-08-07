package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.database.TestDatabase;
import au.edu.sydney.cpa.erp.ordering.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of Client class. Implemented as a lazy loaded object, all of its fields
 * are only initialised when they are required.
 */
public class ClientImpl implements Client {

  private final int id;
  private String fName;
  private String lName;
  private String phoneNumber;
  private String emailAddress;
  private String address;
  private String suburb;
  private String state;
  private String postCode;
  private String internalAccounting;
  private String businessName;
  private String pigeonCoopID;
  private AuthToken token;

  public ClientImpl(AuthToken token, int id) {
    this.token = token;
    this.id = id;
  }

  public int getId() {
    return id;
  }

  @Override
  public String getFName() {
    if (this.fName == null) {
      this.fName = TestDatabase.getInstance().getClientField(token, id, "fName");
      System.out.println("getting fname");
    }
    return fName;
  }

  @Override
  public String getLName() {
    if (this.lName == null) {
      this.lName = TestDatabase.getInstance().getClientField(token, id, "lName");
      System.out.println("getting lname");
    }
    return lName;
  }

  @Override
  public String getPhoneNumber() {
    if (this.phoneNumber == null) {
      this.phoneNumber = TestDatabase.getInstance().getClientField(token, id, "phoneNumber");
      System.out.println("getting phoneNumber");
    }
    return phoneNumber;
  }

  @Override
  public String getEmailAddress() {
    if (this.emailAddress == null) {
      this.emailAddress = TestDatabase.getInstance().getClientField(token, id, "emailAddress");
      System.out.println("getting email address");
    }
    return emailAddress;
  }

  @Override
  public String getAddress() {
    if (this.address == null) {
      this.address = TestDatabase.getInstance().getClientField(token, id, "address");
      System.out.println("getting address");
    }
    return address;
  }

  @Override
  public String getSuburb() {
    if (this.suburb == null) {
      this.suburb = TestDatabase.getInstance().getClientField(token, id, "suburb");
      System.out.println("getting suburb");
    }
    return suburb;
  }

  @Override
  public String getState() {
    if (this.state == null) {
      this.state = TestDatabase.getInstance().getClientField(token, id, "state");
      System.out.println("getting state");
    }
    return state;
  }

  @Override
  public String getPostCode() {
    if (this.postCode == null) {
      this.postCode = TestDatabase.getInstance().getClientField(token, id, "postCode");
      System.out.println("getting post code");
    }
    return postCode;
  }

  @Override
  public String getInternalAccounting() {
    if (this.internalAccounting == null) {
      this.internalAccounting =
          TestDatabase.getInstance().getClientField(token, id, "internal accounting");
      System.out.println("getting internal accounting");
    }
    return internalAccounting;
  }

  @Override
  public String getBusinessName() {
    if (this.businessName == null) {
      this.businessName = TestDatabase.getInstance().getClientField(token, id, "businessName");
      System.out.println("getting business name");
    }
    return businessName;
  }

  @Override
  public String getPigeonCoopID() {
    if (this.pigeonCoopID == null) {
      this.pigeonCoopID = TestDatabase.getInstance().getClientField(token, id, "pigeonCoopID");
      System.out.println("getting pigeon coop");
    }
    return pigeonCoopID;
  }
}
