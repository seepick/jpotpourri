package net.sourceforge.jpotpourri.jpotface;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JTable;

import net.sourceforge.jpotpourri.jpotface.util.PtGuiUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtEscapeDisposer {
	
	private static final Log LOG = LogFactory.getLog(PtEscapeDisposer.class);
	
	
	private PtEscapeDisposer() {
		// no instantiation
	}
	
	public static void enableEscapeOnDialogWithoutFocusableComponents(final JDialog dialog,
			final IPtEscapeDisposeReceiver receiver) {
		LOG.debug("Enabling dialog-without-focusable-components escape notification on receiver with class " +
				"'" + receiver.getClass().getName() + "'.");
    	
		dialog.addKeyListener(new KeyAdapter() {
    		@Override
			public void keyPressed(final KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					LOG.debug("Broadcasting dialog-without-focusable-components escape event to listener with class " +
							"'" + receiver.getClass().getName() + "'.");
					receiver.doEscape();
				}
			}
    	});
	}
    
    public static void enableEscape(final JTable table, final IPtEscapeDisposeReceiver receiver) {
    	LOG.debug("Enabling table escape notification on receiver with class '" + receiver.getClass().getName() + "'.");
    	
    	table.addKeyListener(new KeyAdapter() {
    		@Override
			public void keyPressed(final KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					LOG.debug("Broadcasting table escape event to listener with class " +
							"'" + receiver.getClass().getName() + "'.");
					receiver.doEscape();
				}
			}
    	});
    	
    }
	//  JComponent JRootPane
    public static void enableEscape(final JComponent rootPane, final IPtEscapeDisposeReceiver receiver) {
    	LOG.debug("Enabling rootpane escape notification on receiver with class " +
    			"'" + receiver.getClass().getName() + "'.");
    	
		PtGuiUtil.addGlobalKeyListener(rootPane, new IPtGlobalKeyListener() {
			public void doKeyPressed(final PtGlobalKey key) {
				if(key == PtGlobalKey.ESCAPE) {
					LOG.debug("Broadcasting rootpane escape event to listener with class " +
							"'" + receiver.getClass().getName() + "'.");
					receiver.doEscape();
				}
			}
		});
    }
    
}

