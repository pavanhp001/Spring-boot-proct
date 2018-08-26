/**
 *
 */
package com.A.Vdao.transactional.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.A.V.beans.LineItemPriceInfo;
import com.A.V.beans.LineitemScheduleInfo;
import com.A.V.beans.entity.BusinessParty;
import com.A.V.beans.entity.LineItem;
import com.A.V.beans.entity.LineItemDetail;
import com.A.V.beans.entity.SalesOrder;
import com.A.V.beans.entity.SelectedFeatureValue;
import com.A.V.beans.entity.StatusRecordBean;
import com.A.Vdao.dao.BusinessPartyDao;
import com.A.Vdao.dao.CustomSelectionDao;
import com.A.Vdao.dao.LineItemAttributeDao;
import com.A.Vdao.dao.LineItemDao;
import com.A.Vdao.dao.LineItemDetailDao;
import com.A.Vdao.dao.PaymentEventDao;
import com.A.Vdao.dao.SelectedDialogueDao;
import com.A.Vdao.dao.SelectedFeatureValueDao;
import com.A.Vdao.dao.StatusDao;

/**
 * @author ebthomas
 *
 */

@Component
public class LineItemDaoImpl extends BaseTransactionalJpaDao implements LineItemDao {

    private Logger logger = Logger.getLogger(LineItemDaoImpl.class);
    private static final String NEXT_VAL = "SELECT nextval('OM_LINE_ITEM_BEAN_SEQ')";

    @Autowired
    private SelectedFeatureValueDao selectedFeatureValueDao;

    @Autowired
    private SelectedDialogueDao selectedDialogueDaoImpl;

    @Autowired
    private CustomSelectionDao customSelectionDaoImpl;

    @Autowired
    private BusinessPartyDao businessPartyDao;
    
	@Autowired
	private PaymentEventDao paymentEventDao;

    @Autowired
    private StatusDao statusDao;

    @Autowired
    private LineItemDetailDao lineItemDetailDao;

    @Autowired
    private LineItemAttributeDao lineItemAttributeDao;
    
    /**
     * factory constructor.
     */
    public LineItemDaoImpl() {
	super();
    }

    /**
     * @param lineItemBeanList
     *            List of line items to be removed
     * @param em
     *            Entity Manager that will perform the remove of the line items
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void remove(final List<LineItem> lineItemBeanList) {
	if (lineItemBeanList == null) {
	    return;
	}

	if (getEntityManager() == null) {
	    throw new IllegalArgumentException("Entity Manager should not be NULL");
	}

	for (LineItem lineItem : lineItemBeanList) {
	    // billingDao.remove(lineItem.getBilling());
	    // lineItem.setBilling(null);

	    // LogUtil.log( LogLevelEnum.debug, LogUtil.NULL_LOG4J_LOGGER, "" );
	    List<StatusRecordBean> historicStatus = lineItem.getHistoricStatus();
	    for (StatusRecordBean status : historicStatus) {
		List<String> reasons = status.getReasons();
		for (String reason : reasons) {
		    // em.remove( reason );
		}

		status.setReasons(null);
		getEntityManager().remove(getEntityManager().getReference(StatusRecordBean.class, status.getId()));

	    }

	    if (lineItem.getLineItemDetailBean() != null) {
		lineItemDetailDao.remove(lineItem.getLineItemDetailBean());
	    }
	    /*
	     * if(lineItem.getSelectedFeatureValues() != null) { selectedFeatureValueDao.remove( lineItem.getSelectedFeatureValues() ); }
	     */
	    if (lineItem.getCurrentStatus() != null) {
		getEntityManager().remove(getEntityManager().getReference(StatusRecordBean.class, lineItem.getCurrentStatus().getId()));// lineItem.getCurrentStatus());
	    }

	    /*
	     * if (lineItem.getServiceOrDeliveryAddress() != null) { getEntityManager().remove(getEntityManager().getReference( AddressBean.class,
	     * lineItem.getServiceOrDeliveryAddress().getId() ));//lineItem.getServiceOrDeliveryAddress()); }
	     */

