package com.A.V.beans.entity;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "CM_CONSUMER_ADDRESS")
@AssociationOverrides({
		@AssociationOverride(name = "pk.consumer", 
			joinColumns = @JoinColumn(name = "CONSUMER_ID")),
		@AssociationOverride(name = "pk.address", 
			joinColumns = @JoinColumn(name = "ADDRESS_ID")) })
public class CustomerAddressAssociation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3619587310695326214L;

	@Transient
	private boolean isDeleted = Boolean.FALSE;
	
	@Transient
	private boolean isNewAssn = Boolean.FALSE;
    
    private CustomerAddressAssociationPK pk = new CustomerAddressAssociationPK();
    
    

    public CustomerAddressAssociation(){
	
    }

    @EmbeddedId
    public CustomerAddressAssociationPK getPk() {
    	
        return pk;
    }

    public void setPk(CustomerAddressAssociationPK pk) {
        this.pk = pk;
    }

    @Transient
    public Consumer getConsumer() {
        return getPk().getConsumer();
    }

    public void setConsumer(Consumer consumer) {
       getPk().setConsumer(consumer);
    }

    @Transient
    public AddressBean getAddress() {
        return getPk().getAddress();
    }

    public void setAddress(AddressBean address) {
        getPk().setAddress(address);
    }

    @Transient
    public String getAddressRole() {
        return getPk().getAddressRole();
    }

    public void setAddressRole(String addressRole) {
        getPk().setAddressRole(addressRole);
    }

    @Transient
    public String getUniqueId(){
    	return getPk().getUniqueId();
    }
    public void setUniqueId(String uniqueId){
    	getPk().setUniqueId(uniqueId);
    }
    
    
    
   

	@Transient
    public boolean isDeleted() {
		return isDeleted;
	}

    @Transient
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
   
    @Transient
	public boolean isNewAssn() {
		return isNewAssn;
	}
    
    @Transient
	public void setNewAssn(boolean isNewAssn) {
		this.isNewAssn = isNewAssn;
	}

	@Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((pk == null) ? 0 : pk.hashCode());
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
	CustomerAddressAssociation other = (CustomerAddressAssociation) obj;
	if (pk == null) {
	    if (other.pk != null)
		return false;
	} else if (!pk.equals(other.pk))
	    return false;
	return true;
    }
    
    
    
}
