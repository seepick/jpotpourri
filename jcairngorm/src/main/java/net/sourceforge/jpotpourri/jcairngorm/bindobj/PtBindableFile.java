package net.sourceforge.jpotpourri.jcairngorm.bindobj;

import java.io.File;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtBindableFile extends PtAbstractBindableSimpleObject<File> {
	
	public PtBindableFile(final File file) {
		this(file, "__File");
	}
	
	public PtBindableFile(final File value, final String propertyName) {
		super(value, propertyName);
	}
	
}
