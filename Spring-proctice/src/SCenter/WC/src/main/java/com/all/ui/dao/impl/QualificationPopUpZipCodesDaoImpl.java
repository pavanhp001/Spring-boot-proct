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
import com.A.ui.dao.QualificationPopUpZipCodesDao;
import com.A.ui.domain.QualificationPopUpZipCodes;



@Component
public class QualificationPopUpZipCodesDaoImpl extends BaseTransactionalJpaDao implements QualificationPopUpZipCodesDao {
	
	private static final Logger logger = Logger.getLogger(QualificationPopUpZipCodesDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Map<String, List<String>> getAllQualificationPopUpZipCodes() {
		
		Map<String, List<String>> providerZipCodeMap = new HashMap<String, List<String>>();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select qpz FROM QualificationPopUpZipCodes as qpz");
			Query query = getEntityManager().createQuery(sb.toString());
			List<QualificationPopUpZipCodes> providerZipCodeList = (List<QualificationPopUpZipCodes>)query.getResultList();
			if(providerZipCodeList != null){
				for(QualificationPopUpZipCodes qualificationPopUpZipCodes: providerZipCodeList){
					if(providerZipCodeMap.get(qualificationPopUpZipCodes.getProviderName()+"-"+qualificationPopUpZipCodes.getReferrers()) != null){
						providerZipCodeMap.get(qualificationPopUpZipCodes.getProviderName()+"-"+qualificationPopUpZipCodes.getReferrers()).add(qualificationPopUpZipCodes.getZipcode());
					}
					else{
						List<String> zipCodesList = new ArrayList<String>();
						zipCodesList.add(qualificationPopUpZipCodes.getZipcode());
						providerZipCodeMap.put(qualificationPopUpZipCodes.getProviderName()+"-"+qualificationPopUpZipCodes.getReferrers(), zipCodesList);
					}
				}
			}
		} catch (NoResultException nre) {
			logger.error(nre.getMessage());
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return providerZipCodeMap;
	}

}