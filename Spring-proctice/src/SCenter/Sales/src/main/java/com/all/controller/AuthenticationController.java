package com.AL.controller;

import static com.AL.ui.util.ConfigProperties.SALESCENTER_IDLEPAGE_IMAGE_COUNT;
import static com.AL.ui.util.ConfigProperties.SALESCENTER_IDLEPAGE_IMAGE_DELAY;
import static com.AL.ui.util.ConfigProperties.SALESCENTER_IDLEPAGE_IMAGE_LOCATION;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.AL.mail.GenericEmailHelper;
import com.AL.ui.constants.Constants;
import com.AL.ui.dao.ConfigDao;
import com.AL.ui.dao.CustomerTrackerDao;
import com.AL.ui.dao.ProviderDao;
import com.AL.ui.dao.ProviderTransferAgentGroupDao;
import com.AL.ui.domain.CustomerTracker;
import com.AL.ui.domain.CustomerTrackerConversion;
import com.AL.ui.domain.CustomerTrackerDetails;
import com.AL.ui.domain.Provider;
import com.AL.ui.factory.CartLineItemFactory;
import com.AL.ui.factory.SalesUtilsFactory;
import com.AL.ui.repository.SessionCache;
import com.AL.ui.service.config.ConfigRepo;
import com.AL.ui.service.V.DetailService;
import com.AL.ui.service.V.UserAccessService;
import com.AL.ui.service.V.impl.ThoughtspotCacheService;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.AgentVO;
import com.AL.ui.vo.ReferrerVO;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.V.domain.User;
import com.AL.V.exception.UnRecoverableException;
import com.AL.V.service.auth.AuthenticationService;
import com.AL.vo.AuthorizationMap;
import com.AL.vo.UserAuthorization;
import com.AL.vo.UserGroup;
import com.AL.xml.dtl.v4.DetailsRequestResponse;
import com.AL.xml.dtl.v4.OrderSourceBusinessPartyType;
import com.AL.xml.dtl.v4.OrderSourceResultType;
import com.AL.xml.dtl.v4.TelephonyType;
import com.AL.xml.v4.AttributeEntityType;
import com.AL.xml.v4.LineItemType;
import com.google.gson.Gson;

/**
 * @author kamesh
 *
 */

@Controller
public class AuthenticationController extends BaseController {

	@Autowired
	private ProviderDao providerDao;
	
	@Autowired
	private CustomerTrackerDao customerTrackerDao;
	
	@Value("${buildVersion}")
	private String buildVersion;
	
	@Autowired
	private ProviderTransferAgentGroupDao providerTransferAgentGroupDao;

	private static final Logger logger = Logger.getLogger(AuthenticationController.class);

	/*	
	 * Login Functionality		 
	 */
	@RequestMapping(value = "/login")
	public ModelAndView login(HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");		
		SessionCache.INSTANCE.clear(request.getSession());
		String buildNumber = "";
		if (!Utils.isBlank(buildVersion)) {
			buildNumber = "v(" + buildVersion + ")";
		}
		request.getSession().setAttribute("buildVersion", buildNumber);
		return mav;
	}
	
