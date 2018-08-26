package com.AL.pricing;

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;

import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.ResponseItemOme;
import com.AL.V.beans.LineItemPriceInfo;
import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.entity.SelectedFeatureValue;
import com.AL.xml.v4.ApplicableType;
import com.AL.xml.v4.FeatureValueType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemPriceInfoType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.PriceInfoType;
import com.AL.xml.v4.PricingRequestResponseDocument;
import com.AL.xml.v4.ProductBundleType;
import com.AL.xml.v4.ProductPromotionType;
import com.AL.xml.v4.ProductType;
import com.AL.xml.v4.ProviderSourceType;
import com.AL.xml.v4.SalesContextType;
import com.AL.xml.v4.SelectedFeaturesType.Features;

public class OrderPriceUtil {
	public static final String CALCULATE_ORDER_PRICE = "calculateOrderPrice";

	private static final Logger logger = Logger.getLogger(OrderPriceUtil.class);

	public static PricingRequestResponseDocument copyOrderToPricing(
			OrderType orderRequest, ResponseItemOme rio) {

		Random r = new Random();
		r.nextInt(1000000);

		String guid = rio.getCorrelationId();

		logger.debug("copyOrderToPricing(2):Preparing Pricing service request for the orderRequest: " + orderRequest.xmlText().toString());

		PricingRequestResponseDocument pricingRequest = PricingRequestResponseDocument.Factory
				.newInstance();

		// Set the pricing transaction id and type

		pricingRequest.addNewPricingRequestResponse();

		if(guid != null && guid.trim().length() > 0){
			pricingRequest.getPricingRequestResponse().setGUID(guid);
		}else{
			pricingRequest.getPricingRequestResponse().setGUID(String.valueOf(r.nextInt()));
		}
		pricingRequest.getPricingRequestResponse().setTransactionType(
				pricingRequest.getPricingRequestResponse().getTransactionType()
						.forString("calculateOrderPrice"));
		pricingRequest.getPricingRequestResponse().addNewRequest()
				.addNewPricingRequestOrderElement();
		OrderType pricingOrderType = pricingRequest.getPricingRequestResponse()
				.getRequest().getPricingRequestOrderElementArray(0);

		LineItemCollectionType orderLineItemCollection = orderRequest
				.getLineItems();
		List<LineItemType> orderItemList = orderLineItemCollection
				.getLineItemList();
		LineItemCollectionType pricingLineItemCollection = pricingOrderType
				.addNewLineItems();

		// Get the line item from Order Request and add it to pricing list
		for (LineItemType lineItem : orderItemList) {
			LineItemType priceLineItem = pricingLineItemCollection
					.addNewLineItem();
			ProviderSourceType srcType = priceLineItem
					.addNewProductDatasource();
			srcType.setStringValue(lineItem.getProductDatasource()
					.getStringValue());
			priceLineItem.setExternalId(lineItem.getExternalId());
			priceLineItem.setIsEventSelected(lineItem.getIsEventSelected());
			priceLineItem.setIsEventCompleted(lineItem.getIsEventCompleted());
			priceLineItem.setLineItemNumber(lineItem.getLineItemNumber());
			priceLineItem.setLineItemStatus(lineItem.getLineItemStatus());
			priceLineItem.setLineItemDetail(lineItem.getLineItemDetail());
			priceLineItem.setSelectedFeatures(lineItem.getSelectedFeatures());

			if (lineItem.getCustomSelections() != null) {
				priceLineItem.setCustomSelections(lineItem
						.getCustomSelections());
			}

			logger.debug("Setting LineItemPriceInfo in the Pricing Request!!!! for ExternalId: " + lineItem.getExternalId());
			priceLineItem.setLineItemPriceInfo(lineItem.getLineItemPriceInfo());
		}
		
		//set order external id to pricing request
		Long orderExternalId = (Long) rio.get(TaskContextParamEnum.orderExternalId.name());
		pricingOrderType.setExternalId(orderExternalId);
		
		pricingRequest.getPricingRequestResponse().getRequest()
				.setPricingRequestOrderElementArray(0, pricingOrderType);
		
		//set salescontext
		SalesContextType scType = (SalesContextType) rio.get(TaskContextParamEnum.salesContext.name());
		pricingRequest.getPricingRequestResponse().setSalesContext(scType);

		return pricingRequest;
	}

