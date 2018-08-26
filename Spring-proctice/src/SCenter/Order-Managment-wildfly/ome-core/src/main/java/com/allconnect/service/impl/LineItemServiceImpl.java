package com.AL.service.impl;

import static com.AL.task.util.AccountHolderUtil.lineItemAccountHolderMapKey;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.activity.impl.ActivitySubmitOrder;
import com.AL.ome.OmePricingMessageSender;
import com.AL.pricing.OrderPriceUtil;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.context.impl.ResponseItemOme;
import com.AL.util.XmlUtil;
import com.AL.util.audit.AuditService;
import com.AL.validation.impl.PricingValidationHelper;
import com.AL.V.beans.LineItemPriceInfo;
import com.AL.V.beans.entity.AccountHolder;
import com.AL.V.beans.entity.CoachingBean;
import com.AL.V.beans.entity.CustomSelection;
import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.OrderAudit;
import com.AL.V.beans.entity.ReasonBean;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.entity.SelectedDialogue;
import com.AL.V.beans.entity.SelectedFeatureValue;
import com.AL.Vdao.dao.AccountHolderDao;
import com.AL.Vdao.dao.CoachingDao;
import com.AL.Vdao.dao.CustomSelectionDao;
import com.AL.Vdao.dao.LineItemAttributeDao;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.Vdao.dao.PaymentEventDao;
import com.AL.Vdao.dao.ReasonDao;
import com.AL.Vdao.dao.SelectedDialogueDao;
import com.AL.Vdao.dao.SelectedFeatureValueDao;
import com.AL.vm.service.LineItemService;
import com.AL.vm.util.converter.marshall.MarshallFeatureValue;
import com.AL.vm.util.converter.marshall.MarshallLineItem;
import com.AL.vm.util.converter.unmarshall.UnmarshallCustomSelection;
import com.AL.vm.util.converter.unmarshall.UnmarshallLineItem;
import com.AL.vm.util.converter.unmarshall.UnmarshallSelectedDialogue;
import com.AL.xml.v4.FeatureValueType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemPriceInfoType;
import com.AL.xml.v4.LineItemStatusCodesType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.PriceInfoType;
import com.AL.xml.v4.PricingRequestResponseDocument;
import com.AL.xml.v4.PricingRequestResponseDocument.PricingRequestResponse;
import com.AL.xml.v4.ProviderSourceType;
import com.AL.xml.v4.SelectedFeaturesType;
import com.AL.xml.v4.SelectedFeaturesType.FeatureGroup;
import com.AL.xml.v4.SelectedFeaturesType.Features;
import com.AL.xml.v4.StatusType;

@Component
public class LineItemServiceImpl implements LineItemService {

    private static final String INFO_RES_SUCCESS = "INFO_ORDER_PRICED_SUCCESSFULLY";

    private static final Logger logger = Logger.getLogger(LineItemServiceImpl.class);

    private static final String WARNING_NONE_PRICED = "WARNING_NONE_PRICED";

    @Autowired
    private SelectedFeatureValueDao selectedFeatureValueDao;

    @Autowired
    private LineItemAttributeDao lineItemAttributeDao;

    @Autowired
    ActivitySubmitOrder activitySubmitOrder;

    @Autowired
    private OrderManagementDao orderManagementDao;

    @Autowired
    protected AuditService<OrderAudit> auditService;

    @Autowired
    private MarshallLineItem marshallLineItem;

    @Autowired
    private UnmarshallLineItem unmarshallLineItem;

    @Autowired
    private SelectedDialogueDao selectedDialogueDao;

    @Autowired
    private CustomSelectionDao customSelectionDao;
    
	@Autowired
	private AccountHolderDao accountHolderDao;
	
	@Autowired
	private PaymentEventDao paymentEventDao;
	
	@Autowired
	private ReasonDao reasonDao;
	
	@Autowired
	private CoachingDao coachingDao;


    /**
     * Helper method to replace the Lineitem's scheduling information from Client request document.
     *
     * @param srcLineItemTypeList
     * @param updatedLIColl
     */
    public void updateOtherLineItemInfo(List<LineItemType> srcLineItemTypeList, LineItemCollectionType updatedLIColl) {
	if (srcLineItemTypeList != null && updatedLIColl != null) {
	    for (LineItemType destLineItem : updatedLIColl.getLineItemList()) {
		for (LineItemType srcLineItem : srcLineItemTypeList) {
		    if (destLineItem.getExternalId() == srcLineItem.getExternalId()) {
			destLineItem.setSchedulingInfo(srcLineItem.getSchedulingInfo());
			// destLineItem.setProductDatasource(srcLineItem.getProductDatasource());
			if (srcLineItem.getSvcAddressExtId() != null) {
			    destLineItem.setSvcAddressExtId(srcLineItem.getSvcAddressExtId());
			}
			if (srcLineItem.getBillingInfoExtId() != null) {
			    destLineItem.setBillingInfoExtId(srcLineItem.getBillingInfoExtId());
			}

			// Set other basic info about lineitem
			destLineItem.setLeadId(srcLineItem.getLeadId());
			destLineItem.setNewPhone(srcLineItem.getNewPhone());
			if(srcLineItem.getProviderConfirmationNumber() != null && srcLineItem.getProviderConfirmationNumber().trim().length() > 0) {
			    destLineItem.setProviderConfirmationNumber(srcLineItem.getProviderConfirmationNumber());
			}

			if(!XmlUtil.isElementNull(srcLineItem.newCursor(), "eventType")) {
			    destLineItem.setEventType(srcLineItem.getEventType());
			}
			if (!XmlUtil.isElementNull(srcLineItem.newCursor(), "isEventSelected")) {
			    destLineItem.setIsEventSelected(srcLineItem.getIsEventSelected());
			}

			if (!XmlUtil.isElementNull(srcLineItem.newCursor(), "isEventCompleted")) {
			    destLineItem.setIsEventCompleted(srcLineItem.getIsEventCompleted());
			}
		    }
		}
	    }
	}
    }
    
