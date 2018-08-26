package com.AL.controller;

import static com.AL.ui.util.ConfigProperties.ADVISORYPROMOTION;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.AL.html.Del;
import com.AL.html.Div;
import com.AL.html.Fieldset;
import com.AL.html.Form;
import com.AL.html.Hr;
import com.AL.html.Img;
import com.AL.html.Input;
import com.AL.html.Li;
import com.AL.html.ObjectFactory;
import com.AL.html.Span;
import com.AL.html.Ul;
import com.AL.productResults.managers.ProductResultsManager;
import com.AL.productResults.vo.ProductSearchIface;
import com.AL.productResults.vo.ProductSummaryVO;
import com.AL.service.SalesServiceImpl;
import com.AL.ui.builder.HtmlBuilder;
import com.AL.ui.constants.Constants;
import com.AL.ui.domain.dynamic.dialogue.DataField;
import com.AL.ui.domain.dynamic.dialogue.DataGroup;
import com.AL.ui.domain.dynamic.dialogue.Dialogue;
import com.AL.ui.factory.CartLineItemFactory;
import com.AL.ui.factory.SalesDialogueFactory;
import com.AL.ui.factory.SalesUtilsFactory;
import com.AL.ui.service.config.ConfigRepo;
import com.AL.ui.service.V.DetailService;
import com.AL.ui.service.V.DialogServiceUI;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.service.V.ProductService;
import com.AL.ui.util.HtmlFactory;
import com.AL.ui.util.LineItemUtil;
import com.AL.ui.util.SalesUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.ProductVOJSON;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.ui.vo.SalesDialogueVO;
import com.AL.ui.vo.RoadMapCriteriaVO;
import com.AL.V.domain.SalesContext;
import com.AL.V.exception.UnRecoverableException;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.dtl.v4.BusinessPartyDetailResultType;
import com.AL.xml.dtl.v4.DetailResultType;
import com.AL.xml.dtl.v4.DetailsRequestResponse;
import com.AL.xml.pr.v4.CapabilityType;
import com.AL.xml.pr.v4.DescriptiveInfoType;
import com.AL.xml.pr.v4.EnergyPriceInfoType.Rate;
import com.AL.xml.pr.v4.FeatureGroupType;
import com.AL.xml.pr.v4.FeatureType;
import com.AL.xml.pr.v4.ProductInfoType;
import com.AL.xml.pr.v4.ProductPromotionType;
import com.AL.xml.se.v4.ProviderCriteriaEntityType2;
import com.AL.xml.se.v4.ProviderCriteriaType2;
import com.AL.xml.se.v4.ProviderNameValuePairType2;
import com.AL.xml.v4.OrderType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
public class SalesPowerPitchController extends BaseController {

	private static final Logger logger = Logger.getLogger(SalesPowerPitchController.class);
	private static final ObjectFactory oFactory = new ObjectFactory();

	/*@Value("${advisoryPromotion}")
 private String advisoryPromotion;*/

	@Autowired
	private SalesServiceImpl salesServiceImpl;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/recommendations/getProviderStatus")
	public @ResponseBody String getProviderStatus(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Map < String, String > resultMap = new HashMap < String, String > ();
		Map < String, String > newResultMap = new HashMap < String, String > ();
		boolean isSuccessed = false;
		boolean isPending = false;
		Map < String, String > productMap1 = new HashMap < String, String > ();
		if (request.getSession().getAttribute("realTimeMap") != null) {
			resultMap = (Map < String, String > ) request.getSession().getAttribute("realTimeMap");
		}
		//realTimeStatusMap.put(dispayName, status + "|" + statusMsg);
		ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");
		Map < String, String > providersMap = new HashMap < String, String > (productResultManager.getRealTimeStatusMap());
		boolean isPollingCompleted = productResultManager.isPollingCompleted();
		//logger.info("providersMap=" + providersMap);
		//Code changes for the ticket TI-934
		if (productResultManager.getHideProvidersInRealtimeWidgetList() != null) {
			for (String providerName: productResultManager.getHideProvidersInRealtimeWidgetList()) {
				if (providersMap.get(providerName) != null) {
					providersMap.remove(providerName);
				}
			}
		}
		for (Entry < String, String > entry: providersMap.entrySet()) {
			String[] result = entry.getValue().split("\\|");
			if (result[0].equalsIgnoreCase("Sell") || result[0].equalsIgnoreCase("Success")) {
				if (resultMap != null && resultMap.get(entry.getKey()) != null) {
					String[] status = resultMap.get(entry.getKey()).split("\\|");
					if (status[0].equalsIgnoreCase("Pending")) {
						if(!isSuccessed && productResultManager.getHideHughesNet() != null && productResultManager.getHideHughesNet().equalsIgnoreCase("true") && entry.getKey()!= null && entry.getKey().equalsIgnoreCase(Constants.HUGHESNET_NAME) && !productResultManager.isProductPollingCompleted()){
							isSuccessed = false;
							logger.info("HughesNet isSuccessed ="+isSuccessed);
						}
						else{
							isSuccessed = true;
						}
					}
				}
			}
			if (result[0].equalsIgnoreCase("Pending")) {
				isPending = true;
			}
			if (result[0] != null && result[0].equalsIgnoreCase("Multi_addr_issue")) {
				entry.setValue("Pending|" + result[1]);
			}
			if(productResultManager.getHideHughesNet() != null && productResultManager.getHideHughesNet().equalsIgnoreCase("true") && entry.getKey()!= null && entry.getKey().equalsIgnoreCase(Constants.HUGHESNET_NAME) && !productResultManager.isProductPollingCompleted()){
				productMap1.put(entry.getKey(), Constants.PENDING);
				isPending = true;
			}
			else{
				productMap1.put(entry.getKey(), entry.getValue().replaceAll("Sell", "Available"));
			}
			
		}
		newResultMap.putAll(productMap1);
		request.getSession().setAttribute("realTimeMap", newResultMap);
		JSONObject obj = new JSONObject();
		try {
			if (productResultManager != null && productResultManager.getHintProvidersMap() != null && !productResultManager.getHintProvidersMap().isEmpty()) {
				String hintProviders = productResultManager.getHintProvidersMap().keySet().toString();
				logger.info("hintProviders=" + hintProviders);
				session.setAttribute("hintProviders", hintProviders);
			}
		} catch (Exception e) {
			logger.error("error_while_retreiving_hintProviders" + e.getMessage());
		}
		Map<String,String> selectedExistingProvidersMap = productResultManager.getSelectedExistingProvidersMap();
		logger.info("selectedExistingProviders=" + productResultManager.getSelectedExistingProvidersMap());
		String transferAuthenticatedProviders  = ConfigRepo.getString("*.transfer_authenticated_providers");
		Map<String,String> transferAuthenticatedProvidersMap = new HashMap<String, String>();
		List<String> transferAuthenticatedProviderExtIdsList = new ArrayList<String>();
		if (!Utils.isBlank(transferAuthenticatedProviders) && selectedExistingProvidersMap!= null) {
			String transferAuthenticatedProvidersList[] = transferAuthenticatedProviders.split("\\|");
			for (String providerIdWithName: transferAuthenticatedProvidersList) {
				String providerValues[] = providerIdWithName.split("=");
				transferAuthenticatedProvidersMap.put(providerValues[1], providerValues[0]);
			}
			for (Entry<String,String> entry : transferAuthenticatedProvidersMap.entrySet()){
				if(selectedExistingProvidersMap!= null&&selectedExistingProvidersMap.get(entry.getKey())!=null){
					transferAuthenticatedProviderExtIdsList.add(entry.getValue());
				}
			}
		}
		for (Entry < String, String > entry: productResultManager.getSelectedExistingProvidersMap().entrySet()) {
			logger.info("value=" + providersMap.get(entry.getKey()));
			if (providersMap.get(entry.getKey()) != null) {
				if (providersMap.get(entry.getKey()).contains("Sell|")) {
					if(transferAuthenticatedProviderExtIdsList!=null && transferAuthenticatedProviderExtIdsList.size()>0 && transferAuthenticatedProvidersMap!= null 
							&& transferAuthenticatedProvidersMap.get(entry.getKey())!=null && !entry.getKey().equalsIgnoreCase("CenturyLink")){
						
					}
					else{
						providersMap.put(entry.getKey(), "Start Existing|" + entry.getValue().replaceAll("Sell", "Available"));
					}
				}
			}
		}
		logger.info("providersMap1=" + providersMap);
		try {
			String isMAMSuccess = null;
			if(request.getSession().getAttribute("isMAMSuccess") != null){
				 isMAMSuccess = (String) request.getSession().getAttribute("isMAMSuccess");
			}
			
			providersMap = dispalyRealTimeStatusBasedOnStatusMessage(providersMap);
			logger.info("realTimeStatusMap=" + providersMap);
			boolean isCompleted = true;
			Map < String, String > newProvidersMap = new LinkedHashMap<String, String>() ;
			 Map<Long, Long> rtProductCountMap  = 	null;
			 if(productResultManager.getRtProductCountMap() != null){
				  rtProductCountMap  = 	productResultManager.getRtProductCountMap();
			 }
			boolean isHNProductShow = true;
			boolean isHugesNetProductavailable  = true;
			if(productResultManager.getHideHughesNet() != null && productResultManager.getHideHughesNet().equalsIgnoreCase("true") && productResultManager.isProductPollingCompleted()){
				isHNProductShow = Utils.isHughesNetServiceable(productResultManager.getProductsMap());
				isHugesNetProductavailable = Utils.isHughesNetProductAvaialbelInServiceable(productResultManager.getProductsMap(),rtProductCountMap);
				 logger.info("rtProductCountMap="+rtProductCountMap);
				 logger.info("isHNProductShow ="+isHNProductShow);
			}
			if(!providersMap.isEmpty()){
				for(String key: providersMap.keySet()){
					if(!key.equalsIgnoreCase(Constants.HUGHESNET_NAME)){
						isCompleted = productResultManager.isProductPollingCompleted();
						if(key.equalsIgnoreCase(Constants.ATTV6_NAME)  && providersMap.get(key).toLowerCase().contains("sell") && providersMap.get(key).toLowerCase().contains("contact provider")){
							newProvidersMap.put(key,"Available|SUCCESS");
						}else{
							newProvidersMap.put(key,providersMap.get(key).replaceAll("Sell", "Available"));
						}
					}
				}
				if(providersMap.get(Constants.HUGHESNET_NAME) != null){
					newProvidersMap.put(Constants.HUGHESNET_NAME,providersMap.get(Constants.HUGHESNET_NAME));
					if(productResultManager.getHideHughesNet() != null && productResultManager.getHideHughesNet().equalsIgnoreCase("true") && isHugesNetProductavailable){
						if(!isCompleted){
							newProvidersMap.put(Constants.HUGHESNET_NAME,providersMap.get(Constants.HUGHESNET_NAME).replace("Sell", "Pending"));
						}else if(isCompleted && isHNProductShow){
							newProvidersMap.put(Constants.HUGHESNET_NAME,providersMap.get(Constants.HUGHESNET_NAME).replace("Pending", "Sell"));
						}else if(isCompleted && !isHNProductShow){
							if(rtProductCountMap != null && !rtProductCountMap.isEmpty() && rtProductCountMap.get(Long.parseLong(Constants.HUGHESNET)) != null && (rtProductCountMap.get(Long.parseLong(Constants.HUGHESNET)) > 0)){
								if(productResultManager.isFrontierInternetAvailable()){
									newProvidersMap.put(Constants.HUGHESNET_NAME,providersMap.get(Constants.HUGHESNET_NAME).replace("Sell", "Not Available.").replace("Pending", "Not Available.").replace("Timeout", "Not Available."));
								}else{
									newProvidersMap.put(Constants.HUGHESNET_NAME,providersMap.get(Constants.HUGHESNET_NAME).replace("Sell", "Not Available.").replace("Pending", "Not Available.").replace("Timeout", "Not Available"));
								}
							}
							else{
								if(productResultManager.isFrontierInternetAvailable()){
									newProvidersMap.put(Constants.HUGHESNET_NAME,providersMap.get(Constants.HUGHESNET_NAME).replace("Sell", "Not Available.").replace("Pending", "Not Available.").replace("Timeout", "Not Available."));
								}else{
									newProvidersMap.put(Constants.HUGHESNET_NAME,providersMap.get(Constants.HUGHESNET_NAME).replace("Sell", "Not Available.").replace("Pending", "Not Available").replace("Timeout", "Not Available"));
								}
							}
						}
					}
				}
			}
			if(productResultManager.getHideHughesNet() != null && productResultManager.getHideHughesNet().equalsIgnoreCase("true")){
				if(isHNProductShow){
					obj.put("isHNProductShow", isHNProductShow);
					 if(productResultManager.isProductPollingCompleted()){
						boolean isHughesNetAvailable = Utils.isHughesNetAvailable(productResultManager.getProductsMap());
						logger.info("isHughesNet Available="+isHughesNetAvailable);
						if(isHughesNetAvailable){
							newProvidersMap.put(Constants.HUGHESNET_NAME,providersMap.get(Constants.HUGHESNET_NAME).replace("Pending", "Available"));
						}
						else{
							newProvidersMap.put(Constants.HUGHESNET_NAME,providersMap.get(Constants.HUGHESNET_NAME).replace("Pending", "Not Available"));
						}
						obj.put("isHNProductShow", isHughesNetAvailable);
					}
				}else{
					obj.put("isHNProductShow", false);
				}
			}else{
				obj.put("isHNProductShow", isHNProductShow);
				if(isCompleted){
					boolean isHughesNetAvailable = Utils.isHughesNetAvailable(productResultManager.getProductsMap());
					if(!isHughesNetAvailable){
						newProvidersMap.put(Constants.HUGHESNET_NAME,providersMap.get(Constants.HUGHESNET_NAME).replace("Pending", "Not Available"));
					}
					obj.put("isHNProductShow", isHughesNetAvailable);
				}
			}
			//obj.put("isHNProductShow", isHugesNetProductavailable);
			if(newProvidersMap.get(Constants.HUGHESNET_NAME) != null && !providersMap.get(Constants.HUGHESNET_NAME).contains("Pending")){
				newProvidersMap.put(Constants.HUGHESNET_NAME,providersMap.get(Constants.HUGHESNET_NAME).replace("Sell", "Available"));
			}
			
			if(newProvidersMap != null && newProvidersMap.get("ATTV6").contains("Multiple Address")){
				if(newProvidersMap != null && newProvidersMap.get("DirecTV")!= null && Utils.isBlank(isMAMSuccess)){
					newProvidersMap.put("DirecTV", "Available|Multiple Address_15500201#DirecTV");
				}
			}
			if(isPollingCompleted){
				isPending = false;
			}
			
			obj.put("productMap", newProvidersMap);
			obj.put("isSuccessed", isSuccessed);
			obj.put("isPending", isPending);
			obj.put("isCompleted", isCompleted);
			
		} catch (Exception e) {
			logger.error("error_while_getting_real_time_status_bar_values", e);
		}
		return obj.toString();
	}



	private Map < String, String > dispalyRealTimeStatusBasedOnStatusMessage(
			Map < String, String > providersMap) throws Exception {
		Map < String, String > updatedProvidersMap = new HashMap < String, String > ();
		for (Entry < String, String > entry: providersMap.entrySet()) {
			if (entry.getValue().contains("Bulk Address") && entry.getValue().contains("Not Available")) {
				String rtMessage[] = entry.getValue().split("\\|");
				updatedProvidersMap.put(entry.getKey(), rtMessage[1] + "|" + rtMessage[0]);
			} else {
				updatedProvidersMap.put(entry.getKey(), entry.getValue());
			}
		}
		return updatedProvidersMap;
	}



