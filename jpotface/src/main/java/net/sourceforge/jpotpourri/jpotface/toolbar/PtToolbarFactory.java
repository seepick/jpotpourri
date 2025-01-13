package net.sourceforge.jpotpourri.jpotface.toolbar;

import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;
/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtToolbarFactory {

	private PtToolbarFactory() {
		// no instantiation
	}

	public static IPtToolbar newPtToolbar(final JPanel contentPanel, final List<IPtToolbarItem> items) {
		return new Toolbar(contentPanel, items);
	}

	public static IPtToolbar newPtToolbar(final JPanel contentPanel, final IPtToolbarItem... items) {
		return newPtToolbar(contentPanel, Arrays.asList(items));
	}
}
