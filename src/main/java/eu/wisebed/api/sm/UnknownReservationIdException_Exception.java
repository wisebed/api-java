
package eu.wisebed.api.sm;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6
 * Generated source version: 2.1
 * 
 */
@WebFault(name = "UnknownReservationIdException", targetNamespace = "urn:SessionManagementService")
public class UnknownReservationIdException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private UnknownReservationIdException faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public UnknownReservationIdException_Exception(String message, UnknownReservationIdException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public UnknownReservationIdException_Exception(String message, UnknownReservationIdException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: eu.wisebed.api.sm.UnknownReservationIdException
     */
    public UnknownReservationIdException getFaultInfo() {
        return faultInfo;
    }

}
