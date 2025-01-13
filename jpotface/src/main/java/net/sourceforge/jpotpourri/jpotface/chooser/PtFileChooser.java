package net.sourceforge.jpotpourri.jpotface.chooser;


import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import net.sourceforge.jpotpourri.util.PtCollectionUtil;
import net.sourceforge.jpotpourri.util.PtFileUtil;


/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtFileChooser extends AbstractFileDirectoryChooser {

//    private static final Log LOG = LogFactory.getLog(DirectoryChooser.class);
    
    private static final long serialVersionUID = 4870536517103469362L;

    private final transient FileFilter fileFilter;
    
    
    /** simplest constructor. */
    public PtFileChooser(final String dialogTitle, final String... validExtensions) {
        this(dialogTitle, AbstractFileDirectoryChooser.DEFAULT_BUTTON_LABEL, null,
        		AbstractFileDirectoryChooser.DEFAULT_BUTTON_POSITION, validExtensions);
    }
    
    /** adds button position. */
    public PtFileChooser(final String dialogTitle, final PtButtonPosition position, final String... validExtensions) {
        this(dialogTitle, AbstractFileDirectoryChooser.DEFAULT_BUTTON_LABEL, null,
        		position, validExtensions);
    }
    
    /** adds default path. */
    public PtFileChooser(final String dialogTitle, final File defaultPath,
    		final PtButtonPosition position, final String... validExtensions) {
        this(dialogTitle, AbstractFileDirectoryChooser.DEFAULT_BUTTON_LABEL, defaultPath, position, validExtensions);
    }
    
    /** finally: adds button label. */
    public PtFileChooser(final String dialogTitle, final String buttonLabel,
    		final File defaultPath, final PtButtonPosition position, final String... validExtensions) {
        super(dialogTitle, defaultPath, position, buttonLabel);
        
        final Set<String> validExtensionsSet = new LinkedHashSet<String>(Arrays.asList(validExtensions));
        final String validExtensionsString = PtCollectionUtil.toString(validExtensionsSet);
        
        this.fileFilter = new FileFilter() {
            @Override
            public boolean accept(final File file) {
                if (file.isDirectory() == true) {
                	return true;
                }
                
                final String extension = PtFileUtil.extractExtension(file);
                if (extension == null) {
                    return false;
                }
                
                return validExtensionsSet.contains(extension);
            }
            @Override
            public String getDescription() {
                return validExtensionsString;
            }
        };
    }

    /**
     * @return can be null
     */
    public final File getSelectedFile() {
        return this.getFileOrDir();
    }

    @Override
    final int getSelectionMode() {
        return JFileChooser.FILES_ONLY;
    }

    @Override
    final FileFilter getFileFilter() {
        return this.fileFilter;
    }

    @Override
    final boolean isRightFileOrDir(final File file) {
        return file.isFile();
    }
    
    public final void setFile(final File file) {
        this.setFileOrDir(file);
    }

	@Override
	final String getFileDirName() {
		return "File";
	}
}
