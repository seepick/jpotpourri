package net.sourceforge.jpotpourri.tools;

import java.io.File;
import java.io.FileFilter;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtFileFilters {

	private PtFileFilters() {
		// no instantiation
	}
	
	public static final FileFilter FILE_ONLY_FILTER = new FileFilter() {
		public boolean accept(final File file) {
			return file.isFile();
		}
	};
	
	// FEATURE add some more file filters; eg: just pass vararg of file-extensions
}
