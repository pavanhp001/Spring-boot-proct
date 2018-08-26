package com.A.ui.domain;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.ManyToMany;

/**
 * @author ethomas
 * 
 */

@Entity
@Table(name = "OM_RULES")
public class Rules implements CommonBeanInterface {
	private static final long serialVersionUID = 553127845394L;

	@Id
	@GeneratedValue(generator = "rulesSequence")
	@SequenceGenerator(name = "rulesSequence", sequenceName = "OM_RULES_SEQ")
	private long id; 
 
	@Column(name = "RULE_FILE_NAME")
	private String ruleFileName;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "DESCRIPTION")
	private String description;
  
	@Column(name = "IS_ENABLED")
	private Boolean enabled;

	@Column(name = "EFFECTIVE_FROM_ON")
	private Calendar dateEffectiveFrom;
	
	@Column(name = "EFFECTIVE_TO_ON")
	private Calendar dateEffectiveTo;
	 

	@ManyToMany(mappedBy="rules")   
	  private Set<RulesSet> ruleSets;
	
	/**
	 * {@inheritDoc}
	 */
	public final long getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void setId(final long id) {
		this.id = id;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Calendar getDateEffectiveFrom() {
		return dateEffectiveFrom;
	}

	public void setDateEffectiveFrom(Calendar dateEffectiveFrom) {
		this.dateEffectiveFrom = dateEffectiveFrom;
	}

	public Calendar getDateEffectiveTo() {
		return dateEffectiveTo;
	}

	public void setDateEffectiveTo(Calendar dateEffectiveTo) {
		this.dateEffectiveTo = dateEffectiveTo;
	} 
	 

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
 
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRuleFileName() {
		return ruleFileName;
	}

	public void setRuleFileName(String ruleFileName) {
		this.ruleFileName = ruleFileName;
	}
 
	
}
