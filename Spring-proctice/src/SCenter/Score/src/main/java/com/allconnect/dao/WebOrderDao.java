package com.AL.dao;

import java.util.List;

import com.AL.domain.WebOrder;

/**
 * @author mnagineni
 *
 */
public interface WebOrderDao
{
	/**
	 * Method to get Sales WebOrders using Order Id
	 * 
	 * @param orderId
	 * @return
	 */
	List<WebOrder> getWebOrders(String orderId);
	

	/**
	 * @param ucid
	 * @return
	 */
	public List<String> getOrderIdsWithUCID(String ucid);

	
	/**
	 * @param guid
	 * @return
	 */
	//public List<String> getOrderIdsWithGUID(String guid);
	
	/**
	 * @param lineItemId
	 * @return
	 */
	public List<String> getOrderIdsWithLineItemId(String lineItemId);
	
}
