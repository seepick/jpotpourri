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

package net.sourceforge.jpotpourri.jpotface.inputfield.search;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import net.sourceforge.jpotpourri.tools.PtUserSniffer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * A text field for search/filter interfaces. The extra functionality includes
 * a placeholder string (when the user hasn't yet typed anything), and a button
 * to clear the currently-entered text.
 * 
 * @author Elliott Hughes
 * copyright: http://elliotth.blogspot.com/2004/09/cocoa-like-search-field-for-java.html
 */
public class PtSearchField extends JTextField {
	
    private static final Log LOG = LogFactory.getLog(PtSearchField.class);
    
    private static final long serialVersionUID = -4973040283694201458L;
    
    // some todos:
    // - add a menu of recent searches.
    // - make recent searches persistent.
    // - use rounded corners, at least on Mac OS X.
    //
    private static final Border CANCEL_BORDER = new CancelBorder();
    private boolean sendsNotificationForEachKeystroke = false;
    private boolean showingPlaceholderText = false;
    private boolean armed = false;
        
    private final Set<IPtSearchFieldListener> listeners = new HashSet<IPtSearchFieldListener>();


    
    public PtSearchField(final String placeholderText) {
        super(15);
        addFocusListener(new PlaceholderText(placeholderText));
        initBorder();
        initKeyListener();

        if(PtUserSniffer.isMacOSX()) {
        	LOG.debug("activating macosx search field.");
        	
            this.putClientProperty("JTextField.variant", "search");
            this.putClientProperty("JTextField.Search.CancelAction", new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					cancel();
				}
            });
        }
    }
    
    
    public final void addISearchFieldListener(final IPtSearchFieldListener listener) {
        this.listeners.add(listener);
    }
    public final void removeISearchFieldListener(final IPtSearchFieldListener listener) {
        this.listeners.remove(listener);
    }
    
    
    private void notifiyListenersDidResetSearch() {
        for (IPtSearchFieldListener listener : this.listeners) {
            listener.didResetSearch();
        }
    }
    
    public PtSearchField() {
        this("Search");
    }

    public final void addDefaultSearchFieldListener(final IPtDefaultSearchFieldListener listener) {
    	final PtDefaultSearchFieldListener searchListener = new PtDefaultSearchFieldListener(this, listener);
    	this.addKeyListener(searchListener);
    	this.addISearchFieldListener(searchListener);
    }
    
    /**
     * @return null if nothing entered or is showing placeholder text; string otherwise if something was entered
     */
    public final String getProperText() {
    	if(this.getText().length() == 0 || this.isShowingPlaceholderText()) {
    		return null;
    	}
    	return this.getText();
    }
    
    private void initBorder() {
        setBorder(new CompoundBorder(getBorder(), CANCEL_BORDER));
        MouseInputListener mouseInputListener = new CancelListener();
        addMouseListener(mouseInputListener);
        addMouseMotionListener(mouseInputListener);
    }
    
    public final boolean isShowingPlaceholderText() {
    	return this.showingPlaceholderText;
    }
    
    private void initKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
			public void keyReleased(final KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    cancel();
                } else if (PtSearchField.this.sendsNotificationForEachKeystroke) {
                    maybeNotify();
                }
            }
        });
    }
    
    private void cancel() {
    	LOG.debug("cancel() invoked; notifying listeners.");
        setText("");
        this.notifiyListenersDidResetSearch();
        postActionEvent();
    }
    
    private void maybeNotify() {
        if (this.showingPlaceholderText) {
            return;
        }
        postActionEvent();
    }
    
    public final void setSendsNotificationForEachKeystroke(final boolean eachKeystroke) {
        this.sendsNotificationForEachKeystroke = eachKeystroke;
    }
    
    
    
    
    /**
     * Draws the cancel button as a gray circle with a white cross inside.
     */
    private static final class CancelBorder extends EmptyBorder {
        private static final long serialVersionUID = -2767979848421481905L;
        private static final Color GRAY = new Color(0.7f, 0.7f, 0.7f);
        CancelBorder() {
            super(0, 0, 0, 15);
        }
        @Override
        public void paintBorder(final Component c, final Graphics oldGraphics,
        		final int x, final int y, final int width, final int height) {
            PtSearchField field = (PtSearchField) c;
            if (field.showingPlaceholderText || field.getText().length() == 0) {
                return;
            }
            Graphics2D g = (Graphics2D) oldGraphics;
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            final int circleL = 14;
            final int circleX = x + width - circleL;
            final int circleY = y + (height - 1 - circleL) / 2;
            g.setColor(field.armed ? Color.GRAY : GRAY);
            g.fillOval(circleX, circleY, circleL, circleL);
            final int lineL = circleL - 8;
            final int lineX = circleX + 4;
            final int lineY = circleY + 4;
            g.setColor(Color.WHITE);
            g.drawLine(lineX, lineY, lineX + lineL, lineY + lineL);
            g.drawLine(lineX, lineY + lineL, lineX + lineL, lineY);
        }
    }
    
    /**
     * Handles a click on the cancel button by clearing the text and notifying
     * any ActionListeners.
     */
    private final class CancelListener extends MouseInputAdapter {
        private boolean isOverButton(final MouseEvent e) {
            // If the button is down, we might be outside the component
            // without having had mouseExited invoked.
            if (contains(e.getPoint()) == false) {
                return false;
            }
            // In lieu of proper hit-testing for the circle, check that
            // the mouse is somewhere in the border.
            Rectangle innerArea = SwingUtilities.calculateInnerArea(PtSearchField.this, null);
            return (innerArea.contains(e.getPoint()) == false);
        }
        @Override
        public void mouseDragged(final MouseEvent e) {
            arm(e);
        }
        @Override
        public void mouseEntered(final MouseEvent e) {
            arm(e);
        }
        @Override
        public void mouseExited(final MouseEvent e) {
            disarm();
        }
        @Override
        public void mousePressed(final MouseEvent e) {
            arm(e);
        }
        @Override
        public void mouseReleased(final MouseEvent e) {
        	LOG.debug("Mouse released; armed=" + PtSearchField.this.armed);
            if(PtSearchField.this.armed) {
                cancel();
            }
            disarm();
        }
        private void arm(final MouseEvent e) {
        	PtSearchField.this.armed = (isOverButton(e) && SwingUtilities.isLeftMouseButton(e));
            repaint();
        }
        private void disarm() {
        	PtSearchField.this.armed = false;
            repaint();
        }
    }
    
    
    /**
     * Replaces the entered text with a gray placeholder string when the
     * search field doesn't have the focus. The entered text returns when
     * we get the focus back.
     */
    private class PlaceholderText implements FocusListener {
    	
        private String placeholderText;
        private String previousText = "";
        private Color previousColor;
        
        PlaceholderText(final String placeholderText) {
            this.placeholderText = placeholderText;
            focusLost(null);
        }
        
        public void focusGained(final FocusEvent e) {
            setForeground(this.previousColor);
            setText(this.previousText);
            PtSearchField.this.showingPlaceholderText = false;
        }
        
        public void focusLost(final FocusEvent e) {
        	this.previousText = getText();
        	this.previousColor = getForeground();
            if (this.previousText.length() == 0) {
            	PtSearchField.this.showingPlaceholderText = true;
                setForeground(Color.GRAY);
                setText(this.placeholderText);
            }
        }
    }
}
