package net.sourceforge.jpotpourri.jpotface.table.model;

import java.util.Collection;

public class PtAdvancedEditableTableModel<T> extends PtAdvancedTableModel<T> {

	private static final long serialVersionUID = -6569346818756043272L;

	
	public PtAdvancedEditableTableModel(
			final Collection<PtAbstractTableColumn<T>> columns,
			final Collection<T> rows) {
		super(columns, rows);
	}

	public void addObject(final T object) {
		this.getModelCoreData().add(object);
		// FIXME preselect all rows, which were selected before!
		this.fireTableDataChanged();
	}

	public void addObject(final int rowIndex, final T object) {
		this.getModelCoreData().add(rowIndex, object);
		this.fireTableDataChanged();
	}
	
	/**
	 * @return number of actually removed objects
	 */
	public int remove(final T... objects) {
		final int removedObjects = this.getModelCoreData().remove(objects);
		this.fireTableDataChanged();
		return removedObjects;
	}
	
	public void clear() {
		this.getModelCoreData().clear();
		this.fireTableDataChanged();
	}
	
}
