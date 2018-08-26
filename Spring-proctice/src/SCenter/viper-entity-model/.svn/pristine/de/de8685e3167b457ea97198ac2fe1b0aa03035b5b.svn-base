package com.A.V.beans.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *  
 * @author Gayatri Desai
 */

@Entity
@Table( name = "rtim_adapter" )
public class RTIMAdapterBean implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 7890343124220874682L;

	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "rtimAdapterBean" )
    @SequenceGenerator( name = "rtimAdapterBean", sequenceName = "RTIM_ADAPTER_BEAN_SEQ", allocationSize = 1)
    private long id;

    private String providerExternalId;
    
    private String datasource;
    
    private String name;
    
    private Boolean active;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProviderExternalId() {
		return providerExternalId;
	}

	public void setProviderExternalId(String providerExternalId) {
		this.providerExternalId = providerExternalId;
	}

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
    
	
}
