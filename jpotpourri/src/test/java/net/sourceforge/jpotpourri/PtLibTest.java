package net.sourceforge.jpotpourri;

import net.sourceforge.jpotpourri.util.PtUtilTestSuite;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtLibTest extends TestCase {
	
	public PtLibTest() {
		super("Global Test Suite");
	}

	public static Test suite() {
		final TestSuite suite = new TestSuite();
		
		suite.addTestSuite(PtUtilTestSuite.class);
		
		return suite;
	}

	public void testApp() {
		assertTrue(true);
	}
}
