package com.A.V.beans.entity;

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
@Table( name = "OM_STAT_REC" )
public class StatusRecordBean implements CommonBeanInterface
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -2311834808941595375L;

	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "statusRecordBeanSequence" )
    @SequenceGenerator( name = "statusRecordBeanSequence", sequenceName = "OM_STATUS_RECORD_BEAN_SEQ", allocationSize = 1)
    private long id;
    
    @Column(name = "STATUS")
    private String status;
    
    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name = "OM_STAT_REC_REASONS", joinColumns = @JoinColumn(name = "OM_STAT_REC_ID"))
    @IndexColumn( name = "listOrder", base = 0 )
    @Column(name="ELEMENT")
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

  
    
    
}
