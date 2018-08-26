package com.A.ui.context.impl;

import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.A.validation.Message;

/**
 * @author ebthomas
 * 
 */
public class DefaultMessage implements Message, Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Message.Type type;
    private String messageKey;
    private Long messageCode;
    private List<Object> context;

    
    /**
     * @param type Message Type
     * @param code Message Code
     * @param key  Message Key
     * @param context Objects related to Message.
     */
    public DefaultMessage( final Message.Type type, final Long code, final String key, final List<Object> context )
    {
        if ( type == null || key == null )
        {
            throw new IllegalArgumentException( "Type and messageKey cannot be null" );
        }
        
        this.type = type;
        this.messageKey = key;
        this.context = context;
        this.messageCode = code;
    }

    /**
     * {@inheritDoc}
     */
    public String getMessageKey()
    {
        return messageKey;
    }

    /**
     * {@inheritDoc}
     */
    public Message.Type getType()
    {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    public List<Object> getContextOrdered()
    {
        return context;
    }

    
    
    
    public Long getMessageCode()
    {
        return messageCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals( final Object other )
    {
        if ( this == other )
        {
            return true;
        }
        if ( !( other instanceof DefaultMessage ) )
        {
            return false;
        }
        DefaultMessage castOther = (DefaultMessage) other;
        return new EqualsBuilder().append( type, castOther.type ).append( messageKey, castOther.messageKey ).append( context,
                castOther.context ).append( context,
                        castOther.messageCode ).isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
     
        return new HashCodeBuilder( 1 , 1 ).append( type ).append( messageKey ).append( context ).toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return new ToStringBuilder( this ).append( "type", type ).append( "messageKey", messageKey ).append( "context", context )
                .toString();
    }
}
