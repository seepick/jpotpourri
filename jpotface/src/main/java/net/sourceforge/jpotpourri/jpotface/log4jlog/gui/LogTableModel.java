package net.sourceforge.jpotpourri.jpotface.log4jlog.gui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import net.sourceforge.jpotpourri.jpotface.log4jlog.De;
import net.sourceforge.jpotpourri.jpotface.log4jlog.IPtDisplayedLogMessages;
import net.sourceforge.jpotpourri.jpotface.log4jlog.PtLog4jEvent;
import net.sourceforge.jpotpourri.jpotface.log4jlog.PtLogTableFilter;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
final class LogTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -4864887345823485298L;

	private final transient ModelCoreData data = new ModelCoreData();

	private final Set<IPtDisplayedLogMessages> listeners = new HashSet<IPtDisplayedLogMessages>();
	
	private static final List<LogTableColumn> TABLE_COLUMNS = new ArrayList<LogTableColumn>(2);
	static {
		TABLE_COLUMNS.add(LogTableColumn.DATE);
		TABLE_COLUMNS.add(LogTableColumn.LEVEL);
		TABLE_COLUMNS.add(LogTableColumn.CLASS);
		TABLE_COLUMNS.add(LogTableColumn.METHOD);
		
		TABLE_COLUMNS.add(LogTableColumn.MESSAGE);
		TABLE_COLUMNS.add(LogTableColumn.THREAD);
		TABLE_COLUMNS.add(LogTableColumn.EXCEPTION);
	}

	
	
	public LogTableModel() {
		// nothing to do
	}

	private void broadcastDisplayedLogMessagesChanged(final int displayedMessages, final int totalMessages) {
		for (IPtDisplayedLogMessages listener : this.listeners) {
			listener.displayedLogMessagesChanged(displayedMessages, totalMessages);
		}
	}
	
	// ----------------- custom

	public void addLoggingEvent(final PtLog4jEvent event) {
		De.bug("Log4jTableModel --- addLoggingEvent(" + event + ")");
		this.data.addLoggingEvent(event);
		this.broadcastDisplayedLogMessagesChanged(this.data.getSize(), this.data.getAllEventsSize());
		this.fireTableDataChanged();
	}
	
	public void doFilter(final PtLogTableFilter filter) {
		De.bug("Log4jTableModel --- doFilter(" + filter + ")");
		this.data.doFilter(filter);
		this.broadcastDisplayedLogMessagesChanged(this.data.getSize(), this.data.getAllEventsSize());
		this.fireTableDataChanged();
	}
	
	public void doClearData() {
		De.bug("Log4jTableModel --- doClearData()");
		this.data.doClear();
		this.broadcastDisplayedLogMessagesChanged(0, 0);
		this.fireTableDataChanged();
	}
	
	public static String getColumnIdentifier(final LogTableColumn column) {
		return column.getColumnLabel();
	}
	
	public void addDisplayedLogMessages(final IPtDisplayedLogMessages listener) {
		this.listeners.add(listener);
	}
	
	public boolean removeDisplayedLogMessages(final IPtDisplayedLogMessages listener) {
		return this.listeners.remove(listener);
	}
	
	public PtLog4jEvent getEventAt(final int rowIndex) {
		return this.data.get(rowIndex);
	}
	
	// ----------------- table model optional
	
	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex) {
		return false;
	}

	
	@Override
	public String getColumnName(final int columnIndex) {
		return TABLE_COLUMNS.get(columnIndex).getColumnLabel();
	}
	
	// ----------------- table model mandatory
	
	public int getColumnCount() {
		return TABLE_COLUMNS.size();
	}

	public int getRowCount() {
		return this.data.getSize();
	}

	public Object getValueAt(final int rowIndex, final int columnIndex) {
		final PtLog4jEvent event = this.data.get(rowIndex);
		return TABLE_COLUMNS.get(columnIndex).getTableModelValue(event);
		
	}

}
