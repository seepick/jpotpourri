package net.sourceforge.jpotpourri.jcairngorm.component.fireonly;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import net.sourceforge.jpotpourri.jcairngorm.event.PtAbstractCairngormEvent;


/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtBButton extends AbstractEventComponentSkeleton {

	private final JButton button;
	
	public PtBButton(final PtAbstractCairngormEvent<?> event, final JButton button) {
		this.button = button;
		
		this.button.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				PtBButton.this.dispatchEvent(event);
			}
		});
	}
	
}
