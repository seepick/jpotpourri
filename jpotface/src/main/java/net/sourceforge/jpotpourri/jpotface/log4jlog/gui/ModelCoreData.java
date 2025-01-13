package net.sourceforge.jpotpourri.jpotface.log4jlog.gui;

import java.util.LinkedList;
import java.util.List;

import net.sourceforge.jpotpourri.fprog.PtFprog;
import net.sourceforge.jpotpourri.jpotface.log4jlog.De;
import net.sourceforge.jpotpourri.jpotface.log4jlog.PtLog4jEvent;
import net.sourceforge.jpotpourri.jpotface.log4jlog.PtLogTableFilter;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
final class ModelCoreData {

	private List<PtLog4jEvent> allEvents = new LinkedList<PtLog4jEvent>();
	private List<PtLog4jEvent> filteredEvents = new LinkedList<PtLog4jEvent>();
	
	
//	private final Map<Level, List<LoggingEvent>> map = new HashMap<Level, List<LoggingEvent>>();
	
	private PtLogTableFilter filter;
	
	public ModelCoreData() {
//		for (Level level : LOG_LEVELS) {
//			this.map.put(level, new LinkedList<LoggingEvent>());
//		}
	}
	
	
	public synchronized void addLoggingEvent(final PtLog4jEvent event) {
		this.allEvents.add(0, event);
		
		if(this.isFilter() && this.filter.execute(event)) {
			this.filteredEvents.add(0, event);
		}
	}
	
	public synchronized PtLog4jEvent get(final int index) {
		return this.list().get(index);
	}

	public synchronized int getSize() {
		return this.list().size();
	}

	public synchronized int getAllEventsSize() {
		return this.allEvents.size();
	}
	
	public synchronized void doClear() {
		this.allEvents.clear();
		this.filteredEvents.clear();
	}
	
	
	private List<PtLog4jEvent> list() {
		return this.isFilter() ? this.filteredEvents : this.allEvents;
	}

	public synchronized void doFilter(final PtLogTableFilter newFilter) {
		if(newFilter == null) {
			throw new NullPointerException("filter");
		}
		if(newFilter.isFilterOff()) { // filter.level == Level.ALL && searchString == null
			this.doUnfilter();
		} else {
			De.bug("ModelCoreData --- filtering with filter: " + newFilter);
			this.filter = newFilter;
			final List<PtLog4jEvent> filteredList;
			if(this.filter.isFilterAll()) { // filter.level == Level.OFF
				filteredList = new LinkedList<PtLog4jEvent>();
			} else {
				filteredList = new LinkedList<PtLog4jEvent>(PtFprog.<PtLog4jEvent>filter(this.filter, this.allEvents));
			}
			this.filteredEvents = filteredList;
		}
	}
	
	public synchronized void doUnfilter() {
		De.bug("ModelCoreData --- doUnfilter() invoked");
		this.filter = null;
	}
	private boolean isFilter() {
		return this.filter != null;
	}
}
