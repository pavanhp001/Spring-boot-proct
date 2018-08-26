package com.A.ui.domain;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity
@Table(name = "WEB_LOOKUP")
@XStreamAlias("lookup")
public class WebLookup {
	private static final long serialVersionUID = 553127845394L;

	@Id
	@GeneratedValue(generator = "webLookupSeq")
	@SequenceGenerator(name = "webLookupSeq", sequenceName = "web_lookup_seq")
	@XStreamAlias("id")
	private long id;
  
	@XStreamAlias("context")
	@Column(name = "CONTEXT")
	private long context;
	
	@XStreamAlias("description")
	@Column(name = "DESCRIPTION")
	private String description;

	@XStreamAlias("lookupKey")
	@Column(name = "LOOKUP_KEY")
	private String lookupKey;

	@XStreamAlias("enabled")
	@Column(name = "IS_ENABLED")
	private Boolean enabled;
 
	@XStreamAlias("type")
	@Temporal(TemporalType.DATE)
	@Column(name = "EFFECTIVE_FROM_ON")
	private Date dateEffectiveFrom;

	@XStreamAlias("dateEffectiveTo")
	@Temporal(TemporalType.DATE)
	@Column(name = "EFFECTIVE_TO_ON")
	private Date dateEffectiveTo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getContext() {
		return context;
	}

	public void setContext(long context) {
		this.context = context;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLookupKey() {
		return lookupKey;
	}

	public void setLookupKey(String lookupKey) {
		this.lookupKey = lookupKey;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Date getDateEffectiveFrom() {
		return dateEffectiveFrom;
	}

	public void setDateEffectiveFrom(Date dateEffectiveFrom) {
		this.dateEffectiveFrom = dateEffectiveFrom;
	}

	public Date getDateEffectiveTo() {
		return dateEffectiveTo;
	}

	public void setDateEffectiveTo(Date dateEffectiveTo) {
		this.dateEffectiveTo = dateEffectiveTo;
	}

	 
	
	

}
