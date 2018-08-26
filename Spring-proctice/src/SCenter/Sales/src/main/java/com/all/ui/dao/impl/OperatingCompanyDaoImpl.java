package com.AL.ui.dao.impl;

import javax.persistence.NoResultException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.AL.ui.dao.OperatingCompanyDao;


@Component
public class OperatingCompanyDaoImpl extends BaseTransactionalJpaDao implements OperatingCompanyDao {

	private static final Logger logger = Logger.getLogger(OperatingCompanyDaoImpl.class);
	
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public String getOperatingCompanyName(String operatingCompanyCode) {

		String operatingCompanyName = "";

		try {
			logger.info("OperatingCompany_search");
			Object obj = getEntityManager().createQuery(
					"select oc.name FROM OperatingCompany as oc where code = "+"'"
							+ operatingCompanyCode+"'").getSingleResult();

			if (obj != null) {
				operatingCompanyName = (String) obj;
			}
		} catch (NoResultException nre) {
			return operatingCompanyName;
		}

		return operatingCompanyName;
	}
}
