package net.sourceforge.jpotpourri.jpotface.toolbar;

import javax.swing.JPanel;

public final class PtToolbarItemAndContentPanel {
	
	private final IPtToolbarItem toolbarItem;
	private final JPanel contentPanel;
	
	
	public PtToolbarItemAndContentPanel(IPtToolbarItem toolbarItem, JPanel contentPanel) {
		this.toolbarItem = toolbarItem;
		this.contentPanel = contentPanel;
	}
	
	
	public IPtToolbarItem getToolbarItem() {
		return this.toolbarItem;
	}
	public JPanel getContentPanel() {
		return this.contentPanel;
	}
}