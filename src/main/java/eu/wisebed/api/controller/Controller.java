package eu.wisebed.api.controller;

import eu.wisebed.api.common.Message;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import java.util.List;


/**
 * Version 1.0 of the {@link eu.wisebed.api.wsn.WSN} API was based on synchronous Web Service calls which had several
 * scalability problems. For instance, programming a number of sensor nodes can take a long time. Depending on the
 * implementation of this API, the delays could easily surpass the timeout allowed on HTTP connections. A similar
 * argument applies for the resetting of sensor nodes: some WSN testbeds require the gateway node to be rebooted in
 * order to reboot the attached node. Since one of the design goals of WISEBED is scalability, these operations must
 * use asynchronous replies so that the calling instance does not have to be blocked until the operation is done. In
 * addition, some version 1.0 methods duplicated functionality (resetNode / rebootNode), were lacking certain
 * functionality (e.g., exchange of binary messages), or were not designed with exchangeability in mind. Hence, these
 * methods were changed to be asynchronous.
 * <p/>
 * Asynchronous operations (such as resetNodes, flashPrograms, etc., see later) return an identifier (of type
 * String) so that the experiment controller can match the asynchronous replies with the initial request.
 * <p/>
 * The status of an operation is reported to the controller via so-called status updates (of type {@link
 * RequestStatus}). A status update comprises the original request identifier and a number of values for different
 * nodes. For instance, when flashing a number of nodes at once, a status update contains full or partial information
 * about the progress of the overall operation, i.e. information about a subset of the currently reprogrammed nodes.
 * The controller must therefore track the progress based on potentially a number of these status updates until all
 * nodes report completion or failure. The status update contains the node identifier, a numerical value, and a human
 * readable message. The interpretation of the value depends on the operation and is described later for each of the
 * operations.
 */
@WebService(name = "Controller", targetNamespace = "urn:ControllerService")
@XmlSeeAlso({
		eu.wisebed.api.controller.ObjectFactory.class,
		eu.wisebed.api.common.ObjectFactory.class
})
public interface Controller {


	/**
	 * Notifies that {@link Controller} that it can shut down (if it wishes to) as the experiment ended. The end of the
	 * experiment may either be due to the end of the reservation time slot or as a consequence of a call to {@link
	 * eu.wisebed.api.sm.SessionManagement#free(java.util.List)}.
	 *
	 * @since 2.3
	 */
	@WebMethod
	@Oneway
	@RequestWrapper(localName = "experimentEnded", targetNamespace = "urn:ControllerService",
			className = "eu.wisebed.api.controller.ExperimentEnded")
	public void experimentEnded();

	/**
	 * This method is invoked by the testbed service to convey messages from sensor nodes to the {@link Controller}.
	 * <p/>
	 * <b>Rationale for changes:</b><br/>
	 * <b>1.0:</b> Initial appearance as deliverOutputFromSensorNode()<br/>
	 * <b>2.0:</b> Re-uses the message data type to convey messages from the testbed service to the Controller Service
	 * <b>2.2:</b> {@link Message} type changed to contain <i>only</i> binary data instead of text or binary data as the
	 * backend should not interpret (i.e. assume framing or encoding) of sensor node data
	 *
	 * @param msg
	 * 		a list of {@link Message} instances
	 */
	@WebMethod
	@Oneway
	@RequestWrapper(localName = "receive", targetNamespace = "urn:ControllerService",
			className = "eu.wisebed.api.controller.Receive")
	public void receive(
			@WebParam(name = "msg", targetNamespace = "")
			List<Message> msg);

	/**
	 * Provides a notification from the testbed backend (usually a fault or warning intended for the user).
	 *
	 * @param msg
	 * 		a list of notifications
	 *
	 * @since 2.3
	 */
	@WebMethod
	@Oneway
	@RequestWrapper(localName = "receiveNotification", targetNamespace = "urn:ControllerService",
			className = "eu.wisebed.api.controller.ReceiveNotification")
	public void receiveNotification(
			@WebParam(name = "msg", targetNamespace = "")
			List<String> msg);

	/**
	 * Reports the status of an operation to the controller.
	 * <p/>
	 * As described in the class documentation, asynchronously triggered operations send status messages about the
	 * progress or result of operations. These status messages are delivered from the Testbed Service to the controller
	 * using this operation. The operation does not return any value. The format and semantics of the values contained
	 * in the parameter ”status” depend on the operation, this status messages belongs to. See the individual sections
	 * for a definition.
	 * <p/>
	 * <b>Rationale for changes:</b><br/>
	 * <b>2.0:</b> Performance, avoiding Web service call timeouts (see class documentation).
	 *
	 * @param status
	 * 		the status of an operation. The content is defined by the operation that reports its status. May contain a
	 * 		list of status indicators for a collection of previously-issued operations.
	 *
	 * @since 2.0
	 */
	@WebMethod
	@Oneway
	@RequestWrapper(localName = "receiveStatus", targetNamespace = "urn:ControllerService",
			className = "eu.wisebed.api.controller.ReceiveStatus")
	public void receiveStatus(
			@WebParam(name = "status", targetNamespace = "")
			List<RequestStatus> status);

}