	public boolean isPricingRequestNeeded(SalesOrder orderBean,
			List<LineItemType> lineItemTypeList, boolean isUpdateRequest) {
		boolean isPricingRequestNeeded = false;
		if (isUpdateRequest) {
			lineItemTypeSearch : for (LineItemType lineItemType : lineItemTypeList) {
				for (LineItem lineItem : orderBean.getLineItems()) {
					if (lineItemType.getExternalId() == lineItem.getExternalId() 
						&& lineItem.getProductDatasource().equalsIgnoreCase(
						ProviderSourceType.INTERNAL.toString()) && 
						(lineItemType.getSelectedFeatures() != null) && 
						!lineItemType.getSelectedFeatures().getFeatureGroupList().isEmpty()) {
							isPricingRequestNeeded = true;
							break lineItemTypeSearch;
					}
				}
			}
		} else {
			for (LineItemType lineItemType : lineItemTypeList) {
				if ((lineItemType.getProductDatasource() != null) && 
						ProviderSourceType.INTERNAL.toString().equalsIgnoreCase(
						lineItemType.getProductDatasource().toString()) && 
						(lineItemType.getSelectedFeatures() != null) && 
						!lineItemType.getSelectedFeatures().getFeatureGroupList().isEmpty()) {
					isPricingRequestNeeded = true;
					break;
				}
			}
		}
		return isPricingRequestNeeded;
	}

    /**
     * This method will process the update request for SelectedFeature
     */
    public void processFeatureUpdateRequest(boolean isUpdateRequest, SalesOrder orderBean, List<LineItem> updateLineItemList, 
    		LineItemCollectionType updatedLIColl, ResponseItemOme rio) {
		    long lineitemExtId;
		    LineItem lineItemBean;
		    // Prepare PricingRequest from Updated LineItem info
		    PricingRequestResponseDocument pricingRequest = preparePricingRequest(updatedLIColl, rio);
			if (pricingRequest != null) {
				logger.debug("***** Sending request to pricing *****");
				PricingRequestResponseDocument pricingResponse = OmePricingMessageSender.getPricedOrder(pricingRequest.toString());
				// Dummy price engine for testing
				// PricingRequestResponseDocument pricingResponse =
				// DummyPricingEngine.processOrderPrice(pricingRequest);
				if (pricingResponse != null) {
				    logger.debug(pricingResponse.toString());
				    logger.debug("***** Reposnse from pricing *****");
				}
				// Code to check pricing response had any FATAL errors or not.
				boolean fatalError = PricingValidationHelper.isFatalErrorExist(pricingResponse);
				if (!fatalError && pricingResponse != null) {
				    // Use updated LineItemInfo and price to save it in db
				    OrderType pricedOrder = getPricingResponse(pricingResponse);
				    boolean isPricingSuccessfull = checkPricingStatus(pricingResponse);
				    if (pricedOrder != null && pricedOrder.getLineItems() != null && isPricingSuccessfull) {
						Boolean isWarnMsg = checkWarningPricingStatus(pricingResponse);
						// Update total order price
						if (!isWarnMsg) {
						    updateTotalOrderPriceFromPricingResponse(orderBean, pricedOrder);
						}
						List<LineItemType> pricedLIList = pricedOrder.getLineItems().getLineItemList();
						if (pricedLIList != null) {
						    for (LineItemType priceLIType : pricedLIList) {
								// Retrieve LineItemNumber from request
								lineitemExtId = priceLIType.getExternalId();
								// Copy other lineitem info from original
								// request so
								// that they can also be updated along
								// with Selected Features
								for (LineItemType orgLineIteminfo : updatedLIColl.getLineItemList()) {
								    copyOtherLineItemInfo(orgLineIteminfo, priceLIType);
								}
								// User wants to update lineitem
								if(!isWarnMsg) {
								    lineItemBean = updateLineItem(orderBean, lineitemExtId, priceLIType, isUpdateRequest);
								    updateLineItemList.add(lineItemBean);
								}
						    }
						}
				    } else {
						logger.info("Fatal error in Pricing");
						PricingValidationHelper.populateStatusMsg(pricingResponse, rio.getValidationReport());
				    }
				} else {
				    logger.info("Error or Warning returned from Pricing");
				    PricingValidationHelper.populateStatusMsg(pricingResponse, rio.getValidationReport());
				}
			}
    }

