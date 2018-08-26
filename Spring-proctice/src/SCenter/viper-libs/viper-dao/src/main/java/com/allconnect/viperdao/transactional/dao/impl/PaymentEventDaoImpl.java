package com.A.Vdao.transactional.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.A.V.beans.entity.BillingInformation;
import com.A.V.beans.entity.Consumer;
import com.A.V.beans.entity.CustomerPaymentEvent;
import com.A.V.beans.entity.CustomerPaymentEventStatus;
import com.A.V.beans.entity.LineItem;
import com.A.Vdao.dao.PaymentEventDao;

/**
 * @author ebthomas
 *
 */

@Component
public class PaymentEventDaoImpl extends BaseTransactionalJpaDao implements PaymentEventDao {

    private static final String FIND_PAYMENT_EVENTS_BY_CUSTOMER_ID = "SELECT c FROM Consumer c WHERE c.externalId = ?";
    private static final Logger logger = Logger.getLogger(PaymentEventDaoImpl.class);

    /**
     * factory constructor.
     */
    public PaymentEventDaoImpl() {
	super();
    }

    /**
     * Find consumer by external id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Set<CustomerPaymentEvent> findCustomerPaymentEventByCustomerId(Long id) {
	logger.info("finding phone by customer id: " + id);
	long start = System.currentTimeMillis();
	Query q = getEntityManager().createQuery(FIND_PAYMENT_EVENTS_BY_CUSTOMER_ID);
	q.setParameter(1, Long.valueOf(id));
	Consumer c = (Consumer) q.getSingleResult();

	Set<CustomerPaymentEvent> eventList = c.getPaymentEvents();
	logger.info("findCustomerPaymentEventByCustomerId took : " + (System.currentTimeMillis() - start) + "ms");
	return eventList;

    }

    public void remove(final Set<CustomerPaymentEvent> customerPaymentEventList) {

	for (CustomerPaymentEvent customerPaymentEvent : customerPaymentEventList) {
	    remove(customerPaymentEvent);
	}
    }

    /**
     * @param em
     *            Entity Manager
     * @param CustomerPaymentEvent
     *            phone contact channel
     */
    public void remove(final CustomerPaymentEvent customerPaymentEvent) {
	if (customerPaymentEvent != null) {
	    getEntityManager().remove(getEntityManager().getReference(CustomerPaymentEvent.class, customerPaymentEvent.getId()));
	}
    }

    public void merge(final Consumer consumer, final Set<CustomerPaymentEvent> customerPaymentEventList) {
	logger.info("Executing payment event merge");
	long start = System.currentTimeMillis();
	if ((customerPaymentEventList != null) && (customerPaymentEventList.size() > 0)) {

	    for (CustomerPaymentEvent cpe : customerPaymentEventList) {
		merge(consumer, cpe);
	    }
	}
	logger.info("Payment event merge took : " + (System.currentTimeMillis() - start) + "ms");
    }

    public void persist(final Consumer consumer, final Set<CustomerPaymentEvent> customerPaymentEventList) {
	logger.info("Exeucting payment event save");
	long start = System.currentTimeMillis();
	List<BillingInformation> billingList = new ArrayList<BillingInformation>(consumer.getBillingInfoList());

	if ((customerPaymentEventList != null) && (customerPaymentEventList.size() > 0)) {

	    for (CustomerPaymentEvent cpe : customerPaymentEventList) {

		if (cpe.getBillingInfo() == null) {

		    billInfoLoop: for (BillingInformation bi : billingList) {

			if ((bi.getBillingUniqueId() != null) && (bi.getBillingUniqueId().equals(cpe.getBillingInfoId()))) {
			    cpe.setBillingInfo(bi);

			    cpe.setConsumerId(consumer.getId());

			    if ((cpe.getExternalId() == null) || (cpe.getExternalId().length() == 0)) {
				cpe.setExternalId(String.valueOf(getNextExternalId()));
			    }

			    break billInfoLoop;
			}

		    }
		}

		persist(consumer, cpe);
	    }
	}
	logger.info("Payment event save took : " + (System.currentTimeMillis() - start) + "ms");
    }

    /*public Long getNextStatusExternalId() {
   	Query q = getEntityManager().createNativeQuery("SELECT nextval('CM_PAY_EVENT_STAT_SEQ')");
   	return DataTypeConverter.INSTANCE.objToLong(q.getResultList().get(0));

       }*/
       
