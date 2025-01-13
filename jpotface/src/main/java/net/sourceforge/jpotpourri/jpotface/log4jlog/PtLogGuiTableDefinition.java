package net.sourceforge.jpotpourri.jpotface.log4jlog;

import java.awt.Color;

import net.sourceforge.jpotpourri.jpotface.PtMacColors;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtLogGuiTableDefinition {

	private final int rows;
	public static final int DEFAULT_ROWS = -1;
	
	private final Color colorRowBackgroundEven;
	private static final Color DEFAULT_COLOR_EVEN = PtMacColors.MAC_COLOR_ROW_BACKGROUND_EVEN;
	
	private final Color colorRowBackgroundOdd;
	private static final Color DEFAULT_COLOR_ODD = PtMacColors.MAC_COLOR_ROW_BACKGROUND_ODD;

	private final Color colorRowForeground;
	private static final Color DEFAULT_COLOR_ROW_FOREGROUND = PtMacColors.MAC_COLOR_ROW_FOREGROUND;
	
	private final Color colorSelectedRowForeground;
	private static final Color DEFAULT_COLOR_SELECTED_FOREGROUND = PtMacColors.MAC_COLOR_SELECTED_ROW_FOREGROUND;
	
	private final Color colorSelectedRowBackground;
	private static final Color DEFAULT_COLOR_SELECTED_BACKGROUND = PtMacColors.MAC_COLOR_SELECTED_ROW_BACKGROUND;
	
//	private final Color colorSelectedNofocusRowBackground;
//	private static final Color DEFAULT_COLOR_SELECTED_NOFOCUS_BG = PtMacColors.MAC_COLOR_SELECTED_NOFOCUS_BG;
	
	
	
	public PtLogGuiTableDefinition(
			final Integer rows,
			final Color colorRowBackgroundEven,
			final Color colorRowBackgroundOdd,
			final Color colorRowForeground,
			final Color colorSelectedRowForeground,
			final Color colorSelectedRowBackground
//			final Color colorSelectedNofocusRowBackground
			) {

		this.rows = rows != null ? rows.intValue() : DEFAULT_ROWS;
		this.colorRowBackgroundEven = colorRowBackgroundEven != null ? colorRowBackgroundEven : DEFAULT_COLOR_EVEN;
		this.colorRowBackgroundOdd = colorRowBackgroundOdd != null ? colorRowBackgroundOdd : DEFAULT_COLOR_ODD;
		this.colorRowForeground = colorRowForeground != null ? colorRowForeground : DEFAULT_COLOR_ROW_FOREGROUND;
		this.colorSelectedRowForeground = colorSelectedRowForeground != null ?
				colorSelectedRowForeground : DEFAULT_COLOR_SELECTED_FOREGROUND;
		this.colorSelectedRowBackground = colorSelectedRowBackground != null ?
				colorSelectedRowBackground : DEFAULT_COLOR_SELECTED_BACKGROUND;
//		this.colorSelectedNofocusRowBackground = colorSelectedNofocusRowBackground != null ?
//				colorSelectedNofocusRowBackground : DEFAULT_COLOR_SELECTED_NOFOCUS_BG;
		
		
	}

	@Override
	public String toString() {
		return "Log4jGuiTableDefinition[colorEven=" + this.colorRowBackgroundEven + ";colorOdd=" + 
			this.colorRowBackgroundOdd + ";rows=" + this.rows + ";]";
	}
	

	public int getRows() {
		return this.rows;
	}
	
	public Color getColorRowForeground() {
		return this.colorRowForeground;
	}

	public Color getColorSelectedRowForeground() {
		return this.colorSelectedRowForeground;
	}

	public Color getColorSelectedRowBackground() {
		return this.colorSelectedRowBackground;
	}
	
	public Color getColorRowBackgroundEven() {
		return this.colorRowBackgroundEven;
	}
	
	public Color getColorRowBackgroundOdd() {
		return this.colorRowBackgroundOdd;
	}
	
//	public Color getColorSelectedNofocusRowBackground() {
//		return this.colorSelectedNofocusRowBackground;
//	}
	
}
