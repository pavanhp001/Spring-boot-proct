package com.A.V.beans.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

import com.A.V.interfaces.CommonBeanInterface;

@Entity
@Table(name = "OM_RULE_STORAGE")
public class RuleStorage implements CommonBeanInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5200345003872816034L;

	private static final int MAX_STORAGE_SIZE = 100000;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "rulesStorageSequence")
	@SequenceGenerator(name = "rulesStorageSequence", sequenceName = "OM_RULES_STORAGE_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "Description")
	private String description;

	@Column(name = "IS_ENABLED")
	private Boolean enabled;

	@Column(name = "CREATED_ON")
	private Calendar createdOn;

	@Column(name = "UPDATED_ON")
	private Calendar updatedOn;

	@Lob
	@Column(name = "RULE_BASE", length = MAX_STORAGE_SIZE)
	private byte[] ruleBase;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@NotNull
	public final long getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setId(final long id) {
		this.id = id;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Calendar getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Calendar createdOn) {
		this.createdOn = createdOn;
	}

	public Calendar getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Calendar updatedOn) {
		this.updatedOn = updatedOn;
	}

	public byte[] getRuleBase() {
		return ruleBase;
	}

	public void setRuleBase(byte[] ruleBase) {
		this.ruleBase = ruleBase;
	}

	 
	
	

}
