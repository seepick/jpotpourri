package net.sourceforge.jpotpourri.jpotface.log4jlog;

import java.util.Date;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtLog4jEvent {

	private final LoggingEvent loggingEvent;
	
	private final Date date;

	public PtLog4jEvent(final LoggingEvent loggingEvent, final Date date) {
		this.loggingEvent = loggingEvent;
		this.date = date;
	}

	
	public Date getDate() {
		return this.date;
	}
	
	public LoggingEvent getLoggingEvent() {
		return this.loggingEvent;
	}
	
	
	// DELEGATOR	
	public Level getLevel() {
		return this.loggingEvent.getLevel();
	}
	
	// DELEGATOR
	/**
	 * @return name of the class which sent log message
	 */
	public String getLogClassName() {
		return this.loggingEvent.getLocationInformation().getClassName();
	}

	// DELEGATOR
	public String getLogMethod() {
		return this.loggingEvent.getLocationInformation().getMethodName();
	}
	// line number
	// file
	
	// DELEGATOR
	public String getThreadName() {
		return this.loggingEvent.getThreadName();
	}

	public Throwable getThrowable() {
		if(this.loggingEvent.getThrowableInformation() == null) {
			return null;
		}
		return this.loggingEvent.getThrowableInformation().getThrowable();
	}
	
	// DELEGATOR
	public String getMessageRendered() {
		return this.loggingEvent.getRenderedMessage();
	}

	// DELEGATOR
	public Object getMessage() {
		return this.loggingEvent.getMessage();
	}
}
