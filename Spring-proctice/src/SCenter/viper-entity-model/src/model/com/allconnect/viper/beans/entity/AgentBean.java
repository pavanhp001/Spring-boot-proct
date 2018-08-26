package com.A.V.beans.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.IndexColumn;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Pattern;

import com.A.V.Constants;
import com.A.V.beans.ContactSuperClass;
import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author ebaugh
 *
 */

@Entity
@Table( name = "agent" )
public class AgentBean extends ContactSuperClass implements CommonBeanInterface
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4579407467885980365L;

	@Id
    @GeneratedValue( strategy=GenerationType.SEQUENCE,generator = "agentBeanSequence" )
    @SequenceGenerator( name = "agentBeanSequence", sequenceName = "AGENT_BEAN_SEQ" ,allocationSize = 1)
    private long id;
    
	@Column
    private String externalId;
	@Column
    private String username;
	@Column( nullable = false )
    private String password;
	@Column( nullable = false )
    private String extension;
    
    @ElementCollection
    @IndexColumn( name = "listOrder", base = 0 )
    private List<GenericGroupBean> groups;
    
    @Transient
    private int status; 
    

    /**
     * Generic constructor for the AgentBean class.
     */
    public AgentBean()
    {
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

    public String getExternalId()
    {
        return externalId;
    }

    public void setExternalId( final String externalId )
    {
        this.externalId = externalId;
    }

    /**
     *
     * @return the agent's password
     */
    @NotNull
    @Length( min = Constants.PASSWORD_MIN_LENGTH, max = Constants.PASSWORD_MAX_LENGTH )
    public String getPassword()
    {
        return password;
    }

    /**
     *
     * @param password the password to be set for the agent
     */
    public void setPassword( final String password )
    {
        this.password = password;
    }

    /**
     *
     * @return username the agent's username
     */
    @Length( min = Constants.USERNAME_MIN_LENGTH, max = Constants.USERNAME_MAX_LENGTH )
    @Pattern( regex = "^\\w*$", message = "not a valid username" )
    public String getUsername()
    {
        return username;
    }

    /**
     *
     * @param username the agent's username
     */
    public void setUsername( final String username )
    {
        this.username = username;
    }

    /**
     *
     * @return extension of the agent
     */
    @NotNull
    @Length( min = Constants.PHONE_EXTENSION_MIN_LENGTH, max = Constants.USERNAME_MAX_LENGTH )
    public String getExtension()
    {
        return extension;
    }

    /**
     *
     * @param extension the string representation of the AgentBean's extension
     */
    public void setExtension( final String extension )
    {
        this.extension = extension;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus( final int status )
    {
        this.status = status;
    }
        
    /**
     * Check to see if the agent can take a call...
     * @return true if they're not waiting.
     */
    public boolean isBusy()
    {
        if ( status != Constants.AGENT_WAITING )
        {
            return true;
        }
        
        return false;
    }

    public List<GenericGroupBean> getGroups()
    {
        return groups;
    }

    public void setGroups( final List<GenericGroupBean> groups )
    {
        this.groups = groups;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString()
    {
        return "AgentBean(" + username + ")";
    }
}
