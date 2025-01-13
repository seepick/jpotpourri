package net.sourceforge.jpotpourri.fprog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sourceforge.jpotpourri.fprog.predsfuncs.IPtBinaryFunction;
import net.sourceforge.jpotpourri.fprog.predsfuncs.IPtUnaryFunction;
import net.sourceforge.jpotpourri.fprog.predsfuncs.IPtUnaryPredicate;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtFprog {

	private PtFprog() {
		// no instantiation
	}
	
	public static<R, T> List<R> map(final IPtUnaryFunction<R, T> function, final List<T> list) {
		final List<R> result = new ArrayList<R>(list.size());
		for (final T element : list) {
			result.add(function.execute(element));
		}
		return Collections.unmodifiableList(result);
	}
	
	public static<T> List<T> filter(final IPtUnaryPredicate<T> predicate, final List<T> list) {
		final List<T> result = new ArrayList<T>(list.size());
		for (final T element : list) {
			if(predicate.execute(element)) {
				result.add(element);
			}
		}
		return Collections.unmodifiableList(result);
	}
	
	public static<T> T reduce(final IPtBinaryFunction<T, T> function, final List<T> list, final T initialValue) {
		T result = initialValue;
		for (final T element : list) {
			result = function.execute(result, element);
		}
		return result;
	}
	
}
