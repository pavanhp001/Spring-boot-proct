package com.AL.controller;

import static com.AL.ui.util.ConfigProperties.DATAEXCHANGEREFERRERURL;
import static com.AL.ui.util.ConfigProperties.DATAEXCHANGESETCUSTOMERLINKANDMATCHURL;
import static com.AL.ui.util.ConfigProperties.DATAEXCHANGEURL;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.AL.html.Fieldset;
import com.AL.mail.GenericEmailHelper;
import com.AL.productResults.managers.RESTClientForDrupal;
import com.AL.ui.builder.HtmlBuilder;
import com.AL.ui.constants.Constants;
import com.AL.ui.dao.SalesCallDao;
import com.AL.ui.dao.SalesSessionDao;
import com.AL.ui.dao.WebLookupDao;
import com.AL.ui.domain.ConsumerViewLites;
import com.AL.ui.domain.MDUProperty;
import com.AL.ui.domain.SalesCall;
import com.AL.ui.domain.SalesSession;
import com.AL.ui.domain.dynamic.dialogue.DataGroup;
import com.AL.ui.domain.dynamic.dialogue.Dialogue;
import com.AL.ui.domain.sales.CustomerLookupItem;
import com.AL.ui.domain.sales.CustomerLookupObject;
import com.AL.ui.service.config.ConfigRepo;
import com.AL.ui.service.V.CustomerService;
import com.AL.ui.service.V.DetailService;
import com.AL.ui.service.V.DialogServiceUI;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.service.V.VClient;
import com.AL.ui.service.V.impl.ConcertDialogCacheService;
import com.AL.ui.service.V.impl.MDUCacheService;
import com.AL.ui.util.HtmlFactory;
import com.AL.ui.util.SalesUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.ConsumerVO;
import com.AL.ui.vo.CustomerVO;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.ui.vo.SalesDialogueVO;
import com.AL.V.exception.UnRecoverableException;
import com.AL.V.factory.CustomerFactory;
import com.AL.xml.cm.v4.AddressListType;
import com.AL.xml.cm.v4.AddressType;
import com.AL.xml.cm.v4.BillingAccountTypeType;
import com.AL.xml.cm.v4.BillingInfoList;
import com.AL.xml.cm.v4.CustAddress;
import com.AL.xml.cm.v4.CustAddress.AddressRoles;
import com.AL.xml.cm.v4.CustBillingInfoType;
import com.AL.xml.cm.v4.CustomerContextType;
import com.AL.xml.cm.v4.CustomerType;
import com.AL.xml.cm.v4.EMailAddressType;
import com.AL.xml.cm.v4.RoleType;
import com.AL.xml.dtl.v4.DetailsRequestResponse;
import com.AL.xml.dtl.v4.MetaData;
import com.AL.xml.dtl.v4.OrderSourceResultType;
import com.AL.xml.dtl.v4.TelephonyType;
import com.AL.xml.v4.Customer;
import com.AL.xml.v4.CustomerInformation;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.NameValuePairType;
import com.AL.xml.v4.ObjectFactory;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextEntityType;
import com.AL.xml.v4.SalesContextType;
import com.google.gson.Gson;

@Controller
public class GreetingController  extends BaseController{

	private static final Logger logger = Logger.getLogger(GreetingController.class);

	@Autowired
	private SalesSessionDao salesSessionDao;

	@Autowired
	private SalesCallDao salesCallDao;

	@Autowired
	private WebLookupDao lookupDao;


