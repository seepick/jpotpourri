package net.sourceforge.jpotpourri.jpotface.button;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;

import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtContextMenuButtonTest {

	private static final String ID_BUTTON = "ID_BUTTON";
	private static final String ID_CTX_FOO = "ID_CTX_FOO";
	private static final String ID_CTX_BAR = "ID_CTX_BAR";
	
	private FrameFixture fixture;

	private int clickedFoo;
	private int clickedBar;
	
	@Before
	public void setUp() {
		this.fixture = new FrameFixture(newFrame());
		this.fixture.show();
	}
	
	@After
	public void tearDown() {
		this.fixture.cleanUp();
	}
	
	@Test
	public void buttonTest() {
		this.fixture.button(ID_BUTTON).click();
		this.fixture.menuItem(ID_CTX_FOO).click();
		this.fixture.button(ID_BUTTON).click();
		this.fixture.menuItem(ID_CTX_BAR).click();

		assertEquals(1, this.clickedFoo);
		assertEquals(1, this.clickedBar);
	}
	
	private JFrame newFrame() {
		final Icon icon = null;
		
		final List<JMenuItem> popupItems = new ArrayList<JMenuItem>(2);
		popupItems.add(newMenuItem("foo", "CMD_FOO", ID_CTX_FOO));
		popupItems.add(newMenuItem("bar", "CMD_BAR", ID_CTX_BAR));
		final ActionListener listener = new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				final String cmd = e.getActionCommand(); 
				if(cmd.equals("CMD_FOO")) {
					PtContextMenuButtonTest.this.clickedFoo++;
				} else if(cmd.equals("CMD_BAR")) {
					PtContextMenuButtonTest.this.clickedBar++;
				} else {
					fail("Unhandled action command [" + cmd + "]!");
				}
			}
		};
		
		final PtContextMenuButton button = new PtContextMenuButton(icon, popupItems, listener);
		button.setName(ID_BUTTON);
		button.setText("context button");
		
		final JFrame frame = new JFrame();
		frame.getContentPane().add(button);
		frame.pack();
		
		return frame;
	}
	
	private static JMenuItem newMenuItem(final String text, final String actionCommand, final String name) {
		final JMenuItem item = new JMenuItem();
		item.setText(text);
		item.setActionCommand(actionCommand);
		item.setName(name);
		return item;
	}
	
}
