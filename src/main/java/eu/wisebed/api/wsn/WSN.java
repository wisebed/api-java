package eu.wisebed.api.wsn;

import eu.wisebed.api.common.Message;
import eu.wisebed.api.controller.Controller;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import java.util.List;


/**
 * This interface provides all methods that allow direct interaction with the sensor nodes of a testbed (experiment).
 * Typically there is one instance of this API per experiment (i.e. reservation) and the Web service endpoint URL can
 * only be retrieved by calling {@link eu.wisebed.api.sm.SessionManagement#getInstance(java.util.List, String)} using
 * the secret reservation key that was acquired during the reservation step (security by obscurity).
 */
@WebService(name = "WSN", targetNamespace = "urn:WSNService")
@XmlSeeAlso({
		eu.wisebed.api.common.ObjectFactory.class,
		eu.wisebed.api.wsn.ObjectFactory.class
})
public interface WSN {


	/**
	 * Adds a {@link Controller} service endpoint URL to the set of controllers.
	 * <p/>
	 * <b>Please note:</b> This is an unofficial API method that may not be supported by every testbed.
	 *
	 * @param controllerEndpointUrl
	 * 		the endpoint URL of the {@link Controller} service to add
	 */
	@WebMethod
	@RequestWrapper(
			localName = "addController",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.AddController"
	)
	@ResponseWrapper(
			localName = "addControllerResponse",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.AddControllerResponse"
	)
	public void addController(
			@WebParam(name = "controllerEndpointUrl", targetNamespace = "") String controllerEndpointUrl);

	/**
	 * Returns the status (alive/dead) of a set of nodes defined by their node URNs in the parameter {@code nodes}.
	 * <p/>
	 * Upon successful invocation returns a request identifier to match the asynchronous reply. Any implementation must
	 * asynchronously send a RequestStatus message to the controller.
	 * <p/>
	 * Each RequestStatus message contains a list of nodes and their status. The "value" field contains a "1" if the
	 * node is alive, a "0" if it is presumed dead and a "-1" if the node ID was not known to the testbed, where a node
	 * is deemed to be alive if it can be flashed with a new image. This list of return values may be expanded in future
	 * as necessary. This operation is completed when all nodes have been reported. The msg field may contain a human
	 * readable description about the operation.
	 * <p/>
	 * Before any action is executed, this operation will validate all node URNs in {@code nodes} and if a node URN is
	 * invalid it will throw a fault indication without executing the command for any of the nodes.
	 *
	 * @param nodes
	 * 		a set of node URNs
	 *
	 * @return a request ID for asynchronous response(s) to the {@link Controller}
	 */
	@WebMethod
	@WebResult(
			targetNamespace = ""
	)
	@RequestWrapper(
			localName = "areNodesAlive",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.AreNodesAlive"
	)
	@ResponseWrapper(
			localName = "areNodesAliveResponse",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.AreNodesAliveResponse"
	)
	public String areNodesAlive(
			@WebParam(name = "nodes", targetNamespace = "") List<String> nodes);

	/**
	 * Deletes a virtual link.
	 * <p/>
	 * Upon successful invocation returns a request identifier to match the asynchronous reply. Any implementation must
	 * asynchronously send a RequestStatus message to the controller.
	 * <p/>
	 * A RequestStatus message delivers the result of the operation; if the link was successfully removed the "value"
	 * field is set to "1", if no such link existed is set to "2", if the operation failed is set to "0", and if one of
	 * the node IDs was unknown is set to "-1".
	 * <p/>
	 * <b>Rationale for changes:</b><br/>
	 * <b>2.1:</b> see {@link WSN#setVirtualLink(String, String, String, java.util.List, java.util.List)}
	 *
	 * @param sourceNode
	 * 		URN of a node, not necessarily in the local testbed
	 * @param targetNode
	 * 		URN of the destination node, not necessarily in the local testbed
	 *
	 * @return a request ID for asynchronous response(s) to the {@link Controller}
	 *
	 * @since 2.1
	 */
	@WebMethod
	@WebResult(
			targetNamespace = ""
	)
	@RequestWrapper(
			localName = "destroyVirtualLink",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.DestroyVirtualLink"
	)
	@ResponseWrapper(
			localName = "destroyVirtualLinkResponse",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.SetVirtualLinkResponse"
	)
	public String destroyVirtualLink(
			@WebParam(name = "sourceNode", targetNamespace = "") String sourceNode,
			@WebParam(name = "targetNode", targetNamespace = "") String targetNode);