	@RequestMapping(value="/searchCustomerLookup")
	protected @ResponseBody String searchCustomerLookup(HttpServletRequest request,
			HttpServletResponse response) throws Exception {		
		JSONObject obj = new JSONObject();
		try{			
			logger.info("calling customerLookup micro-service");
			String referrerId = request.getParameter("referrerIdValue");
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String zipCode = request.getParameter("zipCode");
			List<CustomerLookupObject> customerLookups = RESTClient.INSTANCE.searchCustomerLookup(referrerId, firstName, lastName, zipCode);
			StringBuffer responseStr = new StringBuffer("");
			if(customerLookups == null) {
				obj.put("customerLookups", "none");
			} else {
				Map<String,CustomerLookupObject> customerLookupMapSession = new HashMap<String, CustomerLookupObject>();
				for(CustomerLookupObject customerLookupObject : customerLookups){
					customerLookupMapSession.put(customerLookupObject.getId(), customerLookupObject);	
					for(CustomerLookupItem customerLookupItem : customerLookupObject.getLineItems()) {
						responseStr.append(customerLookupItem.getFirstName() + ",");
						responseStr.append(customerLookupItem.getLastName() + ",");
						responseStr.append(customerLookupItem.getAddress1() + ",");
						responseStr.append("<input type='button' value='Select' onclick='submitCustomerLookupSearch(" + customerLookupObject.getId() + ")'/>,");
					}
				}
				request.getSession().setAttribute("customerLookupMapSession", customerLookupMapSession);
				obj.put("customerLookups", responseStr.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw e;
		}
		return obj.toString();
	}	

	@RequestMapping(value="/greeting")
	public  ModelAndView  showGreetingInfo(HttpServletRequest request,
			HttpServletResponse response) throws UnRecoverableException {
		try{
			logger.info("showGreetingInfoBegin");
			clearPreviousSessionInfo( request);

			List<Map<String,Object>> customerVoList = new ArrayList<Map<String,Object>>();
			String primaryLanguage ="";
			String referalId = (String)request.getSession().getAttribute("referralDet");
			logger.info("referalId="+referalId);
			String [] refArray  = null;
			SalesCenterVO salesCenterVo = null;
			StringReader sr = null;
			String xmlInput = (String)request.getSession().getAttribute("dtxml");
			String referrerId = null;
			ConsumerVO dtConsumer = null;
			String vdn =null;
			String referrerFlow = null;
			String callType = null;
			String flow = null;
			String dialogueType = null;
			StringBuilder coBrandName = new StringBuilder("");
			// this is for savers offer button in recomandation page. 
			if (request.getSession().getAttribute("gotoofferpage") != null ){
				request.getSession().removeAttribute("gotoofferpage");
			}

			boolean isCustomerMatched = false;
			if(!Utils.isBlank(xmlInput)) {

				salesCenterVo = new SalesCenterVO();
				salesCenterVo.setValueByName("salesFlow.contextId" , "NA");
				request.getSession().setAttribute("salescontext",salesCenterVo);
				JAXBContext transientContainerJxbContext = null;
				//this is only for DT harness injection of DT xml data
				try {
					if (request != null && request.getSession() != null && request.getSession().getAttribute("transientConsumerView") != null){
						transientContainerJxbContext = (JAXBContext) request.getSession().getAttribute("transientConsumerView");	
					}else{
						transientContainerJxbContext = JAXBContext.newInstance(ConsumerViewLites.class);
						request.getSession().setAttribute("transientConsumerView",transientContainerJxbContext);
					}

					sr = new StringReader(xmlInput);
					Unmarshaller unmarshaller = transientContainerJxbContext.createUnmarshaller();
					JAXBElement<ConsumerVO> b = unmarshaller.unmarshal(new StreamSource(sr), ConsumerVO.class);
					dtConsumer = b.getValue();
					request.getSession().setAttribute("salescontextDt", dtConsumer);
				} 
				catch (JAXBException e) {
					e.printStackTrace();
				}
				catch (Exception ex) {
					ex.printStackTrace();
				} 
				finally {
					sr.close();
				}
				//use the DtConsumer coming from DT harness 
				referalId = dtConsumer.getDtPartner();
				referrerId = referalId;
				String referalname=dtConsumer.getDtPartner();
				refArray = new String[2];
				refArray[0]=referalId;
				refArray[1]=referalname;			
				CustomerVO customerVO = new CustomerVO();
				customerVO.setDtNameFirst(dtConsumer.getDtNameFirst());
				customerVO.setDtNameLast(dtConsumer.getDtNameLast());
				customerVO.setDtSaCity(dtConsumer.getDtSaCity());
				customerVO.setDtSaState(dtConsumer.getDtSaState());
				customerVO.setDtPartnerAccountId(dtConsumer.getDtPartnerAccountId());
				customerVO.setDtSequenceNum(String.valueOf(dtConsumer.getDtSequenceNum()));
				customerVO.setDtEmail(dtConsumer.getDtEmail());
				if(customerVoList.isEmpty()){
					customerVoList.add(getMap(customerVO,customerVO.getDtSequenceNum()));
				}else{
					customerVoList.clear();
					customerVoList.add(getMap(customerVO,customerVO.getDtSequenceNum()));
				}
				salesCenterVo.setValueByName("referrer.businessParty.referrerId", referalId);	
				salesCenterVo.setValueByName("referrer.businessParty.referrerPhoneNum", dtConsumer.getDtPartnerAccountId());
				salesCenterVo.setValueByName("referrer.businessParty.callbackNumber",dtConsumer.getDtPartnerAccountId());
				salesCenterVo.setValueByName("DT Partner", dtConsumer.getDtPartner());
			} 
			else {
				//salesCenterVo = new SalesCenterVO();
				//salesCenterVo.setValueByName("salesFlow.contextId" , "NA");
				//request.getSession().setAttribute("salescontext",salesCenterVo);

				salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
				refArray  = referalId.split("\\|");
				logger.info("refArray[0]_customerdetailsmap_not_equal_null_is="+refArray[0]);
				referrerId = refArray[0];

				String vdnamb = salesCenterVo.getValueByName("vdn");
				logger.info("vdnamb="+vdnamb);
				String vdnuecamb = salesCenterVo.getValueByName("vdnuec");
				logger.info("vdnuecamb="+vdnuecamb);

				String dtsequenceid= salesCenterVo.getValueByName("dtsequenceid");
				logger.info("dtsequenceidGreetingController="+dtsequenceid);


				vdn =refArray[2];
				salesCenterVo.setValueByName("vdn",vdn);
				logger.info("vdn="+vdn);

				List<String> refferDetailsList = new ArrayList<String>();
				refferDetailsList = getReferrerDetais(referrerId,vdn,coBrandName,vdnamb);
				referrerFlow = refferDetailsList.get(3);
				dtConsumer = new ConsumerVO();
				dtConsumer.setDtPartner(refArray[1]);
				request.getSession().setAttribute("salescontextDt", dtConsumer);
				salesCenterVo.setValueByName("referrer.businessParty.referrerId", referrerId);
				salesCenterVo.setValueByName("referrer.businessParty.referrerPhoneNum", refferDetailsList.get(1));
				salesCenterVo.setValueByName("referrer.businessParty.callbackNumber", refferDetailsList.get(1));
				salesCenterVo.setValueByName("DT Partner", refArray[0]);
				logger.info("refarray[0]="+refArray[0]);
				logger.info("refarray[1]="+refArray[1]);

				if(!Utils.isBlank(dtsequenceid)){
					Map<String,CustomerVO> customerMap = RESTClient.INSTANCE.getCustomersdetailsById(dtsequenceid, DATAEXCHANGEURL, request);
					logger.info("customerMap="+customerMap);
					CustomerVO customerVO = customerMap.get(dtsequenceid);
					if(customerVO!=null){
						customerVoList.add(getMap(customerMap.get(dtsequenceid),dtsequenceid));
						isCustomerMatched = true;
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
					}
				}
				else{
					customerVoList = getMethod(refArray);
				}

				logger.info("customerVoList="+customerVoList);
				primaryLanguage = refferDetailsList.get(2);// adding primarylanguage for customer
				logger.info("primaryLanguage="+primaryLanguage);
			}
			String contextIndicator = salesCenterVo.getValueByName("salesFlow.contextId");
			//if contextIndicator is null or empty then we need to get regular dialogues.It should be NA.
			if(Utils.isBlank(contextIndicator)){
				contextIndicator="NA";
			}

			String agentId = salesCenterVo.getValueByName("agent.id");
			String referrerName = refArray[1];
			OrderType order = createOrder(request,response, agentId, referrerId ,xmlInput,primaryLanguage, referrerName, contextIndicator);

			if(isCustomerMatched){
				RESTClient.INSTANCE.setCustomerLinkAndMatch(salesCenterVo.getValueByName("dtsequenceid"), DATAEXCHANGESETCUSTOMERLINKANDMATCHURL, String.valueOf(order.getCustomerInformation().getCustomer().getExternalId()));
			}


			salesCenterVo.setValueByName("order.id", String.valueOf(order.getExternalId()));
			logger.info("getting_email_address");
			if(dtConsumer != null && !Utils.isBlank(dtConsumer.getDtEmail())){
				order.getCustomerInformation().getCustomer().setBestEmailContact(dtConsumer.getDtEmail());
				logger.debug("email="+dtConsumer.getDtEmail());
			}
			request.getSession().setAttribute("customerID", order.getCustomerInformation().getCustomer().getExternalId());
			logger.debug("order.getCustomerInformation().getCustomer().getExternalId()"+order.getCustomerInformation().getCustomer().getExternalId());
			request.getSession().setAttribute("orderId", order.getExternalId());
			request.getSession().setAttribute("order", order);
			request.getSession().setAttribute("callStartTime", Calendar.getInstance().getTimeInMillis());
			request.getSession().setAttribute("addressId", order.getCustomerInformation().getCustomer().getAddressList().getCustomerAddress().get(0).getAddress().getExternalId());
			logger.info("referrerId="+referrerId);

			SalesDialogueVO dialogueVO = new SalesDialogueVO(); 
			logger.info("referrerFlow="+referrerFlow);

			if(referrerFlow.equalsIgnoreCase("confirm")){
				callType = "default";
				flow = "confirm";
				dialogueType = "Greeting";
			}
			else if(referrerFlow.equalsIgnoreCase("nonConfirm")){
				callType = "default";
				flow = "nonConfirm";
				dialogueType = "GreetingNonConfirm";
			}
			else if(referrerFlow.equalsIgnoreCase("referrerSpecificConfirm")){
				callType = "referrerSpecific";
				flow = "confirm";
				dialogueType = "Greeting";
			}
			else if(referrerFlow.equalsIgnoreCase("referrerSpecificNonConfirm")){
				callType = "referrerSpecific";
				flow = "nonConfirm";
				dialogueType = "GreetingNonConfirm";
			}
			else if(referrerFlow.equalsIgnoreCase("agentTransfer")){
				callType = "default";
				flow = "agentTransfer";
				dialogueType = "GreetingAgentTransfer";
			}

			logger.info("referrerFlow="+referrerFlow);
			salesCenterVo.setValueByName("referrer.callType", callType);
			salesCenterVo.setValueByName("referrer.flow", flow);
			logger.info("callType=" + callType);
			logger.info("flow=" + flow);

			Map<String, Map<String, String>> contextMap = new HashMap<String, Map<String, String>>();
			Map<String, String> saleFlow =  new HashMap<String, String>();
			saleFlow.put("salesFlow.referrer.callType", callType);
			saleFlow.put("salesFlow.dialogueType", dialogueType);
			saleFlow.put("salesFlow.contextId", contextIndicator);
			contextMap.put("salesFlow", saleFlow);

			Map<String, String> orderSource =  new HashMap<String, String>();
			orderSource.put("orderSource.referrer", String.valueOf(referrerId));
			contextMap.put("orderSource", orderSource);

			dialogueVO = DialogServiceUI.INSTANCE.getDialoguesByContext(contextMap);

			logger.info("refArray[1]="+refArray[1]);
			logger.info("dialogueVO="+dialogueVO);
			logger.info("contextIndicator="+contextIndicator);
			if(!(contextIndicator.equals("NA") || contextIndicator.equals("00") || contextIndicator.equals("05"))){
				request.getSession().setAttribute("RapidResponsecustomer", "yes");
			}
			if(!Utils.isBlank(coBrandName.toString())){
				salesCenterVo.setValueByName("referrer.businessParty.cobrandName", coBrandName.toString());//refArray[1]);
			}
			else{
				coBrandName.append("N/A" +" - "+refArray[1]);
				salesCenterVo.setValueByName("referrer.businessParty.cobrandName", coBrandName.toString());//refArray[1]);
			}
			StringBuilder events = new StringBuilder();
			List<Fieldset> fieldsetList = new ArrayList<Fieldset>();
			logger.info("CoBrandName=" +coBrandName);
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
			logger.info("Prepared_dialogue_events");
			String agentIdVal = salesCenterVo.getValueByName("agent.id");
			Long salesSessionId = insertIntoSalesSessionAndSalesCall(request, agentIdVal);		
			request.getSession().setAttribute("salesSessionId", salesSessionId);
			ModelAndView mav = new ModelAndView();
			mav.addObject("dialogue" , events.toString());
			mav.addObject("customerVoList" , customerVoList);
			mav.addObject("referalId" , referalId);
			mav.addObject("salesCenterVo" , salesCenterVo);
			logger.info("mav"+mav);
			mav.setViewName("greeting");
			logger.info("showGreetingInfoEnd");
			if (request.getSession().getAttribute("AMBTimer") != null) {
				StopWatch ambTimer = (StopWatch)request.getSession().getAttribute("AMBTimer");
				logger.info("timeTakenForScreenpop="+ambTimer.getTime());
				request.getSession().setAttribute("AMBTimer", null);

			}else{
				logger.info("This_customer_did_not_come_from_AMB");
			}

			return mav;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			throw new UnRecoverableException(e.getMessage());
		}
	}



	private void clearPreviousSessionInfo(HttpServletRequest request) throws Exception{
		logger.info("clearPreviousSessionInfo");
		HttpSession session = request.getSession();
		session.setAttribute("typeOfService", null);
		session.setAttribute("typeOfcheckBox", null);
		session.setAttribute("questionList", null);
		session.setAttribute("categoryName", null);
		session.setAttribute("productResultManager", null);
		session.setAttribute("salescontextDt", null);
		session.setAttribute("orderId", null);
		session.setAttribute("order", null);
		session.setAttribute("callStartTime", null);
		session.setAttribute("customerID", null);
		session.setAttribute("addressId", null);
		session.setAttribute("hasProducts", null);
		session.setAttribute("notepadValue", null);
		session.setAttribute("DtEmail", null);
		session.setAttribute("cycletime", null);
		session.setAttribute("address", null);
		session.setAttribute("lineItemIds", null);
		session.setAttribute("providerIds", null);
		session.setAttribute("productSrcs", null);
		session.setAttribute("utilityOffer", null);
		session.setAttribute("submitvalue", null);
		session.setAttribute("realTimeMap", null);
		session.setAttribute("recIconMap", null);
		session.setAttribute("totalPoints", "0.0");
		session.setAttribute("GUID", null);
		session.setAttribute("isMAMSuccess", null);
		session.setAttribute("pivotAssistJson",null);
		session.setAttribute("MAMproductsUpdated",null);
		session.setAttribute("ListOfProviderIds",null);

		if(session.getAttribute("savedAMBMessageData")!=null)
		{
			session.removeAttribute("savedAMBMessageData");
		}
		if(session.getAttribute("isUtilityOfferExist")!=null)
		{
			session.removeAttribute("isUtilityOfferExist");
		}
		if(session.getAttribute("isConfirmReferrerForUtility")!=null)
		{
			session.removeAttribute("isConfirmReferrerForUtility");
		}
		MDC.put("GUID", "");
		MDC.put("orderId","" );
		if(request.getSession().getAttribute("salescontext") != null){
			SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
			salesCenterVo.setValueByName("consumer.name.first", null);
			salesCenterVo.setValueByName("consumer.name.middle", null);
			salesCenterVo.setValueByName("consumer.name.last", null);
			salesCenterVo.setValueByName("customer.confirmation.number", null);
			salesCenterVo.setValueByName("referrer.businessParty.referrerId", null);
			salesCenterVo.setValueByName("referrer.businessParty.referrerPhoneNum", null);
			salesCenterVo.setValueByName("DT Partner", null);
			salesCenterVo.setValueByName("referrer.businessParty.cobrandName", null);
			salesCenterVo.setValueByName("Customer Name", null);
			salesCenterVo.setValueByName("fallback.cableProviderId", null);
			salesCenterVo.setValueByName("fallback.cableProviderName", null);
			salesCenterVo.setValueByName("fallback.telcoProviderId", null);
			salesCenterVo.setValueByName("fallback.telcoProviderId", null);
			salesCenterVo.setValueByName("campaignId", null);
			salesCenterVo.setValueByName("customer.contractAccountNumber", null);
			if(salesCenterVo.getScMap().containsKey("comcast.order.number")){
				salesCenterVo.getScMap().remove("comcast.order.number");
			}
		}
		session.setAttribute("primaryLanguage",null);
		session.setAttribute("RapidResponsecustomer", "no");
		session.setAttribute("SALES_CONTEXT", null);
		session.setAttribute("addressId", null);
		session.setAttribute("address", null);
		session.setAttribute("lastLineItem", null);
		session.setAttribute("confirmedSecurity", null);
		session.setAttribute("populatedVerizonSmartCartOrderId", null);
	}

	public ModelAndView showGreetingInfo(HttpServletRequest request, VClient VClnt, HttpServletResponse response) throws Exception{
		try {
			return showGreetingInfo(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Map<String,Object>> getList(Map<String,CustomerVO> m, String[] refArray) throws Exception{
		List<Map<String,Object>> customerVoList = new ArrayList<Map<String,Object>>();
		try{
			logger.info("in_get_List_method");
			customerVoList.clear();
			for (Entry<String,CustomerVO> entry : m.entrySet())
			{
				logger.info("in_for_loop_of_get_list");
				logger.info("entry.getValue()="+entry.getValue());
				logger.info("entry.getKey()="+entry.getKey());
				customerVoList.add(getMap(entry.getValue(),entry.getKey()));
				if(refArray!=null){
					logger.info("when_refArray_is_not_null_or_empty");
				}
				logger.info("customervolistgetListMethod="+customerVoList);
			}
		}catch(Exception e){
			e.printStackTrace();
		}	
		return customerVoList;
	}

	public Map<String,Object> getMap(CustomerVO customerVo,String id) throws Exception{
		logger.info("In_getmap_method");
		Map<String, Object> obj=new HashMap<String, Object>();
		obj.put("customerVo", customerVo);
		obj.put("id", id);
		logger.info("object is="+obj.get(customerVo));
		logger.info("obj.get(id)="+obj.get(id));
		return obj;
	}

	private OrderType createOrder(HttpServletRequest request,
			HttpServletResponse response, String agentId, String strReferrerId, String xmlInput, String primaryLanguage, String referrerName, String contextIndicator) throws Exception{
		logger.info("Create_Order_Started");
		logger.info("primaryLanguageCreateOrder="+primaryLanguage);
		ObjectFactory oFactory = new ObjectFactory();

		CustomerType customer_exist = createCustomer(request, response, agentId, strReferrerId ,xmlInput, primaryLanguage, referrerName);
		OrderType order = oFactory.createOrderType();
		logger.info("Order_object_Intialized");
		logger.info("primaryLanguageCreateCustomer="+primaryLanguage);
		order.setExternalId(0L);
		//TODO: Should source be changed?
		order.setSource("salescenter");


		order.setReferrerId(strReferrerId);
		order.setAgentId(agentId);
		Customer customer = oFactory.createCustomer();
		customer.setExternalId(customer_exist.getExternalId());

		logger.info("Added_Customer_to_Order");
		LineItemCollectionType lineItemsCollectionType = oFactory.createLineItemCollectionType();
		order.setLineItems(lineItemsCollectionType);
		logger.info("Order_creation_started");
		SalesContextType salesContext = getDefaultSalesContextType(oFactory,contextIndicator);
		salesContext.setOrderSource(Integer.parseInt(strReferrerId));
		CustomerInformation customerInformation = oFactory
				.createCustomerInformation();
		customerInformation.setCustomer(customer);
		customerInformation.setAction("referenceCustomer");
		order.setCustomerInformation(customerInformation);
		OrderType orderType = OrderService.INSTANCE.createOrder(agentId, salesContext, order);
		logger.info("Order_creation_completed");
		return orderType;
	}

	private CustomerType createCustomer(HttpServletRequest request,
			HttpServletResponse response, String agentId, String strReferrerId , String xmlInput, String primaryLanguage, String referrerName) throws Exception{

		logger.info("Create_customer_process_started");
		com.AL.xml.cm.v4.ObjectFactory oFactory = new com.AL.xml.cm.v4.ObjectFactory();
		ConsumerVO dtConsumer = new ConsumerVO();
		if(request.getSession().getAttribute("salescontextDt")!=null){
			dtConsumer = (ConsumerVO)request.getSession().getAttribute("salescontextDt");
		}
		CustomerType customer = oFactory.createCustomerType();
		String fname = "";
		if(!Utils.isBlank(dtConsumer.getDtNameFirst())){
			fname = dtConsumer.getDtNameFirst();
		}
		else{
			fname = "Default";
		}
		String lname = "";
		if(!Utils.isBlank(dtConsumer.getDtNameLast())){
			lname = dtConsumer.getDtNameLast();
		}
		else{
			lname = "Customer";
		}
		customer.setFirstName(fname);
		customer.setLastName(lname);



		if(!Utils.isBlank(primaryLanguage)){
			customer.setPrimaryLanguage(Integer.valueOf(primaryLanguage));
			request.getSession().setAttribute("primaryLanguage", Integer.valueOf(primaryLanguage));
			logger.info("customerGetPrimaryLanguage()="+customer.getPrimaryLanguage());
		}

		if(!Utils.isBlank(dtConsumer.getDtNameMiddle())){
			customer.setMiddleName(dtConsumer.getDtNameMiddle());
		}
		EMailAddressType email = new EMailAddressType();
		String hemail = "";
		if(!Utils.isBlank(dtConsumer.getDtEmail())){
			hemail = dtConsumer.getDtEmail();
		}
		email.setValue(hemail);
		customer.setBestEmailContact(hemail);
		customer.setEMailOptIn(false);
		customer.setHomeEMail(email);
		String wemail = "";
		if(!Utils.isBlank(dtConsumer.getDtEmail())){
			wemail = dtConsumer.getDtEmail();
		}
		else{
			wemail = "";
		}
		email.setValue(wemail);
		customer.setWorkEMail(email);
		logger.info("Customer_Email_address_added");

		String addressUniqueId = String.valueOf(System.currentTimeMillis());
		String billingUniqueId = customer.getLastName();

		customer.setReferrerId(Long.valueOf(strReferrerId));
		customer.setReferrerGeneralName(referrerName);
		customer.setAgentId(agentId);
		customer.setExternalId(0L);
		AddressType address = oFactory.createAddressType();
		address.setCountry("US");
		AddressListType addressList = oFactory.createAddressListType();
		AddressRoles addrRoles = oFactory.createCustAddressAddressRoles();
		addrRoles.getRole().add(RoleType.SERVICE_ADDRESS);
		CustAddress custAddress = oFactory.createCustAddress();
		custAddress.setStatus("active");
		custAddress.setAddressUniqueId(addressUniqueId);
		custAddress.setAddress(address);
		custAddress.setAddressRoles(addrRoles);
		addressList.getCustomerAddress().add(custAddress);
		BillingInfoList billingInfoList = getCustomerBillingInfo(oFactory, addressUniqueId, billingUniqueId);
		logger.info("agentId="+ agentId);
		logger.info("Customer_Address_added");
		Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
		Map<String, String> sourceEntity = new HashMap<String, String>();
		sourceEntity.put("source", "salescenter");
		data.put("source", sourceEntity);
		CustomerContextType customerContext = CustomerFactory.INSTANCE.buildCustomerContext(data);
		CustomerType customerType = CustomerService.INSTANCE.createCustomer(customer, addressList, billingInfoList,customerContext);

		request.getSession().setAttribute("billingInfoExternalId",customerType.getBillingInfoList().getBillingInfo().get(0).getExternalId());

		logger.info("Create_customer_completed");
		return customerType;
	}

	private BillingInfoList getCustomerBillingInfo(com.AL.xml.cm.v4.ObjectFactory oFactory, String addressUniqueId, String billingUniqueId) throws Exception{

		String status = "active";

		BillingInfoList billingInfoList = oFactory.createBillingInfoList();
		CustBillingInfoType custBillingInfoTypeCC = newBillingAccountTypeCC(oFactory, addressUniqueId, BillingAccountTypeType.CREDIT_CARD, status, billingUniqueId+"-ccPayinfo");
		billingInfoList.getBillingInfo().add(custBillingInfoTypeCC);

		return billingInfoList;
	}

	public CustBillingInfoType newBillingAccountTypeCC(com.AL.xml.cm.v4.ObjectFactory oFactory, String addressRef,
			BillingAccountTypeType acountType, String status,String billingUniqueId) throws Exception{

		CustBillingInfoType cBI = oFactory.createCustBillingInfoType();
		cBI.setAddressRef(addressRef);
		cBI.setBillingAccountType(acountType);
		cBI.setStatus(status);
		cBI.setExternalId(0L);
		cBI.setBillingUniqueId(billingUniqueId);
		return cBI;
	}


	public Map<String,CustomerVO>  getCustomerDetails(String dtsequenceid ) throws Exception
	{
		try{
			Map<String,CustomerVO> m = RESTClient.INSTANCE.getCustomersdetailsById(dtsequenceid, DATAEXCHANGEURL);
			return m;
		}catch(Exception e){
			logger.error("Unable_to_get_the_data_exchange_response_for_get_customer_details:"+e.getMessage());
			logger.error("Proceeding_to_Greetings_page_with_empty_customer_list:");
			e.printStackTrace();
			return new HashMap<String, CustomerVO>();
		}
	}

	public List<Map<String,Object>> getMethod(String[] refArray) throws Exception{
		logger.info("refArray[0]getMethod="+refArray[0]);
		Map<String,CustomerVO> m = new HashMap<String, CustomerVO>();
		try {
			m = RESTClient.INSTANCE.getCustomersbyReferrer(refArray[0], DATAEXCHANGEREFERRERURL);
		} catch (Exception e) {
			logger.error("Unable_to_get_the_data_exchange_response:"+e.getMessage());
			logger.error("Proceeding_to_Greetings_page_with_empty_customer_list:");
			e.printStackTrace();
			m = new HashMap<String, CustomerVO>();			
		}

		logger.info("refArray[0]DataExchangeGetMethod="+refArray[0]);
		logger.info("dataexchangeReferrerUrlDataExchangeGetMethod="+DATAEXCHANGEREFERRERURL);
		logger.info("CustomerVoMapGetMethod="+m);
		logger.info("refArray[0]getMethod="+refArray[0]);
		logger.info("refArray[1]getMethod="+refArray[1]);
		return getList(m,refArray);
	}

	@RequestMapping(value="/refresh")
	public @ResponseBody List<String> getCustomersByReferrer(HttpServletRequest request) throws Exception{
		String referalId = request.getParameter("referalId");
		logger.info("referalId="+referalId);
		List<Map<String,Object>> customerVoList = getMethod(referalId.split("\\|"));
		List<String> result = new ArrayList<String>();
		for(Map<String,Object> custVo: customerVoList){
			CustomerVO cust=(CustomerVO) custVo.get("customerVo");
			String id = (String) custVo.get("id");
			JSONObject obj = new JSONObject();
			try {
				obj.put("id", id);
				obj.put("fisrtName", cust.getDtNameFirst());
				obj.put("lastName", cust.getDtNameLast());
				obj.put("city", cust.getDtSaCity());
				obj.put("state", cust.getDtSaState());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			result.add(obj.toString());
		}
		return result;
	}

	@RequestMapping(value="/validate/customer")
	public @ResponseBody String setCustomerLinkAndMatch(HttpServletRequest request) throws Exception{
		boolean updateIndicator = false;
		String referalId = request.getParameter("referalId");
		String custId = request.getParameter("custId");
		Long customerID =(Long)request.getSession().getAttribute("customerID");
		logger.info("referalId="+referalId);
		logger.info("custId="+custId);
		logger.info("customerID="+customerID);
		updateIndicator = RESTClient.INSTANCE.setCustomerLinkAndMatch(custId, DATAEXCHANGESETCUSTOMERLINKANDMATCHURL, String.valueOf(customerID));
		JSONObject obj = new JSONObject();
		try {
			if(updateIndicator){
				obj.put("stat", true);
			}
			else{
				List<Map<String,Object>> customerVoList = getMethod(referalId.split("\\|"));
				List<String> result = new ArrayList<String>();
				for(Map<String,Object> custVo: customerVoList){
					CustomerVO cust=(CustomerVO) custVo.get("customerVo");
					String id = (String) custVo.get("id");
					try {
						obj.put("id", id);
						obj.put("fisrtName", cust.getDtNameFirst());
						obj.put("lastName", cust.getDtNameLast());
						obj.put("city", cust.getDtSaCity());
						obj.put("state", cust.getDtSaState());
					} catch (JSONException e) {
						e.printStackTrace();
					}
					result.add(obj.toString());
				}
				obj.put("stat", false);
				obj.put("errorMsg", "Error: Customer already selected refreshing data");
				obj.put("custId",custId);
				obj.put("result",result);
			}

		} 
		catch (JSONException e) {
			e.printStackTrace();
		}

		return obj.toString();
	}

	public List<String>  getReferrerDetais(String refId,String vdnValue,StringBuilder coBrandName, String vdnamb) throws Exception
	{
		List<String> refferDetailsList = new ArrayList<String>();
		int detailsCacheTimeout = ConfigRepo.getInt("*.details_cache_time_out") == 0 ? 7200000 : ConfigRepo.getInt("*.details_cache_time_out");
		DetailsRequestResponse drr = DetailService.INSTANCE.getAllOrderSources("12345", Long.valueOf(detailsCacheTimeout));   
		logger.info("vdnValueBeforeDetailsResponseCheck="+vdnValue);
		if(drr != null && !(drr.equals(""))){
			List<OrderSourceResultType> osrtList =  drr.getResponse().getOrderSourceResultElement();
			String phoneNumber = "";
			String primaryLanguage = "";
			String referrerFlow = "confirm";
			for(OrderSourceResultType orst: osrtList){
				String referrerId = orst.getOrderSource().getBusinessParty().getExternalId();

				List<TelephonyType> telephonyList = orst.getOrderSource().getTelephonyList().getTelephony();
				for(TelephonyType telephonyType : telephonyList){
					if(Constants.SALES.equalsIgnoreCase(telephonyType.getCallType())){
						String vdn = telephonyType.getVdn();
						//String vdn = orst.getOrderSource().getTelephonyList().getTelephonies()
						// ideally it should be when refid equals referrerId and when vdn equals vdnuec



						if(refId.equals(referrerId) && vdnValue.equals(vdn) ){
							logger.info("orderSourceTelephonyGetVdn="+vdn);
							if(orst.getOrderSource().getBusinessParty().getMetadataList() != null &&
									orst.getOrderSource().getBusinessParty().getMetadataList().getMetadata()!= null){
								for(MetaData metaData:orst.getOrderSource().getBusinessParty().getMetadataList().getMetadata()){
									if(metaData.getName().equalsIgnoreCase("cobrandname")){
										coBrandName.append( metaData.getValue());
									}
								}
							}

							String imageUrl= orst.getOrderSource().getImageUrl(); //retrieving imageurl                

							phoneNumber = telephonyType.getPhoneNumber();//retrieving phonenumber

							primaryLanguage = telephonyType.getLanguage();	// retrieving PrimaryLanguage	
							logger.info("primaryLanguage="+primaryLanguage);
							if(primaryLanguage.equalsIgnoreCase("English")){
								primaryLanguage ="0";					
							}
							else {
								primaryLanguage= "1";
							}
							if(orst.getOrderSource().getProgram().getMetadataList() != null &&
									orst.getOrderSource().getProgram().getMetadataList().getMetadata()!= null){
								for(MetaData metaData:orst.getOrderSource().getProgram().getMetadataList().getMetadata()){
									if(metaData.getName().equalsIgnoreCase("FLOW")){
										referrerFlow =  metaData.getValue();
									}
								}
							}
							refferDetailsList.add(0,imageUrl);
							refferDetailsList.add(1,phoneNumber);
							logger.info("primaryLanguageRefdetList="+primaryLanguage);
							refferDetailsList.add(2,primaryLanguage);
							refferDetailsList.add(3, referrerFlow);
						}
					}
				}
			}
		}
		return refferDetailsList;
	}

	public Long insertIntoSalesSessionAndSalesCall(HttpServletRequest request, String agentId)  throws Exception{
		logger.info("Inserting_data_into_SalesSession_and_SalesCall_tables");
		SalesSession salesSession = new SalesSession();
		boolean isSalesCallPopulated = false; 
		if(request.getSession().getAttribute("isSalesCallPopulated") != null){
			isSalesCallPopulated = (Boolean) request.getSession().getAttribute("isSalesCallPopulated");
		}
		logger.info("IsSalesCallPopulated="+isSalesCallPopulated);
		salesSession.setOrderId((Long)request.getSession().getAttribute("orderId"));
		try{
			if(request.getSession().getAttribute("idlePageStartTime") != null){
				salesSession.setStartTime((Calendar)request.getSession().getAttribute("idlePageStartTime"));
				salesSession.setEndTime(Calendar.getInstance());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if(!Utils.isBlank(agentId)){
			salesSession.setAgent(agentId);
		}
		salesSession.setCustomerId((Long)request.getSession().getAttribute("customerID"));
		long salesCallId = 0L;
		if(request.getSession().getAttribute("salesCallId") != null){
			salesCallId = (Long) request.getSession().getAttribute("salesCallId");
			logger.info("SalesCallId="+salesCallId);
		}
		salesSession.setSalesCallId(salesCallId);
		salesSessionDao.insertSalesSession(salesSession);
		SalesSession ss = salesSessionDao.getSalesSession((Long)request.getSession().getAttribute("orderId"), (Long)request.getSession().getAttribute("customerID"));
		Long salesSessionId = ss.getSalesSessionId();
		logger.info("SalesSessionId="+salesSessionId);
		String ambDisconnectDateTime = null;
		if(request.getSession().getAttribute("ambDisconnectdatetime") != null){
			ambDisconnectDateTime = (String) request.getSession().getAttribute("ambDisconnectdatetime");
		}
		String ambConnectDateTime = null;
		if(request.getSession().getAttribute("currentdatetime") != null){
			ambConnectDateTime = (String) request.getSession().getAttribute("currentdatetime");
		}
		try{
			JSONObject feedback = new JSONObject();
			if(! isSalesCallPopulated){
				SalesCall salesCall = new SalesCall();
				if(request.getSession().getAttribute("salescontextAuthAMB") != null){
					feedback = (JSONObject) request.getSession().getAttribute("salescontextAuthAMB");
					String agentPhoneId = feedback.getString("agentPhoneId");
					String calledAddress = feedback.getString("calledAddress");

					String callingAddress = feedback.getString("callingAddress");
					String uec = feedback.getString("userEnteredCode");
					String ucid = feedback.getString("ucid");
					salesCall.setCalledAddress(calledAddress);
					salesCall.setCallingAddress(callingAddress);
					SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss z (Z)");
					if(!Utils.isBlank(ambConnectDateTime)){
						Calendar connectCal = Calendar.getInstance();
						String ambConnectDateTime2 = ambConnectDateTime.substring(0, 31);
						String ambConnectDateTime3 = ambConnectDateTime.substring(31, ambConnectDateTime.length());
						ambConnectDateTime = ambConnectDateTime2 + ":" + ambConnectDateTime3;
						connectCal.setTime(sdf.parse(ambConnectDateTime));
						salesCall.setEstablishedTime(connectCal);
					}
					else{
						salesCall.setEstablishedTime(Calendar.getInstance());
					}
					if(!Utils.isBlank(ambDisconnectDateTime)){
						Calendar disconnectCal = Calendar.getInstance();
						String ambDisconnectDateTime2 = ambDisconnectDateTime.substring(0, 31);
						String ambDisconnectDateTime3 = ambDisconnectDateTime.substring(31, ambDisconnectDateTime.length());
						ambDisconnectDateTime = ambDisconnectDateTime2 + ":" + ambDisconnectDateTime3;
						disconnectCal.setTime(sdf.parse(ambDisconnectDateTime));
						salesCall.setDisconnectTime(disconnectCal);
					}
					else{
						salesCall.setDisconnectTime(Calendar.getInstance());
					}

					if(!Utils.isBlank(ucid))
					{
						request.getSession().setAttribute("UCID",ucid);
						salesCall.setUcid(ucid);
					}
					else
					{
						request.getSession().setAttribute("UCID",null);
					}

					if(!Utils.isBlank(uec)){
						if(uec.contains("#")){
							uec = uec.replaceAll("#", "");
						}
						salesCall.setUec(Long.valueOf(uec));
					}
					salesCall.setAgentPhoneId(agentPhoneId);
				}
				if(!Utils.isBlank(agentId)){
					salesCall.setAgent(agentId);
				}
				salesCall.setAlertingTime(Calendar.getInstance());
				salesCall.setSalesSessionId(salesSessionId);
				salesCallDao.put(salesCall);
				salesCallId = salesCallDao.getSalesCallId(salesSessionId);
				logger.info("SalesCallId="+salesCallId);
				salesSession.setSalesCallId(salesCallId);
				salesSessionDao.updateSalesSession(salesSession);
				request.getSession().setAttribute("salesCallId",salesCallId);
				request.getSession().setAttribute("isSalesCallPopulated",true);
				logger.info("Inserted_data_into_SalesSession_and_SalesCall_tables");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return salesSessionId;
	}

	private SalesContextType getDefaultSalesContextType(
			com.AL.xml.v4.ObjectFactory oFactoryOM, String contextIndicator)  throws Exception{

		SalesContextType salesContextType = oFactoryOM.createSalesContextType();

		SalesContextEntityType salesContextEntityType = oFactoryOM
				.createSalesContextEntityType();
		salesContextEntityType.setName(Constants.SYP);

		NameValuePairType nameValuePairType = oFactoryOM
				.createNameValuePairType();
		nameValuePairType.setName(Constants.SOURCE);
		nameValuePairType.setValue(Constants.SALESCENTER);
		salesContextEntityType.getAttribute().add(nameValuePairType);

		nameValuePairType = oFactoryOM
				.createNameValuePairType();
		nameValuePairType.setName(Constants.CONTEXT_INDICATOR);
		nameValuePairType.setValue(contextIndicator);
		salesContextEntityType.getAttribute().add(nameValuePairType);

		salesContextType.getEntity().add(salesContextEntityType);

		return salesContextType;
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return null;
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

	@RequestMapping(value="/getMDUProperties")
	public @ResponseBody String getMDUPropertiesByState(HttpServletRequest request) throws Exception{
		String state = request.getParameter("state");
		String mduProgramExternalId = request.getParameter("mduProgramExternalId");
		logger.info("mduProgramExternalId"+mduProgramExternalId);
		Map<Integer,Map<String,List<MDUProperty>>> stateExtIdMDUMap = (Map<Integer,Map<String,List<MDUProperty>>>)MDUCacheService.INSTANCE.get("mduProperties");
		if(!Utils.isBlank(state) && !Utils.isBlank(mduProgramExternalId)){
			Map<String,List<MDUProperty>> stateMDUMap = stateExtIdMDUMap.get(Integer.valueOf(mduProgramExternalId.trim()));
			if(stateMDUMap != null){
				List<MDUProperty> mduPropertyList = stateMDUMap.get(state);
				if(mduPropertyList != null && ! mduPropertyList.isEmpty()){
					return new Gson().toJson(mduPropertyList);
				}
			}
		}
		return "";
	}

	@RequestMapping(value="/getHintsContentforObjection")
	public @ResponseBody String getHintsContentForObjection(HttpServletRequest request) throws Exception{
		String pageTitle = request.getParameter("pageTitle");
		SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
		String drupalContentUrl = ConfigRepo.getString("*.drupal_dialogues_url");
		logger.info("getObjection_busters_In_page="+pageTitle);

		if (!Utils.isBlank(drupalContentUrl) 
				&& Boolean.valueOf(String.valueOf(request.getSession().getAttribute("dispDrupalDailgVal")))){
			salesCenterVo.setValueByName("drupalContentUrl", drupalContentUrl);
			salesCenterVo.setValueByName("dispDrupalDailgVal", String.valueOf(request.getSession().getAttribute("dispDrupalDailgVal")));
			if(salesCenterVo != null){
				Map<String, Map<String, String>> contextMap = (Map<String, Map<String, String>>)request.getSession().getAttribute("dynamicFlowContextMap");
				Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");
				String dialoguesFromDrupal = SalesUtil.getDrupalContentForHints(dynamicFlow, salesCenterVo);
				if(!Utils.isBlank(dialoguesFromDrupal)){
					return dialoguesFromDrupal;
				}
			}
		}

		return "";
	}

	@RequestMapping(value="/getHintsContentForProviders")
	public @ResponseBody String getHintsContentForProviders(HttpServletRequest request) throws Exception{
		String pageTitle = request.getParameter("pageTitle");
		SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
		String drupalContentUrl = ConfigRepo.getString("*.drupal_dialogues_url");
		logger.info("getPrviderHints_In_page="+pageTitle);
		if (!Utils.isBlank(drupalContentUrl) 
				&& Boolean.valueOf(String.valueOf(request.getSession().getAttribute("dispDrupalDailgVal")))){
			salesCenterVo.setValueByName("drupalContentUrl", drupalContentUrl);
			salesCenterVo.setValueByName("dispDrupalDailgVal", String.valueOf(request.getSession().getAttribute("dispDrupalDailgVal")));
			if(salesCenterVo != null){
				String dialoguesFromDrupal = SalesUtil.getDrupalContentForProviderHints(salesCenterVo);
				if(!Utils.isBlank(dialoguesFromDrupal)){
					return dialoguesFromDrupal;
				}
			}
		}
		return "";
	}
	@RequestMapping(value="/getContentForSalesTips")
	public @ResponseBody String getDrupalContentForSalesTips(HttpServletRequest request) {
		String salesTips = null;
		try {
			String defaultSalesTipsKey = "sales_tips";
			logger.info("drupalDialogueCacheKey_for_Sales_Tips"+defaultSalesTipsKey);
			SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
			Object dialogueObject = ConcertDialogCacheService.INSTANCE.get(defaultSalesTipsKey);
			if(dialogueObject != null)
			{
				salesTips = String.valueOf(dialogueObject);
			}
			if (Utils.isBlank(salesTips)){
				salesTips = RESTClientForDrupal.INSTANCE.getDialoguesFromDrupal(defaultSalesTipsKey, salesCenterVo.getValueByName("drupalContentUrl"));
				if (! Utils.isBlank(salesTips)){
					ConcertDialogCacheService.INSTANCE.store(defaultSalesTipsKey, salesTips);
				}
			}
		} catch (Exception e) {
			logger.error("error_in_getDialoguesFormDrupalContentForProviderHints"+e.getMessage());
		}
		return salesTips;
	}
	@RequestMapping(value="/getContentForPivotAssist")
	public @ResponseBody String getDrupalContentForPivotAssist(HttpServletRequest request) {
		String pivotAssistData = null;
		try {
			String defaultPivotAssistKey = "pivot_assist";
			String pageTitle = request.getParameter("pageTitle");
			logger.info("drupalDialogueCacheKey for PivotAssist_In_Page ="+pageTitle);
			SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
			Object dialogueObject = ConcertDialogCacheService.INSTANCE.get(defaultPivotAssistKey);
			if(dialogueObject != null)
			{
				pivotAssistData = String.valueOf(dialogueObject);
			}
			if (Utils.isBlank(pivotAssistData)){
				pivotAssistData = RESTClientForDrupal.INSTANCE.getDialoguesFromDrupal(defaultPivotAssistKey, salesCenterVo.getValueByName("drupalContentUrl"));
				if (! Utils.isBlank(pivotAssistData)){
					ConcertDialogCacheService.INSTANCE.store(defaultPivotAssistKey, pivotAssistData);
				}
			}
		}
		catch (Exception e) {
			logger.error("error_in_getDialoguesFromDrupalContentFordefaultPivotAssistKey"+e.getMessage());
		}
		return pivotAssistData;
	}
	@RequestMapping(value="/emailTicket")
	protected ModelAndView emailTicket(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("emailTicket_begin");
		ModelAndView view = new ModelAndView("emailDoNotCall");
		String mailType = request.getParameter("mail_type");
		request.getSession().setAttribute("mailType", mailType);
		logger.info("mailType****"+mailType);
		//Order #, GUID, Agent ID, VDN, Referrer (Name and ID), Customer First name, Customer Last Name, Address , Best contact number, Email Id information already there
		request.getSession().setAttribute("mailType", mailType);
		SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
		if(salesCenterVo != null){
			salesCenterVo.getScMap().remove("ticketImgName");
			salesCenterVo.getScMap().remove("ticketImgString");
			salesCenterVo.getScMap().remove("ticketIframeImgName");
			salesCenterVo.getScMap().remove("ticketIframeImgString");
			salesCenterVo.getScMap().remove("pageTitle");
			String imgName = (String)request.getSession().getAttribute("ticketImgName");
			String b64 = (String)request.getSession().getAttribute("ticketImgString");
			if(!Utils.isBlank(imgName)){
				salesCenterVo.setValueByName("ticketImgName", imgName);
				salesCenterVo.setValueByName("ticketImgString", b64);
				request.getSession().removeAttribute("ticketImgName");
				request.getSession().removeAttribute("ticketImgString");
			}			
			String iFrameImgName = (String)request.getSession().getAttribute("ticketIframeImgName");
			String iFrameB64 = (String)request.getSession().getAttribute("ticketIframeImgString");
			if(!Utils.isBlank(iFrameImgName)){
				salesCenterVo.setValueByName("ticketIframeImgName", iFrameImgName);
				salesCenterVo.setValueByName("ticketIframeImgString", iFrameB64);
				request.getSession().removeAttribute("ticketIframeImgName");
				request.getSession().removeAttribute("ticketIframeImgString");
			}
			String pageTitle = request.getParameter("pageTitle");
			if(!Utils.isBlank(pageTitle)){
				salesCenterVo.setValueByName("pageTitle", pageTitle);
			}
		}
		return view;
	}

	@RequestMapping(value="/emailSend")
	public @ResponseBody String emailSend(HttpServletRequest request) {
		logger.info("In emailSend method");
		try{
		String orderNumber = request.getParameter("orderNumber");
		String customerName = request.getParameter("customerName");
		String bestContactNumber = request.getParameter("bestContactNumber");
		String email = request.getParameter("email");
		String guid = (String) request.getSession().getAttribute("GUID");
		String message = request.getParameter("message");
		String emailType = request.getParameter("emailType");

		String vdn = request.getParameter("vdn");
		String agentId = request.getParameter("agentId");
		String referrer = request.getParameter("referrer");
		String address = request.getParameter("address");
		String zip = request.getParameter("zip");
		String unittype = request.getParameter("unittype");
		String state = request.getParameter("state");
		String city = request.getParameter("city");
		address = address + " " + unittype + " " + city + " " + state + " " + zip;
		if (!Utils.isBlank(customerName)){
			customerName = customerName.replaceAll("[^a-zA-Z0-9]", " ");	
		}
		if (!Utils.isBlank(referrer)){
			referrer = referrer.replaceAll("[^a-zA-Z0-9]", " ");	
		}
		if (!Utils.isBlank(address)){
			address = address.replaceAll("[^a-zA-Z0-9]", " ");	
		}
		logger.info("emailSend_orderNumber="+orderNumber);
		logger.info("customerName="+customerName);
		logger.info("message="+message);
		logger.info("email="+email);
		logger.info("bestContactNumber="+bestContactNumber);
		logger.info("emailType="+emailType);
		logger.info("vdn="+vdn);
		logger.info("agentId="+agentId);
		logger.info("referrer="+referrer);
		logger.info("address="+address);
		String content = null;  
		String emailFrom =ConfigRepo.getString("*.no_reply_email") == null ? "noreply@AL.com" : ConfigRepo.getString("*.no_reply_email");
		String toEmail = null;
		SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
		if (emailType != null && emailType.equalsIgnoreCase("tootip_doNotCall")){
			content =  buildDNCEmailData(orderNumber, customerName, bestContactNumber, email, message);
			//toEmail = "donotcall@AL.com";
			toEmail = ConfigRepo.getString("*.do_not_call_email") == null ? "donotcall@AL.com" : ConfigRepo.getString("*.do_not_call_email");
			emailType = "Do Not Call";
		}else if (emailType != null && emailType.equalsIgnoreCase("tootip_IT")){
			//Order #, GUID, Agent ID, VDN, Referrer (Name and ID), Customer First name, Customer Last Name, Address , Best contact number, Email Id information
			//toEmail = "ithelpdesk@AL.com";
			String pageTitle = "";
			if(salesCenterVo != null && !Utils.isBlank(salesCenterVo.getValueByName("pageTitle"))){
				pageTitle = " -- "+salesCenterVo.getValueByName("pageTitle");
				salesCenterVo.getScMap().remove("pageTitle");
			}
			emailType = "Report Problem" + pageTitle;
			toEmail = ConfigRepo.getString("*.it_helpdesk_email") == null ? "ithelpdesk@AL.com" : ConfigRepo.getString("*.it_helpdesk_email");
			content = buildEmailData(orderNumber, guid, agentId, vdn, referrer, customerName,address, bestContactNumber, email, message,salesCenterVo);
		}else if (emailType != null && emailType.equalsIgnoreCase("tootip_selfReport")){
			//toEmail = "ErrorReview@AL.com";
			emailType = "Self Report";
			toEmail = ConfigRepo.getString("*.error_review_email") == null ? "ErrorReview@AL.com" : ConfigRepo.getString("*.error_review_email");
			content = buildEmailData(orderNumber, customerName, bestContactNumber, email, message);
		}
		
		GenericEmailHelper.sendEmail(content, toEmail, emailType, emailFrom);
		}
		catch (Exception e) {
			logger.error("error_in_emailSend"+e.getMessage());
		}
		return null;
	}
     private String buildEmailData(String orderNumber, String customerName, String bestContactNumber, String email, 
			String message) {

 		StringBuffer builder=new StringBuffer();
 		builder.append("<html>");

 		builder.append("<div>");
 		builder.append("<label><b>OrderNumber: </b></label>");
 		builder.append("<span>");
 		builder.append(orderNumber);	
 		builder.append("</span>");
 		builder.append("</div>");

 		builder.append("<div>");
 		builder.append("<label><b>CustomerName: </b></label>");
 		builder.append("<span>");
 		builder.append(customerName);	
 		builder.append("</span>");
 		builder.append("</div>");

 		builder.append("<div>");
 		builder.append("<label><b>BestContactNumber: </b></label>");
 		builder.append("<span>");
 		builder.append(bestContactNumber);	
 		builder.append("</span>");
 		builder.append("</div>");

 		builder.append("<div>");
 		builder.append("<label><b>Email: </b></label>");
 		builder.append("<span>");
 		builder.append(email);	
 		builder.append("</span>");
 		builder.append("</div>");

 		builder.append("<br>");
 		builder.append("<div>");
 		builder.append("<label><b>Please provide detailed reason for self-reporting : </b></label>");
 		builder.append("<span>");
 		builder.append(message);
 		builder.append("</span>");
 		builder.append("</div>");

 		builder.append("</html>");

 		return builder.toString();
 	}

	//Order #, Customer First Name, Customer Last Name, best contact number, customer's email address
	
	private String buildEmailData(String orderNumber, String guid, String agentId, String vdn,
			String referrer, String customerName, String address, String bestContactNumber, String email,
			String message, SalesCenterVO salesCenterVo) {


		StringBuffer builder=new StringBuffer();
		builder.append("<html>");

		builder.append("<div>");
		builder.append("<label><b>Description of the problem: </b></label>");
		builder.append("<span>");
		builder.append(message);	
		builder.append("</span>");
		builder.append("</div>");
		
		builder.append("<br>");
		builder.append("<hr/>");
		builder.append("<div>");
		builder.append("<label><b>Info from Concert session: </b></label>");
		builder.append("</div>");
		
		builder.append("<div>");
		builder.append("<label><b>OrderNumber: </b></label>");
		builder.append("<span>");
		builder.append(orderNumber);	
		builder.append("</span>");
		builder.append("</div>");

		builder.append("<div>");
		builder.append("<label><b>GUID: </b></label>");
		builder.append("<span>");
		if(!Utils.isBlank(guid)){
			builder.append(guid);
		}else{
			builder.append("");	
		}
		builder.append("</span>");
		builder.append("</div>");
		
		builder.append("<div>");
		builder.append("<label><b>Agent Id: </b></label>");
		builder.append("<span>");
		builder.append(agentId);	
		builder.append("</span>");
		builder.append("</div>");
		
		builder.append("<div>");
		builder.append("<label><b>CustomerName: </b></label>");
		builder.append("<span>");
		builder.append(customerName);	
		builder.append("</span>");
		builder.append("</div>");

		builder.append("<div>");
		builder.append("<label><b>Address: </b></label>");
		builder.append("<span>");
		builder.append(address);	
		builder.append("</span>");
		builder.append("</div>");

		builder.append("<div>");
		builder.append("<label><b>VDN: </b></label>");
		builder.append("<span>");
		builder.append(vdn);	
		builder.append("</span>");
		builder.append("</div>");
		
		builder.append("<div>");
		builder.append("<label><b>Referrer (Name and ID): </b></label>");
		builder.append("<span>");
		builder.append(referrer);	
		builder.append("</span>");
		builder.append("</div>");
		String imgName = null;
		if(salesCenterVo != null && !Utils.isBlank(salesCenterVo.getValueByName("ticketImgString"))){
			imgName = salesCenterVo.getValueByName("ticketImgName");
		}
		String s3EmailImgUrl = ConfigRepo.getString("*.s3_email_image_url") != null ? ConfigRepo.getString("*.s3_email_image_url") : "https://s3.amazonaws.com/AL-V-test/it_tickets/images";
		if(!Utils.isBlank(imgName)){
	 		builder.append("<div>");
	 		builder.append("<span>");
	 		builder.append("<img src='"+s3EmailImgUrl+"/"+imgName+"' height='550' alt='"+imgName+"' border='0' />");
	 		builder.append("</span>");
	 		builder.append("</div>");
		}
		String iFrameImgName = null;
		if(salesCenterVo != null && !Utils.isBlank(salesCenterVo.getValueByName("ticketIframeImgName"))){
			iFrameImgName = salesCenterVo.getValueByName("ticketIframeImgName");
		}
		if(!Utils.isBlank(iFrameImgName)){
	 		builder.append("<div>");
	 		builder.append("<span>");
	 		builder.append("<img src='"+s3EmailImgUrl+"/"+iFrameImgName+"' height='550' alt='"+iFrameImgName+"' border='0' />");
	 		builder.append("</span>");
	 		builder.append("</div>");
		}
		
		builder.append("</html>");

		return builder.toString();
	
	}

	private String buildDNCEmailData(String orderNumber, String customerName,
			String bestContactNumber, String email, String message) {

		StringBuffer builder=new StringBuffer();
		builder.append("<html>");

		builder.append("<div>");
		builder.append("<label><b>OrderNumber: </b></label>");
		builder.append("<span>");
		builder.append(orderNumber);	
		builder.append("</span>");
		builder.append("</div>");

		builder.append("<div>");
		builder.append("<label><b>CustomerName: </b></label>");
		builder.append("<span>");
		builder.append(customerName);	
		builder.append("</span>");
		builder.append("</div>");

		builder.append("<div>");
		builder.append("<label ><b>BestContactNumber: </b></label>");
		builder.append("<span>");
		builder.append(bestContactNumber);	
		builder.append("</span>");
		builder.append("</div>");

		builder.append("<div>");
		builder.append("<label><b>Email: </b></label>");
		builder.append("<span>");
		builder.append(email);	
		builder.append("</span>");
		builder.append("</div>");

		builder.append("<br>");
		
		builder.append("<div>");
		builder.append("<label><b>Did customer ask for copy of Do Not Call policy? </b></label>");
		builder.append("<span>");
		builder.append(message);
		builder.append("</span>");
		builder.append("</div>");

		builder.append("</html>");

		return builder.toString();
	}
}

