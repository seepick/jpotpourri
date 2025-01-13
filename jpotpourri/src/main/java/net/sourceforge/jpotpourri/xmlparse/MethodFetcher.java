package net.sourceforge.jpotpourri.xmlparse;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @param <T> object type providing setter method
 * @param <S> type of setter argument
 * @author christoph_pickl@users.sourceforge.net
 */
public class MethodFetcher<T, S> {

	private static final Log LOG = LogFactory.getLog(MethodFetcher.class);
	
	private final List<Method> methods;
	
	public MethodFetcher(
			final Class<? extends T> targetClass,
			final Class<S> setterClass,
			final String setterMethodName
		) {
		try {
			final String[] methodNames = setterMethodName.split("\\.");
			Class<?> tmpClass = targetClass;
			this.methods = new ArrayList<Method>(methodNames.length);
			for (int i = 0; i < methodNames.length; i++) {
				final String methodName = methodNames[i];
				
				final Class<?>[] methodArgs;
				if(i == (methodNames.length - 1)) { // last one, setter
					methodArgs = new Class<?>[] { setterClass };
				} else {
					methodArgs = new Class<?>[0];
				}
				
				LOG.trace("Getting method by name [" + methodName + "] " +
						"and with args " + Arrays.toString(methodArgs) + " " +
						"for class [" + tmpClass.getSimpleName() + "].");
				final Method method = tmpClass.getMethod(methodName, methodArgs);
				this.methods.add(method);
				tmpClass = method.getReturnType();
			}
		} catch (Exception e) {
			throw new RuntimeException("Could not construct method path [" + setterMethodName + "] " +
					"for class [" + targetClass.getName() + "]!", e);
		}
	}
	
	public void invoke(final T target, final Object value) {
		try {
			Object targetObject = target;
			for (int i = 0, n = this.methods.size(); i < n; i++) {
				final Method method = this.methods.get(i);

				if(i == (this.methods.size() - 1)) { // last one, setter
					method.invoke(targetObject, value);
				} else {
					targetObject = method.invoke(targetObject);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Could not invoke method path " +
					"for class [" + target.getClass().getName() + "]!", e);
		}
	}
}
