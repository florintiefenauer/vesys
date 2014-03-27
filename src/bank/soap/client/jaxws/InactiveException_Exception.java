
package bank.soap.client.jaxws;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "InactiveException", targetNamespace = "http://server.soap.bank/")
public class InactiveException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private InactiveException faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public InactiveException_Exception(String message, InactiveException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public InactiveException_Exception(String message, InactiveException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: bank.soap.client.jaxws.InactiveException
     */
    public InactiveException getFaultInfo() {
        return faultInfo;
    }

}
