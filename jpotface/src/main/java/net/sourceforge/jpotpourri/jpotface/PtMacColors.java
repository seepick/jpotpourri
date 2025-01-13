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

package net.sourceforge.jpotpourri.jpotface;

import java.awt.Color;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtMacColors {
	
	private PtMacColors() {
		// no instantiation
	}
	/**
	 * if editting value in cell, this will be background color of all other
	 * (unfocused) cells in that row
	 */
	// private static final Color MAC_UNFOCUSED_SELECTED_CELL_BACKGROUND_COLOR = new Color(0xc0c0c0);
	
	
	// for renderer
	public static final Color MAC_COLOR_FOCUSED_SELECTED_CELL_HORIZONTAL_LINE   = new Color(0x7daaea);
	public static final Color MAC_COLOR_FOCUSED_SELECTED_VERTICAL_LINE          = new Color(0x346dbe);
	public static final Color MAC_COLOR_FOCUSED_UNSELECTED_VERTICAL_LINE        = new Color(0xd9d9d9);
	
	public static final Color MAC_COLOR_UNFOCUSED_SELECTED_CELL_HORIZONTAL_LINE = new Color(0xe0e0e0);
	public static final Color MAC_COLOR_UNFOCUSED_SELECTED_VERTICAL_LINE        = new Color(0xacacac);
	public static final Color MAC_COLOR_UNFOCUSED_UNSELECTED_VERTICAL_LINE      = new Color(0xd9d9d9);


	
	

	/** */
	public static final Color MAC_COLOR_ROW_FOREGROUND = Color.BLACK;

	/** */
	public static final Color MAC_COLOR_ROW_BACKGROUND_EVEN = Color.WHITE;

	/** */
	public static final Color MAC_COLOR_ROW_BACKGROUND_ODD = new Color(241, 245, 250);

	
	
    /** table background for selected rows */
	public static final Color MAC_COLOR_SELECTED_ROW_BACKGROUND = new Color(61, 128, 223);

    /** table foreground for selected rows */
	public static final Color MAC_COLOR_SELECTED_ROW_FOREGROUND = Color.WHITE;	

    /** table background for selected rows without focus */
	public static final Color MAC_COLOR_SELECTED_NOFOCUS_BG = new Color(212, 212, 212);

}
