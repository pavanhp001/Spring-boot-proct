package com.AL.activity.lineitem.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.LineItemAttribute;
import com.AL.V.beans.entity.SalesOrder;


public enum ActivityUpdateStatusAttribute {
	INSTANCE;
	private static final Logger logger = Logger.getLogger(ActivityUpdateStatusAttribute.class);
	private static final String IS_STATUS_UPDATED = "is_status_updated";
	private static final String STATUS_UPDATE = "StatusUpdate";

	/**
	 * Method to set the "is_status_updated" attribute value to "no" for lineitems for which status is not being updated.
	 * This is req for DW to avoid duplicates in DW Status history table.
	 * @param lineItemIds
	 * @param order
	 */
	public void updateStatusAttribute(final List<String> lineItemIds, SalesOrder order) {
		if (order != null) {
			List<LineItem> liList = order.getLineItems();
			if (liList != null) {
				for (LineItem li : liList) {
					logger.info("ActivityUpdateStatusAttribute:updateStatusAttribute():LineItem: "
								+ li.getExternalId() + " :lineItemIds.size(): " + lineItemIds.size() + " :lineItemIds.toString(): " +
								lineItemIds.toString());
					String requestLineItemIdAsString = li.getExternalId().toString();
					if (!lineItemIds.contains(requestLineItemIdAsString)) {
						logger.debug("Setting attribute to 'no' for not updated lineitem : "+ li.getExternalId());
						Set<LineItemAttribute> attribs = li.getLineItemAttribute();
						if (attribs != null && attribs.size() > 0) {
							LineItemAttribute attr = getLineItemAttribute(attribs);
							if (attr != null) {
								attr.setValue("no");
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Method to set the "is_status_updated" attribute value to "no" for lineitems for which status is not being updated.
	 * This is req for DW to avoid duplicates in DW Status history table.
	 * @param lineItemIds
	 * @param order
	 */
	public Set<LineItemAttribute> getStatusAttributes(final List<String> lineItemIds, SalesOrder order) {
		Set<LineItemAttribute> statusAttributes = new HashSet<LineItemAttribute>();
		if (order != null) {
			List<LineItem> liList = order.getLineItems();
			if (liList != null) {
				for (LineItem li : liList) {
					logger.info("ActivityUpdateStatusAttribute:updateStatusAttribute():LineItem: "
								+ li.getExternalId() + " :lineItemIds.size(): " + lineItemIds.size() + " :lineItemIds.toString(): " +
								lineItemIds.toString());
					String requestLineItemIdAsString = li.getExternalId().toString();
					if (!lineItemIds.contains(requestLineItemIdAsString)) {
						logger.debug("Setting attribute to 'no' for not updated lineitem : "+ li.getExternalId());
						Set<LineItemAttribute> attribs = li.getLineItemAttribute();
						if (attribs != null && attribs.size() > 0) {
							LineItemAttribute attr = getLineItemAttribute(attribs);
							if (attr != null) {
								attr.setValue("no");
								statusAttributes.add(attr);
							}
						}
					}
				}
			}
		}
		return statusAttributes;
	}

	private LineItemAttribute getLineItemAttribute(
			Set<LineItemAttribute> attribs) {
		for(LineItemAttribute attr : attribs){
			if(attr.getSource().equalsIgnoreCase(STATUS_UPDATE) && attr.getName().equalsIgnoreCase(IS_STATUS_UPDATED)){
				return attr;
			}
		}
		return null;
	}
}
