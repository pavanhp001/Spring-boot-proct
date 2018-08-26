package com.A.V.beans.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

import com.A.V.interfaces.CommonBeanInterface;


/**
 * Reason Code Bean to store instances 
 * of all status codes for all VES Services
 * 
 * 
 * @author klyons
 *
 */

@Entity
@Table( name = "ReasonCode" )
public class ReasonCodeBean implements CommonBeanInterface
{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -7363967462838418844L;

	@Id
    @GeneratedValue( generator = "ReasonCodeBeanSequence" )
    @SequenceGenerator( name = "ReasonCodeBeanSequence", sequenceName = "REASON_CODE_BEAN_SEQ" )
    private long id;
    
    @Column
    private int reasonCode;
    
    @Column
    private String reasonName;
    
    @Column
    private String description;

    
    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
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

	/**
	 * @param reasonCode the reasonCode to set
	 */
	public void setReasonCode(int reasonCode) {
		this.reasonCode = reasonCode;
	}

	/**
	 * @return the reasonCode
	 */
	public int getReasonCode() {
		return reasonCode;
	}

	/**
	 * @param reasonName the reasonName to set
	 */
	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}

	/**
	 * @return the reasonName
	 */
	public String getReasonName() {
		return reasonName;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
    
    
    
    


	
}
