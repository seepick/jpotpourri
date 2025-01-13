package net.sourceforge.jpotpourri.jpotface.log4jlog.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.sourceforge.jpotpourri.jpotface.inputfield.search.IPtDefaultSearchFieldListener;
import net.sourceforge.jpotpourri.jpotface.log4jlog.PtLog4jEvent;
import net.sourceforge.jpotpourri.jpotface.log4jlog.PtLogGuiHandlerDefinition;
import net.sourceforge.jpotpourri.jpotface.log4jlog.PtLogTableFilter;

import org.apache.log4j.Level;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtLogPanel extends JPanel implements IPtDefaultSearchFieldListener {

	private static final long serialVersionUID = -2803278698478385285L;

	
	private final LogLevelFilterBox logLevelFilterBox = new LogLevelFilterBox(Level.INFO); // default value
	private final LogSearchField searchField = new LogSearchField();
	
	private final LogTable table;
	private final LogTableModel tableModel = new LogTableModel();
	private final DisplayedLogMessagesPanel displayedLogMsgs = new DisplayedLogMessagesPanel();
	
	private PtLogTableFilter tableFilter;
	
	
	
	public PtLogPanel(final PtLogGuiHandlerDefinition handlerDefinition) {
		this.table = new LogTable(this.tableModel, handlerDefinition.getTableDefinition());
		this.tableModel.addDisplayedLogMessages(this.displayedLogMsgs);
		
		// init gui
		final JScrollPane scrollPane = new JScrollPane(this.table);
		this.setLayout(new BorderLayout());
		this.add(this.newFilterPanel(), BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
	}

	
	public void processLoggingEvent(final PtLog4jEvent event) {
		this.tableModel.addLoggingEvent(event);
	}


	public void doResetSearch() {
		final PtLogTableFilter properFilter;
		if(this.tableFilter == null) {
			properFilter = PtLogTableFilter.FILTER_NONE;
		} else {
			this.tableFilter = PtLogTableFilter.newBySearchString(this.tableFilter, null);
			properFilter = this.tableFilter;
		}
		this.tableModel.doFilter(properFilter);
	}

	public void doSearch(final String s) {
		final String p = this.searchField.getProperText();
		if(s == null || p == null) {
			assert (s == null && p == null);
		} else {
			assert (s.equals(p)); // TODO asserts
		}
		
		this.tableFilter = new PtLogTableFilter(this.logLevelFilterBox.getSelectedLevel(), s);
		this.tableModel.doFilter(this.tableFilter);
	}
	
	
	
	
	
	
	private JPanel newFilterPanel() {
		final JPanel panel = new JPanel(new BorderLayout());


		final Dimension dim = new Dimension(this.logLevelFilterBox.getPreferredSize().width + 30,
											this.logLevelFilterBox.getPreferredSize().height);
		this.logLevelFilterBox.setPreferredSize(dim);
		
		this.logLevelFilterBox.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				doLogLevelFilterBoxChanged();
			}
		});
		this.doLogLevelFilterBoxChanged(); // apply initial filter
		
		final JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				doClearLog();
		} });
		
		final JPanel panelWest = new JPanel();
		panelWest.add(btnClear);
		panelWest.add(new JLabel("  Log Level: "));
		panelWest.add(this.logLevelFilterBox);
		panel.add(panelWest, BorderLayout.WEST);

        this.searchField.addDefaultSearchFieldListener(this);
        
        final JPanel searchFieldPanel = new JPanel();
        searchFieldPanel.add(this.displayedLogMsgs.getComponent());
        searchFieldPanel.add(this.searchField);
		panel.add(searchFieldPanel, BorderLayout.EAST);
		
		return panel;
	}
	
	private void doClearLog() {
		this.tableModel.doClearData();
	}
	private void doLogLevelFilterBoxChanged() {
		this.tableFilter = new PtLogTableFilter(
				this.logLevelFilterBox.getSelectedLevel(),
				this.searchField.getProperText());
		this.tableModel.doFilter(this.tableFilter);
	}
	
}
