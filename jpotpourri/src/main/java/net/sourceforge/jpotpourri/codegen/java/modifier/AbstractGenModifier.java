package net.sourceforge.jpotpourri.codegen.java.modifier;

import net.sourceforge.jpotpourri.codegen.java.IPtJavaCode;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
abstract class AbstractGenModifier implements IPtJavaCode {

	private final String code;
	
	
	AbstractGenModifier(final String code) {
		this.code = code;
	}
	
	public final String toCode() {
		return this.code;
	}

	@Override
	public final String toString() {
		return this.code;
	}
	
}
