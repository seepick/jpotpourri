package net.sourceforge.jpotpourri.jcairngorm.bindobj;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sourceforge.jpotpourri.jcairngorm.event.PtBindingEvent;

/**
 * @param <T> object type
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public abstract class PtAbstractBindableSimpleObject<T> extends PtDefaultBindableObject<T> {
	
	private static final Log LOG = LogFactory.getLog(PtAbstractBindableSimpleObject.class);

	private T value;

	private final String propertyName;
	
	public PtAbstractBindableSimpleObject(
			final T value,
			final String propertyName
		) {
		this.value = value;
		this.propertyName = propertyName;
	}
	
	public final void setValue(final T newValue) {
		LOG.trace("setValue(newValue=" + newValue + ")");
		final T oldValue = this.value;
		this.value = newValue;
		this.dispatchBindingEvent(new PtBindingEvent<T>(this, this.propertyName, oldValue, newValue));
	}
	
	public final T getValue() {
		return this.value;
	}
	
	@Override
	public String toString() {
		return this.propertyName + "=[" + this.value + "]";
	}
	
}
