package com.AL.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.AL.html.Div;
import com.AL.html.Fieldset;
import com.AL.ui.builder.HtmlBuilder;
import com.AL.ui.builder.SalesDialogueBuilder;
import com.AL.ui.constants.Constants;
import com.AL.ui.constants.ControllerConstants;
import com.AL.ui.dao.ConfigDao;
import com.AL.ui.dao.CustomerDiscoveryDao;
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
import com.AL.ui.factory.SalesDialogueFactory;
import com.AL.ui.factory.WarmTransferFactory;
import com.AL.ui.service.V.CustomerService;
import com.AL.ui.service.V.LineItemService;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.util.DialogueUtil;
import com.AL.ui.util.LineItemUtil;
import com.AL.ui.util.SalesUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.util.WarmTransferHtmlFactory;
import com.AL.ui.vo.CartError;
import com.AL.ui.vo.ErrorList;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.V.exception.RecoverableException;
import com.AL.V.exception.UnRecoverableException;
import com.AL.V.factory.CustomerFactory;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.cm.v4.CsatSurveyType;
import com.AL.xml.cm.v4.CsatSurveysType;
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

@Controller
public class SalesDispositionDetailsController extends BaseController {

	private static final Logger logger = Logger
			.getLogger(SalesDispositionDetailsController.class);
	private final String context = "disposition";
	@Autowired
	private DispositionDao dispositionDao;

	@Autowired
	private DispoCatAssocDao dispoCatAssocDao;

	@Autowired
	private SalesSessionDao salesSessionDao;

	@Autowired
	private SalesCallDao salesCallDao;

	@Autowired
	private ConfigDao configDao;

	@Autowired
	private CustomerDiscoveryDao customerDiscoveryDao;

	@Autowired
	private WarmTransferController warmTransferController;
	
	@Autowired
	private CustomerTrackerDao customerTrackerDao;

