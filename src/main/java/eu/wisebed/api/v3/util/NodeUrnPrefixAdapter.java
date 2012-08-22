package eu.wisebed.api.v3.util;

import eu.wisebed.api.v3.common.NodeUrnPrefix;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class NodeUrnPrefixAdapter extends XmlAdapter<String, NodeUrnPrefix> {

	public NodeUrnPrefix unmarshal(String v) throws Exception {
		return new NodeUrnPrefix(v);
	}

	public String marshal(NodeUrnPrefix v) throws Exception {
		return v.toString();
	}
}