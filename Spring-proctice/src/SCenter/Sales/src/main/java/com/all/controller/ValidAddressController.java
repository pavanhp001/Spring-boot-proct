package com.AL.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.webflow.execution.Action;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.AL.productResults.managers.ProductResultsManager;
import com.AL.productResults.managers.RESTClientForDrupal;
import com.AL.productResults.managers.SearchResults;
import com.AL.productResults.vo.ProductSearchIface;
import com.AL.productResults.vo.ProductSearchVO;
import com.AL.productResults.vo.ProductSummaryVO;
import com.AL.ui.constants.Constants;
import com.AL.ui.dao.ConfirmReferrersDao;
import com.AL.ui.dao.FrontierPricingGridConfigDao;
import com.AL.ui.dao.GrossCommissionableRevenueDao;
import com.AL.ui.dao.HughesNetServedUpDataDao;
import com.AL.ui.dao.OperatingCompanyDao;
import com.AL.ui.dao.QualificationPopUpRefDetailsDao;
import com.AL.ui.dao.QualificationPopUpZipCodesDao;
import com.AL.ui.dao.WarmtransferTpvProvidersDao;
import com.AL.ui.dao.WebLookupDao;
import com.AL.ui.domain.WebLookupCollection;
import com.AL.ui.factory.CartLineItemFactory;
import com.AL.ui.factory.SalesUtilsFactory;
import com.AL.ui.service.config.ConfigRepo;
import com.AL.ui.service.V.CustomerService;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.util.LineItemUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.CartError;
import com.AL.ui.vo.ConfirmReferrersVO;
import com.AL.ui.vo.ErrorList;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.util.DateUtil;
import com.AL.V.domain.SalesContext;
import com.AL.V.exception.UnRecoverableException;
import com.AL.V.factory.CustomerFactory;
import com.AL.V.factory.OrderFactory;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.cm.v4.AddressType;
import com.AL.xml.cm.v4.Attributes;
import com.AL.xml.cm.v4.CustAddress;
import com.AL.xml.cm.v4.CustomerAttributeEntityType;
import com.AL.xml.cm.v4.CustomerAttributeType;
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
import com.AL.xml.se.v4.ServiceabilityEnterpriseResponse;
import com.AL.xml.se.v4.ServiceabilityResponse2;
import com.AL.xml.v4.NameValuePairType;
import com.AL.xml.v4.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextEntityType;
import com.AL.xml.v4.SalesContextType;

@Controller("ValidAddressController")
public class ValidAddressController extends BaseController implements Action {

	@Autowired
	private OperatingCompanyDao operatingCompanyDao;

	private static ExecutorService pool;

	//private static final ExecutorService pool = Executors.newFixedThreadPool(ConfigRepo.getInt("thread_pool_size"));

	SimpleDateFormat format1 = new SimpleDateFormat("MMddyyyy");
	SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy");

	@Autowired
	private WebLookupDao lookupDao;

	@Autowired
	private ConfirmReferrersDao confirmReferrersDao;

	@Autowired
	private GrossCommissionableRevenueDao grossCommissionableRevenueDao;
	
	@Autowired
	private QualificationPopUpZipCodesDao qualificationPopUpZipCodesDao;
	
	@Autowired
	private WarmtransferTpvProvidersDao warmtransferTpvProvidersDao;
	
	@Autowired
	private QualificationPopUpRefDetailsDao qualificationPopUpRefDetailsDao;
	
	@Autowired
	private HughesNetServedUpDataDao hughesNetServedUpDataDao;
	
	@Autowired
	private FrontierPricingGridConfigDao frontierPricingGridConfigDao;

	private long startTimer=0;

	private static final Logger logger = Logger.getLogger(ValidAddressController.class);

