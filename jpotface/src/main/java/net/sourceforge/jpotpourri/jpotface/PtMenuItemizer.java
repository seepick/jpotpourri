package net.sourceforge.jpotpourri.jpotface;

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtMenuItemizer {

	private PtMenuItemizer() {
		// no instantiation
	}


	public static JMenuItem newMenuItem(
			final String label
			// actionCommand
			// actionListener
			// keyStroke
			// menu
			) {
		return newMenuItem(label, null, null, null, null);
	}

	public static JMenuItem newMenuItem(
			final String label,
			final String actionCommand
			// actionListener
			// keyStroke
			// menu
			) {
		return newMenuItem(label, actionCommand, null, null, null);
	}

	public static JMenuItem newMenuItem(
			final String label,
			final String actionCommand,
			// actionListener
			// keyStroke
			final JMenu menu
			) {
		return newMenuItem(label, actionCommand, null, null, menu);
	}

	public static JMenuItem newMenuItem(
			final String label,
			final String actionCommand,
			final ActionListener actionListener,
			// keyStroke
			final JMenu menu
			) {
		return newMenuItem(label, actionCommand, actionListener, null, menu);
	}

	public static JMenuItem newMenuItem(
			final String label,
			final String actionCommand,
			final ActionListener actionListener,
			final String keyStroke,
			final JMenu menu
			) {
		return newMenuItem(label, actionCommand, actionListener, keyStroke, null, menu);
	}
	
	public static JMenuItem newMenuItem(
			final String label,
			final String actionCommand,
			final ActionListener actionListener,
			final String keyStroke,
			final Character mnemonic,
			final JMenu menu
			) {
		final JMenuItem item = new JMenuItem(label);
		
		if(actionCommand != null) {
			item.setActionCommand(actionCommand);
		}
		if(actionListener != null) {
			item.addActionListener(actionListener);
		}
		
		if(menu != null) {
			menu.add(item);
		}
		
		if(keyStroke != null) {
			item.setAccelerator(KeyStroke.getKeyStroke(keyStroke));
		}
		
		if(mnemonic != null) {
			item.setMnemonic(mnemonic.charValue());
		}
		return item;
	}
}
