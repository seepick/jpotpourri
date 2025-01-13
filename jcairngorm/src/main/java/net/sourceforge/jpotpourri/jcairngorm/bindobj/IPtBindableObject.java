package net.sourceforge.jpotpourri.jcairngorm.bindobj;

import net.sourceforge.jpotpourri.jcairngorm.IPtBindingListener;

/**
 * @param <T> object type
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public interface IPtBindableObject<T> {
	
	void setValue(final T newValue);
	
	T getValue();
	
	boolean addBindingListener(final IPtBindingListener<T> listener);
	
	boolean removeBindingListener(final IPtBindingListener<T> listener);
	
}
