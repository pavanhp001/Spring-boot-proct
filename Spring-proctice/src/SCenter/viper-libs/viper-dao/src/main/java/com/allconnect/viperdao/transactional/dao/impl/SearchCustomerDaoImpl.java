package com.A.Vdao.transactional.dao.impl;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.A.Vdao.dao.SearchCustomerDao;
import com.A.Vdao.dao.impl.SearchCriteria;
import com.A.Vdao.query.SearchQueryBuilder;
import com.A.Vdao.util.AccountHolderDataBean;
import com.A.Vdao.util.CustomerDataBean;
import com.A.Vdao.util.QueryBuilderUtil;

@Component
public class SearchCustomerDaoImpl extends BaseTransactionalJpaDao implements SearchCustomerDao {

    private static final Logger logger = Logger.getLogger(SearchCustomerDaoImpl.class);

    @SuppressWarnings("unchecked")
    public Map<String, Object> searchCustomer(SearchCriteria criteria, int offset, int totalRows) {
	logger.info("Executing searchCustomer query");
	long start = System.currentTimeMillis();

	Map<String, Object> resultMap = new HashMap<String, Object>();

	// Counting total search count based on criteria
	BigInteger totalCount = getTotalCount(criteria);
	logger.info("Total search records : " + totalCount);

	resultMap.put("totalCount", totalCount);

	// Searching records with offset and limit
	List<CustomerDataBean> dataList = new ArrayList<CustomerDataBean>();
	String sql = QueryBuilderUtil.generateSelectQuery(criteria, false);
	logger.info("SearchCustomer query: " + sql);
	Query q = getEntityManager().createNativeQuery(sql);
	q.setFirstResult(offset);
	q.setMaxResults(totalRows);
	List<Object[]> resultList = (List<Object[]>) q.getResultList();
	if (resultList != null && resultList.size() > 0) {

	    for (Object[] row : resultList) {
		CustomerDataBean bean = new CustomerDataBean();
		bean.setCustExtId(String.valueOf(row[0]));
		bean.setFirstName(String.valueOf(row[1]));
		bean.setLastName(String.valueOf(row[2]));
		bean.setHomePhone(String.valueOf(row[3]));
		bean.setWorkPhone(String.valueOf(row[4]));
		bean.setCellPhone(String.valueOf(row[5]));
		bean.setBestPhoneContact(String.valueOf(row[6]));
		bean.setSecondPhone(String.valueOf(row[7]));
		bean.setHomeEmail(String.valueOf(row[8]));
		bean.setWorkEmail(String.valueOf(row[9]));
		bean.setACustNumber(String.valueOf(row[10]));
		bean.setDtCreated(String.valueOf(row[11]));
		dataList.add(bean);
	    }
	}
	logger.info("SearchCustomer query took : " + (System.currentTimeMillis() - start) + "ms");
	resultMap.put("dataSets", dataList);
	return resultMap;
    }

    private BigInteger getTotalCount(SearchCriteria criteria) {
	logger.info("Executing totalCount query");
	long start = System.currentTimeMillis();
	BigInteger i = BigInteger.ZERO;
	String sql = QueryBuilderUtil.generateCountQuery(criteria);
	Query q = getEntityManager().createNativeQuery(sql);
	Object result = q.getSingleResult();
	if (result != null) {
	    i = (BigInteger) result;
	}
	logger.info("totalCount query took : " + (System.currentTimeMillis() - start) + "ms");
	return i;
    }

