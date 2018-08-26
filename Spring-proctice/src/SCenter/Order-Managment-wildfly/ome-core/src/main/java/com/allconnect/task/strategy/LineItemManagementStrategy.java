package com.AL.task.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.AL.pricing.DummyPricingEngine;
import com.AL.pricing.OrderPriceUtil;
import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.Vdao.dao.LineItemDao;
import com.AL.vm.util.converter.marshall.MarshallLineItem;
import com.AL.vm.util.converter.unmarshall.UnmarshallLineItem;
import com.AL.vm.vo.OrderChangeValueObject;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.PricingRequestResponseDocument;

public class LineItemManagementStrategy {

	private static Logger logger = Logger
			.getLogger(LineItemManagementStrategy.class);

	public static LineItem updatePriceInfo(LineItem destLineItem,
			PricingRequestResponseDocument pricingRes) {
		return null;
	}

	public static void updateLineItem(SalesOrder updateableOrderBean,
			final MarshallLineItem marshallLineItem,
			LineItemCollectionType newLineItemContainer) {
		// Prepare PricingRequestResponse
		PricingRequestResponseDocument pricingReqRes = PricingRequestResponseDocument.Factory
				.newInstance();
		pricingReqRes.addNewPricingRequestResponse();
		// Add Exisitng LineItems and new Lineitems
		pricingReqRes = LineItemManagementStrategy
				.addExistingLIToPricingRequest(marshallLineItem,
						updateableOrderBean, pricingReqRes);
		pricingReqRes = LineItemManagementStrategy.addNewLIToPricingRequest(
				newLineItemContainer, pricingReqRes);

		logger.debug("Sending message to Pricing Service...");
		// PricingRequestResponseDocument newPricingRequest =
		// OmePricingMessageSender.getPricedOrder(pricingReqRes.toString());
		PricingRequestResponseDocument newPricingRequest = DummyPricingEngine
				.processOrderPrice(pricingReqRes);
		logger.debug("Received Response from Pricing Service..."
				+ newPricingRequest.toString());

		// OrderChangeValueObject orderStatus =
		// UnmarshallLineItem.Builder.buildUpdate( newLineItem,
		// getEntityManagerLogical() );

	}

	/**
	 * This method will retrieve existing LineItems from Order, Marshall it and
	 * add it to PricingRequestResponse object.
	 * 
	 * @param existingOrderBean
	 * @param pricingReqRes
	 * @return
	 */
	public static PricingRequestResponseDocument addExistingLIToPricingRequest(
			MarshallLineItem marshallLineItem, SalesOrder existingOrderBean,
			PricingRequestResponseDocument pricingReqRes) {
		List<LineItem> liList = existingOrderBean.getLineItems();
		if (liList != null) {
			logger.debug("Existing lineitems in Order : " + liList.size());

			LineItemCollectionType liColl = marshallLineItem.build(liList);
			logger.debug("Marshalled Existing LineItems... "
					+ liColl.toString());
			if (liColl != null) {
				pricingReqRes = OrderPriceUtil.copyLineItemsToPricing(liColl,
						pricingReqRes);

			}
		}
		logger.debug("PricingRequestResponse object with Existing lineitems..."
				+ pricingReqRes.toString());
		return pricingReqRes;
	}

	/**
	 * This method will add new linetems to PricingRequestResponse object
	 * 
	 * @param liColl
	 * @param pricingReqRes
	 * @return
	 */
	public static PricingRequestResponseDocument addNewLIToPricingRequest(
			LineItemCollectionType liColl,
			PricingRequestResponseDocument pricingReqRes) {
		pricingReqRes = OrderPriceUtil.copyLineItemsToPricing(liColl,
				pricingReqRes);
		logger.debug("PricingRequestResponse object with new lineitems..."
				+ pricingReqRes.toString());
		return pricingReqRes;
	}

