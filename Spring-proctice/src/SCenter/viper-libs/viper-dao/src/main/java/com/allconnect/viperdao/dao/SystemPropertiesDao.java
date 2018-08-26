package com.A.Vdao.dao;
 
import java.util.List;
import com.A.V.beans.entity.SystemProperties;

/**
 * Base interface used by system properties
 * dao implementations.
 * 
 * @author klyons
 */
public interface SystemPropertiesDao //extends SyncDao {
{

    /**
     * Utility method to find System Properties by name
     * and context.
     * 
     * @param name
     * @param context
     * @return SystemProperties
     */
    public SystemProperties findByNameAndContext( final String name, final String context );
    
    /**
     * Utility method to find System Properties by context
     * 
     * @param context
     * @return SystemProperties
     */
    public List<SystemProperties> findByContext (final String context);
    
    /**
     * Utility method to load system properties from db
     */
    public void sync();
}
