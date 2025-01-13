package net.sourceforge.jpotpourri.jpotface.button;

import javax.swing.Icon;
import javax.swing.JButton;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtPressableButton extends JButton {

	private static final long serialVersionUID = 1784844960026759904L;

	public PtPressableButton(final Icon iconNormal, final Icon iconPressed) {
		super(iconNormal);
		this.setPressedIcon(iconPressed);

//		this.setRolloverEnabled(true);
//		this.setRolloverIcon(iconPressed);

		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setContentAreaFilled(false);
	}

}

