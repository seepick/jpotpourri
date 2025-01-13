package net.sourceforge.jpotpourri.jpotface.toolbar;

import javax.swing.ImageIcon;
/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtDefaultToolbarItem implements IPtToolbarItem {

	private final String label;
	private final ImageIcon imageIcon;
	private final String actionCommand;
	private final String toString;
	
	public PtDefaultToolbarItem(final String label, final ImageIcon imageIcon, final String actionCommand) {
		this.label = label;
		this.imageIcon = imageIcon;
		this.actionCommand = actionCommand;
		this.toString = "PtDefaultToolbarItem[" +
				"label=" + this.label + ";" +
				"imageIcon=" + this.imageIcon + ";" +
				"actionCommand=" + this.actionCommand + ";" +
				"]";
	}

	@Override
	public String toString() {
		return this.toString;
	}

	/** IPtToolbarItem */
	public String getActionCommand() {
		return this.actionCommand;
	}

	/** IPtToolbarItem */
	public ImageIcon getImageIcon() {
		return this.imageIcon;
	}

	/** IPtToolbarItem */
	public String getTooltipText() {
		return this.label;
	}
	
}
