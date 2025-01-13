package net.sourceforge.jpotpourri.jpotface.table.modelx;

import java.lang.reflect.Method;

/**
 * @param <T> type of data to be displayed
 * @author christoph_pickl@users.sourceforge.net
 */
public class ColumnSpecifier<T extends IPtDataModelDisplayable> {

	private final Class<?> columnClass;
	
	private final String columnName;
	
	private final Method valueMethod;

	public ColumnSpecifier(final Class<?> columnClass, final String columnName, final Method valueMethod) {
		this.columnClass = columnClass;
		this.columnName = columnName;
		this.valueMethod = valueMethod;
	}
	
	@Override
	public String toString() {
		return "ColumnSpecifier[" +
				"columnClass=" + this.columnClass.getName() + ";" +
				"columnName=" + this.columnName + ";" +
				"valueMethod.name=" + this.valueMethod.getName() + "]";
	}

	public Class<?> getColumnClass() {
		return this.columnClass;
	}

	public String getColumnName() {
		return this.columnName;
	}

	public Object getValue(final T object) {
		try {
			return this.valueMethod.invoke(object);
		} catch (Exception e) { // IllegalArgumentException, IllegalAccessException, InvocationTargetException
			throw new RuntimeException("Could not invoke method on object!", e);
		}
	}
	
	
	
}
