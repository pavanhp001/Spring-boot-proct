package com.A.V.domain;

import java.util.Calendar;
import java.util.Set;

public interface IProvider {

	 

	long getId();

	void setId(final long id);

	Boolean getEnabled();

	void setEnabled(Boolean enabled);

	Calendar getDateEffectiveFrom();

	void setDateEffectiveFrom(Calendar dateEffectiveFrom);

	Calendar getDateEffectiveTo();

	void setDateEffectiveTo(Calendar dateEffectiveTo);

	String getDescription();

	void setDescription(String description);

	String getName();

	void setName(String name);

	long getParentId();

	void setParentId(long parentId);

	Set<User> getUsers();

	void setUsers(Set<User> users);

	String getAddress();

	void setAddress(String address);

	String getCity();

	void setCity(String city);

	String getState();

	void setState(String state);

	String getZipcode();

	void setZipcode(String zipcode);

	String getPhone();;

	void setPhone(String phone);

	String getEmail();

	void setEmail(String email);

	String getComments();

	void setComments(String comments);

	long getSerialversionuid();

	String getHoldList();

	void setHoldList(String holdList);

	String getRejectList();

	void setRejectList(String rejectList);

	String getImage();

	void setImage(String image);

}
