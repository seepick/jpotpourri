package net.sourceforge.jpotpourri.codegen.java.method;

import java.util.Arrays;
import java.util.List;

import net.sourceforge.jpotpourri.codegen.java.PtGenArgument;
import net.sourceforge.jpotpourri.codegen.java.PtGenVisibility;
import net.sourceforge.jpotpourri.codegen.java.modifier.PtGenMethodModifier;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public abstract class PtAbstractAbstractGenMethod extends PtAbstractGenMethod {

	public PtAbstractAbstractGenMethod(
			final PtGenVisibility visibility,
			final String methodName,
			final String returnType,
			final List<PtGenArgument> arguments
			) {
		super(visibility, methodName, returnType, arguments, PtGenMethodModifier.ABSTRACT);
	}
	public PtAbstractAbstractGenMethod(
			final PtGenVisibility visibility,
			final String methodName,
			final String returnType,
			final PtGenArgument... arguments
			) {
		this(visibility, methodName, returnType, Arrays.asList(arguments));
	}

	@Override
	protected final String getBody() {
		return null;
	}

}
