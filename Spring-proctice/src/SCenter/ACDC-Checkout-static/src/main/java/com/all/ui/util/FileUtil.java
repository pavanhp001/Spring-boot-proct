package com.AL.ui.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

	public static void log(Logger logger, MultipartFile file) {
		logger.debug("size::" + file.getSize());
		logger.debug("getContentType::" + file.getContentType());
		logger.debug("getName::" + file.getName());
		logger.debug("getOriginalFilename::" + file.getOriginalFilename());

	}

	public static void deleteDirChildren(File dir) {

		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				deleteDir(new File(dir, children[i]));

			}
		}

	}

	public static void deleteDir(File dir) {

		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				deleteDir(new File(dir, children[i]));

			}
		}

		// The directory is now empty so delete it
		dir.delete();

	}

	public static void delete(String path, String filename) {

		String fileName = path + filename;

		File f = new File(fileName);

		// Make sure the file or directory exists and isn't write protected
		if (!f.exists())
			throw new IllegalArgumentException(
					"Delete: no such file or directory: " + fileName);

		if (!f.canWrite())
			throw new IllegalArgumentException("Delete: write protected: "
					+ fileName);

		// If it is a directory, make sure it is empty
		if (f.isDirectory()) {
			String[] files = f.list();
			if (files.length > 0)
				throw new IllegalArgumentException(
						"Delete: directory not empty: " + fileName);
		}

		// Attempt to delete it
		boolean success = f.delete();

		if (!success)
			throw new IllegalArgumentException("Delete: deletion failed");
	}

	public static String create(InputStream inputStream, String path,
			String filename) {

		OutputStream out = null;

		try {

			File dir = new File(path);

			if (!dir.exists()) {
				dir.mkdirs();
			}

			File f = new File(path + filename);

			out = new FileOutputStream(f);

			int readBytes = 0;
			byte[] buffer = new byte[10000];
			while ((readBytes = inputStream.read(buffer, 0, 10000)) != -1) {
				out.write(buffer, 0, readBytes);
			}

			System.out
					.println("\nFile is created........ ........................."
							+ f.getCanonicalPath());

			f.getParentFile().setExecutable(true);
			f.getParentFile().setReadable(true);
			f.getParentFile().setWritable(true);

			f.setExecutable(true);
			f.setReadable(true);
			f.setWritable(true);

			return f.getCanonicalPath();
		} catch (IOException e) {
			throw new IllegalArgumentException("illegal Argument:"
					+ e.getMessage());
		} finally {
			try {
				out.close();
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/**
	 * @param fileName
	 *            name to get FileName
	 * @return String contents
	 */
	public static String getStringContent(final String fileName) {
		File file = new File(fileName);
		StringBuffer contents = new StringBuffer();
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));
			String text = null;

			// repeat until all lines is read
			while ((text = reader.readLine()) != null) {
				contents.append(text).append(
						System.getProperty("line.separator"));
			}

			return contents.toString();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public static void writeToFile(String aFileName, String aStringOutput) {

		FileOutputStream out; // declare a file output object
		PrintStream p; // declare a print stream object

		try {
			// Create a new file output stream
			out = new FileOutputStream(aFileName);

			// Connect print stream to the output stream
			p = new PrintStream(out);
			p.println(aStringOutput);
			p.close();
		} catch (Exception e) {
			System.err.println("Error writing to file");
			e.printStackTrace();
		}

	}

}
