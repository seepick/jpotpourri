package net.sourceforge.jpotpourri.codegen.as.modifier;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtGenMethodModifier extends AbstractGenModifier {

	public static final PtGenMethodModifier OVERRIDEN   = new PtGenMethodModifier("overridden ");
	public static final PtGenMethodModifier STATIC    = new PtGenMethodModifier("static ");
	
	private PtGenMethodModifier(final String code) {
		super(code);
	}
}
