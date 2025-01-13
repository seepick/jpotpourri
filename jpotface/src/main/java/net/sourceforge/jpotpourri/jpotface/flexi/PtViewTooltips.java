package net.sourceforge.jpotpourri.jpotface.flexi;

/* 
 * The contents of this file are subject to the terms of the Common Development and Distribution License (the License).
 * You may not use this file except in compliance with the License.
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html or http://www.netbeans.org/cddl.txt.
 * When distributing Covered Code, include this CDDL Header Notice in each file and include the License file at
 * http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by your own identifying information:
 * 
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Copyright 2006 Sun Microsystems, all rights reserved.
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

/**
 * Displays pseudo-tooltips for tree and list views which don't have enough
 * space.  This class is not NB specific, and can be used with any
 * JTree or JList.
 *
 * @author Tim Boudreau; http://javabyexample.wisdomplug.com/java-concepts/
 * 				34-core-java/59-tips-and-tricks-for-jtree-jlist-and-jcombobox-part-i.html
 * @modified Vimal
 */
public final class PtViewTooltips extends MouseAdapter implements MouseMotionListener { // implements MouseMotionListener necessary for Java5!
	
	// FIXME use this gui util for all jpot lists/trees!
	// ... continous tips could also be usefull for ColumnLimitedTextFields
	
	// TODO enable ViewTooltips for JTable also

	
	/** The default instance, reference counted */
	private static PtViewTooltips instance = null;

	/** A reference count for number of comps listened to */
	private int refcount = 0;

	/** The last known component we were invoked against, nulled on hide() */
	private JComponent inner = null;

	/** The last row we were invoked against */
	private int row = -1;

	/** An array of currently visible popups */
	private Popup[] popups = new Popup[2];

	/** A component we'll reuse to paint into the popups */
	private ImgComp painter = new ImgComp();

	/** Nobody should instantiate this */
	private PtViewTooltips() {
		// singleton
	}

	/**
	 * Register a child of a JScrollPane (only JList or JTree supported 
	 * for now) which should show helper tooltips.  Should be called
	 * from the component's addNotify() method.
	 */
	public static void register(final JList list) {
		registerSafeType(list);
	}
	
	public static void register(final JTree tree) {
		registerSafeType(tree);
	}
	
	private static void registerSafeType(final JComponent comp) {
		if (instance == null) {
			instance = new PtViewTooltips();
		}
		instance.attachTo(comp);
	}
	
	/**
	 * Unregister a child of a JScrollPane (only JList or JTree supported 
	 * for now) which should show helper tooltips. Should be called
	 * from the component's removeNotify() method.
	 */
	public static void unregister(final JComponent comp) {
		assert instance != null : "Unregister asymmetrically called";
		if (null != instance && instance.detachFrom(comp) == 0) {
			instance.hide();
			instance = null;
		}
	}

	/** Start listening to mouse motion on the passed component */
	private void attachTo(final JComponent comp) {
		assert comp instanceof JTree || comp instanceof JList;
		comp.addMouseListener(this);
		comp.addMouseMotionListener(this);
		this.refcount++;
	}

	/** Stop listening to mouse motion on the passed component */
	private int detachFrom(final JComponent comp) {
		assert comp instanceof JTree || comp instanceof JList;
		comp.removeMouseMotionListener(this);
		comp.removeMouseListener(this);
		return this.refcount--;
	}

	public void mouseMoved(final MouseEvent e) {
		Point p = e.getPoint();
		JComponent comp = (JComponent) e.getSource();
		JScrollPane jsp = (JScrollPane)
		SwingUtilities.getAncestorOfClass(JScrollPane.class, comp);

		if (jsp != null) {
			p = SwingUtilities.convertPoint(comp, p, jsp);
			show(jsp, p);
		}
	}

	public void mouseDragged(final MouseEvent e) {
		hide();
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
		hide();
	}

	@Override
	public void mouseExited(final MouseEvent e) {
		hide();
	}

	/** Shows the appropriate popups given the state of the scroll pane and
	 * its view. 
	 * @param view The scroll pane owning the component the event happened on
	 * @param pt The point at which the mouse event happened, in the coordinate
	 *  space of the scroll pane.
	 */
	void show(final JScrollPane view, final Point pt) {
		if (view.getViewport().getView() instanceof JTree) {
			showJTree(view, pt);
		} else if (view.getViewport().getView() instanceof JList) {
			showJList(view, pt);
		} else {
			assert false : "Bad component type registered: "
					+ view.getViewport().getView();
		}
	}

