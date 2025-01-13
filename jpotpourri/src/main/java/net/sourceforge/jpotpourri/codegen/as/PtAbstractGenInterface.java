package net.sourceforge.jpotpourri.codegen.as;

import java.util.LinkedList;
import java.util.List;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public abstract class PtAbstractGenInterface implements IPtAsCode {

	private final String interfaceName;
	
	private final String packageName;
	
	private final List<PtGenInterfaceFunction> functions = new LinkedList<PtGenInterfaceFunction>();
	
	public PtAbstractGenInterface(String interfaceName, String packageName) {
		this.interfaceName = interfaceName;
		this.packageName = packageName;
	}

	public void addFunction(PtGenInterfaceFunction function) {
		this.functions.add(function);
	}
	
	public String getInterfaceName() {
		return this.interfaceName;
	}
	
	public String getPackageName() {
		return this.packageName;
	}

	public String toCode() {
		final StringBuilder sb = new StringBuilder();
		
		sb.append(this.packageName).append(" {\n");
		
		sb.append("\n");
		// TODO imports
		sb.append("// imports \n");
		sb.append("\n");
		
		sb.append("public interface ").append(this.interfaceName).append(" {\n");
		sb.append("\n");
		
		for (PtGenInterfaceFunction function : this.functions) {
			sb.append("\t").append(function.toCode()).append("\n");
			sb.append("\n");
		}
		
		sb.append("}\n"); // endof interface
		sb.append("\n");

		sb.append("}"); // endof package
		
		return sb.toString();
	}

}
