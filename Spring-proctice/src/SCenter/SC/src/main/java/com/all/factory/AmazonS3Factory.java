package com.AL.factory;

import org.apache.log4j.Logger;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;

/**
 * Factory Class for connecting to Amazon SDK depending on the 
 * credentials.source of score.properties
 * 
 * 
 * @author pmyneni
 *
 */
public class AmazonS3Factory 
{
	private static final Logger logger = Logger.getLogger(AmazonS3Factory.class);
	private static String accessKey;
	private static String secretKey;
	private  static String credentialsSource;

	/**
	 * @param accessKey
	 * @param secretKey
	 * @param credentialsSource
	 */
	public AmazonS3Factory(String accessKey, String secretKey,
			String credentialsSource) 
	{
		AmazonS3Factory.accessKey = accessKey;
		AmazonS3Factory.secretKey = secretKey;
		AmazonS3Factory.credentialsSource = credentialsSource;
	}

	/**
	 * @return AmazonS3Client
	 */
	public static AmazonS3Client getAmazonS3Client()
	{
		AmazonS3Client s3 = null;

//		if( credentialsSource.equalsIgnoreCase("FILE") )
//		{
//			logger.info("-------------- Connecting Amazon SDK using FILE -----------------");
//			s3 = new AmazonS3Client(new BasicAWSCredentials(accessKey, secretKey));
//		}
//		else if( credentialsSource.equalsIgnoreCase("IAM") )
//		{
			logger.info("-------------- Connecting Amazon SDK using IAM -----------------");
			//TODO:Check whether the IAM requires additional configuration
			s3 = new AmazonS3Client();
//		}
		logger.info("-------------- Connection Established Successfully !!! -----------------");
		return s3;
	}


}