	private void showJList(final JScrollPane view, final Point pt) {
		JList list = (JList) view.getViewport().getView();
		Point p = SwingUtilities.convertPoint(view, pt.x, pt.y, list);
		int listRow = list.locationToIndex(p);

		if (listRow == -1) {
			hide();
			return;
		}

		Rectangle bds = list.getCellBounds(listRow, listRow);
		
		//GetCellBounds returns a width that is the
		//full component width;  we want only what
		//the renderer really needs.

		ListCellRenderer ren = list.getCellRenderer();
		Dimension rendererSize =
		ren.getListCellRendererComponent(list,
		list.getModel().getElementAt(listRow),
		listRow, false, false).getPreferredSize();
		
		// if(bds != null)
		bds.width = rendererSize.width;

		if (bds.contains(p) == false) {
			hide();
			return;
		}

		if (setCompAndRow(list, listRow)) {
			Rectangle visible = getShowingRect(view);
			Rectangle[] rects = getRects(bds, visible);

			if (rects.length > 0) {
				ensureOldPopupsHidden();
				this.painter.configure(list.getModel().getElementAt(listRow),
				view, list, listRow);
				showPopups(rects, bds, visible, list, view);

			} else {
				hide();
			}
		}
	}

	private void showJTree(final JScrollPane view, final Point pt) {
		JTree tree = (JTree) view.getViewport().getView();
		Point p = SwingUtilities.convertPoint(view, pt.x, pt.y, tree);
		final int closestRow = tree.getClosestRowForLocation(p.x, p.y);
		TreePath path = tree.getClosestPathForLocation(p.x, p.y);
		Rectangle bds = tree.getPathBounds(path);

		if (bds == null || !bds.contains(p)) {
			hide();
			return;
		}

		if (setCompAndRow(tree, closestRow)) {
			Rectangle visible = getShowingRect(view);
			Rectangle[] rects = getRects(bds, visible);

			if (rects.length > 0) {
				ensureOldPopupsHidden();
				this.painter.configure(path.getLastPathComponent(),
				view, tree, path, closestRow);
				showPopups(rects, bds, visible, tree, view);
			} else {
				hide();
			}
		}
	}

	/**
	 * Set the currently shown component and row, returning true if they are
	 * not the same as the last known values.
	 */
	private boolean setCompAndRow(final JComponent newInner, final int newRow) {
		boolean rowChanged = newRow != this.row;
		boolean compChanged = newInner != this.inner;
		this.inner = newInner;
		this.row = newRow;
		return (rowChanged || compChanged);
	}

	/**
	 * Hide all popups and discard any references to the components the
	 * popups were showing for.
	 */
	void hide() {
		ensureOldPopupsHidden();
		if (this.painter != null) {
			this.painter.clear();
		}

		setHideComponent(null, null);
		this.inner = null;
		this.row = -1;
	}

	private void ensureOldPopupsHidden() {
		for (int i = 0; i < this.popups.length; i++) {
			if (this.popups[i] != null) {
				this.popups[i].hide();
				this.popups[i] = null;
			}
		}
	}

	/**
	 * Gets the sub-rectangle of a JScrollPane's area that
	 * is actually showing the view
	 */
	private Rectangle getShowingRect(final JScrollPane pane) {
		final Insets ins1 = pane.getViewport().getInsets();
		final Border border = pane.getViewportBorder();
		
		final Insets ins2;
		if(border != null) {
			ins2 = border.getBorderInsets(pane);
		} else {
			ins2 = new Insets(0, 0, 0, 0);
		}

		Insets ins3 = new Insets(0, 0, 0, 0);
		if(pane.getBorder() != null) {
			ins3 = pane.getBorder().getBorderInsets(pane);
		}

		final Rectangle r = pane.getViewportBorderBounds();
		r.translate(-r.x, -r.y);
		r.width -= ins1.left + ins1.right;
		r.width -= ins2.left + ins2.right;
		r.height -= ins1.top + ins1.bottom;
		r.height -= ins2.top + ins2.bottom;
		r.x -= ins2.left;
		r.x -= ins3.left;
		
		final Point p = pane.getViewport().getViewPosition();
		r.translate(p.x, p.y);

		return SwingUtilities.convertRectangle(pane.getViewport(), r, pane);
	}

