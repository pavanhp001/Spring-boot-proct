package com.AL.controller;

import static com.AL.ui.util.ConfigProperties.CYCLETIME;
import static com.AL.ui.util.ConfigProperties.DATAEXCHANGEURL;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.webflow.execution.Action;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.AL.html.Fieldset;
import com.AL.ui.builder.HtmlBuilder;
import com.AL.ui.constants.Constants;
import com.AL.ui.dao.OperatingCompanyDao;
import com.AL.ui.dao.WebLookupDao;
import com.AL.ui.domain.MDUOrderSource;
import com.AL.ui.domain.MDUProperty;
import com.AL.ui.domain.WebLookupCollection;
import com.AL.ui.domain.dynamic.dialogue.DataField;
import com.AL.ui.domain.dynamic.dialogue.DataGroup;
import com.AL.ui.domain.dynamic.dialogue.Dialogue;
import com.AL.ui.domain.sales.CustomerLookupObject;
import com.AL.ui.domain.sales.CustomerLookupItem;
import com.AL.ui.service.V.DialogServiceUI;
import com.AL.ui.util.HtmlFactory;
import com.AL.ui.util.SalesUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.ConsumerVO;
import com.AL.ui.vo.CustomerVO;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.ui.vo.SalesDialogueVO;
import com.AL.V.exception.BaseException;
import com.AL.V.exception.UnRecoverableException;
import com.AL.xml.v4.OrderType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



@Controller("BasicInformationController")
public class BasicInformationController implements Action {

	//private static final ExecutorService pool = Executors.newFixedThreadPool(ConfigRepo.getInt("thread_pool_size"));

	SimpleDateFormat format1 = new SimpleDateFormat("MMddyyyy");
	SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy");
	
	private final static String BLANK = "";

	@Autowired
	private WebLookupDao lookupDao;
	
	@Autowired
	private OperatingCompanyDao operatingCompanyDao;
	
