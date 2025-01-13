package net.sourceforge.jpotpourri.fprog.predsfuncs;

import java.util.List;

/**
 * @author christoph_pickl@users.sourceforge.net
 * @param <R> return type
 * @param <T> value type
 */
public interface IPtNaryFunction<R, T> {

	R execute(final T... t);

	R execute(final List<T> t);
	
}
