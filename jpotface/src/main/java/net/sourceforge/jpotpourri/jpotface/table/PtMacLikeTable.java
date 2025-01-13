package net.sourceforge.jpotpourri.jpotface.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import net.sourceforge.jpotpourri.jpotface.PtMacColors;
import net.sourceforge.jpotpourri.jpotface.table.model.PtAbstractTableColumn;
import net.sourceforge.jpotpourri.jpotface.table.model.PtAdvancedEditableTableModel;
import net.sourceforge.jpotpourri.tools.PtUserSniffer;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.ColumnHeaderRenderer;

// http://elliotth.blogspot.com/2006/05/making-jtable-look-more-at-home-on-mac.html
public class PtMacLikeTable extends JXTable implements IPtTableFillEmptyRowsReceiver {

	private static final long serialVersionUID = -4230932349867323554L;

	/** delegater */
	private final PtTableEmptyRowsPainter emptyRowsPainter;
	
	
	public PtMacLikeTable() {
		this.emptyRowsPainter = new PtTableEmptyRowsPainter(this);
		this.pseudoConstructor();
	}
	
	public PtMacLikeTable(final TableModel model) {
		super(model);
		this.emptyRowsPainter = new PtTableEmptyRowsPainter(this);
		this.pseudoConstructor();
	}
	
	private void pseudoConstructor() {
        this.setShowGrid(false);
        
//        this.setGridColor(gridColor)
        this.setIntercellSpacing(new Dimension());
        
        // Work-around for Apple 4352937.
//        JLabel.class.cast(getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.LEADING); // cast exception: org.jdesktop.swingx.table.ColumnHeaderRenderer
        if(PtUserSniffer.isMacOSX() == true) {
        	ColumnHeaderRenderer.class.cast(getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.LEADING);
        	this.setShowHorizontalLines(false);
            this.setShowVerticalLines(true);
        }
	}

	public Color getColorRowBackgroundEven() {
		return PtMacColors.MAC_COLOR_ROW_BACKGROUND_EVEN;
	}

	public Color getColorRowBackgroundOdd() {
		return PtMacColors.MAC_COLOR_ROW_BACKGROUND_ODD;
	}

	@Override
	public final void paint(final Graphics g) {
		super.paint(g);
		this.emptyRowsPainter.delegatePaint(g);
	}

	@Override
	public Component prepareRenderer(
			final TableCellRenderer renderer,
			final int modelRowIndex,
			final int modelColumnIndex) {
		final Component c = super.prepareRenderer(renderer, modelRowIndex, modelColumnIndex);

		final Color bgColor;
		final Color fgColor;
        boolean focused = hasFocus();
        boolean selected = isCellSelected(modelRowIndex, modelColumnIndex);
        
        if(selected == true) {
        	if (focused == false) {
            	// selected, but got no focus
                bgColor = PtMacColors.MAC_COLOR_SELECTED_NOFOCUS_BG;
                fgColor = PtMacColors.MAC_COLOR_ROW_FOREGROUND;
        	} else {
	        	bgColor = PtMacColors.MAC_COLOR_SELECTED_ROW_BACKGROUND;
	        	fgColor = PtMacColors.MAC_COLOR_SELECTED_ROW_FOREGROUND;
        	}
        } else {
        	fgColor = PtMacColors.MAC_COLOR_ROW_FOREGROUND;
        	// fgColor = UIManager.getColor("Table.foreground");
        	
        	final boolean isEven = modelRowIndex % 2 == 0;
        	if(isEven == true) {
        		bgColor = this.getColorRowBackgroundEven();
        	} else {
        		bgColor = this.getColorRowBackgroundOdd();
        	}
        }
        
        c.setBackground(bgColor);
		c.setForeground(fgColor);
        
		if (c instanceof JComponent) {
            JComponent jc = (JComponent) c;
            // jc.setOpaque(true);

//            // The Java 6 GTK LAF JCheckBox doesn't paint its background by default.
//            // Sun 5043225 says this is the intended behavior, though presumably not when it's being used as a table cell renderer.
//            if (GuiUtilities.isGtk() && c instanceof JCheckBox) {
//                jc.setOpaque(true);
//            }
            
            if (getCellSelectionEnabled() == false) { // && isEditing() == false) {
                
            	if(PtUserSniffer.isMacOSX() == true) {
            		// Native Mac OS doesn't draw a border on the selected cell.
                    // It does however draw a horizontal line under the whole row, and a vertical line separating each column.
            		fixMacOsCellRendererBorder(jc, selected, focused, isEditing());
            	} else {
            		// FIXME: doesn't Windows have row-wide selection focus?
                    // Hide the cell focus.
                    jc.setBorder(null);
            	}
            }

//            initToolTip(jc, row, column);
        }
		
		return c;
	}
	
