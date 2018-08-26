/**
 *
 */
package com.AL.comm.manager.jms.util;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import com.AL.comm.factory.impl.CommunicationManagerFactoryJMS;
import com.AL.comm.manager.CommunicationManager;

/**
 * @author ebthomas
 *
 */
public enum JMSConfigManager {
    INSTANCE;

    private static final Logger logger = Logger.getLogger(JMSConfigManager.class);
    private static Map<String, JMSConfigNamespace> namespaces = new ConcurrentHashMap<String, JMSConfigNamespace>();
    private static boolean requireInitialize = true;
    private static boolean isOptimizedForManualSender = true;
    // private static final ExceptionListenerDefault
    // defaultConnectionExceptionHandler = new ExceptionListenerDefault();

    // *************************************************************************
    // *************************************************************************

    private final Object jndiLock = new Object();
    private InitialContext initialContext;
    private static String CONNECTION_SUFFIX = ".connection";

    public String getPhysicalDestination(final String namespace,
            final String targetQueue) {
        JMSConfigNamespace ns = JMSConfigManager.INSTANCE
                .getNamespace(namespace);
        String destName = ns.getQueue(targetQueue);

        return destName;
    }

    public JMSConfigNamespace getNamespace(final String key) {

        JMSConfigNamespace namespaceConfig = namespaces.get(key);

        if (namespaces == null) {
            //logger.info("jms.config.manager.not.initialized");
            return null;
        }

        if (namespaceConfig == null) {
            //logger.debug("building.jms.namespace:" + key);
            namespaceConfig = new JMSConfigNamespace(key);

            JMSConfigManager.namespaces.put(key, namespaceConfig);
        }

        return namespaceConfig;
    }

    Map<String, Object> cache = new ConcurrentHashMap<String, Object>();

    private JMSConfigManager() {
        initialize();
        //logger.debug("jms.config.manager.initialized");
    }

    public void initialize() {

    }

    public TemporaryQueue createTemporaryQueue(Session session) {

        TemporaryQueue tempDest;
        try {
            tempDest = session.createTemporaryQueue();
            logger.debug("created temp queue:" + tempDest);
        } catch (JMSException e) {
            logger.error("unable to create temporary queue", e);
            throw new IllegalArgumentException(
                    "unable.to.create.temporary.queue");
        }
        return tempDest;
    }

    public Destination createTemporaryDestination(Session session) {

        Destination tempDest;
        try {
            tempDest = session.createTemporaryQueue();
            return tempDest;
        } catch (JMSException e) {
            logger.error("unable to create temporary destination", e);
        }

        return null;

    }

    public Object lookup(final String key) {

        Object obj = null;

        if ((!isOptimizedForManualSender) && (initialContext != null)) {
            try {
                obj = lookupFromJndi(key);
                if (obj != null)
                    cache.put(key, obj);
            } catch (NamingException e) {
                logger.error("unable to locate object in cache or jndi lookup." + key, e);
            }
        }

        return obj;
    }

    public Object lookupFromJndi(String jndiName) throws NamingException {

        //logger.info("creating.initial.context");
        InitialContext initialContext = new InitialContext();
        //logger.info("returning.initial.context" + jndiName + ".."
                //+ initialContext.getNameInNamespace());
        return initialContext.lookup(jndiName);
        // if (initialContext == null) {
        // createInitialContext();
        // }
        //
        // if (initialContext != null) {
        // synchronized (jndiLock) {
        //
        // try {
        // return initialContext.lookup(jndiName);
        // } catch (Exception ce) {
        // //logger.error(
        // "unable.to.locate.object.in.cache.or.jndi.lookup."
        // + jndiName, ce);
        //
        // return null;
        // }
        // }
        // }
        //
        // return null;
    }

    public void createInitialContext() {
        try {
            if (getInitialContext() == null) {
                synchronized (jndiLock) {
                    if (getInitialContext() == null) {
                        //logger.debug("creating.initial.context");
                        initialContext = new InitialContext();
                        //logger.debug("initial.context.initialized");
                    }
                }
            }
        } catch (NamingException e) {
            //logger.info("JNDI not configured. Assume running outside container");
            initialContext = null;
        }
    }

