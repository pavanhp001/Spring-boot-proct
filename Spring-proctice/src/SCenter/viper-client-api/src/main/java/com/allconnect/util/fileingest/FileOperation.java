
package com.A.util.fileingest;

 
public enum FileOperation {
	ENCRYPT("ENCRYPT"), DECRYPT("DECRYPT"), INFLATE("INFLATE"), DEFLATE("DEFLATE"), CREATEHASH("CREATEHASH"), CHECKHASH(
			"CHECKHASH");

	private String name;

	FileOperation(String pName)
	{
		this.name = pName;
	}
}
