package net.sourceforge.jpotpourri.jpotface.inputfield;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtNumberIntegerSpinner extends JSpinner {

	private static final long serialVersionUID = -7675197955150191561L;

	public PtNumberIntegerSpinner(
			final int value,
			final int minimum,
			final int maximum,
			final int stepSize
			) {
		super(new SpinnerNumberModel(value, minimum, maximum, stepSize));
	}
	
	public int getNumber() {
		return ((Integer) this.getValue()).intValue();
	}
	
	public void setNumber(final int number) {
		this.setValue(new Integer(number));
	}
}
