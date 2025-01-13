package net.sourceforge.jpotpourri.jpotspect.notnull;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
class PtDemo {

	public static void main(final String[] args) {
		new PtDemo().run();
	}

	private PtDemo() {
		// nothing to do
	}
	
	private void run() {
		this.setSomeNull("not null");
		
		try {
			this.setSomeNull(null);
		} catch (NullPointerException e) {
			System.out.println("NullPointerException was thrown");
		}
	}
	
	public void setSomeNull(@PtNotNull final String value) {
		System.out.println("setting value to [" + value + "]");
	}
	
}
