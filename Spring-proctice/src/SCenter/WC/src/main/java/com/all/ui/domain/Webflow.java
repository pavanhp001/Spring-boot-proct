package com.A.ui.domain;

 


import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author hestacio
 * 
 */

@Entity
@Table(name = "DF_WEBFLOW")
public class Webflow implements Serializable {


	@Id
	@GeneratedValue
	@Column(name = "ID")
	private long id;

	@Column(name = "WEBFLOW_ID")
	private Long webflowId;
	
	@Column(name = "WEBFLOW_PATH")
	private String webflowPath;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getWebflowId() {
		return webflowId;
	}

	public void setWebflowId(Long webflowId) {
		this.webflowId = webflowId;
	}

	public String getWebflowPath() {
		return webflowPath;
	}

	public void setWebflowPath(String webflowPath) {
		this.webflowPath = webflowPath;
	}
	
	@Override
	public String toString() {
		return "Concert Webflow [id=" + id + "]";
	}

	 
	 

	 
	
	
	

}