    /**
     * A method to copy updated total order price from Pricing response to SalesOrder bean
     *
     * @param orderBean
     * @param pricedOrder
     */
    private void updateTotalOrderPriceFromPricingResponse(SalesOrder orderBean, OrderType pricedOrder) {
	if (pricedOrder.getTotalPriceInfo() != null) {
	    PriceInfoType totalPriceInfo = pricedOrder.getTotalPriceInfo();
	    orderBean.getTotalPrice().setBaseNonRecurringPrice(totalPriceInfo.getBaseNonRecurringPrice());
	    orderBean.getTotalPrice().setBaseRecurringPrice(totalPriceInfo.getBaseRecurringPrice());
	}

    }

    public boolean checkPricingStatus(PricingRequestResponseDocument pricingResponse) {
	boolean pricingSuccessfull = Boolean.FALSE;
	if (pricingResponse != null) {
	    StatusType status = pricingResponse.getPricingRequestResponse().getStatus();
	    if (status != null) {
		String msg = status.getStatusMsg();
		if (msg.equalsIgnoreCase(INFO_RES_SUCCESS) || msg.equalsIgnoreCase(WARNING_NONE_PRICED)) {
		    pricingSuccessfull = Boolean.TRUE;
		}
	    }
	}
	return pricingSuccessfull;
    }

    public boolean checkWarningPricingStatus(PricingRequestResponseDocument pricingResponse) {
	boolean pricingSuccessfull = Boolean.FALSE;
	if (pricingResponse != null) {
	    StatusType status = pricingResponse.getPricingRequestResponse().getStatus();
	    if (status != null) {
		String msg = status.getStatusMsg();
		if (msg.equalsIgnoreCase(WARNING_NONE_PRICED)) {
		    pricingSuccessfull = Boolean.TRUE;
		}
	    }
	}
	return pricingSuccessfull;
    }

    public OrderType getPricingResponse(PricingRequestResponseDocument updatedPricingResponse) {
	PricingRequestResponse pricingReqRes = updatedPricingResponse.getPricingRequestResponse();
	OrderType pricedOrder = pricingReqRes.getResponse().getPricingResponseOrderElementArray(0);
	return pricedOrder;
    }

    /**
     * Helper method to copy other LineItem information along with Selected Feature so that we can save them for each lineItem. So this will be useful when client wants to update
     * Selected Featue at the same time client also wants to update other line item information, which can now be done in one request.
     *
     * @param orgLineIteminfo
     * @param priceLIType
     */
    public void copyOtherLineItemInfo(LineItemType orgLineIteminfo, LineItemType priceLIType) {
	if (orgLineIteminfo != null && priceLIType != null) {
	    priceLIType.setSchedulingInfo(orgLineIteminfo.getSchedulingInfo());
	    if(orgLineIteminfo.getActiveDialogs() != null) {
		priceLIType.setActiveDialogs(orgLineIteminfo.getActiveDialogs());
	    }
	    if(orgLineIteminfo.getCustomSelections() != null) {
		priceLIType.setCustomSelections(orgLineIteminfo.getCustomSelections());
	    }
	    if (orgLineIteminfo.getSvcAddressExtId() != null) {
		priceLIType.setSvcAddressExtId(orgLineIteminfo.getSvcAddressExtId());
	    }
	    if (orgLineIteminfo.getBillingInfoExtId() != null) {
		priceLIType.setBillingInfoExtId(orgLineIteminfo.getBillingInfoExtId());
	    }

	    if(orgLineIteminfo.getProviderConfirmationNumber() != null && orgLineIteminfo.getProviderConfirmationNumber().trim().length() > 0) {
		priceLIType.setProviderConfirmationNumber(orgLineIteminfo.getProviderConfirmationNumber());
	    }

	    if (!XmlUtil.isElementNull(orgLineIteminfo.newCursor(), "eventType")) {
		priceLIType.setEventType(orgLineIteminfo.getEventType());
	    }

	    if (!XmlUtil.isElementNull(orgLineIteminfo.newCursor(), "isEventSelected")) {
		priceLIType.setIsEventSelected(orgLineIteminfo.getIsEventSelected());
	    }

	    if (!XmlUtil.isElementNull(orgLineIteminfo.newCursor(), "isEventCompleted")) {
		priceLIType.setIsEventCompleted(orgLineIteminfo.getIsEventCompleted());
	    }
	}

    }

