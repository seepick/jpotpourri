package net.sourceforge.jpotpourri.jcairngorm.component.fireonly;

import net.sourceforge.jpotpourri.jcairngorm.event.PtAbstractCairngormEvent;
import net.sourceforge.jpotpourri.jcairngorm.event.PtEventDispatcher;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
abstract class AbstractEventComponentSkeleton implements IPtBindableFireComponent {

	public final void dispatchEvent(final PtAbstractCairngormEvent<?> event) {
		PtEventDispatcher.getInstance().dispatch(event);
	}

}
