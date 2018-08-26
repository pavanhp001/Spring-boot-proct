package com.AL.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.AL.ie.activity.impl.ActivityUpdateStatusHistory;
import com.AL.pricing.OrderPriceUtil;
import com.AL.task.request.ProductServiceRequestBuilder;
import com.AL.validation.ProductResponseValidator;
import com.AL.validation.impl.PricingValidationHelper;
import com.AL.V.beans.entity.AddressBean;
import com.AL.V.beans.entity.AgentBean;
import com.AL.V.beans.entity.BillingInformation;
import com.AL.V.beans.entity.Consumer;
import com.AL.V.beans.entity.CustomerAddressAssociation;
import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.Product;
import com.AL.V.beans.entity.ProductFeature;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.entity.SalesOrderContext;
import com.AL.V.beans.entity.StatusRecordBean;
import com.AL.V.beans.entity.User;
import com.AL.Vdao.dao.BundleDao;
import com.AL.Vdao.dao.CustomerDao;
import com.AL.Vdao.dao.FeatureDao;
import com.AL.Vdao.dao.LineItemDao;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.Vdao.dao.PromotionDao;
import com.AL.Vdao.dao.SalesOrderContextDao;
import com.AL.vm.service.CustomerAgentService;
import com.AL.vm.service.CatalogProductService;
import com.AL.vm.service.OrderManagementService;
import com.AL.vm.vo.OrderChangeValueObject;
import com.AL.xml.v4.ApplicableType;
import com.AL.xml.v4.CatalogEntryType;
import com.AL.xml.v4.CatalogedProductListDocument.CatalogedProductList;
import com.AL.xml.v4.FeatureValueType;
import com.AL.xml.v4.LineItemDetailType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.PricingRequestResponseDocument;
import com.AL.xml.v4.ProcessingMessage;
import com.AL.xml.v4.ProductEnterpriseRequestDocument;
import com.AL.xml.v4.ProductEnterpriseResponseDocument;
import com.AL.xml.v4.ProductResponseType;
import com.AL.xml.v4.SelectedFeaturesType.FeatureGroup;
import com.AL.xml.v4.StatusType;
import com.AL.xml.v4.StatusType.ProcessingMessages;

@Component
public class OrderManagementServiceDefault implements OrderManagementService {

	private static final String ALL_ENTITIES_REQUESTED_WERE_FOUND = "All entities requested were found";

	private static final String PROMOTION_TYPE = "productPromotion";

	@Autowired
	private ProductServiceRequestBuilder productRequestBuilder;

	@Autowired
	private ProductResponseValidator productResponseValidator;

	@Autowired
	private OrderManagementDao orderManagementDao;

	@Autowired(required = false)
	private CustomerAgentService agentService;

	@Autowired
	private CatalogProductService catalogProductService;

	@Autowired
	private PromotionDao promotionDao;

	@Autowired
	private BundleDao bundleDao;

	@Autowired
	private LineItemDao lineItemDao;

	@Autowired
	private FeatureDao featureDao;

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private SalesOrderContextDao salesOrderContextDao;

	private Logger logger = Logger
			.getLogger(OrderManagementServiceDefault.class);

	public Boolean updateSalesOrder(final SalesOrder salesOrder) {
		return orderManagementDao.update(salesOrder);
	}

	/* (non-Javadoc)
	 * @see com.AL.vm.service.OrderManagementService#updateSalesOrderStatus(com.AL.V.beans.entity.SalesOrder)
	 */
	public boolean updateSalesOrderStatus(final SalesOrder salesOrder){
		return orderManagementDao.updateSalesOrder(salesOrder);
	}
	
	public SalesOrder findOrderById(final Long orderId) {
		SalesOrder salesOrder = orderManagementDao.findById(orderId);
		return salesOrder;
	}
	
	public SalesOrder findOrderById(final Long orderId, boolean includeAccountHolders) {
		SalesOrder salesOrder = orderManagementDao.findById(orderId, includeAccountHolders);
		return salesOrder;
	}

