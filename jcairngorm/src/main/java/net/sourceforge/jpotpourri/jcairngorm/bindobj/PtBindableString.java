package net.sourceforge.jpotpourri.jcairngorm.bindobj;


/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtBindableString extends PtAbstractBindableSimpleObject<String> implements IPtBindableStringLike<String> {
	
	public PtBindableString(final String defaultValue) {
		this(defaultValue, "__String");
	}
	
	public PtBindableString(final String defaultValue, final String propertyName) {
		super(defaultValue, propertyName);
	}

	public final String getAsString() {
		return this.getValue();
	}

	public final void setAsString(final String value) {
		this.setValue(value);
	}
	
}
