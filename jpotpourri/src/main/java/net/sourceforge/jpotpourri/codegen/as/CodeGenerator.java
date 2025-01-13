package net.sourceforge.jpotpourri.codegen.as;


public class CodeGenerator {

	public CodeGenerator() {
		// nothing to do
	}
	
	
	public static void main(final String[] args) {
		System.out.println(new AsInterface().toCode());
		
	}
	
	private static class AsInterface extends PtAbstractGenInterface {
		public AsInterface() {
			super("IMovieDao", "curd.model");

			this.addFunction(new PtGenInterfaceFunction(
					"addMovie",
					PtGenArgument.newList("movie", "Movie"),
					"void"));
			this.addFunction(new PtGenInterfaceFunction(
					"getMovies",
					PtGenArgument.newList(),
					"ArrayCollection"));
		}
	}
}
