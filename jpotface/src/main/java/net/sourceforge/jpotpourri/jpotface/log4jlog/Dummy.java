package net.sourceforge.jpotpourri.jpotface.log4jlog;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
final class Dummy {

    /** class' own logger using log4j */
    private static final Logger LOG = Logger.getLogger(Dummy.class);

    private static final boolean SHOW_JPOT_GUI_YOURSELF = true;
    
    private Dummy() {
    	// nothing to do
    }
    
    private static void initDummyLog4jProperties() {
    	final String log4jprop = "/dummylog4j.properties";
    	final URL url = PtLog4jGuiAppender.class.getResource(log4jprop);
    	if(url == null) {
    		throw new NullPointerException("Could not find file [" + log4jprop + "]!");
    	}
    	PropertyConfigurator.configure(url);
    }
    
	public static void main(final String[] args) throws Exception {
		initDummyLog4jProperties();
		
		if(SHOW_JPOT_GUI_YOURSELF == false) {
			System.setProperty(PtLog4jGuiAppender.SYSPROPERT_SHOW_DEBUG_GUI, "true");
			// -Djpotpourri.JpotGuiAppender.DEBUG=1
		}
		
		System.out.println("writing error-hack-log \"MAIN: INIT LOG ERROR\"");
		LOG.error("MAIN: INIT LOG ERROR"); // FIXME hack

		
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		final JPanel panel = new JPanel(new BorderLayout());
		
		JButton btn = new JButton("Create ERROR log");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				LOG.error("Created by user at " + new Date());
			}
		});
		panel.add(btn, BorderLayout.NORTH);

		if(SHOW_JPOT_GUI_YOURSELF == true)  {
			System.out.println("MAIN: getting JPOT appender!");
			PtLog4jGuiHandler handler = PtLogGuiHandlerPool.getInstance().getLog4jGuiHandler("jpot");
//			handler.getTableConfiguration().setColorXyz(Color.RED);
//			handler.setUiVisible(false);
//			handler.registerUiAction(Log4jUiAction.CLEAR_PRESSED, xyz);
			panel.add(handler.getJComponent(), BorderLayout.CENTER);
		}
		f.getContentPane().add(panel);
		f.pack();
		f.setVisible(true);

		System.out.println("MAIN: going to use jpot log adapter.");
		LOG.trace("jpot trace");
		Thread.sleep(100);
		LOG.debug("jpot debug");
		Thread.sleep(100);
		LOG.info("jpot info");
		Thread.sleep(100);
		LOG.warn("jpot warn 1/3");
//		Thread.sleep(100);
//		LOG.warn("jpot warn 2/3");
//		Thread.sleep(100);
//		LOG.warn("jpot warn 3/3");
		Thread.sleep(100);
		LOG.error("jpot error 1/3");
//		Thread.sleep(100);
//		LOG.error("jpot error 2/3");
//		Thread.sleep(100);
//		LOG.error("jpot error 3/3");
		Thread.sleep(100);
		LOG.fatal("jpot fatal");
		Thread.sleep(100);
		LOG.fatal("throwing an exception", new Exception("tut"));
		System.out.println("MAIN: finished.");
	}
}
