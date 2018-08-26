package com.A.Vdao.transactional.dao.impl;

import java.util.HashMap;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.A.V.beans.entity.SystemProperties;
import com.A.Vdao.dao.SystemPropertiesDao;
import com.A.Vdao.util.SystemPropertiesRepo;

/**
 * @author klyons
 *
 */
@Component
public class SystemPropertiesDaoImpl extends BaseTransactionalJpaDao implements SystemPropertiesDao
{
    private static final String FIND_SYSPROPS_BY_NAME_CTX =
        "SELECT sysProp FROM SystemProperties sysProp WHERE sysProp.name = :name " +
        "and sysProp.context = :context";

    private static final String FIND_SYSPROPS_BY_CTX =
        "SELECT sysProp FROM SystemProperties sysProp WHERE sysProp.context = :context";

    private static final String FETCH_ALL_SYSPROPS =
            "SELECT sysProp FROM SystemProperties sysProp";

    private static final String NAME = "name";
    private static final String CONTEXT = "context";
    private static final Logger logger = Logger.getLogger( SystemPropertiesDaoImpl.class );


    /**
     * factory constructor.
     */
    public SystemPropertiesDaoImpl()
    {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public List<SystemProperties> findByContext (final String context)
    {
        if ( getEntityManager() != null )
        {
            try
            {
                Query query = getEntityManager().createQuery( FIND_SYSPROPS_BY_CTX );
                query.setParameter( CONTEXT,  context );
                List<SystemProperties> sysPropsList = (List<SystemProperties>) query.getResultList();

                return sysPropsList;
            }
            catch ( NoResultException nre )
            {
                logger.debug( nre.getMessage() );

            }
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    public SystemProperties findByNameAndContext( final String name, final String context )
    {
        if ( getEntityManager() != null )
        {
            try
            {
                Query query = getEntityManager().createQuery( FIND_SYSPROPS_BY_NAME_CTX );
                query.setParameter( NAME,  name );
                query.setParameter( CONTEXT,  context );
                SystemProperties sysProperties = (SystemProperties) query.getSingleResult();

                return sysProperties;

            }
            catch ( NoResultException nre )
            {
                logger.debug( nre.getMessage() );

            }
        }

        return null;
    }


    /**
     * {@inheritDoc}
     *
     */
    @SuppressWarnings( "unchecked" )
    public void sync()
    {
    	HashMap<String, SystemProperties> propsMap = new HashMap<String, SystemProperties>();

        if ( getEntityManager() != null )
        {
            try
            {
                Query query = getEntityManager().createQuery( FETCH_ALL_SYSPROPS );
                List<SystemProperties> sysPropsList = (List<SystemProperties>) query.getResultList();
                logger.debug("Total properties loaded : " + sysPropsList.size());
                String propertyName = null;
                String propertyContext = null;

                for ( SystemProperties systemProperty : sysPropsList )
                {
                    propertyName = systemProperty.getName();
                    propertyContext =  systemProperty.getContext();

                    if ( propertyName != null && propertyContext != null )
                    {
                    	logger.debug(propertyName + " : " + systemProperty.getValue());
                        propsMap.put( propertyContext + "." + propertyName, systemProperty );
                    }
                }

                // Update the In-Memory system properties &
                // make sure the DB actually returned valid records.
                if ( propsMap.size() > 0 )
                {
                    SystemPropertiesRepo.setSystemProperties( propsMap );
                }
                else
                {
                    String errMsg = "DB returned no system properties on re-sync.";
                    logger.warn( errMsg );
                }
                return;
            }
            catch ( Exception nre )
            {
                logger.error("Exception thrown while loading system properties " , nre);
            }
        }

        return;
    }

}
