package com.AL.controller;

import static com.AL.ui.util.ConfigProperties.DATAEXCHANGEBYCALLINGADDRESS;
import static com.AL.ui.util.ConfigProperties.DATAEXCHANGEREFERRERURL;
import static com.AL.ui.util.ConfigProperties.DATAEXCHANGESETCUSTOMERLINKANDMATCHURL;
import static com.AL.ui.util.ConfigProperties.DATAEXCHANGEURL;
import static com.AL.ui.util.ConfigProperties.PAUSEANDRESUMEURL;

import java.io.IOException;
import java.io.StringReader;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.servlet.ServletException;
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
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.AL.html.Fieldset;
import com.AL.mail.GenericEmailHelper;
import com.AL.ui.builder.HtmlBuilder;
import com.AL.ui.constants.Constants;
import com.AL.ui.dao.CustomerTrackerDao;
import com.AL.ui.dao.QualificationPopUpRefDetailsDao;
import com.AL.ui.dao.SalesCallDao;
import com.AL.ui.dao.SalesSessionDao;
import com.AL.ui.dao.WebLookupDao;
import com.AL.ui.domain.ConsumerViewLites;
import com.AL.ui.domain.CustomerCallsCount;
import com.AL.ui.domain.CustomerTrackerDetails;
import com.AL.ui.domain.SalesCall;
import com.AL.ui.domain.SalesSession;
import com.AL.ui.domain.WebLookupCollection;
import com.AL.ui.domain.dynamic.dialogue.DataGroup;
import com.AL.ui.domain.dynamic.dialogue.Dialogue;
import com.AL.ui.service.config.ConfigRepo;
import com.AL.ui.service.V.CustomerService;
import com.AL.ui.service.V.DetailService;
import com.AL.ui.service.V.DialogServiceUI;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.service.V.VClient;
import com.AL.ui.service.V.impl.MDUCacheService;
import com.AL.ui.util.HtmlFactory;
import com.AL.ui.util.SalesUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.ConsumerVO;
import com.AL.ui.vo.CustomerVO;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.ui.vo.SalesDialogueVO;
import com.AL.V.exception.BaseException;
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
import com.AL.xml.dtl.v4.ProgramType;
import com.AL.xml.dtl.v4.TelephonyType;
import com.AL.xml.v4.Customer;
import com.AL.xml.v4.CustomerInformation;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.NameValuePairType;
import com.AL.xml.v4.ObjectFactory;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextEntityType;
import com.AL.xml.v4.SalesContextType;

import edu.emory.mathcs.backport.java.util.Arrays;
//import com.AL.ui.service.V.impl.ThoughtspotCacheService;


@Component
public class GreetingWebflowController extends BaseController {

	private static final Logger logger = Logger.getLogger(GreetingWebflowController.class);

	@Autowired
	private SalesSessionDao salesSessionDao;

	@Autowired
	private SalesCallDao salesCallDao;

	@Autowired
	private WebLookupDao lookupDao;
	
	@Autowired
	private CustomerTrackerDao customerTrackerDao;
	
	private static final String FOLDER_SUFFIX = "/";

