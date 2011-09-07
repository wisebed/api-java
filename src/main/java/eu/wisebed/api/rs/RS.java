package eu.wisebed.api.rs;

import eu.wisebed.api.snaa.Action;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import java.util.List;


/**
 * This interface defines the reservation system (RS) for the WISEBED experimental facilities.
 */
@WebService(name = "RS", targetNamespace = "urn:RSService")
@XmlSeeAlso({
		ObjectFactory.class
})
public interface RS {


	/**
	 * Returns reservations for a time interval (excluding confidential information).
	 * <p/>
	 * This is useful for users to ﬁnd out which nodes are available at which time before calling
	 * {@link RS#makeReservation(java.util.List, ConfidentialReservationData)}. It could also be used to display a
	 * calendar.
	 *
	 * @param from
	 * 		start time to search for reservations (inclusive)
	 * @param to
	 * 		end time to search for reservations (inclusive)
	 *
	 * @return a list of reservations not including confidential data
	 *
	 * @throws RSExceptionException
	 * 		if an unknown error occurs
	 * @since 1.0
	 */
	@WebMethod
	@WebResult(
			name = "reservations",
			targetNamespace = ""
	)
	@RequestWrapper(
			localName = "getReservations",
			targetNamespace = "urn:RSService",
			className = "eu.wisebed.api.rs.GetReservations"
	)
	@ResponseWrapper(
			localName = "getReservationsResponse",
			targetNamespace = "urn:RSService",
			className = "eu.wisebed.api.rs.GetReservationsResponse"
	)
	public List<PublicReservationData> getReservations(
			@WebParam(name = "from", targetNamespace = "") XMLGregorianCalendar from,
			@WebParam(name = "to", targetNamespace = "") XMLGregorianCalendar to)
			throws RSExceptionException
	;

	/**
	 * Returns reservations for a time interval (including confidential information).
	 *
	 * @param secretAuthenticationKey
	 * 		a sequence of (urnPrefix, secretAuthenticationKey) tuples that were obtained from a call to
	 * 		{@link eu.wisebed.api.snaa.SNAA#authenticate(List)}
	 * @param period
	 * 		the period of time for which to return the data (from and to inclusive)
	 *
	 * @return reservations for a time interval (including confidential information)
	 *
	 * @throws RSExceptionException
	 * 		if an unknown error occurs
	 * @since 1.0
	 */
	@WebMethod
	@WebResult(
			name = "reservationData",
			targetNamespace = ""
	)
	@RequestWrapper(
			localName = "getConfidentialReservations",
			targetNamespace = "urn:RSService",
			className = "eu.wisebed.api.rs.GetConfidentialReservations"
	)
	@ResponseWrapper(
			localName = "getConfidentialReservationsResponse",
			targetNamespace = "urn:RSService",
			className = "eu.wisebed.api.rs.GetConfidentialReservationsResponse"
	)
	public List<ConfidentialReservationData> getConfidentialReservations(
			@WebParam(name = "secretAuthenticationKey", targetNamespace = "")
			List<SecretAuthenticationKey> secretAuthenticationKey,
			@WebParam(name = "period", targetNamespace = "") GetReservations period)
			throws RSExceptionException
	;

	/**
	 * Returns data about a single reservation (including confidential information).
	 * <p/>
	 * Please note that this function returns confidential data such as the username. This is fine since the
	 * secret reservation key is only known to the {@link RS}, the user and later the
	 * {@link eu.wisebed.api.sm.SessionManagement} API.
	 *
	 * @param secretReservationKey
	 * 		a sequence of (urnPrefix, secretReservationKey) tuples as returned by
	 * 		{@link RS#makeReservation(java.util.List, ConfidentialReservationData)} in the reservation step
	 *
	 * @return information about a certain reservation
	 *
	 * @throws ReservervationNotFoundExceptionException
	 * 		if the reservation was not found
	 * @throws RSExceptionException
	 * 		if an unknown error occurs
	 * @since 1.0
	 */
	@WebMethod
	@WebResult(
			name = "reservationData",
			targetNamespace = ""
	)
	@RequestWrapper(
			localName = "getReservation",
			targetNamespace = "urn:RSService",
			className = "eu.wisebed.api.rs.GetReservation"
	)
	@ResponseWrapper(
			localName = "getReservationResponse",
			targetNamespace = "urn:RSService",
			className = "eu.wisebed.api.rs.GetReservationResponse"
	)
	public List<ConfidentialReservationData> getReservation(
			@WebParam(name = "secretReservationKey", targetNamespace = "")
			List<SecretReservationKey> secretReservationKey)
			throws RSExceptionException, ReservervationNotFoundExceptionException
	;

