package net.sourceforge.jpotpourri.codegen.java.method;

import net.sourceforge.jpotpourri.codegen.java.PtGenVisibility;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtPublicEmptyGenConstructor extends PtAbstractGenConstructor {

	public PtPublicEmptyGenConstructor(final String className) {
		super(PtGenVisibility.PUBLIC, className);
	}

	@Override
	protected final String getBody() {
		return "// nothing to do";
	}

}
