
package com.A.util.fileingest;

 
public enum MessageDigestAlgorithm {

	SHA("SHA"), SHA1("SHA-1"), MD5("MD5");
	private String name;

	MessageDigestAlgorithm(String pName)
	{
		this.name = pName;
	}
}
