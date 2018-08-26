package com.A.rules.core.V.impl;

import java.util.Calendar;
import java.util.List;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.digester.Rules;

public class RuleDefault implements Rules {

	private long id; 
	 
	private String namespace;
	
	private String context;

	private String provider;

	private String source;
	 
	private String marketItemExternalId;
	
	private Boolean enabled;

	private Calendar dateEffectiveFrom;
	
	private Calendar dateEffectiveTo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getMarketItemExternalId() {
		return marketItemExternalId;
	}

	public void setMarketItemExternalId(String marketItemExternalId) {
		this.marketItemExternalId = marketItemExternalId;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Calendar getDateEffectiveFrom() {
		return dateEffectiveFrom;
	}

	public void setDateEffectiveFrom(Calendar dateEffectiveFrom) {
		this.dateEffectiveFrom = dateEffectiveFrom;
	}

	public Calendar getDateEffectiveTo() {
		return dateEffectiveTo;
	}

	public void setDateEffectiveTo(Calendar dateEffectiveTo) {
		this.dateEffectiveTo = dateEffectiveTo;
	}

	public void add(String arg0, Rule arg1) {
		// TODO Auto-generated method stub
		
	}

	public void clear() {
		// TODO Auto-generated method stub
		
	}

	public Digester getDigester() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNamespaceURI() {
		// TODO Auto-generated method stub
		return null;
	}

	public List match(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public List match(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public List rules() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setDigester(Digester arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setNamespaceURI(String arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
}
