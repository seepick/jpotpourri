package net.sourceforge.jpotpourri.jpotface.log4jlog;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.log4j.Level;

// stays public!
/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtAllLogLevels {

	private PtAllLogLevels() {
		// no instantiation
	}
	
	public static final Set<Level> LEVELS;
	static {
		 final Set<Level> tmp = new LinkedHashSet<Level>();
		 tmp.add(Level.ALL);
		 tmp.add(Level.TRACE);
		 tmp.add(Level.DEBUG);
		 tmp.add(Level.TRACE);
		 tmp.add(Level.INFO);
		 tmp.add(Level.WARN);
		 tmp.add(Level.ERROR);
		 tmp.add(Level.FATAL);
		 tmp.add(Level.OFF);
		 LEVELS = Collections.unmodifiableSet(tmp);
	}
	
	public static final Set<Level> LEVELS_WITHOUT_ALL_AND_OFF;
	static {
		 final Set<Level> tmp = new LinkedHashSet<Level>();
		 tmp.add(Level.TRACE);
		 tmp.add(Level.DEBUG);
		 tmp.add(Level.TRACE);
		 tmp.add(Level.INFO);
		 tmp.add(Level.WARN);
		 tmp.add(Level.ERROR);
		 tmp.add(Level.FATAL);
		 LEVELS_WITHOUT_ALL_AND_OFF = Collections.unmodifiableSet(tmp);
	}
}
