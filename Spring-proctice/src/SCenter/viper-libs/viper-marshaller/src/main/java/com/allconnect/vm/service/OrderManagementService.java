package com.A.vm.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.xmlbeans.impl.piccolo.xml.EntityManager;

import com.A.V.beans.entity.AgentBean;
import com.A.V.beans.entity.Consumer;
import com.A.V.beans.entity.LineItem;
import com.A.V.beans.entity.SalesOrder;
import com.A.V.beans.entity.SalesOrderContext;
import com.A.V.beans.entity.StatusRecordBean;
import com.A.V.beans.entity.User;
import com.A.vm.vo.OrderChangeValueObject;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.OrderManagementRequestResponseDocument;
import com.A.xml.v4.PricingRequestResponseDocument;
import com.A.xml.v4.ProductEnterpriseRequestDocument;
import com.A.xml.v4.ProductEnterpriseResponseDocument;

public interface OrderManagementService {


	Boolean saveLineItemStatus(final LineItem lineItemBean);

	StatusRecordBean updateLineItemStatus(final SalesOrder orderBean,
			final OrderChangeValueObject orderChangeValueObject);

	StatusRecordBean createNewStatus(final LineItem lineItemBean,
			final OrderChangeValueObject orderChangeValueObject);

	AgentBean getAgent(final EntityManager em, final String agentId);

 

	PricingRequestResponseDocument prepareOrderForPricing(
			final OrderManagementRequestResponseDocument orderDocument);

	void broadcast(String strToBroadcastOriginal);

	void saveNewLineItems(final String agentId, final SalesOrder salesOrder);

	SalesOrder findOrderById(final Long orderId);
	
	SalesOrder findOrderById(final Long orderId, boolean includeAccountHolders);

	public Boolean updateSalesOrder(final SalesOrder salesOrder);

	/**
	 * Added a new method just to save the sales order level status and status history <br>
	 * to avoid stale state issue
	 * @param salesOrder
	 * @return boolean
	 */
	public boolean updateSalesOrderStatus(final SalesOrder salesOrder);
	
	public Map<String, String> validateSelectedFeatures(
			List<LineItemType> lineItemTypeList);

	public StatusRecordBean createCurrentStatus(
			PricingRequestResponseDocument pricingResponse,
			SalesOrder salesOrder);

	public void saveSalesContext(Set<SalesOrderContext> socList);

	public ProductEnterpriseRequestDocument prepareProductServiceRequest(
			OrderManagementRequestResponseDocument orderDoc);

	public ProductEnterpriseRequestDocument prepareProductServiceRequest(OrderChangeValueObject valueObject);
	public Boolean validateProductResponseForFatalError(
			ProductEnterpriseResponseDocument productResponseDoc);

	public Map<String, String> copyLineItemInfoFromProductToOrder(
			ProductEnterpriseResponseDocument productResponse,
			OrderManagementRequestResponseDocument orderDoc);

	public Long getProductUniqueId(
			ProductEnterpriseResponseDocument productResponse,
			OrderManagementRequestResponseDocument orderDoc);

	public void copyPromotionUniqueId(
			ProductEnterpriseResponseDocument productResponse,
			OrderManagementRequestResponseDocument orderDoc);

	public Map<String, String> copyProductUniqueIdFromProductToOrder(
			ProductEnterpriseResponseDocument productResponse,
			OrderManagementRequestResponseDocument orderDoc);
	public void copyPromotionUniqueId(OrderChangeValueObject valueObject, SalesOrder salesOrder);

	public boolean isProductExist(OrderChangeValueObject valueObject);
	
	public Map<String, String> validateAddressAndBillingRef(
			SalesOrder orderBean, List<LineItemType> lineItemTypeList, Consumer customer);
	
	public Map<String,String> validateAddressAndBillingRef(SalesOrder orderBean,List<LineItemType> lineItemTypeList);

	public User getAgent(final String agentId);
}
