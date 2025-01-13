package net.sourceforge.jpotpourri.xmlparse;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Node;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtUnhandledNodeException extends RuntimeException {

	private static final long serialVersionUID = 4445063577255376024L;

	private final Node node;
	
	
	
	public PtUnhandledNodeException(final Node node) {
		super("Unhandled node [" + node.getNodeName() + "]! " + 
				"(node path: " + constructParentNodePath(node) + ")");
		this.node = node;
	}
	
	public Node getNode() {
		return this.node;
	}
	
	private static String constructParentNodePath(final Node node) {
		final List<Node> nodes = new LinkedList<Node>();
		Node parent = node.getParentNode();
		while(parent != null) {
			nodes.add(parent);
			parent = parent.getParentNode();
		}
		
		Collections.reverse(nodes);
		
		final StringBuilder sb = new StringBuilder();
		for (final Node n : nodes) {
			sb.append(n.getNodeName()).append(" -> ");
		}
		sb.append(node.getNodeName());
		
		return sb.toString();
	}
}
