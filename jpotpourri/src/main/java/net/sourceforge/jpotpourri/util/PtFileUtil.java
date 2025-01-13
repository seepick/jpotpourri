package net.sourceforge.jpotpourri.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.Set;

import net.sourceforge.jpotpourri.PtException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtFileUtil {

    private static final Log LOG = LogFactory.getLog(PtFileUtil.class);

    private static final long FIVE_MEGABYTE_IN_BYTE = 1024 * 1024 * 5;


    private static final Set<String> HIDDEN_FILE_NAMES = PtCollectionUtil.immutableSet(
            // apple system files
            ".DS_Store", "Icon\r",
            // windows system files
            "Thumbs.db");
    
    
    protected PtFileUtil() {
        // no instantiation
    }
    

    /**
     * @return null if no extension; otherwise lowercase suffix behind last "."
     */
    public static String extractExtension(final File file) {
        return extractExtension(file.getName());
    }

    /**
     * @return null if no extension; otherwise lowercase suffix behind last "."
     */
    public static String extractExtension(final String fileName) {
        if(fileName.indexOf(".") > 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        }
        return null;
    }
    
    public static void copyFile(final File sourceFile, final File targetFile) throws PtException {
        if(sourceFile.exists() == false) {
            throw new IllegalArgumentException("Could not copy given file '" + sourceFile.getAbsolutePath() + "' " +
            		"becaus it does not exist!");
        }
        
        if(sourceFile.length() > FIVE_MEGABYTE_IN_BYTE) {
            copyBigFile(sourceFile, targetFile);
        } else {
            copySmallFile(sourceFile, targetFile);
        }
    }
    
    private static void copyBigFile(final File sourceFile, final File targetFile) throws PtException {
        LOG.debug("Copying BIG file '" + sourceFile.getAbsolutePath() + "' to '" + targetFile.getAbsolutePath() + "'.");
        
        if(sourceFile.exists() == false) {
            throw new PtException("Could not copy sourcefile '" + sourceFile.getAbsolutePath() + "' " +
            		"because it does not exist!");
        }
        
        InputStream input = null;
        OutputStream output = null;
        
        if(targetFile.exists() == true) {
            LOG.info("Overwrting existing target file '" + targetFile.getAbsolutePath() + "'.");
            if(targetFile.delete() == false) {
                throw new PtException("Could not delete target file '" + targetFile.getAbsolutePath() + "'!");
            }
        }
        try {
            input = new FileInputStream(sourceFile);
            output = new FileOutputStream(targetFile);
            
            byte[] bytes = new byte[1024];
            while (input.read(bytes) >= 0) {
                output.write(bytes);
            }
        } catch (IOException e) {
            throw new PtException("Could not copy file from '" + sourceFile.getAbsolutePath() + "' to " +
            		"'" + targetFile.getAbsolutePath() + "'!", e);
        } finally {
        	PtCloseUtil.close(input, output);
        }
    }
    
    /**
     * @see {@link http://www.rgagnon.com/javadetails/java-0064.html}
     */
    private static void copySmallFile(final File sourceFile, final File targetFile) throws PtException {
        LOG.debug("Copying SMALL file '" + sourceFile.getAbsolutePath() + "' to " +
        		"'" + targetFile.getAbsolutePath() + "'.");
        
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inChannel = new FileInputStream(sourceFile).getChannel();
            outChannel = new FileOutputStream(targetFile).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            
        } catch (IOException e) {
            throw new PtException("Could not copy file from '" + sourceFile.getAbsolutePath() + "' to " +
            		"'" + targetFile.getAbsolutePath() + "'!", e);
        } finally {
        	PtCloseUtil.close(inChannel, outChannel);
        }
    }
    
    public static File copyDirectoryRecursive(final File sourceDir, final File targetSuperDir) 
    	throws PtException {
        LOG.debug("Copying directory recursive '" + sourceDir.getAbsolutePath() + "' to " +
        		"'" + targetSuperDir.getAbsolutePath() + "'.");
        
        if(sourceDir.exists() == false) {
            throw new PtException("Could not copy sourcefile '" + sourceDir.getAbsolutePath() + "' " +
            		"because it does not exist!");
        }
        
        final File targetDir = new File(targetSuperDir, sourceDir.getName());
        if(targetDir.mkdir() == false) {
            throw new PtException("Could not create directory '" + targetDir.getAbsolutePath() + "'!");
        }
        
        for (File subFile : sourceDir.listFiles()) {
            if(subFile.isFile()) {
                copyFile(subFile, new File(targetDir, subFile.getName()));
            } else {
                copyDirectoryRecursive(subFile, targetDir);
            }
        }
        
        return targetDir;
    }
    
    private static final DecimalFormat FILE_SIZE_FORMAT = new DecimalFormat("0.0");
    /**
     * @return something like "13.3 KB" or "3.1 GB"
     */
    public static String formatFileSize(final long inKiloByte) {
//        if(inByte < 1024) {
//            return FILE_SIZE_FORMAT.format(inByte) + "B"; 
//        }
//            
//        final long inKiloByte = inByte / 1024;
        if(inKiloByte < 1024) {
            return FILE_SIZE_FORMAT.format(inKiloByte) + " KB";
        }
            
        final long inMegaByte = inKiloByte / 1024;
        if(inMegaByte < 1024) {
            return FILE_SIZE_FORMAT.format(inKiloByte / 1024.) + " MB";
        }

        return FILE_SIZE_FORMAT.format(inMegaByte / 1024) + " GB";
    }
    
    public static String formatFileSizeGb(final long inKiloByte) {
        return FILE_SIZE_FORMAT.format(inKiloByte / 1024. / 1024.) + " GB";
    }
    
    public static double getGigaByteFromKiloByte(final long kb) {
        double mb = kb / 1024.;
        double gb = mb / 1024. * 10;
        long gb10th = Math.round(gb);
        return gb10th / 10.;
    }

    public static void deleteDirectoryRecursive(final File directory) throws PtException {
        LOG.debug("Deleting directory '" + directory.getAbsolutePath() + "' recursive.");
        if(directory.exists() == false) {
        	throw new IllegalArgumentException("Directory '" + directory.getAbsolutePath() + "' does not exist!");
        }
        if(directory.isDirectory() == false) {
        	throw new IllegalArgumentException("Given file '" + directory.getAbsolutePath() + "' is not a directory!");
        }
        
        for (File subFile : directory.listFiles()) {
            if(subFile.isFile()) {
                LOG.debug("Deleting file '" + subFile.getAbsolutePath() + "'.");
                if(subFile.delete() == false) {
                    throw new PtException("Could not delete file '" + subFile.getAbsolutePath() + "' " +
                    		"(existing=" + subFile.exists() + ")!");
                }
                /* MINOR already fixed (???) - could not delete regular file ->
                 * 		maybe by closing xml file within 'ImporterBackup.parseXmlMovies' -> should work now.
DEBUG 2008-05-19 01:30:07,046 [SwingWorker-pool-11343231-thread-2] net.sourceforge.omov.core.model.db4o.AbstractDb4oDao
	--- Setting auto commit to true.
DEBUG 2008-05-19 01:30:07,046 [SwingWorker-pool-11343231-thread-2] net.sourceforge.omov.core.util.FileUtil 
	--- Deleting directory 'C:\JavaDev\omovApp\temp\backupImport-20080519_013006921' recursive.
DEBUG 2008-05-19 01:30:07,046 [SwingWorker-pool-11343231-thread-2] net.sourceforge.omov.core.util.FileUtil 
	--- Deleting file 'C:\JavaDev\omovApp\temp\backupImport-20080519_013006921\movies.xml'.
ERROR 2008-05-19 01:30:07,046 [SwingWorker-pool-11343231-thread-2] net.sourceforge.omov.core.tools.export.ImporterBackup
	--- Could not delete temporary import directory 'C:\JavaDev\omovApp\temp\backupImport-20080519_013006921'.
net.sourceforge.omov.core.BusinessException: Could not delete file
	'C:\JavaDev\omovApp\temp\backupImport-20080519_013006921\movies.xml'!
	
	at net.sourceforge.omov.core.util.FileUtil.deleteDirectoryRecursive(FileUtil.java:219)
	at net.sourceforge.omov.core.tools.export.ImporterBackup.process(ImporterBackup.java:148)
	at net.sourceforge.omov.app.gui.export.ImportSwingWorker.doInBackground(ImportSwingWorker.java:47)
	at net.sourceforge.omov.app.gui.export.ImportSwingWorker.doInBackground(ImportSwingWorker.java:1)
	at org.jdesktop.swingworker.SwingWorker$1.call(Unknown Source)
	at java.util.concurrent.FutureTask$Sync.innerRun(Unknown Source)
	at java.util.concurrent.FutureTask.run(Unknown Source)
	at org.jdesktop.swingworker.SwingWorker.run(Unknown Source)
	at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(Unknown Source)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(Unknown Source)
	at java.lang.Thread.run(Unknown Source)
                 */
            } else {
                deleteDirectoryRecursive(subFile);
            }
        }
        
        
        if(directory.delete() == false) {
            throw new PtException("Could not delete directory '" + directory.getAbsolutePath() + "'!");
        }
    }
    


    public static boolean isHiddenFile(final File file) {
        return HIDDEN_FILE_NAMES.contains(file.getName());
    }
    