	/**
	 * This method will copy the required details from OrderRequest and paste it
	 * to PricingRequest
	 *
	 * @return
	 */
	public static PricingRequestResponseDocument copyOrderToPricing(
			OrderManagementRequestResponse orderRequest) {


		logger.debug("copyOrderToPricing(1):Preparing Pricing service request for the orderRequest: " + orderRequest.xmlText().toString());
		PricingRequestResponseDocument pricingRequest = PricingRequestResponseDocument.Factory
				.newInstance();

		String GUID = orderRequest.getCorrelationId();

		if ((GUID == null) || (GUID.length() == 0)) {
			GUID = System.nanoTime()+"";
		}

		// Set the pricing transaction id and type
		pricingRequest.addNewPricingRequestResponse().setGUID( GUID);
		pricingRequest.getPricingRequestResponse().setGUID(GUID);
		pricingRequest.getPricingRequestResponse().setTransactionType(
				pricingRequest.getPricingRequestResponse().getTransactionType()
						.forString("calculateOrderPrice"));
		pricingRequest.getPricingRequestResponse().addNewRequest()
				.addNewPricingRequestOrderElement();
		OrderType pricingOrderType = pricingRequest.getPricingRequestResponse()
				.getRequest().getPricingRequestOrderElementArray(0);

		LineItemCollectionType orderLineItemCollection = orderRequest
				.getRequest().getOrderInfo().getLineItems();
		List<LineItemType> orderItemList = orderLineItemCollection
				.getLineItemList();
		LineItemCollectionType pricingLineItemCollection = pricingOrderType
				.addNewLineItems();

		// Get the line item from Order Request and add it to pricing list
		for (LineItemType srcLineItemType : orderItemList) {
			LineItemType priceLineItem = pricingLineItemCollection
					.addNewLineItem();

			ProviderSourceType srcType = priceLineItem
					.addNewProductDatasource();

			try {
				srcType.setStringValue(srcLineItemType.getProductDatasource()
						.getStringValue());
			} catch (Exception e) {
				logger.error(e.getMessage());
			}

			priceLineItem
					.setLineItemNumber(srcLineItemType.getLineItemNumber());
			priceLineItem
					.setLineItemStatus(srcLineItemType.getLineItemStatus());
			priceLineItem
					.setLineItemDetail(srcLineItemType.getLineItemDetail());
			priceLineItem.setSelectedFeatures(srcLineItemType
					.getSelectedFeatures());
			if (srcLineItemType.getCustomSelections() != null) {
				priceLineItem.setCustomSelections(srcLineItemType
						.getCustomSelections());
			}

			if(srcLineItemType.getLineItemPriceInfo() != null){
				logger.debug("Setting LineItemPriceInfo in the Pricing Request!!!! for ExternalId: " + srcLineItemType.getExternalId());
				priceLineItem.setLineItemPriceInfo(srcLineItemType.getLineItemPriceInfo());
			}
		}
		pricingOrderType.setExternalId(orderRequest.getRequest().getOrderInfo().getExternalId());
		pricingRequest.getPricingRequestResponse().getRequest()
				.setPricingRequestOrderElementArray(0, pricingOrderType);

		return pricingRequest;
	}

	/**
	 * A method to decide whether product is internal or realtime based on
	 * external id. If external id starts with "RTS:" then it is a realtime
	 * product/promotion and if not then it is internal Product/Promotion
	 *
	 * @param srcLineItemType
	 * @return
	 */
	private static Boolean isDynamicProduct(LineItemType srcLineItemType) {
		Boolean isDynamic = Boolean.FALSE;
		String detailType = srcLineItemType.getLineItemDetail().getDetailType()
				.toString();
		String extId = "";
		if (detailType.equalsIgnoreCase("product")) {
			ProductType productType = srcLineItemType.getLineItemDetail()
					.getDetail().getProductLineItem();
			extId = productType.getExternalId();
		} else if (detailType.equalsIgnoreCase("productPromotion")) {

			ApplicableType appType = srcLineItemType.getLineItemDetail()
					.getDetail().getPromotionLineItem();
			ProductPromotionType promotionType = appType.getPromotion();
			extId = promotionType.getExternalId();

		} else if (detailType.equalsIgnoreCase("productBundle")) {
			ProductBundleType pbType = srcLineItemType.getLineItemDetail()
					.getDetail().getProductBundleLineItem();
			extId = pbType.getExternalId();
		}

		if (extId.toUpperCase().startsWith("RTS:")) {
			isDynamic = Boolean.TRUE;
		}

		return isDynamic;
	}

