package net.sourceforge.jpotpourri.jpotface.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.sourceforge.jpotpourri.jpotface.IPtGlobalKeyListener;
import net.sourceforge.jpotpourri.jpotface.PtGlobalKey;
import net.sourceforge.jpotpourri.jpotface.dialog.PtErrorDialog;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtGuiUtil {

    private static final Log LOG = LogFactory.getLog(PtGuiUtil.class);
    
    private static final Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
    private static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
    
    
    private PtGuiUtil() {
        // no instantiation
    }

    public static void setCenterLocation(final Component component) {
    	PtGuiUtil.setCenterLocation(component, 0, 0);
    }

    /**
     * sets the position of given component to the center of the screen
     *
     * @param component
     *            which should be placed in the center of screen
     */
    public static void setCenterLocation(final Component component, final int xOffset, final int yOffset) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int x = (screenSize.width - component.getWidth()) / 2;
        int y = (screenSize.height - component.getHeight()) / 2;

        component.setLocation(x + xOffset, y + yOffset);
    }

    public static void enableHandCursor(final Component component) {
        component.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseEntered(final MouseEvent event) {
                component.setCursor(PtGuiUtil.HAND_CURSOR);
            }
            @Override
			public void mouseExited(final MouseEvent event) {
                component.setCursor(PtGuiUtil.DEFAULT_CURSOR);
            }
        });
    }


    public static void info(final String title, final String message) {
        info(null, title, message);
    }
    public static void info(final Component parent, final String title, final String message) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void warning(final String title, final String message) {
        warning(null, title, message);
    }
    public static void warning(final Component parent, final String title, final String message) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.WARNING_MESSAGE);
    }

    
    
    public static void error(final String title, final String message) {
    	PtErrorDialog.newDialog(title, message).setVisible(true);
    }
    public static void error(final String title, final String message, final Exception exception) {
    	PtErrorDialog.newDialog(title, message, exception).setVisible(true);
    }
    public static void error(final JDialog owner, final String title, final String message) {
//      JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
    	PtErrorDialog.newDialog(owner, title, message).setVisible(true);
    }
    public static void error(final JDialog owner, final String title, final String message, final Exception exception) {
    	PtErrorDialog.newDialog(owner, title, message, exception).setVisible(true);
    }
    public static void error(final JFrame owner, final String title, final String message) {
    	PtErrorDialog.newDialog(owner, title, message).setVisible(true);
    }
    public static void error(final JFrame owner, final String title, final String message, final Exception exception) {
    	PtErrorDialog.newDialog(owner, title, message, exception).setVisible(true);
    }
    
    



    
    public static void addGlobalKeyListener(final JComponent contentPanel, final IPtGlobalKeyListener listener) {
    	LOG.debug("Adding global key listener: " + listener.getClass().getName());
    	addGlobalKeyListenerDetails(contentPanel, listener, PtGlobalKey.ESCAPE);
    	addGlobalKeyListenerDetails(contentPanel, listener, PtGlobalKey.SPACE);
    }
    
    // WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
    private static void addGlobalKeyListenerDetails(final JComponent contentPanel, final IPtGlobalKeyListener listener,
    		final PtGlobalKey key) {
    	contentPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(key.getKeyStroke(), key.getActionCommand());
		contentPanel.getActionMap().put(key.getActionCommand(), new AbstractAction() {
			private static final long serialVersionUID = -266823267636545239L;
			public void actionPerformed(final ActionEvent event) {
				listener.doKeyPressed(key);
		    }
		});
    }
    
    
    
    

    public static JMenuItem newMenuItem(final String label, final String actionCmd, final List<JMenuItem> list) {
		final JMenuItem item = new JMenuItem(label);
		item.setActionCommand(actionCmd);
		list.add(item);
		return item;
    }
    
    public static void paintCenteredBackgroundImage(final Graphics g, final JPanel panel, final Image image) {
		final int x = (int) ((panel.getSize().getWidth() - image.getWidth(panel)) / 2);
		final int y = (int) ((panel.getSize().getHeight() - image.getHeight(panel)) / 2);
		g.drawImage(image, x, y, panel);
	}
    

	public static void paintGradient(final Graphics2D g2, final Color colorTop, final Color colorBottom,
			final Dimension size) {
		final float x1 = 0;
		final float y1 = 0;
		final float x2 = 0;
		final float y2 = size.height;
		final GradientPaint gp = new GradientPaint(x1, y1, colorTop, x2, y2, colorBottom, false);
		g2.setPaint(gp);

		final Rectangle r = new Rectangle(size.width, size.height);
		g2.fill(r);
		g2.draw(r);
	}
}
