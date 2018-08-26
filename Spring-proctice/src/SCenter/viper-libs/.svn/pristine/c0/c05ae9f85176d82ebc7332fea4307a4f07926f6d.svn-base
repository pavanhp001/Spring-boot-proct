/**
 *
 */
package com.A.Vdao.dao.impl;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.A.Vdao.dao.SyncDao;
import com.A.Vdao.logical.dao.impl.LogicalJpaDao;
/**
 * @author ebthomas
 * 
 */
@Component
public class SyncDaoImpl extends LogicalJpaDao implements SyncDao
{
    private static final String TIME_SYNC = "select now()::timestamp as SYSDATE";

    private static final Logger logger = Logger.getLogger( SyncDaoImpl.class );

    /**
     * factory constructor.
     */
    public SyncDaoImpl()
    {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public void sync()
    {

        if ( getEntityManager() != null )
        {

            try
            {

                Query query = getEntityManager().createNativeQuery( TIME_SYNC );

                Object obj = query.getSingleResult();
                logger.debug("time sync result:"+ obj );

            }
            catch ( NoResultException nre )
            {
                logger.debug( nre.getMessage() );

            }
            catch ( Exception e )
            {
                logger.debug( e );

            }
        }

    }

}
