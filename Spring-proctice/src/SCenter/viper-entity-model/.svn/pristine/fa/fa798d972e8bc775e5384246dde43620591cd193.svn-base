package com.A.V.beans.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

import com.A.V.interfaces.CommonBeanInterface;


/**
 * EmailBounce entity to store instances 
 * of all email bounces from CCP.
 * 
 * 
 * @author klyons
 *
 */

@Entity
@Table( name = "EmailBounce" )
public class EmailBounce implements CommonBeanInterface
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 5933819168464775450L;

	@Id
    @GeneratedValue( generator = "EmailBounceSequence" )
    @SequenceGenerator( name = "EmailBounceSequence", sequenceName = "EMAIL_BOUNCE_SEQ" )
    private long id;
    
    @Column( nullable = false )
    private long emailStatusID;
    
    @Column( nullable = false )
    private Calendar bounceDate;
    
    @Column( nullable = false, length=255)
    private String bounceType;
    
    
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
     * @param bounceDate the bounceDate to set
     */
    public void setBounceDate( Calendar bounceDate )
    {
        this.bounceDate = bounceDate;
    }

    /**
     * @return the bounceDate
     */
    public Calendar getBounceDate()
    {
        return bounceDate;
    }

    /**
     * @param bounceType the bounceType to set
     */
    public void setBounceType( String bounceType )
    {
        this.bounceType = bounceType;
    }

    /**
     * @return the bounceType
     */
    public String getBounceType()
    {
        return bounceType;
    }

}
