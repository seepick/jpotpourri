package net.sourceforge.jpotpourri.jcairngorm.bindobj;

import java.util.HashSet;
import java.util.Set;

import net.sourceforge.jpotpourri.jcairngorm.IPtBindingListener;
import net.sourceforge.jpotpourri.jcairngorm.event.PtBindingEvent;

/**
 * @param <T> object type
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public abstract class PtDefaultBindableObject<T> implements IPtBindableObject<T>  {

	
	private final Set<IPtBindingListener<T>> listeners = new HashSet<IPtBindingListener<T>>();
	
	
	protected final void dispatchBindingEvent(final PtBindingEvent<T> event) {
		for (final IPtBindingListener<T> listener : this.listeners) {
			listener.receiveEvent(event);
		}
	}

	public boolean addBindingListener(final IPtBindingListener<T> listener) {
		return this.listeners.add(listener);
	}

	public boolean removeBindingListener(final IPtBindingListener<T> listener) {
		return this.listeners.remove(listener);
	}
}
