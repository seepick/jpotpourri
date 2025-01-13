package net.sourceforge.jpotpourri.jpotspect.notnull;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtNotNullTest {

	@Test
	public void testSingleNotNull() {
		final Person person = new Person();
		
		person.setName("success");

		try {
			person.setName(null);
			fail();
		} catch (NullPointerException e) {
			assertNotNull(e.getMessage());
		}
	}

	@Test
	public void testWithinNotNull() {
		final Person person = new Person();
		
		person.setProperties(new Object(), new Object(), new Object());
		person.setProperties(null, new Object(), null);

		try {
			person.setProperties(new Object(), null, new Object());
			fail();
		} catch (NullPointerException e) {
			assertNotNull(e.getMessage());
		}
	}
	
	
	/**
	 * @author christoph_pickl@users.sourceforge.net
	 */
	private static class Person {

		public void setName(
				@SuppressWarnings("unused")
				@PtNotNull final String name) {
			// nothing to do
		}

		public void setProperties(
				@SuppressWarnings("unused")
				final Object property1,
				@SuppressWarnings("unused")
				@PtNotNull final Object property2,
				@SuppressWarnings("unused")
				final Object property3) {
			// nothing to do
		}
		
	}
}