    public Connection getIndependentConnection(final String namespace)
            throws JMSException {
    	logger.debug("Executing getIndependentConnection");
        String username = namespaces.get(namespace).getUsername();
        String password = namespaces.get(namespace).getPassword();

        ConnectionFactory jmsConnectionFactory = this
                .getConnectionFactory(namespace);

        Connection connection = createAndStartConnection(jmsConnectionFactory,
                username, password);

        return connection;
    }

    protected void doStart(final Connection connection) throws JMSException {
        // TODO: This should never be null or an exception should be thrown
        if (connection != null) {
            try {

                ExceptionListener handler = connection.getExceptionListener();

                if ((handler == null) || (!(handler instanceof ExceptionListenerDefault))) {
                    ExceptionListenerDefault exHandler = new ExceptionListenerDefault(
                            connection);
                    connection.setExceptionListener(exHandler);
                }
                connection.start();

            } catch (JMSException e) {
                //logger.error("jms.connection.error.starting.connection", e);
                throw new IllegalArgumentException(
                        "unable.to.start.JMS.connection");
            }
        }
    }

    public boolean start() throws Exception {
        Collection<Object> connList = cache.values();

        for (Object obj : connList) {
            if (obj instanceof Connection) {
                Connection conn = (Connection) obj;
                // Incoming Messages started.
                doStart(conn);
            }
        }

        return Boolean.TRUE;
    }

    public boolean restart() throws Exception {
        Collection<Object> connList = cache.values();

        for (Object obj : connList) {
            if (obj instanceof Connection) {
                Connection conn = (Connection) obj;
                doStop(conn);
                conn.close();
                doStart(conn);

            }
        }

        return Boolean.TRUE;
    }

    public boolean stop() throws JMSException {
        Collection<Object> connList = cache.values();

        for (Object obj : connList) {
            if (obj instanceof Connection) {
                Connection conn = (Connection) obj;
                // Incoming Messages Stopped.
                conn.stop();

                // Close the Connection.
                conn.close();
            }
        }

        return Boolean.TRUE;
    }

    protected void doStop(Connection connection) throws Exception {
        if (connection != null) {
            try {
                connection.stop();
            } catch (JMSException e) {
                //logger.error("jms.connection.error.unable.to.stop.jms.connection", e);
                throw new IllegalArgumentException(
                        "unable.to.stop.JMS.connection");
            }
        }
    }

    public void removeConnection(final String key) {
        if (cache != null) {
            cache.remove(key + CONNECTION_SUFFIX);
        }

    }

    public void putConnectionFactory(final String key,
            final ConnectionFactory jmsConnectionFactory,
            final Connection jmsConnection) {
        if (cache != null) {
            cache.remove(key);
            cache.remove(key + CONNECTION_SUFFIX);

            cache.put(key, jmsConnectionFactory);
            cache.put(key + CONNECTION_SUFFIX, jmsConnection);
        }
    }

    protected void applyVendorSpecificConnectionFactoryProperties(
            String namespace, ConnectionFactory connectionFactory) {
        try {
            Method getRedeliveryPolicyMethod = connectionFactory.getClass()
                    .getMethod("getRedeliveryPolicy");
            Object redeliveryPolicy = getRedeliveryPolicyMethod
                    .invoke(connectionFactory);
            Method setMaximumRedeliveriesMethod = redeliveryPolicy.getClass()
                    .getMethod("setMaximumRedeliveries", Integer.TYPE);
            // redelivery = deliveryCount - 1, but AMQ is considering the first
            // delivery attempt as a redelivery (wrong!). adjust
            // for it
            int redeliveryMax = JMSConfigManager.INSTANCE.getNamespace(
                    namespace).getRedeliveryMax();
            setMaximumRedeliveriesMethod.invoke(redeliveryPolicy,
                    redeliveryMax + 1);
        } catch (Exception e) {
            logger.error("cannot set max redelivery parameter to redelivery policy", e);
        }
    }

