package net.sourceforge.jpotpourri.codegen.as.modifier;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtGenFieldModifier extends AbstractGenModifier {

	 public static final PtGenFieldModifier CONST = new PtGenFieldModifier("const ");
	
	
	private PtGenFieldModifier(final String code) {
		super(code);
	}
}
