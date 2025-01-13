package net.sourceforge.jpotpourri.jpotface.flexi;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author http://javabyexample.wisdomplug.com/java-concepts/34-core-java/
 * 				59-tips-and-tricks-for-jtree-jlist-and-jcombobox-part-i.html
 *
 */
class FlexiComboTest extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6448487530620744983L;

	@Override
	public void init() {

		JPanel control = new JPanel();

		control.setLayout(new GridBagLayout());

		final PtFlexiComboBox box = new PtFlexiComboBox(new String[] {

		"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",

		"bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb",

		"ccccccccccccccccccccccccccccccccccccccccccc",

		"ddddddddddddddddddddddddddddddddddddddddddd",

		"eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",

		});

		GridBagConstraints con = new GridBagConstraints();

		con.gridwidth = 2;

		con.anchor = GridBagConstraints.NORTHWEST;

		final JCheckBox tips = new JCheckBox("Show View Tips");

		tips.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {

				box.setShowTips(tips.isSelected());

			}

		});

		control.add(tips, con);

		con.gridy = 1;

		final JCheckBox scroll = new JCheckBox("Show Horizontal Scroll");

		scroll.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {

				box.setShowHSCroller(scroll.isSelected());

			}

		});

		control.add(scroll, con);

		final JTextField text = new JTextField("0");

		text
				.setPreferredSize(new Dimension(100,
						text.getPreferredSize().height));

		text.setMaximumSize(new Dimension(100, text.getPreferredSize().height));

		text.setMinimumSize(new Dimension(100, text.getPreferredSize().height));

		final JButton button = new JButton("Resize Popup");

		button.setToolTipText("Use 0 for default width");

		text.setToolTipText("Use 0 for default width");

		button.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {

				String val = text.getText();

				try {

					int i = Integer.parseInt(val);

					box.setPopupWidth(i);

				} catch (NumberFormatException ne) {

					JOptionPane.showMessageDialog(FlexiComboTest.this, "'"
							+ val + "' is not a valid number", "Error",
							JOptionPane.ERROR_MESSAGE);

				}

			}

		});

		con.gridy = 2;

		con.gridwidth = 1;

		con.weightx = .7;

		control.add(text, con);

		con.gridx = 1;

		con.weightx = .3;

		control.add(button, con);

		con.gridy = 3;

		con.gridx = 0;

		con.weightx = 1;

		con.gridwidth = 2;

		con.insets.top = 3;

		con.insets.left = 3;

		con.insets.bottom = 3;

		con.insets.right = 3;

		control.add(box, con);

		box.setBorder(BorderFactory.createTitledBorder("Combo:"));

		setLayout(new BorderLayout());

		add(control, BorderLayout.CENTER);

		super.init();

	}

	public static void main(final String[] args) {

		JFrame frame = new JFrame("Flexi Combo");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		FlexiComboTest applet = new FlexiComboTest();

		applet.init();

		frame.add(applet);

		frame.setSize(250, 160);

		frame.setVisible(true);

	}

}
