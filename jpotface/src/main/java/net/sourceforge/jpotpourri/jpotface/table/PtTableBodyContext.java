/*
 * OurMovies - Yet another movie manager
 * Copyright (C) 2008 Christoph Pickl (christoph_pickl@users.sourceforge.net)
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package net.sourceforge.jpotpourri.jpotface.table;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtTableBodyContext extends MouseAdapter implements ActionListener, KeyListener {

    private static final Log LOG = LogFactory.getLog(PtTableBodyContext.class);
    
    private final JTable table;
    private final JPopupMenu popupSingle = new JPopupMenu();
    private final JPopupMenu popupMultiple = new JPopupMenu();
    private final IPtTableBodyContextListener listener;
    
    private int tableRowSelected = -1;
    private boolean isKeyDown = false;
    private boolean wasPopupShownSingle = false;
    
    public PtTableBodyContext(
    		final JTable table,
    		final List<JMenuItem> popupItemsSingle,
    		final List<JMenuItem> popupItemsMultiple,
    		final IPtTableBodyContextListener listener) {
        this.table = table;
        this.listener = listener;
        this.table.addMouseListener(this);
        this.table.addKeyListener(this);

        for (JMenuItem item : popupItemsSingle) {
            if(item == null) {
                this.popupSingle.addSeparator();
            } else {
                item.addActionListener(this);
    //            item.addMouseListener(this);
                this.popupSingle.add(item);
            }
        }
        
        if(popupItemsMultiple != null) {
            for (JMenuItem item : popupItemsMultiple) {
                if(item == null) {
                    this.popupMultiple.addSeparator();
                } else {
                    item.addActionListener(this);
    //                item.addMouseListener(this);
                    this.popupMultiple.add(item);
                }
            }
        }
    }

    public static JMenuItem newJMenuItem(
    		final List<JMenuItem> items,
    		final String label,
    		final String actionCommand
    		) {
        return newJMenuItem(items, label, actionCommand, null);
    }
    
    /**
     * utility method
     */
    public static JMenuItem newJMenuItem(
    		final List<JMenuItem> items,
    		final String label,
    		final String actionCommand,
    		final Icon icon
    		) {
        JMenuItem item = (icon != null) ? new JMenuItem(label, icon) : new JMenuItem(label);
        item.setActionCommand(actionCommand);
        items.add(item);
        return item;
    }
    
    public static void newJMenuSeparator(final List<JMenuItem> items) {
        items.add(null);
    }
    
    @Override
    public final void mousePressed(final MouseEvent event) {
        LOG.debug("mousePressed(); button()=" + event.getButton() + "; isPopupTrigger=" + event.isPopupTrigger());
        
//        final boolean isRightButton = event.isPopupTrigger(); // !!! does not work on windows (but on osx)
//        System.out.println("isPopupTrigger="+event.isPopupTrigger()); // event.consume();
        
        final boolean isRightButton = event.getButton() == MouseEvent.BUTTON3;
        	
        if(isRightButton) {
//            System.out.println("event.isPopupTrigger() =>" + event.isPopupTrigger());
//            System.out.println("event.isConsumed()     => " + event.isConsumed());
//            System.out.println("event.isMetaDown()     => " + event.isMetaDown());
            
            if(this.isKeyDown == true) { // is this really necessary anymore ???
                LOG.debug("SwingUtilities says right button, but actual only (meta-)key is down :/");
                return;
            }
            
            final int selectedRows = this.table.getSelectedRows().length;
            final JPopupMenu popup;
            if(selectedRows == 1) {
                this.wasPopupShownSingle = true;
                popup = this.popupSingle;
            } else if(selectedRows > 1) {
                this.wasPopupShownSingle = false;
                popup = this.popupMultiple;
            } else {
                LOG.debug("selected rows: " + selectedRows + "; ignore right click");
                return;
            }
            
            final Point pointRightClick =
            	SwingUtilities.convertPoint(event.getComponent(), event.getPoint(), this.table);
            this.tableRowSelected = this.table.rowAtPoint(pointRightClick);
            this.table.requestFocus();
            
            popup.show(this.table, pointRightClick.x, pointRightClick.y);
        }
    }
    
    public final void actionPerformed(final ActionEvent event) {
		JMenuItem item = (JMenuItem) event.getSource();
        LOG.debug("actionPerformed(cmd=" + item.getActionCommand() + "; row=" + this.tableRowSelected + "; " +
        		"selection=" + (this.wasPopupShownSingle ? "single" : "multiple") + ")");
        
        if(event.getModifiers() == ActionEvent.META_MASK) {
            LOG.debug("Ignoring performed action because modifier indicates that right button was clicked.");
            return;
        }
        
        if(this.wasPopupShownSingle == true) {
        	this.listener.contextMenuClicked(item, this.tableRowSelected);
        } else {
        	this.listener.contextMenuClickedMultiple(item, this.table.getSelectedRows());
        }
    }
    
    public final void keyPressed(final KeyEvent event) {
        this.isKeyDown = true;
    }

    public final void keyReleased(final KeyEvent event) {
        this.isKeyDown = false;
    }

    public final void keyTyped(final KeyEvent event) {
        // nothing to do
    }

    
}
