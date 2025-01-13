package net.sourceforge.jpotpourri.codegen.java;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtCodeGeneratorException extends Exception {

	private static final long serialVersionUID = 2661065520604650162L;

	public PtCodeGeneratorException(final String msg) {
		super(msg);
	}

	public PtCodeGeneratorException(final String msg, final Exception cause) {
		super(msg, cause);
	}
	
}
