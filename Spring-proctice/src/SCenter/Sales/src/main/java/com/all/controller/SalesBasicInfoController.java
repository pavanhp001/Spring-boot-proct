package com.AL.controller;

import static com.AL.ui.util.ConfigProperties.CYCLETIME;
import static com.AL.ui.util.ConfigProperties.DATAEXCHANGEURL;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.AL.html.Fieldset;
import com.AL.productResults.managers.ProductResultsManager;
import com.AL.productResults.managers.SearchResults;
import com.AL.productResults.vo.ProductSearchVO;
import com.AL.productResults.vo.ProductSummaryVO;
import com.AL.ui.builder.HtmlBuilder;
import com.AL.ui.constants.Constants;
import com.AL.ui.dao.ConfirmReferrersDao;
import com.AL.ui.dao.FrontierPricingGridConfigDao;
import com.AL.ui.dao.GrossCommissionableRevenueDao;
import com.AL.ui.dao.HughesNetServedUpDataDao;
import com.AL.ui.dao.QualificationPopUpRefDetailsDao;
import com.AL.ui.dao.QualificationPopUpZipCodesDao;
import com.AL.ui.dao.WebLookupDao;
import com.AL.ui.domain.WebLookupCollection;
import com.AL.ui.domain.dynamic.dialogue.DataField;
import com.AL.ui.domain.dynamic.dialogue.DataGroup;
import com.AL.ui.domain.dynamic.dialogue.Dialogue;
import com.AL.ui.factory.CartLineItemFactory;
import com.AL.ui.factory.SalesDialogueFactory;
import com.AL.ui.factory.SalesUtilsFactory;
import com.AL.ui.factory.WarmTransferFactory;
import com.AL.ui.service.config.ConfigRepo;
import com.AL.ui.service.V.CustomerService;
import com.AL.ui.service.V.DialogServiceUI;
import com.AL.ui.service.V.ESEService;
import com.AL.ui.service.V.LineItemService;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.util.HtmlFactory;
import com.AL.ui.util.LineItemUtil;
import com.AL.ui.util.SalesUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.CartError;
import com.AL.ui.vo.ConfirmReferrersVO;
import com.AL.ui.vo.ConsumerVO;
import com.AL.ui.vo.CustomerVO;
import com.AL.ui.vo.ErrorList;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.ui.vo.SalesDialogueVO;
import com.AL.util.DateUtil;
import com.AL.V.domain.SalesContext;
import com.AL.V.exception.UnRecoverableException;
import com.AL.V.factory.CustomerFactory;
import com.AL.V.factory.OrderFactory;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.cm.v4.AddressType;
import com.AL.xml.cm.v4.CustAddress;
import com.AL.xml.cm.v4.CustomerContextType;
import com.AL.xml.cm.v4.CustomerType;
import com.AL.xml.cm.v4.DwellingType;
import com.AL.xml.cm.v4.EMailAddressType;
import com.AL.xml.cm.v4.NotificationEventCollectionType;
import com.AL.xml.cm.v4.NotificationEventType;
import com.AL.xml.cm.v4.ObjectFactory;
import com.AL.xml.cm.v4.OwnershipType;
import com.AL.xml.cm.v4.RoleType;
import com.AL.xml.cm.v4.CustAddress.AddressRoles;
import com.AL.xml.se.v4.ProviderCriteriaEntityType2;
import com.AL.xml.se.v4.ProviderCriteriaType2;
import com.AL.xml.se.v4.ProviderNameValuePairType2;
import com.AL.xml.se.v4.ServiceabilityEnterpriseResponse;
import com.AL.xml.se.v4.ServiceabilityResponse2;
import com.AL.xml.v4.AttributeDetailType;
import com.AL.xml.v4.AttributeEntityType;
import com.AL.xml.v4.LineItemAttributeType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemDetailType;
import com.AL.xml.v4.LineItemPriceInfoType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.LinkableType;
import com.AL.xml.v4.NameValuePairType;
import com.AL.xml.v4.OrderLineItemDetailTypeType;
import com.AL.xml.v4.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.ProviderSourceType;
import com.AL.xml.v4.ProviderType;
import com.AL.xml.v4.SalesContextEntityType;
import com.AL.xml.v4.SalesContextType;
import com.AL.xml.v4.ServiceType;


@Controller
public class SalesBasicInfoController extends BaseController{

	private static final Logger logger = Logger.getLogger(SalesBasicInfoController.class);

	private static ExecutorService pool;
	
	private final static String BLANK = "";

	//private static final ExecutorService pool = Executors.newFixedThreadPool(ConfigRepo.getInt("thread_pool_size"));

	SimpleDateFormat format1 = new SimpleDateFormat("MMddyyyy");
	SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy");
	
	@Autowired
	private WebLookupDao lookupDao;
	
	@Autowired
	private GrossCommissionableRevenueDao grossCommissionableRevenueDao;
	
	@Autowired
	private QualificationPopUpRefDetailsDao qualificationPopUpRefDetailsDao;

	@Autowired
	private ConfirmReferrersDao confirmReferrersDao;
	
	@Autowired
	private HughesNetServedUpDataDao hughesNetServedUpDataDao;
	
