package net.sourceforge.jpotpourri.fprog.predsfuncs;

/**
 * @author christoph_pickl@users.sourceforge.net
 * @param <R> return type
 * @param <T> value type
 */
public interface IPtUnaryFunction<R, T> {

	R execute(final T t);
	
}
