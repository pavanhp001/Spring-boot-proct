package com.A.Vdao.logical.dao.impl;

import java.util.Calendar;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import com.A.V.beans.entity.BundleBean;
import com.A.Vdao.dao.BundleDao;

@Component
public class BundleDaoImpl extends LogicalJpaDao implements BundleDao {
	private static final String FIND_BUNDLE_BY_ID = "SELECT b FROM BundleBean b WHERE b.externalId = :externalId";
	
	private static final Logger logger = Logger.getLogger( BundleDaoImpl.class );
	public BundleBean findBundleById( final String externalId) {
	    logger.info( "Searching for BundleItem External ID : " + externalId );
		if (getEntityManager() != null) {
			try {
				Query query = getEntityManager().createQuery(FIND_BUNDLE_BY_ID);
				query.setParameter("externalId", externalId);
				
				List<BundleBean> biList = query.getResultList();

                // Logic to check wheather the item is in effect if there is multiple item in db and and only one is active then
                // return
                // the active one based on todays date which should be between ineffect and expiration
                if ( biList != null )
                {
                    logger.info( "No of BundleItem found based on External ID : " + biList.size() );
                    for ( BundleBean bundleBean : biList )
                    {
                        Calendar currentDate = Calendar.getInstance();
                        logger.info( "Checking for active BundleItem in the list based on todays date..... " );
                        Calendar inEffect = bundleBean.getInEffect();
                        if ( currentDate.after( inEffect ) )
                        {
                            if ( bundleBean.getExpiration() != null )
                            {
                                Calendar expirationDate = bundleBean.getExpiration();
                                logger.info( "Exp Date :" + expirationDate.get( Calendar.YEAR )+"-"+ (expirationDate.get( Calendar.MONTH )+1)  + "-" + expirationDate.get( Calendar.DAY_OF_MONTH ));
                                if ( currentDate.before( expirationDate ) )
                                {
                                    logger.info( "Found one active BundleItem which is active between InEffect and Expiration dates..... "
                                            + bundleBean.getDescription() );
                                    return bundleBean;
                                }
                            }
                            //This part will never reach as in MarketItemBean we set the expiration a year after current date if
                            //expiration date is set to null. So we will always have expiration date.
                            //Code needs to here if in future we modify the VBeans to set the expiration to null then this will
                            //Take care of it
                            else 
                            {
                                logger.info( "Found one active BundleItem with NULL expiraiton date and InEffect date is after current date..... "
                                        + bundleBean.getDescription() );
                                return bundleBean;
                            }

                        }
                    }
                }
				/*Object obj = query.getSingleResult();
				if ((obj != null) && (obj instanceof BundleBean)) {
					return (BundleBean) obj;
				}*/
			} catch (NoResultException nre) {
				//LogUtil.log(LogLevelEnum.info, LogUtil.NULL_JBOSS_LOGGER, nre.getMessage());
				logger.error("NoResultException thrown...", nre);
			}
		}

		return null;
	}

}