	@Autowired
	private FrontierPricingGridConfigDao frontierPricingGridConfigDao;

	
	@RequestMapping(value="/basicInformation")
	protected ModelAndView showBasicInfo(HttpServletRequest request,
			HttpServletResponse response) throws UnRecoverableException {
		try{
		logger.info("showBasicInfo_begin");
		request.getSession().setAttribute("hasProducts",false);
		String customerId = request.getParameter("customerId");
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
		StringBuilder referrerCallType = new StringBuilder("");
		boolean dtCustomerSelected = true;
		CustomerVO customerVO = new CustomerVO();
		SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
		try{
			if (!Utils.isBlank(customerId)){
				Map<String,CustomerVO> m = RESTClient.INSTANCE.getCustomersdetailsById(customerId, DATAEXCHANGEURL, request);
				for (Entry<String,CustomerVO> entry : m.entrySet()){
					customerVO = entry.getValue();
				}
				OrderType order = (OrderType) request.getSession().getAttribute("order");
				if(customerVO != null ){
					if(!Utils.isBlank(customerVO.getDtConfirmedEmailAddress())){
					    order.getCustomerInformation().getCustomer().setBestEmailContact(customerVO.getDtConfirmedEmailAddress());
					    salesCenterVo.setValueByName("customer.bestEmailContact", customerVO.getDtConfirmedEmailAddress());
					}
					else if(!Utils.isBlank(customerVO.getDtEmail())){
						order.getCustomerInformation().getCustomer().setBestEmailContact(customerVO.getDtEmail());
						salesCenterVo.setValueByName("customer.bestEmailContact", customerVO.getDtEmail());
					}
					logger.info("Customer_email_is"+customerVO.getDtEmail());
				}
			}
			
			//handling code for DT harness
			if(Utils.isBlank(customerVO.getDtSequenceNum())) {
				logger.info("When_customerVO_is_null");
				//customer is not in DB; probably DT xml data is injected; 
				//so display customer details based on DT XML
				ConsumerVO dtConsumer = (ConsumerVO)request.getSession().getAttribute("salescontextDt");
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
				logger.info("dtConsumer="+dtConsumer);
				if(dtConsumer!=null) {
					//this is only for DT harness injection of DT xml data
					logger.info("when_xml_input_is_not_null");
					if(dtConsumer.getDtSequenceNum() == null){
						dtCustomerSelected = false;
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
		logger.info("customerVOIs=" +customerVO);
		logger.info("dtEmail=" +customerVO.getDtEmail());
		logger.info("utilityOrderId="+customerVO.getDtPartnerAccountId());
		request.getSession().setAttribute("DtEmail", customerVO.getDtEmail());
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
		SalesDialogueVO dialogueVO = new SalesDialogueVO();
		
		String callType = salesCenterVo.getValueByName("referrer.callType");
		String flow = salesCenterVo.getValueByName("referrer.flow");
		String dialogueType = null;
		String forceNonConfirm = null;
		if(flow.equalsIgnoreCase("confirm") && callType.equalsIgnoreCase("default")){
			dialogueType = "BasicInformation";
			if(! dtCustomerSelected){
				forceNonConfirm = "true"; 
			}
		}
		else if(flow.equalsIgnoreCase("nonConfirm") && callType.equalsIgnoreCase("default")){
			if(dtCustomerSelected){
			    dialogueType = "BasicInfoNonConfirmData";
			}
			else{
				dialogueType = "BasicInfoNonConfirmNoData";
			}
		}
		else if(flow.equalsIgnoreCase("confirm") && callType.equalsIgnoreCase("referrerSpecific")){
			dialogueType = "BasicInformation";
			if(! dtCustomerSelected){
				forceNonConfirm = "true"; 
			}
		}
		else if(flow.equalsIgnoreCase("nonConfirm") && callType.equalsIgnoreCase("referrerSpecific")){
			if(dtCustomerSelected){
			    dialogueType = "BasicInfoNonConfirmData";
			}
			else{
				dialogueType = "BasicInfoNonConfirmNoData";
			}
		}
		else if(flow.equalsIgnoreCase("agentTransfer")){
			dialogueType = "BasicInfoAgentTransfer";
		}
		
		Map<String, Map<String, String>> contextMap = new HashMap<String, Map<String, String>>();
		Map<String, String> saleFlow =  new HashMap<String, String>();
		saleFlow.put("salesFlow.referrer.callType", callType);
		saleFlow.put("salesFlow.dialogueType", dialogueType);
		if(!Utils.isBlank(forceNonConfirm)){
		    saleFlow.put("salesFlow.forceNonConfirm", "true");
		    salesCenterVo.setValueByName("salesFlow.forceNonConfirm" ,"true");
		    logger.info("forceNonConfirmOnSalesCenterVO=true");
		} else {
			logger.info("forceNonConfirmOnSalesCenter_not_set");
		}
		contextMap.put("salesFlow", saleFlow);
		String referrerId = salesCenterVo.getValueByName("referrer.businessParty.referrerId");
		Map<String, String> orderSource =  new HashMap<String, String>();
		orderSource.put("orderSource.referrer", String.valueOf(referrerId));
		contextMap.put("orderSource", orderSource);
		
		dialogueVO = DialogServiceUI.INSTANCE.getDialoguesByContext(contextMap);
		
		salesCenterVo.setValueByName("callType" ,callType);
		salesCenterVo.setValueByName("dialogueType" ,dialogueType);
		
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
		Long orderId = (Long)request.getSession().getAttribute("orderId");
		//salesCenterVo.setValueByName("comcast.order.number", String.valueOf(orderId));
		request.getSession().setAttribute("salescontext", salesCenterVo);
		request.getSession().setAttribute("cycletime", CYCLETIME);
		request.getSession().setAttribute("partnerSpecificDataMap", customerVO.getPartnerSpecificDataMap());
		logger.info("partnerSpecificDataMap="+ customerVO.getPartnerSpecificDataMap());
		logger.info("agentId="+ salesCenterVo.getValueByName("Agent"));
		/*Collection<Map<String, List<DataField>>> coll = dialogueVO.getDataFieldMap().values();

		Iterator<Map<String,List<DataField>>> it = coll.iterator();
		while(it.hasNext()){
			Map<String,List<DataField>> map = (Map<String,List<DataField>>)it.next();
			Collectionmap.values();
		}*/


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

		for (Fieldset fieldset : fieldsetList) {

			String element = HtmlBuilder.INSTANCE.toString(fieldset);
			element = salesCenterVo.replaceNamesWithValues(element);
			events.append(element);
		}		

		ModelAndView mav = new ModelAndView();
		mav.addObject("dialogue" , events.toString());
		mav.addObject("states", webLookupStates.getListAsJSON());
		mav.addObject("namePrefix", webLookupNamePrefix.getListAsJSON());
		mav.addObject("nameSuffix", webLookupNameSuffix.getListAsJSON());
		mav.addObject("unitType", webLookupUnitType.getListAsJSON());
		mav.addObject("rentOwn", webLookupRentOwn.getListAsJSON());
		mav.addObject("serviceAddressType", webLookupServiceAddressType.getListAsJSON());
		mav.addObject("salescenterVo", salesCenterVo);
		mav.addObject("customerVO", customerVO);
		if (Utils.isBlank(customerVO.getDtRequestedStartDate())){
			mav.addObject("HideElectricDate", "true");	
		}
		else{
			mav.addObject("HideElectricDate", "false");	
		}
		if (Utils.isBlank(customerVO.getDtGasRequestedStartDate())){
			mav.addObject("HideGasDate", "true");	
		}else{
			mav.addObject("HideGasDate", "false");	
		}
		mav.setViewName("sales.basic_information");
		
		logger.info("showBasicInfo_end");
		return mav;
	}
		catch (Exception e) {
		    logger.error(e);
		    throw new UnRecoverableException(e.getMessage());
	}
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
	
	
	private String getCapitalizationTextFromNormalText(String normalText){
		normalText = normalText.trim();
		StringBuilder capitalizedAddress = new StringBuilder();
		String[] stringArray = normalText.split(" ");

		for (String value : stringArray) {
			if(value.length()>1){
				capitalizedAddress.append(value.substring(0, 1).toUpperCase());
				capitalizedAddress.append(value.substring(1, value.length()).toLowerCase());
			}else if(value.length()==1){
				capitalizedAddress.append(value.toUpperCase());
			}else
				continue;
			if(!stringArray[stringArray.length-1].equals(value)){
				capitalizedAddress.append(" ");
			}
		}
		return capitalizedAddress.toString();
		
	}
	
	
	private String getCapitalizationNameAfterHyphenated(String normalText){
		normalText = normalText.trim();
		StringBuilder capitalizedAddress = new StringBuilder();
		String[] stringArray = normalText.split("-");

		for (String value : stringArray) {
			if(value.length()>1){
				capitalizedAddress.append(value.substring(0, 1).toUpperCase());
				capitalizedAddress.append(value.substring(1, value.length()));
			}else if(value.length()==1){
				capitalizedAddress.append(value.toUpperCase());
			}else
				continue;
			if(!stringArray[stringArray.length-1].equals(value)){
				capitalizedAddress.append("-");
			}
		}
		
		 stringArray = capitalizedAddress.toString().split("'");
		 
		 capitalizedAddress = capitalizedAddress.delete(0, capitalizedAddress.length());

			for (String value : stringArray) {
				if(value.length()>1){
					capitalizedAddress.append(value.substring(0, 1).toUpperCase());
					capitalizedAddress.append(value.substring(1, value.length()));
				}else if(value.length()==1){
					capitalizedAddress.append(value.toUpperCase());
				}else
					continue;
				if(!stringArray[stringArray.length-1].equals(value)){
					capitalizedAddress.append("'");
				}
			}
		return capitalizedAddress.toString();
	}
	
	@RequestMapping(value="/isvalidaddress")
	protected ModelAndView isValidAddress(HttpServletRequest request,
			HttpServletResponse response) throws UnRecoverableException {
			ModelAndView mav = new ModelAndView();
		try{
				
			logger.info("isValidAddress_begin");
			HttpSession session = request.getSession();
			SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
	
			WebLookupCollection webLookupStates = lookupDao.findUSStates();
			WebLookupCollection webLookupUnitType = lookupDao.findUnitType();
			WebLookupCollection webLookupServiceAddressType = lookupDao.findServiceAddressType();
			WebLookupCollection webLookupRentOwn = lookupDao.findRentOwn();
	
			String prefix = request.getParameter("prefix");
			String suffix = request.getParameter("suffix");
			String firstName = request.getParameter("firstName");
			String middleName = request.getParameter("middleName");
			String lastName = request.getParameter("lastName");
			
			firstName = getCapitalizationNameAfterHyphenated(getCapitalizationTextFromNormalText(firstName));
			lastName = getCapitalizationNameAfterHyphenated(getCapitalizationTextFromNormalText(lastName));
			
			String address1 = request.getParameter("address1");
			String unittype = !Utils.isBlank(request.getParameter("addressTypeList")) ? request.getParameter("addressTypeList") : request.getParameter("unittype");
			String unitnumber = request.getParameter("unitNumber");
			String address2 = request.getParameter("address2");
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			String zipCode = request.getParameter("zipcode");
			String serviceAddrType = request.getParameter("serviceAddrType");
			String rentown = request.getParameter("rentOwnList");
			String datepicker1 = request.getParameter("datepicker1");
			String datepicker2 = request.getParameter("datepicker2");
			String datepicker3 = request.getParameter("datepicker3");
			String noneOftheAbove = request.getParameter("noneoftheabove");
			String isMultiAddress = request.getParameter("isMultiAddress");
			String isFromZipCode = request.getParameter("isFromZipCode");
			SalesContext salesContext = SalesContextFactory.INSTANCE.getSalesContext(createSalesContext(serviceAddrType,request));
			
			StringBuilder fullAddress = new StringBuilder();
			String completeAddress = null;
			ErrorList errorList = new ErrorList();
			
			fullAddress.append(getCapitalizationTextFromNormalText(address1));
			String channel = "salesCenter";
			if(isMultiAddress == null){
			    salesCenterVo.setValueByName("correctedCity", city);
			    salesCenterVo.setValueByName("correctedState", state);
			    salesCenterVo.setValueByName("correctedZipCode", zipCode);
			    salesCenterVo.setValueByName("correctedAddress1", address1);
			}
			if(datepicker3 != null && datepicker3.length() == 8){
				try {
					Date parsedGasStartDate = format1.parse(datepicker3);
					datepicker3 = format2.format(parsedGasStartDate);
				} catch (ParseException e) {
					logger.error(e);
					throw new UnRecoverableException(e.getMessage());
				}
			}
	
			if(datepicker2 != null && datepicker2.length() == 8){
				try {
					Date parsedGasStartDate = format1.parse(datepicker2);
					datepicker2 = format2.format(parsedGasStartDate);
				} catch (ParseException e) {
					logger.error(e);
					throw new UnRecoverableException(e.getMessage());
				}
			}
	
			if(datepicker1 != null && datepicker1.length() == 8){
				try {
					Date parsedGasStartDate = format1.parse(datepicker1);
					datepicker1 = format2.format(parsedGasStartDate);
				} catch (ParseException e) {
					logger.error(e);
					throw new UnRecoverableException(e.getMessage());
				}
			}
			if(!fullAddress.toString().equalsIgnoreCase(Constants.NONE_OF_THE_ABOVE) && isMultiAddress == null) {
				if(unittype != null && unittype.trim().length() > 0){
					fullAddress.append(", ");
					fullAddress.append(unittype);
				}
				if(unitnumber != null && unitnumber.trim().length() > 0){
					fullAddress.append(", ");
					fullAddress.append(unitnumber);
				}
				if(city != null && city.trim().length() > 0){
					fullAddress.append(", ");
					fullAddress.append(getCapitalizationTextFromNormalText(city));
				}
				if(state != null && state.trim().length() > 0){
					fullAddress.append(" ");
					fullAddress.append(state);
				}
				if(zipCode != null && zipCode.trim().length() > 0){
					fullAddress.append(" ");
					fullAddress.append(zipCode);
				}
			}
			
			completeAddress = fullAddress.toString();
			Map<String, String> partnerSpecificDataMap = (Map<String, String>)request.getSession().getAttribute("partnerSpecificDataMap");
			String referrerId = salesCenterVo.getValueByName("referrer.businessParty.referrerId");
			List<String> psMatchreferrerList = getPsMatchReferrers(ConfigRepo.getString("utility.ps_match_referrer_list"));
			logger.info("PsMatchreferrerList="+psMatchreferrerList);
			boolean isPartnerSpecificMatchReqForUtility = false; 
			if (psMatchreferrerList != null && psMatchreferrerList.contains(referrerId)){
				isPartnerSpecificMatchReqForUtility = true;
			}
			
			session.setAttribute("isPartnerSpecificMatchReqForUtility", isPartnerSpecificMatchReqForUtility);
			String operatingCompany = null;
			SearchResults searchForm = new SearchResults(completeAddress, ProductSearchVO.CONCERT, serviceAddrType,rentown,partnerSpecificDataMap,isPartnerSpecificMatchReqForUtility,
					operatingCompany);
			
			salesCenterVo.setValueByName("orderId", String.valueOf((Long)session.getAttribute("orderId")));
			salesCenterVo.setValueByName("sessionId", session.getId());
			searchForm.setSalesCenterVO(salesCenterVo);
			//SearchHelper.SearchProducts(searchForm);
			String addressResult = null;
			com.AL.xml.se.v4.AddressType sreAddress = null;
			ServiceabilityEnterpriseResponse sarRes;
			
			Map<String, Object>  addressMap = new HashMap<String, Object>();
			
			logger.info("completeAddress=" +completeAddress);
			boolean isMultiAddressData = false;
			ProductResultsManager productResultManager;
			if (isMultiAddress != null){
				productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");
				HashMap<String, String> multiAddressMap = (HashMap<String,String>)productResultManager.getMultipleAddressLine2Map();
				isMultiAddressData = checkIfMultipleAddressReturnsMultiple(multiAddressMap,completeAddress);	
			}else{
				productResultManager = new ProductResultsManager(searchForm,grossCommissionableRevenueDao, qualificationPopUpRefDetailsDao, hughesNetServedUpDataDao, frontierPricingGridConfigDao);
				//new Thread(productResultManager).start();
				//productResultManager.run();
				session.setAttribute("productResultManager", productResultManager);				
			}
			logger.info("isMultiAddressData=" +isMultiAddressData);
			if (isMultiAddressData){
				addressMap.put("Invalid", null);
			}else if(isFromZipCode!=null && isFromZipCode.equalsIgnoreCase(Constants.YES)){
				addressMap = productResultManager.isValidAddress(completeAddress, salesContext,serviceAddrType,Constants.ZIPFALLBACK);
			}else{
				addressMap = productResultManager.isValidAddress(completeAddress, salesContext,serviceAddrType,Constants.NORMAL);
			}

			
			for (Map.Entry<String, Object> entry : addressMap.entrySet()) {
				addressResult = entry.getKey();
				if (entry.getValue() != null){
					sarRes = (ServiceabilityEnterpriseResponse)entry.getValue();
					String GUID = sarRes.getGUID();
					request.getSession().setAttribute("GUID", GUID);
					if(GUID != null) {
						salesCenterVo.setValueByName("GUID", GUID);
					}
					ServiceabilityResponse2 sre = (ServiceabilityResponse2)sarRes.getResponse();
					sreAddress = (com.AL.xml.se.v4.AddressType) sre.getCorrectedAddress();
				}
			}
			
			if (!Utils.isBlank(addressResult) && addressResult.equalsIgnoreCase("Address Found - Proceed to next screen")){
				//new Thread(productResultManager).start();
				if (!StringUtils.isEmpty(state)){
					salesCenterVo.setValueByName("state", state);
				}
				
				ObjectFactory oFactory = new ObjectFactory();
				AddressType serviceAddress = oFactory.createAddressType();
				serviceAddress.setExternalId(sreAddress.getExternalId());
				serviceAddress = getStreetNumberAndStreetName(serviceAddress, address1);
				
				if(isFromZipCode!=null && isFromZipCode.equalsIgnoreCase(Constants.YES))
				{
					serviceAddress.setCity(getCapitalizationTextFromNormalText(city));
					serviceAddress.setStateOrProvince(state);
					serviceAddress.setPostalCode(zipCode);
					
					fullAddress.delete(0, fullAddress.length());
					fullAddress.append(address1);
					fullAddress.append(", ");
					fullAddress.append(sreAddress.getCity());
					fullAddress.append(" ");
					fullAddress.append(sreAddress.getStateOrProvince());
					fullAddress.append(" ");
					fullAddress.append(sreAddress.getPostalCode());
					completeAddress = fullAddress.toString();
					serviceAddress.setAddressBlock(completeAddress);
				}
				else{
					serviceAddress.setStreetName(getCapitalizationTextFromNormalText(sreAddress.getStreetName()));
					serviceAddress.setStreetNumber(sreAddress.getStreetNumber());
					serviceAddress.setCity(getCapitalizationTextFromNormalText(sreAddress.getCity()));
					serviceAddress.setStateOrProvince(sreAddress.getStateOrProvince());
					serviceAddress.setPostalCode(sreAddress.getPostalCode());
					serviceAddress.setCountry(sreAddress.getCountry());
					serviceAddress.setAddressBlock(sreAddress.getAddressBlock());
					
					if(sreAddress.getPostfixDirectional() != null){
						serviceAddress.setPostfixDirectional(sreAddress.getPostfixDirectional());
					}
					if(sreAddress.getPrefixDirectional() != null){
						serviceAddress.setPrefixDirectional(getCapitalizationTextFromNormalText(sreAddress.getPrefixDirectional()));
					}
					if(sreAddress.getStreetType() != null){
						serviceAddress.setStreetType(getCapitalizationTextFromNormalText(sreAddress.getStreetType()));
					}
				}
				
				productResultManager.setAddress(serviceAddress);
				
				logger.info("pool="+pool);
				if(pool==null){
					logger.info("thread_pool_size"+ConfigRepo.getInt("*.thread_pool_size"));
					int i = ConfigRepo.getInt("*.thread_pool_size") == 0 ? 500 : ConfigRepo.getInt("*.thread_pool_size");
					pool = Executors.newFixedThreadPool(i);
				}
	
				pool.execute(productResultManager);
				if (!StringUtils.isEmpty(datepicker2)){
					salesCenterVo.setValueByName("electricService.startDate", datepicker2);
				} else {
					salesCenterVo.setValueByName("electricService.startDate", "N/A");
				}
	
				if (!StringUtils.isEmpty(datepicker3)){
					salesCenterVo.setValueByName("gasService.startDate", datepicker3);
				}
				else{
					salesCenterVo.setValueByName("gasService.startDate", "N/A");
				}
				if (!StringUtils.isEmpty(datepicker1)){
					salesCenterVo.setValueByName("moveDate", datepicker1);
				}
				else{
					salesCenterVo.setValueByName("moveDate", "N/A");
				}
				if (!StringUtils.isEmpty(unitnumber)){
					salesCenterVo.setValueByName("unitNum", unitnumber);
				}
				if (!StringUtils.isEmpty(unittype)){
					salesCenterVo.setValueByName("unitType", getFormattedUnitType(unittype));
				}
				
				Long customerID =(Long)request.getSession().getAttribute("customerID");
	
				String line2 = "";
				if (!StringUtils.isEmpty(unittype)){
					line2 = unittype;
				}
				if (!StringUtils.isEmpty(unitnumber)){
					line2 = line2 + " " +unitnumber;
				}
				
				serviceAddress.setLine2(line2);
				
				if(datepicker2 != null && !datepicker2.isEmpty()){
					QName NAME = new QName("http://xml.AL.com/v4","electricityStartAt");
					JAXBElement<XMLGregorianCalendar> jCal = new JAXBElement<XMLGregorianCalendar>(
							NAME, XMLGregorianCalendar.class,
							DateUtil.asXMLGregorianCalendarDate(datepicker2, "MM/dd/yyyy"));
					logger.info("jCal="+jCal.getValue());
					serviceAddress.setElectricityStartAt(jCal);
				}
				else{
					serviceAddress.setElectricityStartAt(null);
				}
				if(datepicker3 != null && !datepicker3.isEmpty()){
					QName NAME = new QName("http://xml.AL.com/v4","gasStartAt");
					JAXBElement<XMLGregorianCalendar> jCal = new JAXBElement<XMLGregorianCalendar>(
							NAME, XMLGregorianCalendar.class,
							DateUtil.asXMLGregorianCalendarDate(datepicker3, "MM/dd/yyyy"));
					serviceAddress.setGasStartAt(jCal);
				}
				else{
					serviceAddress.setGasStartAt(null);
				}
	
				setDwellingTypeAndOwnerShip(serviceAddress,salesCenterVo,rentown,serviceAddrType);
	
				//CustAddress custAddress = oFactory.createCustAddress();
				String GUID = (String)request.getSession().getAttribute("GUID");
				//CustomerContextType customerContext = CustomerFactory.INSTANCE.buildCustomerContext("source", "GUID", GUID);
				Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
				Map<String, String> sourceEntity = new HashMap<String, String>();
				sourceEntity.put("source", "salescenter");
				sourceEntity.put("GUID", GUID);
				data.put("source", sourceEntity);
				CustomerContextType customerContext = CustomerFactory.INSTANCE.buildCustomerContext(data);
				CustomerType customerType = CustomerService.INSTANCE.getCustomerType(customerID.toString());
				CustAddress custAddress = getAddressByRole(customerType.getAddressList().getCustomerAddress(), RoleType.SERVICE_ADDRESS.value());
	
				CustomerType customer = oFactory.createCustomerType();
				
				AddressRoles addrRoles = oFactory.createCustAddressAddressRoles();
				String status = "active";
				String addressUniqueId = null;
				String[] roles = new String[1];
				roles[0] = "SERVICE_ADDRESS";
				for (String role : roles) {
					if ((role != null) && (role.length() > 0)) {
						String cleanRole = resolveRole(role);
						RoleType rt = RoleType.fromValue(cleanRole);
						addrRoles.getRole().add(rt);
					}
				}
				addressUniqueId = custAddress.getAddressUniqueId();
				
				//custAddress.setAddressRoles(addrRoles);
				//custAddress.setStatus(status);
				logger.info("custAddress.getAddress().getExternalId()="+custAddress.getAddress().getExternalId());
				serviceAddress.setExternalId(custAddress.getAddress().getExternalId());
				custAddress.setAddress(serviceAddress);
				//customerType.getAddressList().getCustomerAddress().add(custAddress);
				customer.setAddressList(customerType.getAddressList());
				customer.setExternalId(customerID);
				String agentId = salesCenterVo.getValueByName("Agent");
				//CustomerType customer = VClient.saveAddress(agentId, String.valueOf(customerID), custAddress, new ArrayList<String>(), new ErrorList());
			/*	CustomerType customer = AddressService.INSTANCE.saveAddressUpdate(agentId, String.valueOf(customerID), roles, 
						String.valueOf(custAddress.getAddress().getExternalId()), custAddress.getAddressUniqueId(), serviceAddress, custAddress.getStatus(),customerContext);*/
	
				if (completeAddress != null){
					salesCenterVo.setValueByName("consumer.dwelling.addressBlock", completeAddress);
					salesCenterVo.setValueByName("dwelling.addressBlock", completeAddress);
				}
				//Check address changed or not 
				ConsumerVO dtConsumer = (ConsumerVO)request.getSession().getAttribute("salescontextDt");
	
				OrderType order =(OrderType)request.getSession().getAttribute("order");
				//This is for updating order info
				OrderType order_new = new com.AL.xml.v4.ObjectFactory().createOrderType();
	
				String email = order.getCustomerInformation().getCustomer().getBestEmailContact();
				//order.setExternalId(Long.valueOf(orderIdForMoveDate));formatString
				if(!datepicker1.isEmpty()){
					Date mDate = DateUtil.fromString(datepicker1, "MM/dd/yyyy");
					if(mDate != null){
						order_new.setMoveDate(DateUtil.asXMLGregorianCalendar(mDate));
					}else{
						order_new.setMoveDate(null);
					}
				}else{
					order_new.setMoveDate(null);
				}
				oFactory = new ObjectFactory();
				
				String dtFirstName = salesCenterVo.getValueByName("consumer.name.first");
				String dtLastName = salesCenterVo.getValueByName("consumer.name.last");
				String dtReqStartDate = salesCenterVo.getValueByName("requestedStartDate");
				String dtGasReqStartDate = salesCenterVo.getValueByName("gasRequestedStartDate");
				String partnerAccountId = salesCenterVo.getValueByName("customer.confirmation.number");
				boolean isFNameChanged = false;
				boolean isLNameChanged = false;
				boolean isReqStartDateChanged = false;
				boolean isGasReqStartDateChanged = false;
				boolean isAddrChanged = false;
				
				NotificationEventCollectionType notifEventColl = null;
				NotificationEventType notifEventType = oFactory.createNotificationEventType();
				if ((!Utils.isBlank(dtFirstName) && !Utils.isBlank(firstName.trim()))
						&& (!dtFirstName.equalsIgnoreCase(firstName.trim()))) {
					isFNameChanged = true;
				} else {
					if(Utils.isBlank(dtFirstName) && !Utils.isBlank(firstName)) {
						//first name from DT xml is blank and first name is given in the basic info page
						//then also it is to be treated as name change
						isFNameChanged = true;
					}
				}
	
				customer.setFirstName(firstName.trim());
				logger.info("firstName="+firstName);
	
				if ((!Utils.isBlank(dtLastName) && !Utils.isBlank(lastName.trim()))
						&& (!dtLastName.equalsIgnoreCase(lastName.trim()))) {
					isLNameChanged = true;
				} else {
					if(Utils.isBlank(dtLastName) && !Utils.isBlank(lastName)) {
						//last name from DT xml is blank and last name is given in the basic info page
						//then also it is to be treated as name change
						isLNameChanged = true;
					}
				}
				customer.setLastName(lastName.trim());
				if (!Utils.isBlank(middleName)){
					customer.setMiddleName(middleName.trim());	
				}
				if (!Utils.isBlank(prefix)){
					customer.setTitle(prefix.trim());	
				}
				if (!Utils.isBlank(suffix)){
					customer.setNameSuffix(suffix.trim());	
				}
				logger.info("lastName="+lastName);
	
				if ((!Utils.isBlank(dtReqStartDate) && !Utils.isBlank(datepicker2))
						&& (!dtReqStartDate.equalsIgnoreCase(datepicker2))) {
					isReqStartDateChanged = true;
					notifEventType.getReason().add(30);
				}
				if ((!Utils.isBlank(dtGasReqStartDate) && !Utils.isBlank(datepicker3))
						&& (!dtGasReqStartDate.equalsIgnoreCase(datepicker3))) {
					isGasReqStartDateChanged = true;
					notifEventType.getReason().add(40);
				}
	
				if(isFNameChanged || isLNameChanged){
					notifEventType.getReason().add(10);
				}
	
				String dtStreet1 = salesCenterVo.getValueByName("address.street1");
				String dtStreet2 = salesCenterVo.getValueByName("address.street2");
				String dtCity = salesCenterVo.getValueByName("address.city");
				String dtState = salesCenterVo.getValueByName("address.state");
				String dtZip = salesCenterVo.getValueByName("address.zip");
				String dtTelephoneNum = salesCenterVo.getValueByName("customer.telePhoneNum");
				Long tempAddressUniqueId = null;
				
				//DT Address
				if(dtZip != null && dtZip.trim().length() > 0 && dtStreet1 != null && dtStreet1.trim().length() > 0){
					oFactory = new ObjectFactory();
					AddressType address = oFactory.createAddressType();
					address.setExternalId(0L);
					address = getStreetNumberAndStreetName(address, dtStreet1);
					address.setCity(dtCity);
					address.setStateOrProvince(dtState);
					address.setPostalCode(dtZip);
					address.setCountry("US");
					address.setLine2(dtStreet2);
					if(dtReqStartDate != null && !dtReqStartDate.isEmpty()){
						QName NAME = new QName("http://xml.AL.com/v4","electricityStartAt");
						JAXBElement<XMLGregorianCalendar> jCal = new JAXBElement<XMLGregorianCalendar>(
								NAME, XMLGregorianCalendar.class,
								DateUtil.asXMLGregorianCalendarDate(dtReqStartDate, "MM/dd/yyyy"));
						address.setElectricityStartAt(jCal);
					}
					else{
						address.setElectricityStartAt(null);
					}
					if(dtGasReqStartDate != null && !dtGasReqStartDate.isEmpty()){
						QName NAME = new QName("http://xml.AL.com/v4","gasStartAt");
						JAXBElement<XMLGregorianCalendar> jCal = new JAXBElement<XMLGregorianCalendar>(
								NAME, XMLGregorianCalendar.class,
								DateUtil.asXMLGregorianCalendarDate(dtGasReqStartDate, "MM/dd/yyyy"));
						address.setGasStartAt(jCal);
					}
					else{
						address.setGasStartAt(null);
					}
	
					StringBuilder dtFullAddress = new StringBuilder();
					dtFullAddress.append(getCapitalizationTextFromNormalText(dtStreet1));
					if(dtStreet2 != null && dtStreet2.trim().length() > 0){
						dtFullAddress.append(" ");
						dtFullAddress.append(dtStreet2);
					}
					if(dtCity != null && dtCity.trim().length() > 0){
						dtFullAddress.append(", ");
						dtFullAddress.append(getCapitalizationTextFromNormalText(dtCity));
					}
					if(dtState != null && dtState.trim().length() > 0){
						dtFullAddress.append(" ");
						dtFullAddress.append(dtState);
					}
					if(dtZip != null && dtZip.trim().length() > 0){
						dtFullAddress.append(" ");
						dtFullAddress.append(dtZip);
					}
					address.setAddressBlock(dtFullAddress.toString());
					CustAddress custAddress1 = oFactory.createCustAddress();
					addrRoles = oFactory.createCustAddressAddressRoles();
					status = "active";
					roles = new String[1];
					roles[0] = "DT_ADDRESS";
					for (String role : roles) {
						if ((role != null) && (role.length() > 0)) {
							String cleanRole = resolveRole(role);
							RoleType rt = RoleType.fromValue(cleanRole);
							addrRoles.getRole().add(rt);
						}
					}
					custAddress1.setAddressRoles(addrRoles);
					custAddress1.setStatus(status);
					if(tempAddressUniqueId!=null){
					      tempAddressUniqueId = tempAddressUniqueId+1;
					     }
					else{
					      tempAddressUniqueId = System.currentTimeMillis();
					     }
					custAddress1.setAddressUniqueId(tempAddressUniqueId.toString());
					custAddress1.setAddress(address);
					if(customer.getAddressList() == null){
						customer.setAddressList(oFactory.createAddressListType());
					}
					customer.getAddressList().getCustomerAddress().add(custAddress1);
				}
				
				//customerType = CustomerService.INSTANCE.getCustomerType(customerID.toString());
				//custAddress = getAddress(customerType, RoleType.SERVICE_ADDRESS.name());
				String street = getStreetNumberAndStreetName(address1);
				
				salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
				String correctedCity = salesCenterVo.getValueByName("correctedCity");
				String correctedState = salesCenterVo.getValueByName("correctedState");
				String correctedZipCode = salesCenterVo.getValueByName("correctedZipCode");
				String correctedAddress1 = salesCenterVo.getValueByName("correctedAddress1");
				if((!StringUtils.isEmpty(dtStreet1) && !StringUtils.isEmpty(correctedAddress1))
						&& (!dtStreet1.equalsIgnoreCase(correctedAddress1.trim()))){
					isAddrChanged = true;
				}
				if((!StringUtils.isEmpty(dtCity) && !StringUtils.isEmpty(correctedCity))
						&& (!dtCity.equalsIgnoreCase(correctedCity.toString()))){
					isAddrChanged = true;
				}
				if((!StringUtils.isEmpty(dtState) && !StringUtils.isEmpty(correctedState))
						&& (!dtState.equalsIgnoreCase(correctedState.toString()))){
					isAddrChanged = true;
				}
				if((!StringUtils.isEmpty(dtZip) && !StringUtils.isEmpty(correctedZipCode))
						&& (!dtZip.equalsIgnoreCase(correctedZipCode.toString()))){
					isAddrChanged = true;
				}
	
				logger.info("isAddrChanged="+isAddrChanged);
				if(isAddrChanged){
					CustAddress custAddress1 = oFactory.createCustAddress();
					completeAddress = address1;
					oFactory = new ObjectFactory();
					AddressType address = oFactory.createAddressType();
					address.setExternalId(0L);
					if((correctedCity != null && correctedCity.trim().length() > 0 ) && (correctedState != null && correctedState.trim().length() > 0) && (correctedZipCode != null && correctedZipCode.trim().length() > 0)){
						//address = getStreetNumberAndStreetName(address, address1);
						logger.info("address1="+correctedAddress1);
						address = getStreetNumberAndStreetName(address, correctedAddress1);
						address.setCity(correctedCity);
						address.setStateOrProvince(correctedState);
						address.setPostalCode(correctedZipCode);
						address.setCountry("USA");
						line2 = "";
						if (!StringUtils.isEmpty(unittype)){
							line2 = unittype;
						}
						if (!StringUtils.isEmpty(unitnumber)){
							line2 = line2 + " " +unitnumber;
						}
						
						address.setLine2(line2);
					}else{
						address = setAddress(address, completeAddress);
						address.setLine2(address2);
						address.setCountry("USA");
					}
					
					addrRoles = oFactory.createCustAddressAddressRoles();
					status = "active";
					roles = new String[1];
					roles[0] = "CORRECTED_ADDRESS";
					for (String role : roles) {
						if ((role != null) && (role.length() > 0)) {
							String cleanRole = resolveRole(role);
							RoleType rt = RoleType.fromValue(cleanRole);
							addrRoles.getRole().add(rt);
						}
					}
					custAddress1.setAddressRoles(addrRoles);
					custAddress1.setStatus(status);
					
					if(tempAddressUniqueId!=null){
						tempAddressUniqueId = tempAddressUniqueId+1;
					}else{
						tempAddressUniqueId = System.currentTimeMillis();
					}
					
					custAddress1.setAddressUniqueId(tempAddressUniqueId.toString());
					
					custAddress1.setAddress(address);
					if(customer.getAddressList() == null){
						customer.setAddressList(oFactory.createAddressListType());
					}
					customer.getAddressList().getCustomerAddress().add(custAddress1);
					
					notifEventType.getReason().add(20);
				}
				
				if(!Utils.isBlank(dtTelephoneNum)){
					if(dtTelephoneNum.length()>=12){
					dtTelephoneNum = getFormattedDtTelephoneNum(dtTelephoneNum);
					}
					else{
						dtTelephoneNum = "";
					}
				}
				logger.info("CustId="+customerID);
				
				StringBuilder customerFullName = new StringBuilder();
				if(!Utils.isBlank(prefix)){
					customerFullName.append(prefix);
					customerFullName.append(" ");
				}
				if(!Utils.isBlank(firstName)){
					customerFullName.append(firstName);
					customerFullName.append(" ");
				}
					
				if(!Utils.isBlank(middleName)){
					customerFullName.append(middleName);
					customerFullName.append(" ");
				}
				if(!Utils.isBlank(lastName)){
					customerFullName.append(lastName);
				}
				if(!Utils.isBlank(suffix)){
					customerFullName.append(" ");
					customerFullName.append(suffix);
				}
				salesCenterVo.setValueByName("consumer.name.nameBlock", customerFullName.toString());
				
				if(prefix != null) {
					salesCenterVo.setValueByName("consumer.name.prefix", prefix);
				}
				if(suffix != null) {
					salesCenterVo.setValueByName("consumer.name.suffix",suffix);
				}
	
				salesCenterVo.setValueByName("consumer.name.first", firstName);
				salesCenterVo.setValueByName("consumer.name.last", lastName);
				salesCenterVo.setValueByName("customer.name.first", firstName);
				salesCenterVo.setValueByName("customer.name.last", lastName);
				salesCenterVo.setValueByName("consumer.name.middle", middleName);
				salesCenterVo.setValueByName("Customer Name", firstName +" " +lastName);
				salesCenterVo.setValueByName("address.street1", address1);
				salesCenterVo.setValueByName("address.city", city);
				salesCenterVo.setValueByName("address.state", state);
				salesCenterVo.setValueByName("address.zip", zipCode);
				if(session.getAttribute("primaryLanguage")!=null){
					customer.setPrimaryLanguage(Integer.valueOf(session.getAttribute("primaryLanguage").toString()));
				}
				
				String dtAgentId = salesCenterVo.getValueByName("dtAgentId");
				String dtCreated = salesCenterVo.getValueByName("dtCreated");
				if(dtCreated != null && !dtCreated.isEmpty()){
					QName NAME = new QName("http://xml.AL.com/v4","dtCreated");
					JAXBElement<XMLGregorianCalendar> jCal = new JAXBElement<XMLGregorianCalendar>(
							NAME, XMLGregorianCalendar.class,
							DateUtil.asXMLGregorianCalendarDate(datepicker2, "MM/dd/yyyy"));
					customer.setDtCreated(jCal);
				}
				if(!notifEventType.getReason().isEmpty() 
						&& notifEventType.getReason().size() > 0){
					notifEventType.setCode(100);
					notifEventColl = oFactory.createNotificationEventCollectionType();
					DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
					GregorianCalendar gc = new GregorianCalendar();
					XMLGregorianCalendar dateTime = datatypeFactory.newXMLGregorianCalendar(gc);
					notifEventType.setDateTimeStamp(dateTime);
					notifEventType.setComment("Customer Info Updated");
					notifEventColl.getEvent().add(notifEventType);
					customer.setBestPhoneContact(dtTelephoneNum);
					customer.setBestEmailContact(email);
					if(!Utils.isBlank(email)){
						EMailAddressType emailObj = new EMailAddressType();
						emailObj.setValue(email);
						customer.setHomeEMail(emailObj);
					}
					customer.setPartnerAccountId(partnerAccountId);
					customer.setDtAgentId(dtAgentId);	
					logger.info("partnerAccountId="+partnerAccountId);
					customer = CustomerService.INSTANCE.submitCustomerType(agentId, String.valueOf(order.getCustomerInformation().getCustomer().getExternalId()),
							"updateCustomer", customer, notifEventColl,customerContext, errorList);
					if(errorList != null && errorList.size() > 0){
						for(CartError cartError: errorList){
						    logger.info("UpdateCustomerErrorCode="+cartError.getCode()+" ErrorMessage="+cartError.getMessage()
						    		+" ErrorDescription="+cartError.getDescription());
						}
						throw new UnRecoverableException(errorList.get(0).getMessage());
					}
				}
				else {
					//CustomerType customer2 = oFactory.createCustomerType();
					//customer2.setExternalId(customerID);
					logger.info("CustId="+customerID);
					customer.setBestPhoneContact(dtTelephoneNum);
					customer.setBestEmailContact(email);
					if(!Utils.isBlank(email)){
						EMailAddressType emailObj = new EMailAddressType();
						emailObj.setValue(email);
						customer.setHomeEMail(emailObj);
					}
					customer.setPartnerAccountId(partnerAccountId);
					customer.setDtAgentId(dtAgentId);	
					logger.info("partnerAccountId="+partnerAccountId);
					customer = CustomerService.INSTANCE.submitCustomerType(agentId, String.valueOf(order.getCustomerInformation().getCustomer().getExternalId()),
							"updateCustomer", customer, null, customerContext, errorList);
					if(errorList != null && errorList.size() > 0){
						for(CartError cartError: errorList){
							logger.info("UpdateCustomerErrorCode="+cartError.getCode()+" ErrorMessage="+cartError.getMessage()
						    		+" ErrorDescription="+cartError.getDescription());
						}
						throw new UnRecoverableException(errorList.get(0).getMessage());
					}
				}
				Long orderId = (Long)request.getSession().getAttribute("orderId");
				
				for(CustAddress cust:customer.getAddressList().getCustomerAddress()){
					if(cust.getAddressUniqueId().equals(addressUniqueId)){
						request.getSession().setAttribute("addressId", cust.getAddress().getExternalId());
						request.getSession().setAttribute("address", cust);
					}
				}
				
				logger.info("order_updated_with_salesContext_GUID"+GUID);
				String orderIdStr = String.valueOf(orderId);
				
				logger.info("OrderId="+orderIdStr);
				SalesContextType updateSalesContext = OrderService.INSTANCE.getSalesContext(orderIdStr,agentId);
				SalesContextEntityType salesContextEntityType = new SalesContextEntityType();
				
				if(isFromZipCode!=null && isFromZipCode.equalsIgnoreCase(Constants.YES)){
					for(SalesContextEntityType contextEntityType : updateSalesContext.getEntity()){
						if(contextEntityType.getName().equalsIgnoreCase(Constants.SYP)){
							NameValuePairType nameValuePairType = new NameValuePairType();
							nameValuePairType.setName(Constants.ADDRES_MATCH_TYPE);
							nameValuePairType.setValue(Constants.ZIPFALLBACK);
							contextEntityType.getAttribute().add(nameValuePairType);
						}
					}
				}
				
				NameValuePairType nvpt = new NameValuePairType();
				nvpt.setName("GUID");
				nvpt.setValue(GUID);
				salesContextEntityType.getAttribute().add(nvpt);
				salesContextEntityType.setName("CKO");
				updateSalesContext.getEntity().add(salesContextEntityType);
				order_new.setAgentId(agentId);
				
				if(isFromZipCode!=null && isFromZipCode.equalsIgnoreCase(Constants.YES))
				{
					order_new.setIsZipOnlySearch(1);
				}
				
				OrderManagementRequestResponse requestResponse =OrderService.INSTANCE.updateOrder(agentId, orderIdStr, updateSalesContext, order_new, GUID);
				OrderFactory.INSTANCE.validateOrder(requestResponse, errorList);
				if(errorList != null && errorList.size() > 0){
					for(CartError cartError: errorList){
						
						logger.info("UpdateOrderErrorCode="+cartError.getCode()+" ErrorMessage="+cartError.getMessage()
					    		+" ErrorDescription="+cartError.getDescription());
					}
					throw new UnRecoverableException(errorList.get(0).getMessage());
				}
				
				OrderType orderType = null;
				SalesContextType context = null;
				if ((requestResponse != null) && (requestResponse.getResponse() != null)){
					if (requestResponse.getResponse().getOrderInfo() != null && requestResponse.getResponse().getOrderInfo().size() > 0) {
						orderType = requestResponse.getResponse().getOrderInfo().get(0);
					}
					if (requestResponse.getResponse().getSalesContext() != null) {
						context = requestResponse.getResponse().getSalesContext();
					}
				}
				
				request.getSession().setAttribute("order", orderType);
				SalesContextFactory.INSTANCE.setContextInSession(session,context);
				logger.info("phone="+order.getCustomerInformation().getCustomer().getBestPhoneContact());
				
				mav.addObject("dialogue" , addressResult);
				mav.setViewName("sales.isValidAddress");
			}else if (Utils.isBlank(addressResult) || (!addressResult.equalsIgnoreCase("Address Found - Process to next screen") 
					&& !addressResult.equalsIgnoreCase("Multiple Address"))){
				if (isMultiAddress != null){
						if (completeAddress != null ){
							if(completeAddress != null && !completeAddress.equalsIgnoreCase("None of the above")){
								prepopulateAddress(completeAddress,mav);
							}else{
								prepopulateAddressForNoneOfAbove(noneOftheAbove,mav);
								mav.addObject("unitNumber" , unitnumber);
								mav.addObject("unitTypevalue" , unittype);
							}
						
							mav.addObject("dwellingType",serviceAddrType);
							mav.addObject("rentown" , rentown);
							
						}
					}else{
						mav.addObject("unitTypevalue" , unittype);
						mav.addObject("unitNumber" , unitnumber);
						mav.addObject("addressLine1", address1);
						mav.addObject("city", city);
						mav.addObject("state", state);
						mav.addObject("zip", zipCode);
						mav.addObject("dwellingType",serviceAddrType);
						mav.addObject("rentown" , rentown);
					}
		
					mav.addObject("datepicker1" , datepicker1);
					mav.addObject("datepicker2" , datepicker2);
					mav.addObject("datepicker3" , datepicker3);
					mav.addObject("lastName" , lastName);
					mav.addObject("firstName" , firstName);
					mav.addObject("dialogue" , addressResult);
					
					if (completeAddress != null && !completeAddress.equalsIgnoreCase("None of the above")){
						mav.addObject("completeAddress" , completeAddress);	
					}else{
						mav.addObject("completeAddress" , noneOftheAbove);
					}
					
					mav.addObject("states", webLookupStates.getListAsJSON());
					mav.addObject("unitType", webLookupUnitType.getListAsJSON());
					mav.addObject("serviceAddressType", webLookupServiceAddressType.getListAsJSON());
					mav.addObject("rentOwn", webLookupRentOwn.getListAsJSON());
					mav.setViewName("sales.isInvalidAddress");
				
			}else if (!Utils.isBlank(addressResult) && addressResult.equalsIgnoreCase("Multiple Address")){
				mav.addObject("Prefix" , prefix);
				mav.addObject("Suffix" , suffix);
				mav.addObject("middleName" , middleName);
				mav.addObject("unittype" , unittype);
				mav.addObject("unitnumber" , unitnumber);
				mav.addObject("serviceAddrType" , serviceAddrType);
				mav.addObject("rentown" , rentown);
				mav.addObject("datepicker1" , datepicker1);
				mav.addObject("datepicker2" , datepicker2);
				mav.addObject("datepicker3" , datepicker3);
				mav.addObject("lastName" , lastName);
				mav.addObject("firstName" , firstName);
				mav.addObject("dialogue" ,productResultManager.getMultipleAddress());
				mav.addObject("completeAddress" , completeAddress);
				mav.setViewName("sales.isMultipleAddress");
			}
		}catch (Exception e) {
			logger.error(e);
			logger.warn("Error_in_isValidAddress",e);
			throw new UnRecoverableException(e.getMessage());
		}
		logger.info("isValidAddress_end");
		return mav;
	}


	private List<String> getPsMatchReferrers(String referrers) {
		List<String> rtnList = new ArrayList<String>(); 
		if (!Utils.isBlank(referrers)){
			StringTokenizer stringTokenizer = new StringTokenizer(referrers, "|");
			while (stringTokenizer.hasMoreElements()) {
				rtnList.add(stringTokenizer.nextElement().toString());
			}
		}
		return rtnList;
	}
	private void prepopulateAddress(String completeAddress,
			ModelAndView mav) {
		
		String unitType = "";
		String unit = "";
		String line1 = "";
		String zip = "";
		String state = "";
		String city = ""; 

		if(completeAddress.contains(",")){

			int indexPos = completeAddress.indexOf(",");
			String addressLine1 = completeAddress.substring(0,indexPos);
			String countryBlock = completeAddress.substring(indexPos+1);
			
			if (addressLine1.toUpperCase().contains(" APT ")
					|| addressLine1.contains("Apartment")) {
				unitType = "Apartment";
				unit = "APT";
			} else if (addressLine1.toUpperCase().contains("BLDG")
					|| addressLine1.contains("Building")) {
				unitType = "Building";
				unit = "BLDG";
			} else if (addressLine1.toUpperCase().contains("DEPT")
					|| addressLine1.contains("Department")) {
				unitType = "Department";
				unit = "DEPT";
			} else if (addressLine1.toUpperCase().contains("FL")
					|| addressLine1.contains("Floor")) {
				unitType = "Floor";
				unit = "FL";
			} else if (addressLine1.toUpperCase().contains("HNGR")
					|| addressLine1.contains("Hanger")) {
				unitType = "Hanger";
				unit = "HNGR";
			} else if (addressLine1.toUpperCase().contains("LOT")
					|| addressLine1.contains("Lot")) {
				unitType = "Lot";
				unit = "LOT";
			} else if (addressLine1.toUpperCase().contains("OFC")
					|| addressLine1.contains("Office")) {
				unitType = "Office";
				unit = "OFC";
			} else if (addressLine1.toUpperCase().contains("SPC")
					|| addressLine1.contains("APT")) {
				unitType = "Space";
				unit = "SPC";
			} else if (addressLine1.toUpperCase().contains("STE")
					|| addressLine1.contains("Suite")) {
				unitType = "Suite";
				unit = "STE";
			} else if (addressLine1.toUpperCase().contains("TRLR")
					|| addressLine1.contains("Trailer")) {
				unitType = "Trailer";
				unit = "TRLR";
			} else if (addressLine1.toUpperCase().contains("UNIT")
					|| addressLine1.contains("Unit")) {
				unitType = "Unit";
				unit = "UNIT";
			}

			if(!unit.isEmpty()){
				indexPos = addressLine1.toUpperCase().indexOf(unit);
				if (indexPos >= 0){
					line1 = addressLine1.substring(0,indexPos);	
				}else{
					indexPos = addressLine1.indexOf(unitType);
					line1 = addressLine1.substring(0,indexPos);
				}
			}else{
				line1 = addressLine1;
			}
			
			indexPos = countryBlock.lastIndexOf(" ");
			zip = countryBlock.substring(indexPos+1);

			String country = countryBlock.substring(0,indexPos);
			indexPos = country.lastIndexOf(" ");
			state = country.substring(indexPos+1);
			state = state.replace(",", "");
			city = countryBlock.substring(0, indexPos);
		}
		
		mav.addObject("unitTypevalue" , unitType);
		mav.addObject("city" , city);
		mav.addObject("zip" , zip);
		mav.addObject("state" , state.toUpperCase());
		mav.addObject("addressLine1" , line1);
	}
	private void prepopulateAddressForNoneOfAbove(String completeAddress,ModelAndView mav) {
		int endIndex = completeAddress.lastIndexOf(",");
		String addressTemp = completeAddress.substring(0,endIndex);
		String addressTemp2 = completeAddress.substring(endIndex);
		addressTemp =  addressTemp.replaceAll(",", "");
		String address = addressTemp.concat(addressTemp2);
		prepopulateAddress(address, mav);
	}
	private String getFormattedDtTelephoneNum(String dtTelephoneNum) {
		dtTelephoneNum = dtTelephoneNum.replaceAll("-", "");
		dtTelephoneNum = dtTelephoneNum.trim();
		dtTelephoneNum = dtTelephoneNum.substring(0,3)+"-"+dtTelephoneNum.substring(3,6)+"-"+dtTelephoneNum.substring(6);	
		return dtTelephoneNum;
	}

	@RequestMapping(value="/confirmation")
	protected ModelAndView showConfirmation(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try{
		logger.info("showConfirmation_begin");
		SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
		String contextIndicator = salesCenterVo.getValueByName("salesFlow.contextId");
		if(Utils.isBlank(contextIndicator)){
			contextIndicator="NA";
		}
		
		String referrerFlow = "";
		if(salesCenterVo.getValueByName("referrer.flow")!= null){
			referrerFlow = salesCenterVo.getValueByName("referrer.flow");
		}
		if((contextIndicator.equals("00")) || (contextIndicator.equals("05")
				|| referrerFlow.equalsIgnoreCase("nonConfirm")|| referrerFlow.equalsIgnoreCase("agentTransfer"))){
			logger.info("showConfirmation_end");
				String absoluteURL = (String) request.getSession()
						.getAttribute("urlPath");
				return new ModelAndView("redirect:" + absoluteURL
						+ "/salescenter/offer");
		}
		else{
			logger.info("Get_Dialog_Service_started_in_showConfirmation");
			/*
			 * If we want to GUID on Sales Context then last parameter of below method is true otherwise false.
			 */
			Map<String, Map<String, String>> context = SalesDialogueFactory.INSTANCE.updateContextMapWithReferrerAndCallType("Confirmation",salesCenterVo,true);
			
			SalesDialogueVO dialogueVO = DialogServiceUI.INSTANCE.getDialoguesByContext(context);

			salesCenterVo.getValueByName("consumer.name.first");
			StringBuilder events = new StringBuilder();

			List<DataField> dataFields = new ArrayList<DataField>();

			for (Dialogue dialogue : dialogueVO.getDialogueList()){
				List<DataGroup> dgList = dialogue.getDataGroupList();
				for(DataGroup dGroup : dgList){
					for(DataField data : dGroup.getDataFieldList()){
						dataFields.add(data);
					}
				}
			}       

			List<Fieldset> fieldsetList = HtmlFactory.INSTANCE.dialogueToFieldSet(events, dataFields, null);

			for (Fieldset fieldset : fieldsetList) {
				String element = HtmlBuilder.INSTANCE.toString(fieldset);
				element = salesCenterVo.replaceNamesWithValues(element);
				events.append(element);
			}
			
			ModelAndView mav = new ModelAndView();	
			//mav.addObject("address", address);
			mav.addObject("dialogue" , events.toString());
			mav.addObject("Confirm_NonRR_4",salesCenterVo.getValueByName("consumer.email"));
			mav.setViewName("sales.confirmation");
			OrderType order =(OrderType)request.getSession().getAttribute("order");
			mav.addObject("address", SalesUtil.	INSTANCE.getAddress(order.getCustomerInformation().getCustomer(),
					com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString()));
			logger.info("showConfirmation_end");
			return mav;
		}
		}catch (Exception e) {
			logger.error(e);
			logger.warn("Error_in_showConfirmation",e);
			throw new UnRecoverableException(e.getMessage());
		}
	}

	@RequestMapping(value="/offer")
	protected ModelAndView showOffer(HttpServletRequest request,
			HttpServletResponse response) throws UnRecoverableException {
		try{
			ErrorList errorList = new ErrorList();
			logger.info("showOffer_begin");
		    HttpSession session = request.getSession();
		    
		    String emailAddress = null;
			String sendEmail = null;
		    
		    if (session.getAttribute("gotoofferpage") != null ){
		    	
		    	emailAddress = (String)session.getAttribute("Confirm_NonRR_4");
		    	sendEmail = (String)session.getAttribute("sendEmail");
		    	session.removeAttribute("gotoRecommondation");
		    	
		    }else{
		    	
		    	emailAddress = request.getParameter("Confirm_NonRR_4");
		    	sendEmail = request.getParameter("sendEmail");
		    	session.setAttribute("sendEmail", (String)request.getParameter("sendEmail"));
		    	session.setAttribute("Confirm_NonRR_4", (String)request.getParameter("Confirm_NonRR_4"));
		    }
		    
		    logger.info("sendEmail="+sendEmail);
			Long orderId =(Long)request.getSession().getAttribute("orderId");
			Long customerID =(Long)request.getSession().getAttribute("customerID");
			logger.info("emailAddress=" +emailAddress);
			ModelAndView mav = new ModelAndView();
			
			String resubmitvalue =(String)session.getAttribute("submitvalue");
			logger.info("resubmit_value"+resubmitvalue);

			SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
			String agentId= salesCenterVo.getValueByName("Agent");
			OrderType order =(OrderType)request.getSession().getAttribute("order");
			boolean isEmailAddressChanged = false;
			if(Utils.isBlank(emailAddress)&&Utils.isBlank(order.getCustomerInformation().getCustomer().getBestEmailContact())){
				mav.addObject("isEmptyEmail",true);
			}
			else if(! Utils.isBlank(emailAddress)){
				isEmailAddressChanged = WarmTransferFactory.INSTANCE.isEmailAddressUpdated(emailAddress, salesCenterVo);
			}

			com.AL.xml.cm.v4.ObjectFactory oFactory = new com.AL.xml.cm.v4.ObjectFactory();
			CustomerType customer = oFactory.createCustomerType();
			customer.setExternalId(customerID);
			logger.info("best_contact_phone"+order.getCustomerInformation().getCustomer().getBestPhoneContact());
			customer.setBestPhoneContact(order.getCustomerInformation().getCustomer().getBestPhoneContact());
			
			if(Utils.isBlank(emailAddress) && !Utils.isBlank(order.getCustomerInformation().getCustomer().getBestEmailContact())){
				emailAddress = order.getCustomerInformation().getCustomer().getBestEmailContact();
			}
			
			//Is request comes from recommendations page update email address 
			//with his best email address which have been saved earlier.
			else if(!Utils.isBlank(emailAddress) && session.getAttribute("gotoofferpage") != null){
				emailAddress = order.getCustomerInformation().getCustomer().getBestEmailContact();
			}
			
			customer.setBestEmailContact(emailAddress);
			
			if(!Utils.isBlank(emailAddress)){
				EMailAddressType email = new EMailAddressType();
				email.setValue(emailAddress);
				customer.setHomeEMail(email);
			}
			if(!Utils.isBlank(sendEmail)  && sendEmail.equalsIgnoreCase("on")){
				customer.setEMailOptIn(true);
			}
			else{
				customer.setEMailOptIn(false);
			}
			QName NAME = new QName("http://xml.AL.com/v4",
					"offersPresented");
			JAXBElement<String> offerPresented = new JAXBElement<String>(NAME,
					String.class, "yes");
			customer.setOffersPresented(offerPresented);
			if(session.getAttribute("primaryLanguage")!=null){
				customer.setPrimaryLanguage(Integer.valueOf(session.getAttribute("primaryLanguage").toString()));
			}
			
			order.getCustomerInformation().getCustomer().setEMailOptIn(customer.isEMailOptIn());
			order.getCustomerInformation().getCustomer().setBestEmailContact(emailAddress);

			String GUID = (String)request.getSession().getAttribute("GUID");
			//CustomerContextType customerContext = CustomerFactory.INSTANCE.buildCustomerContext("source", "GUID", GUID);
			Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
			Map<String, String> sourceEntity = new HashMap<String, String>();
			sourceEntity.put("source", "salescenter");
			sourceEntity.put("GUID", GUID);
			data.put("source", sourceEntity);
			CustomerContextType customerContext = CustomerFactory.INSTANCE.buildCustomerContext(data);
			if(isEmailAddressChanged){
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
				customer = CustomerService.INSTANCE.submitCustomerType(agentId, customerID.toString(), "updateCustomer", customer, notifEventColl,customerContext, errorList);
			}
			else{
			    customer = CustomerService.INSTANCE.submitCustomerType(agentId, String.valueOf(order.getCustomerInformation().getCustomer().getExternalId()),
					"updateCustomer", customer, null, customerContext, errorList);
			}

			if(errorList != null && errorList.size() > 0){
				for(CartError cartError: errorList){
					logger.info("UpdateCustomerErrorCode="+cartError.getCode()+" ErrorMessage="+cartError.getMessage()
				    		+" ErrorDescription="+cartError.getDescription());
				}
				throw new UnRecoverableException(errorList.get(0).getMessage());
			}

			logger.info("Get_Dialog_Service_started_in_show_Offer");
			
			/*
			 * If we want to GUID on Sales Context then last parameter of below method is true otherwise false.
			 */
			Map<String, Map<String, String>> context = SalesDialogueFactory.INSTANCE.updateContextMapWithReferrerAndCallType("OpeningOffer",salesCenterVo,true);
			
			SalesDialogueVO dialogueVO = DialogServiceUI.INSTANCE.getDialoguesByContext(context);
			
			logger.debug("Get_Dialog_Service_finished_in_show_Offer"+dialogueVO.toString());

			if(emailAddress != null) {
				salesCenterVo.setValueByName("consumer.email", emailAddress);
			}
			
			StringBuilder events = new StringBuilder();
			List<DataField> dataFields = new ArrayList<DataField>();

			for (Dialogue dialogue : dialogueVO.getDialogueList()){
				List<DataGroup> dgList = dialogue.getDataGroupList();
				for(DataGroup dGroup : dgList){
					for(DataField dataField : dGroup.getDataFieldList()){
						dataFields.add(dataField);
					}
				}
			}       

			List<Fieldset> fieldsetList = HtmlFactory.INSTANCE.dialogueToFieldSet(events, dataFields, null);

			for (Fieldset fieldset : fieldsetList) {
				String element = HtmlBuilder.INSTANCE.toString(fieldset);
				//element = salesCenterVo.replaceNamesWithValues(element);
				events.append(element);
			}

			List<ProductSummaryVO> details = new ArrayList<ProductSummaryVO>();
			String result = salesCenterVo.replaceNamesWithValues(events.toString());

			ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");
			HashMap<String, List<ProductSummaryVO>> saversMap = (HashMap<String, List<ProductSummaryVO>>)productResultManager.getSaversOfferMap();
			for (Entry<String, List<ProductSummaryVO>> entry : saversMap.entrySet()){
				details.addAll(entry.getValue());
			}
			//details = (List<SalesProductDetailsVO>) productResultManager.getProductByIconMap().get("OFFERS");

			logger.info("details=" +details);
			String offers = "";
			if (details != null) {
				offers = getOffersData(details);
				//utilityOffersCnt = getUtilityOffersCnt(details);
			}
			logger.info("offers=" + offers.toString());
			logger.info("result" + result);
			if (result != null && result.contains("includes:")){
				result = result.replaceFirst("includes:", "includes: "+offers);
			}

			if (details != null && orderId !=null) {
				mav.addObject("detailsOfferJson", createOfferJSON(getOffersProductList(details), orderId));	
			}

			mav.addObject("address", SalesUtil.INSTANCE.getAddress(order.getCustomerInformation().getCustomer(),
					com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString()));
			
			
			mav.addObject("dialogue" , result);
			
			boolean isPartnerSpecificMatchReqForUtility = ((Boolean)session.getAttribute("isPartnerSpecificMatchReqForUtility")).booleanValue();
			
			logger.info("isPartnerSpecificMatchReqForUtility="   +isPartnerSpecificMatchReqForUtility);
			if (isPartnerSpecificMatchReqForUtility){
				if((productResultManager.getUtilityOffersMap()!= null) && (productResultManager.getUtilityOffersMap().size()>0)){
					Map<String, String> partnerSpecificDataMap = (Map<String, String>)request.getSession().getAttribute("partnerSpecificDataMap");
					partnerSpecificDataMap = sortByComparator(partnerSpecificDataMap);
					logger.info("partnerSpecificDataMap="+partnerSpecificDataMap);
					List<ProductSummaryVO> psVOList = new ArrayList<ProductSummaryVO>();
					for (Entry<String,String> entry: partnerSpecificDataMap.entrySet()){
						for (ProductSummaryVO psVO :productResultManager.getUtilityOffersMap().get("UTILITY")){
							String psMetaData = null;
							List<String> metaDataList = psVO.getPromotionMetaDataList();
							for(String metaData : metaDataList){
								String[] productDataVal = null;
								if (metaData != null &&  metaData.contains("=")){
									productDataVal = metaData.split("=");
									if (productDataVal[0] != null && productDataVal[0].toString().equalsIgnoreCase("PS_PRODUCT")){
										psMetaData = productDataVal[1];            
									}
								}
							}
							if (entry.getKey().equalsIgnoreCase(psMetaData)){
								psVOList.add(psVO);
								productResultManager.getUtilityOffersMap().clear();
								productResultManager.getUtilityOffersMap().put("UTILITY", psVOList);
								break;
							}
						}
					}
				}
			}
			logger.info("productResultManager.getUtilityOffersMap()="+productResultManager.getUtilityOffersMap());
			if (productResultManager.getSaversOfferMap().size() == 0 && productResultManager.getUtilityOffersMap().size() == 0){
				request.getSession().setAttribute("utilityOffer" , "false");
				request.getSession().setAttribute("offer" , "false");
				logger.info("showOffer_end");
				String absoluteURL = (String) request.getSession()
						.getAttribute("urlPath");
				return new ModelAndView("redirect:" + absoluteURL
						+ "/salescenter/qualification");
			}else if(productResultManager.getSaversOfferMap().size() == 0 && productResultManager.getUtilityOffersMap().size() != 0){
				com.AL.xml.v4.CustAddress cust = SalesUtil.INSTANCE.getAddress(order.getCustomerInformation().getCustomer(),
						com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString());
				String billingInfo = String.valueOf(order.getCustomerInformation().getCustomer()
						.getBillingInfoList().getBillingInfo().get(0).getExternalId());
				String svcAddressExtId = String.valueOf(cust.getAddress().getExternalId());
				order = addUtitlityOffers( String.valueOf(orderId),productResultManager.getUtilityOffersMap().get("UTILITY"),billingInfo,svcAddressExtId,session,agentId);
				String lineItemIds = "";
				String providerIds = "";
				String productSrcs = "";

				if(order.getLineItems().getLineItem()!=null){
					logger.info("when_order.getlineitems_is_not_null");
					List<LineItemType> lineItemList = order.getLineItems().getLineItem();
					for(LineItemType lit: lineItemList){
						String productType = SalesUtilsFactory.INSTANCE.getLineItemAttr(lit,"PRODUCT_TYPE","TYPE");
						if(productType != null && productType.equalsIgnoreCase("UtilityOffer")){
							if(lit.getProductDatasource() != null && lit.getLineItemDetail() != null){
								lineItemIds = lineItemIds + lit.getExternalId()+ "," ;
								providerIds = providerIds + lit.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId()+ "," ;
								productSrcs = productSrcs + lit.getProductDatasource().getValue()+ "," ;
							}
						}
					}
				}
				logger.info("lineItemIds="+lineItemIds);
				logger.info("providerIds="+providerIds);
				logger.info("productSrcs="+productSrcs);
				request.getSession().setAttribute("offer" , "true");
				request.getSession().setAttribute("lineItemIds", lineItemIds.substring(0, lineItemIds.length()-1));
				request.getSession().setAttribute("providerIds", providerIds.substring(0, providerIds.length()-1));
				request.getSession().setAttribute("productSrcs", productSrcs.substring(0, productSrcs.length()-1));
				request.getSession().setAttribute("productSrcs", productSrcs.substring(0, productSrcs.length()-1));
				request.getSession().setAttribute("utilityOffer" , "true");
				request.getSession().setAttribute("callTimeBeforeUtility", Double.valueOf(Calendar.getInstance().getTimeInMillis()));
				logger.info("showOffer_end");
				String absoluteURL = (String) request.getSession()
						.getAttribute("urlPath");
				return new ModelAndView("redirect:"
						+ absoluteURL
						+ "/salescenter/CKO/"
						+ order.getCustomerInformation().getCustomer()
								.getExternalId() + "/order/"
						+ order.getExternalId());
			}else{
				mav.addObject("address", SalesUtil.	INSTANCE.getAddress(order.getCustomerInformation().getCustomer(),
						com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString()));
				mav.setViewName("sales.offer");
				mav.addObject("CKOCompletedLineItems" , LineItemUtil.prepareCKOCompletedLinItemsListFromOrder(order));
				if(Utils.isBlank(customer.getBestEmailContact())){
					mav.addObject("isEmailEmpty","true");
				}else{
					mav.addObject("isEmailEmpty","false");
				}
				logger.info("showOffer_end");
				return mav;
			}
		}catch (Exception e) {
			logger.warn("Error_in_showOffer",e);
			logger.error(e);
			throw new UnRecoverableException(e.getMessage());
		}
	}

	public JSONObject  createOfferJSON(List<ProductSummaryVO> details, Long orderId) throws Exception {
		
		JSONObject mainJson = new JSONObject();
		
		try {
			JSONArray jsonArray = new  JSONArray();
			
			for(ProductSummaryVO detailsVO : details){
				JSONObject prodJson = new JSONObject();
				prodJson.put("orderId", orderId);
				prodJson.put("partnerExternalId", escapeSpecialCharacters(detailsVO.getProviderExternalId()));
				prodJson.put("productExernalId",escapeSpecialCharacters(detailsVO.getExternalId()));
				prodJson.put("productname",escapeSpecialCharacters(detailsVO.getName()));
				prodJson.put("isPromotion", false);
				prodJson.put("appliesTo", 0L);
				prodJson.put("providerName", escapeSpecialCharacters(detailsVO.getProviderName()));
				prodJson.put("providerSourceBaseType", detailsVO.getSource());
				jsonArray.put(prodJson);
			}
			
			mainJson.put("index", jsonArray);
			logger.info("mainJson="+mainJson.toString());

		} catch (JSONException e) {
			throw new Exception("JSON Exception: "+e.getMessage());
		}
		return mainJson;
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
		}
		return str;
	}
	

	private String getOffersData(List<ProductSummaryVO> details) {
		StringBuilder offers = new StringBuilder();
		int i = 0;
		for (ProductSummaryVO mdo : details){
			if (i == 2) {
				break;
			}
			if (mdo.getPromotionMetaDataList() != null && mdo.getPromotionMetaDataList().size() > 0){
				for(String metaData :mdo.getPromotionMetaDataList()){
					if (metaData != null && metaData.equalsIgnoreCase("OFFER_PHASE=OpeningOffer")){
						if (mdo != null && mdo.getMarketingHighlightsList() != null && mdo.getMarketingHighlightsList().size() > 0) {
							offers.append("<li style=\"font-size: 16px; font-weight:bold; margin-left: 20px;\">"+mdo.getMarketingHighlightsList().get(0)+"</li> ");
							i++;
						}	
					}
				}
			}
			logger.info("offers=" +offers.toString());
		}
		return offers.toString();
	}

	private List<ProductSummaryVO> getOffersProductList(List<ProductSummaryVO> details) {
		List<ProductSummaryVO> detailsOffer = new ArrayList<ProductSummaryVO>();
		int i = 0;
		for (ProductSummaryVO mdo : details){
			if (i == 2) {
				break;
			}
			if (mdo.getPromotionMetaDataList() != null && mdo.getPromotionMetaDataList().size() > 0){
				for(String metaData :mdo.getPromotionMetaDataList()){
					if (metaData != null && metaData.equalsIgnoreCase("OFFER_PHASE=OpeningOffer")){
						if (mdo != null && mdo.getMarketingHighlightsList() != null && mdo.getMarketingHighlightsList().size() > 0) {
							detailsOffer.add(mdo);
							i++;
						}	
					}
				}
			}
		}
		return detailsOffer;
	}


	@RequestMapping(value="/utilityoffer")
	protected ModelAndView showUtilityOffer(HttpServletRequest request,
			HttpServletResponse response) throws UnRecoverableException {
		try{
			logger.info("showUtilityOffer_begin");
		HttpSession session = request.getSession();
		boolean isFromRecomandationPage = false;
		
		String submit = request.getParameter("Opening_Offer_NonRR_2");
		String email = request.getParameter("email");
		ErrorList errorList = new ErrorList();
	    if (session.getAttribute("gotoofferpage") != null ){
	    	
	    	isFromRecomandationPage = true;
	    	
	    	session.removeAttribute("gotoofferpage");
	    	session.setAttribute("gotoRecommondation", "yes");
	    	
	    }else{
	    	isFromRecomandationPage = false;
	    }

		SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");

		
		if (Utils.isBlank(submit)){
			session.setAttribute("displayButton",false);
		}else if (!Utils.isBlank(submit) && submit.equalsIgnoreCase("NO")){
			session.setAttribute("displayButton",false);
		}else {
			session.setAttribute("displayButton",true);
		}
		logger.info("email="+submit);
		
		Long customerID =(Long)request.getSession().getAttribute("customerID");
		String agentId= salesCenterVo.getValueByName("Agent");
		OrderType order2 =(OrderType)request.getSession().getAttribute("order");
		com.AL.xml.cm.v4.ObjectFactory oFactory1 = new com.AL.xml.cm.v4.ObjectFactory();
		CustomerType customer = oFactory1.createCustomerType();
		customer.setExternalId(customerID);
		logger.info("order2.getCustomerInformation().getCustomer().getBestEmailContact()_before"+order2.getCustomerInformation().getCustomer().getBestEmailContact());
		
		if(order2.getCustomerInformation().getCustomer().getBestPhoneContact() != null){
			customer.setBestPhoneContact(order2.getCustomerInformation().getCustomer().getBestPhoneContact());	
		}
		EMailAddressType emailObj = new EMailAddressType();
		if(!Utils.isBlank(email)){
			customer.setBestEmailContact(email);
			String hemail = "";
			if(!Utils.isBlank(email)){
			    hemail = email;
			    emailObj.setValue(hemail);
				customer.setHomeEMail(emailObj);
			}
		}
		else{
			customer.setBestEmailContact(order2.getCustomerInformation().getCustomer().getBestEmailContact());
			String hemail = "";
			if(!Utils.isBlank(order2.getCustomerInformation().getCustomer().getBestEmailContact())){
			    hemail = order2.getCustomerInformation().getCustomer().getBestEmailContact();
			    emailObj.setValue(hemail);
				customer.setHomeEMail(emailObj);
			}
			
		}
		customer.setEMailOptIn(order2.getCustomerInformation().getCustomer().isEMailOptIn());
		if((!Utils.isBlank(submit)) && (submit.equalsIgnoreCase("Yes"))){
			customer.setMarketingOptIn(true);
		}
		else{
			customer.setMarketingOptIn(false);
		}
		if(session.getAttribute("primaryLanguage")!=null){
			customer.setPrimaryLanguage(Integer.valueOf(session.getAttribute("primaryLanguage").toString()));
		}
		logger.info("order2.getCustomerInformation().getCustomer().getBestEmailContact()_after"+order2.getCustomerInformation().getCustomer().getBestEmailContact());
		String GUID = (String)request.getSession().getAttribute("GUID");
		//CustomerContextType customerContext = CustomerFactory.INSTANCE.buildCustomerContext("source", "GUID", GUID);
		Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
		Map<String, String> sourceEntity = new HashMap<String, String>();
		sourceEntity.put("source", "salescenter");
		sourceEntity.put("GUID", GUID);
		data.put("source", sourceEntity);
		CustomerContextType customerContext = CustomerFactory.INSTANCE.buildCustomerContext(data);
		customer = CustomerService.INSTANCE.submitCustomerType(agentId, String.valueOf(order2.getCustomerInformation().getCustomer().getExternalId()),
				"updateCustomer", customer, null,customerContext, errorList);
		if(errorList != null && errorList.size() > 0){
			for(CartError cartError: errorList){
				logger.info("UpdateCustomerErrorCode="+cartError.getCode()+" ErrorMessage="+cartError.getMessage()
			    		+" ErrorDescription="+cartError.getDescription());
			}
			throw new UnRecoverableException(errorList.get(0).getMessage());
		}
		logger.info("customer_best_contact"+customer.getBestPhoneContact());
		order2.getCustomerInformation().getCustomer().setMarketingOptIn(customer.isMarketingOptIn());
		//order2.getCustomerInformation().getCustomer().setBestPhoneContact(customer.getBestPhoneContact());

		if (!Utils.isBlank(submit)){
			request.getSession().setAttribute("submitvalue", submit);	
		}
		String offerJson = request.getParameter("offerJson");
		logger.info("offerJson="+offerJson);  
		logger.info("submit="+submit);  
		if(submit != null && submit.equalsIgnoreCase("Yes")){
			String productData = offerJson;
			com.AL.xml.v4.ObjectFactory oFactory = new com.AL.xml.v4.ObjectFactory();
			LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();
			String orderExtId = null;
			try {
				if(!productData.equals("")){
					JSONObject index = new JSONObject(productData);
					JSONArray selectedArray = index.getJSONArray("index");

					for(int i=0;i < selectedArray.length(); i++){
						String jsonString=selectedArray.getString(i);
						JSONObject feedback =new JSONObject(jsonString);
						//String appliesTo = feedback.getString("appliesTo");
						String productExernalId = feedback.getString("productExernalId");
						//Boolean isPromotion = feedback.getBoolean("isPromotion");
						String partnerExternalId = feedback.getString("partnerExternalId");
						//String productUniqueId = feedback.getString("productUniqueId");
						String providerName = feedback.getString("providerName");
						String productname = feedback.getString("productname");
						orderExtId = feedback.getString("orderId");
						String providerSourceBaseType = feedback.getString("providerSourceBaseType");
							
						
						LineItemType lineItemType=oFactory.createLineItemType();
						lineItemType.setPartnerName(providerName);
						lineItemType.setLineItemNumber(0);
						lineItemType.setExternalId(0L);
						lineItemType.setService(ServiceType.BUSINESS);
						if(order2 != null){
						 com.AL.xml.v4.CustAddress cust = SalesUtil.INSTANCE.getAddress(order2.getCustomerInformation().getCustomer(),
									com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString());
							String billingInfo = String.valueOf(order2.getCustomerInformation().getCustomer()
									.getBillingInfoList().getBillingInfo().get(0).getExternalId());
							String svcAddressExtId = String.valueOf(cust.getAddress().getExternalId());
						lineItemType.setBillingInfoExtId(billingInfo);
						lineItemType.setSvcAddressExtId(svcAddressExtId);
						}

						LineItemAttributeType lineItemAttributeType=oFactory.createLineItemAttributeType();
						
						lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType("TYPE", "SaversOffer",
								"", "PRODUCT_TYPE"));
						lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType("Display", "false",
								"", "provider_feedback"));
						lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType("STATUS", "CKOComplete",
								"CKO COMPLETED", "CKO"));
						
						lineItemType.setLineItemAttributes(lineItemAttributeType);

						LineItemDetailType lineItemDetailType = oFactory.createLineItemDetailType();
						lineItemDetailType.setDetailType("product");
						lineItemDetailType.setProductUniqueId(null);
						
						ProviderSourceType pstValue = oFactory.createProviderSourceType();
						pstValue.setDatasource(providerName);
						pstValue.setValue(CartLineItemFactory.INSTANCE.getProviderSourceBaseType(providerSourceBaseType));
						lineItemType.setProductDatasource(pstValue);

						OrderLineItemDetailTypeType orderLineItemDetailTypeType = oFactory.createOrderLineItemDetailTypeType();
						LinkableType productType = oFactory.createLinkableType();
						productType.setExternalId(productExernalId);
						productType.setName(productname);
						ProviderType providerType = oFactory.createProviderType();
						providerType.setExternalId(partnerExternalId);
						providerType.setName(providerName);
						productType.setProvider(providerType);
						orderLineItemDetailTypeType.setProductLineItem(productType);
						lineItemDetailType.setDetail(orderLineItemDetailTypeType);
						lineItemType.setLineItemDetail(lineItemDetailType);

						liCollection.getLineItem().add(lineItemType);
					}
					if(liCollection.getLineItem().size()>0){
						/*	
						 * Getting SalesContext from the session				 
						 */
						SalesContextType salesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);
						/*	
						 * ServiceCall for adding LineItem to Order					 
						 */
						OrderType order = LineItemService.INSTANCE.addLineItem(agentId, orderExtId, liCollection,salesContext);
					}
				}

			} catch (JSONException e) {
				logger.warn("Error_in_showUtilityOffer",e);
				logger.error(e);
				throw new UnRecoverableException(e.getMessage());
			}
		}
		String orderId = String.valueOf(order2.getExternalId());
		OrderType order = OrderService.INSTANCE.getOrderByOrderNumber(orderId, agentId, null,null,false, null);
		request.getSession().setAttribute("order" , order);
		String referrerId = salesCenterVo.getValueByName("referrer.businessParty.referrerId");
		
		ConfirmReferrersVO confirmReferrerVO = confirmReferrersDao.getConfirmReferrers(Long.valueOf(referrerId));
		String confirmReferrer = "false";
		if((confirmReferrerVO!= null)&&(confirmReferrerVO.getReferrerId()!=null)){
			confirmReferrer = "true";
		}
		String contextIndicator = salesCenterVo.getValueByName("salesFlow.contextId");
		if(Utils.isBlank(contextIndicator)){
			contextIndicator="NA";
		}
		if((confirmReferrer.equalsIgnoreCase("false"))&&(contextIndicator.equals("00")) || (contextIndicator.equals("05"))){
			
			request.getSession().setAttribute("utilityOffer" , "false");
			request.getSession().setAttribute("offer" , "true");
				String absoluteURL = (String) request.getSession()
						.getAttribute("urlPath");
			if (isFromRecomandationPage){
					return new ModelAndView("redirect:" + absoluteURL
							+ "/salescenter/recommendations");
			}else{
					return new ModelAndView("redirect:" + absoluteURL
							+ "/salescenter/qualification");
			}
		}
		else{
			
			List<ProductSummaryVO> details = new ArrayList<ProductSummaryVO>();

			ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");
			details = (List<ProductSummaryVO>) productResultManager.getProductByIconMap().get("OFFERS");
			logger.info("details=" +details);
			logger.info("productResultManager="+productResultManager.getUtilityOffersMap().size());
			if ( productResultManager.getUtilityOffersMap().size() == 0){
				request.getSession().setAttribute("utilityOffer" , "false");
				request.getSession().setAttribute("offer" , "true");
					String absoluteURL = (String) request.getSession()
							.getAttribute("urlPath");
				if (isFromRecomandationPage){
						return new ModelAndView("redirect:" + absoluteURL
								+ "/salescenter/recommendations");
				}else{
						return new ModelAndView("redirect:" + absoluteURL
								+ "/salescenter/qualification");
				}
			}else{
				OrderType order1 =(OrderType)request.getSession().getAttribute("order");
				 orderId= String.valueOf(order1.getExternalId());
				 com.AL.xml.v4.CustAddress cust = SalesUtil.INSTANCE.getAddress(order.getCustomerInformation().getCustomer(),
							com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString());
					String billingInfo = String.valueOf(order.getCustomerInformation().getCustomer()
							.getBillingInfoList().getBillingInfo().get(0).getExternalId());
					String svcAddressExtId = String.valueOf(cust.getAddress().getExternalId());
					
				 order = addUtitlityOffers(orderId,productResultManager.getUtilityOffersMap().get("UTILITY"),billingInfo,svcAddressExtId,session,agentId);
				String lineItemIds = "";
				String providerIds = "";
				String productSrcs = "";

				if(order.getLineItems().getLineItem()!=null){
					logger.info("when_order.getlineitems_is_not_null");
					List<LineItemType> lineItemList = order.getLineItems().getLineItem();
					for(LineItemType lit: lineItemList){
						String productType = SalesUtilsFactory.INSTANCE.getLineItemAttr(lit,"PRODUCT_TYPE","TYPE");
						if(productType != null && productType.equalsIgnoreCase("UtilityOffer")){
							if(lit.getProductDatasource() != null && lit.getLineItemDetail() != null){
								lineItemIds = lineItemIds + lit.getExternalId()+ "," ;
								providerIds = providerIds + lit.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId()+ "," ;
								productSrcs = productSrcs + lit.getProductDatasource().getValue()+ "," ;
							}
						}
					}
				}
				logger.info("lineItemIds="+lineItemIds);
				logger.info("providerIds="+providerIds);
				logger.info("productSrcs="+productSrcs);
				request.getSession().setAttribute("lineItemIds", lineItemIds.substring(0, lineItemIds.length()-1));
				request.getSession().setAttribute("providerIds", providerIds.substring(0, providerIds.length()-1));
				request.getSession().setAttribute("productSrcs", productSrcs.substring(0, productSrcs.length()-1));
				request.getSession().setAttribute("productSrcs", productSrcs.substring(0, productSrcs.length()-1));
				request.getSession().setAttribute("utilityOffer" , "true");
				request.getSession().setAttribute("offer" , "true");
				request.getSession().setAttribute("callTimeBeforeUtility", Double.valueOf(Calendar.getInstance().getTimeInMillis()));
					String absoluteURL = (String) request.getSession()
							.getAttribute("urlPath");
				if (isFromRecomandationPage){
					logger.info("showUtilityOffer_end");
						return new ModelAndView("redirect:" + absoluteURL
								+ "/salescenter/recommendations");
				}else{
					logger.info("showUtilityOffer_end");
						return new ModelAndView("redirect:"
								+ absoluteURL
								+ "/salescenter/CKO/"
								+ order.getCustomerInformation().getCustomer()
										.getExternalId() + "/order/"
								+ order.getExternalId());
				}
			}
		}
	}catch (Exception e) {
		logger.warn("Error_in_showUtilityOffer",e);
		logger.error(e);
		throw new UnRecoverableException(e.getMessage());
	}
	}


