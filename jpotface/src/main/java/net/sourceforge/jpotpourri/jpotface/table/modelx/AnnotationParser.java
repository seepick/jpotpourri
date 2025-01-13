package net.sourceforge.jpotpourri.jpotface.table.modelx;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class AnnotationParser {

    private static final Log LOG = LogFactory.getLog(AnnotationParser.class);
    
	private AnnotationParser() {
		// no instantiation
	}
	
	public static <T extends IPtDataModelDisplayable> List<ColumnSpecifier<T>> parse(final Class<?> clazz) {
		LOG.info("Parsing annotations for class: " + clazz.getName());
		
		final Map<Integer, ColumnSpecifier<T>> tmp = new HashMap<Integer, ColumnSpecifier<T>>();
		for (final Method method : clazz.getDeclaredMethods()) {
			if(method.isAnnotationPresent(PtDataModelColumn.class) == true) {
				final PtDataModelColumn column = method.getAnnotation(PtDataModelColumn.class);
				final String columnName = column.label();
				final Class<?> columnClass = method.getReturnType();
				if(columnClass.isPrimitive() == true) {
					throw new RuntimeException(
							"@PtDataModelColumn for " + clazz.getName() + "#" + method.getName() + " must not " +
							"return a primitive type [" + columnClass.getSimpleName() + "]!");
				}
				final int index = column.index();
				final ColumnSpecifier<T> specifier = new ColumnSpecifier<T>(columnClass, columnName, method);
				LOG.trace("Found annotation: " + specifier);
				tmp.put(new Integer(index), specifier);
			}
		}
		
		final List<ColumnSpecifier<T>> result = new LinkedList<ColumnSpecifier<T>>();
		
		final List<Integer> indices = new ArrayList<Integer>(tmp.keySet());
		Collections.sort(indices);
		for (Integer index : indices) {
			result.add(tmp.get(index));
		}
		
		return result;
	}
	

	@SuppressWarnings("unused")
	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				class Person implements IPtDataModelDisplayable {
					private final String name;
					private final Integer age;
					public Person(final String name, final Integer age) {
						this.name = name;
						this.age = age;
					}

					@PtDataModelColumn(label = "Name", index = 0)
					public String getName() {
						return this.name;
					}

					@PtDataModelColumn(label = "Age", index = 1)
					public Integer getAge() {
						return this.age;
					}
				}
				
				System.out.println("main running ...");
				JFrame f = new JFrame();
				f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				
				List<Person> persons = new ArrayList<Person>(2);
				persons.add(new Person("Alex", 21));
				persons.add(new Person("Otto", 54));
				JTable tbl = new JTable(new PtDataModelDisplayer<Person>(Person.class, persons));
				
				f.getContentPane().add(new JScrollPane(tbl));
				
				f.pack();
				f.setVisible(true);
			}
		});
	}

}
