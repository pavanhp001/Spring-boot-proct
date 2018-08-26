package com.A.V.beans.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class CustomerAddressAssociationPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4683238377324628294L;
	
	private Consumer consumer;
	private AddressBean address;
	private String addressRole;
	private String uniqueId;
	 

	@ManyToOne
	public Consumer getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	@ManyToOne
	public AddressBean getAddress() {
		return address;
	}

	public void setAddress(AddressBean address) {
		this.address = address;
	}

	@Column(name = "ADDRESS_ROLE")
	public String getAddressRole() {
		return addressRole;
	}

	public void setAddressRole(String addressRole) {
		this.addressRole = addressRole;
	}

	 

	 

	@Column(name = "UNIQUE_ID")
	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	
	
 

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result
				+ ((addressRole == null) ? 0 : addressRole.hashCode());
		result = prime * result
				+ ((consumer == null) ? 0 : consumer.hashCode());
		result = prime * result
				+ ((uniqueId == null) ? 0 : uniqueId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerAddressAssociationPK other = (CustomerAddressAssociationPK) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (addressRole == null) {
			if (other.addressRole != null)
				return false;
		} else if (!addressRole.equals(other.addressRole))
			return false;
		if (consumer == null) {
			if (other.consumer != null)
				return false;
		} else if (!consumer.equals(other.consumer))
			return false;
		if (uniqueId == null) {
			if (other.uniqueId != null)
				return false;
		} else if (!uniqueId.equals(other.uniqueId))
			return false;
		return true;
	}
	
	

	

	@Override
	public String toString() {
		return "CustomerAddressAssociationPK [consumer=" + consumer
				+ ", address=" + address + ", addressRole=" + addressRole
				+ ", uniqueId=" + uniqueId + "]";
	}

	
}
