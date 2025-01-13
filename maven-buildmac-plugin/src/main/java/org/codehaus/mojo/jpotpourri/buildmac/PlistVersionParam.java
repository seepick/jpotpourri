package org.codehaus.mojo.jpotpourri.buildmac;

/**
 * Enum Class.
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
final class PlistVersionParam
{

    /** optional, default: project.name */
    static final PlistVersionParam PROJECT_NAME = new PlistVersionParam( "ProjectName" );

    /** optional, default: project.version */
    static final PlistVersionParam CF_BUNDLE_SHORT_VERSION_STRING =
        new PlistVersionParam( "CFBundleShortVersionString" );

    private final String xmlPomKey;

    private PlistVersionParam( final String xmlPomKey )
    {
        this.xmlPomKey = xmlPomKey;
    }

    String getXmlPomKey()
    {
        return this.xmlPomKey;
    }

}
