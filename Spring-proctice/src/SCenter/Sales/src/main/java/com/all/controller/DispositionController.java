package com.AL.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.webflow.execution.Action;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.AL.html.Div;
import com.AL.html.Fieldset;
import com.AL.ui.builder.HtmlBuilder;
import com.AL.ui.builder.SalesDialogueBuilder;
import com.AL.ui.constants.Constants;
import com.AL.ui.constants.ControllerConstants;
import com.AL.ui.dao.ConfigDao;
import com.AL.ui.dao.CustomerTrackerDao;
import com.AL.ui.dao.DispoCatAssocDao;
import com.AL.ui.dao.DispositionDao;
import com.AL.ui.dao.SalesCallDao;
import com.AL.ui.dao.SalesSessionDao;
import com.AL.ui.domain.SalesSession;
import com.AL.ui.domain.SystemConfig;
import com.AL.ui.domain.sales.DispoCatAssoc;
import com.AL.ui.domain.sales.Disposition;
import com.AL.ui.factory.CartLineItemFactory;
import com.AL.ui.factory.CartSalesContextFactory;
import com.AL.ui.factory.SalesUtilsFactory;
import com.AL.ui.factory.WarmTransferFactory;
import com.AL.ui.service.V.CustomerService;
import com.AL.ui.service.V.LineItemService;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.util.DialogueUtil;
import com.AL.ui.util.LineItemUtil;
import com.AL.ui.util.SalesUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.util.WarmTransferHtmlFactory;
import com.AL.ui.vo.Address;
import com.AL.ui.vo.CartError;
import com.AL.ui.vo.ErrorList;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.util.DateUtil;
import com.AL.V.exception.RecoverableException;
import com.AL.V.exception.UnRecoverableException;
import com.AL.V.factory.CustomerFactory;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.cm.v4.Attributes;
import com.AL.xml.cm.v4.CsatSurveyType;
import com.AL.xml.cm.v4.CsatSurveysType;
import com.AL.xml.cm.v4.CustomerAttributeEntityType;
import com.AL.xml.cm.v4.CustomerAttributeType;
import com.AL.xml.cm.v4.CustomerContextType;
import com.AL.xml.cm.v4.CustomerType;
import com.AL.xml.cm.v4.NotificationEventCollectionType;
import com.AL.xml.cm.v4.NotificationEventType;
import com.AL.xml.di.v4.DataFieldMatrixType;
import com.AL.xml.di.v4.DataFieldType;
import com.AL.xml.di.v4.DataGroupType;
import com.AL.xml.di.v4.DialogueType;
import com.AL.xml.v4.AttributeDetailType;
import com.AL.xml.v4.AttributeEntityType;
import com.AL.xml.v4.LineItemStatusCodesType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderLineItemDetailTypeType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;

@Controller("DispositionController")
public class DispositionController implements Action{
	private static final Logger logger = Logger.getLogger(DispositionController.class);

	private final String context = "disposition";

	@Autowired
	private DispositionDao dispositionDao;

	@Autowired
	private DispoCatAssocDao dispoCatAssocDao;

	@Autowired
	private ConfigDao configDao;

	@Autowired
	private SalesSessionDao salesSessionDao;
	
	@Autowired
	private CustomerTrackerDao customerTrackerDao;
	