	/**
	 * @param orderBean
	 *            SalesOrder whose status will be updated
	 * @param orderChangeValueObject
	 *            value of the new status
	 * @return SalesRecordBean
	 */
	public StatusRecordBean updateLineItemStatus(final SalesOrder orderBean,
			final OrderChangeValueObject orderChangeValueObject) {
		if ((orderChangeValueObject == null) || (orderBean == null)) {
			throw new IllegalArgumentException(
					"null pointer exception:orderChangeValueObject:"
							+ orderChangeValueObject + " orderBean:"
							+ orderBean);
		}

		String liExtId = orderChangeValueObject.getLineItemExternalId();
		String status = orderChangeValueObject.getStatus();

		StatusRecordBean newStatus = null;

		if ((liExtId != null) && (status != null)) {
			try {
				long lineItemExtId = Long.parseLong(liExtId);

				LineItem lineitem = getLineItemFromSalesOrder(orderBean,
						lineItemExtId);

				if (lineitem != null) {
					orderChangeValueObject.setLineItem(lineitem);

					//ActivityUpdateStatusHistory.INSTANCE.addStatusRecordHistory(lineitem);


					newStatus = createNewStatus(lineitem,
							orderChangeValueObject);
				}
			} catch (NumberFormatException e) {

				if (logger.isEnabledFor(Level.ERROR)) {
					logger.error(e.getMessage());
				}

			}
		}

		return newStatus;

	}

	public StatusRecordBean createNewStatus(final LineItem lineItemBean,
			final OrderChangeValueObject orderChangeValueObject) {
		Calendar calNow = Calendar.getInstance();

		logger.debug("create new status:" + orderChangeValueObject.toString());
		StatusRecordBean statusRecord = new StatusRecordBean();
		lineItemBean.setCurrentStatus(statusRecord);
		statusRecord.setStatus(orderChangeValueObject.getStatus());
		statusRecord.setDateTimeStamp(calNow);

		logger.debug("validate agent id:" + orderChangeValueObject.getAgentId());
		User agent = getAgent(orderChangeValueObject.getAgentId());
		statusRecord.setAgent(agent);

		logger.debug("assign reasons");
		List<String> reasons = orderChangeValueObject.getReasonListAsString();
		statusRecord.setReasons(reasons);

		return statusRecord;
	}

	public LineItem getLineItemFromSalesOrder(final SalesOrder so,
			long searchingForlineItemExtId) {
		List<LineItem> liList = so.getLineItems();

		for (LineItem li : liList) {
			if (li.getExternalId() == searchingForlineItemExtId) {
				return li;
			}

		}

		return null;

	}

	public Boolean saveLineItemStatus(final LineItem lineItemBean) {
		return orderManagementDao.updateLineItemStatus(lineItemBean);
	}

	public User getAgent(final String agentId) {

		User agentBean = agentService.findAgentById(agentId.trim());

		if (agentBean == null) {
			 agentBean = new User();
			 agentBean.setUserId(0);
			 agentBean.setUserLogin(agentId);
			 agentBean.setUserName(agentId);
			 agentBean.setUserUpdatedDate(new Date());
		}

		return agentBean;
	}



	public PricingRequestResponseDocument prepareOrderForPricing(
			final OrderManagementRequestResponseDocument orderDocument) {
		// Request orderRequest =
		// orderDocument.getOrderManagementRequestResponse().getRequest();
		PricingRequestResponseDocument pricingRequest = OrderPriceUtil
				.copyOrderToPricing(orderDocument
						.getOrderManagementRequestResponse());
		return pricingRequest;
	}

	public void saveNewLineItems(final String agentId, final SalesOrder salesOrder) {
		lineItemDao.saveNewLineItems(agentId, salesOrder);
	}

