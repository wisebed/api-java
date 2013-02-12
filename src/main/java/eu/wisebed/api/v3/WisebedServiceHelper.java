/**********************************************************************************************************************
 * Copyright (c) 2010, Institute of Telematics, University of Luebeck                                                  *
 * All rights reserved.                                                                                               *
 *                                                                                                                    *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the   *
 * following conditions are met:                                                                                      *
 *                                                                                                                    *
 * - Redistributions of source code must retain the above copyright notice, this list of conditions and the following *
 *   disclaimer.                                                                                                      *
 * - Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the        *
 *   following disclaimer in the documentation and/or other materials provided with the distribution.                 *
 * - Neither the name of the University of Luebeck nor the names of its contributors may be used to endorse or promote *
 *   products derived from this software without specific prior written permission.                                   *
 *                                                                                                                    *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, *
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE      *
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,         *
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE *
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF    *
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY   *
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.                                *
 **********************************************************************************************************************/

package eu.wisebed.api.v3;

import eu.wisebed.api.v3.common.SecretReservationKey;
import eu.wisebed.api.v3.controller.Controller;
import eu.wisebed.api.v3.controller.ControllerService;
import eu.wisebed.api.v3.rs.RS;
import eu.wisebed.api.v3.rs.RSService;
import eu.wisebed.api.v3.sm.SessionManagement;
import eu.wisebed.api.v3.sm.SessionManagementService;
import eu.wisebed.api.v3.sm.UnknownSecretReservationKeyFault;
import eu.wisebed.api.v3.snaa.SNAA;
import eu.wisebed.api.v3.snaa.SNAAService;
import eu.wisebed.api.v3.wsn.ReservationNotRunningFault;
import eu.wisebed.api.v3.wsn.ReservationNotRunningFault_Exception;
import eu.wisebed.api.v3.wsn.WSN;
import eu.wisebed.api.v3.wsn.WSNService;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Small helper class that allows to obtain instances of the several web services.
 */
public class WisebedServiceHelper {

	private static File tmpFileSNAA = null;

	private static File tmpFileRS = null;

	private static File tmpFileSessionManagement = null;

	private static File tmpFileWSN = null;

	private static File tmpFileController = null;

	private static final Lock tmpFileSNAALock = new ReentrantLock();

	private static final Lock tmpFileRSLock = new ReentrantLock();

	private static final Lock tmpFileSessionManagementLock = new ReentrantLock();

	private static final Lock tmpFileWSNLock = new ReentrantLock();

	private static final Lock tmpFileControllerLock = new ReentrantLock();

