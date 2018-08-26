package com.A.ui.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.NoResultException;

import com.A.ui.dao.CustomerDiscoveryDao;
import com.A.ui.domain.CustomerDiscovery;

@Component
public class CustomerDiscoveryDaoImpl extends BaseTransactionalJpaDao implements
CustomerDiscoveryDao {

	private static final Logger logger = Logger
			.getLogger(CustomerDiscoveryDaoImpl.class);

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public void insert(Long orderId, Long customerId, Map<String, String> customerDiscoveryInsertMap){
		logger.info("CustomerDiscovery insert");
		try {
			for (Map.Entry<String, String> entry : customerDiscoveryInsertMap.entrySet())
			{
				CustomerDiscovery customerDiscovery = CustomerDiscovery.create(orderId, customerId, entry.getKey(), entry.getValue());
				getEntityManager().persist(customerDiscovery);
				getEntityManager().flush();
			}
		} catch (Exception e) {
			logger.warn("Error CustomerDiscovery",e);
		}
	}
	

}
