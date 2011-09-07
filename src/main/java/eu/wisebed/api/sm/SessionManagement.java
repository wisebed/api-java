package eu.wisebed.api.sm;

import eu.wisebed.api.common.KeyValuePair;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import java.util.List;


/**
 * The Session Management API runs on every testbed (physical or virtual) and provides access to the per-experiment WSN
 * API.
 * <p/>
 * In some cases the WSN API will be a singleton and so the testbed service will always return the same address (this
 * is the default behaviour on a privately owned testbed). For others, it will spawn a new API instance, for example on
 * a different port, and return that one. This is necessary if different users reserve different parts of the same
 * testbed. An alternative to this approach would be to use session keys on all WISEBED API calls so that the call is
 * relative to your session; however this is problematic in several respects: Firstly a WSN API implementation would
 * have to understand the notion of a "session", making it a more complex task; secondly the client of a WSN API must
 * also understand what session keys are, making this a more complex implementation. Finally, a session-key-based
 * approach would create additional work in the upgrade of WISEBED sites conforming to WSN API 1.0. For these reasons
 * we elect to use API instancing rather than session-keys.
 */
@WebService(name = "SessionManagement", targetNamespace = "urn:SessionManagementService")
@XmlSeeAlso({
		eu.wisebed.api.common.ObjectFactory.class,
		eu.wisebed.api.sm.ObjectFactory.class
})
public interface SessionManagement {


	/**
	 * Returns the status (alive/dead) of a set of nodes.
	 * <p/>
	 * Upon successful invocation, the method returns a request identifier to match the asynchronous response(s). Any
	 * implementation must asynchronously send one or more {@link eu.wisebed.api.controller.RequestStatus} message(s) to
	 * the {@link eu.wisebed.api.controller.Controller} service.
	 * <p/>
	 * Each {@link eu.wisebed.api.controller.RequestStatus} message contains a list of nodes and their {@link
	 * eu.wisebed.api.controller.Status}. The {@link eu.wisebed.api.controller.Status#getValue()} method returns the
	 * integer value of 1 if the node is alive, an integer value of 0 if it is presumed dead and an integer value of -1 if
	 * the node URN was not known to the testbed, where a node is deemed to be alive if it can be flashed with a new
	 * image.
	 * This list of returned values may be expanded in future as necessary. This operation is completed when all nodes'
	 * status have been reported. The method {@link eu.wisebed.api.controller.Status#getMsg()} may return a human readable
	 * description about the operation.
	 * <p/>
	 * Before any action is executed, this operation will validate all node URNs in {@code nodes} and if a node URN is
	 * invalid the method will throw an exception without executing the command for any of the nodes.
	 * <p/>
	 * <b>Note:</b> The implementation must ensure that this function does not interfere with running experiments. It must
	 * either check node liveliness without querying any node-resident software; or else if a liveliness check does
	 * require
	 * node-resident software communication you must first check if an experiment is in progress on that node and if so
	 * either return an error or else return a cached liveliness value.
	 * <p/>
	 * <b>Rationale for changes:</b><br/>
	 * <b>2.3:</b> Added to the {@link SessionManagement} API (in addition to the {@link eu.wisebed.api.wsn.WSN} API) to
	 * allow querying of node liveliness without having an active WSN instance.
	 *
	 * @param nodes
	 * 		a set of node URNs
	 * @param controllerEndpointUrl
	 * 		Web Service endpoint URL of the {@link eu.wisebed.api.controller.Controller} to which
	 * 		the asynchronous response(s) will be delivered
	 *
	 * @return returns java.lang.String request identifier to match the asynchronous response(s)
	 *
	 * @since 2.3
	 */
	@WebMethod
	@WebResult(targetNamespace = "")
	@RequestWrapper(localName = "areNodesAlive", targetNamespace = "urn:SessionManagementService",
			className = "eu.wisebed.api.sm.AreNodesAlive")
	@ResponseWrapper(localName = "areNodesAliveResponse", targetNamespace = "urn:SessionManagementService",
			className = "eu.wisebed.api.sm.AreNodesAliveResponse")
	public String areNodesAlive(
			@WebParam(name = "nodes", targetNamespace = "")
			List<String> nodes,
			@WebParam(name = "controllerEndpointUrl", targetNamespace = "")
			String controllerEndpointUrl);

	/**
	 * Ends the experiment and makes the testbed available again. For a testbed without a reservation system, this call
	 * may be ignored (or an exception thrown). If the reservation has already been freed calling this method has no
	 * effect.
	 * <p/>
	 * <b>Rationale for changes:</b><br/>
	 * <b>2.1:</b> For a privately owned network (say, "the student network" in your working group), it means that you
	 * have finished using the network and release it for others to use. Additionally in "commercially-run" testbed
	 * federations this feature could be used to issue a refund of paid testbed time; or even in free-to-use federations
	 * could simply be used to relinquish reserved time that the user no longer requires.
	 *
	 * @param secretReservationKey
	 * 		the secret reservation key of the experiment to "free"
	 *
	 * @throws ExperimentNotRunningException_Exception
	 * 		if the experiment has either not started or already finished
	 * @throws UnknownReservationIdException_Exception
	 * 		if the secret reservation key is unknown
	 * @since 2.1
	 */
	@WebMethod
	@RequestWrapper(localName = "free", targetNamespace = "urn:SessionManagementService",
			className = "eu.wisebed.api.sm.Free")
	@ResponseWrapper(localName = "freeResponse", targetNamespace = "urn:SessionManagementService",
			className = "eu.wisebed.api.sm.FreeResponse")
	public void free(
			@WebParam(name = "secretReservationKey", targetNamespace = "")
			List<SecretReservationKey> secretReservationKey)
			throws ExperimentNotRunningException_Exception, UnknownReservationIdException_Exception
	;

