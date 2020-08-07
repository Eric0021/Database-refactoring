package au.edu.sydney.cpa.erp.feaa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** A multi-thread enabled class to turn contact priority from a list of string to ContactMethod. */
public class GetContactPriority implements Runnable {
  private List<ContactMethod> contactPriorityAsMethods = new ArrayList<>();
  private List<String> contactPriority = new ArrayList<>();

  public GetContactPriority(List<String> contactPriority) {
    this.contactPriority = contactPriority;
  }

  @Override
  public void run() {
    if (null != contactPriority) {
      for (String method : contactPriority) {
        switch (method.toLowerCase()) {
          case "internal accounting":
            contactPriorityAsMethods.add(ContactMethod.INTERNAL_ACCOUNTING);
            break;
          case "email":
            contactPriorityAsMethods.add(ContactMethod.EMAIL);
            break;
          case "carrier pigeon":
            contactPriorityAsMethods.add(ContactMethod.CARRIER_PIGEON);
            break;
          case "mail":
            contactPriorityAsMethods.add(ContactMethod.MAIL);
            break;
          case "phone call":
            contactPriorityAsMethods.add(ContactMethod.PHONECALL);
            break;
          case "sms":
            contactPriorityAsMethods.add(ContactMethod.SMS);
            break;
          default:
            break;
        }
      }
    }

    if (contactPriorityAsMethods.size() == 0) { // needs setting to default
      contactPriorityAsMethods =
          Arrays.asList(
              ContactMethod.INTERNAL_ACCOUNTING,
              ContactMethod.EMAIL,
              ContactMethod.CARRIER_PIGEON,
              ContactMethod.MAIL,
              ContactMethod.PHONECALL);
    }
  }

  public List<ContactMethod> getContactPriorityAsMethods() {
    return this.contactPriorityAsMethods;
  }
}
