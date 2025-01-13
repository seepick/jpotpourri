package net.sourceforge.jpotpourri.jpotface.toolbar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.sourceforge.jpotpourri.jpotface.PtImageInfo;
import net.sourceforge.jpotpourri.jpotface.util.PtGuiUtil;
import net.sourceforge.jpotpourri.jpotface.util.PtImageUtil;
/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
class ToolbarPanel extends JPanel {

	private static final long serialVersionUID = -1252497144319795776L;

	public ToolbarPanel(final List<IPtToolbarItem> items, final ActionListener actionListener) {
		final GridBagLayout layout = new GridBagLayout();
		final GridBagConstraints c = new GridBagConstraints();
		layout.setConstraints(this, c);
		this.setLayout(layout);

		
		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		for (IPtToolbarItem item : items) {
			this.add(this.newToolbarItemPanel(item, actionListener), c);
			c.gridy++;
		}
	}

//	@Override
//    public void paintComponent(final Graphics g) {
//		super.paintComponent(g);
//		System.out.println(this.getSize());
//    }
	
	private JPanel newToolbarItemPanel(final IPtToolbarItem item, final ActionListener actionListener) {
		final JPanel panel = new JPanel(new BorderLayout());

		panel.setOpaque(false);
		
		final ResizableImageButton btn = new ResizableImageButton(this, item);
		btn.addActionListener(actionListener);
		
		panel.add(btn, BorderLayout.CENTER);
//		panel.add(new JLabel(item.getImageIcon(), JLabel.CENTER), BorderLayout.SOUTH);
		
		
		return panel;
	}

	/**
	 * 
	 * @author christoph_pickl@users.sourceforge.net
	 */
	private static class ResizableImageButton extends JButton {
		private static final long serialVersionUID = 5311413134892569631L;

		private final JPanel parent;
		private final ImageIcon icon;
		
//		public ResizableImageButton(final JPanel parent, final ImageIcon icon) {
//			this(parent, icon, null);
//		}
		
//	x	public void paint(final Graphics gg) {
//	x		super.paint(gg);
//	x		System.out.println("paint img btn");
////x			this.icon.
//	x		Toolkit.getDefaultToolkit().createImage(arg0)
//	x	}

		// TODO das richtet sich daweil nicht aus... muss man erst haendisch schreiben!
		@Override
	    public void paintComponent(final Graphics g) {
			final PtImageInfo info = PtImageUtil.getResizedImage(this.icon.getImage(), this,
					this.parent.getSize().width, this.parent.getSize().height);
			g.drawImage(info.getImage(), 0, 0, null);
			
			final Dimension newSize = new Dimension(
					info.getWidthHeight().getWidth(), info.getWidthHeight().getHeight()
				);
//			this.setPreferredSize(newSize);
			this.setSize(newSize);
	    }
		
		public ResizableImageButton(final JPanel parent, final IPtToolbarItem item) {
			super(item.getImageIcon());
			if(item.getImageIcon() == null) {
				throw new NullPointerException("icon");
			}
			this.icon = item.getImageIcon();
			this.parent = parent;

			this.setOpaque(false);
			this.setBorderPainted(false);
			if(item.getActionCommand() != null) {
				this.setActionCommand(item.getActionCommand());
			}
			if(item.getTooltipText() != null) {
				this.setToolTipText(item.getTooltipText());
			}
			PtGuiUtil.enableHandCursor(this);
		}
	}
}