	/**
	 * Turns off a node.
	 * <p/>
	 * Upon successful invocation returns a request identifier to match the asynchronous reply. Any implementation must
	 * asynchronously send a RequestStatus message to the controller.
	 * <p/>
	 * A RequestStatus message delivers the result of the operation; if the node was successfully disabled
	 * the "value" field is set to "1", if the operation failed is set to "0", and if the node ID was unknown is
	 * set to "-1".
	 *
	 * @param node
	 * 		the node URN of a node in the local or federated testbed served by this {@link WSN} instance
	 *
	 * @return a request ID for asynchronous response(s) to the {@link Controller}
	 *
	 * @since 2.1
	 */
	@WebMethod
	@WebResult(
			targetNamespace = ""
	)
	@RequestWrapper(
			localName = "disableNode",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.DisableNode"
	)
	@ResponseWrapper(
			localName = "disableNodeResponse",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.DisableNodeResponse"
	)
	public String disableNode(
			@WebParam(name = "node", targetNamespace = "") String node);

	/**
	 * Disables the directed physical radio link between {@code nodeA} and {@code nodeB} in the topology control logic
	 * on the nodes. In real WSN nodes this is supported by suppressing messages from the relevant source using an
	 * appropriate software component.
	 * <p/>
	 * Upon successful invocation returns a request identifier to match the asynchronous reply. Any implementation must
	 * asynchronously send a RequestStatus message to the controller.
	 * <p/>
	 * A RequestStatus message delivers the result of the operation; if the link was successfully disabled the "value"
	 * field is set to "1", if the operation failed it is set to "0", and if one of the node IDs was unknown is set to
	 * "-1".
	 *
	 * @param nodeA
	 * 		URN of the source node
	 * @param nodeB
	 * 		URN of the target node
	 *
	 * @return a request ID for asynchronous response(s) to the {@link Controller}
	 *
	 * @since 2.1
	 */
	@WebMethod
	@WebResult(
			targetNamespace = ""
	)
	@RequestWrapper(
			localName = "disablePhysicalLink",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.DisablePhysicalLink"
	)
	@ResponseWrapper(
			localName = "disablePhysicalLinkResponse",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.DisablePhysicalLinkResponse"
	)
	public String disablePhysicalLink(
			@WebParam(name = "nodeA", targetNamespace = "") String nodeA,
			@WebParam(name = "nodeB", targetNamespace = "") String nodeB);

	/**
	 * Turns on a node.
	 * <p/>
	 * A RequestStatus message delivers the result of the operation; if the node was successfully enabled the
	 * "value" field is set to "1", if the operation failed is set to "0", and if the node ID was unknown is set
	 * to "-1".
	 *
	 * @param node
	 * 		URN of a node in the local or federated testbed served by this {@link WSN} instance
	 *
	 * @return a request ID for asynchronous response(s) to the {@link Controller}
	 */
	@WebMethod
	@WebResult(
			targetNamespace = ""
	)
	@RequestWrapper(
			localName = "enableNode",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.EnableNode"
	)
	@ResponseWrapper(
			localName = "enableNodeResponse",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.EnableNodeResponse"
	)
	public String enableNode(
			@WebParam(name = "node", targetNamespace = "") String node);

	/**
	 * Enables the directed physical radio link between {@code nodeA} and {@code nodeB} in the topology control logic
	 * on the nodes. For simulated nodes: Actually creates a link.
	 * <p/>
	 * Upon successful invocation returns a request identifier to match the asynchronous reply. Any implementation must
	 * asynchronously send a RequestStatus message to the controller.
	 * <p/>
	 * A RequestStatus message delivers the result of the operation; if the link was successfully enabled the
	 * "value" field is set to "1", if the operation failed it is set to "0", and if one of the node IDs was unknown
	 * is set to "-1".
	 *
	 * @param nodeA
	 * 		URN of the source node
	 * @param nodeB
	 * 		URN of the target node
	 *
	 * @return a request ID for asynchronous response(s) to the {@link Controller}
	 */
	@WebMethod
	@WebResult(
			targetNamespace = ""
	)
	@RequestWrapper(
			localName = "enablePhysicalLink",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.EnablePhysicalLink"
	)
	@ResponseWrapper(
			localName = "enablePhysicalLinkResponse",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.EnablePhysicalLinkResponse"
	)
	public String enablePhysicalLink(
			@WebParam(name = "nodeA", targetNamespace = "") String nodeA,
			@WebParam(name = "nodeB", targetNamespace = "") String nodeB);

