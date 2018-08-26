package com.A.util.fileingest;

 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;

public enum GZipUtil {

	INSTANCE;

	final static Logger logger = Logger.getLogger(GZipUtil.class);
	final static String PATH_DEFAULT = "./tmp/";
	final static String FILENAME_DEFAULT = "templates.zip";

	final int BUFFER = 2048;

	public void unzip() {
		unzip(PATH_DEFAULT, FILENAME_DEFAULT);
	}

	public void unzip(String fullPath, String destinationDir ) {

		try {

			byte[] buf = new byte[BUFFER];
			ZipInputStream zipinputstream = null;
			ZipEntry zipentry;
			zipinputstream = new ZipInputStream(new FileInputStream(fullPath));

			zipentry = zipinputstream.getNextEntry();
			while (zipentry != null) {
				// for each entry to be extracted
				String entryName = destinationDir + zipentry.getName();
				entryName = entryName.replace('/', File.separatorChar);
				entryName = entryName.replace('\\', File.separatorChar);
				logger.info("entryname " + entryName);
				int n;
				FileOutputStream fileoutputstream;
				File newFile = new File(entryName);
				if (zipentry.isDirectory()) {
					if (!newFile.mkdirs()) {
						break;
					}
					zipentry = zipinputstream.getNextEntry();
					continue;
				}

				fileoutputstream = new FileOutputStream(entryName);

				while ((n = zipinputstream.read(buf, 0, BUFFER)) > -1) {
					fileoutputstream.write(buf, 0, n);
				}

				fileoutputstream.close();
				zipinputstream.closeEntry();
				zipentry = zipinputstream.getNextEntry();

			}// while

			zipinputstream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// public void unzip(String destinationDir , String filename) {
	//
	// final String fullPathFilename = destinationDir + filename;
	//
	// try {
	// BufferedOutputStream dest = null;
	// FileInputStream fis = new FileInputStream(fullPathFilename);
	// ZipInputStream zis = new ZipInputStream(
	// new BufferedInputStream(fis));
	// ZipEntry entry;
	// while ((entry = zis.getNextEntry()) != null) {
	// logger.info("Extracting: " + entry);
	// int count;
	// byte data[] = new byte[BUFFER];
	// // write the files to the disk
	// FileOutputStream fos = new FileOutputStream(destinationDir
	// + entry.getName());
	// dest = new BufferedOutputStream(fos, BUFFER);
	// while ((count = zis.read(data, 0, BUFFER)) != -1) {
	// dest.write(data, 0, count);
	// }
	// dest.flush();
	// dest.close();
	// }
	// zis.close();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
}
