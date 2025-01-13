package net.sourceforge.jpotpourri.codegen.java.modifier;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtGenClassModifier extends AbstractGenModifier {

	public static final PtGenClassModifier STATIC   = new PtGenClassModifier("static ");
	public static final PtGenClassModifier FINAL    = new PtGenClassModifier("final ");
	public static final PtGenClassModifier ABSTRACT = new PtGenClassModifier("abstract ");
	
	private PtGenClassModifier(final String code) {
		super(code);
	}
}

