package com.AL.service.impl;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.AL.service.RepositoryService;
import com.AL.ui.vo.FileStream;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

/**
 * @author pmyneni
 *
 */

public class RepositoryAmazonS3Impl implements RepositoryService 
{
	private static final Logger logger = Logger.getLogger(RepositoryAmazonS3Impl.class);

	private static final String FOLDER_SUFFIX = "/";

	private AmazonS3 s3;
	private String bucket;	
	private String key;

	public void setKey(String key) 
	{
		this.key = key;
	}

	public void setBucket(String bucket)
	{
		this.bucket = bucket;
	}

	public RepositoryAmazonS3Impl(AmazonS3 client) 
	{
		s3 = client;
	}

	
	/* (non-Javadoc)
	 * @see com.AL.service.RepositoryService#getObjectByName(java.lang.String)
	 */
	public FileStream getObjectByName(String name)
	throws FileNotFoundException
	{	
		logger.debug(" Fetching the Object for keyName: ["+ getS3Key( key ) + name +"]");
		S3Object obj = s3.getObject(new GetObjectRequest(bucket, getS3Key( key ) + name));
		logger.debug(" Fetching the Object Completed!!! ");
		FileStream result = new FileStream(obj.getObjectContent(), obj.getObjectMetadata().getContentLength());
		return result;
	}

	
	/* (non-Javadoc)
	 * @see com.AL.service.RepositoryService#getKeyList()
	 */
	public List<String> getKeyList()
	{
		List<String> result = new ArrayList<String>();	
		//Gets only 1000 items at a time
		ObjectListing current = s3.listObjects(bucket,getS3Key( key ));
		List<S3ObjectSummary> keyList = current.getObjectSummaries();
		current = s3.listNextBatchOfObjects(current);

		while (current.isTruncated())
		{
		keyList.addAll(current.getObjectSummaries());
		current = s3.listNextBatchOfObjects(current);
		}
		keyList.addAll(current.getObjectSummaries());
		
		for (S3ObjectSummary summary: keyList) 
		{
			//ignore folders
			if(! summary.getKey().endsWith(FOLDER_SUFFIX))
			{
				result.add(summary.getKey().substring( key.length() ));
			}
		}
		return result;
	}

	
	/* (non-Javadoc)
	 * @see com.AL.service.RepositoryService#putObject(java.lang.String, java.io.InputStream)
	 */
	public void putObject(String name, InputStream is) 
	{
		logger.debug("--------------------- Sending the HTML to S3 -----------------------");
		
		long currentTime = System.currentTimeMillis();
		if(s3.doesBucketExist( bucket ))
		{
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentLength(((ByteArrayInputStream) is).available());
			logger.debug(" bucketName: [" + bucket +"] keyName: ["+ key+"]");
			PutObjectResult result = s3.putObject(new PutObjectRequest(bucket, getS3Key( key ) + name, is, meta));
			//TODO:Need to validate the Etag
			logger.debug(" ETag: " + result.getETag());
		}
		else
		{
			logger.error("-------------- Please create a valid bucket --------------------------");
			//TODO:should ask whether we can create a bucket from code
		}
		
		logger.debug(" Sending the HTML completed Total time: ["+(System.currentTimeMillis() - currentTime) + "ms]");
	}

	/**
	 * Formats the S3 key based on the root value
	 * 
	 * 
	 * @param key
	 * @return String
	 */
	private String getS3Key(String key) 
	{
		if(StringUtils.isNotBlank(key))
		{
			if (key.startsWith(FOLDER_SUFFIX))
			{
				key = key.substring(1);
			}
			else if(key.endsWith( FOLDER_SUFFIX ))
			{
				return key;
			}
			
			return key + FOLDER_SUFFIX;
		}
		
		return "";
	}
}
