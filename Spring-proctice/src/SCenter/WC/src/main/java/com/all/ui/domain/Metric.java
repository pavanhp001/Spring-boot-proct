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
 * @author ethomas
 * 
 */

@Entity
@Table(name = "WEB_METRIC")
public class Metric implements Serializable {
	private static final long serialVersionUID = 553127845394L;

	@Id
	@GeneratedValue(generator = "metricSequence")
	@SequenceGenerator(name = "metricSequence", sequenceName = "WEB_METRIC_SEQ")
	private long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "AGENT")
	private String agent;

	@Column(name = "PROVIDER")
	private Long provider;

	@Column(name = "PAGE")
	private String page;

	@Column(name = "MEASURED_VALUE")
	private Double mValue;

	@Column(name = "CREATED_ON")
	private Calendar dateEffectiveFrom;
	
	@Column(name = "SALES_SESSION_ID")
	private Long salesSessionId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public Long getProvider() {
		return provider;
	}

	public void setProvider(Long provider) {
		this.provider = provider;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public Double getmValue() {
		return mValue;
	}

	public void setmValue(Double mValue) {
		this.mValue = mValue;
	}

	public Calendar getDateEffectiveFrom() {
		return dateEffectiveFrom;
	}

	public void setDateEffectiveFrom(Calendar dateEffectiveFrom) {
		this.dateEffectiveFrom = dateEffectiveFrom;
	}
	
	public Long getSalesSessionId() {
		return salesSessionId;
	}

	public void setSalesSessionId(Long salesSessionId) {
		this.salesSessionId = salesSessionId;
	}

	@Override
	public String toString() {
		return "Metric [agent=" + agent + ", dateEffectiveFrom="
				+ dateEffectiveFrom + ", id=" + id + ", mValue=" + mValue
				+ ", name=" + name + ", page=" + page + ", provider="
				+ provider + ", salesSessionId=" + salesSessionId + "]";
	}

	 
	
	
	

}