	/*	
	 * Logout Functionality		 
	 */	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");
		try{
			String agentId = (String) request.getSession().getAttribute("username");
			CustomerTrackerDetails customerTrackerDetails= customerTrackerDao.getCustomerTrackerDetailsByAgentId(agentId);
			if(request.getSession().getAttribute("updatedActualPointsMap") != null){
				Map<String,Float> updatedActualPointsMap = (Map<String,Float>)request.getSession().getAttribute("updatedActualPointsMap");
				if(updatedActualPointsMap != null && !updatedActualPointsMap.isEmpty()){
					customerTrackerDao.updateCustomerTracker(updatedActualPointsMap) ;
				}
			}
			if(customerTrackerDetails == null && request.getSession().getAttribute("customerTrackerDetails") != null){
				customerTrackerDetails = (CustomerTrackerDetails)request.getSession().getAttribute("customerTrackerDetails");
				customerTrackerDao.insertCustomerTrackerDetails(customerTrackerDetails);
			}
			else if(request.getSession().getAttribute("customerTrackerDetails") != null){
				 customerTrackerDetails = (CustomerTrackerDetails)request.getSession().getAttribute("customerTrackerDetails");
				if(customerTrackerDetails != null){
					customerTrackerDao.updateCustomerTrackerDetails(customerTrackerDetails);
				}
			}
			
		}catch(Exception e){
			logger.info("error occured while inserting the data "+e.getMessage());
		}
		SessionCache.INSTANCE.clear(request.getSession());
		return mav;
	}
	
	
	@RequestMapping(value = "/checkSession")
	public @ResponseBody boolean checkSession(HttpServletRequest request,HttpServletResponse response)throws Exception{
		logger.info("in_checkSession_method");
		logger.info("checkSession_in_AuthenticationController");
		request.getSession().setAttribute("idlePageStartTime", Calendar.getInstance());
		return true;
	}
	
	@RequestMapping(value = "/session_timeout")
	public ModelAndView sessionTimeOut(HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("session_TimeOut_in_AuthenticationController");
		request.setAttribute("isSessionTimeOut", true);
		request.setAttribute("isSessionTimeOutText", "Session Session Time Out");
		request.getSession().setAttribute("sameCall", null);
		//String absoluteURL = (String) request.getSession().getAttribute(
		//		"urlPath");
		request.getSession().setAttribute("idlePageStartTime", Calendar.getInstance());
		//ModelAndView mav = new ModelAndView("redirect:" + absoluteURL
		//		+ "/salescenter/login");
		//mav.setViewName("login");
		//request.getRequestDispatcher(request.getContextPath()+"/login").forward(request, response);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");
		logger.info("redirecting_to_login_page");
		return mav;
	}
	

	@RequestMapping(value = "/login_process", method = RequestMethod.GET)
	public ModelAndView login_process(HttpServletRequest request) throws Exception{
		SalesUtilsFactory.INSTANCE.clearPreviousSessionInfo(request);
		logger.info("login_process_get_method_begin_in_AuthenticationController");
		request.getSession().setAttribute("idlePageStartTime", Calendar.getInstance());
		String customerTracker= ConfigRepo.getString("*.display_customer_tracker");
		request.getSession().setAttribute("customerTracker", Boolean.valueOf(customerTracker));
		String displayRoadmap= ConfigRepo.getString("*.display_roadmap");
		request.getSession().setAttribute("displayRoadmap", Boolean.valueOf(displayRoadmap));
		logger.info("show customer tracker "+customerTracker);
		Integer customerCallNumberId = null;
		Integer utilityPitchedCount = null;
		String agentId = (String) request.getSession().getAttribute("username");
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		Calendar calendar = Calendar.getInstance();;
		
		request.getSession().setAttribute("trackerUpdateTime", sdf.format(calendar.getTime()));
		 
		try{
			getThoughtSpotDataFromCache(request);
			
			if(request.getSession().getAttribute("customerTrackerDetails") == null){
				CustomerTrackerDetails customerTrackerDetails= customerTrackerDao.getCustomerTrackerDetailsByAgentId(agentId);
				request.getSession().setAttribute("customerTrackerDetails",customerTrackerDetails);
			}
			List<CustomerTracker> tsLineItemTrackerDataList= (List<CustomerTracker>)request.getSession().getAttribute("tsLineItemTrackerDataList");
			request.getSession().setAttribute("currentOrderId", null);
			if( tsLineItemTrackerDataList == null || tsLineItemTrackerDataList.isEmpty()){
				tsLineItemTrackerDataList = customerTrackerDao.getCustomerTrackerDataByAgentId(agentId);
				tsLineItemTrackerDataList = Utils.sortCustomers(tsLineItemTrackerDataList);
				Utils.setAgentTracker(tsLineItemTrackerDataList, request.getSession());
				request.getSession().setAttribute("tsLineItemTrackerDataList",tsLineItemTrackerDataList);
			}
			else{
				logger.info("customerTrackerList size="+tsLineItemTrackerDataList.size());
				Utils.setAgentTracker(tsLineItemTrackerDataList, request.getSession());
			}
			logger.info("customerCallNumberId="+customerCallNumberId);
			
		}catch(Exception e){
			logger.info("error_in_login_process"+e.getMessage());
		}
		
		if(request.getSession().getAttribute("mavFromSession") == null){
			String absoluteURL = (String) request.getSession().getAttribute("urlPath");
			
			ModelAndView mav = new ModelAndView("redirect:" + absoluteURL+ "/salescenter/login");
			mav.setViewName("login");
			return mav;
		}
		ModelAndView mav = (ModelAndView)request.getSession().getAttribute("mavFromSession");
		mav.addObject("sameCall", true);
		if (ConfigRepo.getString("*.enable_samecall_restriction") != null && ConfigRepo.getString("*.enable_samecall_restriction").equalsIgnoreCase("true")){
			mav.addObject("enableSamecallRestriction", true);
		}
		logger.info("before_addPopFlowObjects_in_login_process_get_method");
		addPopFlowObjects(request, mav);
		logger.info("login_process_get_method_end_in_AuthenticationController");
		return mav;
	}
	
	
	/**
	 * Authenticating the user	   
	 * 
	 * @param request
	 * @param userid
	 * @param password
	 * @return mav
	 */

	@RequestMapping(value = "/login_process", method = RequestMethod.POST)

	public ModelAndView loginProcess(HttpServletRequest request,
			@RequestParam("chatForm:j_username") String userid,
			@RequestParam("chatForm:j_password") String password) throws UnRecoverableException{
		StopWatch timer=new StopWatch();
		long startTimer = 0;
		timer.start();
		try{
		ModelAndView mav = new ModelAndView();
		logger.info("login_process_begin_in_AuthenticationController");
		SalesUtilsFactory.INSTANCE.clearPreviousSessionInfo(request);
		int callAlertTimeout  = ConfigRepo.getInt("*.call_alert_timeout") == 0 ? 5000 : ConfigRepo.getInt("*.call_alert_timeout");
		request.getSession().setAttribute("callAlertTimeout", callAlertTimeout);
		if ((userid != null) && (userid.length() > 0) && (password != null)
				&& (password.length() > 0)) {

			String clientIpAddr = SalesUtilsFactory.INSTANCE.getClientIPAddress(request);

			logger.info("useridAuthenticationController="+userid);
			logger.info(userid+"_is_attempting_login_from_IP_Address="+clientIpAddr);

			// Verify Valid User with Access to Salescenter Application
			User user = AuthenticationService.INSTANCE.getUser(userid);

			// V Authentication and Authorization
			if (user != null) {	

				logger.info("userName_AuthenticationController="+user.getName());
                startTimer=timer.getTime();
				UserAuthorization userAuthorization = UserAccessService.INSTANCE.authenticate(userid, password);
				logger.info("TimeTakenForUserAccessServiceCall="+(timer.getTime()-startTimer));
				userAuthorization.setUser(user);
				String imgPath = SALESCENTER_IDLEPAGE_IMAGE_LOCATION;
				String imgCount = SALESCENTER_IDLEPAGE_IMAGE_COUNT;
				String imgDelay = SALESCENTER_IDLEPAGE_IMAGE_DELAY;

				if (userAuthorization.authenticated()) {
					//checking for permission to login to CONCERT
					AuthorizationMap<String, Map<String, List<String>>> authMap = userAuthorization.getPermissions();
					Set<String> resourceSet = authMap.getContext("CONCERT").keySet();
					if(!resourceSet.contains("LOGIN")){
						mav.setViewName("login");
						mav.addObject("loginFailed", true);
						mav.addObject("permissionDenied", true);
						return mav;
					}
					logger.info("Is_User_Authorization_Authenticated="+userAuthorization.authenticated());
					SessionCache.INSTANCE.set(request.getSession(),	userAuthorization);

					if (SessionCache.INSTANCE.isAuthenticated(request.getSession())) {
						try{
							Boolean enable_fuse_analytics = Boolean.valueOf(ConfigRepo.getString("*.enable_fuse_analytics"));
							if(enable_fuse_analytics){
								request.getSession().setAttribute("enable_fuse_analytics", enable_fuse_analytics);
								String fuseUrl = ConfigRepo.getString("*.fuse_url");
								if(!Utils.isBlank(fuseUrl)){
									request.getSession().setAttribute("fuseUrl", fuseUrl);
								}
							}
							List<CustomerTracker> tsLineItemTrackerDataList= (List<CustomerTracker>)request.getSession().getAttribute("tsLineItemTrackerDataList");
							request.getSession().setAttribute("currentOrderId", null);
							
							if(request.getSession().getAttribute("customerTrackerDetails") == null){
								CustomerTrackerDetails customerTrackerDetails= customerTrackerDao.getCustomerTrackerDetailsByAgentId(userid);
								request.getSession().setAttribute("customerTrackerDetails",customerTrackerDetails);
							}
							if( tsLineItemTrackerDataList == null || tsLineItemTrackerDataList.isEmpty()){
								tsLineItemTrackerDataList = customerTrackerDao.getCustomerTrackerDataByAgentId(userid);
								if(tsLineItemTrackerDataList != null && !tsLineItemTrackerDataList.isEmpty()){
									tsLineItemTrackerDataList = Utils.sortCustomers(tsLineItemTrackerDataList);
									logger.info("tsLineItemTrackerDataList size="+tsLineItemTrackerDataList.size());
								}
								Utils.setAgentTracker(tsLineItemTrackerDataList, request.getSession());
								request.getSession().setAttribute("tsLineItemTrackerDataList",tsLineItemTrackerDataList);
							}else{
								Utils.setAgentTracker(tsLineItemTrackerDataList, request.getSession());
							}
							//logger.info("customerCallNumberId="+customerCallNumberId);
							
						}catch(Exception e){
							logger.info("error_in_login_process"+e.getMessage());
						}
						String name=userAuthorization.getUserName();
						String names[]=name.split(" ");
						String firstname=names[0];
						SalesCenterVO salesCenterVo = new SalesCenterVO();
						salesCenterVo.setValueByName("agent.name.first", firstname);
						salesCenterVo.setValueByName("agent.id", userid);
						request.getSession().setAttribute("salescontext", salesCenterVo);
						request.getSession().setAttribute("username", userid);
						request.getSession().setAttribute("password", password);
						request.getSession().setAttribute("phoneId", userAuthorization.getMetaDataMap().get("AGENT_PHONE_ID"));
						String customerTracker= ConfigRepo.getString("*.display_customer_tracker");
						request.getSession().setAttribute("customerTracker", Boolean.valueOf(customerTracker));
						String displayRoadmap= ConfigRepo.getString("*.display_roadmap");
						request.getSession().setAttribute("displayRoadmap", Boolean.valueOf(displayRoadmap));
						logger.info("show customer tracker "+customerTracker);
						getThoughtSpotDataFromCache(request);
						if(!Utils.isBlank(userAuthorization.getMetaDataMap().get("AGENT_PHONE_ID"))){
							request.getSession().setAttribute("phoneIdValue", userAuthorization.getMetaDataMap().get("AGENT_PHONE_ID"));
						}
						else{
							request.getSession().setAttribute("phoneIdValue", "12345");
						}
						request.getSession().setAttribute("agentExtId", userAuthorization.getMetaDataMap().get("agentid"));
						String userGroupNameCallFlow = "defaultUserGroup";
						String userGroupNameProduct = "defaultUserGroup";
						if (userAuthorization.getUserGroups() != null && !userAuthorization.getUserGroups().isEmpty()) {
							for(UserGroup userGroup:userAuthorization.getUserGroups()){
								if (userGroup.getImpactedAreas() != null && !userGroup.getImpactedAreas().isEmpty()) {
									if (userGroup.getImpactedAreas().toString().toLowerCase().trim().contains("call flow")) {
										userGroupNameCallFlow = userGroup.getGroupName();
									}
									if (userGroup.getImpactedAreas().toString().toLowerCase().trim().contains("product")) {
										userGroupNameProduct = userGroup.getGroupName();
									}
								}
							}
						} 
						logger.info("userGroupCallFlow="+userGroupNameCallFlow);
						logger.info("userGroupProduct="+userGroupNameProduct);
						request.getSession().setAttribute("userGroup", userGroupNameCallFlow);
						request.getSession().setAttribute("userGroupProduct", userGroupNameProduct);
						
						/*ProviderTransferAgentGroup providerTransferAgentGroup = providerTransferAgentGroupDao.getProviderTransferAgentGroups(userGroupNameCallFlow);
						if((providerTransferAgentGroup != null) && (!Utils.isBlank(providerTransferAgentGroup.getProviderId()))){
							salesCenterVo.setValueByName("transferProviderIds",providerTransferAgentGroup.getProviderId());
							logger.info("transferEnabledProviderIds based on current agent group["+ providerTransferAgentGroup.getProviderId() + "]");
						}
						salesCenterVo.setValueByName("hasComcastGroup",Boolean.toString(providerTransferAgentGroupDao.hasComcastGroup()));
						salesCenterVo.setValueByName("hasATTGroup",Boolean.toString(providerTransferAgentGroupDao.hasATTGroup()));
						salesCenterVo.setValueByName("hasATTV6Group",Boolean.toString(providerTransferAgentGroupDao.hasATTV6Group()));*/
						try {
							
							addPopFlowObjects(request, mav);
							
						} catch (Exception e) {
							e.printStackTrace();
						}

						try{
							Provider provider = providerDao.get(0L);
							logger.info("provider.getUrl()::::::::::::::::::::"+provider.getUrl());
							request.getSession().setAttribute("cometd_url", provider.getUrl());
						} catch (Exception e){
							logger.info("Exception while getting Provider content -->"+e.getMessage());
							request.getSession().setAttribute("cometd_url", "http://sym.symdvi001.AL.com/amb");
						}
							String urlPath = "";
							if (request.getRequestURL().toString().contains(
									"localhost")) {
								String url = request.getRequestURL().toString();
								String uri = request.getRequestURI().toString();
								int i = url.indexOf(uri);
								urlPath = url.substring(0, i);
								request.getSession().setAttribute("urlPath",
										urlPath);
							} else {
								urlPath = ConfigRepo
										.getString("*.host_name_url");
								logger.info("urlPath from DB:::" + urlPath);
								if (urlPath != null && urlPath != "") {
									urlPath = urlPath + "/salescenter";
									request.getSession().setAttribute(
											"urlPath", urlPath);
								} else {
									request.getSession().setAttribute(
											"urlPath", "");
								}
							}
							logger.info("absolute url path:::::" + urlPath);

						mav.addObject("userFromDB", user);						
						mav.addObject("pageId", "startCall");
						mav.addObject("imgDelay", imgDelay);
						mav.addObject("imgPath", imgPath);
						mav.addObject("imgCount", imgCount);
						mav.setViewName("context_select");
						request.getSession().setAttribute("idlePageStartTime", Calendar.getInstance());
						
						
						SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
						Calendar calendar = Calendar.getInstance();;
						
						request.getSession().setAttribute("trackerUpdateTime", sdf.format(calendar.getTime()));

						if(request.getSession().getAttribute("webMetricStartTime") == null){
							Double webMetricStartTime = Double.valueOf(Calendar.getInstance().getTimeInMillis());
							request.getSession().setAttribute("webMetricStartTime", webMetricStartTime.toString());
						}
						if (ConfigRepo.getString("*.enable_samecall_restriction") != null && ConfigRepo.getString("*.enable_samecall_restriction").equalsIgnoreCase("true")){
							mav.addObject("enableSamecallRestriction", true);
						}
						//Putting Auth in Session after Successful login
						request.getSession().setAttribute("mavFromSession", mav);
						
						String isEmailIconEnabled = ConfigRepo.getString("*.enable_email_icon") != null ? ConfigRepo.getString("*.enable_email_icon") : "true";
						logger.info("enable_email_icon=" + (ConfigRepo.getString("*.enable_email_icon") != null ? ConfigRepo.getString("*.enable_email_icon") : "true"));
						request.getSession().setAttribute("isEmailIconEnabled", isEmailIconEnabled);
					}
				}else{
					mav.setViewName("login");
					mav.addObject("loginFailed", true);
					String failure_text = userAuthorization.getFailureText();
					if(failure_text.equals("WRONG_PWD")){
						mav.addObject("wrongPwd", true);
						mav.addObject("userName",userid);
						mav.addObject("pswd", "");
					} else {
						mav.addObject("wrongUserName", true);
					}
				}
			}
		}
		logger.info("TimeTakenforcompleteTheLoginProcess="+timer.getTime());
		logger.info("login_process_end_in_AuthenticationController");
		return mav;
		}catch(Exception e){
			logger.error("Error_in_AuthenticationController",e);
			throw new UnRecoverableException(e.getMessage());
		}
		finally{
			timer.stop();
		}
	}

	@RequestMapping(value = "/request")
	public ModelAndView request() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("request");
		return mav;
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		return mav;
	}


	/*	
	 * Populating List of BusinessPartyNames.		 
	 */
	public String addPopFlowObjects(HttpServletRequest request, ModelAndView mav) throws UnRecoverableException {
		StopWatch timer=new StopWatch();
		timer.start();
		long startTimer = 0;
		try{
			logger.info("addPopFlowObjects_begin_in_AuthenticationController");
			List<OrderSourceBusinessPartyType> businessPartyList = new ArrayList<OrderSourceBusinessPartyType>();

			int detailsCacheTimeout = ConfigRepo.getInt("*.details_cache_time_out") == 0 ? 7200000 : ConfigRepo.getInt("*.details_cache_time_out");
			startTimer=timer.getTime();
			DetailsRequestResponse drr = DetailService.INSTANCE.getAllOrderSources("12345", Long.valueOf(detailsCacheTimeout));
			logger.info("TimeTakenForDetailsServiceCall="+(timer.getTime()-startTimer));
			List<OrderSourceResultType> osrtList = new ArrayList<OrderSourceResultType>();
			if(drr != null && !(drr.equals(""))){
				osrtList =  drr.getResponse().getOrderSourceResultElement();			
				addBusinesspartyToModel(osrtList, businessPartyList);
			}

			JSONObject vdnWithReferrerNameJSONObject = new JSONObject();
			Map<String,String> vdnEnglishMap = new HashMap<String, String>();
			Map<String,String> vdnSpanishMap = new HashMap<String, String>();
			Map<String,String> vdnEnglishDefaultMap = new HashMap<String, String>();
			Map<String,String> vdnSpanishDefaultMap = new HashMap<String, String>();
			
			for(OrderSourceResultType obj:osrtList){
				String referrerId =obj.getOrderSource().getBusinessParty().getExternalId();
				String referrername = obj.getOrderSource().getBusinessParty().getName();
				String referrerkey  = referrerId +"|"+ referrername;
				List<TelephonyType> telephonyList = obj.getOrderSource().getTelephonyList().getTelephony();
				for(TelephonyType telephonyType : telephonyList){
					if(Constants.SALES.equalsIgnoreCase(telephonyType.getCallType())){
						vdnWithReferrerNameJSONObject.put(telephonyType.getVdn(),referrerkey);
						if ("English".equalsIgnoreCase(telephonyType.getLanguage())){
							if(obj.getOrderSource().isIsDefault()){
								vdnEnglishDefaultMap.put(referrerkey, telephonyType.getVdn());
							}
							else{
								vdnEnglishMap.put(referrerkey, telephonyType.getVdn());
							}
						}else{
							if(obj.getOrderSource().isIsDefault()){
								vdnSpanishDefaultMap.put(referrerkey, telephonyType.getVdn());
							}
							else{
								vdnSpanishMap.put(referrerkey, telephonyType.getVdn());
							}
						}
					}
				}
			}
			List<ReferrerVO> referrerVOList = new ArrayList<ReferrerVO>();
			Map<String,OrderSourceBusinessPartyType> sorting = new TreeMap<String, OrderSourceBusinessPartyType>();
			for(OrderSourceBusinessPartyType obj:businessPartyList){
				//For 91247 Sorting and changes the code for Unique Referrer names
				if(sorting.get(obj.getName()+"|"+obj.getExternalId()) == null)
				{
					sorting.put(obj.getName()+"|"+obj.getExternalId(), obj);
				}
			}

		//	logger.info("vdnWithReferrerNameJSONObject="+vdnWithReferrerNameJSONObject);

			JSONArray businessPartyJSONArray = new JSONArray();

			for(Entry<String, OrderSourceBusinessPartyType> entry:sorting.entrySet()){
				String currentVdn = vdnEnglishDefaultMap.get(entry.getValue().getExternalId()+"|"+entry.getValue().getName());
			    JSONObject jsonObject = new JSONObject();
			    if(currentVdn == null) 
			    { 
			     if(!Utils.isBlank(vdnSpanishDefaultMap.get(entry.getValue().getExternalId()+"|"+entry.getValue().getName()))){
			         currentVdn = vdnSpanishDefaultMap.get(entry.getValue().getExternalId()+"|"+entry.getValue().getName());
			     }
			     else if(!Utils.isBlank(vdnEnglishMap.get(entry.getValue().getExternalId()+"|"+entry.getValue().getName()))){
			         currentVdn = vdnEnglishMap.get(entry.getValue().getExternalId()+"|"+entry.getValue().getName());
			     }
			     else if(!Utils.isBlank(vdnSpanishMap.get(entry.getValue().getExternalId()+"|"+entry.getValue().getName()))){
			         currentVdn = vdnSpanishMap.get(entry.getValue().getExternalId()+"|"+entry.getValue().getName());
			     }
			    }
				jsonObject.put("label",entry.getValue().getName());
				jsonObject.put("value",entry.getValue().getExternalId()+"|"+entry.getValue().getName()+"|"+currentVdn);
				referrerVOList.add(new ReferrerVO(entry.getValue().getName(),entry.getValue().getExternalId(), currentVdn));
				businessPartyJSONArray.put(jsonObject);
			}

			mav.addObject("businessPartyJSONArray", businessPartyJSONArray.toString());
			mav.addObject("vdnWithReferrerNameJSONObject",  vdnWithReferrerNameJSONObject.toString());
			mav.addObject("businessPartyList", referrerVOList);

			if(referrerVOList.isEmpty())
			{
				throw new UnRecoverableException("No Referrer's Found");
			}
			return null;
		}catch (Exception e) {

			logger.error("error_at_addPopFlowObjects_in_AuthenticationController",e);
			throw new UnRecoverableException(e.getMessage());
		}finally{
			timer.stop();
			logger.info("addPopFlowObjects_end_in_AuthenticationController");
		}

	}

	private void addBusinesspartyToModel(List<OrderSourceResultType> list,
			List<OrderSourceBusinessPartyType> businessPartyList)throws Exception {
		for(OrderSourceResultType orst: list){
			if(!orst.getOrderSource().getName().equals("IDLE_PAGE_HIDDEN")){
				businessPartyList.add(orst.getOrderSource().getBusinessParty());				
			} 
		}
	}

	@SuppressWarnings("unchecked")
	private void getThoughtSpotDataFromCache(HttpServletRequest request) {

		logger.info("Before cache clear ==========");

		String thoughtSpotrank= ConfigRepo.getString("*.display_thoughtspot_rank");

		//logger.info("thoughtSpotData from cache="+ThoughtspotCacheService.INSTANCE.get("thoughtSpotData"));
		DecimalFormat df =new DecimalFormat("#.##");
		Map<String,List<AgentVO>> enterpriseDataMap = new HashMap<String,List<AgentVO>>();
		Map<String, List<AgentVO>> callCenterDataMap = new HashMap<String, List<AgentVO>>();
		Map<String, List<AgentVO>> regionDataMap = new HashMap<String, List<AgentVO>>();
		Map<String, List<AgentVO>> thoughtSpotDataMap =new HashMap<String, List<AgentVO>>();
		Map<String,AgentVO> agentDataMap = new HashMap<String,AgentVO>();


		//String callcenter= "lex"; //[lex, stg, atl, fcs, tja]
		//String agentId = "mimenifield";//"lamoncur";//"almurphy"; //maquijano
		String agentId = (String) request.getSession().getAttribute("username"); 
		AgentVO agentObject = new AgentVO();
		List<AgentVO> agentsList = new ArrayList<AgentVO>();
		logger.info("TSCacheDateTime ="+ThoughtspotCacheService.INSTANCE.get("TSCacheDateTime"));

		if(ThoughtspotCacheService.INSTANCE.get("agentsList") != null){
			agentsList =(List<AgentVO>)ThoughtspotCacheService.INSTANCE.get("agentsList");
		}
		
		if(ThoughtspotCacheService.INSTANCE.get("thoughtSpotData") != null){
			thoughtSpotDataMap =(Map<String, List<AgentVO>>)ThoughtspotCacheService.INSTANCE.get("thoughtSpotData");
		}
		if(ThoughtspotCacheService.INSTANCE.get("agentDataMap") != null){
			agentDataMap =(Map<String, AgentVO>)ThoughtspotCacheService.INSTANCE.get("agentDataMap");
		}
		JSONObject thoughtSpotJsonObj = new JSONObject();
		try{

			if(thoughtSpotDataMap != null && !thoughtSpotDataMap.isEmpty()){
				if(!agentDataMap.isEmpty()){
					JSONObject obj = new JSONObject();
					if (agentDataMap.get(agentId) == null){
						for (AgentVO vo :agentsList){
							if (vo != null && vo.getAgentId() != null && vo.getAgentId().equalsIgnoreCase(agentId)){
								agentObject = vo;
								obj = new JSONObject();
								obj.put("agentId", agentId);
								obj.put("agentName", WordUtils.capitalize(vo.getAgentName()));
								obj.put("callCenter", WordUtils.capitalize(vo.getCallCenter()));
								obj.put("callCenterRank", "N/A");
								obj.put("enterpriseRank", "N/A");
								obj.put("region", WordUtils.capitalize(vo.getRegion()));
								obj.put("regionRank", "N/A");
								obj.put("points", "0");
							}
						}
					}else {
						obj = new JSONObject();
						agentObject = (AgentVO)agentDataMap.get(agentId);
						obj.put("agentId", agentObject.getAgentId());
						obj.put("agentName", WordUtils.capitalize(agentObject.getAgentName()));
						obj.put("callCenter", WordUtils.capitalize(agentObject.getCallCenter()));
						obj.put("callCenterRank", agentObject.getCallCenterRank() != null ? agentObject.getCallCenterRank() : "N/A");
						obj.put("enterpriseRank", agentObject.getEnterpriseRank() != null ? agentObject.getEnterpriseRank() : "N/A");
						obj.put("region", WordUtils.capitalize(agentObject.getRegion()));
						obj.put("regionRank", agentObject.getRegionRank() != null ? agentObject.getRegionRank() : "N/A");
						if(agentObject.getGCRevenue() != 0.0){
							obj.put("points", df.format(agentObject.getGCRevenue()));
						}else{
							obj.put("points", agentObject.getGCRevenue());
						}
					}
					thoughtSpotJsonObj.put("currentuserData", obj);
				}
				if(thoughtSpotDataMap.get("enterpriseData") != null){
					enterpriseDataMap = (Map<String, List<AgentVO>>) thoughtSpotDataMap.get("enterpriseData");
					JSONArray enterpriseDataArray = new JSONArray();
					int i =0;
					for(Map.Entry<String,List<AgentVO>> entry : enterpriseDataMap.entrySet()){
						List<AgentVO> agentVOList = entry.getValue();
						enterpriseDataArray = getAgentsData(agentVOList, enterpriseDataArray);
					}
					thoughtSpotJsonObj.put("enterpriseData", enterpriseDataArray);
				}
				if(thoughtSpotDataMap.get("callCenterData") != null){
					callCenterDataMap = (Map<String, List<AgentVO>>) thoughtSpotDataMap.get("callCenterData");	
					JSONArray callCenterDataArray = new JSONArray();
					List<AgentVO> agentVOList = callCenterDataMap.get(agentObject.getCallCenter());
					callCenterDataArray = getAgentsData(agentVOList, callCenterDataArray);
					thoughtSpotJsonObj.put("callCenterData", callCenterDataArray);
				}
				if(thoughtSpotDataMap.get("regionData") != null){
					regionDataMap = (Map<String, List<AgentVO>>) thoughtSpotDataMap.get("regionData");
					JSONArray regionDataArray = new JSONArray();
					List<AgentVO> agentVOList = regionDataMap.get(agentObject.getRegion());
					regionDataArray = getAgentsData(agentVOList, regionDataArray);
					thoughtSpotJsonObj.put("regionData", regionDataArray);
				}
				//logger.info("thoughtSpotJsonObj="+thoughtSpotJsonObj.toString());
				request.getSession().setAttribute("TSCacheDateTime", ThoughtspotCacheService.INSTANCE.get("TSCacheDateTime"));
				request.getSession().setAttribute("thoughtSpotData", thoughtSpotJsonObj.toString());
				logger.info("thoughtSpotDataMap size"+thoughtSpotDataMap.size());
			}
		}catch(Exception e){
			logger.info("error occured while retrieving data from cache"+e);
		}
		request.getSession().setAttribute("thoughtSpotrank", Boolean.valueOf(thoughtSpotrank));

		//logger.info("thoughtSpotData ===="+thoughtSpotJsonObj.toString());
		logger.info("TSCacheDateTime ===="+ThoughtspotCacheService.INSTANCE.get("TSCacheDateTime"));
		logger.info("thoughtSpotrank ===="+Boolean.valueOf(thoughtSpotrank));

	}
	private JSONArray getAgentsData(List<AgentVO> agentVOList,JSONArray regionDataArray) throws IOException {
		int k =0;
		DecimalFormat df =new DecimalFormat("#.##");
		JSONObject obj = new JSONObject();
		try{
			if(agentVOList != null && !agentVOList.isEmpty()){
				for(AgentVO agentObect: agentVOList){
					k++;
					obj = new JSONObject();
					obj.put("agentId", agentObect.getAgentId());
					obj.put("agentName", WordUtils.capitalize(agentObect.getAgentName()));
					obj.put("callCenter", WordUtils.capitalize(agentObect.getCallCenter()));
					obj.put("callCenterRank", agentObect.getCallCenterRank() != null ? agentObect.getCallCenterRank() : "N/A");
					obj.put("enterpriseRank", agentObect.getEnterpriseRank() != null ? agentObect.getEnterpriseRank() : "N/A");
					obj.put("region", WordUtils.capitalize(agentObect.getRegion()));
					obj.put("regionRank", agentObect.getRegionRank() != null ? agentObect.getRegionRank() : "N/A");
					if(agentObect.getGCRevenue() != 0.0){
						obj.put("points", df.format(agentObect.getGCRevenue()));
					}else{
						obj.put("points", agentObect.getGCRevenue());
					}
					regionDataArray.put(obj);
					if(k == 10){
						break;
					}
				}
			}
		}catch(Exception e){
			logger.info("error occured while retrieving data from Agent "+e);
		}
		return regionDataArray;
	}
	@RequestMapping(value = "/updateAgentPoints")
	public @ResponseBody String updateTrackerData(HttpServletRequest request,HttpServletResponse response)throws Exception{
		CustomerTrackerConversion customerTrackerJson = null;
		Gson gson = new Gson();
		try{
			List<CustomerTracker> tsLineItemTrackerDataList= (List<CustomerTracker>)request.getSession().getAttribute("tsLineItemTrackerDataList");
			Map<String,Float> updatedActualPointsMap = new HashMap<String,Float>();
			logger.info("CustomerTrackerDataList size="+tsLineItemTrackerDataList.size());
			if(tsLineItemTrackerDataList != null && !tsLineItemTrackerDataList.isEmpty()){
				tsLineItemTrackerDataList = Utils.sortCustomers(tsLineItemTrackerDataList);
				ListIterator<CustomerTracker> itr = tsLineItemTrackerDataList.listIterator();
				
				String updatedTrackerJson = request.getParameter("updatedTrackerJson");
				logger.info("updatedTrackerJson="+updatedTrackerJson);
				JSONObject jsonObject = new JSONObject(updatedTrackerJson);
					try {
						ObjectMapper mapper = new ObjectMapper();
						String json1 = jsonObject.toString();
						updatedActualPointsMap = mapper.readValue(json1, new TypeReference<Map<String, Float>>(){});
						
					} catch (Exception e) {
						logger.info("error occured while converting json into map"+e.getMessage());
					}
				if(!updatedActualPointsMap.isEmpty() && updatedActualPointsMap != null){
					while(itr.hasNext()){
						CustomerTracker customerTracker = (CustomerTracker) itr.next();
						logger.info("LineItemId="+customerTracker.getLineItemId());
						if(customerTracker.getLineItemId() != null ){
							for(String objectKey: updatedActualPointsMap.keySet()){
								if(objectKey.equalsIgnoreCase(String.valueOf(customerTracker.getLineItemId()))){
										customerTracker.setActualPoints(Float.valueOf(updatedActualPointsMap.get(objectKey)));
										customerTracker.setIsPointsUpdated(1);
										logger.info("key="+objectKey+" :: value="+updatedActualPointsMap.get(objectKey));
										break;
								}
							}
						}
					}
				}
			}
			if(tsLineItemTrackerDataList != null && !tsLineItemTrackerDataList.isEmpty()){
				request.getSession().setAttribute("updatedActualPointsMap",updatedActualPointsMap);
				request.getSession().setAttribute("tsLineItemTrackerDataList",tsLineItemTrackerDataList);
				tsLineItemTrackerDataList = Utils.sortCustomers(tsLineItemTrackerDataList);
				Utils.setAgentTracker(tsLineItemTrackerDataList, request.getSession());
				customerTrackerJson = (CustomerTrackerConversion)request.getSession().getAttribute("customerTrackerJson");
				//logger.info("updated tracker data list="+tsLineItemTrackerDataList);
				
			}else{
				Utils.setAgentTracker(tsLineItemTrackerDataList, request.getSession());
				customerTrackerJson = (CustomerTrackerConversion)request.getSession().getAttribute("customerTrackerJson");
			}
			if(updatedActualPointsMap != null && !updatedActualPointsMap.isEmpty()){
				customerTrackerDao.updateCustomerTracker(updatedActualPointsMap) ;
			}
		}catch(Exception e){
			logger.info("error while updating customer tracker points"+e.getMessage());
			return null;
		}
		if(customerTrackerJson != null){
			return gson.toJson(customerTrackerJson);
		}else{
			return null;
		}
	}
	
	@RequestMapping(value = "/updateAgentData")
	public @ResponseBody String updateAgentData(HttpServletRequest request,HttpServletResponse response)throws Exception{
		CustomerTrackerConversion customerTrackerJson = null;
		Gson gson = new Gson();
		try{
			List<CustomerTracker> tsLineItemTrackerDataList= (List<CustomerTracker>)request.getSession().getAttribute("tsLineItemTrackerDataList");
			if(tsLineItemTrackerDataList != null && !tsLineItemTrackerDataList.isEmpty()){
				request.getSession().setAttribute("updatedCallCount",request.getParameter("updatedCallCount"));
				tsLineItemTrackerDataList = Utils.sortCustomers(tsLineItemTrackerDataList);
				Utils.setAgentTracker(tsLineItemTrackerDataList, request.getSession());
				customerTrackerJson = (CustomerTrackerConversion)request.getSession().getAttribute("customerTrackerJson");
			}else{
				request.getSession().setAttribute("updatedCallCount",request.getParameter("updatedCallCount"));
				Utils.setAgentTracker(tsLineItemTrackerDataList, request.getSession());
				customerTrackerJson = (CustomerTrackerConversion)request.getSession().getAttribute("customerTrackerJson");
			}
			if(request.getSession().getAttribute("customerTrackerDetails") != null){
				CustomerTrackerDetails customerTrackerDetails = (CustomerTrackerDetails)request.getSession().getAttribute("customerTrackerDetails");
				customerTrackerDao.updateCustomerTrackerDetails(customerTrackerDetails);
			}
		}catch(Exception e){
			logger.info("error while updating customer data"+e.getMessage());
			return null;
		}
		if(customerTrackerJson != null){
			return gson.toJson(customerTrackerJson);
		}else{
			return null;
		}
	}
	
	@RequestMapping(value = "/updatedUtilityPoints")
	public @ResponseBody String updatedUtilityPoints(HttpServletRequest request,HttpServletResponse response)throws Exception{
		CustomerTrackerConversion customerTrackerJson = null;
		Gson gson = new Gson();
		try{
			List<CustomerTracker> tsLineItemTrackerDataList= (List<CustomerTracker>)request.getSession().getAttribute("tsLineItemTrackerDataList");
			if(tsLineItemTrackerDataList != null && !tsLineItemTrackerDataList.isEmpty()){
				request.getSession().setAttribute("updatedUtilityPoints",request.getParameter("updatedUtilityPoints"));
				tsLineItemTrackerDataList = Utils.sortCustomers(tsLineItemTrackerDataList);
				Utils.setAgentTracker(tsLineItemTrackerDataList, request.getSession());
				customerTrackerJson = (CustomerTrackerConversion)request.getSession().getAttribute("customerTrackerJson");
			}else{
					request.getSession().setAttribute("updatedUtilityPoints",request.getParameter("updatedUtilityPoints"));
					Utils.setAgentTracker(tsLineItemTrackerDataList, request.getSession());
					customerTrackerJson = (CustomerTrackerConversion)request.getSession().getAttribute("customerTrackerJson");
			}
			if(request.getSession().getAttribute("customerTrackerDetails") != null){
				CustomerTrackerDetails customerTrackerDetails = (CustomerTrackerDetails)request.getSession().getAttribute("customerTrackerDetails");
				customerTrackerDao.updateCustomerTrackerDetails(customerTrackerDetails);
			}
		}catch(Exception e){
			logger.info("error while updating customer updatedUtilityPoints "+e.getMessage());
			return null;
		}
		if(customerTrackerJson != null){
			return gson.toJson(customerTrackerJson);
		}else{
			return null;
		}
	}
	
}

