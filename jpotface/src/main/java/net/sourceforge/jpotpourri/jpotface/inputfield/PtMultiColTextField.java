package net.sourceforge.jpotpourri.jpotface.inputfield;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.sourceforge.jpotpourri.util.PtStringUtil;


/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtMultiColTextField extends JLabel {

//    private static final Log LOG = LogFactory.getLog(MultiColTextField.class);
    private static final long serialVersionUID = 2860809976529219917L;
    
    private final int visibleTextColumns;
    private static final Point POINT_ZERO = new Point(0, 0);
    private String realText;
    
    public PtMultiColTextField(final int columns) {
        this("", columns);
    }
    
    public PtMultiColTextField(final String text, final int columns) {
//    	this.setColumns(columns);
    	this.visibleTextColumns = columns;
    	this.realText = text;
//        this.setPreferredSize(new Dimension(columns * 2, (int) this.getPreferredSize().getHeight()));
        
    	
//        this.setOpaque(true); this.setBackground(Color.RED);
    	this.setOpaque(false);
        
        this.setHorizontalAlignment(SwingConstants.LEFT);
        this.setVerticalAlignment(SwingConstants.TOP);
        
        this.setBorder(BorderFactory.createEmptyBorder());
        
        this.setText(text);
    }
    
    @Override
	public final void setText(final String text) {
    	this.realText = text;
        super.setText(PtStringUtil.enforceMaxWidth(text, this.visibleTextColumns));
        
        if(text.length() == 0) {
        	this.setToolTipText(null);
        } else {
        	this.setToolTipText(text);
        }
    }
    
    public final String getFullText() {
    	return this.realText;
    }

    @Override
	public final Point getToolTipLocation(final MouseEvent e) {
        if (getToolTipText(e) == null) {
            return null;
        }
        return POINT_ZERO;
    }
}