	@RequestMapping(value = "/recommendations")
	protected ModelAndView showRecommendations(HttpServletRequest request,
			HttpServletResponse response) throws UnRecoverableException {
		StopWatch timer = new StopWatch();
		long startTimer = 0;
		try {

			timer.start();
			String categoryName = request.getParameter("CategoryName");
			logger.info("showRecommendations_begin");
			HttpSession session = request.getSession();
			String typeOfService = (String) session.getAttribute("typeOfService");

			Map < String, String > discoveryTransfer = null;
			Map < String, String > discoveryNew = null;

			if (session.getAttribute("gotoRecommondation") == null) {

				String header = (String) request.getHeader("referer");
				//getting parameters from the DiscoveryTransfer 
				if (!Utils.isBlank(typeOfService) && typeOfService.equals("existingService")) {

					String currentTV = request.getParameter("Discovery_Transfer_NonRR_1");
					String currentInternet = request.getParameter("Discovery_Transfer_NonRR_2");
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

					discoveryTransfer = new LinkedHashMap < String, String > ();

					discoveryTransfer.put("Current TV", currentTV);
					discoveryTransfer.put("Current Internet", currentInternet);
					discoveryTransfer.put("Current total monthly bill", monthlyBill);
					discoveryTransfer.put("#TVS", noTVS);
					discoveryTransfer.put("#HD", noHD);
					discoveryTransfer.put("#DVR", noDVRs);
					discoveryTransfer.put("Channels", channels);
					discoveryTransfer.put("Movie Channels", movieChannels);
					discoveryTransfer.put("Internet", internet);
					discoveryTransfer.put("Internet Usage", internetUsage);
					discoveryTransfer.put("#Devices", devices);
					discoveryTransfer.put("Home Phone", homePhone);

					if (session.getAttribute("questionList") == null || (header != null && header.endsWith("/discovery"))) {
						session.setAttribute("questionList", discoveryTransfer);
					}

					session.setAttribute("existingService", discoveryTransfer);
					session.setAttribute("preDiscoveryTransfer", discoveryTransfer);
				}


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

					discoveryNew = new LinkedHashMap < String, String > ();
					if ((session.getAttribute("questionList") != null) && (session.getAttribute("preDiscoveryTransfer") != null)) {
						Map < String, String > preDiscoveryTransfer = (Map < String, String > ) session.getAttribute("preDiscoveryTransfer");
						discoveryNew.put("Current TV", preDiscoveryTransfer.get("Current TV"));
						discoveryNew.put("Current Internet", preDiscoveryTransfer.get("Current Internet"));
						discoveryNew.put("Current total monthly bill", preDiscoveryTransfer.get("Current total monthly bill"));
					}
					discoveryNew.put("#TVS", firstField);
					discoveryNew.put("#HD", secondField);
					discoveryNew.put("#DVR", thirdField);
					discoveryNew.put("Channels", fourthField);
					discoveryNew.put("Movie Channels", fifthField);
					discoveryNew.put("Internet", sixthField);
					discoveryNew.put("Internet Usage", seventhField);
					discoveryNew.put("#Devices", eightthField);
					discoveryNew.put("Home Phone", ninethField);

					if (session.getAttribute("questionList") == null || (header != null && header.endsWith("/discovery"))) {
						session.setAttribute("questionList", discoveryNew);
					}
					session.setAttribute("newService", discoveryNew);
				}
			} else {

				discoveryTransfer = (Map < String, String > ) session.getAttribute("existingService");
				discoveryNew = (Map < String, String > ) session.getAttribute("newService");

				session.removeAttribute("gotoRecommondation");

			}

			Long orderId = (Long) session.getAttribute("orderId");

			SalesCenterVO salesCenterVo = (SalesCenterVO) session.getAttribute("salescontext");
			String agentId = salesCenterVo.getValueByName("Agent");
			startTimer = timer.getTime();
			OrderType order = OrderService.INSTANCE.getOrderByOrderNumber(String.valueOf(orderId), agentId, null, null, false, null);
			logger.info("TimeTakenforsubmitCustomerType=" + (timer.getTime() - startTimer));

			/*
			 * If we want to GUID on Sales Context then last parameter of below method is true otherwise false.
			 */
			Map < String, Map < String, String >> context = SalesDialogueFactory.INSTANCE.updateContextMapWithReferrerAndCallType("Recommendations", salesCenterVo, true);

			startTimer = timer.getTime();
			SalesDialogueVO dialogueVO = DialogServiceUI.INSTANCE.getDialoguesByContext(context);
			logger.info("TimeTakenforgetDialoguesByContext=" + (timer.getTime() - startTimer));
			StringBuilder events = new StringBuilder();

			List < DataField > dataFields = new ArrayList < DataField > ();

			for (Dialogue dialogue: dialogueVO.getDialogueList()) {
				List < DataGroup > dgList = dialogue.getDataGroupList();
				for (DataGroup dGroup: dgList) {
					for (DataField dataField: dGroup.getDataFieldList()) {
						dataFields.add(dataField);
					}
				}
			}

			List < Fieldset > fieldsetList = HtmlFactory.INSTANCE.dialogueToFieldSet(events, dataFields, null);

			for (Fieldset fieldset: fieldsetList) {
				String element = HtmlBuilder.INSTANCE.toString(fieldset);
				element = salesCenterVo.replaceNamesWithValues(element);
				events.append(element);
			}

			List < ProductSummaryVO > details = new ArrayList < ProductSummaryVO > ();
			ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");
			details = (List < ProductSummaryVO > ) productResultManager.getPowerPitchList();
			if (details != null) {
				for (int i = 0; i < details.size(); i++) {
					createJSON(details.get(i), orderId);
				}
			}
			Fieldset buildData = getPowerPitchFieldSetByName(details, request, productResultManager.getSelectedExistingProvidersMap());

			String buildHtmlData = HtmlBuilder.INSTANCE.toString(buildData);

			populateRecIconMap(productResultManager.getProductByIconMap(), request,productResultManager.getProductsMap(),productResultManager.getSelectedExistingProvidersMap());

			ModelAndView mav = new ModelAndView();
			mav.addObject("buildHtmlData", escapeSpecialCharacters(buildHtmlData));


			List < ProductVOJSON > productVOJSONList = new ArrayList < ProductVOJSON > ();
			if (details != null) {
				boolean isDish = false;
				int totalProductsList = 0;
				if (!CollectionUtils.isEmpty(details)) {
					for (ProductSummaryVO vo: details) {
						if (vo.isDishProduct()) {
							isDish = true;
						}
					}
					if (isDish) {
						totalProductsList = details.size();
					} else {
						if (details.size() > 3) {
							totalProductsList = details.size() - 1;
						} else {
							totalProductsList = details.size();
						}
					}
					for (int i = 0; i < totalProductsList; i++) {
						createJSON(details.get(i), orderId);
						productVOJSONList.add(buildProductJSONVO(details.get(i), productResultManager, categoryName, i));
					}
				}
			}
			mav.addObject("productVOJSONList", SalesUtilsFactory.INSTANCE.toJson(productVOJSONList));
			//mav.addObject("details",details);
			mav.addObject("dialogue", escapeSpecialCharacters(events.toString()));
			mav.addObject("order", order);
			mav.addObject("lineItemList", CartLineItemFactory.INSTANCE.sortLineItemBasedOnStatus(order));
			mav.addObject("promoMap", CartLineItemFactory.INSTANCE.hasLineItemPromtions(order));
			mav.addObject("title", "recommendations.header");
			Long addressId = (Long) session.getAttribute("addressId");
			mav.addObject("addressId", addressId);
			mav.addObject("address", SalesUtil.INSTANCE.getAddress(order.getCustomerInformation().getCustomer(),
					com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString()));
			mav.addObject("salesDiscoveryNew", discoveryNew);
			mav.addObject("salesDiscoveryTransfer", discoveryTransfer);
			mav.addObject("isUtilityOfferCompleted", SalesUtilsFactory.INSTANCE.isUtilityOfferCompleted(order));
			mav.addObject("isSaverOfferCompleted", (Boolean) session.getAttribute("displayButton"));
			mav.addObject("CKOCompletedLineItems", LineItemUtil.prepareCKOCompletedLinItemsListFromOrder(order));

			mav.setViewName("sales.recommendations");
			logger.info("showRecommendations_end");
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new UnRecoverableException(e.getMessage());
		} finally {
			timer.stop();
		}
	}

	private void populateRecIconMap(
			Map < String, List < ProductSummaryVO >> productByIconMap, HttpServletRequest request, boolean isFromWebFlow) {
		HttpSession session = request.getSession();
		String path = request.getContextPath() + "/salescenter/recommendations/";
		String pathCategory = request.getContextPath() + "/salescenter/recommendationsbyCategory/";
		if (isFromWebFlow) {
			String flowExecutionUrl = request.getParameter("flowExecutionUrl");
			path = flowExecutionUrl + "&_eventId=recommendationsEvent&CategoryName=";
			pathCategory = flowExecutionUrl + "&_eventId=recommendationsByCategoryEvent&CategoryName=";
		}

		Map < String, Object > recIconMap = new HashMap < String, Object > ();
		String[] productArr = {
				"TRIPLE_PLAY",
				"DOUBLE_PLAY",
				"VIDEO",
				"PHONE",
				"INTERNET",
				"HOMESECURITY",
				"ELECTRICITY",
				"WATER",
				"APPLIANCEPROTECTION",
				"NATURALGAS",
				"WASTEREMOVAL",
				"ASIS_PLAN",
				"OFFERS",
				"BUNDLES",
				"MIXEDBUNDLES"
		};
		logger.info("productArr_length=" + productArr.length);
		for (int k = 0; k < productArr.length; k++) {
			logger.info("productArr[k]=" + productArr[k]);
			if (productByIconMap.get(productArr[k]) != null && productByIconMap.get(productArr[k]).size() > 0) {
				if (productArr[k].equalsIgnoreCase("PP")) {
					recIconMap.put(productArr[k], path + productArr[k]);
				} else {
					recIconMap.put(productArr[k], pathCategory + productArr[k]);
				}
				if (productArr[k].equalsIgnoreCase("HOMESECURITY")) {
					session.setAttribute("confirmedSecurity", null);
				}
			} else {
				recIconMap.put(productArr[k], "#");
				if (productArr[k].equalsIgnoreCase("HOMESECURITY")) {
					session.setAttribute("confirmedSecurity", "true");
				}
			}
		}
		request.getSession().setAttribute("recIconMap", recIconMap);
	}

	private void populateRecIconMap(
			Map < String, List < ProductSummaryVO >> productByIconMap, HttpServletRequest request,Map<String, List<ProductVOJSON>> productsMap,HashMap<String, String> selectedExistingProvidersMap) {
		HttpSession session = request.getSession();
		String path = null;
		path = "${flowExecutionUrl}&_eventId=recommendationsEvent&CategoryName=";
		path = request.getContextPath() + "/salescenter/recommendationsbyCategory/";
		String transferAuthenticatedProviders  = ConfigRepo.getString("*.transfer_authenticated_providers");
		Map<String,String> transferAuthenticatedProvidersMap = new HashMap<String, String>();
		List<String> transferAuthenticatedProviderExtIdsList = new ArrayList<String>();
		List<String> transferAuthProviderExtIdsList = new ArrayList<String>();
		List<String> transferAuthOtherProviderExtIdsList = new ArrayList<String>();
		if (!Utils.isBlank(transferAuthenticatedProviders)) {
			String transferAuthenticatedProvidersList[] = transferAuthenticatedProviders.split("\\|");
			for (String providerIdWithName: transferAuthenticatedProvidersList) {
				String providerValues[] = providerIdWithName.split("=");
				transferAuthenticatedProvidersMap.put(providerValues[1], providerValues[0]);
			}
			for (Entry<String,String> entry : transferAuthenticatedProvidersMap.entrySet()){
				if(selectedExistingProvidersMap!= null&&selectedExistingProvidersMap.get(entry.getKey())!=null){
					transferAuthenticatedProviderExtIdsList.add(entry.getValue());
				}
				else{
					transferAuthOtherProviderExtIdsList.add(entry.getValue());
				}
				transferAuthProviderExtIdsList.add(entry.getValue());
			}
		}
		
		Map < String, Object > recIconMap = new HashMap < String, Object > ();
		String[] productArr = {
				"TRIPLE_PLAY",
				"DOUBLE_PLAY",
				"VIDEO",
				"PHONE",
				"INTERNET",
				"HOMESECURITY",
				"ELECTRICITY",
				"WATER",
				"APPLIANCEPROTECTION",
				"NATURALGAS",
				"WASTEREMOVAL",
				"ASIS_PLAN",
				"OFFERS",
				"BUNDLES",
				"MIXEDBUNDLES"
		};
		logger.info("productArr_length=" + productArr.length);
		for (int k = 0; k < productArr.length; k++) {
			boolean isOtherProduct = false;
			boolean isASISProducts = false;
			boolean otherASISProduct = false;
			if(productsMap.get(productArr[k]) != null && productsMap.get(productArr[k]).size()>0 ){
				List<ProductVOJSON> products= productsMap.get(productArr[k]);
				if(selectedExistingProvidersMap != null && transferAuthenticatedProviderExtIdsList != null && transferAuthenticatedProviderExtIdsList.size()>0){
					for( ProductVOJSON productVO:products){
						try{
							if(productVO.getPairedProduct() != null){
								ProductVOJSON pairedProduct = productVO.getPairedProductVOJSON();
								if(pairedProduct != null && pairedProduct.getProviderExtId() != null && !transferAuthenticatedProviderExtIdsList.contains(pairedProduct.getProviderExtId())){
									if(productVO.getProviderExtId() != null && !transferAuthenticatedProviderExtIdsList.contains(productVO.getProviderExtId())){
										isOtherProduct = true;
									}
								}
							}else if(productVO.getProviderExtId() != null && !transferAuthenticatedProviderExtIdsList.contains(productVO.getProviderExtId())){
								if(productArr[k].equalsIgnoreCase("ASIS_PLAN")){
									if(transferAuthOtherProviderExtIdsList == null || !transferAuthOtherProviderExtIdsList.contains(productVO.getProviderExtId())){
										isOtherProduct = true;
									}
								}
								else{
									isOtherProduct = true;
								}
							}
							if(isOtherProduct){
								break;
							}
						}catch(Exception e){
							logger.info("the error occured while converting json to java object ="+e.getMessage());
						}
					}
				}else {
					for(ProductVOJSON productVo:products){
						if(productArr[k].equalsIgnoreCase("ASIS_PLAN")){
							if(transferAuthenticatedProviderExtIdsList!= null && transferAuthenticatedProviderExtIdsList.contains(productVo.getProviderExtId())){
								isASISProducts = true;
							}
							else if(transferAuthOtherProviderExtIdsList== null || !transferAuthOtherProviderExtIdsList.contains(productVo.getProviderExtId())){
								otherASISProduct =true;
							}
							if(isASISProducts && otherASISProduct){
								break;
							}
						}
					}

				}

			}
			if(!isOtherProduct && selectedExistingProvidersMap != null && transferAuthenticatedProviderExtIdsList != null && transferAuthenticatedProviderExtIdsList.size()>0 && !productArr[k].equalsIgnoreCase("ASIS_PLAN")){
				recIconMap.put(productArr[k], "#");
			}else if(isASISProducts && !otherASISProduct && productArr[k].equalsIgnoreCase("ASIS_PLAN")){
				recIconMap.put(productArr[k], "#");
			}else if (productByIconMap.get(productArr[k]) != null && productByIconMap.get(productArr[k]).size() > 0) {
				recIconMap.put(productArr[k], path + productArr[k]);
				if (productArr[k].equalsIgnoreCase("HOMESECURITY")) {
					session.setAttribute("confirmedSecurity", null);
				}
			} else {
				recIconMap.put(productArr[k], "#");
				if (productArr[k].equalsIgnoreCase("HOMESECURITY")) {
					session.setAttribute("confirmedSecurity", "true");
				}
			}
		}
		request.getSession().setAttribute("recIconMap", recIconMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/recommendations/refreshProductData")
	protected @ResponseBody String refreshProductData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long orderId = (Long) request.getSession().getAttribute("orderId");
		ProductResultsManager productResultManager = (ProductResultsManager) request.getSession().getAttribute("productResultManager");
		List < ProductSummaryVO > details = new ArrayList < ProductSummaryVO > ();
		details = (List < ProductSummaryVO > ) productResultManager.getPowerPitchList();
		//populateRecIconMap(productResultManager.getProductByIconMap(), request);
		if (details != null) {
			for (int i = 0; i < details.size(); i++) {
				createJSON(details.get(i), orderId);
			}
		}
		boolean isWebFlow = false;
		if (Constants.YES.equalsIgnoreCase(request.getParameter("dynamicFlow"))) {
			isWebFlow = true;
		}

		Fieldset buildData = getPowerPitchFieldSetByName(details, request, productResultManager.getSelectedExistingProvidersMap());
		String buildHtmlData = HtmlBuilder.INSTANCE.toString(buildData);
		populateRecIconMap(productResultManager.getProductByIconMap(), request, isWebFlow);
		Map < String, Object > recIconMap = (Map < String, Object > ) request.getSession().getAttribute("recIconMap");
		String path = isWebFlow ? request.getParameter("flowExecutionUrl") : request.getContextPath();
		Fieldset buildData1 = HtmlFactory.INSTANCE.buildProductIcons(recIconMap, request, path);

		String buildHtmlData1 = HtmlBuilder.INSTANCE.toString(buildData1);
		JSONObject obj = new JSONObject();
		try {
			obj.put("value", escapeSpecialCharacters(buildHtmlData));
			obj.put("productIconS", escapeSpecialCharacters(buildHtmlData1));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return obj.toString();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/recommendations/refreshProductDataByCategory")
	protected @ResponseBody String refreshProductDataByCategory(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String categoryName = request.getParameter("categoryName");
		List < ProductSummaryVO > details = new ArrayList < ProductSummaryVO > ();
		logger.info("categoryName=" + categoryName);
		HttpSession session = request.getSession();
		ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");
		details = (List < ProductSummaryVO > ) productResultManager.getProductByIconMap().get(categoryName.toUpperCase());

		if (categoryName != null && (categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_PI") || categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_PV") || categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_VI"))) {
			details = (List < ProductSummaryVO > ) productResultManager.getProductByIconMap().get("DOUBLE_PLAY");
			details = productResultManager.getDoublePlayRenderData(details, categoryName.toUpperCase());
		}
		Long orderId = (Long) request.getSession().getAttribute("orderId");
		if (details != null) {
			for (int i = 0; i < details.size(); i++) {
				createJSON(details.get(i), orderId);
			}
		}
		JSONObject obj = null;
		if (details != null) {
			boolean isWebFlow = false;
			if (Constants.YES.equalsIgnoreCase(request.getParameter("dynamicFlow"))) {
				isWebFlow = true;
			}

			Fieldset buildData = getProductFieldSetByCategory(details, request, productResultManager.getSelectedExistingProvidersMap());
			String buildHtmlData = HtmlBuilder.INSTANCE.toString(buildData);
			populateRecIconMap(productResultManager.getProductByIconMap(), request, isWebFlow);
			Map < String, Object > recIconMap = (Map < String, Object > ) request.getSession().getAttribute("recIconMap");
			logger.info("recIconMap=" + recIconMap);

			String path = isWebFlow ? request.getParameter("flowExecutionUrl") : request.getContextPath();
			Fieldset buildData1 = HtmlFactory.INSTANCE.buildProductIcons(recIconMap, request, path);

			String buildHtmlData1 = HtmlBuilder.INSTANCE.toString(buildData1);

			obj = new JSONObject();
			try {
				obj.put("value", escapeSpecialCharacters(buildHtmlData));
				obj.put("productIconS", escapeSpecialCharacters(buildHtmlData1));
			} catch (JSONException e) {
				e.printStackTrace();
				throw new Exception("JSON Exception: " + e.getMessage());
			}

		}
		return obj == null ? "" : obj.toString();


	}

	public void createJSON(ProductSummaryVO detailsVO, Long orderId) throws Exception {
		DecimalFormat format = new DecimalFormat("#0.00");
		JSONObject prodJson = new JSONObject();
		try {
			prodJson.put("orderId", orderId);
			prodJson.put("partnerExternalId", escapeSpecialCharacters(detailsVO.getProviderExternalId()));
			if (detailsVO.getParentExternalId() != null) {
				prodJson.put("img_id", escapeSpecialCharacters(detailsVO.getParentExternalId()));
			} else {
				prodJson.put("img_id", escapeSpecialCharacters(detailsVO.getProviderExternalId()));
			}
			prodJson.put("productExernalId", escapeSpecialCharacters(detailsVO.getExternalId()));
			prodJson.put("productname", escapeSpecialCharacters(detailsVO.getName()));
			prodJson.put("isPromotion", false);
			prodJson.put("providerName", escapeSpecialCharacters(detailsVO.getProviderName()));
			prodJson.put("non_recuring", ("$" + format.format(detailsVO.getBaseNonRecurringPrice())));
			prodJson.put("recuring", ("$" + format.format(detailsVO.getBaseRecurringPrice())));
			prodJson.put("capabilityMap", detailsVO.getCapabilityMap());
			prodJson.put("productType", detailsVO.getProductType());
			prodJson.put("providerSourceBaseType", detailsVO.getSource());
			String hybrisShell = "false";
			String noEmail = "false";
			String noOrderStatus = "false";
			Map < String, String > metadata = detailsVO.getMetadata();
			for (Map.Entry < String, String > mapEntry: metadata.entrySet()) {
				if (mapEntry.getValue().equalsIgnoreCase(Constants.HYBRIS_SHELL) || mapEntry.getValue().equalsIgnoreCase(Constants.ATG_LINK)) {
					hybrisShell = "true";
					//break;
				} else if (mapEntry.getValue().equalsIgnoreCase(Constants.NO_EMAIL)) {
					noEmail = "true";
				} else if (mapEntry.getValue().equalsIgnoreCase(Constants.NO_ORDER_STATUS)) {
					noOrderStatus = "true";
				}
			}
			List < String > promotionMetadata = detailsVO.getPromotionMetaDataList();
			if (promotionMetadata != null) {
				if (promotionMetadata.contains(Constants.HYBRIS_SHELL) || promotionMetadata.contains(Constants.ATG_LINK)) {
					hybrisShell = "true";
				}
				if (promotionMetadata.contains(Constants.NO_EMAIL)) {
					noEmail = "true";
				}
				if (promotionMetadata.contains(Constants.NO_ORDER_STATUS)) {
					noOrderStatus = "true";
				}
			}
			prodJson.put("hybrisShell", hybrisShell);
			prodJson.put("noEmail", noEmail);
			prodJson.put("noOrderStatus", noOrderStatus);
			/*
			 * adding TPV and Warm Transfer to JSON
			 */
			prodJson.put(Constants.TPV_PRODUCT, detailsVO.isTPVProduct());
			prodJson.put(Constants.WARM_TRANSFER_PRODUCT, detailsVO.isWarmTransferProduct());
		} catch (JSONException e) {
			e.printStackTrace();
			logger.error(e);
			throw new Exception("JSONException: " + e.getMessage());
		}
		detailsVO.setProdJson(prodJson);
	}

	public String escapeSpecialCharacters(String str) {
		if (str != null) {
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

	@RequestMapping(value = "/recommendationsbyCategory/{categoryName}")
	protected ModelAndView showRecommendations(@PathVariable String categoryName, HttpServletRequest request,
			HttpServletResponse response) throws UnRecoverableException {
		StopWatch timer = new StopWatch();
		long startTimer = 0;
		try {
			timer.start();
			logger.info("showRecommendationsbyCategory_begin");
			HttpSession session = request.getSession();
			session.setAttribute("categoryName", categoryName);
			//		if (categoryName.equalsIgnoreCase("HOMESECURITY")) {
			//			session.setAttribute("confirmedSecurity", "true");
			//		}
			Long orderId = (Long) session.getAttribute("orderId");
			SalesCenterVO salesCenterVo = (SalesCenterVO) session.getAttribute("salescontext");
			String agentId = salesCenterVo.getValueByName("Agent");
			startTimer = timer.getTime();
			OrderType order = OrderService.INSTANCE.getOrderByOrderNumber(String.valueOf(orderId), agentId, null, null, false, null);
			logger.info("TimeTakenforgetOrderByOrderNumber=" + (timer.getTime() - startTimer));
			List < ProductSummaryVO > details = new ArrayList < ProductSummaryVO > ();
			logger.info("categoryName=" + categoryName);
			ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");
			details = (List < ProductSummaryVO > ) productResultManager.getProductByIconMap().get(categoryName.toUpperCase());
			if (categoryName.toUpperCase() != null && (categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_PI") || categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_PV") || categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_VI"))) {
				details = (List < ProductSummaryVO > ) productResultManager.getProductByIconMap().get("DOUBLE_PLAY");
				details = productResultManager.getDoublePlayRenderData(details, categoryName.toUpperCase());
			}
			logger.info("Total_Records=" + details);
			ModelAndView mav = new ModelAndView();

			if (details != null) {
				for (int i = 0; i < details.size(); i++) {
					createJSON(details.get(i), orderId);
				}
				Fieldset buildData = getProductFieldSetByCategory(details, request, productResultManager.getSelectedExistingProvidersMap());

				String buildHtmlData = HtmlBuilder.INSTANCE.toString(buildData);
				mav.addObject("buildHtmlData", escapeSpecialCharacters(buildHtmlData));
			}
			Long addressId = (Long) session.getAttribute("addressId");
			mav.addObject("addressId", addressId);
			mav.addObject("address", SalesUtil.INSTANCE.getAddress(order.getCustomerInformation().getCustomer(),
					com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString()));
			mav.addObject("details", details);
			mav.addObject("order", order);
			mav.addObject("lineItemList", CartLineItemFactory.INSTANCE.sortLineItemBasedOnStatus(order));
			mav.addObject("title", "recommendations." + categoryName);
			mav.addObject("categoryName", categoryName);
			mav.addObject("promoMap", CartLineItemFactory.INSTANCE.hasLineItemPromtions(order));
			//mav.addObject("dialogue" , events.toString());
			mav.setViewName("sales.recommendationsbyCategory");
			mav.addObject("CKOCompletedLineItems", LineItemUtil.prepareCKOCompletedLinItemsListFromOrder(order));
			mav.addObject("totalPoints", Utils.getTotalPoints(order, session));
			logger.info("showRecommendationsbyCategory_end");
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new UnRecoverableException(e.getMessage());
		} finally {
			timer.stop();
		}
	}

	public Map < String, Map < String, String >> createSalesContext(String dwellingType, HttpServletRequest request) {
		Map < String, Map < String, String >> salesContextData = new HashMap < String, Map < String, String >> ();
		SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
		Map < String, String > context = new HashMap < String, String > ();
		context.put("context.mode", "production");
		salesContextData.put("context", context);

		Map < String, String > orderSource = new HashMap < String, String > ();
		orderSource.put("orderSource.source", "123");
		orderSource.put("orderSource.channel", "1");
		orderSource.put("orderSource.referrer", salesCenterVo.getValueByName("DT Partner"));

		salesContextData.put("orderSource", orderSource);

		Map < String, String > consumer = new HashMap < String, String > ();
		consumer.put("consumer.creditScore", Constants.CONSUMER_CREDITSCORE);
		salesContextData.put("consumer", consumer);

		if (dwellingType != null && dwellingType.equalsIgnoreCase("Apartment")) {
			dwellingType = "apartment";
		} else {
			dwellingType = "house";
		}
		Map < String, String > dwelling = new HashMap < String, String > ();
		dwelling.put("dwelling.dwellingType", dwellingType);
		dwelling.put("dwelling.stateOrProvince", salesCenterVo.getValueByName("state"));
		salesContextData.put("dwelling", dwelling);

		Map < String, String > salesFlow = new HashMap < String, String > ();
		salesFlow.put("salesFlow.dialogueType", "core");
		salesFlow.put("salesFlow.forceNonConfirm", "false");
		salesContextData.put("salesFlow", salesFlow);

		Map < String, String > agent = new HashMap < String, String > ();
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

	private Fieldset getPowerPitchFieldSetByName(List < ProductSummaryVO > productList, HttpServletRequest httpRequest, HashMap < String, String > selectedProviders) {
		Fieldset fieldSet = oFactory.createFieldset();
		boolean isDish = false;
		int totalProductsList = 0;
		if (!CollectionUtils.isEmpty(productList)) {
			for (ProductSummaryVO vo: productList) {
				if (vo.isDishProduct()) {
					isDish = true;
				}
			}
			if (isDish) {
				totalProductsList = productList.size();
			} else {
				if (productList.size() > 3) {
					totalProductsList = productList.size() - 1;
				} else {
					totalProductsList = productList.size();
				}
			}
			for (int index = 0, counter = 0; index < totalProductsList; index++, counter++) {
				ProductSummaryVO product = productList.get(index);
				boolean isDishProduct = product.isDishProduct();
				//Crating Tabs Main <div class="tabs">
				Div tabs = oFactory.createDiv();
				tabs.getClazz().add("tabs");

				Div tabcont = oFactory.createDiv();
				tabcont.getClazz().add("tab_contain");

				//Div content = oFactory.createDiv();		
				Div content = oFactory.createDiv();

				String tabtext = "";

				if (counter == 0) {
					content.getClazz().add("content green_border2");
					tabtext = "Power Pitch";
				} else if (counter == 1) {
					content.getClazz().add("content blue_border2");
					tabtext = "Primary Alternative";
				} else {
					content.getClazz().add("content grey_border2");
					tabtext = "Secondary Alternative";
				}

				// Creating div data
				Div tdiv = renderProductData(index, product, httpRequest, tabtext, selectedProviders);
				content.getContent().add(tdiv);

				if (isDishProduct && index != productList.size() - 1) {
					Hr hr = oFactory.createHr();
					content.getContent().add(hr);

					index++;
					Div tempdiv = renderProductData(index, productList.get(index), httpRequest, "", selectedProviders);
					content.getContent().add(tempdiv);
				}

				tabcont.getContent().add(content);
				tabs.getContent().add(tabcont);
				fieldSet.getContent().add(tabs);
			}
		}

		return fieldSet;
	}

	private Fieldset getProductFieldSetByCategory(List < ProductSummaryVO > productList, HttpServletRequest httpRequest, HashMap < String, String > selectedProviders) {
		Fieldset fieldSet = oFactory.createFieldset();
		if (!CollectionUtils.isEmpty(productList)) {
			for (int index = 0, counter = 0; index < productList.size(); index++, counter++) {
				ProductSummaryVO product = productList.get(index);
				//Crating Tabs Main <div class="tabs">
				Div tabs = oFactory.createDiv();
				tabs.getClazz().add("tabs");

				Div tabcont = oFactory.createDiv();
				tabcont.getClazz().add("tab_contain");

				//Div content = oFactory.createDiv();		
				Div content = oFactory.createDiv();
				content.getClazz().add("content grey_border2");

				String tabtext = "";

				// Creating paragraph data
				Div cdiv = renderProductData(index, product, httpRequest, tabtext, selectedProviders);
				content.getContent().add(cdiv);

				tabcont.getContent().add(content);
				tabs.getContent().add(tabcont);
				fieldSet.getContent().add(tabs);
			}
		}

		return fieldSet;
	}

	private Div renderProductData(int index, ProductSummaryVO product, HttpServletRequest httpRequest, String tabtext, HashMap < String, String > selectedProviders) {
		String strContextPath = httpRequest.getContextPath();
		String providersImageLocation = (String) httpRequest.getSession().getAttribute("providersImageLocation");
		boolean isValidIconsForProducts = false;
		boolean isChannelLineupProvider = false;
		boolean isVideoCapable = false;
		String providersData = ConfigRepo.getString("*.channelLineupProviders") == null ? null : ConfigRepo.getString("*.channelLineupProviders");
		logger.info("providersData=" + providersData);
		String providerExtId = product.getParentExternalId() != null ? product.getParentExternalId() : product.getProviderExternalId();
		String providerName = escapeSpecialCharacters(product.getProviderName());

		if (!Utils.isBlank(providersData)) {
			String providersList[] = providersData.split("\\|");
			for (String providerIdWithName: providersList) {
				String providerValues[] = providerIdWithName.split("=");
				if (providerValues[0].trim().equalsIgnoreCase(providerExtId)) {
					isChannelLineupProvider = true;
					if (Utils.isBlank(providerName)) {
						providerName = providerValues[1];
					}
					break;
				}
			}
		}

		if (providerName.equalsIgnoreCase("ATTSTI")) {
			providerName = "AT&T";
		} else if (providerName.equalsIgnoreCase("DISH Network")) {
			providerName = "Dish";
		}

		//Crating Tabs Main <div class="tabs">
		Div tdiv = oFactory.createDiv();
		tdiv.setStyle("float:left");

		Div row1 = oFactory.createDiv();

		row1.getClazz().add("row1");

		if (tabtext != "") {
			Div tab = oFactory.createDiv();
			tab.getClazz().add("tab");
			tab.getContent().add(tabtext);
			row1.getContent().add(tab);
		}

		Div productTitle = oFactory.createDiv();
		productTitle.getClazz().add("productTitle");
		productTitle.getContent().add(product.getName()); //Real Product Title Should add
		row1.getContent().add(productTitle);

		//Creating Cell for Product Icons <span class="cell productIcons">
		Div productIcons = oFactory.createDiv();
		productIcons.getClazz().add("productIcons");

		Img pIcon = null;

		if (isProductVideoCapable(product)) {
			//Creating Image for Product Icons
			pIcon = oFactory.createImg();
			pIcon.setSrc(strContextPath + "/images/images_new/tv.png");
			pIcon.setWidth("19");
			pIcon.setHeight("18");
			productIcons.getContent().add(pIcon);
			isValidIconsForProducts = true;
			isVideoCapable = true;
		}

		if (isProductPhoneCapable(product)) {
			pIcon = oFactory.createImg();
			pIcon.setSrc(strContextPath + "/images/images_new/phone.png");
			pIcon.setWidth("19");
			pIcon.setHeight("18");
			productIcons.getContent().add(pIcon);
			isValidIconsForProducts = true;
		}

		if (isProductInternetCapable(product)) {
			pIcon = oFactory.createImg();
			pIcon.setSrc(strContextPath + "/images/images_new/internet.png");
			pIcon.setWidth("23");
			pIcon.setHeight("18");
			productIcons.getContent().add(pIcon);
			isValidIconsForProducts = true;
		}

		if (Constants.APPLIANCEPROTECTION.equalsIgnoreCase(product.getProductCategory())) {
			pIcon = oFactory.createImg();
			pIcon.setSrc(strContextPath + "/images/images_new/ApplianceProtection.png");
			pIcon.setWidth("33");
			pIcon.setHeight("28");
			productIcons.getContent().add(pIcon);
			isValidIconsForProducts = true;

		}

		if (isValidIconsForProducts) {
			row1.getContent().add(productIcons);
		}

		boolean isExistingCustomer = false;
		if (!selectedProviders.isEmpty() && selectedProviders.containsValue(product.getProviderExternalId())) {
			isExistingCustomer = true;
		}

		Div productBaseInfo = oFactory.createDiv();
		productBaseInfo.getClazz().add("productBaseInfo");

		Div delBasePrice = oFactory.createDiv();
		delBasePrice.getClazz().add("baseprc");
		Span deleteSpan = oFactory.createSpan();
		Del del = oFactory.createDel();

		Div promoPrice = oFactory.createDiv();
		promoPrice.getClazz().add("promoprc");

		Input iPriceHidden = oFactory.createInput();
		iPriceHidden.setType("hidden");
		iPriceHidden.setId("price_" + product.getProviderExternalId() + "_" + product.getExternalId());

		String promotionPrice = null;
		if (product.getPromotionType() != null && product.getPromotionType().equalsIgnoreCase("baseMonthlyDiscount")) {
			if (product.getPromotionPriceValueType() != null && product.getPromotionPriceValueType().equalsIgnoreCase("absolute")) {
				promotionPrice = product.getPromotionPrice().toString();
			} else if (product.getPromotionPriceValueType() != null && product.getPromotionPriceValueType().equalsIgnoreCase("relative")) {
				promotionPrice = String.valueOf(product.getBaseRecurringPrice() - product.getPromotionPrice());
			}
		}
		/*else if (product.getPromotionType() != null &&  product.getPromotionType().equalsIgnoreCase("informationalPromotion")){
  	promotionPrice = String.valueOf(product.getBaseRecurringPrice());
  }*/
		DecimalFormat format = new DecimalFormat("#0.00");
		if (promotionPrice != null && product.getBaseRecurringPrice() != null && Double.parseDouble(promotionPrice) < product.getBaseRecurringPrice() && !product.getProviderExternalId().equals("4353598")) {

			if (isExistingCustomer) {
				iPriceHidden.setValue(product.getBaseRecurringPrice().toString());
				productBaseInfo.getContent().add(iPriceHidden);
				Span span = oFactory.createSpan();
				span.setStyle("color:red;");
				span.getContent().add("TBD");
				delBasePrice.getContent().add("BASE PRICE: "); //Add base recurring Price 
				delBasePrice.getContent().add(span);
				productBaseInfo.getContent().add(delBasePrice);
			} else {
				delBasePrice.getContent().add("BASE PRICE: ");
				del.getContent().add("$" + format.format(product.getBaseRecurringPrice())); //Add Promotion Price real data
				deleteSpan.getContent().add(del);
				delBasePrice.getContent().add(deleteSpan);
				productBaseInfo.getContent().add(delBasePrice);

				promoPrice.getContent().add("$" + format.format(Double.parseDouble(promotionPrice))); //Add Promotion Price real data
				productBaseInfo.getContent().add(promoPrice);

				iPriceHidden.setValue(product.getBaseRecurringPrice().toString());
				productBaseInfo.getContent().add(iPriceHidden);
			}

		} else {
			if (isExistingCustomer) {
				iPriceHidden.setValue(product.getBaseRecurringPrice().toString());
				productBaseInfo.getContent().add(iPriceHidden);
				Span span = oFactory.createSpan();
				span.setStyle("color:red;");
				span.getContent().add("TBD");
				delBasePrice.getContent().add("BASE PRICE: "); //Add base recurring Price 
				delBasePrice.getContent().add(span);
				productBaseInfo.getContent().add(delBasePrice);
			} else {
				iPriceHidden.setValue((product.getBaseRecurringPrice().toString()));
				productBaseInfo.getContent().add(iPriceHidden);
				delBasePrice.getContent().add("BASE PRICE: $" + format.format(product.getBaseRecurringPrice())); //Add base recurring Price 
				productBaseInfo.getContent().add(delBasePrice);
			}

		}

		tdiv.getContent().add(row1);

		Div row2 = oFactory.createDiv();
		row2.getClazz().add("row2");

		//Create Form under Cell Span <form class="pp_form">
		Form pp_form = oFactory.createForm();
		pp_form.setAction("#");
		//pp_form.setClazz("pp_form");

		Div ppformcontent = oFactory.createDiv();
		ppformcontent.getClazz().add("pp_form");

		//Create Fields to Cell form <input type="checkbox" id="c1" name="cc" class="pp_checkbox" /> <input type="button" name="View Details" value="View Details" class="ViewDetailsBtn" />
		Input iChkBox = oFactory.createInput();
		iChkBox.setType("checkbox");
		iChkBox.setName("cbox" + index);
		iChkBox.setId("cbox" + index);
		iChkBox.setClazz("pp_checkbox");
		iChkBox.setValue("input_" + product.getProviderExternalId() + "_" + product.getExternalId());
		ppformcontent.getContent().add(iChkBox);

		Input iChkBoxHidden = oFactory.createInput();
		iChkBoxHidden.setType("hidden");
		iChkBoxHidden.setId("hidden_product_" + product.getProviderExternalId() + "_" + product.getExternalId());
		iChkBoxHidden.setValue(product.getProdJson().toString());
		ppformcontent.getContent().add(iChkBoxHidden);

		Input iInstallPriceHidden = oFactory.createInput();
		iInstallPriceHidden.setType("hidden");
		iInstallPriceHidden.setId("installPrice_" + product.getProviderExternalId() + "_" + product.getExternalId());
		if (product.getPromotionType() != null && product.getPromotionCode().equals("Free Installation")) {
			iInstallPriceHidden.setValue("0.00");
		} else {
			iInstallPriceHidden.setValue(product.getBaseNonRecurringPrice().toString());
		}
		ppformcontent.getContent().add(iInstallPriceHidden);
		if (isChannelLineupProvider && isVideoCapable) {
			Input viewChannelsBtn = oFactory.createInput();
			viewChannelsBtn.setType("button");
			viewChannelsBtn.setName("");
			viewChannelsBtn.setId("product_" + product.getProviderExternalId() + "_" + product.getExternalId());
			viewChannelsBtn.setClazz("ViewChannelsBtn");
			viewChannelsBtn.setValue("View Channels");
			viewChannelsBtn.setOnclick("displayChannelLineUpData('" + providerExtId + "','" + providerName + "')");
			ppformcontent.getContent().add(viewChannelsBtn);
		}
		// Create View Details buttons <input type="button" name="View Details" value="View Details" class="ViewDetailsBtn" />
		Input ViewDetailsBtn = oFactory.createInput();
		ViewDetailsBtn.setType("button");
		ViewDetailsBtn.setName("");
		ViewDetailsBtn.setId("product_" + product.getProviderExternalId() + "_" + product.getExternalId());
		ViewDetailsBtn.setClazz("ViewDetailsBtn");
		ViewDetailsBtn.setValue("View Details");
		ppformcontent.getContent().add(ViewDetailsBtn);

		Input iPriceHidden1 = oFactory.createInput();
		iPriceHidden1.setType("hidden");
		iPriceHidden1.setId("aidvalue");
		iPriceHidden1.setName("aidvalue");
		iPriceHidden1.setValue(product.getExternalId() + "," + product.getName() + "," + product.getProviderExternalId() + "," + product.getProductType());
		ppformcontent.getContent().add(iPriceHidden1);

		// Create span for provider logo <span class="cell productLogopp"><img src="" alt=""/></span>
		Span productLogopp = oFactory.createSpan();
		productLogopp.getClazz().add("cell productLogopp");
		//productLogopp.setStyle("padding-left:10px;text-align: center;");

		//Creating Image for Product Icons
		Img productLogo = oFactory.createImg();

		if (product.getParentExternalId() != null) {
			productLogo.setSrc(providersImageLocation + product.getParentExternalId() + ".jpg");
			productLogo.setOnerror("this.onerror=null;this.src='" + providersImageLocation + product.getProviderExternalId() + ".jpg'");
		} else {
			productLogo.setSrc(providersImageLocation + product.getProviderExternalId() + ".jpg");
		}

		productLogo.setStyle("border:none;text-align: center;");
		productLogo.setTitle1(product.getPoints() + "|" + product.getProviderExternalId());
		productLogo.setTitle("Points : " + (product.getPoints() == -1 ? "NA" : product.getPoints()) + ", Product External Id : " + product.getExternalId());
		productLogo.setAlt(product.getProviderExternalId());

		productLogopp.getContent().add(productLogo);

		//Create Description <div class="productDescription">$19.99 per month for 12 months with a 24 month commitment</div>
		Div productDescription = oFactory.createDiv();
		productDescription.getClazz().add("productDescription");

		if (isExistingCustomer) {
			productDescription.getContent().add(ADVISORYPROMOTION);
		} else {
			String promoDescription = null;
			if (!Constants.ATTV6.equals(product.getProviderExternalId()) && !Constants.ATT.equals(product.getProviderExternalId()) && product.getShortDescription() != null && !(Utils.isBlank(product.getShortDescription())) && !("N/A".equals(product.getShortDescription()))) {
				promoDescription = product.getShortDescription();
				productDescription.getContent().add(promoDescription);
			} else if (Constants.ATTV6.equals(product.getProviderExternalId()) && (Constants.ATT_BUILD_PRODUCT_EXID.equals(product.getExternalId()) || Constants.ATT_BUILD_PRODUCT_EXID_TRANSFER.equals(product.getExternalId()))) {
				productDescription.getContent().add(product.getDescriptiveInfoValue());
			} else {
				if (product.getPromotionDescription() != null) {
					if (!Utils.isBlank(product.getPromotionDescription()) && product.getPromotionDescription().length() >= 307) {
						promoDescription = product.getPromotionDescription().substring(0, 302);
						promoDescription = promoDescription + " ...";
					} else {
						promoDescription = product.getPromotionDescription();
					}
					productDescription.getContent().add(promoDescription); //Real Product Description Should add
				} else {
					//cellDesc.getContent().add("No Promotions");//Real Product Description Should add
					productDescription.getContent().add(""); // 87230 Ticket
				}
			}

		}

		pp_form.getContent().add(ppformcontent);
		row2.getContent().add(pp_form);
		row2.getContent().add(productLogopp);
		row2.getContent().add(productDescription);


		tdiv.getContent().add(row2);

		Div row3 = oFactory.createDiv();
		row3.getClazz().add("productPriceContent");
		// removed base non recurring price for the ticket 86324
		row3.getContent().add(productBaseInfo);

		Div mainDiv = oFactory.createDiv();
		mainDiv.getClazz().add("content_cont");
		mainDiv.getContent().add(tdiv);
		mainDiv.getContent().add(row3);

		return mainDiv;
	}

	private boolean isProductVideoCapable(ProductSummaryVO product) {
		if ((product.getCapabilityMap().get("iptv") != null ||
				product.getCapabilityMap().get("ipDslamIptv") != null ||
				product.getCapabilityMap().get("analogCable") != null ||
				product.getCapabilityMap().get("digitalCable") != null ||
				product.getCapabilityMap().get("satellite") != null)) {
			return true;
		}
		return false;
	}

	private boolean isProductInternetCapable(ProductSummaryVO product) {
		if ((product.getCapabilityMap().get("fiberDataDownSpeed") != null || //internet conditions
				product.getCapabilityMap().get("ipDslamDataDownSpeed") != null ||
				product.getCapabilityMap().get("wiredDataDownSpeed") != null ||
				product.getCapabilityMap().get("dialUpInternet") != null)) {
			return true;
		}
		return false;
	}

	private boolean isProductPhoneCapable(ProductSummaryVO product) {
		if ((product.getCapabilityMap().get("voip") != null || //phone conditions
				product.getCapabilityMap().get("ipDslamVoip") != null ||
				product.getCapabilityMap().get("localPhone") != null ||
				product.getCapabilityMap().get("longDistancePhone") != null ||
				product.getCapabilityMap().get("wirelessPhone") != null)) {
			return true;
		}
		return false;
	}
	@RequestMapping(value = "/recommendations/compareProducts")
	protected @ResponseBody String compareProducts(HttpServletRequest request) throws Exception {
		StopWatch timer = new StopWatch();
		long startTimer = 0;
		timer.start();
		try {
			String selectedProducts = request.getParameter("selectedProducts");
			JSONObject index = new JSONObject(selectedProducts);
			JSONArray selectedArray = index.getJSONArray("lineItems");
			List < ProductSummaryVO > listOfProductMdo = new ArrayList < ProductSummaryVO > ();
			SalesContext salesContext = SalesContextFactory.INSTANCE.getSalesContext(createSalesContext("House", request));
			ProductResultsManager productResultManager = (ProductResultsManager) request.getSession().getAttribute("productResultManager");
			for (int i = 0; i < selectedArray.length(); i++) {
				String jsonString = (String) selectedArray.get(i);
				JSONObject feedback = new JSONObject(jsonString);
				String productExernalId = feedback.getString("productExernalId");
				String providerExernalId = feedback.getString("providerExernalId");
				Map < String, ProductSummaryVO > resultmap = new HashMap < String, ProductSummaryVO > ();
				/*List<ProductSummaryVO> resultProductSummaryVOList = new ArrayList<ProductSummaryVO>();
			if(categoryName.equals("powerPitch")){
				resultProductSummaryVOList = productResultManager.getPowerPitchList();
			}else{
				resultProductSummaryVOList = productResultManager.getProductByIconMap().get(categoryName);
			}*/

				for (ProductSummaryVO newMdo: productResultManager.getProdSearchVO().getAllProductList()) {
					if (newMdo != null && newMdo.getProviderExternalId().equalsIgnoreCase(providerExernalId) && newMdo.getExternalId().equalsIgnoreCase(productExernalId)) {
						resultmap.put(productExernalId, newMdo);
					}
				}
				String GUID = (String) request.getSession().getAttribute("GUID");
				for (Entry < String, ProductSummaryVO > entry: resultmap.entrySet()) {
					ProductSummaryVO mdo = entry.getValue();
					if (mdo != null && mdo.getExternalId().equalsIgnoreCase(productExernalId) && mdo.getProviderExternalId().equalsIgnoreCase(providerExernalId)) {
						startTimer = timer.getTime();
						ProductInfoType product = ProductService.INSTANCE.getProduct(providerExernalId, productExernalId, GUID, salesContext);
						logger.info("TimeTakenforgetProduct=" + (timer.getTime() - startTimer));
						List < FeatureType > features = null;
						List < FeatureGroupType > featureGroup = null;
						if (product != null && product.getProductDetails() != null) {
							features = product.getProductDetails().getFeature();
							featureGroup = product.getProductDetails().getFeatureGroup();
						}
						if (!Utils.isEmpty(features)) {
							for (FeatureType feature: features) {
								logger.info("feature_values " + feature.getExternalId());
								if (feature != null && feature.getIncluded() != null && feature.getExternalId() != null && feature.getExternalId().equalsIgnoreCase("NUM_CHANNEL_AVAIL")) {
									if (feature.getDataConstraints() != null && feature.getDataConstraints().getIntegerConstraint() != null) {
										if (feature.getDataConstraints().getIntegerConstraint().getValue() != null) {
											mdo.setNoOfChannels(String.valueOf(feature.getDataConstraints().getIntegerConstraint().getValue()));
										}
									}
								}
								if (feature != null && feature.getExternalId().equalsIgnoreCase("DOM_LONG_DIST")) {
									if (feature != null && feature.isAvailable() && feature.getDataConstraints() != null && feature.getDataConstraints().getStringConstraint() != null) {
										mdo.setLongDistance(feature.getDataConstraints().getStringConstraint().getValue());
									}
								}
								if (feature != null && feature.getExternalId().equalsIgnoreCase("CONN_SPEED")) {
									if (feature != null && feature.getIncluded() != null && feature.getDataConstraints() != null && feature.getDataConstraints().getStringConstraint() != null) {
										mdo.setInternetSpeed(feature.getDataConstraints().getStringConstraint().getValue());
									}
								}
							}
						}
						if (!Utils.isEmpty(featureGroup)) {
							for (FeatureGroupType featureGrp: featureGroup) {
								if (featureGrp != null) {
									if (featureGrp.getType() != null && featureGrp.getExternalId().equalsIgnoreCase("CONN_SPEED")) {
										for (FeatureType feature: featureGrp.getFeature()) {
											if (feature != null && feature.getIncluded() != null && feature.getDataConstraints() != null && feature.getDataConstraints().getStringConstraint() != null) {
												mdo.setInternetSpeed(feature.getDataConstraints().getStringConstraint().getValue());
											}
										}
									}
								}
								if (featureGrp != null) {
									StringBuffer str = new StringBuffer();
									if (featureGrp.getType() != null && featureGrp.getExternalId().equalsIgnoreCase("MODEM_OPTION")) {
										for (FeatureType feature: featureGrp.getFeature()) {
											if (feature != null && feature.isAvailable() && feature.getType() != null) {
												String curValue = feature.getDataConstraints().getStringConstraint().getValue();
												if (!Utils.isBlank(curValue)) {
													if (str.length() == 0) {
														str.append(curValue);
													} else {
														str.append(", ");
														str.append(curValue);
													}
												}
											}
										}
									}
									if (str.toString() != null && str.toString().trim().endsWith(",")) {
										mdo.setModemOptions(str.toString().substring(0, str.toString().length() - 1));
									} else if (str.toString() != null) {
										mdo.setModemOptions(str.toString());
									}
								}
							}
						}
						if (Utils.isBlank(mdo.getNoOfChannels())) {
							mdo.setNoOfChannels("NA");
						}
						if (Utils.isBlank(mdo.getInternetSpeed())) {
							mdo.setInternetSpeed("NA");
						}
						if (Utils.isBlank(mdo.getModemOptions())) {
							mdo.setModemOptions("NA");
						}
						if (Utils.isBlank(mdo.getLongDistance())) {
							mdo.setLongDistance("NA");
						}
						listOfProductMdo.add(mdo);
					}
				}
				//SalesContext salesContext = SalesContextFactory.INSTANCE.getSalesContext(createSalesContext("House",request));
				//ProductInfoType product = ProductService.INSTANCE.getProduct(providerExernalId,productExernalId,GUID,salesContext);
				//listOfProducts.add(product);
			}
			logger.info("listOfProductMdo=" + listOfProductMdo);
			Fieldset compFieldsetList = HtmlFactory.INSTANCE.getCompareFieldSet(listOfProductMdo, request, productResultManager.getSelectedExistingProvidersMap());

			String element = HtmlBuilder.INSTANCE.toString(compFieldsetList);
			JSONObject obj = new JSONObject();
			obj.put("table", escapeSpecialCharacters(element));
			return obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw e;
		} finally {
			timer.stop();
		}
	}
	@RequestMapping(value = "/recommendations/viewOrderDetails")
	protected @ResponseBody String viewOrderDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StopWatch timer = new StopWatch();
		long startTimer = 0;
		timer.start();
		try {
			String anchor = request.getParameter("aidval");
			String productPointsDisplay = request.getParameter("productPoints");
			JSONObject feedback = new JSONObject(anchor);
			//String[] val = anchor.split(",");
			//String providerExtId = "12228509";//request.getParameter("partnerExternalId");
			//String productExtId = "TW-CHARLOTTE-BUNDLE3"; //request.getParameter("productExernalId");
			String productExtId = feedback.getString("productExernalId"); //request.getParameter("partnerExternalId");
			String providerExtId = feedback.getString("partnerExternalId"); //request.getParameter("productExernalId");
			//String productPointsDisplay = feedback.getString("productPointDisplay"); //request.getParameter("productExernalId");
			//String productName = feedback.getString("productName");
			String GUID = (String) request.getSession().getAttribute("GUID");
			String providerName = "";
			logger.info("providerExtId_Viewdetails=" + providerExtId);
			logger.info("productExtId_Viewdetails=" + productExtId);
			logger.info("productPoints=" + productPointsDisplay);
			//logger.info("productNAme *******************88 " +productName);

			if (productExtId.contains("&")) {
				if (!productExtId.contains("&amp;")) {
					productExtId = productExtId.replace("&", "&amp;");
				}
			}

			SalesContext salesContext = SalesContextFactory.INSTANCE.getSalesContext(createSalesContext("House", request));
			startTimer = timer.getTime();
			ProductInfoType product = ProductService.INSTANCE.getProduct(providerExtId, productExtId, GUID, salesContext);
			logger.info("TimeTakenforgetProduct=" + (timer.getTime() - startTimer));
			final ObjectFactory oFactory = new ObjectFactory();

			List < FeatureType > features = product.getProductDetails().getFeature();
			List < FeatureGroupType > featureGroup = product.getProductDetails().getFeatureGroup();
			List < ProductPromotionType > promotions = product.getProductDetails().getPromotion();
			boolean isDirecTV = isDirecTV(product);
			Fieldset fieldset = oFactory.createFieldset();
			if (features != null) {
				List < FeatureType > featuresNew = new ArrayList < FeatureType > (features);
				boolean hasStringNumChannels = false;
				boolean hasIntNumChannels = false;
				for (FeatureType ft: features) {
					if (ft != null && "NUM_CHANNEL_AVAIL_STRING".equalsIgnoreCase(ft.getExternalId()) && ft.isAvailable()) {
						hasStringNumChannels = true;
					} else if (ft != null && "NUM_CHANNEL_AVAIL".equalsIgnoreCase(ft.getExternalId()) && ft.isAvailable()) {
						hasIntNumChannels = true;
					}
				}

				if (hasStringNumChannels && hasIntNumChannels) {
					int i = 0;
					for (FeatureType ft: features) {
						if (ft != null && "NUM_CHANNEL_AVAIL".equalsIgnoreCase(ft.getExternalId()) && ft.isAvailable()) {
							featuresNew.remove(i);
						}
						i++;
					}
				}

				if (!hasStringNumChannels && !hasIntNumChannels) {
					if (product.getProduct().getCapabilityList() != null && product.getProduct().getCapabilityList().getCapability() != null && !product.getProduct().getCapabilityList().getCapability().isEmpty()) {
						for (CapabilityType capability: product.getProduct().getCapabilityList().getCapability()) {
							if (capability.getName().equalsIgnoreCase("iptv") || capability.getName().equalsIgnoreCase("ipDslamIptv") || capability.getName().equalsIgnoreCase("analogCable") || capability.getName().equalsIgnoreCase("digitalCable") || capability.getName().equalsIgnoreCase("satellite")) {
								FeatureType defaultFeature = new FeatureType();
								defaultFeature.setExternalId("DEFAULT_NUM_CHANNEL_AVAIL");
								defaultFeature.setDescription("Number of Available Channels");
								defaultFeature.setType("Number of Available Channels");
								defaultFeature.setAvailable(true);
								featuresNew.add(defaultFeature);
							}
						}
					}
				}

				if (product.getProduct() != null && product.getProduct().getProvider() != null && !Utils.isBlank(product.getProduct().getProvider().getName())) {
					providerName = product.getProduct().getProvider().getName();
				}
				if (product.getProduct().getProvider() != null &&
						product.getProduct().getProvider().getParent() != null &&
						product.getProduct().getProvider().getParent().getExternalId() != null) {
					fieldset = HtmlFactory.INSTANCE.getFeatureFieldSet(featuresNew, featureGroup, product.getProduct().getProvider().getParent().getExternalId(), providerName, isDirecTV);
				} else {
					fieldset = HtmlFactory.INSTANCE.getFeatureFieldSet(featuresNew, featureGroup, providerExtId, providerName, isDirecTV);
				}
			} else {
				if (product.getProduct().getCapabilityList() != null && product.getProduct().getCapabilityList().getCapability() != null && !product.getProduct().getCapabilityList().getCapability().isEmpty()) {
					for (CapabilityType capability: product.getProduct().getCapabilityList().getCapability()) {
						if (capability.getName().equalsIgnoreCase("iptv") ||
								capability.getName().equalsIgnoreCase("ipDslamIptv") ||
								capability.getName().equalsIgnoreCase("analogCable") ||
								capability.getName().equalsIgnoreCase("digitalCable") ||
								capability.getName().equalsIgnoreCase("satellite")) {
							FeatureType defaultFeature = new FeatureType();
							defaultFeature.setExternalId("DEFAULT_NUM_CHANNEL_AVAIL");
							defaultFeature.setDescription("Number of Available Channels");
							defaultFeature.setType("Number of Available Channels");
							defaultFeature.setAvailable(true);

							List < FeatureType > featuresNew = new ArrayList < FeatureType > ();
							featuresNew.add(defaultFeature);

							if (product.getProduct().getProvider() != null &&
									product.getProduct().getProvider().getParent() != null &&
									product.getProduct().getProvider().getParent().getExternalId() != null) {
								fieldset = HtmlFactory.INSTANCE.getFeatureFieldSet(featuresNew, featureGroup, product.getProduct().getProvider().getParent().getExternalId(), providerName, isDirecTV);
							} else {
								fieldset = HtmlFactory.INSTANCE.getFeatureFieldSet(featuresNew, featureGroup, providerExtId, providerName, isDirecTV);
							}
						}
					}
				}
			}

			HashMap < String, Map < String, String >> productFeatureNameMap = null;
			if (request.getSession().getAttribute("productFeatureNameMap") != null) {
				productFeatureNameMap = (HashMap < String, Map < String, String >> ) request.getSession().getAttribute("productFeatureNameMap");
				Map < String, String > fetureMap = new HashMap < String, String > ();
				if (features != null) {
					for (FeatureType feature: features) {
						setDescriptionToFetureMap(feature, fetureMap);
					}
				}
				if (featureGroup != null) {
					for (FeatureGroupType feature: featureGroup) {
						if (feature != null && feature.getFeature() != null) {
							for (FeatureType featuregrp: feature.getFeature()) {
								setDescriptionToFetureMap(featuregrp, fetureMap);
							}
						}
					}
				}
				if (promotions != null) {
					for (ProductPromotionType productPromotionType: promotions) {
						fetureMap.put(productPromotionType.getExternalId(), productPromotionType.getDescription());
					}
				}

				productFeatureNameMap.put(feedback.getString("productExernalId"), fetureMap);
				request.getSession().setAttribute("productFeatureNameMap", productFeatureNameMap);
			} else {
				productFeatureNameMap = new HashMap < String, Map < String, String >> ();
				Map < String, String > fetureMap = new HashMap < String, String > ();
				if (features != null) {
					for (FeatureType feature: features) {
						setDescriptionToFetureMap(feature, fetureMap);
					}
				}
				if (featureGroup != null) {
					for (FeatureGroupType feature: featureGroup) {
						if (feature != null && feature.getFeature() != null) {
							for (FeatureType featuregrp: feature.getFeature()) {
								setDescriptionToFetureMap(featuregrp, fetureMap);
							}
						}
					}
				}
				if (promotions != null) {
					for (ProductPromotionType productPromotionType: promotions) {
						fetureMap.put(productPromotionType.getExternalId(), productPromotionType.getDescription());
					}
				}

				productFeatureNameMap.put(feedback.getString("productExernalId"), fetureMap);
				request.getSession().setAttribute("productFeatureNameMap", productFeatureNameMap);
			}
			ProductResultsManager productResultManager = (ProductResultsManager) request.getSession().getAttribute("productResultManager");

			boolean isExistingCustomer = false;
			if (!productResultManager.getSelectedExistingProvidersMap().isEmpty() && !isDirecTV) {
				if (productResultManager.getSelectedExistingProvidersMap().containsValue(providerExtId)) {
					isExistingCustomer = true;
				}
			}
			String transferAuthenticatedProviders  = ConfigRepo.getString("*.transfer_authenticated_providers");
			Map<String,String> transferAuthenticatedProvidersMap = new HashMap<String, String>();
			List<String> transferAuthenticatedProviderExtIdsList = new ArrayList<String>();
			if (!Utils.isBlank(transferAuthenticatedProviders)) {
				String transferAuthenticatedProvidersList[] = transferAuthenticatedProviders.split("\\|");
				for (String providerIdWithName: transferAuthenticatedProvidersList) {
					String providerValues[] = providerIdWithName.split("=");
					if(!providerValues[0].equals("32416075")){
						transferAuthenticatedProvidersMap.put(providerValues[0], providerValues[1]);
					}
				}
			}
			if(transferAuthenticatedProvidersMap!= null && transferAuthenticatedProvidersMap.get(providerExtId)!= null){
				isExistingCustomer = false;
			}
			Fieldset promotionsFieldsetList = null;

			promotions = sortPromotionsBasedOnAdvertise(promotions);

			if (isExistingCustomer && !providerExtId.equalsIgnoreCase("32416075")) {
				promotionsFieldsetList = HtmlFactory.INSTANCE.getPromotionsFieldSet(promotions, providerExtId, ADVISORYPROMOTION);
			} else {
				promotionsFieldsetList = HtmlFactory.INSTANCE.getPromotionsFieldSet(promotions, providerExtId, "");
			}


			String element = HtmlBuilder.INSTANCE.toString(fieldset);
			String element1 = HtmlBuilder.INSTANCE.toString(promotionsFieldsetList);

			String priceElement = getPriceDescription(product);
			//String priceElement = HtmlBuilder.INSTANCE.toString(priceSet);

			String nameElement = getProductName(product);
			//String nameElement = HtmlBuilder.INSTANCE.toString(productName);

			Fieldset marketingHighlights = getMarketingInfo(product);

			String marketing = HtmlBuilder.INSTANCE.toString(marketingHighlights);

			String longDescription = "";
			String promoSummary = "";
			SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
			if (product.getProductDetails() != null && !CollectionUtils.isEmpty(product.getProductDetails().getDescriptiveInfo())) {
				for (DescriptiveInfoType infoType: product.getProductDetails().getDescriptiveInfo()) {
					if (infoType.getType() != null && infoType.getType().equalsIgnoreCase("longDescription")) {
						String result = infoType.getValue();
						if (!Utils.isBlank(result) && result.contains("[Channels]")) {
							int index = result.indexOf("[Channels]");
							result = result.substring(0, index);
						}
						//for electric AM-3029
						if (!Utils.isBlank(result) && result.contains("{businessParty.name}")) {

							if (Utils.isBlank(GUID)) {
								GUID = "123456";
							}
							List < String > businessPartyId = new ArrayList < String > ();
							if (product.getProduct() != null && product.getProduct().getProvider() != null && !Utils.isBlank(product.getProduct().getProvider().getExternalId())) {
								businessPartyId.add(product.getProduct().getProvider().getExternalId());
								DetailsRequestResponse drr = DetailService.INSTANCE.getAllDetails(businessPartyId, GUID);
								if (drr != null && !(drr.equals(""))) {
									if (drr.getResponse().getDetailResultElement() != null && drr.getResponse().getDetailResultElement().get(0) != null) {
										DetailResultType detailResultType = drr.getResponse().getDetailResultElement().get(0);
										BusinessPartyDetailResultType businessParty = detailResultType.getBusinessPartyResult();
										if (businessParty != null && !Utils.isBlank(businessParty.getPhoneNumForOrdering())) {
											result = result.replace("{businessParty.salesNumber}", businessParty.getPhoneNumForOrdering());
										}
										if (businessParty != null && !Utils.isBlank(businessParty.getPhoneNumForCustomerCare())) {
											result = result.replace("{businessParty.callbackNumber}", businessParty.getPhoneNumForCustomerCare());
										}
										if (businessParty != null && !Utils.isBlank(businessParty.getUrlForOrdering())) {
											result = result.replace("{businessParty.address}", businessParty.getUrlForOrdering());
										}
									}
								}
							}

							if (product.getProduct() != null && product.getProduct().getProvider() != null && !Utils.isBlank(product.getProduct().getProvider().getName())) {
								result = result.replace("{businessParty.name}", product.getProduct().getProvider().getName());
							}
						}
						longDescription = result;
					}
				}
			}
			if(product.getProduct().getProvider()!= null && product.getProduct().getProvider().getExternalId()!= null && Constants.COMCAST.equals(product.getProduct().getProvider().getExternalId()) && !Utils.isBlank(Utils.getViewDetailsPromoSummary(product))){
				promoSummary = Utils.getViewDetailsPromoSummary(product);
			}
			JSONObject obj = new JSONObject();
			try {
				if (product.getProductDetails().getMetaData() != null 
				&& product.getProductDetails().getMetaData().getMetaData() != null 
					&& product.getProductDetails().getMetaData().getMetaData().size() > 0){
					List<String> mDataList = product.getProductDetails().getMetaData().getMetaData();
					for (String mdata : mDataList) {
						if(mdata.indexOf('=') > -1){
							String[] nameValuePair = mdata.split("=");
							if (nameValuePair.length == 2){
								try{
									if (nameValuePair[0].equalsIgnoreCase("DISPLAY_BASE_PRICE")){
										obj.put("displayBasePrice", Double.valueOf(nameValuePair[1]));
									}
									else if (nameValuePair[0].equalsIgnoreCase("DISPLAY_PROMO_PRICE")){
										obj.put("displayPromotionPrice", Double.valueOf(nameValuePair[1]));
									}
								}
								catch(Exception e){
			                        logger.info("Invalid display price "+e.getMessage());
								}
							}
						}
					}
				}
				obj.put("features", escapeSpecialCharacters(element));
				obj.put("promotions", escapeSpecialCharacters(element1));
				if (product.getProduct().getProvider().getParent() != null && product.getProduct().getProvider().getParent().getExternalId() != null) {
					obj.put("detailsImage", product.getProduct().getProvider().getParent().getExternalId());
					obj.put("onErrorImage", product.getProduct().getProvider().getExternalId());
				} else {
					obj.put("detailsImage", product.getProduct().getProvider().getExternalId());
					obj.put("onErrorImage", product.getProduct().getProvider().getExternalId());
				}


				if (isExistingCustomer) {
					obj.put("isExistingCustomer", "true");
				} else {
					obj.put("isExistingCustomer", "false");
				}

				obj.put("isValidProductToAddCart", "yes");

				if (product.getProductDetails() != null && product.getProductDetails().getMetaData() != null && product.getProductDetails().getMetaData().getMetaData() != null && !product.getProductDetails().getMetaData().getMetaData().isEmpty()) {
					for (String metadataName: product.getProductDetails().getMetaData().getMetaData()) {
						logger.info("metadataName=" + metadataName);
						if ("WWGE-NONPROVISIONABLE".equalsIgnoreCase(metadataName.trim())) {
							obj.put("isValidProductToAddCart", "no");
							break;
						}
					}
				}
				Map < String, Double > energyTier = new HashMap < String, Double > ();
				if (product.getProduct().getPriceInfo().getEnergyPriceInfo() != null && product.getProduct().getPriceInfo().getEnergyPriceInfo().getRate() != null && !product.getProduct().getPriceInfo().getEnergyPriceInfo().getRate().isEmpty()) {
					for (Rate rate: product.getProduct().getPriceInfo().getEnergyPriceInfo().getRate()) {
						energyTier.put(rate.getTier(), rate.getValue());
					}
					if (energyTier.get(Constants.ENERGY_RATE) != null) {
						obj.put("usageRate", "$" + energyTier.get(Constants.ENERGY_RATE));
					} else {
						for (Entry < String, Double > map: energyTier.entrySet()) {
							obj.put("usageRate", "$" + map.getValue());
							break;
						}
					}
				}
				if (product.getProduct().getPriceInfo().getEnergyPriceInfo() != null && product.getProduct().getPriceInfo().getEnergyPriceInfo().getEnergyUnit() != null) {
					obj.put("usageUnitName", "/" + product.getProduct().getPriceInfo().getEnergyPriceInfo().getEnergyUnit().value());
				}
				if (isDirecTV) {
					obj.put("detailsImage", Constants.ATT_DIRECTV);
					obj.put("isExistingCustomer", "false");
				}
				obj.put("offerType", product.getOfferType() != null ? product.getOfferType().value() : "");
				obj.put("name", escapeSpecialCharacters(StringEscapeUtils.escapeHtml(nameElement)));
				obj.put("productExID", product.getExternalId());
				obj.put("longDescription", escapeSpecialCharacters(StringEscapeUtils.escapeHtml(longDescription)));
				logger.info("promoSummary"+promoSummary);
				obj.put("promoSummary", escapeSpecialCharacters(StringEscapeUtils.escapeHtml(promoSummary)));
				obj.put("prices", priceElement);
				obj.put("marketing", escapeSpecialCharacters(marketing));
				obj.put("providerExtId", providerExtId);
				obj.put("productPointDisplay", productPointsDisplay);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
				throw e;
			}
			return obj.toString();
		} finally {
			timer.stop();
		}
	}

	@RequestMapping(value = "/recommendations/viewSyntheticBdDetails")
	protected @ResponseBody
	String viewSyntheticBdDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("viewMixedBundleDetails_begin_in_SalesPowerPitchController");
		StopWatch timer = new StopWatch();
		long startTimer = 0;
		timer.start();
		try {
			String anchor = request.getParameter("aidval");
			JSONArray SyntheticBdfeedback = new JSONArray(anchor);
			JSONArray SyntheticBdArray = new JSONArray();

			for (int k = 0; k < 2; k++) {

				JSONObject feedback = SyntheticBdfeedback.getJSONObject(k);

				String productExtId = feedback.getString("productExernalId"); // request.getParameter("partnerExternalId");
				String providerExtId = feedback.getString("partnerExternalId"); // request.getParameter("productExernalId");
				String GUID = (String) request.getSession()
						.getAttribute("GUID");
				String providerName = "";
				logger.info("viewMixedBundleDetails providerExtId=" + providerExtId + " productExtId="+productExtId);
				// logger.info("productNAme *******************88 "
				// +productName);
				Double displayBasePrice = null;
				Double displayPromotionPrice = null;
				if (productExtId.contains("&")) {
					if (!productExtId.contains("&amp;")) {
						productExtId = productExtId.replace("&", "&amp;");
					}
				}

				SalesContext salesContext = SalesContextFactory.INSTANCE
						.getSalesContext(createSalesContext("House", request));
				startTimer = timer.getTime();
				ProductInfoType product = ProductService.INSTANCE.getProduct(
						providerExtId, productExtId, GUID, salesContext);
				logger.info("TimeTakenforgetProduct=" + (timer.getTime() - startTimer));
				final ObjectFactory oFactory = new ObjectFactory();

				List < FeatureType > features = product.getProductDetails()
						.getFeature();
				List < FeatureGroupType > featureGroup = product
						.getProductDetails().getFeatureGroup();
				List < ProductPromotionType > promotions = product
						.getProductDetails().getPromotion();
				boolean isDirecTV = isDirecTV(product);
				Fieldset fieldset = oFactory.createFieldset();
				if (features != null) {
					List < FeatureType > featuresNew = new ArrayList < FeatureType > (
							features);
					boolean hasStringNumChannels = false;
					boolean hasIntNumChannels = false;
					for (FeatureType ft: features) {
						if (ft != null && "NUM_CHANNEL_AVAIL_STRING"
								.equalsIgnoreCase(ft.getExternalId()) && ft.isAvailable()) {
							hasStringNumChannels = true;
						} else if (ft != null && "NUM_CHANNEL_AVAIL".equalsIgnoreCase(ft
								.getExternalId()) && ft.isAvailable()) {
							hasIntNumChannels = true;
						}
					}

					if (hasStringNumChannels && hasIntNumChannels) {
						int i = 0;
						for (FeatureType ft: features) {
							if (ft != null && "NUM_CHANNEL_AVAIL".equalsIgnoreCase(ft
									.getExternalId()) && ft.isAvailable()) {
								featuresNew.remove(i);
							}
							i++;
						}
					}

					if (!hasStringNumChannels && !hasIntNumChannels) {
						if (product.getProduct().getCapabilityList() != null && product.getProduct().getCapabilityList()
								.getCapability() != null && !product.getProduct().getCapabilityList()
								.getCapability().isEmpty()) {
							for (CapabilityType capability: product
									.getProduct().getCapabilityList()
									.getCapability()) {
								if (capability.getName().equalsIgnoreCase(
										"iptv") || capability
										.getName()
										.equalsIgnoreCase("ipDslamIptv") || capability
										.getName()
										.equalsIgnoreCase("analogCable") || capability.getName()
										.equalsIgnoreCase(
												"digitalCable") || capability.getName()
										.equalsIgnoreCase("satellite")) {
									FeatureType defaultFeature = new FeatureType();
									defaultFeature
									.setExternalId("DEFAULT_NUM_CHANNEL_AVAIL");
									defaultFeature
									.setDescription("Number of Available Channels");
									defaultFeature
									.setType("Number of Available Channels");
									defaultFeature.setAvailable(true);
									featuresNew.add(defaultFeature);
								}
							}
						}
					}

					if (product.getProduct() != null && product.getProduct().getProvider() != null && !Utils.isBlank(product.getProduct()
							.getProvider().getName())) {
						providerName = product.getProduct().getProvider()
								.getName();
					}
					if (product.getProduct().getProvider() != null && product.getProduct().getProvider().getParent() != null && product.getProduct().getProvider().getParent()
							.getExternalId() != null) {
						fieldset = HtmlFactory.INSTANCE.getFeatureFieldSet(
								featuresNew, featureGroup, product.getProduct()
								.getProvider().getParent()
								.getExternalId(), providerName,
								isDirecTV);
					} else {
						fieldset = HtmlFactory.INSTANCE.getFeatureFieldSet(
								featuresNew, featureGroup, providerExtId,
								providerName, isDirecTV);
					}
				} else {
					if (product.getProduct().getCapabilityList() != null && product.getProduct().getCapabilityList()
							.getCapability() != null && !product.getProduct().getCapabilityList()
							.getCapability().isEmpty()) {
						for (CapabilityType capability: product.getProduct()
								.getCapabilityList().getCapability()) {
							if (capability.getName().equalsIgnoreCase("iptv") || capability.getName().equalsIgnoreCase(
									"ipDslamIptv") || capability.getName().equalsIgnoreCase(
											"analogCable") || capability.getName().equalsIgnoreCase(
													"digitalCable") || capability.getName().equalsIgnoreCase(
															"satellite")) {
								FeatureType defaultFeature = new FeatureType();
								defaultFeature
								.setExternalId("DEFAULT_NUM_CHANNEL_AVAIL");
								defaultFeature
								.setDescription("Number of Available Channels");
								defaultFeature
								.setType("Number of Available Channels");
								defaultFeature.setAvailable(true);

								List < FeatureType > featuresNew = new ArrayList < FeatureType > ();
								featuresNew.add(defaultFeature);

								if (product.getProduct().getProvider() != null && product.getProduct().getProvider()
										.getParent() != null && product.getProduct().getProvider()
										.getParent().getExternalId() != null) {
									fieldset = HtmlFactory.INSTANCE
											.getFeatureFieldSet(featuresNew,
													featureGroup, product
													.getProduct()
													.getProvider()
													.getParent()
													.getExternalId(),
													providerName, isDirecTV);
								} else {
									fieldset = HtmlFactory.INSTANCE
											.getFeatureFieldSet(featuresNew,
													featureGroup,
													providerExtId,
													providerName, isDirecTV);
								}
							}
						}
					}
				}

				HashMap < String, Map < String, String >> productFeatureNameMap = null;
				if (request.getSession().getAttribute("productFeatureNameMap") != null) {
					productFeatureNameMap = (HashMap < String, Map < String, String >> ) request
							.getSession().getAttribute("productFeatureNameMap");
					Map < String, String > fetureMap = new HashMap < String, String > ();
					if (features != null) {
						for (FeatureType feature: features) {
							setDescriptionToFetureMap(feature, fetureMap);
						}
					}
					if (featureGroup != null) {
						for (FeatureGroupType feature: featureGroup) {
							if (feature != null && feature.getFeature() != null) {
								for (FeatureType featuregrp: feature
										.getFeature()) {
									setDescriptionToFetureMap(featuregrp,
											fetureMap);
								}
							}
						}
					}
					if (promotions != null) {
						for (ProductPromotionType productPromotionType: promotions) {
							fetureMap.put(productPromotionType.getExternalId(),
									productPromotionType.getDescription());
						}
					}

					productFeatureNameMap.put(feedback
							.getString("productExernalId"), fetureMap);
					request.getSession().setAttribute("productFeatureNameMap",
							productFeatureNameMap);
				} else {
					productFeatureNameMap = new HashMap < String, Map < String, String >> ();
					Map < String, String > fetureMap = new HashMap < String, String > ();
					if (features != null) {
						for (FeatureType feature: features) {
							setDescriptionToFetureMap(feature, fetureMap);
						}
					}
					if (featureGroup != null) {
						for (FeatureGroupType feature: featureGroup) {
							if (feature != null && feature.getFeature() != null) {
								for (FeatureType featuregrp: feature
										.getFeature()) {
									setDescriptionToFetureMap(featuregrp,
											fetureMap);
								}
							}
						}
					}
					if (promotions != null) {
						for (ProductPromotionType productPromotionType: promotions) {
							fetureMap.put(productPromotionType.getExternalId(),
									productPromotionType.getDescription());
						}
					}

					productFeatureNameMap.put(feedback
							.getString("productExernalId"), fetureMap);
					request.getSession().setAttribute("productFeatureNameMap",
							productFeatureNameMap);
				}
				ProductResultsManager productResultManager = (ProductResultsManager) request
						.getSession().getAttribute("productResultManager");

				boolean isExistingCustomer = false;
				if (!productResultManager.getSelectedExistingProvidersMap()
						.isEmpty() && !isDirecTV) {
					if (productResultManager.getSelectedExistingProvidersMap()
							.containsValue(providerExtId)) {
						isExistingCustomer = true;
					}
				}

				Fieldset promotionsFieldsetList = null;

				promotions = sortPromotionsBasedOnAdvertise(promotions);

				if (isExistingCustomer) {
					promotionsFieldsetList = HtmlFactory.INSTANCE
							.getPromotionsFieldSet(promotions, providerExtId,
									ADVISORYPROMOTION);
				} else {
					promotionsFieldsetList = HtmlFactory.INSTANCE
							.getPromotionsFieldSet(promotions, providerExtId,
									"");
				}

				String element = HtmlBuilder.INSTANCE.toString(fieldset);
				String element1 = HtmlBuilder.INSTANCE
						.toString(promotionsFieldsetList);

				String priceElement = getPriceDescription(product);
				// String priceElement =
				// HtmlBuilder.INSTANCE.toString(priceSet);

				String nameElement = getProductName(product);
				// String nameElement =
				// HtmlBuilder.INSTANCE.toString(productName);

				Fieldset marketingHighlights = getMarketingInfo(product);

				String marketing = HtmlBuilder.INSTANCE
						.toString(marketingHighlights);

				String longDescription = "";
				String promoSummary = "";
				SalesCenterVO salesCenterVo = (SalesCenterVO) request
						.getSession().getAttribute("salescontext");
				if (product.getProductDetails() != null && !CollectionUtils.isEmpty(product.getProductDetails()
						.getDescriptiveInfo())) {
					for (DescriptiveInfoType infoType: product
							.getProductDetails().getDescriptiveInfo()) {
						if (infoType.getType() != null && infoType.getType().equalsIgnoreCase(
								"longDescription")) {
							String result = infoType.getValue();
							if (!Utils.isBlank(result) && result.contains("[Channels]")) {
								int index = result.indexOf("[Channels]");
								result = result.substring(0, index);
							}
							// for electric AM-3029
							if (!Utils.isBlank(result) && result.contains("{businessParty.name}")) {

								if (Utils.isBlank(GUID)) {
									GUID = "123456";
								}
								List < String > businessPartyId = new ArrayList < String > ();
								if (product.getProduct() != null && product.getProduct().getProvider() != null && !Utils.isBlank(product.getProduct()
										.getProvider().getExternalId())) {
									businessPartyId.add(product.getProduct()
											.getProvider().getExternalId());
									DetailsRequestResponse drr = DetailService.INSTANCE
											.getAllDetails(businessPartyId,
													GUID);
									if (drr != null && !(drr.equals(""))) {
										if (drr.getResponse()
												.getDetailResultElement() != null && drr
												.getResponse()
												.getDetailResultElement()
												.get(0) != null) {
											DetailResultType detailResultType = drr
													.getResponse()
													.getDetailResultElement()
													.get(0);
											BusinessPartyDetailResultType businessParty = detailResultType
													.getBusinessPartyResult();
											if (businessParty != null && !Utils
													.isBlank(businessParty
															.getPhoneNumForOrdering())) {
												result = result
														.replace(
																"{businessParty.salesNumber}",
																businessParty
																.getPhoneNumForOrdering());
											}
											if (businessParty != null && !Utils
													.isBlank(businessParty
															.getPhoneNumForCustomerCare())) {
												result = result
														.replace(
																"{businessParty.callbackNumber}",
																businessParty
																.getPhoneNumForCustomerCare());
											}
											if (businessParty != null && !Utils
													.isBlank(businessParty
															.getUrlForOrdering())) {
												result = result
														.replace(
																"{businessParty.address}",
																businessParty
																.getUrlForOrdering());
											}
										}
									}
								}

								if (product.getProduct() != null && product.getProduct().getProvider() != null && !Utils.isBlank(product.getProduct()
										.getProvider().getName())) {
									result = result.replace(
											"{businessParty.name}", product
											.getProduct().getProvider()
											.getName());
								}
							}
							longDescription = result;
						}
					}
				}
				if(product.getProduct().getProvider()!= null && product.getProduct().getProvider().getExternalId()!= null && Constants.COMCAST.equals(product.getProduct().getProvider().getExternalId()) && !Utils.isBlank(Utils.getViewDetailsPromoSummary(product))){
					promoSummary = Utils.getViewDetailsPromoSummary(product);
				}
				JSONObject obj = new JSONObject();
				try {
					obj.put("features", escapeSpecialCharacters(element));
					obj.put("promotions", escapeSpecialCharacters(element1));
					if (product.getProduct().getProvider().getParent() != null && product.getProduct().getProvider().getParent()
							.getExternalId() != null) {
						obj.put("detailsImage", product.getProduct()
								.getProvider().getParent().getExternalId());
						obj.put("onErrorImage", product.getProduct()
								.getProvider().getExternalId());
					} else {
						obj.put("detailsImage", product.getProduct()
								.getProvider().getExternalId());
						obj.put("onErrorImage", product.getProduct()
								.getProvider().getExternalId());
					}

					if (isExistingCustomer) {
						obj.put("isExistingCustomer", "true");
					} else {
						obj.put("isExistingCustomer", "false");
					}

					obj.put("isValidProductToAddCart", "yes");

					if (product.getProductDetails() != null && product.getProductDetails().getMetaData() != null && product.getProductDetails().getMetaData()
							.getMetaData() != null && !product.getProductDetails().getMetaData()
							.getMetaData().isEmpty()) {
						for (String metadataName: product.getProductDetails()
								.getMetaData().getMetaData()) {
							logger.info("metadataName=" + metadataName);
							if ("WWGE-NONPROVISIONABLE"
									.equalsIgnoreCase(metadataName.trim())) {
								obj.put("isValidProductToAddCart", "no");
								break;
							}
						}
					}
					Map < String, Double > energyTier = new HashMap < String, Double > ();
					if (product.getProduct().getPriceInfo()
							.getEnergyPriceInfo() != null && product.getProduct().getPriceInfo()
							.getEnergyPriceInfo().getRate() != null && !product.getProduct().getPriceInfo()
							.getEnergyPriceInfo().getRate().isEmpty()) {
						for (Rate rate: product.getProduct().getPriceInfo()
								.getEnergyPriceInfo().getRate()) {
							energyTier.put(rate.getTier(), rate.getValue());
						}
						if (energyTier.get(Constants.ENERGY_RATE) != null) {
							obj.put("usageRate", "$" + energyTier.get(Constants.ENERGY_RATE));
						} else {
							for (Entry < String, Double > map: energyTier
									.entrySet()) {
								obj.put("usageRate", "$" + map.getValue());
								break;
							}
						}
					}
					if (product.getProduct().getPriceInfo()
							.getEnergyPriceInfo() != null && product.getProduct().getPriceInfo()
							.getEnergyPriceInfo().getEnergyUnit() != null) {
						obj.put("usageUnitName", "/" + product.getProduct().getPriceInfo()
								.getEnergyPriceInfo().getEnergyUnit()
								.value());
					}
					if (isDirecTV) {
						obj.put("detailsImage", Constants.ATT_DIRECTV);
						obj.put("isExistingCustomer", "false");
					}
					String promotionPrice = null;
					/*if (product.getPromotionType() != null && product.getPromotionType().equalsIgnoreCase("baseMonthlyDiscount")) {
     	if (product.getPromotionPriceValueType() != null && product.getPromotionPriceValueType().equalsIgnoreCase("absolute")) {
     		promotionPrice = product.getPromotionPrice().toString();
     	} else if (product.getPromotionPriceValueType() != null && product.getPromotionPriceValueType().equalsIgnoreCase("relative")) {
     		promotionPrice = String.valueOf(product.getBaseRecurringPrice()- product.getPromotionPrice());
     	}
     }*/
					obj.put("offerType",
							product.getOfferType() != null ? product
									.getOfferType().value() : "");
					obj.put("name", escapeSpecialCharacters(StringEscapeUtils
							.escapeHtml(nameElement)));
					obj.put("productExID", product.getExternalId());
					obj.put("longDescription",
							escapeSpecialCharacters(StringEscapeUtils
									.escapeHtml(longDescription)));
					obj.put("promoSummary",
							escapeSpecialCharacters(StringEscapeUtils
									.escapeHtml(promoSummary)));
					
					obj.put("prices", priceElement);
					obj.put("marketing", escapeSpecialCharacters(marketing));
					obj.put("providerExtId", providerExtId);
					if (!Utils.isBlank(productResultManager.getProductPoints(productExtId))) {
						obj.put("productPointDisplay", productResultManager.getProductPoints(productExtId));
						logger.info("productPoints=" + productResultManager.getProductPoints(productExtId));
					}

					if (!Utils.isBlank(product.getProduct().getProvider().getName())) {
						obj.put("providerName", product.getProduct().getProvider().getName());
					} else if (productResultManager.getProviderNamesAndExtIdsMap() != null) {
						obj.put("providerName", productResultManager.getProviderNamesAndExtIdsMap().get(providerExtId));
					}

					logger.info("providerName " + productResultManager.getProviderNamesAndExtIdsMap().get(providerExtId));
					obj.put("providerName", productResultManager.getProviderNamesAndExtIdsMap().get(providerExtId));
					SyntheticBdArray.put(obj);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e);
					throw e;
				}
			}
			return SyntheticBdArray.toString();
		} finally {
			timer.stop();
		}
	}

	private void setDescriptionToFetureMap(FeatureType feature,
			Map < String, String > fetureMap) {

		if (feature != null && feature.getExternalId() != null && (feature.getExternalId().startsWith("RTS:DISH:SSA") || feature.getExternalId().startsWith("RTS:VZ") || feature.getExternalId().startsWith(Constants.RTS_COX))) {
			if (!Utils.isBlank(feature.getDescription())) {
				fetureMap.put(feature.getExternalId(), feature.getDescription());
			} else {
				fetureMap.put(feature.getExternalId(), feature.getType());
			}
		} else {
			fetureMap.put(feature.getExternalId(), feature.getType());
		}
	}

	//Product Name
	private String getProductName(ProductInfoType product) {
		String name = product.getProduct().getName();
		//Fieldset set = oFactory.createFieldset();
		//H1 cellPName = oFactory.createH1();
		//cellPName.getClazz().add("productName");
		//cellPName.getContent().add(name);
		//set.getContent().add(cellPName);
		return name;
	}
	// Price Description
	private String getPriceDescription(ProductInfoType product) {
		Double baseRecurringPrice = product.getProduct().getPriceInfo().getBaseRecurringPrice();
		String promotionCode = "";
		Float promotionPrice = 0.0f;
		DecimalFormat format = new DecimalFormat("#0.00");
		/*
   if (product.getPromotionType() != null &&  product.getPromotionType().equalsIgnoreCase("baseMonthlyDiscount")){
  	if (product.getPromotionPriceValueType() != null && product.getPromotionPriceValueType().equalsIgnoreCase("absolute")){
  		promotionPrice  = product.getPromotionPrice().toString();
  	}else if (product.getPromotionPriceValueType() != null && product.getPromotionPriceValueType().equalsIgnoreCase("relative")){
  		promotionPrice = String.valueOf(product.getBaseRecurringPrice() - product.getPromotionPrice());
  	}
  }
		 */
		if (product.getProductDetails().getPromotion() != null && product.getProductDetails().getPromotion().size() > 0) {
			int i = 0;
			int j = 0;
			for (ProductPromotionType promotion: product.getProductDetails().getPromotion()) {
				if (promotion != null) {
					if (i == 0) {
						if (promotion.getType() != null && promotion.getType().equalsIgnoreCase("baseMonthlyDiscount")) {
							if (promotion.getPriceValueType() != null && promotion.getPriceValueType().equalsIgnoreCase("absolute")) {
								promotionCode = promotion.getPromoCode();
								promotionPrice = promotion.getPriceValue();
							} else if (promotion.getPriceValueType() != null && promotion.getPriceValueType().equalsIgnoreCase("relative")) {
								promotionCode = promotion.getPromoCode();
								promotionPrice = Float.parseFloat(String.valueOf(baseRecurringPrice - Double.valueOf(promotion.getPriceValue())));
							}
						}
					}
				}
				if (promotion.getMetaData() != null) {
					if (!CollectionUtils.isEmpty(promotion.getMetaData().getMetaData())) {
						String strMetaData = promotion.getMetaData().getMetaData().get(0);
						if (strMetaData != null && strMetaData.equals("ADVERTISE")) {
							if (j == 0) {
								if (promotion.getType() != null && promotion.getType().equalsIgnoreCase("baseMonthlyDiscount")) {
									if (promotion.getPriceValueType() != null && promotion.getPriceValueType().equalsIgnoreCase("absolute")) {
										promotionCode = promotion.getPromoCode();
										promotionPrice = promotion.getPriceValue();
									} else if (promotion.getPriceValueType() != null && promotion.getPriceValueType().equalsIgnoreCase("relative")) {
										promotionCode = promotion.getPromoCode();
										promotionPrice = Float.parseFloat(String.valueOf(baseRecurringPrice - Double.valueOf(promotion.getPriceValue())));
									}
								}
							}
							j++;
						}
					}
				}
				i++;
			}

		}
		String pricedata = "";

		if (promotionCode != null && promotionCode != "" && (promotionPrice != null && baseRecurringPrice != null && promotionPrice < baseRecurringPrice)) {
			pricedata = "$" + format.format(promotionPrice); //Add Promotion Price

		} else {
			pricedata = "$" + format.format(baseRecurringPrice); //Add base recurring Price
		}
		return pricedata;

	}

	// Marketing HighLights
	private Fieldset getMarketingInfo(ProductInfoType product) {
		Fieldset set = oFactory.createFieldset();
		Ul ul = oFactory.createUl();
		if (product.getProductDetails() != null) {
			if (product.getProductDetails().getMarketingHighlights() != null) {
				for (String m_list: product.getProductDetails().getMarketingHighlights().getMarketingHighlight()) {
					/*String result = m_list.replaceAll("[&amp;#8482;]","");
     //String marketingList=m_list.replaceAll("&amp;#8482;", "");
     logger.info("MarketingHighlights list::::::::::::"+result);*/
					if (!Utils.isBlank(m_list)) {
						Li list = oFactory.createLi();
						//Span span = oFactory.createSpan();
						list.getContent().add(m_list);

						//span.getClazz().add("productListSpan");
						//list.getContent().add(span);
						ul.getContent().add(list);
						//set.setClazz("productList");						
					}
				}
				set.getContent().add(ul);
			}
		}
		return set;
	}

	@RequestMapping(value = "/recommendationsbyCategory/filtering")
	protected @ResponseBody String showRecommendationsByFiltring(HttpServletRequest request) {
		try {
			return sortingOffersByFiltring(request);
		} catch (Exception e) {
			logger.error("error_occuerd_while_filter_products", e);
			return "";
		}
	}

	/**
	 * Sorting the Promotions based on Advertise
	 * @param promotions
	 * @return promotions
	 * */
	private List < ProductPromotionType > sortPromotionsBasedOnAdvertise(List < ProductPromotionType > promotions) {
		List < ProductPromotionType > returnPromotions = new ArrayList < ProductPromotionType > ();
		List < ProductPromotionType > normalPromotions = new ArrayList < ProductPromotionType > ();
		List < ProductPromotionType > advertisePromotions = new ArrayList < ProductPromotionType > ();
		Map < String, List < String >> advertiseAndIncludePromotionsMap = new HashMap < String, List < String >> ();
		Map < String, ProductPromotionType > promotionsMap = new HashMap < String, ProductPromotionType > ();
		List < ProductPromotionType > advertisePromotionsList = new ArrayList < ProductPromotionType > ();

		boolean isPromotionAdded = false;

		for (ProductPromotionType promotion: promotions) {
			List < String > includedPromotionsList = new ArrayList < String > ();
			isPromotionAdded = false;
			if (promotion.getMetaData() != null) {
				if (promotion.getMetaData().getMetaData() != null && promotion.getMetaData().getMetaData().size() > 0) {
					for (String metaDataVal: promotion.getMetaData()
							.getMetaData()) {
						boolean isAdvertisePromotion = false;
						boolean isIncludePromotion = false;
						String includedPromotionExtId = null;
						if (metaDataVal != null) {
							String includedValues[] = metaDataVal.split("=");
							if (includedValues[0].trim().equalsIgnoreCase("INCLUDES")) {
								isIncludePromotion = true;
								includedPromotionExtId = includedValues[1];
								includedPromotionsList.add(includedPromotionExtId);
							}
							if (metaDataVal.equals("ADVERTISE")) {
								isAdvertisePromotion = true;
							}
						}
						if (isAdvertisePromotion && isIncludePromotion) {
							advertiseAndIncludePromotionsMap.put(promotion.getExternalId(), includedPromotionsList);
						} else if (isAdvertisePromotion) {
							advertisePromotionsList.add(promotion);
						}
					}
				}
				promotionsMap.put(promotion.getExternalId(), promotion);
			}
		}

		if (advertiseAndIncludePromotionsMap != null && advertiseAndIncludePromotionsMap.size() > 0) {
			List < String > addedPromotionsList = new ArrayList < String > ();
			for (Entry < String, List < String >> map: advertiseAndIncludePromotionsMap.entrySet()) {
				advertisePromotions.add(promotionsMap.get(map.getKey()));
				addedPromotionsList.add(map.getKey());
				List < String > includedPromotionsList = map.getValue();
				for (String extId: includedPromotionsList) {
					if (promotionsMap.get(extId) != null) {
						advertisePromotions.add(promotionsMap.get(extId));
						addedPromotionsList.add(extId);
					}
				}
			}
			for (ProductPromotionType promotion: advertisePromotionsList) {
				advertisePromotions.add(promotion);
				addedPromotionsList.add(promotion.getExternalId());
			}
			for (Entry < String, ProductPromotionType > promoMap: promotionsMap.entrySet()) {
				if (!addedPromotionsList.contains(promoMap.getKey())) {
					normalPromotions.add(promoMap.getValue());
				}
			}
		} else {
			for (ProductPromotionType promotion: promotions) {
				isPromotionAdded = false;
				if (promotion.getMetaData() != null) {
					if (promotion.getMetaData().getMetaData() != null && promotion.getMetaData().getMetaData().size() > 0) {
						for (String metaDataVal: promotion.getMetaData()
								.getMetaData()) {
							if (metaDataVal != null && metaDataVal.equals("ADVERTISE")) {
								advertisePromotions.add(promotion);
								isPromotionAdded = true;
								break;
							}
						}
					}
				}
				if (promotion != null && !isPromotionAdded) {
					normalPromotions.add(promotion);
				}
			}
		}
		if (advertisePromotions != null && advertisePromotions.size() > 0) {
			returnPromotions.addAll(advertisePromotions);
			returnPromotions.addAll(normalPromotions);
			return returnPromotions;
		} else {
			promotions = SalesUtilsFactory.INSTANCE.sortPromotions(promotions);
		}
		return promotions;
	}

	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sortingOffersByFiltring ", method = RequestMethod.POST)
	public @ResponseBody String sortingOffersByFiltring(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String categoryName = request.getParameter("categoryName");
		String sortOption = request.getParameter("sortOfferOption");
		try {
			logger.info("sortOption=" + sortOption);
			logger.info("categoryName=" + categoryName);
			logger.info("categoryName_providers=" + request.getParameter("provider"));
			logger.info("categoryName_contractTerm=" + request.getParameter("contractTerm"));
			logger.info("categoryName_channels=" + request.getParameter("channels"));
			logger.info("categoryName_internetSpeed=" + request.getParameter("internetSpeed"));
			logger.info("latino=" + request.getParameter("latino"));
			final ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");
			List < ProductSummaryVO > productVoList = null;
			if (!Utils.isBlank(categoryName)) {
				if (categoryName.toUpperCase() != null && (categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_PI") || categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_PV") || categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_VI"))) {
					productVoList = (List < ProductSummaryVO > ) productResultManager.getProductByIconMap().get(ProductSearchIface.DOUBLE_PLAY);
					productVoList = productResultManager.getDoublePlayRenderData(productVoList, categoryName.toUpperCase());
				} else if (Constants.PowerPitch.equalsIgnoreCase(categoryName)) {
					productVoList = productResultManager.getPowerPitchList();
				} else {
					productVoList = productResultManager.getProductByIconMap().get(categoryName.toUpperCase());
				}
				productVoList = salesServiceImpl.filterProductList(request, productResultManager, productVoList);
			}
			if (!Utils.isBlank(sortOption) && productVoList != null) {
				salesServiceImpl.sortOfferBasedOnOption(sortOption, productVoList, productResultManager);
				Fieldset buildData = getProductFieldSetByCategory(productVoList, request, productResultManager.getSelectedExistingProvidersMap());
				logger.info("sortOffer_results_found_for_" + sortOption);
				return escapeSpecialCharacters(HtmlBuilder.INSTANCE.toString(buildData));
			} else {
				logger.info("sortOffer_results_not_found_for_" + sortOption);
				return "";
			}
		} catch (Exception e) {
			logger.error("error_occured_while_sortingOffersByFiltring", e);
			return "";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/perssistFilterOptions", method = RequestMethod.POST)
	public @ResponseBody String perssitFilterData(HttpServletRequest request) {
		try {
			if (!Utils.isBlank(request.getParameter("category"))) {
				Gson gson = new Gson();
				HttpSession session = request.getSession();
				Map < String, String > reqMap = new HashMap < String, String > ();
				Map < String, Map < String, String >> allReqMap = new HashMap < String, Map < String, String >> ();
				if (!Utils.isBlank((String) session.getAttribute("pesistFilterOptions"))) {
					allReqMap = gson.fromJson((String) session.getAttribute("pesistFilterOptions"), new TypeToken < Map < String, Map < String, String >>> () {}.getType());
				}
				Iterator it = request.getParameterMap().entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry < String, String[] > entry = (Entry < String, String[] > ) it.next();
					reqMap.put(entry.getKey(), entry.getValue()[0]);
				}
				if (!reqMap.isEmpty()) {
					allReqMap.put(request.getParameter("category"), reqMap);
					session.setAttribute("pesistFilterOptions", gson.toJson(allReqMap));
				}
			}
		} catch (Exception e) {
			logger.error("error_getting_while_perssitFilterData", e);
		}
		return "";
	}
	private boolean isDirecTV(ProductInfoType product) {
		if ((!Utils.isBlank(product.getProduct().getProvider().getExternalId()) && Constants.ATTV6.equals(product.getProduct().getProvider().getExternalId())) || (product.getProduct().getProvider().getParent() != null && !Utils.isBlank(product.getProduct().getProvider().getParent().getExternalId()) && Constants.ATTV6.equals(product.getProduct().getProvider().getParent().getExternalId()) && product.getProduct().getCapabilityList() != null && product.getProduct().getCapabilityList().getCapability() != null)) {
			for (CapabilityType capability: product.getProduct().getCapabilityList().getCapability()) {
				if (capability != null && Constants.SATELLITE.equalsIgnoreCase(capability.getName())) {
					return true;
				}
			}
		}
		return false;
	}
	private ProductVOJSON buildProductJSONVO(ProductSummaryVO product, ProductResultsManager productResultManager, String categoryName, int i) {
		ProductVOJSON productVOJSON = new ProductVOJSON();
		String providersData = ConfigRepo.getString("*.channelLineupProviders") == null ? null : ConfigRepo.getString("*.channelLineupProviders");
		String providerExtId = product.getParentExternalId() != null ? product.getParentExternalId() : product.getProviderExternalId();
		String providerName = escapeSpecialCharacters(product.getProviderName());
		logger.info("providersData=" + providersData);
		productVOJSON.setProviderExtId(product.getProviderExternalId());
		productVOJSON.setProductType(product.getProductType());
		/*if(i == 0){
  	productVOJSON.setTabTexClass("content green_border2");
  	productVOJSON.setTabTex("Power Pitch");
  }else if(i == 1){
  	productVOJSON.setTabTexClass("content blue_border2");
  	productVOJSON.setTabTex("Primary Alternative");
  }else{
  	productVOJSON.setTabTexClass("content grey_border2");
  	productVOJSON.setTabTex("Secondary Alternative");
  }*/
		if (!Utils.isBlank(providersData)) {
			String providersList[] = providersData.split("\\|");
			for (String providerIdWithName: providersList) {
				String providerValues[] = providerIdWithName.split("=");
				if (providerValues[0].trim().equalsIgnoreCase(providerExtId)) {
					productVOJSON.setChannelLineupProvider(true);
					if (Utils.isBlank(providerName)) {
						providerName = providerValues[1];
					}
					break;
				}
			}
		}
		if (providerName.equalsIgnoreCase("ATTSTI")) {
			providerName = "AT&T";
		} else if (providerName.equalsIgnoreCase("DISH Network")) {
			providerName = "Dish";
		}
		productVOJSON.setProductIconList(new ArrayList < String > ());
		if (isProductVideoCapable(product)) {
			productVOJSON.getProductIconList().add("tv");
			productVOJSON.setVideoCapable(true);
		}
		if (isProductPhoneCapable(product)) {
			productVOJSON.getProductIconList().add("phone");
		}
		if (isProductInternetCapable(product)) {
			productVOJSON.getProductIconList().add("internet");
		}
		if (!productResultManager.getSelectedExistingProvidersMap().isEmpty() && productResultManager.getSelectedExistingProvidersMap().containsValue(product.getProviderExternalId())) {
			productVOJSON.setExistingCustomer(true);
		}
		if (product.getPromotionType() != null && product.getPromotionType().equalsIgnoreCase("baseMonthlyDiscount")) {
			if (product.getPromotionPriceValueType() != null && product.getPromotionPriceValueType().equalsIgnoreCase("absolute")) {
				productVOJSON.setPromoPrice(Double.valueOf(product.getPromotionPrice()));
			} else if (product.getPromotionPriceValueType() != null && product.getPromotionPriceValueType().equalsIgnoreCase("relative")) {
				productVOJSON.setPromoPrice(product.getBaseRecurringPrice() - product.getPromotionPrice());
			}
		} else {
			ProductPromotionType productPromoType = SalesUtilsFactory.INSTANCE.isBaseMonthlyAvailable(product);
			if (productPromoType != null) {
				if (Constants.ABSOLUTE.equalsIgnoreCase(productPromoType.getPriceValueType())) {
					productVOJSON.setPromoPrice(Double.valueOf(productPromoType.getPriceValue()));
				} else if (Constants.RELATIVE.equalsIgnoreCase(productPromoType.getPriceValueType())) {
					productVOJSON.setPromoPrice(product.getBaseRecurringPrice() - productPromoType.getPriceValue());
				}
				product.setShortDescription(productPromoType.getShortDescription());
			}
		}

		Map < Float, Float > promoTreeSplitMap = new TreeMap < Float, Float > ();
		if (Constants.ATTV6.equals(providerExtId) || Constants.ATT.equals(providerExtId)) {
			if (!product.getPromotions().isEmpty()) {
				double promoTotal = 0.0;
				boolean isRelativePromotionExist = false;
				for (ProductPromotionType promo: product.getPromotions()) {
					if (promo.getType() != null && promo.getType().equalsIgnoreCase("baseMonthlyDiscount")) {
						if (promo.getPriceValueType() != null && promo.getPriceValueType().equalsIgnoreCase("relative")) {
							promoTotal = promoTotal + promo.getPriceValue();
							isRelativePromotionExist = true;
						} else if (promo.getPriceValueType() != null && promo.getPriceValueType().equalsIgnoreCase("absolute")) {
							promoTreeSplitMap.put(promo.getPriceValue(), promo.getPriceValue());
						}
					}
				}
				if (promoTreeSplitMap != null && !promoTreeSplitMap.isEmpty() && promoTreeSplitMap.size() > 0) {
					for (Float value: promoTreeSplitMap.keySet()) {
						productVOJSON.setPromoPrice(value.doubleValue());
						break;
					}
				} else if (isRelativePromotionExist) {
					productVOJSON.setPromoPrice(product.getBaseRecurringPrice() - promoTotal);
				}
			}
		}

		if (product.getPromotionType() != null && product.getPromotionCode().equals("Free Installation")) {
			productVOJSON.setBaseNonRecurringPrice(0.00);
		} else {
			productVOJSON.setBaseNonRecurringPrice(product.getBaseNonRecurringPrice());
		}
		logger.info("advisoryPromotion=" + ADVISORYPROMOTION);
		if (productVOJSON.isExistingCustomer()) {
			productVOJSON.setProductDescription(ADVISORYPROMOTION);
		} else {
			if (!Constants.ATTV6.equals(product.getProviderExternalId()) && !Constants.ATT.equals(product.getProviderExternalId()) && product.getShortDescription() != null) {
				if (Constants.COX_RTS_PROVIDER_ID.equals(product.getProviderExternalId()) && SalesUtilsFactory.INSTANCE.isBaseMonthlyAvailable(product) != null && !Utils.isBlank(SalesUtilsFactory.INSTANCE.getInformationalPromoShortDescription(product))) {
					productVOJSON.setProductDescription(product.getShortDescription() + "</br>" + SalesUtilsFactory.INSTANCE.getInformationalPromoShortDescription(product));
				} else {
					productVOJSON.setProductDescription(product.getShortDescription());
				}
			} else if (Constants.ATTV6.equals(product.getProviderExternalId()) && (Constants.ATT_BUILD_PRODUCT_EXID.equals(product.getExternalId()) || Constants.ATT_BUILD_PRODUCT_EXID_TRANSFER.equals(product.getExternalId()))) {
				productVOJSON.setProductDescription(product.getDescriptiveInfoValue());
			} else {
				if (product.getPromotionDescription() != null) {
					productVOJSON.setProductDescription(product.getPromotionDescription());
				} else {
					productVOJSON.setProductDescription("");
				}
			}
		}
		productVOJSON.setFilterMetaDatMap(new HashMap < String, String > ());
		if (product.getPromotionMetaDataList() != null && product.getPromotionMetaDataList().size() > 0) {
			for (String metaData: product.getPromotionMetaDataList()) {
				if (!Utils.isBlank(metaData)) {
					for (String metaDataName: SalesUtil.INSTANCE.metaDataKeyArray) {
						if (metaData.contains("=") && metaData.split("=").length > 1 && metaDataName.equalsIgnoreCase(metaData.split("=")[0])) {
							productVOJSON.getFilterMetaDatMap().put(metaData.split("=")[0], metaData.split("=")[1]);
						} else if (metaDataName.equalsIgnoreCase(metaData.split("=")[0])) {
							productVOJSON.getFilterMetaDatMap().put(metaData.split("=")[0], metaData.split("=")[0]);
						}
					}
				}
			}
		}
		if (product.getParentExternalId() != null) {
			productVOJSON.setImageID(product.getParentExternalId());
		} else {
			productVOJSON.setImageID(product.getProviderExternalId());
		}
		if (Constants.ATTV6.equals(product.getProviderExternalId()) && productResultManager.isATTProductHasSatellite(product)) {
			productVOJSON.setImageID(Constants.ATT_DIRECTV);
			productVOJSON.setExistingCustomer(false);
		}
		if (!Utils.isBlank(product.getEnergyUnitName())) {
			productVOJSON.setUnitName("/" + product.getEnergyUnitName());
		}
		if (product.getEnergyTierMap() != null && !product.getEnergyTierMap().isEmpty()) {
			productVOJSON.setEnergyTierMap(product.getEnergyTierMap());
			if (product.getEnergyTierMap().get(Constants.ENERGY_RATE) != null) {
				productVOJSON.setUsageRate(product.getEnergyTierMap().get(Constants.ENERGY_RATE));
			} else {
				for (Entry < String, Double > map: product.getEnergyTierMap().entrySet()) {
					productVOJSON.setUsageRate(map.getValue());
					break;
				}
			}
		}
		if (Constants.ATTV6.equals(product.getProviderExternalId()) && (product.getName().contains(Constants.BUILD_YOUR_BUNDLE) || Constants.ATT_BUILD_PRODUCT_EXID.equals(product.getExternalId()) || Constants.ATT_BUILD_PRODUCT_EXID_TRANSFER.equals(product.getExternalId()))) {
			if (categoryName.toUpperCase() != null && categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY")) {
				productVOJSON.getProductIconList().add("phone");
				productVOJSON.getProductIconList().add("internet");
			} else if (categoryName.toUpperCase() != null && categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_VI")) {
				productVOJSON.getProductIconList().add("tv");
				productVOJSON.getProductIconList().add("internet");
			} else if (categoryName.toUpperCase() != null && categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_PI")) {
				productVOJSON.getProductIconList().add("phone");
				productVOJSON.getProductIconList().add("internet");
			} else if (categoryName.toUpperCase() != null && categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_PV")) {
				productVOJSON.getProductIconList().add("tv");
				productVOJSON.getProductIconList().add("phone");
			} else {
				productVOJSON.getProductIconList().add("tv");
				productVOJSON.getProductIconList().add("phone");
				productVOJSON.getProductIconList().add("internet");
			}
			productVOJSON.setBaseRecNA(Constants.NA);
		}

		if (product.getBaseRecurringPrice() != null && productVOJSON.getPromoPrice() != null) {
			Double promoRoundPrice = Math.round(productVOJSON.getPromoPrice() * 100.0) / 100.0;
			Double baseRecRoundPrice = Math.round(product.getBaseRecurringPrice() * 100.0) / 100.0;
			if (promoRoundPrice.equals(baseRecRoundPrice)) {
				productVOJSON.setPromoPrice(null);
				productVOJSON.setProductDescription("");
			}
		}
		
		if(productVOJSON.getFilterMetaDatMap() != null && productVOJSON.getFilterMetaDatMap().get("NUM_CHANNELS") != null && !productVOJSON.getFilterMetaDatMap().get("NUM_CHANNELS").equalsIgnoreCase("NA")){
			productVOJSON.setChannels(Float.valueOf(productVOJSON.getFilterMetaDatMap().get("NUM_CHANNELS")));
		}else{
			productVOJSON.setChannels(Float.valueOf(0));
		}
		if(productVOJSON.getFilterMetaDatMap() != null && productVOJSON.getFilterMetaDatMap().get("CONN_SPEED") != null && !productVOJSON.getFilterMetaDatMap().get("CONN_SPEED").equalsIgnoreCase("NA")){
			productVOJSON.setConnSpeed(Float.valueOf(productVOJSON.getFilterMetaDatMap().get("CONN_SPEED")));
		}else{
			productVOJSON.setConnSpeed(Float.valueOf(0));
		}
		productVOJSON.setCapabilitMap(product.getCapabilityMap());
		productVOJSON.setProductPointDisplay(product.getPoints() == -1 ? "NA" : String.valueOf(product.getPoints()));
		productVOJSON.setProductScore(product.getScore());
		productVOJSON.setSortPrice(getProductSortPrice(product, productResultManager));
		productVOJSON.setBaseRecurringPrice(product.getBaseRecurringPrice());
		productVOJSON.setBaseRecurringPrice(product.getBaseRecurringPrice());
		productVOJSON.setHiddenProductJSON(StringEscapeUtils.unescapeHtml(product.getProdJson().toString()));
		productVOJSON.setProductType(product.getProductType());
		productVOJSON.setProviderName(providerName);
		productVOJSON.setProductName(product.getName());
		productVOJSON.setProviderExtId(product.getProviderExternalId());
		productVOJSON.setParentProviderExtId(providerExtId);
		productVOJSON.setProductExID(product.getExternalId());
		return productVOJSON;
	}
	public Double getProductSortPrice(ProductSummaryVO summaryVO, ProductResultsManager productResultManager) {
		Double sortPrice = summaryVO.getBaseRecurringPrice();
		boolean isBaseMonthly = false;
		if (summaryVO.getPromotionPrice() != null) {
			if (!summaryVO.getPromotions().isEmpty()) {
				if (Constants.BASE_MONTHLY_DISCOUNT.equalsIgnoreCase(summaryVO.getPromotionType())) {
					if (Constants.ABSOLUTE.equalsIgnoreCase(summaryVO.getPromotionPriceValueType())) {
						isBaseMonthly = true;
						sortPrice = Double.valueOf(summaryVO.getPromotionPrice());
					} else if (Constants.RELATIVE.equalsIgnoreCase(summaryVO.getPromotionPriceValueType())) {
						isBaseMonthly = true;
						sortPrice = summaryVO.getBaseRecurringPrice() - summaryVO.getPromotionPrice();
					}
				}
				if (isBaseMonthly && summaryVO.getBaseRecurringPrice() != null && sortPrice < summaryVO.getBaseRecurringPrice() && !summaryVO.getProviderExternalId().equals("4353598")) {
					//is exiting customer 
					if (productResultManager.getSelectedExistingProvidersMap().containsValue(summaryVO.getProviderExternalId())) {
						sortPrice = summaryVO.getBaseRecurringPrice();
					}
				}
			}
		}
		return sortPrice;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/recommendations/refreshProductResuts")
	public @ResponseBody String refreshProductResuts(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Refresh_Product_Resuts_Start");
		ProductResultsManager productResultManager = (ProductResultsManager) request.getSession().getAttribute("productResultManager");
		JSONObject  obj = new JSONObject();
		
		try {
			boolean isWebFlow = false;
			if (Constants.YES.equalsIgnoreCase(request.getParameter("dynamicFlow"))) {
				isWebFlow = true;
			}
			populateRecIconMap(productResultManager.getProductByIconMap(), request,productResultManager.getProductsMap(),productResultManager.getSelectedExistingProvidersMap());
			Map < String, Object > recIconMap = (Map < String, Object > ) request.getSession().getAttribute("recIconMap");
			String path = isWebFlow ? request.getParameter("flowExecutionUrl") : request.getContextPath();
			Fieldset buildData1 = HtmlFactory.INSTANCE.buildProductIcons(recIconMap, request, path);
			HashMap<String, String> selectedExistingProvidersMap = productResultManager.getSelectedExistingProvidersMap();
			 Map<String, List<ProductVOJSON>> newProductsMap = new HashMap<String,List<ProductVOJSON>>();
			 String transferAuthenticatedProviders  = ConfigRepo.getString("*.transfer_authenticated_providers");
				Map<String,String> transferAuthenticatedProvidersMap = new HashMap<String, String>();
				List<String> transferAuthenticatedProviderExtIdsList = new ArrayList<String>();
				List<String> transferAuthProviderExtIdsList = new ArrayList<String>();
				List<String> transferAuthOtherProviderExtIdsList = new ArrayList<String>();
				if (!Utils.isBlank(transferAuthenticatedProviders)) {
					String transferAuthenticatedProvidersList[] = transferAuthenticatedProviders.split("\\|");
					for (String providerIdWithName: transferAuthenticatedProvidersList) {
						String providerValues[] = providerIdWithName.split("=");
						transferAuthenticatedProvidersMap.put(providerValues[1], providerValues[0]);
					}
					for (Entry<String,String> entry : transferAuthenticatedProvidersMap.entrySet()){
						if(selectedExistingProvidersMap!= null&&selectedExistingProvidersMap.get(entry.getKey())!=null){
							transferAuthenticatedProviderExtIdsList.add(entry.getValue());
						}
						else{
							transferAuthOtherProviderExtIdsList.add(entry.getValue());
						}
						transferAuthProviderExtIdsList.add(entry.getValue());
					}
				}
		     if(productResultManager.getSelectedExistingProvidersMap() != null && transferAuthenticatedProviderExtIdsList!= null && transferAuthenticatedProviderExtIdsList.size()>0){
		    	 if(productResultManager.getProductsMap() != null ){
					for(Map.Entry<String, List<ProductVOJSON>> productVoList:productResultManager.getProductsMap().entrySet()){
						List<ProductVOJSON> newProductsList = new ArrayList<ProductVOJSON>();
						String category = productVoList.getKey();
						List<ProductVOJSON> products= productVoList.getValue();
						if(!products.isEmpty()){
							for(ProductVOJSON product : products){
								if(!category.equalsIgnoreCase("ASIS_PLAN")){
									try{
										if(product.getPairedProduct() != null){
											ProductVOJSON pairedProduct = product.getPairedProductVOJSON();
											if(pairedProduct != null && pairedProduct.getProviderExtId() != null && !transferAuthenticatedProviderExtIdsList.contains(pairedProduct.getProviderExtId())){
												if(product.getProviderExtId() != null && !transferAuthenticatedProviderExtIdsList.contains(product.getProviderExtId())){
													newProductsList.add(product);
												}
											}
										}else if(product.getProviderExtId() != null && !transferAuthenticatedProviderExtIdsList.contains(product.getProviderExtId())){
											newProductsList.add(product);
										}
									}catch(Exception e){
										logger.info("error occured while converting json to java object ="+e.getMessage());
									}
								}else if(category.equalsIgnoreCase("ASIS_PLAN")){
									if(transferAuthOtherProviderExtIdsList == null || !transferAuthOtherProviderExtIdsList.contains(product.getProviderExtId())){
										newProductsList.add(product);
									}
								}
							}
							newProductsMap.put(category, newProductsList);
						}else{
							newProductsMap.put(category, newProductsList);
						}
					}
				}
		    	 obj.put("productMap", SalesUtilsFactory.INSTANCE.toJson(newProductsMap));
		      }else{
		    	  if(productResultManager.getProductsMap() != null ){
		  			for(Map.Entry<String, List<ProductVOJSON>> productVoList:productResultManager.getProductsMap().entrySet()){
		  				List<ProductVOJSON> newProductsList = new ArrayList<ProductVOJSON>();
		  				String category = productVoList.getKey();
		  				List<ProductVOJSON> products= productVoList.getValue();
		  				if(!products.isEmpty()){
		  					for(ProductVOJSON product : products){
		  						if(category.equalsIgnoreCase("ASIS_PLAN")){
		  							try{
		  								if(product.getProviderExtId() != null && transferAuthProviderExtIdsList!= null && !transferAuthProviderExtIdsList.contains(product.getProviderExtId())){
		  									newProductsList.add(product);
		  								}
		  							}catch(Exception e){
		  								logger.info("error occured while converting json to java object is="+e.getMessage());
		  							}
		  						}else if(!category.equalsIgnoreCase("ASIS_PLAN")){
		  							newProductsList.add(product);
		  						}
		  					}
		  					newProductsMap.put(category, newProductsList);
		  				}else{
		  					newProductsMap.put(category, productVoList.getValue());
		  				}
		  			}
		  			 obj.put("productMap", SalesUtilsFactory.INSTANCE.toJson(newProductsMap));
		  		}
			}
			obj.put("isSynthetic", productResultManager.isSynthetic());
			obj.put("isConsumer", productResultManager.isConsumer());
			obj.put("recIconMap", escapeSpecialCharacters(HtmlBuilder.INSTANCE.toString(buildData1)));
		} catch (JSONException e) {
			e.printStackTrace();
			throw new Exception("Refresh Products JSON Exception: " + e.getMessage());
		}
		logger.info("Refresh_Product_Resuts_End");
		return obj.toString();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/recommendations/getRoadMapContent")
	public @ResponseBody String getRoadMapContent(HttpServletRequest request) {
		HttpSession session = request.getSession();
		JSONObject obj = new JSONObject();
		String roadMapCriteriaVOJson = "";
		try{
			ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");
			  Gson gson = new Gson();
			  if(productResultManager != null){
				  List<RoadMapCriteriaVO> roadMapCriteriaVOList = new ArrayList<RoadMapCriteriaVO>() ;
				  boolean isCompleted = productResultManager.isRoadmapCompleted();
				   if(isCompleted && productResultManager != null && productResultManager.getRoadMapPowerPitchList() != null && !productResultManager.getRoadMapPowerPitchList().isEmpty()){
					   roadMapCriteriaVOList =(List<RoadMapCriteriaVO>)productResultManager.getRoadMapPowerPitchList();
					    roadMapCriteriaVOJson = gson.toJson(roadMapCriteriaVOList);
					    logger.info("roadMapCriteriaVOList size="+roadMapCriteriaVOList.size());
				   }
				   obj.put("roadMapCriteriaVOJson", roadMapCriteriaVOJson);
				   obj.put("isPollingCompleted", isCompleted); 
			  }
		} catch (Exception e) {
			logger.error("error_while_getting_RoadMapContent", e);
		}
		return obj.toString();
	}


} 