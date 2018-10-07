/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
/**
 * @author MMotlib-Siddiqui
 */
package abc.xyz.pts.bcs.wi.iir.connection;

import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;
import org.apache.commons.pool.KeyedObjectPool;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import abc.xyz.pts.bcs.wi.iir.exception.IIRConnectException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRConnectionPoolException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSessionException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSocketException;
import abc.xyz.pts.bcs.wi.iir.exception.IIRSystemException;

public class IIRConnectionPool implements InitializingBean
{
    private static final Logger log = Logger.getLogger(IIRConnectionPool.class);

    private static final int POOL_IDLE_CHECK_INTERVAL = 5 * 60 * 1000; // 5 minutes

    private KeyedObjectPool primaryPool;
    private KeyedObjectPool secondaryPool;

    // to record if Primary connections are working or not.
    private boolean isPrimaryUp = true;

    private static final int DEFAULT_INIT_POOL_SIZE = 0;
    private static final int DEFAILT_MAX_POOL_SIZE = 20;

    private int initialPoolSize = DEFAULT_INIT_POOL_SIZE;
    private int maxPoolSize = DEFAILT_MAX_POOL_SIZE;

    // connection attributes
    private final String iirPrimaryHost;
    private final Integer iirPrimaryPort;
    private final String iirSecondaryHost;
    private final Integer iirSecondaryPort;
    private final String iirSystemName;
    private final String iirSystemOptions;

    private final String iirUserName;
    private final String iirPassword;
    private final Integer iirRuleBaseNumber;
    private final String iirService;
    private final Integer iirTimeoutMilliseconds;

    @Override
    // Creates the IIR connection pools, and enable the idle object eviction, as IIR doesn't like connections hanging around
    public void afterPropertiesSet() throws Exception {
        primaryPool = new GenericKeyedObjectPool(new BaseKeyedPoolableObjectFactory() {
            @Override
            public IIRConnection makeObject(final Object key) throws Exception {
                log.debug("Creating new primary IIRConnection object");
                return createConnection(iirPrimaryHost, iirPrimaryPort, true);
            }

            @Override
            public void passivateObject(final Object key, final Object object) throws Exception {
                log.debug("Passivating primary IIRConnection");
            }

            @Override
            public void destroyObject(final Object key, final Object object) throws Exception {
               try
                {
                    log.debug("destroyObject primary IIRConnection");
                    ((IIRConnection)object).disconnect();
                }
                catch (final IIRException e)
                {
                    // no point in logging as error since the container is going down
                    log.warn("Destroy primary IIR connection failed: " + e.getIirErrMsg(), e);
                }
            }
        }, maxPoolSize,
       GenericKeyedObjectPool.WHEN_EXHAUSTED_GROW,
       GenericKeyedObjectPool.DEFAULT_MAX_WAIT,
       maxPoolSize, // maxIdle
       false,    // testonBorrow
           false,    // testonReturn
           POOL_IDLE_CHECK_INTERVAL,
           maxPoolSize, // Check all objects for being idle
           GenericKeyedObjectPool.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS,
           false); // Don't test objects while idle

        secondaryPool = new GenericKeyedObjectPool(new BaseKeyedPoolableObjectFactory() {
            @Override
            public IIRConnection makeObject(final Object key) throws Exception {
                log.debug("Creating new secondary IIRConnection");
                return createConnection(iirSecondaryHost, iirSecondaryPort, false);
            }

            @Override
            public void passivateObject(final Object key, final Object object) throws Exception {
                log.debug("Passivating secondary IIRConnection");
            }

            @Override
            public void destroyObject(final Object key, final Object object) throws Exception {
               try
                {
                   log.debug("destroyObject secondary IIRConnection");
                    ((IIRConnection)object).disconnect();
                }
                catch (final IIRException e)
                {
                    // no point in logging as error since the container is going down
                    log.warn("Destroy secondary IIR connection faileD: " + e.getIirErrMsg(), e);
                }
            }
        }, maxPoolSize,
       GenericKeyedObjectPool.WHEN_EXHAUSTED_GROW,
       GenericKeyedObjectPool.DEFAULT_MAX_WAIT,
       maxPoolSize, // maxIdle
       false,    // testonBorrow
           false,    // testonReturn
           POOL_IDLE_CHECK_INTERVAL,
           maxPoolSize, // Check all objects for being idle
           GenericKeyedObjectPool.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS,
           false); // Don't test objects while idle
    }


