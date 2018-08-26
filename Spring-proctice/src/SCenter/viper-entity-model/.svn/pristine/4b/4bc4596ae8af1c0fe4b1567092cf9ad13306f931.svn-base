package com.A.V.beans.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.A.V.interfaces.CommonBeanInterface;

/**
 *
 * @author ebaugh
 *
 */
@Entity
@Table( name = "incomingCall" )
public class IncomingCallBean implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3865517534952935390L;

	@Id
    @GeneratedValue( generator = "incomingCallBeanSequence" )
    @SequenceGenerator( name = "incomingCallBeanSequence", sequenceName = "INCOMING_CALL_BEAN_SEQ" )
    private long id;

    private String digits;
    private String collectVDN;
    private String callAddress;
    private String extensionCalled;
    private Date dateReceived;

    /**
     *
     * @param digits the digits of the incoming call
     * @param collectVDN the VDN for the call
     * @param callAddress the address of the call
     * @param extensionCalled the destination extension
     * @param dateReceived the date the call was received
     */
    public IncomingCallBean( final String digits, final String collectVDN, final String callAddress, final String extensionCalled,
            final Calendar dateReceived )
    {
        this.digits = digits;
        this.collectVDN = collectVDN;
        this.callAddress = callAddress;
        this.extensionCalled = extensionCalled;
        this.dateReceived = dateReceived.getTime();
    }

    /**
     * Default Constructor.
     */
    public IncomingCallBean()
    {
        this.dateReceived = Calendar.getInstance().getTime();
    }

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


    public String getDigits()
    {
        return digits;
    }


    public void setDigits( final String digits )
    {
        this.digits = digits;
    }


    public String getCollectVDN()
    {
        return collectVDN;
    }


    public void setCollectVDN( final String collectVDN )
    {
        this.collectVDN = collectVDN;
    }


    public String getCallAddress()
    {
        return callAddress;
    }


    public void setCallAddress( final String callAddress )
    {
        this.callAddress = callAddress;
    }


    public String getExtensionCalled()
    {
        return extensionCalled;
    }


    public void setExtensionCalled( final String extensionCalled )
    {
        this.extensionCalled = extensionCalled;
    }

    /**
     *
     * @return Calendar representation of date received.
     */
    public Calendar getDateReceived()
    {
        Calendar result = Calendar.getInstance();
        result.setTimeInMillis( dateReceived.getTime() );

        return result;
    }

    public void setDateReceived( final Calendar dateReceived )
    {
        this.dateReceived = dateReceived.getTime();
    }

    /**
     * @param dateReceived Additional Date version of setDateReceived setter.
     */
    public void setDateReceived( final Date dateReceived )
    {       
        this.dateReceived.setTime( dateReceived.getTime() );
    }
}
