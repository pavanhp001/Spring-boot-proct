package com.A.V.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
 
 

 
public class User {
	private static final long serialVersionUID = 553127845394L;

	 
	private long id;

	
	 
	private String loginId;
	
	private String description;

	private String context;

	private Boolean enabled;

	private Calendar lastLoginAt;
	
	private Integer loginAttempt;
	
	private Calendar dateEffectiveFrom;

	private Calendar dateEffectiveTo;

	private List<IProvider> providers;

	public static User create(final String name) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 4);

		User user = new User();
		user.setName(name);
		user.setEnabled(Boolean.TRUE);
		user.setDateEffectiveFrom(Calendar.getInstance());
		user.setDateEffectiveTo(cal);
		user.setContext(UUID.randomUUID().toString());

		return user;
	}

	public final long getId() {
		return id;
	}

	public final void setId(final long id) {
		this.id = id;
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

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getName() {
		return loginId;
	}

	public void setName(String name) {
		this.loginId = name;
	}

	public List<IProvider> getProviders() {
		
		if (providers == null) {
			providers = new ArrayList<IProvider>();
		}
		return providers;
	}

	public void setProviders(List<IProvider> providers) {
		this.providers = providers;
	}

	public Calendar getLastLoginAt() {
		return lastLoginAt;
	}

	public void setLastLoginAt(Calendar lastLoginAt) {
		this.lastLoginAt = lastLoginAt;
	}

	public int getLoginAttempt() {
		return loginAttempt;
	}

	public void setLoginAttempt(int loginAttempt) {
		this.loginAttempt = loginAttempt;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLoginAttempt(Integer loginAttempt) {
		this.loginAttempt = loginAttempt;
	}
	
	

}
