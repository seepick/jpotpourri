package net.sourceforge.jpotpourri.codegen.java.method;

import java.util.LinkedList;
import java.util.List;

import net.sourceforge.jpotpourri.codegen.java.IPtAnnotationable;
import net.sourceforge.jpotpourri.codegen.java.IPtJavaCode;
import net.sourceforge.jpotpourri.codegen.java.PtCodeUtil;
import net.sourceforge.jpotpourri.codegen.java.PtGenArgument;
import net.sourceforge.jpotpourri.codegen.java.PtGenVisibility;
import net.sourceforge.jpotpourri.codegen.java.modifier.PtGenMethodModifier;

/**
 * Used by real method generator AND constructor, as it is a "pseduo" method.
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
abstract class AbstractGenPseudoMethod implements IPtJavaCode, IPtAnnotationable {

	// method&constructor common
	private final boolean isForMethod; // and not for constructor
	private final PtGenVisibility visibility;
	private final List<PtGenArgument> arguments;
	private final List<String> annotations = new LinkedList<String>();

	
	// method specific
	private final String methodName;
	private final String returnType;
	private final PtGenMethodModifier[] modifiers;
	
	
	// constructor specific
	private final String className;
	

	
	
	
	/** Constructor for method class. */
	AbstractGenPseudoMethod(final PtGenVisibility visibility, final String methodName,
			final String returnType, final List<PtGenArgument> arguments, final PtGenMethodModifier... modifiers) {
		if(visibility == null) {
			throw new NullPointerException("visibility");
		}
		this.visibility = visibility;
		this.methodName = methodName;
		this.returnType = returnType;
		this.arguments = arguments;
		this.modifiers = modifiers;
		
		// for constructor
		this.isForMethod = true;
		this.className = null;
	}

	/** Constructor for constructor class. */
	AbstractGenPseudoMethod(final PtGenVisibility visibility, final String className,
			final List<PtGenArgument> arguments) {
		if(visibility == null) {
			throw new NullPointerException("visibility");
		}
		if(className == null) {
			throw new NullPointerException("className");
		}
		this.visibility = visibility;
		this.className = className;
		this.arguments = arguments;
		
		// for method
		this.isForMethod = false;
		this.methodName = null;
		this.returnType = null;
		this.modifiers = null;
	}
	
	/**
	 * @return no need of manual indentation ("\t"); will be done automatically
	 */
	protected abstract String getBody();

	final String getUnsafeReturnType() {
		return this.returnType;
	}
	
	public final String toCode() {
		if(this.isForMethod == false && this instanceof PtNullGenConstructor) {
			return "";
		}
		
		final StringBuilder sb = new StringBuilder();
		
		for (String annotation : this.annotations) {
			sb.append("\t@").append(annotation).append("\n");
		}
		
		sb.append("\t").append(this.visibility.toCode());
		
		if(this.isForMethod) {
			for (PtGenMethodModifier modifier : this.modifiers) {
				sb.append(modifier.toCode());
			}
			
			sb.append(this.returnType).append(" ");
		}
		
		if(this.isForMethod) {
			sb.append(this.methodName);
		} else {
			sb.append(this.className);
		}
		
		sb.append("(");
		boolean first = true;
		for (PtGenArgument arg : this.arguments) {
			if(first) {
				first = false;
			} else {
				sb.append(", ");
			}
			sb.append(arg.toCode());
		}
		
		sb.append(")");
		
		if(this instanceof PtAbstractAbstractGenMethod) {
			
			sb.append(";");
			
		} else {
			
			sb.append(" {"); // TODO add possible exceptions for GenMethod
	
			sb.append("\n");
			
			sb.append(PtCodeUtil.autoIndentCode(this.getBody(), 2));
			
			sb.append("\t}");
		}
		sb.append("\n");
		sb.append("\n");
		
		return sb.toString();
	}
	
	public final void addAnnotation(final String textAfterAt) {
		this.annotations.add(textAfterAt);
	}
	
	final PtGenVisibility getVisibility() {
		return this.visibility;
	}
	
	final List<PtGenArgument> getArguments() {
		return this.arguments;
	}
}
