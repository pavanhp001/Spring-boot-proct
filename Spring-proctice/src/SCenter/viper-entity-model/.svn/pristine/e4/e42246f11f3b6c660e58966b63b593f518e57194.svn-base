package com.A.V.beans.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.IndexColumn;

import com.A.V.beans.PriceInfo;
import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author ebaugh
 *
 */

@Entity
@Table( name = "OM_SALES_ORDER" )
public class SalesOrder implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4132212407227612809L;


	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "salesOrderBeanSequence" )
    @SequenceGenerator( name = "salesOrderBeanSequence", sequenceName = "OM_SALESORDER_BEAN_SEQ", allocationSize = 1)
    @Index( name = "IDX_SALESORDER_ID", columnNames = { "id" } )
    private long id;


    @Column(name = "EXTERNAL_ID")
    private Long externalId;

    @Column(name = "SOURCE")
    private String source;

    @Column(name = "IS_ASSOCIATED_WITH_MOVE")
    private Boolean associatedWithMove;


    @Column(name = "CAMPAIGN_ID")
    private String campaignId;

    @Column(name="AGENT_ID")
    private String agentId;

    @Transient
    private String agentName;

    @Column(name="CUST_EXT_ID")
    private Long consumerExternalId;

    @Column(name = "ALL_CONNECT_CONFIRM_NUM")
    private String AConfirmNumber;

    @Column(name = "ALL_CONNECT_ACCT_NUM")
    private String AAccountNumber;


    @Column(name = "TOTAL_PRICE")
    private PriceInfo totalPrice;

    @Column(name = "REFERRER_ID")
    private String referrer;

    @Column(name = "MOVE_ON")
    private Calendar moveDate;

    @Column(name = "ORDER_DATE")
    private Calendar orderDate;
    
    @Transient
    private List<AccountHolder> accountHolders;

    @Column(name = "PROGRAM_EXT_ID")
    private String programExternalId;
    
    @Column(name = "ORDERSOURCE_EXT_ID")
    private String ordersourceExternalId;
    
    @Column(name = "INBOUND_VDN")
    private String inboundVdn;

    @OneToOne
    @ForeignKey( name = "FK_SALES_ORDER_CUR_STAT" )
    @JoinColumn(
        name="CURRENT_STATUS_ID", unique=false, nullable=true, updatable=true)
    private StatusRecordBean currentStatus;

    @ElementCollection(fetch=FetchType.LAZY)
    @CollectionTable(name = "OM_SALES_ORDER_OM_STAT_REC", joinColumns = @JoinColumn(name = "OM_SALES_ORDER_ID"))
    @IndexColumn( name = "listOrder", base = 0 )
    private List<StatusRecordBean> historicStatus;

    @Column(name = "WHEN_TO_CALL_BACK_AT")
    private Calendar whenToCallBack;

    @ElementCollection(fetch=FetchType.LAZY)
    @CollectionTable(name = "OM_SALES_ORDER_OM_LINE_ITEM", joinColumns = @JoinColumn(name = "OM_SALES_ORDER_ID"))
    @IndexColumn( name = "listOrder", base = 0 )
    private List<LineItem> lineItems;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="SALES_ORDER_ID", referencedColumnName="ID")
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private Set<SalesOrderContext> salesOrderContexts;

    @Column(name="GUID")
    private String guid;
    
    @Column(name = "IS_ZIP_ONLY_SEARCH")
	private int isZipOnlySearch;
    
    /**
     *
     */
    public SalesOrder()
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final long getId()
    {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setId( final long id )
    {
        this.id = id;
    }

    public String getCampaignId()
    {
        return campaignId;
    }

    public void setCampaignId( final String campaignId )
    {
        this.campaignId = campaignId;
    }


    public Long getExternalId() {
		return externalId;
	}

	public void setExternalId(Long externalId) {
		this.externalId = externalId;
	}

	/**
     * @return the source
     */
    public final String getOrderSource()
    {
        return source;
    }

    /**
     * @param orderSource
     *            the orderSource value to set
     */
    public final void setOrderSource( final String orderSource )
    {
        this.source = orderSource;
    }

    public Boolean getAssociatedWithMove()
    {
        return associatedWithMove;
    }

    public void setAssociatedWithMove( final Boolean associatedWithMove )
    {
        this.associatedWithMove = associatedWithMove;
    }

    public String getAConfirmNumber()
    {
        return AConfirmNumber;
    }

    public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public void setAConfirmNumber( final String AConfirmNumber )
    {
        this.AConfirmNumber = AConfirmNumber;
    }

    public String getAAccountNumber()
    {
        return AAccountNumber;
    }

    public void setAAccountNumber( final String AAccountNumber )
    {
        this.AAccountNumber = AAccountNumber;
    }

    /**
     *
     * @return the calendar with the move date.
     */
    public final Calendar getMoveDate()
    {
        return moveDate;
    }

    /**
     *
     * @param moveDate move date to set.
     */
    public final void setMoveDate( final Calendar moveDate )
    {
        this.moveDate = moveDate;
    }

    public final void setMoveDate( final GregorianCalendar moveDate )
    {
        this.moveDate = moveDate;
    }

    public StatusRecordBean getCurrentStatus()
    {
        return currentStatus;
    }

    public void setCurrentStatus( final StatusRecordBean currentStatus )
    {
        this.currentStatus = currentStatus;
    }

    public List<StatusRecordBean> getHistoricStatus()
    {
        return historicStatus;
    }

    public void setHistoricStatus( final List<StatusRecordBean> historicStatus )
    {
        this.historicStatus = historicStatus;
    }

    public Calendar getWhenToCallBack()
    {
        return whenToCallBack;
    }

    public void setWhenToCallBack( final Calendar whenToCallBack )
    {
        this.whenToCallBack = whenToCallBack;
    }

    /**
     * @return the totalPrice
     */
    public final PriceInfo getTotalPrice()
    {
        return totalPrice;
    }

    /**
     * @param totalPrice
     *            the totalPrice to set
     */
    public final void setTotalPrice( final PriceInfo totalPrice )
    {
        this.totalPrice = totalPrice;
    }


    /**
     * @return the lineItems
     */
    public final List<LineItem> getLineItems()
    {
        return lineItems;
    }

    /**
     * @param lineItems
     *            the lineItems to set
     */
    public final void setLineItems( final List<LineItem> lineItems )
    {
        this.lineItems = lineItems;
    }

	public Long getConsumerExternalId() {
		return consumerExternalId;
	}

	public void setConsumerExternalId(Long consumerExternalId) {
		this.consumerExternalId = consumerExternalId;
	}

	public Calendar getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Calendar orderDate) {
		this.orderDate = orderDate;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	public Set<SalesOrderContext> getSalesOrderContexts()
	{
		return salesOrderContexts;
	}

	public void setSalesOrderContexts( Set<SalesOrderContext> salesOrderContexts )
	{
		this.salesOrderContexts = salesOrderContexts;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getGuid() {
	    return guid;
	}

	public void setGuid(String guid) {
	    this.guid = guid;
	}

	/**
	 * @return the isZipOnlySearch
	 */
	public int getIsZipOnlySearch() {
		return isZipOnlySearch;
	}

	/**
	 * @param isZipOnlySearch the isZipOnlySearch to set
	 */
	public void setIsZipOnlySearch(int isZipOnlySearch) {
		this.isZipOnlySearch = isZipOnlySearch;
	}

	public List<AccountHolder> getAccountHolders() {
		if(accountHolders == null) {
			accountHolders = new ArrayList<AccountHolder>();
		}
		return accountHolders;
	}

	public void setAccountHolders(List<AccountHolder> accountHolders) {
		this.accountHolders = accountHolders;
	}

	public String getProgramExternalId() {
		return programExternalId;
	}

	public void setProgramExternalId(String programExternalId) {
		this.programExternalId = programExternalId;
	}

	public String getOrdersourceExternalId() {
		return ordersourceExternalId;
	}

	public void setOrdersourceExternalId(String ordersourceExternalId) {
		this.ordersourceExternalId = ordersourceExternalId;
	}

	public String getInboundVdn() {
		return inboundVdn;
	}

	public void setInboundVdn(String inboundVdn) {
		this.inboundVdn = inboundVdn;
	}
}
