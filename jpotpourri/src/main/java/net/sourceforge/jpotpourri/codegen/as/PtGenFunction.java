package net.sourceforge.jpotpourri.codegen.as;

import java.util.List;

public class PtGenFunction implements IPtAsCode {

	private final PtGenVisibility visibility;
	
	private final String functionName;
	
	private final List<PtGenArgument> arguments;
	
	private final String returnType;

	public PtGenFunction(PtGenVisibility visibility, String functionName,
			List<PtGenArgument> arguments, String returnType) {
		this.visibility = visibility;
		this.functionName = functionName;
		this.arguments = arguments;
		this.returnType = returnType;
	}

	public final String toCode() {
		final StringBuilder sb = new StringBuilder();
		
		sb.append(this.visibility.toCode()).append(this.functionName);
		sb.append("(");
		boolean first = true;
		for (PtGenArgument argument : this.arguments) {
			if(first) first = false;
			else sb.append(", ");
			sb.append(argument.toCode());
		}
		sb.append("):");
		sb.append(this.returnType).append(";"); // TODO not for non-abstract functions!
		
		return sb.toString();
	}

	public PtGenVisibility getVisibility() {
		return this.visibility;
	}

	public String getFunctionName() {
		return this.functionName;
	}

	public List<PtGenArgument> getArguments() {
		return this.arguments;
	}

	public String getReturnType() {
		return this.returnType;
	}
	
}