//    public static String getFileContents(final File sourceFile, int initialCapacityOfStringBuilder) 
//	  	  throws BusinessException {
//        final StringBuilder sb = new StringBuilder(initialCapacityOfStringBuilder);
//        BufferedReader reader = null;
//        try {
//            reader = new BufferedReader(new FileReader(sourceFile));
//            String line = null;
//            while ( (line = reader.readLine()) != null) {
//                sb.append(line).append("\n");
//            }
//            
//            return sb.toString();
//        } catch (IOException e) {
//            throw new BusinessException("Could not get contents of file '"+sourceFile.getAbsolutePath()+"'!", e);
//        } finally {
//            CloseUtil.close(reader);
//        }
//    }
    
    public static String getFileContentsFromJar(final String jarFile, final int initialCapacityOfStringBuilder)
    	throws PtException {
        LOG.debug("Getting contents of file '" + jarFile + "' from jar.");
        final StringBuilder sb = new StringBuilder(initialCapacityOfStringBuilder);
        InputStream input = null;
        BufferedReader reader = null;
        try {
            input = PtFileUtil.class.getResourceAsStream(jarFile);
            reader = new BufferedReader(new InputStreamReader(input));
            String line = reader.readLine();
            while (line != null) {
                sb.append(line).append("\n");
                line = reader.readLine();
            }
            
            return sb.toString();
        } catch (IOException e) {
            throw new PtException("Could not get contents of resource '" + jarFile + "'!", e);
        } finally {
        	PtCloseUtil.close(reader);
        }
    }
    
    public static String extractLastFolderName(final String path) {
        final int index = path.lastIndexOf(File.separator);
        if(index == -1) {
            LOG.warn("Could not get last folder name of path '" + path + "'!");
            return null;
        }
        return path.substring(index + 1);
    }
    
    public static long getSizeRecursive(final File file) {
        if(file.isFile()) {
            return file.length() / 1024;
        }
        
        long size = 0;
        for (File subFile : file.listFiles()) {
            size += getSizeRecursive(subFile);
        }
        return size;
    }
    
    public static File getParentByPath(final File file) {
        final String path = file.getAbsolutePath();
        
        return new File(path.substring(0, path.lastIndexOf(File.separator)));
    }
    