    public Map<String, Object> searchOrderByCustomer(SearchCriteria criteria) {
	logger.info("Executing searchOrderByCustomer");
	long start = System.currentTimeMillis();

	Map<String, Object> resultMap = new HashMap<String, Object>();
	// Searching records with offset and limit
	List<CustomerDataBean> dataList = new ArrayList<CustomerDataBean>();
	String sql = SearchQueryBuilder.getCustomerSearchQuery(criteria);
	logger.info("SearchCustomer query: " + sql);
	Query q = getEntityManager().createNativeQuery(sql);

	List<Object[]> resultList = (List<Object[]>) q.getResultList();
	if (resultList != null && resultList.size() > 0) {

	    for (Object[] row : resultList) {
		CustomerDataBean bean = new CustomerDataBean();
		bean.setCustExtId(String.valueOf(row[0]));
		bean.setFirstName(String.valueOf(row[1]));
		bean.setLastName(String.valueOf(row[2]));
		bean.setAddress(String.valueOf(row[3]));
		bean.setOrderExtId(Long.valueOf(String.valueOf(row[4])));
		bean.setAgentId(String.valueOf(row[5]));
		String dt = String.valueOf(row[6]);
		if (dt != null && !dt.equalsIgnoreCase("null")) {
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.s");
		    Date date;
		    try {
			date = sdf.parse(dt);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			bean.setOrderDate(cal);
		    }
		    catch (ParseException e) {
			e.printStackTrace();
		    }
		}
		dataList.add(bean);
	    }
	}
	resultMap.put("dataSets", dataList);

	logger.info("SearchOrderByCustomer took : " + (System.currentTimeMillis() - start) + "ms");
	return resultMap;
    }

    public Map<String, Object> advanceOrderSearch(SearchCriteria criteria) {
	logger.info("Executing advanceOrderSearch");
	long start = System.currentTimeMillis();

	Map<String, Object> resultMap = new HashMap<String, Object>();
	// Searching records with offset and limit
	List<CustomerDataBean> dataList = new ArrayList<CustomerDataBean>();
	String sql = SearchQueryBuilder.getOrderSearchQuery(criteria);
	logger.info("SearchCustomer query: " + sql);
	Query q = getEntityManager().createNativeQuery(sql);

	List<Object[]> resultList = (List<Object[]>) q.getResultList();
	if (resultList != null && resultList.size() > 0) {

	    for (Object[] row : resultList) {
		CustomerDataBean bean = new CustomerDataBean();
		bean.setCustExtId(String.valueOf(row[0]));
		bean.setFirstName(String.valueOf(row[1]));
		bean.setLastName(String.valueOf(row[2]));
		bean.setAddress(String.valueOf(row[3]));
		bean.setOrderExtId(Long.valueOf(String.valueOf(row[4])));
		bean.setAgentId(String.valueOf(row[5]));
		String dt = String.valueOf(row[6]);
		if (dt != null && !dt.equalsIgnoreCase("null")) {
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.s");
		    Date date;
		    try {
			date = sdf.parse(dt);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			bean.setOrderDate(cal);
		    }
		    catch (ParseException e) {
			e.printStackTrace();
		    }
		}
		dataList.add(bean);
	    }
	}
	resultMap.put("dataSets", dataList);

	logger.info("advanceOrderSearch took : " + (System.currentTimeMillis() - start) + "ms");
	return resultMap;
    }

	public Map<String, Object> searchAccountHolder(SearchCriteria criteria) {

		logger.info("Executing searchAccountHolder");
		long start = System.currentTimeMillis();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		// Searching records with offset and limit
		List<AccountHolderDataBean> dataList = new ArrayList<AccountHolderDataBean>();
		String sql = SearchQueryBuilder.getAccountHolderSearchQuery(criteria);
		sql = sql.replace('"', ' ');
		logger.info("SearchAccountHolder query: " + sql);
		Query q = getEntityManager().createNativeQuery(sql);

		List<Object[]> resultList = (List<Object[]>) q.getResultList();
		if (resultList != null && resultList.size() > 0) {

		    for (Object[] row : resultList) {
		    AccountHolderDataBean bean = new AccountHolderDataBean();
		    bean.setCustomerId(String.valueOf(row[0]));
			bean.setCustomerName(String.valueOf(row[1]));
			bean.setAddress(String.valueOf(row[2]));
			bean.setOrderId(Long.valueOf(String.valueOf(row[3])));
			bean.setAgentId(String.valueOf(row[4]));
			bean.setLineItemId(Long.valueOf(String.valueOf(row[5])));
			bean.setAccountName(String.valueOf(row[6]));
			bean.setAcctHolderId(String.valueOf(row[7]));
			bean.setProductDetails(String.valueOf(row[8]));
			bean.setSsn(String.valueOf(row[9]));
			bean.setStatus(String.valueOf(row[10]));
			bean.setProviderId(String.valueOf(row[11]));
			bean.setCustomerSsn(String.valueOf(row[12]));
			
			dataList.add(bean);
		    }
		}
		resultMap.put("dataSets", dataList);

		logger.info("SearchAccountHolder took : " + (System.currentTimeMillis() - start) + "ms");
		return resultMap;
	}

