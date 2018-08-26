package com.A.vm.service;

import java.util.List;
import com.A.task.context.impl.ResponseItemOme;
import com.A.V.beans.entity.LineItem;
import com.A.V.beans.entity.SalesOrder;
import com.A.V.beans.entity.SelectedFeatureValue;
import com.A.xml.v4.FeatureValueType;
import com.A.xml.v4.LineItemCollectionType;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.OrderType;
import com.A.xml.v4.PricingRequestResponseDocument;
import com.A.xml.v4.SelectedFeaturesType.FeatureGroup;

public interface LineItemService {

	/**
	 * Helper method to replace the Lineitem's scheduling information from
	 * Client request document.
	 *
	 * @param srcLineItemTypeList
	 * @param updatedLIColl
	 */
	void updateOtherLineItemInfo(List<LineItemType> srcLineItemTypeList,
			LineItemCollectionType updatedLIColl);

	/**
	 * This method will process the update request for SelectedFeature
	 */
	 void processFeatureUpdateRequest(boolean isUpdateRequest,
			SalesOrder orderBean, List<LineItem> updateLineItemList,
			LineItemCollectionType updatedLIColl, ResponseItemOme rio);

	boolean checkPricingStatus(PricingRequestResponseDocument pricingResponse);

	OrderType getPricingResponse(
			PricingRequestResponseDocument updatedPricingResponse);

	/**
	 * Helper method to copy other LineItem information along with Selected
	 * Feature so that we can save them for each lineItem. So this will be
	 * useful when client wants to update Selected Featue at the same time
	 * client also wants to update other line item information, which can now be
	 * done in one request.
	 *
	 * @param orgLineIteminfo
	 * @param priceLIType
	 */
	void copyOtherLineItemInfo(LineItemType orgLineIteminfo,
			LineItemType priceLIType);

	/**
	 * Method will generate complete information for LineItem and its detail so
	 * that it can be used to prepare PricingRequestResponse object
	 *
	 * @param orderBean
	 * @param lineItemTypeList
	 * @param updatedLIColl
	 */
	void generateLineItemInfo(SalesOrder orderBean,
			List<LineItemType> lineItemTypeList,
			LineItemCollectionType updatedLIColl);

	/**
	 * Preparing PricingRequestRespose object for Pricing Service.
	 *
	 * @param updatedLIColl
	 * @return
	 */
	PricingRequestResponseDocument preparePricingRequest(
			LineItemCollectionType updatedLIColl,ResponseItemOme rio);

	void saveFeatures(List<LineItem> updatedLineItemList);

	void updateLineItems(List<LineItem> updatedLineItemList);

	LineItem updateLineItem(final SalesOrder orderBean,
			final Long lineitemExtId, final LineItemType ucLineItemInfo,
			boolean isUpdateRequest);

	/**
	 * This method will either add SelectedFeature in LineItem(retrieved from
	 * db) OR remove SelectedFeature from it. And then this LineItem bean will
	 * be used to Marshall XML so that with all the information for LineItem we
	 * can prepare PricingRequestResponse object with updated SelectedFrature.
	 * The way we are adding or updating features are , we are deleting all the
	 * existing features and adding only supplied selected features.
	 *
	 *
	 * @param ucLineItemInfo
	 * @param lineItemBean
	 */
	void updateLineItemSelectedFeature(LineItemType ucLineItemInfo,
			LineItem lineItemBean);
	
	/**
	 * Helper method to check FEATURE GROUP is exist in db or not
	 *
	 * @param featureBeanList
	 * @param feature
	 * @param externalId
	 * @return
	 */
	SelectedFeatureValue getNewFeatureBeanFromFeatureGroup(
			FeatureGroup featureGroup);

	/**
	 * Helper method to check FEATURE is exist in db or not
	 *
	 * @param featureBeanList
	 * @param feature
	 * @param externalId
	 * @return
	 */
	SelectedFeatureValue getNewFeatureBean(FeatureValueType feature);

	/**
	 * A method to update the salesorder information
	 * @param salesOrder
	 */
	public void updateOrder(SalesOrder salesOrder);


	public void saveLineItemAttributes(List<LineItem> updatedLineItemList);

	public void updateOrderPrice(SalesOrder orderBean,	List<LineItem> updateLineItemList) ;

	public Boolean isExist(List<LineItem> lineItemList, Long extId);
}
