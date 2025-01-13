package net.sourceforge.jpotpourri.jpotface.log4jlog;

import javax.swing.JComponent;
import javax.swing.JFrame;

import net.sourceforge.jpotpourri.jpotface.log4jlog.gui.PtLogPanel;

/**
 * actually most important class for developer/client
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtLog4jGuiHandler {

	private final PtLogPanel panel;
	
	private final String appenderName;

//	private final Log4jGuiHandlerDefinition handlerDefinition;

	PtLog4jGuiHandler(final PtLogGuiHandlerDefinition handlerDefinition) {
		De.bug("new Log4jGuiHandler(handlerDefinition=" + handlerDefinition + "); thread=" +
				Thread.currentThread().getName());
//		this.handlerDefinition = handlerDefinition;
		this.appenderName = handlerDefinition.getAppenderName();
		this.panel = new PtLogPanel(handlerDefinition);
	}
	
	
	public String getAppenderName() {
		return this.appenderName;
	}
	
	public void processLoggingEvent(final PtLog4jEvent event) {
		this.panel.processLoggingEvent(event);
	}
	
	public JComponent getJComponent() {
		return this.panel;
	}
	
	private JFrame debugFrame;
	
	void setDebugFrame(final JFrame debugFrame) {
		final String debugProperty = System.getProperty("jpotpourri.JpotGuiAppender.DEBUG");
		assert(debugProperty != null && debugProperty.length() > 0);
		this.debugFrame = debugFrame;
	}
	
	public JFrame getDebugFrame() {
		return this.debugFrame;
	}
}
