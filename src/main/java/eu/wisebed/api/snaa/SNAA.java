package eu.wisebed.api.snaa;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import java.util.List;


/**
 * This interface defines the Sensor Network Authentication and Authorization API of the WISEBED experimental facility.
 */
@WebService(name = "SNAA", targetNamespace = "http://testbed.wisebed.eu/api/snaa/v1/")
@XmlSeeAlso({
		ObjectFactory.class
})
public interface SNAA {


	/**
	 * Returns a list of authentication keys for a set of credentials or signals a fault otherwise.
	 * <p/>
	 * An implementation of this API function may choose to only accept a single-entry list (e.g., because it only
	 * serves a single URN prefix) while another implementation (e.g., a federator) may accept credentials for
	 * more than one URN prefix.
	 *
	 * @param authenticationData
	 * 		a sequence of (urnPrefix, username, password) tuples
	 *
	 * @return a list of {@link SecretAuthenticationKey} instances
	 *
	 * @throws SNAAExceptionException
	 * 		if an unknown exception occurred
	 * @throws AuthenticationExceptionException
	 * 		if the authentication failed
	 */
	@WebMethod
	@WebResult(
			name = "secretAuthenticationKey",
			targetNamespace = ""
	)
	@RequestWrapper(
			localName = "authenticate",
			targetNamespace = "http://testbed.wisebed.eu/api/snaa/v1/",
			className = "eu.wisebed.api.snaa.Authenticate"
	)
	@ResponseWrapper(
			localName = "authenticateResponse",
			targetNamespace = "http://testbed.wisebed.eu/api/snaa/v1/",
			className = "eu.wisebed.api.snaa.AuthenticateResponse"
	)
	public List<SecretAuthenticationKey> authenticate(
			@WebParam(name = "authenticationData", targetNamespace = "") List<AuthenticationTriple> authenticationData)
			throws AuthenticationExceptionException, SNAAExceptionException
	;

	/**
	 * Asks for authorization for a given action.
	 * <p/>
	 * This function is invoked by, e.g., the Reservation System, to ensure that an already authenticated used has the
	 * necessary rights to execute a certain action.
	 *
	 * @param authenticationData
	 * 		a sequence of (urnPrefix, secretAuthenticationKey) tuples as returned by
	 * 		{@link SNAA#authenticate(java.util.List)}
	 * @param action
	 * 		the action the user wants to be authorized for
	 *
	 * @return returns {@code true} if authorization is granted, {@code false} otherwise
	 *
	 * @throws SNAAExceptionException
	 * 		if an unknown exception occurs
	 */
	@WebMethod
	@WebResult(
			name = "authorization",
			targetNamespace = ""
	)
	@RequestWrapper(
			localName = "isAuthorized",
			targetNamespace = "http://testbed.wisebed.eu/api/snaa/v1/",
			className = "eu.wisebed.api.snaa.IsAuthorized"
	)
	@ResponseWrapper(
			localName = "isAuthorizedResponse",
			targetNamespace = "http://testbed.wisebed.eu/api/snaa/v1/",
			className = "eu.wisebed.api.snaa.IsAuthorizedResponse"
	)
	public boolean isAuthorized(
			@WebParam(name = "authenticationData", targetNamespace = "")
			List<SecretAuthenticationKey> authenticationData,
			@WebParam(name = "action", targetNamespace = "") Action action)
			throws SNAAExceptionException
	;

}
