package com.AL.ui.service.CKO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public enum LogicalServers {
	
	INSTANCE, dish_url,att_url,static_url,verizon_url,g2b_url;
	
	private static Map<String, String> urlList = new HashMap<String, String>();
	
	static {
		String urlfile_path = "src\\main\\resources\\CKO_url.properties";
		
		Properties prop = new Properties();
		File propFile = new File(urlfile_path);
		try {
			FileReader inputFile = new FileReader(propFile);
			BufferedReader inputBuffer = new BufferedReader(inputFile);
			prop.load(inputBuffer);
			
			urlList.put(LogicalServers.dish_url.name(), prop.getProperty("dish_url"));
			urlList.put(LogicalServers.att_url.name(), prop.getProperty("att_url"));
			urlList.put(LogicalServers.static_url.name(), prop.getProperty("static_url"));
			urlList.put(LogicalServers.verizon_url.name(), prop.getProperty("verizon_url"));
			urlList.put(LogicalServers.g2b_url.name(), prop.getProperty("g2b_url"));
			
			inputBuffer.close();
			inputFile.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public String getUrl(LogicalServers server) {
		
		return urlList.get(server.name());
		
	}
	
	public Map<String, String> getUrlList() {
		
		if (urlList == null) {
			urlList = new HashMap<String, String>();
		}
		return urlList;
	}

	public void setUrlList(Map<String, String> urlList) {
		this.urlList = urlList;
	}
	
}
