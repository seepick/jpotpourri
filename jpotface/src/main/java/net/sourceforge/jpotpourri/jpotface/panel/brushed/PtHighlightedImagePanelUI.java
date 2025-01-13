package net.sourceforge.jpotpourri.jpotface.panel.brushed;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;

import javax.swing.JComponent;

/**
 * 
 * @author http://blog.elevenworks.com/?p=10
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtHighlightedImagePanelUI extends PtTiledImagePanelUI {
    
	private boolean active = true;
	
	
    public static PtHighlightedImagePanelUI createUI(final JComponent component) {
        return new PtHighlightedImagePanelUI();
    }

    @Override
	protected final boolean overridesPaint() {
    	return true;
    }
    
    @Override
    protected final void overriddenPaint(final Graphics g, final JComponent c) {
        super.actualPaint(g, c);
        
        final Dimension vSize = c.getSize();
        final Graphics2D g2d = (Graphics2D) g;
        
        // Create the paint for the second layer of the button
        final Color vGradientStartColor = this.active ? Color.GRAY : Color.WHITE;
        final Color vGradientEndColor = Color.WHITE;
        
        final int vHorizontalCenter = vSize.width / 2;
        final int vOffset = (int) (vSize.width * .1);
        
        final Composite vPreviousComposite = g2d.getComposite();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .3f));
        g2d.setColor(Color.WHITE);
        g2d.fillRect(vHorizontalCenter - vOffset, 0, vOffset * 2, vSize.height);
        
        final Paint vPaint = new GradientPaint(0, 0, vGradientStartColor,
        		vHorizontalCenter - vOffset, 0, vGradientEndColor, false);
        g2d.setPaint(vPaint);
        g2d.fillRect(0, 0, vHorizontalCenter - vOffset, vSize.height);
        
        final Paint vPaint2 = new GradientPaint(vSize.width, 0, vGradientStartColor,
        		vHorizontalCenter + vOffset, 0, vGradientEndColor, false);
        g2d.setPaint(vPaint2);
        g2d.fillRect(vHorizontalCenter + vOffset, 0, vHorizontalCenter - vOffset, vSize.height);
        g2d.setComposite(vPreviousComposite);
    }
    

    public final void setActive(final boolean active) {
    	this.active = active;
    }
}

