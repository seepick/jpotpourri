package net.sourceforge.jpotpourri.xmlparse;

import org.w3c.dom.Node;

/**
 * @author christoph.pickl@bmi.gv.at
 */
public interface IPtNodeListFunction2 {

	void invoke(final Node node, final String nodeName, final String nodeText);
	
}
