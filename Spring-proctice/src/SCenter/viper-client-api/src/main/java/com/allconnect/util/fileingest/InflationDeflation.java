
package com.A.util.fileingest;

 
import java.io.InputStream;

 
public interface InflationDeflation
{
	/**
	 * This method should take a InputStream and return a String with the path
	 * of the file with the last file extension removed.
	 * @param inputStream
	 * @return
	 */
	public String inflateStream(InputStream inputStream, String fileName);

	/**
	 * This method should take a InputStream and return a String with the path
	 * of the file with .gz appended to the file name.
	 * @param inputStream
	 * @return
	 */
	public String deflateStream(InputStream inputStream, String fileName);

	/**
	 * This method should take a fileName and return a String with the path
	 * of the file with the last file extension removed.
	 * @param fileName
	 * @return
	 */
	public String inflateFile(String fileName);

	/**
	 * This method should take a fileName and return a String with the path
	 * of the file with .gz appended to the file name.
	 * @param fileName
	 * @return
	 */
	public String deflateFile(String fileName);
}
