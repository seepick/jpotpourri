package net.sourceforge.jpotpourri.jpotface.log4jlog;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtLogGuiHandlerDefinition {

	private final String appenderName;
	private final boolean systemLafEnabled;
	private final PtLogGuiTableDefinition tableDefinition;
	
	public PtLogGuiHandlerDefinition(final String appenderName,
			final boolean systemLafEnabled, final PtLogGuiTableDefinition tableDefinition) {
		De.bug("new Log4jGuiHandlerDefinition(appenderName=" + appenderName + ";systemLafEnabled=" + systemLafEnabled +
				"); thread=" + Thread.currentThread().getName());
		if(appenderName == null) {
			throw new NullPointerException("appenderName");
		}
		if(tableDefinition == null) {
			throw new NullPointerException("tableDefinition");
		}
		
		this.appenderName = appenderName;
		this.systemLafEnabled = systemLafEnabled;
		
		this.tableDefinition = tableDefinition;

	}
	public final String getAppenderName() {
		return this.appenderName;
	}
	public final PtLogGuiTableDefinition getTableDefinition() {
		return this.tableDefinition;
	}
	public final boolean isSystemLafEnabled() {
		return this.systemLafEnabled;
	}
}
