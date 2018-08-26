package com.A.V.beans.entity;

import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.NotNull;

import com.A.V.interfaces.CommonBeanInterface;

@Entity
@Table(name = "OM_RULE_SETS")
public class RulesSet implements CommonBeanInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9195597121108050098L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "rulesSetSequence")
	@SequenceGenerator(name = "rulesSetSequence", sequenceName = "OM_RULES_SETS_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "CONTEXT")
	private String context;

	@Column(name = "PROVIDER")
	private String provider;

	@Column(name = "SOURCE")
	private String source;

	@Column(name = "SOURCE_CONTEXT")
	private String sourceContext;

	@Column(name = "LINE_ITEM_DETAIL_EX_ID")
	private String lineItemDetailExternalId;

	@Column(name = "IS_ENABLED")
	private Boolean enabled;

	@Column(name = "EFFECTIVE_FROM_ON")
	private Calendar dateEffectiveFrom;

	@Column(name = "EFFECTIVE_TO_ON")
	private Calendar dateEffectiveTo;

 
	@ManyToOne(fetch = FetchType.EAGER ) 
	@ForeignKey(name = "FK_RULE_SET_RULE_STORAGE") 
	private RuleStorage ruleStorage;
	
	@ManyToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL ) 
	@JoinTable(name = "OM_RULE_SETS_RULES", 
			joinColumns = @JoinColumn(name = "RULE_SET_ID"), 
			inverseJoinColumns = @JoinColumn(name = "RULE_ID"))
	private List<Rules> rules;

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

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getContext() {
		return context;
	}

	public String getLineItemDetailExternalId() {
		return lineItemDetailExternalId;
	}

	public void setLineItemDetailExternalId(String lineItemDetailExternalId) {
		this.lineItemDetailExternalId = lineItemDetailExternalId;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSourceContext() {
		return sourceContext;
	}

	public void setSourceContext(String sourceContext) {
		this.sourceContext = sourceContext;
	}

	public List<Rules> getRules() {
		return rules;
	}

	public void setRules(List<Rules> rules) {
		this.rules = rules;
	}

	public RuleStorage getRuleStorage() {
		return ruleStorage;
	}

	public void setRuleStorage(RuleStorage ruleStorage) {
		this.ruleStorage = ruleStorage;
	}
	
	

}
