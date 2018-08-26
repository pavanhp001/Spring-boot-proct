package com.A.ui.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.NoResultException;

import com.A.ui.dao.DialogDao;


@Component
public class DialogDaoImpl extends BaseTransactionalJpaDao implements
DialogDao {

	private static final Logger logger = Logger.getLogger(DialogDaoImpl.class);

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Long getDialogId(Long id) {

		Long dialogId = 0L;

		try {
			logger.info("Dialog_search");
			Object obj = getEntityManager().createQuery(
					"select d.dialogId FROM Dialog as d where id = "
							+ id.longValue()).getSingleResult();

			if (obj != null) {
				dialogId = (Long) obj;
			}
		} catch (NoResultException nre) {
			return dialogId;
		}

		return dialogId;
	}

}
