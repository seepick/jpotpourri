package net.sourceforge.jpotpourri.jpotface.memory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import net.sourceforge.jpotpourri.util.PtClassUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @param <K> key type
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtPreferencesMemoryStorage<K extends IPtMemoryKey<String>> extends PtAbstractMemoryGuiStorage<K> {

	private static final Log LOG = LogFactory.getLog(PtPreferencesMemoryStorage.class);
	
	private static final Preferences PREF = Preferences.userNodeForPackage(PtPreferencesMemoryStorage.class);
	
	private final Map<Class<?>, PreferencesMethod<K>> prefClassStore;
	{
		final Map<Class<?>, PreferencesMethod<K>> tmp = new HashMap<Class<?>, PreferencesMethod<K>>();

		try {
			tmp.put(Boolean.class, new PreferencesMethod<K>(PREF,
					// Preferences.getBoolean(String key, boolean def):boolean
					Preferences.class.getDeclaredMethod("getBoolean", String.class, boolean.class),
					// Preferences.putBoolean(String key, boolean value):void
					Preferences.class.getDeclaredMethod("putBoolean", String.class, boolean.class)));
			
			tmp.put(int.class, new PreferencesMethod<K>(PREF,
					// Preferences.getInt(String key, int def):int
					Preferences.class.getDeclaredMethod("getInt", String.class, int.class),
					// Preferences.putInt(String key, int value):void
					Preferences.class.getDeclaredMethod("putInt", String.class, int.class)));
			
			tmp.put(long.class, new PreferencesMethod<K>(PREF,
					// Preferences.getLong(String key, long def):long
					Preferences.class.getDeclaredMethod("getLong", String.class, long.class),
					// Preferences.putLong(String key, long value):void
					Preferences.class.getDeclaredMethod("putLong", String.class, long.class)));
			
			tmp.put(float.class, new PreferencesMethod<K>(PREF,
					// Preferences.getFloat(String key, float def):float
					Preferences.class.getDeclaredMethod("getFloat", String.class, float.class),
					// Preferences.putFloat(String key, float value):void
					Preferences.class.getDeclaredMethod("putFloat", String.class, float.class)));
			
			tmp.put(double.class, new PreferencesMethod<K>(PREF,
					// Preferences.getDouble(String key, double def):double
					Preferences.class.getDeclaredMethod("getDouble", String.class, double.class),
					// Preferences.putDouble(String key, double value):void
					Preferences.class.getDeclaredMethod("putDouble", String.class, double.class)));
			
			tmp.put(String.class, new PreferencesMethod<K>(PREF,
					// Preferences.get(String key, String def):String
					Preferences.class.getDeclaredMethod("get", String.class, String.class),
					// Preferences.put(String key, String value):void
					Preferences.class.getDeclaredMethod("put", String.class, String.class)));
			
		} catch(final Exception e) {
			final String errMsg = "Internal Error: Could not get Preferences methods!";
			LOG.error(errMsg, e);
			throw new RuntimeException(errMsg, e);
		}
		
		this.prefClassStore = Collections.unmodifiableMap(tmp);
	}
	
	
	private final Set<PtStorageItem<K>> items = new HashSet<PtStorageItem<K>>();
	
	private final Set<K> keys = new HashSet<K>();
	


	public PtPreferencesMemoryStorage() {
		// nothing to do
	}


	@Override
	final void resetInternal() {
		try {
			PREF.clear();
			PREF.flush();
		} catch (BackingStoreException e) {
			throw new RuntimeException("Could not reset preferences memory storage!", e);
		}
	}


	@Override
	final Collection<PtStorageItem<K>> getItems() {
		return this.items;
	}
	
    // -----------------------------------------------------------------------------------------------------------------
    //  enable stuff
    // -----------------------------------------------------------------------------------------------------------------

	// FEATURE enableMemoryOn shortcut for own classes also (not only JTextField/JCheckBox) and 
	//         store their getter/setter-names within a certain datastructure
	
	@Override
	public void enableMemoryOn(
			final K key,
			final Object target,
			final String getterMethodName,
			final String setterMethodName,
			final Class<?> propertyClass,
			final boolean isPrimitive,
			final Object defaultValue
			) {
		if(target == null || getterMethodName == null || 
			setterMethodName == null || propertyClass == null || defaultValue == null) {
			throw new NullPointerException();
		}
		
		if(this.isValidPropertyClass(propertyClass) == false) {
			final String errMsg = "Invalid property class [" + propertyClass.getName() + "]!";
			LOG.warn(errMsg);
			throw new RuntimeException(errMsg);
		}
		
		if(checkClassEquality(defaultValue.getClass(), propertyClass) == false) {
			final String errMsg = "DefaultValue class (" + defaultValue.getClass().getSimpleName() + ") " +
								  "does not match Property class (" + propertyClass.getSimpleName() + ")!";
			LOG.warn(errMsg);
			throw new RuntimeException(errMsg);
		}
		
		final Class<?> targetClass = target.getClass();
		final Method getterMethod = fetchMethod(targetClass, getterMethodName, isPrimitive, null);
		if(checkClassEquality(getterMethod.getReturnType(), propertyClass) == false) {
			final String errMsg = "Getter method must return something of class [" + propertyClass.getName() + "] " +
					"but does return [" + getterMethod.getReturnType().getName() + "]!";
			LOG.warn(errMsg);
			throw new RuntimeException(errMsg);
		}
		
		final Method setterMethod = fetchMethod(targetClass, setterMethodName, isPrimitive, propertyClass);
		if(setterMethod.getReturnType() != void.class) {
			final String errMsg = "SetterMethod must return void but [" + setterMethod.getReturnType().getName() + "]!";
			LOG.warn(errMsg);
			throw new RuntimeException(errMsg);
		}
		
		
		this.addStorageItem(new PtStorageItem<K>(
				key, target, propertyClass, getterMethod, setterMethod, isPrimitive, defaultValue));
	}

	
	private static boolean checkClassEquality(final Class<?> clazz1, final Class<?> clazz2) {
		if(clazz1 == clazz2) {
			return true;
		}
		
		if(clazz1.isPrimitive() == false && clazz2.isPrimitive() == false) {
			return false;
		}
		
		return PtClassUtil.areClassesPrimitiveAndComplex(clazz1, clazz2);
	}
	
	private static Method fetchMethod(
			final Class<?> clazz,
			final String methodName,
			final boolean isPrimitive, 
			final Class<?> parameterType
			) {
		try {
			if(parameterType == null) {
				return clazz.getMethod(methodName);
			} else {
				final Class<?> properParameterType;
				if(isPrimitive && parameterType.isPrimitive() == false) {
					properParameterType = PtClassUtil.convertToPrimitiveClass(parameterType);
				} else {
					properParameterType = parameterType;
				}
				return clazz.getMethod(methodName, properParameterType);
			}
			
		} catch (final Exception e) { // SecurityException, NoSuchMethodException
			final String errMsg = "Could not get Method [" + methodName + "] for Class [" + clazz.getName() + "] " +
					"with ParameterTypes " + (parameterType != null ? parameterType.getName() : "null") + "!";
			LOG.warn(errMsg, e);
			throw new RuntimeException(errMsg, e);
		}
		
	}
    // -----------------------------------------------------------------------------------------------------------------
    //  load/save stuff
    // -----------------------------------------------------------------------------------------------------------------
	
	@Override
	public void retain() {
		LOG.info("retain() " + this.items.size() + " items");
		
		
		for (final PtStorageItem<K> item : this.items) {
			final PreferencesMethod<K> prefMethod = this.prefClassStore.get(item.getPropertyClass());
			try {
				final Method getterMethod = item.getGetterMethod();
				
				LOG.trace("Invoking getter method [" + getterMethod.getName()  + "] " +
						"for target [" + item.getTarget().getClass().getName() + "].");
				final Object value = getterMethod.invoke(item.getTarget());
				LOG.trace("Setting value to [" + value + "] for key [" + item.getKey() + "].");
				prefMethod.setValue(item.getKey(), value);
			} catch (Exception e) { // IllegalArgumentException, IllegalAccessException, InvocationTargetException
				final String errMsg = "Could not set value for item [" + item + "]!";
				LOG.error(errMsg, e);
				throw new RuntimeException(errMsg, e);
			}
		}
	}

	@Override
	void addStorageItem(final PtStorageItem<K> item) {
		LOG.info("addStorageItem(item=" + item + ")");

		if(this.keys.add(item.getKey()) == false) {
			throw new IllegalArgumentException("The key [" + item.getKey() + "] is already in use!");
		}
		
		// set initial value
		try {

			LOG.trace("Getting PreferencesMethod for property class [" + item.getPropertyClass().getName() + "].");
			final PreferencesMethod<K> method = this.prefClassStore.get(item.getPropertyClass());
			
			final Object value = method.getValue(item.getKey(), item.getDefaultValue());
			LOG.trace("Preferences value for key [" + item.getKey() + "] returned value [" + value + "] " +
					"with default [" + item.getDefaultValue() + "].");
			
			
			item.getSetterMethod().invoke(item.getTarget(), value);
			
		} catch (final Exception e) { // IllegalArgumentException, IllegalAccessException, InvocationTargetException
			final String errMsg = "Could not set default value via setter method for [" + item + "]!";
			LOG.error(errMsg, e);
			throw new RuntimeException(errMsg, e);
		}
		
		this.items.add(item);
	}
	
	@Override
	boolean isValidPropertyClass(final Class<?> propertyClass) {
		return this.prefClassStore.keySet().contains(propertyClass);
	}

	
	
	
	/**
	 * 
	 * @author christoph_pickl@users.sourceforge.net
	 */
	private static class PreferencesMethod<T extends IPtMemoryKey<String>> {
		
		private final Preferences preferences;
		
		private final Method getMethod;
		
		private final Method setMethod;
		
		private PreferencesMethod(final Preferences preferences, final Method getMethod, final Method setMethod) {
			this.preferences = preferences;
			this.getMethod = getMethod;
			this.setMethod = setMethod;
		}
		
		public Object getValue(final T key, final Object defaultValue) {
			try {
				return this.getMethod.invoke(this.preferences, key.get(), defaultValue);
			} catch (Exception e) { // IllegalArgumentException, IllegalAccessException, InvocationTargetException
				final String errMsg = "Could not get value for method [" + this.getMethod.getName() + "]!";
				LOG.error(errMsg, e);
				throw new RuntimeException(errMsg, e);
			}
		}
		
		public void setValue(final T key, final Object value) {
			try {
				this.setMethod.invoke(this.preferences, key.get(), value);
			} catch (Exception e) { // IllegalArgumentException, IllegalAccessException, InvocationTargetException
				final String errMsg = "Could not set value for method [" + this.setMethod.getName() + "]!";
				LOG.error(errMsg, e);
				throw new RuntimeException(errMsg, e);
			}
		}
	}
	
}
