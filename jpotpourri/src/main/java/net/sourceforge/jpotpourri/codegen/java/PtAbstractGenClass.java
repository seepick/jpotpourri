package net.sourceforge.jpotpourri.codegen.java;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.sourceforge.jpotpourri.codegen.java.method.PtAbstractGenConstructor;
import net.sourceforge.jpotpourri.codegen.java.method.PtAbstractGenMethod;
import net.sourceforge.jpotpourri.codegen.java.method.PtNullGenConstructor;
import net.sourceforge.jpotpourri.codegen.java.modifier.PtGenClassModifier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public abstract class PtAbstractGenClass implements IPtJavaCode, IPtAnnotationable {

	private static final Log LOG = LogFactory.getLog(PtAbstractGenClass.class);
	
	private static final DateFormat FULL_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	public static final String CLASSNAME_GEN_SUFFIX = "Gen";
	public static final String CLASSNAME_MAN_SUFFIX = "Man";
	
	private final PtGenVisibility visibility;
	
	private final String className;
	
	private final String packageName;

	private final String superClass;

	private final PtGenClassModifier[] classModifiers;

	private final Set<String> imports = new LinkedHashSet<String>();

	private final Set<PtGenField> fields = new LinkedHashSet<PtGenField>();
	
	private final Set<PtAbstractGenMethod> methods = new LinkedHashSet<PtAbstractGenMethod>();

	private PtAbstractGenConstructor constructor;

	private String staticInitializer = null;

	private PtManClassDefinition manClassDefinition = null;
	
	private final List<String> annotations = new LinkedList<String>();

	
	// keywords (static, final, abstract)
	/**
	 * @param superClass can be null
	 */
	public PtAbstractGenClass(final PtGenVisibility visibility, final String className,
			final String packageName, final String superClass, final PtGenClassModifier... modifiers) {
		if(visibility == null) {
			throw new NullPointerException("visibility");
		}
		if(className == null) {
			throw new NullPointerException("className");
		}
		if(className.endsWith(CLASSNAME_GEN_SUFFIX) == false &&
		   className.endsWith(CLASSNAME_MAN_SUFFIX) == false) {
			throw new IllegalArgumentException("className=" + className);
		}
		this.visibility = visibility;
		this.className = className;
		this.packageName = packageName;
		this.superClass = superClass;
		this.constructor = new PtNullGenConstructor();
		this.classModifiers = modifiers;
	}
	
	static PtAbstractGenClass newManClass(final PtAbstractGenClass genClass, final PtManClassDefinition manDef) {
		assert (genClass.isGenerateManClassSet() == true);
		assert (genClass.className.endsWith(CLASSNAME_GEN_SUFFIX));
		
		final String genName = genClass.className;
		// convert from "FoobarGen" to "FoobarMan"
		final String manClassName = genName.substring(0, genName.length() - CLASSNAME_GEN_SUFFIX.length()) +
				CLASSNAME_MAN_SUFFIX;
		
		final String packageName = manDef.getPackageName() != null ?
				manDef.getPackageName() :
				genClass.packageName;
		
		final PtAbstractGenClass manClass = new PtAbstractGenClass(
				PtGenVisibility.PUBLIC,
				manClassName,
				packageName,
				genClass.className) {
			// nothing to do
		};
		if(packageName.equals(genClass.packageName) == false) {
			manClass.addImport(genClass.packageName + "." + genClass.className);
		}
		
		manClass.setConstructor(PtAbstractGenConstructor.newManConstructor(genClass, manClassName));
		return manClass;
	}
	
	protected final void addImport(final String qualifiedName) {
		this.imports.add(qualifiedName);
	}
	
	protected final void addField(final PtGenField field) {
		this.fields.add(field);
	}
	
	protected final void addMethod(final PtAbstractGenMethod method) {
		LOG.debug("adding method to class " + this.className + ": " + method);
		assert(this.methods.contains(method) == false);
		this.methods.add(method);
	}
	
	protected final void setConstructor(final PtAbstractGenConstructor constructor) {
		LOG.debug("Setting new constructor to: " + constructor);
		if(constructor == null) {
			throw new NullPointerException("constructor");
		}
		this.constructor = constructor;
	}

	public final PtAbstractGenConstructor getConstructor() {
		return this.constructor;
	}

	public final boolean isGenerateManClassSet() {
		return this.manClassDefinition != null;
	}
	
	final PtManClassDefinition getManClassDefinition() {
		return this.manClassDefinition;
	}
	
	public final void setManClassDefinition(final PtManClassDefinition manClassDefinition) {
		this.manClassDefinition = manClassDefinition;
	}
	
	public final String getClassName() {
		return this.className;
	}

	public final String getPackageName() {
		return this.packageName;
	}
	
	public final void setStaticInitializer(final String staticInitializer) {
		this.staticInitializer = staticInitializer;
	}
	
	public final void addAnnotation(final String textAfterAt) {
		this.annotations.add(textAfterAt);
	}

	public final String toCode() {
		final StringBuilder sb = new StringBuilder();
		
		sb.append("package ").append(this.packageName).append(";\n");
		sb.append("\n");
		
		for (String importStatement : this.imports) {
			sb.append("import ").append(importStatement).append(";\n");
		}
		sb.append("\n");

		sb.append("/**\n");
		sb.append(" * GENERATED CLASS (" + FULL_DATE_FORMAT.format(new Date()) + ")\n");
		sb.append(" *\n");
		for (final String annotation : this.annotations) {
			sb.append(" * @").append(annotation).append("\n");
		}
		sb.append(" */\n");
		
		sb.append(this.visibility.toCode());
		
		for (PtGenClassModifier modifier : this.classModifiers) {
			sb.append(modifier.toCode());
		}
		
		sb.append("class ");
		
		sb.append(this.className);
		if(this.superClass != null) {
			sb.append(" extends ").append(this.superClass);
		}
		sb.append(" {\n");

		if(this.fields.isEmpty() == false) {
			sb.append("\n");
		}
		for (PtGenField field : this.fields) {
			sb.append(field.toCode());
		}

		sb.append("\n");
		

		if(this.staticInitializer != null) {
			sb.append("\tstatic {\n");
			sb.append(PtCodeUtil.autoIndentCode(this.staticInitializer, 2));
			sb.append("\n");
			sb.append("\t}\n");
		}

		sb.append("\n");
		
		sb.append(this.constructor.toCode());
		
		for (PtAbstractGenMethod method : this.methods) {
			sb.append(method.toCode());
		}

		sb.append("}");
		sb.append("\n");
		
		return sb.toString();
	}
	
}
