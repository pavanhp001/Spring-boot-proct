package com.A.Vdao.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.A.V.beans.entity.LineItem;
import com.A.V.beans.entity.SalesOrder;

public interface LineItemDao {

	/**
	 * @param lineItemBeanList
	 *            List of line items to be removed
	 * @param em
	 *            Entity Manager that will perform the remove of the line items
	 */
	void remove(final List<LineItem> lineItemBeanList);

	Boolean merge(final LineItem lineItemBean);

	public Boolean persist(final LineItem lineItemBean);
	
	public Boolean saveNewLineItems(final String agentId,  final SalesOrder salesOrder );

	public LineItem getLineItem(final Integer lineItemNumber);
	public Long getNextExternalId();
	
	public List<LineItem> getLineItemsByProviderId(final String providerId, final String liStatus, final Date reqInstallDate); 

	/**
	 * Responsible to retrieve the line item status, based on RTIM confirmation number
	 * @param confirmationNumber
	 * @return resultMap
	 */
	public Map<String, String> getLineItemByProviderConfirmationNumber(final String confirmationNumber);
}