	private static final Logger logger = Logger.getLogger(BasicInformationController.class);
	public Event execute(RequestContext request) throws UnRecoverableException {
		HttpServletRequest httpRequest =(HttpServletRequest)request.getExternalContext().getNativeRequest();
		HttpSession session = httpRequest.getSession();
		StopWatch timer = new StopWatch();
		timer.start();
		long startTimer = 0;
		try{
			logger.info("showBasicInfo_begin");
			
			httpRequest.getSession().setAttribute("hasProducts",false);
			httpRequest.getSession().setAttribute("ctiMessage", "");
			String sameCall = (String) session.getAttribute("sameCall");
			String customerId = httpRequest.getParameter("customerId");
			if(!Utils.isBlank(httpRequest.getParameter("callStartTimeInGreeting"))){
				httpRequest.getSession().setAttribute("callStartTime", httpRequest.getParameter("callStartTimeInGreeting"));
			}
			if(Utils.isBlank(customerId)){
				customerId = (String) httpRequest.getSession().getAttribute("selectedCustomerId");
			}
			
			WebLookupCollection webLookupStates = lookupDao.findUSStates();
			WebLookupCollection webLookupNamePrefix = lookupDao.findNamePrefix();
			WebLookupCollection webLookupNameSuffix = lookupDao.findNameSuffix();
			WebLookupCollection webLookupUnitType = lookupDao.findUnitType();
			WebLookupCollection webLookupRentOwn = lookupDao.findRentOwn();
			WebLookupCollection webLookupServiceAddressType = lookupDao.findServiceAddressType();

			logger.debug("JSON_String_For_STATES_is"+webLookupStates.getListAsJSON());
			logger.debug("JSON_String_For_Nameprefix_is"+webLookupNamePrefix.getListAsJSON());
			logger.debug("JSON_String_For_NameSuffix_is"+webLookupNameSuffix.getListAsJSON());
			logger.debug("JSON_String_For_unitType_is"+webLookupUnitType.getListAsJSON());
			logger.debug("JSON_String_For_RentOwn_is"+webLookupRentOwn.getListAsJSON());
			logger.debug("JSON_String_For_ServiceAddressType_is"+webLookupServiceAddressType.getListAsJSON());
			String contextIndicator = "";

			CustomerVO customerVO = new CustomerVO();
			OrderType order = (OrderType) httpRequest.getSession().getAttribute("order");
			SalesCenterVO salesCenterVo = (SalesCenterVO) httpRequest.getSession().getAttribute("salescontext");
			try{

				if (!Utils.isBlank(customerId)){
					httpRequest.getSession().removeAttribute("selectedCustomerId");
					startTimer=timer.getTime();
					Map<String,CustomerVO> m = RESTClient.INSTANCE.getCustomersdetailsById(customerId, DATAEXCHANGEURL, httpRequest);
					logger.info("TimeTakenforRestClientServicecall=" +(timer.getTime() - startTimer));
					for (Entry<String,CustomerVO> entry : m.entrySet()){
						customerVO = entry.getValue();
					}					
					if(customerVO != null){
						if(!Utils.isBlank(customerVO.getDtConfirmedEmailAddress())){
							order.getCustomerInformation().getCustomer().setBestEmailContact(customerVO.getDtConfirmedEmailAddress());
							salesCenterVo.setValueByName("customer.bestEmailContact", customerVO.getDtConfirmedEmailAddress());
						}
						else if(!Utils.isBlank(customerVO.getDtEmail())){
							order.getCustomerInformation().getCustomer().setBestEmailContact(customerVO.getDtEmail());
							salesCenterVo.setValueByName("customer.bestEmailContact", customerVO.getDtEmail());
						}
					}
				}
				//handling code for DT harness

				if(Utils.isBlank(customerVO.getDtSequenceNum())) {
					logger.info("when_customerVO_is_null");
					//customer is not in DB; probably DT xml data is injected; 
					//so display customer details based on DT XML
					ConsumerVO dtConsumer = (ConsumerVO)httpRequest.getSession().getAttribute("salescontextDt");
					String gasStrtDt = dtConsumer.getDtGasRequestedStartDate();
					String electricityStrtDt = dtConsumer.getDtRequestedStartDate();		
					if(gasStrtDt != null && gasStrtDt.length() == 8){
						Date parsedGasStartDate = format1.parse(gasStrtDt);
						gasStrtDt = format2.format(parsedGasStartDate);
					}

					if(electricityStrtDt != null && electricityStrtDt.length() == 8){
						Date parsedGasStartDate = format1.parse(electricityStrtDt);
						electricityStrtDt = format2.format(parsedGasStartDate);
					}
					logger.info("dtConsumer"+dtConsumer);
					if(dtConsumer!=null) {
						//this is only for DT harness injection of DT xml data
						logger.info("when_xml_input_is_not_null");
						if(dtConsumer.getDtSequenceNum() == null && Utils.isBlank(httpRequest.getParameter("customerLookupSelectId"))){
							contextIndicator = "00";
							salesCenterVo.setValueByName("salesFlow.contextId", contextIndicator);
						}
						//use the DtConsumer coming from DT harness 
						customerVO = new CustomerVO();
						customerVO.setDtNameFirst(dtConsumer.getDtNameFirst());
						customerVO.setDtNameMiddle(dtConsumer.getDtNameMiddle());
						customerVO.setDtNameLast(dtConsumer.getDtNameLast());
						customerVO.setDtSaCity(dtConsumer.getDtSaCity());
						customerVO.setDtSaState(dtConsumer.getDtSaState());
						customerVO.setDtPartnerAccountId(dtConsumer.getDtPartnerAccountId());
						customerVO.setDtSequenceNum(String.valueOf(dtConsumer.getDtSequenceNum()));
						customerVO.setDtSaStreet1(dtConsumer.getDtSaStreet1());
						customerVO.setDtPartner(dtConsumer.getDtPartner());
						customerVO.setDtSaZip(dtConsumer.getDtSaZip());
						customerVO.setDtGasRequestedStartDate(gasStrtDt);
						customerVO.setDtRequestedStartDate(electricityStrtDt);
						customerVO.setDtEmail(dtConsumer.getDtEmail());
						customerVO.setDtTelephoneNum(dtConsumer.getDtTelephoneNum());
						customerVO.setDtNamePrefix(dtConsumer.getDtNamePrefix());
						customerVO.setDtNameSuffix(dtConsumer.getDtNameSuffix());
						customerVO.setDtAgentId(dtConsumer.getDtAgentId());
						customerVO.setDtGasReqStartTimeBegin(dtConsumer.getDtGasReqStartTimeBegin());
						customerVO.setDtGasReqStartTimeEnd(dtConsumer.getDtGasReqStartTimeEnd());
						customerVO.setDtReqStartTimeBegin(dtConsumer.getDtReqStartTimeBegin());
						customerVO.setDtReqStartTimeEnd(dtConsumer.getDtReqStartTimeEnd());
						logger.info("the_suffix_value_is"+dtConsumer.getDtNameSuffix());
					}
					
				}
			}
			catch (ParseException e) {
				logger.error(e);
				throw new UnRecoverableException(e.getMessage());
			}
			catch(Exception e){
				logger.error(e);
				throw new UnRecoverableException(e.getMessage());
			} 
			logger.info("the_customerVO_is" +customerVO);
			logger.info("DtEmail=" +customerVO.getDtEmail());
			logger.info("UtilityOrderId="+customerVO.getDtPartnerAccountId());
			httpRequest.getSession().setAttribute("DtEmail", customerVO.getDtEmail());
			String completeAddress=customerVO.getDtSaStreet1();
			String dtSaStreet1 = customerVO.getDtSaStreet1();
			String dtSaCity = "";
			String dtSaState = "";
			String dtSaZip = "";
			List<String> unitList = new ArrayList<String>();
			
			if(customerVO.getDtSaStreet2() != null && customerVO.getDtSaStreet2().trim().length() > 0){
				completeAddress = completeAddress +", "+customerVO.getDtSaStreet2();
			}
			
			if(customerVO.getDtSaCity() != null && customerVO.getDtSaCity().trim().length() > 0){
				completeAddress = completeAddress +", "+customerVO.getDtSaCity();
				dtSaCity = customerVO.getDtSaCity();
			}

			if(customerVO.getDtSaState()!= null && customerVO.getDtSaState().trim().length() > 0){
				completeAddress = completeAddress +", "+customerVO.getDtSaState();
				dtSaState = customerVO.getDtSaState();
			}

			if(customerVO.getDtSaZip() != null && customerVO.getDtSaZip().trim().length() > 0){
				completeAddress = completeAddress +" "+customerVO.getDtSaZip();
				dtSaZip = customerVO.getDtSaZip();
			}
			if(!Utils.isBlank(customerVO.getDtSaStreet2())){
				unitList = getUnitTypeAndNumber(customerVO.getDtSaStreet2());
			}
			contextIndicator = salesCenterVo.getValueByName("salesFlow.contextId");
			if(Utils.isBlank(contextIndicator)){
				contextIndicator="NA";
			}
			
			Map<String, Map<String, String>> contextMap = (Map<String, Map<String, String>>)httpRequest.getSession().getAttribute("dynamicFlowContextMap");
			Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");
			dynamicFlow.put("dynamicFlow.page", "BasicInformation");
			
			Map<String, String> saleFlow =  new HashMap<String, String>();
			if(dynamicFlow.get("dynamicFlow.flowType").contains("peco")){
				saleFlow.put("salesFlow.forceNonConfirm", "true");
				salesCenterVo.setValueByName("salesFlow.forceNonConfirm" ,"true");
			}
			else if((contextIndicator.equals("00")) || (contextIndicator.equals("05"))){
				saleFlow.put("salesFlow.forceNonConfirm", "true");
				request.getFlowScope().put("salesFlow.forceNonConfirm", "true");
				salesCenterVo.setValueByName("salesFlow.forceNonConfirm" ,"true");
			}
			else{
				saleFlow.put("salesFlow.forceNonConfirm", "false");
				request.getFlowScope().put("salesFlow.forceNonConfirm", "false");
				salesCenterVo.setValueByName("salesFlow.forceNonConfirm" ,"false");
			}
			
			if(dynamicFlow.get("dynamicFlow.flowType").contains("peco")){
				saleFlow.put("salesFlow.forceNonConfirm", "true");
				request.getFlowScope().put("salesFlow.forceNonConfirm", "true");
				salesCenterVo.setValueByName("salesFlow.forceNonConfirm" ,"true");
			}
			if(dynamicFlow.get("dynamicFlow.flowType").contains("consumersInteractions")){
                salesCenterVo.setValueByName("flowTypeValue" ,"consumersInteractions");
            }
			if(salesCenterVo == null){
				salesCenterVo = new SalesCenterVO();
			}
			//SalesCenterVO salesCenterVo = new SalesCenterVO();
			String customerFullName = "";
			if(!Utils.isBlank(prefixNameChange(customerVO.getDtNamePrefix()))){
				customerFullName = prefixNameChange(customerVO.getDtNamePrefix());
			}
			if(!Utils.isBlank(customerVO.getDtNameFirst())){
				if(!Utils.isBlank(customerFullName)){
					customerFullName = customerFullName + " "+ customerVO.getDtNameFirst();
				}
				else{
					customerFullName = customerFullName + customerVO.getDtNameFirst();
				}
			}
			if(!Utils.isBlank(customerVO.getDtNameMiddle())){
				customerFullName = customerFullName + " "+ customerVO.getDtNameMiddle();
			}
			if(!Utils.isBlank(customerVO.getDtNameLast())){
				customerFullName = customerFullName + " "+ customerVO.getDtNameLast();
			}
			if(!Utils.isBlank(suffixNameChange(customerVO.getDtNameSuffix()))){
				customerFullName = customerFullName + " "+ suffixNameChange(customerVO.getDtNameSuffix());
			}
			salesCenterVo.setValueByName("consumer.name.nameBlock", customerFullName);
			salesCenterVo.setValueByName("consumer.name.prefix", prefixNameChange(customerVO.getDtNamePrefix()));
			salesCenterVo.setValueByName("consumer.name.suffix",suffixNameChange(customerVO.getDtNameSuffix()));
			salesCenterVo.setValueByName("consumer.name.first", customerVO.getDtNameFirst());
			salesCenterVo.setValueByName("consumer.name.last", customerVO.getDtNameLast());
			salesCenterVo.setValueByName("Customer Name", customerVO.getDtNameFirst() +" " +customerVO.getDtNameLast());
			if (!Utils.isBlank(customerVO.getDtNameMiddle())){
				salesCenterVo.setValueByName("consumer.name.middle", customerVO.getDtNameMiddle().substring(0, 1));	
			}else{
				salesCenterVo.setValueByName("consumer.name.middle", customerVO.getDtNameMiddle());
			}
			salesCenterVo.setValueByName("customer.name.first", customerVO.getDtNameFirst());
			salesCenterVo.setValueByName("customer.name.last", customerVO.getDtNameLast());
			salesCenterVo.setValueByName("consumer.email", customerVO.getDtConfirmedEmailAddress());
			if(!Utils.isBlank(salesCenterVo.getValueByName("agent.id"))){
				salesCenterVo.setValueByName("Agent", salesCenterVo.getValueByName("agent.id"));
			}
			else{
				salesCenterVo.setValueByName("Agent", customerVO.getDtAgentId());
			}
			salesCenterVo.setValueByName("dwelling.addressBlock", completeAddress);
			salesCenterVo.setValueByName("address.street1", dtSaStreet1);
			salesCenterVo.setValueByName("address.street2", customerVO.getDtSaStreet2());
			salesCenterVo.setValueByName("address.city", dtSaCity);
			salesCenterVo.setValueByName("address.state", dtSaState);
			salesCenterVo.setValueByName("address.zip", dtSaZip);
			salesCenterVo.setValueByName("customer.telePhoneNum", customerVO.getDtTelephoneNum());
			salesCenterVo.setValueByName("customer.confirmation.number", customerVO.getDtPartnerAccountId());
			salesCenterVo.setValueByName("gasRequestedStartDate", customerVO.getDtGasRequestedStartDate());
			salesCenterVo.setValueByName("requestedStartDate", customerVO.getDtRequestedStartDate());
			salesCenterVo.setValueByName("dtAgentId", customerVO.getDtAgentId());
			
			if(!Utils.isBlank(customerVO.getDtRequestedStartDate()))
			{
				salesCenterVo.setValueByName(Constants.ELECTRIC_START_DATE,customerVO.getDtRequestedStartDate());
			}
			else
			{
				salesCenterVo.setValueByName(Constants.ELECTRIC_START_DATE,BLANK);
			}
			
			if(!Utils.isBlank(customerVO.getDtGasRequestedStartDate()))
			{
				salesCenterVo.setValueByName(Constants.GAS_START_DATE,customerVO.getDtGasRequestedStartDate());
			}
			else
			{
				salesCenterVo.setValueByName(Constants.GAS_START_DATE,BLANK);
			}
			
			if(!Utils.isBlank(customerVO.getDtGasReqStartTimeBegin()) )
			{
				salesCenterVo.setValueByName(Constants.GAS_START_TIME, customerVO.getDtGasReqStartTimeBegin());
			}
			else
			{
				salesCenterVo.setValueByName(Constants.GAS_START_TIME,BLANK);
			}
			
			if(!Utils.isBlank(customerVO.getDtGasReqStartTimeEnd()) )
			{
				salesCenterVo.setValueByName(Constants.GAS_END_TIME, customerVO.getDtGasReqStartTimeEnd());
			}
			else
			{
				salesCenterVo.setValueByName(Constants.GAS_END_TIME,BLANK);
			}
			
			
			if(!Utils.isBlank(customerVO.getDtReqStartTimeBegin()))
			{
				salesCenterVo.setValueByName(Constants.ELECTRIC_START_TIME, customerVO.getDtReqStartTimeBegin());
			}
			else
			{
				salesCenterVo.setValueByName(Constants.ELECTRIC_START_TIME,BLANK);
			}
			
			if(!Utils.isBlank(customerVO.getDtReqStartTimeEnd()))
			{
				salesCenterVo.setValueByName(Constants.ELECTRIC_END_TIME, customerVO.getDtReqStartTimeEnd());
			}
			else
			{
				salesCenterVo.setValueByName(Constants.ELECTRIC_END_TIME,BLANK);
			}
			
			if (completeAddress != null){
				salesCenterVo.setValueByName("consumer.dwelling.addressBlock", completeAddress);
				salesCenterVo.setValueByName("dwelling.addressBlock", completeAddress);
			}
			else{
				salesCenterVo.setValueByName("consumer.dwelling.addressBlock", "");
				salesCenterVo.setValueByName("dwelling.addressBlock", "");
			}
			if(!Utils.isBlank(customerVO.getDtSaStreet2())){
				salesCenterVo.setValueByName("unit.type", unitList.get(0));
				salesCenterVo.setValueByName("unit.number", unitList.get(1));
			}
			else{
				salesCenterVo.setValueByName("unit.type", "");
				salesCenterVo.setValueByName("unit.number", "");
			}
			salesCenterVo.setValueByName("dtCreated", customerVO.getDtCreated());
			Long orderId = (Long)httpRequest.getSession().getAttribute("orderId");
			//salesCenterVo.setValueByName("comcast.order.number", String.valueOf(orderId));
			
			salesCenterVo.setValueByName("electricService.startDate", customerVO.getDtRequestedStartDate());
			httpRequest.getSession().setAttribute("salescontext", salesCenterVo);
			httpRequest.getSession().setAttribute("cycletime", CYCLETIME);
			httpRequest.getSession().setAttribute("partnerSpecificDataMap", customerVO.getPartnerSpecificDataMap());
			logger.info("AgentId="+ salesCenterVo.getValueByName("Agent"));
			/*Collection<Map<String, List<DataField>>> coll = dialogueVO.getDataFieldMap().values();

		Iterator<Map<String,List<DataField>>> it = coll.iterator();
		while(it.hasNext()){
			Map<String,List<DataField>> map = (Map<String,List<DataField>>)it.next();
			Collectionmap.values();
		}*/
			String flow = (String) httpRequest.getSession().getAttribute("referrerFlowAgentGroup");
			if (!Utils.isBlank(flow)) {
				if (flow.toLowerCase().contains("mdu") && httpRequest.getParameter("mduProJSON") != null) {
					String mduPropertyData = httpRequest.getParameter("mduProJSON");
					logger.info("mduPropertyData="+mduPropertyData);
					MDUProperty mduProperty = new Gson().fromJson(mduPropertyData, new TypeToken<MDUProperty>(){}.getType());
					salesCenterVo.setValueByName("orderSourceExternalId", mduProperty.getOrdersourceExternalId());
				}
			}

			String customerSelectId = httpRequest.getParameter("customerLookupSelectId");
			logger.info("customerSelectId=" + customerSelectId);
			if(!Utils.isBlank(customerSelectId)) {
				String customerLookupEmail = "";
				String customerLookupPhone = "";
				String customerLookupAddress2 = "";
			Map<String,CustomerLookupObject> customerLookupMapSession = (Map<String,CustomerLookupObject>) httpRequest.getSession().getAttribute("customerLookupMapSession");
			CustomerLookupObject customerLookupObject = customerLookupMapSession.get(customerSelectId);
			for(CustomerLookupItem customerLookupItem : customerLookupObject.getLineItems()) {
				salesCenterVo.setValueByName("consumer.name.first", customerLookupItem.getFirstName());
				salesCenterVo.setValueByName("consumer.name.last", customerLookupItem.getLastName());
				salesCenterVo.setValueByName("Customer Name", customerLookupItem.getFirstName() +" " +customerLookupItem.getLastName());
				salesCenterVo.setValueByName("consumer.name.middle", customerLookupItem.getMiddleName());
				
				String lookupCustomerFullName = "";
				if(!Utils.isBlank(customerLookupItem.getFirstName())){
					lookupCustomerFullName = lookupCustomerFullName + customerLookupItem.getFirstName();
				}
				if(!Utils.isBlank(customerLookupItem.getMiddleName())){
					lookupCustomerFullName = lookupCustomerFullName + " "+ customerLookupItem.getMiddleName();
				}
				if(!Utils.isBlank(customerLookupItem.getLastName())){
					lookupCustomerFullName = lookupCustomerFullName + " "+ customerLookupItem.getLastName();
				}
				salesCenterVo.setValueByName("consumer.name.nameBlock", lookupCustomerFullName);
				salesCenterVo.setValueByName("address.street1", customerLookupItem.getAddress1());
				salesCenterVo.setValueByName("address.street2", customerLookupItem.getAddress2());
				customerLookupAddress2 = customerLookupItem.getAddress2();
				salesCenterVo.setValueByName("address.city", customerLookupItem.getCity());
				salesCenterVo.setValueByName("address.state", customerLookupItem.getState().toUpperCase());
				salesCenterVo.setValueByName("address.zip", customerLookupItem.getZip());
				salesCenterVo.setValueByName("customer.telePhoneNum", customerLookupItem.getPhone());
				customerLookupPhone = customerLookupItem.getPhone();
				salesCenterVo.setValueByName("customer.confirmation.number", customerLookupItem.getPartnerId());
				salesCenterVo.setValueByName("customer.bestEmailContact", customerLookupItem.getEmail());
				customerLookupEmail = customerLookupItem.getEmail();
				salesCenterVo.setValueByName("customer.contractAccountNumber", customerLookupItem.getContractAccountNumber());				
				if( !Utils.isBlank(customerLookupItem.getOperatingCompany()) )
				{
					salesCenterVo.setValueByName("firstenergy.operatingCompanyCode", customerLookupItem.getOperatingCompany());
					String operatingCompanyName = operatingCompanyDao.getOperatingCompanyName(customerLookupItem.getOperatingCompany());
					if(!Utils.isBlank(operatingCompanyName)){
						salesCenterVo.setValueByName("firstenergy.operatingCompanyName",operatingCompanyName);
					}
					logger.info("operatingCompanyCode=" + customerLookupItem.getOperatingCompany() +" operatingCompanyName="+operatingCompanyName);
				}
				salesCenterVo.setValueByName("customer.providerData1", customerLookupItem.getProviderData1());
				salesCenterVo.setValueByName("customer.providerData2", customerLookupItem.getProviderData2());
				salesCenterVo.setValueByName("customer.providerData3", customerLookupItem.getProviderData3());
				salesCenterVo.setValueByName("customer.providerData4", customerLookupItem.getProviderData4());
				salesCenterVo.setValueByName("customer.providerData5", customerLookupItem.getProviderData5());
				if(!Utils.isBlank(customerLookupItem.getCampaignId())){
				salesCenterVo.setValueByName("campaignId", customerLookupItem.getCampaignId());
				}
			}
				if(!Utils.isBlank(customerLookupEmail)) {
					order.getCustomerInformation().getCustomer().setBestEmailContact(customerLookupEmail);
				}
				if(!Utils.isBlank(customerLookupPhone)) {
					order.getCustomerInformation().getCustomer().setBestPhoneContact(customerLookupPhone);
				}
				if(!Utils.isBlank(customerLookupAddress2)) {
					List<String> unitListLookup = new ArrayList<String>();
					unitListLookup = getUnitTypeAndNumber(customerLookupAddress2);
				salesCenterVo.setValueByName("unit.type", unitListLookup.get(0));
				salesCenterVo.setValueByName("unit.number", unitListLookup.get(1));
				}
				salesCenterVo.setValueByName("salesFlow.forceNonConfirm" ,"false");
			}
			if(!Utils.isBlank(sameCall) && sameCall.equals("true")){
				if(salesCenterVo.getValueByName("vdn")!=null && salesCenterVo.getValueByName("vdn").equals((String)session.getAttribute("vdn"))&&
						salesCenterVo.getValueByName("referrer.businessParty.referrerName")!= null && 
						salesCenterVo.getValueByName("referrer.businessParty.referrerName").equals((String)session.getAttribute("referrerName"))){
					String bestPhone = (String)session.getAttribute("bestPhoneContactValue");
					salesCenterVo.setValueByName("consumer.name.first", (String)session.getAttribute("firstName"));
					salesCenterVo.setValueByName("consumer.name.last", (String)session.getAttribute("lastName"));
					salesCenterVo.setValueByName("consumer.name.middle", (String)session.getAttribute("middleName"));
					salesCenterVo.setValueByName("consumer.name.prefix", prefixNameChange((String)session.getAttribute("prefix")));
					salesCenterVo.setValueByName("consumer.name.suffix",suffixNameChange((String)session.getAttribute("suffix")));
					salesCenterVo.setValueByName("address.street1", (String)session.getAttribute("address1"));
					salesCenterVo.setValueByName("address.city", (String)session.getAttribute("city"));
					salesCenterVo.setValueByName("address.state", (String)session.getAttribute("state"));
					salesCenterVo.setValueByName("address.zip", (String)session.getAttribute("zip"));
					if(!Utils.isBlank((String)session.getAttribute("unitType"))) {
						salesCenterVo.setValueByName("unit.type", (String)session.getAttribute("unitType"));
					}
					if(!Utils.isBlank((String)session.getAttribute("unitNumber"))) {
						salesCenterVo.setValueByName("unit.number", (String)session.getAttribute("unitNumber"));
					}
					salesCenterVo.setValueByName("rentOwnVal", (String)session.getAttribute("rentOwnVal"));
					salesCenterVo.setValueByName("dwellingType", (String)session.getAttribute("dwellingType"));
					salesCenterVo.setValueByName("moveInDate", "");
					if(!Utils.isBlank((String)session.getAttribute("isMoveInValue"))){
						salesCenterVo.setValueByName("isMoveInValue", (String)session.getAttribute("isMoveInValue"));
					}
					if(!Utils.isBlank((String)session.getAttribute("moveInDateValue"))){
						salesCenterVo.setValueByName("moveInDate", (String)session.getAttribute("moveInDateValue"));
					}
					if(!Utils.isBlank((String)session.getAttribute("bestEmailContactValue"))){
						order.getCustomerInformation().getCustomer().setBestEmailContact((String)session.getAttribute("bestEmailContactValue"));
						salesCenterVo.setValueByName("customer.bestEmailContact", (String)session.getAttribute("bestEmailContactValue"));
					}
					if(!Utils.isBlank(bestPhone) && bestPhone.length() >= 10){
						bestPhone = bestPhone.trim();
						bestPhone = bestPhone.substring(0,3)+"-"+bestPhone.substring(3,6)+"-"+bestPhone.substring(6);
						order.getCustomerInformation().getCustomer().setBestPhoneContact((String)session.getAttribute("bestPhoneContactValue"));
						//Need to format phoneNumber
						salesCenterVo.setValueByName("customer.telePhoneNum", bestPhone);
					}
				}
			}
			startTimer=timer.getTime();
			contextMap.put("salesFlow", saleFlow);
			if (salesCenterVo.getValueByName("drupalContentUrl") != null 
					&& salesCenterVo.getValueByName("dispDrupalDailgVal") != null ){
				boolean isFuseBasicInfo = false;
				String fuseBasicInfoDialogues = "";
				if(dynamicFlow != null && dynamicFlow.get("dynamicFlow.flowType") != null && (dynamicFlow.get("dynamicFlow.flowType").contains("webMicro") || dynamicFlow.get("dynamicFlow.flowType").contains("webCCP") 
						|| dynamicFlow.get("dynamicFlow.flowType").contains("webReferrer"))){
					JSONObject obj = new JSONObject();
					String customerAddress=null;
					if(httpRequest.getSession().getAttribute("fuseResponseData") != null){
						JSONObject responseHeaderJson = (JSONObject)httpRequest.getSession().getAttribute("fuseResponseData");
						Iterator jsonKeys = responseHeaderJson.sortedKeys();
						if(responseHeaderJson != null){
						     while(jsonKeys.hasNext()){
								 String jsonkey = (String)jsonKeys.next();
								 if(!Utils.isBlank(jsonkey) && jsonkey.equalsIgnoreCase("metadata")){
									 JSONObject metadata = (JSONObject)responseHeaderJson.get("metadata");
										if(metadata != null && metadata.length() != 0 && Utils.isBlank(dtSaZip)){
										      Iterator keys = metadata.sortedKeys();
										       while(keys.hasNext()){
										    	   String key = (String)keys.next();
										    	   if(key.equalsIgnoreCase("customerAddress")){
										    		   customerAddress = metadata.getString(key);
										    		   String customerAddr = metadata.getString(key);
										    		   String addrData[] = customerAddr.split(",");
														if(addrData[1] != null && !addrData[1].trim().equalsIgnoreCase("")){
															List <String> unitType=getUnitTypeAndNumber(addrData[1]);
															obj.put("unitType", unitType.get(0));
															obj.put("unitNumber", unitType.get(1));
														}
														obj.put("street", addrData[0].trim());
														salesCenterVo.setValueByName("fuse.customerAddress", customerAddress); 
														obj.put("zipCode", addrData[2].trim());
														httpRequest.getSession().setAttribute("customerAddress", obj);
														isFuseBasicInfo = true;
										    	   }
										       }
										    logger.info("isFuseBasicInfoPage="+isFuseBasicInfo);
											fuseBasicInfoDialogues = SalesUtil.getDrupalDialoguesForFuseBasicInformation(salesCenterVo,isFuseBasicInfo);
											if(!Utils.isBlank(fuseBasicInfoDialogues)){
													request.getFlashScope().put("referrerFlow", (String) httpRequest.getSession().getAttribute("referrerFlowAgentGroup"));	
													request.getFlowScope().put("dialogue" , fuseBasicInfoDialogues);	
											}
										}
								 	}
								}
							}
						logger.info("Customer address from fuse API call="+customerAddress);
					}
				}
				if(Utils.isBlank(fuseBasicInfoDialogues)){
					String dialoguesFromDrupal = SalesUtil.getDialoguesFormDrupalContent(contextMap,salesCenterVo);
					if (Utils.isBlank(dialoguesFromDrupal)){
						generateDialoguesFromService(contextMap,salesCenterVo,dynamicFlow,request);	
					}else{
						request.getFlashScope().put("referrerFlow", (String) httpRequest.getSession().getAttribute("referrerFlowAgentGroup"));	
						request.getFlowScope().put("dialogue" , dialoguesFromDrupal);	
					}
				}
			}else{
				generateDialoguesFromService(contextMap,salesCenterVo,dynamicFlow,request);
			}
			contextMap.remove("salesFlow");
			request.getFlowScope().put("states", webLookupStates.getListAsJSON());
			request.getFlowScope().put("namePrefix", webLookupNamePrefix.getListAsJSON());
			request.getFlowScope().put("nameSuffix", webLookupNameSuffix.getListAsJSON());
			request.getFlowScope().put("unitType", webLookupUnitType.getListAsJSON());
			request.getFlowScope().put("rentOwn", webLookupRentOwn.getListAsJSON());
			request.getFlowScope().put("serviceAddressType", webLookupServiceAddressType.getListAsJSON());
			//request.getFlowScope().put("salescenterVo", salesCenterVo);
			request.getFlowScope().put("customerVO", customerVO);
			String contextIndicatorId = "00";
			
			if(!Utils.isBlank(salesCenterVo.getValueByName("salesFlow.contextId"))) {
				if(!(salesCenterVo.getValueByName("salesFlow.contextId").equals("05") || salesCenterVo.getValueByName("salesFlow.contextId").equals("00"))) {
					contextIndicatorId = salesCenterVo.getValueByName("salesFlow.contextId");
				}
			}
			request.getFlowScope().put("rrContextId", contextIndicatorId);
			if (Utils.isBlank(customerVO.getDtRequestedStartDate())){
				request.getFlowScope().put("HideElectricDate", "true");	
			}
			else{
				request.getFlowScope().put("HideElectricDate", "false");	
			}
			if (Utils.isBlank(customerVO.getDtGasRequestedStartDate())){
				request.getFlowScope().put("HideGasDate", "true");	
			}else{
				request.getFlowScope().put("HideGasDate", "false");	
			}
			if (httpRequest.getSession().getAttribute("thoughtSpotrank") != null){
				request.getFlowScope().put("thoughtSpotrank",(Boolean) httpRequest.getSession().getAttribute("thoughtSpotrank"));
			}
			if (httpRequest.getSession().getAttribute("TSCacheDateTime") != null){
				request.getFlowScope().put("TSCacheDateTime", httpRequest.getSession().getAttribute("TSCacheDateTime"));
			}
			if (httpRequest.getSession().getAttribute("thoughtSpotData") != null){
				request.getFlowScope().put("thoughtSpotData",(String) httpRequest.getSession().getAttribute("thoughtSpotData"));
			}
			
			logger.info("timeTakenForShowBasicInformation="+timer.getTime());
			logger.info("showBasicInfo_end");
			//return mav;
			return new Event(this, "basicViewEvent");
		}catch (Exception e) {
			request.getFlowScope().put("message", e.getMessage());
			request.getFlowScope().put("pageTitle",httpRequest.getParameter("pageTitle")!=null?httpRequest.getParameter("pageTitle"):"");
			logger.error(e);
			throw new UnRecoverableException(e.getMessage());
		}finally{
			timer.stop();
        }
	}

