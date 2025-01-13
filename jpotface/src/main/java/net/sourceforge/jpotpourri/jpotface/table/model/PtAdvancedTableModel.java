package net.sourceforge.jpotpourri.jpotface.table.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.table.AbstractTableModel;

import net.sourceforge.jpotpourri.jpotface.table.IPtTableFillEmptyRowsReceiver;
import net.sourceforge.jpotpourri.jpotface.table.PtTableEmptyRowsPainter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXTable;

public class PtAdvancedTableModel<T> extends AbstractTableModel {

    private static final Log LOG = LogFactory.getLog(PtAdvancedTableModel.class);
	private static final long serialVersionUID = -3047291765284729248L;

	private final List<PtAbstractTableColumn<T>> columns;

	private final ModelCoreData<T> data;
	
	// FIXME work in progress
	
	public PtAdvancedTableModel(
			final Collection<PtAbstractTableColumn<T>> columns,
			final Collection<T> rows) {
		this.columns = new ArrayList<PtAbstractTableColumn<T>>(columns);
		this.data = new ModelCoreData<T>(rows);
	}
	
	protected ModelCoreData<T> getModelCoreData() {
		return this.data;
	}
    
    public T getObjectAt(final int rowIndex) {
        if (rowIndex == -1) {
            LOG.trace("Not any row selected (rowIndex == -1).");
            return null;
        }
        return this.data.get(rowIndex);
    }
	
	@Override
	public boolean isCellEditable(final int rowIndex, final int colIndex) {
		return this.columns.get(colIndex).isEditable();
	}
	
	@Override
	public String getColumnName(final int colIndex) {
		return this.columns.get(colIndex).getLabel();
	}
	
	@Override
	public Class<?> getColumnClass(final int colIndex) {
		return this.columns.get(colIndex).getColClass();
	}

	// AbstractTableModel
	public int getColumnCount() {
		return this.columns.size();
	}


	// AbstractTableModel
	public int getRowCount() {
		return this.data.size();
	}


	// AbstractTableModel
	public Object getValueAt(final int rowIndex, final int colIndex) {
		final T row = this.data.get(rowIndex);
		final PtAbstractTableColumn<T> col = this.columns.get(colIndex);
		
		final Object value = col.getValueAt(row);
		assert(value == null || value.getClass() == col.getColClass());
		return value;
	}
	
	
	
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
		col4.setColClass(Boolean.class);
		cols.add(col4);
		
		List<Person> rows = new LinkedList<Person>();
		rows.add(new Person("Muster Mann", 18, Color.RED, true));
		rows.add(new Person("Anna Nym", 21, Color.GREEN, false));
		rows.add(new Person("Otto Normal", 44, Color.BLUE, true));
		
		final PtAdvancedEditableTableModel<Person> m = new PtAdvancedEditableTableModel<Person>(cols, rows);
		
		class MyTable extends JXTable implements IPtTableFillEmptyRowsReceiver {
			private static final long serialVersionUID = 1L;
			private final PtTableEmptyRowsPainter emptyRowsPainter;
			private MyTable(PtAdvancedTableModel<Person> model) {
				super(model);
				this.emptyRowsPainter = new PtTableEmptyRowsPainter(this);
			}
			public Color getColorRowBackgroundEven() {
				return Color.GRAY;
			}
			public Color getColorRowBackgroundOdd() {
				return Color.BLUE;
			}
			@Override
			public final void paint(final Graphics g) {
				super.paint(g);
				this.emptyRowsPainter.delegatePaint(g);
			}
		}
		MyTable t = new MyTable(m);
		// JTable t = new JTable(m); ... works the same
		
		
		JPanel p = new JPanel();
		p.add(new JScrollPane(t));
		JButton btn = new JButton("add");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				m.addObject(new Person("Button Added", (int) new Date().getTime(), Color.WHITE, false));
			}
		});
		p.add(btn);
		
		f.getContentPane().add(p);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}
	
}
