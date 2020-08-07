package au.edu.sydney.cpa.erp.feaa.Chain;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.feaa.ContactMethod;
import au.edu.sydney.cpa.erp.ordering.Client;

/** Interface for chain of responsibility. */
public interface Chain {

  /** Sets the next contact method in the chain. */
  void setNextChain(Chain nextChain);

  /**
   * Sends the invoice out to the client.
   *
   * @return boolean of whether send invoice was successful.
   */
  boolean sendInvoice(AuthToken token, Client client, ContactMethod contactMethod, String data);
}
