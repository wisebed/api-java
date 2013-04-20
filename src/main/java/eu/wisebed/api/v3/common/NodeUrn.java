package eu.wisebed.api.v3.common;

import java.io.Serializable;
import java.util.regex.Pattern;

public class NodeUrn implements Serializable {

	public final static Pattern URN_PATTERN = Pattern.compile(
			"^urn:[a-zA-Z0-9][a-zA-Z0-9-]{0,31}:([a-zA-Z0-9()+,.:=@;$_!*'-]|%[0-9A-Fa-f]{2})+$",
			Pattern.CASE_INSENSITIVE
	);

	private String nodeUrn;

	public NodeUrn() {
	}

	public NodeUrn(final String nodeUrn) {
		setNodeUrn(nodeUrn);
	}

	@Override
	public boolean equals(final Object o) {

		if (this == o) {
			return true;
		}

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final NodeUrn other = (NodeUrn) o;

		return this.nodeUrn.equals(other.nodeUrn);
	}

	@Override
	public int hashCode() {
		return nodeUrn.hashCode();
	}

	public void setNodeUrn(final String nodeUrn) {

		if (nodeUrn == null) {
			throw new NullPointerException("Parameter nodeUrn is null");
		}

		if (!URN_PATTERN.matcher(nodeUrn).matches()) {
			throw new IllegalArgumentException("Parameter nodeUrn (\"" + nodeUrn + "\") is not a valid URN");
		}

		if (!hasHexOrDecLongUrnSuffix(nodeUrn)) {
			throw new IllegalArgumentException(
					"Parameter nodeUrn (\"" + nodeUrn + "\") must have a decimal or hexadecimal suffix"
			);
		}

		this.nodeUrn = nodeUrn.toLowerCase();
	}

	public String getNodeUrn() {
		return nodeUrn;
	}

	@Override
	public String toString() {
		return nodeUrn;
	}

	private static boolean hasHexOrDecLongUrnSuffix(final String nodeUrn) {
		String[] arr = nodeUrn.split(":");
		String suffix = arr[arr.length - 1];
		try {
			if (suffix.startsWith("0x")) {
				Long.parseLong(suffix.substring(2), 16);
			} else {
				Long.parseLong(suffix, 10);
			}
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public NodeUrnPrefix getPrefix() {
		return new NodeUrnPrefix(nodeUrn.substring(0, nodeUrn.lastIndexOf(':') + 1));
	}

	public String getSuffix() {
		return nodeUrn.substring(nodeUrn.lastIndexOf(':') + 1);
	}

	public boolean belongsTo(final NodeUrnPrefix nodeUrnPrefix) {
		if (nodeUrnPrefix == null) {
			throw new NullPointerException("Parameter nodeUrnPrefix must not be null");
		}
		return getPrefix().equals(nodeUrnPrefix);
	}
}