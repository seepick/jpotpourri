package net.sourceforge.jpotpourri.xmlparse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Node;

/**
 * @param <T> object type providing setter method
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtNodeListDefinition<T> {
	
	private final List<IPtNodeDefinition<T>> definitions;

	@SuppressWarnings("unchecked")
	public PtNodeListDefinition(final IPtNodeDefinition<?>... definitions) {
		if(definitions == null || definitions.length == 0) {
			throw new IllegalArgumentException("definitions");
		}
		this.definitions = new ArrayList<IPtNodeDefinition<T>>(Arrays.asList(
				// i really hate java and the way generics+varargs are implemented
				(IPtNodeDefinition<T>[]) definitions));
	}
	
	public void processNode(final T alert, final Node node) {
		final String nodeName = node.getNodeName();
		
		boolean yetProcessed = false;
		for (IPtNodeDefinition<T> definition : this.definitions) {
			// System.out.println(nodeName + " -> " + definition.getXmlNodeName().getNodeName());
			if(definition.getXmlNodeName().getNodeName().equals(nodeName)) {
				definition.invoke(alert, node);
				yetProcessed = true; // could be processed twice
			}
		}
		
		if(yetProcessed == false) {
			throw new PtUnhandledNodeException(node);
		}
	}
}
