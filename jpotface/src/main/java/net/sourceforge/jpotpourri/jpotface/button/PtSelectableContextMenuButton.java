package net.sourceforge.jpotpourri.jpotface.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JMenuItem;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtSelectableContextMenuButton extends PtContextMenuButton {

	private static final long serialVersionUID = 2844205041492573761L;


	private String recentSelectActionCommand;
	
	private final Map<String, JMenuItem> menuItemByCmd = new HashMap<String, JMenuItem>();
	
	
	public PtSelectableContextMenuButton(final Icon icon, final List<JMenuItem> popupItems,
			final ActionListener clickedListener) {
		super(icon, popupItems, clickedListener);
		
		final ActionListener myChangeListener =  new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				selectItem(PtSelectableContextMenuButton.this.menuItemByCmd.get(
						PtSelectableContextMenuButton.this.recentSelectActionCommand), false);
				selectItem(PtSelectableContextMenuButton.this.menuItemByCmd.get(
						event.getActionCommand()), true);
				
				PtSelectableContextMenuButton.this.recentSelectActionCommand = event.getActionCommand();
			}
		};
		
		
		
		for (JMenuItem menuItem : popupItems) {
			if(menuItem != null) {
				if(this.recentSelectActionCommand == null && menuItem.isEnabled() == true) {
					this.recentSelectActionCommand = menuItem.getActionCommand(); // default is first entry
					menuItem.setText("-  " + menuItem.getText());
				} else {
					menuItem.setText("  " + menuItem.getText());
				}
				this.menuItemByCmd.put(menuItem.getActionCommand(), menuItem);
				
				menuItem.addActionListener(myChangeListener);
			}
		}
	}

	public final String getSelectedActionCommand() {
		return this.recentSelectActionCommand;
	}
	
	private void selectItem(final JMenuItem item, final boolean select) {
		final String newLabel;
		if(select == true) {
			// MINOR GUI use JComboBox instead for popup menu (because it gots exact some logic like coded here)
			newLabel = "- " + item.getText().substring(2);
		} else {
			newLabel = "  " + item.getText().substring(2);
		}
		
		item.setText(newLabel);
	}

}
