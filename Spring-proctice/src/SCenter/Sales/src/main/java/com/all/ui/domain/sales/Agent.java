package com.AL.ui.domain.sales;

import javax.persistence.ManyToOne;

public class Agent {
private String externalId;
private String score;
    private String firstName;

    private String middleName;

    private String lastName;

    @ManyToOne
    private Address address;

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String arg) {
		externalId = arg;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

   public String toString() {
      return firstName + " " + lastName;
   }

    public String getFirstName() {
        return this.firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getMiddleName() {
        return this.middleName;
    }
    
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    
    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public Address getAddress() {
        return this.address;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }   
}
