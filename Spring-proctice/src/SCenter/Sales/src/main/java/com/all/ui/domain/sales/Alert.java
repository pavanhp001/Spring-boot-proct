package com.AL.ui.domain.sales;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
/**
 *
 */
public class Alert {
	
	private String shortDesc;
	private String longDesc;
	private String type;
	private String title;
	private String createdDate;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}
	
	public String getLongDesc() {
		return longDesc;
	}
	
	public void setLongDesc(String longDesc) {
		this.longDesc = longDesc;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	

}
