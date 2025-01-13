package net.sourceforge.jpotpourri.jcairngorm.component;

import java.io.File;

import net.sourceforge.jpotpourri.jcairngorm.bindobj.PtBindableString;
import net.sourceforge.jpotpourri.jpotface.chooser.IPtFileDirectoryChooserListener;
import net.sourceforge.jpotpourri.jpotface.chooser.PtDirectoryChooser;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtBDirectoryChooser {

	private final PtDirectoryChooser directoryChooser;
	// TODO also write this for file chooser
	
	public PtBDirectoryChooser(
//			final PtAbstractCairngormEvent<?> event,
			final PtDirectoryChooser directoryChooser,
			final PtBindableString string
		) {
		this.directoryChooser = directoryChooser;
		this.directoryChooser.uncheckedSetFileOrDir(string.getValue());
		
		// TODO do not only check at creation!
		if(this.directoryChooser.isTextfieldEditable()) {
			new PtBTextField(this.directoryChooser.getTextField(), string);
		}
		
		this.directoryChooser.addChooserListener(new IPtFileDirectoryChooserListener() {
			public void doChoosen(final File dir) {
				string.setValue(dir.getAbsolutePath());
			}
		});
	}
	
}
