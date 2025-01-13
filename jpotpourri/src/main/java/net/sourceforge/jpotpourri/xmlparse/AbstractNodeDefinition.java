package net.sourceforge.jpotpourri.xmlparse;

import org.w3c.dom.Node;

/**
 * @param <T> object type providing setter method
 * @param <S> setter parameter type
 * @author christoph_pickl@users.sourceforge.net
 */
abstract class AbstractNodeDefinition<T, S> implements IPtNodeDefinition<T> {

	
	private final IPtNodeName xmlNodeName;
	private final MethodFetcher<T, S> methodFetcher; 
	
	AbstractNodeDefinition(
			final Class<? extends T> targetClass,
			final Class<S> argumentClass,
			final IPtNodeName xmlNodeName,
			final String setterMethodName
		) {
		this.methodFetcher = new MethodFetcher<T, S>(targetClass, argumentClass, setterMethodName);
		this.xmlNodeName = xmlNodeName;
	}
	
	public final IPtNodeName getXmlNodeName() {
		return this.xmlNodeName;
	}
	
	public final void invoke(final T alert, final Node node) {
		this.methodFetcher.invoke(alert, this.formatValue(node.getTextContent()));
	}
	
	abstract S formatValue(final String value);
}