	public Event execute(RequestContext requestContext) throws Exception {
		HttpServletRequest request = (HttpServletRequest)requestContext.getExternalContext().getNativeRequest();
		StopWatch timer = new StopWatch();
		timer.start();
		long startTimer = 0;
		try{
			logger.info("begin_of_DispositionController");			
			String emailFlag = request.getParameter("emailFlag");
			String emailValue = request.getParameter("bestEmail");
			String csatVal = request.getParameter("CloseCallNoSale_CSAT_Survey_Email");
			Long customerID =(Long)request.getSession().getAttribute("customerID");

			List<SystemConfig> systemConfigList = configDao.findByContext(context);
			ErrorList errorList = new ErrorList();
			SystemConfig systemConfig = new SystemConfig();

			if(systemConfigList != null){
				systemConfig = systemConfigList.get(0);
			}
			String timeOut = "0";
			if(!Utils.isBlank(systemConfig.getValue())){
				timeOut = systemConfig.getValue();
			}
			else{
				timeOut = "5";
			}

			SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
			String agentId = salesCenterVo.getValueByName("agent.id");

			OrderType order = null;
			Long orderId = (Long)request.getSession().getAttribute("orderId");
			if( orderId!=null ){
				startTimer=timer.getTime();
				order = OrderService.INSTANCE.getOrderByOrderNumber(String.valueOf(orderId), agentId, null, null, false, null);
				logger.info("TimeTakenforOrderserviceCall="+(timer.getTime()-startTimer));
			}

			if (order != null && !Utils.isBlank(order.getAgentId())) 
			{
				agentId = order.getAgentId();
			}

			com.AL.xml.cm.v4.ObjectFactory oFactory = new com.AL.xml.cm.v4.ObjectFactory();
			CustomerType customer = oFactory.createCustomerType();
			customer.setExternalId(customerID);	
			Map<String, Map<String, String>> contextMap = (Map<String, Map<String, String>>)request.getSession().getAttribute("dynamicFlowContextMap");
			Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");
			//	order.customerInformation.customer

			if(order !=null)
			{
				customer.setEMailOptIn(order.getCustomerInformation().getCustomer().isEMailOptIn());		
				customer.setBestPhoneContact(order.getCustomerInformation().getCustomer().getBestPhoneContact());
				customer.setMarketingOptIn(order.getCustomerInformation().getCustomer().isMarketingOptIn());

				order.getCustomerInformation().getCustomer().isMarketingOptIn();
				boolean isEmailAddressChanged = false;
				if((!Utils.isBlank(emailFlag)) && (emailFlag.equalsIgnoreCase("Yes")))
				{
					if(!Utils.isBlank(emailValue)){
						customer.setBestEmailContact(emailValue);
						String dtCreated = salesCenterVo.getValueByName("dtCreated");
						if( !Utils.isBlank( dtCreated ) )
						{
							isEmailAddressChanged = WarmTransferFactory.INSTANCE.isEmailAddressUpdated(emailValue, salesCenterVo);
						}
					}
					customer.setNonBuyerWebOptIn(true);
				}
				else if((!Utils.isBlank(emailFlag)) && (emailFlag.equalsIgnoreCase("No")))
				{
					customer.setDirectMailOptIn(true);
				}
				if((!Utils.isBlank(csatVal)) && (csatVal.equalsIgnoreCase("Yes"))){
					CsatSurveyType csatSurveyType = oFactory.createCsatSurveyType();
					csatSurveyType.setId(1);
					CsatSurveysType csatSurveysType = oFactory.createCsatSurveysType();
					csatSurveysType.getCsatSurvey().add(csatSurveyType);
					customer.setCsatSurveys(csatSurveysType);
				}
				String GUID = (String)request.getSession().getAttribute("GUID");
				if(GUID == null)
				{
					GUID = UUID.randomUUID().toString();
				}
				if(request.getSession().getAttribute("primaryLanguage")!=null){
					customer.setPrimaryLanguage(Integer.valueOf(request.getSession().getAttribute("primaryLanguage").toString()));
				}

				Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
				Map<String, String> sourceEntity = new HashMap<String, String>();
				sourceEntity.put("source", "salescenter");
				sourceEntity.put("GUID", GUID);
				data.put("source", sourceEntity);
				CustomerContextType customerContext = CustomerFactory.INSTANCE.buildCustomerContext(data);
				
				CustomerAttributeType customerAttributeType = new com.AL.xml.cm.v4.ObjectFactory().createCustomerAttributeType();
				Attributes attributes = customer.getAttributes();
				if(attributes == null)
				{
					attributes = new com.AL.xml.cm.v4.ObjectFactory().createAttributes();
				}
				List<CustomerAttributeEntityType> customerAttributeEntityTypes = customerAttributeType.getEntity();
				
				//Add VVD as cm_cust_attribute 
				String typeOfService = (String)request.getSession().getAttribute("typeOfService");
				logger.info("typeOfService="+typeOfService);
				if( request.getSession().getAttribute("isMoveInDelta")!= null && !Utils.isBlank(typeOfService) && !"moveInDeltaService".equalsIgnoreCase(typeOfService) )
				{
					customerAttributeEntityTypes.add(CartLineItemFactory.INSTANCE.setCustomerAttributeEntityType("VVD", "true", "", "VVD"));
				}
				else
				{
					customerAttributeEntityTypes.add(CartLineItemFactory.INSTANCE.setCustomerAttributeEntityType("VVD", "false", "", "VVD"));
				}
				
				attributes.getAttribute().add(customerAttributeType);
				customer.setAttributes(attributes);
				
				if(isEmailAddressChanged  && dynamicFlow.get("dynamicFlow.flowType")!= null 
						&& !dynamicFlow.get("dynamicFlow.flowType").contains("customerLookup")){
					NotificationEventType notifEventType = oFactory.createNotificationEventType();
					notifEventType.getReason().add(50);
					notifEventType.setCode(100);
					NotificationEventCollectionType notifEventColl = oFactory.createNotificationEventCollectionType();
					DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
					GregorianCalendar gc = new GregorianCalendar();
					XMLGregorianCalendar dateTime = datatypeFactory.newXMLGregorianCalendar(gc);
					notifEventType.setDateTimeStamp(dateTime);
					notifEventType.setComment("Customer Info Updated");
					notifEventColl.getEvent().add(notifEventType);
					startTimer=timer.getTime();
					customer = CustomerService.INSTANCE.submitCustomerType(agentId, String.valueOf(order.getCustomerInformation().getCustomer().getExternalId()),
							"updateCustomer", customer, notifEventColl,customerContext, errorList);
					logger.info("TimeTakenforUpdateCustomerserviceCall="+(timer.getTime()-startTimer));
				}
				else{
					startTimer=timer.getTime();
					customer = CustomerService.INSTANCE.submitCustomerType(agentId, String.valueOf(order.getCustomerInformation().getCustomer().getExternalId()),
							"updateCustomer", customer, null, customerContext, errorList);
					logger.info("TimeTakenforUpdateCustomerserviceCall="+(timer.getTime()-startTimer));
				}
				startTimer = timer.getTime();		
				order = OrderService.INSTANCE.getOrderByOrderNumber(salesCenterVo.getValueByName("order.id"), agentId, null,null,false, null);
				logger.info("TimeTakenforOrderserviceCall="+(timer.getTime()-startTimer));
				request.getSession().setAttribute("order",order);
				if(errorList != null && errorList.size() > 0){
					for(CartError cartError: errorList){
						logger.info("UpdateCustomer_Error_Code" + cartError.getCode());
						logger.info("UpdateCustomer_Error_Message" + cartError.getMessage());
						logger.info("UpdateCustomer_Error_Description" + cartError.getDescription());
					}
					throw new UnRecoverableException(errorList.get(0).getMessage());
				}				
				request.getSession().setAttribute("bestEmailContactValue", order.getCustomerInformation().getCustomer().getBestEmailContact());
			}
			
			logger.info("moveInDelta_Atuo_Dispotion="+requestContext.getFlowScope().get("moveInDeltaAtuoDisposition"));
			if(requestContext.getFlowScope().get("moveInDeltaAtuoDisposition")!= null){
				excuteAtuoDisposition(request);
        	   return new Event(this, "idlePageEvent");
           }
			
			try{
				Utils.insertAndUpdateCustomerTrackerData(order, request.getSession(), customerTrackerDao);
				}catch(Exception e){
					logger.info("error occured while inserting the data "+e.getMessage());
			}
			List<DispoCatAssoc> dispCatAssocList = new ArrayList<DispoCatAssoc>();
			dispCatAssocList = dispoCatAssocDao.getDispositionCategoryAssociations();
			List<Disposition> dispositionList = new ArrayList<Disposition>();
			dispositionList = dispositionDao.getDispositions();
			request.getSession().setAttribute("dispositionList", dispositionList);
			List<String> noOpportunityDispositionIdList = new ArrayList<String>();
			List<String> qualifiedDidNotPurchaseDispositionIdList = new ArrayList<String>();
			
			logger.info("flowType="+dynamicFlow.get("dynamicFlow.flowType"));
			if((dynamicFlow.get("dynamicFlow.flowType").contains("webReferrer"))
					||(dynamicFlow.get("dynamicFlow.flowType").contains("webMicro"))
					||(dynamicFlow.get("dynamicFlow.flowType").contains("webCCP"))
					||(dynamicFlow.get("dynamicFlow.flowType").contains("confirm"))
					||(dynamicFlow.get("dynamicFlow.flowType").contains("nonConfirm"))
					||(dynamicFlow.get("dynamicFlow.flowType").contains("referrerSpecificConfirm"))
					||(dynamicFlow.get("dynamicFlow.flowType").contains("referrerSpecificNonConfirm"))
					||(dynamicFlow.get("dynamicFlow.flowType").contains("agentTransfer"))
					||(dynamicFlow.get("dynamicFlow.flowType").contains("USAA"))
					||(dynamicFlow.get("dynamicFlow.flowType").contains("simpleChoice"))
					||(dynamicFlow.get("dynamicFlow.flowType").contains("customerLookup"))
					||(dynamicFlow.get("dynamicFlow.flowType").contains("mdu"))
					||(dynamicFlow.get("dynamicFlow.flowType").contains("consumersInteractions"))){
					
				logger.info("flowType="+dynamicFlow.get("dynamicFlow.flowType"));
				List<String> timeAndApprovalDispositionIdList = new ArrayList<String>();
				List<String> priceAndCreditDispositionIdList = new ArrayList<String>();
				List<String> productsAndServiceDispositionIdList = new ArrayList<String>();
				List<String> noSalesOpportunityDispositionIdList = new ArrayList<String>();

				for (DispoCatAssoc dispoCatAssoc : dispCatAssocList) 
				{
					if (dispoCatAssoc.getCategoryId().equals("1")){
						timeAndApprovalDispositionIdList.add(dispoCatAssoc.getDispositionId());
					} 
					else if (dispoCatAssoc.getCategoryId().equals("2")) {
						productsAndServiceDispositionIdList.add(dispoCatAssoc.getDispositionId());
					}
					else if (dispoCatAssoc.getCategoryId().equals("3")) {
						priceAndCreditDispositionIdList.add(dispoCatAssoc.getDispositionId());
					} 
					else if (dispoCatAssoc.getCategoryId().equals("4")) {
						noSalesOpportunityDispositionIdList.add(dispoCatAssoc.getDispositionId());
					} 

				}

				Map<String, String> timeAndApprovalDispositionMap = new LinkedHashMap<String, String>();
				Map<String, String> priceAndCreditDispositionIdMap = new LinkedHashMap<String, String>();
				Map<String, String> productsAndServiceDispositionIdMap = new LinkedHashMap<String, String>();
				Map<String, String> noSalesOpportunityDispositionIdMap = new LinkedHashMap<String, String>();

				for (Disposition disposition : dispositionList)
				{
					if (timeAndApprovalDispositionIdList.contains(disposition.getDispositionId())) {
						timeAndApprovalDispositionMap.put(disposition.getDispositionId(),disposition.getDescription());
					} 
					else if (priceAndCreditDispositionIdList.contains(disposition.getDispositionId())) {
						priceAndCreditDispositionIdMap.put(disposition.getDispositionId(), disposition.getDescription());
					} 
					else if (productsAndServiceDispositionIdList.contains(disposition.getDispositionId())){
						productsAndServiceDispositionIdMap.put(disposition.getDispositionId(),disposition.getDescription());
					}
					else if (noSalesOpportunityDispositionIdList.contains(disposition.getDispositionId())){
						noSalesOpportunityDispositionIdMap.put(disposition.getDispositionId(), disposition.getDescription());
					} 

				}

				requestContext.getFlowScope().put("timeAndApprovalDispositionMap", timeAndApprovalDispositionMap);
				requestContext.getFlowScope().put("priceAndCreditDispositionIdMap", priceAndCreditDispositionIdMap);
				requestContext.getFlowScope().put("productsAndServiceDispositionIdMap", productsAndServiceDispositionIdMap);
				requestContext.getFlowScope().put("noSalesOpportunityDispositionIdMap",noSalesOpportunityDispositionIdMap);

			}			
			else{
				for(DispoCatAssoc dispoCatAssoc: dispCatAssocList){
					if(dispoCatAssoc.getCategoryId().equals("9")){
						noOpportunityDispositionIdList.add(dispoCatAssoc.getDispositionId());
					}
					else if(dispoCatAssoc.getCategoryId().equals("10")){
						qualifiedDidNotPurchaseDispositionIdList.add(dispoCatAssoc.getDispositionId());
					}
				}
				Map<String,String> noOportunityDispositionMap = new HashMap<String, String>();
				Map<String,String> qualifiedDidNotPurchaseDispositionMap = new HashMap<String, String>();
				for(Disposition disposition: dispositionList){
					if(noOpportunityDispositionIdList.contains(disposition.getDispositionId())){
						noOportunityDispositionMap.put(disposition.getDispositionId(), disposition.getDescription());
					}
					else if(qualifiedDidNotPurchaseDispositionIdList.contains(disposition.getDispositionId())){
						qualifiedDidNotPurchaseDispositionMap.put(disposition.getDispositionId(), disposition.getDescription());
					}
				}
				requestContext.getFlowScope().put("noOportunityDispositionMap",noOportunityDispositionMap);
				requestContext.getFlowScope().put("qualifiedDidNotPurchaseDispositionMap",qualifiedDidNotPurchaseDispositionMap);
			}

			com.AL.xml.v4.CustAddress custAdr = SalesUtil.INSTANCE.getAddress(order.getCustomerInformation().getCustomer(),
					com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString());

			//Here we setting OfferLineItemExtID in request level for Score Capturing.
			LineItemType offerLineItem = LineItemUtil.getLineItemBasedOnProductType(order,"SaversOffer");
			logger.info("OfferLineItemExtID="+offerLineItem);
			if(offerLineItem !=  null){
				requestContext.getFlashScope().put("offerLineItemExtID", offerLineItem.getExternalId());
			}
			requestContext.getFlowScope().put("address",getAddress(custAdr));
			requestContext.getFlowScope().put("isDispositions",true);
			requestContext.getFlowScope().put("timeOut",timeOut);
			logger.info("End_of_DispositionController");
			logger.info("timeTakenForShowDisposition="+timer.getTime());
			return new Event(this, "dispositionEvent");
		}catch(Exception e){
			requestContext.getFlowScope().put("message", e.getMessage());
			requestContext.getFlowScope().put("pageTitle",request.getParameter("pageTitle")!=null?request.getParameter("pageTitle"):"");
			logger.warn("Error_in_execute_DispositionController",e);
			logger.error(e);
			throw new UnRecoverableException(e.getMessage());
		}finally{
			timer.stop();
		}
	}

