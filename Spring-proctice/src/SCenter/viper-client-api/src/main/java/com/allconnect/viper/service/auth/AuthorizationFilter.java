package com.A.V.service.auth;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.A.xml.v4.LineItemStatusCodesType;
import com.A.xml.v4.AttributeDetailType;
import com.A.xml.v4.AttributeEntityType;
import com.A.xml.v4.LineItemAttributeType;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.ObjectFactory;
import com.A.xml.v4.OrderManagementRequestResponse;
import com.A.xml.v4.OrderType;

public enum AuthorizationFilter {

	INSTANCE;
	
	private final static Logger logger = Logger.getLogger(AuthorizationFilter.class);

	public void removeDeleted(final OrderManagementRequestResponse orderReqRes) {

		if ((orderReqRes != null) && (orderReqRes.getResponse() != null)
				&& (orderReqRes.getResponse().getOrderInfo() != null)
				&& (orderReqRes.getResponse().getOrderInfo().size() > 0)) {
			List<OrderType> orderList = orderReqRes.getResponse()
					.getOrderInfo();

			removeDeleted(orderList);
		}
	}

	public void removeDeleted(final OrderType orderType) {
		if (orderType != null && orderType.getExternalId() != 0) {

			if (orderType.getLineItems() != null) {
				List<LineItemType> listLineItemList = orderType.getLineItems()
						.getLineItem();

				if (listLineItemList != null) {

					Iterator<LineItemType> it = listLineItemList.iterator();

					while (it.hasNext()) {
						LineItemType lineItem = (LineItemType) it.next();

						if (lineItem != null) {

							if (lineItem.getLineItemStatus() != null && (LineItemStatusCodesType.CANCELLED_REMOVED
									.equals(lineItem.getLineItemStatus().getStatusCode()) ||
									LineItemStatusCodesType.PROCESSING_CANCELLED.equals(lineItem.getLineItemStatus().getStatusCode()))) {
								
								logger.debug("filtering deleted:"+orderType.getExternalId()+" lineItem:"+lineItem.getExternalId());
								it.remove();
							}
						}
					}

				}
			}
		}

	}

	public void removeDeleted(final List<OrderType> orderList) {

		for (OrderType orderType : orderList) {
			removeDeleted(orderType);
		}
	}

	public void filterShowAuthorizedView(final List<OrderType> orderTypeList,
			Map<String, Object> roles, final String ACTIVE_PROVIDER) {

		for (OrderType orderType : orderTypeList) {
			filterShowAuthorizedView(orderType, roles, ACTIVE_PROVIDER);
		}

	}

	public void filterShowAuthorizedView(final OrderType orderType,
			Map<String, Object> roles, final String ACTIVE_PROVIDER) {
		
		logger.debug("filterShowAuthorizedView: ProviderId : "+ACTIVE_PROVIDER);
		if (orderType != null && orderType.getExternalId() != 0) {

			List<LineItemType> listLineItemList = orderType.getLineItems()
					.getLineItem();

			Iterator<LineItemType> it = listLineItemList.iterator();

			while (it.hasNext()) {
				LineItemType lineItem = (LineItemType) it.next();
				
				validateProduct(lineItem);

				if ("*".equals(ACTIVE_PROVIDER)) {
					continue;
				}

				if (((lineItem.getPartnerExternalId() == null))
						|| (!(lineItem.getPartnerExternalId()
								.equals(ACTIVE_PROVIDER)))) {
					logger.debug("current context:"+ACTIVE_PROVIDER+" filtering blocked provider:"+lineItem.getPartnerExternalId()+" order:"+orderType.getExternalId()+" lineItem:"+lineItem.getExternalId());
					it.remove();
				}

			}

		}

	}

