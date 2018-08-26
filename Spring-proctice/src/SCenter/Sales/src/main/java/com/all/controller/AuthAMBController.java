package com.AL.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.AL.managers.SalesStatusUpdateManager;
import com.AL.productResults.managers.RESTClientForDrupal;
import com.AL.ui.constants.Constants;
import com.AL.ui.dao.CustomerDiscoveryDao;
import com.AL.ui.dao.CustomerTrackerDao;
import com.AL.ui.dao.SalesCallDao;
import com.AL.ui.dao.SalesSessionDao;
import com.AL.ui.factory.SalesUtilsFactory;
import com.AL.ui.service.config.ConfigRepo;
import com.AL.ui.service.V.DetailService;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.xml.dtl.v4.DetailsRequestResponse;
import com.AL.xml.dtl.v4.OrderSourceBusinessPartyType;
import com.AL.xml.dtl.v4.OrderSourceResultType;
import com.AL.xml.dtl.v4.TelephonyListType;
import com.AL.xml.dtl.v4.TelephonyType;
import com.AL.xml.v4.NameValuePairType;
import com.AL.xml.v4.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextEntityType;
import com.AL.xml.v4.SalesContextType;

/**
 * @author kamesh
 *
 */

@Controller
public class AuthAMBController extends BaseController {

	private static final int detailsCacheTimeout = ConfigRepo.getInt("*.details_cache_time_out") == 0 ? 7200000 : ConfigRepo.getInt("*.details_cache_time_out");	

	private static final Logger logger = Logger.getLogger(AuthAMBController.class);
	
	private static ExecutorService pool;
	
	@Autowired
	private SalesSessionDao salesSessionDao;

	@Autowired
	private SalesCallDao salesCallDao;
	
	@Autowired
	private CustomerDiscoveryDao customerDiscoveryDao;
	
	@Autowired
	private CustomerTrackerDao customerTrackerDao;

	/**
	 * Default constructor.
	 */
	public AuthAMBController() 
	{
		super();
	}

	/**
	 * Capturing the Date and Time when we have callAlert Event	   
	 * 
	 * @param request
	 * @return message
	 */
	@RequestMapping(value="/authAMBMessage")
	public @ResponseBody String handleAMBMessage1(HttpServletRequest request) throws Exception {
		String message = request.getParameter("message");
		logger.info("message="+message);
		return message;
	}

	/**
	 * Capturing Date and Time when we have callDisconnect Event	   
	 * 
	 * @param request  
	 * @return  
	 */
	@RequestMapping(value="/ambDisconnect")
	public @ResponseBody void handleAMBDisconnection(HttpServletRequest request) throws Exception {
		String ambDisconnectdatetime = request.getParameter("date");
		request.getSession().setAttribute("ambDisconnectdatetime", ambDisconnectdatetime);
		logger.info("ambDisconnectdatetime="+ambDisconnectdatetime);
	}

