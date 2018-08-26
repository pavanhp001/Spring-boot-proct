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
@Table( name = "OM_ORDER_AUDIT" )
public class OrderAudit implements CommonBeanInterface {


    /**
	 * 
	 */
	private static final long serialVersionUID = -8228249450871638743L;

	@Id
    @GeneratedValue( generator = "orderAuditBeanSequence" )
    @SequenceGenerator( name = "orderAuditBeanSequence", sequenceName = "OM_ORDERAUDIT_BEAN_SEQ" )
    @Index( name = "IDX_ORDER_AUDIT", columnNames = { "id" } )
    private long id;
    
    @NotNull
    @Column(name = "ORDER_ID")
    private String orderId;
    
    @Column(name = "LINE_ITEM_NUMBER")
    private long lineItemNumber;
    
    @NotNull
    @Column(name = "AGENT_ID")
    private String agentId;
    
    @NotNull
    @Column(name = "AUDIT_ON")
    private Calendar auditDate;
    
    @NotNull
    @Column(name = "FROM_STATUS_CODE")
    private String fromStatusCode;
    
    @NotNull
    @Column(name = "FROM_REASON_CODE")
    private String fromReasonCode;
    
    @NotNull
    @Column(name = "TO_STATUS_CODE")
    private String toStatusCode;
    
    @NotNull
    @Column(name = "TO_REASON_CODE")
    private String toReasonCode;
    
    @Column(name = "DESCRIPTION")
    private String description;
    
    @Column(name = "DETAIL")
    @Lob
    private String detail;

    public final long getId()
    {
        return id;
    }

    public final void setId( final long id )
    {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getLineItemNumber() {
        return lineItemNumber;
    }

    public void setLineItemNumber(long lineItemNumber) {
        this.lineItemNumber = lineItemNumber;
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

    public String getFromStatusCode() {
        return fromStatusCode;
    }

    public void setFromStatusCode(String fromStatusCode) {
        this.fromStatusCode = fromStatusCode;
    }

    public String getFromReasonCode() {
        return fromReasonCode;
    }

    public void setFromReasonCode(String fromReasonCode) {
        this.fromReasonCode = fromReasonCode;
    }

    public String getToStatusCode() {
        return toStatusCode;
    }

    public void setToStatusCode(String toStatusCode) {
        this.toStatusCode = toStatusCode;
    }

    public String getToReasonCode() {
        return toReasonCode;
    }

    public void setToReasonCode(String toReasonCode) {
        this.toReasonCode = toReasonCode;
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
    
    
}