	public Event execute(RequestContext req) throws UnRecoverableException {
		HttpServletRequest  request = (HttpServletRequest)req.getExternalContext().getNativeRequest();
		StopWatch timer = new StopWatch();
		timer.start();
		try{
			logger.info("isValidAddress_begin");
			HttpSession session = request.getSession();
			SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
			
			String sameCall = (String) session.getAttribute("sameCall");
			
			if(!Utils.isBlank(request.getParameter("callStartTimeInGreeting")) && !Utils.isBlank(sameCall) && sameCall.equals("true")){
				request.getSession().setAttribute("callStartTime", request.getParameter("callStartTimeInGreeting"));
			}

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
			String unittype = request.getParameter("addressTypeList");
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
			logger.info("isMultiAddress="+isMultiAddress);
			String isFromZipCode = request.getParameter("isFromZipCode");
			String usaaId = request.getParameter("usaaId");
			String moveInValue = ""; 
			if(!Utils.isBlank(request.getParameter("customerMove"))){
				moveInValue = request.getParameter("customerMove");
			}else if(!Utils.isBlank(request.getParameter("moveindateYesNo"))){
				moveInValue = request.getParameter("moveindateYesNo");
			}
			Map<String, Map<String, String>> contextMap = (Map<String, Map<String, String>>)request.getSession().getAttribute("dynamicFlowContextMap");
			Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");
			dynamicFlow.put("dynamicFlow.page", "ValidAddress");
			if((dynamicFlow.get("dynamicFlow.flowType").contains("webReferrer"))||(dynamicFlow.get("dynamicFlow.flowType").contains("webMicro"))||
					(dynamicFlow.get("dynamicFlow.flowType").contains("webCCP"))){
				String customerMove = request.getParameter("customerMove");
				if(Utils.isBlank(salesCenterVo.getValueByName("customerMove"))){
					salesCenterVo.setValueByName("customerMove", customerMove);
				}
				if(!Utils.isBlank(customerMove) && customerMove.equalsIgnoreCase("No")){
					datepicker1 = "";
				}
			}
			if(dynamicFlow.get("dynamicFlow.flowType")!= null && !Utils.isBlank(rentown) && dynamicFlow.get("dynamicFlow.flowType").contains("consumersInteractions")){
				if(rentown.equalsIgnoreCase("own")){
					dynamicFlow.put("rentown", "Owners");
				}else if(rentown.equalsIgnoreCase("rent")){
					dynamicFlow.put("rentown", "Renters");
				}
			}
			if(request.getSession().getAttribute("userGroup") != null){
				salesCenterVo.setValueByName("userGroup", String.valueOf(request.getSession().getAttribute("userGroup")));
			}
			if(request.getSession().getAttribute("userGroupProduct") != null){
				salesCenterVo.setValueByName("userGroupProduct", String.valueOf(request.getSession().getAttribute("userGroupProduct")));
			}
			SalesContext salesContext = SalesContextFactory.INSTANCE.getSalesContext(createSalesContext(serviceAddrType,request));

			StringBuilder fullAddress = new StringBuilder();
			String completeAddress = null;
			ErrorList errorList = new ErrorList();

			fullAddress.append(getCapitalizationTextFromNormalText(address1));
			if(isMultiAddress == null){
				salesCenterVo.setValueByName("correctedCity", city);
				salesCenterVo.setValueByName("correctedState", state);
				salesCenterVo.setValueByName("correctedZipCode", zipCode);
				salesCenterVo.setValueByName("correctedAddress1", address1);
				salesCenterVo.setValueByName("correctedUnitType", unittype);
				salesCenterVo.setValueByName("correctedUnitNumber", unitnumber);
			}
			else if(isMultiAddress!=null && (address1 != null && !address1.equalsIgnoreCase("None of the above"))){
				AddressType addressType = new AddressType();
				addressType = setAddress(addressType, address1);

				salesCenterVo.setValueByName("correctedCity", addressType.getCity());
				salesCenterVo.setValueByName("correctedState", addressType.getStateOrProvince());
				salesCenterVo.setValueByName("correctedZipCode", addressType.getPostalCode());
				StringBuffer correctedAddress1 = new StringBuffer();
				if( !Utils.isBlank(addressType.getStreetNumber()) )
				{
					correctedAddress1.append(addressType.getStreetNumber()+" ");
				}
				if( !Utils.isBlank(addressType.getStreetName()) )
				{
					correctedAddress1.append(addressType.getStreetName()+" ");
				}
				if( !Utils.isBlank(addressType.getStreetType()) )
				{
					correctedAddress1.append(addressType.getStreetType()+" ");
				}
				salesCenterVo.setValueByName("correctedAddress1", correctedAddress1.toString().trim());
				salesCenterVo.setValueByName("correctedUnitType", "");
				salesCenterVo.setValueByName("correctedUnitNumber", "");
			}
			prepopulateSameCallAddrData(request);
			if(datepicker3 != null && datepicker3.length() == 8){
				try {
					Date parsedGasStartDate = format1.parse(datepicker3);
					datepicker3 = format2.format(parsedGasStartDate);
				} catch (ParseException e) {
					e.printStackTrace();
					logger.error(e);
					throw e;
				}
			}

			if(datepicker2 != null && datepicker2.length() == 8){
				try {
					Date parsedGasStartDate = format1.parse(datepicker2);
					datepicker2 = format2.format(parsedGasStartDate);
				} catch (ParseException e) {
					e.printStackTrace();
					logger.error(e);
					throw e;
				}
			}

			if(datepicker1 != null && datepicker1.length() == 8){
				try {
					Date parsedGasStartDate = format1.parse(datepicker1);
					datepicker1 = format2.format(parsedGasStartDate);
				} catch (ParseException e) {
					e.printStackTrace();
					logger.error(e);
					throw e;
				}
			}
			if(!fullAddress.toString().equalsIgnoreCase(Constants.NONE_OF_THE_ABOVE) && isMultiAddress == null) {
				if(unittype != null && unittype.trim().length() > 0){
					fullAddress.append(", ");
					fullAddress.append(unittype);
				}
				if(unitnumber != null && unitnumber.trim().length() > 0){
					fullAddress.append(", ");
					fullAddress.append(unitnumber.replaceAll(" ", ""));
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
			String enableExistingCustomerUserGroupControl = ConfigRepo.getString("*.enableExistingCustomerUserGroupControl") != null ? ConfigRepo.getString("*.enableExistingCustomerUserGroupControl") : "false";
			salesCenterVo.setValueByName("enableExistingCustomerUserGroupControl", enableExistingCustomerUserGroupControl);
			Map<String, String> partnerSpecificDataMap = (Map<String, String>)request.getSession().getAttribute("partnerSpecificDataMap");
			logger.info("PartnerSpecificDataMap="+partnerSpecificDataMap);
			String operatingCompany = null;
			if(partnerSpecificDataMap != null && partnerSpecificDataMap.size()>0){
				for(Entry<String,String> entry: partnerSpecificDataMap.entrySet()){
					if(entry.getKey().equalsIgnoreCase("Operating_Company")){
						operatingCompany = entry.getValue();
						salesCenterVo.setValueByName("simpleChoice.operatingCompanyCode", operatingCompany);
						if( !Utils.isBlank(operatingCompany) )
						{
							String operatingCompanyName = operatingCompanyDao.getOperatingCompanyName(operatingCompany);
							salesCenterVo.setValueByName("simpleChoice.operatingCompany",operatingCompanyName);
						}
					}
					if(entry.getKey().equalsIgnoreCase("Default_Service_Rate")){
						salesCenterVo.setValueByName("simpleChoice.currentRate", entry.getValue());
					}
					if(entry.getKey().equalsIgnoreCase("Rate_Code")){
						salesCenterVo.setValueByName("simpleChoice.rateCode", entry.getValue());
					}
					if(entry.getKey().equalsIgnoreCase("Utility_Billing")) {
						salesCenterVo.setValueByName("simpleChoice.eligibility", entry.getValue());
					}
					if(Constants.PRIOR_ENROLL_SURGE.equalsIgnoreCase(entry.getKey()) ) {
						salesCenterVo.setValueByName("priorEnrollSurge", entry.getValue());
					}
				}
			}
			//if dtCreated,we don't have priorEnrollSurge then set as "0"
			if(!Utils.isBlank(salesCenterVo.getValueByName("dtCreated"))
				&& Utils.isBlank(salesCenterVo.getValueByName("priorEnrollSurge"))){
				salesCenterVo.setValueByName("priorEnrollSurge", "0");
			}
			String referrerId = salesCenterVo.getValueByName("referrer.businessParty.referrerId");
			List<String> psMatchreferrerList = getPsMatchReferrers(ConfigRepo.getString("utility.ps_match_referrer_list"));
			logger.info("PsMatchreferrerList="+psMatchreferrerList);
			boolean isPartnerSpecificMatchReqForUtility = false; 
			if (psMatchreferrerList != null && psMatchreferrerList.contains(referrerId)){
				isPartnerSpecificMatchReqForUtility = true;
			}
			request.getSession().setAttribute("isPartnerSpecificMatchReqForUtility", isPartnerSpecificMatchReqForUtility);
			if(!Utils.isBlank(rentown)) {
				if(rentown.equalsIgnoreCase("Landlord")) {
					rentown = "Own";
					request.getSession().setAttribute("landlord", true);
				}
				salesCenterVo.setValueByName("rentown", rentown);
			}
			try{
			    warmtransferTpvProvidersDao.syncWarmtransferTpvProviders();
			}
			catch(Exception e){
				logger.error("Exception_Occured_In_Retrieving_WarmtransferTpvProviders"+e.getMessage());
			}
			SearchResults searchForm = new SearchResults(completeAddress, ProductSearchVO.CONCERT, serviceAddrType,rentown,partnerSpecificDataMap,isPartnerSpecificMatchReqForUtility, operatingCompany);


			salesCenterVo.setValueByName("orderId", String.valueOf((Long)session.getAttribute("orderId")));
			salesCenterVo.setValueByName("sessionId", session.getId());
			salesCenterVo.setValueByName("serviceAddrType", serviceAddrType);

			searchForm.setSalesCenterVO(salesCenterVo);
			//SearchHelper.SearchProducts(searchForm);
			//ProductResultsManager productResultManager = new ProductResultsManager(searchForm,grossCommissionableRevenueDao);
			//new Thread(productResultManager).start();
			//productResultManager.run();
			//session.setAttribute("productResultManager", productResultManager);
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
				startTimer=timer.getTime();
				addressMap = productResultManager.isValidAddress(completeAddress, salesContext,serviceAddrType,Constants.ZIPFALLBACK);
				logger.info("TimeTakenForSe2ServiceCall="+(timer.getTime()-startTimer));
			}else{
				startTimer=timer.getTime();
				addressMap = productResultManager.isValidAddress(completeAddress, salesContext,serviceAddrType,Constants.NORMAL);
				logger.info("TimeTakenForSe2ServiceCall="+(timer.getTime()-startTimer));
			}
			req.getFlowScope().put("pollTimeOut" , productResultManager.getPollTimeOut());
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
					if(sreAddress != null && !Utils.isBlank(sreAddress.getLine2())){
						fullAddress.append(" ");
						fullAddress.append(sreAddress.getLine2());
					}
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
				String creditZipCode = sreAddress.getPostalCode().replaceAll("-", "");
				if(creditZipCode.length() == 9){
					String creditScore = RESTClientForDrupal.INSTANCE.getCreditScore(creditZipCode);
					if(!Utils.isBlank(creditScore)){
						salesCenterVo.setValueByName("creditScore", creditScore);
					}
				}else{
					salesCenterVo.setValueByName("creditScore", "-1");
				}
				productResultManager.setAddress(serviceAddress);

				logger.info("pool_Value="+pool);

				//				if(!dynamicFlow.get("dynamicFlow.flowType").contains("nonMovers")){
				if(pool==null){
					logger.info("thread pool size::::::::::::::::"+ConfigRepo.getInt("*.thread_pool_size"));
					int i = ConfigRepo.getInt("*.thread_pool_size") == 0 ? 500 : ConfigRepo.getInt("*.thread_pool_size");
					pool = Executors.newFixedThreadPool(i);
				}
				pool.execute(productResultManager);
				//				}

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
				
				if(isMultiAddress!=null && (address1 != null && !address1.equalsIgnoreCase("None of the above"))){
					if(!Utils.isBlank(sreAddress.getLine2()) ){
						String[] arr = sreAddress.getLine2().split(" ");
						String[] temp = new String[arr.length];
						int j = 0;
						for(int i=0;i<arr.length;i++){
							if(!Utils.isBlank(arr[i])){
								temp[j]=arr[i];
								++j;
							}
						}
						unittype = getFormattedUnitTypeFromSE2(temp[0]);
						unitnumber = temp[1];
						
						if (!StringUtils.isEmpty(unittype)){
							line2 = unittype;
						}
						if (!StringUtils.isEmpty(unitnumber)){
							line2 = line2 + " " +unitnumber;
						}
						serviceAddress.setLine2(line2);
					}else{
						unittype = "";
						unitnumber = "";
					}
				}else{
					if (!StringUtils.isEmpty(unittype)){
						line2 = unittype;
					}
					if (!StringUtils.isEmpty(unitnumber)){
						line2 = line2 + " " +unitnumber;
					}
				}

				serviceAddress.setLine2(line2);
				if(datepicker2 != null && !datepicker2.isEmpty()){
					logger.info(("datepicker2="+datepicker2));
					QName NAME = new QName("http://xml.AL.com/v4","electricityStartAt");
					JAXBElement<XMLGregorianCalendar> jCal = new JAXBElement<XMLGregorianCalendar>(
							NAME, XMLGregorianCalendar.class,
							DateUtil.asXMLGregorianCalendarDate(datepicker2, "MM/dd/yyyy"));
					serviceAddress.setElectricityStartAt(jCal);
				}
				else{
					serviceAddress.setElectricityStartAt(null);
				}
				if(datepicker3 != null && !datepicker3.isEmpty()){
					logger.info(("datepicker3="+datepicker3));
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
				//CustomerType customerType = CustomerService.INSTANCE.getCustomerType(customerID.toString());
				CustomerType customerType = (CustomerType) request.getSession().getAttribute("customer");
				CustAddress custAddress = getAddressByRole(customerType.getAddressList().getCustomerAddress(), RoleType.SERVICE_ADDRESS.value());

				CustomerType customer = oFactory.createCustomerType();

				if(partnerSpecificDataMap != null && partnerSpecificDataMap.size() > 0){
					for(Entry<String,String> entry: partnerSpecificDataMap.entrySet()){
						if(entry.getKey() != null && (entry.getKey().equalsIgnoreCase("Contract Acct#") || entry.getKey().equalsIgnoreCase("Contract_Account"))){
							customer.setContractAccountNumber(entry.getValue());
						}
					}
				}

				if(!Utils.isBlank(salesCenterVo.getValueByName("customer.contractAccountNumber"))){
					customer.setContractAccountNumber(salesCenterVo.getValueByName("customer.contractAccountNumber"));
				}
				//Add cm_cust_attribute if usaa
				if(!Utils.isBlank(usaaId)) {
					CustomerAttributeType customerAttributeType = new com.AL.xml.cm.v4.ObjectFactory().createCustomerAttributeType();
					Attributes attributes = new com.AL.xml.cm.v4.ObjectFactory().createAttributes();
					List<CustomerAttributeEntityType> customerAttributeEntityTypes = customerAttributeType.getEntity();
					customerAttributeEntityTypes.add(CartLineItemFactory.INSTANCE.setCustomerAttributeEntityType("USAA_ID", usaaId, "", "USAA_ID"));
					attributes.getAttribute().add(customerAttributeType);
					customer.setAttributes(attributes);
					salesCenterVo.setValueByName("usaa.id", usaaId);
				}

				if(!Utils.isBlank(salesCenterVo.getValueByName("customer.providerData1")) || !Utils.isBlank(salesCenterVo.getValueByName("customer.providerData2")) ||
						!Utils.isBlank(salesCenterVo.getValueByName("customer.providerData3")) || !Utils.isBlank(salesCenterVo.getValueByName("customer.providerData4")) ||
						!Utils.isBlank(salesCenterVo.getValueByName("customer.providerData5"))) {
					Attributes customerLookupAttributes = new com.AL.xml.cm.v4.ObjectFactory().createAttributes();
					if(!Utils.isBlank(salesCenterVo.getValueByName("customer.providerData1"))) {
						CustomerAttributeType customerAttributeType = new com.AL.xml.cm.v4.ObjectFactory().createCustomerAttributeType();
						List<CustomerAttributeEntityType> customerAttributeEntityTypes = customerAttributeType.getEntity();
						customerAttributeEntityTypes.add(CartLineItemFactory.INSTANCE.setCustomerAttributeEntityType("customer.providerData1", salesCenterVo.getValueByName("customer.providerData1"), "", "customer.providerData1"));
						customerLookupAttributes.getAttribute().add(customerAttributeType);
					}
					if(!Utils.isBlank(salesCenterVo.getValueByName("customer.providerData2"))) {
						CustomerAttributeType customerAttributeType = new com.AL.xml.cm.v4.ObjectFactory().createCustomerAttributeType();
						List<CustomerAttributeEntityType> customerAttributeEntityTypes = customerAttributeType.getEntity();
						customerAttributeEntityTypes.add(CartLineItemFactory.INSTANCE.setCustomerAttributeEntityType("customer.providerData2", salesCenterVo.getValueByName("customer.providerData2"), "", "customer.providerData2"));
						customerLookupAttributes.getAttribute().add(customerAttributeType);					
					}
					if(!Utils.isBlank(salesCenterVo.getValueByName("customer.providerData3"))) {
						CustomerAttributeType customerAttributeType = new com.AL.xml.cm.v4.ObjectFactory().createCustomerAttributeType();
						List<CustomerAttributeEntityType> customerAttributeEntityTypes = customerAttributeType.getEntity();
						customerAttributeEntityTypes.add(CartLineItemFactory.INSTANCE.setCustomerAttributeEntityType("customer.providerData3", salesCenterVo.getValueByName("customer.providerData3"), "", "customer.providerData3"));
						customerLookupAttributes.getAttribute().add(customerAttributeType);					
					}
					if(!Utils.isBlank(salesCenterVo.getValueByName("customer.providerData4"))) {
						CustomerAttributeType customerAttributeType = new com.AL.xml.cm.v4.ObjectFactory().createCustomerAttributeType();
						List<CustomerAttributeEntityType> customerAttributeEntityTypes = customerAttributeType.getEntity();
						customerAttributeEntityTypes.add(CartLineItemFactory.INSTANCE.setCustomerAttributeEntityType("customer.providerData4", salesCenterVo.getValueByName("customer.providerData4"), "", "customer.providerData4"));
						customerLookupAttributes.getAttribute().add(customerAttributeType);					
					}
					if(!Utils.isBlank(salesCenterVo.getValueByName("customer.providerData5"))) {
						CustomerAttributeType customerAttributeType = new com.AL.xml.cm.v4.ObjectFactory().createCustomerAttributeType();
						List<CustomerAttributeEntityType> customerAttributeEntityTypes = customerAttributeType.getEntity();
						customerAttributeEntityTypes.add(CartLineItemFactory.INSTANCE.setCustomerAttributeEntityType("customer.providerData5", salesCenterVo.getValueByName("customer.providerData5"), "", "customer.providerData5"));
						customerLookupAttributes.getAttribute().add(customerAttributeType);					
					}		

					customer.setAttributes(customerLookupAttributes);

				}

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
				logger.info("custAddress_getAddress_getExternalId="+custAddress.getAddress().getExternalId());
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

				OrderType order =(OrderType)request.getSession().getAttribute("order");
				//This is for updating order info
				OrderType order_new = new com.AL.xml.v4.ObjectFactory().createOrderType();

				String email = order.getCustomerInformation().getCustomer().getBestEmailContact();
				//order.setExternalId(Long.valueOf(orderIdForMoveDate));formatString
				if(datepicker1!=null && !datepicker1.isEmpty()){
					Date mDate = DateUtil.fromString(datepicker1, "MM/dd/yyyy");
					if(mDate != null){
						order_new.setMoveDate(DateUtil.asXMLGregorianCalendar(mDate));
						genrateDeltaDateDiff(mDate,req);

					}else{
						order_new.setMoveDate(null);
					}
				}else{
					order_new.setMoveDate(null);
				}
				logger.info("isMoveInDeltaPassed="+req.getFlowScope().get("isMoveInDeltaPassed"));

				oFactory = new ObjectFactory();

				String dtFirstName = salesCenterVo.getValueByName("consumer.name.first");
				String dtMiddleName = salesCenterVo.getValueByName("consumer.name.middle");
				String dtLastName = salesCenterVo.getValueByName("consumer.name.last");
				String dtReqStartDate = salesCenterVo.getValueByName("requestedStartDate");
				String dtGasReqStartDate = salesCenterVo.getValueByName("gasRequestedStartDate");
				String partnerAccountId = salesCenterVo.getValueByName("customer.confirmation.number");

				String dtStreet1 = salesCenterVo.getValueByName("address.street1");
				String dtStreet2 = salesCenterVo.getValueByName("address.street2");
				String dtUnitType = salesCenterVo.getValueByName("unit.type");
				String dtUnitNumber = salesCenterVo.getValueByName("unit.number");
				String dtCity = salesCenterVo.getValueByName("address.city");
				String dtState = salesCenterVo.getValueByName("address.state");
				String dtZip = salesCenterVo.getValueByName("address.zip");
				String dtTelephoneNum = salesCenterVo.getValueByName("customer.telePhoneNum");
				String dtCreated = salesCenterVo.getValueByName("dtCreated");
                logger.info("dtTelephoneNum"+dtTelephoneNum);
				boolean isFNameChanged = false;
				boolean isMNameChanged = false;
				boolean isLNameChanged = false;
				boolean isAddrChanged = false;

				NotificationEventCollectionType notifEventColl = null;
				NotificationEventType notifEventType = oFactory.createNotificationEventType();

				if (  !Utils.isBlank( dtFirstName ) && !Utils.isBlank(firstName) ) {
					if (!dtFirstName.equalsIgnoreCase( firstName.trim() )) {						
						isFNameChanged = true;
					}
				} 

				customer.setFirstName(firstName.trim());

				if (  !Utils.isBlank( dtLastName ) && !Utils.isBlank(lastName) ) {
					if (!dtLastName.equalsIgnoreCase( lastName.trim() )) {				
						isLNameChanged = true;
					}
				} 

				customer.setLastName(lastName.trim());

				if (  !Utils.isBlank( dtMiddleName ) && !Utils.isBlank(middleName) ) {
					if (!dtMiddleName.equalsIgnoreCase( middleName.trim() )) {
						isMNameChanged = true;
					}
				} 

				if (!Utils.isBlank(middleName)){
					customer.setMiddleName(middleName.trim());	
				}
				if (!Utils.isBlank(prefix)){
					customer.setTitle(prefix.trim());	
				}
				if (!Utils.isBlank(suffix)){
					customer.setNameSuffix(suffix.trim());	
				}

				if ((!Utils.isBlank(dtReqStartDate) && !Utils.isBlank(datepicker2))
						&& (!dtReqStartDate.equalsIgnoreCase(datepicker2))) {
					notifEventType.getReason().add(30);
				}
				if ((!Utils.isBlank(dtGasReqStartDate) && !Utils.isBlank(datepicker3))
						&& (!dtGasReqStartDate.equalsIgnoreCase(datepicker3))) {
					notifEventType.getReason().add(40);
				}

				if( isFNameChanged || isLNameChanged || isMNameChanged ){
					notifEventType.getReason().add(10);
				}

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
					logger.info("dtStreet2="+dtStreet2);
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

				salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
				String correctedCity = salesCenterVo.getValueByName("correctedCity");
				String correctedState = salesCenterVo.getValueByName("correctedState");
				String correctedZipCode = salesCenterVo.getValueByName("correctedZipCode");
				String correctedAddress1 = salesCenterVo.getValueByName("correctedAddress1");
				String correctedUnitType = salesCenterVo.getValueByName("correctedUnitType");
				String correctedUnitNumber =  salesCenterVo.getValueByName("correctedUnitNumber");

				boolean isAddressCorrected = false;
				if (!Utils.isBlank(dtStreet1)){
					String [] addrStr = dtStreet1.split(" ");
					String str =  addrStr[addrStr.length-1];
					logger.info("str="+str);
					logger.info("correctedUnitNumber="+correctedUnitNumber +"=dtUnitNumber="+dtUnitNumber);
					logger.info("correctedAddress1="+correctedAddress1+"=dtStreet1="+dtStreet1);
					if (!correctedAddress1.endsWith(str) && !Utils.isBlank(correctedUnitNumber) && Utils.isBlank(dtUnitNumber) 
							&& correctedUnitNumber.trim().equalsIgnoreCase(str)){
						isAddressCorrected = true;
					} 
				}

				logger.info("isAddressCorrected="+isAddressCorrected);
				if((!isAddressCorrected && !StringUtils.isEmpty(dtStreet1) && !StringUtils.isEmpty(correctedAddress1))
						&& (!dtStreet1.equalsIgnoreCase(correctedAddress1.trim()))){

					isAddrChanged = true;
				}
				if((!StringUtils.isEmpty(dtCity) && !StringUtils.isEmpty(correctedCity))
						&& (!dtCity.equalsIgnoreCase(correctedCity.trim()))){
					isAddrChanged = true;
				}
				if((!StringUtils.isEmpty(dtState) && !StringUtils.isEmpty(correctedState))
						&& (!dtState.equalsIgnoreCase(correctedState.trim()))){
					isAddrChanged = true;
				}
				if((!StringUtils.isEmpty(dtZip) && !StringUtils.isEmpty(correctedZipCode))
						&& (!dtZip.equalsIgnoreCase(correctedZipCode.trim()))){
					isAddrChanged = true;
				}

				if((!StringUtils.isEmpty(dtUnitType) && !StringUtils.isEmpty(correctedUnitType))
						&& (!getUnitTypeByUnitTypeCode(dtUnitType).toUpperCase()
								.equalsIgnoreCase(getUnitTypeByUnitTypeCode(correctedUnitType.trim()).toUpperCase())))
				{
					isAddrChanged = true;
				}

				if((!StringUtils.isEmpty(dtUnitNumber) && !StringUtils.isEmpty(correctedUnitNumber))
						&& (!dtUnitNumber.equalsIgnoreCase(correctedUnitNumber.trim())))
				{
					isAddrChanged = true;
				}

				if(!StringUtils.isEmpty(dtUnitType) && !StringUtils.isEmpty(dtUnitNumber)
						&& StringUtils.isEmpty(correctedUnitType) && StringUtils.isEmpty(correctedUnitNumber))
				{
					isAddrChanged = true;
				}

				if(!isAddressCorrected && StringUtils.isEmpty(dtUnitType) &&  StringUtils.isEmpty(dtUnitNumber) && !StringUtils.isEmpty(dtCreated)
						&& !StringUtils.isEmpty(correctedUnitType) && !StringUtils.isEmpty(correctedUnitNumber))
				{
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
						logger.info("correctedAddress1="+correctedAddress1);
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
				if(dtCreated != null && !dtCreated.isEmpty())
				{
					QName NAME = new QName("http://xml.AL.com/v4","dtCreated");
					JAXBElement<XMLGregorianCalendar> jCal = new JAXBElement<XMLGregorianCalendar>( NAME, XMLGregorianCalendar.class,
							DateUtil.asXMLGregorianCalendarDate(datepicker2, "MM/dd/yyyy") );
					customer.setDtCreated(jCal);
				}
				if (!notifEventType.getReason().isEmpty()
						&& notifEventType.getReason().size() > 0 && dynamicFlow.get("dynamicFlow.flowType")!= null 
						&& !dynamicFlow.get("dynamicFlow.flowType").contains("customerLookup"))
				{
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
					if (!Utils.isBlank(email)) 
					{
						EMailAddressType emailObj = new EMailAddressType();
						emailObj.setValue(email);
						customer.setHomeEMail(emailObj);
					}
					logger.info("NotificationEventType_getCode="+ notifEventType.getCode());
					customer.setPartnerAccountId(partnerAccountId);
					customer.setDtAgentId(dtAgentId);
					logger.info("partnerAccountId=" + partnerAccountId);
					startTimer = timer.getTime();
					customer = CustomerService.INSTANCE.submitCustomerType(agentId, String.valueOf(order.getCustomerInformation().getCustomer().getExternalId()), 
							"updateCustomer",customer, notifEventColl, customerContext,	errorList );
					logger.info("TimeTakenforCustomerServicecall=" + (timer.getTime() - startTimer));
					if (errorList != null && errorList.size() > 0)
					{
						for (CartError cartError : errorList)
						{
							logger.info("UpdateCustomer_Error_Code:" + cartError.getCode());
							logger.info("UpdateCustomer_Error_Message:"	+ cartError.getMessage());
							logger.info("UpdateCustomer_Error_Description:"	+ cartError.getDescription());
						}
						throw new UnRecoverableException(errorList.get(0).getMessage());
					}
				}
				else {
					//CustomerType customer2 = oFactory.createCustomerType();
					//customer2.setExternalId(customerID);
					logger.info("CustId_Inelse="+customerID);
					customer.setBestPhoneContact(dtTelephoneNum);
					customer.setBestEmailContact(email);
					if(!Utils.isBlank(email)){
						EMailAddressType emailObj = new EMailAddressType();
						emailObj.setValue(email);
						customer.setHomeEMail(emailObj);
					}
					customer.setPartnerAccountId(partnerAccountId);
					customer.setDtAgentId(dtAgentId);	
					logger.info("partnerAccountId_Inelse="+partnerAccountId);
					startTimer=timer.getTime();
					customer = CustomerService.INSTANCE.submitCustomerType(agentId, String.valueOf(order.getCustomerInformation().getCustomer().getExternalId()),
							"updateCustomer", customer, null, customerContext, errorList);
					logger.info("TimeTakenforCustomerServicecall=" +(timer.getTime()-startTimer));
					if(errorList != null && errorList.size() > 0){
						for(CartError cartError: errorList){
							logger.info("UpdateCustomer Error Code:"+cartError.getCode());
							logger.info("UpdateCustomer Error Message:"+cartError.getMessage());
							logger.info("UpdateCustomer Error Description:"+cartError.getDescription());
						}
						throw new UnRecoverableException(errorList.get(0).getMessage());
					}
				}
				Long orderId = (Long)request.getSession().getAttribute("orderId");

				request.getSession().setAttribute("customer", customer);

				for(CustAddress cust:customer.getAddressList().getCustomerAddress()){
					if(cust.getAddressUniqueId().equals(addressUniqueId)){
						request.getSession().setAttribute("addressId", cust.getAddress().getExternalId());
						request.getSession().setAttribute("address", cust);
					}
				}

				String orderIdStr = String.valueOf(orderId);
				startTimer=timer.getTime();

				SalesContextType updateSalesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);
				if(updateSalesContext == null) {
					updateSalesContext = OrderService.INSTANCE.getSalesContext(orderIdStr,agentId);
					logger.info("TimeTakenforOrderServiceCallForSalesContext=" +(timer.getTime()-startTimer));
				}				

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

				if(!LineItemUtil.isSalesContextEntityExit(updateSalesContext, "CKO") )
				{
					SalesContextEntityType salesContextEntityType = new SalesContextEntityType();
					NameValuePairType nvpt = new NameValuePairType();
					nvpt.setName("GUID");
					nvpt.setValue(GUID);
					salesContextEntityType.getAttribute().add(nvpt);
					salesContextEntityType.setName("CKO");
					logger.info("CKO_salescontext_entity_added");
					updateSalesContext.getEntity().add(salesContextEntityType);
				}

				order_new.setAgentId(agentId);

				if(isFromZipCode!=null && isFromZipCode.equalsIgnoreCase(Constants.YES))
				{
					order_new.setIsZipOnlySearch(1);
					salesCenterVo.setValueByName("isZipOnlySearch", "YES");
				}
				String flow = (String) request.getSession().getAttribute("referrerFlowAgentGroup");
				if (!Utils.isBlank(flow)) {
					if (flow.toLowerCase().contains("mdu") && salesCenterVo.getValueByName("orderSourceExternalId") != null) {
						order_new.setOrdersourceExternalId(salesCenterVo.getValueByName("orderSourceExternalId"));
					}
				}		
				if(salesCenterVo.getValueByName("campaignId") != null){
					order_new.setCampaignId(salesCenterVo.getValueByName("campaignId"));
				}
				startTimer=timer.getTime();
				OrderManagementRequestResponse updateOrderResponse =OrderService.INSTANCE.updateOrder(agentId, orderIdStr, updateSalesContext, order_new, GUID);
				logger.info("TimeTakenforOrderServiceCallUpdateOrder=" +(timer.getTime()-startTimer));
				OrderFactory.INSTANCE.validateOrder(updateOrderResponse, errorList);
				if(errorList != null && errorList.size() > 0){
					for(CartError cartError: errorList){
						logger.info("Update_Order_Error_Code:"+cartError.getCode());
						logger.info("Update_Order_Error_Message:"+cartError.getMessage());
						logger.info("Update_Order_Error_Description:"+cartError.getDescription());
					}
					throw new UnRecoverableException(errorList.get(0).getMessage());
				}

				OrderType orderType = null;
				SalesContextType context = null;
				if ((updateOrderResponse != null) && (updateOrderResponse.getResponse() != null)){
					if (updateOrderResponse.getResponse().getOrderInfo() != null && updateOrderResponse.getResponse().getOrderInfo().size() > 0) {
						orderType = updateOrderResponse.getResponse().getOrderInfo().get(0);
					}
					if (updateOrderResponse.getResponse().getSalesContext() != null) {
						context = updateOrderResponse.getResponse().getSalesContext();
					}
				}

				request.getSession().setAttribute("order", orderType);
				SalesContextFactory.INSTANCE.setContextInSession(session,context);

				String drupalContentUrl = ConfigRepo.getString("*.drupal_dialogues_url");
				logger.info("sys_config cache_enabled=" + drupalContentUrl);
				logger.info("dispDrupalDailgVal=" + salesCenterVo.getValueByName("dispDrupalDailgVal"));


				if (salesCenterVo.getValueByName("drupalContentUrl") != null 
						&& salesCenterVo.getValueByName("dispDrupalDailgVal") != null 
						&& dynamicFlow.get("dynamicFlow.flowType").toUpperCase().contains("PECO"))
				{
					req.getFlowScope().put("isDialoguesFromDrupal" , Boolean.TRUE);
				}
				else
				{
					req.getFlowScope().put("isDialoguesFromDrupal" , Boolean.FALSE);
				}

				req.getFlowScope().put("dialogue" , addressResult);
				if (request.getSession().getAttribute("thoughtSpotrank") != null){
					req.getFlowScope().put("thoughtSpotrank",(Boolean) request.getSession().getAttribute("thoughtSpotrank"));
				}
				if (request.getSession().getAttribute("TSCacheDateTime") != null){
					req.getFlowScope().put("TSCacheDateTime", request.getSession().getAttribute("TSCacheDateTime"));
				}
				if (request.getSession().getAttribute("thoughtSpotData") != null){
					req.getFlowScope().put("thoughtSpotData",(String) request.getSession().getAttribute("thoughtSpotData"));
				}
				logger.info("timeTakenForIsValidAddress="+timer.getTime());
				timer.stop();
				logger.info("isValidAddress_end");
				return new Event(this, "validAddressEvent");
			}else if (Utils.isBlank(addressResult) || (!addressResult.equalsIgnoreCase("Address Found - Process to next screen") 
					&& !addressResult.equalsIgnoreCase("Multiple Address"))){

				if (isMultiAddress != null){
					if (completeAddress != null ){

						if(completeAddress != null && !completeAddress.equalsIgnoreCase("None of the above")){
							prepopulateAddress(completeAddress,req);
						}else{
							prepopulateAddressForNoneOfAbove(noneOftheAbove,req);
							req.getFlowScope().put("unitTypevalue" , unittype);
							req.getFlowScope().put("unitNumber" , unitnumber);
						}

						req.getFlowScope().put("dwellingType",serviceAddrType);
						req.getFlowScope().put("rentown" , rentown);
					}
				}else{
					req.getFlowScope().put("unitTypevalue" , unittype);
					req.getFlowScope().put("unitNumber" , unitnumber);
					req.getFlowScope().put("addressLine1", address1);
					req.getFlowScope().put("city", city);
					req.getFlowScope().put("state", state);
					req.getFlowScope().put("zip", zipCode);
					req.getFlowScope().put("dwellingType",serviceAddrType);
					req.getFlowScope().put("rentown" , rentown);
				}

				req.getFlowScope().put("datepicker1" , datepicker1);
				req.getFlowScope().put("datepicker2" , datepicker2);
				req.getFlowScope().put("datepicker3" , datepicker3);
				req.getFlowScope().put("lastName" , lastName);
				req.getFlowScope().put("middleName" , middleName);
				req.getFlowScope().put("firstName" , firstName);
				req.getFlowScope().put("dialogue" , addressResult);
				req.getFlowScope().put("Prefix" , prefix);
				req.getFlowScope().put("Suffix" , suffix);
				req.getFlowScope().put("moveInValue" , moveInValue);

				if (completeAddress != null && !completeAddress.equalsIgnoreCase("None of the above")){
					req.getFlowScope().put("completeAddress" , completeAddress);	
				}else{
					req.getFlowScope().put("completeAddress" , noneOftheAbove);
				}

				req.getFlowScope().put("states", webLookupStates.getListAsJSON());
				req.getFlowScope().put("unitType", webLookupUnitType.getListAsJSON());
				req.getFlowScope().put("serviceAddressType", webLookupServiceAddressType.getListAsJSON());
				req.getFlowScope().put("rentOwn", webLookupRentOwn.getListAsJSON());
				logger.info("timeTakenForInvalidAddress="+timer.getTime());
				timer.stop();
				return new Event(this, "invalidAddressEvent");

			}else if (!Utils.isBlank(addressResult) && addressResult.equalsIgnoreCase("Multiple Address")){
				req.getFlowScope().put("Prefix" , prefix);
				req.getFlowScope().put("Suffix" , suffix);
				req.getFlowScope().put("middleName" , middleName);
				req.getFlowScope().put("unittype" , unittype);
				req.getFlowScope().put("unitnumber" , unitnumber);
				req.getFlowScope().put("serviceAddrType" , serviceAddrType);
				req.getFlowScope().put("rentown" , rentown);
				req.getFlowScope().put("datepicker1" , datepicker1);
				req.getFlowScope().put("datepicker2" , datepicker2);
				req.getFlowScope().put("datepicker3" , datepicker3);
				req.getFlowScope().put("lastName" , lastName);
				req.getFlowScope().put("firstName" , firstName);
				req.getFlowScope().put("dialogue" ,productResultManager.getMultipleAddress());
				req.getFlowScope().put("completeAddress" , completeAddress);
				req.getFlowScope().put("moveInValue" , moveInValue);
				logger.info("timeTakenForMultipleAddress="+timer.getTime());
				timer.stop();
				return new Event(this, "multipleAddressEvent"); 
			}
		}catch (Exception e) {
			logger.warn("Valid_address_error",e);
			req.getFlowScope().put("message", e.getMessage());
			req.getFlowScope().put("pageTitle",request.getParameter("pageTitle")!=null?request.getParameter("pageTitle"):"");
			timer.stop();
			logger.error(e);
			throw new UnRecoverableException(e.getMessage());
		}
		
		return new Event(this, ""); 
	}


	private void genrateDeltaDateDiff(Date mDate, RequestContext req) {
		try
		{
			HttpServletRequest request = (HttpServletRequest)req.getExternalContext().getNativeRequest();
			SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
			if(!Utils.isBlank(salesCenterVo.getValueByName("referrer.dateDelta"))){
				Long dateDeltaFloor = Long.valueOf(salesCenterVo.getValueByName("referrer.dateDelta"));
				Date curDate =  new Date();
				long diffDays = Utils.dateDiffInDays(mDate, curDate);
				//-9 <= -7
				if( diffDays <= dateDeltaFloor){
					req.getFlowScope().put("isMoveInDeltaPassed",  true);
					request.getSession().setAttribute("isMoveInDelta",  true);
				}
			}
		}catch (Exception e) {
			logger.error("Exception_Occured_In_genrateDeltaDateDiff",e);
		}
	}


	/**
	 * Here confirming next flow.
	 * 
	 * @param requestContext
	 * @return Event
	 * @throws UnRecoverableException
	 */
	public String confirmNextCallFlow(HttpServletRequest  request, RequestContext requestContext) throws UnRecoverableException 
	{
		try{
			logger.info("ConfirmNextCallFlow_begin");
			StopWatch timer = new StopWatch();
			timer.start();
			SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
			String contextIndicator = salesCenterVo.getValueByName("salesFlow.contextId");
			if(Utils.isBlank(contextIndicator)){
				contextIndicator="NA";
			}
			ProductResultsManager productResultManager = (ProductResultsManager)request.getSession().getAttribute("productResultManager");
			String referrerId = salesCenterVo.getValueByName("referrer.businessParty.referrerId");
			startTimer=timer.getTime();
			ConfirmReferrersVO confirmReferrerVO = confirmReferrersDao.getConfirmReferrers(Long.valueOf(referrerId));
			logger.info("TimeTakenForgetConfirmReferrers="+(timer.getTime()-startTimer));
			timer.stop();
			boolean isConfirmReferrerForUtility = false;
			if((confirmReferrerVO!= null)&&(confirmReferrerVO.getReferrerId()!= null)){
				isConfirmReferrerForUtility = true;
			}
			request.getSession().setAttribute("isConfirmReferrerForUtility" , isConfirmReferrerForUtility);
			boolean isUtilityOfferExist = SalesUtilsFactory.INSTANCE.confirmUtilityOffer(salesCenterVo, productResultManager, request, isConfirmReferrerForUtility);
			request.getSession().setAttribute("isUtilityOfferExist" , isUtilityOfferExist);
			if((contextIndicator.equals("00")) || (contextIndicator.equals("05")))
			{

				HashMap<String, List<ProductSummaryVO>> saversMap = (HashMap<String, List<ProductSummaryVO>>)productResultManager.getSaversOfferMap();

				if(saversMap!=null && !saversMap.isEmpty())
				{
					logger.info("returns_offerEvent");
					return "offerEvent";
				}
				else
				{
					logger.info("returns_utilityOfferViewEvent ");
					return "utilityOfferViewEvent";
				}
			}
		}catch (Exception e) {
			logger.warn("Confirmation_call_error",e);
			throw new UnRecoverableException(e.getMessage());
		}
		logger.info("ConfirmNextCallFlow_end");
		return "confirmationViewEvent";
	}

	/**
	 * Here confirming next flow.
	 * 
	 * @param requestContext
	 * @return Event
	 * @throws UnRecoverableException
	 */
	public String confirmNextCallFlowForNonConfirm(HttpServletRequest  request, RequestContext requestContext) throws UnRecoverableException 
	{
		try{
			logger.info("ConfirmNextCallFlow_begin");
			StopWatch timer = new StopWatch();
			timer.start();
			SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
			String contextIndicator = salesCenterVo.getValueByName("salesFlow.contextId");
			if(Utils.isBlank(contextIndicator)){
				contextIndicator="NA";
			}
			ProductResultsManager productResultManager = (ProductResultsManager)request.getSession().getAttribute("productResultManager");
			String referrerId = salesCenterVo.getValueByName("referrer.businessParty.referrerId");
			startTimer=timer.getTime();
			ConfirmReferrersVO confirmReferrerVO = confirmReferrersDao.getConfirmReferrers(Long.valueOf(referrerId));
			logger.info("TimeTakenForgetConfirmReferrers="+(timer.getTime()-startTimer));
			timer.stop();
			boolean isConfirmReferrerForUtility = false;
			if((confirmReferrerVO!= null)&&(confirmReferrerVO.getReferrerId()!= null)){
				isConfirmReferrerForUtility = true;
			}
			request.getSession().setAttribute("isConfirmReferrerForUtility" , isConfirmReferrerForUtility);
			boolean isUtilityOfferExist = SalesUtilsFactory.INSTANCE.confirmUtilityOffer(salesCenterVo, productResultManager, request, isConfirmReferrerForUtility);
			request.getSession().setAttribute("isUtilityOfferExist" , isUtilityOfferExist);
			HashMap<String, List<ProductSummaryVO>> saversMap = (HashMap<String, List<ProductSummaryVO>>)productResultManager.getSaversOfferMap();
			logger.info("ConfirmNextCallFlow_end");
			if(saversMap!=null && !saversMap.isEmpty())
			{
				logger.info("returns_offerEvent");
				return "offerEvent";
			}
			else
			{
				logger.info("returns_utilityOfferViewEvent ");
				return "utilityOfferViewEvent";
			}

		}catch (Exception e) {
			logger.warn("Confirmation_call_error",e);
			throw new UnRecoverableException(e.getMessage());
		}

	}

	/**
	 * Here confirming next flow.
	 * 
	 * @param requestContext
	 * @return Event
	 * @throws UnRecoverableException
	 */
	public String confirmNextWebCallFlow(HttpServletRequest  request, RequestContext requestContext) throws UnRecoverableException 
	{
		try{
			logger.info("ConfirmNextCallFlow_begin");
			StopWatch timer = new StopWatch();
			timer.start();
			SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
			String contextIndicator = salesCenterVo.getValueByName("salesFlow.contextId");
			if(Utils.isBlank(contextIndicator)){
				contextIndicator="NA";
			}

			String customerMove = salesCenterVo.getValueByName("customerMove");
			if((!Utils.isBlank(customerMove)) && (customerMove.equalsIgnoreCase("No"))){
				logger.info("returns_utilityOfferViewEvent");
				return "utilityOfferViewEvent";
			}
			ProductResultsManager productResultManager = (ProductResultsManager)request.getSession().getAttribute("productResultManager");
			String referrerId = salesCenterVo.getValueByName("referrer.businessParty.referrerId");
			startTimer=timer.getTime();
			ConfirmReferrersVO confirmReferrerVO = confirmReferrersDao.getConfirmReferrers(Long.valueOf(referrerId));
			logger.info("TimeTakenForgetConfirmReferrers="+(timer.getTime()-startTimer));
			timer.stop();
			boolean isConfirmReferrerForUtility = false;
			if((confirmReferrerVO!= null)&&(confirmReferrerVO.getReferrerId()!= null)){
				isConfirmReferrerForUtility = true;
			}
			request.getSession().setAttribute("isConfirmReferrerForUtility" , isConfirmReferrerForUtility);
			boolean isUtilityOfferExist = SalesUtilsFactory.INSTANCE.confirmUtilityOffer(salesCenterVo, productResultManager, request, isConfirmReferrerForUtility);
			request.getSession().setAttribute("isUtilityOfferExist" , isUtilityOfferExist);
			HashMap<String, List<ProductSummaryVO>> saversMap = (HashMap<String, List<ProductSummaryVO>>)productResultManager.getSaversOfferMap();
			logger.info("ConfirmNextCallFlow_end");
			if(saversMap!=null && !saversMap.isEmpty())
			{
				logger.info("returns_offerEvent");
				return "offerEvent";
			}
			else
			{
				logger.info("returns_utilityOfferViewEvent ");
				return "utilityOfferViewEvent";
			}

		}catch (Exception e) {
			logger.warn("Confirmation_call_error",e);
			throw new UnRecoverableException(e.getMessage());
		}

	}


	private void prepopulateAddressForNoneOfAbove(String completeAddress,RequestContext request) {
		int endIndex = completeAddress.lastIndexOf(",");
		String addressTemp = completeAddress.substring(0,endIndex);
		String addressTemp2 = completeAddress.substring(endIndex);
		addressTemp =  addressTemp.replaceAll(",", "");
		String address = addressTemp.concat(addressTemp2);
		prepopulateAddress(address, request);
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

	public Map<String, Map<String, String>> createSalesContext(String dwellingType, HttpServletRequest request) {
		Map<String, Map<String, String>> salesContextData = new HashMap<String, Map<String, String>>();
		SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
		Map<String, String> context = new HashMap<String, String>();
		context.put("context.mode", "production");
		salesContextData.put("context", context);	
		//		String orderSourceChannel = "1";
		//		if(!Utils.isBlank(salesCenterVo.getValueByName("ordersource.channel"))) {
		//			orderSourceChannel = salesCenterVo.getValueByName("ordersource.channel");
		//		}
		Map<String, String> orderSource = new HashMap<String, String>();
		orderSource.put("orderSource.source", "123");
		orderSource.put("orderSource.channel", "1");
		orderSource.put("orderSource.referrer",salesCenterVo.getValueByName("DT Partner"));
		orderSource.put("orderSource.client", "core");
		if (salesCenterVo != null && salesCenterVo.getValueByName("userGroup") != null) {
			logger.info("userGroup="+salesCenterVo.getValueByName("userGroup"));
			orderSource.put("orderSource.userGroup", salesCenterVo.getValueByName("userGroup"));
		}
		salesContextData.put("orderSource", orderSource);

		Map<String, String> consumer = new HashMap<String, String>();
		consumer.put("consumer.creditScore", "650");
		salesContextData.put("consumer", consumer);

		if (dwellingType != null && dwellingType.equalsIgnoreCase(ProductSearchIface.APARTMENT)) {
			dwellingType = "apartment";
		} else if (dwellingType != null && dwellingType.equalsIgnoreCase(ProductSearchIface.CONDO)) {
			dwellingType = "condo/townhouse";
		} else if (dwellingType != null && dwellingType.equalsIgnoreCase(ProductSearchIface.DUPLEX)) {
			dwellingType = "duplex";
		} else if (dwellingType != null && dwellingType.equalsIgnoreCase(ProductSearchIface.MOBILE_HOME)) {
			dwellingType = "mobile home";
		}
		else{
			dwellingType = "house";
		}
		logger.info("dwellingType=" + dwellingType);
		salesCenterVo.setValueByName("dwellingType", dwellingType);
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

	private static AddressType setAddress(AddressType address,String completeAddress){
		String [] fields = completeAddress.split(",");
		if(fields.length == 2){
			address = getStreetNumberAndStreetName(address,fields[0]);

			String[] subFields = fields[1].trim().split(" ");
			logger.info("AddressType_size="+subFields.length);
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

	private String getUnitTypeByUnitTypeCode(String unitType) {
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

	private String getFormattedDtTelephoneNum(String dtTelephoneNum) {
		dtTelephoneNum = dtTelephoneNum.replaceAll("-", "");
		dtTelephoneNum = dtTelephoneNum.trim();
		dtTelephoneNum = dtTelephoneNum.substring(0,3)+"-"+dtTelephoneNum.substring(3,6)+"-"+dtTelephoneNum.substring(6);
		return dtTelephoneNum;
	}

	private void prepopulateAddress(String completeAddress,	RequestContext request) {
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
		request.getFlowScope().put("unitTypevalue" , unitType);
		request.getFlowScope().put("city" , city);
		request.getFlowScope().put("zip" , zip);
		request.getFlowScope().put("state" , state.toUpperCase());
		request.getFlowScope().put("addressLine1" , line1);
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

	@RequestMapping(value = "/isProductServiceCallCompleted")
	public @ResponseBody Boolean isProductServiceCallCompleted(HttpServletRequest request){
		ProductResultsManager productResultManager = (ProductResultsManager) request.getSession().getAttribute("productResultManager");
		if(productResultManager.IsProductServiceCallCompleted() && !Utils.isBlank(ProductResultsManager.getSiftFileVersion())){
			request.getSession().setAttribute("siftFileVersion", ProductResultsManager.getSiftFileVersion());
		}		
		return productResultManager.IsProductServiceCallCompleted();
	}
	
	private void prepopulateSameCallAddrData(HttpServletRequest  request) {
		SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
		HttpSession session = request.getSession();
		session.setAttribute("firstName", request.getParameter("firstName"));
		session.setAttribute("lastName", request.getParameter("lastName"));
		session.setAttribute("middleName", request.getParameter("middleName"));
		session.setAttribute("prefix", request.getParameter("prefix"));
		session.setAttribute("suffix", request.getParameter("suffix"));
		session.setAttribute("address1", salesCenterVo.getValueByName("correctedAddress1"));
		session.setAttribute("city", salesCenterVo.getValueByName("correctedCity"));
		session.setAttribute("state", salesCenterVo.getValueByName("correctedState"));
		session.setAttribute("zip", salesCenterVo.getValueByName("correctedZipCode"));
		session.setAttribute("unitType", salesCenterVo.getValueByName("correctedUnitType"));
		session.setAttribute("unitNumber", salesCenterVo.getValueByName("correctedUnitNumber"));
		session.setAttribute("vdn", salesCenterVo.getValueByName("vdn"));
		session.setAttribute("referrerName", salesCenterVo.getValueByName("referrer.businessParty.referrerName"));
		session.setAttribute("rentOwnVal", request.getParameter("rentOwnList"));
		session.setAttribute("dwellingType", request.getParameter("serviceAddrType"));
		if(!Utils.isBlank(request.getParameter("customerMove"))){
			session.setAttribute("isMoveInValue", request.getParameter("customerMove"));
		}else if(!Utils.isBlank(request.getParameter("moveindateYesNo"))){
			session.setAttribute("isMoveInValue", request.getParameter("moveindateYesNo"));
		}else{
			session.setAttribute("isMoveInValue", "");
		}
	}
	private static String getFormattedUnitTypeFromSE2(String unitType){
		 String type = "";
		 if((unitType.toLowerCase().indexOf("apt") > -1) || (unitType.toLowerCase().indexOf("apartment") > -1) ){
		 type = "Apartment";
		 }
		 else if((unitType.toLowerCase().indexOf("bldg") > -1) || (unitType.toLowerCase().indexOf("building") > -1)){
		 type = "Building";
		 }else if((unitType.toLowerCase().indexOf("dept") > -1) || (unitType.toLowerCase().indexOf("department") > -1)){
		 type = "Department";
		 }else if((unitType.toLowerCase().indexOf("fl") > -1) || (unitType.toLowerCase().indexOf("floor") > -1)){
		 type = "Floor";
		 }else if((unitType.toLowerCase().indexOf("hngr") > -1) || (unitType.toLowerCase().indexOf("hanger") > -1)){
		 type = "Hanger";
		 }else if((unitType.toLowerCase().indexOf("lot") > -1) || (unitType.toLowerCase().indexOf("lot") > -1)){
		 type = "Lot";
		 }else if((unitType.toLowerCase().indexOf("ofc") > -1) || (unitType.toLowerCase().indexOf("office") > -1)){
		 type = "Office";
		 }else if((unitType.toLowerCase().indexOf("spc") > -1) || (unitType.toLowerCase().indexOf("space") > -1)){
		 type = "Space";
		 }else if((unitType.toLowerCase().indexOf("ste") > -1) || (unitType.toLowerCase().indexOf("suite") > -1)){
		 type = "Suite";
		 }else if((unitType.toLowerCase().indexOf("trlr") > -1) || (unitType.toLowerCase().indexOf("trailer") > -1)){
		 type = "Trailer";
		 }else if((unitType.toLowerCase().indexOf("unt") > -1) || (unitType.toLowerCase().indexOf("unit") > -1)){
		 type = "Unit";
		 }
		 return type;
		 }
}