	/**
	 * Decide next view after close call sale.
	 * 
	 * @param requestContext
	 * @return display event
	 * @throws Exception
	 */
	public Event decideViewAfterCloseCallSale(RequestContext requestContext) throws Exception{

		StopWatch timer= new StopWatch();
		timer.start();
		HttpServletRequest request = (HttpServletRequest)requestContext.getExternalContext().getNativeRequest();
		SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
		Long orderId = (Long) request.getSession().getAttribute("orderId");
		String agentId = salesCenterVo.getValueByName("Agent");
		long startTimer = 0;
		try{
			if( orderId!=null ){
				startTimer=timer.getTime();
				OrderType order = OrderService.INSTANCE.getOrderByOrderNumber(String.valueOf(orderId), agentId, null, null, false, null);
				logger.info("TimeTakenforOrderServicecall="+(timer.getTime()-startTimer));

				request.getSession().setAttribute(ControllerConstants.ORDER, order);
				List<LineItemType> lineItems = CartLineItemFactory.INSTANCE.getTPVLineItemsWithSortingOrder(order);
				logger.info("lineItems=" + lineItems);

				if (!lineItems.isEmpty()) 
				{
					Long addressId = (Long) request.getSession().getAttribute("addressId");
					request.getSession().setAttribute("tpvLineItems", lineItems);
					requestContext.getFlowScope().put("tpvLineItemId", lineItems.get(0).getExternalId());

					Event event = displayTPVView(requestContext);

					com.AL.xml.v4.CustAddress custAdr = SalesUtil.INSTANCE.getAddress(order.getCustomerInformation().getCustomer(),
							com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString());

					requestContext.getFlowScope().put("address",getAddress(custAdr));
					requestContext.getFlowScope().put("addressId", addressId);
					return event;
				}
				else 
				{
					String isWarmTransferEnabled = request.getParameter("isWarmTransferEnabled");

					if (isWarmTransferEnabled != null && isWarmTransferEnabled.equalsIgnoreCase("true")) 
					{
						Long addressId = (Long) request.getSession().getAttribute("addressId");

						Event event = warmTransfer(requestContext);

						LineItemType lastLineItem = (LineItemType)request.getSession().getAttribute("lastLineItem");
						requestContext.getFlowScope().put("warmTransferLineItemId", lastLineItem.getExternalId());

						com.AL.xml.v4.CustAddress custAdr = SalesUtil.INSTANCE.getAddress(order.getCustomerInformation().getCustomer(),
								com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString());

						requestContext.getFlowScope().put("address",getAddress(custAdr));
						requestContext.getFlowScope().put("addressId", addressId);
						return event;
					}
					logger.info("Forwarding_into_dispositions_page.");
				}
			}
		}finally{
			timer.stop();
		}
		return new Event(this,"dispositionSaleEvent");
	}


