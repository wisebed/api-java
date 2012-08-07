
package eu.wisebed.api.sm;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6
 * Generated source version: 2.1
 * 
 */
@WebFault(name = "ExperimentNotRunningException", targetNamespace = "urn:SessionManagementService")
public class ExperimentNotRunningException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private ExperimentNotRunningException faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public ExperimentNotRunningException_Exception(String message, ExperimentNotRunningException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public ExperimentNotRunningException_Exception(String message, ExperimentNotRunningException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: eu.wisebed.api.sm.ExperimentNotRunningException
     */
    public ExperimentNotRunningException getFaultInfo() {
        return faultInfo;
    }

}
