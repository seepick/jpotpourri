package net.sourceforge.jpotpourri.jpotface.memory;

import java.lang.reflect.Method;

/**
 * 
 * @param <T> key type
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtStorageItem<T extends IPtMemoryKey<?>> {

	private final T key;

	private final Object target;
	
	private final Class<?> propertyClass;
	
	private final Method getterMethod;
	
	private final Method setterMethod;
	
	private final boolean isPrimitive;
	
	private final Object defaultValue;
	
	
	
	public PtStorageItem(
			final T key,
			final Object target,
			final Class<?> propertyClass,
			final Method getterMethod,
			final Method setterMethod,
			final boolean isPrimitive,
			final Object defaultValue
			) {
		this.key = key;
		this.target = target;
		this.propertyClass = propertyClass;
		this.getterMethod = getterMethod;
		this.setterMethod = setterMethod;
		this.isPrimitive = isPrimitive;
		this.defaultValue = defaultValue;
	}
	
	@Override
	public String toString() {
		return "PtStorageItem[" +
			"key=" + this.key + ";" +
			"target.class=" + this.target.getClass().getName() + ";" +
			"propertyClass=" + this.propertyClass.getName() + ";" +
			"getterMethod.name=" + this.getterMethod.getName() + ";" +
			"setterMethod.name=" + this.setterMethod.getName() + ";" +
			"isPrimitive=" + this.isPrimitive + ";" +
			"defaultValue=" + this.defaultValue + ";" +
		"]";
	}
	
	public T getKey() {
		return this.key;
	}
	
	public Object getTarget() {
		return this.target;
	}
	
	public Class<?> getPropertyClass() {
		return this.propertyClass;
	}
	
	public Method getGetterMethod() {
		return this.getterMethod;
	}

	public Method getSetterMethod() {
		return this.setterMethod;
	}

	public boolean isPrimitive() {
		return this.isPrimitive;
	}
	
	public Object getDefaultValue() {
		return this.defaultValue;
	}
	
}
