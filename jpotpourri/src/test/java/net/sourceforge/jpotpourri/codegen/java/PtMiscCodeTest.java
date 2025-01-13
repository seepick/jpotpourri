package net.sourceforge.jpotpourri.codegen.java;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.jpotpourri.codegen.java.PtGenArgument;

import junit.framework.TestCase;

/**
 * @author christoph.pickl@bmi.gv.at
 */
public final class PtMiscCodeTest extends TestCase {

	public void testGenArgumentEmpty() {
		final List<PtGenArgument> actual = PtGenArgument.newList();
		assertEquals(0, actual.size());
	}

	public void testGenArgument() {
		final List<PtGenArgument> expected = new ArrayList<PtGenArgument>(2);
		expected.add(new PtGenArgument("String", "foo"));
		expected.add(new PtGenArgument("int", "i"));
		expected.add(new PtGenArgument("Object", "obj"));
		
		final List<PtGenArgument> actual = PtGenArgument.newList("String", "foo", "int", "i", "Object", "obj");
		
		assertEquals(expected.size(), actual.size());
		for (int i = 0; i < expected.size(); i++) {
			assertEquals(expected.get(i), actual.get(i));
		}
	}
}
