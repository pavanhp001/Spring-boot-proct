package com.A.ui.builder;

import com.A.xml.pr.v4.CapabilityType;
import com.A.xml.pr.v4.ProviderSourceBaseType;
import com.A.xml.pr.v4.ProviderSourceType;
import com.A.xml.pr.v4.ProviderType;
import com.A.xml.pr.v4.ProductRequestType.ProviderList;
import com.A.xml.pr.v4.ProviderType.CapabilityList;

public enum ProviderBuilder {

	INSTANCE;
	
	
	public ProviderType addRtimProvidersToProvidersList(String providerName,
			ProviderType provider, String name) {
		CapabilityList capabilityList = new CapabilityList();
		CapabilityType capability = new CapabilityType();
		capabilityList.getCapability().add(capability);
		provider.setCapabilityList(capabilityList);
		provider.setName(name);
		provider.setExternalId(providerName);
		ProviderSourceType source = new ProviderSourceType();
		if (name != null && name.equalsIgnoreCase("Verizon")) {
			source.setDatasource("VZ");
		} else if (name.equalsIgnoreCase("ATTSTI")) {
			source.setDatasource("ATTSTI");
		} else if (name.equalsIgnoreCase("G2B-Comcast")) {
			source.setDatasource("G2B");
		} else if (name.equalsIgnoreCase("DISH Network")) {
			source.setDatasource("DISH");
		}
		source.setValue(ProviderSourceBaseType.REALTIME);
		provider.setSource(source);
		ProviderType parent = new ProviderType();
		provider.setParent(parent);
		return provider;
	}

	public ProviderList getProviderList(
			com.A.xml.se.v4.ServiceabilityResponse2.ProviderList proList,
			ProviderList providerList) {
		for (com.A.xml.se.v4.ProviderType pr : proList.getProviders()) {
			ProviderType provider = new ProviderType();
			CapabilityList capabilityList = new CapabilityList();
			CapabilityType capability = new CapabilityType();
			for (com.A.xml.se.v4.CapabilityType cab : pr
					.getCapabilityList().getCapabilities()) {
				capability.setName(cab.getName());
			}
			capabilityList.getCapability().add(capability);
			provider.setCapabilityList(capabilityList);
			provider.setExternalId(pr.getExternalId());
			provider.setName(pr.getName());
			ProviderSourceType source = new ProviderSourceType();
			source.setDatasource(pr.getSource().getDatasource());
			source.setValue(ProviderSourceBaseType.INTERNAL);
			provider.setSource(source);
			ProviderType parent = new ProviderType();
			parent.setName(pr.getParent().getName());
			parent.setExternalId(pr.getParent().getExternalId());
			provider.setParent(parent);
			providerList.getProvider().add(provider);
		}
		return providerList;
	}
}
