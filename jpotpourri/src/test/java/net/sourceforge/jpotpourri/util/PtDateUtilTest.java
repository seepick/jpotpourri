package net.sourceforge.jpotpourri.util;

import static net.sourceforge.jpotpourri.util.PtDateUtil.*;

/**
 * @author christoph.pickl@bmi.gv.at
 */
public class PtDateUtilTest extends PtAbstractUtilTestCase {

	public void testValidateTimestampPart() {

		assertTrue(validateTimestampPart(""));
		assertTrue(validateTimestampPart("2"));
		assertTrue(validateTimestampPart("20"));
		assertTrue(validateTimestampPart("200"));
		assertTrue(validateTimestampPart("2008"));
		assertTrue(validateTimestampPart("2008-"));
		assertTrue(validateTimestampPart("2008-1"));
		assertTrue(validateTimestampPart("2008-12"));
		assertTrue(validateTimestampPart("2008-12-"));
		assertTrue(validateTimestampPart("2008-12-3"));
		assertTrue(validateTimestampPart("2008-12-31"));
		assertTrue(validateTimestampPart("2008-12-31 "));
		assertTrue(validateTimestampPart("2008-12-31 1"));
		assertTrue(validateTimestampPart("2008-12-31 12"));
		assertTrue(validateTimestampPart("2008-12-31 12:"));
		assertTrue(validateTimestampPart("2008-12-31 12:5"));
		assertTrue(validateTimestampPart("2008-12-31 12:58"));
		assertTrue(validateTimestampPart("2008-12-31 12:58:"));
		assertTrue(validateTimestampPart("2008-12-31 12:58:5"));
		assertTrue(validateTimestampPart("2008-12-31 12:58:59"));
		assertTrue(validateTimestampPart("2008-12-31 12:58:59."));
		assertTrue(validateTimestampPart("2008-12-31 12:58:59.1"));
		assertTrue(validateTimestampPart("2008-12-31 12:58:59.12"));
		assertTrue(validateTimestampPart("2008-12-31 12:58:59.123"));
		assertTrue(validateTimestampPart("2008-12-31 12:58:59.1234"));
		assertTrue(validateTimestampPart("2008-12-31 12:58:59.12345"));
		assertTrue(validateTimestampPart("2008-12-31 12:58:59.123456"));
		assertTrue(validateTimestampPart("1999-01-01 00:00:00.000000"));
		

		assertFalse(validateTimestampPart("2008-13-31 12:58:59.123456"));
		assertFalse(validateTimestampPart("2008-00-31 12:58:59.123456"));
		assertFalse(validateTimestampPart("2008-12-32 12:58:59.123456"));
		assertFalse(validateTimestampPart("2008-12-00 12:58:59.123456"));
		assertFalse(validateTimestampPart("2008-12-31 24:58:59.123456"));
		assertFalse(validateTimestampPart("2008-12-31 12:60:59.123456"));
		assertFalse(validateTimestampPart("2008-12-31 12:58:60.123456"));

		assertFalse(validateTimestampPart("2008-12-31-12:58:59.123456"));
		assertFalse(validateTimestampPart("2008:12:31 12:58:59.123456"));
		assertFalse(validateTimestampPart("2008-12-31 12:58:59:123456"));
		assertFalse(validateTimestampPart("2008-12-31 12:58.59.123456"));
		assertFalse(validateTimestampPart("2008-12-31 12.58:59.123456"));
		assertFalse(validateTimestampPart("2008-12-3112:58:59.123456"));
		assertFalse(validateTimestampPart("2008-12-31 12:58:59.1234567"));
		assertFalse(validateTimestampPart("008-12-31 12:58:59.123456"));
		assertFalse(validateTimestampPart("2008-1-31 12:58:59.123456"));
		assertFalse(validateTimestampPart("2008-12-3 12:58:59.123456"));
		assertFalse(validateTimestampPart("2008-12-31 2:58:59.123456"));
		assertFalse(validateTimestampPart("2008-12-31 12:5:59.123456"));
		assertFalse(validateTimestampPart("2008-12-31 12:58:5.123456"));
		
		
		
	}
}