	@SuppressWarnings("unused")
    private void fixMacOsCellRendererBorder(JComponent renderer, boolean selected, boolean focused, boolean isEditing) {
        Border border;
        if (selected) {
        	final Color horizontalLinecolor = focused ?
        		PtMacColors.MAC_COLOR_FOCUSED_SELECTED_CELL_HORIZONTAL_LINE :
        		PtMacColors.MAC_COLOR_UNFOCUSED_SELECTED_CELL_HORIZONTAL_LINE;
            border = BorderFactory.createMatteBorder(0, 0, 1, 0, horizontalLinecolor);
        } else {
            border = BorderFactory.createEmptyBorder(0, 0, 1, 0);
        }

        if (getShowVerticalLines()) {
            final Color verticalLineColor;
            if (focused) {
                verticalLineColor = selected ?
                		PtMacColors.MAC_COLOR_FOCUSED_SELECTED_VERTICAL_LINE :
                	PtMacColors.MAC_COLOR_FOCUSED_UNSELECTED_VERTICAL_LINE;
            } else {
//                if(isEditing && row == selectedRow) {
//                    verticalLineColor = dark grey thing: MAC_FOCUSED_SELECTED_VERTICAL_LINE_COLOR?
//                } else {
                    verticalLineColor = selected ?
                    	PtMacColors.MAC_COLOR_UNFOCUSED_SELECTED_VERTICAL_LINE :
                    	PtMacColors.MAC_COLOR_UNFOCUSED_UNSELECTED_VERTICAL_LINE;
//                }
            }
            Border verticalBorder = BorderFactory.createMatteBorder(0, 0, 0, 1, verticalLineColor);
            border = BorderFactory.createCompoundBorder(border, verticalBorder);
        }

        renderer.setBorder(border);
    }
	
	/**
     * Changes the behavior of a table in a JScrollPane to be more like
     * the behavior of JList, which expands to fill the available space.
     * JTable normally restricts its size to just what's needed by its
     * model.
     */
    @Override
	public boolean getScrollableTracksViewportHeight() {
        if (getParent() instanceof JViewport) {
            JViewport parent = (JViewport) getParent();
            return (parent.getHeight() > getPreferredSize().height);
        }
        return false;
    }
    
    /**
     * Improve the appearance of of a table in a JScrollPane on Mac OS, where there's otherwise an unsightly hole.
     */
    @Override
    protected void configureEnclosingScrollPane() {
        super.configureEnclosingScrollPane();
        
        if (PtUserSniffer.isMacOSX() == false) {
            return;
        }
        
        Container p = getParent();
        if (p instanceof JViewport) {
            Container gp = p.getParent();
            if (gp instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) gp;
                // Make certain we are the viewPort's view and not, for
                // example, the rowHeaderView of the scrollPane -
                // an implementor of fixed columns might do this.
                JViewport viewport = scrollPane.getViewport();
                if (viewport == null || viewport.getView() != this) {
                    return;
                }
                
                // JTable copy & paste above this point; our code below.
                
                // Put a dummy header in the upper-right corner.
                final Component renderer = new JTableHeader().getDefaultRenderer().getTableCellRendererComponent(
                		null, "", false, false, -1, 0);
                JPanel panel = new JPanel(new BorderLayout());
                panel.add(renderer, BorderLayout.CENTER);
                scrollPane.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, panel);
            }
        }
    }
	
	// TODO tooltip -> omov.MacLikeTable
	
	
	
	@SuppressWarnings("boxing")
	public static void main(String[] args) {
		JFrame f = new JFrame();
		
		class Person {
			String name;
			Integer age;
			Color color;
			Boolean flag;
			private Person(String name, Integer age, Color color, Boolean flag) {
				super();
				this.name = name;
				this.age = age;
				this.color = color;
				this.flag = flag;
			}
			
		}
		
		List<PtAbstractTableColumn<Person>> cols = new LinkedList<PtAbstractTableColumn<Person>>();
		cols.add(new PtAbstractTableColumn<Person>("Name") {
			@Override
			protected Object getValueAt(Person p) {
				return p.name;
			}
		});
		PtAbstractTableColumn<Person> col2 = new PtAbstractTableColumn<Person>("Age") {
			@Override
			protected Object getValueAt(Person p) {
				return p.age;
			}
		};
		col2.setEditable(true);
		col2.setColClass(Integer.class);
		cols.add(col2);
		PtAbstractTableColumn<Person> col3 = new PtAbstractTableColumn<Person>("Color") {
			@Override
			protected Object getValueAt(Person p) {
				return p.color;
			}
		};
		col3.setColClass(Color.class);
		cols.add(col3);
		PtAbstractTableColumn<Person> col4 = new PtAbstractTableColumn<Person>("Some Flag") {
			@Override
			protected Object getValueAt(Person p) {
				return p.flag;
			}
		};
		col4.setColClass(String.class);
		cols.add(col4);
		
		List<Person> rows = new LinkedList<Person>();
		rows.add(new Person("Muster Mann", 18, Color.RED, true));
		rows.add(new Person("Anna Nym", 21, Color.GREEN, false));
		rows.add(new Person("Otto Normal", 44, Color.BLUE, true));
		
		final PtAdvancedEditableTableModel<Person> m = new PtAdvancedEditableTableModel<Person>(cols, rows);

		PtMacLikeTable t = new PtMacLikeTable(m);
		
		JPanel p = new JPanel(new BorderLayout());
		p.add(new JScrollPane(t), BorderLayout.CENTER);
		JButton btn = new JButton("add");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				m.addObject(new Person("Button Added", (int) new Date().getTime(), Color.WHITE, false));
			}
		});
		p.add(btn, BorderLayout.NORTH);
		p.add(new JTextField(10), BorderLayout.SOUTH);
		
		f.getContentPane().add(p);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}
}
