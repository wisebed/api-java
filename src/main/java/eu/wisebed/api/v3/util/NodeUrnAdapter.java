package eu.wisebed.api.v3.util;

import eu.wisebed.api.v3.common.NodeUrn;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class NodeUrnAdapter extends XmlAdapter<String, NodeUrn> {

	public NodeUrn unmarshal(String v) throws Exception {
		return new NodeUrn(v);
	}

	public String marshal(NodeUrn v) throws Exception {
		return v.toString();
	}
}