	/**
	 * Returns the "configuration" relating to this server, including the Web service endpoints of other relevant services
	 * associated with this {@link SessionManagement} endpoint.
	 * <p/>
	 * <b>Rationale for changes:</b><br/>
	 * <b>2.3:</b> Allows client applications to learn all relevant endpoint URLs and additional
	 * information using a single Web service endpoint (i.e. the {@link SessionManagement} endpoint URL).
	 *
	 * @param rsEndpointUrl
	 * 		a {@link Holder} instance in which the endpoint URL of the {@link eu.wisebed.api.rs.RS}
	 * 		service will be put
	 * @param snaaEndpointUrl
	 * 		a {@link Holder} instance in which the endpoint URL of the {@link eu.wisebed.api.snaa.SNAA}
	 * 		service will be put
	 * @param options
	 * 		a {@link Holder} instance in which a list of key-value pairs will be put, containing
	 * 		arbitrary non-mandatory additional information about testbed properties (e.g. backend
	 * 		implementation and version, additional APIs, ...)
	 *
	 * @since 2.3
	 */
	@WebMethod
	@RequestWrapper(localName = "getConfiguration", targetNamespace = "urn:SessionManagementService",
			className = "eu.wisebed.api.sm.GetConfiguration")
	@ResponseWrapper(localName = "getConfigurationResponse", targetNamespace = "urn:SessionManagementService",
			className = "eu.wisebed.api.sm.GetConfigurationResponse")
	public void getConfiguration(
			@WebParam(name = "rsEndpointUrl", targetNamespace = "", mode = WebParam.Mode.OUT)
			Holder<String> rsEndpointUrl,
			@WebParam(name = "snaaEndpointUrl", targetNamespace = "", mode = WebParam.Mode.OUT)
			Holder<String> snaaEndpointUrl,
			@WebParam(name = "options", targetNamespace = "", mode = WebParam.Mode.OUT)
			Holder<List<KeyValuePair>> options);

	/**
	 * Returns the endpoint URL of the {@link eu.wisebed.api.wsn.WSN} API associated with the given {@code
	 * secretReservationKey}.
	 * <p/>
	 * If the implementation is a federator this method may return a fault if one of the federated {@link
	 * SessionManagement} endpoints is unreachable or returns a fault. Federator implementations must not call the {@link
	 * SessionManagement#free(java.util.List)} method if this happens.
	 * <p/>
	 * If this method is called repeatedly with different {@link eu.wisebed.api.controller.Controller} endpoint URLs these
	 * URLs will be added to a set of {@link eu.wisebed.api.controller.Controller} endpoints of which all will be treated
	 * as equals.
	 * <p/>
	 * <b>Rationale for changes:</b><br/>
	 * <b>2.0:</b> initial appearance as setPortalEndpoint()<br/>
	 * <b>2.1:</b> changed to getInstance() and moved to {@link SessionManagement} API
	 *
	 * @param secretReservationKey
	 * 		the secret reservation key that was returned by the reservation system / website when
	 * 		the reservation was made, used here as credentials. For a privately owned network
	 * 		without secret reservation key, this parameter would be an empty string.
	 * @param controller
	 * 		The endpoint URL of the {@link eu.wisebed.api.controller.Controller} service. This is
	 * 		typically the "owner" of the experiment. All asynchronous replies to {@link
	 * 		eu.wisebed.api.wsn.WSN} API calls are being sent to this endpoint URL.
	 *
	 * @return the endpoint URL of the {@link eu.wisebed.api.wsn.WSN} API associated with the given {@code
	 *         secretReservationKey}.
	 *
	 * @throws ExperimentNotRunningException_Exception
	 * 		if the experiment has either not started or already finished
	 * @throws UnknownReservationIdException_Exception
	 * 		if the secret reservation key is unknown
	 * @since 2.1
	 */
	@WebMethod
	@WebResult(targetNamespace = "")
	@RequestWrapper(localName = "getInstance", targetNamespace = "urn:SessionManagementService",
			className = "eu.wisebed.api.sm.GetInstance")
	@ResponseWrapper(localName = "getInstanceResponse", targetNamespace = "urn:SessionManagementService",
			className = "eu.wisebed.api.sm.GetInstanceResponse")
	public String getInstance(
			@WebParam(name = "secretReservationKey", targetNamespace = "")
			List<SecretReservationKey> secretReservationKey,
			@WebParam(name = "controller", targetNamespace = "")
			String controller)
			throws ExperimentNotRunningException_Exception, UnknownReservationIdException_Exception
	;

	/**
	 * Returns the average/static state of the network described as specified by the "static" part of a WiseML ﬁle.
	 * <p/>
	 * This function is also contained in the {@link eu.wisebed.api.wsn.WSN} API but {@link
	 * eu.wisebed.api.wsn.WSN#getNetwork()} returns current data opposed to static data returned here.
	 * <p/>
	 * <b>Rationale for changes:</b><br/>
	 * <b>1.1 Monitoring API:</b> Allows a client of the WSN API to acquire the network topology of that instance. Using
	 * virtualisation this may not necessarily correspond to any physical testbed. Also allows querying of changes in
	 * the network after the reservation has been made (say, one node died and was replaced by one with different ID).
	 *
	 * @return an average/static description of the testbed in WiseML format
	 *
	 * @since 1.1
	 */
	@WebMethod
	@WebResult(targetNamespace = "")
	@RequestWrapper(localName = "getNetwork", targetNamespace = "urn:CommonTypes",
			className = "eu.wisebed.api.common.GetNetwork")
	@ResponseWrapper(localName = "getNetworkResponse", targetNamespace = "urn:CommonTypes",
			className = "eu.wisebed.api.common.GetNetworkResponse")
	public String getNetwork();

}
