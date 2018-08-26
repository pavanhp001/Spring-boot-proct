package com.A.V.beans.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;
import org.hibernate.validator.NotNull;

import com.A.V.interfaces.CommonBeanInterface;

@Entity
@Table( name = "CM_CUSTOMER_AUDIT" )
public class CustomerAudit implements CommonBeanInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2039688295294979345L;

	@Id
    @GeneratedValue( generator = "customerAuditBeanSequence" )
    @SequenceGenerator( name = "customerAuditBeanSequence", sequenceName = "CM_CUSTOMERAUDIT_BEAN_SEQ" )
    @Index( name = "IDX_CUSTOMER_AUDIT", columnNames = { "id" } )
	private long id;
	
	@NotNull
    @Column(name = "CUSTOMER_ID")
    private long customerId;
    
    @NotNull
    @Column(name = "AGENT_ID")
    private String agentId;
    
    @NotNull
    @Column(name = "AUDIT_ON")
    private Calendar auditDate;
    
    @Column(name = "DESCRIPTION")
    private String description;
    
    @Column(name = "DETAIL")
    @Lob
    private String detail;
	
	
	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getAgentId()
	{
		return agentId;
	}

	public void setAgentId( String agentId )
	{
		this.agentId = agentId;
	}

	public Calendar getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Calendar auditDate) {
		this.auditDate = auditDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	

}
