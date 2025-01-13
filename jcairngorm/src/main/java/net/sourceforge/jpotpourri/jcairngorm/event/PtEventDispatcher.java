package net.sourceforge.jpotpourri.jcairngorm.event;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtEventDispatcher implements IPtEventDispatcher {

	private static final Log LOG = LogFactory.getLog(PtEventDispatcher.class);
	
	private static final PtEventDispatcher INSTANCE = new PtEventDispatcher();
	
	
	private final Set<IPtEventListener> listeners = new HashSet<IPtEventListener>();
	
	
	private PtEventDispatcher() {
		// nothing to do
	}
	
	public static IPtEventDispatcher getInstance() {
		return PtEventDispatcher.INSTANCE;
	}
	
	public void dispatch(final PtAbstractCairngormEvent<?> event) {
		LOG.debug("dispatch(event=" + event + ")");
		for (final IPtEventListener listener : this.listeners) {
			listener.receiveEvent(event);
		}
	}

	public boolean addEventListener(final IPtEventListener listener) {
		LOG.debug("addEventListener(listener=" + listener + ")");
		return this.listeners.add(listener);
	}
	
	public boolean removeEventListener(final IPtEventListener listener) {
		LOG.debug("removeEventListener(listener=" + listener + ")");
		return this.listeners.remove(listener);
	}
}
