package com.A.V.beans.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table( name = "CCPRedirectStatus" )
public class CCPRedirectStatus implements Serializable
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -655748668132906074L;

	@Id
    private long id;
        
    @Column( nullable = false )
    private boolean redirect;
    
    /**
     * @return the id
     */
    public long getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId( long id )
    {
        this.id = id;
    }

    /**
     * @return the redirect
     */
    public boolean isRedirect()
    {
        return redirect;
    }

    /**
     * @param redirect the redirect to set
     */
    public void setRedirect( boolean redirect )
    {
        this.redirect = redirect;
    }

    /**
     * @return the emailAddr
     */
    public String getEmailAddr()
    {
        return emailAddr;
    }

    /**
     * @param emailAddr the emailAddr to set
     */
    public void setEmailAddr( String emailAddr )
    {
        this.emailAddr = emailAddr;
    }

    @Column( nullable = false )
    private String emailAddr;
    
   

    
}