	/*
	 * 
	 * Displays TPV View.
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws RecoverableException
	 */
	@SuppressWarnings("unchecked")
	public Event displayTPVView(RequestContext requestContext)
			throws UnRecoverableException {

		logger.info("displayTPVView_begin");
		StopWatch timer = new StopWatch();
		timer.start();
		long startTimer = 0;
		try {
			HttpServletRequest request = (HttpServletRequest)requestContext.getExternalContext().getNativeRequest();
			SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession()
					.getAttribute("salescontext");

			List<LineItemType> lineItems = (List<LineItemType>) request
					.getSession().getAttribute("tpvLineItems");
			String providerExternalId = null;
			String parentExternalId = null;
			String tpvScript = null;
			LineItemType lastItemType = lineItems.get(0);

			OrderLineItemDetailTypeType detailType = lastItemType.getLineItemDetail().getDetail();
			if (detailType != null) 
			{
				if (detailType.getProductLineItem() != null
						&& detailType.getProductLineItem().getProvider() != null
						&& !Utils.isBlank(detailType.getProductLineItem().getProvider().getExternalId()) ) 
				{
					providerExternalId = detailType.getProductLineItem()
							.getProvider().getExternalId();
				}
			}

			if (lastItemType.getLineItemAttributes() != null
					&& lastItemType.getLineItemAttributes().getEntity() != null
					&& !lastItemType.getLineItemAttributes().getEntity().isEmpty()) 
			{
				for (AttributeEntityType entityType : lastItemType.getLineItemAttributes().getEntity() ) 
				{
					if (!Utils.isBlank(entityType.getSource())
							&& entityType.getSource().equals(Constants.IMAGE_ID)) 
					{
						if (entityType.getAttribute() != null
								&& !entityType.getAttribute().isEmpty()) 
						{
							for (AttributeDetailType attributeDetailType : entityType.getAttribute()) {
								if (!Utils.isBlank(attributeDetailType.getName())
										&& attributeDetailType.getName().equalsIgnoreCase("id")
										&& !Utils.isBlank(attributeDetailType.getValue())) 
								{
									parentExternalId = attributeDetailType.getValue();
									break;
								}
							}
						}
						//break;
					}else if (!Utils.isBlank(entityType.getSource())
							&& entityType.getSource().equalsIgnoreCase(Constants.CKO)) 
					{
						if (entityType.getAttribute() != null
								&& !entityType.getAttribute().isEmpty()) 
						{
							for (AttributeDetailType attributeDetailType : entityType.getAttribute()) {
								if (!Utils.isBlank(attributeDetailType.getName())
										&& attributeDetailType.getName().equalsIgnoreCase(Constants.TPV_SCRIPT)
										&& !Utils.isBlank(attributeDetailType.getDescription())) 
								{
									tpvScript = attributeDetailType.getDescription();
									break;
									
								}
							}
						}
						//break;
					}
				}
			}
			Map<String, String> salesFlow = new HashMap<String, String>();
			salesFlow.put("salesFlow.dialogueType","TPV");
			salesFlow.put("GUID", salesCenterVo.getValueByName("GUID"));
			Map<String, Map<String, String>> context = new HashMap<String, Map<String,String>>();
			context.put("salesFlow",salesFlow);

			List<DialogueType> dialogueListType = SalesDialogueBuilder.INSTANCE.getTPVDialogue(providerExternalId, 
					parentExternalId, salesCenterVo.getValueByName("GUID"),context);

			logger.info("Getting_TPV_Dialogie_List" + dialogueListType);

			List<DataFieldType> dataFieldList = new ArrayList<DataFieldType>();
			List<DataFieldMatrixType> dataFieldEnableList = new ArrayList<DataFieldMatrixType>();

			for (DialogueType dialoge : dialogueListType)
			{
				for (DataGroupType dataGroup : dialoge.getDataGroupList().getDataGroup()) 
				{
					dataFieldList.addAll(dataGroup.getDataFieldList().getDataField());
					dataFieldEnableList.add(dataGroup.getDataFieldMatrix());
				}
			}

			request.getSession().setAttribute("dataFieldList", dataFieldList);
			List<String> allDataFieldList = new ArrayList<String>();
			Map<String, Map<String, List<String>>> enableDependencyMap = DialogueUtil.INSTANCE.getEnableDependencies(dataFieldEnableList, request);
			Map<String, Map<String, List<String>>> disableDependencyMap = DialogueUtil.INSTANCE.getDisableDependencies(dataFieldList, enableDependencyMap, request);

			StringBuilder events = new StringBuilder();
			Map<String, String> dataFieldMap = new HashMap<String, String>();

			List<Fieldset> fieldsetList = WarmTransferHtmlFactory.INSTANCE.dialogueToFieldSet(events, dialogueListType,
					enableDependencyMap, allDataFieldList, dataFieldMap, request);
			 if(!Utils.isBlank(tpvScript) 
						&& fieldsetList != null
						&& !fieldsetList.isEmpty()
						&& fieldsetList.get(0).getContent() != null
						&& fieldsetList.get(0).getContent().size() > 1  
						){
					Div div = (Div)fieldsetList.get(0).getContent().get(1);
					List<Object>  list = new ArrayList<Object>(div.getContent());
					div.getContent().clear();
					div.getContent().add(WarmTransferHtmlFactory.INSTANCE.buildCustomDataFieldData(salesCenterVo.replaceNamesWithValues(tpvScript)));
					div.getContent().addAll(list);
				}
				
			for (Entry<String, Map<String, List<String>>> enableDependenciesEntry : enableDependencyMap.entrySet())
			{
				for (Entry<String, List<String>> enableDependenciesList : enableDependenciesEntry.getValue().entrySet()) 
				{
					for (String dependedEle : enableDependenciesList.getValue()) 
					{
						allDataFieldList.add(dependedEle);
					}
				}
			}

			for (Fieldset fieldset : fieldsetList) 
			{
				String element = HtmlBuilder.INSTANCE.toString(fieldset);
				element = salesCenterVo.replaceNamesWithValues(element);
				element = parseHtmlTags(element);

				events.append(element);
			}
			logger.debug("TPV_Dialogus=" + events.toString());
			logger.debug("TPV_allDataFiedList=" + allDataFieldList);

			requestContext.getFlowScope().put("dataField", events.toString());
			requestContext.getFlowScope().put("enableDialogueMap", enableDependencyMap);
			requestContext.getFlowScope().put("disableDialogueMap", disableDependencyMap);
			requestContext.getFlowScope().put("allDataFieldList", allDataFieldList);
			request.getSession().setAttribute("dataFieldMap", dataFieldMap);
			
			requestContext.getFlowScope().put("isClosingCall", true);

			startTimer=timer.getTime();
			logger.info("TimeTakenforToShowTPV="+(timer.getTime()-startTimer));
			logger.info("displayTPVView_End");
			return new Event(this, "tpvEvent");

		} catch (Exception e) {
			logger.error("Error_preparing_TPV_page",e);
			throw new UnRecoverableException(e.getMessage());
		}finally{
			timer.stop();
		}
	}



