package net.sourceforge.jpotpourri.jpotface.chooser;

import java.io.File;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public interface IPtFileDirectoryChooserListener {

    /**
     * gets invoked if user has choosen directory and approved operation.
     * @param dir is never null
     */
    void doChoosen(File dir);
    
}