    /**
     * Method will generate complete information for LineItem and its detail so that it can be used to prepare PricingRequestResponse object
     *
     * @param orderBean
     * @param lineItemTypeList
     * @param updatedLIColl
     */
    public void generateLineItemInfo(SalesOrder orderBean, List<LineItemType> lineItemTypeList, LineItemCollectionType updatedLIColl) {
	List<LineItem> lineItemBeanList = orderBean.getLineItems();

	if (lineItemTypeList != null) {
	    for (LineItemType ucLineItemInfo : lineItemTypeList) {

		// Retrieve lineitem number from request xml
		// String lineItemNo =
		// String.valueOf(ucLineItemInfo.getLineItemNumber());
		long lineitemExtId = ucLineItemInfo.getExternalId();
		for (LineItem itemBean : lineItemBeanList) {
		    if (itemBean.getExternalId().longValue() == lineitemExtId) {

			// Update SelectedFeatureValue for LineItem
			updateLineItemSelectedFeature(ucLineItemInfo, itemBean);

			// Update new ActiveDialogues for LineItem
			updateActiveDialogues(ucLineItemInfo, itemBean);

			// Update custom selections for lineitem
			updateCustomSelections(ucLineItemInfo, itemBean);

			// Update custom selections for lineitem
			updateLineItemAttributes(ucLineItemInfo, itemBean);

			// Marshall LineItem info into the XML, so that
			// updated info with added/removed SelectedFeature can
			// be used to prepare PricingReqestResponse object
			LineItemType updatedLineItemType = updatedLIColl.addNewLineItem();

			updatedLineItemType.setExternalId(ucLineItemInfo.getExternalId());
			updatedLineItemType.setLeadId(ucLineItemInfo.getLeadId());

			updatedLineItemType.addNewProductDatasource().setStringValue(itemBean.getProductDatasource());
			updatedLineItemType.setLineItemNumber(itemBean.getLineItemNumber());
			updatedLineItemType.setProviderConfirmationNumber(ucLineItemInfo.getProviderConfirmationNumber());
			// Set LineItemPriceInfo
			LineItemPriceInfoType lipInfoType = ucLineItemInfo.getLineItemPriceInfo();

			if (lipInfoType != null) {
			    logger.debug("Setting LineItemPriceInfo for each lineItem: " + ucLineItemInfo.getExternalId() + " : lipInfoType.getBaseNonRecurringPrice(): "
				    + lipInfoType.getBaseNonRecurringPrice());
			    LineItemPriceInfo liInfoPrice = new LineItemPriceInfo();
			    liInfoPrice.setBaseNonRecurringPrice(lipInfoType.getBaseNonRecurringPrice());
			    liInfoPrice.setBaseNonRecurringPriceUnits(lipInfoType.getBaseNonRecurringPriceUnits());
			    liInfoPrice.setBaseRecurringPrice(lipInfoType.getBaseRecurringPrice());
			    liInfoPrice.setBaseRecurringPriceUnits(lipInfoType.getBaseRecurringPriceUnits());
			    if (lipInfoType.getOnDeliveryPrice() != null && !lipInfoType.getOnDeliveryPrice().equals("")) {
				liInfoPrice.setOnDeliveryPrice(Double.valueOf(lipInfoType.getOnDeliveryPrice()));
			    }
			    else {
				liInfoPrice.setOnDeliveryPrice(0);
			    }

			    itemBean.setPrice(liInfoPrice);
			    // updatedLineItemType.setLineItemPriceInfo(lipInfoType);
			}

			marshallLineItem.copyLineitemPriceInfo(itemBean, updatedLineItemType);

			logger.debug("Done copying the LineitemPriceInfo ");

			marshallLineItem.copyStatusRecordBean(itemBean, updatedLineItemType);
			marshallLineItem.copyHistoricStatus(itemBean, updatedLineItemType);
			marshallLineItem.copyLineItemDetail(itemBean, updatedLineItemType);

			if (!itemBean.getLineItemDetail().getType().equalsIgnoreCase("productPromotion")) {
			    MarshallFeatureValue.INSTANCE.copySelectedFeatureValue(itemBean, updatedLineItemType, null);
			}
			marshallLineItem.copyScheduleInfo(itemBean, updatedLineItemType);
			marshallLineItem.copyActiveDialogues(itemBean, updatedLineItemType);
			marshallLineItem.copyCustomSelections(itemBean, updatedLineItemType);
			marshallLineItem.copyLineItemAttributes(itemBean, updatedLineItemType);

			if (XmlUtil.isElementNull(ucLineItemInfo.newCursor(), "eventType")) {
			    updatedLineItemType.setEventType(itemBean.getEventType());

			}else {
			    updatedLineItemType.setEventType(ucLineItemInfo.getEventType());
			}

			if (XmlUtil.isElementNull(ucLineItemInfo.newCursor(), "isEventSelected")) {
			    updatedLineItemType.setIsEventSelected(itemBean.getIsEventSelected() != null ? itemBean.getIsEventSelected() : Boolean.FALSE);

			}else {
			    updatedLineItemType.setIsEventSelected(ucLineItemInfo.getIsEventSelected());
			}

			if (XmlUtil.isElementNull(ucLineItemInfo.newCursor(), "isEventCompleted")) {
			    updatedLineItemType.setIsEventCompleted(itemBean.getIsEventCompleted() != null ? itemBean.getIsEventCompleted() : Boolean.FALSE);
			}else {
			    updatedLineItemType.setIsEventCompleted(ucLineItemInfo.getIsEventCompleted());

			}
			// auditService.audit(getEm(),
			// AuditBuilder.createOrderAudit(itemBean));

		    }
		}
	    }
	}
    }