	/**
	 * Flashes a set of nodes.
	 * <p/>
	 * Upon successful invocation returns a request identifier to match the asynchronous replies. Any implementation must
	 * asynchronously send one or more RequestStatus to the controller.
	 * <p/>
	 * Each RequestStatus contains a list of nodes and the current progress of the flashing. The "value" ﬁeld indicates
	 * a progress from 0% to 100% using values from "0" to "100" where "100" indicates success. A value of "-1"
	 * indicates failure, and "-2" indicates an unknown node ID. The msg field may contain a human readable description
	 * of the success or failure. The operation is finished once all nodes have a reported value of "-1", "-2" or "100".
	 * Please note that for each node, multiple status updates may arrive to indicate the progress of the operation.
	 * Values greater or equal to "0" and less than "100" may therefore be safely ignored.
	 * <p/>
	 * The programs are transmitted as an array of the Program data type. It contains the binary image as a byte array
	 * and metadata about the program (version, name, platform, other). To specify which program is flashed on which
	 * node, the parameter "programIndices" is used as follows: {@code nodeIds} and {@code programIndices} and have the
	 * same length, and the value at {@code programIndices[i]} specifies the index into {@code programs} which should be
	 * flashed to {@code nodeIds[i]}. This is done for efficiency to avoid repeating the same program many times in
	 * {@code programs}.
	 * <p/>
	 * The following example in Java-based pseudo code illustrates how to flash Program {@code pX} on nodes
	 * {@code urn:wisebed:node:uzl1:node345} and {@code urn:wisebed:node:uzl1:node678} and Program {@code pY} on node
	 * {@code urn:wisebed:node:uzltestbed:node123}:
	 * <pre>
	 * String[] nodeURNs = new String[] {
	 *     "urn:wisebed:node:uzltestbed:node123",
	 *     "urn:wisebed:node:uzltestbed:node345",
	 *     "urn:wisebed:node:uzltestbed:node678"
	 * }
	 *
	 * Program pX = new Program(...), pY = new Program(...);
	 *
	 * Program[] programs = new Program[] {pX, pY};
	 *
	 * // index 0 is program pX, index 1 is program pY
	 * int[] programIndices = new int[] { 1, 0, 0 };
	 *
	 * wsn.flashPrograms(nodeURNs, programIndices, programs);
	 * </pre>
	 * <p/>
	 * Before any action is executed, this operation will validate that all the parameters are semantically
	 * consistent before executing any of the flash actions, i.e., if for every node ID there’s a program index
	 * and for every program index there’s a program. If the parameters are semantically inconsistent a fault
	 * is indicated.
	 *
	 * @param nodeIds
	 * 		the set of node URNs to flash
	 * @param programIndices
	 * 		a list containing an index in the {@code programs} list for every node index in {@code nodeIds}
	 * @param programs
	 * 		a set of programs to be flashed
	 *
	 * @return a request ID for asynchronous response(s) to the {@link Controller}
	 */
	@WebMethod
	@WebResult(
			targetNamespace = ""
	)
	@RequestWrapper(
			localName = "flashPrograms",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.FlashPrograms"
	)
	@ResponseWrapper(
			localName = "flashProgramsResponse",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.FlashProgramsResponse"
	)
	public String flashPrograms(
			@WebParam(name = "nodeIds", targetNamespace = "") List<String> nodeIds,
			@WebParam(name = "programIndices", targetNamespace = "") List<Integer> programIndices,
			@WebParam(name = "programs", targetNamespace = "") List<Program> programs);

	/**
	 * Returns all available channel handlers.
	 *
	 * @return a list of channel handler descriptions or an empty list of no channel handlers are supported
	 *
	 * @since 2.3
	 */
	@WebMethod
	@WebResult(
			targetNamespace = ""
	)
	@RequestWrapper(
			localName = "getSupportedChannelHandlers",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.GetSupportedChannelHandlers"
	)
	@ResponseWrapper(
			localName = "getSupportedChannelHandlersResponse",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.GetSupportedChannelHandlersResponse"
	)
	public List<ChannelHandlerDescription> getSupportedChannelHandlers();

