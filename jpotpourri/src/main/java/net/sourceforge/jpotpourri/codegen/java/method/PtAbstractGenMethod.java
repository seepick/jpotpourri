package net.sourceforge.jpotpourri.codegen.java.method;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.jpotpourri.codegen.java.PtGenArgument;
import net.sourceforge.jpotpourri.codegen.java.PtGenVisibility;
import net.sourceforge.jpotpourri.codegen.java.modifier.PtGenMethodModifier;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public abstract class PtAbstractGenMethod extends AbstractGenPseudoMethod {

	public PtAbstractGenMethod(
			final PtGenVisibility visibility,
			final String methodName,
			final String returnType,
			final PtGenMethodModifier... modifiers
			) {
		super(visibility, methodName, returnType, new ArrayList<PtGenArgument>(0), modifiers);
		// nothing else to do
	}
	
	public PtAbstractGenMethod(
			final PtGenVisibility visibility,
			final String methodName,
			final String returnType,
			final List<PtGenArgument> arguments,
			final PtGenMethodModifier... modifiers
			) {
		super(visibility, methodName, returnType, arguments, modifiers);
		// nothing else to do
	}

	protected final String getReturnType() {
		return this.getUnsafeReturnType();
	}
	
}
