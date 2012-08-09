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

package eu.wisebed.api.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.naming.directory.InvalidAttributesException;

import eu.wisebed.api.common.SecretAuthenticationKey;
import eu.wisebed.api.common.UsernameNodeUrnsMap;
import eu.wisebed.api.common.UsernameUrnPrefixPair;

/**
 * Small helper class to convert between two or more API types
 */
public class WisebedConversionHelper {

	// ------------------------------------------------------------------------
	/**
	 * Converts a list of secret authentication keys to a list of tuples comprising user names and
	 * urn prefixes and returns the result.
	 * 
	 * @param secretAuthenticationKeys
	 *            A list of secret authentication keys
	 * @return A list of tuples comprising user names and urn prefixes
	 */
	public static List<UsernameUrnPrefixPair> convert(final List<SecretAuthenticationKey> secretAuthenticationKeys) {
		List<UsernameUrnPrefixPair> usernamePrefixPairs = new LinkedList<UsernameUrnPrefixPair>();
		for (SecretAuthenticationKey secretAuthenticationKey : secretAuthenticationKeys) {
			UsernameUrnPrefixPair upp = new UsernameUrnPrefixPair();
			usernamePrefixPairs.add(upp);
			upp.setUsername(secretAuthenticationKey.getUsername());
			upp.setUrnPrefix(secretAuthenticationKey.getUrnPrefix());
		}
		return usernamePrefixPairs;
	}

	// ------------------------------------------------------------------------
	/**
	 * Relates user name prefix pairs to node urns based or the node urns' prefixes and returns the
	 * result.
	 * 
	 * @param usernameUrnPrefixPairs
	 *            A collection of tuples of user name and urn prefix. The ladder one indicates the
	 *            user's associated testbed organization.
	 * @param nodeURNs
	 *            A collection of node urns
	 * @return A list of associations between tuples of user names and urn prefixes and node urns.
	 * @throws InvalidAttributesException
	 *             Thrown if two tuples of user names and urn prefixes share an urn prefix.
	 */
	public static List<UsernameNodeUrnsMap> convertToUsernameNodeUrnsMap(final Collection<UsernameUrnPrefixPair> usernameUrnPrefixPairs,
			final Collection<String> nodeURNs) throws InvalidAttributesException {

		/*
		 * Check whether two tuples of user names and urn prefixes share an urn prefix
		 */
		Set<String> prefixes = new HashSet<String>();
		for (UsernameUrnPrefixPair unpp : usernameUrnPrefixPairs) {
			if (!prefixes.add(unpp.getUrnPrefix())) {
				throw new InvalidAttributesException("The node urn prefix '" + unpp.getUrnPrefix() + "' is associated to multiple user names.");
			}
		}

		List<UsernameNodeUrnsMap> mappings = new LinkedList<UsernameNodeUrnsMap>();
		for (UsernameUrnPrefixPair unpp : usernameUrnPrefixPairs) {

			UsernameNodeUrnsMap map = new UsernameNodeUrnsMap();
			mappings.add(map);

			map.setUsername(unpp);
			for (String nodeUrn : nodeURNs) {
				if (nodeUrn.startsWith(unpp.getUrnPrefix())) {
					map.getNodeUrns().add(nodeUrn);
				}
			}
		}
		return mappings;
	}

	// ------------------------------------------------------------------------
	/**
	 * Matches secret authentication keys to related node urns based or the node urns' prefixes and
	 * returns the result.
	 * 
	 * @param secretAuthenticationKeys
	 *            A list of secret authentication keys
	 * @param nodeURNs
	 *            A collection of node urns
	 * @return A list of associations between tuples of user names and urn prefixes and node urns.
	 * @throws InvalidAttributesException
	 *             Thrown if the matching cannot be performed due to ambigious user data
	 * @see WisebedConversionHelper#convert(List)
	 * @see WisebedConversionHelper#convertToUsernameNodeUrnsMap(Collection, Collection)
	 */
	public static List<UsernameNodeUrnsMap> convertToUsernameNodeUrnsMap(final List<SecretAuthenticationKey> secretAuthenticationKeys,
			final Collection<String> nodeURNs) throws InvalidAttributesException {
		return WisebedConversionHelper.convertToUsernameNodeUrnsMap(convert(secretAuthenticationKeys), nodeURNs);
	}

}