	/**
	 * Validates Service address and Billing Info external id provided at
	 * LineItem level against the Customer's service address and Billing
	 * Information.
	 *
	 * @param orderBean
	 * @param lineItemTypeList
	 * @return
	 */
	public Map<String, String> validateAddressAndBillingRef(
			SalesOrder orderBean, List<LineItemType> lineItemTypeList) {
		return validateAddressAndBillingRef(orderBean, lineItemTypeList, null);
	}
	
	public Map<String, String> validateAddressAndBillingRef(
			SalesOrder orderBean, List<LineItemType> lineItemTypeList, Consumer customer) {
		Map<String, String> validationMsg = new HashMap<String, String>();
		Long customerId = orderBean.getConsumerExternalId();
		if(customer == null) {
			customer = customerDao.findCustomerByExternalId(customerId);
		}

		if (customer != null && lineItemTypeList != null
				&& !lineItemTypeList.isEmpty()) {
			for (LineItemType lineItemInfo : lineItemTypeList) {
				String srcBillingInfoId = lineItemInfo.getBillingInfoExtId();
				String srcSvcAddrId = lineItemInfo.getSvcAddressExtId();
				if (srcBillingInfoId != null) {
					Boolean isBillingRefValid = isBillingInfoExist(customer,
							srcBillingInfoId);
					if (!isBillingRefValid) {
						validationMsg
								.put("InvalidBillingInfo",
										"Billing Information External Id ("
												+ srcBillingInfoId
												+ ") provided for LineItem does not match with Customer's Billing Information Id !!!");
					}
				}

				if (srcSvcAddrId != null) {
					Boolean isSvcAddressRefValid = isSvcAddressExist(customer,
							srcSvcAddrId);
					if (!isSvcAddressRefValid) {
						validationMsg
								.put("InvalidServiceAddress",
										"Service address External Id ("
												+ srcSvcAddrId
												+ ") provided for LineItem does not match with Customer's Service Address Id!!!");
					}
				}

			}
		}

		return validationMsg;
	}

	/**
	 * Validing service address external id provided at lineitem level exist in
	 * Customer Information and is it Service Address or not.
	 *
	 * @param customer
	 * @param srcSvcAddrId
	 * @return
	 */
	private Boolean isSvcAddressExist(Consumer customer, String srcSvcAddrId) {
		Boolean isValid = Boolean.FALSE;

		List<CustomerAddressAssociation> custAddrList = new ArrayList<CustomerAddressAssociation>(
				customer.getAddresses());

		for (CustomerAddressAssociation custAddr : custAddrList) {
			AddressBean address = custAddr.getPk().getAddress();
			String role = custAddr.getAddressRole();
			String aExtId = String.valueOf(address.getExternalId());
			if (aExtId.equalsIgnoreCase(srcSvcAddrId)
					&& role.equalsIgnoreCase("ServiceAddress")) {
				isValid = Boolean.TRUE;
				break;
			}
		}
		return isValid;
	}

	/**
	 * Validating billing information external id provided at lineitem level
	 * exist in Customer information.
	 *
	 * @param customer
	 * @param srcBillingInfoId
	 * @return
	 */
	private Boolean isBillingInfoExist(Consumer customer,
			String srcBillingInfoId) {
		Boolean isValid = Boolean.FALSE;

		List<BillingInformation> billingList = new ArrayList<BillingInformation>(
				customer.getBillingInfoList());
		if (billingList != null) {
			for (BillingInformation billingInfo : billingList) {
				String bExtId = String.valueOf(billingInfo.getExternalId());
				if (srcBillingInfoId.equalsIgnoreCase(bExtId)) {
					isValid = Boolean.TRUE;
					break;
				}
			}
		}
		return isValid;
	}

