package net.sourceforge.jpotpourri.jpotface.table.model;

public abstract class PtAbstractTableColumn<T> {

	private final String label;
	
	private Class<?> colClass = String.class;
	
	private boolean editable = false;
	
	// TODO boolean initiallyVisible (for TableX)
	// int preferredWidth
	
	public PtAbstractTableColumn(final String label) {
		if(label == null) {
			throw new NullPointerException("label");
		}
		this.label = label;
	}
	

	public String getLabel() {
		return this.label;
	}
	
	public void setColClass(final Class<?> colClass) {
		if(colClass == null) {
			throw new NullPointerException("colClass");
		}
		this.colClass = colClass;
	}
	
	public Class<?> getColClass() {
		return this.colClass;
	}
	
	public void setEditable(final boolean editable) {
		this.editable = editable;
	}

	public boolean isEditable() {
		return this.editable;
	}
	
	protected abstract Object getValueAt(final T row);
}
