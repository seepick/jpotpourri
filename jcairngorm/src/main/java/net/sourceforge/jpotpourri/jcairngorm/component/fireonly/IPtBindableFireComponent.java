package net.sourceforge.jpotpourri.jcairngorm.component.fireonly;

import net.sourceforge.jpotpourri.jcairngorm.event.PtAbstractCairngormEvent;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public interface IPtBindableFireComponent {

	void dispatchEvent(final PtAbstractCairngormEvent<?> event);
	
}