	public Map<String, Object> searchCustomerDetails(SearchCriteria criteria) {
		logger.info("Executing searchCustomerWithAccountHolderDetails");
		long start = System.currentTimeMillis();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		// Searching records with offset and limit
		List<AccountHolderDataBean> dataList = new ArrayList<AccountHolderDataBean>();
		String sql = SearchQueryBuilder.getCustomerSearchQuerywithAccntHolderDetails(criteria);
		sql = sql.replace('"', ' ');
		logger.info("SearchCustomer with AccountHolder Details query: " + sql);
		Query q = getEntityManager().createNativeQuery(sql);

		List<Object[]> resultList = (List<Object[]>) q.getResultList();
		if (resultList != null && resultList.size() > 0) {

		    for (Object[] row : resultList) {
		    AccountHolderDataBean bean = new AccountHolderDataBean();
		    bean.setCustomerId(String.valueOf(row[0]));
			bean.setCustomerName(String.valueOf(row[1]));
			bean.setAddress(String.valueOf(row[2]));
			bean.setOrderId(Long.valueOf(String.valueOf(row[3])));
			bean.setAgentId(String.valueOf(row[4]));
			bean.setLineItemId(Long.valueOf(String.valueOf(row[5])));
			bean.setAccountName(String.valueOf(row[6]));
			bean.setAcctHolderId(String.valueOf(row[7]));
			bean.setProductDetails(String.valueOf(row[8]));
			bean.setSsn(String.valueOf(row[9]));
			bean.setStatus(String.valueOf(row[10]));
			bean.setProviderId(String.valueOf(row[11]));
			bean.setCustomerSsn(String.valueOf(row[12]));
						
			dataList.add(bean);
		    }
		}
		resultMap.put("dataSets", dataList);

		logger.info("SearchCustomerWithAccountHolderDetails took : " + (System.currentTimeMillis() - start) + "ms");
		return resultMap;
	}

	public Map<String, Object> searchOrderByAccntHolderDetails(SearchCriteria criteria) {
		logger.info("Executing searchOrderByAccntHolderDetails");
		long start = System.currentTimeMillis();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		// Searching records with offset and limit
		List<AccountHolderDataBean> dataList = new ArrayList<AccountHolderDataBean>();
		String sql = SearchQueryBuilder.getOrderSearchQuerywithAccntHolderDetails(criteria);
		sql = sql.replace('"', ' ');
		logger.info("SearchCustomer query: " + sql);
		Query q = getEntityManager().createNativeQuery(sql);

		List<Object[]> resultList = (List<Object[]>) q.getResultList();
		if (resultList != null && resultList.size() > 0) {

		    for (Object[] row : resultList) {
		    AccountHolderDataBean bean = new AccountHolderDataBean();
		    bean.setCustomerId(String.valueOf(row[0]));
			bean.setCustomerName(String.valueOf(row[1]));
			bean.setAddress(String.valueOf(row[2]));
			bean.setOrderId(Long.valueOf(String.valueOf(row[3])));
			bean.setAgentId(String.valueOf(row[4]));
			bean.setLineItemId(Long.valueOf(String.valueOf(row[5])));
			bean.setAccountName(String.valueOf(row[6]));
			bean.setAcctHolderId(String.valueOf(row[7]));
			bean.setProductDetails(String.valueOf(row[8]));
			bean.setSsn(String.valueOf(row[9]));
			bean.setStatus(String.valueOf(row[10]));
			bean.setProviderId(String.valueOf(row[11]));
			bean.setCustomerSsn(String.valueOf(row[12]));
						
			dataList.add(bean);
		    }
		}
		resultMap.put("dataSets", dataList);

		logger.info("SearchOrderByAccntHolderDetails took : " + (System.currentTimeMillis() - start) + "ms");
		return resultMap;
	}

}
