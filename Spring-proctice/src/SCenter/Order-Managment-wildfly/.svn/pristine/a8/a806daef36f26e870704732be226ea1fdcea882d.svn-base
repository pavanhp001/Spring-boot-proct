package com.AL.util.service;

import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.AL.enums.LineItemStatus;
import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.LineItemAttribute;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.entity.SalesOrderContext;

public class OrderManagementServiceUtil {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(OrderManagementServiceUtil.class);

	private static final String SUBMITTED_STATUS = "submitted";
	private static final String STATUS_UPDATE = "StatusUpdate";
	private static final String IS_STATUS_UPDATED = "is_status_updated";
	
	/**
	 * Validate the requested status change with the current line item status. 
	 * If its valid return that line item, else NULL
	 * 
	 * @param pLineItemExtId
	 * @param pNewStatus
	 * @param pSalesOrder
	 * @return line item
	 */
	public static LineItem getLineitemOnValidStatusChange(long pLineItemExtId, String pNewStatus, SalesOrder pSalesOrder){
		LineItem lineItem = findLineItemFromSalesOrder(pLineItemExtId, pSalesOrder);
		if(null != lineItem && null != lineItem.getCurrentStatus() 
				&& lineItem.getCurrentStatus().getStatus().equalsIgnoreCase(LineItemStatus.cancelled_removed.name()) 
				&& pNewStatus.equalsIgnoreCase(SUBMITTED_STATUS)){
			return null;
		}
		return lineItem;
	}
	
	/**
	 * Find the required line item from sales order object
	 * @param pLineItemExtId
	 * @param pSalesOrder
	 * @return line item
	 */
	public static LineItem findLineItemFromSalesOrder(long pLineItemExtId, SalesOrder pSalesOrder){
		if (pSalesOrder.getLineItems() != null) {
			for (LineItem li : pSalesOrder.getLineItems()) {
				if (li.getExternalId().equals(pLineItemExtId)) {
					return li;
				}
			}
		}
		return null;
	}
	
	/**
	 * Update the line item attributes to 'YES' or 'NO' for DWME when there is line item update process
	 * @param attrIbuteValue
	 * @param liAttributes
	 */
	public static void updateLineItemAttributes(String attrIbuteValue, Set<LineItemAttribute> liAttributes){
		if(liAttributes == null || liAttributes.size() == 0){
			LOGGER.debug("Adding IS_STATUS_UPDATED attribute for updated lineitem");
			LineItemAttribute attribute = new LineItemAttribute();
			attribute.setName(IS_STATUS_UPDATED);
			attribute.setSource(STATUS_UPDATE);
			attribute.setValue(attrIbuteValue);
			
			liAttributes.add(attribute);
		}
		else{
			LineItemAttribute attribute = getLineItemAttribute(liAttributes);
			if(attribute != null){
				LOGGER.debug("Updating IS_STATUS_UPDATED attribute for updated lineitem");
				attribute.setValue(attrIbuteValue);
				liAttributes.add(attribute);
			}else{
				LOGGER.debug("Adding IS_STATUS_UPDATED attribute for updated lineitem");
				attribute = new LineItemAttribute();
				attribute.setName(IS_STATUS_UPDATED);
				attribute.setSource(STATUS_UPDATE);
				attribute.setValue(attrIbuteValue);
				liAttributes.add(attribute);
			}
		}
	}
	
	/**
	 * Builds sales order context for external process if there is no input from request
	 * @param salesOrder
	 */
	public static void buildSalesOrderContextForExtPorcess(SalesOrder salesOrder, Set<SalesOrderContext> socFromRequest){

		//Filter duplicate and use new one with new value
		Iterator<SalesOrderContext> oldContexts = salesOrder.getSalesOrderContexts().iterator();
		while (oldContexts.hasNext()) {
			SalesOrderContext oldCtx = oldContexts.next();
			for (SalesOrderContext newCtx : socFromRequest) {
			    	String oldEntyName = oldCtx.getEntityName();
			    	String newEntyName = newCtx.getEntityName();
			    	String oldName = oldCtx.getName();
			    	String newName = newCtx.getName();
				if (oldEntyName.equalsIgnoreCase(newEntyName) & oldName.equalsIgnoreCase(newName)) {
					oldContexts.remove();
				}
			}
		}
		salesOrder.getSalesOrderContexts().addAll(socFromRequest);
	}
	
	/**
	 * Find the line item attribute that has 'is_status_updated'
	 * @param liAttributes
	 * @return line item attribute
	 */
	private static LineItemAttribute getLineItemAttribute(Set<LineItemAttribute> liAttributes) {
		if(liAttributes != null && liAttributes.size() > 0){
			for(LineItemAttribute attrib : liAttributes){
				if(attrib.getSource().equalsIgnoreCase(STATUS_UPDATE) && attrib.getName().equalsIgnoreCase(IS_STATUS_UPDATED)){
					return attrib;
				}
			}
		}
		return null;
	}
}
