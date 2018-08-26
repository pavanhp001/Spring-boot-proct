package com.A.Vdao.transactional.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.A.V.beans.entity.CustomerInteraction;
import com.A.V.beans.entity.User;
import com.A.Vdao.dao.CustomerInteractionDao;
import com.A.Vdao.dao.UserDao;

@Component
public class CustomerInteractionDaoImpl extends BaseTransactionalJpaDao implements CustomerInteractionDao {

    private Logger logger = Logger.getLogger(CustomerInteractionDaoImpl.class);

    @Autowired
    private UserDao agentDao;

    private static final String FIND_CI_BY_CUST_ID = "SELECT  ci.agent_ext_id, ci.customer_external_id, c.firstname , "
	    + "c.lastname, ci.interaction_on, ci.notes, ci.order_ext_id, ci.source, ci.LI_EXT_ID, ci.EXTERNAL_ID, ci.PROVIDER_ID, ci.SERVICE_TYPE " + "FROM CM_CUSTOMER_INTERACTION ci "
	    + "LEFT JOIN CM_CONSUMER c on ci.CUSTOMER_EXTERNAL_ID = c.EXTERNAL_ID " + "WHERE ci.CUSTOMER_EXTERNAL_ID = ? " + "ORDER BY ci.INTERACTION_ON DESC";

    private static final String FIND_CI_BY_LINEITEM_ID = "SELECT   ci.agent_ext_id, ci.customer_external_id, c.firstname , "
	    + " c.lastname, ci.interaction_on, ci.notes, ci.order_ext_id, ci.source, ci.LI_EXT_ID, ci.EXTERNAL_ID, ci.PROVIDER_ID, ci.SERVICE_TYPE " + " FROM CM_CUSTOMER_INTERACTION ci "
	    + " LEFT JOIN CM_CONSUMER c on ci.CUSTOMER_EXTERNAL_ID = c.EXTERNAL_ID " + " WHERE ci.LI_EXT_ID = ?    " + "ORDER BY ci.INTERACTION_ON DESC";

    private static final String FIND_CI_BY_ORDER_ID = "SELECT   ci.agent_ext_id, ci.customer_external_id, c.firstname , "
	    + " c.lastname, ci.interaction_on, ci.notes, ci.order_ext_id, ci.source, ci.LI_EXT_ID, ci.EXTERNAL_ID, ci.PROVIDER_ID, ci.SERVICE_TYPE " + " FROM CM_CUSTOMER_INTERACTION ci "
	    + " LEFT JOIN CM_CONSUMER c on ci.CUSTOMER_EXTERNAL_ID = c.EXTERNAL_ID " + " WHERE ci.order_ext_id = ?    " + "ORDER BY ci.INTERACTION_ON DESC";

    private static final String FIND_CI_BY_AGENT_ID = "SELECT   ci.agent_ext_id, ci.customer_external_id, c.firstname, "
	    + "c.lastname, ci.interaction_on, ci.notes, ci.order_ext_id, ci.source, ci.LI_EXT_ID, ci.EXTERNAL_ID, ci.PROVIDER_ID, ci.SERVICE_TYPE  " + "FROM CM_CUSTOMER_INTERACTION ci "
	    + "LEFT JOIN CM_CONSUMER c on ci.CUSTOMER_EXTERNAL_ID = c.EXTERNAL_ID " + "WHERE ci.AGENT_EXT_ID = ? " + "ORDER BY ci.INTERACTION_ON DESC";

    private static final String FIND_CI_BY_EXT_ID = "SELECT ci FROM CustomerInteraction ci WHERE ci.externalId = ?";

    private static final String NEXT_VAL = "SELECT nextval('CUSTOMER_INTER_SEQ')";

    private static final String GET_LINEITEM_CATEGORY = "SELECT  om_line_item_dtl.category as category " +
	    					      "FROM  V_tx.om_line_item,  V_tx.om_line_item_dtl " +
	    					      "WHERE om_line_item.lineitem_dtl_id = om_line_item_dtl.id AND om_line_item.external_id=?";