       public Long getNextStatusExternalId() {
     	  Session session = (Session)getEntityManager().getDelegate();
     	  Connection conn = ((SessionImpl)session).connection();
     	  PreparedStatement ps = null;
     	  ResultSet rs = null;
     	  long externalId = 0;
     	  try {
     	   ps = conn.prepareStatement("SELECT nextval('CM_PAY_EVENT_STAT_SEQ')");
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

       /*public Long getNextExternalId() {
   	Query q = getEntityManager().createNativeQuery("SELECT nextval('CM_PAYEVENT_SEQ')");
   	return DataTypeConverter.INSTANCE.objToLong(q.getResultList().get(0));
       }*/

       public Long getNextExternalId() {
       	  Session session = (Session)getEntityManager().getDelegate();
       	  Connection conn = ((SessionImpl)session).connection();
       	  PreparedStatement ps = null;
       	  ResultSet rs = null;
       	  long externalId = 0;
       	  try {
       	   ps = conn.prepareStatement("SELECT nextval('CM_PAYEVENT_SEQ')");
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
       

    /**
     * @param em
     *            Entity Manager
     * @param CustomerPaymentEvent
     *            phone contact channel
     */
    public void persist(final Consumer consumerBean, final CustomerPaymentEvent customerPaymentEvent) {
	logger.info("Executing payment event save");
	long start = System.currentTimeMillis();
	String currentStatus = "";
	if (customerPaymentEvent != null) {

	    for (CustomerPaymentEventStatus status : customerPaymentEvent.getPaymentStatusHistory()) {
		if (status.getId() == 0) {
		    status.setExternalId(String.valueOf(getNextStatusExternalId().longValue()));
		    getEntityManager().persist(status);
		}
		else {
		    getEntityManager().merge(status);
		}

		currentStatus = status.getStatus();
	    }

	    customerPaymentEvent.setPayStatus(currentStatus);
	    getEntityManager().persist(customerPaymentEvent);
	    getEntityManager().flush();
	}
	logger.info("Saving payment event took : " + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * @param em
     *            Entity Manager
     * @param CustomerPaymentEvent
     *            phone contact channel
     */
    public void merge(final Consumer consumerBean, final CustomerPaymentEvent customerPaymentEvent) {
	logger.info("Executing payment event merge");
	long start = System.currentTimeMillis();
	if (customerPaymentEvent != null) {

	    String paymentEventBillingInfoUniqueId = customerPaymentEvent.getBillingInfoId();

	    List<BillingInformation> billingInfoList = new ArrayList<BillingInformation>(consumerBean.getBillingInfoList());

	    for (BillingInformation bInfo : billingInfoList) {
		logger.debug("assigning billing info to payment event:" + bInfo.getExternalId());
		if (paymentEventBillingInfoUniqueId.equals(bInfo.getBillingUniqueId())) {
		    customerPaymentEvent.setBillingInfo(bInfo);
		}

	    }

	    for (CustomerPaymentEventStatus status : customerPaymentEvent.getPaymentStatusHistory()) {
		if (status.getId() == 0) {
		    status.setExternalId(String.valueOf(getNextStatusExternalId().longValue()));
		    getEntityManager().persist(status);
		}
		else {
		    getEntityManager().merge(status);
		}
	    }

	    if (customerPaymentEvent.getBillingInfo() == null) {
		throw new IllegalArgumentException("customer payment event missing billing info");
	    }

	    if (customerPaymentEvent.getId() != 0) {
		getEntityManager().merge(customerPaymentEvent);
	    }
	    else {
		customerPaymentEvent.setExternalId(String.valueOf(getNextStatusExternalId().longValue()));
		getEntityManager().persist(customerPaymentEvent);
	    }
	}
	getEntityManager().flush();
	logger.info("Payment event merge took : " + (System.currentTimeMillis() - start) + "ms");
    }
    
    @Transactional(value = "transactional", propagation = Propagation.REQUIRES_NEW)
    public void persistLineItemPaymentEvents(Set<CustomerPaymentEvent> customPaymentEvents) {
		logger.debug("Persisting  lineItem paymentEvents");
		long start = System.currentTimeMillis();
		if (customPaymentEvents != null) {
		    for (CustomerPaymentEvent paymentEvent : customPaymentEvents) {
		    	for (CustomerPaymentEventStatus status : paymentEvent.getPaymentStatusHistory()) {
		    		if (status.getId() == 0) {
		    		    status.setExternalId(String.valueOf(getNextStatusExternalId().longValue()));
		    		    getEntityManager().persist(status);
		    		}
		    		else {
		    		    getEntityManager().merge(status);
		    		}
		    	}
				if (paymentEvent.getId() == 0) {
				    getEntityManager().persist(paymentEvent);
				}
				else {
				    getEntityManager().merge(paymentEvent);
				}
		    }
		    getEntityManager().flush();
		}
		logger.info("persistLineItemPaymentEvents took: " + (System.currentTimeMillis() - start) + "ms");
    }
    
	//@Transactional(propagation = Propagation.REQUIRED)
    @Transactional(value = "transactional")
	public Set<CustomerPaymentEvent> findPaymentEventsByLineItemId(
			Long id) {
		logger.info("Finding payment events by lineItemId: " + id);
		Set<CustomerPaymentEvent> eventSet = new HashSet<CustomerPaymentEvent>();
		try {
			long start = System.currentTimeMillis();
			Query q = getEntityManager()
					.createQuery(
							"SELECT li FROM LineItem li LEFT JOIN FETCH li.paymentEvents WHERE li.id = ?");
			q.setParameter(1, Long.valueOf(id));
			LineItem li = (LineItem)q.getSingleResult();
			eventSet = li.getPaymentEvents();
			logger.info("findPaymentEventsByLineItemId took: "+ (System.currentTimeMillis() - start) + "ms");
		} catch (Exception e) {
			logger.error(
					"********** Error occured in finding payment events by lineItemId ******",
					e);
		}
		return eventSet;
	}

}
