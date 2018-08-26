package com.A.ui.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.A.ui.dao.HughesNetServedUpDataDao;
import com.A.ui.dao.impl.BaseTransactionalJpaDao;
import com.A.ui.domain.HughesNetServedUpData;

@Component
public class HughesNetServedUpDataDaoImpl extends BaseTransactionalJpaDao implements HughesNetServedUpDataDao {

	private static final Logger logger = Logger.getLogger(HughesNetServedUpDataDaoImpl.class);

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public void insertHughesNetServedUpData(HughesNetServedUpData hughesNetServedUpData) {
		try {
			logger.info("Inserting_data_into_hughesnet_served_up_data_table");
			getEntityManager().persist(hughesNetServedUpData);
		} catch (Exception e) {
			logger.warn("Error_in_inserting_HughesNetServedUpData"+e.getMessage());
		}
	}
}
