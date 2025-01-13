package net.sourceforge.jpotpourri.codegen.java;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtCodeUtil {

	private PtCodeUtil() {
		// no instantiation
	}
	

	
	public static String autoIndentCode(final String code, final int depth) {
		if(depth < 0) {
			throw new IllegalArgumentException("depth=" + depth);
		}
		
		final StringBuilder sb = new StringBuilder();
		
		for (String line : code.split("\\n")) {
			for (int i = 1; i <= depth; i++) {
				sb.append("\t");
			}
			sb.append(line).append("\n");
		}
		
		return sb.toString();
	}
}
