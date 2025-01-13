package net.sourceforge.jpotpourri.jpotface.table;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import net.sourceforge.jpotpourri.jpotface.PtMacColors;
import net.sourceforge.jpotpourri.tools.PtUserSniffer;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtTableEmptyRowsPainter {
	
	private final IPtTableFillEmptyRowsReceiver table;
	
	
	public PtTableEmptyRowsPainter(final IPtTableFillEmptyRowsReceiver table) {
		if(table == null) {
			throw new NullPointerException("table");
		}
		this.table = table;
	}
	
	/**
	 * !!! must have invoked super.paint(g); before !!!
	 * 
     * Paints the backgrounds of the implied empty rows when the
     * table model is insufficient to fill all the visible area
     * available to us. We don't involve cell renderers, because
     * we have no data.
	 */
    public void delegatePaint(final Graphics g) {
    	
        final int rowCount = this.table.getRowCount();
        final Rectangle clip = g.getClipBounds();
        final int height = clip.y + clip.height;
        
        if (rowCount * this.table.getRowHeight() < height) {
        	
            for (int i = rowCount; i <= height / this.table.getRowHeight(); ++i) {
                g.setColor(colorForRow(i));
                g.fillRect(clip.x, i * this.table.getRowHeight(), clip.width, this.table.getRowHeight());
            }

            // Mac OS' Aqua LAF never draws vertical grid lines, so we have to draw them ourselves.
            if (PtUserSniffer.isMacOSX() && this.table.getShowVerticalLines()) {
                g.setColor(PtMacColors.MAC_COLOR_UNFOCUSED_UNSELECTED_VERTICAL_LINE);
                final TableColumnModel model = this.table.getColumnModel();
                int x = 0;
                
                for (int i = 0; i < model.getColumnCount(); ++i) {
                    TableColumn column = model.getColumn(i);
                    x += column.getWidth();
                    g.drawLine(x - 1, rowCount * this.table.getRowHeight(), x - 1, height);
                }
                
            }
        }
    }

    private Color colorForRow(final int row) {
        return (row % 2 == 1) ? this.table.getColorRowBackgroundOdd() : this.table.getColorRowBackgroundEven();
    }
}
