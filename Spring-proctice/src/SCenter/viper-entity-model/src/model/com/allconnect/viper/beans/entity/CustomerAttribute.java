package com.A.V.beans.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.A.V.interfaces.CommonBeanInterface;
@Entity
@Table( name = "CM_CUST_ATTRIBUTE" )
public class CustomerAttribute implements CommonBeanInterface {


	/**
	 * 
	 */
	private static final long serialVersionUID = 3066604745957496667L;

	@Id
    @GeneratedValue( strategy=GenerationType.SEQUENCE,generator = "customerAttributeSequence" )
    @SequenceGenerator( name = "customerAttributeSequence", sequenceName = "CM_CUST_ATTRIBUTE_SEQ" ,allocationSize = 1)
	private long id;

	@Column(name = "SOURCE")
	private String source;

	@Column(name = "NAME")
	private String name;

	@Column(name = "VALUE")
	private String value;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CONSUMER_ID")
	private Long consumerId;

	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Long getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(Long consumerId) {
		this.consumerId = consumerId;
	}

	@Override
	public String toString() {
		return "CustomerAttribute [id=" + id + ", source=" + source + ", name="
				+ name + ", value=" + value + ", description=" + description
				+ ", consumerId=" + consumerId + "]";
	}




}