	private List<String> getUnitTypeAndNumber(String street2){
		String unitType = "";
		String unitNumber = "";
		List<String> unitList = new ArrayList<String>();
		if(!Utils.isBlank(street2)){
			if(street2.contains(" ")){
				int indexPos = street2.indexOf(" ");
				unitType = street2.substring(0, indexPos);
				unitNumber = street2.substring((indexPos+1), street2.length());
				unitType = prePopulateUnitType(unitType);
				unitList.add(0, unitType);
				unitList.add(1, unitNumber);
			}
			else{
				unitType = street2;
				unitType = prePopulateUnitType(unitType);
				unitList.add(0, unitType);
				unitList.add(1, unitNumber);
			}
		}
		return unitList;

	}
	

	private String prefixNameChange(String givenSting) throws Exception {
		String initial="";
		if (!Utils.isBlank(givenSting))
		{
			if(givenSting.equalsIgnoreCase("Ms") || givenSting.equalsIgnoreCase("Ms.")){
				initial="Ms";
			}
			if(givenSting.equalsIgnoreCase("Mrs") || givenSting.equalsIgnoreCase("Mrs.")){
				initial="Mrs";
			}
			if(givenSting.equalsIgnoreCase("mr") || givenSting.equalsIgnoreCase("mr.")){
				initial="Mr";
			}
			if(givenSting.equalsIgnoreCase("Miss") || givenSting.equalsIgnoreCase("Miss.")){
				initial="Miss"; 
			}
			if(givenSting.equalsIgnoreCase("Master") || givenSting.equalsIgnoreCase("Master.")){
				initial="Master";
			}
			if(givenSting.equalsIgnoreCase("Rev") || givenSting.equalsIgnoreCase("Rev.")){
				initial="Rev";
			}
			if(givenSting.equalsIgnoreCase("Fr") || givenSting.equalsIgnoreCase("Fr.")){
				initial="Fr";
			}
			if(givenSting.equalsIgnoreCase("Dr") || givenSting.equalsIgnoreCase("Dr.")){
				initial="Dr";
			}
			if(givenSting.equalsIgnoreCase("Atty") || givenSting.equalsIgnoreCase("Atty.")){
				initial="Atty";
			}
			if(givenSting.equalsIgnoreCase("Prof") || givenSting.equalsIgnoreCase("Prof.")){
				initial="Prof";
			}
			if(givenSting.equalsIgnoreCase("Hon") || givenSting.equalsIgnoreCase("Hon.")){
				initial="Hon";
			}
			if(givenSting.equalsIgnoreCase("Pres") || givenSting.equalsIgnoreCase("Pres.")){
				initial="Pres";
			}
			if(givenSting.equalsIgnoreCase("Gov") || givenSting.equalsIgnoreCase("Gov.")){
				initial="Gov";
			}
			if(givenSting.equalsIgnoreCase("Coach") || givenSting.equalsIgnoreCase("Coach.")){
				initial="Coach";
			}
			if(givenSting.equalsIgnoreCase("Ofc") || givenSting.equalsIgnoreCase("Ofc.")){
				initial="Ofc";
			}
		}
		return initial;
	}


