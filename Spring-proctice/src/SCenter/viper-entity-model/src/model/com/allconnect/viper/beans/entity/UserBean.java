package com.A.V.beans.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;

import com.A.V.beans.ContactSuperClass;
import com.A.V.interfaces.CommonBeanInterface;

/**
 * @(#) UserBean.java
 */

@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class UserBean extends ContactSuperClass implements CommonBeanInterface
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -4431562663393464482L;

	@Id
    @GeneratedValue( generator = "userBeanSequence" )
    @SequenceGenerator( name = "userBeanSequence", sequenceName = "USER_BEAN_SEQ" )
    private long id;
    
    private boolean badAccount;
    private boolean lockedAccount;
    private Date lastSuccessfulLogin;    
    private Date lastFailedLogin;    
    private Integer failuresSinceSuccess;    
    private Long successfulLoginCount;    
    private Long failedLoginCount;
    
    /**
     * Default Constructor.
     */
    public UserBean( )
    {
        
    }
    
    public long getId()
    {
        return id;
    }

    public void setId( final long id )
    {
        this.id = id;
    }

    /**
     * 
     * @return true if authenticated.
     */
    public Boolean authenticate( )
    {
        return false;
    }
    
    public Boolean isBadAccount( )
    {
        return badAccount;
    }
    
    protected void setBadAccount( final boolean flagValue )
    {
        this.badAccount = flagValue;
    }
    
    public boolean isLockedAccount( )
    {
        return lockedAccount;
    }
    
    protected void setLockedAccount( final boolean flagValue )
    {
        this.lockedAccount = flagValue;
    }
    
    public Date getLastSuccessfulLogin( )
    {
        return lastSuccessfulLogin;
    }
    
    protected void setLastSuccessfulLogin( final Date date )
    {
        this.lastSuccessfulLogin = date;
    }
    
    public Date getLastFailedLogin( )
    {
        return lastFailedLogin;
    }
    
    protected void setLastFailedLogin( final Date date )
    {
        this.lastFailedLogin = date;
    }
    
    public Integer getFailuresSinceSuccess()
    {
        return failuresSinceSuccess;
    }

    protected void setFailuresSinceSuccess( final Integer failuresSinceSuccess )
    {
        this.failuresSinceSuccess = failuresSinceSuccess;
    }
    
    public Long getSuccessfulLoginCount( )
    {
        return successfulLoginCount;
    }
    
    protected void setSuccessfulLoginCount( final Long count )
    {
        this.successfulLoginCount = count;
    }
    
    public Long getFailedLoginCount( )
    {
        return failedLoginCount;
    }

    protected void setFailedLoginCount( final Long count )
    {
        this.failedLoginCount = count;
    }

}
