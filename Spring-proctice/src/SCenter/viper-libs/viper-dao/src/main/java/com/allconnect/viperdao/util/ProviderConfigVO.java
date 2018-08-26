package com.A.Vdao.util;

public class ProviderConfigVO {
	
	private String providerName;
	private String queueName;
	
	public ProviderConfigVO(String providerName, String queueName) {
		this.providerName = providerName;
		this.queueName = queueName;
	}
	
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

}
