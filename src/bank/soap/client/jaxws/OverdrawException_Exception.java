
package bank.soap.client.jaxws;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "OverdrawException", targetNamespace = "http://server.soap.bank/")
public class OverdrawException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private OverdrawException faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public OverdrawException_Exception(String message, OverdrawException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public OverdrawException_Exception(String message, OverdrawException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: bank.soap.client.jaxws.OverdrawException
     */
    public OverdrawException getFaultInfo() {
        return faultInfo;
    }

}
