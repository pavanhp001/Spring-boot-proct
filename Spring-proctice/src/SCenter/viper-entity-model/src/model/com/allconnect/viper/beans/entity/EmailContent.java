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
 * EmailContent entity to store instances 
 * of all email content from CCP.
 * 
 * 
 * @author klyons
 *
 */

@Entity
@Table( name = "EmailContent" )
public class EmailContent implements CommonBeanInterface
{
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -7825839396420409830L;

	@Id
    @GeneratedValue( generator = "EmailContentSequence" )
    @SequenceGenerator( name = "EmailContentSequence", sequenceName = "EMAIL_CONTENT_SEQ" )
    private long id;
        
    @Column( nullable = false )
    private long emailStatusID;
    
    @Column( nullable = false, length=65)
    private String emailContentName;
    
    @Column( nullable = false, length=255)
    private String emailContentValue;
    
    
    public static enum Name
    {
        EMAILADDRESS("EmailAddress"),
        CAMPAIGN_ID("CampaignID"),
        FEEDBACKURL("FeedbackURL"),
        REFERRER_ID("ReferrerID"),
        ORDER_DATE("OrderDate");
        
        private String value;
        
        public String getValue() 
        {
            return value;
        }
        
        private Name( final String val ) 
        {
            value = val;
        }
    }    
    
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
     * @param emailStatusID the emailStatusID to set
     */
    public void setEmailStatusID( long emailStatusID )
    {
        this.emailStatusID = emailStatusID;
    }

    /**
     * @return the emailStatusID
     */
    public long getEmailStatusID()
    {
        return emailStatusID;
    }

    /**
     * @param emailContentName the emailContentName to set
     */
    public void setEmailContentName( String emailContentName )
    {
        this.emailContentName = emailContentName;
    }

    /**
     * @return the emailContentName
     */
    public String getEmailContentName()
    {
        return emailContentName;
    }

    /**
     * @param emailContentValue the emailContentValue to set
     */
    public void setEmailContentValue( String emailContentValue )
    {
        this.emailContentValue = emailContentValue;
    }

    /**
     * @return the emailContentValue
     */
    public String getEmailContentValue()
    {
        return emailContentValue;
    }

    
}