	public void validateProduct(LineItemType lit) {

		ObjectFactory of = new ObjectFactory();
		if (lit == null) {
			return;
		}

		if (lit.getLineItemDetail() == null) {
			lit.setLineItemDetail(of.createLineItemDetailType());
		}

		if (lit.getLineItemDetail().getDetail() == null) {
			lit.getLineItemDetail().setDetail(
					of.createOrderLineItemDetailTypeType());
		}

		if (lit.getLineItemDetail().getDetail().getProductLineItem() == null) {
			lit.getLineItemDetail().getDetail()
					.setProductLineItem(of.createProductType());
		}

		if (lit.getLineItemDetail().getDetail().getProductLineItem()
				.getProvider() == null) {
			lit.getLineItemDetail().getDetail().getProductLineItem()
					.setProvider(of.createProviderType());
		}

		if (StringUtils.isEmpty(lit.getLineItemDetail().getDetail()
				.getProductLineItem().getProvider().getExternalId())) {
			lit.getLineItemDetail().getDetail().getProductLineItem()
					.getProvider().setExternalId(lit.getPartnerExternalId());
		}

		if (StringUtils.isEmpty(lit.getLineItemDetail().getDetail()
				.getProductLineItem().getProvider().getName())) {
			lit.getLineItemDetail().getDetail().getProductLineItem()
					.getProvider().setName(getAccordProdDesc(lit));
		}
		
		 
		String accordPlanDesc = getAccordProdDesc(lit);
		String accordPlanId = getAccordPlanId(lit);
		
		if ( !StringUtils.isEmpty(accordPlanDesc)) {
			lit.getLineItemDetail().getDetail().getProductLineItem().setName( accordPlanDesc);
		}
		
		
		if ( !StringUtils.isEmpty(accordPlanId)) {
			lit.getLineItemDetail().setProductUniqueId(
					Long.valueOf(getAccordPlanId(lit)));
		}

	}

	public static String getAccordProdDesc(LineItemType lit) {
		return getSelectedAttribute(lit, "PROD_DESC");
	}

	public static String getAccordPlanId(LineItemType lit) {
		return getSelectedAttribute(lit, "SRVC_TYPE_ID");
	}

	public static String getSelectedAttribute(LineItemType lit, final String id) {

		LineItemAttributeType liAttributes = lit.getLineItemAttributes();
		if (liAttributes == null) {
			return "";
		}

		List<AttributeEntityType> attibuteEntityList = liAttributes.getEntity();
		for (int i = 0; i < attibuteEntityList.size(); i++) {
			AttributeEntityType attrEntity = attibuteEntityList.get(i);
			List<AttributeDetailType> attrDetailList = attrEntity
					.getAttribute();
			for (int k = 0; k < attrDetailList.size(); k++) {
				AttributeDetailType attrDetailType = attrDetailList.get(k);
				String name = attrDetailType.getName();
				String value = attrDetailType.getValue();
				if (name.equals(id)) {
					return value;
				}
			}
		}

		return "";
	}

	public void buildContextView(final List<OrderType> orderList,
			Map<String, Object> roles, final String ACTIVE_PROVIDER) {

		if (roles.containsKey("ADMIN") || roles.containsKey("SUPER")
				|| (ACTIVE_PROVIDER == null)) {
			return;
		}

		for (OrderType orderType : orderList) {
			filterShowAuthorizedView(orderType, roles, ACTIVE_PROVIDER);
		}

	}

	public void buildContextView(
			final OrderManagementRequestResponse orderReqRes,
			Map<String, Object> roles, final String ACTIVE_PROVIDER) {

		if (roles.containsKey("ADMIN") || roles.containsKey("SUPER")
				|| (ACTIVE_PROVIDER == null)) {
			return;
		}

		if ((orderReqRes != null) && (orderReqRes.getResponse() != null)
				&& (orderReqRes.getResponse().getOrderInfo() != null)
				&& (orderReqRes.getResponse().getOrderInfo().size() > 0)) {
			List<OrderType> orderList = orderReqRes.getResponse()
					.getOrderInfo();

			for (OrderType orderType : orderList) {
				filterShowAuthorizedView(orderType, roles, ACTIVE_PROVIDER);
			}
		}

	}

}