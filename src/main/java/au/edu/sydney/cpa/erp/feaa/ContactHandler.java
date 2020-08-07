package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.feaa.Chain.*;
import au.edu.sydney.cpa.erp.ordering.Client;

import java.util.Arrays;
import java.util.List;

/**
 * A handler class for contact methods. Handles and starts the chain of responsibility to contact
 * clients.
 */
public class ContactHandler {
  public static boolean sendInvoice(
      AuthToken token, Client client, List<ContactMethod> priority, String data) {
    List<Chain> chain =
        Arrays.asList(
            new SMSContact(),
            new MailContact(),
            new EmailContact(),
            new PhonecallContact(),
            new InternalAccountingContact(),
            new CarrierPigeonContact());

    // sets the chain of responsibility
    for (int i = 0; i < chain.size(); i++) {
      // don't set the last contact method's next chain
      if (i == chain.size() - 1) {
        continue;
      }
      chain.get(i).setNextChain(chain.get(i + 1));
    }

    for (ContactMethod method : priority) {
      if (chain.get(0).sendInvoice(token, client, method, data)) {
        return true;
      }
    }
    return false;
  }

  public static List<String> getKnownMethods() {
    return Arrays.asList(
        "Carrier Pigeon", "Email", "Mail", "Internal Accounting", "Phone call", "SMS");
  }
}
