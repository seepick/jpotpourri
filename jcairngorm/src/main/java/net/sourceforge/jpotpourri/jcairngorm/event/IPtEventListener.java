package net.sourceforge.jpotpourri.jcairngorm.event;

/**
 * @param <T> event type
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public interface IPtEventListener<T extends PtAbstractCairngormEvent<?>> {

	void receiveEvent(final T event);
	
}
