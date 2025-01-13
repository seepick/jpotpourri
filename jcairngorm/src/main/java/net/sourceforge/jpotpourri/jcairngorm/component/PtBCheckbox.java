package net.sourceforge.jpotpourri.jcairngorm.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import net.sourceforge.jpotpourri.jcairngorm.bindobj.PtBindableBoolean;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtBCheckbox {

	private final JCheckBox checkBox;
	
	public PtBCheckbox(
			final JCheckBox checkBox,
			final PtBindableBoolean bool) {
		
		this.checkBox = checkBox;
		
		// set bindable value to preset state's value
		if(bool.getValue().booleanValue() != this.checkBox.isSelected()) {
			bool.setValue(this.checkBox.isSelected());
		}
		
		this.checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				bool.setValue(PtBCheckbox.this.checkBox.isSelected());
			}
		});
		
	}
	
}
