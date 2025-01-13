package net.sourceforge.jpotpourri.codegen.java;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtGenVisibility implements IPtJavaCode {
	
	public static final PtGenVisibility PUBLIC = new PtGenVisibility("public ");
	public static final PtGenVisibility PROTECTED = new PtGenVisibility("protected ");
	public static final PtGenVisibility DEFAULT = new PtGenVisibility("");
	public static final PtGenVisibility PRIVATE = new PtGenVisibility("private ");
	
	private final String code;
	
	
	private PtGenVisibility(final String code) {
		this.code = code;
	}
	
	public String toCode() {
		return this.code;
	}

	@Override
	public String toString() {
		return this.code;
	}
}
