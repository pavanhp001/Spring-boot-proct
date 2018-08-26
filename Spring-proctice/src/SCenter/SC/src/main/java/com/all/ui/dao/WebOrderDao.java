package com.AL.ui.dao;


import com.AL.ui.domain.WebOrder;

/**
 * @author mnagineni
 *
 */
public interface WebOrderDao
{
	/**
	 * Method to save Sales WebOrder
	 * 
	 * @param webOrder
	 */
	void saveOrder(WebOrder webOrder);
}
