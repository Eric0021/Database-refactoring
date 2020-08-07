package au.edu.sydney.cpa.erp.feaa.Chain;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.contact.CarrierPigeon;
import au.edu.sydney.cpa.erp.feaa.ContactMethod;
import au.edu.sydney.cpa.erp.ordering.Client;

public class CarrierPigeonContact implements Chain {
  private Chain nextInChain;

  @Override
  public void setNextChain(Chain nextChain) {
    this.nextInChain = nextChain;
  }

  /** Sends invoice using carrier pigeon method. */
  @Override
  public boolean sendInvoice(
      AuthToken token, Client client, ContactMethod contactMethod, String data) {
    if (contactMethod == ContactMethod.CARRIER_PIGEON) {
      String pigeonCoopID = client.getPigeonCoopID();
      if (null != pigeonCoopID) {
        CarrierPigeon.sendInvoice(token, client.getFName(), client.getLName(), data, pigeonCoopID);
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
