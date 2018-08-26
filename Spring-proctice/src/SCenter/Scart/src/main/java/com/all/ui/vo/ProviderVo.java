package com.AL.ui.vo;

import java.util.List;

import com.AL.xml.se.v4.CapabilityType;

public class ProviderVo {

	protected List<CapabilityType> capability;
	protected com.AL.xml.se.v4.ProviderType provider;

	public static ProviderVo create(com.AL.xml.se.v4.ProviderType provider,
			List<com.AL.xml.se.v4.CapabilityType> list) {

		return new ProviderVo(provider, list);

	}

	public ProviderVo(com.AL.xml.se.v4.ProviderType provider,
			List<com.AL.xml.se.v4.CapabilityType> capability) {
		this.capability = capability;
		this.provider = provider;
	}

	public List<CapabilityType> getCapability() {
		return capability;
	}

	public void setCapability(List<CapabilityType> capability) {
		this.capability = capability;
	}

	public com.AL.xml.se.v4.ProviderType getProvider() {
		return provider;
	}

	public void setProvider(com.AL.xml.se.v4.ProviderType provider) {
		this.provider = provider;
	}

}
