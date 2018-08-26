package com.A.ui.context.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import java.util.Collection;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;

import com.A.validation.Message;
import com.A.validation.ReportFactory;
import com.A.validation.ValidationReport;
import com.A.validation.Message.Type;

/**
 * @author ebthomas
 * 
 */
public class DefaultValidationReport implements ValidationReport, Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Map<Message.Type, Set<Message>> messagesMap = new HashMap<Message.Type, Set<Message>>();

    private ReportFactory factory = null;

    /**
     * Constructor default validation report.
     */
    public DefaultValidationReport()
    {
        factory = new DefaultReportFactory();
    }

    /**
     * @param factory Report Factory.
     */
    public DefaultValidationReport( final ReportFactory factory )
    {
        this.factory = factory;
    }

    /**
     * {@inheritDoc}
     */
    public Set<Message> getMessages()
    {
        Set<Message> messagesAll = new HashSet<Message>();
        for ( Collection<Message> messages : messagesMap.values() )
        {
            messagesAll.addAll( messages );
        }
        return messagesAll;
    }

    /**
     * {@inheritDoc}
     */
    public Message getMessage( final String messageKey )
    {
        if ( contains( messageKey ) )
        {
            for ( Message message : getMessages() )
            {
                if ( messageKey.equals( message.getMessageKey() ) )
                {
                    return message;
                }
            }
        }

        return null;
    }
    
    public Set<Message> getOutcome( )
    {
    	return getMessagesByType(   Message.Type.OUTCOME   );
    }
     
    
    public Set<Message> getError( )
    {
    	return getMessagesByType(   Message.Type.ERROR   );
    }
    
    public Set<Message> getFatal( )
    {
    	return getMessagesByType(   Message.Type.FATAL   );
    }
    
    public Set<Message> getWarning( )
    {
    	return getMessagesByType(   Message.Type.WARNING   );
    }
    
    public Set<Message> getDebug( )
    {
    	return getMessagesByType(   Message.Type.DEBUG );
    }
    
    public Set<Message> getInfo( )
    {
    	return getMessagesByType(   Message.Type.INFO );
    }
    
    public Set<Message> getSystem( )
    {
    	return getMessagesByType(   Message.Type.SYSTEM );
    }
    
    

    /**
     * {@inheritDoc}
     */
    public Set<Message> getMessagesByType( final Message.Type type )
    {
        if ( type == null )
        {
            return Collections.emptySet();
        }

        Set<Message> messages = messagesMap.get( type );
        if ( messages == null )
        {
            return Collections.emptySet();
        }

        return messages;
    }

    /**
     * @return boolean to indicate existance of messages
     */
    public boolean hasMessages()
    {
        return ( getMessages().size() > 0 );
    }

    /**
     * @return boolean to indicate existance of messages
     */
    public boolean hasErrors()
    {
        return contains( Type.ERROR ) || contains( Type.FATAL ) ;
    }

    /**
     * @return boolean to indicate existance of messages
     */
    public boolean hasFatal()
    {
        return contains( Type.FATAL );
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasInfo()
    {
        return contains( Message.Type.INFO );
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasWarnings()
    {
        return contains( Message.Type.WARNING );
    }

    /**
     * {@inheritDoc}
     */
    public boolean contains( final Message.Type type )
    {
        for ( Message message : getMessages() )
        {
            if ( type.equals( message.getType() ) )
            {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean contains( final String messageKey )
    {
        for ( Message message : getMessages() )
        {
            if ( messageKey.equals( message.getMessageKey() ) )
            {
                return true;
            }
        }
        return false;
    }

    
    /**
     * {@inheritDoc}
     */
    public Message addInfoMessage(  final Long messageCode, final String messageKey )
    {
        Message message = factory.createMessage( Message.Type.INFO, messageCode, messageKey );

        if ( message != null )
        {
            this.addMessage( message );
        }

        return message;
    }
    
    
    
    
    /**
     * {@inheritDoc}
     */
    public Message addErrorMessage(  final Long messageCode, final String messageKey )
    {
        Message message = factory.createMessage( Message.Type.ERROR, messageCode, messageKey );

        if ( message != null )
        {
            this.addMessage( message );
        }

        return message;
    }
    
    
    /**
     * {@inheritDoc}
     */
    public Message addMessage( final Message.Type type, final Long messageCode, final String messageKey, final Object... context )
    {
        Message message = factory.createMessage( type, messageCode, messageKey, context );

        if ( message != null )
        {
            this.addMessage( message );
        }

        return message;
    }

    /**
     * {@inheritDoc}
     */
    public boolean addMessages( final Set<Message> newMessages )
    {
        if ( newMessages == null )
        {
            return false;
        }

        for ( Message message : newMessages )
        {
            addMessage( message );
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean addMessage( final Message message )
    {
        if ( message == null )
        {
            return false;
        }

        Set<Message> messages = messagesMap.get( message.getType() );
        if ( messages == null )
        {
            messages = new HashSet<Message>();
            messagesMap.put( message.getType(), messages );
        }
        return messages.add( message );
    }

  
    public boolean hasMessages( final Type type )
    {
        return contains( type );
    }

    public Map<Message.Type, Set<Message>> getMessagesMap()
    {
        return messagesMap;
    }

    public void setMessagesMap( final Map<Message.Type, Set<Message>> messagesMap )
    {
        this.messagesMap = messagesMap;
    }

}