	/**
	 * @param request
	 * @return
	 * @throws RecoverableException
	 */
	public  Event warmTransfer(RequestContext requestContext) throws RecoverableException {
		StopWatch timer = new StopWatch();
		try{
			timer.start();
			long startTimer = 0;
			HttpServletRequest request = (HttpServletRequest)requestContext.getExternalContext().getNativeRequest();

			SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");

			String orderId = (String) request.getParameter("orderId");
			String agentId = salesCenterVo.getValueByName("agent.id");
			startTimer = timer.getTime();
			OrderType order = OrderService.INSTANCE.getOrderByOrderNumber(orderId,agentId,new HashMap<String, Object>(),"*", Boolean.FALSE, null);
			logger.info("TimeTakenforgetOrderByOrderNumber=" +(timer.getTime() - startTimer));
			List<LineItemType> lineItems = CartLineItemFactory.INSTANCE.sortLineItemProducts(order, request);

			String providerId = WarmTransferFactory.INSTANCE.getLastLineItemProvider(lineItems, request);

			Map<String, String> salesFlow = new HashMap<String, String>();
			salesFlow.put("salesFlow.dialogueType","WarmTransfer");
			salesFlow.put("GUID", salesCenterVo.getValueByName("GUID"));

			Map<String, Map<String, String>> context = new HashMap<String, Map<String,String>>();
			context.put("salesFlow",salesFlow);

			List<DialogueType> dialogueListType = SalesDialogueBuilder.INSTANCE.getWarmTransferDialogue(providerId,context);

			logger.info("Getting_WarmTransfer_Dialogie_List=" + dialogueListType);

			List<DataFieldType> dataFieldList = new ArrayList<DataFieldType>();

			List<DataFieldMatrixType> dataFieldEnableList = new ArrayList<DataFieldMatrixType>();

			for (DialogueType dialoge : dialogueListType){
				for (DataGroupType dataGroup : dialoge.getDataGroupList().getDataGroup()){
					dataFieldList.addAll(dataGroup.getDataFieldList().getDataField());
					dataFieldEnableList.add(dataGroup.getDataFieldMatrix());
				}
			}

			List<String> allDataFieldList = new ArrayList<String>();
			Map<String, Map<String, List<String>>> enableDependencyMap = DialogueUtil.INSTANCE.getEnableDependencies(dataFieldEnableList, request);
			Map<String, Map<String, List<String>>> disableDependencyMap = DialogueUtil.INSTANCE.getDisableDependencies(dataFieldList, enableDependencyMap, request);

			StringBuilder events = new StringBuilder();
			Map<String, String> dataFieldMap = new HashMap<String, String>();
			List<Fieldset> fieldsetList = WarmTransferHtmlFactory.INSTANCE.dialogueToFieldSet(events, dialogueListType, enableDependencyMap, allDataFieldList, dataFieldMap, request);

			for(Entry<String, Map<String, List<String>>> enableDependenciesEntry : enableDependencyMap.entrySet()){
				for(Entry<String, List<String>> enableDependenciesList : enableDependenciesEntry.getValue().entrySet()){
					for(String dependedEle : enableDependenciesList.getValue()){
						allDataFieldList.add(dependedEle);
					}
				}
			}

			for (Fieldset fieldset : fieldsetList)
			{
				String element = HtmlBuilder.INSTANCE.toString(fieldset);
				element = salesCenterVo.replaceNamesWithValues(element);
				element = parseHtmlTags(element);

				events.append(element);
				logger.debug("warmTransfer_dialogs=" + events.toString());
			}

			logger.debug("warmTransfer_allDataFiedList=" + allDataFieldList);

			requestContext.getFlowScope().put("dataField", events.toString());
			requestContext.getFlowScope().put("enableDialogueMap", enableDependencyMap);
			requestContext.getFlowScope().put("disableDialogueMap", disableDependencyMap);
			requestContext.getFlowScope().put("allDataFieldList", allDataFieldList);
			requestContext.getFlowScope().put("isClosingCall", true);
			request.getSession().setAttribute("dataFieldMap", dataFieldMap);
			return new Event(this,"warmTransferEvent");

		}
		catch(Exception e)
		{
			logger.error("Error_preparing_warmTransfer_page",e);
			throw new RecoverableException(e.getMessage());
		}finally{
			timer.stop();
		}
	}

