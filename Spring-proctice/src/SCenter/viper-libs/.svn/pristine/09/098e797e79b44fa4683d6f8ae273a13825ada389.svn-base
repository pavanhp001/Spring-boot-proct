package com.A.vm.util.converter.marshall;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.GDuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.A.enums.LineItemStatus;
import com.A.util.DateUtil;
import com.A.util.convert.SafeConvert;
import com.A.V.beans.ProductBase;
import com.A.V.beans.entity.AccountHolder;
import com.A.V.beans.entity.CoachingBean;
import com.A.V.beans.entity.Consumer;
import com.A.V.beans.entity.CustomerPaymentEvent;
import com.A.V.beans.entity.LineItem;
import com.A.V.beans.entity.LineItemAttribute;
import com.A.V.beans.entity.LineItemDetail;
import com.A.V.beans.entity.Product;
import com.A.V.beans.entity.ProductPromotion;
import com.A.V.beans.entity.ReasonBean;
import com.A.V.beans.entity.SelectedDialogue;
import com.A.V.beans.entity.StatusRecordBean;
import com.A.V.beans.entity.User;
import com.A.V.utility.StringUtils;
import com.A.Vdao.dao.CoachingDao;
import com.A.Vdao.dao.CustomerDao;
import com.A.Vdao.dao.PaymentEventDao;
import com.A.Vdao.dao.ReasonDao;
import com.A.vm.service.CatalogProductService;
import com.A.vm.service.CustomerAgentService;
import com.A.vm.service.OrderAgentService;
import com.A.vm.util.converter.mapper.AccountHolderMapper;
import com.A.xml.v4.AccountHolderType;
import com.A.xml.v4.AttributeDetailType;
import com.A.xml.v4.AttributeEntityType;
import com.A.xml.v4.ChannelType;
import com.A.xml.v4.CoachingDesc;
import com.A.xml.v4.CoachingList;
import com.A.xml.v4.CoachingType;
import com.A.xml.v4.Coachings;
import com.A.xml.v4.CustomerDocument.Customer;
import com.A.xml.v4.DriverLicenseType;
import com.A.xml.v4.ItemCategory;
import com.A.xml.v4.LineItemAttributeType;
import com.A.xml.v4.LineItemCollectionType;
import com.A.xml.v4.LineItemDetailType;
import com.A.xml.v4.LineItemPriceInfoType;
import com.A.xml.v4.LineItemStatusCodesType;
import com.A.xml.v4.LineItemStatusHistoryType;
import com.A.xml.v4.LineItemStatusType;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.PaymentEventType;
import com.A.xml.v4.PaymentsType;
import com.A.xml.v4.ProductBundleType;
import com.A.xml.v4.ProductCategoryListType;
import com.A.xml.v4.ProductPromotionType;
import com.A.xml.v4.ProductType;
import com.A.xml.v4.ProviderSourceType;
import com.A.xml.v4.ProviderType;
import com.A.xml.v4.ReasonCategory;
import com.A.xml.v4.ReasonDesc;
import com.A.xml.v4.ReasonList;
import com.A.xml.v4.ReasonType;
import com.A.xml.v4.Reasons;
import com.A.xml.v4.SchedulingInfoType;
import com.A.xml.v4.SecurityVerificationType;
import com.A.xml.v4.WishScheduleCollectionType;

/**
 * @author ebthomas
 *
 */
@Component("marshallLineItem")
public final class MarshallLineItem {

    public enum detail {
	PROMOTION, MARKETITEM, BUNDLE
    }

    private final static Logger logger = Logger.getLogger(MarshallLineItem.class);

