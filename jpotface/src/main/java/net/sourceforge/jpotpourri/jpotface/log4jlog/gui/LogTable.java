package net.sourceforge.jpotpourri.jpotface.log4jlog.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import net.sourceforge.jpotpourri.jpotface.log4jlog.De;
import net.sourceforge.jpotpourri.jpotface.log4jlog.PtLog4jEvent;
import net.sourceforge.jpotpourri.jpotface.log4jlog.PtLogGuiTableDefinition;
import net.sourceforge.jpotpourri.jpotface.table.IPtTableBodyContextListener;
import net.sourceforge.jpotpourri.jpotface.table.IPtTableFillEmptyRowsReceiver;
import net.sourceforge.jpotpourri.jpotface.table.PtTableBodyContext;
import net.sourceforge.jpotpourri.jpotface.table.PtTableEmptyRowsPainter;

import org.apache.log4j.Level;
import org.jdesktop.swingx.JXTable;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
class LogTable extends JXTable implements IPtTableFillEmptyRowsReceiver, IPtTableBodyContextListener {
	// TODO extends PtMacLikeTable + remove IPtTableFillEmptyRowsReceiver!
	private static final long serialVersionUID = -5142437725186427922L;

	private final transient PtTableEmptyRowsPainter emptyRowsPainter;

	private static final Color COLOR_BG_ROW_WARN = Color.YELLOW;
	private static final Color COLOR_BG_ROW_ERROR = Color.RED;
	private static final Color COLOR_BG_ROW_FATAL = new Color(187, 74, 207);

    private static final String CMD_SHOW_DETAILS = "CMD_SHOW_DETAILS";
    
	
	private Color colorRowBackgroundEven;
	private final Color colorRowBackgroundOdd;

	private final Color colorRowForeground;
	private final Color colorSelectedRowForeground;
	private final Color colorSelectedRowBackground;

	
    