    /**
     * This method will replace existing custom selections with new selections
     *
     * @param ucLineItemInfo
     * @param itemBean
     */
    private void updateCustomSelections(LineItemType ucLineItemInfo, LineItem itemBean) {
	Set<CustomSelection> newCustSelectionList = UnmarshallCustomSelection.buildCustomSelection(ucLineItemInfo, itemBean.getSelections(), Boolean.FALSE);
	if(newCustSelectionList != null && !newCustSelectionList.isEmpty()) {
	    itemBean.setSelections(newCustSelectionList);
	}
    }

    /**
     * a method which will keep existing dialogues and unmarshall new dialogues and set them to lineitem
     *
     * @param ucLineItemInfo
     * @param lineItemBean
     */
    private void updateActiveDialogues(LineItemType ucLineItemInfo, LineItem lineItemBean) {
	// Set<SelectedDialogue> existingDialogues = lineItemBean.getDialogues();
	Set<SelectedDialogue> mixDialogues = UnmarshallSelectedDialogue.buildSelectedDialogue(ucLineItemInfo, lineItemBean.getDialogues());
	if(mixDialogues != null && !mixDialogues.isEmpty()) {
	    lineItemBean.setDialogues(mixDialogues);
	}

    }

    /**
     * Preparing PricingRequestRespose object for Pricing Service.
     *
     * @param updatedLIColl
     * @return
     */
    public PricingRequestResponseDocument preparePricingRequest(LineItemCollectionType updatedLIColl, ResponseItemOme rio) {

	OrderManagementRequestResponseDocument orderDoc = OrderManagementRequestResponseDocument.Factory.newInstance();
	orderDoc.addNewOrderManagementRequestResponse();
	orderDoc.getOrderManagementRequestResponse().addNewRequest();
	orderDoc.getOrderManagementRequestResponse().getRequest().addNewOrderInfo();
	orderDoc.getOrderManagementRequestResponse().getRequest().getOrderInfo().addNewLineItems();
	orderDoc.getOrderManagementRequestResponse().getRequest().getOrderInfo().setLineItems(updatedLIColl);
	PricingRequestResponseDocument pricingRequest = OrderPriceUtil.copyOrderToPricing(orderDoc.getOrderManagementRequestResponse().getRequest().getOrderInfo(), rio);
	return pricingRequest;
    }

    public void saveFeatures(List<LineItem> updatedLineItemList) {

	for (LineItem lineItemBean : updatedLineItemList) {
	    if ((lineItemBean != null) && (lineItemBean.getSelectedFeatureValues() != null)) {
		Set<SelectedFeatureValue> allFeatures = lineItemBean.getSelectedFeatureValues();
		if(allFeatures != null && !allFeatures.isEmpty()) {
		    Iterator<SelectedFeatureValue> fvIter = allFeatures.iterator();
		    while(fvIter.hasNext()) {
			SelectedFeatureValue fv = fvIter.next();
			if(fv != null && null != fv.getIncluded()) {

			    if(fv.getIncluded()) {
				fvIter.remove();
			    }
			}
		    }
		}
		logger.debug("Selected Features size : " + allFeatures.size());
		selectedFeatureValueDao.persistAll(allFeatures);
	    }
	}
    }

    public void saveDialogues(List<LineItem> updatedLineItemList) {

	if ((updatedLineItemList != null) && (updatedLineItemList.size() > 0)) {

	    for (LineItem lineItemBean : updatedLineItemList) {

		if ((lineItemBean != null) && (lineItemBean.getDialogues() != null)) {
		    logger.debug("LineItemServiceImpl:saveDialogues():Dialogues().size(): " + lineItemBean.getDialogues().size());

		    Set<SelectedDialogue> dlSet = (Set<SelectedDialogue>) lineItemBean.getDialogues();
		    String[] sdArray = new String[dlSet.size()];

		    selectedDialogueDao.persist(dlSet);
		}
	    }
	}
    }

    public void saveSelections(List<LineItem> updatedLineItemList) {

	if ((updatedLineItemList != null) && (updatedLineItemList.size() > 0)) {

	    for (LineItem lineItemBean : updatedLineItemList) {

		if ((lineItemBean != null) && (lineItemBean.getSelections() != null)) {
		    customSelectionDao.persist(lineItemBean.getSelections());
		}
	    }
	}
    }

    public void updateLineItems(List<LineItem> updatedLineItemList, Boolean isStatusUpdateIncluded) {
	orderManagementDao.update(updatedLineItemList, isStatusUpdateIncluded);
    }

    public void updateLineItems(List<LineItem> updatedLineItemList) {
	orderManagementDao.update(updatedLineItemList);
    }

