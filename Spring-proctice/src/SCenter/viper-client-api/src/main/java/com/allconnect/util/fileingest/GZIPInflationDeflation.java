
package com.A.util.fileingest;

import com.A.util.fileingest.FileWorkConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

 
public class GZIPInflationDeflation implements InflationDeflation
{
	private static final Logger log = LoggerFactory.getLogger(GZIPInflationDeflation.class);
	private FileWorkConfig fileWorkConfig;

	public static InflationDeflation getInstance(FileWorkConfig fileWorkConfig)
	{
		return new GZIPInflationDeflation(fileWorkConfig);
	}

	private GZIPInflationDeflation(FileWorkConfig fileWorkConfig)
	{

		this.fileWorkConfig = fileWorkConfig;
	}

	@Override
	public String inflateStream(InputStream fileInputStream, String fileName)
	{

		if (null == fileName || emptyString.equals(fileName))
		{
			throw new IllegalArgumentException("Filename can not be null or empty.");
		}
		if (null == fileInputStream)
		{
			throw new IllegalArgumentException("FileInputStream can not be null or empty.");
		}
		//FileInputStream fin = null;
		// TODO Refactor this
		String returnFileName = fileName.substring(0, fileName.length() - 3);
		try
		{
			//fin = new FileInputStream(fileName);
			GZIPInputStream gzin = new GZIPInputStream(fileInputStream);
			FileOutputStream fout = new FileOutputStream(returnFileName);
			for (int c = gzin.read(); c != -1; c = gzin.read())
			{
				fout.write(c);
			}
			fout.close();
		}
		catch (IOException ex)
		{
			log.error(ex.getMessage(), ex);
			throw new RuntimeException(ex.getMessage(), ex);

		}
		return returnFileName; //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String deflateStream(InputStream fileInputStream, String fileName)
	{
		if (null == fileName || emptyString.equals(fileName))
		{
			throw new IllegalArgumentException("Filename can not be null or empty.");
		}
		if (null == fileInputStream)
		{
			throw new IllegalArgumentException("FileInputStream can not be null or empty.");
		}
		//FileInputStream returnFileInputStream = null;
		String returnFileName = fileWorkConfig.getWorkDirectoryPath() + constructOutFileName(fileName)
			+ fileWorkConfig.getGzipFileSuffix();
		try
		{
			//returnFileInputStream = new FileInputStream(returnFileName);
			OutputStream fout = new FileOutputStream(returnFileName);
			GZIPOutputStream gzout = new GZIPOutputStream(fout);
			for (int c = fileInputStream.read(); c != -1; c = fileInputStream.read())
			{
				gzout.write(c);
			}
			gzout.close();
		}
		catch (IOException ex)
		{
			log.error(ex.getMessage(), ex);
			throw new RuntimeException(ex.getMessage(), ex);
		}
		return returnFileName; //To change body of implemented methods use File | Settings | File Templates.
	}

	private final static String emptyString = "";

	@Override
	public String inflateFile(String fileName)
	{

		if (null == fileName || emptyString.equals(fileName))
		{
			throw new IllegalArgumentException("Filename can not be null or empty.");
		}
		// TODO Refactor this
		String returnFileName = fileName.substring(0, fileName.length() - 3);
		try
		{
			FileInputStream fin = new FileInputStream(fileName);
			String returnFile = this.inflateStream(fin, fileName);
		}
		catch (Exception ex)
		{
			log.error(ex.getMessage(), ex);
			throw new RuntimeException(ex.getMessage(), ex);
		}

		return returnFileName;
	}

	@Override
	public String deflateFile(String fileName)
	{

		if (null == fileName || emptyString.equals(fileName))
		{
			throw new IllegalArgumentException("Filename can not be null or empty.");
		}
		String returnFileName = fileWorkConfig.getWorkDirectoryPath() + constructOutFileName(fileName)
			+ fileWorkConfig.getGzipFileSuffix();
		try
		{
			FileInputStream fin = new FileInputStream(fileName);
			String returnFile = this.deflateStream(fin, fileName);
		}
		catch (Exception ex)
		{
			log.error(ex.getMessage(), ex);
			throw new RuntimeException(ex.getMessage(), ex);
		}

		return returnFileName;
	}

	/*
	    We are assuming this to be a nix based filesystem.
	 */
	private String constructOutFileName(String fileNameWithPath)
	{
		int index1 = (fileNameWithPath.lastIndexOf("/") < 0) ? 0 : fileNameWithPath.lastIndexOf("/");
		String fileName = fileNameWithPath.substring(index1);
		return fileName;
	}
}
