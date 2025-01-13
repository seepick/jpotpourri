package net.sourceforge.jpotpourri.codegen.as;

import java.util.List;

public class PtGenInterfaceFunction extends PtGenFunction {
/*
extends PtGenFunction
  + visibility (interfaceFunction got default visibility by default)
*/
	public PtGenInterfaceFunction(String functionName,
			List<PtGenArgument> arguments, String returnType) {
		super(PtGenVisibility.DEFAULT, functionName, arguments, returnType);
	}
}