	@SuppressWarnings("unchecked")
	public Event showGreetingInfo(HttpServletRequest request,
			HttpServletResponse response, RequestContext context) throws UnRecoverableException ,ServletException, IOException{
		StopWatch timer = new StopWatch();
		timer.start();
		long startTimer = 0;
		try{
			String agentTestId = "satavares";
			String callCenter = null;
			String region = null;
			logger.info("showGreetingInfoBegin");
		//	getThoughtSpotDataFromCache(context,request);
			//pouplateTopTenFromCache(context,request);
			clearPreviousSessionInfo( request);
			List<Map<String,Object>> customerVoList = new ArrayList<Map<String,Object>>();
			String primaryLanguage ="";
			String referalId = (String) request.getSession().getAttribute("referralDet");
			String sameCall = (String) request.getSession().getAttribute("sameCall");
			logger.info("referalId="+referalId);
			String [] refArray  = null;
			SalesCenterVO salesCenterVo = null;
			StringReader sr = null;
			String xmlInput = (String) request.getSession().getAttribute("dtxml");
			String referrerId = null;
			ConsumerVO dtConsumer = null;
			String vdn =null;
			boolean isCallingAddressMatched = false;
			StringBuilder coBrandName = new StringBuilder("");
			List<String> refferDetailsList = new ArrayList<String>();
			// this is for savers offer button in recomandation page. 
			if (request.getSession().getAttribute("gotoofferpage") != null ){
				request.getSession().removeAttribute("gotoofferpage");
			}
			String providersImageLocation = ConfigRepo.getString("*.provider_logo_location") == null ? null : ConfigRepo.getString("*.provider_logo_location");
			logger.info("salescenterProvidersImageLocation : "+providersImageLocation);
			if(!Utils.isBlank(providersImageLocation)){
				request.getSession().setAttribute("providersImageLocation", providersImageLocation);
			}
			boolean isCustomerMatched = false;
			Map<String, Map<String, String>> contextMap = (Map<String, Map<String, String>>)request.getSession().getAttribute("dynamicFlowContextMap");
			Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");
			JAXBContext transientContainerJxbContext = null;
			if(!Utils.isBlank(xmlInput)) {

				salesCenterVo = new SalesCenterVO();
				salesCenterVo.setValueByName("salesFlow.contextId" , "NA");
				request.getSession().setAttribute("salescontext",salesCenterVo);
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
				salesCenterVo.setValueByName("referrer.businessParty.referrerName", referalname);
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
				logger.info("referal id ::"+referalId);

				String vdnamb = salesCenterVo.getValueByName("vdn");
				if(request.getSession().getAttribute("salescontextAuthAMB") != null && !Utils.isBlank(vdnamb)){
					JSONObject feedback = (JSONObject) request.getSession().getAttribute("salescontextAuthAMB");
					String ucid = feedback.getString("ucid");
					String agentPhoneId = feedback.getString("agentPhoneId");
					logger.info("screen popup successful with VDN="+vdnamb +" agentPhoneId="+agentPhoneId+" ucid="+ucid);
				}
				else if(Utils.isBlank(sameCall)){ 
					request.getSession().setAttribute("fuseResponseData",null);
				}
				String vdnuecamb = salesCenterVo.getValueByName("vdnuec");
				logger.info("vdnuecamb="+vdnuecamb);

				String dtsequenceid= salesCenterVo.getValueByName("dtsequenceid");
				logger.info("dtsequenceidGreetingController="+dtsequenceid);


				vdn =refArray[2];
				salesCenterVo.setValueByName("vdn",vdn);
				logger.info("vdn="+vdn);


				refferDetailsList = getReferrerDetais(referrerId,vdn,coBrandName);
				setBusinessPartyDetailsToVO(referrerId,vdn,salesCenterVo);
				dtConsumer = new ConsumerVO();
				dtConsumer.setDtPartner(refArray[1]);
				request.getSession().setAttribute("salescontextDt", dtConsumer);
				salesCenterVo.setValueByName("referrer.businessParty.referrerName", refArray[1]);
				salesCenterVo.setValueByName("referrer.businessParty.referrerId", referrerId);
				salesCenterVo.setValueByName("referrer.businessParty.referrerPhoneNum", refferDetailsList.get(1));
				salesCenterVo.setValueByName("referrer.businessParty.callbackNumber",refferDetailsList.get(1));
				salesCenterVo.setValueByName("referrer.businessParty.url",refferDetailsList.get(3));
				salesCenterVo.setValueByName("referrer.dateDelta",refferDetailsList.get(10));
				salesCenterVo.setValueByName("referrer.program.emailCallback",refferDetailsList.get(11));
				salesCenterVo.setValueByName("orderSource.program.name",refferDetailsList.get(13));

				salesCenterVo.setValueByName("DT Partner", refArray[0]);
				logger.info("refarray[0]="+refArray[0]);
				logger.info("refarray[1]="+refArray[1]);

				if(dynamicFlow.get("dynamicFlow.flowType").contains("consumersInteractions")){
					String callingAddress = salesCenterVo.getValueByName("callingAddress");
					if(!Utils.isBlank(callingAddress)){
						CustomerVO customerVO = getConsumersInteractionsCustomerVO(callingAddress, refArray[0], customerVoList, salesCenterVo, startTimer, timer, request);
						if(customerVO != null){
							isCustomerMatched = true;
							isCallingAddressMatched = true;
						}
						else{
							customerVoList = getMethod(refArray);
							salesCenterVo.setValueByName("consumer.name.nameBlock", ""); 
						}
					}
					else{
						customerVoList = getMethod(refArray);
						salesCenterVo.setValueByName("consumer.name.nameBlock", ""); 
					}
				}
				else if(!Utils.isBlank(dtsequenceid)){
					startTimer=timer.getTime();
					Map<String,CustomerVO> customerMap = RESTClient.INSTANCE.getCustomersdetailsById(dtsequenceid, DATAEXCHANGEURL, request);
					logger.info("TimeTakenforRestClientServicecall=" +(timer.getTime() - startTimer));
					logger.debug("customerMap *************************"+customerMap);
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
			String agentName = salesCenterVo.getValueByName("agent.name.first");
			salesCenterVo.setValueByName("agent.name", agentName);
			String referrerName = refArray[1];
			startTimer= timer.getTime();
			String orderProgramExternalId = "";
			String orderOrderSourceExternalId = "";
			String orderVdn = "";
			if (!Utils.isBlank(refferDetailsList.get(4))) {
				orderProgramExternalId = refferDetailsList.get(4);
				salesCenterVo.setValueByName("ordersource.programId", orderProgramExternalId);
			}
			if (!Utils.isBlank(refferDetailsList.get(5))) {
				orderOrderSourceExternalId = refferDetailsList.get(5);
				salesCenterVo.setValueByName("ordersource.ID", orderOrderSourceExternalId);
			}
			if (!Utils.isBlank(refferDetailsList.get(6))) {
				orderVdn = refferDetailsList.get(6);
			}
			if (!Utils.isBlank(refferDetailsList.get(8))) {
				salesCenterVo.setValueByName("ordersource.channel", refferDetailsList.get(8)); 
			}	
			if (!Utils.isBlank(refferDetailsList.get(9))) {
				salesCenterVo.setValueByName("referrer.program.name",refferDetailsList.get(9));
			}
			OrderType order = createOrder(request,response, agentId, referrerId ,xmlInput,primaryLanguage, referrerName,contextIndicator, orderProgramExternalId,
					orderOrderSourceExternalId, orderVdn);
			logger.info("TimeTakenforcreateOrder=" +(timer.getTime() - startTimer));
			if(isCustomerMatched){
				startTimer= timer.getTime();	
				RESTClient.INSTANCE.setCustomerLinkAndMatch(salesCenterVo.getValueByName("dtsequenceid"), DATAEXCHANGESETCUSTOMERLINKANDMATCHURL, String.valueOf(order.getCustomerInformation().getCustomer().getExternalId()));
				logger.info("TimeTakenforRestClientServicecall=" +(timer.getTime() - startTimer));
			}
			salesCenterVo.setValueByName("order.id", String.valueOf(order.getExternalId()));
			if(dtConsumer != null && !Utils.isBlank(dtConsumer.getDtEmail())){
				order.getCustomerInformation().getCustomer().setBestEmailContact(dtConsumer.getDtEmail());
				logger.debug("email="+dtConsumer.getDtEmail());
			}
			request.getSession().setAttribute("customerID", order.getCustomerInformation().getCustomer().getExternalId());
			logger.info("CustomerId="+order.getCustomerInformation().getCustomer().getExternalId()+" OrderId="+order.getExternalId());
			request.getSession().setAttribute("orderId", order.getExternalId());
			request.getSession().setAttribute("order", order);
			request.getSession().setAttribute("callStartTime", Calendar.getInstance().getTimeInMillis());
			request.getSession().setAttribute("addressId", order.getCustomerInformation().getCustomer().getAddressList().getCustomerAddress().get(0).getAddress().getExternalId());
			request.getSession().setAttribute("pauseAndResumeURL", PAUSEANDRESUMEURL);
			String isPAREnabled = ConfigRepo.getString("*.par_enable") != null ? ConfigRepo.getString("*.par_enable") : "true";
			logger.info("sys_config_par_enabled=" + (ConfigRepo.getString("*.par_enable") != null ? isPAREnabled : "null"));
			request.getSession().setAttribute("isPAREnabled", isPAREnabled);

			SalesDialogueVO dialogueVO = new SalesDialogueVO(); 

			dynamicFlow.put("dynamicFlow.page", "Greeting");
			Map<String, String> saleFlow =  new HashMap<String, String>();
			if(isCallingAddressMatched){
				saleFlow.put("salesFlow.contextId", "01");
			}
			else{
				saleFlow.put("salesFlow.contextId", contextIndicator);
			}
			contextMap.put("salesFlow", saleFlow);
			startTimer=timer.getTime();
			if((!(contextIndicator.equals("NA") || contextIndicator.equals("00") || contextIndicator.equals("05"))) || isCallingAddressMatched){
				request.getSession().setAttribute("RapidResponsecustomer", "yes");
			}

			if(!Utils.isBlank(refferDetailsList.get(12))) {
				salesCenterVo.setValueByName("referrer.businessParty.cobrandName", refferDetailsList.get(12));
			} else {
				if(!Utils.isBlank(coBrandName.toString())){
					salesCenterVo.setValueByName("referrer.businessParty.cobrandName", coBrandName.toString());//refArray[1]);
				} else {
					coBrandName.append("N/A" +" - "+refArray[1]);
					salesCenterVo.setValueByName("referrer.businessParty.cobrandName", coBrandName.toString());//refArray[1]);
				}
			}

			String agentGroup = (String) request.getSession().getAttribute("userGroup");
			String agentProductGroup = (String) request.getSession().getAttribute("userGroupProduct");
			String atgLink = ConfigRepo.getString("*.atg_link");
			String isATGUserGroupEnabled = ConfigRepo.getString("*.atg_usergroup_enable") != null ? ConfigRepo.getString("*.atg_usergroup_enable") : "false";
			List<String> atgShellPlanExcludeReferrersList  = Utils.isBlank(ConfigRepo.getString("*.atg_shell_plan_exclude_referrers")) ? null : 
				Arrays.asList(ConfigRepo.getString("*.atg_shell_plan_exclude_referrers").split("\\|"));
			List<String> atgUserGroupList  = Utils.isBlank(ConfigRepo.getString("*.atg_user_group")) ? null : Arrays.asList(ConfigRepo.getString("*.atg_user_group").split("\\|"));
			String drupalContentUrl = ConfigRepo.getString("*.drupal_dialogues_url");
			String digitalEnvUrl = ConfigRepo.getString("*.digital_env_url");
			String referrerFlowAgentGroup = (String) request.getSession().getAttribute("referrerFlowAgentGroup");
			if ((!Utils.isBlank(refferDetailsList.get(7)) && (refferDetailsList.get(7).contains(Constants.CHARTER) || refferDetailsList.get(7).contains(Constants.SPECTRUM))) 
					||(!Utils.isBlank(atgLink) && (atgShellPlanExcludeReferrersList ==null || !atgShellPlanExcludeReferrersList.contains(referrerId)) 
							&&((Utils.isBlank(isATGUserGroupEnabled)||(!Utils.isBlank(isATGUserGroupEnabled)&& isATGUserGroupEnabled.equalsIgnoreCase("false")))
									||(!Utils.isBlank(isATGUserGroupEnabled)&& isATGUserGroupEnabled.equalsIgnoreCase("true") && atgUserGroupList != null && (atgUserGroupList.contains(agentGroup)||atgUserGroupList.contains(agentProductGroup)))))) {
				String atgAffiliateId = ConfigRepo.getString("*.atg_affiliate_id");
				String atgPhoneNumber = ConfigRepo.getString("*.atg_phone_number");
				if(!Utils.isBlank(atgLink)){
					salesCenterVo.setValueByName("referrer.program.url", atgLink);
				}
				else if(!Utils.isBlank(refferDetailsList.get(7))){
					salesCenterVo.setValueByName("referrer.program.url", refferDetailsList.get(7));
				}
				salesCenterVo.setValueByName("atg.affiliate.id", atgAffiliateId);
				salesCenterVo.setValueByName("atg.phone.number", atgPhoneNumber);
			}
			if (!Utils.isBlank(refferDetailsList.get(7))) {
				if (!Utils.isBlank(digitalEnvUrl)) {
					salesCenterVo.setValueByName("referrer.program.url", MessageFormat.format(refferDetailsList.get(7), digitalEnvUrl));
				} else {
					salesCenterVo.setValueByName("referrer.program.url", MessageFormat.format(refferDetailsList.get(7), ""));
				}					
				salesCenterVo.setValueByName("agentExtId",(String) request.getSession().getAttribute("agentExtId"));
				//ordersource is expected to give http or https
				String hybrisUrl = salesCenterVo.getValueByName("referrer.program.url") + "?";
				String urlParams = "invalidateSession=true&";
				if(!Utils.isBlank(hybrisUrl)) {
					if (!Utils.isBlank(salesCenterVo.getValueByName("agent.id"))) {
						urlParams = urlParams + "agentIdentifier=" + salesCenterVo.getValueByName("agent.id") + "_" + salesCenterVo.getValueByName("agentExtId");
					}
					if (!Utils.isBlank(salesCenterVo.getValueByName("referrer.program.name"))) {
						if(!Utils.isBlank(urlParams)) {
							urlParams = urlParams + "&";
						}
						urlParams = urlParams + "programName=" + salesCenterVo.getValueByName("referrer.program.name").replaceAll(" ", "%");;
					}
					if (!Utils.isBlank(salesCenterVo.getValueByName("referrer.businessParty.referrerId"))) {
						if(!Utils.isBlank(urlParams)) {
							urlParams = urlParams + "&";
						}
						urlParams = urlParams + "referrerID=" + salesCenterVo.getValueByName("referrer.businessParty.referrerId");
					}
					if (!Utils.isBlank(salesCenterVo.getValueByName("order.id"))) {
						if(!Utils.isBlank(urlParams)) {
							urlParams = urlParams + "&";
						}
						urlParams = urlParams + "concertId=" + salesCenterVo.getValueByName("order.id");
					}
				}
				logger.info("hybrisUrl=" + hybrisUrl + urlParams);
				salesCenterVo.setValueByName("hybrisUrl", hybrisUrl + urlParams);

			}
			logger.info("sys_config cache_enabled=" + drupalContentUrl);
			if (!Utils.isBlank(drupalContentUrl) && Boolean.valueOf(String.valueOf(request.getSession().getAttribute("dispDrupalDailgVal")))){
				salesCenterVo.setValueByName("drupalContentUrl", drupalContentUrl);
				salesCenterVo.setValueByName("dispDrupalDailgVal", String.valueOf(request.getSession().getAttribute("dispDrupalDailgVal")));
			}
			if(Utils.isBlank(sameCall)){
				logger.info("dispDrupalDailgVal=" + Boolean.valueOf(String.valueOf(request.getSession().getAttribute("dispDrupalDailgVal"))));
				if (!Utils.isBlank(drupalContentUrl) && Boolean.valueOf(String.valueOf(request.getSession().getAttribute("dispDrupalDailgVal")))){
					String dialoguesFromDrupal = SalesUtil.getDialoguesFormDrupalContent(contextMap,salesCenterVo);
					if (Utils.isBlank(dialoguesFromDrupal)){
						getDialoguesFromService(dialogueVO,salesCenterVo,contextMap,context);
					}else{
						context.getFlowScope().put("referrerFlow", referrerFlowAgentGroup);	
						context.getFlowScope().put("dialogue" , dialoguesFromDrupal);
					}
				}
				else{
					getDialoguesFromService(dialogueVO,salesCenterVo,contextMap,context);
				}
				clearSameCallSessionInfo(request);
			}
			logger.info("TimeTakenforDialougeServiceCall="+(timer.getTime() - startTimer));

			contextMap.remove("salesFlow");

			logger.info("refArray[1]="+refArray[1]);
			logger.info("dialogueVO="+dialogueVO);
			logger.info("contextIndicator="+contextIndicator);

			logger.info("Prepared_dialogue_events");
			String agentIdVal = salesCenterVo.getValueByName("agent.id");
			startTimer=timer.getTime();
			Long salesSessionId = insertIntoSalesSessionAndSalesCall(request, agentIdVal);	
			insertIntoCustomerCallsCount(request, agentIdVal);
			logger.info("TimeTakenforInsertingtheSalesSessionAndSalesCall"+(timer.getTime()-startTimer));
			request.getSession().setAttribute("showObjectionBusters",true);
			request.getSession().setAttribute("showProviderHints",false);
			request.getSession().setAttribute("salesSessionId", salesSessionId);
			request.getSession().setAttribute("displaySaversButton",false);

			if (context != null) {
				context.getFlowScope().put("customerVoList" , customerVoList);
				context.getFlowScope().put("referalId" , referalId);
				context.getFlowScope().put("salesCenterVo" , salesCenterVo);
			} 

			String flow = (String) request.getSession().getAttribute("referrerFlowAgentGroup");
			if (!Utils.isBlank(flow)) {
				if (flow.toLowerCase().contains("mdu")) {
					WebLookupCollection webLookupStates = lookupDao.findUSStates();
					context.getFlowScope().put("states", webLookupStates.getListAsJSON());
					if (!Utils.isBlank(refferDetailsList.get(4))) {
						context.getFlowScope().put("mduProgramExternalId", refferDetailsList.get(4));
					}
					logger.debug("JSON_String_For_STATES_is"+webLookupStates.getListAsJSON());
					if(MDUCacheService.INSTANCE.get("mduProperties") == null){
						String mduUrl = ConfigRepo.getString("*.mdu_url");
						logger.info("mdu_url="+mduUrl);
						try {
							if(!Utils.isBlank(mduUrl)){
								SalesUtil.INSTANCE.getMduProperties(mduUrl);
							}
						} 
						catch (Exception e) {
							logger.error("Error_in_Loading_MDU_Properties",e);
						}
					}
				}
			}
			request.getSession().setAttribute("programId", salesCenterVo.getValueByName("ordersource.programId"));
			request.getSession().setAttribute("referrerIdValue", salesCenterVo.getValueByName("referrer.businessParty.referrerId"));
			logger.info("timeTakenForShowGreetingInfo="+timer.getTime());
			logger.info("Greeting_Webflow_Controller_Success");
			if(!Utils.isBlank(sameCall) && sameCall.equals("true")){
				return new Event(this, "basicInfoEvent");
			}
			else{				
				return new Event(this, "greetingViewEvent");
			}
		}catch(Exception e){
			logger.error("Exception_in_GreetingWebflowController",e);
			throw new UnRecoverableException(e.getMessage());
		}finally{
			timer.stop();
		}
	}

	private void insertIntoCustomerCallsCount(HttpServletRequest request, String agentIdVal) {
		try{
			Integer callNumberId = 0;
			Integer actualCallCount = 0;
			CustomerTrackerDetails customerTrackerDetails = null;
			String sameCall = (String) request.getSession().getAttribute("sameCall");
			
			if(request.getSession().getAttribute("customerTrackerDetails") != null){
				customerTrackerDetails = (CustomerTrackerDetails)request.getSession().getAttribute("customerTrackerDetails");
				callNumberId = customerTrackerDetails.getConcertCallCount();
				actualCallCount = customerTrackerDetails.getActualCallCount();
				if(!Utils.isBlank(sameCall) && sameCall.equals("true")){
					//For Same call callnumber is same
				}else{
					callNumberId++;
					actualCallCount++;
				}
				customerTrackerDetails.setConcertCallCount(callNumberId);
				customerTrackerDetails.setActualCallCount(actualCallCount);
				customerTrackerDao.updateCustomerTrackerDetails(customerTrackerDetails);
			}
			else{
				customerTrackerDetails = new CustomerTrackerDetails();
				if(!Utils.isBlank(sameCall) && sameCall.equals("true")){
					//For Same call callnumber is same
				}else{
					callNumberId++;
					actualCallCount++;
				}
				customerTrackerDetails.setAgentId(agentIdVal);
				customerTrackerDetails.setCreateDate(Calendar.getInstance());
				customerTrackerDetails.setUtilityPitchedCount(0);
				customerTrackerDetails.setUtilityPoints(0);
				customerTrackerDetails.setConcertCallCount(callNumberId);
				customerTrackerDetails.setActualCallCount(actualCallCount);
				
				customerTrackerDao.insertCustomerTrackerDetails(customerTrackerDetails);
			}
			request.getSession().setAttribute("isCustomerTrackerDetailsPopulated",true);
			request.getSession().setAttribute("customerTrackerDetails",customerTrackerDetails);
		}
		catch(Exception e){
			logger.warn("Exception_in_insertIntoCustomerCallsCount"+e.getMessage());
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
		session.setAttribute("GUID", null);
		session.setAttribute("fromQualificationMoveInDelta",null);
		session.setAttribute("moveInDeltaService", null);
		session.setAttribute("isMoveInDelta",  null);
		session.setAttribute("pesistFilterOptions",  null);
		session.setAttribute("customerLookupMapSession", null);
		session.setAttribute("activeAlert", null);
		session.setAttribute("coxProductDetailsData",null);
		session.setAttribute("wtDisabledProviderIDList", null);
		session.setAttribute("siftFileVersion", null);	
		session.setAttribute("newAttv6LineItemExtID", null);
		session.setAttribute("removedExistingProvidersMapAfterAuth", null);	
		session.setAttribute("selectedExistingProvidersAfterAuthentication",null);
		session.setAttribute("selectedExistingProviders",null);
		session.setAttribute("pivotAssistJson",null);
		session.setAttribute("inumVal",null);
		session.setAttribute("dominionProductExtIds",null);
		session.setAttribute("isDominionOffer",null);
		session.setAttribute("populatedCustomerTrackerOrderId", null);	
		session.setAttribute("closingOfferName",null);
		session.setAttribute("saversProductExtIds",null);
		session.setAttribute("utilityOfferName",null);
		session.setAttribute("closingOfferPoints",null);
		session.setAttribute("popUpMessage",null);
		session.setAttribute("showPopUp",null);
		session.setAttribute("rvSeqNumVal",null);
		session.setAttribute("providerId",null);
		session.setAttribute("MAMproductsUpdated",null);
		session.setAttribute("isMAMSuccess", null);
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
		if(session.getAttribute("newAMBMessageData")!=null)
		{
			session.removeAttribute("newAMBMessageData");
		}
		if( session.getAttribute("customer")!=null )
		{
			session.setAttribute("customer", null);
		}
		if(session.getAttribute("orderExternalId") !=  null)
		{
			session.removeAttribute("orderExternalId");
		}
		MDC.put("GUID", "");
		MDC.put("orderId","" );
		if(request.getSession().getAttribute("salescontext") != null){
			SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
			salesCenterVo.setValueByName("consumer.name.first", null);
			salesCenterVo.setValueByName("consumer.name.middle", null);
			salesCenterVo.setValueByName("consumer.name.last", null);
			salesCenterVo.setValueByName("customer.confirmation.number", null);
			salesCenterVo.setValueByName("referrer.businessParty.referrerName",null);
			salesCenterVo.setValueByName("referrer.businessParty.referrerId", null);
			salesCenterVo.setValueByName("referrer.businessParty.referrerPhoneNum", null);
			salesCenterVo.setValueByName("referrer.businessParty.url", null);
			salesCenterVo.setValueByName("referrer.program.name", null);
			salesCenterVo.setValueByName("referrer.dateDelta",null);
			salesCenterVo.setValueByName("DT Partner", null);
			salesCenterVo.setValueByName("referrer.businessParty.cobrandName", null);
			salesCenterVo.setValueByName("orderSource.program.name", null);
			salesCenterVo.setValueByName("Customer Name", null);
			salesCenterVo.setValueByName("drupalContentUrl", null);
			salesCenterVo.setValueByName("dispDrupalDailgVal", null);
			salesCenterVo.setValueByName("drupalDialogueCacheKey", null);
			salesCenterVo.setValueByName("usaa.id", null);
			salesCenterVo.setValueByName("referrer.program.url", null);
			salesCenterVo.setValueByName("agentExtId", null);
			salesCenterVo.setValueByName("simpleChoice.operatingCompanyCode", null);
			salesCenterVo.setValueByName("simpleChoice.currentRate", null);
			salesCenterVo.setValueByName("simpleChoice.rateCode", null);
			salesCenterVo.setValueByName("simpleChoice.eligibility", null);
			salesCenterVo.setValueByName("isZipOnlySearch", null);
			salesCenterVo.setValueByName("ordersource.programId", null);
			salesCenterVo.setValueByName("ordersource.ID", null);
			salesCenterVo.setValueByName("priorEnrollSurge", null);
			salesCenterVo.setValueByName("fallback.cableProviderId", null);
			salesCenterVo.setValueByName("fallback.cableProviderName", null);
			salesCenterVo.setValueByName("fallback.telcoProviderId", null);
			salesCenterVo.setValueByName("fallback.telcoProviderId", null);
			salesCenterVo.setValueByName("campaignId", null);
			salesCenterVo.setValueByName("customer.contractAccountNumber", null);
			salesCenterVo.setValueByName("firstenergy.operatingCompanyName", null);
			salesCenterVo.setValueByName("firstenergy.operatingCompanyCode", null);
			salesCenterVo.setValueByName("fuse.customerAddress", null);
			salesCenterVo.setValueByName("unit.type", null);
			salesCenterVo.setValueByName("unit.number", null);
			salesCenterVo.setValueByName("unitType", null);
			salesCenterVo.setValueByName("unitNum", null);
			if(salesCenterVo.getScMap().containsKey("comcast.order.number")){
				salesCenterVo.getScMap().remove("comcast.order.number");
			}
			if(salesCenterVo.getScMap().containsKey("cox.order.number")){
				salesCenterVo.getScMap().remove("cox.order.number");
			}
			if(salesCenterVo.getScMap().containsKey("hughesnet.order.number")){
				salesCenterVo.getScMap().remove("hughesnet.order.number");
			}
			if(salesCenterVo.getScMap().containsKey("flowTypeValue")){
				salesCenterVo.getScMap().remove("flowTypeValue");
			}
		}
		session.setAttribute("primaryLanguage",null);
		session.setAttribute("RapidResponsecustomer", "no");
		session.setAttribute("SALES_CONTEXT", null);
		session.setAttribute("addressId", null);
		session.setAttribute("address", null);
		session.setAttribute("lastLineItem", null);
		session.setAttribute("isWarmTransferEnabled", null);
		session.setAttribute("customerDiscoveryMap", null);
		session.setAttribute("landlord", null);
		session.setAttribute("discoveryNotepadlinkMap", null);
		session.setAttribute("notePadMap", null);
		session.setAttribute("mOfferTrackId", null);
		session.setAttribute("displaySaversButton", null);
		session.setAttribute("offersFromRecommendations", null);
		session.setAttribute("isFromRecommendation", null);
		session.setAttribute("pauseAndResumeUrl",  null);
		session.setAttribute("isPAREnabled",  null);
		session.setAttribute("showObjectionBusters",  null);
		session.setAttribute("showProviderHints",null);
		session.setAttribute("hintProviders",null);
		session.setAttribute("populatedVerizonSmartCartOrderId", null);
	}

	public void showGreetingInfo(HttpServletRequest request, VClient VClnt, HttpServletResponse response) throws Exception{
		showGreetingInfo(request, response, null);
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
			HttpServletResponse response, String agentId, String strReferrerId, String xmlInput, String primaryLanguage, String referrerName,String contextIndicator,
			String programExternalId, String orderSourceExternalId, String vdn) throws Exception{
		logger.info("Create_Order_Started");
		logger.info("primaryLanguageCreateOrder="+primaryLanguage);
		ObjectFactory oFactory = new ObjectFactory();
		StopWatch timer = new StopWatch();
		timer.start();
		long startTimer = 0;
		CustomerType customer_exist = createCustomer(request, response, agentId, strReferrerId ,xmlInput, primaryLanguage, referrerName);
		logger.info("TimeTakenforcreateCustomer=" +timer.getTime());
		OrderType order = oFactory.createOrderType();
		logger.info("Order_object_Intialized");
		logger.info("primaryLanguageCreateCustomer="+primaryLanguage);
		order.setExternalId(0L);
		//TODO: Should source be changed?
		order.setSource("salescenter");
		order.setReferrerId(strReferrerId);
		order.setAgentId(agentId);
		order.setProgramExternalId(programExternalId);
		order.setOrdersourceExternalId(orderSourceExternalId);
		order.setInboundVdn(vdn);
		Customer customer = oFactory.createCustomer();
		customer.setExternalId(customer_exist.getExternalId());
		logger.info("Added_Customer_to_Order");
		LineItemCollectionType lineItemsCollectionType = oFactory.createLineItemCollectionType();
		order.setLineItems(lineItemsCollectionType);
		logger.info("Order_creation_started");
		SalesContextType salesContext = getDefaultSalesContextType(oFactory,contextIndicator);		
		salesContext.setOrderSource(Integer.parseInt(strReferrerId));
		request.getSession().setAttribute("orderSalesContext", salesContext);
		CustomerInformation customerInformation = oFactory
				.createCustomerInformation();
		customerInformation.setCustomer(customer);
		customerInformation.setAction("referenceCustomer");
		order.setCustomerInformation(customerInformation);
		startTimer = timer.getTime();
		OrderType orderType = OrderService.INSTANCE.createOrder(agentId, salesContext, order);
		logger.info("TimeTakenforcreateOrder=" +(timer.getTime() - startTimer));
		logger.info("Order_creation_completed");
		timer.stop();
		return orderType;
	}

	private CustomerType createCustomer(HttpServletRequest request,
			HttpServletResponse response, String agentId, String strReferrerId , String xmlInput, String primaryLanguage, String referrerName) throws Exception{
		StopWatch timer = new StopWatch();
		timer.start();
		long startTimer = 0;
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
		//customer.setPartnerName(referrerName);
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
		startTimer = timer.getTime();
		CustomerType customerType = CustomerService.INSTANCE.createCustomer(customer, addressList, billingInfoList,customerContext);
		logger.info("TimeTakenforcreateCustomer=" +(timer.getTime() - startTimer));
		request.getSession().setAttribute("billingInfoExternalId",customerType.getBillingInfoList().getBillingInfo().get(0).getExternalId());
		request.getSession().setAttribute("customer",customerType);
		logger.info("Create_customer_completed");
		timer.stop();
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

	private String getRandomSixDigit() throws Exception{
		Random generator = new Random();
		generator.setSeed(System.currentTimeMillis());
		int random = generator.nextInt(99999) + 99999;
		if (random < 100000 || random > 999999) {
			random = generator.nextInt(99999) + 99999;
			if (random < 100000) {
				logger.info("Unable to generate random at this time..");
				random = random + 100000;
			} else if (random > 999999) {
				logger.info("Unable to generate random at this time..");
				random = 123456;
			}
		}
		return String.valueOf(random);
	}

	public Map<String,CustomerVO>  getCustomerDetails(String dtsequenceid ) throws Exception
	{
		StopWatch timer = new StopWatch();
		timer.start();
		long startTimer = 0;
		try{
			startTimer = timer.getTime();
			Map<String,CustomerVO> m = RESTClient.INSTANCE.getCustomersdetailsById(dtsequenceid, DATAEXCHANGEURL);
			logger.info("TimeTakenforgetCustomersdetailsById=" +(timer.getTime() - startTimer));
			return m;
		}catch(Exception e){
			logger.error("Unable_to_get_the_data_exchange_response_for_get_customer_details:"+e.getMessage());
			logger.error("Proceeding_to_Greetings_page_with_empty_customer_list:");
			return new HashMap<String, CustomerVO>();
		}finally{
			timer.stop();
		}
	}

	public List<Map<String,Object>> getMethod(String[] refArray) throws Exception{
		StopWatch timer = new StopWatch();
		timer.start();
		long startTimer = 0;
		logger.info("refArray[0]getMethod="+refArray[0]);
		Map<String,CustomerVO> m = new HashMap<String, CustomerVO>();
		try {
			startTimer = timer.getTime();
			m = RESTClient.INSTANCE.getCustomersbyReferrer(refArray[0], DATAEXCHANGEREFERRERURL);
			logger.info("TimeTakenforgetCustomersbyReferrer=" +(timer.getTime() - startTimer));
		} catch (Exception e) {
			logger.error("Unable_to_get_the_data_exchange_response:"+e.getMessage());
			logger.error("Proceeding_to_Greetings_page_with_empty_customer_list:");
			m = new HashMap<String, CustomerVO>();			
		}

		logger.info("refArray[0]DataExchangeGetMethod="+refArray[0]);
		logger.info("dataexchangeReferrerUrlDataExchangeGetMethod="+DATAEXCHANGEREFERRERURL);
		logger.info("CustomerVoMapGetMethod="+m);
		logger.info("refArray[0]getMethod="+refArray[0]);
		logger.info("refArray[1]getMethod="+refArray[1]);
		timer.stop();
		return getList(m,refArray);
	}


	public List<String>  getReferrerDetais(String refId,String vdnValue,StringBuilder coBrandName) throws Exception
	{
		StopWatch timer = new StopWatch();
		timer.start();
		long startTimer = 0;
		List<String> refferDetailsList = new ArrayList<String>();
		int detailsCacheTimeout = ConfigRepo.getInt("*.details_cache_time_out") == 0 ? 7200000 : ConfigRepo.getInt("*.details_cache_time_out");	
		startTimer = timer.getTime();
		DetailsRequestResponse drr = DetailService.INSTANCE.getAllOrderSources("12345", Long.valueOf(detailsCacheTimeout));  
		logger.info("TimeTakenforgetAllOrderSources=" +(timer.getTime() - startTimer));
		logger.info("vdnValueBeforeDetailsResponseCheck="+vdnValue);
		if(drr != null && !(drr.equals(""))){
			List<OrderSourceResultType> osrtList =  drr.getResponse().getOrderSourceResultElement();
			String phoneNumber = "";
			String primaryLanguage = "";
			String closeCallURL = "";
			String baseUrl = "";
			String programName = "";
			String dateDelta = "";
			String orderSourceProgramName = "";
			String emailCallbackNumber = "";
			String programCoBrandName = "";
			for(OrderSourceResultType orst: osrtList){
				ProgramType programType = orst.getOrderSource().getProgram();
				String referrerId = orst.getOrderSource().getBusinessParty().getExternalId();
				String programExternalId = programType.getExternalId();
				orderSourceProgramName = programType.getName();
				String ordersourceExternalId = orst.getOrderSource().getSourceCode();
				//String vdn = orst.getOrderSource().getTelephonyList().getTelephony().get(0).getVdn();
				baseUrl = programType.getBaseURL();
				String orderSourceChannel = orst.getOrderSource().getChannel();
				programName = orst.getOrderSource().getBusinessParty().getName();

				List<TelephonyType> telephonyList = orst.getOrderSource().getTelephonyList().getTelephony();
				for(TelephonyType telephonyType : telephonyList){
					if(Constants.SALES.equalsIgnoreCase(telephonyType.getCallType())){
						String vdn = telephonyType.getVdn();
						if(refId.equals(referrerId) && vdnValue.equals(vdn) ){
							logger.info("orderSourceTelephonyGetVdn="+vdn);
							if(orst.getOrderSource().getBusinessParty().getMetadataList() != null ){

								if(orst.getOrderSource().getBusinessParty().getMetadataList().getMetadata()!= null){
									for(MetaData metaData:orst.getOrderSource().getBusinessParty().getMetadataList().getMetadata()){
										if(metaData.getName().equalsIgnoreCase("cobrandname")){
											coBrandName.append( metaData.getValue());
										}else if(metaData.getName().equalsIgnoreCase("CloseCallURL") && !(Utils.isBlank(metaData.getValue()))){
											closeCallURL = metaData.getValue(); //retrieving CloseCallURL          
										}
									}
								}
								if(orst.getOrderSource().getProgram().getMetadataList().getMetadata()!= null){
									for(MetaData metaData:orst.getOrderSource().getProgram().getMetadataList().getMetadata()){
										if("DateDelta".equalsIgnoreCase(metaData.getName())){
											dateDelta =  metaData.getValue();
										}
									}
								}
							}

							String imageUrl= orst.getOrderSource().getImageUrl(); //retrieving imageurl                

							phoneNumber = telephonyType.getPhoneNumber();//retrieving phonenumber

							primaryLanguage =telephonyType.getLanguage();	// retrieving PrimaryLanguage	
							logger.info("primaryLanguage="+primaryLanguage);
							if(primaryLanguage.equalsIgnoreCase("English")){
								primaryLanguage ="0";					
							}
							else{
								primaryLanguage= "1";
							}


							if(programType != null) {
								if(!Utils.isBlank(programType.getCallbackNumber())) {
									phoneNumber = programType.getCallbackNumber();
								}
								if(!Utils.isBlank(programType.getCoBrandName())) {
									programCoBrandName = programType.getCoBrandName();
								}
								if(!Utils.isBlank(programType.getEmailCallbackNumber())) {
									emailCallbackNumber = programType.getEmailCallbackNumber();
								}
							}

							refferDetailsList.add(imageUrl);
							refferDetailsList.add(phoneNumber);
							//logger.info("primaryLanguage in refdetList is::::::"+primaryLanguage);
							refferDetailsList.add(primaryLanguage);
							//logger.info("closecallURL="+closeCallURL);
							refferDetailsList.add(closeCallURL);
							refferDetailsList.add(programExternalId);
							refferDetailsList.add(ordersourceExternalId);
							refferDetailsList.add(vdn);
							refferDetailsList.add(baseUrl);
							refferDetailsList.add(orderSourceChannel);
							refferDetailsList.add(programName);
							refferDetailsList.add(dateDelta);
							refferDetailsList.add(emailCallbackNumber);
							refferDetailsList.add(programCoBrandName);
							refferDetailsList.add(orderSourceProgramName);
							logger.info("refferDetailsList="+refferDetailsList);
						}
					}
				}
			}
		}
		timer.stop();
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
		boolean isAMBMessageDataReceived = false; 
		if(request.getSession().getAttribute("isAMBMessageDataReceived") != null){
			isAMBMessageDataReceived = (Boolean) request.getSession().getAttribute("isAMBMessageDataReceived");
		}
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
		//SalesSession ss = salesSessionDao.getSalesSession((Long)request.getSession().getAttribute("orderId"), (Long)request.getSession().getAttribute("customerID"));
		Long salesSessionId = salesSession.getSalesSessionId();
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
			JSONObject ambFeedbackData = new JSONObject();
			if(! isSalesCallPopulated || isAMBMessageDataReceived){
				SalesCall salesCall = new SalesCall();
				if(request.getSession().getAttribute("salescontextAuthAMB") != null || request.getSession().getAttribute("salescontextAuthAMBMessage") != null){
					feedback = (JSONObject) request.getSession().getAttribute("salescontextAuthAMB");
					if(request.getSession().getAttribute("salescontextAuthAMBMessage") != null){
						ambFeedbackData = (JSONObject) request.getSession().getAttribute("salescontextAuthAMBMessage");
					}
					String agentPhoneId = null;
					String calledAddress = null;
					String callingAddress = null;
					String uec = null;
					String ucid = null;
					if(feedback.getString("ucid") != null && ambFeedbackData.getString("ucid")!= null){
						if(feedback.getString("ucid") != null && feedback.getString("ucid").equalsIgnoreCase(ambFeedbackData.getString("ucid"))){
							agentPhoneId = feedback.getString("agentPhoneId");
							calledAddress = feedback.getString("calledAddress");
							callingAddress = feedback.getString("callingAddress");
							uec = feedback.getString("userEnteredCode");
							ucid = feedback.getString("ucid");
						}
						else{
							agentPhoneId = ambFeedbackData.getString("agentPhoneId");
							calledAddress = ambFeedbackData.getString("calledAddress");
							callingAddress = ambFeedbackData.getString("callingAddress");
							uec = ambFeedbackData.getString("userEnteredCode");
							ucid = ambFeedbackData.getString("ucid");
							logger.info("Saved_ucid="+ucid);
						}
					}

					salesCall.setCalledAddress(calledAddress);
					salesCall.setCallingAddress(callingAddress);
					SimpleDateFormat sdf = new SimpleDateFormat("mm/DD/yyyy, hh:mm:ss a");
					logger.info("before_initialize_ambConnectDateTime="+ambConnectDateTime);					
					if(!Utils.isBlank(ambConnectDateTime)){
						ambConnectDateTime = getDateBasedOnString(ambConnectDateTime);
						logger.info("after_initialize_ambConnectDateTime="+ambConnectDateTime);
						Calendar connectCal = Calendar.getInstance();
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
				else{
					salesCall.setDisconnectTime(Calendar.getInstance());
					salesCall.setEstablishedTime(Calendar.getInstance()); 
				}
				if(!Utils.isBlank(agentId)){
					salesCall.setAgent(agentId);
				}
				salesCall.setAlertingTime(Calendar.getInstance());
				salesCall.setSalesSessionId(salesSessionId);
				salesCallDao.put(salesCall);
				salesCallId = salesCall.getSalesCallId();
				logger.info("salesCallId="+salesCallId);
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

	private void getDialoguesFromService(SalesDialogueVO dialogueVO, SalesCenterVO salesCenterVo, 
			Map<String, Map<String, String>> contextMap, RequestContext context) throws UnRecoverableException {
		try {
			dialogueVO = DialogServiceUI.INSTANCE.getDialoguesByContext(contextMap);
			StringBuilder events = new StringBuilder();
			List<Fieldset> fieldsetList = new ArrayList<Fieldset>();
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
			context.getFlowScope().put("dialogue" , SalesUtil.INSTANCE.parseHtmlTags(events.toString()));
			logger.info("dialogue"+SalesUtil.INSTANCE.parseHtmlTags(events.toString()));
		} catch (BaseException e) {
			logger.error("Exception_in_GreetingWebflowController_getDialogues",e);
			throw new UnRecoverableException(e.getMessage());
		}
	}

	private  String getDateBasedOnString(String strDate)
	{
		try{
			Date date = new Date(strDate) {};
			SimpleDateFormat sdf = new SimpleDateFormat("mm/DD/yyyy, hh:mm:ss a");
			String ambConnectDateTime = sdf.format(date);
			logger.info("ambConnectDateTime_At_getDateBasedOnString="+ambConnectDateTime);
			return ambConnectDateTime;
		} catch (Exception e) {
			logger.error("while_we_getDate_based_On_StringDate"+e);
		}
		return null;
	}

	private void setBusinessPartyDetailsToVO(String refId, String vdnValue, SalesCenterVO salesCenterVo) {

		StopWatch timer = new StopWatch();
		timer.start();
		long startTimer = 0;
		int detailsCacheTimeout = ConfigRepo.getInt("*.details_cache_time_out") == 0 ? 7200000 : ConfigRepo.getInt("*.details_cache_time_out");	
		startTimer = timer.getTime();
		DetailsRequestResponse drr = DetailService.INSTANCE.getAllOrderSources("12345", Long.valueOf(detailsCacheTimeout));  
		logger.info("TimeTakenforgetAllOrderSources=" +(timer.getTime() - startTimer));
		if(drr != null && !(drr.equals(""))){
			String callBackNumber = "";
			List<OrderSourceResultType> osrtList =  drr.getResponse().getOrderSourceResultElement();
			for(OrderSourceResultType orst: osrtList){
				ProgramType programType = orst.getOrderSource().getProgram();
				callBackNumber = programType.getCallbackNumber();
				salesCenterVo.setValueByName("businessParty.callbackNumber", callBackNumber);
				String referrerId = orst.getOrderSource().getBusinessParty().getExternalId();
				List<TelephonyType> telephonyList = orst.getOrderSource().getTelephonyList().getTelephony();
				for(TelephonyType telephonyType : telephonyList){
					if(Constants.SALES.equalsIgnoreCase(telephonyType.getCallType())){
						if(refId.equals(referrerId) && vdnValue.equals(telephonyType.getVdn()) ){
							if(orst.getOrderSource().getBusinessParty().getMetadataList() != null ){
								if(orst.getOrderSource().getBusinessParty().getMetadataList().getMetadata()!= null){
									for(MetaData metaData:orst.getOrderSource().getBusinessParty().getMetadataList().getMetadata()){
										if(metaData.getName().equalsIgnoreCase("cobrandname")){
											salesCenterVo.setValueByName("businessParty.name", metaData.getValue());
										}else if(metaData.getName().equalsIgnoreCase("CloseCallURL") && !(Utils.isBlank(metaData.getValue()))){
											salesCenterVo.setValueByName("businessParty.address", metaData.getValue());          
										}
									}
								}
							}

							String imageUrl= orst.getOrderSource().getImageUrl(); //retrieving imageurl                

							salesCenterVo.setValueByName("businessParty.salesNumber", telephonyType.getPhoneNumber());    
						}
					}
				}
			}
		}
	}

	public void clearSameCallSessionInfo(HttpServletRequest request) {
		logger.info("clearing_SameCall_SessionInfo");
		HttpSession session = request.getSession();
		SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
		session.setAttribute("firstName", null);
		session.setAttribute("lastName", null);
		session.setAttribute("middleName", null);
		session.setAttribute("prefix", null);
		session.setAttribute("suffix", null);
		session.setAttribute("address1", null);
		session.setAttribute("city", null);
		session.setAttribute("state", null);
		session.setAttribute("zip", null);
		session.setAttribute("unitType", null);
		session.setAttribute("unitNumber", null);
		session.setAttribute("rentOwnVal", null);
		session.setAttribute("dwellingType", null);
		session.setAttribute("bestEmailContactValue", null);
		session.setAttribute("bestPhoneContactValue", null);
		session.setAttribute("moveInDateValue", null);
		session.setAttribute("isMoveInValue", null);
		session.setAttribute("customerAddress", null);
		session.setAttribute("vdn", salesCenterVo.getValueByName("vdn"));
		session.setAttribute("referrerName", salesCenterVo.getValueByName("referrer.businessParty.referrerName"));
		logger.info("cleared_SameCall_SessionInfo");
	}

	private CustomerVO getConsumersInteractionsCustomerVO(String callingAddress, String refId, List<Map<String,Object>> customerVoList, SalesCenterVO salesCenterVo, long startTimer, StopWatch timer, HttpServletRequest request) {
		startTimer=timer.getTime();
		CustomerVO customerVO = null;
		String callingAddressVal = null;
		try {
			if(callingAddress.length()>10){
				callingAddressVal = callingAddress.substring(1);
			}else {
				callingAddressVal = callingAddress;
			}
			String partnerSpecificDataName = null;
			List<String> refList = Utils.isBlank(ConfigRepo.getString("*.partnerSpecificDataMatchReferrers")) ? null : Arrays.asList(ConfigRepo.getString("*.partnerSpecificDataMatchReferrers").split("\\|"));
			logger.info("partnerSpecificDataMatchReferrers="+refList);
			if(refList != null)
			{
				for (String refWithDataName : refList) 
				{
					String refValues[] = refWithDataName.split("=");
					if(refValues[0].trim().equals(refId))
					{
						partnerSpecificDataName = refValues[1];
						break;
					}
				}
			}
			Map<String,CustomerVO> customerMap = RESTClient.INSTANCE.getDataExchangeByCallingAddress(partnerSpecificDataName, callingAddressVal, DATAEXCHANGEBYCALLINGADDRESS, request);
			logger.info("TimeTakenforRestClientServicecall=" +(timer.getTime() - startTimer));
			logger.info("customerMap *************************"+customerMap);
			customerVO = customerMap.get(callingAddressVal);
			logger.debug("customerVO *************************"+customerVO);
			if(customerVO!=null){
				customerVoList.add(getMap(customerMap.get(callingAddressVal),customerVO.getDtSequenceNum()));
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
				salesCenterVo.setValueByName("dtsequenceid", customerVO.getDtSequenceNum());
				salesCenterVo.setValueByName("callingAddress", null);
			}
		} catch (Exception e) {
			logger.error("Error_In_getCustomerVO"+e.getMessage());
		}
		return customerVO;
	}

	@SuppressWarnings("unchecked")
	public Event showHomeServeGreetingInfo(HttpServletRequest request,
			HttpServletResponse response, RequestContext context) throws UnRecoverableException ,ServletException, IOException{
		StopWatch timer = new StopWatch();
		timer.start();
		long startTimer = 0;
		try{
			logger.info("showHomeServeGreetingInfoBegin");

			clearPreviousSessionInfo( request);
			List<Map<String,Object>> customerVoList = new ArrayList<Map<String,Object>>();
			String primaryLanguage ="";
			String referalId = (String) request.getSession().getAttribute("referralDet");
			String sameCall = (String) request.getSession().getAttribute("sameCall");
			logger.info("referalId="+referalId);
			String [] refArray  = null;
			SalesCenterVO salesCenterVo = null;
			StringReader sr = null;
			String referrerId = null;
			ConsumerVO dtConsumer = null;
			String vdn =null;
			boolean isCallingAddressMatched = false;
			StringBuilder coBrandName = new StringBuilder("");
			List<String> refferDetailsList = new ArrayList<String>();
			// this is for savers offer button in recomandation page. 
			if (request.getSession().getAttribute("gotoofferpage") != null ){
				request.getSession().removeAttribute("gotoofferpage");
			}
			String providersImageLocation = ConfigRepo.getString("*.provider_logo_location") == null ? null : ConfigRepo.getString("*.provider_logo_location");
			logger.info("salescenterProvidersImageLocation : "+providersImageLocation);
			if(!Utils.isBlank(providersImageLocation)){
				request.getSession().setAttribute("providersImageLocation", providersImageLocation);
			}
			boolean isCustomerMatched = false;
			Map<String, Map<String, String>> contextMap = (Map<String, Map<String, String>>)request.getSession().getAttribute("dynamicFlowContextMap");
			Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");

			salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
			refArray  = referalId.split("\\|");
			logger.info("refArray[0]_customerdetailsmap_not_equal_null_is="+refArray[0]);
			referrerId = refArray[0];
			logger.info("referal id ::"+referalId);

			String vdnamb = salesCenterVo.getValueByName("vdn");
			logger.info("vdnamb="+vdnamb);
			String vdnuecamb = salesCenterVo.getValueByName("vdnuec");
			logger.info("vdnuecamb="+vdnuecamb);

			String dtsequenceid= salesCenterVo.getValueByName("dtsequenceid");
			logger.info("dtsequenceidGreetingController="+dtsequenceid);


			vdn =refArray[2];
			salesCenterVo.setValueByName("vdn",vdn);
			logger.info("vdn="+vdn);


			refferDetailsList = getReferrerDetais(referrerId,vdn,coBrandName);
			setBusinessPartyDetailsToVO(referrerId,vdn,salesCenterVo);
			dtConsumer = new ConsumerVO();
			dtConsumer.setDtPartner(refArray[1]);
			request.getSession().setAttribute("salescontextDt", dtConsumer);
			salesCenterVo.setValueByName("referrer.businessParty.referrerName", refArray[1]);
			salesCenterVo.setValueByName("referrer.businessParty.referrerId", referrerId);
			salesCenterVo.setValueByName("referrer.businessParty.referrerPhoneNum", refferDetailsList.get(1));
			salesCenterVo.setValueByName("referrer.businessParty.callbackNumber",refferDetailsList.get(1));
			salesCenterVo.setValueByName("referrer.businessParty.url",refferDetailsList.get(3));
			salesCenterVo.setValueByName("referrer.dateDelta",refferDetailsList.get(10));
			salesCenterVo.setValueByName("referrer.program.emailCallback",refferDetailsList.get(11));
			salesCenterVo.setValueByName("orderSource.program.name",refferDetailsList.get(13));

			salesCenterVo.setValueByName("DT Partner", refArray[0]);
			logger.info("refarray[0]="+refArray[0]);
			logger.info("refarray[1]="+refArray[1]);

			primaryLanguage = refferDetailsList.get(2);// adding primarylanguage for customer
			logger.info("primaryLanguage="+primaryLanguage);
			String contextIndicator = salesCenterVo.getValueByName("salesFlow.contextId");
			//if contextIndicator is null or empty then we need to get regular dialogues.It should be NA.
			if(Utils.isBlank(contextIndicator)){
				contextIndicator="NA";
			}

			String agentId = salesCenterVo.getValueByName("agent.id");
			String agentName = salesCenterVo.getValueByName("agent.name.first");
			salesCenterVo.setValueByName("agent.name", agentName);
			String referrerName = refArray[1];
			startTimer= timer.getTime();
			String orderProgramExternalId = "";
			String orderOrderSourceExternalId = "";
			String orderVdn = "";
			if (!Utils.isBlank(refferDetailsList.get(4))) {
				orderProgramExternalId = refferDetailsList.get(4);
				salesCenterVo.setValueByName("ordersource.programId", orderProgramExternalId);
			}
			if (!Utils.isBlank(refferDetailsList.get(5))) {
				orderOrderSourceExternalId = refferDetailsList.get(5);
				salesCenterVo.setValueByName("ordersource.ID", orderOrderSourceExternalId);
			}
			if (!Utils.isBlank(refferDetailsList.get(6))) {
				orderVdn = refferDetailsList.get(6);
			}
			if (!Utils.isBlank(refferDetailsList.get(8))) {
				salesCenterVo.setValueByName("ordersource.channel", refferDetailsList.get(8)); 
			}	
			if (!Utils.isBlank(refferDetailsList.get(9))) {
				salesCenterVo.setValueByName("referrer.program.name",refferDetailsList.get(9));
			}

			SalesDialogueVO dialogueVO = new SalesDialogueVO(); 

			dynamicFlow.put("dynamicFlow.page", "Greeting");
			Map<String, String> saleFlow =  new HashMap<String, String>();
			if(isCallingAddressMatched){
				saleFlow.put("salesFlow.contextId", "01");
			}
			else{
				saleFlow.put("salesFlow.contextId", contextIndicator);
			}
			contextMap.put("salesFlow", saleFlow);
			startTimer=timer.getTime();
			if((!(contextIndicator.equals("NA") || contextIndicator.equals("00") || contextIndicator.equals("05"))) || isCallingAddressMatched){
				request.getSession().setAttribute("RapidResponsecustomer", "yes");
			}

			if(!Utils.isBlank(refferDetailsList.get(12))) {
				salesCenterVo.setValueByName("referrer.businessParty.cobrandName", refferDetailsList.get(12));
			} else {
				if(!Utils.isBlank(coBrandName.toString())){
					salesCenterVo.setValueByName("referrer.businessParty.cobrandName", coBrandName.toString());//refArray[1]);
				} else {
					coBrandName.append("N/A" +" - "+refArray[1]);
					salesCenterVo.setValueByName("referrer.businessParty.cobrandName", coBrandName.toString());//refArray[1]);
				}
			}


			String drupalContentUrl = ConfigRepo.getString("*.drupal_dialogues_url");
			String referrerFlowAgentGroup = (String) request.getSession().getAttribute("referrerFlowAgentGroup");
			logger.info("sys_config cache_enabled=" + drupalContentUrl);
			if (!Utils.isBlank(drupalContentUrl) && Boolean.valueOf(String.valueOf(request.getSession().getAttribute("dispDrupalDailgVal")))){
				salesCenterVo.setValueByName("drupalContentUrl", drupalContentUrl);
				salesCenterVo.setValueByName("dispDrupalDailgVal", String.valueOf(request.getSession().getAttribute("dispDrupalDailgVal")));
			}
			logger.info("dispDrupalDailgVal=" + Boolean.valueOf(String.valueOf(request.getSession().getAttribute("dispDrupalDailgVal"))));
			if (!Utils.isBlank(drupalContentUrl) && Boolean.valueOf(String.valueOf(request.getSession().getAttribute("dispDrupalDailgVal")))){
				String dialoguesFromDrupal = SalesUtil.getDrupalDialoguesForHomeServe(salesCenterVo);
				if (Utils.isBlank(dialoguesFromDrupal)){
					getDialoguesFromService(dialogueVO,salesCenterVo,contextMap,context);
				}else{
					context.getFlowScope().put("referrerFlow", referrerFlowAgentGroup);	
					context.getFlowScope().put("dialogue" , dialoguesFromDrupal);
				}
			}
			else{
				getDialoguesFromService(dialogueVO,salesCenterVo,contextMap,context);
			}
			clearSameCallSessionInfo(request);
			logger.info("TimeTakenforDialougeServiceCall="+(timer.getTime() - startTimer));

			contextMap.remove("salesFlow");

			logger.info("refArray[1]="+refArray[1]);
			logger.info("dialogueVO="+dialogueVO);
			logger.info("contextIndicator="+contextIndicator);

			logger.info("Prepared_dialogue_events");
			String agentIdVal = salesCenterVo.getValueByName("agent.id");
			startTimer=timer.getTime();
			insertIntoCustomerCallsCount(request, agentIdVal);
			logger.info("TimeTakenforinsertIntoCustomerCallsCount"+(timer.getTime()-startTimer));
			request.getSession().setAttribute("showObjectionBusters",true);
			request.getSession().setAttribute("showProviderHints",false);
			request.getSession().setAttribute("displaySaversButton",false);

			if (context != null) {
				context.getFlowScope().put("customerVoList" , customerVoList);
				context.getFlowScope().put("referalId" , referalId);
				context.getFlowScope().put("salesCenterVo" , salesCenterVo);
			} 

			request.getSession().setAttribute("programId", salesCenterVo.getValueByName("ordersource.programId"));
			request.getSession().setAttribute("referrerIdValue", salesCenterVo.getValueByName("referrer.businessParty.referrerId"));
			request.getSession().setAttribute("callStartTime", Calendar.getInstance().getTimeInMillis());
			logger.info("timeTakenForShowGreetingInfo="+timer.getTime());
			logger.info("Greeting_Webflow_Controller_Success");
			if(!Utils.isBlank(sameCall) && sameCall.equals("true")){
				return new Event(this, "basicInfoEvent");
			}
			else{				
				return new Event(this, "greetingViewEvent");
			}
		}catch(Exception e){
			logger.error("Exception_in_GreetingWebflowController",e);
			throw new UnRecoverableException(e.getMessage());
		}finally{
			timer.stop();
		}
	}
}