	/**
	 * This method will update the PriceInfo for Existing LineItems in Order and
	 * New LineItems. Also it will add new lineitems to existing lineitem list
	 * at specific position
	 * 
	 * @param orderBean
	 *            SalesOrder that will contain the new TransientLineItem
	 * @param orderChangeValueObject
	 *            container that holds the new TransientLineItem
	 * @return new TransientLineItem
	 */
	public static void updateLineItemList(LineItemDao lineItemDao,
			final SalesOrder orderBean,
			final OrderChangeValueObject orderChangeValueObject,
			final PricingRequestResponseDocument pricingResponse) {
		logger.debug("updateLineItemList()........Enter");
		String indexAfter = orderChangeValueObject.getLineItemExternalId();
		// TransientLineItem lineItem = orderStatus.getLineItem();
		List<LineItem> newLineItemList = orderChangeValueObject.getLiList();

		if ((indexAfter != null) && (newLineItemList != null)) {
			try {
				int indexLocation = Integer.parseInt(indexAfter);

				List<LineItem> existingLineItemList = orderBean.getLineItems();
				for (LineItem existingLIBean : existingLineItemList) {
					logger.debug("Updating LineItemPrice info for NewLineItem from Pricing Response...");
					existingLIBean = OrderPriceUtil.updateLineItemPriceInfo(
							existingLIBean, pricingResponse);
					logger.debug("Updated LineItemPrice info for NewLineItem from Pricing Response...");

					logger.debug("Updating existing linetem bean ....");
					lineItemDao.merge(existingLIBean);
				}

				for (LineItem newLineItem : newLineItemList) {
					logger.debug("Updating LineItemPrice info for NewLineItem from Pricing Response...");
					newLineItem = OrderPriceUtil.updateLineItemPriceInfo(
							newLineItem, pricingResponse);
					logger.debug("Updated LineItemPrice info for NewLineItem from Pricing Response...");

					// TODO make sure while adding a new lineitem, promotion
					// lineitem still having valid "appliesTo" value
					if (existingLineItemList.size() > indexLocation) {
						existingLineItemList
								.add(indexLocation + 1, newLineItem);
						logger.debug("New TransientLineItem added at position..."
								+ indexLocation + 1);
					} else {
						existingLineItemList.add(newLineItem);
						logger.debug("New TransientLineItem added at the end of the existing list...");
					}
					logger.debug("Persisting new linetem bean....");
					lineItemDao.persist(newLineItem);
				}

			} catch (Exception e) {
				logger.debug("Exception thrown ..." + e.getMessage());
				e.printStackTrace();
			}
			logger.debug("updateLineItemList()........Exit");
		}
	}

	/**
	 * @param orderBean
	 *            SalesOrder that will contain the new TransientLineItem
	 * @param orderChangeValueObject
	 *            container that holds the new TransientLineItem
	 * @return new TransientLineItem
	 */
	public static LineItem updateLineItemList(final SalesOrder orderBean,
			final OrderChangeValueObject orderChangeValueObject) {

		return null;
	}

