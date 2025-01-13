package net.sourceforge.jpotpourri.jpotface.memory;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import net.sourceforge.jpotpourri.jpotface.chooser.AbstractFileDirectoryChooser;
import net.sourceforge.jpotpourri.jpotface.inputfield.PtNumberDoubleSpinner;
import net.sourceforge.jpotpourri.jpotface.inputfield.PtNumberIntegerSpinner;

/**
 * 
 * @param <T> key type
 * @author christoph_pickl@users.sourceforge.net
 */
public abstract class PtAbstractMemoryGuiStorage<T extends IPtMemoryKey<?>> extends PtAbstractMemoryStorage<T> {

//	public abstract void enableMemoryOn(
//			final T key,
//			final Object target,
//			final String getterMethodName,
//			final String setterMethodName,
//			final Class<?> propertyClass,
//			final boolean isPrimitive,
//			final Object defaultValue);
	
    // -----------------------------------------------------------------------------------------------------------------
    //  JTextField
    // -----------------------------------------------------------------------------------------------------------------
	public void enableMemoryOn(
		final T key,
		final JTextField target
		) {
		this.enableMemoryOn(key, target, "");
	}
	public void enableMemoryOn(
			final T key,
			final JTextField target,
			final String defaultValue
			) {
		this.enableMemoryOn(key, target, "getText", "setText", String.class, false, defaultValue);
	}
	

    // -----------------------------------------------------------------------------------------------------------------
    //  JCheckBox
    // -----------------------------------------------------------------------------------------------------------------
	public void enableMemoryOn(
		final T key,
		final JCheckBox target
		) {
		this.enableMemoryOn(key, target, false);
	}
	public void enableMemoryOn(
			final T key,
			final JCheckBox target,
			final boolean defaultValue
			) {
		this.enableMemoryOn(key, target, "isSelected", "setSelected", Boolean.class, true, defaultValue);
	}
	
    // -----------------------------------------------------------------------------------------------------------------
    //  JTabbedPane
    // -----------------------------------------------------------------------------------------------------------------
	public void enableMemoryOn(
		final T key,
		final JTabbedPane target
		) {
		this.enableMemoryOn(key, target, 0);
	}
	public void enableMemoryOn(
			final T key,
			final JTabbedPane target,
			final int defaultValue
			) {
		this.enableMemoryOn(key, target, "getSelectedIndex", "setSelectedIndex", int.class, true, defaultValue);
	}
	

    // -----------------------------------------------------------------------------------------------------------------
    //  ButtonGroup
    // -----------------------------------------------------------------------------------------------------------------
	public void enableMemoryOn(
		final T key,
		final ButtonGroup target,
		final JRadioButton... radioButtons
		) {
		this.enableMemoryOn(key, target, 0, radioButtons);
	}
	public void enableMemoryOn(
			final T key,
			final ButtonGroup target,
			final int defaultValue,
			final JRadioButton... radioButtons
			) {
		if(radioButtons.length != target.getButtonCount()) {
			throw new IllegalArgumentException("Passed [" + radioButtons.length + "] button keys, " +
					"expected [" + target.getButtonCount() + "]!");
		}
		final RadioButtonGroupContainer newTarget = new RadioButtonGroupContainer(target, radioButtons);
		this.enableMemoryOn(key, newTarget, "getSelectedButton", "setSelectedButton", int.class, true, defaultValue);
	}

	/**
	 * 
	 * @param <T> key type
	 * @author christoph_pickl@users.sourceforge.net
	 */
	static class RadioButtonGroupContainer {
		private final ButtonGroup group;
		private final JRadioButton[] radioButtons;
		public RadioButtonGroupContainer(final ButtonGroup group, final JRadioButton... radioButtons) {
			this.group = group;
			this.radioButtons = radioButtons;
		}
		public int getSelectedButton() {
			for (int i = 0; i < this.radioButtons.length; i++) {
				if(this.radioButtons[i].isSelected()) {
//					System.out.println("getSelectedButton(): " + i);
					return i;
				}
			}
			throw new RuntimeException("Not any button selected of ButtonGroup [" + this.group + "]!");
		}
		public void setSelectedButton(final int selectedIndex) {
//			System.out.println("setSelectedButton(selectedIndex=" + selectedIndex + ")");
			final JRadioButton button = this.radioButtons[selectedIndex];
			this.group.setSelected(button.getModel(), true);
		}
	}
	
	
	
	
	
	

    // -----------------------------------------------------------------------------------------------------------------
    //  PtDirectoryChooser
    // -----------------------------------------------------------------------------------------------------------------
	public void enableMemoryOn(
		final T key,
		final AbstractFileDirectoryChooser target
		) {
		this.enableMemoryOn(key, target, "");
	}
	public void enableMemoryOn(
			final T key,
			final AbstractFileDirectoryChooser target,
			final String defaultValue
			) {
		this.enableMemoryOn(key, target, "uncheckedGetFileOrDir", "uncheckedSetFileOrDir",
				String.class, false, defaultValue);
	}
	

    // -----------------------------------------------------------------------------------------------------------------
    //  PtNumberIntegerSpinner
    // -----------------------------------------------------------------------------------------------------------------
	public void enableMemoryOn(
		final T key,
		final PtNumberIntegerSpinner target
		) {
		this.enableMemoryOn(key, target, 0);
	}
	public void enableMemoryOn(
			final T key,
			final PtNumberIntegerSpinner target,
			final int defaultValue
			) {
		this.enableMemoryOn(key, target, "getNumber", "setNumber", int.class, true, defaultValue);
	}
	

    // -----------------------------------------------------------------------------------------------------------------
    //  PtNumberDoubleSpinner
    // -----------------------------------------------------------------------------------------------------------------
	public void enableMemoryOn(
		final T key,
		final PtNumberDoubleSpinner target
		) {
		this.enableMemoryOn(key, target, 0);
	}
	public void enableMemoryOn(
			final T key,
			final PtNumberDoubleSpinner target,
			final int defaultValue
			) {
		this.enableMemoryOn(key, target, "getNumber", "setNumber", double.class, true, defaultValue);
	}
	
	
	
	
}
