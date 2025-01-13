package net.sourceforge.jpotpourri.jpotface.memory;

import java.util.Collection;

/**
 * @param <T> key type
 * @author christoph_pickl@users.sourceforge.net
 */
public abstract class PtAbstractMemoryStorage<T extends IPtMemoryKey<?>> {


	public abstract void enableMemoryOn(
		final T key,
		final Object target,
		final String getterMethodName,
		final String setterMethodName,
		final Class<?> propertyClass,
		final boolean isPrimitive,
		final Object defaultValue
	);
	
	
	/** save method */
	public abstract void retain();
	
	/** clears all data */
	public final void reset() {
		this.resetInternal();
		this.resetAllItemsToDefault();
	}
	
	abstract void resetInternal();
	
	
	abstract void addStorageItem(final PtStorageItem<T> item);
	
	abstract boolean isValidPropertyClass(final Class<?> propertyClass);

	abstract Collection<PtStorageItem<T>> getItems();
	
	
	private void resetAllItemsToDefault() {
		for (final PtStorageItem<T> item : this.getItems()) {
			try {
				item.getSetterMethod().invoke(item.getTarget(), item.getDefaultValue());
			} catch (Exception e) { // IllegalArgumentException, IllegalAccessException, InvocationTargetException
				throw new RuntimeException("Could not invoke setter method [" + item.getSetterMethod() + "] " +
						"of object [" + item.getTarget() + "] with argument [" + item.getDefaultValue() + "]!", e);
			}
		}
	}
}
