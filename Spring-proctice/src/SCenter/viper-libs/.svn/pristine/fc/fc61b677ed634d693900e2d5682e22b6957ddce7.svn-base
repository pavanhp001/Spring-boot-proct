package com.A.Vdao.transactional.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.A.V.beans.entity.LineItemDetail;
import com.A.Vdao.dao.LineItemDetailDao;

@Component
public class LineItemDetailDaoImpl extends BaseTransactionalJpaDao implements LineItemDetailDao {

    private static final Logger logger = Logger.getLogger(LineItemDetailDaoImpl.class);
    public LineItemDetailDaoImpl() {
	super();
    }

    public void persist(final LineItemDetail lineItemDetailBean) {
	logger.info("Executing lineitem detail save");
	long start = System.currentTimeMillis();
	if (lineItemDetailBean != null) {
	    getEntityManager().persist(lineItemDetailBean);
	}
	logger.info("LineItemDetail save took : " + (System.currentTimeMillis() - start) + "ms");
    }

    public void merge(final LineItemDetail lineItemDetailBean) {
	logger.info("Executing lineitem detail merge");
	long start = System.currentTimeMillis();
	if (lineItemDetailBean != null) {
	    getEntityManager().merge(lineItemDetailBean);
	}
	logger.info("LineItemDetail merge took : " + (System.currentTimeMillis() - start) + "ms");
    }

    public void remove(LineItemDetail lineItemDetailBean) {
	if (lineItemDetailBean != null) {
	    getEntityManager().remove(getEntityManager().getReference(LineItemDetail.class, lineItemDetailBean.getId()));
	}
    }

}
