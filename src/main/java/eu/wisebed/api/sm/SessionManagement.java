
package eu.wisebed.api.sm;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import eu.wisebed.api.common.KeyValuePair;
import eu.wisebed.api.common.SecretReservationKey;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "SessionManagement", targetNamespace = "urn:SessionManagementService")
@XmlSeeAlso({
    eu.wisebed.api.sm.ObjectFactory.class,
    eu.wisebed.api.common.ObjectFactory.class
})
public interface SessionManagement {


    /**
     * 
     * @param nodes
     * @param controllerEndpointUrl
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "areNodesAlive", targetNamespace = "urn:SessionManagementService", className = "eu.wisebed.api.sm.AreNodesAlive")
    @ResponseWrapper(localName = "areNodesAliveResponse", targetNamespace = "urn:SessionManagementService", className = "eu.wisebed.api.sm.AreNodesAliveResponse")
    public String areNodesAlive(
        @WebParam(name = "nodes", targetNamespace = "")
        List<String> nodes,
        @WebParam(name = "controllerEndpointUrl", targetNamespace = "")
        String controllerEndpointUrl);

    /**
     * 
     * @param snaaEndpointUrl
     * @param rsEndpointUrl
     * @param options
     */
    @WebMethod
    @RequestWrapper(localName = "getConfiguration", targetNamespace = "urn:SessionManagementService", className = "eu.wisebed.api.sm.GetConfiguration")
    @ResponseWrapper(localName = "getConfigurationResponse", targetNamespace = "urn:SessionManagementService", className = "eu.wisebed.api.sm.GetConfigurationResponse")
    public void getConfiguration(
        @WebParam(name = "rsEndpointUrl", targetNamespace = "", mode = WebParam.Mode.OUT)
        Holder<String> rsEndpointUrl,
        @WebParam(name = "snaaEndpointUrl", targetNamespace = "", mode = WebParam.Mode.OUT)
        Holder<String> snaaEndpointUrl,
        @WebParam(name = "options", targetNamespace = "", mode = WebParam.Mode.OUT)
        Holder<List<KeyValuePair>> options);

    /**
     * 
     * @param secretReservationKey
     * @return
     *     returns java.lang.String
     * @throws UnknownReservationIdException_Exception
     * @throws ExperimentNotRunningException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getInstance", targetNamespace = "urn:SessionManagementService", className = "eu.wisebed.api.sm.GetInstance")
    @ResponseWrapper(localName = "getInstanceResponse", targetNamespace = "urn:SessionManagementService", className = "eu.wisebed.api.sm.GetInstanceResponse")
    public String getInstance(
        @WebParam(name = "secretReservationKey", targetNamespace = "")
        List<SecretReservationKey> secretReservationKey)
        throws ExperimentNotRunningException_Exception, UnknownReservationIdException_Exception
    ;

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getNetwork", targetNamespace = "urn:CommonTypes", className = "eu.wisebed.api.common.GetNetwork")
    @ResponseWrapper(localName = "getNetworkResponse", targetNamespace = "urn:CommonTypes", className = "eu.wisebed.api.common.GetNetworkResponse")
    public String getNetwork();

}
