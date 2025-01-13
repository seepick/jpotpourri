package net.sourceforge.jpotpourri.xmlparse;

import org.w3c.dom.Node;

/**
 * @author christoph.pickl@bmi.gv.at
 */
public abstract class PtAbstractNodeName implements IPtNodeName {

	public final boolean matchesName(final Node node) {
		return node.getNodeName().equals(this.getNodeName());
	}
	
	public final boolean matchesName(final String nodeName) {
		return nodeName.equals(this.getNodeName());
	}
	
}
