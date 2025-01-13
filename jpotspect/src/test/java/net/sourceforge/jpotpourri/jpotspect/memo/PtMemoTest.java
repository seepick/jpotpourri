package net.sourceforge.jpotpourri.jpotspect.memo;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtMemoTest {
	
	@Test
	public final void testAdd() {
		final Calculator calc = new Calculator();

		assertEquals(7, calc.add(3, 4));
		assertEquals(6, calc.add(3, 3));
		assertEquals(7, calc.add(3, 4));

		assertEquals(2, calc.methodExecuted);
	}

	@Test
	public final void testArray() {
		final Calculator calc = new Calculator();

		calc.array(new Object[] { null, null });
		calc.array(new Object[] { null, null });

		assertEquals(1, calc.methodExecuted);
	}

	@Test
	public final void testArrayInt() {
		final Calculator calc = new Calculator();

		calc.arrayInt(new int[] { 3, 3});
		calc.arrayInt(new int[] { 3, 3});

		calc.arrayInt(new int[] { 3, 4});

		assertEquals(2, calc.methodExecuted);
	}

	@Test
	public final void testNullReturn() {
		final Calculator calc = new Calculator();

		calc.nullReturn("a");
		calc.nullReturn("a");
		calc.nullReturn(1);
		
		assertEquals(2, calc.methodExecuted);
	}
		
	@Test
	public final void testComplex() {
		final Calculator calc = new Calculator();

		calc.complex(new Object(), new Integer(3), "string", new ArrayList<String>(0));
		
		calc.complex(new Object(), new Integer(3), "string", new ArrayList<String>(0)); // ArrayList considered not equals

		calc.complex(new Object(), new Integer(3), "string", new ArrayList<String>(4));

		calc.complex(null, null, null, null);
		calc.complex(null, null, null, null);

		calc.complex(null, 7, null, null);
		calc.complex(null, 7, null, null);
		
		calc.complex(null, 8, null, null);

		calc.complex(null, null, "somebua", null);
		calc.complex(null, null, "somebua", null);

		assertEquals(7, calc.methodExecuted);
	}
	
	

	/**
	 * @author christoph_pickl@users.sourceforge.net
	 */
	private static class Calculator {

		private int methodExecuted;

		@PtMemorizable
		public int add(final int operand1, final int operand2) {
			this.methodExecuted++;
			return operand1 + operand2;
		}
		
		@PtMemorizable
		@SuppressWarnings("unused")
		public Object complex(
				final Object object,
				final Integer integer,
				final String string,
				final List<String> list) {
			this.methodExecuted++;
			return "complex";
		}

		@PtMemorizable
		@SuppressWarnings("unused")
		public Object array(final Object[] array) {
			this.methodExecuted++;
			return "array";
		}

		@PtMemorizable
		@SuppressWarnings("unused")
		public Object nullReturn(final Object object) {
			this.methodExecuted++;
			return null;
		}

		@PtMemorizable
		@SuppressWarnings("unused")
		public Object arrayInt(final int[] array) {
			this.methodExecuted++;
			return "arrayInt";
		}
		
		
	}

}
