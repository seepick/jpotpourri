package net.sourceforge.jpotpourri.util;

import java.io.Closeable;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtCloseUtil {

    private static final Log LOG = LogFactory.getLog(PtCloseUtil.class);

    protected PtCloseUtil() {
        // no instantiation
    }

    public static void close(final Closeable... closeables) {
    	if(closeables == null) {
    		return;
    	}
    	for (Closeable closeable : closeables) {
    		closeSingle(closeable);
		}
    }
    
    private static void closeSingle(final Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                LOG.error("Could not close closeable of type " + closeable.getClass().getName() + "!", e);
            }
        }
    }
    
}
