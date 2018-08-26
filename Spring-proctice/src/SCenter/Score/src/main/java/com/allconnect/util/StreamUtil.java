package com.AL.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class StreamUtil 
{
	private static final Logger logger = Logger.getLogger(StreamUtil.class);

	// convert InputStream to String
	public static String getStringFromInputStream(InputStream is) 
	{
 
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
 
		String line;
		try {
 
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
 
		} catch (IOException e) {
			logger.error("Error in converting from input stream to string" + e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("Error in closing buffer reader" + e);
				}
			}
		}
 
		return sb.toString();
 
	}
	
}