	/**
	 * Fetches an array or rectangles representing the non-overlapping
	 * portions of a cell rect against the visible portion of the component.
	 * @bds The cell's bounds, in the coordinate space of the tree or list
	 * @vis The visible area of the tree or list, in the tree or list's coordinate space
	 */
	private static Rectangle[] getRects(final Rectangle bds, final Rectangle vis) {
		Rectangle[] result;

		if (vis.contains(bds)) {
			result = new Rectangle[0];
		} else {

			if (bds.x < vis.x && bds.x + bds.width > vis.x + vis.width) {
				Rectangle a = new Rectangle(bds.x, bds.y, vis.x - bds.x, bds.height);
				Rectangle b = new Rectangle(vis.x + vis.width, bds.y,
						(bds.x + bds.width) - (vis.x + vis.width), bds.height);
				result = new Rectangle[] { a, b };

			} else if (bds.x < vis.x) {
				result = new Rectangle[] {
						new Rectangle(bds.x, bds.y, vis.x - bds.x, bds.height)
				};

			} else if (bds.x + bds.width > vis.x + vis.width) {
				result = new Rectangle[] {
				new Rectangle(vis.x + vis.width, bds.y, (bds.x + bds.width) - (vis.x + vis.width), bds.height)
				};
			} else {
				result = new Rectangle[0];
			}
		}

//		for (int i = 0; i < result.length; i++) { // MINOR was war denn das hier?!?!
//
//		}
		return result;
	}

	/**
	 * Show popups for each rectangle, using the now configured painter.
	 */
	private void showPopups(
			final Rectangle[] rects,
			final Rectangle bds,
			final Rectangle visible,
			final JComponent comp,
			final JScrollPane view
			) {
		boolean shown = false;

		for (int i = 0; i < rects.length; i++) {
			Rectangle sect = rects[i];
			sect.translate(-bds.x, -bds.y);
			ImgComp part = this.painter.getPartial(sect, bds.x + rects[i].x < visible.x);
			Point pos = new Point(bds.x + rects[i].x, bds.y + rects[i].y);
			SwingUtilities.convertPointToScreen(pos, comp);

			if (comp instanceof JList) {
				//XXX off by one somewhere, only with JLists - where?
				pos.y--;
			}

			if (pos.x > 0) { //Mac OS will reposition off-screen popups to x=0,
				//so don't try to show them
				this.popups[i] = getPopupFactory().getPopup(view,
				part, pos.x, pos.y);
				this.popups[i].show();
				shown = true;
			}
		}

		if (shown) {
			setHideComponent(comp, view);
		} else {
			setHideComponent(null, null); //clear references
		}
	}
	
	private static PopupFactory getPopupFactory() {
		//        if ((Utilities.getOperatingSystem() & Utilities.OS_MAC) != 0 ) {
		//            
		//            // See ide/applemenu/src/org/netbeans/modules/applemenu/ApplePopupFactory
		//            // We have a custom PopupFactory that will consistently use 
		//            // lightweight popups on Mac OS, since HW popups get a drop
		//            // shadow.  By default, popups returned when a heavyweight popup
		//            // is needed (SDI mode) are no-op popups, since some hacks
		//            // are necessary to make it really work.
		//            
		//            // To enable heavyweight popups which have no drop shadow
		//            // *most* of the time on mac os, run with
		//            // -J-Dnb.explorer.hw.completions=true
		//            
		//            // To enable heavyweight popups which have no drop shadow 
		//            // *ever* on mac os, you need to put the cocoa classes on the
		//            // classpath - modify netbeans.conf to add 
		//            // System/Library/Java on the bootclasspath.  *Then*
		//            // run with the above line switch and 
		//            // -J-Dnb.explorer.hw.cocoahack=true
		//            
		//            PopupFactory result = (PopupFactory) Lookup.getDefault().lookup (
		//                    PopupFactory.class);
		//            return result == null ? PopupFactory.getSharedInstance() : result;
		//        } else {
		return PopupFactory.getSharedInstance();
		//        }
	}

	private Hider hider = null;
	
	/**
	 * Set a component (JList or JTree) which should be listened to, such that if
	 * a model, selection or scroll event occurs, all currently open popups
	 * should be hidden.
	 */
	private void setHideComponent(final JComponent comp, final JScrollPane parent) {
		if (this.hider != null && this.hider.isListeningTo(comp)) {
			return;
		}
		
		if (this.hider != null) {
			this.hider.detach();
		}
		
		if (comp != null) {
			this.hider = new Hider(comp, parent);
		} else {
			this.hider = null;
		}
	}

	/**
	 * A JComponent which creates a BufferedImage of a cell renderer and can
	 * produce clones of itself that display subrectangles of that cell
	 * renderer.
	 */
	private static final class ImgComp extends JComponent {

		private static final long serialVersionUID = -3812447265015213647L;

		private BufferedImage img;

		private Dimension d = null;

