package net.sourceforge.jpotpourri.jpotface.panel.brushed;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPanelUI;


/**
 * 
 * @author http://blog.elevenworks.com/?p=10
 */
public abstract class PtTiledImagePanelUI extends BasicPanelUI implements PropertyChangeListener {
	
    private static Image image; // TODO why static?

    public PtTiledImagePanelUI() {
        // nothing to do
    }

    public PtTiledImagePanelUI(final Image image) {
    	PtTiledImagePanelUI.image = image;
    }

    public static ComponentUI createUI(final JComponent component) {
        return new DefaultTiledImagePanelUI();
    }

    @Override
	public final void installUI(final JComponent c) {
        super.installUI(c);

        if (PtTiledImagePanelUI.image == null) {
            if (c instanceof PtTiledImagePanel) {
            	PtTiledImagePanelUI.image = ((PtTiledImagePanel) c).getImage();
                c.addPropertyChangeListener("image", this);
            } else {
                throw new RuntimeException("TiledImagePanelUI must be used with a TiledImagePanel or " +
                		"the image must be set explicitly");
            }
        }
    }

    @Override
	protected final void uninstallDefaults(final JPanel p) {
        p.removePropertyChangeListener("image", this);
        super.uninstallDefaults(p);
    }

    // -----------------------------------------------------------------------------------------------------------------
    //  Custom painting methods
    // -----------------------------------------------------------------------------------------------------------------

    protected abstract boolean overridesPaint();
    protected abstract void overriddenPaint(final Graphics g, final JComponent c);
    
    protected final void actualPaint(final Graphics g, final JComponent c) {
    	if (PtTiledImagePanelUI.image == null) {
            return;
        }
        Dimension vSize = c.getSize();

        Graphics2D g2d = (Graphics2D) g;

        for (int x = 0; x < vSize.width; x += 200) {
            for (int y = 0; y < vSize.height; y += 200) {
                g2d.drawImage(PtTiledImagePanelUI.image, x, y, null);
            }
        }
    }
    
    @Override
	public final void paint(final Graphics g, final JComponent c) {
        if(this.overridesPaint()) {
        	this.overriddenPaint(g, c);
        } else {
        	this.actualPaint(g, c);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    //  Implementation of PropertyChangeListener interface
    // -----------------------------------------------------------------------------------------------------------------

    public final void propertyChange(final PropertyChangeEvent evt) {
        if ("image".equals(evt.getPropertyName())) {
        	PtTiledImagePanelUI.image = (Image) evt.getNewValue();
        }
    }
    

    /**
     * @author christoph_pickl@users.sourceforge.net
     */
    public static class DefaultTiledImagePanelUI extends PtTiledImagePanelUI {
		@Override
		protected final void overriddenPaint(final Graphics g, final JComponent c) {
			// nothing to do
		}
		@Override
		protected final boolean overridesPaint() {
			return false;
		}
    	
    }
}