	/**
	 * Returns the port to the RS API.
	 *
	 * @param endpointUrl
	 * 		the endpoint URL to connect to
	 *
	 * @return a {@link eu.wisebed.api.v3.rs.RS} instance that is
	 *         connected to the Web Service endpoint
	 */
	public static RS getRSService(String endpointUrl) {

		InputStream resourceStream = WisebedServiceHelper.class.getClassLoader().getResourceAsStream("RS.wsdl");

		tmpFileRSLock.lock();
		try {
			if (tmpFileRS == null) {
				try {
					tmpFileRS = copyToTmpFile(resourceStream, "tr.rs", "wsdl");
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		} finally {
			tmpFileRSLock.unlock();
		}

		RSService service;
		try {
			service = new RSService(
					tmpFileRS.toURI().toURL(),
					new QName("http://wisebed.eu/api/v3/rs", "RSService")
			);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}

		RS rsPort = service.getRSPort();

		Map<String, Object> context = ((BindingProvider) rsPort).getRequestContext();
		context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl);

		return rsPort;

	}

	/**
	 * Returns the port to the SNAA API.
	 *
	 * @param endpointUrl
	 * 		the endpoint URL to connect to
	 *
	 * @return a {@link eu.wisebed.api.v3.snaa.SNAA} instance that is
	 *         connected to the Web Service endpoint
	 */
	public static SNAA getSNAAService(String endpointUrl) {

		InputStream resourceStream =
				WisebedServiceHelper.class.getClassLoader().getResourceAsStream("SNAA.wsdl");

		tmpFileSNAALock.lock();
		try {
			if (tmpFileSNAA == null) {
				try {
					tmpFileSNAA = copyToTmpFile(resourceStream, "tr.snaa", "wsdl");
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		} finally {
			tmpFileSNAALock.unlock();
		}

		SNAAService service;
		try {
			service = new SNAAService(tmpFileSNAA.toURI().toURL(),
					new QName("http://wisebed.eu/api/v3/snaa", "SNAAService")
			);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		SNAA snaaPort = service.getSNAAPort();

		Map<String, Object> context = ((BindingProvider) snaaPort).getRequestContext();
		context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl);

		return snaaPort;
	}

	/**
	 * Returns the port to the Session Management API.
	 *
	 * @param endpointUrl
	 * 		the endpoint URL to connect to
	 *
	 * @return a {@link eu.wisebed.api.v3.sm.SessionManagement} instance that is connected to the Web Service endpoint
	 */
	public static SessionManagement getSessionManagementService(String endpointUrl) {

		InputStream resourceStream =
				WisebedServiceHelper.class.getClassLoader().getResourceAsStream("SM.wsdl");

		tmpFileSessionManagementLock.lock();
		try {
			if (tmpFileSessionManagement == null) {
				try {
					tmpFileSessionManagement = copyToTmpFile(resourceStream, "tr.controller.sessionmanagement", "wsdl");
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		} finally {
			tmpFileSessionManagementLock.unlock();
		}

		SessionManagementService service;
		try {
			service = new SessionManagementService(
					tmpFileSessionManagement.toURI().toURL(),
					new QName("http://wisebed.eu/api/v3/sm", "SessionManagementService")
			);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}

		SessionManagement sessionManagementPort = service.getSessionManagementPort();

		Map<String, Object> context = ((BindingProvider) sessionManagementPort).getRequestContext();
		context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl);

		return sessionManagementPort;

	}

	public static Controller getControllerService(String endpointUrl, ExecutorService executorService) {

		InputStream resourceStream =
				WisebedServiceHelper.class.getClassLoader().getResourceAsStream("Controller.wsdl");

		tmpFileControllerLock.lock();
		try {
			if (tmpFileController == null) {
				try {
					tmpFileController = copyToTmpFile(resourceStream, "tr.controller.controller", "wsdl");
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		} finally {
			tmpFileControllerLock.unlock();
		}

		ControllerService service;
		try {
			service = new ControllerService(
					tmpFileController.toURI().toURL(),
					new QName("http://wisebed.eu/api/v3/controller", "ControllerService")
			);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}

		if (executorService != null) {
			service.setExecutor(executorService);
		}

		Controller controllerPort = service.getControllerPort();

		Map<String, Object> context = ((BindingProvider) controllerPort).getRequestContext();
		context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl);

		return controllerPort;

	}

	/**
	 * Returns the port to the WSN API instance.
	 *
	 * @param endpointUrl
	 * 		the endpoint URL to connect to
	 *
	 * @return a {@link WSN} instance that is connected to the Web Service endpoint
	 */
	public static WSN getWSNService(String endpointUrl) {

		InputStream resourceStream = WisebedServiceHelper.class.getClassLoader().getResourceAsStream("WSN.wsdl");

		tmpFileWSNLock.lock();
		try {
			if (tmpFileWSN == null) {
				try {
					tmpFileWSN = copyToTmpFile(resourceStream, "tr.controller.wsn", "wsdl");
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		} finally {
			tmpFileWSNLock.unlock();
		}

		WSNService service;
		try {
			service = new WSNService(
					tmpFileWSN.toURI().toURL(),
					new QName("http://wisebed.eu/api/v3/wsn", "WSNService")
			);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}

		WSN wsnPort = service.getWSNPort();

		Map<String, Object> context = ((BindingProvider) wsnPort).getRequestContext();
		context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl);

		return wsnPort;
	}

	public static ReservationNotRunningFault_Exception createReservationNotRunningException(String msg,
																							Exception e) {
		ReservationNotRunningFault exception = new ReservationNotRunningFault();
		exception.setMessage(msg);
		return new ReservationNotRunningFault_Exception(msg, exception, e);
	}

	public static eu.wisebed.api.v3.rs.UnknownSecretReservationKeyFault createRSUnknownSecretReservationKeyFault(
			String msg,
			SecretReservationKey srk) {
		return new eu.wisebed.api.v3.rs.UnknownSecretReservationKeyFault(msg,
				createUnknownSecretReservationKeyFaultInfo(msg, srk)
		);
	}

	public static UnknownSecretReservationKeyFault createSMUnknownSecretReservationKeyFault(String msg,
																							SecretReservationKey srk) {
		return new UnknownSecretReservationKeyFault(msg, createUnknownSecretReservationKeyFaultInfo(msg, srk));
	}

	public static UnknownSecretReservationKeyFault createSMUnknownSecretReservationKeyFault(String msg,
																							SecretReservationKey srk,
																							Exception e) {
		return new UnknownSecretReservationKeyFault(msg, createUnknownSecretReservationKeyFaultInfo(msg, srk), e);
	}

	public static eu.wisebed.api.v3.common.UnknownSecretReservationKeyFault createUnknownSecretReservationKeyFaultInfo(
			final String msg,
			final SecretReservationKey srk) {
		final eu.wisebed.api.v3.common.UnknownSecretReservationKeyFault faultInfo =
				new eu.wisebed.api.v3.common.UnknownSecretReservationKeyFault();
		faultInfo.setMessage(msg);
		faultInfo.setSecretReservationKey(srk);
		return faultInfo;
	}

	private static File copyToTmpFile(InputStream in, String prefix, String suffix) throws IOException {
		File tempFile = File.createTempFile(prefix, suffix);
		tempFile.deleteOnExit();
		copy(in, new FileOutputStream(tempFile));
		return tempFile;
	}

	private static void copy(InputStream in, OutputStream out) throws IOException {
		final ReadableByteChannel inputChannel = Channels.newChannel(in);
		final WritableByteChannel outputChannel = Channels.newChannel(out);
		// copy the channels
		copy(inputChannel, outputChannel);
		// closing the channels
		inputChannel.close();
		outputChannel.close();
	}

	private static void copy(ReadableByteChannel src, WritableByteChannel dest) throws IOException {
		final ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
		while (src.read(buffer) != -1) {
			// prepare the buffer to be drained
			buffer.flip();
			// write to the channel, may block
			dest.write(buffer);
			// If partial transfer, shift remainder down
			// If buffer is empty, same as doing clear()
			buffer.compact();
		}
		// EOF will leave buffer in fill state
		buffer.flip();
		// make sure the buffer is fully drained.
		while (buffer.hasRemaining()) {
			dest.write(buffer);
		}
	}

}