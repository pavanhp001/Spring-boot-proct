package com.A.ui.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.NoResultException;

import com.A.ui.dao.WebflowDao;
import com.A.ui.domain.Webflow;

@Component
public class WebflowDaoImpl extends BaseTransactionalJpaDao implements
		WebflowDao {

	private static final Logger logger = Logger.getLogger(WebflowDaoImpl.class);

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public String getPath(Long webflowId) {

		String webflowPath = "";

		try {
			logger.info("Webflow_search");
			Object obj = getEntityManager().createQuery(
					"select wf.webflowPath FROM Webflow as wf where webflowId = "
							+ webflowId.longValue()).getSingleResult();

			if (obj != null) {
				webflowPath = (String) obj;
			}
		} catch (NoResultException nre) {
			return webflowPath;
		}

		return webflowPath;
	}

}
