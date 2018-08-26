package com.AL.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

/**
 * @author pmyneni@AL.com
 *
 */
public class CompressorUtil 
{

	/**
	 * Compresses given String to Base64 Format
	 * 
	 * @param srcTxt
	 * @return String
	 * @throws IOException
	 */
	public static String compressString(String srcTxt)
	throws IOException 
	{
		ByteArrayOutputStream rstBao = new ByteArrayOutputStream();
		GZIPOutputStream zos = new GZIPOutputStream(rstBao);
		zos.write(srcTxt.getBytes());
		IOUtils.closeQuietly(zos);

		byte[] bytes = rstBao.toByteArray();

		return Base64.encodeBase64String(bytes);
	}

	
	/**
	 * When client receives the zipped base64 string, it first decode base64
	 * String to byte array, then use ZipInputStream to revert the byte array to a
	 * string.
	 * 
	 * @param zippedBase64Str
	 * @return String
	 * @throws IOException
	 */
	public static String uncompressString(String zippedBase64Str)
	throws IOException 
	{
		String result = null;
		byte[] bytes = Base64.decodeBase64(zippedBase64Str);
		GZIPInputStream zi = null;
		try
		{
			zi = new GZIPInputStream(new ByteArrayInputStream(bytes));
			result = IOUtils.toString(zi);
		}
		finally 
		{
			IOUtils.closeQuietly(zi);
		}

		return result;
	}
}
