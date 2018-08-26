package com.A.ui.dao.impl;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.A.ui.dao.WarmtransferTpvProvidersDao;
import com.A.ui.domain.WarmtransferTpvRepo;
import com.A.ui.vo.WarmtransferTpvProvidersVO;


@Component
public class WarmtransferTpvProvidersDaoImpl extends BaseTransactionalJpaDao implements WarmtransferTpvProvidersDao {

	private static final Logger logger = Logger.getLogger(WarmtransferTpvProvidersDaoImpl.class);

	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public void syncWarmtransferTpvProviders() {
		if (getEntityManager() != null) {
			try {
				StringBuilder sb = new StringBuilder();
				sb.append("select wtTpvVO FROM WarmtransferTpvProvidersVO as wtTpvVO");
				Query q = getEntityManager().createQuery(sb.toString());
				List<WarmtransferTpvProvidersVO> wtTpvList = (List<WarmtransferTpvProvidersVO>) q.getResultList();
				if (wtTpvList != null) {
					WarmtransferTpvRepo.setWarmTransferTpvProviders(wtTpvList);
				}
			} catch (NoResultException nre) {
				logger.debug(nre.getMessage());
			}
		}
	}
}
