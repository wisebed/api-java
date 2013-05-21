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

package eu.wisebed.api.v3.util;

import eu.wisebed.api.v3.common.*;

import javax.naming.directory.InvalidAttributesException;
import java.util.*;

/**
 * Small helper class to convert between two or more API types
 */
public class WisebedConversionHelper {

	/**
	 * Matches secret authentication keys to related node urns based or the node urns' prefixes and
	 * returns the result.
	 *
	 * @param secretAuthenticationKeys
	 * 		A list of secret authentication keys
	 * @param nodeUrns
	 * 		A collection of node urns
	 *
	 * @return A list of associations between tuples of user names and urn prefixes and node urns.
	 *
	 * @throws InvalidAttributesException
	 * 		Thrown if the matching cannot be performed due to ambiguous user data
	 */
	public static List<UsernameNodeUrnsMap> convertToUsernameNodeUrnsMap(
			final List<SecretAuthenticationKey> secretAuthenticationKeys,
			final List<NodeUrn> nodeUrns) throws InvalidAttributesException {

		/*
		 * Check whether two tuples of user names and urn prefixes share an urn prefix
		 */
		final Set<NodeUrnPrefix> prefixes = new HashSet<NodeUrnPrefix>();

		for (SecretAuthenticationKey secretAuthenticationKey : secretAuthenticationKeys) {
			if (!prefixes.add(secretAuthenticationKey.getUrnPrefix())) {
				throw new InvalidAttributesException("The node urn prefix '"
						+ secretAuthenticationKey.getUrnPrefix()
						+ "' is associated to multiple user names."
				);
			}
		}

		final List<UsernameNodeUrnsMap> mappings = new LinkedList<UsernameNodeUrnsMap>();

		for (SecretAuthenticationKey secretAuthenticationKey : secretAuthenticationKeys) {

			final NodeUrnPrefix urnPrefix = secretAuthenticationKey.getUrnPrefix();
			final String username = secretAuthenticationKey.getUsername();

			final UsernameNodeUrnsMap mapping = new UsernameNodeUrnsMap();
			mapping.setUrnPrefix(urnPrefix);
			mapping.setUsername(username);
			mappings.add(mapping);

			for (NodeUrn nodeUrn : nodeUrns) {
				if (nodeUrn.belongsTo(urnPrefix)) {
					mapping.getNodeUrns().add(nodeUrn);
				}
			}
		}

		return mappings;
	}
}
