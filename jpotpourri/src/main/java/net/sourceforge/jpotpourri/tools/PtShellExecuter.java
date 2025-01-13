package net.sourceforge.jpotpourri.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.sourceforge.jpotpourri.PtException;

import org.apache.log4j.Logger;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtShellExecuter {

    /** class' own logger using log4j */
    private static final Logger LOG = Logger.getLogger(PtUserSniffer.class); // FIXME remove each and every log4j stuff (use commons-logging instead)
    
    private static final String[] ENV_VARS = {
        "PATH=/opt/local/bin:/bin:/sbin:/usr/bin:/usr/sbin:/usr/local/bin"
    };
    
    private static final File PWD = new File(".");
    
    
    private PtShellExecuter() {
    	// no instantiation
    }
    
    public static void executeProcess(final String cmd) throws PtException {
        LOG.debug("Going to execute process with command: " + cmd);
        
        try {
            Process process = Runtime.getRuntime().exec(cmd, ENV_VARS, PWD);
            StreamGobbler outputGobbler = new StreamGobbler(process.getInputStream());
            StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream());
            outputGobbler.start();
            errorGobbler.start();
            final int returnCode = process.waitFor();
            
            if(returnCode != 0) {
                final String processOutput = outputGobbler.getResponse();
                if(processOutput.length() > 0) {
                    LOG.debug("process output:\n" + processOutput);
                } else {
                    LOG.debug("process output was empty.");
                }
                
                throw new PtException("Process exited with code " + returnCode + "! Error output was:\n" +
                		errorGobbler.getResponse());
            }
        } catch (IOException e) {
            throw new PtException("Converting file to mp3 failed because of an I/O exception!", e);
        } catch (InterruptedException e) {
            throw new PtException("Converting file to mp3 failed because thread was interrupted!", e);
        }
    }

    /**
     * @author christoph_pickl@users.sourceforge.net
     */
    private static class StreamGobbler extends Thread {
        
    	private InputStream input;
        
        private StringBuffer outputText = new StringBuffer();
        
        public StreamGobbler(final InputStream input) {
            this.input = input;
        }
        
        @Override
		public void run() {
            try {
                final BufferedReader reader = new BufferedReader(new InputStreamReader(this.input));
                String line;
                while((line = reader.readLine()) != null) {
                    this.outputText.append(line).append("\n");
                }
            } catch(IOException e) {
                LOG.error(e);
            }
        }
        
        public String getResponse() {
            return this.outputText.toString();
        }
    }
}
