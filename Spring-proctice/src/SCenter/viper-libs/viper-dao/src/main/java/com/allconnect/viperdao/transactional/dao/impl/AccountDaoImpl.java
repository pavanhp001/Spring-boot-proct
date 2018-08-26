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

import com.A.V.beans.entity.Account;
import com.A.V.beans.entity.Consumer;
import com.A.Vdao.dao.AccountDao;
import com.A.Vdao.dao.CustomerDao;

/**
 * @author ebthomas
 *
 */
@Component
public class AccountDaoImpl extends BaseTransactionalJpaDao implements AccountDao {

    private static final String NEXT_VAL = "SELECT nextval('CM_ACCOUNT_SEQ')";

    private static final Logger logger = Logger.getLogger(AccountDaoImpl.class);

    @Autowired
    private CustomerDao customerDao;

    /**
     * factory constructor.
     */
    public AccountDaoImpl() {
	super();
    }

    /*public long getNextAccountExternalId() {
	Query q = getEntityManager().createNativeQuery("SELECT CM_ACCOUNT_SEQ.nextval FROM dual");

	return DataTypeConverter.INSTANCE.objToLong(q.getResultList().get(0));

    }*/

    private void verifyConsumer(final Account account) {

	if (account.getConsumerExtId() != null) {
	    Long consumerExtId = Long.valueOf(account.getConsumerExtId());

	    if (account.getConsumer() == null) {
		Consumer updatedConsumer = customerDao.findCustomerByExternalId(consumerExtId);
		account.setConsumer(updatedConsumer);
	    }
	}

    }

    /**
     * @param em
     *            Entity Manager
     * @param billingBean
     *            Billing Information Bean
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void merge(final Account account) {
	logger.info("Executing account merge");
	long start = System.currentTimeMillis();
	if (account != null) {

	    verifyConsumer(account);

	    if ((account.getExternalId() == null) || (account.getExternalId().equals(0L))) {
		account.setExternalId(getNextExternalId());
		getEntityManager().persist(account);
	    }
	    else {

		getEntityManager().merge(account);
	    }
	}
	logger.info("Account merge took : " + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * @param em
     *            Entity Manager
     * @param billingBean
     *            Billing Information Bean
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void persist(final Account account) {
	logger.info("Executing account save");
	long start = System.currentTimeMillis();
	verifyConsumer(account);
	if ((account.getExternalId() == null) || (account.getExternalId().equals(0L))) {
	    account.setExternalId(getNextExternalId());
	    getEntityManager().persist(account);
	}
	else {

	    getEntityManager().merge(account);
	}
	logger.info("Account save took : " + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * @param statusRecordHistoryList
     *            Status history list with reasons to be removed
     * @param em
     *            Entity Manager that will perform remove operation
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void remove(final Account account) {
	if (account == null) {
	    return;
	}

	if (getEntityManager() == null) {
	    throw new IllegalArgumentException("Entity Manager should not be NULL");
	}

	getEntityManager().remove(getEntityManager().getReference(Account.class, account.getId()));

    }

    /*public long getNextExternalId() {
	// TODO Need to be fixed as we are converting big decimal value to long
	// then we might loose
	// some data and might generate duplicate id for external id field
	Query q = getEntityManager().createNativeQuery(NEXT_VAL);
	return DataTypeConverter.INSTANCE.objToLong(q.getResultList().get(0));
    }
*/
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
