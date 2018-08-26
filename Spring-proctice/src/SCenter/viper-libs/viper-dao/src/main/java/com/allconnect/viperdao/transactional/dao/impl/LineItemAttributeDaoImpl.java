package com.A.Vdao.transactional.dao.impl;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.A.V.beans.entity.LineItemAttribute;
import com.A.Vdao.dao.LineItemAttributeDao;

@Component("lineItemAttributeDao")
public class LineItemAttributeDaoImpl extends BaseTransactionalJpaDao implements LineItemAttributeDao {

    private static final Logger logger = Logger.getLogger(LineItemAttributeDaoImpl.class);

    @Transactional(value = "transactional", propagation = Propagation.REQUIRES_NEW)
    public void saveLineItemAttributes(Set<LineItemAttribute> liAttrubuteList) {
	logger.info("Saving LineItem Attributes.");
	long start = System.currentTimeMillis();
	try{
	if (liAttrubuteList != null) {
		logger.info("Looping all LineItemAttributes");
		for (LineItemAttribute liAttrib : liAttrubuteList) {
			if (liAttrib != null) {
				logger.info("LineItemAttributes "+liAttrib.toString());
				if (liAttrib.getSource().equalsIgnoreCase("StatusUpdate") && liAttrib.getName().equalsIgnoreCase("is_status_updated")) {
					logger.info("liAttrib: " + liAttrib.toString());
				}
				
				if (liAttrib.getId() == 0) {
					getEntityManager().persist(liAttrib);
					getEntityManager().flush();
				}
				else {
					getEntityManager().merge(liAttrib);
					getEntityManager().flush();
				}
			}

		}
	}
	}catch(Exception e){
		e.printStackTrace();
		logger.error("Error occured due to "+e.getStackTrace());
	}
	
	logger.info("LineItem attribute save took : " + (System.currentTimeMillis() - start) + "ms");
    }

}
