package com.A.V.beans.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.A.V.interfaces.CommonBeanInterface;
/**
 *
 * @author ebaugh
 *
 */
@Entity
@Table( name = "CM_CONSUMER_INTERACTION" )
public class ConsumerInteraction implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5942957046212762666L;

	@Id
    @GeneratedValue( generator = "consumerInteractionBeanSequence",strategy=GenerationType.SEQUENCE)
    @SequenceGenerator( name = "consumerInteractionBeanSequence", sequenceName = "CONSUMER_INTER_BEAN_SEQ" ,allocationSize = 1)
    private long id;
    
    @Column(name = "CONSUMER_EXTERNAL_ID")
    private Long consumerExternalId;
    
    @Column(name = "NOTES")
    private String notes;

    @Column(name = "INTERACTION_ON")
    private Calendar dateOfInteraction;
    
    @Column(name = "AGENT_EXT_ID")
    private String agentExternalId;
    
    //Referes to Business Party
    @Column(name = "SOURCE")
    private String source;
    
    @Column(name = "ORDER_EXT_ID")
    private Long orderExternalId;
    
    @Transient
    private String agentName;
    
    @Transient
    private String customerName;
    /*@ManyToOne
    @ForeignKey( name = "CONSUMER_INTER_CONSUMER_FK01" )
    private Consumer consumer;
    
    @ManyToOne
    @ForeignKey( name = "CONSUMER_INTER_AGENT_FK02" )
    private AgentBean agentBean;
        
    @ManyToOne
    @ForeignKey( name = "CONSUMER_INTER_BUS_PARTY_FK03" )
    private BusinessParty source;
    
    @OneToOne
    @ForeignKey( name = "CONSUMER_INTER_ORDER_FK04" )
    private SalesOrder order;*/

    /**
     *
     */
    public ConsumerInteraction()
    {
    }

    /**
     * {@inheritDoc}
     */
    public final long getId()
    {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    public final void setId( final long id )
    {
        this.id = id;
    }

    /**
     * @return the dateOfInteraction
     */
    public final Calendar getDateOfInteraction()
    {
        return dateOfInteraction;
    }

        /**
     * @param dateOfInteraction
     *            the dateOfInteraction to set
     */
    public final void setDateOfInteraction( final Calendar dateOfInteraction )
    {
        this.dateOfInteraction = dateOfInteraction;
    }

	public Long getConsumerExternalId() {
		return consumerExternalId;
	}

	public void setConsumerExternalId(Long consumerExternalId) {
		this.consumerExternalId = consumerExternalId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getAgentExternalId() {
		return agentExternalId;
	}

	public void setAgentExternalId(String agentExternalId) {
		this.agentExternalId = agentExternalId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Long getOrderExternalId() {
		return orderExternalId;
	}

	public void setOrderExternalId(Long orderExternalId) {
		this.orderExternalId = orderExternalId;
	}

	public String getAgentName()
	{
		return agentName;
	}

	public void setAgentName( String agentName )
	{
		this.agentName = agentName;
	}

	public String getCustomerName()
	{
		return customerName;
	}

	public void setCustomerName( String customerName )
	{
		this.customerName = customerName;
	}

	

	
}
