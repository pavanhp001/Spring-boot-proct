package com.AL.pricing;

import java.util.Calendar;
import java.util.List;

import com.AL.xml.v4.LineItemDetailType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.PriceInfoType;
import com.AL.xml.v4.PricingRequestResponseDocument;
import com.AL.xml.v4.PricingRequestResponseDocument.PricingRequestResponse;
import com.AL.xml.v4.PricingRequestResponseDocument.PricingRequestResponse.Request;
import com.AL.xml.v4.PricingRequestResponseDocument.PricingRequestResponse.Response;
import com.AL.xml.v4.StatusType;

public class DummyPricingEngine {
	public static PricingRequestResponseDocument processOrderPrice(
			PricingRequestResponseDocument oldPriceRequest) {
		PricingRequestResponseDocument updatedDoc = PricingRequestResponseDocument.Factory
				.newInstance();
		if (oldPriceRequest != null) {
			Request req = oldPriceRequest.getPricingRequestResponse()
					.getRequest();
			// Response req =
			// oldPriceRequest.getPricingRequestResponse().getResponse();
			OrderType orderType = req.getPricingRequestOrderElementArray(0);
			double ordNonRecPrice = 0.0;
			double ordRecPrice = 0.0;
			if (orderType != null) {
				List<LineItemType> lineItemTypeList = orderType.getLineItems()
						.getLineItemList();
				for (LineItemType lineItemType : lineItemTypeList) {
					lineItemType.addNewLineItemPriceInfo();
					LineItemDetailType detailType = lineItemType
							.getLineItemDetail();

					if ((detailType != null) && ( detailType.getDetailType() != null)) {
						String dType = detailType.getDetailType().toString();
						if (dType.equalsIgnoreCase("promotion")) {
							// PromotionType pi
							// =detailType.getDetail().getPromotionLineItem().getPromotion();
							// pi.setPriceValue((float)111.11);
							lineItemType.getLineItemPriceInfo()
									.setBaseNonRecurringPrice(123.33);
							ordNonRecPrice = ordNonRecPrice + 123.33;
							lineItemType.getLineItemPriceInfo()
									.setBaseRecurringPrice(123.34);
							ordRecPrice = ordRecPrice + 123.34;
							updatePriceStatus(lineItemType);
						} else if (dType.equalsIgnoreCase("marketitem")) {
							/*
							 * MarketItemType mi =
							 * detailType.getDetail().getMarketItemLineItem();
							 * mi
							 * .getPriceInfo().setBaseNonRecurringPrice(99.99);
							 * mi.getPriceInfo().setBaseRecurringPrice(89.89);
							 */

							lineItemType.getLineItemPriceInfo()
									.setBaseNonRecurringPrice(245.89);
							ordNonRecPrice = ordNonRecPrice + 245.89;
							lineItemType.getLineItemPriceInfo()
									.setBaseRecurringPrice(245.99);
							ordRecPrice = ordRecPrice + 245.99;
							updatePriceStatus(lineItemType);

						} else if (dType.equalsIgnoreCase("bundle")) {
							/*
							 * BundleType bt =
							 * detailType.getDetail().getBundleLineItem();
							 * bt.getPriceInfo
							 * ().setBaseNonRecurringPrice(123.12);
							 * bt.getPriceInfo().setBaseRecurringPrice(233.23);
							 */

							lineItemType.getLineItemPriceInfo()
									.setBaseNonRecurringPrice(355.89);
							ordNonRecPrice = ordNonRecPrice + 355.89;
							lineItemType.getLineItemPriceInfo()
									.setBaseRecurringPrice(355.99);
							ordRecPrice = ordRecPrice + 355.99;
							updatePriceStatus(lineItemType);
						}
					}
				}
			}

			if (orderType != null) {
				PriceInfoType orderTotalPInfo = orderType
						.addNewTotalPriceInfo();
				orderTotalPInfo.setBaseNonRecurringPrice(ordNonRecPrice);
				orderTotalPInfo.setBaseRecurringPrice(ordRecPrice);

				if (updatedDoc != null) {
					PricingRequestResponse prr = updatedDoc
							.addNewPricingRequestResponse();
					StatusType statusType = prr.addNewStatus();
					statusType.addNewProcessingMessages();
					statusType.setStatusMsg("WARN_ORDER_NONE_PRICED");
					Response res = updatedDoc.getPricingRequestResponse()
							.addNewResponse();
					res.setPricingResponseOrderElementArray(req
							.getPricingRequestOrderElementArray());
				}
			}
		}
		return updatedDoc;
	}

	private static void updatePriceStatus(LineItemType lineItemType) {
		Calendar cal = Calendar.getInstance();
		lineItemType.getLineItemPriceInfo().setPricingDate(cal);
		if (lineItemType.getLineItemPriceInfo().getPriceInfoStatus() != null) {
			lineItemType.getLineItemPriceInfo().getPriceInfoStatus()
					.setStatusCode(100);// GOOD
			lineItemType.getLineItemPriceInfo().getPriceInfoStatus()
					.setStatusMsg("PRICED SUCCESSFULLY");
		} else {
			StatusType status = lineItemType.getLineItemPriceInfo()
					.addNewPriceInfoStatus();
			status.setStatusCode(100);// GOOD
			status.setStatusMsg("PRICED SUCCESSFULLY");
		}
	}
}
