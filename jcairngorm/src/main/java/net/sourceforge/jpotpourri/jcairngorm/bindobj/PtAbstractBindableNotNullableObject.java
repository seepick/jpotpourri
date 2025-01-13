package net.sourceforge.jpotpourri.jcairngorm.bindobj;

import net.sourceforge.jpotpourri.jcairngorm.event.PtBindingEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @param <T> object type
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public abstract class PtAbstractBindableNotNullableObject<T> extends PtDefaultBindableObject<T> {
	
	private static final Log LOG = LogFactory.getLog(PtAbstractBindableSimpleObject.class);

	private T value;

	private final String propertyName;
	
	public PtAbstractBindableNotNullableObject(
			final T value,
			final String propertyName
		) {
		this.value = value;
		this.propertyName = propertyName;
	}
	
	public final void setValue(final T newValue) {
		LOG.trace("setValue(newValue=" + newValue + ")");
		if(newValue == null) {
			throw new IllegalArgumentException(this.getClass().getSimpleName() + ".setValue(null)");
		}
		final T oldValue = this.value;
		this.value = newValue;
		this.dispatchBindingEvent(new PtBindingEvent<T>(this, this.propertyName, oldValue, newValue));
	}
	
	public final T getValue() {
		return this.value;
	}
}
