/**
 *
 */
package com.A.Vdao.logical.dao.impl;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.A.V.beans.entity.AgentBean;
import com.A.Vdao.dao.AgentDao;

/**
 * @author ebthomas
 *
 */
@Component
public   class AgentDaoImpl extends LogicalJpaDao implements AgentDao
{
    private static final String FIND_AGENT_BY_ID = "SELECT ab FROM AgentBean ab WHERE ab.externalId = :externalId ";
    private static final String AGENT_ID = "externalId";
    private static final Logger logger = Logger.getLogger( AgentDaoImpl.class );
    
     
    /**
     * factory constructor.
     */
    public AgentDaoImpl()
    {
        super();
    }
    
    
    /**
     * {@inheritDoc}
     */
    public   AgentBean findAgentById(   final String id )
    {

        if ( getEntityManager() != null )
        {

            try
            {

                Query query = getEntityManager().createQuery( FIND_AGENT_BY_ID );
                query.setParameter( AGENT_ID,  id  );
                Object obj = query.getSingleResult();
 
                if ( ( obj != null ) && ( obj instanceof AgentBean ) )
                {
                    return (AgentBean) obj;
                } 
            }
            catch ( NoResultException nre )
            {
                logger.debug( nre.getMessage() );

            }
        }

        return null;

    }


	 
    
}
