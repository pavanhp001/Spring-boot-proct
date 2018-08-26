package com.AL.controller;

import static com.AL.ui.util.ConfigProperties.ADVISORYPROMOTION;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.webflow.execution.Action;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.AL.html.Del;
import com.AL.html.Div;
import com.AL.html.Fieldset;
import com.AL.html.Form;
import com.AL.html.Img;
import com.AL.html.Input;
import com.AL.html.ObjectFactory;
import com.AL.html.Span;
import com.AL.productResults.managers.ProductResultsManager;
import com.AL.productResults.vo.PollingStatus;
import com.AL.productResults.vo.ProductSummaryVO;
import com.AL.ui.constants.Constants;
import com.AL.ui.domain.sales.Alert;
import com.AL.ui.factory.CartLineItemFactory;
import com.AL.ui.factory.SalesUtilsFactory;
import com.AL.ui.service.config.ConfigRepo;
import com.AL.ui.util.LineItemUtil;
import com.AL.ui.util.SalesUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.Address;
import com.AL.ui.vo.ChannelLineupVO;
import com.AL.ui.vo.ChannelLineupVO.Response.Data.Channels.Channel;
import com.AL.ui.vo.ChannelLineupVO.Response.Data.Provider.Packages.Package;
import com.AL.ui.vo.ChannelLineupVO.Response.Data.Provider.Packages.Package.Category;
import com.AL.ui.vo.ChannelLineupVO.Response.Data.Provider.Packages.Package.ChannelRefs.ChannelId;
import com.AL.ui.vo.ProductVOJSON;
import com.AL.V.exception.UnRecoverableException;
import com.AL.xml.pr.v4.ProductPromotionType;
import com.AL.xml.v4.OrderType;

@Controller("RecommendationsByCategory")
public class RecommendationsByCategory  implements Action 
{

	private static final ObjectFactory oFactory = new ObjectFactory();
	private static final Logger logger = Logger.getLogger(RecommendationsByCategory.class);

	@Autowired  
	private MessageSource messageSource;  

