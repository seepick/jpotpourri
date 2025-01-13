package net.sourceforge.jpotpourri.jcairngorm.bindobj;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtBindableBoolean extends PtAbstractBindableSimpleObject<Boolean> {

	public PtBindableBoolean(final Boolean value) {
		this(value, "__Boolean");
	}
	
	public PtBindableBoolean(final Boolean value, final String propertyName) {
		super(value, propertyName);
	}
	
}
