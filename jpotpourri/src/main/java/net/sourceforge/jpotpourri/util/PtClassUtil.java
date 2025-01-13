package net.sourceforge.jpotpourri.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtClassUtil {
	/*
	private static final Set<Class<?>> ARRAY_CLASSES_FOR_PRIMITIVES;
	static {
		final Set<Class<?>> tmp = new HashSet<Class<?>>();
		tmp.add(byte[].class);
		tmp.add(char[].class);
		tmp.add(short[].class);
		tmp.add(int[].class);
		tmp.add(long[].class);
		tmp.add(float[].class);
		tmp.add(double[].class);
		tmp.add(boolean[].class);
		ARRAY_CLASSES_FOR_PRIMITIVES = Collections.unmodifiableSet(tmp);
	}
	*/
	
	private static final Set<ArrayClass> ARRAY_CLASSES_FOR_PRIMITIVES;
	static {
		final Set<ArrayClass> tmp = new HashSet<ArrayClass>();
		tmp.add(ArrayClass.BYTE);
		tmp.add(ArrayClass.CHAR);
		tmp.add(ArrayClass.SHORT);
		tmp.add(ArrayClass.INT);
		tmp.add(ArrayClass.LONG);
		tmp.add(ArrayClass.FLOAT);
		tmp.add(ArrayClass.DOUBLE);
		tmp.add(ArrayClass.BOOLEAN);
		ARRAY_CLASSES_FOR_PRIMITIVES = Collections.unmodifiableSet(tmp);
	}
	
	private static final Map<Class<?>, Class<?>> COMPLEX_TO_PRIMITIVE_MAP;
	static {
		final Map<Class<?>, Class<?>> tmp = new HashMap<Class<?>, Class<?>>();

		tmp.put(Boolean.class, boolean.class);
		tmp.put(Integer.class, int.class);
		tmp.put(Long.class,    long.class);
		tmp.put(Float.class,   float.class);
		tmp.put(Double.class,  double.class);
		
		COMPLEX_TO_PRIMITIVE_MAP = Collections.unmodifiableMap(tmp);
	}
	
	
	private PtClassUtil() {
		// no instantiation
	}

	public static void main(String[] args) {
		Object o1 = new int[] {3, 3};
		Object o2 = new int[] {3, 3};
	}
	
	public static int hashCodeOfArray(final Object object) {
		if(object.getClass().isArray() == false) {
			throw new IllegalArgumentException("Given object is not an array but: " +
					"[" + object.getClass().getName() + "]!");
		}
		
		for (ArrayClass clazz : ARRAY_CLASSES_FOR_PRIMITIVES) {
			if(clazz.isAssignableTo(object)) {
				return clazz.calcHashCode(object);
			}
		}
		
		return Arrays.hashCode((Object[]) object);
	}
	
	public static boolean areArraysEquals(final Object o1, final Object o2) {
		if(o1 == null || o2 == null) {
			throw new NullPointerException((o1 == null ? "First" : "Second") + 
					" argument was null!");
		}
		if(o1.getClass().isArray() == false || o2.getClass().isArray() == false) {
			throw new IllegalArgumentException((o1.getClass().isArray() == false ? "First" : "Second") +
					" argument is not an array but [" + (o1.getClass().isArray() == false ? 
							o1.getClass().getName() : o2.getClass().getName()) + "]!");
		}
		
		if(o1.getClass() != o2.getClass()) {
			throw new IllegalArgumentException("Argument types do not match: " +
					"[" + o1.getClass().getName() + "] vs [" + o2.getClass().getName() + "]!");
		}
		
		for (ArrayClass clazz : ARRAY_CLASSES_FOR_PRIMITIVES) {
			if(clazz.isAssignableTo(o1)) {
				return clazz.isUnsafeArrayEquals(o1, o2);
			}
		}
		
		return Arrays.equals((Object[]) o1, (Object[]) o2);
	}
	

	public static Class<?> convertToPrimitiveClass(final Class<?> complexClass) {
		if(COMPLEX_TO_PRIMITIVE_MAP.containsKey(complexClass) == false) {
			throw new IllegalArgumentException("Class [" + complexClass.getName() + "] got no little brother!");
		}
		return COMPLEX_TO_PRIMITIVE_MAP.get(complexClass);
	}
	
	public static boolean areClassesPrimitiveAndComplex(final Class<?> clazz1, final Class<?> clazz2) {
		if((clazz1.isPrimitive() ^ clazz2.isPrimitive()) == false) {
			return false;
		}
		
		final Class<?> primitiveClass = (clazz1.isPrimitive() ? clazz1 : clazz2);
		final Class<?> complexClass =   (clazz1.isPrimitive() ? clazz2 : clazz1);
		
		return COMPLEX_TO_PRIMITIVE_MAP.get(complexClass) == primitiveClass;
	}
	
	
	


	/**
	 * 
	 * @author christoph_pickl@users.sourceforge.net
	 */
	private abstract static class ArrayClass {

		public static final ArrayClass BYTE = new ArrayClass(byte[].class) {
			@Override public int calcHashCode(final Object o) {
				return Arrays.hashCode((byte[]) o); }
			@Override boolean isUnsafeArrayEquals(final Object o1, final Object o2) {
				return Arrays.equals((byte[]) o1, (byte[]) o2); } };
				
		public static final ArrayClass CHAR = new ArrayClass(char[].class) {
			@Override public int calcHashCode(final Object o) {
				return Arrays.hashCode((char[]) o); }
			@Override boolean isUnsafeArrayEquals(final Object o1, final Object o2) {
				return Arrays.equals((char[]) o1, (char[]) o2); } };
			
		public static final ArrayClass SHORT = new ArrayClass(short[].class) {
			@Override public int calcHashCode(final Object o) {
				return Arrays.hashCode((short[]) o); }
			@Override boolean isUnsafeArrayEquals(final Object o1, final Object o2) {
				return Arrays.equals((short[]) o1, (short[]) o2); } };
			
		public static final ArrayClass INT = new ArrayClass(int[].class) {
			@Override public int calcHashCode(final Object o) {
				return Arrays.hashCode((int[]) o); }
			@Override boolean isUnsafeArrayEquals(final Object o1, final Object o2) {
				return Arrays.equals((int[]) o1, (int[]) o2); } };
			
		public static final ArrayClass LONG = new ArrayClass(long[].class) {
			@Override public int calcHashCode(final Object o) {
				return Arrays.hashCode((long[]) o); }
			@Override boolean isUnsafeArrayEquals(final Object o1, final Object o2) {
				return Arrays.equals((long[]) o1, (long[]) o2); } };
			
		public static final ArrayClass FLOAT = new ArrayClass(float[].class) {
			@Override public int calcHashCode(final Object o) {
				return Arrays.hashCode((float[]) o); }
			@Override boolean isUnsafeArrayEquals(final Object o1, final Object o2) {
				return Arrays.equals((float[]) o1, (float[]) o2); } };
			
		public static final ArrayClass DOUBLE = new ArrayClass(double[].class) {
			@Override public int calcHashCode(final Object o) {
				return Arrays.hashCode((double[]) o); }
			@Override boolean isUnsafeArrayEquals(final Object o1, final Object o2) {
				return Arrays.equals((double[]) o1, (double[]) o2); } };
			
		public static final ArrayClass BOOLEAN = new ArrayClass(boolean[].class) {
			@Override public int calcHashCode(final Object o) {
				return Arrays.hashCode((boolean[]) o); }
			@Override boolean isUnsafeArrayEquals(final Object o1, final Object o2) {
				return Arrays.equals((boolean[]) o1, (boolean[]) o2); } };
		
		private final Class<?> clazz;
		
		public abstract int calcHashCode(final Object o);

		/**
		 * 
		 * @param o1 has to be of the same (dynamic) type as o2
		 * @param o2 has to be of the same (dynamic) type as o1
		 * @return true, if both arguments are of the same type and Arrays.equals() returns true
		 */
		abstract boolean isUnsafeArrayEquals(final Object o1, final Object o2);
		
		
		private ArrayClass(final Class<?> clazz) {
			this.clazz = clazz;
		}
		
		public final boolean isAssignableTo(final Object o) {
			return o.getClass().isAssignableFrom(this.clazz);
		}
	}
}