	private String suffixNameChange(String givenSting)  throws Exception {
		String initial="";
		if (!Utils.isBlank(givenSting))
		{
			if(givenSting.equalsIgnoreCase("Jr") || givenSting.equalsIgnoreCase("Jr.")){
				initial="Jr";
			}
			if(givenSting.equalsIgnoreCase("II")){
				initial="II";
			}
			if(givenSting.equalsIgnoreCase("III")){
				initial="III";
			}
			if(givenSting.equalsIgnoreCase("IV")){
				initial="IV";
			}
			if(givenSting.equalsIgnoreCase("V")){
				initial="V";
			}
			if(givenSting.equalsIgnoreCase("VI")){
				initial="VI";
			}
			if( givenSting.equalsIgnoreCase("Sr") || givenSting.equalsIgnoreCase("Sr.") ){
				initial="Sr";
			}
		}
		return initial;
	}
	
	private String prePopulateUnitType(String unitType) {
		if(unitType.toUpperCase().contains("APT")){
			unitType = "Apartment";
		}
		else if(unitType.toUpperCase().contains("BLDG")){
			unitType = "Building";
		}
		else if(unitType.toUpperCase().contains("DEPT")){
			unitType = "Department";
		}
		else if(unitType.toUpperCase().contains("FL")){
			unitType = "Floor";
		}
		else if(unitType.toUpperCase().contains("HNGR")){
			unitType = "Hanger";
		}
		else if(unitType.toUpperCase().contains("LOT")){
			unitType = "Lot";
		}
		else if(unitType.toUpperCase().contains("OFC")){
			unitType = "Office";
		}
		else if(unitType.toUpperCase().contains("SPC")){
			unitType = "Space";
		}
		else if(unitType.toUpperCase().contains("STE")){
			unitType = "Suite";
		}
		else if(unitType.toUpperCase().contains("TRLR")){
			unitType = "Trailer";
		}
		else if(unitType.toUpperCase().contains("UNIT")){
			unitType = "Unit";
		}
		return unitType;
	}
	