		private Color bg = Color.WHITE;

//		private JScrollPane comp = null;

//		private Object node = null;

		private AffineTransform at = AffineTransform.getTranslateInstance(0d, 0d);

		private boolean isRight = false;

		ImgComp() {
			// nothing to do
		}

		/**
		 * Create a clone with a specified backing image
		 */
		ImgComp(final BufferedImage img, final Rectangle off, final boolean right) {
			this.img = img;
			this.at = AffineTransform.getTranslateInstance(-off.x, 0);
			this.d = new Dimension(off.width, off.height);
			this.isRight = right;
		}

		public ImgComp getPartial(final Rectangle bds, final boolean right) {
			assert this.img != null;
			return new ImgComp(this.img, bds, right);
		}

		/** Configures a tree cell renderer and sets up sizing and the 
		 * backing image from it */
		public boolean configure(
				final Object nd,
				@SuppressWarnings("unused") final JScrollPane tv,
				final JTree tree,
				final TreePath path,
				final int row
				) {
//			boolean sameVn = setLastRendereredObject(nd);
//			boolean sameComp = setLastRenderedScrollPane(tv);
			Component renderer = null;
			
			this.bg = tree.getBackground();
			boolean sel = tree.isSelectionEmpty() ? false :
			tree.getSelectionModel().isPathSelected(path);

			boolean exp = tree.isExpanded(path);
			boolean leaf = !exp && tree.getModel().isLeaf(nd);
			boolean lead = path.equals(tree.getSelectionModel().getLeadSelectionPath());

			renderer = tree.getCellRenderer().getTreeCellRendererComponent(tree, nd, sel, exp, leaf, row, lead);
			if (renderer != null) {
				setComponent(renderer);
			}
			return true;
		}

		/** Configures a list cell renderer and sets up sizing and the 
		 * backing image from it */
		public boolean configure(
				final Object nd,
				@SuppressWarnings("unused") final JScrollPane tv,
				final JList list,
				final int row
				) {
//			boolean sameVn = setLastRendereredObject(nd);
//			boolean sameComp = setLastRenderedScrollPane(tv);
			Component renderer = null;
			this.bg = list.getBackground();

			boolean sel = list.isSelectionEmpty() ? false :
			list.getSelectionModel().isSelectedIndex(row);
			renderer = list.getCellRenderer().getListCellRendererComponent(list, nd, row, sel, false);
			if (renderer != null) {
				setComponent(renderer);
			}
			return true;
		}

//		private boolean setLastRenderedScrollPane(final JScrollPane comp) {
//			boolean result = comp != this.comp;
//			this.comp = comp;
//			return result;
//		}
//
//		private boolean setLastRendereredObject(final Object nd) {
//			boolean result = node != nd;
//			if (result) {
//				node = nd;
//			}
//			return result;
//		}

		void clear() {
//			comp = null;
//			node = null;
		}

		/**
		 * Set the cell renderer we will proxy.
		 */
		public void setComponent(final Component jc) {
			final Dimension prefDim = jc.getPreferredSize();
			BufferedImage nue = new BufferedImage(prefDim.width, prefDim.height + 2,
			BufferedImage.TYPE_INT_ARGB_PRE);
			SwingUtilities.paintComponent(nue.getGraphics(), jc, this, 0, 0, prefDim.width, prefDim.height + 2);
			setImg(nue);
		}

		@Override
		public Rectangle getBounds() {
			Dimension dd = getPreferredSize();
			return new Rectangle(0, 0, dd.width, dd.height);
		}

		private void setImg(final BufferedImage img) {
			this.img = img;
			this.d = null;
		}

		@Override
		public Dimension getPreferredSize() {
			if (this.d == null) {
				this.d = new Dimension(this.img.getWidth(), this.img.getHeight());
			}
			return this.d;
		}

		@Override
		public Dimension getSize() {
			return getPreferredSize();
		}

		@Override
		public void paint(final Graphics g) {
			g.setColor(this.bg);
			g.fillRect(0, 0, this.d.width, this.d.height);
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawRenderedImage(this.img, this.at);
			g.setColor(Color.GRAY);
			g.drawLine(0, 0, this.d.width, 0);
			g.drawLine(0, this.d.height - 1, this.d.width, this.d.height - 1);

			if (this.isRight) {
				g.drawLine(0, 0, 0, this.d.height - 1);
			} else {
				g.drawLine(this.d.width - 1, 0, this.d.width - 1, this.d.height - 1);
			}
		}

		@Override
		public void firePropertyChange(final String s, final Object a, final Object b) {
			// nothing to do
		}