	public static void updateLineItemList(
			OrderChangeValueObject orderChangeValueObject,
			UnmarshallLineItem unmarshallLineItem) {

		if ((orderChangeValueObject == null) || (unmarshallLineItem == null)) {
			orderChangeValueObject.setLiList(Collections.EMPTY_LIST);
			return;
		}

		String indexAfterAsString = orderChangeValueObject
				.getLineItemExternalId();
		List<LineItem> lineItemBeanList = orderChangeValueObject.getLiList();

		if (orderChangeValueObject.getLineItemCollectionType() == null) {
			orderChangeValueObject.setLiList(Collections.EMPTY_LIST);
			return;
		}

		List<LineItemType> liTypeCollection = orderChangeValueObject
				.getLineItemCollectionType().getLineItemList();

		int insertAfterIndex = Integer.parseInt(indexAfterAsString);
		logger.debug("adding new lineitems after:" + insertAfterIndex);

		int newCollectionSize = liTypeCollection.size();
		logger.debug("number of new lineitems:" + newCollectionSize);

		int existingLineItemCount = lineItemBeanList.size();
		logger.debug("existing LineItem Count:" + existingLineItemCount);

		boolean doAppendAtEnd = ((insertAfterIndex + 1 >= existingLineItemCount));
		boolean doAppendAtMiddle = ((insertAfterIndex < (existingLineItemCount - 1)));

		// Update Existing item applies to
		// ****************************************************
		List<LineItem> existingLineItemList = orderChangeValueObject
				.getLiList();

		if ((indexAfterAsString != null) && (existingLineItemList != null)) {
			try {

				Iterator<LineItem> iterator = existingLineItemList.iterator();

				logger.debug("new line items:" + liTypeCollection.size());

				while (iterator.hasNext()) {

					LineItem lineItem = iterator.next();
 
					if (isApplicable(lineItem)) {
					  
						int insertPoint = getInsertPoint(insertAfterIndex,
								lineItemBeanList, lineItem, doAppendAtEnd,
								doAppendAtMiddle, newCollectionSize,
								existingLineItemCount);

						updateAppliesToExisting(existingLineItemCount,
								lineItem, insertPoint, newCollectionSize);
					}

				}
			} catch (Exception e) {

				throw new IllegalArgumentException(e.getMessage());

			}
		}

		// ****************************************************

		if ((indexAfterAsString != null) && (lineItemBeanList != null)) {
			try {

				Iterator<LineItemType> iterator = liTypeCollection.iterator();

				logger.debug("new line items:" + liTypeCollection.size());

				while (iterator.hasNext()) {
					LineItemType lineItemType = iterator.next();
					LineItem lineItem = unmarshallLineItem.buildLineItem(
							lineItemType, orderChangeValueObject.getAgentId());

					lineItem.setExternalId(lineItemType.getExternalId());
					boolean isInternal = Boolean.FALSE;
					if (isApplicable(lineItemType)) {
						isInternal = isInternal(lineItemType);

						int insertPoint = getInsertPoint(insertAfterIndex,
								lineItemBeanList, lineItem, doAppendAtEnd,
								doAppendAtMiddle, newCollectionSize,
								existingLineItemCount);

						int appliesToOffset = getAppliesToOffset(
								insertAfterIndex, isInternal, lineItemBeanList,
								lineItem, doAppendAtEnd, doAppendAtMiddle,
								newCollectionSize, existingLineItemCount);

						updateAppliesTo(appliesToOffset, existingLineItemCount,
								lineItemType, lineItem, insertPoint,
								newCollectionSize, isInternal);
					}

					insertAtCalculatedPoint(insertAfterIndex, isInternal,
							lineItemBeanList, lineItem, doAppendAtEnd,
							doAppendAtMiddle, newCollectionSize,
							existingLineItemCount);
				}
			} catch (Exception e) {
				// TODO Need to throw back the exception as Base task is
				// populating
				// this exception in the response.
				orderChangeValueObject.setLiList(Collections.EMPTY_LIST);
				throw new IllegalArgumentException(e.getMessage());
				/*
				 * e.printStackTrace();
				 * orderChangeValueObject.setLiList(Collections.EMPTY_LIST);
				 * return;
				 */
			}
		}

		assignLineNumbers(lineItemBeanList);

	}

	public static int getAppliesToOffset(int insertAfterIndex,
			boolean isInternal, List<LineItem> lineItemBeanList,
			LineItem lineItem, boolean doAppendAtEnd, boolean doAppendAtMiddle,
			int newCollectionSize, int existingLineItemCount) {
		int appliesToOffset = 0;

		if ((isInternal) && (insertAfterIndex < 0)) {
			appliesToOffset = 0;
		} else if (((doAppendAtEnd) && (isInternal)) || (isInternal)
				&& (insertAfterIndex >= newCollectionSize)) {
			appliesToOffset = existingLineItemCount;

		} else if ((doAppendAtMiddle) && (isInternal)) {
			appliesToOffset = insertAfterIndex + 1;
		}

		return appliesToOffset;
	}

	public static int getInsertPoint(int insertAfterIndex,
			List<LineItem> lineItemBeanList, LineItem lineItem,
			boolean doAppendAtEnd, boolean doAppendAtMiddle,
			int newCollectionSize, int existingLineItemCount) {
		int insertPoint = 0;

		if ((doAppendAtEnd)) {
			insertPoint = lineItemBeanList.size();
		}

		else {
			insertPoint = insertAfterIndex;
		}

		return insertPoint;
	}