	@RequestMapping(value = "/dispositions")
	protected ModelAndView showDispositionDetails(HttpServletRequest request,
			HttpServletResponse response) throws UnRecoverableException {
		StopWatch timer = new StopWatch();
		timer.start();
		long startTimer=0;
		logger.info("showDispositionDetails_begin");
		try {
			String emailFlag = request.getParameter("emailFlag");
			Long customerID = (Long) request.getSession().getAttribute("customerID");
			List<SystemConfig> systemConfigList = configDao.findByContext(context);
			ErrorList errorList = new ErrorList();
			SystemConfig systemConfig = new SystemConfig();

			if (systemConfigList != null) 
			{
				systemConfig = systemConfigList.get(0);
			}

			String timeOut = "0";
			if (!Utils.isBlank(systemConfig.getValue()))
			{
				timeOut = systemConfig.getValue();
			}
			else 
			{
				timeOut = "5";
			}

			SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
			String agentId = salesCenterVo.getValueByName("agent.id");

			OrderType order = null;
			Long orderId = (Long)request.getSession().getAttribute("orderId");
			if( orderId!=null ){
				startTimer = timer.getTime();
				order = OrderService.INSTANCE.getOrderByOrderNumber(String.valueOf(orderId), agentId, null, null, false, null);
				logger.info("TimeTakenforgetOrderByOrderNumber=" +(timer.getTime() - startTimer));
			}
			logger.info("OrderId_in_showDispositionDetails"+orderId);
			if (order != null && !Utils.isBlank(order.getAgentId())) 
			{
				agentId = order.getAgentId();
			}

			com.AL.xml.cm.v4.ObjectFactory oFactory = new com.AL.xml.cm.v4.ObjectFactory();
			CustomerType customer = oFactory.createCustomerType();
			customer.setExternalId(customerID);

			if (order != null) 
			{
				customer.setEMailOptIn(order.getCustomerInformation()		.getCustomer().isEMailOptIn());
				customer.setBestPhoneContact(order.getCustomerInformation()
						.getCustomer().getBestPhoneContact());
				customer.setMarketingOptIn(order.getCustomerInformation()
						.getCustomer().isMarketingOptIn());

				order.getCustomerInformation().getCustomer().isMarketingOptIn();
				boolean isEmailAddressChanged = false;
				String emailId = "";
				if ((!Utils.isBlank(emailFlag))
						&& (emailFlag.equalsIgnoreCase("emailYes")))
				{
					customer.setNonBuyerWebOptIn(true);
					emailId = request.getParameter("bestEmail");

					if(!Utils.isBlank(emailId)){
						customer.setBestEmailContact(emailId);
						String dtCreated = salesCenterVo.getValueByName("dtCreated");
						if( !Utils.isBlank( dtCreated ) )
						{
							isEmailAddressChanged = WarmTransferFactory.INSTANCE.isEmailAddressUpdated(emailId, salesCenterVo);
						}
					}
				} 
				else if ((!Utils.isBlank(emailFlag))
						&& (emailFlag.equalsIgnoreCase("emailNo")))
				{
					customer.setDirectMailOptIn(true);
				}

				String GUID = (String) request.getSession().getAttribute("GUID");

				if (GUID == null)
				{
					GUID = UUID.randomUUID().toString();
				}

				if (request.getSession().getAttribute("primaryLanguage") != null) 
				{
					customer.setPrimaryLanguage(Integer.valueOf(request	.getSession().getAttribute("primaryLanguage").toString()));
				}

				boolean isCSATRequires = false;
				if (request.getSession().getAttribute("requiresCSAT") != null) 
				{
					isCSATRequires = (Boolean) request.getSession().getAttribute("requiresCSAT");
				}

				if (isCSATRequires) 
				{
					CsatSurveyType csatSurveyType = oFactory.createCsatSurveyType();
					csatSurveyType.setId(1);
					CsatSurveysType csatSurveysType = oFactory.createCsatSurveysType();
					csatSurveysType.getCsatSurvey().add(csatSurveyType);
					customer.setCsatSurveys(csatSurveysType);
					request.getSession().setAttribute("requiresCSAT", false);
				}
				logger.info("IsCSATRequires_value_in_showDispositionDetails"+isCSATRequires);
				Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
				Map<String, String> sourceEntity = new HashMap<String, String>();
				sourceEntity.put("source", "salescenter");
				sourceEntity.put("GUID", GUID);
				data.put("source", sourceEntity);
				CustomerContextType customerContext = CustomerFactory.INSTANCE.buildCustomerContext(data);
				Map<String, Map<String, String>> contextMap = (Map<String, Map<String, String>>)request.getSession().getAttribute("dynamicFlowContextMap");
				Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");
				if(isEmailAddressChanged && dynamicFlow.get("dynamicFlow.flowType")!= null 
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
					logger.info("NotificationEventType.getCode()="+notifEventType.getCode());
					startTimer = timer.getTime();
					customer = CustomerService.INSTANCE.submitCustomerType(agentId, String.valueOf(order.getCustomerInformation().getCustomer().getExternalId()),
							"updateCustomer", customer, notifEventColl,customerContext, errorList);
					logger.info("TimeTakenforsubmitCustomerType=" +(timer.getTime() - startTimer));

					logger.info("customer"+customer);
				}
				else{
					startTimer = timer.getTime();
					customer = CustomerService.INSTANCE.submitCustomerType(agentId,	String.valueOf(order.getCustomerInformation()
							.getCustomer().getExternalId()), "updateCustomer", customer, null, customerContext,	errorList);
					logger.info("TimeTakenforsubmitCustomerType=" +(timer.getTime() - startTimer));

				}

				logger.info("UpdateCustomer_completed");
				if (errorList != null && errorList.size() > 0)
				{
					for (CartError cartError : errorList) 
					{
						logger.info("UpdateCustomerErrorCode="+cartError.getCode()+" ErrorMessage="+cartError.getMessage()
								+" ErrorDescription="+cartError.getDescription());
					}

					throw new UnRecoverableException(errorList.get(0).getMessage());
				}

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
			request.getSession().setAttribute("dispositionList",dispositionList);

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

			logger.info("disposition_category_association_list="+dispCatAssocList);
			logger.info("disposition_list="+dispositionList);
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

			ModelAndView mav = new ModelAndView();

			//Here we setting OfferLineItemExtID in request level for Score Capturing.
			LineItemType offerLineItem = LineItemUtil.getLineItemBasedOnProductType(order,"SaversOffer");
			logger.info("OfferLineItemExtID="+offerLineItem);
			if(offerLineItem !=  null){
				mav.addObject("offerLineItemExtID", offerLineItem.getExternalId());
			}

			mav.addObject("timeAndApprovalDispositionMap", timeAndApprovalDispositionMap);
			mav.addObject("priceAndCreditDispositionIdMap", priceAndCreditDispositionIdMap);
			mav.addObject("productsAndServiceDispositionIdMap", productsAndServiceDispositionIdMap);
			mav.addObject("noSalesOpportunityDispositionIdMap",noSalesOpportunityDispositionIdMap);

			mav.addObject("isDispositions", true);
			mav.addObject("timeOut", timeOut);
			mav.setViewName("sales.dispositions");
			return mav;
		} catch (Exception e) {
			logger.warn("Error_in_showDispositionDetails",e);
			logger.error(e);
			throw new UnRecoverableException(e.getMessage());
		}finally{
			timer.stop();
		}
	}

	@RequestMapping(value = "/home")
	protected ModelAndView saveDispositionDetails(HttpServletRequest request,
			HttpServletResponse response) throws UnRecoverableException {
		StopWatch timer = new StopWatch();
		long startTimer=0;
		timer.start();
		try {

			if(!Utils.isBlank(request.getParameter("autoDispo"))) {
				SalesSession salesSessionMDU = new SalesSession();
				if (request.getSession().getAttribute("orderId") != null && request.getSession().getAttribute("customerID") != null) 
				{
					salesSessionMDU = salesSessionDao.getSalesSession((Long) request.getSession().getAttribute("orderId"),(Long) request.getSession().getAttribute("customerID"));
					if (salesSessionMDU != null) {
						salesSessionMDU.setDispositionId(11381L);
						salesSessionMDU.setEndTime(Calendar.getInstance());
						salesSessionMDU.setNormalClose("true");
						salesSessionDao.updateSalesSession(salesSessionMDU);
					} else {
						salesSessionMDU.setStartTime(Calendar.getInstance());
						salesSessionMDU.setDispositionId(11381L);
						salesSessionDao.insertSalesSession(salesSessionMDU);
					}
				}				
				String absoluteURL = (String) request.getSession().getAttribute("urlPath");
				logger.info("AutoDispo for MDU call");
				if( !Utils.isBlank(absoluteURL) )
				{
					return new ModelAndView("redirect:" + absoluteURL + "/salescenter/login_process");
				}
				return new ModelAndView("redirect:/salescenter/login_process");
			}

			logger.info("Save_DispositionDetails_begin");

			String selectedDisposition = request.getParameter("selectedDisp");

			boolean isProductSaleInFlow = Boolean.FALSE;
			if( !Utils.isBlank(request.getParameter("isProductSaleInFlow")) )
			{
				isProductSaleInFlow = Boolean.TRUE;
			}

			SalesSession salesSession = new SalesSession();
			if(!Utils.isBlank(request.getParameter("orderId")))
			{
				Long orderId = Long.valueOf(request.getParameter("orderId"));
				Long customerId = Long.valueOf(request.getParameter("customerId"));
				if (customerId != null)
				{
					if (request.getSession().getAttribute("customerDiscoveryMap") != null) {
						Map<String, String> customerDiscoveryInsertMap = (Map<String, String>) request.getSession().getAttribute("customerDiscoveryMap");	
						boolean notepadRecoSave = request.getSession().getAttribute("notepadRecoSave") != null ? (Boolean) request.getSession().getAttribute("notepadRecoSave") : false;
						if (!customerDiscoveryInsertMap.isEmpty()) {
							customerDiscoveryDao.insert(orderId, customerId, customerDiscoveryInsertMap);
						}
					} else {
						if(request.getSession().getAttribute("landlord") != null){
							Map<String, String> customerDiscoveryInsertLandlordMap = new LinkedHashMap<String, String>();
							customerDiscoveryInsertLandlordMap.put("Landlord","true");
							customerDiscoveryDao.insert(orderId, customerId, customerDiscoveryInsertLandlordMap);
						}
					}
				}
				if ( isProductSaleInFlow ) 
				{
					SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
					String agentId = salesCenterVo.getValueByName("agent.id");
					boolean isSalesSessionPopulated = true;
					if (customerId != null && orderId != null) 
					{
						salesSession = salesSessionDao.getSalesSession(orderId,	customerId);
					}

					Long dispositionId = 0L;
					if ( !Utils.isBlank(selectedDisposition) )
					{
						//DispositionId selected in the Disposition page
						dispositionId = Long.valueOf(selectedDisposition);
					}
					else
					{
						//Disposition not applied
						dispositionId = 11295L;

					} 
					if(salesSession == null){
						isSalesSessionPopulated = false;
						salesSession = new SalesSession();
					}
					salesSession.setDispositionId(dispositionId);
					logger.info("DispositionId="+dispositionId);
					salesSession.setEndTime(Calendar.getInstance());
					salesSession.setNormalClose("true");
					salesSession.setOrderId(orderId);
					salesSession.setCustomerId(customerId);
					salesSession.setAgent(agentId);
					if (customerId != null && isSalesSessionPopulated)
					{
						salesSessionDao.updateSalesSession(salesSession);
					}
					else 
					{
						salesSession.setStartTime(Calendar.getInstance());
						salesSessionDao.insertSalesSession(salesSession);
					}

					String ambDisconnectDateTime = request.getParameter("ambDisconnectdatetime");
					logger.info("ambDisconnectDateTime="+ambDisconnectDateTime);

					SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss z (Z)");
					try {

						if (!Utils.isBlank(ambDisconnectDateTime) && isSalesSessionPopulated) 
						{
							long salesCallId = Long.parseLong(request.getParameter("salesCallId"));
							Calendar disconnectCal = Calendar.getInstance();
							String ambDisconnectDateTime2 = ambDisconnectDateTime.substring(0, 31);
							String ambDisconnectDateTime3 = ambDisconnectDateTime.substring(31, ambDisconnectDateTime.length());
							ambDisconnectDateTime = ambDisconnectDateTime2 + ":"+ ambDisconnectDateTime3;
							disconnectCal.setTime(sdf.parse(ambDisconnectDateTime));
							salesCallDao.updateSalesCallWithAmbDisconnectDateTime(salesCallId, disconnectCal);
						}
					} catch (Exception e) {
						logger.warn("Error_in_SaveDispositionDetails",e);
					}

					logger.info("Saved_DispositionDetails");

					//
					/*
					 * Getting SalesContext from the session
					 */
					
					startTimer = timer.getTime();
					OrderType order = OrderService.INSTANCE.getOrderByOrderNumber(String.valueOf(orderId), agentId, null, null, false, null);
					logger.info("TimeTakenforgetOrderByOrderNumber=" +(timer.getTime() - startTimer));

					if (order != null && !Utils.isBlank(order.getAgentId())) 
					{
						agentId = order.getAgentId();
					}

					SalesContextType salesContext = SalesContextFactory.INSTANCE.getContextFromSession(request.getSession());

					if(salesContext == null)
					{
						startTimer=timer.getTime();	
						salesContext = OrderService.INSTANCE.getSalesContext(String.valueOf(order.getExternalId()), agentId);	
						logger.info("TimeTakenforOrderserviceCall="+(timer.getTime()-startTimer));
					}
					int zipOnlySearch = 0;
					if(salesCenterVo!= null && salesCenterVo.getValueByName("isZipOnlySearch") != null && salesCenterVo.getValueByName("isZipOnlySearch").equalsIgnoreCase("YES")){
						zipOnlySearch = 1;
					}
					if( order.getLineItems().getLineItem().size() == 0)
					{
						String flow = (String) request.getSession().getAttribute("referrerFlowAgentGroup");
						if (!Utils.isBlank(flow) && flow.toLowerCase().contains("mdu") && salesCenterVo!= null && salesCenterVo.getValueByName("orderSourceExternalId") != null) {
							startTimer=timer.getTime();	
							OrderService.INSTANCE.updateSalesContext(agentId, String.valueOf(order.getExternalId()), CartSalesContextFactory.INSTANCE.getStatusCompleteSalesContext(salesContext), 
									SalesContextFactory.INSTANCE.getAttribute(salesContext, "GUID"), zipOnlySearch, salesCenterVo.getValueByName("orderSourceExternalId"));
							logger.info("TimeTakenforOrderserviceCall="+(timer.getTime()-startTimer));
						}
						else{
							startTimer=timer.getTime();	
							OrderService.INSTANCE.updateSalesContext(agentId, String.valueOf(order.getExternalId()), CartSalesContextFactory.INSTANCE.getStatusCompleteSalesContext(salesContext), SalesContextFactory.INSTANCE.getAttribute(salesContext, "GUID"), zipOnlySearch);
							logger.info("TimeTakenforOrderserviceCall="+(timer.getTime()-startTimer));
						}
					}
					else
					{
						List<String> listOflineItemIds = LineItemUtil.containsUtilityOffers(order);
						logger.info("listOflineItemIds=" + listOflineItemIds);
						if (!listOflineItemIds.isEmpty())
						{
							ErrorList errorList = new ErrorList();
							List<Integer> reasons = new ArrayList<Integer>();
							reasons.add(ControllerConstants.REASON_CODE);
							startTimer=timer.getTime();	
							LineItemService.INSTANCE.updateLineItemStatus(agentId, String.valueOf(order.getExternalId()),
									listOflineItemIds, LineItemStatusCodesType.CANCELLED_REMOVED.value(),
									LineItemStatusCodesType.CANCELLED_REMOVED.value(), reasons, salesContext,errorList);
							logger.info("TimeTakenforUpdateLineItemStatus="+(timer.getTime()-startTimer));
							logger.info("UpdateLineItemStatus_completed");
							if (errorList != null && errorList.size() > 0) 
							{
								for (CartError cartError : errorList) 
								{
									logger.info("updateLineItemStatus_Error_Code=" + cartError.getCode());
									logger.info("updateLineItemStatus_Error_Message=" + cartError.getMessage());
									logger.info("updateLineItemStatus_Error_Description=" + cartError.getDescription());
								}
								throw new UnRecoverableException(errorList.get(0).getMessage());
							}
						}

						List<String> lineitem_id_list =  LineItemUtil.containsSaversOffers(order);
						if( !lineitem_id_list.isEmpty())
						{
							startTimer=timer.getTime();	
							LineItemService.INSTANCE.submitMultipleLineItem( agentId, String.valueOf(order.getExternalId()), lineitem_id_list,
									CartSalesContextFactory.INSTANCE.getStatusCompleteSalesContext(salesContext), zipOnlySearch);	
							logger.info("TimeTakenforSubmitLineItemServiceCall="+(timer.getTime()-startTimer));

						}
						else
						{
							if(salesContext != null)
							{
								startTimer=timer.getTime();	
								OrderService.INSTANCE.updateSalesContext(agentId, String.valueOf(order.getExternalId()), CartSalesContextFactory.INSTANCE.getStatusCompleteSalesContext(salesContext), SalesContextFactory.INSTANCE.getAttribute(salesContext, "GUID"), zipOnlySearch);
								logger.info("TimeTakenforUpdateOrderServiceCall="+(timer.getTime()-startTimer));
							}
						}
					}
					try{
						Utils.postVZSaveSmartCartProduct(order, request.getSession());
					}catch(Exception e){
						logger.info("error occured while post VZ SaveSmartCartProduct "+e.getMessage());
					}
				}
				boolean isCSATRequires = false;
				if ( !Utils.isBlank(request.getParameter("requiresCSAT")) ) 
				{
					isCSATRequires = Boolean.parseBoolean(request.getParameter("requiresCSAT"));
				}
				logger.info("isCSATRequires=" + isCSATRequires);

				SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
				String agentId = Utils.isBlank(request.getParameter("agentId")) ? salesCenterVo.getValueByName("Agent") : request.getParameter("agentId");

				if( orderId!=null && !isProductSaleInFlow)
				{
					startTimer = timer.getTime();
					OrderType order = OrderService.INSTANCE.getOrderByOrderNumber(String.valueOf(orderId), agentId, null, null, false, null);
					logger.info("TimeTakenforgetOrderByOrderNumber=" +(timer.getTime() - startTimer));
					request.getSession().setAttribute(ControllerConstants.ORDER, order);
					logger.info("Got_order_by_OrderNumber");
					if (isCSATRequires) 
					{
						com.AL.xml.cm.v4.ObjectFactory oFactory = new com.AL.xml.cm.v4.ObjectFactory();
						CustomerType customer = oFactory.createCustomerType();
						customer.setExternalId( customerId );

						ErrorList errorList = new ErrorList();
						CsatSurveyType csatSurveyType = oFactory.createCsatSurveyType();
						csatSurveyType.setId(1);
						CsatSurveysType csatSurveysType = oFactory	.createCsatSurveysType();
						csatSurveysType.getCsatSurvey().add(csatSurveyType);
						customer.setCsatSurveys(csatSurveysType);
						customer.setEMailOptIn(order.getCustomerInformation().getCustomer().isEMailOptIn());
						customer.setBestPhoneContact(order.getCustomerInformation()
								.getCustomer().getBestPhoneContact());
						customer.setMarketingOptIn(order.getCustomerInformation()
								.getCustomer().isMarketingOptIn());
						customer.setEMailProductUpdatesOptIn(order.getCustomerInformation()
								.getCustomer().isEMailProductUpdatesOptIn());
						String GUID = (String) request.getSession().getAttribute("GUID");

						if (GUID == null) 
						{
							GUID = UUID.randomUUID().toString();
						}

						Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
						Map<String, String> sourceEntity = new HashMap<String, String>();
						sourceEntity.put("source", "salescenter");
						sourceEntity.put("GUID", GUID);
						data.put("source", sourceEntity);
						CustomerContextType customerContext = CustomerFactory.INSTANCE.buildCustomerContext(data);
						startTimer = timer.getTime();
						customer = CustomerService.INSTANCE.submitCustomerType(agentId,String.valueOf(order.getCustomerInformation().getCustomer().getExternalId()),
								"updateCustomer", customer, null, customerContext,errorList);
						logger.info("TimeTakenforsubmitCustomerType=" +(timer.getTime() - startTimer));
						logger.info("UpdateCustomer_completed");
						if (errorList != null && errorList.size() > 0)
						{
							for (CartError cartError : errorList) 
							{
								logger.info("UpdateCustomerErrorCode="+cartError.getCode()+" ErrorMessage="+cartError.getMessage()
										+" ErrorDescription="+cartError.getDescription());
							}

							throw new UnRecoverableException(errorList.get(0).getMessage());
						}
					}

					List<LineItemType> lineItems = CartLineItemFactory.INSTANCE.getTPVLineItemsWithSortingOrder(order);

					logger.info("lineItems=" + lineItems);

					if (!lineItems.isEmpty()) 
					{
						request.getSession().setAttribute("tpvLineItems", lineItems);

						ModelAndView mav = showTpv(request);


						mav.addObject("tpvLineItemId", lineItems.get(0).getExternalId());

						logger.info("getting_mav_from_TPV" + mav);
						if(!Utils.isBlank(request.getParameter("addressId"))){
							Long addressId = Long.valueOf(request.getParameter("addressId"));
							mav.addObject("addressId", addressId);
						}
						mav.addObject("address", SalesUtil.INSTANCE.getAddress(order.getCustomerInformation().getCustomer(),
								com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString()));

						return mav;
					}
					else 
					{
						String isWarmTransferEnabled = request.getParameter("isWarmTransferEnabled");

						if (isWarmTransferEnabled != null && isWarmTransferEnabled.equalsIgnoreCase("true")) 
						{
							ModelAndView mav = warmTransferController.warmTransfer(request);
							logger.info("getting_mav_from_warm_transfer"+ mav);
							return mav;
						}
						else 
						{
							//SalesUtilsFactory.INSTANCE.clearPreviousSessionInfo(request);
							String absoluteURL = (String) request.getParameter("urlPath");
							logger.info("Redirecting_mav_into_login_process_page.");
							if( !Utils.isBlank(absoluteURL) )
							{
								return new ModelAndView("redirect:" + absoluteURL + "/salescenter/login_process");
							}
							return new ModelAndView("redirect:/salescenter/login_process");
						}
					}
				}
				else 
				{
					//SalesUtilsFactory.INSTANCE.clearPreviousSessionInfo(request);
					String absoluteURL = (String) request.getParameter("urlPath");
					logger.info("Redirecting_mav_into_login_process_page.");
					if( !Utils.isBlank(absoluteURL) )
					{
						return new ModelAndView("redirect:" + absoluteURL + "/salescenter/login_process");
					}
					return new ModelAndView("redirect:/salescenter/login_process");
				}
			}else
			{
				return new ModelAndView("redirect:/salescenter/login_process");
			}
		} 
		catch (Exception e) 
		{
			logger.error(e);
			throw new UnRecoverableException(e.getMessage());
		}finally{
			timer.stop();
		}

	}

	/**
	 * 
	 * Displays TPV dialogs if the line item has TPV event.
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws RecoverableException
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView showTpv(HttpServletRequest request)
			throws UnRecoverableException {
		logger.info("showTpv_begin");
		try {
			SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession()
					.getAttribute("salescontext");

			List<LineItemType> lineItems = (List<LineItemType>) request
					.getSession().getAttribute("tpvLineItems");
			String providerExternalId = null;
			String parentExternalId = null;
			String tpvScript = null;
			String productExternalId = null;
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
					productExternalId = detailType.getProductLineItem().getExternalId();
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

			ModelAndView mav = new ModelAndView("sales.tpv");

			/*
			 * If we want to GUID on Sales Context then last parameter of below method is true otherwise false.
			 */
			Map<String, Map<String, String>> context = SalesDialogueFactory.INSTANCE.updateContextMapWithReferrerAndCallType("TPV",salesCenterVo,true);

			List<DialogueType> dialogueListType = SalesDialogueBuilder.INSTANCE.getTPVDialogue(providerExternalId, 
					parentExternalId, salesCenterVo.getValueByName("GUID"),context);

			logger.info("Getting_TPV_Dialogue_List" + dialogueListType);
			Map<String, String> dataFieldMap = new HashMap<String, String>();
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

			logger.debug("Enable_Dependency_Map" + enableDependencyMap);
			logger.debug("Disable_Dependency_Map" + disableDependencyMap);

			StringBuilder events = new StringBuilder();
			List<Fieldset> fieldsetList = WarmTransferHtmlFactory.INSTANCE.dialogueToFieldSet(events, dialogueListType,
					enableDependencyMap, allDataFieldList, dataFieldMap, request);

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
			
			for (Fieldset fieldset : fieldsetList) 
			{
				String element = HtmlBuilder.INSTANCE.toString(fieldset);
				element = salesCenterVo.replaceNamesWithValues(element);
				element = parseHtmlTags(element);

				events.append(element);
				logger.debug("Html_data"	+ events.toString());
			}

			logger.debug("TPV_All_Data_Field_List" + allDataFieldList);

			mav.addObject("dataField", events.toString());
			mav.addObject("enableDialogueMap", enableDependencyMap);
			mav.addObject("disableDialogueMap", disableDependencyMap);
			mav.addObject("allDataFieldList", allDataFieldList);
			mav.addObject("providerExternalId", providerExternalId);
			mav.addObject("productExternalId", productExternalId);
			request.getSession().setAttribute("dataFieldMap", dataFieldMap);
			mav.addObject("isClosingCall", true);
			logger.info("showTpv_End");
			return mav;
		} catch (Exception e) {
			logger.error(e);
			throw new UnRecoverableException(e.getMessage());
		}
	}

	private String parseHtmlTags(String dataFieldText) {
		dataFieldText = dataFieldText.replaceAll("&amp;", "&");
		dataFieldText = dataFieldText.replaceAll("&lt;", "<");
		dataFieldText = dataFieldText.replaceAll("&gt;", ">");
		return dataFieldText;
	}

}