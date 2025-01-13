package net.sourceforge.jpotpourri.codegen.java;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import net.sourceforge.jpotpourri.util.PtCloseUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
class CodeGenerator implements IPtCodeGenerator {

	private static final Log LOG = LogFactory.getLog(CodeGenerator.class);

	private static final boolean DEBUG = false;
	
	private final File sourceFolder;
	
	public CodeGenerator(final File sourceFolder) {
		this.sourceFolder = sourceFolder;
	}
	
	public void process(final PtAbstractGenClass genClass) throws PtCodeGeneratorException {
		if(DEBUG == true) {
			System.out.println("==========================");
			System.out.println(genClass.toCode());
			System.out.println("==========================");
			return;
		}
		
		this.processClass(genClass, false);
		
		if(genClass.isGenerateManClassSet() == true) {
			this.processClass(PtAbstractGenClass.newManClass(genClass, genClass.getManClassDefinition()), true);
		}
		LOG.info("Finished generating code.");
	}
	
	private void processClass(
			final PtAbstractGenClass clazz,
			final boolean isManClass
			) throws PtCodeGeneratorException {
		final File targetFile = this.getTargetFile(clazz.getPackageName(), clazz.getClassName());
		
		if(isManClass == false) { // only do for genClasses
			checkFileAndDirectory(targetFile);
		} else {
			assert(targetFile.getParentFile().exists() == true) : "Not existing: " + targetFile.getParent();
		}
		
		if(isManClass == false || (isManClass == true && targetFile.exists() == false)) {
			// only write if genClass, or manClass is not yet existing
			writeCodeToFile(clazz.toCode(), targetFile);
		}
	}
	
	private static void checkFileAndDirectory(final File targetFile) throws PtCodeGeneratorException {
		if(targetFile.exists() == true) {
			LOG.info("Deleting existing genFile at [" + targetFile.getAbsolutePath() + "].");
			if(targetFile.delete() == false) {
				throw new PtCodeGeneratorException("Could not delete target file at " +
						"[" + targetFile.getAbsolutePath() + "]!");
			}
		}
		
		final File targetDirectory = targetFile.getParentFile();
		if(targetDirectory.exists() == false) {
			LOG.info("Creating target directory: " + targetDirectory.getAbsolutePath());
			if(targetDirectory.mkdirs() == false) {
				throw new PtCodeGeneratorException("Could not create directories for " +
						"[" + targetDirectory.getAbsolutePath() + "]!");
			}
		}
	}
	
	private static void writeCodeToFile(final String code, final File file) throws PtCodeGeneratorException {
		LOG.debug("Writing code to file: " + file.getAbsolutePath());
		assert(file.exists() == false);
		
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(code);
		} catch (IOException e) {
			throw new PtCodeGeneratorException("Could not write code to file [" + file.getAbsolutePath() + "]!", e);
		} finally {
			PtCloseUtil.close(writer);
		}
	}
	
	
	private File getTargetFile(final String packageName, final String className) {
		final String packageFolder = packageName.replaceAll("\\.", "/");
		final File folder = new File(this.sourceFolder, packageFolder);
		final String fileName = className + ".java";
		return new File(folder, fileName);
	}
}
