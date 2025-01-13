package net.sourceforge.jpotpourri.xmlparse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author christoph.pickl@bmi.gv.at
 */
public final class PtXmlClosure {

	private static final Log LOG = LogFactory.getLog(PtXmlClosure.class);
	
	private PtXmlClosure() {
		// no instantiation
	}
	
	public static void withNodeList(final NodeList list, final IPtNodeListFunction function) {
		for(int i = 0; i < list.getLength(); i++) {
			final Node node = list.item(i);
			LOG.trace("Parsing child node [" + node.getNodeName() + "]");
			function.invoke(node);
		}
	}
	
	public static void withNodeList2(final NodeList list, final IPtNodeListFunction2 function) {
		for(int i = 0; i < list.getLength(); i++) {
			final Node node = list.item(i);
			final String nodeName = node.getNodeName();
			final String nodeText = node.getTextContent();
			LOG.trace("Parsing child node [" + nodeName + "]");
			function.invoke(node, nodeName, nodeText);
		}
	}
	
}
