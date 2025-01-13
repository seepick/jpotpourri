package net.sourceforge.jpotpourri.jpotface.memory;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtStringKey implements IPtMemoryKey<String> {

	private final String key;
	
	public PtStringKey(final String key) {
		this.key = key;
	}
	
	public String get() {
		return this.key;
	}

}
