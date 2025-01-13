package net.sourceforge.jpotpourri.util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtUtilTestSuite extends TestCase {
	
	public PtUtilTestSuite() {
		super("Util Test Suite");
	}

	public static Test suite() {
		final TestSuite suite = new TestSuite();
		
		suite.addTestSuite(PtCloseUtilTest.class);
		suite.addTestSuite(PtCollectionUtilTest.class);
		
		return suite;
	}

	public void testApp() {
		assertTrue(true);
	}
}
