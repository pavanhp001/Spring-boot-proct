package com.A.Vdao.logical.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.A.Vdao.dao.ProviderConfigDao;
import com.A.Vdao.util.ProviderConfigVO;

@Component
public class ProviderConfigDaoImpl extends LogicalJpaDao implements ProviderConfigDao {
	
	private static Logger logger = Logger.getLogger(ProviderConfigDaoImpl.class);
	
    public static final String PROVIDER_NAME = "PROVIDER_NAME";
    public static final String QUEUE_NAME = "QUEUE_NAME";
	
	public Map<String, ProviderConfigVO> getRTIMProviderConfig() {
		logger.info("Getting RTIM provider config from database...");
		Map<String, ProviderConfigVO> rtProviderConfigMap = new HashMap<String, ProviderConfigVO>();
		// query adapter IDs
		Query q1 = entityManager
				.createQuery("select ra.id from RTIMAdapterBean ra "
						+ "where ra.active = true");
		List<?> rows = q1.getResultList();
		for (Object adapterid : rows) {
			// for each adapter ID query config from rtim_adapter_config
			// (only PROVIDERID, PARENT_PROVIDER)
			logger.info("Pulled adapterid " + adapterid);
			Query q2 = entityManager.createQuery("select rac.key, rac.value, "
					+ "rac.adapter.datasource, rac.adapter.providerExternalId "
					+ "from RTIMAdapterConfigBean rac "
					+ "where rac.adapter.id = :aid "
					+ "and rac.key in ("
					+ "'" + PROVIDER_NAME + "','" + QUEUE_NAME + "')");

			q2.setParameter("aid", adapterid);
			List<?> configRows = q2.getResultList();
			if(configRows == null) 
			{
				logger.info("Unable to load config for adapterID " + adapterid + ". Integration will be unavailable.");
				continue;
			}

			String providerName = null;
			String queueName = null;
			String providerExtID = null;
			String datasource = null;
			
			for (Object configRow : configRows) 
			{
				Object[] configArray = (Object[]) configRow;
				if (PROVIDER_NAME.equals(configArray[0])) {
					providerName = (String) configArray[1];
				} else if (QUEUE_NAME.equals(configArray[0])) {
					queueName = (String) configArray[1];
				}
				if (datasource == null) {
					datasource = (String) configArray[2];
				}
				if (providerExtID == null) {
					providerExtID = (String) configArray[3];
				}
			}

			// if PROVIDER_NAME key wasn't found, fall back on datasource
			if (providerName == null) {
				providerName = datasource;
			}
			rtProviderConfigMap.put(providerExtID, new ProviderConfigVO(
					providerName, queueName));
			logger.info("RtProviderConfigMap. ProviderExtId: " + providerExtID
					+ ". " + ", QueueName: "
					+ rtProviderConfigMap.get(providerExtID).getQueueName()
					+ ", ProviderName: "
					+ rtProviderConfigMap.get(providerExtID).getProviderName()
					);
		}
		
		return rtProviderConfigMap;
	}

}