    /**
     * Method will retrieve LineItem from SalesOrder and if line item external id provided will match with one of the existing lineitem, we will update it.
     */
    public LineItem updateLineItem(final SalesOrder orderBean, final Long lineitemExtId, final LineItemType ucLineItemInfo, boolean isUpdateRequest) {
		if ((lineitemExtId == null) || (orderBean == null) || (orderBean.getLineItems() == null)) {
		    return null;
		}
		if (ucLineItemInfo != null) {
		    try {
				List<LineItem> lineItemList = orderBean.getLineItems();
				if (lineItemList != null && !lineItemList.isEmpty()) {
				    for (LineItem lineItemBean : lineItemList) {
						long itemExtId = lineItemBean.getExternalId();
						if (itemExtId == lineitemExtId) {
						    LineItem updatedLineItem = unmarshallLineItem.build(ucLineItemInfo, lineItemBean, isUpdateRequest, orderBean.getAgentId());
						    return updatedLineItem;
						}
				    }
				}
		    }
		    catch (Exception e) {
				logger.error("Exception thrown in UpdateLineItem request ", e);
				e.printStackTrace();
		    }
		}
		return null;
    }

    /**
     * This method will either add SelectedFeature in LineItem(retrieved from db) OR remove SelectedFeature from it. And then this LineItem bean will be used to Marshall XML so
     * that with all the information for LineItem we can prepare PricingRequestResponse object with updated SelectedFrature. The way we are adding or updating features are , we are
     * deleting all the existing features and adding only supplied selected features.
     *
     *
     * @param ucLineItemInfo
     * @param lineItemBean
     */
    public void updateLineItemSelectedFeature(LineItemType ucLineItemInfo, LineItem lineItemBean) {

	// Retrieve selectedfeaturevalue type from src xml
	// List<SelectedFeatureValue> featureList =
	// ucLineItemInfo.getSelectedFeatureValues().getSelectedFeatureValueList();
	SelectedFeaturesType srcSelFeaturesType = ucLineItemInfo.getSelectedFeatures();

	// Retrieve existing SelectedFeatureValue from LineItem
	Set<SelectedFeatureValue> featureBeanList = lineItemBean.getSelectedFeatureValues();

	//TODO set is_active flag to false for existing features as they no longer being referenced.
	//TODO Better fix is to remove via orphan delete flag in Entity bean but as it has performance issue in load test, this is workaround

	if(featureBeanList != null && !featureBeanList.isEmpty()) {
	    logger.info("Inactivating existing features from lineitem service : " + featureBeanList.toString());

	    Iterator<SelectedFeatureValue> oldFVIter = featureBeanList.iterator();
	    while(oldFVIter.hasNext()) {

		SelectedFeatureValue oldFV = oldFVIter.next();
		oldFV.setActive(false);
		oldFV.setFeatureDate(Calendar.getInstance());
	    }
	    selectedFeatureValueDao.merge(featureBeanList);
	}

	// Lets remove all the selected feature for this lineitem and we will
	// add the supplied SelectedFeatures all at once.
	featureBeanList.clear();

	// Add all the provided Selected Feature in the client request
	if (srcSelFeaturesType != null) {
	    List<FeatureGroup> srcFeatureGroupList = srcSelFeaturesType.getFeatureGroupList();

	    // Creating SelectedFeatureBean for Feature group and its childs
	    if (srcFeatureGroupList != null) {
		for (FeatureGroup srcFeatureGroup : srcFeatureGroupList) {

		    SelectedFeatureValue parentFeatureBean = getNewFeatureBeanFromFeatureGroup(srcFeatureGroup);
		    List<FeatureValueType> srcFeatureValueList = srcFeatureGroup.getFeatureValueList();

		    featureBeanList.add(parentFeatureBean);

		    if (srcFeatureValueList != null) {
			for (FeatureValueType featureValue : srcFeatureValueList) {
			    SelectedFeatureValue childFeatureBean = getNewFeatureBean(featureValue);
			    if (childFeatureBean != null) {
				childFeatureBean.setParentNode(parentFeatureBean);
				featureBeanList.add(childFeatureBean);
			    }
			}
		    }

		}
	    }

	    // Creating SelectedFeatureBean for individual Selected features
	    Features indFeature = srcSelFeaturesType.getFeatures();
	    if (indFeature != null) {
		List<FeatureValueType> srcFeatureValueList = indFeature.getFeatureValueList(); // indFeature.getFeatureList();
		if (srcFeatureValueList != null) {
		    for (FeatureValueType featureValue : srcFeatureValueList) {
			SelectedFeatureValue featureBean = getNewFeatureBean(featureValue);
			if (featureBean != null) {
			    featureBean.setParentNode(null);
			    featureBeanList.add(featureBean);
			}
		    }
		}
	    }
	}

	// Update lineItemBean with updated SelectedFeatureValue list
	lineItemBean.setSelectedFeatureValues((Set<SelectedFeatureValue>) featureBeanList);
    }

