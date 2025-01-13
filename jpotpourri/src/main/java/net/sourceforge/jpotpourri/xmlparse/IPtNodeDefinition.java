package net.sourceforge.jpotpourri.xmlparse;

import org.w3c.dom.Node;

/**
 * @param <T> object type providing setter method
 * @author christoph_pickl@users.sourceforge.net
 */
public interface IPtNodeDefinition<T> {
	
	IPtNodeName getXmlNodeName();
	
	void invoke(final T alert, final Node node);
	
}
