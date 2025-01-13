package net.sourceforge.jpotpourri.jpotface.inputfield;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtLabeledCheckbox extends JCheckBox {

	private static final long serialVersionUID = 2024920956271300038L;

	private static final String LABEL_ENABLED = "enabled";
	
	private static final String LABEL_DISABLED = "disabled";

	
	public PtLabeledCheckbox() {
		this.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				PtLabeledCheckbox.this.updateLabel();
			}
		});
	}

	public void updateLabel() {
		this.setText(this.isSelected() ? LABEL_ENABLED : LABEL_DISABLED);
	}
}
