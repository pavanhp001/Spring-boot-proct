package com.AL.ui.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.NoResultException;

import com.AL.ui.dao.ProviderTransferAgentGroupDao;
import com.AL.ui.vo.ProviderTransferAgentGroup;

@Component
public class ProviderTransferAgentGroupDaoImpl extends BaseTransactionalJpaDao implements ProviderTransferAgentGroupDao {

	private static final Logger logger = Logger.getLogger(ProviderTransferAgentGroupDaoImpl.class);

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public ProviderTransferAgentGroup getProviderTransferAgentGroups(String agentGroup) {

		ProviderTransferAgentGroup providerTransferAgentGroup = new ProviderTransferAgentGroup();
		
		try {
			logger.info("Search Provider transfer provider ids for agentGroup=" + agentGroup);
			Object obj = getEntityManager().createQuery("select ptag FROM ProviderTransferAgentGroup as ptag where agentGroup = '"
							+ agentGroup + "'").getSingleResult();

			if (obj != null) {
				providerTransferAgentGroup = (ProviderTransferAgentGroup) obj;
			}
		} 
		catch (NoResultException nre) {
			return providerTransferAgentGroup;
		}

		return providerTransferAgentGroup;
	}
	
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public boolean hasComcastGroup() {

		ProviderTransferAgentGroup providerTransferAgentGroup = new ProviderTransferAgentGroup();
		
		try {
			Object obj = getEntityManager().createQuery("select ptag FROM ProviderTransferAgentGroup as ptag where providerId = '26069942'").getSingleResult();

			if (obj != null) {
				providerTransferAgentGroup = (ProviderTransferAgentGroup) obj;
			}
		} 
		catch (NoResultException nre) {
			logger.info("noComcast");
			return false;
		}
		logger.info("hasComcast");
		return true;
	}
	
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public boolean hasATTV6Group() {

		ProviderTransferAgentGroup providerTransferAgentGroup = new ProviderTransferAgentGroup();
		
		try {
			Object obj = getEntityManager().createQuery("select ptag FROM ProviderTransferAgentGroup as ptag where providerId = '15500201'").getSingleResult();
			
			if (obj != null) {
				providerTransferAgentGroup = (ProviderTransferAgentGroup) obj;
			}
		} 
		catch (NoResultException nre) {
			logger.info("noATT");
			return false;
		}
		logger.info("hasATT");
		return true;
	}	
	
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public boolean hasATTGroup() {

		ProviderTransferAgentGroup providerTransferAgentGroup = new ProviderTransferAgentGroup();
		
		try {
			Object obj = getEntityManager().createQuery("select ptag FROM ProviderTransferAgentGroup as ptag where providerId = '24699452'").getSingleResult();
			
			if (obj != null) {
				providerTransferAgentGroup = (ProviderTransferAgentGroup) obj;
			}
		} 
		catch (NoResultException nre) {
			logger.info("noATT");
			return false;
		}
		logger.info("hasATT");
		return true;
	}	
	

}
