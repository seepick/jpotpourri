package net.sourceforge.jpotpourri.jpotface.table.modelx;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * @param <T> type of data to be displayed
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtDataModelDisplayer<T extends IPtDataModelDisplayable> extends AbstractTableModel {

	private static final long serialVersionUID = 4564191144783156668L;

	
	private final List<ColumnSpecifier<T>> columns;
	
	private List<T> data;
	
	
	public PtDataModelDisplayer(final Class<T> clazz, final Collection<T> data) {
		this.data = new ArrayList<T>(data);
		this.columns = AnnotationParser.<T>parse(clazz);
	}
	
	public void setData(final Collection<T> data) {
		this.data = new ArrayList<T>(data);
		this.fireTableDataChanged();
	}
	
	
    // -----------------------------------------------------------------------------------------------------------------
    //  TableModel interface methods
    // -----------------------------------------------------------------------------------------------------------------

	@Override
	public final Class<?> getColumnClass(final int colIndex) {
		return this.columns.get(colIndex).getColumnClass();
	}

	public final int getColumnCount() {
		return this.columns.size();
	}

	@Override
	public final String getColumnName(final int colIndex) {
		return this.columns.get(colIndex).getColumnName();
	}

	public final int getRowCount() {
		return this.data.size();
	}

	public final Object getValueAt(final int rowIndex, final int colIndex) {
		final T object = this.data.get(rowIndex);
		return this.columns.get(colIndex).getValue(object);
	}

	@Override
	public final boolean isCellEditable(final int rowIndex, final int colIndex) {
		return false;
	}

//	@Override
//	public final void setValueAt(Object arg0, int arg1, int arg2) {
//		
//	}

}