	public static void insertAtCalculatedPoint(int insertAfterIndex,
			boolean isInternal, List<LineItem> lineItemBeanList,
			LineItem lineItem, boolean doAppendAtEnd, boolean doAppendAtMiddle,
			int newCollectionSize, int existingLineItemCount) {

		if (insertAfterIndex < 0) {

			int insertAtIndex = 0 + lineItem.getLineItemNumber();

			if (insertAtIndex > lineItemBeanList.size()) {
				insertAtIndex = lineItemBeanList.size();
			}

			lineItemBeanList.add(insertAtIndex, lineItem);

		}

		else if ((doAppendAtEnd)
				|| ((isInternal) && (insertAfterIndex >= newCollectionSize))) {
			lineItemBeanList.add(lineItem);

		}

		else if (existingLineItemCount > insertAfterIndex) {
			lineItemBeanList.add(
					insertAfterIndex + 1 + lineItem.getLineItemNumber(),
					lineItem);
		} else {
			lineItemBeanList.add(lineItem);
		}
	}

	public static boolean isInternal(LineItemType lineItemType) {
		return lineItemType.getLineItemDetail().getDetail()
				.getPromotionLineItem().getIsAppliesToInternal();
	}

	public static boolean isApplicable(LineItem lineItem) {

		if ((lineItem == null) || (lineItem.getLineItemDetail() == null)) {
			return false;
		}

		String appliesTo = lineItem.getLineItemDetail().getAppliesTo();
		List<Integer> appliesToList = lineItem.getLineItemDetail()
				.getAppliesToList();

		boolean isAppliesTo = ((appliesTo != null) && appliesTo.length() > 0);
		boolean isAppliesToList = ((appliesToList != null) && (appliesTo
				.length() > 0));

		return ((isAppliesTo) || (isAppliesToList));

	}

	public static boolean isApplicable(LineItemType lineItemType) {
		return ((lineItemType != null)
				&& (lineItemType.getLineItemDetail() != null)
				&& (lineItemType.getLineItemDetail().getDetail() != null) && (lineItemType
				.getLineItemDetail().getDetail().getPromotionLineItem() != null));
	}

	public static void assignLineNumbers(final List<LineItem> lineItemBeanList) {

		for (int i = 0; i < lineItemBeanList.size(); i++) {

			LineItem lib = lineItemBeanList.get(i);
			lib.setLineItemNumber(i);
		}

	}

	public static List<LineItemType> sortReverseOrder(
			final int existingCollectionSize, final int newCollectionSize,
			final List<LineItemType> liTypeCollection) {
		// Cannot Use Collections.reverse because XMLBeans Sort Results are not
		// consistent.
		List<LineItemType> liTypeCollectionSorted = new ArrayList<LineItemType>();

		if ((liTypeCollection != null) && (liTypeCollection.size() == 1)) {
			liTypeCollectionSorted.add(liTypeCollection.get(0));

		}

		else {

			for (int i = newCollectionSize - 1; i >= 0; i--) {
				for (LineItemType lineItemType : liTypeCollection) {
					int lineNum = lineItemType.getLineItemNumber();
					if (lineNum == i)
						liTypeCollectionSorted.add(lineItemType);
				}

			}
		}

		return liTypeCollectionSorted;
	}

	public static void print(LineItemType lineItemType, LineItem lineItem) {
		if ((lineItemType != null)
				&& (lineItemType.getLineItemDetail() != null)
				&& (lineItemType.getLineItemDetail().getDetail() != null)
				&& (lineItemType.getLineItemDetail().getDetail()
						.getPromotionLineItem() != null)) {
			String externalId = lineItemType.getExternalId()
					+ " applies to --->"
					+ lineItemType.getLineItemDetail().getDetail()
							.getPromotionLineItem().getAppliesToList() + " ";

			logger.debug(externalId);
		}
	}