	/**
	 * Functionality for displaying Screenpop	   
	 * 
	 * @param request  
	 * @return String 
	 */
	@RequestMapping(value="/authAMB")
	public @ResponseBody String handleAMBMessage(HttpServletRequest request) throws Exception {

		String message = request.getParameter("message");
		String currentdatetime = request.getParameter("currentdatetime");
		request.getSession().setAttribute("currentdatetime", currentdatetime);
		logger.info("currentdatetime="+currentdatetime);
		logger.info("messageInAuthAMBController="+message);
		logger.info("Popflow_Entered_in_AuthAMBController");
		long startTimer = 0;
		StopWatch ambTimer = new StopWatch();
		ambTimer.start();
		logger.info("AMBstartTime=" + ambTimer.getTime());
		request.getSession().setAttribute("AMBTimer", ambTimer);

		String ctiResponse = "";

		boolean isSalesCallPopulated = false;


		SalesCenterVO salesCenterVo = new SalesCenterVO();
		List<OrderSourceResultType> orderSourceResultTypeList = new ArrayList<OrderSourceResultType>();
		List<OrderSourceBusinessPartyType> businessPartyList = new ArrayList<OrderSourceBusinessPartyType>();

		request.getSession().setAttribute("isSalesCallPopulated", isSalesCallPopulated);
		request.getSession().setAttribute("idlePageStartTime", Calendar.getInstance());
		logger.info("getting_response_from_amb");
		ctiResponse= request.getParameter("message");
		logger.info("ctiResponseInAMB="+ctiResponse);
		
		//Retrieving the values from JSON
		JSONObject feedback = new JSONObject(ctiResponse);
		String agentPhoneId = feedback.getString("agentPhoneId");
		String calledAddress = feedback.getString("calledAddress");
		String callingAddress = feedback.getString("callingAddress");
		String calledDevice = feedback.getString("calledDevice");
		String callingDevice = feedback.getString("callingDevice");
		String UEC = feedback.getString("userEnteredCode");
		String ucid = feedback.getString("ucid");
		String eventType = feedback.getString("eventType");
		String dtsequenceid ="";
		logger.info("agentPhoneId="+agentPhoneId + " calledAddress="+calledAddress + " callingAddress="+callingAddress 
				+ " uec="+UEC+ " ucid="+ucid + " eventType="+eventType + " calledDevice="+calledDevice + " callingDevice="+callingDevice);
		request.getSession().setAttribute("salescontextAuthAMB", feedback);
		if(Utils.isBlank(calledDevice)){
			if (calledAddress != null && calledAddress.length() > 5){
				calledDevice = calledAddress;
				logger.info("calledDeviceFromCalledAddress="+calledDevice);
			}
		}
		if (calledAddress != null && calledAddress.length() > 5){
			//calledAddress = "65316";
			calledAddress = "66229";
		}
		
		if(Utils.isBlank(callingDevice)){
			if (!Utils.isBlank(callingAddress)){
				callingDevice = callingAddress;
			}
		}
		request.getSession().setAttribute("fuseResponseData",null);
		if(!Utils.isBlank(calledDevice) && (calledDevice.length()==10 || calledDevice.length()==11) && !Utils.isBlank(callingDevice) && !Utils.isBlank(ucid)){
			String publicIpAddr = null;  
			try{
				URL ipAddr = new URL("http://checkip.amazonaws.com");
			    BufferedReader in = new BufferedReader(new InputStreamReader(ipAddr.openStream()));
			    publicIpAddr = in.readLine(); 
			}catch(Exception e){
				logger.warn("Error_in_handleAMBMessage",e);
			}
			String clientIpAddr = SalesUtilsFactory.INSTANCE.getClientIPAddress(request);
			logger.info("making Fuse call with dnis="+calledDevice + " ani="+callingDevice + " ucid="+ucid +" clientIpAddress="+clientIpAddr +" publicIpAddress="+publicIpAddr+" vdn="+calledAddress +" vdnLength="+calledAddress.length());
			JSONObject obj = new JSONObject();
			obj.put("dnis", calledDevice);
			obj.put("ani", callingDevice);
			obj.put("callId", ucid);
			obj.put("callIdSource", "AL");
			request.getSession().setAttribute("ctiMessage", obj);
		}
		try {
			//when ctiresponse is not null
			if (ctiResponse !=null && !ctiResponse.trim().isEmpty()) {
				String contextIndicator = "";
				salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
				salesCenterVo.setValueByName("vdn", calledAddress);// put this in session
				salesCenterVo.setValueByName("callingAddress", callingAddress);
				if (UEC != null && !(UEC.equals(""))) {
					int UECLength= UEC.length();
					logger.info("UECLength="+UECLength);
					if(UECLength > 3){						
						dtsequenceid = UEC.substring(3, UECLength-1);
						dtsequenceid=String.valueOf(Integer.parseInt(dtsequenceid));
						logger.info("dtsequenceid="+dtsequenceid);
					}
					//salesCenterVo.setValueByName("dtsequenceid", dtsequenceid);	
					logger.info("when_UEC_is_not_empty_or_null_in_AuthAMBController");
					if(UEC.length()>3){
						contextIndicator = UEC.substring(1, 3);// gives context indicator value
					}
					else{
						contextIndicator="NA";

					}
					if(Utils.isBlank(contextIndicator)){
						contextIndicator="NA";
					}
					logger.info("contextIndicator="+contextIndicator);
					//when contextindicator value is 00 or 05
					if (contextIndicator.equals("00")|| contextIndicator.equals("05")) {
						logger.info("contextIndicator_is_0_or_5_in_AuthAMBController");
						// get vdn from uec
						String vdnuec=UEC.substring(3, UECLength-1);//5 digits in direct transfer sequence number
						logger.info("vdnuec="+vdnuec);
						vdnuec=String.valueOf(Integer.parseInt(vdnuec));
						salesCenterVo.setValueByName("vdnuec", vdnuec);	
						logger.info("vdnuecAuthAMBControllerNoLeadingZeros="+vdnuec);
						// get order sources
						startTimer = ambTimer.getTime();
						DetailsRequestResponse drr1 = DetailService.INSTANCE.getAllOrderSources("12345", Long.valueOf(detailsCacheTimeout));
						logger.info("TimeTakenforgetAllOrderSources=" +(ambTimer.getTime() - startTimer));
						if(drr1 == null || drr1.getCorrelationId().isEmpty()){
							logger.error("Detail_Service_is_Down");
						}
						orderSourceResultTypeList =  drr1.getResponse().getOrderSourceResultElement();
						logger.info("orstList_contextIndicator_is_0_or_5_in_AuthAMBController="+orderSourceResultTypeList.size());
						try {
							addBusinesspartyToModel(orderSourceResultTypeList, calledAddress, businessPartyList, request);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					else {
						salesCenterVo.setValueByName("dtsequenceid", dtsequenceid);	
						logger.info("dtsequenceidAuthAMBController="+dtsequenceid);
						logger.info("dtsequenceidAfterTrimAuthAMBController="+dtsequenceid);
						startTimer = ambTimer.getTime();
						DetailsRequestResponse drr1 = DetailService.INSTANCE.getAllOrderSources("12345", Long.valueOf(detailsCacheTimeout));
						logger.info("TimeTakenforgetAllOrderSources=" +(ambTimer.getTime() - startTimer));
						if(drr1 == null || drr1.getCorrelationId().isEmpty()){
							logger.error("Detail_Service_is_Down");
						}
						orderSourceResultTypeList =  drr1.getResponse().getOrderSourceResultElement();
						try {
							addBusinesspartyToModel(orderSourceResultTypeList, calledAddress, businessPartyList, request);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				} 

				/*	
				 * When UEC is Empty 			 
				 */
				else {
					logger.info("when UEC is empty in AuthAMBController");
					contextIndicator="NA";
					// when call has no UEC we need to check whether it has VDN	(Called Address)		
					if(calledAddress!=null && !(calledAddress.equals(""))){
						logger.info("calledAddressAuthAMBController="+calledAddress);
//						calledAddress=String.valueOf(Integer.parseInt(calledAddress));
						startTimer = ambTimer.getTime();
						DetailsRequestResponse drr1 = DetailService.INSTANCE.getAllOrderSources("12345", Long.valueOf(detailsCacheTimeout));
						logger.info("TimeTakenforgetAllOrderSources=" +(ambTimer.getTime() - startTimer));
						if(drr1 == null || drr1.getCorrelationId().isEmpty()){
							logger.error("Detail_Service_is_Down");
						}
						orderSourceResultTypeList =  drr1.getResponse().getOrderSourceResultElement();
						logger.info("orstListAuthAMBController="+orderSourceResultTypeList.size());
						try {
							addBusinesspartyToModel(orderSourceResultTypeList, calledAddress, businessPartyList, request);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					else{
						logger.info("if_called_address_value_is_not_matching_with_vdn_in_ordersources_in_AuthAMBController");
						startTimer = ambTimer.getTime();
						DetailsRequestResponse drr = DetailService.INSTANCE.getAllOrderSources("12345", Long.valueOf(detailsCacheTimeout));
						logger.info("TimeTakenforgetAllOrderSources=" +(ambTimer.getTime() - startTimer));
						if(drr == null || drr.getCorrelationId().isEmpty()){
							logger.error("Detail_Service_is_Down");
						}
						if(drr != null && !(drr.equals(""))){ 
							logger.info("drr_is_not_null_in_AuthAMBController");
							orderSourceResultTypeList =  drr.getResponse().getOrderSourceResultElement();
							addBusinesspartyToModel(orderSourceResultTypeList, businessPartyList);
						}
					}
				}
				salesCenterVo.setValueByName("salesFlow.contextId", contextIndicator);
			}
			else{
				startTimer = ambTimer.getTime();
				DetailsRequestResponse drr = DetailService.INSTANCE.getAllOrderSources("12345", Long.valueOf(detailsCacheTimeout));
				logger.info("TimeTakenforgetAllOrderSources=" +(ambTimer.getTime() - startTimer));
				
				if(drr == null || drr.getCorrelationId().isEmpty()){
					logger.error("Detail_Service_is_Down");
				}
				if(drr != null && !(drr.equals(""))){
					logger.info("drr_is_not_null_in_AuthAMBController");
					orderSourceResultTypeList =  drr.getResponse().getOrderSourceResultElement();
					addBusinesspartyToModel(orderSourceResultTypeList, businessPartyList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ambTimer.stop();
		}
		JSONArray businessPartyJArray = new JSONArray();
		List<OrderSourceBusinessPartyType> uniqueBusinessPartyList = new ArrayList<OrderSourceBusinessPartyType>();
		Map<String,OrderSourceBusinessPartyType> sorting = new TreeMap<String, OrderSourceBusinessPartyType>();
		for(OrderSourceBusinessPartyType obj:businessPartyList){
			//For 91247 Sorting and changes the code for Unique Referrer names
			if(sorting.get(obj.getName()) == null){
				sorting.put(obj.getName(), obj);
			}
		}
		String resultBusinessParty = "";
		Map<String,String> vdnMap = new HashMap<String, String>();
		for(OrderSourceResultType obj:orderSourceResultTypeList){
			List<TelephonyType> telephonyList = obj.getOrderSource().getTelephonyList().getTelephony();
			for(TelephonyType telephonyType : telephonyList){
				if(Constants.SALES.equalsIgnoreCase(telephonyType.getCallType())){
					String referrerId =obj.getOrderSource().getBusinessParty().getExternalId();
					String referrername = obj.getOrderSource().getBusinessParty().getName();
					String referrerkey  = referrerId +"|"+ referrername;

					String vdn = telephonyType.getVdn();
					if (vdn.equals(calledAddress)) {
						resultBusinessParty = referrerId +"|"+ referrername;
					}	
					vdnMap.put(referrerkey, vdn);
				}
			}
		}

		for(Entry<String, OrderSourceBusinessPartyType> entry:sorting.entrySet()){
			uniqueBusinessPartyList.add(entry.getValue());
		}
		for(OrderSourceBusinessPartyType business : uniqueBusinessPartyList){
			JSONObject jobj = new JSONObject();
			String curBusinessName = business.getExternalId()+"|"+business.getName();
			try {				
				jobj.put("value", curBusinessName+"|"+(curBusinessName.equalsIgnoreCase(resultBusinessParty) ? calledAddress : vdnMap.get(curBusinessName)) );
				jobj.put("text", business.getName());
				businessPartyJArray.put(jobj);
			} catch (JSONException e) {
				logger.warn("Error_in_AuthAMBController",e);
			}
		}
		logger.info("BusinessPartyArraySize="+businessPartyJArray.length());
		return businessPartyJArray.toString();
	}


	/**
	 * Utility to fetch the BusinessPartyNames

	 *
	 * @param list
	 * @param businessPartyList   
	 * @throws JSONException
	 */
	private void addBusinesspartyToModel(List<OrderSourceResultType> list,
			List<OrderSourceBusinessPartyType> businessPartyList)throws Exception {
		for(OrderSourceResultType orst: list){
			businessPartyList.add(orst.getOrderSource().getBusinessParty());
		}
	}


	/**
	 * Utility to fetch the BusinessPartyNames

	 *
	 * @param orstList
	 * @param businessPartyList   
	 * @param vdnuec
	 * @throws JSONException
	 */
	private void addBusinesspartyToModel(List<OrderSourceResultType> orstList, String vdnuec,
			List<OrderSourceBusinessPartyType> businessPartyList, HttpServletRequest request) throws Exception{
		StopWatch ambTimer = new StopWatch();
		ambTimer.start();
		long startTimer = 0;
		boolean isCustomerCareVDN = false;
		try{
		logger.info("OrderSourceResultTypeSizeAuthAMBController="+orstList.size());
		logger.info("vdnuec="+vdnuec);
		JSONObject vdnBusinessObj = new JSONObject();
		for(OrderSourceResultType orst: orstList){
			//logger.info("In ordersourceresulttype loop in AuthAMBController");
			TelephonyListType tlt= orst.getOrderSource().getTelephonyList();
			if(tlt!=null && !tlt.equals("")){
				List<TelephonyType> telephonyList=tlt.getTelephony();
				for(TelephonyType telephonyType : telephonyList){
					if(Constants.SALES.equalsIgnoreCase(telephonyType.getCallType())){
						String vdn = telephonyType.getVdn();
						//logger.info("vdn value in new method is::in AuthAMBController::::"+vdn);
						vdnBusinessObj.put(vdn, orst.getOrderSource().getBusinessParty());
					}
					else if(Constants.CUSTOMERCARE.equalsIgnoreCase(telephonyType.getCallType()) 
							&& telephonyType.getVdn()!= null && telephonyType.getVdn().equals(vdnuec)){
						isCustomerCareVDN = true;
					}
				}
				//logger.info("vdn value issss:::in AuthAMBController:::"+vdn);
				//logger.info("vdnBusinessObj::::::::::::::"+vdnBusinessObj);
			}
			//logger.info("************************");
		}
		if(vdnBusinessObj.has(vdnuec)){
			logger.info("when_vdn_equals_vdnuec_in_AuthAMBController");
			OrderSourceBusinessPartyType busParty = (OrderSourceBusinessPartyType)vdnBusinessObj.get(vdnuec);
			logger.info("BusinessPartyExternalIdAuthAMBController=" + busParty.getExternalId());
			logger.info("BusinessPartyNameAuthAMBController" + busParty.getName());
			businessPartyList.add(busParty);
		}
		else{
			if(isCustomerCareVDN){
				logger.info("customerCareVDNReceivedInTheCTIMessage="+vdnuec);
			}
			else{
				logger.info("vdnNotFoundInDetailsData="+vdnuec);
			}
			startTimer = ambTimer.getTime();
			DetailsRequestResponse drr = DetailService.INSTANCE.getAllOrderSources("12345", Long.valueOf(detailsCacheTimeout));
			logger.info("TimeTakenforgetAllOrderSources=" +(ambTimer.getTime() - startTimer));
			if(drr == null || drr.getCorrelationId().isEmpty()){
				logger.error("Detail_Service_is_Down");
			}
			if(drr != null && !(drr.equals(""))){
				List<OrderSourceResultType> osrtList =  drr.getResponse().getOrderSourceResultElement();   
				addBusinesspartyToModel(osrtList, businessPartyList);
			}
		 }
		}finally{
			ambTimer.stop();
		}
	}
	
	@RequestMapping(value="/newAMBMessage")
	public @ResponseBody void newAMBMessage(HttpServletRequest request) throws Exception {
		try{
			logger.info("newAMBMessageData="+request.getParameter("messageData"));
			request.getSession().setAttribute("newAMBMessageData", request.getParameter("messageData"));
			
		}catch (Exception e) 
		{
			logger.warn("Error_at_AuthAMBController",e);
		}
	}

	@RequestMapping(value="/storeAMBMessage")
	public @ResponseBody void storeAMBMessageInSession(HttpServletRequest request) throws Exception {
		try{
			logger.info("storeAMBMessageInSession_begin");
			HttpSession session = request.getSession();
			session.setAttribute("savedAMBMessageData", request.getParameter("messageData"));
			session.removeAttribute("newAMBMessageData");

			String isConfirmedToSubmit = request.getParameter("isConfirmedToSubmit");
			String lineItems = request.getParameter("lineItemsJson");
			String orderId = request.getParameter("orderId");
			Long customerId = (Long) request.getSession().getAttribute("customerID");
			SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
			int zipOnlySearch = 0;
			if(salesCenterVo != null && salesCenterVo.getValueByName("isZipOnlySearch") != null && salesCenterVo.getValueByName("isZipOnlySearch").equalsIgnoreCase("YES")){
				zipOnlySearch = 1;
			}
			if (orderId != null && customerId != null) {
				if (request.getSession().getAttribute("customerDiscoveryMap") != null) {
					Map<String, String> customerDiscoveryInsertMap = (Map<String, String>) request.getSession().getAttribute("customerDiscoveryMap");	
					boolean notepadRecoSave = request.getSession().getAttribute("notepadRecoSave") != null ? (Boolean) request.getSession().getAttribute("notepadRecoSave") : false;
					if (!customerDiscoveryInsertMap.isEmpty()) {
							customerDiscoveryDao.insert(Long.valueOf(orderId), customerId, customerDiscoveryInsertMap);
					}
				} else {
					if(request.getSession().getAttribute("landlord") != null){
						Map<String, String> customerDiscoveryInsertLandlordMap = new LinkedHashMap<String, String>();
						customerDiscoveryInsertLandlordMap.put("Landlord","true");
						customerDiscoveryDao.insert(Long.valueOf(orderId), customerId, customerDiscoveryInsertLandlordMap);
						}
				}
				OrderManagementRequestResponse orderManagementRequestResponse = OrderService.INSTANCE.getOrderManagementRequestResponseByOrderNumber(orderId);
				if(orderManagementRequestResponse!=null)
				{
					try{
					OrderType orderType = orderManagementRequestResponse.getResponse().getOrderInfo().get(0);
					SalesContextType salesContext = orderManagementRequestResponse.getResponse().getSalesContext();
					boolean isSalesSessionCompleted = isSessionStatusCompleted(salesContext);
					String populatedCustomerTrackerOrderId = String.valueOf((Long)request.getSession().getAttribute("populatedCustomerTrackerOrderId"));
					String populatedVerizonSmartCartOrderId = String.valueOf(request.getSession().getAttribute("populatedVerizonSmartCartOrderId")); 
					 
					if(Utils.isBlank(populatedCustomerTrackerOrderId) || !populatedCustomerTrackerOrderId.equals(orderType.getExternalId()))
					{
						
							Utils.insertAndUpdateCustomerTrackerData(orderType, session, customerTrackerDao);
							
					}
					try{
						if(Utils.isBlank(populatedVerizonSmartCartOrderId) || !populatedVerizonSmartCartOrderId.equals(Long.toString(orderType.getExternalId()))){
							Utils.postVZSaveSmartCartProduct(orderType, session);
						}
					}catch(Exception e){
						logger.info("error occured while post VZ SaveSmartCartProduct "+e.getMessage());
					}
					}catch(Exception e){
						logger.info("error occured while inserting the data "+e.getMessage());
					}
				}
			}
			try{
				String dominionProductExtIds = (String)request.getSession().getAttribute("dominionProductExtIds");
				if (request.getSession().getAttribute("pauseAndResumeURL")!= null && request.getSession().getAttribute("phoneId")!= null 
						&& !Utils.isBlank(dominionProductExtIds)&& !"NA".equalsIgnoreCase(dominionProductExtIds)){
					RESTClientForDrupal.INSTANCE.resumeCall(String.valueOf(request.getSession().getAttribute("phoneId")), String.valueOf(request.getSession().getAttribute("pauseAndResumeURL")));
				}
			}
			catch(Exception e){
				logger.warn("Exception_occurred_in_resume_call"+e.getMessage());
			}
			if(pool == null)
			{
				int i = ConfigRepo.getInt("*.thread_pool_size") == 0 ? 500 : ConfigRepo.getInt("*.thread_pool_size");
				logger.info("threadPoolSizeInAuthAMBController="+i);
				pool = Executors.newFixedThreadPool(i);
			}
			SalesStatusUpdateManager updateManager = new SalesStatusUpdateManager();
			String typeOfService = (String) request.getSession().getAttribute("typeOfService");
			logger.info("typeOfService=" + typeOfService);
			if (request.getSession().getAttribute("isMoveInDelta") != null
					&& !Utils.isBlank(typeOfService)
					&& !"moveInDeltaService".equalsIgnoreCase(typeOfService)) {
				updateManager.setVvdFlag("true");
			} else {
				updateManager.setVvdFlag("false");
			}
			updateManager.setZipOnlySearch(zipOnlySearch);
			if(!Utils.isBlank(lineItems))
			{
				JSONObject lineItemsJsonObject = new JSONObject(lineItems);
				
				if("yes".equalsIgnoreCase(lineItemsJsonObject.getString("hasLineItemsTOSubmit")) )
				{
					JSONArray lineItemList = lineItemsJsonObject.getJSONArray("lineItemList");
					updateManager.setLineItemIds(lineItemList);
				}
				
				if("yes".equalsIgnoreCase(lineItemsJsonObject.getString("hasSubmittedRTProduct")) )
				{
					updateManager.setHasSubmittedRtProducts(true);
				}
				else
				{
					updateManager.setHasSubmittedRtProducts(false);
				}
				
				if(Constants.YES.equalsIgnoreCase(isConfirmedToSubmit))
				{
					updateManager.setPlaceOrder(true);
				}
				else
				{
					updateManager.setPlaceOrder(false);
				}
				
				if(request.getSession().getAttribute("isAllProductsSubmitted")!=null 
					&& Constants.YES.equalsIgnoreCase((String)request.getSession().getAttribute("isAllProductsSubmitted")))
				{
					updateManager.setHasAllProductsSubmitted(true);
				}
				else
				{
					updateManager.setHasAllProductsSubmitted(false);
				}
				
				if( lineItemsJsonObject.get("orderId") != null)
				{
					updateManager.setOrderId(String.valueOf(lineItemsJsonObject.get("orderId")));
				}
				updateManager.setSalesCallDao(salesCallDao);
				updateManager.setSalesSessionDao(salesSessionDao);
				if(request.getSession().getAttribute("salesCallId")!= null){
				    updateManager.setSalesCallId((Long) request.getSession().getAttribute("salesCallId"));
				}
				if(request.getSession().getAttribute("ambDisconnectdatetime")!= null){
				    updateManager.setAmbDisconnectDateTime((String) request.getSession().getAttribute("ambDisconnectdatetime"));
				}
			}
			else
			{
				updateManager.setOrderId(orderId);
				updateManager.setSalesCallDao(salesCallDao);
				updateManager.setSalesSessionDao(salesSessionDao);
				if(request.getSession().getAttribute("isAllProductsSubmitted")!=null 
					&& Constants.YES.equalsIgnoreCase((String)request.getSession().getAttribute("isAllProductsSubmitted")))
				{
					updateManager.setHasAllProductsSubmitted(true);
				}
				else
				{
					updateManager.setHasAllProductsSubmitted(false);
				}
			}
			  
			pool.execute(updateManager);
			
		}catch (Exception e) 
		{
			logger.warn("Error_at_AuthAMBController",e);
		}
	}
	

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return null;
	}
	
	/**
	 * Functionality for saving AMB message	   
	 * 
	 * @param request  
	 * @return String 
	 */
	@RequestMapping(value="/saveAMBMessage")
	public @ResponseBody String saveAMBMessage(HttpServletRequest request) throws Exception {
		try{

			String message = request.getParameter("ambMessageData");
			String currentdatetime = request.getParameter("currentdatetime");
			request.getSession().setAttribute("currentdatetime", currentdatetime);
			logger.info("currentdatetime="+currentdatetime);
			logger.info("messageInAuthSaveAMBController="+message);
			StopWatch ambTimer = new StopWatch();
			ambTimer.start();
			logger.info("AMBstartTime=" + ambTimer.getTime());
			request.getSession().setAttribute("AMBTimer", ambTimer);

			String ctiResponse = "";

			boolean isAMBMessageDataReceived = true;

			request.getSession().setAttribute("isAMBMessageDataReceived", isAMBMessageDataReceived);
			request.getSession().setAttribute("idlePageStartTime", Calendar.getInstance());
			logger.info("getting_response_from_amb");
			ctiResponse= request.getParameter("ambMessageData");
			logger.info("ctiResponseInAMB="+ctiResponse);

			//Retrieving the values from JSON
			JSONObject feedback = new JSONObject(ctiResponse);
			String agentPhoneId = feedback.getString("agentPhoneId");
			String calledAddress = feedback.getString("calledAddress");
			String callingAddress = feedback.getString("callingAddress");
			String UEC = feedback.getString("userEnteredCode");
			String ucid = feedback.getString("ucid");
			String eventType = feedback.getString("eventType");
			logger.info("agentPhoneId="+agentPhoneId);
			logger.info("calledAddress="+calledAddress);
			logger.info("callingAddress="+callingAddress);
			logger.info("uec="+UEC);
			logger.info("ucid="+ucid);
			logger.info("eventType="+eventType);
			request.getSession().setAttribute("salescontextAuthAMBMessage", feedback);
			return "SUCCESS";
		}
		catch(Exception e){
			return "FAIL";
		}
	}
	@RequestMapping(value="/fuseAnalyticsResponse")
	public @ResponseBody String fuseAnalyticsResponse(HttpServletRequest request) throws Exception {
		try{
			String message = request.getParameter("data");
			String publicIpAddress  = request.getParameter("clientIpAddress");
			String responseHeader = request.getParameter("responseHeader");
			String scriptError = request.getParameter("scriptError");
			if(scriptError != null){
				logger.info("Error occured in JavaScript for the Fuse call");
			}
			JSONObject result = new JSONObject();
			//logger.info("fuseAnalyticsResponse data="+message);
			Map<String, String> map = new HashMap<String, String>();
	        Enumeration headerNames = request.getHeaderNames();
	        while (headerNames.hasMoreElements()) {
	            String key = (String) headerNames.nextElement();
	            String value = request.getHeader(key);
	            if(!key.equalsIgnoreCase("Cookie")){
	            	if(key.equalsIgnoreCase("Host")){
	            		map.put(key, (String)request.getSession().getAttribute("fuseUrl"));
	            	}else{
	            		if(key.equalsIgnoreCase("Content-Length")){
	            			map.put(key, String.valueOf((request.getSession().getAttribute("ctiMessage").toString()).length()));
	            		}if(key.equalsIgnoreCase("Content-Type")){
	            			map.put(key, "application/json");
	            		}else{
	            			map.put(key, value);
	            		}
	            		
	            	}
	            }
	        }
	        JSONObject responseHeaderJson = new JSONObject();
	        if(responseHeader != null){
	        	responseHeader = responseHeader.trim().replace("\\n", "").replace("\\r", ",").replaceAll("\"", "");
	        	for(String s : responseHeader.split(",")){
	        		if(s.length()>1){
	        			String jsonValeus[] = s.split(":");
		        		if(jsonValeus[0] != null && jsonValeus[1] != null){
		        			responseHeaderJson.put(jsonValeus[0].trim(), jsonValeus[1].trim());
		        		}	
	        		}
	        	}
	        }
	        String clientIpAddr = SalesUtilsFactory.INSTANCE.getClientIPAddress(request);
	        result.put("requestData", new JSONObject(request.getSession().getAttribute("ctiMessage").toString()));
	        result.put("responseData",  new JSONObject(message));
	        result.put("requestHeader", new JSONObject(map));
	        result.put("responseHeader",responseHeaderJson);
	        result.put("clientIpAddress",clientIpAddr);
	        result.put("publicIpAddress",publicIpAddress);
	        request.getSession().setAttribute("fuseResponseData", new JSONObject(message));
	        
	        logger.info("fuseAnalyticsRequestResponse="+result.toString().replaceAll("\"publicIpAddress\":", "publicIpAddress=").replaceAll("\"clientIpAddress\":", "clientIpAddress=").replaceAll("\"requestData\":", "requestData=").replaceAll("\"responseData\":", "responseData=").replaceAll("\"requestHeader\":", "requestHeader=").replaceAll("\"responseHeader\":", "responseHeader="));
	       
			request.getSession().setAttribute("ctiMessage", "");
	
		}catch(Exception e){
			logger.error("error in fuseAnalyticsResponse="+e.getMessage());
		}
		return "{}";
	}
	
	/**
	 * Verifying session status is completed or not. Returns "true" if and only if session status is completed and is updated by concert otherwise returns "false" 
	 * @param salesContext
	 * @return
	 */
	private boolean isSessionStatusCompleted(SalesContextType salesContext)
	{
		boolean isUpdateByConcert = false;
		boolean isSessionStatusCompleted = false;
		for (SalesContextEntityType salesContextEntityType : salesContext.getEntity())
		{
			if (salesContextEntityType.getName().equalsIgnoreCase(Constants.SYP))
			{
				for (NameValuePairType nameValuePairType : salesContextEntityType.getAttribute())
				{
					if ((nameValuePairType.getName().equalsIgnoreCase("updatedBy")))
					{
						if ( nameValuePairType.getValue().equalsIgnoreCase(Constants.CONCERT))
						{
							isUpdateByConcert = true;
						}
					}
					if ((nameValuePairType.getName().equalsIgnoreCase("sessionStatus")))
					{
						if (!nameValuePairType.getValue().equalsIgnoreCase("completed")){
							isSessionStatusCompleted = true;
						}
					}
				}
			}
		}
		
		if (isSessionStatusCompleted && isUpdateByConcert)
		{
			return true;
		}
		return false;
	}
}