	/**
	 * Returns a set of virtual link filter names supported.
	 *
	 * @return a set of virtual link filter names or an empty set if none are supported
	 */
	@WebMethod
	@WebResult(
			targetNamespace = ""
	)
	@RequestWrapper(
			localName = "getFilters",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.GetFilters"
	)
	@ResponseWrapper(
			localName = "getFiltersResponse",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.GetFiltersResponse"
	)
	public List<String> getFilters();

	/**
	 * Returns the current state of the network (i.e. the state at the time this function is called) described as
	 * specified by the "static" part of a WiseML file.
	 * <p/>
	 * Allows a client of the WSN API to acquire the network topology of that instance. Please note that, if using
	 * testbed virtualisation, this may not necessarily correspond to any physical testbed.
	 * <p/>
	 * Allows querying of changes in the network after the reservation has
	 * been made.
	 * <p/>
	 * <b>Rationale for changes:</b><br/>
	 * <b>1.0:</b> Appeared as getRecords()<br/>
	 * <b>2.1:</b> Returns WiseML instead of GraphML
	 *
	 * @return the current state of the network as a WiseML file
	 */
	@WebMethod
	@WebResult(
			targetNamespace = ""
	)
	@RequestWrapper(
			localName = "getNetwork",
			targetNamespace = "urn:CommonTypes",
			className = "eu.wisebed.api.common.GetNetwork"
	)
	@ResponseWrapper(
			localName = "getNetworkResponse",
			targetNamespace = "urn:CommonTypes",
			className = "eu.wisebed.api.common.GetNetworkResponse"
	)
	public String getNetwork();

	/**
	 * Returns the version of this {@link WSN} API instance.
	 * <p/>
	 * The version string matches [0-9]+.[0-9]+  .
	 * <p/>
	 * Ability to distinguish what behaviour to expect from the API, useful in migrating between versions of this API –
	 * for example allowing some sites to implement "1.0" while others test "2.0".
	 *
	 * @return the version of this {@link WSN} API instance
	 */
	@WebMethod
	@WebResult(
			targetNamespace = ""
	)
	@RequestWrapper(
			localName = "getVersion",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.GetVersion"
	)
	@ResponseWrapper(
			localName = "getVersionResponse",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.GetVersionResponse"
	)
	public String getVersion();

	/**
	 * Removes a {@link Controller} service endpoint URL from the set of controllers.
	 * <p/>
	 * <b>Please note:</b> This is an unofficial API method that may not be supported by every testbed.
	 *
	 * @param controllerEndpointUrl
	 * 		the endpoint URL of the {@link Controller} service to remove
	 *
	 * @since 2.1
	 */
	@WebMethod
	@RequestWrapper(
			localName = "removeController",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.RemoveController"
	)
	@ResponseWrapper(
			localName = "removeControllerResponse",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.RemoveControllerResponse"
	)
	public void removeController(
			@WebParam(name = "controllerEndpointUrl", targetNamespace = "") String controllerEndpointUrl);

	/**
	 * Resets a set of nodes.
	 * <p/>
	 * Upon successful invocation, returns a request identifier to match the asynchronous replies. Any implementation
	 * must asynchronously send one or more RequestStatus messages to the controller.
	 * <p/>
	 * Each RequestStatus contains a list of nodes which were reset or could not be reset. To indicate successful reset
	 * of a node, the "value" field contains a "1". Upon failure, it is set to "0". A value of "-1" indicates an unknown
	 * node ID. This operation is completed when all nodes have been reported. The msg field may contain a human
	 * readable description of the success or failure.
	 * <p/>
	 * <b>Rationale for changes:</b><br/>
	 * <b>1.0:</b> Initial appearance<br/>
	 * <b>2.0:</b> Operates asynchronously; extended to multiple nodes that may be queried
	 *
	 * @param nodes
	 * 		a set of node URNs to reset
	 *
	 * @return a request ID for asynchronous response(s) to the {@link Controller}
	 *
	 * @since 1.0
	 */
	@WebMethod
	@WebResult(
			targetNamespace = ""
	)
	@RequestWrapper(
			localName = "resetNodes",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.ResetNodes"
	)
	@ResponseWrapper(
			localName = "resetNodesResponse",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.ResetNodesResponse"
	)
	public String resetNodes(
			@WebParam(name = "nodes", targetNamespace = "") List<String> nodes);

