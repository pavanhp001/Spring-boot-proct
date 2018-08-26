package com.A.V.beans.entity;

 

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "OM_RULE_CONTENT")
@SequenceGenerator(name = "ruleContentSequence", sequenceName = "OM_RULE_CONTENT_SEQ")
public class RuleContent implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -2951210700529847390L;

	@Id
	@Column(name = "EXTERNAL_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ruleContentSequence")
	long externalId;
	
	@Column(name = "ID")
	private Long id;
 
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "CONTENT")
	private String content;
	
	@Column(name = "CONTENT_TYPE")
	private String contentType;

	public long getExternalId() {
		return externalId;
	}

	public void setExternalId(long externalId) {
		this.externalId = externalId;
	}

	public String getName() {
		return name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