//    public static String getCoverFile(Movie movie, CoverFileType type) {
//        assert(movie.isCoverFileSet()) : "Coverfile not set for movie: " + movie;
//        
//        final String extension = movie.getOriginalCoverFile().substring(
//    	      movie.getOriginalCoverFile().lastIndexOf(".") + 1);
//        final StringBuilder sb = new StringBuilder(20);
//        sb.append(movie.getId());
//        sb.append(type.getFilenamePart());
//        sb.append(".");
//        sb.append(extension);
//        return sb.toString();
//    }


	public static void writeFileContent(final File file, final String content) throws IOException {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(content);
		} finally {
			PtCloseUtil.close(writer);
		}
	}

	public static String getFileContent(final File file) throws IOException {
		return getFileContent(file.getAbsolutePath());
	}
	
	public static String getFileContent(final String absoluteAbsolutePath) throws IOException {
		final StringBuilder sb = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(absoluteAbsolutePath));
			String line = reader.readLine();
			while (line != null) {
				sb.append(line).append("\n");
				line = reader.readLine();
			}
		} finally {
			PtCloseUtil.close(reader);
		}
		return sb.toString();
	}
	
	public static void deleteFileSilently(final File file) {
		if(file.exists() == false) {
			return;
		}
		try {
			if(file.delete() == false) {
				LOG.error("Could not delete file [" + file.getAbsolutePath() + "]!");
			}
		} catch(Exception e) {
			LOG.error("Could not delete file [" + file.getAbsolutePath() + "]!", e);
		}
	}

	/*
try {
	FileInputStream fis = new FileInputStream(args[0]);
	byte[] contents = new byte[fis.available()];
	fis.read(contents, 0, contents.length);
	String asString = new String(contents, "ISO8859_1");
	byte[] newBytes = asString.getBytes("UTF8");
	FileOutputStream fos = new FileOutputStream(args[1]);
	fos.write(newBytes);
	fos.close();
	} catch(Exception e) {
	e.printStackTrace();
	}
}
	 */

	public static String getFileContentFromJar(final Class<?> clazz, final String fileName) throws IOException {
//		final InputStream input = clazz.getClassLoader().getResourceAsStream(fileName);
		final InputStream input = clazz.getResourceAsStream(fileName);
		
		if(input == null) {
			throw new IOException("Could not find resource [" + fileName + "]!");
		}
		
		final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		try {
			int byteRead;
			while((byteRead = input.read()) != -1) {
				byteStream.write(byteRead);
			}
		} finally {
			PtCloseUtil.close(input, byteStream);
		}
		return byteStream.toString("UTF-8");
	}
	
	/**
	 * this method is not capable of loading sources from a jar!
	 */
	public static String getFileContentFromClasspath(final Class<?> clazz, final String fileName) throws PtException {
		final StringBuilder sb = new StringBuilder();
		final URL url = clazz.getResource(fileName);
		
		if(url == null) {
			throw new PtException("Could not find file [" + fileName + "] by resource " +
									"for class [" + clazz.getName() + "]!");
		}
		
		final String encoding = "UTF8";
		final BufferedReader reader;
		
		// create reader instance
		try {
			final URI uri = url.toURI();
			LOG.trace("Loading file from classpath by URI [" + uri + "].");
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(uri)), encoding));
		} catch (URISyntaxException e) {
			throw new PtException("Malformed URI syntax by URL [" + url + "]!", e);
			//throw new IOException("Malformed URI syntax by URL [" + url + "]!", e); // only available since JDK6
		} catch (UnsupportedEncodingException e) {
			throw new PtException("Encoding [" + encoding + "] is not supported!", e);
		} catch (FileNotFoundException e) {
			throw new PtException("Could not find file [" + fileName + "]!", e);
		}
		
		// read file contents by line
		try {
			String line = reader.readLine();
			if(line != null) {
				sb.append(line);
			}
			line = reader.readLine();
			while(line != null) {
				sb.append("\n").append(line);
				line = reader.readLine();
			}
		} catch(IOException e) {
			throw new PtException("IO error while reading file [" + fileName + "]!", e);
		} finally {
			PtCloseUtil.close(reader);
		}
		
		return sb.toString();
	}
	
    
    public static void main(final String[] args) {
//        System.out.println(extractLastFolderName("/folder3/folder2/folder1"));
    }
    
}
