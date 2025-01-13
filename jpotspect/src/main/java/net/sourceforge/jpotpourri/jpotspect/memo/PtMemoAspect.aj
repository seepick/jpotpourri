package net.sourceforge.jpotpourri.jpotspect.memo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public aspect PtMemoAspect {

	private static final Log LOG = LogFactory.getLog(PtMemoAspect.class);

	private final Map<String, Map<PtArgumentWrapper, Object>> data = new HashMap<String, Map<PtArgumentWrapper,Object>>();
	
	
	pointcut memoMethod() :
		call(@net.sourceforge.jpotpourri.jpotspect.memo.PtMemorizable * *.*(**, ..)); // at least one

	
	@SuppressAjWarnings
	Object around() : memoMethod() {
		final String methodKey = createMethodKey((MethodSignature) thisJoinPoint.getSignature());
		LOG.trace("methodKey=[" + methodKey + "] params=" + Arrays.toString(thisJoinPoint.getArgs()));
		
		if(this.data.get(methodKey) == null) {
			this.data.put(methodKey, new HashMap<PtArgumentWrapper, Object>());
		}
		
		final PtArgumentWrapper args = new PtArgumentWrapper(thisJoinPoint.getArgs());
		LOG.trace("ArgumentWrapper.hashCode = " + args.hashCode());
		final Map<PtArgumentWrapper, Object> memo = this.data.get(methodKey);
		
		
		if(memo.containsKey(args) == true) {
			LOG.debug("Returning stored result.");
			return memo.get(args);
		}
		
		LOG.debug("Calculating for first time.");
		Object newResult = proceed();
		memo.put(args, newResult);
		return newResult;
	}
	
	/**
	 * @param signature of the method
	 * @return a unique string for the given method (even if its overloaded)
	 */
	private static String createMethodKey(final MethodSignature signature) {
		final StringBuilder params = new StringBuilder();
		for (Class<?> paramType : signature.getParameterTypes()) {
			params.append(",").append(paramType.getName());
		}
		
		final String className = signature.getDeclaringTypeName();
		final String methodName = signature.getName();
		return className + "." + methodName + "(" + params.substring(2) + ")";
	}
	
	
}
