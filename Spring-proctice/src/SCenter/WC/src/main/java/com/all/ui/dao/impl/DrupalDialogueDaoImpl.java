package com.A.ui.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.A.ui.dao.DrupalDialogueDao;
import com.A.ui.domain.DrupalDialogueEntity;

@Component
public class DrupalDialogueDaoImpl extends BaseTransactionalJpaDao implements
		DrupalDialogueDao {
	private static final Logger logger = Logger.getLogger(DrupalDialogueDaoImpl.class);

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public DrupalDialogueEntity get(String refferFlowType) 
	{
		DrupalDialogueEntity drupalDailgEntity = null;
		try 
		{
			logger.info("Get_DrupalDialogueEntity_based_on_flowType_"+ refferFlowType);
			drupalDailgEntity = getEntityManager().find(DrupalDialogueEntity.class, refferFlowType);
		} 
		catch (Exception e) 
		  {
			logger.error("Error_occured_while_get_DrupalDialogueEntity_based_on_flowType",e);
		  }
		return drupalDailgEntity;
	}

}
