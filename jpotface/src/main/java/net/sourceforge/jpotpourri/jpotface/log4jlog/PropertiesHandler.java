package net.sourceforge.jpotpourri.jpotface.log4jlog;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
class PropertiesHandler {
	
	private String appenderName;
	private boolean systemLafEnabled;
	// nothing else yet

	public PropertiesHandler() {
		// nothing to do
	}

	public PtLogGuiHandlerDefinition toDefinition(final PtLogGuiTableDefinition tableDefinition) {
		return new PtLogGuiHandlerDefinition(this.appenderName, this.systemLafEnabled, tableDefinition);
	}

	
	public void setAppenderName(final String appenderName) {
		this.appenderName = appenderName;
	}
	
	public boolean isSystemLafEnabled() {
		return this.systemLafEnabled;
	}

	public void setSystemLafEnabled(final boolean systemLafEnabled) {
		this.systemLafEnabled = systemLafEnabled;
	}
}