	/**
	 * Sends a message to a set of nodes.
	 * <p/>
	 * Upon successful invocation returns a request identiﬁer to match the asynchronous replies. Any implementation must
	 * asynchronously send one or more RequestStatus message to the controller.
	 * <p/>
	 * Each RequestStatus message contains a list of nodes. If a message was delivered to a node, the "value" field is
	 * set to "1", else a "0". A value of "-1" indicates an unknown node ID. This operation is completed when all nodes
	 * have been reported. The msg field may contain a human readable description of the success or failure.
	 * <p/>
	 * This function could for example be used to inject events into the sensor network, send messages to sensor nodes
	 * from virtual links, or even upload individual components to sensor nodes if the experiment software is using a
	 * dynamic component-based OS.
	 * <p/>
	 * <b>Rationale for changes:</b><br/>
	 * <b>1.0:</b> Initial appearance as runCommand()<br/>
	 * <b>2.0:</b> Operates asynchronously. One message can be sent to multiple nodes. Messages are no longer restricted
	 * to being text-based but may also carry binary data instead. The message data type comprises payload and metadata
	 * of a message (time, origin, etc.).<br/>
	 * <b>2.2:</b> {@link Message} type changed to contain <i>only</i> binary data instead of text or binary data as the
	 * backend should not interpret (i.e. assume framing or encoding) of sensor node data.
	 *
	 * @param message
	 * 		the message to send
	 * @param nodeIds
	 * 		the set of node URNs to which the message shall be sent
	 *
	 * @return a request ID for asynchronous response(s) to the {@link Controller}
	 */
	@WebMethod
	@WebResult(
			targetNamespace = ""
	)
	@RequestWrapper(
			localName = "send",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.Send"
	)
	@ResponseWrapper(
			localName = "sendResponse",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.SendResponse"
	)
	public String send(
			@WebParam(name = "nodeIds", targetNamespace = "") List<String> nodeIds,
			@WebParam(name = "message", targetNamespace = "") Message message);

	/**
	 * Sets handlers for sensor node output.
	 * <p/>
	 * Necessary to put the user in control of how sensor node output is handled (particularly useful when bespoke
	 * framing formats such as virtual link packets are transmitted).
	 * <p/>
	 * A RequestStatus message delivers the result of the operation; if successful the "value" field is set to
	 * "1", if the operation failed is set to "0", and if any node ID was unknown is set to "-1".
	 *
	 * @param nodes
	 * 		a list of node URNs for which the channel pipeline shall be configured
	 * @param channelHandlerConfigurations
	 * 		a list of {@link ChannelHandlerConfiguration} instances describing the
	 * 		individual channel pipelines of each node
	 *
	 * @return a request ID for asynchronous response(s) to the {@link Controller}
	 *
	 * @since 2.3
	 */
	@WebMethod
	@WebResult(
			targetNamespace = ""
	)
	@RequestWrapper(
			localName = "setChannelPipeline",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.SetChannelPipeline"
	)
	@ResponseWrapper(
			localName = "setChannelPipelineResponse",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.SetChannelPipelineResponse"
	)
	public String setChannelPipeline(
			@WebParam(name = "nodes", targetNamespace = "") List<String> nodes,
			@WebParam(name = "channelHandlerConfigurations", targetNamespace = "")
			List<ChannelHandlerConfiguration> channelHandlerConfigurations);

