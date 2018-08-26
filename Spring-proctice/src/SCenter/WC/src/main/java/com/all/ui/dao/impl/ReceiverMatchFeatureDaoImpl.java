package com.A.ui.dao.impl;

import java.util.List;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.A.ui.dao.ReceiverMatchFeatureDao;
import com.A.ui.domain.ReceiverMatchFeature;
import com.A.xml.v4.Receiver;


@Component
public class ReceiverMatchFeatureDaoImpl extends BaseTransactionalJpaDao implements ReceiverMatchFeatureDao{

	private static final Logger logger =  Logger.getLogger(ReceiverMatchFeatureDaoImpl.class);


	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public List<ReceiverMatchFeature> getAllReceiverMatchFeature() {
		logger.info("getting featureExternalId from ReceiverMatchFeature table");

		try {
			if (getEntityManager() != null) {
				Query query = getEntityManager().createQuery("from ReceiverMatchFeature as rmf");
				List<ReceiverMatchFeature> receiverslist = query.getResultList();
				if (receiverslist != null && !receiverslist.isEmpty()) {
					
					return receiverslist;
				}
				
			}
		} catch (Exception e) {
			logger.error("error_occured_while_getAllReceiverMatchFeature", e);
		}
		return null;
	}
}