    public ConnectionFactory getConnectionFactory(final String namespace) {

        logger.debug("locating connection factory for namespace=" + namespace);

        JMSConfigNamespace jmsConfigNS = JMSConfigManager.INSTANCE
                .getNamespace(namespace);

        if (jmsConfigNS != null) {
            logger.trace("located jms config namespace." + namespace);
        } else {
            logger.trace("unable to locate jms config namespace." + namespace);
        }

        String jndiConnectionFactoryName = jmsConfigNS
                .getJndiConnectionFactoryName();

        logger.debug("searching connection cache for connection." + jndiConnectionFactoryName);

        ConnectionFactory jmsConnectionFactory = (ConnectionFactory) get(jndiConnectionFactoryName);

        if (jmsConnectionFactory == null) {

            String username = namespaces.get(namespace).getUsername();

            String password = namespaces.get(namespace).getPassword();

            logger.debug("connection factory not found in cache");
            try {
                if ((isRunningInContainer()) && (!isOptimizedForManualSender)) {
                    logger.debug("getting container factory connection");
                    jmsConnectionFactory = (ConnectionFactory) initialContext
                            .lookup(jndiConnectionFactoryName);

                } else {

                    String messageBrokerUrl = namespaces.get(namespace).getMessageBrokerUrl();

                    // TODO:Place this into a Factory ...to be Invoked.
                    String jmsVendor = namespaces.get(namespace).getVendor();
                    logger.debug("creating manual connection factory : "+ jmsVendor);

                    if (jmsVendor != null) {
                        jmsConnectionFactory = createConnectionFactory(
                                username, password, messageBrokerUrl);

                    } else {
                        jmsConnectionFactory = createConnectionFactory(
                                username, password, messageBrokerUrl);

                    }

                }

                if (jmsConnectionFactory != null) {
                    logger.debug("connection factory created apply vendor properties");
                    applyVendorSpecificConnectionFactoryProperties(namespace,
                            jmsConnectionFactory);
                    put(jndiConnectionFactoryName.trim(), jmsConnectionFactory);

                } else {
                    logger.warn("Unable to create connection factory");
                }

            } catch (Exception cce) {

                logger.error("Unable to get and initialize connection factory",cce);
            }

        }

        return jmsConnectionFactory;
    }

    private Connection createAndStartConnection(
            final ConnectionFactory jmsConnectionFactory,
            final String username, final String password) {
        Connection connection = null;

        if ((username != null) && (password != null) && (username.length() > 0)
                && (password.length() > 0)) {
            try {
                connection = jmsConnectionFactory.createConnection(username,
                        password);

                logger.trace("starting credential authenticated connection");
                doStart(connection);
                logger.debug("put credential authenticated connection and factory in cache");
            } catch (JMSException e) {

                for (int i = 0; i < 3; i++)
                    logger.warn("################ " + e.getMessage() + " JMS Connection Error.  Wait and Try Again ####################");

                try {
                    Thread.sleep(2000);

                    connection = jmsConnectionFactory.createConnection(
                            username, password);

                    logger.trace("retry starting credential authenticated connection");
                    doStart(connection);
                    logger.trace("retry put credential authenticated connection and factory in cache");
                } catch (Exception e1) {
                    logger.error("createAndStartConnection() failed with : " , e1);
                }
            }

        } else {
            try {
                connection = jmsConnectionFactory.createConnection();

                logger.trace("starting unsecure connection");
                doStart(connection);
                logger.debug("put unsecure connection and factory in cache");
            } catch (JMSException e) {
                logger.error("Exception thrown",e);
            }
        }

        return connection;
    }

    private ConnectionFactory createConnectionFactory(final String username,
            final String password, final String messageBrokerUrl) {
        ConnectionFactory jmsConnectionFactory = null;

        if ((username != null) && (password != null) && (username.length() > 0)
                && (password.length() > 0)) {
            logger.trace("create connection factory:" + messageBrokerUrl);
            
            ActiveMQConnectionFactory activeMQConnectionFactory= new ActiveMQConnectionFactory(username,
                    password, messageBrokerUrl);
            activeMQConnectionFactory.setWatchTopicAdvisories(false); // JBOss EAP 7 was not supporting param in URL so added in code
            jmsConnectionFactory = activeMQConnectionFactory;
            
//          PooledConnectionFactory pcf = new PooledConnectionFactory();
//          pcf.setConnectionFactory( jmsConnectionFactory);
//          pcf.setIdleTimeout(idleTimeout);
//          pcf.setMaxConnections(maxConnections);
//          pcf.setMaximumActive(maximumActive);


        } else {
            logger.trace("create connection factory:" + messageBrokerUrl);
            jmsConnectionFactory = new ActiveMQConnectionFactory(
                    messageBrokerUrl);
        }
        return jmsConnectionFactory;
    }

