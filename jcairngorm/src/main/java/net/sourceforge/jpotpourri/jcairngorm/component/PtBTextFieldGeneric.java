package net.sourceforge.jpotpourri.jcairngorm.component;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import net.sourceforge.jpotpourri.jcairngorm.IPtBindingListener;
import net.sourceforge.jpotpourri.jcairngorm.bindobj.IPtBindableStringLike;
import net.sourceforge.jpotpourri.jcairngorm.event.PtBindingEvent;

/**
 * @param <T> string like bindable type
 * @param <O> actual object, which should be bound
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtBTextFieldGeneric<O, T extends IPtBindableStringLike<O>> implements IPtBindingListener<O> {
	
	// TODO extends AbstractEventComponentSkeleton ... if enter pressed
	
	private final JTextField textField;

//	public PtBTextField(
//			final JTextField textField,
//			final PtBindableString string
//		) {
//		this(null, textField, string);
//	}
	
	public PtBTextFieldGeneric(
//			final PtAbstractCairngormEvent<?> event,
			final JTextField textField,
			final T value
		) {
		this.textField = textField;
		this.textField.setText(value.getAsString());
		
		this.textField.addKeyListener(new KeyListener() {

			private String tmp;
			
			public void keyPressed(final KeyEvent e) {
				this.tmp = textField.getText();
			}

			public void keyReleased(final KeyEvent e) {
				if(this.tmp.equals(textField.getText()) == false) {
					value.setAsString(textField.getText()); // enable binding
				}
			}

			public void keyTyped(final KeyEvent e) {
				// nothing to do
			}
			
		});
		
		value.addBindingListener(this);
	}
	
	public void receiveEvent(final PtBindingEvent<O> event) {
		final String newString = event.getNewValue().toString();
		
		if(this.textField.getText().equals(newString) == false) {
			this.textField.setText(newString);
		} // dont update textfield if string is the same (broadcasted by component itself in keyReleased method)
		
	}
	
}
