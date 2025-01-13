package net.sourceforge.jpotpourri.codegen.java.method;

import java.util.Arrays;
import java.util.List;

import net.sourceforge.jpotpourri.codegen.java.PtAbstractGenClass;
import net.sourceforge.jpotpourri.codegen.java.PtGenArgument;
import net.sourceforge.jpotpourri.codegen.java.PtGenVisibility;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public abstract class PtAbstractGenConstructor extends AbstractGenPseudoMethod {

	public PtAbstractGenConstructor(final PtGenVisibility visibility, final String className,
			final List<PtGenArgument> arguments) {
		super(visibility, className, arguments);
	}

	public PtAbstractGenConstructor(final PtGenVisibility visibility, final String className,
			final PtGenArgument... arguments) {
		this(visibility, className, Arrays.asList(arguments));
	}
	
	public static PtAbstractGenConstructor newManConstructor(
			final PtAbstractGenClass genClass,
			final String manClassName
			) {
		final PtAbstractGenConstructor gen = genClass.getConstructor();
		
		return new PtAbstractGenConstructor(gen.getVisibility(), manClassName, gen.getArguments()) {
			@Override
			protected String getBody() {
				final StringBuilder sb = new StringBuilder();
				sb.append("super(");
				boolean first = true;
				for (PtGenArgument arg : this.getArguments()) {
					if(first) {
						first = false;
					} else {
						sb.append(", ");
					}
					sb.append(arg.getName());
				}
				sb.append(");\n");
				return sb.toString();
			}
		};
	}
}