	public Event execute(RequestContext request) throws Exception {

		logger.info("showRecommendationsbyCategory_begin_in_RecommendationsByCategory");
		HttpServletRequest httpRequest =(HttpServletRequest)request.getExternalContext().getNativeRequest();
		StopWatch timer = new StopWatch();
		timer.start();
		try{
			String categoryName = httpRequest.getParameter("CategoryName");
			List<ProductSummaryVO> syntheticBundlesList = new ArrayList<ProductSummaryVO>();
			//To Show All Products of selected ProviderID 
			String selectedProviderID = httpRequest.getParameter("selectedProviderID");
			HttpSession session = httpRequest.getSession();
			session.setAttribute("categoryName", categoryName);
			Long orderId =(Long)session.getAttribute("orderId");
			Map<String, String> customerDiscovery = new LinkedHashMap<String, String>();
			Map<String, String[]> parameters = httpRequest.getParameterMap();
			Map<String, String> notePadHeaders = new LinkedHashMap<String, String>();
			Map<String, String> discoveryNotepadlinkMap = new LinkedHashMap<String, String>();
			String typeOfService = (String)session.getAttribute("typeOfService");
			String pageTitle = httpRequest.getParameter("pageTitle");
			session.setAttribute("pivotAssistJson","");
			boolean isFromDiscovery = false;
			String isOtherChecked = httpRequest.getParameter("isOtherChecked");
			if("Discovery Transfer".equalsIgnoreCase(pageTitle) 
					|| "Discovery New".equalsIgnoreCase(pageTitle)
					|| "MoveInDeltaService".equalsIgnoreCase(pageTitle))
			{
				if(!Utils.isBlank(isOtherChecked))
				{
					request.getFlowScope().put("isOtherChecked", "Yes");
				}
				else
				{
					request.getFlowScope().put("isOtherChecked", "No");
				}
				String discoveryQuestionsOrder = httpRequest.getParameter("discoveryQuestionsOrder");
				if( !Utils.isBlank(discoveryQuestionsOrder) )
				{
					String notePadDiscoveryOrder[] = discoveryQuestionsOrder.split(",");
					isFromDiscovery = true;
					for(String discoveryQuestion : notePadDiscoveryOrder)
					{
						if( parameters.get(discoveryQuestion)!=null )
						{
							String[] header = discoveryQuestion.split("\\|");
							String[] values = parameters.get(discoveryQuestion);
							if (!Utils.isBlank(values[0])) {
								customerDiscovery.put(header[0], values[0]);
							}
							if (header.length > 0) {
								notePadHeaders.put(header[1], (!Utils.isBlank(values[0]) ? values[0] : ""));
								discoveryNotepadlinkMap.put(header[1], header[0]);
							}
						}
					}
				}
				session.setAttribute("notePadMap", notePadHeaders);
				session.setAttribute("discoveryNotepadlinkMap", discoveryNotepadlinkMap);
				if(session.getAttribute("landlord") != null){
					customerDiscovery.put("Landlord","true");
				}
				session.setAttribute("customerDiscoveryMap", customerDiscovery);
			}
			if( isFromDiscovery )
			{
				isFromDiscovery = true;
				if (!Utils.isBlank(typeOfService) && typeOfService.equals("existingService")) {
					session.setAttribute("existingService", notePadHeaders); //cleanup
					session.setAttribute("preDiscoveryTransfer", notePadHeaders); //cleanup
					session.setAttribute("questionList", notePadHeaders); //cleanup
				}
				if (!Utils.isBlank(typeOfService) && typeOfService.equals("newService")) {
					session.setAttribute("newService", notePadHeaders); //cleanup
					session.setAttribute("questionList", notePadHeaders); //cleanup
				}
				if (!Utils.isBlank(typeOfService) && typeOfService.equals("moveInDeltaService")) {
					session.setAttribute("moveInDeltaService", notePadHeaders); //cleanup
					session.setAttribute("questionList", notePadHeaders); //cleanup
				}
			}else{
				notePadHeaders = (Map<String, String>) session.getAttribute("notePadMap");
			}
			if(session.getAttribute("pesistFilterOptions") == null 
					&& Utils.isBlank((String)session.getAttribute("pesistFilterOptions"))){
				session.setAttribute("pesistFilterOptions", "");				
			}
			//OrderType order = OrderService.INSTANCE.getOrderByOrderNumber(String.valueOf(orderId), agentId, null,null,false, null);
			logger.info("getting_order_in_session_recommendations_category");			
			OrderType order = (OrderType) session.getAttribute("order");
			List<ProductSummaryVO> details = new ArrayList<ProductSummaryVO>();
			
			ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");
			if(categoryName.equalsIgnoreCase("MIXEDBUNDLES")){
				syntheticBundlesList = (List<ProductSummaryVO>) productResultManager.getProductByIconMap().get(categoryName.toUpperCase());
			}
			else{
				details = (List<ProductSummaryVO>) productResultManager.getProductByIconMap().get(categoryName.toUpperCase());
			}
			if(!syntheticBundlesList.isEmpty()){
				logger.info(String.format("categoryName=%s selectedProviderID=%s syntheticBundlesListSize=%s", categoryName, selectedProviderID,syntheticBundlesList.size()));
			}else{
				logger.info(String.format("categoryName=%s selectedProviderID=%s ", categoryName, selectedProviderID));
			}
			
			try{
				if(productResultManager != null && productResultManager.getHintProvidersMap() != null && !productResultManager.getHintProvidersMap().isEmpty()){
					String hintProviders = productResultManager.getHintProvidersMap().keySet().toString();
					logger.info("hintProviders="+hintProviders);
					session.setAttribute("hintProviders", hintProviders);
				}
			}
			catch(Exception e)
			{
				logger.error("error_while_retreiving_hintProviders"+e.getMessage());
			}

			if (categoryName.toUpperCase() != null && (categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_PI")
					|| categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_PV") 
					|| categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_VI"))){

				details = (List<ProductSummaryVO>) productResultManager.getProductByIconMap().get("DOUBLE_PLAY");
				details = productResultManager.getDoublePlayRenderData(details,categoryName.toUpperCase());
			}

			if(!Utils.isBlank(request.getFlowScope().getString("moveInDeltaAccepted"))){
				populateRecIconMap(productResultManager.getProductByIconMap(), request);	
			}

			Map<String, String> filterProviderNamesAndExtIdsMap = productResultManager.getCategoryWiseProductNameAndExternalId().get(categoryName.toUpperCase());
			List<ProductVOJSON> productVOJSONList = new ArrayList<ProductVOJSON>();
			List<ProductVOJSON> syntheticProductVOJSONList = new ArrayList<ProductVOJSON>();
			Map<String,List<ProductVOJSON>> syntheticbundleMap = new LinkedHashMap<String,List<ProductVOJSON>>();
			Map<String,List<ProductVOJSON>> syntheticMap = new LinkedHashMap<String,List<ProductVOJSON>>();
			boolean isSyntheticBundle = false;
			if (details != null){
				if(filterProviderNamesAndExtIdsMap != null 
						&& filterProviderNamesAndExtIdsMap.get(Constants.DIRECTV_NAME) == null
						&& productResultManager.getRealTimeStatusMap() != null ){
					if(productResultManager.getRealTimeStatusMap().get(Constants.DIRECTV_NAME) != null 
							&& productResultManager.getRealTimeStatusMap().get(Constants.DIRECTV_NAME).contains(PollingStatus.SUCCESS)
							&& productResultManager.isATTDirecTVAvailable(details)){
						filterProviderNamesAndExtIdsMap.put(Constants.DIRECTV_NAME, Constants.DIRECTV_NAME);
					}
					if(filterProviderNamesAndExtIdsMap.get(Constants.ATTV6) != null && !productResultManager.isATTAvailable(details)){
						filterProviderNamesAndExtIdsMap.remove(Constants.ATTV6);
					}
					if(filterProviderNamesAndExtIdsMap.get(Constants.ATTV6) != null){
						filterProviderNamesAndExtIdsMap.put(Constants.ATTV6,Constants.ATT_NAME);
					}
				}
			}
			if (categoryName.equalsIgnoreCase("MIXEDBUNDLES") && !CollectionUtils.isEmpty(syntheticBundlesList)){
				if(!CollectionUtils.isEmpty(syntheticBundlesList)) {
					logger.info("syntheticBundlesList_size_In_MixedBundlesCategory="+ syntheticBundlesList.size());
					for(int i=0;i<syntheticBundlesList.size();i++){
						createJSON(syntheticBundlesList.get(i).getPairedProduct(), orderId);
						createJSON(syntheticBundlesList.get(i), orderId);
						//syntheticProductVOJSONList.add(buildProductJSONVO(syntheticBundlesList.get(i).getPairedProduct(),productResultManager,categoryName));
						syntheticProductVOJSONList.add(buildProductJSONVO(syntheticBundlesList.get(i),productResultManager,categoryName));
					}
				}
				//request.getFlowScope().put("syntheticProductVOJSONList",SalesUtilsFactory.INSTANCE.toJson(syntheticProductVOJSONList));
				
				request.getFlowScope().put("productVOJSONList",SalesUtilsFactory.INSTANCE.toJson(syntheticProductVOJSONList));
			}
			else{
				for(int i=0;i<details.size();i++){
					if((categoryName.equalsIgnoreCase("DOUBLE_PLAY") || categoryName.equalsIgnoreCase("TRIPLE_PLAY") || categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_VI")) 
						       && (categoryName.equalsIgnoreCase(details.get(i).getProductCategory())||categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_VI")) && details.get(i).isSyntheticBundle()){
						isSyntheticBundle = true;
						createJSON(details.get(i).getPairedProduct(), orderId);
						}
					createJSON(details.get(i), orderId);
					productVOJSONList.add(buildProductJSONVO(details.get(i),productResultManager,categoryName));
				}
				request.getFlowScope().put("productVOJSONList",SalesUtilsFactory.INSTANCE.toJson(productVOJSONList));
			}
			if(!syntheticMap.isEmpty()){
				logger.info(String.format("categoryName=%s mixedBundlesListSize=%s", categoryName,syntheticMap.size()));
			}
			List<ProductVOJSON> tempList = new ArrayList<ProductVOJSON>();
			for(int i=0;i<syntheticProductVOJSONList.size();i++){
				ProductVOJSON  productVOJSON = syntheticProductVOJSONList.get(i);
				tempList.add(productVOJSON);
				if(i%2 != 0 && i != 0){
					syntheticbundleMap.put("bundle"+i, tempList);
					tempList =  new ArrayList<ProductVOJSON>();
				}
			}
			request.getFlowScope().put("syntheticbundleMap",SalesUtilsFactory.INSTANCE.toJson(syntheticbundleMap));
			request.getFlowScope().put("syntheticMap",SalesUtilsFactory.INSTANCE.toJson(syntheticMap));
			request.getFlowScope().put("isSyntheticBundle",isSyntheticBundle);
			session.setAttribute("activeAlert", (Alert) RESTClient.INSTANCE.getActiveAlert());
			if(session.getAttribute("salesTips") != null){
				logger.info("salesTips content");
				request.getFlowScope().put("salesTips", (String)session.getAttribute("salesTips"));
			}
			logger.info("product_details_by_categoryName=" +details.size());
			Long addressId = (Long) session.getAttribute("addressId");
			request.getFlowScope().put("addressId", addressId);
			httpRequest.getSession().setAttribute("address", SalesUtil.INSTANCE.getAddress(order.getCustomerInformation().getCustomer(),
					com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString()));
			request.getFlashScope().put("CKOCompletedLineItems" , LineItemUtil.prepareCKOCompletedLinItemsListFromOrder(order));
			httpRequest.getSession().setAttribute("order" , order);
			httpRequest.getSession().setAttribute("lineItemList", CartLineItemFactory.INSTANCE.sortLineItemBasedOnStatus(order));
			request.getFlowScope().put("title" , "recommendations."+categoryName);
			request.getFlashScope().put("filterDisplay" , "Yes");
			request.getFlowScope().put("categoryName" , categoryName);
			request.getFlowScope().put("selectedProviderID" , selectedProviderID);
			request.getFlowScope().put("notePadMap", notePadHeaders);
			request.getFlowScope().put("promoMap", CartLineItemFactory.INSTANCE.hasLineItemPromtions(order));
			request.getFlowScope().put("providerNamesAndExtIdsMap", productResultManager.getProviderNamesAndExtIdsMap());
			request.getFlowScope().put("filterProviderNamesAndExtIdsMap", SalesUtilsFactory.INSTANCE.toJson(filterProviderNamesAndExtIdsMap));
			
			request.getFlowScope().put("totalPoints", Utils.getTotalPoints(order, session));
			//request.getFlowScope().put("contractTermList", SalesUtilsFactory.INSTANCE.toJson(contractTermList));
			//request.getFlowScope().put("internetRangeList", SalesUtilsFactory.INSTANCE.toJson(getFilterRangeListBasedOnSection("internetValInJsonFrmt",internetList)));
			//request.getFlowScope().put("channleRangeList", SalesUtilsFactory.INSTANCE.toJson(getFilterRangeListBasedOnSection("channelValinJsonFrmt",channelList)));
			logger.info("TotalTimeTakenForShowRecomendationsbyCatgory="+timer.getTime());
			logger.info("showRecommendationsbyCategory_end_in_RecommendationsByCategory");
			return null;
		}
		catch(Exception e)
		{
			request.getFlowScope().put("message", e.getMessage());
			request.getFlowScope().put("pageTitle",httpRequest.getParameter("pageTitle")!=null?httpRequest.getParameter("pageTitle"):"");
			logger.error("error_while_displaying_products_by_ctegory",e);
			throw new UnRecoverableException(e.getMessage());
		}finally{
			timer.stop();
		}
	}


	private List<Integer> filterInternetSpeedList(
			List<ProductSummaryVO> details) {
		List<Integer> resultList = new ArrayList<Integer>();
		for (ProductSummaryVO productVo :details){
			if (productVo.getPromotionMetaDataList() != null && productVo.getPromotionMetaDataList().size() > 0){
				for(String metaData :productVo.getPromotionMetaDataList()){
					if (metaData != null) {
						if (metaData != null && metaData.contains("=")){
							String[] str1 = metaData.split("=");
							if (str1[0].equalsIgnoreCase("CONN_SPEED")){
								try {
									//"0-5":"up to 5M","6-10":"6-10M", 2
									String[] str2 = messageSource.getMessage("internetValInJsonFrmt", null, "", null).split(",");
									for (int j =0; j< str2.length; j++){
										String[] str3 = str2[j].split(":");
										String[] str4 = str3[0].split("-");
										//2 > 0 && 2 < 5
										if (Long.valueOf(str1[1]) >= Long.valueOf(str4[0]) &&  Long.valueOf(str1[1]) <= Long.valueOf(str4[1])){
											resultList.add(Integer.valueOf(str4[1]));
										}

									}
								} catch (Exception e) {
									logger.error("InternetSpeedError="+e.getMessage());
								}

							}							

						}
					}
				}
			}
		}

		return resultList;
	}


	private List<Integer> filterChannelsList(
			List<ProductSummaryVO> details) {
		List<Integer> resultList = new ArrayList<Integer>();
		for (ProductSummaryVO productVo :details){
			if (productVo.getPromotionMetaDataList() != null && productVo.getPromotionMetaDataList().size() > 0){
				for(String metaData :productVo.getPromotionMetaDataList()){
					if (metaData != null) {
						if (metaData != null && metaData.contains("=")){
							String[] str1 = metaData.split("=");
							if (str1[0].equalsIgnoreCase("NUM_CHANNELS")){
								try {
									//"0-5":"up to 5M","6-10":"6-10M"
									String[] str2 = messageSource.getMessage("channelValinJsonFrmt", null, "", null).split(",");
									for (int j =0; j< str2.length; j++){
										String[] str3 = str2[j].split(":");
										String[] str4 = str3[0].split("-");
										//2 > 0 && 2 < 5
										if (Long.valueOf(str1[1]) >= Long.valueOf(str4[0]) &&  Long.valueOf(str1[1]) <= Long.valueOf(str4[1])){
											resultList.add(Integer.valueOf(str4[1]));
										}

									}
								} catch (Exception e) {
									logger.error("Channelrror="+e.getMessage());
								}

							}
						}
					}
				}
			}
		}
		return resultList;
	}


	private Fieldset getProductFieldSetByCategory(List<ProductSummaryVO> productList,HttpServletRequest httpRequest, HashMap<String, String> selectedProviders) {
		String strContextPath = httpRequest.getContextPath();
		Fieldset fieldSet = oFactory.createFieldset();
		if(!CollectionUtils.isEmpty(productList)) {
			for(int index=0, counter=0; index < productList.size(); index++, counter++) {
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
				Div cdiv = renderProductData(index, product, httpRequest, tabtext,selectedProviders);
				content.getContent().add(cdiv);

				tabcont.getContent().add(content);				
				tabs.getContent().add(tabcont);
				fieldSet.getContent().add(tabs);
			}
		}

		return fieldSet;
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
	public void  createJSON(ProductSummaryVO detailsVO, Long orderId) throws Exception {
		DecimalFormat format = new DecimalFormat("#0.00");
		JSONObject prodJson = new JSONObject();
		try {
			prodJson.put("orderId", orderId);
			prodJson.put("partnerExternalId", escapeSpecialCharacters(detailsVO.getProviderExternalId()));
			if(detailsVO.getParentExternalId() != null){
				prodJson.put("img_id", escapeSpecialCharacters(detailsVO.getParentExternalId()));
			}else{
				prodJson.put("img_id", escapeSpecialCharacters(detailsVO.getProviderExternalId()));
			}
			prodJson.put("productExernalId",escapeSpecialCharacters(detailsVO.getExternalId()));
			prodJson.put("productname",escapeSpecialCharacters(detailsVO.getName()));
			prodJson.put("isPromotion", false);
			prodJson.put("providerName", escapeSpecialCharacters(detailsVO.getProviderName()));
			prodJson.put("non_recuring", ("$"+format.format(detailsVO.getBaseNonRecurringPrice())));
			prodJson.put("recuring", ("$"+format.format(detailsVO.getBaseRecurringPrice())));
			prodJson.put("capabilityMap", detailsVO.getCapabilityMap());
			prodJson.put("productType", detailsVO.getProductType());
			prodJson.put("providerSourceBaseType", detailsVO.getSource());
			/*
			 * adding TPV and Warm Transfer to JSON
			 */
			prodJson.put("isTPVProduct", detailsVO.isTPVProduct());
			prodJson.put("isWarmTransfer", detailsVO.isWarmTransferProduct());
			String hybrisShell = "false";
			String noEmail = "false";
			String noOrderStatus = "false";
			Map<String, String> metadata = detailsVO.getMetadata();	
			for(Map.Entry<String, String> mapEntry : metadata.entrySet()){
				if(mapEntry.getValue().equalsIgnoreCase(Constants.HYBRIS_SHELL) || mapEntry.getValue().equalsIgnoreCase(Constants.ATG_LINK)) {
					hybrisShell = "true";
					//break;
				}
				else if(mapEntry.getValue().equalsIgnoreCase(Constants.NO_EMAIL)) {
					noEmail = "true";
				}
				else if(mapEntry.getValue().equalsIgnoreCase(Constants.NO_ORDER_STATUS)) {
					noOrderStatus = "true";
				}
			}
			List<String> promotionMetadata = detailsVO.getPromotionMetaDataList();	
			if(promotionMetadata != null) {
				if(promotionMetadata.contains(Constants.HYBRIS_SHELL) || promotionMetadata.contains(Constants.ATG_LINK)) {
					hybrisShell = "true";
				}
				if(promotionMetadata.contains(Constants.NO_EMAIL)) {
					noEmail = "true";
				}
				if(promotionMetadata.contains(Constants.NO_ORDER_STATUS)) {
					noOrderStatus = "true";
				}
			}
			prodJson.put("hybrisShell", hybrisShell);
			prodJson.put("noEmail", noEmail);
			prodJson.put("noOrderStatus", noOrderStatus);

		} catch (JSONException e) {
			logger.error("error_while_preparing_product_json",e);
			throw new Exception("JSONException: "+e.getMessage());
		}
		detailsVO.setProdJson(prodJson);
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
	private Div renderProductData(int index, ProductSummaryVO product, HttpServletRequest httpRequest, String tabtext, HashMap<String, String> selectedProviders){
		String strContextPath = httpRequest.getContextPath();
		String providersImageLocation = (String)httpRequest.getSession().getAttribute("providersImageLocation");
		boolean isValidIconsForProducts = false;
		boolean isChannelLineupProvider = false;
		boolean isVideoCapable = false;
		String providersData = ConfigRepo.getString("*.channelLineupProviders") == null ? null : ConfigRepo.getString("*.channelLineupProviders");
		logger.debug("providersData="+providersData);
		String providerExtId = product.getParentExternalId()!=null?product.getParentExternalId():product.getProviderExternalId();
		String providerName = escapeSpecialCharacters(product.getProviderName());

		if(!Utils.isBlank(providersData))
		{
			String providersList[] = providersData.split("\\|");
			for (String providerIdWithName : providersList) 
			{
				String providerValues[] = providerIdWithName.split("=");
				if(providerValues[0].trim().equalsIgnoreCase(providerExtId))
				{
					isChannelLineupProvider = true;
					if(Utils.isBlank(providerName)){
						providerName = providerValues[1];
					}
					break;
				}
			}
		}

		if (providerName.equalsIgnoreCase("ATTSTI"))
		{
			providerName = "AT&T";
		}
		else if (providerName.equalsIgnoreCase("DISH Network"))
		{
			providerName = "Dish";
		}

		//Crating Tabs Main <div class="tabs">
		Div tdiv = oFactory.createDiv();
		tdiv.setStyle("float:left");

		Div row1 = oFactory.createDiv();

		row1.getClazz().add("row1");

		if(tabtext != ""){
			Div tab = oFactory.createDiv();
			tab.getClazz().add("tab");
			tab.getContent().add(tabtext);
			row1.getContent().add(tab);
		}

		Div productTitle = oFactory.createDiv();
		productTitle.getClazz().add("productTitle");
		productTitle.getContent().add(product.getName());//Real Product Title Should add
		row1.getContent().add(productTitle);

		//Creating Cell for Product Icons <span class="cell productIcons">
		Div productIcons = oFactory.createDiv();
		productIcons.getClazz().add("productIcons");

		Img pIcon = null;

		if(isProductVideoCapable(product)) {
			//Creating Image for Product Icons
			pIcon = oFactory.createImg();
			pIcon.setSrc(strContextPath+"/images/images_new/tv.png");
			pIcon.setWidth("19");
			pIcon.setHeight("18");
			productIcons.getContent().add(pIcon);
			isValidIconsForProducts = true;
			isVideoCapable = true;
		}

		if(isProductPhoneCapable(product)) {
			pIcon = oFactory.createImg();
			pIcon.setSrc(strContextPath+"/images/images_new/phone.png");
			pIcon.setWidth("19");
			pIcon.setHeight("18");
			productIcons.getContent().add(pIcon);
			isValidIconsForProducts = true;
		}

		if(isProductInternetCapable(product)) {
			pIcon = oFactory.createImg();
			pIcon.setSrc(strContextPath+"/images/images_new/internet.png");
			pIcon.setWidth("23");
			pIcon.setHeight("18");
			productIcons.getContent().add(pIcon);
			isValidIconsForProducts = true;
		}
		
		if(Constants.APPLIANCEPROTECTION.equalsIgnoreCase(product.getProductCategory())) {
			pIcon = oFactory.createImg();
			pIcon.setSrc(strContextPath+"/images/images_new/ApplianceProtection.png");
			pIcon.setWidth("33");
			pIcon.setHeight("28");
			productIcons.getContent().add(pIcon);
			isValidIconsForProducts = true;
			
		}

		if (isValidIconsForProducts){
			row1.getContent().add(productIcons);	
		}

		boolean isExistingCustomer = false;
		if( !selectedProviders.isEmpty() && selectedProviders.containsValue(product.getProviderExternalId()) )
		{
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
		iPriceHidden.setId("price_"+product.getProviderExternalId()+"_"+product.getExternalId());

		String promotionPrice = null;
		if (product.getPromotionType() != null &&  product.getPromotionType().equalsIgnoreCase("baseMonthlyDiscount") ){
			if (product.getPromotionPriceValueType() != null && product.getPromotionPriceValueType().equalsIgnoreCase("absolute")){
				promotionPrice  = product.getPromotionPrice().toString();
			}else if (product.getPromotionPriceValueType() != null && product.getPromotionPriceValueType().equalsIgnoreCase("relative")){
				promotionPrice = String.valueOf(product.getBaseRecurringPrice() - product.getPromotionPrice());
			}
		}
		/*else if (product.getPromotionType() != null &&  product.getPromotionType().equalsIgnoreCase("informationalPromotion")){
			promotionPrice = String.valueOf(product.getBaseRecurringPrice());
		}*/
		DecimalFormat format = new DecimalFormat("#0.00");
		if(promotionPrice != null && product.getBaseRecurringPrice()!= null 
				&& Double.parseDouble(promotionPrice) < product.getBaseRecurringPrice() 
				&& !product.getProviderExternalId().equals("4353598")){ 

			if( isExistingCustomer ){
				iPriceHidden.setValue(product.getBaseRecurringPrice().toString());
				productBaseInfo.getContent().add(iPriceHidden);
				Span span = oFactory.createSpan();
				span.setStyle("color:red;");
				span.getContent().add("TBD");
				delBasePrice.getContent().add("BASE PRICE: ");//Add base recurring Price 
				delBasePrice.getContent().add(span);
				productBaseInfo.getContent().add(delBasePrice);
			}
			else
			{
				delBasePrice.getContent().add("BASE PRICE: ");
				del.getContent().add("$"+format.format(product.getBaseRecurringPrice()));//Add Promotion Price real data
				deleteSpan.getContent().add(del);
				delBasePrice.getContent().add(deleteSpan);
				productBaseInfo.getContent().add(delBasePrice);

				promoPrice.getContent().add("$"+format.format(Double.parseDouble(promotionPrice)));//Add Promotion Price real data
				productBaseInfo.getContent().add(promoPrice);

				iPriceHidden.setValue(product.getBaseRecurringPrice().toString());
				productBaseInfo.getContent().add(iPriceHidden);
			}

		}else{

			if( isExistingCustomer )
			{
				iPriceHidden.setValue(product.getBaseRecurringPrice().toString());
				productBaseInfo.getContent().add(iPriceHidden);
				Span span = oFactory.createSpan();
				span.setStyle("color:red;");
				span.getContent().add("TBD");
				delBasePrice.getContent().add("BASE PRICE: ");//Add base recurring Price 
				delBasePrice.getContent().add(span);
				productBaseInfo.getContent().add(delBasePrice);
			}
			else
			{
				iPriceHidden.setValue((product.getBaseRecurringPrice().toString()));
				productBaseInfo.getContent().add(iPriceHidden);
				delBasePrice.getContent().add("BASE PRICE: $"+format.format(product.getBaseRecurringPrice()));//Add base recurring Price 
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
		iChkBox.setName("cbox"+index);
		iChkBox.setId("cbox"+index);
		iChkBox.setClazz("pp_checkbox");
		iChkBox.setValue("input_"+product.getProviderExternalId()+"_"+product.getExternalId());
		ppformcontent.getContent().add(iChkBox);

		Input iChkBoxHidden = oFactory.createInput();
		iChkBoxHidden.setType("hidden");
		iChkBoxHidden.setId("hidden_product_"+product.getProviderExternalId()+"_"+product.getExternalId());
		iChkBoxHidden.setValue(product.getProdJson().toString());
		ppformcontent.getContent().add(iChkBoxHidden);

		Input iInstallPriceHidden = oFactory.createInput();
		iInstallPriceHidden.setType("hidden");
		iInstallPriceHidden.setId("installPrice_"+product.getProviderExternalId()+"_"+product.getExternalId());
		if(product.getPromotionType() != null &&  product.getPromotionCode().equals("Free Installation")){
			iInstallPriceHidden.setValue("0.00");
		}else{
			iInstallPriceHidden.setValue(product.getBaseNonRecurringPrice().toString());	
		}
		ppformcontent.getContent().add(iInstallPriceHidden);

		if( isChannelLineupProvider && isVideoCapable )
		{
			// Create View Details buttons <input type="button" name="View Channels" value="View Channels" class="ViewChannelsBtn" />
			Input viewChannelsBtn = oFactory.createInput();
			viewChannelsBtn.setType("button");
			viewChannelsBtn.setName("");
			viewChannelsBtn.setId("product_"+product.getProviderExternalId()+"_"+product.getExternalId());
			viewChannelsBtn.setClazz("ViewChannelsBtn");
			viewChannelsBtn.setValue("View Channels");
			viewChannelsBtn.setOnclick("displayChannelLineUpData('"+providerExtId+"','"+providerName+"')");
			ppformcontent.getContent().add(viewChannelsBtn);
		}
		// Create View Details buttons <input type="button" name="View Details" value="View Details" class="ViewDetailsBtn" />
		Input ViewDetailsBtn = oFactory.createInput();
		ViewDetailsBtn.setType("button");
		ViewDetailsBtn.setName("");
		ViewDetailsBtn.setId("product_"+product.getProviderExternalId()+"_"+product.getExternalId());
		ViewDetailsBtn.setClazz("ViewDetailsBtn");
		ViewDetailsBtn.setValue("View Details");
		ppformcontent.getContent().add(ViewDetailsBtn);		

		Input iPriceHidden1 = oFactory.createInput();
		iPriceHidden1.setType("hidden");
		iPriceHidden1.setId("aidvalue");
		iPriceHidden1.setName("aidvalue");
		iPriceHidden1.setValue(product.getExternalId() + "," +product.getName()+","+product.getProviderExternalId() + "," + product.getProductType());
		ppformcontent.getContent().add(iPriceHidden1);

		// Create span for provider logo <span class="cell productLogopp"><img src="" alt=""/></span>
		Span productLogopp = oFactory.createSpan();
		productLogopp.getClazz().add("cell productLogopp");
		//productLogopp.setStyle("padding-left:10px;text-align: center;");

		//Creating Image for Product Icons
		Img productLogo = oFactory.createImg();

		if(product.getParentExternalId() != null){
			productLogo.setSrc(providersImageLocation+product.getParentExternalId()+".jpg");
			productLogo.setOnerror("this.onerror=null;this.src='"+providersImageLocation+product.getProviderExternalId()+".jpg'");
		}
		else{
			productLogo.setSrc(providersImageLocation+product.getProviderExternalId()+".jpg");
		}

		productLogo.setStyle("border:none;text-align: center;");
		productLogo.setTitle1(product.getPoints()+"|"+product.getProviderExternalId());
		productLogo.setTitle("Points : " +(product.getPoints()==-1?"NA":product.getPoints()) +", Product External Id : " + product.getExternalId() );
		productLogo.setAlt(product.getProviderExternalId());

		productLogopp.getContent().add(productLogo);

		//Create Description <div class="productDescription">$19.99 per month for 12 months with a 24 month commitment</div>
		Div productDescription = oFactory.createDiv();
		productDescription.getClazz().add("productDescription");

		logger.info("advisoryPromotion="+ADVISORYPROMOTION);

		if( isExistingCustomer )
		{
			productDescription.getContent().add(ADVISORYPROMOTION);
		}
		else
		{
			String promoDescription = null;
			if(!Constants.ATTV6.equals(product.getProviderExternalId()) && !Constants.ATT.equals(product.getProviderExternalId()) && product.getShortDescription() != null && (!Utils.isBlank(product.getShortDescription()))){
				promoDescription = product.getShortDescription();
				productDescription.getContent().add(promoDescription);
			}else{
				if (product.getPromotionDescription() != null){
					if (!Utils.isBlank(product.getPromotionDescription()) && product.getPromotionDescription().length() >= 307){
						promoDescription = product.getPromotionDescription().substring(0, 302);
						promoDescription  = promoDescription + " ...";
					}else{
						promoDescription = product.getPromotionDescription();
					}
					productDescription.getContent().add(promoDescription);//Real Product Description Should add
				}else{
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
		if((product.getCapabilityMap().get("iptv") != null ||
				product.getCapabilityMap().get("ipDslamIptv") != null ||
				product.getCapabilityMap().get("analogCable") != null ||
				product.getCapabilityMap().get("digitalCable") != null ||
				product.getCapabilityMap().get("satellite") != null)) {
			return true;
		}
		return false;
	}

	private boolean isProductInternetCapable(ProductSummaryVO product) {
		if((product.getCapabilityMap().get("fiberDataDownSpeed") != null || //internet conditions
				product.getCapabilityMap().get("ipDslamDataDownSpeed") != null ||
				product.getCapabilityMap().get("wiredDataDownSpeed") != null ||
				product.getCapabilityMap().get("dialUpInternet") != null)) {
			return true;
		}
		return false;
	}

	private boolean isProductPhoneCapable(ProductSummaryVO product) {
		if((product.getCapabilityMap().get("voip") != null || //phone conditions
				product.getCapabilityMap().get("ipDslamVoip") != null ||
				product.getCapabilityMap().get("localPhone") != null ||
				product.getCapabilityMap().get("longDistancePhone") != null ||
				product.getCapabilityMap().get("wirelessPhone") != null)) {
			return true;
		}
		return false;
	}


	@RequestMapping(value="/channelLineUpData")
	protected ModelAndView channelLineUpData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("channelLineUpData_begin");
		String providerId = request.getParameter("providerExternalId");
		String providerName = request.getParameter("providerName");
		ModelAndView view = new ModelAndView("channel_lineup");
		try{
			String channelLineupDataUrlForProviderId = ConfigRepo.getString("*.provider_channel_lineup_data_url");
			logger.info("channelLineupDataUrlForProviderId="+channelLineupDataUrlForProviderId);
			ChannelLineupVO channelLineupVO = RESTClient.INSTANCE.generateChannelLineupDataByProviderId(providerId, channelLineupDataUrlForProviderId, request);
			if(channelLineupVO != null && channelLineupVO.getResponse() != null && channelLineupVO.getResponse().getStatus() != null 
					&& Constants.CHANNEL_LINEUP_SUCCESS_STATUSCODE.equalsIgnoreCase(channelLineupVO.getResponse().getStatus().getCode())){
				ArrayList<Package>  packages = channelLineupVO.getResponse().getData().getProvider().getPackages().getCluPackage();
				JSONArray packagesWithChannelsRef = new JSONArray();
				int count = 1;
				for( Package packageObj : packages ){
					JSONArray channelRefs = new JSONArray();
					List<Category> categoryList = packageObj.getCategory();
					Category category = null;
					for( Category categoryObj : categoryList ){
						if( categoryObj.getName().equalsIgnoreCase("All") ){
							category = categoryObj;
						}
					}
					if(category != null){
						for( ChannelId channelId : category.getChannelRefs().getChannelId() ){
							channelRefs.put(channelId.getRef());
						}
					}
					JSONObject packWithChannel = new JSONObject();
					packWithChannel.put("count", count);
					packWithChannel.put("packName", packageObj.getName());
					packWithChannel.put("channelRefs", channelRefs);
					packagesWithChannelsRef.put(packWithChannel);
					count++;
				}
				logger.info("packages="+packagesWithChannelsRef.toString());
				List<Channel>  channels = channelLineupVO.getResponse().getData().getChannels().getChannel();
				view.addObject("providerName",providerName);
				view.addObject("packages",packagesWithChannelsRef.toString());
				view.addObject("channels",channels);
				view.addObject("statusMsg","");
			}else if(channelLineupVO != null && channelLineupVO.getResponse() != null 
					&& channelLineupVO.getResponse().getStatus() != null 
					&& !Utils.isBlank(channelLineupVO.getResponse().getStatus().getMsg())){
				view.addObject("providerName",providerName);
				logger.info("Channel_Lineup_Error_msg="+channelLineupVO.getResponse().getStatus().getMsg());
				view.addObject("statusMsg",channelLineupVO.getResponse().getStatus().getMsg());
				view.addObject("packages","[]");
				view.addObject("channels","{}");
			}else{
				view.addObject("providerName",providerName);
				view.addObject("statusMsg","ChannelData not available");
				view.addObject("packages","[]");
				view.addObject("channels","{}");
			}
			logger.info("channelLineUpData_end");

		} catch (Exception e) {
			logger.error("Error_occured_while_getChannelUpData", e);
		}
		return view;
	}

	private void populateRecIconMap(
			Map<String, List<ProductSummaryVO>> productByIconMap,RequestContext requestContext ) {
		HttpServletRequest request = (HttpServletRequest)requestContext.getExternalContext().getNativeRequest();
		HttpSession session = request.getSession();
		String path = requestContext.getFlowExecutionUrl()+"&_eventId=recommendationsEvent&CategoryName=";
		String pathCategory = requestContext.getFlowExecutionUrl()+"&_eventId=recommendationsByCategoryEvent&CategoryName=";

		Map<String, Object> recIconMap = new HashMap<String, Object>();
		String[] productArr = {"TRIPLE_PLAY", "DOUBLE_PLAY", "VIDEO", "PHONE", "INTERNET", "HOMESECURITY", "ELECTRICITY", "WATER", "APPLIANCEPROTECTION", "NATURALGAS", "WASTEREMOVAL", "ASIS_PLAN","OFFERS", "BUNDLES","MIXEDBUNDLES"};
		for(int k = 0; k<productArr.length; k++){

			if (productByIconMap.get(productArr[k]) != null &&	productByIconMap.get(productArr[k]).size() > 0){
				if (productArr[k].equalsIgnoreCase("PP")) {
					recIconMap.put(productArr[k], path+productArr[k]);
				} else {
					recIconMap.put(productArr[k], pathCategory+productArr[k]);
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
		logger.debug("recIconMap="+recIconMap);
		request.getSession().setAttribute("recIconMap", recIconMap);
	}



	private ProductVOJSON buildProductJSONVO(ProductSummaryVO product,ProductResultsManager productResultManager,String categoryName){
		ProductVOJSON  productVOJSON = new ProductVOJSON();
		String providersData = ConfigRepo.getString("*.channelLineupProviders") == null ? null : ConfigRepo.getString("*.channelLineupProviders");
		String providerExtId = product.getParentExternalId()!=null?product.getParentExternalId():product.getProviderExternalId();
		String providerName = escapeSpecialCharacters(product.getProviderName());
		DecimalFormat df = new DecimalFormat("#.##");
		logger.info("providersData="+providersData);
		if(!Utils.isBlank(providersData)){
			String providersList[] = providersData.split("\\|");
			for (String providerIdWithName : providersList){
				String providerValues[] = providerIdWithName.split("=");
				if(providerValues[0].trim().equalsIgnoreCase(providerExtId)){
					productVOJSON.setChannelLineupProvider(true);
					if(Utils.isBlank(providerName)){
						providerName = providerValues[1];
					}
					break;
				}
			}
		}
		if (providerName.equalsIgnoreCase("ATTSTI")){
			providerName = "AT&T";
		}else if (providerName.equalsIgnoreCase("DISH Network")){
			providerName = "Dish";
		}
		productVOJSON.setProductIconList(new ArrayList<String>());
		if(isProductVideoCapable(product)) {
			productVOJSON.getProductIconList().add("tv");
			productVOJSON.setVideoCapable(true);
		}
		if(isProductPhoneCapable(product)) {
			productVOJSON.getProductIconList().add("phone");
		}
		if(isProductInternetCapable(product)) {
			productVOJSON.getProductIconList().add("internet");
		}
		if( !productResultManager.getSelectedExistingProvidersMap().isEmpty() && productResultManager.getSelectedExistingProvidersMap().containsValue(product.getProviderExternalId()) ){
			productVOJSON.setExistingCustomer(true);
		}
		List<ProductPromotionType> promotions = product.getPromotions();
		if(promotions != null && !promotions.isEmpty()){
			productVOJSON.setPromotions(promotions);
		}
		if(product.getPromotionDescription() != null){
			productVOJSON.setPromotionDescription(product.getPromotionDescription());
		}
		if (product.getPromotionType() != null &&  product.getPromotionType().equalsIgnoreCase("baseMonthlyDiscount") ){
			if (product.getPromotionPriceValueType() != null && product.getPromotionPriceValueType().equalsIgnoreCase("absolute")){
				productVOJSON.setPromoPrice(Double.valueOf(product.getPromotionPrice()));
				productVOJSON.setCondition(product.getConditions());
			}else if (product.getPromotionPriceValueType() != null && product.getPromotionPriceValueType().equalsIgnoreCase("relative")){
				productVOJSON.setPromoPrice(product.getBaseRecurringPrice() - product.getPromotionPrice());
				productVOJSON.setCondition(product.getConditions());
			}
		}else{
			ProductPromotionType productPromoType = SalesUtilsFactory.INSTANCE.isBaseMonthlyAvailable(product);
			if(productPromoType != null){
				if (Constants.ABSOLUTE.equalsIgnoreCase(productPromoType.getPriceValueType())){
					productVOJSON.setPromoPrice(Double.valueOf(productPromoType.getPriceValue()));
				}else if (Constants.RELATIVE.equalsIgnoreCase(productPromoType.getPriceValueType())){
					productVOJSON.setPromoPrice(product.getBaseRecurringPrice() - productPromoType.getPriceValue());
				}
				product.setShortDescription(productPromoType.getShortDescription());
			}
		}

		Map<Float,Float> promoTreeSplitMap = new TreeMap<Float, Float>();
		if (Constants.ATTV6.equals(providerExtId) ||Constants.ATT.equals(providerExtId)){
			if(!product.getPromotions().isEmpty()){
				double promoTotal = 0.0;
				boolean isRelativePromotionExist = false;
				for(ProductPromotionType promo : product.getPromotions()) {
					if (promo.getType() != null &&  promo.getType().equalsIgnoreCase("baseMonthlyDiscount") ){
						if (promo.getPriceValueType() != null && promo.getPriceValueType().equalsIgnoreCase("relative")){
							promoTotal = promoTotal + promo.getPriceValue();
							isRelativePromotionExist = true;
						}else if (promo.getPriceValueType() != null && promo.getPriceValueType().equalsIgnoreCase("absolute")){
							promoTreeSplitMap.put(promo.getPriceValue(), promo.getPriceValue());
						}
					}
				}
				if (promoTreeSplitMap != null && !promoTreeSplitMap.isEmpty() && promoTreeSplitMap.size() > 0){
					for (Float value :promoTreeSplitMap.keySet()){
						productVOJSON.setPromoPrice(value.doubleValue());
						break;
					}
				}else if(isRelativePromotionExist){
					productVOJSON.setPromoPrice(product.getBaseRecurringPrice() - promoTotal);
				}
			}	
		}

		if(product.getPromotionType() != null &&  product.getPromotionCode().equals("Free Installation")){
			productVOJSON.setBaseNonRecurringPrice(0.00);
		}else{
			productVOJSON.setBaseNonRecurringPrice(product.getBaseNonRecurringPrice());
		}
		logger.info("advisoryPromotion="+ADVISORYPROMOTION);
		if( productVOJSON.isExistingCustomer()){
			productVOJSON.setProductDescription(ADVISORYPROMOTION);
		}else{
			if (!Constants.ATTV6.equals(product.getProviderExternalId())&& !Constants.ATT.equals(product.getProviderExternalId()) && product.getShortDescription() != null){
				if(Constants.COX_RTS_PROVIDER_ID.equals(product.getProviderExternalId())
						&& SalesUtilsFactory.INSTANCE.isBaseMonthlyAvailable(product) != null
						&& !Utils.isBlank(SalesUtilsFactory.INSTANCE.getInformationalPromoShortDescription(product))){
					productVOJSON.setProductDescription(product.getShortDescription()+"</br>"+SalesUtilsFactory.INSTANCE.getInformationalPromoShortDescription(product));
				}else{
					productVOJSON.setProductDescription(product.getShortDescription());
				}
			}else if(Constants.ATTV6.equals(product.getProviderExternalId()) 
					&& (Constants.ATT_BUILD_PRODUCT_EXID.equals(product.getExternalId())
					|| Constants.ATT_BUILD_PRODUCT_EXID_TRANSFER.equals(product.getExternalId()))){
				productVOJSON.setProductDescription(product.getDescriptiveInfoValue());
			}else{
				if (product.getPromotionDescription() != null){
					productVOJSON.setProductDescription(product.getPromotionDescription());
				}else{
					productVOJSON.setProductDescription("");
				}
			}
		}
		productVOJSON.setFilterMetaDatMap(new HashMap<String,String>());
		if (product.getPromotionMetaDataList() != null && product.getPromotionMetaDataList().size() > 0){
			for (String metaData : product.getPromotionMetaDataList()){
				if (!Utils.isBlank(metaData)){
					for(String metaDataName:SalesUtil.INSTANCE.metaDataKeyArray){
						if(metaData.contains("=") && metaData.split("=").length > 1&& metaDataName.equalsIgnoreCase(metaData.split("=")[0])){
							productVOJSON.getFilterMetaDatMap().put(metaData.split("=")[0], metaData.split("=")[1]);
						}else if(metaDataName.equalsIgnoreCase(metaData.split("=")[0])){
							productVOJSON.getFilterMetaDatMap().put(metaData.split("=")[0], metaData.split("=")[0]);
						}
					}
				}
			}
		}
		if(product.getParentExternalId() != null){
			productVOJSON.setImageID(product.getParentExternalId());
		}else{
			productVOJSON.setImageID(product.getProviderExternalId());
		}
		if(Constants.ATTV6.equals(product.getProviderExternalId()) 
				&& productResultManager.isATTProductHasSatellite(product)){
			productVOJSON.setImageID(Constants.ATT_DIRECTV);
			productVOJSON.setExistingCustomer(false);
		}
		if(!Utils.isBlank(product.getEnergyUnitName())){
			productVOJSON.setUnitName("/"+product.getEnergyUnitName());
		}
		if(product.getEnergyTierMap() != null && !product.getEnergyTierMap().isEmpty()){
			productVOJSON.setEnergyTierMap(product.getEnergyTierMap());
			if(product.getEnergyTierMap().get(Constants.ENERGY_RATE) != null){
				productVOJSON.setUsageRate(product.getEnergyTierMap().get(Constants.ENERGY_RATE));
			}else{
				for(Entry<String,Double> map : product.getEnergyTierMap().entrySet()){
					productVOJSON.setUsageRate(map.getValue());
					break;
				}
			}
		}
		if(Constants.ATTV6.equals(product.getProviderExternalId()) 
				&& (product.getName().contains(Constants.BUILD_YOUR_BUNDLE) 
						|| Constants.ATT_BUILD_PRODUCT_EXID.equals(product.getExternalId())
						|| Constants.ATT_BUILD_PRODUCT_EXID_TRANSFER.equals(product.getExternalId()))){
			if (categoryName.toUpperCase() != null && categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY")){
				productVOJSON.getProductIconList().add("phone");
				productVOJSON.getProductIconList().add("internet");
			}else if(categoryName.toUpperCase() != null && categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_VI") ){
				productVOJSON.getProductIconList().add("tv");
				productVOJSON.getProductIconList().add("internet");
			}else if (categoryName.toUpperCase() != null && categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_PI")){
				productVOJSON.getProductIconList().add("phone");
				productVOJSON.getProductIconList().add("internet");
			}else if(categoryName.toUpperCase() != null && categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_PV") ){
				productVOJSON.getProductIconList().add("tv");
				productVOJSON.getProductIconList().add("phone");
			}else{
				productVOJSON.getProductIconList().add("tv");
				productVOJSON.getProductIconList().add("phone");
				productVOJSON.getProductIconList().add("internet");
			}
			productVOJSON.setBaseRecNA(Constants.NA);
		}
		
		if(product.getBaseRecurringPrice() != null 
				&& productVOJSON.getPromoPrice() != null){
			Double	promoRoundPrice = Math.round(productVOJSON.getPromoPrice() * 100.0) / 100.0;
			Double	baseRecRoundPrice = Math.round(product.getBaseRecurringPrice() * 100.0) / 100.0;
			if(promoRoundPrice.equals(baseRecRoundPrice)){
				productVOJSON.setPromoPrice(null);
				productVOJSON.setProductDescription("");
			}
		}
		productVOJSON.setCapabilitMap(product.getCapabilityMap());
		productVOJSON.setProductPointDisplay(product.getPoints() == -1 ? "NA":String.valueOf(product.getPoints()));
		productVOJSON.setProductScore(product.getScore());
		
		productVOJSON.setBaseRecurringPrice(product.getBaseRecurringPrice());
		productVOJSON.setBaseRecurringPrice(product.getBaseRecurringPrice());
		productVOJSON.setHiddenProductJSON(StringEscapeUtils.unescapeHtml(product.getProdJson().toString()));
		productVOJSON.setProductType(product.getProductType());
		productVOJSON.setProviderName(providerName);
		productVOJSON.setProductName(product.getName());
		productVOJSON.setProviderExtId(product.getProviderExternalId());
		productVOJSON.setParentProviderExtId(providerExtId);
		productVOJSON.setProductExID(product.getExternalId());
		productVOJSON.setSortPrice(getProductSortPrice(product,productResultManager));
		try{
		if(productVOJSON.getFilterMetaDatMap() != null && productVOJSON.getFilterMetaDatMap().get("NUM_CHANNELS") != null && !productVOJSON.getFilterMetaDatMap().get("NUM_CHANNELS").equalsIgnoreCase("NA")){
			productVOJSON.setChannels(Float.valueOf(productVOJSON.getFilterMetaDatMap().get("NUM_CHANNELS")));
			productVOJSON.setChannelsCount(Float.valueOf(productVOJSON.getFilterMetaDatMap().get("NUM_CHANNELS")));
		}else{
			productVOJSON.setChannels(Float.valueOf(0));
			//productVOJSON.setChannelsCount(Float.valueOf(0));
		}
		if(productVOJSON.getFilterMetaDatMap() != null && productVOJSON.getFilterMetaDatMap().get("CONN_SPEED") != null && !productVOJSON.getFilterMetaDatMap().get("CONN_SPEED").equalsIgnoreCase("NA")){
			productVOJSON.setConnSpeed(Float.valueOf(productVOJSON.getFilterMetaDatMap().get("CONN_SPEED")));
			if(Float.valueOf(productVOJSON.getFilterMetaDatMap().get("CONN_SPEED")) >= 1000){
				productVOJSON.setConnectionSpeedCount(String.valueOf(Float.valueOf(productVOJSON.getFilterMetaDatMap().get("CONN_SPEED"))/1000)+"G");
			}else{
				productVOJSON.setConnectionSpeedCount(String.valueOf(Float.valueOf(productVOJSON.getFilterMetaDatMap().get("CONN_SPEED")))+"M");
			}
			//productVOJSON.setConnectionSpeedCount(Float.valueOf(productVOJSON.getFilterMetaDatMap().get("CONN_SPEED")));
		}else{
			productVOJSON.setConnSpeed(Float.valueOf(0));
			//productVOJSON.setConnectionSpeedCount(Float.valueOf(0));
		}
		if(productVOJSON.getFilterMetaDatMap() != null && productVOJSON.getFilterMetaDatMap().get("CONTRACT_TERM") != null && !productVOJSON.getFilterMetaDatMap().get("CONTRACT_TERM").equalsIgnoreCase("NA")){
			productVOJSON.setContractTerm(Float.valueOf(productVOJSON.getFilterMetaDatMap().get("CONTRACT_TERM")));
		}
		}catch(Exception e){
			logger.error("error_while_getting ProductData"+e.getMessage());
		}
		if(product.getDisplayBasePrice()!= null){
			productVOJSON.setDisplayBasePrice(product.getDisplayBasePrice());
		}
		if(product.getDisplayPromotionPrice()!= null){
			productVOJSON.setDisplayPromotionPrice(product.getDisplayPromotionPrice());
		}
		
		if(product.getDisplayPromotionPrice()!= null){
			productVOJSON.setSortPrice(product.getDisplayPromotionPrice());
		}
		else if(productVOJSON.getPromoPrice()!= null){
			productVOJSON.setSortPrice(productVOJSON.getPromoPrice());
		}
		else if(product.getDisplayBasePrice() != null){
			productVOJSON.setSortPrice(product.getDisplayBasePrice());
		}
		if(!Utils.isBlank(product.getDisplayPricingGrid()) && !product.isSyntheticBundle() && isDisplayPricingGridNotEmpty(product.getDisplayPricingGrid())){
			productVOJSON.setPricingGridJson(buildPriceGrid(product.getDisplayPricingGrid(),product.getProviderExternalId()));
		}
		if(product.getPairedProduct() != null){
			ProductVOJSON  pairedProductVOJSON = buildMixedBundleProductJSONVO(product.getPairedProduct(),productResultManager,categoryName);
			productVOJSON.setSortPrice(productVOJSON.getSortPrice() + pairedProductVOJSON.getSortPrice());
			productVOJSON.setTotalPoints((product.getPoints() == -1 ? 0:product.getPoints())+(product.getPairedProduct().getPoints() == -1 ? 0:product.getPairedProduct().getPoints()));
			productVOJSON.setTotalPrice((product.getBaseRecurringPrice()+product.getPairedProduct().getBaseRecurringPrice()));
			productVOJSON.setPairedProduct(SalesUtilsFactory.INSTANCE.toJson(pairedProductVOJSON));
			productVOJSON.setPairedProductVOJSON(pairedProductVOJSON);
			productVOJSON.setProductScore(product.getScore() + pairedProductVOJSON.getProductScore());
		
			if(productVOJSON.getPromoPrice() != null && pairedProductVOJSON.getPromoPrice() != null){
				productVOJSON.setTotalPromotionPrice(productVOJSON.getPromoPrice() + pairedProductVOJSON.getPromoPrice());
			}else if(productVOJSON.getPromoPrice() == null && pairedProductVOJSON.getPromoPrice() != null){
				if(productVOJSON.getDisplayBasePrice()!= null){
					productVOJSON.setTotalPromotionPrice(productVOJSON.getDisplayBasePrice() + pairedProductVOJSON.getPromoPrice());
				}
				else{
					productVOJSON.setTotalPromotionPrice(productVOJSON.getBaseRecurringPrice() + pairedProductVOJSON.getPromoPrice());
				}
			}else if(productVOJSON.getPromoPrice() != null && pairedProductVOJSON.getPromoPrice() == null){
				productVOJSON.setTotalPromotionPrice(pairedProductVOJSON.getBaseRecurringPrice() + productVOJSON.getPromoPrice());
			}else{
				productVOJSON.setTotalPromotionPrice(0.0);
			}
			
			if(pairedProductVOJSON.getDisplayBasePrice() != null){
				if(productVOJSON.getDisplayBasePrice() != null){
					productVOJSON.setTotalDisplayBasePrice(productVOJSON.getDisplayBasePrice() + pairedProductVOJSON.getDisplayBasePrice());
					
				}else{
					productVOJSON.setTotalDisplayBasePrice(pairedProductVOJSON.getDisplayBasePrice() + productVOJSON.getBaseRecurringPrice());
				}
			}else if(productVOJSON.getDisplayBasePrice() != null){
				productVOJSON.setTotalDisplayBasePrice(productVOJSON.getDisplayBasePrice() + pairedProductVOJSON.getBaseRecurringPrice());
			}
			
			if(pairedProductVOJSON.getDisplayPromotionPrice() != null){
				if(productVOJSON.getDisplayPromotionPrice() != null){
					productVOJSON.setTotalDisplayPromotionPrice(productVOJSON.getDisplayPromotionPrice() + pairedProductVOJSON.getDisplayPromotionPrice());
				}else{
					if(productVOJSON.getPromoPrice() != null){
						productVOJSON.setTotalDisplayPromotionPrice(pairedProductVOJSON.getDisplayPromotionPrice() + productVOJSON.getPromoPrice());
					}else{
						if(productVOJSON.getDisplayBasePrice() != null){
							productVOJSON.setTotalDisplayPromotionPrice(pairedProductVOJSON.getDisplayPromotionPrice() + productVOJSON.getDisplayBasePrice());

						}else{
							productVOJSON.setTotalDisplayPromotionPrice(pairedProductVOJSON.getDisplayPromotionPrice() + productVOJSON.getBaseRecurringPrice());
						}
					}
				}
			}else if(productVOJSON.getDisplayPromotionPrice() != null){
				if(pairedProductVOJSON.getPromoPrice() != null){
					productVOJSON.setTotalDisplayPromotionPrice(productVOJSON.getDisplayPromotionPrice() + pairedProductVOJSON.getPromoPrice());
				}else{
					productVOJSON.setTotalDisplayPromotionPrice(productVOJSON.getDisplayPromotionPrice() + pairedProductVOJSON.getBaseRecurringPrice());
				}
			}else if(productVOJSON.getTotalPromotionPrice() != null && productVOJSON.getTotalPromotionPrice() !=0.0){
				//productVOJSON.setTotalDisplayPromotionPrice(productVOJSON.getTotalPromotionPrice());
			}
			if(productVOJSON.getTotalDisplayPromotionPrice() != null){
				productVOJSON.setSortPrice(productVOJSON.getTotalDisplayPromotionPrice());
			}else if(productVOJSON.getTotalPromotionPrice() != null && productVOJSON.getTotalPromotionPrice() !=0.0){
				productVOJSON.setSortPrice(productVOJSON.getTotalPromotionPrice());
			}
			else if(productVOJSON.getTotalDisplayBasePrice() != null){
				productVOJSON.setSortPrice(productVOJSON.getTotalDisplayBasePrice());
			}
			/*if(productVOJSON.getTotalPromotionPrice() != null && productVOJSON.getTotalDisplayPromotionPrice() == null && Double.valueOf(productVOJSON.getTotalPromotionPrice()) != 0.0 ){
				productVOJSON.setSortPrice(productVOJSON.getTotalPromotionPrice());
			}else if(productVOJSON.getTotalDisplayPromotionPrice() != null && productVOJSON.getTotalPromotionPrice() == null && Double.valueOf(productVOJSON.getTotalDisplayPromotionPrice()) != 0.0 ){
				productVOJSON.setSortPrice(productVOJSON.getTotalDisplayPromotionPrice());
			}*/
						
			if(pairedProductVOJSON.getFilterMetaDatMap() != null && pairedProductVOJSON.getFilterMetaDatMap().get("NUM_CHANNELS") != null && !pairedProductVOJSON.getFilterMetaDatMap().get("NUM_CHANNELS").equalsIgnoreCase("NA")){
				productVOJSON.setChannels(Float.valueOf(productVOJSON.getChannels()) + Float.valueOf(pairedProductVOJSON.getFilterMetaDatMap().get("NUM_CHANNELS")));
			}
			if(pairedProductVOJSON.getFilterMetaDatMap() != null && pairedProductVOJSON.getFilterMetaDatMap().get("CONN_SPEED") != null && !pairedProductVOJSON.getFilterMetaDatMap().get("CONN_SPEED").equalsIgnoreCase("NA")){
				productVOJSON.setConnSpeed(Float.valueOf(productVOJSON.getConnSpeed()) + Float.valueOf(pairedProductVOJSON.getFilterMetaDatMap().get("CONN_SPEED")));
			}
			if(product.isSyntheticBundle() && pairedProductVOJSON != null && !Utils.isBlank(product.getDisplayPricingGrid()) && isDisplayPricingGridNotEmpty(product.getDisplayPricingGrid())){
				try {
					Double pairedProductpromoPrice = 0.0d;
					if(pairedProductVOJSON != null){
						if(pairedProductVOJSON.getDisplayPromotionPrice() != null ){
							pairedProductpromoPrice = pairedProductVOJSON.getDisplayPromotionPrice();
						}
						else if(pairedProductVOJSON.getPromoPrice() != null ){
							pairedProductpromoPrice = pairedProductVOJSON.getPromoPrice();
						}else if(pairedProductVOJSON.getBaseRecurringPrice() != null){
							pairedProductpromoPrice = pairedProductVOJSON.getBaseRecurringPrice();
						}
					}
					JSONObject jsonObj = new  JSONObject(product.getDisplayPricingGrid());
		            JSONArray arr = new JSONArray();
		        	arr.put("Plus with Hopper");
		        	arr.put("Total Mixed Bundle Price");
		            
		            JSONObject dvrObj = (JSONObject) jsonObj.opt("NO_DVR");
		            Map<String, Map<String, String>> pricingGridJsonMap = new LinkedHashMap<String, Map<String, String>>();
		           
		            for (int j = 1 ; j < dvrObj.length()+1; j++) {
		            	 JSONObject Tvs = (JSONObject) dvrObj.opt("TV"+j);
		            	 if(pricingGridJsonMap.get("TV"+j) == null){
		                	 pricingGridJsonMap.put("TV"+j, new LinkedHashMap<String, String>());
		                 }
		                 Map<String, String> priceData = pricingGridJsonMap.get("TV"+j);
		                 priceData.put("NoOfTv",j+" TV");
		                 String val = "";
		                 
		                 if(Tvs.opt("MTH") != null && Tvs.opt("MTH") != ""){
		                	 val = (String)Tvs.opt("MTH");
		                 } else if(Tvs.opt("M2M") != null && Tvs.opt("M2M") != ""){
		                	 val = (String)Tvs.opt("M2M");
		                 }
		                 
		                 String baseVal = "";
		                 String headerData = "";
		                 String Months = "";
		                 if(Tvs.opt("A24M") != null && Tvs.opt("A24M") != ""){
		                     baseVal = (String)Tvs.opt("A24M");
		                     headerData = "After 24 Months";
		                     Months = "A24M";
		                 } else if(Tvs.opt("A12M") != null && Tvs.opt("A12M") != ""){
		                     baseVal = (String)Tvs.opt("A12M");
		                     headerData = "After 12 Months";
		                     Months = "A12M";
		                   }
		                 priceData.put("header1","Monthly Price");
		                 priceData.put("header2",headerData);
		                 val = val.replace("$", "");
		                 baseVal = baseVal.replace("$", "");
		                 Double  MTHprice = Double.parseDouble(val);
		                 Double AMPrice = Double.parseDouble(baseVal);
		                 priceData.put("NO_DVR_MTH","$"+df.format(MTHprice));
		                 priceData.put("NO_DVR_"+Months,"$"+df.format(AMPrice));
		                 Double MTHtotal = MTHprice + pairedProductpromoPrice;
		                 Double AMTotal = AMPrice + pairedProductpromoPrice;

		                 priceData.put("TOTAL_MTH", "$"+df.format(MTHtotal));
		                 priceData.put("TOTAL_A12M", "$"+df.format(AMTotal));
		                 pricingGridJsonMap.put("TV"+j, priceData);
		                 
		            }
		            JSONObject bundlePriceJsonData  = new JSONObject(pricingGridJsonMap);
		            JSONObject pricingGridJsonData  = new JSONObject();
		            pricingGridJsonData.put("headers",arr);
		            pricingGridJsonData.put("PRICE_JSON",bundlePriceJsonData);
					productVOJSON.setPricingGridJson(pricingGridJsonData.toString());
					
				} catch (Exception e) {
					logger.error("error_while_getting DisplayPricingGrid"+e.getMessage());
				}
			}else if(!Utils.isBlank(product.getDisplayPricingGrid()) && isDisplayPricingGridNotEmpty(product.getDisplayPricingGrid())){
				productVOJSON.setPricingGridJson(buildPriceGrid(product.getDisplayPricingGrid(),product.getProviderExternalId()));
			}
		}else if(!Utils.isBlank(product.getDisplayPricingGrid()) && isDisplayPricingGridNotEmpty(product.getDisplayPricingGrid())){
			productVOJSON.setPricingGridJson(buildPriceGrid(product.getDisplayPricingGrid(),product.getProviderExternalId()));
		}
		return productVOJSON;
	}
	private boolean isDisplayPricingGridNotEmpty(String displayPricingGrid) {
		
		JSONObject jsonObj = null;
		try {
			jsonObj = new  JSONObject(displayPricingGrid);
			if(jsonObj.length() != 0){
				return true;
			}
		} catch (JSONException e) {
			logger.warn("Error getting while DisplayPricingGrid"+e.getMessage());
		}
		return false;
	}
	private ProductVOJSON buildMixedBundleProductJSONVO(ProductSummaryVO product,ProductResultsManager productResultManager,String categoryName){
		ProductVOJSON  productVOJSON = new ProductVOJSON();
		String providersData = ConfigRepo.getString("*.channelLineupProviders") == null ? null : ConfigRepo.getString("*.channelLineupProviders");
		String providerExtId = product.getParentExternalId()!=null?product.getParentExternalId():product.getProviderExternalId();
		String providerName = escapeSpecialCharacters(product.getProviderName());
		logger.info("providersData="+providersData);
		if(!Utils.isBlank(providersData)){
			String providersList[] = providersData.split("\\|");
			for (String providerIdWithName : providersList){
				String providerValues[] = providerIdWithName.split("=");
				if(providerValues[0].trim().equalsIgnoreCase(providerExtId)){
					productVOJSON.setChannelLineupProvider(true);
					if(Utils.isBlank(providerName)){
						providerName = providerValues[1];
					}
					break;
				}
			}
		}
		if (providerName.equalsIgnoreCase("ATTSTI")){
			providerName = "AT&T";
		}else if (providerName.equalsIgnoreCase("DISH Network")){
			providerName = "Dish";
		}
		productVOJSON.setProductIconList(new ArrayList<String>());
		if(isProductVideoCapable(product)) {
			productVOJSON.getProductIconList().add("tv");
			productVOJSON.setVideoCapable(true);
		}
		if(isProductPhoneCapable(product)) {
			productVOJSON.getProductIconList().add("phone");
		}
		if(isProductInternetCapable(product)) {
			productVOJSON.getProductIconList().add("internet");
		}
		if( !productResultManager.getSelectedExistingProvidersMap().isEmpty() && productResultManager.getSelectedExistingProvidersMap().containsValue(product.getProviderExternalId()) ){
			productVOJSON.setExistingCustomer(true);
		}
		List<ProductPromotionType> promotions = product.getPromotions();
		if(promotions != null && !promotions.isEmpty()){
			productVOJSON.setPromotions(promotions);
		}
		if(product.getPromotionDescription() != null){
			productVOJSON.setPromotionDescription(product.getPromotionDescription());
		}
		
		if (product.getPromotionType() != null &&  product.getPromotionType().equalsIgnoreCase("baseMonthlyDiscount") ){
			if (product.getPromotionPriceValueType() != null && product.getPromotionPriceValueType().equalsIgnoreCase("absolute")){
				productVOJSON.setPromoPrice(Double.valueOf(product.getPromotionPrice()));
				productVOJSON.setCondition(product.getConditions());
			}else if (product.getPromotionPriceValueType() != null && product.getPromotionPriceValueType().equalsIgnoreCase("relative")){
				productVOJSON.setPromoPrice(product.getBaseRecurringPrice() - product.getPromotionPrice());
				productVOJSON.setCondition(product.getConditions());
			}
		}else{
			ProductPromotionType productPromoType = SalesUtilsFactory.INSTANCE.isBaseMonthlyAvailable(product);
			if(productPromoType != null){
				if (Constants.ABSOLUTE.equalsIgnoreCase(productPromoType.getPriceValueType())){
					productVOJSON.setPromoPrice(Double.valueOf(productPromoType.getPriceValue()));
				}else if (Constants.RELATIVE.equalsIgnoreCase(productPromoType.getPriceValueType())){
					productVOJSON.setPromoPrice(product.getBaseRecurringPrice() - productPromoType.getPriceValue());
				}
				product.setShortDescription(productPromoType.getShortDescription());
			}
		}

		Map<Float,Float> promoTreeSplitMap = new TreeMap<Float, Float>();
		if (Constants.ATTV6.equals(providerExtId) ||Constants.ATT.equals(providerExtId)){
			if(!product.getPromotions().isEmpty()){
				double promoTotal = 0.0;
				boolean isRelativePromotionExist = false;
				for(ProductPromotionType promo : product.getPromotions()) {
					if (promo.getType() != null &&  promo.getType().equalsIgnoreCase("baseMonthlyDiscount") ){
						if (promo.getPriceValueType() != null && promo.getPriceValueType().equalsIgnoreCase("relative")){
							promoTotal = promoTotal + promo.getPriceValue();
							isRelativePromotionExist = true;
						}else if (promo.getPriceValueType() != null && promo.getPriceValueType().equalsIgnoreCase("absolute")){
							promoTreeSplitMap.put(promo.getPriceValue(), promo.getPriceValue());
						}
					}
				}
				if (promoTreeSplitMap != null && !promoTreeSplitMap.isEmpty() && promoTreeSplitMap.size() > 0){
					for (Float value :promoTreeSplitMap.keySet()){
						productVOJSON.setPromoPrice(value.doubleValue());
						break;
					}
				}else if(isRelativePromotionExist){
					productVOJSON.setPromoPrice(product.getBaseRecurringPrice() - promoTotal);
				}
			}	
		}

		if(product.getPromotionType() != null &&  product.getPromotionCode().equals("Free Installation")){
			productVOJSON.setBaseNonRecurringPrice(0.00);
		}else{
			productVOJSON.setBaseNonRecurringPrice(product.getBaseNonRecurringPrice());
		}
		logger.info("advisoryPromotion="+ADVISORYPROMOTION);
		if( productVOJSON.isExistingCustomer()){
			productVOJSON.setProductDescription(ADVISORYPROMOTION);
		}else{
			if (!Constants.ATTV6.equals(product.getProviderExternalId())&& !Constants.ATT.equals(product.getProviderExternalId()) && product.getShortDescription() != null){
				if(Constants.COX_RTS_PROVIDER_ID.equals(product.getProviderExternalId())
						&& SalesUtilsFactory.INSTANCE.isBaseMonthlyAvailable(product) != null
						&& !Utils.isBlank(SalesUtilsFactory.INSTANCE.getInformationalPromoShortDescription(product))){
					productVOJSON.setProductDescription(product.getShortDescription()+"</br>"+SalesUtilsFactory.INSTANCE.getInformationalPromoShortDescription(product));
				}else{
					productVOJSON.setProductDescription(product.getShortDescription());
				}
			}else if(Constants.ATTV6.equals(product.getProviderExternalId()) 
					&& (Constants.ATT_BUILD_PRODUCT_EXID.equals(product.getExternalId())
					|| Constants.ATT_BUILD_PRODUCT_EXID_TRANSFER.equals(product.getExternalId()))){
				productVOJSON.setProductDescription(product.getDescriptiveInfoValue());
			}else{
				if (product.getPromotionDescription() != null){
					productVOJSON.setProductDescription(product.getPromotionDescription());
				}else{
					productVOJSON.setProductDescription("");
				}
			}
		}
		productVOJSON.setFilterMetaDatMap(new HashMap<String,String>());
		if (product.getPromotionMetaDataList() != null && product.getPromotionMetaDataList().size() > 0){
			for (String metaData : product.getPromotionMetaDataList()){
				if (!Utils.isBlank(metaData)){
					for(String metaDataName:SalesUtil.INSTANCE.metaDataKeyArray){
						if(metaData.contains("=") && metaData.split("=").length > 1&& metaDataName.equalsIgnoreCase(metaData.split("=")[0])){
							productVOJSON.getFilterMetaDatMap().put(metaData.split("=")[0], metaData.split("=")[1]);
						}else if(metaDataName.equalsIgnoreCase(metaData.split("=")[0])){
							productVOJSON.getFilterMetaDatMap().put(metaData.split("=")[0], metaData.split("=")[0]);
						}
					}
				}
			}
		}
		if(product.getParentExternalId() != null){
			productVOJSON.setImageID(product.getParentExternalId());
		}else{
			productVOJSON.setImageID(product.getProviderExternalId());
		}
		if(Constants.ATTV6.equals(product.getProviderExternalId()) 
				&& productResultManager.isATTProductHasSatellite(product)){
			productVOJSON.setImageID(Constants.ATT_DIRECTV);
			productVOJSON.setExistingCustomer(false);
		}
		if(!Utils.isBlank(product.getEnergyUnitName())){
			productVOJSON.setUnitName("/"+product.getEnergyUnitName());
		}
		if(product.getEnergyTierMap() != null && !product.getEnergyTierMap().isEmpty()){
			productVOJSON.setEnergyTierMap(product.getEnergyTierMap());
			if(product.getEnergyTierMap().get(Constants.ENERGY_RATE) != null){
				productVOJSON.setUsageRate(product.getEnergyTierMap().get(Constants.ENERGY_RATE));
			}else{
				for(Entry<String,Double> map : product.getEnergyTierMap().entrySet()){
					productVOJSON.setUsageRate(map.getValue());
					break;
				}
			}
		}
		if(Constants.ATTV6.equals(product.getProviderExternalId()) 
				&& (product.getName().contains(Constants.BUILD_YOUR_BUNDLE) 
						|| Constants.ATT_BUILD_PRODUCT_EXID.equals(product.getExternalId())
						|| Constants.ATT_BUILD_PRODUCT_EXID_TRANSFER.equals(product.getExternalId()))){
			if (categoryName.toUpperCase() != null && categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY")){
				productVOJSON.getProductIconList().add("phone");
				productVOJSON.getProductIconList().add("internet");
			}else if(categoryName.toUpperCase() != null && categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_VI") ){
				productVOJSON.getProductIconList().add("tv");
				productVOJSON.getProductIconList().add("internet");
			}else if (categoryName.toUpperCase() != null && categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_PI")){
				productVOJSON.getProductIconList().add("phone");
				productVOJSON.getProductIconList().add("internet");
			}else if(categoryName.toUpperCase() != null && categoryName.toUpperCase().equalsIgnoreCase("DOUBLE_PLAY_PV") ){
				productVOJSON.getProductIconList().add("tv");
				productVOJSON.getProductIconList().add("phone");
			}else{
				productVOJSON.getProductIconList().add("tv");
				productVOJSON.getProductIconList().add("phone");
				productVOJSON.getProductIconList().add("internet");
			}
			productVOJSON.setBaseRecNA(Constants.NA);
		}
		
		if(product.getBaseRecurringPrice() != null 
				&& productVOJSON.getPromoPrice() != null){
			Double	promoRoundPrice = Math.round(productVOJSON.getPromoPrice() * 100.0) / 100.0;
			Double	baseRecRoundPrice = Math.round(product.getBaseRecurringPrice() * 100.0) / 100.0;
			if(promoRoundPrice.equals(baseRecRoundPrice)){
				productVOJSON.setPromoPrice(null);
				productVOJSON.setProductDescription("");
			}
		}
		if(product.getDisplayBasePrice()!= null){
			productVOJSON.setDisplayBasePrice(product.getDisplayBasePrice());
		}
		if(product.getDisplayPromotionPrice()!= null){
			productVOJSON.setDisplayPromotionPrice(product.getDisplayPromotionPrice());
		}
     try{
		if(productVOJSON.getFilterMetaDatMap() != null && productVOJSON.getFilterMetaDatMap().get("CONTRACT_TERM") != null && !productVOJSON.getFilterMetaDatMap().get("CONTRACT_TERM").equalsIgnoreCase("NA")){
			productVOJSON.setContractTerm(Float.valueOf(productVOJSON.getFilterMetaDatMap().get("CONTRACT_TERM")));
		}
		if(productVOJSON.getFilterMetaDatMap() != null && productVOJSON.getFilterMetaDatMap().get("NUM_CHANNELS") != null && !productVOJSON.getFilterMetaDatMap().get("NUM_CHANNELS").equalsIgnoreCase("NA")){
			productVOJSON.setChannelsCount(Float.valueOf(productVOJSON.getFilterMetaDatMap().get("NUM_CHANNELS")));
		}else{
			//productVOJSON.setChannelsCount(Float.valueOf(0));
		}
		if(productVOJSON.getFilterMetaDatMap() != null && productVOJSON.getFilterMetaDatMap().get("CONN_SPEED") != null && !productVOJSON.getFilterMetaDatMap().get("CONN_SPEED").equalsIgnoreCase("NA")){
			if(Float.valueOf(productVOJSON.getFilterMetaDatMap().get("CONN_SPEED")) >= 1000){
				productVOJSON.setConnectionSpeedCount(String.valueOf(Float.valueOf(productVOJSON.getFilterMetaDatMap().get("CONN_SPEED"))/1000)+"G");
			}else{
				productVOJSON.setConnectionSpeedCount(String.valueOf(Float.valueOf(productVOJSON.getFilterMetaDatMap().get("CONN_SPEED")))+"M");
			}
		}else{
			//productVOJSON.setConnectionSpeedCount(Float.valueOf(0));
		}
       }catch(Exception e){ 
	       logger.error("error_while_getting ProductData"+e.getMessage());
       }
		productVOJSON.setCapabilitMap(product.getCapabilityMap());
		productVOJSON.setProductPointDisplay(product.getPoints() == -1 ? "NA":String.valueOf(product.getPoints()));
		productVOJSON.setProductScore(product.getScore());
		productVOJSON.setSortPrice(getProductSortPrice(product,productResultManager));
		productVOJSON.setBaseRecurringPrice(product.getBaseRecurringPrice());
		productVOJSON.setBaseRecurringPrice(product.getBaseRecurringPrice());
		productVOJSON.setHiddenProductJSON(StringEscapeUtils.unescapeHtml(product.getProdJson().toString()));
		productVOJSON.setProductType(product.getProductType());
		productVOJSON.setProviderName(providerName);
		productVOJSON.setProductName(product.getName());
		productVOJSON.setProviderExtId(product.getProviderExternalId());
		productVOJSON.setParentProviderExtId(providerExtId);
		productVOJSON.setProductExID(product.getExternalId());

		if(product.getDisplayPromotionPrice()!= null){
			productVOJSON.setSortPrice(product.getDisplayPromotionPrice());
		}
		else if(productVOJSON.getPromoPrice()!= null){
			productVOJSON.setSortPrice(productVOJSON.getPromoPrice());
		}
		else if(product.getDisplayBasePrice() != null){
			productVOJSON.setSortPrice(product.getDisplayBasePrice());
		}
		return productVOJSON;
	}


	/**
	 * @param summaryVO
	 * @param productResultManager
	 * @return
	 */
	public Double getProductSortPrice(ProductSummaryVO summaryVO,  ProductResultsManager productResultManager){  
		Double sortPrice = summaryVO.getBaseRecurringPrice();
		boolean isBaseMonthly = false;
		if(summaryVO.getPromotionPrice()!=null){
			if(!summaryVO.getPromotions().isEmpty()){
				if(Constants.BASE_MONTHLY_DISCOUNT.equalsIgnoreCase(summaryVO.getPromotionType())){
					if( Constants.ABSOLUTE.equalsIgnoreCase(summaryVO.getPromotionPriceValueType()) ){
						isBaseMonthly = true;
						sortPrice  = Double.valueOf(summaryVO.getPromotionPrice());
					}else if(Constants.RELATIVE.equalsIgnoreCase(summaryVO.getPromotionPriceValueType())){
						isBaseMonthly = true;
						sortPrice = summaryVO.getBaseRecurringPrice() - summaryVO.getPromotionPrice();
					}
				}
				if(isBaseMonthly && summaryVO.getBaseRecurringPrice()!= null 
						&& sortPrice < summaryVO.getBaseRecurringPrice() 
						&& !summaryVO.getProviderExternalId().equals("4353598")){
					//is exiting customer 
					if(productResultManager.getSelectedExistingProvidersMap().containsValue(summaryVO.getProviderExternalId())){
						sortPrice = summaryVO.getBaseRecurringPrice();
					}			
				}
			}
		}
		return sortPrice;
	}
	public Double getProductBaseSortPrice(ProductSummaryVO summaryVO,  ProductResultsManager productResultManager){  
		Double sortPrice = summaryVO.getDisplayBasePrice();
		boolean isBaseMonthly = false;
		if(summaryVO.getPromotionPrice()!=null){
			if(!summaryVO.getPromotions().isEmpty()){
				if(Constants.BASE_MONTHLY_DISCOUNT.equalsIgnoreCase(summaryVO.getPromotionType())){
					if( Constants.ABSOLUTE.equalsIgnoreCase(summaryVO.getPromotionPriceValueType()) ){
						isBaseMonthly = true;
						sortPrice  = Double.valueOf(summaryVO.getPromotionPrice());
					}else if(Constants.RELATIVE.equalsIgnoreCase(summaryVO.getPromotionPriceValueType())){
						isBaseMonthly = true;
						sortPrice = summaryVO.getDisplayBasePrice() - summaryVO.getPromotionPrice();
					}
				}
				if(isBaseMonthly && summaryVO.getDisplayBasePrice()!= null 
						&& sortPrice < summaryVO.getDisplayBasePrice() 
						&& !summaryVO.getProviderExternalId().equals("4353598")){
					//is exiting customer 
					if(productResultManager.getSelectedExistingProvidersMap().containsValue(summaryVO.getProviderExternalId())){
						sortPrice = summaryVO.getDisplayBasePrice();
					}			
				}
			}
		}
		return sortPrice;
	}
	private String buildPriceGrid(String displayPricingGrid, String providerExtId) {
		 
		JSONArray arr = new JSONArray();
		JSONObject priceGridJsonData  = new JSONObject();
		try{
	         
	         JSONObject jsonObj = new  JSONObject(displayPricingGrid);
	         Map<String, Map<String, String>> pricingGridJsonMap = new LinkedHashMap<String, Map<String, String>>(); 
	         
	         for (int i = 1 ; i < jsonObj.length()+1; i++) {
	         	JSONObject dvrObj = null;
	         	String headerValue = "";
	         	if(i == 1){
	                dvrObj = (JSONObject) jsonObj.opt("NO_DVR");
	                if(Constants.DISH.equals(providerExtId)){
	                 headerValue = "NO_DVR";
	                 arr.put("Plus with Hopper");
	                }else{
	                	headerValue = "NO_DVR";
	                	arr.put("NO DVR");
	                }
	               }else if(i == 2){
	                dvrObj = (JSONObject) jsonObj.opt("EN_DVR");
	                headerValue = "EN_DVR";
	                arr.put("ENH DVR");
	               }
	               else if(i == 3){
	                dvrObj = (JSONObject) jsonObj.opt("PR_DVR");
	                headerValue = "PR_DVR";
	                arr.put("PREM DVR");
	               }
	         
		            if(dvrObj != null){
			            for (int j = 1 ; j < dvrObj.length()+1; j++) {
			            	 JSONObject noOfTv = (JSONObject) dvrObj.opt("TV"+j);
			                 String val = (String)noOfTv.get("MTH");
			                 
			                 String baseVal = "";
			                 String headerData = "";
			                 if(noOfTv.opt("A24M") != null && noOfTv.opt("A24M") != ""){
			                     baseVal = (String)noOfTv.opt("A24M");
			                     headerData = "After 24 Months";
			                 }else if(noOfTv.opt("A12M") != null && noOfTv.opt("A12M") != ""){
			                     baseVal = (String)noOfTv.opt("A12M");
			                     headerData = "After 12 Months";
			                 }
			                 if(pricingGridJsonMap.get("TV"+j) == null){
			                	 pricingGridJsonMap.put("TV"+j, new LinkedHashMap<String, String>());
			                 }
			                 Map<String, String> priceData = pricingGridJsonMap.get("TV"+j);
			                 
			                 priceData.put("NoOfTv",j+" TV");
			                 priceData.put("header1","Monthly Price");
			                 priceData.put("header2",headerData);
			                 priceData.put(headerValue+"_MTH", val);
			                 priceData.put(headerValue+"_A12M", baseVal);
			                 pricingGridJsonMap.put("TV"+j, priceData);
			            }
		            }
	         }
	         JSONObject bundlePriceJsonData  = new JSONObject(pricingGridJsonMap);
	         priceGridJsonData.put("headers",arr);
	         priceGridJsonData.put("PRICE_JSON",bundlePriceJsonData);
		} catch (Exception e) {
			logger.error("error_while_buildPriceGrid"+e.getMessage());
		}
		 return priceGridJsonData.toString();
		
	}
}