	/**
	 * Deletes a single reservation.
	 *
	 * @param authenticationData
	 * 		a sequence of (urnPrefix, secretAuthenticationKey) tuples that were obtained from a call to
	 * 		{@link eu.wisebed.api.snaa.SNAA#authenticate(List)}
	 * @param secretReservationKey
	 * 		a sequence of (urnPrefix, secretReservationKey) tuples as returned by
	 * 		{@link RS#makeReservation(java.util.List, ConfidentialReservationData)} in the reservation step
	 *
	 * @throws ReservervationNotFoundExceptionException
	 * 		if {@code secretReservationKey} does not point to a known reservation
	 * @throws RSExceptionException
	 * 		if an unknown exception occurs
	 * @since 1.0
	 */
	@WebMethod
	@RequestWrapper(
			localName = "deleteReservation",
			targetNamespace = "urn:RSService",
			className = "eu.wisebed.api.rs.DeleteReservation"
	)
	@ResponseWrapper(
			localName = "deleteReservationResponse",
			targetNamespace = "urn:RSService",
			className = "eu.wisebed.api.rs.DeleteReservationResponse"
	)
	public void deleteReservation(
			@WebParam(name = "authenticationData", targetNamespace = "")
			List<SecretAuthenticationKey> authenticationData,
			@WebParam(name = "secretReservationKey", targetNamespace = "")
			List<SecretReservationKey> secretReservationKey)
			throws RSExceptionException, ReservervationNotFoundExceptionException
	;

	/**
	 * Reserves a set of nodes for an interval of time.
	 * <p/>
	 * The reservation system uses uses {@code authenticationData} as a parameter in calling {@link
	 * eu.wisebed.api.snaa.SNAA#isAuthorized(List, Action)} with these keys and the string "reserve" for the {@code
	 * action} parameter to determine whether a user is authorized to perform this reservation.
	 * <p/>
	 * The second parameter contains a list of node URNs that are to be reserved from {@link
	 * eu.wisebed.api.rs.ConfidentialReservationData#getFrom()} until
	 * {@link eu.wisebed.api.rs.ConfidentialReservationData#getTo()} (inclusive). The
	 * parameter {@link eu.wisebed.api.rs.ConfidentialReservationData#getUserData()} can include arbitrary (public)
	 * information about the reservation. This could be used to store additional information about this reservation used
	 * by the client to the RS.
	 * <p/>
	 * In addition, the RS must check whether the reservation can be made, i.e. if all the requested nodes are available
	 * for the requested time interval.
	 * <p/>
	 * If authorization and feasibility is given for ALL the nodes, it returns a set of
	 * (urnPrefix, secretReservationKey) tuples for each corresponding input tuple or a fault otherwise. If the
	 * reservation is not feasible due to conflicts with other reservations, a ReservationConflict fault is signaled.
	 * For other RS-internal faults, a RS fault is returned.
	 *
	 * @param authenticationData
	 * 		a sequence of (urnPrefix, secretAuthenticationKey) tuples that were obtained from a call to
	 * 		{@link eu.wisebed.api.snaa.SNAA#authenticate(List)}
	 * @param reservation
	 * 		the set of nodes and the time interval to reserve the nodes for
	 *
	 * @return a sequence of (urnPrefix, secretReservationKey) tuples for each corresponding input tuple
	 *
	 * @throws ReservervationConflictExceptionException
	 * 		if the requested reservation conflicts with other reservations
	 * @throws RSExceptionException
	 * 		if an unknown exception occurs
	 * @throws AuthorizationExceptionException
	 * 		if the authentication credentials given by {@code authenticationData} are
	 * 		invalid or timed out or the user has insufficient rights to perform this operation
	 * @since 1.0
	 */
	@WebMethod
	@WebResult(
			name = "secretReservationKey",
			targetNamespace = ""
	)
	@RequestWrapper(
			localName = "makeReservation",
			targetNamespace = "urn:RSService",
			className = "eu.wisebed.api.rs.MakeReservation"
	)
	@ResponseWrapper(
			localName = "makeReservationResponse",
			targetNamespace = "urn:RSService",
			className = "eu.wisebed.api.rs.MakeReservationResponse"
	)
	public List<SecretReservationKey> makeReservation(
			@WebParam(name = "authenticationData", targetNamespace = "")
			List<SecretAuthenticationKey> authenticationData,
			@WebParam(name = "reservation", targetNamespace = "") ConfidentialReservationData reservation)
			throws AuthorizationExceptionException, RSExceptionException, ReservervationConflictExceptionException
	;

}
