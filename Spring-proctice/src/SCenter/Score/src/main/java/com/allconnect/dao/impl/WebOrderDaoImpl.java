package com.AL.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.AL.dao.WebOrderDao;
import com.AL.domain.WebOrder;

/**
 * @author mnagineni
 *
 */
@Component
public class WebOrderDaoImpl extends BaseTransactionalJpaDao implements WebOrderDao 
{

	private static final Logger logger = Logger.getLogger(WebOrderDaoImpl.class);
	private static final String FIND_ORDER_BY_ORDER = "select u FROM WebOrder as u WHERE u.orderId = :id order by id ASC";
	private static final String FIND_ORDER_ID_BY_UCID  = "select distinct(u.orderId) FROM WebOrder as u WHERE u.ucid = :ucid";
	//private static final String FIND_ORDER_ID_BY_GUID  = "select distinct(u.orderId) FROM WebOrder as u WHERE u.guid = :guid";
	private static final String FIND_ORDER_ID_BY_LineItemId  = "select distinct(u.orderId) FROM WebOrder as u WHERE u.lineItemId = :lineItemId";

	/* (non-Javadoc)
	 * @see com.AL.dao.WebOrderDao#getOrderIdsWithUCID(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<String> getOrderIdsWithUCID(String ucid) 
	{
		logger.info("Fetching Order Ids for UCID: " + ucid);
		
		if (getEntityManager() != null) 
		{
			try 
			{
				Query query = getEntityManager().createQuery(FIND_ORDER_ID_BY_UCID);
				query.setParameter("ucid", ucid);
				List<String> orderList = (List<String>) query.getResultList();
				return orderList;
			}
			catch (Exception e) 
			{
				try 
				{

					logger.debug("Retrying getOrderWithUCID for exception " + e);

					Query query = getEntityManager().createQuery(FIND_ORDER_ID_BY_UCID);
					query.setParameter("ucid", ucid);
					List<String> orderList = (List<String>) query.getResultList();
					return orderList;
				} 
				catch (Exception ex)
				{
					logger.error("Failed to get Order using UCID "+ ex);
				}
			}
		}

		return null;
	}

	
	/* (non-Javadoc)
	 * @see com.AL.dao.WebOrderDao#getWebOrders(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<WebOrder> getWebOrders(String orderId) 
	{
		logger.info("Fetching WebOrders for Order Id: " + orderId);
		
		if (getEntityManager() != null) 
		{
			try 
			{
				Query query = getEntityManager().createQuery(FIND_ORDER_BY_ORDER);
				query.setParameter("id", orderId);
				List<WebOrder> orderList = (List<WebOrder>) query.getResultList();
				return orderList;
			} 
			catch (Exception e) 
			{
				logger.debug("Retrying getWebOrders for exception " + e);
				try 
				{
					Query query = getEntityManager().createQuery(FIND_ORDER_BY_ORDER);
					query.setParameter("id", orderId);
					List<WebOrder> orderList = (List<WebOrder>) query.getResultList();
					return orderList;
				} 
				catch (Exception ex)
				{
					logger.error("Failed to get WebOrders "+ ex);
				}
			}
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see com.AL.dao.WebOrderDao#getOrderIdsWithGUID(java.lang.String)
	 */
	/*@Override
	public List<String> getOrderIdsWithGUID(String guid) {
		logger.info("Fetching Order Ids for GUID: " + guid);
		
		if (getEntityManager() != null) 
		{
			try 
			{
				Query query = getEntityManager().createQuery(FIND_ORDER_ID_BY_GUID);
				query.setParameter("guid", guid);
				List<String> orderList = (List<String>) query.getResultList();
				return orderList;
			}
			catch (Exception e) 
			{
				try 
				{

					logger.debug("Retrying getOrderWithUCID for exception " + e);

					Query query = getEntityManager().createQuery(FIND_ORDER_ID_BY_UCID);
					query.setParameter("guid", guid);
					List<String> orderList = (List<String>) query.getResultList();
					return orderList;
				} 
				catch (Exception ex)
				{
					logger.error("Failed to get Order using UCID "+ ex);
				}
			}
		}

		return null;
	}
	*/
	/* (non-Javadoc)
	 * @see com.AL.dao.WebOrderDao#getOrderIdsWithLineItemId(java.lang.String)
	 */
	@Override
	public List<String> getOrderIdsWithLineItemId(String lineItemId) {
		logger.info("Fetching Order Ids for lineItemId: " + lineItemId);
		
		if (getEntityManager() != null) 
		{
			try 
			{
				Query query = getEntityManager().createQuery(FIND_ORDER_ID_BY_LineItemId);
				query.setParameter("lineItemId", lineItemId);
				List<String> orderList = (List<String>) query.getResultList();
				return orderList;
			}
			catch (Exception e) 
			{
				try 
				{

					logger.debug("Retrying getOrderWithlineItemId for exception " + e);

					Query query = getEntityManager().createQuery(FIND_ORDER_ID_BY_LineItemId);
					query.setParameter("lineItemId", lineItemId);
					List<String> orderList = (List<String>) query.getResultList();
					return orderList;
				} 
				catch (Exception ex)
				{
					logger.error("Failed to get Order using lineItemId "+ ex);
				}
			}
		}

		return null;
	}


}