	public LogTable(final LogTableModel tableModel, final PtLogGuiTableDefinition tableDefinition) {
		super(tableModel);
		// this.addHighlighter() ... no, we are painting alternating rows at our own
		De.bug("Creating new Log4jTable with defintion: " + tableDefinition);
		
		if(tableDefinition.getRows() != PtLogGuiTableDefinition.DEFAULT_ROWS) {
			this.setVisibleRowCount(tableDefinition.getRows());
		}
		
		this.colorRowBackgroundEven = tableDefinition.getColorRowBackgroundEven();
		this.colorRowBackgroundOdd = tableDefinition.getColorRowBackgroundOdd();
		
		this.colorRowForeground = tableDefinition.getColorRowForeground();
		this.colorSelectedRowForeground = tableDefinition.getColorSelectedRowForeground();
		this.colorSelectedRowBackground = tableDefinition.getColorSelectedRowBackground();
		
		// set log level comparator
		this.getColumnExt(LogTableModel.getColumnIdentifier(LogTableColumn.LEVEL)).
			setComparator(new Comparator<String>() {
			public int compare(final String o1, final String o2) {
				final Level l1 = Level.toLevel(o1);
				final Level l2 = Level.toLevel(o2);
				return l1.toInt() - l2.toInt();
			}
	    });

        // adjust column width
        this.getColumnExt(LogTableModel.getColumnIdentifier(LogTableColumn.LEVEL)).setMaxWidth(44);
        this.getColumnExt(LogTableModel.getColumnIdentifier(LogTableColumn.LEVEL)).setMinWidth(44);
        this.getColumnExt(LogTableModel.getColumnIdentifier(LogTableColumn.DATE)).setMaxWidth(130);
        this.getColumnExt(LogTableModel.getColumnIdentifier(LogTableColumn.DATE)).setMinWidth(130);
//        this.getColumnExt(Log4jTableModel.getColumnIdentifier(LogTableColumn.CLASS)).setPreferredWidth(80);
//        this.getColumnExt(Log4jTableModel.getColumnIdentifier(LogTableColumn.THREAD)).setPreferredWidth(60);
        
		// hide some columns
		this.getColumnExt(LogTableModel.getColumnIdentifier(LogTableColumn.METHOD)).setVisible(false);
		this.getColumnExt(LogTableModel.getColumnIdentifier(LogTableColumn.THREAD)).setVisible(false);
		this.getColumnExt(LogTableModel.getColumnIdentifier(LogTableColumn.EXCEPTION)).setVisible(false);
		
		this.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        this.setColumnSelectionAllowed(false);
        this.setRowSelectionAllowed(true);
        
        this.setColumnControlVisible(true);
        this.setShowGrid(false);

        this.packAll();
        
        this.emptyRowsPainter = new PtTableEmptyRowsPainter(this);
        this.initContextMenu();
        this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent event) {
				if(event.getClickCount() >= 2) {
					if(getSelectedRowCount() == 1) {
						LogTable.this.doShowDetails(getSelectedRow());
					} else {
						Toolkit.getDefaultToolkit().beep();
					}
				}
			}
        });
	}
	
	private void initContextMenu() {
		final List<JMenuItem> itemsSingle = new ArrayList<JMenuItem>();
		PtTableBodyContext.newJMenuItem(itemsSingle, "Show Details", CMD_SHOW_DETAILS); // optional icon
        

        final List<JMenuItem> itemsMultiple = new ArrayList<JMenuItem>();
        final JMenuItem exceptionDetailsMultiple =
        	PtTableBodyContext.newJMenuItem(itemsMultiple, "Show Details", CMD_SHOW_DETAILS); // optional icon
        exceptionDetailsMultiple.setEnabled(false);
        new PtTableBodyContext(this, itemsSingle, itemsMultiple, this);
	}

	@Override
	public final void paint(final Graphics g) {
		super.paint(g);
		this.emptyRowsPainter.delegatePaint(g);
	}

	public final Color getColorRowBackgroundEven() {
		return this.colorRowBackgroundEven;
	}

	public final Color getColorRowBackgroundOdd() {
		return this.colorRowBackgroundOdd;
	}
	


	@Override
	public final Component prepareRenderer(
			final TableCellRenderer renderer,
			final int modelRowIndex,
			final int modelColumnIndex) {
		final Component c = super.prepareRenderer(renderer, modelRowIndex, modelColumnIndex);

		final Color bgColor;
		final Color fgColor;
//        boolean focused = hasFocus();
        boolean selected = isCellSelected(modelRowIndex, modelColumnIndex);
        
        if(selected) {
        	bgColor = this.colorSelectedRowBackground;
        	fgColor = this.colorSelectedRowForeground;
        } else {
        	final boolean isEven = modelRowIndex % 2 == 0;
        	
        	final int viewRow = this.convertRowIndexToModel(modelRowIndex);
        	final PtLog4jEvent event = this.getProperModel().getEventAt(viewRow);
        	final Level level = event.getLevel();
        	
        	if(level == Level.WARN) {
        		bgColor = COLOR_BG_ROW_WARN;
        	} else if(level == Level.ERROR) {
        		bgColor = COLOR_BG_ROW_ERROR;
        	} else if(level == Level.FATAL) {
        		bgColor = COLOR_BG_ROW_FATAL;
        	} else {
        		bgColor = isEven ? this.colorRowBackgroundEven : this.colorRowBackgroundOdd;
        	}
        	
        	
        	fgColor = this.colorRowForeground;
        }
        c.setBackground(bgColor);
		c.setForeground(fgColor);
        
		return c;
	}

	private LogTableModel getProperModel() {
		return (LogTableModel) this.getModel();
	}
    
	public final void contextMenuClicked(final JMenuItem item, final int tableRowSelected) {
		final String cmd = item.getActionCommand();
		if(cmd.equals(CMD_SHOW_DETAILS)) {
			this.doShowDetails(tableRowSelected);
		} else {
			throw new IllegalArgumentException("actionCommand=" + cmd);
		}
	}
	
	private void doShowDetails(final int viewRow) {
		final int modelRow = this.convertRowIndexToModel(viewRow);
		final PtLog4jEvent event = this.getProperModel().getEventAt(modelRow);
		new LogEventDialog(event).setVisible(true);
	}


	public final void contextMenuClickedMultiple(final JMenuItem item, final int[] tableRowsSelected) {
		final String cmd = item.getActionCommand();
		if(cmd.equals(CMD_SHOW_DETAILS)) {
			assert(false); // may not happen
		} else {
			throw new IllegalArgumentException("actionCommand=" + cmd);
		}
	}
	
}
