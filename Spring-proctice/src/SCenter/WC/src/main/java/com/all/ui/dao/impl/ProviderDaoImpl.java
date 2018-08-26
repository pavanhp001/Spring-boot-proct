package com.A.ui.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.A.ui.dao.ProviderDao;
import com.A.ui.domain.Provider;

@Component
public class ProviderDaoImpl extends BaseTransactionalJpaDao implements
		ProviderDao {

	private static final Logger logger = Logger
			.getLogger(ProviderDaoImpl.class);

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Provider get(Long providerId) {

		Provider provider = null;

		logger.info("provider_search");
		Object obj = getEntityManager().createQuery(
				"select p FROM Provider as p where providerId = "
						+ providerId.longValue()).getSingleResult();

		if ((obj != null)&&(obj instanceof Provider)) {
			provider = (Provider) obj;

		}

		return provider;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public List<Provider> getProviders(){
		
		List<Provider> providerList = new ArrayList<Provider>();
		Object obj = getEntityManager().createQuery("select p FROM Provider as p").getResultList();
		
		if (obj != null) {
			providerList = (List<Provider>) obj;
		}

		return providerList;
	}
}
