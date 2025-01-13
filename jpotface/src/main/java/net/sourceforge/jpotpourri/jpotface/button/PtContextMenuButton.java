package net.sourceforge.jpotpourri.jpotface.button;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import net.sourceforge.jpotpourri.jpotface.util.PtGuiUtil;


/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtContextMenuButton extends JButton {
	
	private static final long serialVersionUID = -2658781971359830733L;
	
	private final JPopupMenu popupMenu = new JPopupMenu();
	
	/**
	 * 
	 * @param popupItems if added a null-value in list -> will add a separator line
	 * @param listener attache dto every menu item; can be null
	 */
	public PtContextMenuButton(final Icon icon, final List<JMenuItem> popupItems, final ActionListener listener) {
		super(icon);
		
		this.setBorderPainted(false);
		PtGuiUtil.enableHandCursor(this);
		
		for (JMenuItem menuItem : popupItems) {
			if(menuItem == null) {
				this.getPopupMenu().addSeparator();
			} else {
				this.getPopupMenu().add(menuItem);
				if(listener != null) {
					menuItem.addActionListener(listener);
				}
			}
		}
		
		this.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				assert(event.getSource() == PtContextMenuButton.this);
				final Point p = PtContextMenuButton.this.getMousePosition();
				
				// would be dirty hack to get mouse position just because of existing GlassPane
				if(p == null) {
//System.out.println("Could not get mouse position, therefore going to use position relative to glass pane.");
//p = ContextMenuButton.this.getRootPane().getGlassPane().getMousePosition(); ... does NOT return correct position!
					throw new NullPointerException("Could not get mouse position! " +
							"(Maybe an existing GlassPane overlays content)");
				}
				PtContextMenuButton.this.getPopupMenu().show(PtContextMenuButton.this, p.x, p.y);
			}
		});
	}

	final JPopupMenu getPopupMenu() {
		return this.popupMenu;
	}
		
}

