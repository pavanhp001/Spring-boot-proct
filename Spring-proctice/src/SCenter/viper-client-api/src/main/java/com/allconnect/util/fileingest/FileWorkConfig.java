package com.A.util.fileingest;

public class FileWorkConfig {

	private String workDirectoryPath = "./tmp";
	private String gzipFileSuffix = ".gz";
	public String getWorkDirectoryPath() {
		return workDirectoryPath;
	}
	public void setWorkDirectoryPath(String workDirectoryPath) {
		this.workDirectoryPath = workDirectoryPath;
	}
	public String getGzipFileSuffix() {
		return gzipFileSuffix;
	}
	public void setGzipFileSuffix(String gzipFileSuffix) {
		this.gzipFileSuffix = gzipFileSuffix;
	}
	@Override
	public String toString() {
		return "FileWorkConfig [workDirectoryPath=" + workDirectoryPath
				+ ", gzipFileSuffix=" + gzipFileSuffix + "]";
	}
	
	
}
