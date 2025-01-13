package net.sourceforge.jpotpourri.jpotface.table;

import java.awt.Color;

import javax.swing.table.TableColumnModel;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public interface IPtTableFillEmptyRowsReceiver {
	
	Color getColorRowBackgroundEven();

	Color getColorRowBackgroundOdd();
	
	int getRowCount();
	
	int getRowHeight();
	
	TableColumnModel getColumnModel();
	
	boolean getShowVerticalLines();
	
}
