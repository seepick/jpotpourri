package net.sourceforge.jpotpourri.codegen.as.modifier;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtGenClassModifier extends AbstractGenModifier {

	public static final PtGenClassModifier DYNAMIC   = new PtGenClassModifier("dynamic ");
	public static final PtGenClassModifier FINAL    = new PtGenClassModifier("final ");
	
	private PtGenClassModifier(final String code) {
		super(code);
	}
}

