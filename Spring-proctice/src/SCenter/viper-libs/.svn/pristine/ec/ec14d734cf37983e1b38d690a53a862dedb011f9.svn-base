package com.A.Vdao.transactional.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.A.Vdao.dao.AuditDao;

/**
 * @author ebthomas
 *
 * @param <T>
 */

@Component
public class AuditDaoImpl<T> extends BaseTransactionalJpaDao implements AuditDao<T>
{
    public static final Logger LOGGER = Logger.getLogger( AuditDaoImpl.class );

    /**
    *
    */
    public AuditDaoImpl()
    {
        super();

    }

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED )
    public Boolean audit( final T auditable )
    {

    	LOGGER.info("disable auditing");

//        try
//        {
//
//            getEntityManager().persist( auditable );
//
//            getEntityManager().flush();
//
//            return Boolean.TRUE;
//        }
//        catch ( Exception e )
//        {
//            LOGGER.error( e );
//
//        }

        return Boolean.FALSE;

    }

}
