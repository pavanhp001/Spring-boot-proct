package com.A.V.gateway.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public enum FileUtil {

	INSTANCE;
	
	public   String readFileToString(String filename) {
		File aFileName = new File(filename);
		StringBuffer sb = new StringBuffer();
		String line = null;

		try {
			FileReader inputFile = new FileReader(aFileName);
			BufferedReader inputBuffer = new BufferedReader(inputFile);

			while ((line = inputBuffer.readLine()) != null)
				sb.append(line);

			inputBuffer.close();
			inputFile.close();

		}
		// Catches any error conditions
		catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}
	
	/**
	 * @param fileName
	 *            name to get FileName
	 * @return String contents
	 */
	public String getStringContent(final String fileName) {
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
	
}
