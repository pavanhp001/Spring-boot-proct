package com.A.Vdao.transactional.dao.impl;

import java.util.Calendar;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.A.V.beans.entity.VOrderMapping;
import com.A.Vdao.dao.VOrderMappingDao;

@Component
public class VOrderMappingDaoImpl extends BaseTransactionalJpaDao implements VOrderMappingDao {

    private static final Logger logger = Logger.getLogger(VOrderMappingDaoImpl.class);

    private final String FIND_MAPPING_BY_VPRORDNO_ORDEXT_LIEXT = "select v from VOrderMapping v where v.VOrderNo=? and v.orderExtId=? and v.liExtId=?";

    private final String UPDATE_PROVIDER_CONF_NUM = "update V_tx.om_line_item set provider_confirm_num=? where external_id=?";

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public Boolean save(VOrderMapping mapping) {
	logger.info("Saving rtim V order mapping : " + mapping.toString());
	long start = System.currentTimeMillis();
	try {
	    if (mapping.getId() == 0) {
		mapping.setDateCreated(Calendar.getInstance());
		getEntityManager().persist(mapping);
	    }
	    else {
		mapping.setDateCreated(Calendar.getInstance());
		getEntityManager().merge(mapping);
	    }
	    getEntityManager().flush();
	}
	catch (Exception e) {
	    logger.error("Exception while saving V order mapping", e);
	    e.printStackTrace();
	    return false;
	}
	logger.info("saving rtim V order mapping took : " + (System.currentTimeMillis() - start) + "ms");
	return true;
    }

    public VOrderMapping findByVOrderNoAndOrderExtIdAndLIExtId(String VOrderNo, Long orderExtId, Long liExtId) {
	logger.info("Searching V order mapping by VOrderNo [" + VOrderNo + "] OrderExtId [" + orderExtId + "] LI ExtId [" + liExtId + "]");
	long start = System.currentTimeMillis();
	if (getEntityManager() != null) {

	    try {

		Query query = getEntityManager().createQuery(FIND_MAPPING_BY_VPRORDNO_ORDEXT_LIEXT);
		query.setParameter(1, VOrderNo);
		query.setParameter(2, orderExtId);
		query.setParameter(3, liExtId);

		Object obj = query.getSingleResult();
		if (obj != null) {
		    logger.info("findByVOrderNoAndOrderExtIdAndLIExtId took : " + (System.currentTimeMillis() - start) + "ms");
		    return (VOrderMapping) obj;
		}

	    }
	    catch (NoResultException nre) {
		logger.warn("No entity found in rtim order mapping table");

	    }
	}
	logger.info("findByVOrderNoAndOrderExtIdAndLIExtId took : " + (System.currentTimeMillis() - start) + "ms");
	return null;
    }

    /**
     * Update provider number in lineitem table.
     */
    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public int updateProviderNumber(VOrderMapping mapping) {
	logger.info("Updating lineitem to update provider confirmation num field : " + mapping.toString());
	long start = System.currentTimeMillis();
	int i = 0;

	try {

	    Query q = getEntityManager().createNativeQuery(UPDATE_PROVIDER_CONF_NUM);
	    q.setParameter(1, mapping.getVOrderNo().trim());
	    q.setParameter(2, mapping.getLiExtId());

	    i = q.executeUpdate();
	    logger.debug("Total records updated : " + i);
	}
	catch (Exception e) {
	    logger.error("Exception while updating lineitem with provider conf no.", e);
	    e.printStackTrace();
	    return i;
	}
	logger.info("updateProviderNumber took : " + (System.currentTimeMillis() - start) + "ms");
	return i;
    }

}