	/**
	 * Creates a unidirectional virtual link from {@code sourceNode} to {@code targetNode}.
	 * <p/>
	 * Consider the example {@code wsn1.setVirtualLink(s, t, wsn2, parameters, filters);}:
	 * <ul>
	 * <li>
	 * If {@code wsn1} is controlling the testbed containing node {@code s}, it will inform {@code s} that there is
	 * a new virtual link with node {@code t}. It passes the parameters to {@code s}.
	 * </li>
	 * <li>
	 * If {@code wsn1} is <i>not</i> controlling the testbed containing {@code s}, it will create a local forwarding
	 * rule. Another call of {@link WSN#setVirtualLink(String, String, String, java.util.List, java.util.List)} with
	 * another WSN API ensures that packets travelling from {@code s} to {@code t} will show up at {@code wsn1}.
	 * </li>
	 * </ul>
	 * When there is a packet from {@code s} to process (because it originated in the local testbed, or because it
	 * arrived using {@link WSN#send(java.util.List, eu.wisebed.api.common.Message)}, {@code wsn1} will:
	 * <ol>
	 * <li>
	 * Apply the filters described in {@code filters} in order.
	 * </li>
	 * <li>
	 * Call {@link WSN#send(java.util.List, eu.wisebed.api.common.Message)} on the Web service endpoint at
	 * {@code remoteServiceInstance} to forward the packet.
	 * </li>
	 * </ol>
	 * Calling {@link WSN#setVirtualLink(String, String, String, java.util.List, java.util.List)} again for same source
	 * and destination will overwrite a previously stored route (perhaps with different filters).
	 * <p/>
	 * <b>Examples:</b><br/>
	 * Assume we have two testbeds, ULANC and TUBS. Node {@code n_ulanc}  is in ULANC, node {@code n_tubs} is in TUBS.
	 * The corresponding testbed service APIs are running on {@code tb_ulanc} and {@code tb_tubs}. The user is operating
	 * a packet filtering software somewhere at a third location, {@code filter_host}.
	 * <ul>
	 * <li>
	 * To create a simple bi-directional virtual link between {@code n_ulanc} and {@code n_tubs}, call:
	 * <pre>
	 *     tb_ulanc.setVirtualLink(n_ulanc, n_tubs, tb_tubs, (), ());
	 *     tb_tubs.setVirtualLink(n_tubs, n_ulanc, tb_ulanc, (), ());
	 * </pre>
	 * </li>
	 * <li>
	 * To create a virtual link with message loss, using a default filter installed in the testbed services of ULANC and
	 * TUBS, call:
	 * <pre>
	 *     tb_ulanc.setVirtualLink(n_ulanc, n_tubs, tb_tubs, (), ("random_drop 5%"));
	 *     tb_tubs.setVirtualLink(n_tubs, n_ulanc, tb_ulanc, (), ("random_drop 5%"));
	 * </pre>
	 * </li>
	 * <li>
	 * To create a bi-directional virtual link that routes all packets through filter host we have 4 virtual link
	 * segments configured as follows:
	 * <pre>
	 *     tb_ulanc.setVirtualLink(n_ulanc, n_tubs, filter_host, (), ());
	 *     filter_host.setVirtualLink(n_ulanc, n_tubs, tb_tubs, (), ("my_esoteric_filter weirdness=very"));
	 *
	 *     tb_tubs.setVirtualLink(n_tubs, n_ulanc, filter_host, (), ());
	 *     filter_host.setVirtualLink(n_tubs, n_ulanc, tb_ulanc, (), ("my_esoteric_filter weirdness=very"));
	 * </pre>
	 * </li>
	 * </ul>
	 * <b>Rationale for changes:</b><br/>
	 * <b>2.1:</b> Added method
	 *
	 * @param sourceNode
	 * 		URN of the source node (within the scope of this WSN API instance)
	 * @param targetNode
	 * 		URN of the target node (not necessarily in the testbed represented by {@code remoteServiceInstance})
	 * @param remoteServiceInstance
	 * 		Web service endpoint URL of a {@link WSN} instance that should receive the virtual link packets
	 * @param parameters
	 * 		a set of parameter strings that is passed to {@code sourceNode}. Allows specialised configuration of
	 * 		individual virtual radio implementations
	 * @param filters
	 * 		description of a filter chain
	 *
	 * @return a request ID for asynchronous response(s) to the {@link Controller}
	 */
	@WebMethod
	@WebResult(
			targetNamespace = ""
	)
	@RequestWrapper(
			localName = "setVirtualLink",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.SetVirtualLink"
	)
	@ResponseWrapper(
			localName = "setVirtualLinkResponse",
			targetNamespace = "urn:WSNService",
			className = "eu.wisebed.api.wsn.SetVirtualLinkResponse"
	)
	public String setVirtualLink(
			@WebParam(name = "sourceNode", targetNamespace = "") String sourceNode,
			@WebParam(name = "targetNode", targetNamespace = "") String targetNode,
			@WebParam(name = "remoteServiceInstance", targetNamespace = "") String remoteServiceInstance,
			@WebParam(name = "parameters", targetNamespace = "") List<String> parameters,
			@WebParam(name = "filters", targetNamespace = "") List<String> filters);

}
