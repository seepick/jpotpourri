package net.sourceforge.jpotpourri.codegen.java;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtManClassDefinition {
	
	private String packageName = null;
	
	
	public PtManClassDefinition() {
		// nothing to do
	}
	
	
	public void setPackageName(final String packageName) {
		this.packageName = packageName;
	}
	
	public String getPackageName() {
		return this.packageName;
	}
}