    /**
     *
     * @throws IIRConnectException
     */
    public IIRConnectionPool
        ( final String iirPrimaryHost
        , final Integer iirPrimaryPort
        , final String iirSecondaryHost
        , final Integer iirSecondaryPort
        , final String iirSystemName
        , final String iirUserName
        , final String iirPassword
        , final Integer iirRuleBaseNumber
        , final String iirService
        , final Integer iirTimeoutMilliseconds
        , final Integer initialPoolSize
        , final Integer maxPoolSize
        , final String iirSystemOptions
        ) throws IIRConnectException
    {
        this.iirPrimaryHost = iirPrimaryHost;
        this.iirPrimaryPort = iirPrimaryPort;
        this.iirSecondaryHost = iirSecondaryHost;
        this.iirSecondaryPort = iirSecondaryPort;
        this.iirSystemName = iirSystemName;
        this.iirUserName = iirUserName;
        this.iirPassword = iirPassword;
        this.iirRuleBaseNumber = iirRuleBaseNumber;
        this.iirService = iirService;
        this.iirTimeoutMilliseconds = iirTimeoutMilliseconds;
        this.initialPoolSize = initialPoolSize;
        this.maxPoolSize = maxPoolSize;
        this.iirSystemOptions = iirSystemOptions;

        if(log.isDebugEnabled())
        {
            log.debug("primaryHost:" + this.iirPrimaryHost);
            log.debug("primaryPort:" + this.iirPrimaryPort);
            log.debug("secondaryHost:" + this.iirSecondaryHost);
            log.debug("secondaryPort:" + this.iirSecondaryPort);
            log.debug("systemName:" + this.iirSystemName);
            log.debug("systemOptions:" + this.iirSystemOptions);
            log.debug("username:" + this.iirUserName);
            log.debug("ruleBaseNumber:" + this.iirRuleBaseNumber);
            log.debug("service:" + this.iirService);
            log.debug("timeout:" + this.iirTimeoutMilliseconds);
            log.debug("initialPoolSize:" + this.initialPoolSize);
            log.debug("maxPoolSize:" + this.maxPoolSize);
        }

    }

    // This is only used for debug logging, so I'm not loosing sleep over the string concatenation
    private static String getPoolStatusString(final KeyedObjectPool pool) {
    return "Active: " + pool.getNumActive() + " Idle: " + pool.getNumIdle();
    }

    /**
     *
     * @param numConnections
     * @param host
     * @param port
     * @return
     * @throws IIRConnectException
     */
    private IIRConnection createConnection
            ( final String host
            , final Integer port
            , final boolean isPrimary
            ) throws IIRConnectException
    {
        try
        {

                final IIRConnectionImpl c = new IIRConnectionImpl();
                c.setPrimary(isPrimary);
                c.setIirConnectionServerHost(host);
                c.setIirConnectionServerPort(port);
                c.setIirSystemName(iirSystemName);
                c.setIirSystemOptions(iirSystemOptions);
                c.setIirRuleBaseNumber(iirRuleBaseNumber);
                c.setIirUserName(iirUserName);
                c.setIirPassword(iirPassword);
                c.setIirTimeoutMilliseconds(iirTimeoutMilliseconds);
                c.setIirService(iirService);

                // ** make connection
                c.connect();

                log.debug("IIR connection created to host:" + host + " port: " + port + " rulebase:" + iirRuleBaseNumber
                        + " system:" + iirSystemName);
            return c;
        }
        catch (final IIRSessionException e)
        {
            log.error(e.getIirErrMsg(), e);
            throw new IIRConnectException(e);
        }
        catch (final IIRSocketException e)
        {
            log.error(e.getIirErrMsg(), e);
            throw new IIRConnectException(e);
        }
        catch (final IIRSystemException e)
        {
            log.error(e.getIirErrMsg(), e);
            throw new IIRConnectException(e);
        }
    }

