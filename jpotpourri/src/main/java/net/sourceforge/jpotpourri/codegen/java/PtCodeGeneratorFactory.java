package net.sourceforge.jpotpourri.codegen.java;

import java.io.File;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtCodeGeneratorFactory {

	private PtCodeGeneratorFactory() {
		// no instantiation
	}
	
	public static IPtCodeGenerator newCodeGenerator(final File sourceFolder) {
		return new CodeGenerator(sourceFolder);
	}
}
