package net.sourceforge.jpotpourri.jpotface.panel.brushed;

import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.plaf.PanelUI;

/**
 * @author http://blog.elevenworks.com/?p=10
 */
public abstract class PtTiledImagePanel extends JPanel {
    
    private static final long serialVersionUID = -3588314729894479207L;

    private Image image;

    
    
    public PtTiledImagePanel() {
    	this.initializeUI();
    }

    public PtTiledImagePanel(final Image image) {
        this();
        setImage(image);
    }

    protected abstract boolean overridesInitializeUI();
    protected abstract void overriddenInitializeUI();
    
    protected final void initializeUI() {
    	if(this.overridesInitializeUI()) {
    		this.overriddenInitializeUI();
    	} else {
    		super.setUI(PtTiledImagePanelUI.createUI(this));
    	}
    }

    protected abstract boolean overridesSetImage(final Image pImage);
    protected abstract void overriddenSetImage(final Image pImage);
    
    public final void setImage(final Image image) {
    	if(this.overridesSetImage(image)) {
    		this.overriddenSetImage(image);
    	} else {
    		this.actualSetImage(image);
    	}
    }
    
    protected final void actualSetImage(final Image pImage) {
        final Image oldImage = this.image;
        this.image = pImage;
        this.firePropertyChange("image", oldImage, this.image);
        this.repaint();
    }
    
    

    @Override
	public final void setUI(final PanelUI ui) {
        if (ui instanceof PtTiledImagePanelUI) {
            super.setUI(ui);
        }
    }


    public final Image getImage() {
        return this.image;
    }
    
    


    /**
     * @author http://blog.elevenworks.com/?p=10
     */
    public static class DefaultTiledImagePanel extends PtTiledImagePanel {
		
    	private static final long serialVersionUID = 6260577847081797644L;
		
		@Override
		protected final void overriddenInitializeUI() {
			// nothing to do
		}
		@Override
		protected final boolean overridesInitializeUI() {
			return false;
		}
		@Override
		protected final void overriddenSetImage(final Image image) {
			// nothing to do
		}
		@Override
		protected final boolean overridesSetImage(final Image image) {
			return false;
		}
    }
}
