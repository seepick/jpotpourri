package net.sourceforge.jpotpourri.jpotface.log4jlog.gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.sourceforge.jpotpourri.jpotface.log4jlog.PtLog4jEvent;
import net.sourceforge.jpotpourri.util.PtStringUtil;


/**
 * @author christoph_pickl@users.sourceforge.net
 */
final class LogEventDialog extends JDialog {
	
	private static final long serialVersionUID = 5266842801576134828L;
	
	private static final Font LABEL_FONT = new Font(null, Font.BOLD, 12);
	
	private static final Insets INSET_LEFT = new Insets(0, 0, 2, 3);
	private static final Insets INSET_RIGHT = new Insets(0, 0, 2, 0);
	
	private final PtLog4jEvent event;
	
	public LogEventDialog(final PtLog4jEvent event) {
		this.event = event;
		this.setTitle("Log Event Details");
		this.getContentPane().add(this.initComponents());
		this.pack();
		this.setLocationRelativeTo(null);
	}
	
	private JPanel initComponents() {
		final JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		final GridBagLayout layout = new GridBagLayout();
		final GridBagConstraints c = new GridBagConstraints();
		layout.setConstraints(panel, c);
		panel.setLayout(layout);
		
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridx = 0;
		c.gridy = 0;

		this.addRow("Date", LogTableColumn.DATE_FORMAT.format(this.event.getDate()), c, panel);
		this.addRow("Level", this.event.getLevel().toString(), c, panel);
		this.addRow("Class", this.event.getLogClassName(), c, panel);
		this.addRow("Method", this.event.getLogMethod(), c, panel);
		this.addRow("Thread", this.event.getThreadName(), c, panel);
		final JTextArea textAreaMessage = new JTextArea(this.event.getMessageRendered(), 3, 60);
		textAreaMessage.setEditable(false);
		this.addRow("Message", new JScrollPane(textAreaMessage), c, panel, 0.0, 0.3, GridBagConstraints.BOTH);
		final String exceptionString = this.event.getThrowable() != null ?
				PtStringUtil.convertExceptionToString(this.event.getThrowable(), true) :
				"N/A";
		final JTextArea textAreaException = new JTextArea(exceptionString, 3, 60);
		textAreaException.setEditable(false);
		this.addRow("Exception", new JScrollPane(textAreaException), c, panel, 0.0, 0.3, GridBagConstraints.BOTH);
		
		return panel;
	}
	
	private void addRow(final String label, final String value, final GridBagConstraints c, final JPanel panel) {
		this.addRow(label, new JLabel(value), c, panel, 0.0, 0.0, GridBagConstraints.NONE);
	}
	
	private void addRow(final String label, final JComponent comp, final GridBagConstraints c, final JPanel panel,
			final double weighty1, final double weighty2, final int fillMode) {
		
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.0;
		c.weighty = weighty1;
		c.gridx = 0;
		c.gridy++;
		final JLabel lbl = new JLabel(label);
		lbl.setFont(LABEL_FONT);
		c.insets = INSET_LEFT;
		panel.add(lbl, c);
		
		c.fill = fillMode;
		c.weightx = 1.0;
		c.weighty = weighty2;
		c.gridx = 1;
		c.insets = INSET_RIGHT;
		panel.add(comp, c);
	}
}
