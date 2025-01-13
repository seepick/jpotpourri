package net.sourceforge.jpotpourri.xmlparse;

import org.w3c.dom.Node;

/**
 * @param <T> object type providing setter method
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtAttributeStringDefinition<T> implements IPtNodeDefinition<T> {
	
	
	private final IPtNodeName xmlNodeName;
	
	private final IPtNodeAttributeName xmlNodeAttribute;
	
	private final MethodFetcher<T, String> methodFetcher;
	
	
	
	public PtAttributeStringDefinition(
			final Class<? extends T> clazz,
			final IPtNodeName xmlNodeName,
			final IPtNodeAttributeName xmlNodeAttribute,
			final String setterMethodName
		) {
		this.xmlNodeName = xmlNodeName;
		this.xmlNodeAttribute = xmlNodeAttribute;
		this.methodFetcher = new MethodFetcher<T, String>(clazz, String.class, setterMethodName);
	}
	
	public final IPtNodeName getXmlNodeName() {
		return this.xmlNodeName;
	}
	
	public final void invoke(final T alert, final Node node) {
		final Node attribute = node.getAttributes().getNamedItem(this.xmlNodeAttribute.getAttributeName());
		final String value = attribute.getTextContent().trim();
		this.methodFetcher.invoke(alert, value);
	}
	
}