	/**
	 * Helper method to validate selected features and populate the error
	 * message
	 */
	@Transactional
	public Map<String, String> validateSelectedFeatures(
			List<LineItemType> lineItemTypeList) {
		Map<String, String> validationMsg = new HashMap<String, String>();

		// Disabling validation of features and feature groups as it will be
		// done by
		// Pricing
		/*
		 * if (lineItemTypeList != null && !lineItemTypeList.isEmpty()) { for
		 * (LineItemType lineItemInfo : lineItemTypeList) { Long productUniqueId
		 * = lineItemInfo.getLineItemDetail() .getProductUniqueId(); if
		 * (productUniqueId != null) { Product product = catalogProductService
		 * .findCatalogProductById(productUniqueId); SelectedFeaturesType sfType
		 * = lineItemInfo .getSelectedFeatures(); if (sfType != null) { Features
		 * indFeatures = sfType.getFeatures(); if (indFeatures != null &&
		 * indFeatures.getFeatureValueList() != null) {
		 * validateIndividualFeatures( indFeatures.getFeatureValueList(),
		 * validationMsg, product); } List<FeatureGroup> fGroupList = sfType
		 * .getFeatureGroupList(); validateFeatureGroup(fGroupList,
		 * validationMsg, product); } } else { validationMsg
		 * .put("ProductUniqueId",
		 * "Product unique id cannot be null. Please provide product unique id for the product."
		 * ); } } }
		 */
		return validationMsg;
	}

	/**
	 * Helper method to validate individual features. Method will just check
	 * that provided external id of the individual feature is present in db or
	 * not
	 *
	 * @param features
	 * @param validationMsg
	 */
	public void validateIndividualFeatures(List<FeatureValueType> fvTypeList,
			Map<String, String> validationMsg, Product product) {
		if (fvTypeList != null && !fvTypeList.isEmpty() && product != null) {
			List<ProductFeature> productFeatureList = product
					.getProductFeatures();

			if (productFeatureList != null && !productFeatureList.isEmpty()) {
				for (FeatureValueType fvType : fvTypeList) {
					String fExtId = fvType.getExternalId();
					Boolean isValidFeature = Boolean.FALSE;
					for (ProductFeature feature : productFeatureList) {
						String prodFeatId = feature.getProductFeatureBase()
								.getExternalId().trim();
						if (prodFeatId.equalsIgnoreCase(fExtId)) {
							isValidFeature = Boolean.TRUE;
							break;
						}
					}
					if (!isValidFeature) {
						validationMsg.put(fExtId, "Invalid Selected Feature ("
								+ fExtId + ")");
					}
				}
			} else {
				validationMsg.put("", "Product does not contain any features.");
			}
		}
	}

	/**
	 * Helper method to validate Feature group. Method will check provided
	 * feature group exist in db or not. It will also check the all the feature
	 * values of that group if it is present in feature group container
	 *
	 * @param fGroupList
	 * @param validationMsg
	 */
	private void validateFeatureGroup(List<FeatureGroup> fGroupList,
			Map<String, String> validationMsg, Product product) {
		if (fGroupList != null && !fGroupList.isEmpty()) {

			List<ProductFeature> productFeatureList = product
					.getProductFeatures();

			if (productFeatureList != null && !productFeatureList.isEmpty()) {
				for (FeatureGroup fgType : fGroupList) {
					String fgExtId = fgType.getExternalId();

					for (ProductFeature prodFeature : productFeatureList) {
						String prodFeatExtId = prodFeature
								.getProductFeatureBase().getExternalId().trim();
						if (fgExtId.equalsIgnoreCase(prodFeatExtId)) {
							Boolean isFeatureGroup = prodFeature
									.getProductFeatureBase().getFeatureGroup();
							if (!isFeatureGroup) {
								validationMsg.put(fgExtId,
										"Invalid Selected Feature Group ("
												+ fgExtId + ")");
							}
						}
					}
					List<FeatureValueType> fvList = fgType
							.getFeatureValueList();
					validateIndividualFeatures(fvList, validationMsg, product);
				}
			} else {
				validationMsg.put("",
						"Product does not contain any feature group");
			}
		}
	}

