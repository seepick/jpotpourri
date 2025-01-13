package net.sourceforge.jpotpourri.fprog.predsfuncs;

import java.util.List;

/**
 * @author christoph_pickl@users.sourceforge.net
 * @param <T> value type
 */
public interface IPtNaryPredicate<T> {
	
	boolean execute(final T... t);

	boolean execute(final List<T> t);
	
}
