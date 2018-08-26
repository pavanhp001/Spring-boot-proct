package com.A.V.beans.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.NotNull;

/**
 *  
 * @author Gayatri Desai
 */

@Entity
@Table( name = "rtim_adapter_config" )
public class RTIMAdapterConfigBean implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 3905925470440591893L;

	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "rtimAdapterConfigBeanSequence" )
    @SequenceGenerator( name = "rtimAdapterConfigBeanSequence", sequenceName = "RTIM_ADAPTER_CONFIG_BEAN_SEQ", allocationSize = 1)
    private long id;

    private String key;
    
    private String value; 
    
    @ManyToOne
    @NotNull
    @ForeignKey( name = "RT_ADAP_CONF_FK01" )
    @JoinColumn ( name="ADAPTERID" )
    private RTIMAdapterBean adapter;
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public RTIMAdapterBean getAdapter() {
		return adapter;
	}

	public void setAdapter(RTIMAdapterBean adapter) {
		this.adapter = adapter;
	}
	
	
	
}
