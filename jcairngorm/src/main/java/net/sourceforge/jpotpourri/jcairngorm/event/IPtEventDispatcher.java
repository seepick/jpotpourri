package net.sourceforge.jpotpourri.jcairngorm.event;


/**
 * @author christoph_pickl@users.sourceforge.net
 */
public interface IPtEventDispatcher {

	void dispatch(final PtAbstractCairngormEvent<?> event);
	
	
	boolean addEventListener(final IPtEventListener<? extends PtAbstractCairngormEvent<?>> listener);
	
	boolean removeEventListener(final IPtEventListener<? extends PtAbstractCairngormEvent<?>> listener);
		
}
