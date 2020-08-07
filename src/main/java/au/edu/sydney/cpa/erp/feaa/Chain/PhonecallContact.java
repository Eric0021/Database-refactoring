package au.edu.sydney.cpa.erp.feaa.Chain;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.contact.PhoneCall;
import au.edu.sydney.cpa.erp.feaa.ContactMethod;
import au.edu.sydney.cpa.erp.ordering.Client;

public class PhonecallContact implements Chain {
  private Chain nextInChain;

  @Override
  public void setNextChain(Chain nextChain) {
    this.nextInChain = nextChain;
  }

  /** Sends invoice using phone call method. */
  @Override
  public boolean sendInvoice(
      AuthToken token, Client client, ContactMethod contactMethod, String data) {
    if (contactMethod == ContactMethod.PHONECALL) {
      String phone = client.getPhoneNumber();
      if (null != phone) {
        PhoneCall.sendInvoice(token, client.getFName(), client.getLName(), data, phone);
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