    /**
     * Helper method to check FEATURE GROUP is exist in db or not
     *
     * @param featureBeanList
     * @param feature
     * @param externalId
     * @return
     */
    public SelectedFeatureValue getNewFeatureBeanFromFeatureGroup(FeatureGroup featureGroup) {
	String featureExtId = featureGroup.getExternalId();

	// Removed db validation check as it is already validated in previous
	// method call
	SelectedFeatureValue newFeatureBean = null;
	newFeatureBean = new SelectedFeatureValue();
	newFeatureBean.setExternalId(featureGroup.getExternalId());
	newFeatureBean.setParentNode(null);
	newFeatureBean.setFeatureType(featureGroup.getGroupType());
	newFeatureBean.setActive(true);
	newFeatureBean.setFeatureDate(Calendar.getInstance());
	return newFeatureBean;
    }

    /**
     * Helper method to check FEATURE is exist in db or not
     *
     * @param featureBeanList
     * @param feature
     * @param externalId
     * @return
     */
    public SelectedFeatureValue getNewFeatureBean(FeatureValueType feature) {
	String featureExtId = feature.getExternalId();

	// Removed db validation check as it is already validated in previous
	// method call
	// boolean exist = featureDao.isValidFeature( featureExtId);
	SelectedFeatureValue newFeatureBean = null;
	newFeatureBean = new SelectedFeatureValue();
	newFeatureBean.setExternalId(feature.getExternalId());
	newFeatureBean.setValue(feature.getValue());
	if (feature.getAvailable()) {
	    newFeatureBean.setAvailable(feature.getAvailable());
	}
	if (feature.getIncluded() != null) {
	    newFeatureBean.setIncluded(Boolean.TRUE);
	}
	if (feature.getType() != null) {
	    newFeatureBean.setDataType(feature.getType().toString());
	}
	else {
	    newFeatureBean.setDataType("");
	}
	// Setting Feature Price Values
	LineItemPriceInfo price = new LineItemPriceInfo();
	if (feature.getPrice() != null) {
	    logger.debug("Setting the Selected Feature Price Values for each feature: " + feature.getExternalId());
	    price.setBaseNonRecurringPrice(feature.getPrice().getBaseNonRecurringPrice());
	    price.setBaseNonRecurringPriceUnits(feature.getPrice().getBaseNonRecurringPriceUnits());
	    price.setBaseRecurringPrice(feature.getPrice().getBaseRecurringPrice());
	    price.setBaseRecurringPriceUnits(feature.getPrice().getBaseRecurringPriceUnits());
	}
	newFeatureBean.setPrice(price);
	newFeatureBean.setActive(true);
	newFeatureBean.setFeatureDate(Calendar.getInstance());
	return newFeatureBean;
    }

    public void updateOrder(SalesOrder salesOrder) {
    	orderManagementDao.updateLW(salesOrder);
    }

    public void saveLineItemAttributes(List<LineItem> updatedLineItemList) {

	if ((updatedLineItemList != null) && (updatedLineItemList.size() > 0)) {

	    for (LineItem lineItemBean : updatedLineItemList) {

		if ((lineItemBean != null) && (lineItemBean.getLineItemAttribute() != null)) {
		    lineItemAttributeDao.saveLineItemAttributes(lineItemBean.getLineItemAttribute());
		}
	    }

	}
    }

    /**
     * Updated the total order price once lineitems prices being updated
     *
     * @param orderBean
     * @param updateLineItemList
     */
    public void updateOrderPrice(SalesOrder orderBean, List<LineItem> updateLineItemList) {
		if (orderBean != null && updateLineItemList != null) {
		    double totalNonRecPrice = 0.0;
		    double totalRecPrice = 0.0;
	
		    List<LineItem> oldLIs = orderBean.getLineItems();
		    for (LineItem lineItem : updateLineItemList) {
		    	if (lineItem.getPrice() != null) {
		    			    		
		    		
		    		// FIX - PR-31: Add restriction for not to sum canceled line items
			    	if(null != lineItem.getCurrentStatus() && null != lineItem.getCurrentStatus().getStatus() && 
			    			(!lineItem.getCurrentStatus().getStatus().equalsIgnoreCase(LineItemStatusCodesType.CANCELLED_REMOVED.toString()) && 
			    					!lineItem.getCurrentStatus().getStatus().equalsIgnoreCase(LineItemStatusCodesType.PROCESSING_CANCELLED.toString()) && 
			    					!lineItem.getCurrentStatus().getStatus().equalsIgnoreCase(LineItemStatusCodesType.PROCESSING_DISCONNECTED.toString()))){ // For PR-35
			    		
			    		logger.info("old line item ext id :"+lineItem.getExternalId()+" status :"+lineItem.getCurrentStatus().getStatus());
			    		
			    		totalNonRecPrice = totalNonRecPrice + lineItem.getPrice().getBaseNonRecurringPrice();
		    		    totalRecPrice = totalRecPrice + lineItem.getPrice().getBaseRecurringPrice();
			    	}
		    	}
		    }
	
		    // Recalculating total order price based on new updated lineitem price and existing lineitem price which is not being updated
		    for (LineItem oldLI : oldLIs) {
				if (!isExist(updateLineItemList, oldLI.getExternalId())) {

		    		// FIX - PR-31: Add restriction for not to sum canceled line items
			    	if(null != oldLI.getCurrentStatus() && null != oldLI.getCurrentStatus().getStatus() && 
			    			(!oldLI.getCurrentStatus().getStatus().equalsIgnoreCase(LineItemStatusCodesType.CANCELLED_REMOVED.toString()) && 
			    					!oldLI.getCurrentStatus().getStatus().equalsIgnoreCase(LineItemStatusCodesType.PROCESSING_CANCELLED.toString()) && 
			    					!oldLI.getCurrentStatus().getStatus().equalsIgnoreCase(LineItemStatusCodesType.PROCESSING_DISCONNECTED.toString()))){ // For PR-35
			    		
			    		logger.info("old line item ext id :"+oldLI.getExternalId()+" status :"+oldLI.getCurrentStatus().getStatus());
			    		
			    		totalNonRecPrice = totalNonRecPrice + oldLI.getPrice().getBaseNonRecurringPrice();
					    totalRecPrice = totalRecPrice + oldLI.getPrice().getBaseRecurringPrice();
					    // break;
			    	}
				}
		    }
		    
		    if (orderBean.getTotalPrice() != null) {
				orderBean.getTotalPrice().setBaseNonRecurringPrice(totalNonRecPrice);
				orderBean.getTotalPrice().setBaseRecurringPrice(totalRecPrice);
		    }
		}
    }

