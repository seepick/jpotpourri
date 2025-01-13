package org.codehaus.mojo.jpotpourri.buildmac;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Builds the app-folder by an existing jar-with-dependencies.jar
 * 
 * @author christoph_pickl@users.sourceforge.net
 * @goal buildmac
 * @phase package
 */
public class BuildMacMojo
    extends AbstractMojo
    implements IMojoData, IMojoLogger
{

    /**
     * maven workaround, because there is no "real required" parameter instead you can choose on of two options
     */
    private static final String TRICK_MAVEN = "TRICK_MAVEN";

    /**
     * Either <code>plistInfo</code> file, or <code>plistVersionParams</code> map given
     * 
     * @required
     * @parameter default-value=TRICK_MAVEN
     */
    private File plistInfo;

    /**
     * Either <code>plistInfo</code> file, or <code>plistVersionParams</code> map given Map<String, String>
     * 
     * @required
     * @parameter default-value=TRICK_MAVEN
     */
    private Map plistInfoParams;

    /**
     * Either <code>plistVersion</code> file, or <code>plistVersionParams</code> map given
     * 
     * @required
     * @parameter default-value=TRICK_MAVEN
     */
    private File plistVersion;

    /**
     * Either <code>plistVersion</code> file, or <code>plistVersionParams</code> map given Map<String, String>
     * 
     * @required
     * @parameter default-value=TRICK_MAVEN
     */
    private Map plistVersionParams;

    // ------------ OPTIONAL -------------------

    /**
     * Optional; project.name will be used, if null
     * 
     * @parameter
     */
    private String appName;

    /**
     * Optional; project.version will be used, if null
     * 
     * @parameter
     */
    private String appVersion;

    /**
     * Optional
     * 
     * @parameter
     */
    private File appIcon;

    /**
     * List of additional files which should be copied to <code>*.app/Contents/MacOS/Resources/</code>. Gets usefull
     * if you want to have some more OS specific possibilities. List<String>
     * 
     * @parameter
     */
    private List additionalResources;

    // ------------ READ ONLY -------------------

    /**
     * Should NOT be set by user.
     * 
     * @readonly
     * @parameter expression="${project.build.directory}"
     */
    private File targetDirectory;

    /**
     * Should NOT be set by user.
     * 
     * @readonly
     * @parameter expression="${basedir}"
     */
    private File baseDirectory;

    /**
     * Should NOT be set by user.
     * 
     * @readonly
     * @parameter expression="${project.version}"
     */
    private String projectVersion;

    /**
     * Should NOT be set by user.
     * 
     * @readonly
     * @parameter expression="${project.name}"
     */
    private String projectName;

    // AbstractMojo
    public void execute()
        throws MojoExecutionException, MojoFailureException
    {

        this.debug( "======= DEBUG VALUE OUTPUT BEGIN =======" );
        this.debug( "this.isPlistInfoSet() = " + this.isPlistInfoSet() + " [" + this.plistInfo + "]" );
        this.debug( "this.isPlistInfoParamsSet() = " + this.isPlistInfoParamsSet() + " [" + this.plistInfoParams + "]" );
        this.debug( "this.isPlistVersionSet() = " + this.isPlistVersionSet() + " [" + this.plistVersion + "]" );
        this.debug( "this.isPlistInfoParamsSet() = " + this.isPlistInfoParamsSet() + " [" + this.plistInfoParams + "]" );
        this.debug( "======= DEBUG VALUE OUTPUT END =======" );

        // check if one of info's values is set (either file name, or
        // parameters)
        if ( ( this.isPlistInfoSet() ^ this.isPlistInfoParamsSet() ) == false )
        {
            final String msg =
                "Either <plistInfo> (configured: " + this.isPlistInfoSet() + ") or "
                    + "<plistInfoParams> (configured: " + this.isPlistInfoParamsSet() + ") "
                    + "has to be defined in pom.xml for buildmac plugin!";
            throw new MojoExecutionException( msg );
        }

        if ( this.isPlistVersionSet() == false && this.isPlistVersionParamsSet() == false )
        {
            this.info( "Neither plistVersion nor plistVersionParams defined, using default values instead." );
            this.plistVersionParams = new HashMap( 0 ); // <String, String>
        }

        // set default app name
        if ( this.appName == null )
        {
            this.debug( "No appName defined, using project name '" + this.projectName + "' instead." );
            this.appName = this.projectName;
        }

        // set default app version
        if ( this.appVersion == null )
        {
            this.debug( "No appVersion defined, using project version '" + this.projectVersion + "' instead." );
            this.appVersion = this.projectVersion;
        }

        new Executer( this, this ).execute();

    }

    // should NOT be accessible; only used to get default values (by maven
    // expression) for appName/appVersion
    // public String getProjectName()
    // {
    // return this.projectName;
    // }
    //
    // public String getProjectVersion()
    // {
    // return this.projectVersion;
    // }

    // IMojoLogger
    public void debug( final String msg )
    {
        getLog().debug( msg );
    }

    // IMojoLogger
    public void info( final String msg )
    {
        getLog().info( msg );
    }

    // IMojoData
    public String getAppName()
    {
        return this.appName;
    }

    // IMojoData
    public String getAppVersion()
    {
        return this.appVersion;
    }

    // IMojoData
    public File getAppIcon()
    {
        return this.appIcon;
    }

    // IMojoData
    public File getPlistInfo()
    {
        if ( this.isPlistInfoParamsSet() == true )
        {
            throw new IllegalStateException( "Accessing PlistInfo although PlistInfoParams is set!" );
        }
        return this.plistInfo;
    }

    // IMojoData
    public String getPlistInfoParams( final PlistInfoParam param )
    {
        return (String) this.plistInfoParams.get( param.getXmlPomKey() );
    }

    // IMojoData
    public File getPlistVersion() // TODO rename {PlistVersion, PlistInfo} to:
    // <*>FileName
    {
        if ( this.isPlistVersionParamsSet() == true )
        {
            throw new IllegalStateException( "Accessing PlistVersion although PlistVersionParams is set!" );
        }
        return this.plistVersion;
    }

    // IMojoData
    public String getPlistVersionParams( final PlistVersionParam param ) // TODO
    // rename
    // {Params}
    // to:
    // Parameters
    {
        if ( this.isPlistVersionSet() == true )
        {
            throw new IllegalStateException( "Accessing PlistVersionParams although PlistVersion is set!" );
        }
        return (String) this.plistVersionParams.get( param.getXmlPomKey() );
    }

    // IMojoData
    public List getAdditionalResources()
    {
        return this.additionalResources;
    }

    // IMojoData
    public File getTargetDirectory()
    {
        return this.targetDirectory;
    }

    // IMojoData
    public File getBaseDirectory()
    {
        return this.baseDirectory;
    }

    // IMojoData
    public boolean isPlistInfoParamsSet()
    {
        final boolean result = this.plistInfoParams.isEmpty() == false;
        assert ( result ^ this.isPlistInfoSet() );
        return result;
    }

    // IMojoData
    public boolean isPlistVersionParamsSet()
    {
        final boolean result = this.plistVersionParams.isEmpty() == false;
        assert ( result ^ this.isPlistVersionSet() );
        return result;
    }

    // IMojoData
    public boolean isPlistInfoSet()
    {
        return this.plistInfo.getName().equals( TRICK_MAVEN ) == false;
    }

    // IMojoData
    public boolean isPlistVersionSet()
    {
        return this.plistVersion.getName().equals( TRICK_MAVEN ) == false;
    }

}
