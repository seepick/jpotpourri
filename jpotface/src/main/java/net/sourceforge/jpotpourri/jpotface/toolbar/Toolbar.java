package net.sourceforge.jpotpourri.jpotface.toolbar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
class Toolbar implements IPtToolbar {

	private final Map<String, IPtToolbarItem> toolbarItems;
	private final Set<IPtToolbarListener> toolbarListeners = new HashSet<IPtToolbarListener>();
	private final JSplitPane splitPane;
	private final ToolbarPanel toolbarPanel;
	
	public Toolbar(final JPanel contentPanel, final List<IPtToolbarItem> items) {
		this.toolbarPanel = new ToolbarPanel(items, new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				Toolbar.this.broadcastToolbarClicked(event.getActionCommand());
			}
		});
		
//		this.toolbarPanel.setBackground(Color.RED);
		
//		this.toolbarPanel.setMaximumSize(new Dimension(60, 60)); // TODO setting max does not work
//		this.toolbarPanel.setPreferredSize(new Dimension(40, 40));
		this.toolbarPanel.setMinimumSize(new Dimension(30, 0));
		
		final Map<String, IPtToolbarItem> tmp = new HashMap<String, IPtToolbarItem>();
		for (final IPtToolbarItem item : items) {
			if(item.getActionCommand() == null) {
				throw new NullPointerException("IPtToolbarItem.actionCommand");
			}
			final IPtToolbarItem previousStored = tmp.put(item.getActionCommand(), item);
			if(previousStored != null) {
				throw new IllegalArgumentException(
						"Duplicate IPtToolbarItem.actionCommand [" + item.getActionCommand() + "]!");
			}
		}
		this.toolbarItems = Collections.unmodifiableMap(tmp);
		

		
		final boolean newContinuousLayout = true;
		this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, newContinuousLayout,
				this.toolbarPanel, contentPanel);
        
		this.splitPane.setBorder(null);
		this.splitPane.setDividerLocation(-1);
		// default: right component gets free space, this.splitPane.setResizeWeight(0)
		this.splitPane.setDividerSize(2);
		this.splitPane.setBackground(Color.BLACK);

//		frame.addComponentListener(new ComponentAdapter() {
//			public void componentResized(ComponentEvent event) {
//				System.out.println(frame.getWidth());
//				// this.splitPane.setDividerLocation(700);
//			}
//		});
	}
	
	private void broadcastToolbarClicked(final String actionCommand) {
		final IPtToolbarItem item = this.toolbarItems.get(actionCommand);
		assert(item != null);
		
		for (final IPtToolbarListener listener : this.toolbarListeners) {
			listener.doToolbarClicked(item);
		}
	}
	
	public void setBackgroundColor(final Color backgroundColor) {
		this.toolbarPanel.setBackground(backgroundColor);
	}
	
	
	public JComponent asJComponent() {
		return this.splitPane;
	}
	
	

	/** IPtToolbar */
	public boolean addIPtToolbarListener(final IPtToolbarListener listener) {
		return this.toolbarListeners.add(listener);
	}

	/** IPtToolbar */
	public boolean removeIPtToolbarListener(final IPtToolbarListener listener) {
		return this.toolbarListeners.remove(listener);
	}
}
