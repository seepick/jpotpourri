package net.sourceforge.jpotpourri.jpotface.toolbar;

import java.awt.Color;
import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public final class ToolbarPlayground {

	private ToolbarPlayground() {
		// no instantiation
	}


	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initGui();
			}
		});
	}

	private static final String CMD_BRAZIL = "CMD_BRAZIL";
	private static final String CMD_CHINA = "CMD_CHINA";
	private static final String CMD_FRANCE = "CMD_FRANCE";
	
	private static final Map<String, JPanel> PANELS = new HashMap<String, JPanel>();
	private static final JPanel PANEL_BRAZIL = new JPanel();
	private static final JPanel PANEL_CHINA = new JPanel();
	private static final JPanel PANEL_FRANCE = new JPanel();
	static {
		PANEL_BRAZIL.add(new JLabel("this is brazil."));
		PANEL_CHINA.add(new JLabel("welcome to china."));
		PANEL_FRANCE.add(new JLabel("i -the frenchman- like soup."));
		PANELS.put(CMD_BRAZIL, PANEL_BRAZIL);
		PANELS.put(CMD_CHINA, PANEL_CHINA);
		PANELS.put(CMD_FRANCE, PANEL_FRANCE);
	}
	
	private static final JPanel CONTENT_PANEL = new JPanel();
	
	private static void initGui() {
		final JFrame f = new JFrame();
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		CONTENT_PANEL.add(PANEL_BRAZIL);
		
		
		final List<IPtToolbarItem> items = new LinkedList<IPtToolbarItem>();
		items.add(new PtDefaultToolbarItem("Brazil", loadImageIcon("Brazil.gif"), CMD_BRAZIL));
		items.add(new PtDefaultToolbarItem("China", loadImageIcon("China.gif"), CMD_CHINA));
		items.add(new PtDefaultToolbarItem("France", loadImageIcon("France.gif"), CMD_FRANCE));
		final IPtToolbar tb = PtToolbarFactory.newPtToolbar(CONTENT_PANEL, items);
		
		tb.addIPtToolbarListener(new IPtToolbarListener() {
			public void doToolbarClicked(final IPtToolbarItem item) {
				System.out.println("clicked on toolbar item: " + item);
				CONTENT_PANEL.removeAll();
				CONTENT_PANEL.add(PANELS.get(item.getActionCommand()));
				CONTENT_PANEL.revalidate();
				CONTENT_PANEL.repaint();
			}
		});
		tb.setBackgroundColor(Color.RED);
		
		f.getContentPane().add(tb.asJComponent());
		f.pack();
		f.setVisible(true);
	}
	
	public static ImageIcon loadImageIcon(final String imageFileName) {
//		return new ImageIcon(Toolkit.getDefaultToolkit().getImage(ToolbarPlayground.class.getResource(imagePath)));
		try {
			return new ImageIcon(new File("/delme/"+imageFileName).toURL());
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
}
