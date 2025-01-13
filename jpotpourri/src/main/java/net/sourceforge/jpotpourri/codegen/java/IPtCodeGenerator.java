package net.sourceforge.jpotpourri.codegen.java;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public interface IPtCodeGenerator {

	void process(final PtAbstractGenClass genClass) throws PtCodeGeneratorException;
	
}
