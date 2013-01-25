package eu.wisebed.api.v3.common;

import java.io.Serializable;
import java.util.regex.Pattern;

public class NodeUrnPrefix implements Serializable {

	public final static Pattern URN_PREFIX_PATTERN = Pattern.compile(
			"^urn:[a-zA-Z0-9][a-zA-Z0-9-]{0,31}((:([a-zA-Z0-9()+,.:=@;$_!*'-]|%[0-9A-Fa-f]{2})+):|:)$",
			Pattern.CASE_INSENSITIVE
	);

	private String nodeUrnPrefix;

	public NodeUrnPrefix(final String nodeUrnPrefix) {

		if (nodeUrnPrefix == null) {
			throw new NullPointerException("Parameter nodeUrnPrefix is null");
		}

		if (!URN_PREFIX_PATTERN.matcher(nodeUrnPrefix).matches()) {
			throw new IllegalArgumentException("Parameter nodeUrnPrefix is not a valid URN prefix");
		}

		this.nodeUrnPrefix = nodeUrnPrefix;
	}

	@Override
	public boolean equals(final Object o) {

		if (this == o) {
			return true;
		}

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final NodeUrnPrefix that = (NodeUrnPrefix) o;

		return nodeUrnPrefix.equals(that.nodeUrnPrefix);
	}

	@Override
	public int hashCode() {
		return nodeUrnPrefix.hashCode();
	}

	@Override
	public String toString() {
		return nodeUrnPrefix;
	}

	public boolean belongsTo(NodeUrn nodeUrn) {
		if (nodeUrn == null) {
			throw new NullPointerException("Parameter nodeUrn must not be null");
		}
		return nodeUrn.getPrefix().equals(this);
	}
}