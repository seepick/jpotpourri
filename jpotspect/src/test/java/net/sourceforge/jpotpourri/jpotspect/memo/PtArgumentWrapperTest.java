package net.sourceforge.jpotpourri.jpotspect.memo;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class PtArgumentWrapperTest {

	@Test
	public final void testArrayNull() {
		checkArgs(Equality.SAME,
				newArgWrapper(new Object[] { null, null }),
				newArgWrapper(new Object[] { null, null }));
	}

	@Test
	public final void testSimple() {
		checkArgs(Equality.SAME,
				newArgWrapper(4, 2, 88L),
				newArgWrapper(4, 2, 88L));
		checkArgs(Equality.SAME_HASH,
				newArgWrapper(4, 2, 88L),
				newArgWrapper(4, 2, 88));
	}

	@Test
	public final void testString() {
		checkArgs(Equality.SAME,
				newArgWrapper("a"),
				newArgWrapper("a"));
		checkArgs(Equality.DIFFERENT,
				newArgWrapper("a"),
				newArgWrapper("b"));
	}
	
	@Test
	public final void testHashing() {
		this.testHashingHelper(true,
				new Object[] {new int[] {3, 3}},
				new Object[] {new int[] {3, 3}});
		this.testHashingHelper(false,
				new Object[] {new int[] {3, 3}},
				new Object[] {new int[] {3, 4}});
	}
	private void testHashingHelper(boolean correct, Object[] o1, Object[] o2) {
		PtArgumentWrapper a1 = new PtArgumentWrapper(o1);
		PtArgumentWrapper a2 = new PtArgumentWrapper(o2);
		
		final String expected = "foobar";
		Map<PtArgumentWrapper, String> m = new HashMap<PtArgumentWrapper, String>();
		m.put(a1, expected);
		if(correct == true) {
			assertEquals(expected, m.get(a2));
		} else {
			assertNull(m.get(a2));
		}
	}
	

	
	private static PtArgumentWrapper newArgWrapper(Object... args) {
		final Object[] obj = new Object[args.length];
		for (int i = 0; i < args.length; i++) {
			obj[i] = args[i];
		}
		return new PtArgumentWrapper(obj);
	}
	
	private static void checkArgs(final Equality equality, final PtArgumentWrapper arg1, final PtArgumentWrapper arg2) {
		final String msg = "arg1=" + arg1 + "; arg2=" + arg2;
		
		if (equality == Equality.SAME) {
			assertEquals(msg, arg1.hashCode(), arg2.hashCode());
			assertEquals(msg, arg1, arg2);
			
		} else if (equality == Equality.DIFFERENT) {
			assertFalse(msg, arg1.hashCode() == arg2.hashCode());
			assertFalse(msg, arg1.equals(arg2));
			
		} else if (equality == Equality.SAME_HASH) {
			assertTrue(msg, arg1.hashCode() == arg2.hashCode());
			assertFalse(msg, arg1.equals(arg2));
			
		} else {
			throw new RuntimeException("Unhandled equality [" + equality + "]!");
		}
	}
	
	private enum Equality {
		SAME,
		DIFFERENT,
		SAME_HASH
	}
}