    public void releaseConnection(final IIRConnection c)
    {
        releaseConnection(c, null);
    }

    public void releaseConnection(final IIRConnection c, final Exception e)
    {
        if (c == null) {
            return;
        }

        if(log.isDebugEnabled()) {
            if (e instanceof IIRException) {
                log.debug("Release connection after IIR exception:" + ((IIRException)e).getIirErrMsg(), e);
            } else {
                log.debug("Release connection after exception",e);
            }
        }

        // If there was an exception thrown by, always invalidate the connection
        if(e != null) {
            try{
                c.disconnect();
            } catch(final IIRException s) {
                log.warn("Failed to clean up IIR connection properly", s);
            } finally {
                log.warn("Invalidating connection object");
                try {
                   if(c.isPrimary()) {
                       primaryPool.invalidateObject(c.getIirSearchName(),c);
                    } else {
                       secondaryPool.invalidateObject(c.getIirSearchName(), c);
                    }
                } catch (final Exception s) {
                    log.warn("Unable to invalidate stale object: " + c, s);
                }
            }
        } else {
            try{
                log.debug("Returning connection to pool");
                if(c.isPrimary()) {
                    primaryPool.returnObject(c.getIirSearchName(), c);
                } else {
                    secondaryPool.returnObject(c.getIirSearchName(), c);
                }
            } catch(final Exception s) {
                log.warn("Unable to return object to pool", s);
            }
        }

        if(log.isDebugEnabled()) {
            log.debug("Current status of primary pool after connection release - " + getPoolStatusString(primaryPool));
            log.debug("Current status of secondary pool: after connection release - " + getPoolStatusString(secondaryPool));
        }

    }

    /**
     * Allocate a connection from the pool.
     *
     * @return connection
     * @throws IIRConnectException
     * @throws IIRConnectionPoolException
     */
    public IIRConnection allocateConnection(
            final String searchName
            , final boolean isPrimary
            ) throws IIRConnectException, IIRConnectionPoolException
    {

        if(log.isDebugEnabled()) {
            log.debug("Current status of primary pool before allocation - " + getPoolStatusString(primaryPool));
            log.debug("Current status of secondary pool: before allocation - " + getPoolStatusString(secondaryPool));
        }
        IIRConnection con = null;

        try {

            if (log.isDebugEnabled()) {
                log.debug("allocating connection from iir-" + (isPrimary ? "primary" : "secondary") + " pool");
            }

            if(isPrimary) {
                con = (IIRConnection)primaryPool.borrowObject(searchName);
            } else {
                con = (IIRConnection)secondaryPool.borrowObject(searchName);
            }

            con.setIirSearchName(searchName);

        } catch(final Exception e) {// That's the contract of the commons-pool API, sorry!
            log.error("IIR connection allocation failed", e);
            throw new IIRConnectionPoolException("Cannot allocate connection from pool:", e);
        }

        return con;
    }


    private void closeConnections(final KeyedObjectPool pool)
    {
        try {
            pool.clear();
        } catch(final Exception e) {
            log.warn("Unabled to clear connections from pool:" + pool, e);
        }
    }

    /**
     * Close all connections
     *
     * @throws Exception
     */
    public void closeAllConnections()
    {
        log.debug("closing all connections");

        closeConnections(this.primaryPool);
        closeConnections(this.secondaryPool);
    }

    public boolean isPrimaryUp()
    {
        return isPrimaryUp;
    }

    /**
     * The caller passes in the value it holds for isPrimaryUp.
     * This is used to see if the Primary needs toggling.
     *
     * @param isPrimaryUpStored
     */
    public synchronized void toggleConnectionPool(final boolean isPrimaryUpStored)
    {
        // Are we still using the same Pool when the caller took a snapshot?
        if (this.isPrimaryUp == isPrimaryUpStored)
        {
            // Yes - so we need to toggle the Pool
            this.isPrimaryUp = !this.isPrimaryUp;
            if (log.isDebugEnabled())
            {
                log.debug("isPrimaryUp=" + this.isPrimaryUp);
            }
        }
    }

}