    public Connection restartConnection(final String namespace) {
        Connection connection = null;
        try {
            connection = getIndependentConnection(namespace);
        } catch (JMSException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        return restartConnection(connection);

    }

    public Connection restartConnection(final Connection connection) {

        logger.trace("attempting restart");
        try {
            //logger.info("attempting stop connection:");
            doStop(connection);
            //logger.info("stop connection:");
        } catch (Exception e) {
            //logger.info("error stopping connection:" + e.getMessage());
        }

        try {
            //logger.info("attempting start connection:");
            doStart(connection);
            //logger.info("connection started:");
        } catch (JMSException e) {
            logger.error("Error starting connection:",e);
        }

        return connection;
    }

    public Connection getConnection(final String namespace) {

        if (true)
            throw new IllegalArgumentException(
                    "this code should not run until connection pool is setup");

        JMSConfigNamespace jmsConfigNS = JMSConfigManager.INSTANCE
                .getNamespace(namespace);

        if (jmsConfigNS != null) {
            //logger.info("located.jms.config.namespace." + namespace);
        } else {
            //logger.info("unable.to.locate.jms.config.namespace." + namespace);
            throw new IllegalArgumentException("missing jms config namespace:"
                    + namespace);
        }

        String jndiConnectionFactoryName = jmsConfigNS
                .getJndiConnectionFactoryName();

        Connection connection = (Connection) get(jndiConnectionFactoryName
                + CONNECTION_SUFFIX);

        if (connection == null) {
            logger.trace("connection NOT found in cache locating factory for namespace:" + namespace);

            ConnectionFactory connectionFactory = getConnectionFactory(namespace);

            if (connectionFactory != null) {
                //logger.info("connection.factory.found:" + namespace);
                connection = (Connection) get(jndiConnectionFactoryName
                        + CONNECTION_SUFFIX);

                try {
                    doStart(connection);
                } catch (JMSException e) {
                    //logger.error(e);
                }
            }
        } else {
            logger.debug("connection found in cache");
        }

        return connection;
    }

    public Object get(final String key) {
        //logger.info("get.from.connection.cache.with.key:" + key);
        Object obj = null;

        if (cache != null) {
            obj = cache.get(key);

            if (obj == null) {
                obj = lookup(key);

                if (obj != null) {
                    put(key, obj);
                }
            } else {
                //logger.info("found.connection.in.cache." + key);
            }
        } else {
            //logger.info("connection.cache.does.not.exist");
        }
        if(obj != null)
        	logger.info("Object Type="+obj.getClass());
        return obj;
    }

    public void put(final String key, final Object value) {
        if ((cache != null) && ((value instanceof ConnectionFactory))) {
            //logger.info("adding.to.cache:" + key);
            cache.put(key, value);
        }
    }

    public void remove(final String key) {
        if (cache != null) {
            cache.remove(key);
        }
    }

    public Map<String, Object> getCache() {
        return cache;
    }

    public void setCache(Map<String, Object> cache) {
        this.cache = cache;
    }

    public InitialContext getInitialContext() {
        if ((initialContext == null) && (requireInitialize)) {
            synchronized (this) {
                initialize();
            }
            requireInitialize = false;
        }

        return initialContext;

    }

    public boolean isRunningInContainer() {

        if (isOptimizedForManualSender) {
            return Boolean.FALSE.booleanValue();
        }

        try {
            if ((initialContext == null) && (requireInitialize)) {
                initialContext = new InitialContext();
            }

            if ((initialContext != null) && (!requireInitialize)) {
                return (initialContext.lookup("") != null);
            }
            return Boolean.FALSE;
        } catch (NamingException ex)

        {
            //logger.info("initial context not found");
        }

        return Boolean.FALSE;

    }

    public void doDisconnect(Connection connection) {
        if (connection == null) {
            return;
        }

        try {
            //logger.info("stopping c ...");
            ExceptionListener exHandler = connection.getExceptionListener();
            if ((exHandler != null)
                    && (exHandler instanceof ExceptionListenerDefault)) {
                //logger.info("stopped c...");
                ExceptionListenerDefault defaultHandler = (ExceptionListenerDefault) exHandler;
                defaultHandler.clearConnection();
            }

            connection.setExceptionListener(null);
            connection.stop();
            //logger.info("stopped c...");
        } catch (JMSException e) {
            //logger.error(e);
        } finally {
            try {
                //logger.info("closing c ...");
                connection.close();
                //logger.debug("closed c...");
            } catch (JMSException e) {
                //logger.error(e);
            }
        }

    }

    protected void doDisconnect(final String key) throws Exception {
        try {
            Connection connection = getConnection(key);
            if (connection == null) {
                return;
            }

            final Class<?> clazz = connection.getClass();
            Method cleanupMethod;

            cleanupMethod = clazz.getMethod("cleanup", (Class[]) null);

            try {
                if (cleanupMethod != null) {
                    cleanupMethod.invoke(connection, (Object[]) null);
                }
            } finally {
                doDisconnect(connection);
            }
        } catch (Exception e) {
            //logger.error("unable.to.disconnect.from.message.queue", e);
        } finally {

            removeConnection(key);
        }
    }

    public void closeSession(Session session) {
        if (session != null) {
            try {
                session.close();
            } catch (JMSException ex) {
                //logger.debug("Could not close JMS Session", ex);
            } catch (Throwable ex) {
                // We don't trust the JMS provider: It might throw
                // RuntimeException or Error.
                //logger.debug("Unexpected exception on closing JMS Session", ex);
            }
        }
    }

    public void closeMessageProducer(MessageProducer producer) {
        if (producer != null) {
            try {
                producer.close();
            } catch (JMSException ex) {
                //logger.debug("Could not close JMS MessageProducer", ex);
            } catch (Throwable ex) {
                // We don't trust the JMS provider: It might throw
                // RuntimeException or Error.
                //logger.debug(
                        //"Unexpected exception on closing JMS MessageProducer",
                        //ex);
            }
        }
    }

    public void closeQueueBrowser(QueueBrowser browser) {
        if (browser != null) {
            try {
                browser.close();
            } catch (JMSException ex) {
                //logger.debug("Could not close JMS QueueBrowser", ex);
            } catch (Throwable ex) {
                // We don't trust the JMS provider: It might throw
                // RuntimeException or Error.
                //logger.debug(
                        //"Unexpected exception on closing JMS QueueBrowser", ex);
            }
        }
    }

    public static void commitIfNecessary(Session session) throws JMSException {

        try {
            session.commit();
        } catch (javax.jms.TransactionInProgressException ex) {
            // Ignore -> can only happen in case of a JTA transaction.
        } catch (javax.jms.IllegalStateException ex) {
            // Ignore -> can only happen in case of a JTA transaction.
        }
    }

    public void closeMessageConsumer(MessageConsumer consumer) {
        if (consumer != null) {
            // Clear interruptions to ensure that the consumer closes
            // successfully...
            // (working around misbehaving JMS providers such as ActiveMQ)
            boolean wasInterrupted = Thread.interrupted();
            try {
                consumer.close();
            } catch (JMSException ex) {
                //logger.debug("Could not close JMS MessageConsumer", ex);
            } catch (Throwable ex) {
                // We don't trust the JMS provider: It might throw
                // RuntimeException or Error.
                //logger.debug("Unexpected exception on closing JMS MessageConsumer",ex);
            } finally {
                if (wasInterrupted) {
                    // Reset the interrupted flag as it was before.
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private CommunicationManagerFactoryJMS communicationManagerFactoryJMS = new CommunicationManagerFactoryJMS();

    public CommunicationManager<Message, MessageListener> createCommunicationManager(String namespace)
    {
        CommunicationManager<Message, MessageListener> cm
            = communicationManagerFactoryJMS.createCommunicationManager(namespace);

        return cm;
    }

    public CommunicationManager<Message, MessageListener> createCommunicationManager()
    {
        final String defaultNameSpaceName = "default";

        CommunicationManager<Message, MessageListener> cm
            = communicationManagerFactoryJMS.createCommunicationManager(defaultNameSpaceName);

        return cm;
    }

}