	public void broadcast(String strToBroadcastOriginal) {

		throw new IllegalArgumentException(
				"Broadcast Not Implemented.  Illegal Function");
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public OrderManagementDao getOrderManagementDao() {
		return orderManagementDao;
	}

	public void setOrderManagementDao(OrderManagementDao orderManagementDao) {
		this.orderManagementDao = orderManagementDao;
	}

	public AgentBean getAgent(
			org.apache.xmlbeans.impl.piccolo.xml.EntityManager em,
			String agentId) {
		// TODO Auto-generated method stub
		return null;
	}

	public CustomerAgentService getAgentService() {
		return agentService;
	}

	public void setAgentService(CustomerAgentService agentService) {
		this.agentService = agentService;
	}

	/**
	 * A method to prepare Current Status based on response received from
	 * Pricing Service
	 */
	public StatusRecordBean createCurrentStatus(
			PricingRequestResponseDocument pricingResponse,
			SalesOrder salesOrder) {
		Calendar calNow = Calendar.getInstance();
		String statusMsg = PricingValidationHelper
				.getPricingStatus(pricingResponse);
		List<String> reasons = new ArrayList<String>();
		reasons.add(statusMsg);
		StatusRecordBean currentStatus = new StatusRecordBean();
		currentStatus.setDateTimeStamp(calNow);
		currentStatus.setStatus("100");
		currentStatus.setReasons(reasons);
		currentStatus.setAgent(agentService.findAgentById(salesOrder
				.getAgentId()));
		return currentStatus;
	}

	/**
	 * Service method to save Sales Order Contexts
	 *
	 * @param socList
	 */
	public void saveSalesContext(Set<SalesOrderContext> socList) {
		salesOrderContextDao.save(socList);
	}

	public ProductEnterpriseRequestDocument prepareProductServiceRequest(
			OrderManagementRequestResponseDocument orderDoc) {
		return productRequestBuilder.buildRequest(orderDoc);
	}

	public Boolean validateProductResponseForFatalError(
			ProductEnterpriseResponseDocument productResponseDoc) {
		return productResponseValidator.hasFatalError(productResponseDoc);
	}

	/**
	 * Method to copy product unique id returned by ProductService to OME order.
	 *
	 */
	public Map<String, String> copyLineItemInfoFromProductToOrder(
			ProductEnterpriseResponseDocument productResponse,
			OrderManagementRequestResponseDocument orderDoc) {

		Map<String, String> validationMsg = new HashMap<String, String>();
		if (productResponse != null && orderDoc != null) {
			Request orderReq = orderDoc.getOrderManagementRequestResponse()
					.getRequest();
			List<LineItemType> destLIList = orderReq.getOrderInfo()
					.getLineItems().getLineItemList();
			// List<LineItemType> orgLIList =
			// orderReq.getOrderInfo().getLineItems().getLineItemList();
			ProductResponseType prodRes = (ProductResponseType) productResponse
					.getProductEnterpriseResponse().getResponse();
			List<CatalogedProductList> prodList = prodRes
					.getCatalogedProductListList();
			if (prodList != null & !prodList.isEmpty()) {
				for (CatalogedProductList catProdList : prodList) {
					List<CatalogEntryType> catEntryList = catProdList
							.getProductCatalogEntryList();
					for (CatalogEntryType catEntry : catEntryList) {
						String uniqueId = catEntry.getCatalogId();
						String lineItemNo = catEntry.getInstanceId();
						Boolean validProduct = Boolean.TRUE;
						String extId = catEntry.getExternalId();
						if (catEntry.getStatus() != null) {
							validProduct = catEntry.getStatus().getSuccess();
						}

						for (LineItemType liType : destLIList) {
							String destLINumber = String.valueOf(liType
									.getLineItemNumber());
							if (uniqueId != null
									&& !uniqueId.equalsIgnoreCase("0")) {

								// Copying product unique id
								if (destLINumber.equalsIgnoreCase(lineItemNo)) {
									liType.getLineItemDetail()
											.setProductUniqueId(
													Long.valueOf(uniqueId));
								}

								if (!validProduct) {
									validationMsg.put(lineItemNo, "LineItem ("
											+ extId + ") is not valid.");

								}
								// TODO write code to update lineitem product
								// datasource in order document based on flag
								// returned
								// from product service
								// liType.setProductDatasource( );

								// break;
							} else {
								populateProductServiceMessages(productResponse,
										validationMsg);
								// validationMsg.put( "0",
								// "Product service failed!!! \n Invalid Catalog Id returned!!!"
								// );
							}

						}
					}
				}
			}
		} else {
			validationMsg.put("0", "Product service failed!!!!");
		}
		return validationMsg;
	}

	/**
	 * Method to copy product unique id returned by ProductService to actual
	 * request.
	 *
	 */
	public Map<String, String> copyProductUniqueIdFromProductToOrder(
			ProductEnterpriseResponseDocument productResponse,
			OrderManagementRequestResponseDocument orderDoc) {

		Map<String, String> validationMsg = new HashMap<String, String>();
		if (productResponse != null && orderDoc != null) {
			Request orderReq = orderDoc.getOrderManagementRequestResponse()
					.getRequest();
			List<LineItemType> destLIList = orderReq.getNewLineItems()
					.getLineItemList();

			ProductResponseType prodRes = (ProductResponseType) productResponse
					.getProductEnterpriseResponse().getResponse();
			List<CatalogedProductList> prodList = prodRes
					.getCatalogedProductListList();

			if (prodList != null & !prodList.isEmpty()) {
				for (CatalogedProductList catProdList : prodList) {
					List<CatalogEntryType> catEntryList = catProdList
							.getProductCatalogEntryList();
					for (CatalogEntryType catEntry : catEntryList) {
						String uniqueId = catEntry.getCatalogId();
						String lineItemNo = catEntry.getInstanceId();

						Boolean validProduct = Boolean.TRUE;
						String extId = catEntry.getExternalId();
						if (catEntry.getStatus() != null) {
							validProduct = catEntry.getStatus().getSuccess();
						}

						for (LineItemType liType : destLIList) {
							String liProviderExternalId = null;
							if(null != liType.getLineItemDetail() && null != liType.getLineItemDetail().getDetailType() && liType.getLineItemDetail().getDetailType().toString().equalsIgnoreCase("product")){
								// Dint do null check, if execution reached here then definitely value must be there, else earlier product call itself would have failed
								liProviderExternalId = liType.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId();
							}
							if (null != liProviderExternalId && catEntry.getProviderExternalId().equalsIgnoreCase(liProviderExternalId)
									&& uniqueId != null
									&& !uniqueId.equalsIgnoreCase("0")) {
								// Copying product unique id
								liType.getLineItemDetail().setProductUniqueId(
										Long.valueOf(uniqueId));


								if (!validProduct) {
									validationMsg.put(lineItemNo, "LineItem ("
											+ extId + ") is not valid.");

								}
								// TODO write code to update lineitem product
								// datasource in order document based on flag
								// returned
								// from product service
								// liType.setProductDatasource( );

								break;
							} else {
								populateProductServiceMessages(productResponse,
										validationMsg);
								// validationMsg.put( "0",
								// "Product service failed!!! \n Invalid Catalog Id returned!!!"
								// );
							}

						}
					}
				}
			}
		} else {
			validationMsg.put("0", "Product service failed!!!!");
		}
		return validationMsg;
	}

	/**
	 * Helper method to populate processing message from product service.
	 *
	 * @param productResponse
	 * @param validationMsg
	 */
	private void populateProductServiceMessages(
			ProductEnterpriseResponseDocument productResponse,
			Map<String, String> validationMsg) {
		if (productResponse.getProductEnterpriseResponse().getStatus() != null) {
			StatusType prodStatusType = productResponse
					.getProductEnterpriseResponse().getStatus();
			if (null != prodStatusType.getProcessingMessages()) {
				ProcessingMessages procMsgs = prodStatusType
						.getProcessingMessages();
				List<ProcessingMessage> msgList = procMsgs.getMessageList();
				if (msgList != null && !msgList.isEmpty()) {
					for (ProcessingMessage msg : msgList) {
						//TODO hack to find if res from prod service is success or not. Prod svs do not return any code like INFO,WARN or ERROR which
						//can identify res is success or failed.
						if(!msg.getText().equalsIgnoreCase(ALL_ENTITIES_REQUESTED_WERE_FOUND)){
							validationMsg.put(String.valueOf(msg.getCode()),
								msg.getText());
						}
					}
				}
			}
		}
	}

	public Long getProductUniqueId(
			ProductEnterpriseResponseDocument productResponse,
			OrderManagementRequestResponseDocument orderDoc) {

		Long prodId = -1L;
		if (productResponse != null && orderDoc != null) {
			Request orderReq = orderDoc.getOrderManagementRequestResponse()
					.getRequest();
			ProductResponseType prodRes = (ProductResponseType) productResponse
					.getProductEnterpriseResponse().getResponse();
			List<CatalogedProductList> prodList = prodRes
					.getCatalogedProductListList();
			if (prodList != null & !prodList.isEmpty()) {
				for (CatalogedProductList catProdList : prodList) {
					List<CatalogEntryType> catEntryList = catProdList
							.getProductCatalogEntryList();
					for (CatalogEntryType catEntry : catEntryList) {
						String uniqueId = catEntry.getCatalogId();
						prodId = Long.valueOf(uniqueId);
						break;
					}
				}
			}
		}
		return prodId;
	}

	/**
	 * Helper method to copy ProductUniqueId of a product where promotion
	 * applies to. This ProductUniqueId will be used later on during Promotion
	 * unmarshalling to get the details about the promotion.
	 */
	public void copyPromotionUniqueId(
			ProductEnterpriseResponseDocument productResponse,
			OrderManagementRequestResponseDocument orderDoc) {

		logger.trace("Copying product unique id");
		Request orderReq = orderDoc.getOrderManagementRequestResponse()
				.getRequest();
		List<LineItemType> destLIList = orderReq.getOrderInfo().getLineItems()
				.getLineItemList();
		List<LineItemType> tempLIList = orderReq.getOrderInfo().getLineItems()
				.getLineItemList();
		if (destLIList != null && !destLIList.isEmpty()) {
			// Loop through the Lineitems and find promotion where it applies to
			for (LineItemType liType : destLIList) {
				String detailType = liType.getLineItemDetail().getDetailType()
						.toString();
				if (detailType.equalsIgnoreCase(PROMOTION_TYPE)) {
					LineItemDetailType promotionDetailType = liType
							.getLineItemDetail();
					ApplicableType appType = promotionDetailType.getDetail()
							.getPromotionLineItem();
					List<Integer> appliesToList = appType.getAppliesToList();
					// As per current applies to it is returning a list of
					// lineitem number where it applies to
					// but actually in business promotion will be applied to
					// only one lineitem
					int appliesTo = -1;
					if (appliesToList != null && !appliesToList.isEmpty()) {
						appliesTo = appliesToList.get(0);

					}

					// Loop through the lineitem list, find the lineitem where
					// promotion applies to, get its productuniqueid
					// and set it to promotion
					for (LineItemType tmpLiType : tempLIList) {
						int lineItemNumber = tmpLiType.getLineItemNumber();
						if (appliesTo == lineItemNumber) {
							promotionDetailType.setProductUniqueId(tmpLiType
									.getLineItemDetail().getProductUniqueId());
							break;
						}
					}

				}
			}
		}
	}

	/**
	 * Helper method to copy ProductUniqueId of a product where promotion
	 * applies to. This ProductUniqueId will be used later on during Promotion
	 * marshalling to get the details about the promotion. This method will find
	 * the unique id from existing lineitems or new lineitem provided in
	 * AddLineItem request
	 */
	public void copyPromotionUniqueId(OrderChangeValueObject valueObject,
			SalesOrder salesOrder) {
		List<LineItemType> tempLIList = valueObject.getLineItemCollectionType()
				.getLineItemList();
		List<LineItemType> newLIList = valueObject.getLineItemCollectionType()
				.getLineItemList();

		if (newLIList != null && !newLIList.isEmpty()) {
			// Loop through the Lineitems and find promotion where it applies to
			for (LineItemType liType : newLIList) {
				String detailType = liType.getLineItemDetail().getDetailType()
						.toString();
				if (detailType != null
						&& detailType.equalsIgnoreCase(PROMOTION_TYPE)) {
					LineItemDetailType promotionDetailType = liType
							.getLineItemDetail();
					ApplicableType appType = promotionDetailType.getDetail()
							.getPromotionLineItem();
					List<Integer> appliesToList = appType.getAppliesToList();
					// As per current applies to it is returning a list of
					// lineitem number where it applies to
					// but actually in business promotion will be applied to
					// only one lineitem
					int appliesTo = -1;
					if (appliesToList != null && !appliesToList.isEmpty()) {
						appliesTo = appliesToList.get(0);

					}

					if (valueObject.isAppliesToLineItemIncluded()) {
						// if new promotion applies to lineitem which is part of
						// the request then
						// loop through it , get its product unique id
						// and store it for new product lineitem which will be
						// used later on during marshalling promotion
						for (LineItemType tmpLiType : tempLIList) {
							int lineItemNumber = tmpLiType.getLineItemNumber();
							if (appliesTo == lineItemNumber) {
								promotionDetailType
										.setProductUniqueId(tmpLiType
												.getLineItemDetail()
												.getProductUniqueId());
								break;
							}
						}
					} else {
						// if new promotion applies to existing lineitem then
						// loop through it , get its product unique id
						// and store it for new product lineitem which will be
						// used later on during marshalling promotion
						List<LineItem> existingLIList = salesOrder
								.getLineItems();
						if (existingLIList != null && !existingLIList.isEmpty()) {
							for (LineItem existingLI : existingLIList) {
								int lineItemNumber = existingLI
										.getLineItemNumber();
								if (appliesTo == lineItemNumber) {
									promotionDetailType
											.setProductUniqueId(existingLI
													.getLineItemDetail()
													.getProductUniqueId());
									break;
								}
							}
						} else {
							throw new IllegalArgumentException(
									"New promotion applies to existing lineitem is not valid!!!");
						}

					}

				}
			}
		}
	}

	public ProductEnterpriseRequestDocument prepareProductServiceRequest(
			OrderChangeValueObject valueObject) {
		return productRequestBuilder.buildRequestForAddLineitem(
				valueObject.getLineItemCollectionType(),
				valueObject.getCorrelationId());
	}

	/**
	 * A method to check if addLineItem request contains any product or not in
	 * the request. If it exist then later on product service request will be
	 * build otherwise not.
	 */
	public boolean isProductExist(OrderChangeValueObject valueObject) {

		logger.debug("Validating product lineitem exist in request or not.");
		Boolean isProductExist = Boolean.FALSE;
		if (valueObject.getLineItemCollectionType() != null) {
			List<LineItemType> lineItemTypeList = valueObject
					.getLineItemCollectionType().getLineItemList();
			if (lineItemTypeList != null && !lineItemTypeList.isEmpty()) {
				for (LineItemType liType : lineItemTypeList) {
					LineItemDetailType liDetailType = liType
							.getLineItemDetail();
					String detailType = liDetailType.getDetailType().toString();
					if (detailType.equalsIgnoreCase("product")) {
						isProductExist = Boolean.TRUE;
						break;
					}
				}
			}
		}

		return isProductExist;
	}

}
