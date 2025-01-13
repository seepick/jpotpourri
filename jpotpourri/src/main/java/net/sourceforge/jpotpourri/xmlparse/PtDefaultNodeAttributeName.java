package net.sourceforge.jpotpourri.xmlparse;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtDefaultNodeAttributeName implements IPtNodeAttributeName {

	private final String attributeName;
	
	
	public PtDefaultNodeAttributeName(final String attributeName) {
		this.attributeName = attributeName;
	}
	
	public String getAttributeName() {
		return this.attributeName;
	}
	
	public String getAttributeContent(final Node node) {
		final NamedNodeMap attribuets = node.getAttributes();
		if(attribuets == null) {
			throw new RuntimeException("Node [" + node + "] got not any attributes!");
		}
		
		final Node attribute = attribuets.getNamedItem(this.getAttributeName());
		if(attribute == null) {
			throw new RuntimeException("Node [" + node + "] got no attribute named [" + this.getAttributeName() + "]!");
		}
		
		return attribute.getTextContent().trim();
	}
	
}