	/**
	 * This method will copy the required details from LineItemsCollection and
	 * paste it to PricingRequest
	 *
	 * @return
	 */
	public static PricingRequestResponseDocument copyLineItemsToPricing(
			LineItemCollectionType srcLIColl,
			PricingRequestResponseDocument pricingRequest) {
		// PricingRequestResponseDocument pricingRequest =
		// PricingRequestResponseDocument.Factory.newInstance();

		// Set the pricing transaction id and type
		// pricingRequest.addNewPricingRequestResponse().setTransactionId(orderRequest.getTransactionId());
		pricingRequest.getPricingRequestResponse().setTransactionType(
				pricingRequest.getPricingRequestResponse().getTransactionType()
						.forString("calculateOrderPrice"));
		pricingRequest.getPricingRequestResponse().addNewRequest()
				.addNewPricingRequestOrderElement();
		OrderType pricingOrderType = pricingRequest.getPricingRequestResponse()
				.getRequest().getPricingRequestOrderElementArray(0);

		List<LineItemType> orderItemList = srcLIColl.getLineItemList();
		LineItemCollectionType pricingLineItemCollection = pricingOrderType
				.addNewLineItems();

		// Get the line item from Order Request and add it to pricing list
		for (LineItemType lineItem : orderItemList) {
			LineItemType priceLineItem = pricingLineItemCollection
					.addNewLineItem();
			priceLineItem.setLineItemNumber(lineItem.getLineItemNumber());
			priceLineItem.setLineItemStatus(lineItem.getLineItemStatus());
			priceLineItem.setLineItemDetail(lineItem.getLineItemDetail());
			priceLineItem.setSelectedFeatures(lineItem.getSelectedFeatures());
		}
		pricingRequest.getPricingRequestResponse().getRequest()
				.setPricingRequestOrderElementArray(0, pricingOrderType);

		return pricingRequest;
	}

	/**
	 * This method is updating the actual price in SalesOrderBean before saving
	 * it to db which was calculated by Pricing
	 */
	public static SalesOrder updateLineItemPrice(SalesOrder salesOrderBean,
			PricingRequestResponseDocument newPriceRequest) {
		// OrderType updatedOrderType =
		// newPriceRequest.getPricingRequestResponse().getRequest().getPricingRequestOrderElementArray(0);

		if ((newPriceRequest != null)
				&& (newPriceRequest.getPricingRequestResponse() != null)
				&& (newPriceRequest.getPricingRequestResponse().getResponse() != null)
				&& (newPriceRequest.getPricingRequestResponse().getResponse()
						.getPricingResponseOrderElementArray(0) != null)) {

			OrderType updatedOrderType = newPriceRequest
					.getPricingRequestResponse().getResponse()
					.getPricingResponseOrderElementArray(0);
			List<LineItemType> lineItemListSrc = updatedOrderType
					.getLineItems().getLineItemList();
			logger.debug("lineItemListSrc Size: " + lineItemListSrc.size());

			List<LineItem> orgLineItems = salesOrderBean.getLineItems();
			if (orgLineItems != null && !orgLineItems.isEmpty()) {
				
				int i = 0;
				for (LineItem orgLineItemBean : orgLineItems) {
					logger.debug("orgLineItems Size: " + orgLineItems.size());
					// TODO Makesure that it is updating the price of the actual
					// LineItem, somekind of check is required before
					// Updating the price, like check by ExternalID
					// But possibilities are that there could be two lineitems
					// with
					// same ExternalID
					LineItemType srcLineItemType = lineItemListSrc.get(i++);

					if (srcLineItemType != null && srcLineItemType.getLineItemPriceInfo() != null ) {
						logger.debug("srcLineItemType NOT NULL");
						// String itemType =
						// srcLineItemType.getLineItemDetail().getDetailType().toString();
						LineItemPriceInfo pInfo = orgLineItemBean.getPrice();
						LineItemPriceInfoType pricingPriceInfoType = srcLineItemType.getLineItemPriceInfo();
						pInfo.setBaseNonRecurringPrice(pricingPriceInfoType.getBaseNonRecurringPrice());
						pInfo.setBaseRecurringPrice(pricingPriceInfoType.getBaseRecurringPrice());
						if(pricingPriceInfoType.getPricingDate() != null){
							pInfo.setPricingDate(srcLineItemType.getLineItemPriceInfo().getPricingDate());
						}
						try {
							Long pricingStatus = Long.valueOf(srcLineItemType
									.getLineItemPriceInfo()
									.getPriceInfoStatus().getStatusCode());
							pInfo.setPricingStatus(pricingStatus);
						} catch (Exception e) {
							logger.warn("unable to price");
						}

						if (orgLineItemBean != null) {
							orgLineItemBean.setPrice(pInfo);
						}
					}

					// Set Individual Feature Price Info
					if (srcLineItemType != null && srcLineItemType.getSelectedFeatures() != null) {
						if (srcLineItemType.getSelectedFeatures().getFeatures() != null) {
							logger.debug("START -- Setting FeaturePriceInfo from Pricing");
							Set<SelectedFeatureValue> selFeatureValues = orgLineItemBean.getSelectedFeatureValues();
							Features selectedFeatureItems = srcLineItemType.getSelectedFeatures().getFeatures();
							List<FeatureValueType> selectedFeatureValues = selectedFeatureItems.getFeatureValueList();

							for (FeatureValueType selFtr: selectedFeatureValues) {
								PriceInfoType priceInfo = selFtr.getPrice();
								for (SelectedFeatureValue sfv: selFeatureValues) {
									if (sfv.getExternalId().equalsIgnoreCase(selFtr.getExternalId())) {
										logger.debug("************************** Setting FeaturePriceValues for " + selFtr.getExternalId());
										sfv.getPrice().setBaseNonRecurringPrice(priceInfo.getBaseNonRecurringPrice());
										sfv.getPrice().setBaseRecurringPrice(priceInfo.getBaseRecurringPrice());
									}
								}
							}

							orgLineItemBean.setSelectedFeatureValues(selFeatureValues);
							logger.debug("DONE -- Setting FeaturePriceInfo from Pricing");
						}
					}
				}

				logger.debug("Updating Total Order Price");
				// Updating total order price from info provided by Pricing
				PriceInfoType orderTotalPriceInfo = updatedOrderType
						.getTotalPriceInfo();
				if (orderTotalPriceInfo != null) {
					salesOrderBean.getTotalPrice().setBaseNonRecurringPrice(
							orderTotalPriceInfo.getBaseNonRecurringPrice());
					salesOrderBean.getTotalPrice().setBaseRecurringPrice(
							orderTotalPriceInfo.getBaseRecurringPrice());
					logger.debug("DONE -- Updating Total Order Price");
				} else {
					logger.debug("Missing TotalPriceInfo from Pricing");
					salesOrderBean.getTotalPrice()
							.setBaseNonRecurringPrice(0.0);
					salesOrderBean.getTotalPrice().setBaseRecurringPrice(0.0);
					logger.debug("Setting TotalPriceInfo to 0.0");
				}
			}

		}
		return salesOrderBean;
	}

