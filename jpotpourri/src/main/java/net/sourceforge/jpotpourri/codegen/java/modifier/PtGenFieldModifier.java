package net.sourceforge.jpotpourri.codegen.java.modifier;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtGenFieldModifier extends AbstractGenModifier {

	public static final PtGenFieldModifier STATIC = new PtGenFieldModifier("static ");
	public static final PtGenFieldModifier FINAL  = new PtGenFieldModifier("final ");
	
	private PtGenFieldModifier(final String code) {
		super(code);
	}
}
