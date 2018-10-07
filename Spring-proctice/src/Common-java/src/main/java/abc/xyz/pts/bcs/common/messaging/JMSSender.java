/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
 package abc.xyz.pts.bcs.common.messaging;

import java.util.Date;
import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.SessionCallback;
import org.springframework.jms.support.JmsUtils;
import org.springframework.transaction.annotation.Transactional;

import abc.xyz.pts.bcs.common.messaging.properties.MessageProperties;

public class JMSSender {
    private static final Logger log = Logger.getLogger( JMSSender.class );

    private JmsTemplate jmsTemplate;
    private Destination destination;
    public final static int JMS_LOWEST_PRIORITY = 0;

    public void setJmsTemplate( final JmsTemplate jmsTemplate ) {
        this.jmsTemplate = jmsTemplate;
    }

    public void setDestination( final Destination destination ) {
        if( log.isInfoEnabled() ) {
            log.info( "Setting destination : " + destination.toString() );
        }
        this.destination = destination;
    }

    public void sendMessageAsynchronously(final String message) throws JMSException {
        if (log.isDebugEnabled()) {
            log.debug("Sending message : " + message);
        }
        final Thread spawned = new Thread() {
            @Override
            public void run() {
                try {
                jmsTemplate.send(destination, new MessageCreator() {
                    @Override
                    public Message createMessage(final Session arg0) throws JMSException {
                        return arg0.createTextMessage(message);
                    }
                }); } catch (final JmsException e) {
                    log.error ( "Failed to send message to db " , e);
                    throw e;
                }
            }
        };
        spawned.start();
    }

    @Transactional
    public void sendMessage(final List <String> listOfMessages) throws JMSException {
        jmsTemplate.execute( new SessionCallback() {
            @Override
            public Object doInJms( final Session session ) {
                sendMessage( listOfMessages,  session );
                return new Boolean(true);
            }
        } );
    }

    private void sendMessage(final List <String> listOfMessages,  final Session session ) {

        MessageProducer messageProducer = null;
        try {
            messageProducer = session.createProducer( destination );
            for( final String message: listOfMessages ) {
                final TextMessage textMessage = session.createTextMessage( message );
                messageProducer.send( textMessage );
                if( log.isDebugEnabled() ) {
                    log.debug( "Sending message : " + message );
                }
            }
        }
        catch( final JMSException e ) {
            throw JmsUtils.convertJmsAccessException( e );
        }
        finally {
            JmsUtils.closeMessageProducer( messageProducer );
        }
    }

    public void sendMessage( final String message ) throws JMSException {
        if( log.isDebugEnabled() ) {
            log.debug( "Sending message : " + message );
        }
        jmsTemplate.send( destination, new MessageCreator() {
            @Override
            public Message createMessage( final Session arg0 ) throws JMSException {
                final TextMessage textMessage = arg0.createTextMessage( message );
                return textMessage;
            }
        } );
    }

    public void sendMessage( final String message, final String correlationId ) throws JMSException {
        if( log.isDebugEnabled() ) {
            log.debug( "Sending message : " + message + " with correlationId: " + correlationId );
        }
        jmsTemplate.send( destination, new MessageCreator() {
            @Override
            public Message createMessage( final Session arg0 ) throws JMSException {
                final TextMessage textMessage = arg0.createTextMessage( message );
                textMessage.setJMSCorrelationID( correlationId );
                return textMessage;
            }
        } );
    }

    public void sendMessage( final String message, final long timeToLive ) throws JMSException {
        if( log.isDebugEnabled() ) {
            log.debug( "Sending message : " + message + " with time to live: " + timeToLive );
        }
        jmsTemplate.send( destination, new MessageCreator() {
            @Override
            public Message createMessage( final Session arg0 ) throws JMSException {
                TextMessage textMessage = arg0.createTextMessage( message );
                textMessage = getTimeToLive( textMessage, timeToLive );
                return textMessage;
            }
        } );
    }

    public void sendMessage( final String message, final String correlationId, final long timeToLive ) throws JMSException {
        if( log.isDebugEnabled() ) {
            log.debug ( "Sending message : " + message + " with correlationId: " + correlationId + " and time to live: " + timeToLive );
        }
        jmsTemplate.send( destination, new MessageCreator() {
            @Override
            public Message createMessage( final Session arg0 ) throws JMSException {
                TextMessage textMessage = arg0.createTextMessage( message );
                textMessage = getTimeToLive( textMessage, timeToLive );
                textMessage.setJMSCorrelationID( correlationId );
                return textMessage;
            }
        } );
    }