    private final static List<String> listNumbers = Arrays.asList(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15" });

    public static final LineItemCollectionType DEFAULT_EMPTY_LIST = LineItemCollectionType.Factory.newInstance();

    public static final String INTEGER_TYPE = "integer";
    public static final String STRING_TYPE = "string";
    public static final String BOOLEAN_TYPE = "boolean";
    public static final String PROMOTION = "promotion";
    public static final String MARKET_ITEM = "marketItem";
    public static final String BUNDLE = "bundle";

    private static final String PRODUCT = "product";
    private static final String PRODUCT_PROMOTION = "productPromotion";
    private static final String PRODUCT_BUNDLE = "productBundle";

    @Autowired(required = false)
    private OrderAgentService orderAgentService;

    @Autowired(required = false)
    private CustomerAgentService customerAgentService;

    @Autowired
    private CustomerDao customerDao;
    
    @Autowired
    private PaymentEventDao paymentEventDao; 

    @Autowired
    private MarshallConsumers marshallConsumer;

    // When CM will load spring beans then this bean is not implemented there
    // ,so it should be optional
    @Autowired(required = false)
    private CatalogProductService catalogProductService;
    
    @Autowired
    private ReasonDao reasonDao;
    
    @Autowired
    private CoachingDao coachingDao;

    /**
     * Line Item Marshaller.
     */
    public MarshallLineItem() {
	super();
    }

    /**
         *
         */

    public Object lineItemBeans(final List<LineItem> lineItemBeansArg) {
	// lineItemBeans = lineItemBeansArg;

	return null;
    }

    /**
     * @return xxx
     */
    private Boolean validate() {
	return Boolean.TRUE;
    }

    /**
     * @param src
     *            source Line Item Bean
     * @param destLineItem
     *            destination
     */
	private void copyBasicLineItemInfo(final LineItem src,
			final LineItemType destLineItem, boolean includeAccountHolders) {
		if ((src == null) || (destLineItem == null)) {
			return;
		}
		if (src.getExternalId() != null) {
			destLineItem.setExternalId(src.getExternalId().longValue());
		}
		
		if (src.getAccountHolderExternalId() != null && includeAccountHolders) {
			destLineItem.setAccountHolderExternalId(src
					.getAccountHolderExternalId());
		}

		destLineItem.setLineItemNumber(src.getLineItemNumber());
		destLineItem.setProviderConfirmationNumber(src
				.getProviderConfirmationNumber());
		destLineItem.setProviderCustomerAccountNumber(src
				.getProviderCustomerAccountNumber());
		destLineItem.setSvcAddressExtId(src.getServiceAddressExtId());
		destLineItem.setBillingInfoExtId(src.getBillingInfoExtId());

		if (src.getLineItemOwnerExtId() != null) {

			Long ownerExtId = SafeConvert.convertLong(src
					.getLineItemOwnerExtId());
			if (ownerExtId != null) {
				Consumer consumerBean = customerDao
						.findCustomerByExternalId(ownerExtId);

				if (consumerBean != null) {

					Customer customer = destLineItem.addNewCustomer();
					customer.setExternalId(Long.valueOf(src
							.getLineItemOwnerExtId()));

					marshallConsumer.build(consumerBean, customer);
				}
			}
		}

		if (src.getProductDatasource() != null) {
			ProviderSourceType srcType = destLineItem.addNewProductDatasource();
			srcType.setStringValue(src.getProductDatasource());
			destLineItem.setProductDatasource(srcType);
		}
		if (src.getLeadId() != null) {
			destLineItem.setLeadId(src.getLeadId());
		} else {
			destLineItem.setLeadId(0L);
		}
		if (src.getNewPhone() != null) {
			destLineItem.setNewPhone(src.getNewPhone());
		}

		if (src.getCreditStatus() != null
				&& src.getCreditStatus().trim().length() > 0) {
			destLineItem.setCreditStatus(destLineItem.getCreditStatus()
					.forString(src.getCreditStatus()));
		} else {
			destLineItem.setCreditStatus(null);
		}

		if (src.getServiceType() != null
				&& src.getServiceType().trim().length() > 0) {
			destLineItem.setService(destLineItem.getService().forString(
					src.getServiceType()));
		} else {
			destLineItem.setService(null);
		}

		destLineItem.setLineItemCreateDate(src.getLineItemCreationDate());
		if (src.getIsEventSelected() != null) {
			destLineItem.setIsEventSelected(src.getIsEventSelected());
		} else {
			destLineItem.setIsEventSelected(Boolean.FALSE);
		}

		if (src.getIsEventCompleted() != null) {
			destLineItem.setIsEventCompleted(src.getIsEventCompleted());
		} else {
			destLineItem.setIsEventCompleted(Boolean.FALSE);
		}

		destLineItem.setEventType(src.getEventType());

		destLineItem.setIsTransfer(src.getIsTransfer());

	}

    /**
     * @param src
     *            source
     * @param destLineItem
     *            destination
     */
    public void copyStatusRecordBean(final LineItem src, final LineItemType destLineItem) {
	LineItemStatusType stType = destLineItem.addNewLineItemStatus();

	StatusRecordBean srcStatus = src.getCurrentStatus();

	if (srcStatus != null) {

	    // String agentId = resolveAgent(srcStatus);
	    String statusCode = srcStatus.getStatus();

	    if (statusCode.equals("submitted")) {
		statusCode = LineItemStatus.sales_submitted.name();
	    }

	    if (listNumbers.contains(statusCode)) {
		statusCode = LineItemStatus.sales_new_order.name();
	    }
	    String[] reasons = {};
	    if (srcStatus.getReasons() == null) {
		srcStatus.setReasons(new ArrayList<String>());
	    }
	    reasons = srcStatus.getReasons().toArray(reasons);

	    stType.setDateTimeStamp(srcStatus.getDateTimeStamp());
	    stType.setAgentId(srcStatus.getAgentExternalId());

	    try {
		LineItemStatusCodesType.Enum lineItemStatus = LineItemStatus.valueOf(statusCode).getTransportType();

		if (lineItemStatus != null) {
		    try {
			stType.setStatusCode(lineItemStatus);
		    }
		    catch (Exception e) {
			stType.setStatusCode(LineItemStatusCodesType.SUBMIT_FAILED);
			logger.warn(e.getMessage());
		    }
		}

	    }
	    catch (Exception e) {
		logger.warn("invalid status code:" + statusCode);
	    }

	    stType.setReasonArray(convert(reasons));
	}

    }

    public String resolveAgent(final StatusRecordBean srcStatus) {
	String agentId = "";
	if (srcStatus.getAgent() == null) {

	    User agent = null;

	    if (orderAgentService != null) {
		agent = orderAgentService.findAgentById(srcStatus.getAgentExternalId());
		srcStatus.setAgent(agent);
	    }
	    else if (customerAgentService != null) {
		agent = customerAgentService.findAgentById(srcStatus.getAgentExternalId());
		srcStatus.setAgent(agent);
	    }
	    else {
		srcStatus.setAgent(null);
	    }

	}

	if (srcStatus.getAgent() != null) {
	    agentId = String.valueOf(srcStatus.getAgent().getUserId());
	}

	return agentId;
    }

    public void copyHistoricStatus(final LineItem src, final LineItemType destLineItem) {
	List<StatusRecordBean> srbList = src.getHistoricStatus();
	LineItemStatusHistoryType stTypeHistory = destLineItem.addNewLineItemStatusHistory();

	if (srbList == null) {
	    return;
	}

	for (StatusRecordBean srb : srbList) {
	    LineItemStatusType destinationStatusHistory = stTypeHistory.addNewPreviousStatus();

	    // String agentId = resolveAgent(srb);
	    String agentId = srb.getAgentExternalId();

	    String statusCode = srb.getStatus();

	    if (statusCode.equals("submitted")) {
		statusCode = LineItemStatus.sales_submitted.name();
	    }

	    String[] reasons = {};
	    reasons = srb.getReasons().toArray(reasons);

	    if (srb != null) {
		destinationStatusHistory.setDateTimeStamp(srb.getDateTimeStamp());
		destinationStatusHistory.setAgentId(agentId);

		try {
		    destinationStatusHistory.setStatusCode(LineItemStatus.valueOf(statusCode).getTransportType());
		}
		catch (Exception e) {
		    logger.warn("invalid status code:" + statusCode);
		    destinationStatusHistory.setStatusCode(LineItemStatusCodesType.SALES_NEW_ORDER);
		}
		destinationStatusHistory.setReasonArray(convert(reasons));
	    }
	}
    }

    public int[] convert(String[] sarray) {
	if (sarray != null) {
	    int intarray[] = new int[sarray.length];
	    for (int i = 0; i < sarray.length; i++) {

		try {
		    intarray[i] = Integer.parseInt(sarray[i]);
		}
		catch (NumberFormatException nfe) {
		    logger.warn(sarray[i] + " invalid numeric value");
		    intarray[i] = 1;
		}
	    }
	    return intarray;
	}
	return null;
    }

    /**
     * @param lineitemCollection
     *            source
     * @param srcLineItem
     *            destination lineitem bean
     *
     */
    private LineItemType build(final LineItemCollectionType lineitemCollection, final LineItem srcLineItem, boolean includeAccountHolders) {
		logger.trace("marshalling lineitem response.");
		LineItemType lineItemType = lineitemCollection.addNewLineItem();
		if (srcLineItem != null) {
		    copyBasicLineItemInfo(srcLineItem, lineItemType, includeAccountHolders);
		    copyLineItemAttributes(srcLineItem, lineItemType);
		    copyLineItemDetail(srcLineItem, lineItemType);
		    copyStatusRecordBean(srcLineItem, lineItemType);
		    copyHistoricStatus(srcLineItem, lineItemType);
		    copyScheduleInfo(srcLineItem, lineItemType);
		    copyLineitemPriceInfo(srcLineItem, lineItemType);
		    copyActiveDialogues(srcLineItem, lineItemType);
		    copyCustomSelections(srcLineItem, lineItemType);
		    copyReasonsInfo(srcLineItem, lineItemType);
		    copyCoachingInfo(srcLineItem, lineItemType);
		    if(includeAccountHolders) {
	    		//srcLineItem.setPaymentEvents(paymentEventDao.findPaymentEventsByLineItemId(srcLineItem.getId()));
		    	copyPaymentEvents(srcLineItem, lineItemType);
		    }
		}
		logger.trace("marshalling lineitem response completed.");
		return lineItemType;
    }    
    private void copyCoachingInfo(LineItem srcLineItem,
			LineItemType lineItemType) {
    	Set<CoachingBean> coachingSet = srcLineItem.getCoachings();
    	if(coachingSet== null || coachingSet.isEmpty()){
    		long lineItemId = srcLineItem.getId();
    		coachingSet = coachingDao.getCoachingBeans(lineItemId);
		}
    	Coachings coachings = lineItemType.addNewCoachings();
    	for(CoachingBean coaching: coachingSet){
    		CoachingList coachingList = coachings.addNewCoaching();
    		CoachingDesc  coachingDesc = coachingList.addNewCoachingDesc();
    		CoachingType coachingType = coachingList.addNewCoachingType();
    		coachingList.setAgentId(coaching.getAgentId());
    		coachingDesc.setId(coaching.getCoachingId());
    		coachingType.setId(coaching.getCoachingGroupId());
    	} 
	}

	private void copyReasonsInfo(LineItem srcLineItem, LineItemType lineItemType) {
		Set<ReasonBean> reasonSet = srcLineItem.getReasons();
		if(reasonSet== null || reasonSet.isEmpty()){
			long lineItemId = srcLineItem.getId();
			reasonSet = reasonDao.getReasonBeans(lineItemId);
		}
    	Reasons reasons = lineItemType.addNewReasons();
    	for(ReasonBean reason: reasonSet){
    		ReasonList reasonList = reasons.addNewReason();
    		ReasonCategory reasonCategory = reasonList.addNewReasonCategory();
    		ReasonDesc reasonDesc = reasonList.addNewReasonDesc();
    		ReasonType reasonType = reasonList.addNewReasonType();
    		reasonList.setPriority(reason.getPriorityId());
    		reasonCategory.setId(reason.getReasonCategoryId());
    		reasonDesc.setId(reason.getReasonId());
    		reasonType.setId(reason.getReasonTypeId());
    	} 
	}

	/**
     * Method to populate lineitems extra info
     *
     * @param src
     * @param destLIType
     */
    public void copyLineItemAttributes(LineItem src, LineItemType destLIType) {
	if (src != null && src.getLineItemAttribute() != null) {
	    Set<LineItemAttribute> infoSet = src.getLineItemAttribute();

	    LineItemAttributeType destLIAttribType = destLIType.getLineItemAttributes() == null ? destLIType.addNewLineItemAttributes() : destLIType.getLineItemAttributes();

	    String infoSrc = "";
	    AttributeEntityType destAttrbEntityType = destLIAttribType.addNewEntity();
	    // LineItemAttribute[] infoList =(LineItemAttribute[])
	    // infoSet.toArray();

	    Iterator<LineItemAttribute> iterator = infoSet.iterator();
	    // for(int index = 0 ; index < infoSet.size() ; index++ ){
	    int index = 0;
	    while (iterator.hasNext()) {
		// LineItemAttribute srcLIAttribute = infoList[index];
		LineItemAttribute srcLIAttribute = iterator.next();
		AttributeDetailType attribDetailType = null;
		if (index == 0) {
		    infoSrc = srcLIAttribute.getSource();
		    destAttrbEntityType.setSource(infoSrc);
		    attribDetailType = destAttrbEntityType.addNewAttribute();
		    attribDetailType.setName(srcLIAttribute.getName());
		    attribDetailType.setValue(srcLIAttribute.getValue());
		    attribDetailType.setDescription(srcLIAttribute.getDescription());
		}
		else if (infoSrc.equalsIgnoreCase(srcLIAttribute.getSource())) {
		    infoSrc = srcLIAttribute.getSource();
		    // attrbEntityType.setSource(infoSrc);
		    attribDetailType = destAttrbEntityType.addNewAttribute();
		    attribDetailType.setName(srcLIAttribute.getName());
		    attribDetailType.setValue(srcLIAttribute.getValue());
		    attribDetailType.setDescription(srcLIAttribute.getDescription());
		}
		else {

		    destAttrbEntityType = findExistingEntityType(srcLIAttribute.getSource(), destLIAttribType);
		    if (destAttrbEntityType != null) {
			infoSrc = srcLIAttribute.getSource();
			attribDetailType = destAttrbEntityType.addNewAttribute();
			attribDetailType.setName(srcLIAttribute.getName());
			attribDetailType.setValue(srcLIAttribute.getValue());
			attribDetailType.setDescription(srcLIAttribute.getDescription());
		    }
		    else {

			destAttrbEntityType = destLIAttribType.addNewEntity();
			infoSrc = srcLIAttribute.getSource();
			destAttrbEntityType.setSource(infoSrc);
			AttributeDetailType attrbDtlType = destAttrbEntityType.addNewAttribute();
			attrbDtlType.setName(srcLIAttribute.getName());
			attrbDtlType.setValue(srcLIAttribute.getValue());
			attrbDtlType.setDescription(srcLIAttribute.getDescription());
		    }
		}
		index++;
	    }
	}
    }

    private AttributeEntityType findExistingEntityType(String source, LineItemAttributeType liAttribType) {
	if (liAttribType.getEntityList() != null) {
	    for (AttributeEntityType entityType : liAttribType.getEntityList()) {
		if (entityType.getSource().equalsIgnoreCase(source)) {
		    return entityType;
		}
	    }
	}
	return null;
    }

    public void copyCustomSelections(LineItem src, LineItemType lineItemType) {
	MarshallCustomSelection.buildCustomSelection(src, lineItemType);

    }
    
    public void copyPaymentEvents(LineItem lineItem, LineItemType lineItemType) {
    	if(lineItem.getPaymentEvents().size() > 0) {
    		PaymentsType paymentsType = lineItemType.addNewPayments();
    		for(CustomerPaymentEvent paymentEvent : lineItem.getPaymentEvents()) {
    			PaymentEventType paymentEventType = paymentsType.addNewPaymentEvent();
    			MarshallPaymentEvent.Builder.build(paymentEvent, paymentEventType);
    		}
    	}
    }

    public void copyActiveDialogues(LineItem src, LineItemType lineItemType) {
	MarshallSelectedDialogue.buildSelectedDialogue(src, lineItemType);
    }

    public void copyLineitemPriceInfo(LineItem src, LineItemType lineItemType) {
	if (src.getPrice() != null) {
	    LineItemPriceInfoType priceInfoType = lineItemType.addNewLineItemPriceInfo();
	    priceInfoType.setBaseNonRecurringPrice(src.getPrice().getBaseNonRecurringPrice());
	    priceInfoType.setBaseRecurringPrice(src.getPrice().getBaseRecurringPrice());

	    priceInfoType.setBaseNonRecurringPriceUnits(src.getPrice().getBaseNonRecurringPriceUnits());
	    priceInfoType.setBaseRecurringPriceUnits(src.getPrice().getBaseRecurringPriceUnits());

	    priceInfoType.setOnDeliveryPrice(String.format("%.2f", src.getPrice().getOnDeliveryPrice()));

	    logger.debug("copyLineitemPriceInfo:src.getPrice() != null: " + src.getPrice().getBaseNonRecurringPrice());
	}

    }

    public void copyLineItemDetail(LineItem src, LineItemType lineItemType) {
	if (src != null) {
	    Long prodUniqueId = src.getLineItemDetail().getProductUniqueId();
	    Product product = catalogProductService.findCatalogProductById(prodUniqueId);
	    transformToLineItemDetailType(src, lineItemType,product).getDetail();
	    if (!src.getLineItemDetail().getType().equalsIgnoreCase(PRODUCT_PROMOTION)) {
		MarshallFeatureValue.INSTANCE.copySelectedFeatureValue(src, lineItemType, product);
	    }
	}

    }

    @SuppressWarnings("static-access")
    private void transformToLineItemDetailProductType(LineItem li, LineItemDetail detailBean, LineItemDetailType detailType, LineItemType lineItemType,Product productBean) {

	if ((li != null) && (detailBean != null) && (detailType != null) && (lineItemType != null)) {
	    detailType.setDetailType(detailType.getDetailType().forString(PRODUCT));

	    detailType.setDetailType(detailType.getDetailType().forString(PRODUCT));

	    //Product productBean = catalogProductService.findCatalogProductById(detailBean.getProductUniqueId());

	    if (productBean != null) {

		ProductType productLineItemType = detailType.addNewDetail().addNewProductLineItem();

		if (productBean.getProductBase() != null && productBean.getProductBase().getName() != null) {
		    productLineItemType.setName(productBean.getProductBase().getName());
		}
		else {
		    productLineItemType.setName(detailBean.getLineItemDetailExternalId());
		}
		productLineItemType.setExternalId(detailBean.getLineItemDetailExternalId());

		detailType.setProductUniqueId(detailBean.getProductUniqueId());

		ProviderType providerType = productLineItemType.getProvider();

		if (providerType == null) {
		    providerType = productLineItemType.addNewProvider();
		}

		ProductCategoryListType catListType = productLineItemType.addNewProductCategoryList();
		ItemCategory itemCat = catListType.addNewProductCategory();

		// For harmony orders we will not have product detail, so display what is being sent to OME
		itemCat.setDisplayName(detailBean.getCategory());
		ChannelType chType = productLineItemType.addNewChannels();

		ProductBase productBase = productBean.getProductBase();

		if (productBase != null) {
		    // productLineItemType.setExternalId(productBase.getExternalId());
		    providerType.setExternalId(productBase.getProviderExternalId());
		    // itemCat.setDisplayName(productBase.getItemCategoryName());
		    productLineItemType.setProductCategoryList(catListType);
		    chType.addChannel(String.valueOf(productBase.getChannels()));
		    productLineItemType.setChannels(chType);

		    if (productBase.getExternalId() == null) {
			providerType.setExternalId(li.getProviderExternalId());
			lineItemType.setPartnerExternalId(li.getProviderExternalId());

			providerType = productLineItemType.getProvider();

			if (providerType == null) {
			    providerType = productLineItemType.addNewProvider();
			}

			if ((li != null) && (li.getProvider() != null)) {
			    providerType.setExternalId(li.getProviderExternalId());
			}

		    }
		}
		else {
		    providerType.setExternalId(li.getProviderExternalId());

		}

		if ((productLineItemType != null)) {

		    if (productLineItemType.getProvider() != null) {
			lineItemType.setPartnerExternalId(productLineItemType.getProvider().getExternalId());

		    }

		    providerType = productLineItemType.getProvider();

		    if (providerType == null) {
			providerType = productLineItemType.addNewProvider();
		    }

		    if ((li != null) && (li.getProvider() != null)) {
			providerType.setName(li.getProvider().getName());
			providerType.setExternalId(li.getProvider().getExternalId());
		    }

		}

	    }
	}
    }

    @SuppressWarnings("static-access")
    private LineItemDetailType transformToLineItemDetailType(LineItem VLineItem, LineItemType lineItemType,Product product) {

	LineItemDetailType lineItemDetailType = null;
	if (VLineItem != null) {

	    LineItemDetail VLineItemDetail = VLineItem.getLineItemDetailBean();
	    lineItemDetailType = lineItemType.addNewLineItemDetail();

	    if ((VLineItemDetail != null) && (VLineItemDetail.getType().equalsIgnoreCase(PRODUCT_PROMOTION))) {
		lineItemDetailType.setDetailType(lineItemDetailType.getDetailType().forString(PRODUCT_PROMOTION));

		//ProductPromotion productPromotion  = catalogProductService.findPromotion(product, VLineItemDetail);

		ProductPromotion productPromotion = catalogProductService.findProductPromotionById(VLineItemDetail.getProductUniqueId(),
			VLineItemDetail.getLineItemDetailExternalId());
		ProductPromotionType productPromotionType = lineItemDetailType.addNewDetail().addNewPromotionLineItem().addNewPromotion();
		productPromotionType.setExternalId(VLineItemDetail.getLineItemDetailExternalId());

		// Set applies to
		String[] appliesToArray = VLineItemDetail.getAppliesTo().split(",");
		for (String appliesTo : appliesToArray) {
		    lineItemDetailType.getDetail().getPromotionLineItem().addAppliesTo(Integer.parseInt(appliesTo));
		}

		lineItemDetailType.setProductUniqueId(VLineItemDetail.getProductUniqueId());

		if (productPromotion != null) {

		    productPromotionType.setDescription(productPromotion.getDescription());
		    productPromotionType.setPriceValue(productPromotion.getPriceValue());

		    String priceValueType = productPromotion.getPriceValueType() != null ? productPromotion.getPriceValueType() : "";
		    if (priceValueType.trim().length() > 0) {
			int pvType = 0;
			pvType = Integer.parseInt(priceValueType);
			logger.debug("Promotion PriceValueType :" + priceValueType);
			productPromotionType.setPriceValueType(productPromotionType.getPriceValueType().forInt(pvType));
		    }
		    else {
			logger.warn("PriceValueType is empty or null. Setting unspecifiedPriceValueType.");
			// default to unspecifiedPriceValueType if value is not found
			productPromotionType.setPriceValueType(productPromotionType.getPriceValueType().forInt(4));
		    }
		    productPromotionType.setShortDescription(productPromotion.getShortDescription());
		    productPromotionType.setPromoCode(productPromotion.getPromotionCode());
		    productPromotionType.setConditions(productPromotion.getConditions());
		    String promoType = productPromotion.getType();
		    if (promoType != null && promoType.trim().length() > 0) {
			logger.debug("Setting product promotion type : " + promoType);
			productPromotionType.setType(productPromotionType.getType().forInt(Integer.parseInt(promoType)));
		    }
		    else {
			logger.warn("Pormotion type is empty or null. Setting unspecifiedType");
			productPromotionType.setType(productPromotionType.getType().forInt(4));
		    }

		    if (productPromotion.getPromotionDuration() != null && !productPromotion.getPromotionDuration().equals("")) {
			productPromotionType.setPromotionDuration(new GDuration(productPromotion.getPromotionDuration()));
		    }

		}
		else {
		    logger.warn("Promotion Detail not found. Promotion details will not be populated in response xml.");
		}
	    }
	    else if ((VLineItemDetail != null) && (VLineItemDetail.getType().equalsIgnoreCase(PRODUCT))) {

		transformToLineItemDetailProductType(VLineItem, VLineItemDetail, lineItemDetailType, lineItemType,product);
	    }
	    else if ((VLineItemDetail != null) && (VLineItemDetail.getType().equalsIgnoreCase(PRODUCT_BUNDLE))) {
		lineItemDetailType.setDetailType(lineItemDetailType.getDetailType().forString(PRODUCT_BUNDLE));

		// BundleBean bundleBean =
		// bService.findBundleById(detailBean.getLineItemDetailExternalId());
		Product productBundle = catalogProductService.findCatalogProductById(VLineItemDetail.getProductUniqueId());
		if (productBundle != null) {

		    // BundleType bType =
		    // detailType.addNewDetail().addNewBundleLineItem();
		    ProductBundleType bundleType = lineItemDetailType.addNewDetail().addNewProductBundleLineItem();

		    if (productBundle.getProductBase() != null) {
			bundleType.setExternalId(productBundle.getProductBase().getExternalId());
			bundleType.setName(productBundle.getProductBase().getName());
			bundleType.setProviderId(productBundle.getProductBase().getProviderExternalId());
			// TODO need to fix provider name and channels
			// bundleType.setProviderName(productBundle.getProvider().getName());

			ChannelType chType = bundleType.addNewChannels();
			chType.addChannel(String.valueOf(productBundle.getProductBase().getChannels()));
			bundleType.setChannels(chType);

			if ((productBundle != null) && (productBundle.getProductBase() != null) && (productBundle.getProductBase().getProviderExternalId() != null)) {
			    lineItemType.setPartnerExternalId(productBundle.getProductBase().getProviderExternalId());
			}
		    }

		}
	    }

	}
	return lineItemDetailType;
    }

    /**
     * Method to marshall Scheduling information from LineitemBean to xml
     *
     * @param src
     *            source
     * @param destLineItem
     *            destination
     */
	public void copyScheduleInfo(final LineItem src,
			final LineItemType destLineItem) {
		// destLineItem.setDesiredStartDate(MarshallDate.getCalendar(src.getDesiredStartDate()));
		// destLineItem.setScheduledStartDate(MarshallDate.getCalendar(src.getScheduledStartDate()));
		// destLineItem.setActualStartDate(MarshallDate.getCalendar(src.getActualStartDate()));
		if (src.getLineitemScheduleInfo() != null) {
			SchedulingInfoType destSchInfo = destLineItem
					.addNewSchedulingInfo();

			destSchInfo.setDisconnectDate(MarshallDate.getCalendar(src
					.getLineitemScheduleInfo().getDisconnectDate(), src
					.getLineitemScheduleInfo().getDisconnectEEDate()));

			destSchInfo.setOrderDate(MarshallDate.getCalendar(src
					.getLineitemScheduleInfo().getOrderDate()));

			destSchInfo.setActualStartDate(MarshallDate.getCalendar(src
					.getLineitemScheduleInfo().getActualStartDate(), src
					.getLineitemScheduleInfo().getActualStartEEDate()));

			destSchInfo.setDesiredStartDate(MarshallDate.getCalendar(src
					.getLineitemScheduleInfo().getDesiredStartDate(), src
					.getLineitemScheduleInfo().getDesiredStartEEDate()));

			destSchInfo.setScheduledStartDate(MarshallDate.getCalendar(src
					.getLineitemScheduleInfo().getScheduledStartDate(), src
					.getLineitemScheduleInfo().getScheduledStartEEDate()));

			destSchInfo.setAppointmentComment(src.getLineitemScheduleInfo()
					.getAppointmentComment());

			if (src.getLineitemScheduleInfo().getInstallationFee() != null) {
				destSchInfo.setInstallationFee(src.getLineitemScheduleInfo()
						.getInstallationFee());
			} else {
				destSchInfo.setInstallationFee(new BigDecimal(1));
			}

			destSchInfo.setScheduleAsSoonAsPossible(src
					.getLineitemScheduleInfo().isScheduleAsSoonAsPossible());

			destSchInfo.setBillingInstallments(src.getLineitemScheduleInfo()
					.isBillingInstallments());
			destSchInfo.setResidenceType(src.getLineitemScheduleInfo()
					.getResidenceType());
			destSchInfo.setEarlierAppointmentDate(src.getLineitemScheduleInfo()
					.isEarlierAppointmentDate());

			// Convert to XML from stored XML format
			if ((src.getLineitemScheduleInfo() != null)
					&& (src.getLineitemScheduleInfo().getDesiredStartReq() != null)
					&& (src.getLineitemScheduleInfo().getDesiredStartReq()
							.length() > 0)) {
				try {
					WishScheduleCollectionType doc = WishScheduleCollectionType.Factory
							.parse(src.getLineitemScheduleInfo()
									.getDesiredStartReq());

					destSchInfo.setWishScheduleCollection(doc);
					destSchInfo.getWishScheduleCollection()
							.setScheduleAsSoonAsPossible(
									src.getLineitemScheduleInfo()
											.isScheduleAsSoonAsPossible());
				} catch (Exception e) {
					logger.warn("error " + e.getMessage());
				}

			}

			if ((destSchInfo.getWishScheduleCollection() == null)
					&& (src.getLineitemScheduleInfo()
							.isScheduleAsSoonAsPossible())) {
				destSchInfo.addNewWishScheduleCollection();
				destSchInfo.getWishScheduleCollection()
						.setScheduleAsSoonAsPossible(
								src.getLineitemScheduleInfo()
										.isScheduleAsSoonAsPossible());
			}

		}

	}

	public LineItemCollectionType buildLineItem(List<LineItem> lineItems) {
		return buildLineItem(lineItems, false);
	}

	
	public LineItemCollectionType buildLineItem(List<LineItem> lineItems, boolean includeAccountHolders) {
		LineItemCollectionType lineitemCollection = LineItemCollectionType.Factory
				.newInstance();
		logger.trace("Building lineitem response");
		for (LineItem lineItemBean : lineItems) {
			build(lineitemCollection, lineItemBean, includeAccountHolders);
		}
		logger.trace("Building lineitem response completed");
		return lineitemCollection;
	}

	
	/**
	 * @param lineitemCollection
	 *            source
	 * @return xxx destination
	 */
	public LineItemCollectionType build(final List<LineItem> lineitemCollection) {
		if (validate()) {
			return buildLineItem(lineitemCollection);
		}

		return DEFAULT_EMPTY_LIST;
	}

	public LineItemCollectionType build(final List<LineItem> lineitemCollection, boolean includeAccountHolders) {
		if (validate()) {
			return buildLineItem(lineitemCollection, includeAccountHolders);
		}

		return DEFAULT_EMPTY_LIST;
	}
	
    /**
     * A common method for all enums since they can't have another base class
     *
     * @param <T>
     *            Enum type
     * @param c
     *            enum type. All enums must be all caps.
     * @param string
     *            case insensitive
     * @return corresponding enum, or null
     */
    public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) {
	if (c != null && string != null) {
	    try {
		return Enum.valueOf(c, string.trim().toUpperCase());
	    }
	    catch (IllegalArgumentException ex) {
	    }
	}
	return null;
    }

    public OrderAgentService getOrderAgentService() {
	return orderAgentService;
    }

    public void setOrderAgentService(OrderAgentService orderAgentService) {
	this.orderAgentService = orderAgentService;
    }

    public CustomerAgentService getCustomerAgentService() {
	return customerAgentService;
    }

    public void setCustomerAgentService(CustomerAgentService customerAgentService) {
	this.customerAgentService = customerAgentService;
    }

    public CatalogProductService getCatalogProductService() {
	return catalogProductService;
    }

    public void setCatalogProductService(CatalogProductService catalogProductService) {
	this.catalogProductService = catalogProductService;
    }

}
