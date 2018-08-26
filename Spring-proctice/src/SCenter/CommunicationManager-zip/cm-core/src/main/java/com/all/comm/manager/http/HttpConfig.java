/**
 *
 */
package com.AL.comm.manager.http;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.log4j.Logger;

/**
 * @author ebthomas
 * 
 */
public enum HttpConfig
{

    INSTANCE;

    //Logger logger = Logger.getLogger( HttpConfig.class );
    private InitialContext initialContext;
    protected static boolean connected = false;

    Map<String, Object> cache = new ConcurrentHashMap<String, Object>();

    private HttpConfig()
    {
        initialize();
    }

    public void initialize()
    {
        try
        {
            if ( initialContext == null )
            {
                initialContext = new InitialContext();
            }
        }
        catch ( NamingException e )
        {
            //logger.error( "unable to create Initial Context. Running outside container", e );
            initialContext = null;
        }
    }

    public Object lookup( final String key )
    {
        Object obj = cache.get( key );

        if ( ( obj == null ) && ( initialContext != null ) )
        {
            try
            {
                obj = initialContext.lookup( key );
                cache.put( key, obj );
            }
            catch ( NamingException e )
            {
                //logger.error( "unable to locate object in cache or JNDI lookup", e );
            }
        }

        return obj;
    }

    public Object get( final String key )
    {
        Object obj = null;

        if ( cache != null )
        {
            obj = cache.get( key );

            if ( obj == null )
            {
                obj = lookup( key );

                if ( obj != null )
                {
                    cache.put( key, obj );
                }
            }
        }

        return obj;
    }

    public void put( final String key, final Object value )
    {
        if ( ( cache != null ) )
        {
            cache.put( key, value );
        }
    }

    public void remove( final String key )
    {
        if ( cache != null )
        {
            cache.remove( key );
        }
    }

    public Map<String, Object> getCache()
    {
        return cache;
    }

    public void setCache( Map<String, Object> cache )
    {
        this.cache = cache;
    }

    public InitialContext getInitialContext()
    {
        if ( initialContext == null )
        {
            synchronized ( this )
            {
                initialize();
            }
        }

        return initialContext;

    }

    public boolean isRunningInContainer()
    {
        try
        {
            if ( initialContext == null )
            {
                initialContext = new InitialContext();
            }

            return ( initialContext.lookup( "java:comp/env" ) != null );
        }
        catch ( NamingException ex )
        {
            return Boolean.FALSE;
        }
    }
}
