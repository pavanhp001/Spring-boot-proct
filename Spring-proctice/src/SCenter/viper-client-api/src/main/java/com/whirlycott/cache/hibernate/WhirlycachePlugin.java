/*
 * Created on Dec 19, 2004 by pjacob
 *
 */
package com.whirlycott.cache.hibernate;

//import net.sf.hibernate.cache.Timestamper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;
import com.whirlycott.cache.Messages;

/**
 * @author pjacob
 *  
 */
public class WhirlycachePlugin { //implements net.sf.hibernate.cache.Cache {

    /**
     * Logger.
     */
    private static final Log log = LogFactory.getLog(WhirlycachePlugin.class);

    /**
     * Number of milliseconds in 1 minute.
     */
    private static final int MS_PER_MINUTE = 60000;

    /**
     * Reference to the Whirlycache that we are going to use.
     */
    private Cache cache;

    /**
     * Name of the cache we're using.
     */
    private final String cacheName;

    /**
     * 
     * @param _name
     */
    public WhirlycachePlugin(final String _name)
            throws com.whirlycott.cache.CacheException {
        super();

        //Short circuit if there's any nonsense.
        if (_name == null)
            throw new IllegalArgumentException(Messages.getString("WhirlycachePlugin.cannot_lookup_cache_with_null_name")); //$NON-NLS-1$

        //Store the cache name away for using with the destroy() method.
        cacheName = _name;

        try {
            cache = CacheManager.getInstance().getCache(_name);
        } catch (final CacheException e) {
            //Rethrow this whirlycache-specific exception as a hibernate exception.
           // throw new com.whirlycott.cache.sf.hibernate.cache.CacheException(e.getMessage());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.hibernate.cache.Cache#clear()
     */
    public void clear() throws com.whirlycott.cache.CacheException {
        cache.clear();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.hibernate.cache.Cache#destroy()
     */
    public void destroy() throws com.whirlycott.cache.CacheException {
        try {
            CacheManager.getInstance().destroy(cacheName);
        } catch (final CacheException e) {
            log.error(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.hibernate.cache.Cache#get(java.lang.Object)
     */
    public Object get(final Object _key) throws com.whirlycott.cache.CacheException {
        return cache.retrieve(_key);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.hibernate.cache.Cache#getTimeout()
     */
    public int getTimeout() {
      return 0;//  return Timestamper.ONE_MS * MS_PER_MINUTE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.hibernate.cache.Cache#lock(java.lang.Object)
     */
    public void lock(final Object arg0) throws com.whirlycott.cache.CacheException {
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.hibernate.cache.Cache#nextTimestamp()
     */
    public long nextTimestamp() {
     return 0;//   return Timestamper.next();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.hibernate.cache.Cache#put(java.lang.Object, java.lang.Object)
     */
    public void put(final Object _key, final Object _val) throws com.whirlycott.cache.CacheException {
        cache.store(_key, _val);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.hibernate.cache.Cache#remove(java.lang.Object)
     */
    public void remove(final Object _key) throws com.whirlycott.cache.CacheException {
        cache.remove(_key);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.hibernate.cache.Cache#unlock(java.lang.Object)
     */
    public void unlock(final Object arg0) throws com.whirlycott.cache.CacheException {
        return;
    }

}

