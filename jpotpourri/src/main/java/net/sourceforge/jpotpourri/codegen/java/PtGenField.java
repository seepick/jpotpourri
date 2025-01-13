package net.sourceforge.jpotpourri.codegen.java;

import net.sourceforge.jpotpourri.codegen.java.modifier.PtGenFieldModifier;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtGenField implements IPtJavaCode {
	
	private final PtGenVisibility visibility;
	private final String fieldType;
	private final String fieldName;
	/** eg: "new SomeClass()". */
	private final String initValue;
	private final PtGenFieldModifier[] modifiers;


	public PtGenField(final PtGenVisibility visibility, final String fieldType, final String fieldName,
			final PtGenFieldModifier... modifiers) {
		this(visibility, fieldType, fieldName, null, modifiers);
	}
	public PtGenField(final PtGenVisibility visibility, final String fieldType, final String fieldName,
			final String initValue, final PtGenFieldModifier... modifiers) {
		this.visibility = visibility;
		this.fieldType = fieldType;
		this.fieldName = fieldName;
		this.initValue = initValue;
		this.modifiers = modifiers;
	}

	public final String toCode() {
		final StringBuilder sb = new StringBuilder();
		
		sb.append("\t").append(this.visibility.toCode());
		
		for (PtGenFieldModifier modifier : this.modifiers) {
			sb.append(modifier.toCode());
		}
		
		sb.append(this.fieldType).append(" ");
		
		sb.append(this.fieldName);
		
		if(this.initValue != null) {
			sb.append(" = ").append(this.initValue);
		}
		
		sb.append(";\n");
		
		return sb.toString();
	}

}
