package net.sourceforge.jpotpourri.jcairngorm.event;

import net.sourceforge.jpotpourri.jcairngorm.bindobj.IPtBindableObject;

/**
 * @param <T> bindable object type
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtBindingEvent<T> {

	private final IPtBindableObject<T> source;
	
	private final String propertyName;

	private final T oldValue;

	private final T newValue;

	
	public PtBindingEvent(
			final IPtBindableObject<T> source,
			final String propertyName,
			final T oldValue,
			final T newValue
		) {
		this.source = source;
		this.propertyName = propertyName;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
	

	public IPtBindableObject<T> getSource() {
		return this.source;
	}

	public T getOldValue() {
		return this.oldValue;
	}

	public String getPropertyName() {
		return this.propertyName;
	}

	public T getNewValue() {
		return this.newValue;
	}
	
}
