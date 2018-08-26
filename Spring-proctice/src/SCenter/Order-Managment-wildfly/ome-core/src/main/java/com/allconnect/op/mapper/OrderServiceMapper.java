package com.AL.op.mapper;

import com.AL.op.ProvisionConstants.PersistEntityType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemPriceInfoType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.orderProvisioning.OpProductLineItem;
import com.AL.xml.v4.orderProvisioning.OrderProvisioningRequestDocument.OrderProvisioningRequest;
import com.AL.xml.v4.orderProvisioning.OrderQualificationRequest;
import static com.AL.op.ProvisionConstants.USD;

public enum OrderServiceMapper {
	
	INSTANCE;
	
	public OrderManagementRequestResponseDocument createOrmRequestResponseDoc(OrderProvisioningRequest opRequest, PersistEntityType entityType) {
		OrderManagementRequestResponseDocument ormRRDoc = OrderManagementRequestResponseDocument.Factory.newInstance();
		OrderManagementRequestResponse ormRR = ormRRDoc.addNewOrderManagementRequestResponse();
		ormRR.setCorrelationId(opRequest.getCorrelationId());
		ormRR.setTransactionId(opRequest.getTransactionId());
		Request request = ormRR.addNewRequest();
		request.setSalesContext(opRequest.getOpContext());
		request.setAgentId(opRequest.getAgentId());
		request.setOrderId(opRequest.getOrderId());
		OrderQualificationRequest oqRequest = (OrderQualificationRequest)opRequest.getRequest();
		LineItemCollectionType liCollection = request.addNewUpdatedLineItemInfo();
		OpProductLineItem productLineItem = oqRequest.getProductLineItem();
		if(entityType == PersistEntityType.PRODUCTLINEITEM) {
			LineItemType lineItem = liCollection.addNewLineItem();
			ormRR.setTransactionType(OrderManagementRequestResponse.TransactionType.UPDATE_LINE_ITEM);
			lineItem.setLineItemNumber(productLineItem.getLineItemNumber());
			lineItem.setExternalId(productLineItem.getLineItemExternalId());
			lineItem.setActiveDialogs(productLineItem.getActiveDialogs());
			lineItem.setCustomSelections(productLineItem.getCustomSelections());
			lineItem.setLineItemAttributes(productLineItem.getLineItemAttributes());
			lineItem.setSelectedFeatures(productLineItem.getSelectedFeatures());
			if(productLineItem.getPriceInfo() != null) {
				LineItemPriceInfoType lineItemPriceInfo = lineItem.addNewLineItemPriceInfo();
				lineItemPriceInfo.setBaseNonRecurringPrice(productLineItem.getPriceInfo().getBaseNonRecurringPrice());
				lineItemPriceInfo.setBaseRecurringPrice(productLineItem.getPriceInfo().getBaseRecurringPrice());
				lineItemPriceInfo.setBaseNonRecurringPriceUnits(USD);
				lineItemPriceInfo.setBaseRecurringPriceUnits(USD);
			}
		} else if(entityType == PersistEntityType.PROMOTIONLINEITEM) {
			//TODO MAP PROMO
		}
		return ormRRDoc;
	}
}
