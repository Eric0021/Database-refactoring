package au.edu.sydney.cpa.erp.feaa.Chain;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.contact.Mail;
import au.edu.sydney.cpa.erp.feaa.ContactMethod;
import au.edu.sydney.cpa.erp.ordering.Client;

public class MailContact implements Chain {
  private Chain nextInChain;

  @Override
  public void setNextChain(Chain nextChain) {
    this.nextInChain = nextChain;
  }

  /** Sends invoice using mail method. */
  @Override
  public boolean sendInvoice(
      AuthToken token, Client client, ContactMethod contactMethod, String data) {
    if (contactMethod == ContactMethod.MAIL) {
      String address = client.getAddress();
      String suburb = client.getSuburb();
      String state = client.getState();
      String postcode = client.getPostCode();
      if (null != address && null != suburb && null != state && null != postcode) {
        Mail.sendInvoice(
            token, client.getFName(), client.getLName(), data, address, suburb, state, postcode);
        return true;
      }
      return false;
    } else {
      // if this is the last in the chain, then its next in chain will be null.
      if (nextInChain == null) {
        return false;
      }
      return nextInChain.sendInvoice(token, client, contactMethod, data);
    }
  }
}
