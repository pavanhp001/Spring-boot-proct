/**
 *
 */
package com.A.Vdao.transactional.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.A.V.beans.entity.BillingInformation;
import com.A.Vdao.dao.AddressDao;
import com.A.Vdao.dao.BillingDao;

/**
 * @author ebthomas
 *
 */
@Component
public class BillingDaoImpl extends BaseTransactionalJpaDao implements BillingDao {

    private static final String NEXT_VAL = "SELECT nextval('CM_BILLING_INFO_BEAN_SEQ')";
    private static final Logger logger = Logger.getLogger(BillingDaoImpl.class);

    @Autowired
    private AddressDao addressDao;

    /**
     * factory constructor.
     */
    public BillingDaoImpl() {
	super();
    }

   /* public long getNextBillingInfoExternalId() {
	Query q = getEntityManager().createNativeQuery("SELECT CM_BILLING_INFO_BEAN_SEQ.nextval FROM dual");
	return DataTypeConverter.INSTANCE.objToLong(q.getResultList().get(0));
    }*/

    /**
     * @param em
     *            Entity Manager
     * @param billingBean
     *            Billing Information Bean
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void merge(final BillingInformation billingBean) {
	logger.info("Executing billing info merge");
	long start = System.currentTimeMillis();
	if (billingBean != null) {

	    if ((billingBean.getExternalId() == null) || (billingBean.getExternalId().equals(0L))) {
		billingBean.setExternalId(getNextExternalId());
		getEntityManager().persist(billingBean);
	    }
	    else {

		getEntityManager().merge(billingBean);
	    }
	}
	logger.info("Billing info merge took : " + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * @param em
     *            Entity Manager
     * @param billingBean
     *            Billing Information Bean
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void persist(final BillingInformation billingBean) {
	logger.info("Executing billing ingo save");
	long start = System.currentTimeMillis();
	if ((billingBean.getExternalId() == null) || (billingBean.getExternalId().equals(0L))) {

	    billingBean.setExternalId(getNextExternalId());
	    getEntityManager().persist(billingBean);
	}
	else {

	    getEntityManager().merge(billingBean);
	}
	logger.info("Billing info save took : " + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * @param statusRecordHistoryList
     *            Status history list with reasons to be removed
     * @param em
     *            Entity Manager that will perform remove operation
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void remove(final BillingInformation billingInformation) {
	if (billingInformation == null) {
	    return;
	}

	if (getEntityManager() == null) {
	    throw new IllegalArgumentException("Entity Manager should not be NULL");
	}

	/*
	 * AddressBean addressBean = billingInformation.getBillingAddress(); //addressDao.persist(addressBean); addressDao.remove( addressBean );
	 */

	// billingInformation.setBillingAddress(null);
	getEntityManager().remove(getEntityManager().getReference(BillingInformation.class, billingInformation.getId()));

    }

    public AddressDao getAddressDao() {
	return addressDao;
    }

    public void setAddressDao(AddressDao addressDao) {
	this.addressDao = addressDao;
    }

    /*public long getNextExternalId() {
	// TODO Need to be fixed as we are converting big decimal value to long
	// then we might loose
	// some data and might generate duplicate id for external id field
	Query q = getEntityManager().createNativeQuery(NEXT_VAL);
	return DataTypeConverter.INSTANCE.objToLong(q.getResultList().get(0));
    }*/

    public long getNextExternalId() {
  	  Session session = (Session)getEntityManager().getDelegate();
  	  Connection conn = ((SessionImpl)session).connection();
  	  PreparedStatement ps = null;
  	  ResultSet rs = null;
  	  long externalId = 0;
  	  try {
  	   ps = conn.prepareStatement(NEXT_VAL);
  	   rs = ps.executeQuery();
  	   rs.next();
  	   externalId = rs.getLong("NEXTVAL");
  	  } catch (SQLException e) {
  	   // TODO Auto-generated catch block
  	   e.printStackTrace();
  	  } finally {
  	   if(rs != null) {
  	          try {
  	           rs.close();
  	          } catch(SQLException e) {
  	           e.printStackTrace();
  	          }
  	      }
  	   
  	   if(ps != null) {
  	          try {
  	           ps.close();
  	          } catch(SQLException e) {
  	           e.printStackTrace();
  	          }
  	      }
  	  }
  	  return externalId;
  	 }

}
