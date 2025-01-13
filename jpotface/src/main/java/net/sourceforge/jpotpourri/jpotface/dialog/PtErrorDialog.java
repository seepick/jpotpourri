package net.sourceforge.jpotpourri.jpotface.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import net.sourceforge.jpotpourri.jpotface.IPtEscapeDisposeReceiver;
import net.sourceforge.jpotpourri.jpotface.PtEscapeDisposer;
import net.sourceforge.jpotpourri.jpotface.PtImageFactory;
import net.sourceforge.jpotpourri.jpotface.util.PtGuiUtil;
import net.sourceforge.jpotpourri.util.PtStringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtErrorDialog extends JDialog {

    private static final Log LOG = LogFactory.getLog(PtErrorDialog.class);
	private static final long serialVersionUID = 2690262434402199523L;


	private static final String DETAILS_SHOW = "Details >>";
	private static final String DETAILS_HIDE = "Details <<";
	
	private final Exception exception;
	
	private final JPanel panelDetails = new JPanel();
	private final JButton btnDetails = new JButton(DETAILS_SHOW);
	private final JTextArea stackTraceText = new JTextArea();
	
	


	/**
	 * @param message will be wrapped by a html-tag
	 */
	public static PtErrorDialog newDialog(final String title, final String message) {
		return new PtErrorDialog(title, message, null, null);
	}


	/**
	 * @param message will be wrapped by a html-tag
	 */
	public static PtErrorDialog newDialog(final String title, final String message, final Color backgroundColor) {
		return new PtErrorDialog(title, message, null, backgroundColor);
	}

	/**
	 * @param message will be wrapped by a html-tag
	 */
	public static PtErrorDialog newDialog(final String title, final String message, final Exception exception) {
		return new PtErrorDialog(title, message, exception, null);
	}

	/**
	 * @param message will be wrapped by a html-tag
	 */
	public static PtErrorDialog newDialog(final String title, final String message, final Exception exception,
			final Color backgroundColor) {
		return new PtErrorDialog(title, message, exception, backgroundColor);
	}

	/**
	 * @param message will be wrapped by a html-tag
	 */
	public static PtErrorDialog newDialog(final JDialog owner, final String title, final String message) {
		return new PtErrorDialog(owner, title, message, null, null);
	}

	/**
	 * @param message will be wrapped by a html-tag
	 */
	public static PtErrorDialog newDialog(final JDialog owner, final String title, final String message,
			final Color backgroundColor) {
		return new PtErrorDialog(owner, title, message, null, backgroundColor);
	}

	/**
	 * @param message will be wrapped by a html-tag
	 */
	public static PtErrorDialog newDialog(final JDialog owner, final String title, final String message,
			final Exception exception) {
		return new PtErrorDialog(owner, title, message, exception, null);
	}

	/**
	 * @param message will be wrapped by a html-tag
	 */
	public static PtErrorDialog newDialog(final JDialog owner, final String title, final String message,
			final Exception exception, final Color backgroundColor) {
		return new PtErrorDialog(owner, title, message, exception, backgroundColor);
	}

	/**
	 * @param message will be wrapped by a html-tag
	 */
	public static PtErrorDialog newDialog(final JFrame owner, final String title, final String message) {
		return new PtErrorDialog(owner, title, message, null, null);
	}

	/**
	 * @param message will be wrapped by a html-tag
	 */
	public static PtErrorDialog newDialog(final JFrame owner, final String title, final String message,
			final Color backgroundColor) {
		return new PtErrorDialog(owner, title, message, null, backgroundColor);
	}

	/**
	 * @param message will be wrapped by a html-tag
	 */
	public static PtErrorDialog newDialog(final JFrame owner, final String title, final String message,
			final Exception exception) {
		return new PtErrorDialog(owner, title, message, exception, null);
	}

	/**
	 * @param message will be wrapped by a html-tag
	 */
	public static PtErrorDialog newDialog(final JFrame owner, final String title, final String message,
			final Exception exception, final Color backgroundColor) {
		return new PtErrorDialog(owner, title, message, exception, backgroundColor);
	}
	
	

	private PtErrorDialog(final String title, final String message, final Exception exception,
			final Color backgroundColor) {
		this.exception = exception;	
		this.pseudoConstructor(title, message, backgroundColor);
	}
	
	private PtErrorDialog(final JDialog owner, final String title, final String message, final Exception exception,
			final Color backgroundColor) {
		super(owner);
		
		this.exception = exception;
		this.pseudoConstructor(title, message, backgroundColor);
	}
	
	private PtErrorDialog(final JFrame owner, final String title, final String message, final Exception exception,
			final Color backgroundColor) {
		super(owner);

		this.exception = exception;
		this.pseudoConstructor(title, message, backgroundColor);
	}
	
	
	
	/**
	 * 
	 * @param title
	 * @param message
	 * @param backgroundColor can be null
	 */
	private void pseudoConstructor(final String title, final String message, final Color backgroundColor) {
		LOG.debug("Creating new error dialog");
		this.setModal(true);
		// TODO set minimum size to width about 650px
		this.setTitle(title);
		
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				doClose();
			}
		});

		PtEscapeDisposer.enableEscape(this.getRootPane(), new IPtEscapeDisposeReceiver() {
			public void doEscape() {
				doClose();
			}
		});
		
		
		final JPanel panel = new JPanel(new BorderLayout());
		if(backgroundColor != null) {
			panel.setBackground(backgroundColor);
		}
		
		panel.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16)); // top left bottom right
		panel.add(this.initComponents(message), BorderLayout.CENTER);
		
		if(this.exception != null) {
			panel.add(this.initDetailsPanel(), BorderLayout.SOUTH);
		}
		this.getContentPane().add(panel);
		
		this.pack();
		this.setResizable(false);
		PtGuiUtil.setCenterLocation(this);
	}

	
	
	private JPanel initDetailsPanel() {
		assert(this.exception != null);
		
		this.panelDetails.setLayout(new BorderLayout(0, 5));
		this.panelDetails.setVisible(false);
		
		final String detailText = PtStringUtil.convertExceptionToString(this.exception);
		this.stackTraceText.setText(detailText);
		this.stackTraceText.setRows(6);
		this.stackTraceText.setColumns(45);
		
		final JButton btnCopyClipboard = new JButton("Copy to Clipboard");
		btnCopyClipboard.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				doCopyClipboard();
		} });
		
		this.panelDetails.add(new JScrollPane(this.stackTraceText), BorderLayout.CENTER);
		JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		southPanel.add(btnCopyClipboard);
		this.panelDetails.add(southPanel, BorderLayout.SOUTH);
		
		this.panelDetails.setOpaque(false);
		southPanel.setOpaque(false);
		
		return this.panelDetails;
	}
	
	private JPanel initComponents(final String errorMessage) {
		final JPanel wrapPanel = new JPanel(new BorderLayout());
		

		JPanel westPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		westPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		westPanel.add(new JLabel(PtImageFactory.getInstance().getDialogError()));

		final JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		final JTextArea txt = new JTextArea(errorMessage);
		txt.setEditable(false);
		txt.setLineWrap(true);
		txt.setWrapStyleWord(true);
//		txt.setOpaque(true);
//		txt.setBackground(Color.WHITE);
		final JScrollPane txtScroll = new JScrollPane(txt);
//		txtScroll.setOpaque(false);
//		txtScroll.setBorder(BorderFactory.createEmptyBorder());
		txtScroll.setPreferredSize(new Dimension(300, 80));
		centerPanel.add(txtScroll);
		

		JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		// JButton btnReport = new JButton("Send Report...");
//		southPanel.add(btnReport); // TODO implement Send Report...
		
		JButton btnClose = new JButton("Close");
		southPanel.add(btnClose);
		
		if (this.exception != null) {
			southPanel.add(this.btnDetails);
		}
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				doClose();
		} });
			
		this.btnDetails.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				doDetails();
		} });
		this.getRootPane().setDefaultButton(btnClose);

		westPanel.setOpaque(false);
		southPanel.setOpaque(false);
		centerPanel.setOpaque(false);
		wrapPanel.setOpaque(false);

		wrapPanel.add(westPanel, BorderLayout.WEST);
		wrapPanel.add(southPanel, BorderLayout.SOUTH);
		wrapPanel.add(centerPanel, BorderLayout.CENTER);
		return wrapPanel;
	}
	
	private void doCopyClipboard() {
		LOG.info("doCopyClipboard()");
		StringSelection stringSelection = new StringSelection(this.stackTraceText.getText());
	    Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    systemClipboard.setContents(stringSelection, new ClipboardOwner() {
	    	public void lostOwnership(final Clipboard clipboard, final Transferable content) {
				// can be ignored
			}
	    });

	}
	
	private void doClose() {
		this.dispose();
	}
	
	private void doDetails() {
		LOG.debug("doDetails()");
		this.btnDetails.setText(this.panelDetails.isVisible() ? DETAILS_SHOW : DETAILS_HIDE);
		this.panelDetails.setVisible(!this.panelDetails.isVisible());
		this.pack();
	}
	
	
	

    public static void main(final String[] args) {
//    	ErrorDialog.newDialog("Title", "Msg").setVisible(true);

    	Exception cause = new Exception("ganz unten");
    	Exception cause2 = new Exception("rums", cause);
    	Exception ex = new Exception("bla blu", cause2);
    	
    	final String message = "<html>Das sit eine ganz lange<br>und auch nach unten<br>" +
    			"gehts einfach weiter<br><br>ja das ist so!</html>";
    	PtErrorDialog.newDialog("Title", message, ex).setVisible(true);
    }
}