	/**
	 * This method will update the total price of the order
	 *
	 * @param srcOrderBean
	 * @param newPriceRequest
	 * @return
	 */
	public static SalesOrder updateTotalPriceOfOrder(SalesOrder srcOrderBean,
			PricingRequestResponseDocument newPriceRequest) {
		OrderType updatedOrderType = newPriceRequest
				.getPricingRequestResponse().getResponse()
				.getPricingResponseOrderElementArray(0);

		if (updatedOrderType != null) {
			PriceInfoType orderTotalPriceInfo = updatedOrderType
					.getTotalPriceInfo();
			if (orderTotalPriceInfo != null) {
				srcOrderBean.getTotalPrice().setBaseNonRecurringPrice(
						orderTotalPriceInfo.getBaseNonRecurringPrice());
				srcOrderBean.getTotalPrice().setBaseRecurringPrice(
						orderTotalPriceInfo.getBaseRecurringPrice());
			} else {
				logger.debug("**************************Missing TotalPriceInfo from Pricing***********************");
				srcOrderBean.getTotalPrice().setBaseNonRecurringPrice(0.0);
				srcOrderBean.getTotalPrice().setBaseRecurringPrice(0.0);
				logger.debug("**************************Setting TotalPriceInfo to 0.0***********************");
			}

		}
		return srcOrderBean;
	}

	/**
	 * This method will update the PriceInfo for LineItem from PricingResponse
	 * document. It will also check for validity of the lineitem before it
	 * updates the price by LineItemNumber. So LineItemNumber in XML should
	 * match the LineItemNumber in db otherwise it will not update the price.
	 *
	 * @param destLineItemBean
	 * @param newPriceRequest
	 * @return
	 */
	public static LineItem updateLineItemPriceInfo(LineItem destLineItemBean,
			PricingRequestResponseDocument newPriceRequest) {
		OrderType updatedOrderType = newPriceRequest
				.getPricingRequestResponse().getResponse()
				.getPricingResponseOrderElementArray(0);
		List<LineItemType> lineItemListSrc = updatedOrderType.getLineItems()
				.getLineItemList();
		for (LineItemType srcLineItemType : lineItemListSrc) {
			if (srcLineItemType.getLineItemNumber() == destLineItemBean
					.getLineItemNumber()) {
				// String itemType =
				// srcLineItemType.getLineItemDetail().getDetailType().toString();
				LineItemPriceInfo pInfo = destLineItemBean.getPrice();
				pInfo.setBaseNonRecurringPrice(srcLineItemType
						.getLineItemPriceInfo().getBaseNonRecurringPrice());
				pInfo.setBaseRecurringPrice(srcLineItemType
						.getLineItemPriceInfo().getBaseRecurringPrice());
				pInfo.setPricingDate(srcLineItemType.getLineItemPriceInfo()
						.getPricingDate());
				pInfo.setPricingStatus(Long.valueOf(srcLineItemType
						.getLineItemPriceInfo().getPriceInfoStatus()
						.getStatusCode()));
				destLineItemBean.setPrice(pInfo);
			}
		}
		return destLineItemBean;
	}

}
