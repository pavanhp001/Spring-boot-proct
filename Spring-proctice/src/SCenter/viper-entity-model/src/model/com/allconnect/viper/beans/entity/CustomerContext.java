package com.A.V.beans.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author PPatel
 *
 */
@Entity
@Table( name = "CM_CUSTOMER_CONTEXT" )
public class CustomerContext implements CommonBeanInterface {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 8889652925178850774L;

	@Id
    @GeneratedValue( strategy=GenerationType.SEQUENCE,generator = "customerContextSequence" )
    @SequenceGenerator( name = "customerContextSequence", sequenceName = "CM_CUSTOMER_CONTEXT_SEQ" ,allocationSize = 1)
    private long id;
	
	@Column(name = "ENTITY_NAME")
	private String entityName;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "VALUE")
	private String value;
	
	
	@Column(name = "CONSUMER_ID")
	private Long consumerId;
	
	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public void setId(long id) {
		this.id=id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(Long consumerId) {
		this.consumerId = consumerId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	@Override
	public String toString() {
		return "CustomerContext [id=" + id + ", entityName=" + entityName
				+ ", name=" + name + ", value=" + value + ", consumerId="
				+ consumerId + "]";
	}

	
}
