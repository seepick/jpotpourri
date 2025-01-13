package net.sourceforge.jpotpourri.jcairngorm.component;

import javax.swing.JTextField;

import net.sourceforge.jpotpourri.jcairngorm.bindobj.PtBindableString;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtBTextField extends PtBTextFieldGeneric<String, PtBindableString> {

	public PtBTextField(
			final JTextField textField,
			final PtBindableString string
		) {
		super(textField, string);
	}
}