	private String parseHtmlTags(String dataFieldText) {
		dataFieldText = dataFieldText.replaceAll("&amp;", "&");
		dataFieldText = dataFieldText.replaceAll("&lt;", "<");
		dataFieldText = dataFieldText.replaceAll("&gt;", ">");
		return dataFieldText;
	}


	/**
	 * Display Disposition view.
	 * 
	 * @param requestContext
	 * @return display event
	 * @throws Exception
	 */
	public Event showDispositionViewForSale(RequestContext requestContext) throws Exception {
		try{
			logger.info("begin_of_DispositionController");
			HttpServletRequest request = (HttpServletRequest)requestContext.getExternalContext().getNativeRequest();
			List<SystemConfig> systemConfigList = configDao.findByContext(context);
			SystemConfig systemConfig = new SystemConfig();

			if(systemConfigList != null){
				systemConfig = systemConfigList.get(0);
			}
			String timeOut = "0";
			if(!Utils.isBlank(systemConfig.getValue())){
				timeOut = systemConfig.getValue();
			}
			else{
				timeOut = "5";
			}

			List<DispoCatAssoc> dispCatAssocList = new ArrayList<DispoCatAssoc>();
			dispCatAssocList = dispoCatAssocDao.getDispositionCategoryAssociations();
			List<Disposition> dispositionList = new ArrayList<Disposition>();
			dispositionList = dispositionDao.getDispositions();
			request.getSession().setAttribute("dispositionList", dispositionList);
			List<String> noOpportunityDispositionIdList = new ArrayList<String>();
			List<String> qualifiedDidNotPurchaseDispositionIdList = new ArrayList<String>();
			Map<String, Map<String, String>> contextMap = (Map<String, Map<String, String>>)request.getSession().getAttribute("dynamicFlowContextMap");
			Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");
			if((dynamicFlow.get("dynamicFlow.flowType").contains("peco"))&&(!dynamicFlow.get("dynamicFlow.flowType").contains("nonMovers"))){
				for(DispoCatAssoc dispoCatAssoc: dispCatAssocList){
					if(dispoCatAssoc.getCategoryId().equals("11")){
						noOpportunityDispositionIdList.add(dispoCatAssoc.getDispositionId());
					}
					else if(dispoCatAssoc.getCategoryId().equals("12")){
						qualifiedDidNotPurchaseDispositionIdList.add(dispoCatAssoc.getDispositionId());
					}
				}
			}
			else{
				for(DispoCatAssoc dispoCatAssoc: dispCatAssocList){
					if(dispoCatAssoc.getCategoryId().equals("9")){
						noOpportunityDispositionIdList.add(dispoCatAssoc.getDispositionId());
					}
					else if(dispoCatAssoc.getCategoryId().equals("10")){
						qualifiedDidNotPurchaseDispositionIdList.add(dispoCatAssoc.getDispositionId());
					}
				}
			}
			List<String> noOportunityDispositionList = new ArrayList<String>();
			List<String> qualifiedDidNotPurchaseDispositionList = new ArrayList<String>();
			for(Disposition disposition: dispositionList){
				if(noOpportunityDispositionIdList.contains(disposition.getDispositionId())){
					noOportunityDispositionList.add(disposition.getDescription());
				}
				else if(qualifiedDidNotPurchaseDispositionIdList.contains(disposition.getDispositionId())){
					qualifiedDidNotPurchaseDispositionList.add(disposition.getDescription());
				}
			}

			requestContext.getFlowScope().put("noOportunityDispositionList",noOportunityDispositionList);
			requestContext.getFlowScope().put("qualifiedDidNotPurchaseDispositionList",qualifiedDidNotPurchaseDispositionList);
			requestContext.getFlowScope().put("isDispositions",true);
			requestContext.getFlowScope().put("timeOut",timeOut);
			logger.info("End_of_DispositionController");
			request.getSession().setAttribute("isProductSaleInFlow",Boolean.TRUE);
			return new Event(this, "dispositionEvent");
		}catch(Exception e){
			logger.warn("Error_in_showDispositionViewForSale_in_DispositionController",e);
			logger.error(e);
			throw new UnRecoverableException(e.getMessage());
		}
	}


