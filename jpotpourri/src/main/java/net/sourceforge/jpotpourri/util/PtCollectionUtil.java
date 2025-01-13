package net.sourceforge.jpotpourri.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author christoph_pickl@users.sourceforge.net
 * @param <T> concrete collection type
 */
public class PtCollectionUtil<T> { // TODO instead of generic class, use generic static methods

    private static final String DEFAULT_ELEMENT_SEPARATOR = ";";
    
    public PtCollectionUtil() {
        // instantiation used to replace type parameter T
    }
    
    public final Collection<T> asCollection(final T... values) {
        final Collection<T> result = new HashSet<T>(values.length);
        
        for (T value : values) {
            result.add(value);
        }
        
        return result;
    }
    
    public final Set<T> asSet(final T... values) {
        final Set<T> result = new HashSet<T>(values.length);
        
        for (T value : values) {
            result.add(value);
        }
        
        return result;
    }
    
    public final List<T> asList(final T... values) {
        final List<T> result = new ArrayList<T>(values.length);
        
        for (T value : values) {
            result.add(value);
        }
        
        return result;
    }
    
    public final List<T> asImmutableList(final T... values) {
        return Collections.unmodifiableList(this.asList(values));
    }
    


    public static final Collection<String> asStringCollection(final String... values) {
        return new PtCollectionUtil<String>().asCollection(values);
    }
    
    public static final Set<String> asStringSet(final String... values) {
        return new PtCollectionUtil<String>().asSet(values);
    }
    
    public static final String[] asArray(final String... values) {
        return values;
    }
    
    public static final String toString(final Collection<?> collection) {
        if(collection.size() == 0) {
        	return "";
        }
        
        StringBuilder sb = new StringBuilder();
        for (Object object : collection) {
            sb.append(", ").append(object.toString());
        }
        return sb.substring(2);
    }
    
    

    public static final Set<String> immutableSet(final Set<String> original) {
        return Collections.unmodifiableSet(new HashSet<String>(original));
    }

    public static final Set<String> immutableSet(final String... values) {
        return Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(values)));
    }

    public static final List<String> immutableList(final String... values) {
        return Collections.unmodifiableList(new ArrayList<String>(Arrays.asList(values)));
    }

    public static final String toString(final Set<?> set) {
    	return toString(set, DEFAULT_ELEMENT_SEPARATOR);
    }
    
    public static final String toString(final Set<?> set, final String separator) {
		if(set == null) {
			return "null";
		}

        final StringBuilder sb = new StringBuilder();
		sb.append("Set[");
		
        boolean first = true;
        for (final Object element : set) {
        	if(first == true) {
        		first = false;
        	} else {
        		sb.append(separator);
        	}
            sb.append(String.valueOf(element));
        }
        
		sb.append("]");
        return sb.toString();
    }

	public static String toString(final Map<?, ?> map) {
		if(map == null) {
			return "null";
		}
		final StringBuilder sb = new StringBuilder();
		
		boolean first = true;
		sb.append("Map[");
		for (Object key : map.keySet()) {
			if(first == true) {
				first = false;
			} else {
				sb.append(";");
			}
			sb.append(String.valueOf(key));
			sb.append("=");
			sb.append(String.valueOf(map.get(key)));
			
		}
		sb.append("]");
		
		return sb.toString();
	}
    
}
