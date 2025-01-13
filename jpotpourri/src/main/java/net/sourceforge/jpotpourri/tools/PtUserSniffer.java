package net.sourceforge.jpotpourri.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtUserSniffer {

    /** class' own logger using log4j */
    private static final Log LOG = LogFactory.getLog(PtUserSniffer.class);

    /**  */
    private static PtOperatingSystem os;


    
    
    private PtUserSniffer() {
    	// no instantiation
    }
    
    /**
     *
     * @return
     */
    public static PtOperatingSystem getOS() {
        if(PtUserSniffer.os == null) {
            final String osname = System.getProperty("os.name");

            if (osname.contains("Mac")) {
                PtUserSniffer.os = PtOperatingSystem.MAC;
            } else if (osname.contains("Windows")) {
                PtUserSniffer.os = PtOperatingSystem.WIN;
            } else if (osname.contains("Linux")) {
                PtUserSniffer.os = PtOperatingSystem.LINUX;
            } else if (osname.contains("Unix") || osname.contains("UNIX")  || osname.contains("HP-UX")
                    || osname.contains("AIX") || osname.contains("BSD") || osname.contains("Irix")) {
                PtUserSniffer.os = PtOperatingSystem.UNIX;
            } else if (osname.contains("SunOS") || osname.contains("Solaris")) {
                PtUserSniffer.os = PtOperatingSystem.SOLARIS;
            } else if (osname.contains("OS/2")) {
                PtUserSniffer.os = PtOperatingSystem.OS2;
            } else {
                PtUserSniffer.LOG.warn("could not determine operating system by name [" + osname + "]");
                PtUserSniffer.os = PtOperatingSystem.UNKOWN;
            }
            
            assert(PtUserSniffer.os != null);
            LOG.debug("Seems as user is running '" + PtUserSniffer.os + "'.");
        }
    	return PtUserSniffer.os;
    }

    /**
     *
     * @param os
     * @return
     */
    public static boolean isOperatingSystem(final PtOperatingSystem isOs) {
        return PtUserSniffer.getOS().equals(isOs);
    }

    public static boolean isWindows() {
        return PtUserSniffer.getOS().equals(PtOperatingSystem.WIN);
    }

    public static boolean isMacOSX() {
        return PtUserSniffer.getOS().equals(PtOperatingSystem.MAC);
    }

    public static boolean isLinux() {
        return PtUserSniffer.getOS().equals(PtOperatingSystem.LINUX);
    }

    public static boolean isUnix() {
        return PtUserSniffer.getOS().equals(PtOperatingSystem.UNIX);
    }

    public static boolean isOS2() {
        return PtUserSniffer.getOS().equals(PtOperatingSystem.OS2);
    }

    public static boolean isUnkown() {
        return PtUserSniffer.getOS().equals(PtOperatingSystem.UNKOWN);
    }

}
