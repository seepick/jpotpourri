package net.sourceforge.jpotpourri.jpotface.inputfield;

import java.awt.Toolkit;
import java.text.ParseException;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import net.sourceforge.jpotpourri.util.PtDateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * validates input for format "yyyy-MM-dd HH:mm:ss.SSSSSS"
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtTimestampField extends JTextField {

	private static final Log LOG = LogFactory.getLog(PtTimestampField.class);
	private static final long serialVersionUID = 4513534707427639864L;
	

    private final boolean constructorFinished;
	
	public PtTimestampField() {
		super(20);
		this.setToolTipText(PtDateUtil.TIMESTAMP_FORMAT);
		
        this.constructorFinished = true;
	}

    @Override
	protected final Document createDefaultModel() {
        return new TimestampTextDocument();
    }
    
    public boolean isFullyValid() {
    	return this.getText().length() == PtDateUtil.TIMESTAMP_FORMAT.length() &&
    		   PtDateUtil.validateTimestampPart(this.getText()) == true;
    }
    
    public long getLongValue() {
    	assert(this.isFullyValid());
    	try {
			return PtDateUtil.parseTimestamp(this.getText());
		} catch (ParseException e) {
			throw new RuntimeException("Encountered invalid timestamp text [" + this.getText() + "]!");
		}
    }

    /**
     * 
     * @author christoph_pickl@users.sourceforge.net
     */
    private class TimestampTextDocument extends PlainDocument {

		private static final long serialVersionUID = 3372026217391753096L;

        @Override
		public void insertString(final int offs, final String str, final AttributeSet a) throws BadLocationException {
            if (str == null) {
            	return;
            }

            if(PtTimestampField.this.constructorFinished == false) {
            	// super constructor invocation; we can trust him
                super.insertString(offs, str, a);
                return;
            }
            
            final String oldString = getText(0, getLength());
            final String newString = oldString.substring(0, offs) + str + oldString.substring(offs);
            LOG.trace("DEBUG: oldString='" + oldString + "'; newString='" + newString + "'");
            
            if(PtDateUtil.validateTimestampPart(newString) == true) {
                super.insertString(offs, str, a);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }
        
    }
    
    
    public static void main(final String[] args) {
//		JFrame f = new JFrame();
//		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//		JPanel p = new JPanel();
//		p.add(new PtTimestampField());
//		f.getContentPane().add(p);
//		f.pack();
//		f.setVisible(true);
	}
}
