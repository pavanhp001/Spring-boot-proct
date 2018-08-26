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
@Table(name = "WEB_PROVIDER")
public class Provider implements Serializable {
	private static final long serialVersionUID = 553127845394L;

	@Id
	@GeneratedValue(generator = "providerSequence")
	@SequenceGenerator(name = "providerSequence", sequenceName = "WEB_PROVIDER_SEQ")
	private long id;

 

	@Column(name = "PROVIDER")
	private Long providerId;
	
	@Column(name = "CERTIFICATE")
	private String certificate;


	@Column(name = "URL")
	private String url;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	@Override
	public String toString() {
		return "Provider [id=" + id + ", providerId=" + providerId
				+ ", certificate=" + certificate + ", url=" + url + "]";
	}

	 
	 

	 
	
	
	

}
