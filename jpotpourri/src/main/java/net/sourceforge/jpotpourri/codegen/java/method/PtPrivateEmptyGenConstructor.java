package net.sourceforge.jpotpourri.codegen.java.method;

import net.sourceforge.jpotpourri.codegen.java.PtGenVisibility;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtPrivateEmptyGenConstructor extends PtAbstractGenConstructor {

	public PtPrivateEmptyGenConstructor(final String className) {
		super(PtGenVisibility.PRIVATE, className);
	}

	@Override
	protected final String getBody() {
		return "// no instantiation (singleton/utility)";
	}

}
