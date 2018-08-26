package com.A.ui.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.A.ui.dao.FrontierPricingGridConfigDao;
import com.A.ui.domain.FrontierPricingGridConfig;
import com.A.ui.domain.FrontierProductDetails;

@Component
public class FrontierPricingGridConfigDaoImpl<T> extends BaseTransactionalJpaDao implements FrontierPricingGridConfigDao {
	
	private static final Logger logger = Logger.getLogger(FrontierPricingGridConfigDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Map<String, String> getFrontierPricingGridConfig() {
		Map<String, String> frontierPricingGridConfigMap = new HashMap<String, String>();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select fpgc FROM FrontierPricingGridConfig as fpgc");
			Query query = getEntityManager().createQuery(sb.toString());
			List<FrontierPricingGridConfig> frontierPricingGridConfigList = (List<FrontierPricingGridConfig>)query.getResultList();
			if(frontierPricingGridConfigList != null){
				for(FrontierPricingGridConfig frontierPricingGridConfig: frontierPricingGridConfigList){
					frontierPricingGridConfigMap.put(frontierPricingGridConfig.getState()+"-"+frontierPricingGridConfig.getType(), frontierPricingGridConfig.getFile());
				}
			}
		} catch (NoResultException nre) {
			logger.error(nre.getMessage());
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return frontierPricingGridConfigMap;
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Map<String, List<String>> getFrontierProductDetails() {
		Map<String, List<String>> frontierProductDetailsMap = new HashMap<String, List<String>>();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select fpd FROM FrontierProductDetails as fpd");
			Query query = getEntityManager().createQuery(sb.toString());
			List<FrontierProductDetails> frontierProductDetailsList = (List<FrontierProductDetails>)query.getResultList();
			List<String> fiberProductsList = new ArrayList<String>();
			List<String> broadbandProductsList = new ArrayList<String>();
			if(frontierProductDetailsList != null){
				for(FrontierProductDetails frontierProductDetails: frontierProductDetailsList){
					if(frontierProductDetails.getType()!= null && frontierProductDetails.getType().equalsIgnoreCase("Broadband")){
						broadbandProductsList.add(frontierProductDetails.getExternalId());
					}
					else{
						fiberProductsList.add(frontierProductDetails.getExternalId());
					}
				}
			}
			frontierProductDetailsMap.put("Broadband", broadbandProductsList);
			frontierProductDetailsMap.put("Fiber", fiberProductsList);
		} catch (NoResultException nre) {
			logger.error(nre.getMessage());
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return frontierProductDetailsMap;
	}

}