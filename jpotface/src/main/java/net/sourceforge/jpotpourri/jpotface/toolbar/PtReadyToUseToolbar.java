package net.sourceforge.jpotpourri.jpotface.toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PtReadyToUseToolbar {

	private final Map<String, JPanel> panelMap = new HashMap<String, JPanel>();
	
	private final JPanel wrapPanel = new JPanel();
	
	private final JComponent toolbarComponent;
	
	private JFrame repackFrame;
	
	
	public PtReadyToUseToolbar(final List<PtToolbarItemAndContentPanel> content) {
		final List<IPtToolbarItem> toolbarItems = new ArrayList<IPtToolbarItem>(content.size());
		
		for (PtToolbarItemAndContentPanel c : content) {
			toolbarItems.add(c.getToolbarItem());
			this.panelMap.put(c.getToolbarItem().getActionCommand(), c.getContentPanel());
		}
		
		this.wrapPanel.add(content.get(0).getContentPanel());
		final IPtToolbar tb = PtToolbarFactory.newPtToolbar(this.wrapPanel, toolbarItems);
		
		tb.addIPtToolbarListener(new IPtToolbarListener() {
			public void doToolbarClicked(final IPtToolbarItem item) {
				PtReadyToUseToolbar.this.toolbarClicked(item);
			}
		});
		
		this.toolbarComponent = tb.asJComponent();
	}
	
	private void toolbarClicked(final IPtToolbarItem item) {
		this.wrapPanel.removeAll();
		this.wrapPanel.add(this.panelMap.get(item.getActionCommand()));
		this.wrapPanel.revalidate();
		this.wrapPanel.repaint();
		
		if(this.repackFrame != null) {
			this.repackFrame.pack();
		}
	}
	
	public void setRepackFrame(final JFrame repackFrame) {
		this.repackFrame = repackFrame;
	}
	
	public JComponent asJComponent() {
		return this.toolbarComponent;
	}
	
}