		@Override
		public void invalidate() {
			// nothing to do
		}

		@Override
		public void validate() {
			// nothing to do
		}

		@Override
		public void revalidate() {
			// nothing to do
		}
	}

	/**
	 * A listener that listens to just about everything in the known universe
	 * and hides all currently displayed popups if anything happens.
	 */
	private static final class Hider implements ChangeListener,
			PropertyChangeListener, TreeModelListener, TreeSelectionListener,
			HierarchyListener, HierarchyBoundsListener, ListSelectionListener,
			ListDataListener, ComponentListener {

		private final JTree tree;

		private JScrollPane pane;

		private final JList list;

		private boolean detached = false;
		
		public Hider(final JComponent comp, final JScrollPane pane) {
			if (comp instanceof JTree) {
				this.tree = (JTree) comp;
				this.list = null;
			} else {
				this.list = (JList) comp;
				this.tree = null;
			}

			assert this.tree != null || this.list != null;
			this.pane = pane;
			attach();
		}

		private boolean isListeningTo(final JComponent comp) {
			return !this.detached && (comp == this.list || comp == this.tree);
		}

		private void attach() {
			if (this.tree != null) {
				this.tree.getModel().addTreeModelListener(this);
				this.tree.getSelectionModel().addTreeSelectionListener(this);
				this.tree.addHierarchyBoundsListener(this);
				this.tree.addHierarchyListener(this);
				this.tree.addComponentListener(this);
			} else {
				this.list.getSelectionModel().addListSelectionListener(this);
				this.list.getModel().addListDataListener(this);
				this.list.addHierarchyBoundsListener(this);
				this.list.addHierarchyListener(this);
				this.list.addComponentListener(this);
			}

			if (null != this.pane.getHorizontalScrollBar()) {
				this.pane.getHorizontalScrollBar().getModel().addChangeListener(this);
			}

			if (null != this.pane.getVerticalScrollBar()) {
				this.pane.getVerticalScrollBar().getModel().addChangeListener(this);
			}
			KeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener(this);
		}

		

		private void detach() {
			KeyboardFocusManager.getCurrentKeyboardFocusManager().removePropertyChangeListener(this);

			if (this.tree != null) {
				this.tree.getSelectionModel().removeTreeSelectionListener(this);
				this.tree.getModel().removeTreeModelListener(this);
				this.tree.removeHierarchyBoundsListener(this);
				this.tree.removeHierarchyListener(this);
				this.tree.removeComponentListener(this);
			} else {
				this.list.getSelectionModel().removeListSelectionListener(this);
				this.list.getModel().removeListDataListener(this);
				this.list.removeHierarchyBoundsListener(this);
				this.list.removeHierarchyListener(this);
				this.list.removeComponentListener(this);
			}

			if (null != this.pane.getHorizontalScrollBar()) {
				this.pane.getHorizontalScrollBar().getModel().removeChangeListener(this);
			}

			if (null != this.pane.getVerticalScrollBar()) {
				this.pane.getVerticalScrollBar().getModel().removeChangeListener(this);
			}
			this.detached = true;
		}

		private void change() {
			if (PtViewTooltips.instance != null) {
				PtViewTooltips.instance.hide();
			}
			detach();
		}

		public void propertyChange(final PropertyChangeEvent evt) {
			change();
		}

		public void treeNodesChanged(final TreeModelEvent e) {
			change();
		}

		public void treeNodesInserted(final TreeModelEvent e) {
			change();
		}

		public void treeNodesRemoved(final TreeModelEvent e) {
			change();
		}

		public void treeStructureChanged(final TreeModelEvent e) {
			change();
		}

		public void hierarchyChanged(final HierarchyEvent e) {
			change();
		}

		public void valueChanged(final TreeSelectionEvent e) {
			change();
		}

		public void ancestorMoved(final HierarchyEvent e) {
			change();
		}

		public void ancestorResized(final HierarchyEvent e) {
			change();
		}

		public void stateChanged(final ChangeEvent e) {
			change();
		}

		public void valueChanged(final ListSelectionEvent e) {
			change();
		}

		public void intervalAdded(final ListDataEvent e) {
			change();
		}

		public void intervalRemoved(final ListDataEvent e) {
			change();
		}

		public void contentsChanged(final ListDataEvent e) {
			change();
		}

		public void componentResized(final ComponentEvent e) {
			change();
		}

		public void componentMoved(final ComponentEvent e) {
			change();
		}
		
		public void componentShown(final ComponentEvent e) {
			change();
		}
		
		public void componentHidden(final ComponentEvent e) {
			change();
		}
	}
}
