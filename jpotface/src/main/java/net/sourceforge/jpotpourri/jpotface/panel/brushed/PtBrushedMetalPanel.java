package net.sourceforge.jpotpourri.jpotface.panel.brushed;

import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import net.sourceforge.jpotpourri.jpotface.PtImageFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 * @author http://blog.elevenworks.com/?p=10
 */
public class PtBrushedMetalPanel extends PtTiledImagePanel {

    private static final Log LOG = LogFactory.getLog(PtBrushedMetalPanel.class);
    private static final long serialVersionUID = -8717131766395076544L;

    private static Image image = PtImageFactory.getInstance().getImgBrushed();

    private PtHighlightedImagePanelUI ui;

    public PtBrushedMetalPanel() {
        super(PtBrushedMetalPanel.image);
    }

    @Override
    protected final boolean overridesInitializeUI() {
    	return true;
    }
    
    @Override
    protected final void overriddenInitializeUI() {
    	LOG.debug("initializeUI()");
    	this.ui = PtHighlightedImagePanelUI.createUI(this);
        super.setUI(this.ui);
    }

    @Override
	protected final boolean overridesSetImage(final Image pImage) {
    	return (PtBrushedMetalPanel.image == pImage);
    }
    
    @Override
	protected final void overriddenSetImage(final Image pImage) {
    	super.actualSetImage(pImage);
    }
    
    public final void setActive(final boolean active) {
//    	LOG.debug("setActive(active="+active+")");
    	this.ui.setActive(active);
    }
    
    


    public static void main(final String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            LOG.warn("Could not set system look and feel!", e);
        }

        final JFrame frame = new JFrame("Brushed Metal Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        final JPanel panel = new PtBrushedMetalPanel();
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}
