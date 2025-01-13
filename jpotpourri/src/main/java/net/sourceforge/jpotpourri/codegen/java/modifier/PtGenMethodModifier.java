package net.sourceforge.jpotpourri.codegen.java.modifier;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtGenMethodModifier extends AbstractGenModifier {

	public static final PtGenMethodModifier STATIC   = new PtGenMethodModifier("static ");
	public static final PtGenMethodModifier FINAL    = new PtGenMethodModifier("final ");
	public static final PtGenMethodModifier ABSTRACT = new PtGenMethodModifier("abstract ");
	
	private PtGenMethodModifier(final String code) {
		super(code);
	}
}
