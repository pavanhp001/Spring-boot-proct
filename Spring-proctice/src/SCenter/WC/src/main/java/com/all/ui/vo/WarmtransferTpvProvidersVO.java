package com.A.ui.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 
 * 
 */

@Entity
@Table(name = "WARMTRANSFER_TPV_PROVIDERS")
public class WarmtransferTpvProvidersVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "PROVIDER_ID")
	private Integer providerId;

	@Column(name = "PROVIDER_NAME")
	private String providerName;

	@Column(name = "PRIORITY")
	private Integer priority;

	@Column(name = "IS_WARMTRANSFER")
	private String isWarmtransfer;

	@Column(name = "IS_TPV")
	private String isTpv;

	@Override
	public String toString() {
		return "WarmtransferTpvProvidersVO [providerId=" + providerId
				+ ", providerName=" + providerName + ", priority=" + priority
				+ ", isWarmtransfer=" + isWarmtransfer + ", isTpv=" + isTpv
				+ "]";
	}

	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getIsWarmtransfer() {
		return isWarmtransfer;
	}

	public void setIsWarmtransfer(String isWarmtransfer) {
		this.isWarmtransfer = isWarmtransfer;
	}

	public String getIsTpv() {
		return isTpv;
	}

	public void setIsTpv(String isTpv) {
		this.isTpv = isTpv;
	}



}