	public String escapeSpecialCharacters(String str){
		if(str!=null){
			str = str.replaceAll("&amp;", "&");
			str = str.replaceAll("'", "&#39;");
			str = str.replaceAll("&quot;", "&#34;");
			
			str = str.replaceAll("&#10;", "&nbsp;");
			str = str.replaceAll("\u00a0", "&nbsp;");
			//this is for - mark
			str = str.replaceAll("\u2013", "&#8211;");
			//this is for trademark
			str = str.replaceAll("\u2122", "&#8482;");
			
			//this is for Copyright mark
			str = str.replaceAll("\u00a9", "&#169;");
			//this is for Registered trade mark
			str = str.replaceAll("\u00ae", "&#174;");
			
			//this is for bullet point
			str = str.replaceAll("\u2022", "&#8226;");
			//this is for exclamation point
			str = str.replaceAll("\u0021", "&#33;");
			//this is for colon
			str = str.replaceAll("\u003a", "&#58;");
			//this is for inverted question mark
			str = str.replaceAll("\u00bf", "&#191;");
			
			//this is for right single quotation mark
			str = str.replaceAll("\u2019", "&#8217;");
			//this is for left single quotation mark
			str = str.replaceAll("\u2018", "&#8216;");
			//this is for left double quotation mark
			str = str.replaceAll("\u201C", "&#8220;");
			//this is for right double quotation mark
			str = str.replaceAll("\u201D", "&#8221;");
			
			//this is for left tag
			str = str.replaceAll("&lt;", "<");
			
			//this is for right tag
			str = str.replaceAll("&gt;", ">");
		}
		return str;
	}
	
