package net.sourceforge.jpotpourri.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import net.sourceforge.jpotpourri.PtException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtZipUtil {

    private static final Log LOG = LogFactory.getLog(PtZipUtil.class);

    private static final int BUFFER_SIZE = 8192;


    private PtZipUtil() {
        /* no instantiation */
    }

    public static void unzip(final File file, final ZipFile zipFile,
    		final File targetDirectory) throws PtException {
        LOG.info("Unzipping zip file '" + file.getAbsolutePath() + "' to directory " +
        		"'" + targetDirectory.getAbsolutePath() + "'.");
        assert(file.exists() && file.isFile());

        if(targetDirectory.exists() == false) {
            LOG.debug("Creating target directory.");
            if(targetDirectory.mkdirs() == false) {
                throw new PtException("Could not create target directory at " +
                		"'" + targetDirectory.getAbsolutePath() + "'!");
            }
        }

        ZipInputStream zipin = null;
        try {
//            final ZipFile zipFile = new ZipFile(file);

            zipin = new ZipInputStream(new FileInputStream(file));
            ZipEntry nextZipEntry = zipin.getNextEntry();
            while (nextZipEntry != null) {
                LOG.debug("Unzipping entry '" + nextZipEntry.getName() + "'.");

                if(nextZipEntry.isDirectory()) {
                    LOG.debug("Skipping directory.");
                    continue;
                }

                final File targetFile = new File(targetDirectory, nextZipEntry.getName());
                final File parentTargetFile = targetFile.getParentFile();
                if(parentTargetFile.exists() == false) {
                    LOG.debug("Creating directory '" + parentTargetFile.getAbsolutePath() + "'.");
                    if(parentTargetFile.mkdirs() == false) {
                        throw new PtException("Could not create target directory at " +
                        		"'" + parentTargetFile.getAbsolutePath() + "'!");
                    }
                }

                InputStream input = null;
                FileOutputStream output = null;
                try {
                    input = zipFile.getInputStream(nextZipEntry);
                    if(targetFile.createNewFile() == false) {
                        throw new PtException("Could not create target file " +
                        		"'" + targetFile.getAbsolutePath() + "'!");
                    }
                    output = new FileOutputStream(targetFile);

                    byte[] buffer = new byte[BUFFER_SIZE];
                    int readBytes = input.read(buffer, 0, buffer.length);
                    while (readBytes > 0) {
                        output.write(buffer, 0, readBytes);
                        readBytes = input.read(buffer, 0, buffer.length);
                    }
                } finally {
                	PtCloseUtil.close(input, output);
                }
                
                nextZipEntry = zipin.getNextEntry();
            }
        } catch (IOException e) {
            throw new PtException("Could not unzip file '" + file.getAbsolutePath() + "'!", e);
        } finally {
        	PtCloseUtil.close(zipin);
        }
    }

    public static void zipDirectory(final File sourceDirectory, final File targetZipFile) throws PtException {
        LOG.info("Zipping directory '" + sourceDirectory.getAbsolutePath() + "' to file " +
        		"'" + targetZipFile.getAbsolutePath() + "'.");
        assert(sourceDirectory.exists() && sourceDirectory.isDirectory());
        assert(targetZipFile.exists() == false);

        ZipOutputStream zipout = null;
        boolean finishedSuccessfully = false;
        try {

            zipout = new ZipOutputStream(new FileOutputStream(targetZipFile));
            zipout.setLevel(9);
            zipFiles(zipout, sourceDirectory, sourceDirectory);
            zipout.finish();
            finishedSuccessfully = true;

        } catch (Exception e) {
            throw new PtException("Could not zip directory '" + sourceDirectory.getAbsolutePath() + "'!", e);
        } finally {
        	PtCloseUtil.close(zipout);

            if(finishedSuccessfully == false &&
        		targetZipFile.exists() &&
        		targetZipFile.delete() == false) {
                LOG.warn("Could not delete zip file '" + targetZipFile.getAbsolutePath() + "'!");
            }
        }
    }

    private static void zipFiles(final ZipOutputStream zipout, final File file,
    		final File sourceDirectory) throws IOException {
        if(file.isDirectory()) {
            for (File subFile : file.listFiles()) {
                zipFiles(zipout, subFile, sourceDirectory);
            }
        } else { // file.isFile()
            final String entryName = getZipEntryName(file, sourceDirectory);
            LOG.debug("Zipping file '" + file.getAbsolutePath() + "' as entry '" + entryName + "'.");
            final ZipEntry entry = new ZipEntry(entryName);

            BufferedInputStream fileInput = null;
            try {
                fileInput = new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE);

                byte[] buffer = new byte[BUFFER_SIZE];
                zipout.putNextEntry(entry);

                int count = fileInput.read(buffer, 0, BUFFER_SIZE);
                while (count != -1) {
                  zipout.write(buffer, 0, count);
                  count = fileInput.read(buffer, 0, BUFFER_SIZE);
                }

            zipout.closeEntry();
            } finally {
            	PtCloseUtil.close(fileInput);
            }
        }
    }

    private static String getZipEntryName(final File file, final File sourceDirectory) {
        final String filePath = file.getAbsolutePath();
        return filePath.substring(sourceDirectory.getAbsolutePath().length() + 1, filePath.length());
    }


    public static void main(final String[] args) throws PtException {
//        ZipUtil.zipDirectory(new File("/zip/covers"), new File("/zip/covers.zip"));

        File file = new File("/zip/asdf.script");
        ZipFile zipFile;
        try {
            zipFile = new ZipFile(file);
        } catch (ZipException e) {
            System.out.println("invalid zip file");
            return;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        PtZipUtil.unzip(file, zipFile, new File("/zip/unzippedCovers"));
    }
}
