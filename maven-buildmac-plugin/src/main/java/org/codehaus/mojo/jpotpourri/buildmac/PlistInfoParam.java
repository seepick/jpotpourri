package org.codehaus.mojo.jpotpourri.buildmac;

/**
 * Enum Class.
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
final class PlistInfoParam
{

    /** mandatory */
    static final PlistInfoParam MAIN_CLASS = new PlistInfoParam( "MainClass" );

    /** optional, default: project.name */
    static final PlistInfoParam CF_BUNDLE_GET_INFO_STRING = new PlistInfoParam( "CFBundleGetInfoString" );

    /** optional, default: project.version */
    static final PlistInfoParam CF_BUNDLE_SHORT_VERSION_STRING = new PlistInfoParam( "CFBundleShortVersionString" );

    /** optional, default: project.version */
    static final PlistInfoParam CF_BUNDLE_VERSION = new PlistInfoParam( "CFBundleVersion" );

    /** optional, default: project.name */
    static final PlistInfoParam CF_BUNDLE_NAME = new PlistInfoParam( "CFBundleName" );

    /** optional, default: 1.5+ */
    static final PlistInfoParam JVM_VERSION = new PlistInfoParam( "JVMVersion" );

    private final String xmlPomKey;

    private PlistInfoParam( final String xmlPomKey )
    {
        this.xmlPomKey = xmlPomKey;
    }

    String getXmlPomKey()
    {
        return this.xmlPomKey;
    }

}
