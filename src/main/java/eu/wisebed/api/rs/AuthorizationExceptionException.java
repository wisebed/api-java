
package eu.wisebed.api.rs;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebFault(name = "AuthorizationFault", targetNamespace = "urn:RSService")
public class AuthorizationExceptionException
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private AuthorizationException faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public AuthorizationExceptionException(String message, AuthorizationException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public AuthorizationExceptionException(String message, AuthorizationException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: eu.wisebed.api.rs.AuthorizationException
     */
    public AuthorizationException getFaultInfo() {
        return faultInfo;
    }

}