    public Boolean isExist(List<LineItem> lineItemList, Long extId) {
	logger.info("Validating lineitem exist for a given order");
	Boolean isExist = false;
	for (LineItem li : lineItemList) {
	    if (extId.longValue() == li.getExternalId().longValue()) {
		isExist = true;
		break;

	    }
	}
	return isExist;
    }

    public void updateLineItemAttributes(LineItemType ucLineItemInfo, LineItem lineItemBean) {
	unmarshallLineItem.copyLineItemAttributes(lineItemBean, ucLineItemInfo, Boolean.TRUE);
    }
    
    /*
     * This method will save all accountHolders and 
     * set accountHolder externalId in the lineItem
     */
    @SuppressWarnings("unchecked")
	public void saveAccountHolders(List<LineItem> updatedLineItemList, final OrchestrationContext params, 
			List<AccountHolder> accountHolders) {
		if (accountHolders != null && accountHolders.size() > 0) {
			logger.debug("Updating account holders");
			accountHolderDao.persistAll(accountHolders);
			Map<Long, AccountHolder> lineItemAccountHolderMap = (Map<Long, AccountHolder>) params
					.get(lineItemAccountHolderMapKey);
			//set accountHolderExternalId in lineItem from the map
			if ((lineItemAccountHolderMap != null)
					&& (lineItemAccountHolderMap.size() > 0))
				for (LineItem li : updatedLineItemList) {
					AccountHolder accHolder = lineItemAccountHolderMap
							.get(li.getExternalId());
					if (accHolder != null) {
						li.setAccountHolderExternalId(accHolder
								.getId());
					}
				}
		}
    }
    
    public void savePaymentEvents(List<LineItem> updatedLineItemList) {
    	if ((updatedLineItemList != null) && (updatedLineItemList.size() > 0)) {
    		logger.debug("updating lineItem payment events");
    		for (LineItem lineItemBean : updatedLineItemList) {
	    		if (lineItemBean.getPaymentEvents().size() > 0) {
	    			paymentEventDao.persistLineItemPaymentEvents(lineItemBean.getPaymentEvents());
	    		}
    	    }
    	}
    }
    
    public void saveReasons(List<LineItem> updatedLineItemList) {
    	logger.info("Saving Reasons");
    	if ((updatedLineItemList != null) && (updatedLineItemList.size() > 0)) {
    		LineItem lineItemBean = updatedLineItemList.get(0);
    		long lineItemId = lineItemBean.getId();
    		if(lineItemBean.getReasons() != null){
    			reasonDao.remove(lineItemId);
    			Set<ReasonBean> reasonBeansSet = (Set<ReasonBean>) lineItemBean.getReasons();
    			reasonDao.persistAll(reasonBeansSet);
    		}
    	}
    }
    
    public void saveCoachings(List<LineItem> updatedLineItemList) {
    	logger.info("Saving Coachings");
    	if ((updatedLineItemList != null) && (updatedLineItemList.size() > 0)) {
    		LineItem lineItemBean = updatedLineItemList.get(0);
    		long lineItemId = lineItemBean.getId();
    		if(lineItemBean.getCoachings()!= null){
    			coachingDao.remove(lineItemId);
    			Set<CoachingBean> coachingBeansSet = (Set<CoachingBean>) lineItemBean.getCoachings();
    			coachingDao.persistAll(coachingBeansSet);
    		}
    	}
    }
}
