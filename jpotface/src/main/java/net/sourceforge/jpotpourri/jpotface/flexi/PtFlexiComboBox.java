package net.sourceforge.jpotpourri.jpotface.flexi;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

/**
 * Flexi Combo box class porvides options for showing Horizontal Scrollbar, View
 * Tips and Widening the Popup
 * @author Vimal;
 * http://javabyexample.wisdomplug.com/java-concepts/34-core-java/
 *      59-tips-and-tricks-for-jtree-jlist-and-jcombobox-part-i.html
 */
public class PtFlexiComboBox extends JComboBox {

	private static final long serialVersionUID = -6636780961012342920L;

	/**
	 * Whether ViewTips should be shown.
	 */
	private boolean showTips = false;

	/**
	 * Set the popup Width
	 */
	private int popupWidth = 0;

	/**
	 * Keep track of whether layout is happening
	 */
	private boolean layingOut = false;

	/**
	 * Display the horizontal scrollbar
	 */
	private boolean showHSCroller = false;

	/**
	 * Default Constructor Creates a default flexi Combobox
	 */
	public PtFlexiComboBox() {
		super();
		setUI(new FlexiComboUI());
	}

	/**
	 * Creates a Flexi Combobox with the given Model
	 * @param model
	 * The Combobox Model
	 */
	public PtFlexiComboBox(final ComboBoxModel model) {
		super(model);
		setUI(new FlexiComboUI());
	}

	/**
	 * Creates a Flexi Combobox with the given items
	 * @param items
	 * the Items to be added to the Combo Box
	 */
	public PtFlexiComboBox(final Object[] items) {
		super(items);
		setUI(new FlexiComboUI());
	}

	/**
	 * Creates a Flexi Combobox with the given items
	 * @param items
	 * the Items to be added to the Combo Box
	 */
	public PtFlexiComboBox(final Vector<?> items) {
		super(items);
		setUI(new FlexiComboUI());
	}

	/**
	 * Overriden to handle the popup Size
	 */
	@Override
	public void doLayout() {
		try {
			this.layingOut = true;
			super.doLayout();
		} finally {
			this.layingOut = false;
		}
	}

	/**
	 * Overriden to handle the popup Size
	 */
	@Override
	public Dimension getSize() {
		Dimension dim = super.getSize();
		if (!this.layingOut && this.popupWidth != 0) {
			dim.width = this.popupWidth;
		}
		return dim;
	}

	/**
	 * @return the showTips
	 */
	public boolean isShowTips() {
		return this.showTips;
	}

	/**
	 * @param showTips
	 * the showTips to set
	 */
	public void setShowTips(final boolean showTips) {
		this.showTips = showTips;
		if (showTips) {
			PtViewTooltips.register(((FlexiComboUI) getUI()).getPopup().getList());
		} else {
			PtViewTooltips.unregister(((FlexiComboUI) getUI()).getPopup().getList());
		}
	}

	/**
	 * @return the popupWidth
	 */
	public int getPopupWidth() {
		return this.popupWidth;
	}

	/**
	 * @param popupWidth
	 * the popupWidth to set
	 */
	public void setPopupWidth(final int popupWidth) {
		this.popupWidth = popupWidth;
	}

	/**
	 * @return the showHSCroller
	 */
	public boolean isShowHSCroller() {
		return this.showHSCroller;
	}

	/**
	 * @param showHSCroller
	 * the showHSCroller to set
	 */
	public void setShowHSCroller(final boolean showHSCroller) {
		this.showHSCroller = showHSCroller;
		if (showHSCroller) {
			((FlexiComboUI) getUI()).getPopup().getScrollPane()
					.setHorizontalScrollBarPolicy(
							ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		} else {
			((FlexiComboUI) getUI()).getPopup().getScrollPane()
					.setHorizontalScrollBarPolicy(
							ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		}
	}

	/**
	 * @author Vimal
	 */
	private class FlexiComboUI extends BasicComboBoxUI {

		@Override
		protected ComboPopup createPopup() {
			return new FlexiComboPopup(this.comboBox);
		}

		public FlexiComboPopup getPopup() {
			return (FlexiComboPopup) this.popup;
		}
	}

	/**
	 * 
	 * @author christoph_pickl@users.sourceforge.net
	 */
	private class FlexiComboPopup extends BasicComboPopup {

		private static final long serialVersionUID = 4890905534316787190L;

//		private Dimension size = null;

		public FlexiComboPopup(final JComboBox combo) {
			super(combo);
			if (PtFlexiComboBox.this.showTips) {
				PtViewTooltips.register(this.list);
			} else {
				PtViewTooltips.unregister(this.list);
			}
		}

		@Override
		public JScrollPane createScroller() {
			JScrollPane newScroller = null;

			if (PtFlexiComboBox.this.showHSCroller) {
				newScroller = new JScrollPane(this.list,
						ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
						ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			} else {

				newScroller = new JScrollPane(this.list,
						ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
						ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			}
			return newScroller;
		}

		public JScrollPane getScrollPane() {
			return this.scroller;
		}

		@Override
		public JList getList() {
			return this.list;
		}
	}

}
