package net.sourceforge.jpotpourri.jcairngorm.component.fireonly;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import net.sourceforge.jpotpourri.jcairngorm.event.PtAbstractCairngormEvent;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtBMenuItem extends AbstractEventComponentSkeleton {

	private final JMenuItem menuItem;
	
	public PtBMenuItem(final PtAbstractCairngormEvent<?> event, final JMenuItem button) {
		this.menuItem = button;
		
		this.menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				PtBMenuItem.this.dispatchEvent(event);
			}
		});
	}
	
}
