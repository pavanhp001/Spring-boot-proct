package com.A.V.beans.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * EmailStatus entity to store instances 
 * of all email statuses from CCP.
 * 
 * 
 * @author klyons
 *
 */

@Entity
@Table( name = "EmailStatus" )
public class EmailStatus implements Serializable
{
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -1294374195081046768L;

	@Id
    @GeneratedValue( generator = "EmailStatusSequence" )
    @SequenceGenerator( name = "EmailStatusSequence", sequenceName = "EMAIL_STATUS_SEQ" )
    private long emailStatusID;
        
    @Column
    private int srvcSltcnID;
    
    @Column
    private Calendar startDate;
    
    @Column
    private Calendar sendDate;
    
    @Column
    private Calendar respondDate;
    
    @Column
    private Calendar openDate;
    
    @Column
    private Calendar pushDate;
    
    @Column
    private int statusCode;
    
    @Column(length=8)
    private String emailTypeOld;
    
    @Column( nullable = false )
    private int entityID;
    
    @Column( nullable = false, precision=2, scale=0 )
    private int emailType;
    
    @Column(length=30)
    private String emailStatusIDEncrypted;    
    

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
     * @param srvcSltcnID the srvcSltcnID to set
     */
    public void setSrvcSltcnID( int srvcSltcnID )
    {
        this.srvcSltcnID = srvcSltcnID;
    }

    /**
     * @return the srvcSltcnID
     */
    public long getSrvcSltcnID()
    {
        return srvcSltcnID;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate( Calendar startDate )
    {
        this.startDate = startDate;
    }

    /**
     * @return the startDate
     */
    public Calendar getStartDate()
    {
        return startDate;
    }

    /**
     * @param sendDate the sendDate to set
     */
    public void setSendDate( Calendar sendDate )
    {
        this.sendDate = sendDate;
    }

    /**
     * @return the sendDate
     */
    public Calendar getSendDate()
    {
        return sendDate;
    }

    /**
     * @param respondDate the respondDate to set
     */
    public void setRespondDate( Calendar respondDate )
    {
        this.respondDate = respondDate;
    }

    /**
     * @return the respondDate
     */
    public Calendar getRespondDate()
    {
        return respondDate;
    }

    /**
     * @param pushDate the pushDate to set
     */
    public void setPushDate( Calendar pushDate )
    {
        this.pushDate = pushDate;
    }

    /**
     * @return the pushDate
     */
    public Calendar getPushDate()
    {
        return pushDate;
    }

    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode( int statusCode )
    {
        this.statusCode = statusCode;
    }

    /**
     * @return the statusCode
     */
    public int getStatusCode()
    {
        return statusCode;
    }

    /**
     * @param emailTypeOld the emailTypeOld to set
     */
    public void setEmailTypeOld( String emailTypeOld )
    {
        this.emailTypeOld = emailTypeOld;
    }

    /**
     * @return the emailTypeOld
     */
    public String getEmailTypeOld()
    {
        return emailTypeOld;
    }

    /**
     * @param entityID the entityID to set
     */
    public void setEntityID( int entityID )
    {
        this.entityID = entityID;
    }

    /**
     * @return the entityID
     */
    public int getEntityID()
    {
        return entityID;
    }

    /**
     * @param emailType the emailType to set
     */
    public void setEmailType( int emailType )
    {
        this.emailType = emailType;
    }

    /**
     * @return the emailType
     */
    public int getEmailType()
    {
        return emailType;
    }

    /**
     * @param emailStatusIDEncrypted the emailStatusIDEncrypted to set
     */
    public void setEmailStatusIDEncrypted( String emailStatusIDEncrypted )
    {
        this.emailStatusIDEncrypted = emailStatusIDEncrypted;
    }

    /**
     * @return the emailStatusIDEncrypted
     */
    public String getEmailStatusIDEncrypted()
    {
        return emailStatusIDEncrypted;
    }

    /**
     * @param openDate the openDate to set
     */
    public void setOpenDate( Calendar openDate )
    {
        this.openDate = openDate;
    }

    /**
     * @return the openDate
     */
    public Calendar getOpenDate()
    {
        return openDate;
    }



    
}
