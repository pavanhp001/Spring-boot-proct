package com.A.ui.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.A.ui.dao.CoxZipcodesDao;



@Component
public class CoxZipcodesDaoImpl<T> extends BaseTransactionalJpaDao implements CoxZipcodesDao {
	
	private static final Logger logger = Logger.getLogger(GrossCommissionableRevenueDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public List<String> getAllZipcodes() {

		List<String> zipcodes = new ArrayList<String>();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select cz.zipcode FROM CoxZipcodes as cz");
			Query query = getEntityManager().createQuery(sb.toString());
			List<Integer> zipcodeList = (List<Integer>)query.getResultList();
			for(Integer zip: zipcodeList){
				if(zip != null){
					zipcodes.add(zip.toString());
				}
			}
		} catch (NoResultException nre) {
			logger.error(nre.getMessage());
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return zipcodes;
	}

}