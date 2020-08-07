package au.edu.sydney.cpa.erp.feaa.Chain;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.contact.SMS;
import au.edu.sydney.cpa.erp.feaa.ContactMethod;
import au.edu.sydney.cpa.erp.ordering.Client;

public class SMSContact implements Chain {
  private Chain nextInChain;

  @Override
  public void setNextChain(Chain nextChain) {
    this.nextInChain = nextChain;
  }

  /** Sends invoice using SMS method. */
  @Override
  public boolean sendInvoice(
      AuthToken token, Client client, ContactMethod contactMethod, String data) {
    if (contactMethod == ContactMethod.SMS) {
      String smsPhone = client.getPhoneNumber();
      if (null != smsPhone) {
        SMS.sendInvoice(token, client.getFName(), client.getLName(), data, smsPhone);
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
