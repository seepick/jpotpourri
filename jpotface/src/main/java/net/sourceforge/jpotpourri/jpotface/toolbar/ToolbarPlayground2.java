package net.sourceforge.jpotpourri.jpotface.toolbar;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class ToolbarPlayground2 {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initGui();
			}
		});
	}
	
	private static void initGui() {
		List<PtToolbarItemAndContentPanel> content = new ArrayList<PtToolbarItemAndContentPanel>(3);

		content.add(newContent("Brazil", "CMD_BRAZIL"));
		content.add(newContent("China", "CMD_CHINA"));
		content.add(newContent("France", "CMD_FRANCE"));
		PtReadyToUseToolbar tb = new PtReadyToUseToolbar(content);
		
		JFrame f = new JFrame();
		tb.setRepackFrame(f);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.getContentPane().add(tb.asJComponent());
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
	private static PtToolbarItemAndContentPanel newContent(String lbl, String cmd) {
		PtDefaultToolbarItem tbItem = new PtDefaultToolbarItem(lbl, ToolbarPlayground.loadImageIcon(lbl+".gif"), cmd);
		JPanel tbPanel = new JPanel();
		tbPanel.add(new JLabel("Welcome to " + lbl + (lbl.equals("China") ? "aaaaaaaaaa" : "")));
		return new PtToolbarItemAndContentPanel(tbItem, tbPanel);
	}
}
