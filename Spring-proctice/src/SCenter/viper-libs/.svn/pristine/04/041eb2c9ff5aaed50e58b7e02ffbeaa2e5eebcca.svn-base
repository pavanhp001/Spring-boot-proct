package com.A.task;

import javax.persistence.EntityManager;

/**
 * @author ebthomas
 * 
 * @param <T>
 */
public interface Task<T>
{
    /**
     * @param doc xml document that will be parsed.
     * @return return processed document
     */
    T execute( T doc );

    /**
     * @return EntityManager
     */
    EntityManager getEntityManagerLogical();

    /**
     * @param entityManagerLogical setter for EntityManager
     */
    void setEntityManagerLogical( final EntityManager entityManagerLogical );

    /**
     * @return EntityManager
     */
    EntityManager getEntityManagerCache();

    /**
     * @param entityManagerCache setter for EntityManager
     */
    void setEntityManagerCache( final EntityManager entityManagerCache );

    /**
     * @return EntityManager
     */
    EntityManager getEntityManagerReference();

    /**
     * @param entityManagerReference setter for EntityManager
     */
    void setEntityManagerReference( final EntityManager entityManagerReference );
}
