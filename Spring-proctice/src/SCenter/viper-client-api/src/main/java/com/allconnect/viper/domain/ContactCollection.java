package com.A.V.domain;
import java.util.ArrayList;
import java.util.List;


public class ContactCollection {
	
	private List<Email> emails = new ArrayList<Email>();
	private List<Phone> phones = new ArrayList<Phone>();
	public List<Email> getEmails() {
		return emails;
	}
	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}
	public List<Phone> getPhones() {
		return phones;
	}
	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}
	
	
	

}
