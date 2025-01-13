package net.sourceforge.jpotpourri.jpotface.inputfield;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import net.sourceforge.jpotpourri.jpotface.util.PtGuiUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtDateField extends JTextField {

    private static final Log LOG = LogFactory.getLog(PtDateField.class);
    private static final long serialVersionUID = 8776501786825325958L;
    private static final boolean DEBUG = false;

    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");
    private static final int DEFAULT_COLUMN_SIZE = 6;
    
    private final SimpleDateFormat format; // use to parse back user input
    private final boolean numberFieldConstructorFinished;
    
    public PtDateField(final Date date, final SimpleDateFormat format, final int columnSize) {
        super(format.format(date), columnSize);
        this.setToolTipText("Format: " + format.toPattern().toUpperCase());
        this.format = format;
        
        this.numberFieldConstructorFinished = true;
    }
    
    public PtDateField(final Date date, final int columnSize) {
        this(date, DEFAULT_DATE_FORMAT, columnSize);
    }
    
    public PtDateField(final Date date) {
        this(date, DEFAULT_DATE_FORMAT, DEFAULT_COLUMN_SIZE);
    }
    
    public final Date getDate() {
        try {
            return this.format.parse(this.getText());
        } catch (ParseException e) {
            LOG.warn("Could not parse datestring '" + this.getText() + "'!");
            return null;
        }
    }
    

    @Override
	protected final Document createDefaultModel() {
        return new DateTextDocument();
    }
    
    private static boolean isValid(final String text) {
        final int n = text.length();
        if(DEBUG) {
        	System.out.println("DEBUG: isValid(" + text + ") length=" + n);
        }
        
        if(n > 10) {
        	return false;
        }
        
        if(n == 0) {
            return true;
        } else if(n > 0 && n <= 4) {
            return isNumber(text);
        } else if(n == 5) {
            return isNumber(text.substring(0, 4)) && text.charAt(4) == '/';
        } else if(n > 5 && n <= 7) {
            return isNumber(text.substring(0, 4)) && text.charAt(4) == '/'
                && isNumber(text.substring(5));
        } else if(n == 8) {
            return isNumber(text.substring(0, 4)) && text.charAt(4) == '/'
                && isNumber(text.substring(5, 7)) && text.charAt(7) == '/';
        } else if(n > 8 && n <= 10) {
            return isNumber(text.substring(0, 4)) && text.charAt(4) == '/'
                && isNumber(text.substring(5, 7)) && text.charAt(7) == '/'
                && isNumber(text.substring(8));
        }
        throw new RuntimeException("unhandled text '" + text + "'!"); // should never occur
    }
    
    private static boolean isNumber(final String text) {
        if(DEBUG) {
        	System.out.println("DEBUG: isNumber(" + text + ")");
        }
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * @author christoph_pickl@users.sourceforge.net
     */
    private class DateTextDocument extends PlainDocument {
        private static final long serialVersionUID = 2505716035235158421L;

        
        @Override
		public void insertString(final int offs, final String str, final AttributeSet a) throws BadLocationException {
            if (str == null) {
            	return;
            }
            
            if(PtDateField.this.numberFieldConstructorFinished == false) {
            	// super constructor invocation; we can trust him
                super.insertString(offs, str, a);
                return;
            }
            
            final String oldDateText = getText(0, getLength());
            final String newDateText = oldDateText.substring(0, offs) + str + oldDateText.substring(offs);
            if(DEBUG) {
            	System.out.println("DEBUG: oldDateText='" + oldDateText + "'; newDateText='" + newDateText + "'");
            }
            
            final boolean isValidValue = PtDateField.isValid(newDateText); 
                
            if(isValidValue == true) {
                super.insertString(offs, str, a);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }
    

    public static void main(final String[] args) {
        JFrame f = new JFrame();
        f.addWindowListener(new WindowAdapter() {
          @Override
		public void windowClosing(final WindowEvent e) {
            System.exit(0);
        } });
        f.add(new PtDateField(new Date()));
        f.pack();
        PtGuiUtil.setCenterLocation(f);
        f.setVisible(true);
    }
}
