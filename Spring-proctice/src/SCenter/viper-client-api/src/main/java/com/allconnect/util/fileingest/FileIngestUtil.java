
package com.A.util.fileingest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

 
public class FileIngestUtil
{
	private static final Logger log = LoggerFactory.getLogger(FileIngestUtil.class);

	/**
	 * This method takes a file and returns a byte array representation
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytesFromFile(File file) throws IOException
	{
		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		if (length > Integer.MAX_VALUE)
		{
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)
		{
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length)
		{
			throw new IOException("Could not completely read file " + file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	/**
	 * This method takes an InputStream and returns a byte array representation
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytesFromInputStream(InputStream inputStream) throws IOException
	{
		BufferedInputStream buf = new BufferedInputStream(inputStream);
		// Get the size of the file
		long length = buf.available();

		if (length > Integer.MAX_VALUE)
		{
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = inputStream.read(bytes, offset, bytes.length - offset)) >= 0)
		{
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length)
		{
			throw new IOException("Could not completely read InputStream ");
		}

		// Close the input stream and return bytes
		inputStream.close();
		return bytes;
	}

	/**
	 * This method will return the String hexidecimal representation
	 * of an array of bytes.
	 * @param data
	 * @return
	 */
	public static String convertByteToHex(byte[] data)
	{
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++)
		{
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do
			{
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) ('0' + halfbyte));
				else
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			}
			while (two_halfs++ < 1);
		}
		return buf.toString();
	}

	/**
	 * This method takes an InputStream, digest algorithm and returns the byte array
	 * representation of the hashing algorithm.
	 * @param inputStream
	 * @param digest
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] getMessageDigestFromStream(InputStream inputStream, String digest) throws IOException,
		NoSuchAlgorithmException
	{
		byte[] inputBytes = getBytesFromInputStream(inputStream);
		MessageDigest messageDigest = MessageDigest.getInstance(digest.toUpperCase());
		messageDigest.update(inputBytes, 0, inputBytes.length);
		byte[] returnBytes = messageDigest.digest();
		return returnBytes;
	}

	/**
	 * This method takes an array inputBytes, digest algorithm and returns the byte array
	 * representation of the hashing algorithm.
	 * @param inputBytes
	 * @param digest
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] getMessageDigest(byte[] inputBytes, String digest) throws IOException,
		NoSuchAlgorithmException
	{
		MessageDigest messageDigest = MessageDigest.getInstance(digest.toUpperCase());
		messageDigest.update(inputBytes, 0, inputBytes.length);
		byte[] returnBytes = messageDigest.digest();
		return returnBytes;
	}

	/**
	 *
	 * @param inputStream
	 * @param digestAlgorithm
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static String getMessageDigestAsHex(InputStream inputStream, String digestAlgorithm) throws IOException,
		NoSuchAlgorithmException
	{
		byte[] returnBytes = getMessageDigestFromStream(inputStream, digestAlgorithm);
		String hexString = convertByteToHex(returnBytes);
		return hexString;
	}

	/**
	 *
	 * @param inputBytes
	 * @param digestAlgorithm
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static String getMessageDigestAsHex(byte[] inputBytes, String digestAlgorithm) throws IOException,
		NoSuchAlgorithmException
	{
		byte[] returnBytes = getMessageDigest(inputBytes, digestAlgorithm);
		String hexString = convertByteToHex(returnBytes);
		return hexString;
	}

	public static String createFileNameWithExtension(String fileName, String extension)
	{
		if (null == fileName)
		{
			throw new IllegalArgumentException("Filename cannot be null.");
		}
		if (null == extension || "".equals(extension.trim()))
		{
			throw new IllegalArgumentException("Filename extension cannot be null or empty.");
		}

		if (fileName.length() < 3)
		{
			throw new IllegalArgumentException("Filename has to be at least three characters.");
		}
		String newFileName = "";
		int index = fileName.lastIndexOf(".");
		if (index < 0)
		{
			newFileName = fileName + "." + extension;
		}
		else
		{
			newFileName = fileName.substring(0, fileName.lastIndexOf(".") + 1) + extension;
		}
		return newFileName;
	}

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException
	{
		log.info("Entering main!!!!!!!");
		if (null == args || args.length < 1)
		{
			log.error("At least one parameter expected.");
			System.exit(0);
		}
		if (args.length == 1)
		{
			String str = args[0];
			String hexCheckSum = getMessageDigestAsHex(str.getBytes(), "MD5");
			StringBuilder sb = new StringBuilder("The MD5 checksum returned for string ");
			sb.append(str).append(" is ");
			sb.append(hexCheckSum).append(".");
			log.info(sb.toString());
		}
		if (args.length == 2)
		{
			String fileName = args[0];
			String checkSumFileName = args[1];
			FileInputStream fis = new FileInputStream(fileName);
			FileInputStream fis2 = new FileInputStream(checkSumFileName);
			byte[] checkSumBytes = getBytesFromInputStream(fis2);
			String checkSumFromFile = new String(checkSumBytes);
			String hexCheckSum = getMessageDigestAsHex(fis, "MD5");
			StringTokenizer stringTokenizer = new StringTokenizer(checkSumFromFile);
			checkSumFromFile = stringTokenizer.nextToken();
			StringBuilder sb = new StringBuilder("\n");
			sb.append("Checksum for ").append(fileName).append(" calculated is: ").append("\t\t").append(
				hexCheckSum.trim()).append("\n");
			sb.append("Checksum in checksum file ").append(checkSumFileName).append(" is: ").append("\t\t").append(
				checkSumFromFile.trim());
			sb.append("\n");
			if (checkSumFromFile.trim().equals(hexCheckSum.trim()))
			{
				sb.append("Checksum comparison: PASS");
			}
			else
			{
				sb.append("Checksum comparison: FAIL");
			}
			log.info(sb.toString());
		}
	}

	public static void cleanUpWorkStream(final String deflatedFileName)
	{
		Thread cleaner = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				File file = new File(deflatedFileName);
				boolean deleted = file.delete();
			}
		});
		cleaner.start();
	}
}
