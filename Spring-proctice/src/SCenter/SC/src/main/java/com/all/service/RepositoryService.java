package com.AL.service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import com.AL.ui.vo.FileStream;


/**
 * @author pmyneni
 *
 */
public interface RepositoryService 
{
	/**
	 * Puts the Object into Amazon S3 bucket
	 * 
	 * @param name
	 * @param is
	 */
	public void putObject(String name, InputStream is);
	
	/**
	 * Gets all the key names in a bucket
	 * 
	 * 
	 * @return List<String>
	 */
	public List<String> getKeyList();
	
	/**
	 * Gets the Object from bucket
	 * 
	 * @param name
	 * @return FileStream
	 * @throws FileNotFoundException
	 */
	public FileStream getObjectByName(String name) throws FileNotFoundException;
}
