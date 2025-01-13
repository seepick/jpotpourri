package org.codehaus.mojo.jpotpourri.buildmac;

import java.io.File;
import java.util.List;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
interface IMojoData
{

    String getAppName();

    String getAppVersion();

    File getAppIcon();

    File getTargetDirectory();

    File getBaseDirectory();

    // String getProjectName(); // should NOT be accessible

    // String getProjectVersion(); // should NOT be accessible

    boolean isPlistInfoSet();

    File getPlistInfo();

    boolean isPlistInfoParamsSet();

    String getPlistInfoParams( final PlistInfoParam paramKey );

    boolean isPlistVersionSet();

    File getPlistVersion();

    boolean isPlistVersionParamsSet();

    String getPlistVersionParams( final PlistVersionParam paramKey );

    /**
     * @return List<String>
     */
    List getAdditionalResources();
}
