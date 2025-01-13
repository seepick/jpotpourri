package net.sourceforge.jpotpourri.xmlparse;

/**
 * @param <T> object type providing setter method
 * @author christoph_pickl@users.sourceforge.net
 */
public abstract class PtCustomNodeDefinition<T> implements IPtNodeDefinition<T> {
	
	private final IPtNodeName xmlNodeName;
	
	public PtCustomNodeDefinition(final IPtNodeName xmlNodeName) {
		this.xmlNodeName = xmlNodeName;
	}
	
	public final IPtNodeName getXmlNodeName() {
		return this.xmlNodeName;
	}
}
