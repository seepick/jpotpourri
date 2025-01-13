package net.sourceforge.jpotpourri;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtException extends Exception {

	private static final long serialVersionUID = -7578161634836956750L;

	public PtException(final String msg) {
		super(msg);
	}
	
	public PtException(final String msg, final Exception cause) {
		super(msg, cause);
	}
	
}
