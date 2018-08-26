package com.AL.ui.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.AL.ui.dao.WebOrderDao;
import com.AL.ui.domain.WebOrder;

/**
 * @author mnagineni
 *
 */
@Component
public class WebOrderDaoImpl extends ScoreBaseTransactionalJpaDao implements WebOrderDao 
{
	private static final Logger logger = Logger.getLogger(WebOrderDaoImpl.class);
	
	/* (non-Javadoc)
	 * @see com.AL.ui.dao.WebOrderDao#saveOrder(com.AL.ui.domain.WebOrder)
	 */
	@Transactional(value = "transactional_score", propagation = Propagation.REQUIRED)
	public void saveOrder(WebOrder webOrder)
	{
		if (webOrder == null) 
		{
			return;
		}

		try 
		{
			getEntityManager().persist(webOrder);
			getEntityManager().flush();
		} catch (Exception e) 
		{
			logger.error("Error in saving the WebOrder data", e);
		}
	}
}