	private void generateDialoguesFromService(Map<String, Map<String, String>> contextMap, 
			SalesCenterVO salesCenterVo, Map<String, String> dynamicFlow, RequestContext request) throws UnRecoverableException {
		try {
			SalesDialogueVO dialogueVO = DialogServiceUI.INSTANCE.getDialoguesByContext(contextMap);
			List<Fieldset> fieldsetList = null;
			StringBuilder events = new StringBuilder();
			List<DataGroup> dgList = null;

			List<DataField> dataFields = new ArrayList<DataField>();

			for (Dialogue dialogue : dialogueVO.getDialogueList()){

				logger.debug("dialogue_external_id"+dialogue.getExternalId());
				dgList = dialogue.getDataGroupList();
				for(DataGroup dGroup : dgList){
					for(DataField data : dGroup.getDataFieldList()){
						dataFields.add(data);
					}
				}
			}        

			logger.debug("dataFields_size"+dataFields.size());

			fieldsetList = HtmlFactory.INSTANCE.dialogueToFieldSet(events, dataFields, null);

			logger.debug("events="+events);

			for (Fieldset fieldset : fieldsetList) {

				String element = HtmlBuilder.INSTANCE.toString(fieldset);
				element = dynamicFlow.get("dynamicFlow.flowType").contains("peco") ?element:salesCenterVo.replaceNamesWithValues(element);
				events.append(element);
			}		
			request.getFlowScope().put("dialogue" , escapeSpecialCharacters(events.toString()));
		} catch (BaseException e) {
			logger.error("Exception_in_BasicInformation_getDialogues",e);
			throw new UnRecoverableException(e.getMessage());
		}
	}
}