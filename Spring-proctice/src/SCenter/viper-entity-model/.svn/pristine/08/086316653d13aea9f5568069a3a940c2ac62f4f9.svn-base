package com.A.V.beans.entity;

 

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.IndexColumn;

import com.A.V.interfaces.CommonBeanInterface;
/**
 * @author ebaugh
 *
 */

@Entity
@Table( name = "CM_PAY_EVENT_STAT" )
public class CustomerPaymentEventStatus implements CommonBeanInterface
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 3966420553119405504L;

	@Id
    @GeneratedValue( strategy=GenerationType.SEQUENCE,generator = "custPayEvtStatusSequence" )
    @SequenceGenerator( name = "custPayEvtStatusSequence", sequenceName = "CM_PAY_EVENT_STAT_SEQ",allocationSize = 1 )
    private long id;
    
    @Column(name = "EXTERNAL_ID")
    private String externalId;
    
    @Column(name = "STATUS")
    private String status;
    
    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name = "CM_PAY_EVENT_STAT_REASONS", joinColumns = @JoinColumn(name = "CM_PAY_EVENT_STAT_ID"))
    @IndexColumn( name = "listOrder", base = 0 )
    private List<String> reasons;
    
    @Column(name = "CREATED_AT")
    private Calendar dateTimeStamp;
    
 
    @Transient
    private User agent;
    
    @Column(name = "AGENT_EXT_ID")
    private String agentExternalId;
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public long getId()
    {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setId( final long id )
    {
        this.id = id;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus( final String status )
    {
        this.status = status;
    }

    public List<String> getReasons()
    {
    	if (reasons == null) {
    		reasons = new ArrayList<String>();
		}
    	
    	
        return reasons;
    }

    public void setReasons( final List<String> reasons )
    {
    	
    	
        this.reasons = reasons;
    }

    public Calendar getDateTimeStamp()
    {
        return dateTimeStamp;
    }

    public void setDateTimeStamp( final Calendar dateTimeStamp )
    {
        this.dateTimeStamp = dateTimeStamp;
    }

    public User getAgent() {
        return agent;
    }

    public void setAgent(User agent) {
    	  
        this.agent = agent;
        
        if (this.agent != null)
        {
        	this.setAgentExternalId(this.agent.getUserLogin());
        }
    }

	public String getAgentExternalId() {
		return agentExternalId;
	}

	public void setAgentExternalId(String agentExternalId) {
		this.agentExternalId = agentExternalId;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

  
    
    
}
