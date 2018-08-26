package com.A.Vdao.transactional.dao.impl;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.lang.StringUtils;

import org.springframework.dao.DataAccessException;

import com.A.util.DateUtil;
import com.A.V.beans.entity.LineItem;
import com.A.V.beans.entity.Product;
import com.A.V.beans.entity.SalesOrder;
import com.A.Vdao.dao.CustomerDao;
import com.A.Vdao.dao.OrderManagementDao;
import com.A.Vdao.dao.SearchOrderDao;
import com.A.Vdao.dao.UserDao;
import com.A.Vdao.dao.impl.SearchCriteria;
import com.A.Vdao.util.OrderDataBean;

@Component
public class SearchOrderDaoImpl extends BaseTransactionalJpaDao implements SearchOrderDao {

    private static final Logger logger = Logger.getLogger(SearchOrderDaoImpl.class);

    @Autowired
    private OrderManagementDao orderManagementDao;

    /**
     * Search Order Management.
     */
    public SearchOrderDaoImpl() {
	super();
    }

    public OrderManagementDao getOrderManagementDao() {
	return orderManagementDao;
    }

    public void setOrderManagementDao(OrderManagementDao orderManagementDao) {
	this.orderManagementDao = orderManagementDao;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> searchOrder(SearchCriteria criteria, int offset, int totalRows) {
	logger.info("Executing searchOrder query");
	long start = System.currentTimeMillis();

	Map<String, Object> resultMap = new HashMap<String, Object>();

	logger.debug("SearchOrderDaoImpl:searchOrder: Searching Order...  totalRows: " + totalRows);

	if (getEntityManager() == null) {
	    throw new IllegalArgumentException("Entity Manager should not be NULL");
	}

	List<OrderDataBean> dataList = new ArrayList<OrderDataBean>();

	try {
	    
		StringBuilder sql = new StringBuilder();
		sql.append("select salesorder0_.CUST_EXT_ID as consumerExternalId, salesorder0_.EXTERNAL_ID as externalId, lineitem2_.EXTERNAL_ID as liexternalId, ");
		sql.append(" lineitemde4_.PRODUCT_UNIQUE_ID as productUniqueId, lineitem2_.PROVIDER_EXT_ID as providerExtId, lineitem2_.SCHEDULED_START_ON as scheduledStartDt, salesorder0_.ORDER_DATE as orderDt, ");
		sql.append(" lineitem2_.LI_CREATE_DATE as liCreateDt from OM_SALES_ORDER salesorder0_ ");
		sql.append(" left outer join OM_SALES_ORDER_OM_LINE_ITEM lineitems1_ on salesorder0_.id=lineitems1_.OM_SALES_ORDER_id ");
		sql.append(" left outer join OM_LINE_ITEM lineitem2_ on lineitems1_.lineItems_id=lineitem2_.id ");
		sql.append(" left outer join OM_STAT_REC statusreco3_ on lineitem2_.currentStatus_id=statusreco3_.id ");
		if(criteria.getChannelType() > 0){
			sql.append(" left outer join OM_JOB omjob1_ on lineitem2_.external_id=omjob1_.resource_ext_id ");
		}
		sql.append(" left outer join OM_LINE_ITEM_DTL lineitemde4_ on lineitem2_.LINEITEM_DTL_ID=lineitemde4_.id ");
		sql.append(" where lineitem2_.PROVIDER_EXT_ID="+criteria.getProviderId());
		if(criteria.getChannelType() > 0){
			sql.append(" and omjob1_.channel_type="+criteria.getChannelType());
		}
		sql.append(" and statusreco3_.STATUS='" + criteria.getLineItemStatus() + "' and lineitem2_.SCHEDULED_START_ON<='");
		sql.append(criteria.getScheduledInstallDate() + "' order by salesorder0_.id");

	    logger.debug("SearchOrderDaoImpl:searchOrder SQL statement : " + sql);

	    Query q = getEntityManager().createNativeQuery(sql.toString());
	    List<Object[]> result = q.getResultList();

	    if (result != null) {
		dataList = mapResultSet(result);
	    }

	    logger.debug("SearchOrderDaoImpl:searchOrder dataList.size() : " + dataList.size());
	}
	catch (NoResultException nre) {
	    nre.printStackTrace();
	    logger.error("SearchOrderDaoImpl:searchOrder: NoResultException thrown by searchOrder :", nre);
	    return null;
	}
	catch (Exception exp) {
	    exp.printStackTrace();
	    logger.error("SearchOrderDaoImpl:searchOrder: Exception thrown by searchOrder :", exp);
	    return null;
	}

	resultMap.put("dataSets", dataList);
	resultMap.put("totalCount", dataList.size());
	logger.info("SearchOrderDaoImpl:searchOrder took : " + (System.currentTimeMillis() - start) + "ms");

	return resultMap;
    }

    private List<OrderDataBean> mapResultSet(List<Object[]> resultSet) {
	OrderDataBean odb = null;
	List<OrderDataBean> listOB = new ArrayList<OrderDataBean>();

	for (Object[] resultElement : resultSet) {
	    odb = new OrderDataBean();

	    BigInteger consumerExternalId = (BigInteger) resultElement[0];
	    if (consumerExternalId != null) {
		logger.debug("SearchOrderDaoImpl:searchOrder consumerExternalId : " + consumerExternalId);
		odb.setCustomerId(String.valueOf(consumerExternalId));
	    }

	    BigInteger externalId = (BigInteger) resultElement[1];
	    if (externalId != null) {
		logger.debug("SearchOrderDaoImpl:searchOrder order externalId() : " + externalId);
		odb.setOrderId(String.valueOf(externalId));
	    }

	    BigInteger liexternalId = (BigInteger) resultElement[2];
	    if (liexternalId != null) {
		logger.debug("SearchOrderDaoImpl:searchOrder lineitemExternalId() : " + liexternalId);
		odb.setLineItemId(String.valueOf(liexternalId));
	    }

	    BigInteger productUniqueId = (BigInteger) resultElement[3];
	    if (productUniqueId != null) {
		odb.setProductId(String.valueOf(productUniqueId));
		logger.debug("SearchOrderDaoImpl:searchOrder ProductId() : " + productUniqueId);
		Product liso_pr = orderManagementDao.findProductById(String.valueOf(productUniqueId));
		if (liso_pr != null) {
		    odb.setProductName(liso_pr.getProductBase().getName() != null ? liso_pr.getProductBase().getName().trim() : "");
		}
		else {
		    odb.setProductName("");
		}
		logger.debug("SearchOrderDaoImpl:searchOrder ProductName() : " + odb.getProductName());
	    }

	    String providerExtId = (String) resultElement[4];
	    odb.setProviderExternalId(providerExtId);

	    java.sql.Timestamp scheduledStartDt = (java.sql.Timestamp) resultElement[5];
	    Calendar cal_scheduledStartDt = Calendar.getInstance();
	    cal_scheduledStartDt.setTimeInMillis(scheduledStartDt.getTime());
	    if (cal_scheduledStartDt != null) {
		logger.debug("SearchOrderDaoImpl:searchOrder lineItem ScheduledStartDate() : " + scheduledStartDt.toString());
		odb.setScheduledInstallDate(cal_scheduledStartDt);
	    }

	    java.sql.Timestamp orderDt = (java.sql.Timestamp) resultElement[6];
	    Calendar cal_orderDt = Calendar.getInstance();
	    cal_orderDt.setTimeInMillis(orderDt.getTime());
	    if (cal_orderDt != null) {
		logger.debug("SearchOrderDaoImpl:searchOrder order OrderDate() : " + orderDt.toString());
		odb.setOrderDate(cal_orderDt);
	    }

	    // odb.setLineItemCreateDate((Calendar) resultElement[7]);
	    listOB.add(odb);
	}
	return listOB;
    }

}