	private OrderType addUtitlityOffers(String orderId, List<ProductSummaryVO> list, String billingInfo, String svcAddressExtId,HttpSession session,String agentId) {


		com.AL.xml.v4.ObjectFactory oFactory = new com.AL.xml.v4.ObjectFactory();

		LineItemType lineItemType= buildLineItem(oFactory,list.get(0),billingInfo,svcAddressExtId);

		LineItemCollectionType liCollection = oFactory.createLineItemCollectionType();
		liCollection.getLineItem().add(lineItemType);

		OrderType order = null;
		if(liCollection.getLineItem().size() > 0){
			/*	
			 * Getting SalesContext from the session				 
			 */
			SalesContextType salesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);
			
			/*	
			 * ServiceCall for adding LineItem to Order					 
			 */
			order = LineItemService.INSTANCE.addLineItem(agentId, orderId, liCollection,salesContext);
		}

		return order;
	}

	private LineItemType buildLineItem(com.AL.xml.v4.ObjectFactory oFactory, ProductSummaryVO salesProductDetailsVO, String billingInfo, String svcAddressExtId) {
		String partnerExternalId = salesProductDetailsVO.getProviderExternalId();
		String partnerName = salesProductDetailsVO.getProviderName();
		String productName = salesProductDetailsVO.getName();
		String productExternalId  = salesProductDetailsVO.getExternalId();
		String providerSourceBaseType = salesProductDetailsVO.getSource();
		Long productUniqueId = System.currentTimeMillis();

		LineItemType lineItemType= oFactory.createLineItemType();

		lineItemType.setPartnerName(partnerName);
		lineItemType.setLineItemNumber(0);
		lineItemType.setExternalId(0L);
		lineItemType.setBillingInfoExtId(billingInfo);
		lineItemType.setSvcAddressExtId(svcAddressExtId);
		
		lineItemType.setService(ServiceType.BUSINESS);

		ProviderSourceType pstValue = oFactory.createProviderSourceType();
		pstValue.setValue(CartLineItemFactory.INSTANCE.getProviderSourceBaseType(providerSourceBaseType));
		lineItemType.setProductDatasource(pstValue);

		LineItemDetailType lineItemDetailType = new LineItemDetailType();
		lineItemDetailType.setDetailType("product");
		lineItemDetailType.setProductUniqueId(productUniqueId);

		LineItemAttributeType lineItemAttributeType=oFactory.createLineItemAttributeType();
		lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType("TYPE", "UtilityOffer",
				"", "PRODUCT_TYPE"));
		lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType("Display", "false",
				"", "provider_feedback"));
		lineItemAttributeType.getEntity().add(CartLineItemFactory.INSTANCE.setAttributeEntityType("STATUS", "CKOReady",
				"CKO IS READY FOR CKO PROCESSING", "CKO"));
		
		lineItemType.setLineItemAttributes(lineItemAttributeType);

		OrderLineItemDetailTypeType orderLineItemDetailTypeType = new OrderLineItemDetailTypeType();
		LinkableType productType = new LinkableType();
		productType.setExternalId(productExternalId);
		productType.setName(productName);
		ProviderType providerType = new ProviderType();
		providerType.setExternalId(partnerExternalId);
		providerType.setName(partnerName);
		productType.setProvider(providerType);
		orderLineItemDetailTypeType.setProductLineItem(productType);
		lineItemDetailType.setDetail(orderLineItemDetailTypeType);
		lineItemType.setLineItemDetail(lineItemDetailType);
		LineItemPriceInfoType liPriceInfo = oFactory.createLineItemPriceInfoType();
		liPriceInfo.setBaseNonRecurringPrice(salesProductDetailsVO.getBaseNonRecurringPrice());
		liPriceInfo.setBaseRecurringPrice(salesProductDetailsVO.getBaseRecurringPrice());
		lineItemType.setLineItemPriceInfo(liPriceInfo);
		return lineItemType;
	}

	@RequestMapping(value="/qualification")
	protected ModelAndView showQualification(HttpServletRequest request,
			HttpServletResponse response) throws UnRecoverableException {
		try{
		logger.info("showQualification_begin");
		HttpSession session = request.getSession();
		if(session.getAttribute("isServiceChecked") != null){
			if((String)session.getAttribute("isServiceChecked") != null){
				session.setAttribute("typeOfService", null);
				session.setAttribute("isServiceChecked", null);
			}
		}
		
		String typeOfService = "";
		if(session.getAttribute("typeOfService")!= null){
		    typeOfService = (String)session.getAttribute("typeOfService");
		}
		session.setAttribute("fromQualification","yes");
		String header = (String)request.getHeader("referer");
		Map<String, String> discoveryTransfer = null;
		//getting parameters from the DiscoveryTransfer 
		if (!Utils.isBlank(typeOfService) && typeOfService.equals("existingService")) {

			String currentTV = request.getParameter("Discovery_Transfer_NonRR_1");
			String currentInternet  = request.getParameter("Discovery_Transfer_NonRR_2");
			String monthlyBill = request.getParameter("Discovery_Transfer_NonRR_3");
			String noTVS = request.getParameter("Discovery_Transfer_NonRR_4");
			String noHD = request.getParameter("Discovery_Transfer_NonRR_5");
			String noDVRs = request.getParameter("Discovery_Transfer_NonRR_6");
			String channels = request.getParameter("Discovery_Transfer_NonRR_7");
			String movieChannels = request.getParameter("Discovery_Transfer_NonRR_8");
			String internet = request.getParameter("Discovery_Transfer_NonRR_9");
			String internetUsage = request.getParameter("Discovery_Transfer_NonRR_10");
			String devices = request.getParameter("Discovery_Transfer_NonRR_11");
			String homePhone = request.getParameter("Discovery_Transfer_NonRR_12");

			discoveryTransfer=new LinkedHashMap<String, String>();

			discoveryTransfer.put("Current TV", currentTV);
			discoveryTransfer.put("Current Internet", currentInternet);
			discoveryTransfer.put("Current total monthly bill", monthlyBill);
			discoveryTransfer.put("#TVS", noTVS);
			discoveryTransfer.put("#HD", noHD);
			discoveryTransfer.put("#DVR", noDVRs);
			discoveryTransfer.put("Channels", channels);
			discoveryTransfer.put("Movie Channels",movieChannels);
			discoveryTransfer.put("Internet", internet );
			discoveryTransfer.put("Internet Usage", internetUsage);
			discoveryTransfer.put("#Devices", devices );
			discoveryTransfer.put("Home Phone", homePhone);
            if(session.getAttribute("questionList") != null){
            	
            }
            else if (session.getAttribute("questionList") == null || (header !=null && header.endsWith("/discovery"))){
				session.setAttribute("questionList", discoveryTransfer);
			}
		}
		
		Map<String, String> discoveryNew = null;
		
		if (!Utils.isBlank(typeOfService) && typeOfService.equals("newService")) {
			//getting parameters from the DiscoveryNew 
			String firstField = request.getParameter("Discovery_New_NonRR_1");
			String secondField = request.getParameter("Discovery_New_NonRR_2");
			String thirdField = request.getParameter("Discovery_New_NonRR_3");
			String fourthField = request.getParameter("Discovery_New_NonRR_4");
			String fifthField = request.getParameter("Discovery_New_NonRR_5");
			String sixthField = request.getParameter("Discovery_New_NonRR_6");
			String seventhField = request.getParameter("Discovery_New_NonRR_7");
			String eightthField = request.getParameter("Discovery_New_NonRR_8");
			String ninethField = request.getParameter("Discovery_New_NonRR_9");

			discoveryNew= new LinkedHashMap<String, String>();

			discoveryNew.put("#TVS", firstField);
			discoveryNew.put("#HD", secondField);
			discoveryNew.put("#DVR", thirdField);
			discoveryNew.put("Channels", fourthField);
			discoveryNew.put("Movie Channels", fifthField);
			discoveryNew.put("Internet", sixthField);
			discoveryNew.put("Internet Usage", seventhField);	
			discoveryNew.put("#Devices", eightthField );
			discoveryNew.put("Home Phone", ninethField);
            if(session.getAttribute("questionList") != null){
            }
            else if (session.getAttribute("questionList") == null || (header !=null && header.endsWith("/discovery"))){
				session.setAttribute("questionList", discoveryNew);
			}	
		}
		SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
		logger.info("salesCenterVo="+salesCenterVo);
		/*
		 * If we want to GUID on Sales Context then last parameter of below method is true otherwise false.
		 */
		Map<String, Map<String, String>> context = SalesDialogueFactory.INSTANCE.updateContextMapWithReferrerAndCallType("ScriptedQualification",salesCenterVo,true);
		
		SalesDialogueVO dialogueVO = DialogServiceUI.INSTANCE.getDialoguesByContext(context);
		
		logger.info("DialogueVO="+dialogueVO.toString());
		
		OrderType order =(OrderType)request.getSession().getAttribute("order");

		List<String> sqProvidersList = new ArrayList<String>();
		List<String> resultSqProvidersList = new ArrayList<String>();
		StringBuffer strProviders = new StringBuffer();
		ProductSummaryVO tripleDetail = null;
		ProductSummaryVO doubleDetail = null;

		ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");
		logger.info("productResultManager.getProductByIconMap()=" +productResultManager.getProductByIconMap());

		//show highest rated bundle (i.e show highest rated triple play; if triple_play is not present show highest rated double_play)		
		if (productResultManager.getProductByIconMap().get("TRIPLE_PLAY") != null &&
				productResultManager.getProductByIconMap().get("TRIPLE_PLAY").size() > 0){
			tripleDetail = (ProductSummaryVO) productResultManager.getProductByIconMap().get("TRIPLE_PLAY").get(0);
		}	
		if (productResultManager.getProductByIconMap().get("DOUBLE_PLAY") != null &&
				productResultManager.getProductByIconMap().get("DOUBLE_PLAY").size() > 0){
			doubleDetail = (ProductSummaryVO) productResultManager.getProductByIconMap().get("DOUBLE_PLAY").get(0);
		}
		//if we have both triple play and double play products, then pick the highest scored bundle
		if (tripleDetail != null && doubleDetail != null){
			if(tripleDetail.getScore() >= doubleDetail.getScore()) {
				sqProvidersList.add(getProviderName(tripleDetail));
			} else {
				sqProvidersList.add(getProviderName(doubleDetail));
			}
		} else if(tripleDetail != null) {
			//if double play products are not found and triple play products are found, pick highest rated triple play
			sqProvidersList.add(getProviderName(tripleDetail));
		} else if(doubleDetail != null) {
			//if triple play products are not found and double play products are found, pick highest rated double play
			sqProvidersList.add(getProviderName(doubleDetail));
		}
		if (productResultManager.getProductByIconMap().get("VIDEO") != null &&
				productResultManager.getProductByIconMap().get("VIDEO").size() > 0){
			List<ProductSummaryVO> list = productResultManager.getProductByIconMap().get("VIDEO");
			for (ProductSummaryVO productVo : list ) {
				String providerName = getProviderName(productVo);
				if(!sqProvidersList.contains(providerName)) {
					sqProvidersList.add(providerName);
					break;
				}
			}
		}
		if (productResultManager.getProductByIconMap().get("INTERNET") != null &&
				productResultManager.getProductByIconMap().get("INTERNET").size() > 0){
			List<ProductSummaryVO> list = productResultManager.getProductByIconMap().get("INTERNET");
			for (ProductSummaryVO productVo : list ) {
				String providerName = getProviderName(productVo);
				if(!sqProvidersList.contains(providerName)) {
					sqProvidersList.add(providerName);
					break;
				}
			}
		}
		if (productResultManager.getProductByIconMap().get("PHONE") != null &&
				productResultManager.getProductByIconMap().get("PHONE").size() > 0){
			List<ProductSummaryVO> list = productResultManager.getProductByIconMap().get("PHONE");
			for (ProductSummaryVO productVo : list ) {
				String providerName = getProviderName(productVo);
				if(!sqProvidersList.contains(providerName)) {
					sqProvidersList.add(providerName);
					break;
				}
			}					
		}
		//need to show max 4 providers only
		if (productResultManager.getProductByIconMap().get("HOMESECURITY") != null &&
				productResultManager.getProductByIconMap().get("HOMESECURITY").size() > 0 && sqProvidersList.size() <= 3){
			List<ProductSummaryVO> list = productResultManager.getProductByIconMap().get("HOMESECURITY");
			for (ProductSummaryVO productVo : list ) {
				String providerName = getProviderName(productVo);
				if(!sqProvidersList.contains(providerName)) {
					sqProvidersList.add(providerName);
					break;
				}
			}								
		}
		logger.info("sqProvidersList=" +sqProvidersList);
		if (sqProvidersList != null && sqProvidersList.size() >= 2) {
			for (int i =0; i<= sqProvidersList.size()-1; i ++){
				String provider = sqProvidersList.get(i);
				if(provider.equalsIgnoreCase("ATTSTI")){
					provider = "AT&T";
				}else if(provider.equalsIgnoreCase("Dish Network")){
					provider  =  "Dish";
				}else if(provider.equalsIgnoreCase("G2B-Comcast")){
					provider = "Comcast";
				}
				resultSqProvidersList.add(provider);
			}
		}else if (sqProvidersList != null && sqProvidersList.size() > 0 ){
			String provider = sqProvidersList.get(0);
			if(provider.equalsIgnoreCase("ATTSTI")){
				provider = "AT&T";
			}else if(provider.equalsIgnoreCase("Dish Network")){
				provider  =  "Dish";
			}else if(provider.equalsIgnoreCase("G2B-Comcast")){
				provider = "Comcast";
			}
			resultSqProvidersList.add(provider);
		}
		
		resultSqProvidersList = removeDuplicates(resultSqProvidersList);
		if (resultSqProvidersList != null && resultSqProvidersList.size() >= 2) {
			for (int i =0; i<= resultSqProvidersList.size()-1; i ++){
				String provider = resultSqProvidersList.get(i);
				strProviders.append(provider);
				if(i< resultSqProvidersList.size()-2){
					strProviders.append(", ");
				}
				if(i==resultSqProvidersList.size()-2){
					strProviders.append(" and ");
				}
			}
		}else if(sqProvidersList != null && sqProvidersList.size() == 1){
			strProviders.append(resultSqProvidersList.get(0));
		}
		logger.info("resultSqProvider=" +resultSqProvidersList);
		salesCenterVo.setValueByName("scriptedQualification.providerList", "<b>"+strProviders.toString()+"</b>");

		List<Fieldset> fieldsetList = null;
		StringBuilder events = new StringBuilder();
		List<DataGroup> dgList = null;
		for (Dialogue dialogue : dialogueVO.getDialogueList()){
			dgList = dialogue.getDataGroupList();
			for(DataGroup dGroup : dgList){
				fieldsetList = HtmlFactory.INSTANCE.dialogueToFieldSet(events, dialogueVO.getDataFieldMap().get(dialogue.getExternalId()).get(dGroup.getName()), null, false);
				for (Fieldset fieldset : fieldsetList) {
					String element = HtmlBuilder.INSTANCE.toString(fieldset);
					element = salesCenterVo.replaceNamesWithValues(element);
					events.append(element);
				}				
			}
		} 
		/*try{
			element = new String(element.getBytes(),"UTF-16");
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw e;
		}*/
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("address", SalesUtil.INSTANCE.getAddress(order.getCustomerInformation().getCustomer(),
				com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString()));
		mav.addObject("dialogue" , escapeSpecialCharacters(events.toString()));
		mav.setViewName("sales.qualification");	
		if(session.getAttribute("callTimeBeforeUtility")!=null){
			session.setAttribute("fromUtility", "yes");
		}
		mav.addObject("CKOCompletedLineItems" , LineItemUtil.prepareCKOCompletedLinItemsListFromOrder(order));
		
		logger.info("showQualification_end");
		return mav;
		}catch(Exception e){
			logger.warn("Error_in_showQualification",e);
			logger.error(e);
			throw new UnRecoverableException(e.getMessage());
			
		}
	}
	public static List<String> removeDuplicates(List<String> providerList){
		if(providerList!=null){
			for (int i = 0 ; i < providerList.size(); i++) {
				for (int j = i + 1; j < providerList.size(); j++) {
					if ( providerList.get(i).toString().equalsIgnoreCase( providerList.get(j).toString() )  ) {
						providerList.remove(j);
						j = j -1; 
					}                                
				}
			}
		}
		return providerList;
	}
	private String getProviderName(ProductSummaryVO product) {
		if(product.getParentName()!= null) {
			return product.getParentName();
		} else {
			return product.getProviderName();
		}
	}

	@RequestMapping(value="/discovery")
	protected ModelAndView showDiscovery(HttpServletRequest request,
			HttpServletResponse response) throws UnRecoverableException {
		try{
		HttpSession session = request.getSession();
		logger.info("showDiscovery_begin");
		Long orderId =(Long)session.getAttribute("orderId");
		SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
		String agentId = salesCenterVo.getValueByName("Agent");
		OrderType order = OrderService.INSTANCE.getOrderByOrderNumber(String.valueOf(orderId), agentId, null,null,false, null);
		
		String newService = request.getParameter("newService");
		logger.info("submitvalue="+newService);

		String existingService = request.getParameter("existingService");
		logger.info("submitvalue_existingService"+existingService);

		String yesTransferService = request.getParameter("yesTransferService");
		String noTransferService = request.getParameter("noTransferService");
		logger.info("yes_Transfer_Service_is" + yesTransferService );
		logger.info("no _Transfer_Service_is" + noTransferService );


		if(StringUtils.isNotBlank(yesTransferService) && yesTransferService.trim().equals("Y")){
			session.setAttribute("typeOfcheckBox", "Y");
		} else if(StringUtils.isNotBlank(noTransferService) && noTransferService.trim().equals("N")){
			session.setAttribute("typeOfcheckBox", "N");
		}
		if(session.getAttribute("fromQualification")!=null 
				&& "yes".equalsIgnoreCase(session.getAttribute("fromQualification").toString())){

			if(session.getAttribute("typeOfService")!=null ){
				String serviceSelected = null;
				if(newService == null && existingService == null){
					session.setAttribute("isServiceChecked", "No");
					session.setAttribute("typeOfService", "existingService");
					serviceSelected = null;
				}else if(newService != null){
					serviceSelected = "newService";
				}else if(existingService != null){
					serviceSelected = "existingService";
				}
				if(serviceSelected!=null && !serviceSelected.equalsIgnoreCase(session.getAttribute("typeOfService").toString())){
					session.setAttribute("typeOfService", serviceSelected);
				}
			}
		}
		if(session.getAttribute("typeOfService")==null && (session.getAttribute("fromQualification")!=null 
				&& "yes".equalsIgnoreCase(session.getAttribute("fromQualification").toString()))){
		if(newService != null){
			Object attribute = session.getAttribute("typeOfService");
			if (attribute != null  && ((String)attribute).equals("existingService")) {
				//session.setAttribute("questionList", null);
			}
			session.setAttribute("typeOfService", newService);
		}
		else {
			   if(newService == null && existingService == null){
				   session.setAttribute("isServiceChecked", "No");
			   }
			
			Object attribute = session.getAttribute("typeOfService");
			if (attribute != null  && ((String)attribute).equals("newService")) {
				//session.setAttribute("questionList", null);
			}
			session.setAttribute("typeOfService", "existingService");
		}
		}
		session.setAttribute("fromQualification",null);

		String typeOfService = (String)session.getAttribute("typeOfService");
		if(Utils.isBlank(typeOfService)){
			typeOfService = "existingService";
		}
		ModelAndView mav = new ModelAndView();

		if(typeOfService.equals("newService")){
			//DiscoveryA Dialogue service code   
			logger.info("Get_Dialog_Service_started_in_show_Discovery_new");
			/*
			 * If we want to GUID on Sales Context then last parameter of below method is true otherwise false.
			 */
			Map<String, Map<String, String>> context = SalesDialogueFactory.INSTANCE.updateContextMapWithReferrerAndCallType("DiscoveryNew",salesCenterVo,true);
			SalesDialogueVO dialogueVO = DialogServiceUI.INSTANCE.getDialoguesByContext(context);
			
			logger.info("Get_Dialog_Service_finished_in_show_Discovery_new"+dialogueVO.toString());

			Fieldset fieldsetList = null;
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

			fieldsetList = HtmlFactory.INSTANCE.dialogueToFieldSetDiscovery(events, dataFields, null);

			//for (Fieldset fieldset : fieldsetList) {
			String element = HtmlBuilder.INSTANCE.toString(fieldsetList);
			logger.debug("element="+element);
			element = salesCenterVo.replaceNamesWithValues(element);
			events.append(element);
			//}		
			//for order summary page
			boolean hasProducts = false;
			int cnt=0;
			List<LineItemType> lineitemList = order.getLineItems().getLineItem();
			for(LineItemType lit:  lineitemList){
				for(AttributeEntityType entity : lit.getLineItemAttributes().getEntity()){
					if(entity.getSource() != null && entity.getSource().equalsIgnoreCase("provider_feedback")){
						for(AttributeDetailType attribute : entity.getAttribute()){
							if(attribute.getName().equals("Display") && attribute.getValue().equals("false")){
								cnt++;
							}
						}     
					}
				}
			}
			logger.info("count="+cnt);
			if(cnt != lineitemList.size()){
				hasProducts = true;
			}
			request.getSession().setAttribute("hasProducts", hasProducts);
			request.getSession().setAttribute("order", order);
			
			mav.addObject("address", SalesUtil.INSTANCE.getAddress(order.getCustomerInformation().getCustomer(),
					com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString()));
			mav.addObject("dialogueA" , events.toString());
			mav.setViewName("sales.discoveryA");
		} 
		if(typeOfService.equals("existingService")) {

			//DiscoveryB Dialogue service code   
			logger.info("Get_Dialog_Service_started_in_show_DiscoveryTransfer");
			/*
			 * If we want to GUID on Sales Context then last parameter of below method is true otherwise false.
			 */
			Map<String, Map<String, String>> context = SalesDialogueFactory.INSTANCE.updateContextMapWithReferrerAndCallType("DiscoveryTransfer",salesCenterVo,true);
			SalesDialogueVO dialogueVO = DialogServiceUI.INSTANCE.getDialoguesByContext(context);
			logger.info("Get_Dialog_Service_finished_in_show_DiscoveryTransfer"+dialogueVO.toString());

			Fieldset fieldsetList = null;
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

			fieldsetList = HtmlFactory.INSTANCE.dialogueToFieldSetDiscovery(events, dataFields, null);



			//for (Fieldset fieldset : fieldsetList) {
			String element = HtmlBuilder.INSTANCE.toString(fieldsetList);
			logger.debug("element="+element);
			element = salesCenterVo.replaceNamesWithValues(element);
			events.append(element);
			//}		

			//for order summary page
			boolean hasProducts = false;
			int cnt=0;
			List<LineItemType> lineitemList = order.getLineItems().getLineItem();
			for(LineItemType lit:  lineitemList){
				for(AttributeEntityType entity : lit.getLineItemAttributes().getEntity()){
					if(entity.getSource() != null && entity.getSource().equalsIgnoreCase("provider_feedback")){
						for(AttributeDetailType attribute : entity.getAttribute()){
							if(attribute.getName() != null && attribute.getValue() != null
									&& attribute.getName().equals("Display") && attribute.getValue().equals("false")){
								cnt++;
							}
						}     
					}
				}
			}
			logger.info("count="+cnt);
			if(cnt != lineitemList.size()){
				hasProducts = true;
			}
			mav.addObject("address", SalesUtil.INSTANCE.getAddress(order.getCustomerInformation().getCustomer(),
					com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString()));
			request.getSession().setAttribute("hasProducts", hasProducts);
			request.getSession().setAttribute("order", order);
			mav.addObject("dialogueB" , events.toString());
			mav.setViewName("sales.discoveryB");

		}
		
		if(session.getAttribute("questionList")!=null){
			JSONObject jsonObject = new JSONObject((Map<String, String>)session.getAttribute("questionList"));
			mav.addObject("questionListForMAV",jsonObject.toString());
		}
		mav.addObject("CKOCompletedLineItems" , LineItemUtil.prepareCKOCompletedLinItemsListFromOrder(order));
		logger.info("showDiscovery_end");
		return mav;
		}catch(Exception e){
			logger.warn("Error_in_showDiscovery",e);
			logger.error(e);
			throw new UnRecoverableException(e.getMessage());
			
		}
	}
	
	
	@RequestMapping(value="/closingcall")
	protected ModelAndView showClosingCall(HttpServletRequest request,
			HttpServletResponse response) throws UnRecoverableException {
		try{

		String closeCall=request.getParameter("endCall");
		logger.info("showClosingCall_begin"+closeCall);

		ModelAndView mav = new ModelAndView();
		if(closeCall.equalsIgnoreCase("End Call")) {
			SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
			String agentId = salesCenterVo.getValueByName("Agent");
			String orderId =String.valueOf(request.getSession().getAttribute("orderId"));
			OrderType order = OrderService.INSTANCE.getOrderByOrderNumber(orderId, agentId, null,null,false, null);
			
			logger.info("Get_Dialog_Service_started_in_show closingcall");
			/*
			 * If we want to GUID on Sales Context then last parameter of below method is true otherwise false.
			 */
			Map<String, Map<String, String>> context = SalesDialogueFactory.INSTANCE.updateContextMapWithReferrerAndCallType("CloseCallNoSaleCSAT",salesCenterVo,true);
			
			String email = order.getCustomerInformation().getCustomer().getBestEmailContact();
			if(!Utils.isBlank(email)){
				if(Utils.isValidEmail(email)){
					Map<String, String> salesFlow = context.get("salesFlow");
				    salesFlow.put("salesFlow.requiresCSAT", "true");
				    request.getSession().setAttribute("requiresCSAT", true);
				}
			}
			else{
				request.getSession().setAttribute("requiresCSAT", false);
			}
			
			SalesDialogueVO dialogueVO = DialogServiceUI.INSTANCE.getDialoguesByContext(context);
			logger.info("Get_Dialog_Service_finished_in_show_closingcall"+dialogueVO.toString());

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
			fieldsetList = HtmlFactory.INSTANCE.dialogueToFieldSet(events, dataFields, null);

			for (Fieldset fieldset : fieldsetList) {
				String element = HtmlBuilder.INSTANCE.toString(fieldset);
				element = salesCenterVo.replaceNamesWithValues(element);
				events.append(element);
			}

			mav.addObject("dialogue" , events.toString());
			mav.addObject("isClosingCall", true);
			mav.setViewName("closingcall");
			mav.addObject("CKOCompletedLineItems" , LineItemUtil.prepareCKOCompletedLinItemsListFromOrder(order));
		}

		return mav;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			throw new UnRecoverableException(e.getMessage());
		}
	}
	@RequestMapping(value = "/recommendations/save/notepad")
	public @ResponseBody String autoSave(HttpServletRequest request){

		String notepadValue = request.getParameter("notepadValue");
		logger.info("Ajax="+notepadValue);
		request.getSession().setAttribute("notepadValue", notepadValue);
		return "Saved...";
	}
	
	@RequestMapping(value = "/recommendations/save/notepadInput")
	public @ResponseBody String autoSaveInput(HttpServletRequest request){
		Map<String, String> notePadHeaders = new LinkedHashMap<String, String>();
		Map<String, String> customerDiscoveryMap = new LinkedHashMap<String, String>();
		Map<String, String> linkedMap = new LinkedHashMap<String, String>();
		notePadHeaders = (Map<String, String>)request.getSession().getAttribute("notePadMap");	
		customerDiscoveryMap = (Map<String, String>)request.getSession().getAttribute("customerDiscoveryMap");	
		linkedMap = (Map<String, String>)request.getSession().getAttribute("discoveryNotepadlinkMap");	
		Map<String, String[]> parameters = request.getParameterMap();
		for(String parameter : parameters.keySet()) {
			String[] values = parameters.get(parameter);
		    if (notePadHeaders.get(parameter) != null) {
		        notePadHeaders.put(parameter, values[0]);
		        customerDiscoveryMap.put(linkedMap.get(parameter), values[0]);
		    }
		}
		request.getSession().setAttribute("notePadMap", notePadHeaders);
		request.getSession().setAttribute("customerDiscoveryMap", customerDiscoveryMap);

		return "Saved...";
	}

	@RequestMapping(value = "/populate")
	public @ResponseBody String populateCityState(HttpServletRequest request){
		String zipCode = request.getParameter("zipCode");
		String dwellingType = "House";
		ServiceabilityEnterpriseResponse sarRes;

		sarRes = ESEService.INSTANCE.getServiceabilityResponse(zipCode, dwellingType);

		ServiceabilityResponse2 sre = (ServiceabilityResponse2)sarRes.getResponse();
		com.AL.xml.se.v4.AddressType sreAddress = sre.getCorrectedAddress();
		String city = sreAddress.getCity();
		String state = sreAddress.getStateOrProvince();
		JSONObject obj = new JSONObject();
		try {
			obj.put("city", formatString(city));
			obj.put("state", state);
		} catch (JSONException e) {
			e.printStackTrace();
			logger.error(e);
		}
		return obj.toString();
	}


	public Map<String, Map<String, String>> createSalesContext(String dwellingType, HttpServletRequest request) {
		Map<String, Map<String, String>> salesContextData = new HashMap<String, Map<String, String>>();
		SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
		Map<String, String> context = new HashMap<String, String>();
		context.put("context.mode", "production");
		salesContextData.put("context", context);	

		Map<String, String> orderSource = new HashMap<String, String>();
		orderSource.put("orderSource.source", "123");
		orderSource.put("orderSource.channel", "1");
		orderSource.put("orderSource.referrer",salesCenterVo.getValueByName("DT Partner"));

		salesContextData.put("orderSource", orderSource);

		Map<String, String> consumer = new HashMap<String, String>();
		consumer.put("consumer.creditScore", "650");
		salesContextData.put("consumer", consumer);

		if (dwellingType != null && dwellingType.equalsIgnoreCase("Apartment")) {
			dwellingType = "apartment";
		}else{
			dwellingType = "house";
		}
		Map<String, String> dwelling = new HashMap<String, String>();
		dwelling.put("dwelling.dwellingType", dwellingType);
		dwelling.put("dwelling.stateOrProvince", request.getParameter("state"));
		salesContextData.put("dwelling", dwelling);

		Map<String, String> salesFlow = new HashMap<String, String>();
		salesFlow.put("salesFlow.dialogueType", "core");
		salesFlow.put("salesFlow.forceNonConfirm", "false");
		salesContextData.put("salesFlow", salesFlow);

		Map<String, String> agent = new HashMap<String, String>();
		agent.put("agent.capability", "advanced");
		salesContextData.put("agent", agent);

		return salesContextData;
	}	

	public ProviderCriteriaType2 createProviderCriteria() {
		ProviderCriteriaType2 criteria = new ProviderCriteriaType2();
		ProviderCriteriaEntityType2 entity = new ProviderCriteriaEntityType2();
		entity.setName("ATTSTI");
		ProviderNameValuePairType2 pair = new ProviderNameValuePairType2();
		pair.setName("salesCode");
		pair.setValueAttribute("KRAMERE");
		entity.getAttributes().add(pair);
		criteria.getProviders().add(entity);
		return criteria;
	}
	private String resolveRole(final String rawRole) {

		if ((rawRole == null) || (rawRole.length() == 0)) {
			return rawRole;
		}

		if ("CURRENT_ADDRESS".equals(rawRole.toUpperCase().trim()))
			return "CurrentAddress";
		else if ("SERVICE_ADDRESS".equals(rawRole.toUpperCase().trim()))
			return "ServiceAddress";
		else if ("BILLING_ADDRESS".equals(rawRole.toUpperCase().trim()))
			return "BillingAddress";
		else if ("PREVIOUS_ADDRESS".equals(rawRole.toUpperCase().trim()))
			return "PreviousAddress";
		else if ("MAILING_ADDRESS".equals(rawRole.toUpperCase().trim()))
			return "MailingAddress";
		else if ("HOME_ADDRESS".equals(rawRole.toUpperCase().trim()))
			return "HomeAddress";
		else if ("SHIPPING_ADDRESS".equals(rawRole.toUpperCase().trim()))
			return "ShippingAddress";
		else if ("CORRECTED_ADDRESS".equals(rawRole.toUpperCase().trim()))
			return "CorrectedAddress";
		else if ("DT_ADDRESS".equals(rawRole.toUpperCase().trim()))
			return "DTAddress";

		return rawRole;

	}
	private static AddressType setAddress(AddressType address,String completeAddress){
		String [] fields = completeAddress.split(",");
		if(fields.length == 2){
			address = getStreetNumberAndStreetName(address,fields[0]);

			String[] subFields = fields[1].trim().split(" ");
			logger.info("size="+subFields.length);
			address.setCity(subFields[0]);

			address.setStateOrProvince(subFields[1]);
			address.setPostalCode(subFields[2]);
		}else{
			address = getStreetNumberAndStreetName(address,fields[0]);
			address.setCity(fields[1].trim());
			String[] subFields = fields[2].trim().split(" ");
			address.setStateOrProvince(subFields[0]);
			address.setPostalCode(subFields[1]);
		}
		return address;

	}
	private static AddressType getStreetNumberAndStreetName(AddressType address,String add1){
		String [] sp = add1.split(" ");
		String streetNumber = sp[0];
		String streetName = "";
		for(int i=1;i < sp.length;i++){
			streetName =  streetName+sp[i]+" ";
		}
		streetName = streetName.trim();
		address.setStreetNumber(streetNumber);
		address.setStreetName(streetName);
		return address;

	}
	public CustAddress getAddress(CustomerType customer, final String key) {
		boolean isRole = Boolean.FALSE;
		if ((customer != null) && (customer.getAddressList() != null)) {
			List<CustAddress> custAddressList = customer.getAddressList().getCustomerAddress();
			if (custAddressList != null) {
				for (CustAddress custAddress : custAddressList) {
					if ((custAddress != null)
							&& (custAddress.getAddressRoles() != null)) {
						List<RoleType> roleTypeList = custAddress.getAddressRoles().getRole();
						for (RoleType roleType : roleTypeList) {
							if (key.equals(roleType.name())) {
								isRole = Boolean.TRUE;
								break;
							}
						}
						if (isRole) {
							return custAddress;
						}
					}
				}
			}
		}

		return new CustAddress();
	}

	private static String getStreetNumberAndStreetName(String addr1){
		String street = "";
		if(!Utils.isBlank(addr1)){
			if(addr1.contains(",")){
				int indexPos = addr1.indexOf(",");
				street = addr1.substring(0, indexPos);
			}
			else{
				street = addr1;
			}
		}
		return street;

	}
	private static String getFormattedUnitType(String unitType){
		String type = "";
		if(unitType.equalsIgnoreCase("apartment")){
			type = "Apt.";
		}
		else if(unitType.equalsIgnoreCase("building")){
			type = "Bldg.";
		}else if(unitType.equalsIgnoreCase("department")){
			type = "Dept.";
		}else if(unitType.equalsIgnoreCase("floor")){
			type = "Fl.";
		}else if(unitType.equalsIgnoreCase("hanger")){
			type = "Hngr.";
		}else if(unitType.equalsIgnoreCase("lot")){
			type = "Lot.";
		}else if(unitType.equalsIgnoreCase("office")){
			type = "Ofc.";
		}else if(unitType.equalsIgnoreCase("space")){
			type = "Spc.";
		}else if(unitType.equalsIgnoreCase("suite")){
			type = "Ste.";
		}else if(unitType.equalsIgnoreCase("trailer")){
			type = "Trlr.";
		}else if(unitType.equalsIgnoreCase("unit")){
			type = "Unt.";
		}
		return type;
	}
	private String formatString(String toBeFormatted){
		String formattedString = "";
		if(!Utils.isBlank(toBeFormatted)){
			String [] sp = toBeFormatted.split(" ");
			for(int i=0;i < sp.length;i++){
				formattedString = formattedString + sp[i].substring(0,1).toUpperCase()+sp[i].substring(1).toLowerCase()+" ";
			}	
		}
		return formattedString;
	}
	
	
	private void setDwellingTypeAndOwnerShip(AddressType address,
			SalesCenterVO salesCenterVo, String rentown, String serviceAddrType) {
		String rentOwn = null;
		String dwellingType = null;

		if(rentown != null && rentown.equalsIgnoreCase("own")){
			address.setAddressOwnership(OwnershipType.OWN);
			rentOwn = "Own";
		}
		else if(rentown != null && rentown.equalsIgnoreCase("rent")){
			address.setAddressOwnership(OwnershipType.RENT);
			rentOwn = "Rent";
		}
		salesCenterVo.setValueByName("rentOwn", rentOwn);
		if(serviceAddrType != null && serviceAddrType.equalsIgnoreCase("apartment")){
			address.setDwellingType(DwellingType.APARTMENT);
			dwellingType = "Apartment";
		}
		else if(serviceAddrType != null && serviceAddrType.equalsIgnoreCase("condo")){
			address.setDwellingType(DwellingType.CONDO_TOWNHOUSE);
			dwellingType = "Condo";
		}
		else if(serviceAddrType != null && serviceAddrType.equalsIgnoreCase("house")){
			address.setDwellingType(DwellingType.HOUSE);
			dwellingType = "House";
		}
		else if(serviceAddrType != null && serviceAddrType.equalsIgnoreCase("duplex")){
			address.setDwellingType(DwellingType.DUPLEX);
			dwellingType = "Duplex";
		}
		else if(serviceAddrType != null && serviceAddrType.equalsIgnoreCase("mobile home")){
			address.setDwellingType(DwellingType.MOBILE_HOME);
			dwellingType = "Mobile Home";
		}
		salesCenterVo.setValueByName("dwellingType", dwellingType);
	}
	
	
	//private static void setUnitTypeFromMultipleAddress(String)

	public static CustAddress getAddressByRole(final List<CustAddress> customerAddressList, final String filterRole) {

		CustAddress selectedAddress = null;

		if ((customerAddressList != null) && (customerAddressList.size() > 0)) {

			// default address
			selectedAddress = customerAddressList.get(0);
			searchRole: for (CustAddress addr : customerAddressList) {
				for (RoleType role : addr.getAddressRoles().getRole()) {

					if (role.name().toString().equals(filterRole)) {
						selectedAddress = addr;
						break searchRole;
					}
				}
			}
		}

		return selectedAddress;
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
	
	private static Map<String, String> sortByComparator(Map<String, String> unsortMap) {
		 
		List<String> list = new LinkedList(unsortMap.entrySet());
 
		// sort list based on comparator
		Collections.sort(list, new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o2)).getValue())
                                       .compareTo(((Map.Entry) (o1)).getValue());
			}
		});
 
		// put sorted list into map again
                //LinkedHashMap make sure order in which keys were inserted
		Map sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
	
	private void buildDialogueValues(String callType, String flow, String dialogueType, boolean dtCustomerSelected, String forceNonConfirm){
		if(flow.equalsIgnoreCase("confirm") && callType.equalsIgnoreCase("default")){
			dialogueType = "BasicInformation";
			if(! dtCustomerSelected){
				forceNonConfirm = "true"; 
			}
		}
		else if(flow.equalsIgnoreCase("nonConfirm") && callType.equalsIgnoreCase("default")){
			if(dtCustomerSelected){
			    dialogueType = "BasicInfoNonConfirmData";
			}
			else{
				dialogueType = "BasicInfoNonConfirmNoData";
			}
		}
		else if(flow.equalsIgnoreCase("confirm") && callType.equalsIgnoreCase("referrerSpecific")){
			dialogueType = "BasicInformation";
			if(! dtCustomerSelected){
				forceNonConfirm = "true"; 
			}
		}
		else if(flow.equalsIgnoreCase("nonConfirm") && callType.equalsIgnoreCase("referrerSpecific")){
			if(dtCustomerSelected){
			    dialogueType = "BasicInfoNonConfirmData";
			}
			else{
				dialogueType = "BasicInfoNonConfirmNoData";
			}
		}
		else if(flow.equalsIgnoreCase("agentTransfer")){
			dialogueType = "BasicInfoAgentTransfer";
		}
	}
	
	private boolean checkIfMultipleAddressReturnsMultiple(HashMap<String, String> addressMap, String completeAddress) {
		boolean result = false;
		if(completeAddress != null  && addressMap.get(completeAddress.toLowerCase()) != null){
				String addressLine2 = addressMap.get(completeAddress.toLowerCase());
				if (!Utils.isBlank(addressLine2) && 
						(addressLine2.toUpperCase().contains(" APT ")
								|| addressLine2.contains("Apartment") || addressLine2.toUpperCase().contains("BLDG")
								|| addressLine2.contains("Building") || addressLine2.toUpperCase().contains("DEPT")
								|| addressLine2.contains("Department") || addressLine2.toUpperCase().contains("FL")
								|| addressLine2.contains("Floor")|| addressLine2.toUpperCase().contains("HNGR")
								|| addressLine2.contains("Hanger") || addressLine2.toUpperCase().contains("LOT")
								|| addressLine2.contains("Lot") || addressLine2.toUpperCase().contains("OFC")
								|| addressLine2.contains("Office") || addressLine2.toUpperCase().contains("SPC")
								|| addressLine2.contains("APT") || addressLine2.toUpperCase().contains("STE")
								|| addressLine2.contains("Suite") || addressLine2.toUpperCase().contains("TRLR")
								|| addressLine2.contains("Trailer") || addressLine2.toUpperCase().contains("UNIT")
								|| addressLine2.contains("Unit"))){
					if (addressLine2.contains("-")){
						result = true;
					}
					// To check any special characters found
					//Pattern p = Pattern.Compile(".*\\W+.*");
					//Pattern p = Pattern.compile("[$&+,:;=?@#|]");
					/*Pattern p = Pattern.compile("[^A-Za-z0-9]");
					Matcher m = p.matcher(addressLine2);
					if (m.find()){
						result = true;
					}*/
				}
		}
		return result;
	}
}