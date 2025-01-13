package org.codehaus.mojo.jpotpourri.buildmac;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import net.sourceforge.jpotpourri.util.PtCloseUtil;

import org.apache.maven.plugin.MojoExecutionException;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
class PlistWriter
{

    /** default necessary java virtual version; set to version 1.5 minimum */
    private static final String DEFAULT_JVM_VERSION = "1.5+";

    /** mojo logger */
    private final IMojoLogger logger;

    /** pom data container */
    private final IMojoData data;

    /**
     * single constructor
     */
    public PlistWriter( final IMojoLogger logger, final IMojoData data )
    {
        this.logger = logger;
        this.data = data;
    }

    /**
     * writes content of Info.plist file.
     * 
     * @param targetFile which will be created
     * @param jarDependenciesFileName name of jar file which will be added to classpath of the app-bundle
     * @throws MojoExecutionException if MainClass was not defined or writing filecontent failed (I/O-error)
     */
    public void createPlistInfo( final File targetFile, final String jarDependenciesFileName )
        throws MojoExecutionException
    {
        assert ( this.data.isPlistInfoParamsSet() );

        final String mainClass = this.data.getPlistInfoParams( PlistInfoParam.MAIN_CLASS );
        if ( mainClass == null )
        {
            throw new MojoExecutionException( "Key 'configuration.plistInfoParams.MainClass' not found!" );
        }

        final String infoString =
            this.getPlistInfoValueWithDefault( PlistInfoParam.CF_BUNDLE_GET_INFO_STRING, this.data.getAppVersion() );
        final String bundleShortVersion =
            this.getPlistInfoValueWithDefault( PlistInfoParam.CF_BUNDLE_SHORT_VERSION_STRING, this.data.getAppVersion() );
        final String bundleVersion =
            this.getPlistInfoValueWithDefault( PlistInfoParam.CF_BUNDLE_VERSION, this.data.getAppVersion() );
        final String bundleName =
            this.getPlistInfoValueWithDefault( PlistInfoParam.CF_BUNDLE_NAME, this.data.getAppName() );
        final String jvmVersion = this.getPlistInfoValueWithDefault( PlistInfoParam.JVM_VERSION, DEFAULT_JVM_VERSION );

        final StringBuilder sb = new StringBuilder();

        sb.append( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" ).append( "\n" );
        sb.append(
                   "<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\" "
                       + "\"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">" ).append( "\n" );
        sb.append( "<plist version=\"1.0\">" ).append( "\n" );
        sb.append( "<dict>" ).append( "\n" );
        sb.append( "\t<key>CFBundleDevelopmentRegion</key>" ).append( "\n" );
        sb.append( "\t<string>English</string>" ).append( "\n" );
        sb.append( "\t<key>CFBundleExecutable</key>" ).append( "\n" );
        sb.append( "\t<string>JavaApplicationStub</string>" ).append( "\n" );

        if ( this.data.getAppIcon() != null )
        {
            sb.append( "\t<key>CFBundleIconFile</key>" ).append( "\n" );
            sb.append( "\t<string>" ).append( this.data.getAppIcon().getName() ).append( "</string>" ).append( "\n" );
        }

        sb.append( "\t<key>CFBundleInfoDictionaryVersion</key>" ).append( "\n" );
        sb.append( "\t<string>6.0</string>" ).append( "\n" );
        sb.append( "\t<key>CFBundleGetInfoString</key>" ).append( "\n" );
        sb.append( "\t<string>" ).append( infoString ).append( "</string>" ).append( "\n" );
        sb.append( "\t<key>CFBundleShortVersionString</key>" ).append( "\n" );
        sb.append( "\t<string>" ).append( bundleShortVersion ).append( "</string>" ).append( "\n" );
        sb.append( "\t<key>CFBundleName</key>" ).append( "\n" );
        sb.append( "\t<string>" ).append( bundleName ).append( "</string>" ).append( "\n" );
        sb.append( "\t<key>CFBundlePackageType</key>" ).append( "\n" );
        sb.append( "\t<string>APPL</string>" ).append( "\n" );
        sb.append( "\t<key>CFBundleSignature</key>" ).append( "\n" );
        sb.append( "\t<string>????</string>" ).append( "\n" );
        sb.append( "\t<key>CFBundleVersion</key>" ).append( "\n" );
        sb.append( "\t<string>" ).append( bundleVersion ).append( "</string>" ).append( "\n" );
        sb.append( "\t<key>Java</key>" ).append( "\n" );
        sb.append( "\t<dict>" ).append( "\n" );
        sb.append( "\t\t<key>ClassPath</key>" ).append( "\n" );
        sb.append( "\t\t<array>" ).append( "\n" );
        sb.append( "\t\t\t<string>$JAVAROOT/" ).append( jarDependenciesFileName ).append( "</string>" ).append( "\n" );
        sb.append( "\t\t</array>" ).append( "\n" );
        sb.append( "\t\t<key>JVMVersion</key>" ).append( "\n" );
        sb.append( "\t\t<string>" ).append( jvmVersion ).append( "</string>" ).append( "\n" );
        sb.append( "\t\t<key>MainClass</key>" ).append( "\n" );
        sb.append( "\t\t<string>" ).append( mainClass ).append( "</string>" ).append( "\n" );
        sb.append( "\t\t<key>Properties</key>" ).append( "\n" );
        sb.append( "\t\t<dict>" ).append( "\n" );
        sb.append( "\t\t\t<key>apple.laf.useScreenMenuBar</key>" ).append( "\n" );
        sb.append( "\t\t\t<string>true</string>" ).append( "\n" );
        sb.append( "\t\t</dict>" ).append( "\n" );
        sb.append( "\t</dict>" ).append( "\n" );
        sb.append( "</dict>" ).append( "\n" );
        sb.append( "</plist>" ).append( "\n" );

        this.writeFileContents( targetFile, sb.toString() );
    }

    /**
     * writes content of version.plist file.
     * 
     * @param targetFile which will be created
     * @throws MojoExecutionException if writing filecontent failed (I/O-error)
     */
    public void createPlistVersion( final File targetFile )
        throws MojoExecutionException
    {

        final String projectName =
            this.getPlistVersionValueWithDefault( PlistVersionParam.PROJECT_NAME, this.data.getAppName() );
        final String bundleVersion =
            this.getPlistVersionValueWithDefault( PlistVersionParam.CF_BUNDLE_SHORT_VERSION_STRING,
                                                  this.data.getAppVersion() );

        final StringBuilder sb = new StringBuilder();
        sb.append( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" ).append( "\n" );
        sb.append(
                   "<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\" "
                       + "\"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">" ).append( "\n" );
        sb.append( "<plist version=\"1.0\">" ).append( "\n" );
        sb.append( "<dict>" ).append( "\n" );

        sb.append( "\t<key>BuildVersion</key>" ).append( "\n" );
        sb.append( "\t<string>1</string>" ).append( "\n" );
        sb.append( "\t<key>CFBundleShortVersionString</key>" ).append( "\n" );
        sb.append( "\t<string>" ).append( bundleVersion ).append( "</string>" ).append( "\n" );
        sb.append( "\t<key>CFBundleVersion</key>" ).append( "\n" );
        sb.append( "\t<string>10</string>" ).append( "\n" );
        sb.append( "\t<key>ProjectName</key>" ).append( "\n" );
        sb.append( "\t<string>" ).append( projectName ).append( "</string>" ).append( "\n" );

        sb.append( "</dict>" ).append( "\n" );
        sb.append( "</plist>" ).append( "\n" );

        this.writeFileContents( targetFile, sb.toString() );

    }

    /**
     * internal method which only writes string content into given file.
     * 
     * @param targetFile which should be created
     * @param fileContent which will be inserted into targetFile
     * @throws MojoExecutionException if an <code>IOException</code> was thrown
     */
    private void writeFileContents( final File targetFile, final String fileContent )
        throws MojoExecutionException
    {
        this.logger.debug( "Writing file contents to file '" + targetFile.getAbsolutePath() + "' ..." );
        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter( new FileWriter( targetFile ) );
            writer.write( fileContent );
        }
        catch ( IOException e )
        {
            throw new MojoExecutionException( "Could not write contents to file " + "'" + targetFile.getAbsolutePath()
                + "'!", e );
        }
        finally
        {
            PtCloseUtil.close( new Closeable[] { writer } );
        }
    }

    /**
     * utility method, to avoid duplicate code
     */
    private String getPlistInfoValueWithDefault( final PlistInfoParam param, final String defaultValue )
    {
        return this.getPlistInfoOrVersionValueWithDefault( param, null, defaultValue );
    }

    /**
     * utility method, to avoid duplicate code
     */
    private String getPlistVersionValueWithDefault( final PlistVersionParam param, final String defaultValue )
    {
        return this.getPlistInfoOrVersionValueWithDefault( null, param, defaultValue );
    }

    /**
     * tries to get value from <code>IMojoData</code>.
     * 
     * @param paramInfo if not null, paramVersion must be null
     * @param paramVersion if not null, paramInfo must be null
     * @param defaultValue will be returned, if param was not set by user
     * @return stored value, or default if it was not set
     */
    private String getPlistInfoOrVersionValueWithDefault( final PlistInfoParam paramInfo,
                                                          final PlistVersionParam paramVersion,
                                                          final String defaultValue )
    {

        // only one of them must have been set
        if ( ( paramInfo != null ^ paramVersion != null ) == false )
        {
            throw new IllegalStateException( "Either paramInfo or paramVersion must be set: " + "paramInfo="
                + paramInfo + "; paramVersion = " + paramVersion );
        }
        final boolean useInfo = paramInfo != null;

        final String storedValue =
            useInfo ? this.data.getPlistInfoParams( paramInfo ) : this.data.getPlistVersionParams( paramVersion );

        if ( storedValue != null )
        {
            return storedValue;
        }

        final String pomKey;
        if ( paramInfo != null )
        {
            pomKey = paramInfo.getXmlPomKey();
        }
        else if ( paramVersion != null )
        {
            pomKey = paramVersion.getXmlPomKey();
        }
        else
        {
            throw new IllegalStateException( "Neither paramInfo nor paramVersion set!" );
        }

        this.logger.debug( "No '" + pomKey + "' value set, using default '" + defaultValue + "' instead." );
        return defaultValue;
    }

}
