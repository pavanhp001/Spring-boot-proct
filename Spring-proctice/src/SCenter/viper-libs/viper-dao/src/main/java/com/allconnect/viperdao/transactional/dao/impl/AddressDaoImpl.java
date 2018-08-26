/**
 *
 */
package com.A.Vdao.transactional.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.A.V.beans.entity.AddressBean;
import com.A.V.beans.entity.Consumer;
import com.A.V.beans.entity.CustomerAddressAssociation;
import com.A.Vdao.dao.AddressDao;

/**
 * @author ebthomas.
 *
 */
@Component
public class AddressDaoImpl extends BaseTransactionalJpaDao implements AddressDao {

    private static final Logger logger = Logger.getLogger(AddressDaoImpl.class);
    private static final String FIND_BY_ADDRESS_ID = "SELECT a FROM AddressBean a WHERE a.externalId = ?";
    private static final String NEXT_VAL = "SELECT nextval('CM_ADDRESS_BEAN_SEQ')";

    private static final String FIND_CUST_WITH_ADDR_BY_EXT_ID = " SELECT c FROM Consumer c " + " LEFT JOIN FETCH c.addresses assoc" + " WHERE c.externalId = ? ";

    private static final List<AddressBean> DEFAULT_EMPTY_MAP = new ArrayList<AddressBean>();

    /**
     * factory constructor.
     */
    public AddressDaoImpl() {
	super();
    }

    /**
     * Find consumer by external id
     */
    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public List<AddressBean> findAddressByCustomerId(String id) {
	logger.info("Executing findAddressByCustomerId");
	long start = System.currentTimeMillis();
	StringBuilder sb = new StringBuilder(FIND_CUST_WITH_ADDR_BY_EXT_ID);
	Map<Long, Long> processed = new HashMap<Long, Long>();

	logger.debug("finding address by customer id: " + id);
	Query q = getEntityManager().createQuery(sb.toString());
	q.setParameter(1, Long.valueOf(id));
	Consumer consumer = (Consumer) q.getSingleResult();

	if (consumer == null) {
	    return DEFAULT_EMPTY_MAP;
	}

	for (CustomerAddressAssociation ca : consumer.getAddresses()) {

	    if ((ca != null) && (ca.getAddress() != null) && (!processed.containsKey(ca.getAddress().getExternalId()))) {

		consumer.getConsumerAddressList().add(ca.getAddress());
		processed.put(ca.getAddress().getExternalId(), ca.getAddress().getExternalId());
	    }

	    ca.getAddress().getAddressRoles().add(ca.getAddressRole());
	}

	List<AddressBean> objList = consumer.getConsumerAddressList();
	logger.info("findAddressByCustomerId took : " + (System.currentTimeMillis() - start) + "ms");
	return objList;

    }

    /**
     * Find consumer by external id
     */
    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public AddressBean findAddressById(String id) {
	logger.info("finding address by external id: " + id);
	long start = System.currentTimeMillis();
	Query q = getEntityManager().createQuery(FIND_BY_ADDRESS_ID);
	q.setParameter(1, Long.valueOf(id));
	List objList = q.getResultList();
	 AddressBean address = null;
	if (objList != null && objList.size() > 0) {
	    logger.info("found address in db ");
	    address = (AddressBean) objList.get(0);
	}
	logger.info("findAddressById took : " + (System.currentTimeMillis() - start) + "ms");
	return address;
    }

    /**
     * @param em
     *            Entity Manager
     * @param addressBean
     *            Address Bean
     */
    public void persist(final AddressBean addressBean) {
	logger.info("Executing address save");
	long start = System.currentTimeMillis();
	if (addressBean != null) {
	    if (addressBean.getInEffect() == null) {
		Calendar inEffect = Calendar.getInstance();
		addressBean.setInEffect(inEffect);
	    }
	    getEntityManager().persist(addressBean);
	}
	logger.info("Address save took : " + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * @param em
     *            Entity Manager
     * @param addressBean
     *            Address Bean
     */
    public void merge(final AddressBean addressBean) {
	logger.info("Executing address merge");
	long start = System.currentTimeMillis();
	if (addressBean != null) {

	    if (addressBean.getId() != 0) {
		getEntityManager().merge(addressBean);
		getEntityManager().flush();

	    }
	    else {
		getEntityManager().persist(addressBean);
		getEntityManager().flush();
	    }
	}
	getEntityManager().flush();
	logger.info("Address merge took : " + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * @param em
     *            Entity Manager
     * @param addressBean
     *            Address Bean
     */
    public void remove(final AddressBean addressBean) {
	if (addressBean != null) {
	    getEntityManager().remove(getEntityManager().getReference(AddressBean.class, addressBean.getId()));
	}
    }

    /*public long getNextExternalId() {
	// TODO Need to be fixed as we are converting big decimal value to long
	// then we might loose
	// some data and might generate duplicate id for external id field
	Query q = getEntityManager().createNativeQuery(NEXT_VAL);

	Object obj = q.getResultList().get(0);
	return DataTypeConverter.INSTANCE.objToLong(obj);

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
