package com.A.ui.repository;

import com.A.xml.se.v4.ProviderCriteriaEntityType2;
import com.A.xml.se.v4.ProviderCriteriaType2;
import com.A.xml.se.v4.ProviderNameValuePairType2;

public enum ProviderCriteria {

	INSTANCE;

	public ProviderCriteriaType2 createProviderCriteria() {
		ProviderCriteriaType2 criteria = new ProviderCriteriaType2();
		//ProviderCriteriaEntityType2 entity = new ProviderCriteriaEntityType2();
		//entity.setName("ATTSTI");
		//ProviderNameValuePairType2 pair = new ProviderNameValuePairType2();
		//pair.setName("salesCode");
		//pair.setValueAttribute("KRAMERE");
		//entity.getAttributes().add(pair);
		//criteria.getProviders().add(entity);
		return criteria;

	}
}