	public static void updateAppliesToExisting(int existingLineItemCount,
			LineItem lineItem, int insertAfterIndex, int newCollectionSize) {

		if ((lineItem != null) && (lineItem.getLineItemDetail() != null)) {

			List<Integer> existingAppliesToList = lineItem.getLineItemDetail()
					.getAppliesToList();

			if (existingAppliesToList != null) {

				int[] newAppliesToList = new int[existingAppliesToList.size()];

				int i = 0;
				for (Integer appliesTo : existingAppliesToList) {

					logger.debug(lineItem.getExternalId()
							+ " original applies to:" + appliesTo
							+ " isInternal:existing insert Index:"
							+ insertAfterIndex);

					// always shift by the size of the new collection
					// If on top of everything; shift down
					// If applies to
					if ((insertAfterIndex < 0)
							|| (insertAfterIndex < appliesTo)) {
						appliesTo = newCollectionSize + appliesTo;
					}

					newAppliesToList[i++] = appliesTo;

					logger.debug(lineItem.getExternalId()
							+ " updated applies to:" + appliesTo
							+ " isInternal:existing");
				}

				lineItem.getLineItemDetail().setAppliesToList(newAppliesToList);

			}

			String existingAppliesTo = lineItem.getLineItemDetail()
					.getAppliesTo();

			if ((existingAppliesTo != null) && (existingAppliesTo.length() > 0)) {
				Integer intExistingAppliesTo = Integer
						.valueOf(existingAppliesTo);

				// always shift by the size of the new collection
				// If on top of everything; shift down
				// If applies to
				if ((insertAfterIndex < 0)
						|| (insertAfterIndex < intExistingAppliesTo)) {
					intExistingAppliesTo = newCollectionSize
							+ intExistingAppliesTo;
				}

				lineItem.getLineItemDetail().setAppliesTo(
						String.valueOf(intExistingAppliesTo));
			}

		}

	}

	public static void updateAppliesTo(int appliesToOffset,
			int existingLineItemCount, LineItemType lineItemType,
			LineItem lineItem, int insertIndex, int newCollectionSize,
			boolean isInternal) {

		if ((lineItemType != null)
				&& (lineItemType.getLineItemDetail() != null)
				&& (lineItemType.getLineItemDetail().getDetail() != null)
				&& (lineItemType.getLineItemDetail().getDetail()
						.getPromotionLineItem() != null)) {

			List<Integer> existingAppliesToList = lineItemType
					.getLineItemDetail().getDetail().getPromotionLineItem()
					.getAppliesToList();

			int[] newAppliesToList = new int[existingAppliesToList.size()];

			int i = 0;
			for (Integer appliesTo : existingAppliesToList) {

				logger.debug(lineItemType.getExternalId()
						+ " original applies to:" + appliesTo + " isInternal:"
						+ isInternal + " insert Index:" + insertIndex);

				if (isInternal) {
					appliesTo = appliesToOffset + appliesTo;
				} else {
					appliesTo = appliesTo
							+ calcExternalAppliesTo(insertIndex,
									newCollectionSize, existingLineItemCount,
									appliesTo);
				}

				newAppliesToList[i++] = appliesTo;

				logger.debug(lineItemType.getExternalId()
						+ " updated applies to:" + appliesTo + " isInternal:"
						+ isInternal);
			}
			lineItemType.getLineItemDetail().getDetail().getPromotionLineItem()
					.setAppliesToArray(newAppliesToList);

			lineItem.getLineItemDetail().setAppliesToList(newAppliesToList);

		}

	}

	public static int calcExternalAppliesTo(int insertAfterIndex,
			int newCollectionSize, int existingLineItemCount, int appliesTo) {

		boolean doAppendAtEnd = ((insertAfterIndex + 1 >= existingLineItemCount));
		boolean doAppendAtMiddle = ((insertAfterIndex < (existingLineItemCount - 1)));

		int appliesToOffset = 0;

		if (insertAfterIndex < 0) {
			appliesToOffset = newCollectionSize;
		}

		else if (doAppendAtEnd) {
			appliesToOffset = 0;
		}

		else if ((doAppendAtMiddle) && (appliesTo <= insertAfterIndex)) {
			appliesToOffset = 0;
		}

		else if ((doAppendAtMiddle) && (appliesTo > insertAfterIndex)) {
			appliesToOffset = newCollectionSize;
		}

		return appliesToOffset;
	}

}
