package net.sourceforge.jpotpourri.jpotface;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtBanner extends JPanel {

	private static final long serialVersionUID = -7183344553915266565L;

	public PtBanner(final ImageIcon imageIcon) {
		this(imageIcon, PtBannerPosition.LEFT);
	}

	public PtBanner(final ImageIcon imageIcon, final PtBannerPosition position) {
		this.setBackground(Color.WHITE);
		this.setOpaque(true);
		this.setLayout(new BorderLayout());
		final String borderLayoutPosition = position == PtBannerPosition.LEFT ?
				BorderLayout.WEST :
				BorderLayout.EAST;
		this.add(new JLabel(imageIcon), borderLayoutPosition);
	}

	/**
	 * @author christoph_pickl@users.sourceforge.net
	 */
	public static enum PtBannerPosition {
		LEFT, RIGHT;
	}
}