	public Address getAddress(com.AL.xml.v4.CustAddress custAdr){
		Address add = new Address();

		add.setPostfixDirectional(custAdr.getAddress().getPostfixDirectional());
		add.setPrefixDirectional(custAdr.getAddress().getPrefixDirectional());
		add.setStreetName(custAdr.getAddress().getStreetName());
		add.setStreetNumber(custAdr.getAddress().getStreetNumber());
		add.setStreetType(custAdr.getAddress().getStreetType());
		add.setLine2(custAdr.getAddress().getLine2());
		add.setCity(custAdr.getAddress().getCity());
		add.setStateOrProvince(custAdr.getAddress().getStateOrProvince());
		add.setPostalCode(custAdr.getAddress().getPostalCode());

		return add;

	}
	
	
	private void excuteAtuoDisposition(HttpServletRequest request) {
		SalesSession salesSessionMDU = null;
		logger.info("atuo_Disposition_begin");
		if (request.getSession().getAttribute("orderId") != null && request.getSession().getAttribute("customerID") != null) 
		{
			salesSessionMDU = salesSessionDao.getSalesSession((Long) request.getSession().getAttribute("orderId"),(Long) request.getSession().getAttribute("customerID"));
			if (salesSessionMDU != null) {
			salesSessionMDU.setDispositionId(11364L);
			salesSessionMDU.setEndTime(Calendar.getInstance());
			salesSessionMDU.setNormalClose("true");
			salesSessionDao.updateSalesSession(salesSessionMDU);
			} else {
				salesSessionMDU = new SalesSession();
				salesSessionMDU.setOrderId((Long) request.getSession().getAttribute("orderId"));
				salesSessionMDU.setCustomerId((Long) request.getSession().getAttribute("customerID"));
				salesSessionMDU.setStartTime(Calendar.getInstance());
				salesSessionMDU.setDispositionId(11381L);
				salesSessionDao.insertSalesSession(salesSessionMDU);
			}
		}				
		logger.info("atuo_Disposition_end");
	}
}
