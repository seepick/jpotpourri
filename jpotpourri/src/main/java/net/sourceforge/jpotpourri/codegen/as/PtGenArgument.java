package net.sourceforge.jpotpourri.codegen.as;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PtGenArgument implements IPtAsCode {

	private final String type;

	private final String name;
	
	public PtGenArgument(String type, String name) {
		this.type = type;
		this.name = name;
	}


	/**
	 * @param args eg: ("arg1", "String", "arg2", "int")
	 */
	public static List<PtGenArgument> newList(final String... args) {
		if(args.length % 2 == 1) {
			throw new IllegalArgumentException("Argument length must be even!");
		}
		final List<PtGenArgument> list = new ArrayList<PtGenArgument>();
		
		for (int i = 0, n = args.length / 2; i < n; i++) {
			list.add(new PtGenArgument(args[i * 2 + 1], args[i * 2]));
		}
		
		return Collections.unmodifiableList(list);
	}
	
	public String toCode() {
		return this.name + ":" + this.type;
	}

	public String getType() {
		return this.type;
	}

	public String getName() {
		return this.name;
	}
}