    public void sendMessage( final String message, final String[][] properties ) throws JMSException {
        if( log.isDebugEnabled() ) {
            log.debug( "Sending message : " + message + " and properties: " + getPropertiesString( properties ) );
        }
        jmsTemplate.send( destination, new MessageCreator() {
            @Override
            public Message createMessage( final Session session ) throws JMSException {
                final TextMessage textMessage = session.createTextMessage( message );
                setProperties( textMessage, properties );
                return textMessage;
            }
        } );
    }

    public void sendMessage( final String message, final String[][] properties, final int priority) throws JMSException {
        if( log.isDebugEnabled() ) {
            log.debug( "Sending message : " + message + " and properties: " + getPropertiesString( properties ) );
        }
        jmsTemplate.send( destination, new MessageCreator() {
            @Override
            public Message createMessage( final Session session ) throws JMSException {
                final TextMessage textMessage = session.createTextMessage( message );
                setProperties( textMessage, properties );
                textMessage.setJMSPriority(priority);
                return textMessage;
            }
        } );
    }

    public void sendMessage( final String message, final String correlationId, final String[][] properties ) throws JMSException {
        if( log.isDebugEnabled() ) {
            log.debug( "Sending message : " + message + " with correlationId: " + correlationId + " and properties: " + getPropertiesString( properties ) );
        }
        jmsTemplate.send( destination, new MessageCreator() {
            @Override
            public Message createMessage( final Session session ) throws JMSException {
                final TextMessage textMessage = session.createTextMessage( message );
                setProperties( textMessage, properties );
                textMessage.setJMSCorrelationID( correlationId );
                return textMessage;
            }
        } );
    }

    public void sendMessage( final String message, final String[][] properties, final long timeToLive ) throws JMSException {
        if( log.isDebugEnabled() ) {
            log.debug( "Sending message : " + message + " with properties: " + getPropertiesString( properties ) + " and time to live: " + timeToLive );
        }
        jmsTemplate.send( destination, new MessageCreator() {
            @Override
            public Message createMessage( final Session session ) throws JMSException {
                TextMessage textMessage = session.createTextMessage( message );
                setProperties( textMessage, properties );
                textMessage = getTimeToLive( textMessage, timeToLive );
                return textMessage;
            }
        } );
    }

    public void sendMessage( final String message, final String correlationId, final String[][] properties, final long timeToLive ) throws JMSException {
        if( log.isDebugEnabled() ) {
            log.debug( "Sending message : " + message + " with correlationId: " + correlationId + " and properties: " + getPropertiesString( properties ) + " and time to live: " + timeToLive );
        }
        jmsTemplate.send( destination, new MessageCreator() {
            @Override
            public Message createMessage( final Session session ) throws JMSException {
                TextMessage textMessage = session.createTextMessage( message );
                setProperties( textMessage, properties );
                textMessage = getTimeToLive( textMessage, timeToLive );
                textMessage.setJMSCorrelationID( correlationId );
                return textMessage;
            }
        } );
    }

    private TextMessage getTimeToLive( final TextMessage textMessage, final long timeToLive ) throws JMSException {
        final Date expirationDate = new Date();
        final long expiration = expirationDate.getTime() + timeToLive;
        expirationDate.setTime( expiration );
        if( log.isDebugEnabled() ) {
            log.debug( "message expiration: " + expiration + " (" + expirationDate + ")" );
        }
        textMessage.setJMSExpiration( expiration );
        return textMessage;
    }

    private void setProperties( final TextMessage textMessage, final String[][] properties ) throws JMSException {
        for( final String[] row: properties ) {
            if( row[ 0 ].equals( MessageProperties.JMSX_GROUP_SEQ ) ) {
                textMessage.setIntProperty( row[ 0 ], Integer.parseInt( row[ 1 ] ) );
            }
            else {
                textMessage.setStringProperty( row[ 0 ], row[ 1 ] );
            }
        }
    }

    private String getPropertiesString( final String[][] properties ) {
        final StringBuffer propertiesDisplay = new StringBuffer();
        for( int i = 0; i < properties.length; i++ ) {
            final String[] row = properties[ i ];
            propertiesDisplay.append( row[ 0 ] );
            propertiesDisplay.append( '=' );
            propertiesDisplay.append( row[ 1 ] );
            if( i < properties.length - 1 ) {
                propertiesDisplay.append( ',' );
            }
        }
        return propertiesDisplay.toString();
    }

}