	    lineItem.setProvider(null);
	    lineItem.setCurrentStatus(null);
	    lineItem.setHistoricStatus(null);
	    // lineItem.setServiceOrDeliveryAddress(null);
	    lineItem.setLineItemDetailBean(null);
	    lineItem.setSelectedFeatureValues(null);
	    getEntityManager().remove(getEntityManager().getReference(LineItem.class, lineItem.getId()));
	}
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean merge(final LineItem lineItemBean) {
	logger.info("Merging lineitem");
	long start = System.currentTimeMillis();
	if ((lineItemBean != null) && (getEntityManager() != null)) {

	    try {

		if (lineItemBean.getPrice() == null) {
		    LineItemPriceInfo priceInfo = new LineItemPriceInfo();
		    priceInfo.setBaseNonRecurringPrice(0);
		    priceInfo.setBaseRecurringPrice(0);
		    lineItemBean.setPrice(priceInfo);
		}

		/*
		 * if (lineItemBean.getServiceOrDeliveryAddress() != null) { addressDao .merge(lineItemBean.getServiceOrDeliveryAddress()); }
		 */

		/*
		 * if (lineItemBean.getBilling() != null) { billingDao.merge(lineItemBean.getBilling()); }
		 */
		if (lineItemBean.getSelectedFeatureValues() != null) {
		    Set<SelectedFeatureValue> featureList = lineItemBean.getSelectedFeatureValues();
		    if (featureList != null) {
			selectedFeatureValueDao.merge(featureList);
		    }
		}
		if (lineItemBean.getLineItemDetailBean() != null) {
		    lineItemDetailDao.merge(lineItemBean.getLineItemDetailBean());
		}

		statusDao.merge(lineItemBean.getCurrentStatus());
		getEntityManager().merge(lineItemBean);

		getEntityManager().flush();

		return Boolean.TRUE;
	    }
	    catch (Exception e) {
		e.printStackTrace();
	    }

	}
	logger.info("Merging lineitem took : " + (System.currentTimeMillis() - start) + "ms");
	return Boolean.FALSE;
    }

    /**
     * @param em
     *            EntityManager for datasource.
     * @param lineItemBean
     *            LineItem to be persisted
     * @return boolean value that specify success of the operation
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean persist(final LineItem lineItemBean) {
	logger.info("Saving lineitem");
	long start = System.currentTimeMillis();
	if ((lineItemBean != null) && (getEntityManager() != null)) {

	    try {

		if (lineItemBean.getPrice() == null) {
		    LineItemPriceInfo priceInfo = new LineItemPriceInfo();
		    priceInfo.setBaseNonRecurringPrice(0);
		    priceInfo.setBaseRecurringPrice(0);
		    lineItemBean.setPrice(priceInfo);
		}

		// This code will set installation fee to zero if client is not
		// providing any sch info
		// otherwise xmlbean will throw error of invalid big decimal
		// value
		if (lineItemBean.getLineitemScheduleInfo() == null) {
		    LineitemScheduleInfo schInfo = new LineitemScheduleInfo();
		    schInfo.setInstallationFee(BigDecimal.valueOf(0l));
		    lineItemBean.setLineitemScheduleInfo(schInfo);
		}
		else if (lineItemBean.getLineitemScheduleInfo().getInstallationFee() == null) {
		    lineItemBean.getLineitemScheduleInfo().setInstallationFee(BigDecimal.valueOf(0l));
		}

		if (lineItemBean.getSelectedFeatureValues() != null) {
		    Set<SelectedFeatureValue> featureList = lineItemBean.getSelectedFeatureValues();
		    if (featureList != null) {
			selectedFeatureValueDao.persist(featureList);
		    }
		}

		if (lineItemBean.getLineItemDetailBean() != null) {
		    LineItemDetail lineItemDetailBean = lineItemBean.getLineItemDetailBean();
		    lineItemDetailDao.persist(lineItemDetailBean);
		} 
	
		statusDao.persist(lineItemBean.getCurrentStatus());
		lineItemBean.setExternalId(getNextExternalId());
		getEntityManager().persist(lineItemBean);

		getEntityManager().flush();

		return Boolean.TRUE;
	    }
	    catch (Exception e) {
		e.printStackTrace();
	    }

	}
	logger.info("Saving lineitem took : " + (System.currentTimeMillis() - start) + "ms");
	return Boolean.FALSE;
    }

    public List<SelectedFeatureValue> getSelectedFeatures() {
	logger.info("executing getSelectedFeatues");
	long start = System.currentTimeMillis();
	List<SelectedFeatureValue> list = getEntityManager().createNamedQuery("SelectedFeatureValue.findAll").getResultList();

	logger.info("getSelectedFeatures took : " + (System.currentTimeMillis() - start) + "ms");
	return list;
    }

    public LineItem getLineItem(final Integer lineItemNumber) {
	logger.info("executing getLineitem");
	long start = System.currentTimeMillis();
	LineItem bean = null;
	if (lineItemNumber == null) {
	    return null;
	}

	if (getEntityManager() == null) {
	    throw new IllegalArgumentException("Entity Manager should not be NULL");
	}
	Query query = getEntityManager().createQuery("SELECT li from LineItem li where li.id= ?1");
	query.setParameter(1, lineItemNumber.longValue());

	try {
	    Object obj = query.getSingleResult();

	    if (obj == null) {
		return null;
	    }

	    if (obj instanceof LineItem) {
		bean = (LineItem) obj;

		if ((bean != null) && (bean.getProviderExternalId() != null)) {
		    BusinessParty provider = businessPartyDao.findBusinessPartyById(bean.getProviderExternalId());
		    bean.setProvider(provider);
		}
	    }

	}
	catch (NoResultException nre) {
	    logger.error(nre.getMessage());
	    return null;
	}
	logger.info("getLineItem took : " + (System.currentTimeMillis() - start) + "ms");
	return bean;
    }

    public List<LineItem> getLineItemsByProviderId(final String providerId, final String liStatus, final Date reqInstallDate) {
	logger.info("executing getLineItemsByProviderId");
	long start = System.currentTimeMillis();
	List<LineItem> obj = null;
	if (providerId == null || liStatus == null || reqInstallDate == null) {
	    return null;
	}

	if (getEntityManager() == null) {
	    throw new IllegalArgumentException("Entity Manager should not be NULL");
	}

	StringBuilder sb = new StringBuilder();
	sb.append(" SELECT li from LineItem li ");
	sb.append(" LEFT JOIN FETCH li.currentStatus cs ");
	sb.append(" LEFT JOIN FETCH li.lineitemScheduleInfo si");
	sb.append(" WHERE ");
	sb.append(" li.providerExternalId=  ?1 ");
	sb.append(" AND ");
	sb.append(" cs.status = ?2  ");
	sb.append(" AND ");
	sb.append(" si.desiredStartDate = ?3  ");

	Query query = getEntityManager().createQuery(sb.toString());
	query.setParameter(1, providerId);
	query.setParameter(2, liStatus);
	query.setParameter(3, reqInstallDate);

	try {
	    obj = (List<LineItem>) query.getResultList();

	    if (obj == null) {
		return null;
	    }

	}
	catch (NoResultException nre) {
	    logger.error(nre.getMessage());
	    return null;
	}
	logger.info("getLineItemsByProviderId took : " + (System.currentTimeMillis() - start) + "ms");
	return obj;
    }

    /*public Long getNextExternalId() {
	Query q = getEntityManager().createNativeQuery(NEXT_VAL);
	return DataTypeConverter.INSTANCE.objToLong(q.getResultList().get(0));
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
    

    /**
     * {@inheritDoc}
     */
    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public Boolean saveNewLineItems(final String agentId, final SalesOrder salesOrder) {

	logger.info("Persisting lineitems");
	long start = System.currentTimeMillis();
	if (salesOrder != null) {
	    try {

		List<LineItem> lineItemBeans = salesOrder.getLineItems();

		for (LineItem lineItemBean : lineItemBeans) {

		    if (lineItemBean.getId() == 0) {

			if (lineItemBean.getPrice() == null) {
			    LineItemPriceInfo priceInfo = new LineItemPriceInfo();
			    priceInfo.setBaseNonRecurringPrice(0);
			    priceInfo.setBaseRecurringPrice(0);
			    lineItemBean.setPrice(priceInfo);
			}

			// This code will set installation fee to zero if client
			// is not providing any sch info
			// otherwise xmlbean will throw error of invalid big
			// decimal value
			if (lineItemBean.getLineitemScheduleInfo() == null) {
			    LineitemScheduleInfo schInfo = new LineitemScheduleInfo();
			    schInfo.setInstallationFee(BigDecimal.valueOf(0l));
			    lineItemBean.setLineitemScheduleInfo(schInfo);
			}
			else if (lineItemBean.getLineitemScheduleInfo().getInstallationFee() == null) {
			    lineItemBean.getLineitemScheduleInfo().setInstallationFee(BigDecimal.valueOf(0l));
			}

			if ((lineItemBean.getDialogues() != null) && lineItemBean.getDialogues().size() > 0) {

			    selectedDialogueDaoImpl.persist(lineItemBean.getDialogues());
			}

			if (lineItemBean.getLineItemAttribute() != null) {
			    lineItemAttributeDao.saveLineItemAttributes(lineItemBean.getLineItemAttribute());
			}

			if ((lineItemBean.getSelections() != null) && (lineItemBean.getSelections().size() > 0)) {

			    customSelectionDaoImpl.persist(lineItemBean.getSelections());
			}
			
			if ((lineItemBean.getPaymentEvents() != null) && (lineItemBean.getPaymentEvents().size() > 0)) {
				paymentEventDao.persistLineItemPaymentEvents(lineItemBean.getPaymentEvents());
			}

			if (lineItemBean.getCurrentStatus() != null) {
			    lineItemBean.getCurrentStatus().setAgentExternalId(agentId);
			    statusDao.persist(lineItemBean.getCurrentStatus());
			}

			statusDao.persist(lineItemBean.getHistoricStatus());

			lineItemDetailDao.persist(lineItemBean.getLineItemDetailBean());
			selectedFeatureValueDao.persist(lineItemBean.getSelectedFeatureValues());
			// setting system generated ExternalId for each Lineitem
			lineItemBean.setExternalId(getNextExternalId());
			getEntityManager().persist(lineItemBean);
		    }
		    else {
			// update changed applies to
			lineItemDetailDao.merge(lineItemBean.getLineItemDetailBean());
			getEntityManager().merge(lineItemBean);
		    }
		}

		getEntityManager().flush();
	    }
	    catch (Exception e) {
		e.printStackTrace();
		return Boolean.FALSE;
	    }

	    return Boolean.TRUE;

	}
	logger.info("saveNewLineItems took : " + (System.currentTimeMillis() - start) + "ms");
	return Boolean.TRUE;
    }

    /* (non-Javadoc)
     * @see com.A.Vdao.dao.LineItemDao#getLineItemByProviderConfirmationNumber(java.lang.String)
     */
    public Map<String, String> getLineItemByProviderConfirmationNumber(final String confirmationNumber){
    	logger.info("start getLineItemByProviderConfirmationNumber");
    	Map<String, String> resultData = null;
    	StringBuilder builder = new StringBuilder();    	
    	
    	builder.append(" select orom.li_ext_id, orom.order_ext_id, osr.status ");
    	builder.append(" from om_rtim_order_mapping orom ");
    	builder.append(" left outer join om_line_item oli on oli.external_id = orom.li_ext_id ");
    	builder.append(" left outer join om_stat_rec osr on osr.id = oli.currentstatus_id ");
    	builder.append(" where orom.V_order_no='").append(confirmationNumber).append("'");

    	Query query = getEntityManager().createNativeQuery(builder.toString());
    	List<Object[]> resultList = query.getResultList();
    	if(null != resultList && resultList.size() > 0){
    		Object[] result = resultList.get(0);
 
    		resultData = new HashMap<String, String>();
    		resultData.put("lineItemExtId", String.valueOf((BigDecimal)result[0]));
    		resultData.put("orderExtId", String.valueOf((BigDecimal)result[1]));
    		resultData.put("lineItemStatus", (String)result[2]);
    	}
    	logger.info("end getLineItemByProviderConfirmationNumber");
    	return resultData;
    }
}
