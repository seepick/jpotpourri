package net.sourceforge.jpotpourri.jcairngorm.component;

import javax.swing.JTextField;

import net.sourceforge.jpotpourri.jcairngorm.bindobj.PtBindableLong;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtBTextFieldLong extends PtBTextFieldGeneric<Long, PtBindableLong> {

	public PtBTextFieldLong(
			final JTextField textField,
			final PtBindableLong value
		) {
		super(textField, value);
	}
}
