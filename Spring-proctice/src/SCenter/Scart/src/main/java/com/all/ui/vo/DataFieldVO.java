package com.AL.ui.vo;

import java.io.Serializable;

public class DataFieldVO implements Serializable
{
	
	private String description;
	private String text;
	private String externalId;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	
	

}
