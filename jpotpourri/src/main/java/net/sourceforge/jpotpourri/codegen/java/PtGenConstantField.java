package net.sourceforge.jpotpourri.codegen.java;

import net.sourceforge.jpotpourri.codegen.java.modifier.PtGenFieldModifier;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtGenConstantField extends PtGenField {

	private PtGenConstantField(final PtGenVisibility visibility, final String fieldType, final String fieldName,
			final String initValue, final PtGenFieldModifier... modifiers) {
		super(visibility, fieldType, fieldName, initValue, modifiers);
	}
	
	public static PtGenConstantField newPublicConstant(final String fieldType, final String fieldName, 
			final String initValue) {
		return new PtGenConstantField(PtGenVisibility.PUBLIC, fieldType, fieldName, initValue,
				PtGenFieldModifier.STATIC, PtGenFieldModifier.FINAL);
	}
	
	public static PtGenConstantField newPrivateConstant(final String fieldType, final String fieldName, 
			final String initValue) {
		return new PtGenConstantField(PtGenVisibility.PRIVATE, fieldType, fieldName, initValue,
				PtGenFieldModifier.STATIC, PtGenFieldModifier.FINAL);
	}
}
