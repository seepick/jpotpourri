package net.sourceforge.jpotpourri.jpotface.inputfield;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtNumberDoubleSpinner extends JSpinner {

	private static final long serialVersionUID = -7675197955150191561L;

	public PtNumberDoubleSpinner(
			final double value,
			final double minimum,
			final double maximum,
			final double stepSize
			) {
		super(new SpinnerNumberModel(value, minimum, maximum, stepSize));
	}
	
	public double getNumber() {
		return ((Double) this.getValue()).doubleValue();
	}
	
	public void setNumber(final double number) {
		this.setValue(new Double(number));
	}
}
