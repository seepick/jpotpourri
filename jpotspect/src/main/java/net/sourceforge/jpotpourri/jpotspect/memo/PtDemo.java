package net.sourceforge.jpotpourri.jpotspect.memo;

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
		System.out.println("Result of: 2 + 2 = " + this.add(2, 2));
		System.out.println("Result of: 2 + 3 = " + this.add(2, 3));
		System.out.println("Result of: 2 + 2 = " + this.add(2, 2)); // will use cached value
	}
	
	@PtMemorizable
	private int add(final int operand1, final int operand2) {
		System.out.println("... computing add(" + operand1 + ", " + operand2 + ")");
		
		try {
			// simulate cpu processing
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return operand1 + operand2;
	}
	
}