    /**
     * Method to save Consumer Interaction
     */
    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public void saveConsumerInteraction(CustomerInteraction ci) {
	logger.debug("Saving Consumer Interaction");
	long start = System.currentTimeMillis();

	if(null == ci.getServiceType() && null != ci.getLineItemExternalId()  && ci.getLineItemExternalId() > 0) {
	    logger.info("Retrieving service type for lineitem : " + ci.getLineItemExternalId());
	    Query q = getEntityManager().createNativeQuery(GET_LINEITEM_CATEGORY);
	    q.setParameter(1, ci.getLineItemExternalId());
	    String result = (String)q.getSingleResult();
	    if(result != null && result.trim().length() > 0) {
		ci.setServiceType(result);
	    }
	}
	if (ci.getExternalId() == null || ci.getExternalId() == 0L || ci.getExternalId() == -1L) {
	    ci.setExternalId(getNextExternalId());
	    getEntityManager().persist(ci);
	    getEntityManager().flush();
	}
	else {
	    getEntityManager().merge(ci);
	}
	logger.info("saveConsumerInteraction took : " + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * A method to search Consumer Interaction for given External ID
     */
    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public CustomerInteraction findConsumerInteractionById(Long externalId) {
	logger.info("findConsumerInteractionById by external id : " + externalId );
	long start = System.currentTimeMillis();
	Query q = getEntityManager().createQuery(FIND_CI_BY_EXT_ID);
	q.setParameter(1, externalId);
	List objList = q.getResultList();
	CustomerInteraction ci = new CustomerInteraction();
	if (objList != null && objList.size() > 0) {
	    ci = (CustomerInteraction) objList.get(0);
	}
	logger.info("findConsumerInteractionById took : " + (System.currentTimeMillis() - start) + "ms");
	return ci;
    }

    /**
     * A method to search Consumer Interaction for given Agent Id(External ID)
     */
    public List<CustomerInteraction> findInteractionByAgentId(String agentId, int offSet, int totalRows) {
	logger.info("findInteractionByAgentId by agent id : " + agentId);
	long start = System.currentTimeMillis();
	Query q = getEntityManager().createNativeQuery(FIND_CI_BY_AGENT_ID);
	q.setParameter(1, agentId);
	q.setFirstResult(offSet);
	q.setMaxResults(totalRows);
	List objList = q.getResultList();
	List<CustomerInteraction> ciList = new ArrayList<CustomerInteraction>();
	for (Object obj : objList) {
	    CustomerInteraction ci = new CustomerInteraction();
	    Object[] objArray = (Object[]) obj;
	    // Agent id
	    ci.setAgentExternalId((String) objArray[0]);
	    User agent = getAgent((String) objArray[0]);
	    ci.setAgentName(agent.getUserName());

	    // Customer Ext Id
	    ci.setCustomerExternalId(((BigDecimal) objArray[1]).longValue());

	    // Customer First Name
	    ci.setCustomerName((String) objArray[2] + " " + (String) objArray[3]);

	    // Interaction date
	    Calendar cal = Calendar.getInstance();
	    cal.setTimeInMillis(((Timestamp) objArray[4]).getTime());
	    ci.setDateOfInteraction(cal);

	    // Notes
	    ci.setNotes((String) objArray[5]);

	    // Order Ext Id
	    BigDecimal bd = (BigDecimal) objArray[6];
	    Long ordId = bd == null ? -1L : bd.longValue();
	    ci.setOrderExternalId(ordId);

	    // Source
	    ci.setSource((String) objArray[7]);

	    // LineItem External Id
	    BigDecimal bdLi = (BigDecimal) objArray[8];
	    Long liId = bdLi == null ? -1L : bdLi.longValue();
	    ci.setLineItemExternalId(liId);

	    // External Id
	    BigInteger extId = (BigInteger) objArray[9];
	    Long exId = extId == null ? -1L : extId.longValue();
	    ci.setExternalId(exId);

	    // Provider Id
	    BigDecimal providerId = (BigDecimal) objArray[10];
	    Long providerIdLong = providerId == null ? -1L : providerId.longValue();
	    ci.setProviderId(providerIdLong);

	    //Service Type
	    String serviceType = (String)objArray[11];
	    ci.setServiceType(serviceType);

	    // Adding CI in list
	    ciList.add(ci);
	}
	logger.info("findInteractionByAgentId took : " + (System.currentTimeMillis() - start) + "ms");
	return ciList;
    }

    public List<CustomerInteraction> findConsumerInteractionByOrderId(Long orderExtId, int offSet, int totalRows) {
	return findConsumerInteraction(FIND_CI_BY_ORDER_ID, orderExtId, offSet, totalRows);
    }

    public List<CustomerInteraction> findConsumerInteractionByLineItemId(Long lineItemExtId, int offSet, int totalRows) {
	return findConsumerInteraction(FIND_CI_BY_LINEITEM_ID, lineItemExtId, offSet, totalRows);
    }

    /**
     * A method to search Consumer Interaction for given Customer Id(External ID)
     */
    public List<CustomerInteraction> findConsumerInteraction(final String sql, Long filterExternalId, int offSet, int totalRows) {
	logger.info("Executing findConsumerInteraction");
	logger.debug("SQL : " + sql);
	long start = System.currentTimeMillis();
	Query q = getEntityManager().createNativeQuery(sql);
	q.setParameter(1, filterExternalId);
	q.setFirstResult(offSet);
	q.setMaxResults(totalRows);
	List objList = q.getResultList();
	List<CustomerInteraction> ciList = new ArrayList<CustomerInteraction>();
	for (Object obj : objList) {
	    CustomerInteraction ci = new CustomerInteraction();
	    Object[] objArray = (Object[]) obj;
	    // Agent id
	    ci.setAgentExternalId((String) objArray[0]);
	    User agent = getAgent((String) objArray[0]);
	    ci.setAgentName(agent.getUserName());

	    // Customer Ext Id
	    ci.setCustomerExternalId(((BigDecimal) objArray[1]).longValue());

	    // Customer First Name
	    ci.setCustomerName((String) objArray[2] + " " + (String) objArray[3]);

	    // Interaction date
	    Calendar cal = Calendar.getInstance();
	    cal.setTimeInMillis(((Timestamp) objArray[4]).getTime());
	    ci.setDateOfInteraction(cal);

	    // Notes
	    ci.setNotes((String) objArray[5]);

	    // Order Ext Id
	    BigDecimal bd = (BigDecimal) objArray[6];
	    Long ordId = bd == null ? -1L : bd.longValue();
	    ci.setOrderExternalId(ordId);

	    // Source
	    ci.setSource((String) objArray[7]);

	    // LineItem External Id
	    BigDecimal bdLi = (BigDecimal) objArray[8];
	    Long liId = bdLi == null ? -1L : bdLi.longValue();
	    ci.setLineItemExternalId(liId);

	    // External Id
	    BigInteger extId = (BigInteger) objArray[9];
	    Long exId = extId == null ? -1L : extId.longValue();
	    ci.setExternalId(exId);

	    // Provider Id
	    BigDecimal providerId = (BigDecimal) objArray[10];
	    Long providerIdLong = providerId == null ? -1L : providerId.longValue();
	    ci.setProviderId(providerIdLong);

	    //Service Type
	    String serviceType = (String)objArray[11];
	    ci.setServiceType(serviceType);

	    // Adding CI in list
	    ciList.add(ci);
	}
	logger.info("findConsumerInteraction took : " + (System.currentTimeMillis() - start) + "ms");
	return ciList;
    }

    /**
     * A method to search Consumer Interaction for given Customer Id(External ID)
     */
    public List<CustomerInteraction> findConsumerInteractionByConsumerId(Long filterExternalId, int offSet, int totalRows) {
	logger.info("findConsumerInteractionByConsumerId : " + filterExternalId);
	long start = System.currentTimeMillis();
	Query q = getEntityManager().createNativeQuery(FIND_CI_BY_CUST_ID);
	q.setParameter(1, filterExternalId);
	q.setFirstResult(offSet);
	q.setMaxResults(totalRows);
	List objList = q.getResultList();
	List<CustomerInteraction> ciList = new ArrayList<CustomerInteraction>();
	for (Object obj : objList) {
	    CustomerInteraction ci = new CustomerInteraction();
	    Object[] objArray = (Object[]) obj;
	    // Agent id
	    ci.setAgentExternalId((String) objArray[0]);
	    User agent = getAgent((String) objArray[0]);

	    if (agent != null) {
		ci.setAgentName(agent.getUserName());
	    }
	    else {
		ci.setAgentName("default");
	    }

	    // Customer Ext Id
	    ci.setCustomerExternalId(((BigDecimal) objArray[1]).longValue());

	    // Customer First Name
	    ci.setCustomerName((String) objArray[2] + " " + (String) objArray[3]);

	    // Interaction date
	    Calendar cal = Calendar.getInstance();
	    cal.setTimeInMillis(((Timestamp) objArray[4]).getTime());
	    ci.setDateOfInteraction(cal);

	    // Notes
	    ci.setNotes((String) objArray[5]);

	    // Order Ext Id
	    BigDecimal bd = (BigDecimal) objArray[6];
	    Long ordId = bd == null ? -1L : bd.longValue();
	    ci.setOrderExternalId(ordId);

	    // Source
	    ci.setSource((String) objArray[7]);

	    // LineItem External Id
	    BigDecimal bdLi = (BigDecimal) objArray[8];
	    Long liId = bdLi == null ? -1L : bdLi.longValue();
	    ci.setLineItemExternalId(liId);

	    // External Id
	    BigInteger extId = (BigInteger) objArray[9];
	    Long exId = extId == null ? -1L : extId.longValue();
	    ci.setExternalId(exId);

	    // Provider Id
	    BigDecimal providerId = (BigDecimal) objArray[10];
	    Long providerIdLong = providerId == null ? -1L : providerId.longValue();
	    ci.setProviderId(providerIdLong);

	    //Service Type
	    String serviceType = (String)objArray[11];
	    ci.setServiceType(serviceType);

	    // Adding CI in list
	    ciList.add(ci);
	}
	logger.info("findConsumerInteractionByConsumerId took : " + (System.currentTimeMillis() - start) + "ms");
	return ciList;
    }

    /**
     * Helper method to find Agent based on Agent External Id
     *
     * @param agentExtId
     * @return
     */
    private User getAgent(String agentExtId) {
	return agentDao.findUserByUserLogin(agentExtId);
    }

    /*public Long getNextExternalId() {
	logger.info("getNextExternalId");
	long start = System.currentTimeMillis();
	Query q = getEntityManager().createNativeQuery(NEXT_VAL);

	Object obj = q.getResultList().get(0);

	Long l = DataTypeConverter.INSTANCE.objToLong(obj);
	logger.info("getNextExternalId took : " + (System.currentTimeMillis() - start) + "ms");
	return l;
    }*/

    public Long getNextExternalId() {
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
