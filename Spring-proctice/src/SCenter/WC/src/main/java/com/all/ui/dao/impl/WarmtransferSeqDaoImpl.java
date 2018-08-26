package com.A.ui.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.A.ui.dao.WarmtransferSeqDao;

@Component(value = "warmtransferSeqDao")
public class WarmtransferSeqDaoImpl extends BaseTransactionalJpaDao implements WarmtransferSeqDao {
	private static final Logger logger = Logger.getLogger(WarmtransferSeqDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Long getUpdatedSequenceId() {
		Long sequenceId = 0l;
		try {
			logger.info("getUpdatedSequenceId");
			StringBuilder sb = new StringBuilder();
			sb.append("select wts.id FROM WarmTransferSeq wts");
			Query query = getEntityManager().createQuery(sb.toString());
			Object obj = query.getResultList();
			if (obj != null) {
				List<Long> WarmSeqNum = (List<Long>) obj;
				sequenceId = WarmSeqNum.get(0);
				logger.info("sequenceId ::"+sequenceId);
				String str = "UPDATE WarmTransferSeq SET id ="+(sequenceId+1);
				Query q = getEntityManager().createQuery(str);
				   q.executeUpdate();
			}
		} 
		catch (NoResultException nre) {
			logger.error(nre);
		}

		return sequenceId;
	}

}
