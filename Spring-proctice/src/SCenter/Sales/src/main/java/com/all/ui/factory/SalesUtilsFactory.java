package com.AL.ui.factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import com.AL.productResults.managers.ProductResultsManager;
import com.AL.productResults.vo.ProductSummaryVO;
import com.AL.ui.constants.Constants;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.ConsumerVO;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.xml.pr.v4.ProductPromotionType;
import com.AL.xml.v4.AttributeDetailType;
import com.AL.xml.v4.AttributeEntityType;
import com.AL.xml.v4.LineItemAttributeType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderType;
import com.google.gson.Gson;

/**
 * @author pmyneni
 *
 */
public enum SalesUtilsFactory {

	/**
	 * 
	 */
	INSTANCE;

	private static final Logger logger = Logger.getLogger(SalesUtilsFactory.class);

	/**
	 * Checks whether the UtilityOffer is in CKOComplete state or not. Returns false if status is CKOInComplete
	 * 
	 * @param order
	 * @return
	 */
	public boolean isUtilityOfferCompleted(OrderType order){

		if (order.getLineItems().getLineItem() != null) {
			for ( LineItemType lineItem : order.getLineItems().getLineItem() )
			{
				if ( lineItem.getLineItemDetail().getDetailType().equals("product"))
				{
					String productType = getLineItemAttr(lineItem,"PRODUCT_TYPE", "TYPE");
					if (productType != null) {
						if (productType.equalsIgnoreCase("UtilityOffer")) {
							String status = getLineItemAttr(lineItem,"CKO", "STATUS");
							if (status.equalsIgnoreCase("CKOInComplete") || status.equalsIgnoreCase("CKOReady")) {
								return false;
							} else {
								return true;
							}
						}
					}
				}
			}

		}

		return true;
	}

	/**
	 * Gets the LineItemAttribute Value Based on Source and Name
	 * 
	 * @param lineItem
	 * @param entitySrc
	 * @param attrName
	 * @return
	 */
	public String getLineItemAttr(final LineItemType lineItem,
			final String entitySrc,
			final String attrName)

	{
		LineItemAttributeType liAttrib = lineItem.getLineItemAttributes();

		if ( liAttrib == null )
		{
			return "";
		}

		List<AttributeEntityType> entities = liAttrib.getEntity();

		for(AttributeEntityType entity : entities)
		{
			if (entity.getSource() != null
					&& entity.getSource().equalsIgnoreCase(entitySrc))
			{
				for(AttributeDetailType attr : entity.getAttribute())
				{
					if (attr.getName() != null && attr.getName().equalsIgnoreCase(attrName))
					{
						return attr.getValue();
					}
				}
			}
		}

		return "";
	}

	/**
	 * clears the Session values
	 * 
	 * @param request
	 */
	public void clearPreviousSessionInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		MDC.clear();
		session.setAttribute("ambDisconnectdatetime", null);
		session.setAttribute("currentdatetime", null);
		session.setAttribute("callStartTime", null);
		//session.setAttribute("salescontextAuthAMB", null);
		session.setAttribute("idlePageStartTime", null);
		session.setAttribute("dispositionList", null);
		session.setAttribute("wtDisabledProviderIDList", null);
		if (request.getSession().getAttribute("salescontext") != null) {
			SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");

			salesCenterVo.setValueByName("consumer.name.first", null);
			salesCenterVo.setValueByName("consumer.name.middle", null);
			salesCenterVo.setValueByName("consumer.name.last", null);
			salesCenterVo.setValueByName("consumer.name.nameBlock", null);
			salesCenterVo.setValueByName("customer.confirmation.number", null);
			salesCenterVo.setValueByName("referrer.businessParty.referrerId",null);
			salesCenterVo.setValueByName("referrer.businessParty.referrerPhoneNum", null);
			salesCenterVo.setValueByName("referrer.businessParty.callbackNumber", null);
			salesCenterVo.setValueByName("referrer.businessParty.url", null);
			salesCenterVo.setValueByName("referrer.businessParty.cobrandName",null);
			salesCenterVo.setValueByName("referrer.businessParty.referrerName",null);
			salesCenterVo.setValueByName("orderSource.program.name",null);
			salesCenterVo.setValueByName("referrer.dateDelta",null);
			salesCenterVo.setValueByName("order.id", null);
			salesCenterVo.setValueByName("DT Partner", null);
			salesCenterVo.setValueByName("Customer Name", null);
			salesCenterVo.setValueByName("consumer.email", null);
			salesCenterVo.setValueByName("vdnuec", null);
			salesCenterVo.setValueByName("vdn", null);
			salesCenterVo.setValueByName("dtsequenceid", null);
			salesCenterVo.setValueByName("salesFlow.contextId", null);
			salesCenterVo.setValueByName("GUID", null);
			session.setAttribute("isMAMSuccess", null);
			session.setAttribute("ListOfProviderIds",null);
			salesCenterVo.setValueByName("referrer.callType", null);
			salesCenterVo.setValueByName("referrer.flow", null);
			salesCenterVo.setValueByName("simpleChoice.operatingCompanyCode", null);
			salesCenterVo.setValueByName("simpleChoice.operatingCompany", null);
			salesCenterVo.setValueByName("simpleChoice.rateCode", null);
			salesCenterVo.setValueByName("simpleChoice.eligibility", null);
			salesCenterVo.setValueByName("salesFlow.forceNonConfirm", null);
			salesCenterVo.setValueByName("customer.bestEmailContact", null);
			salesCenterVo.setValueByName("customerMove", null);
			salesCenterVo.setValueByName("drupalContentUrl", null);
			salesCenterVo.setValueByName("dispDrupalDailgVal", null);
			salesCenterVo.setValueByName("drupalDialogueCacheKey", null);
			salesCenterVo.setValueByName("isZipOnlySearch", null);
			salesCenterVo.setValueByName("campaignId", null);
			salesCenterVo.setValueByName("customer.contractAccountNumber", null);
			session.setAttribute("coxProductDetailsData",null);
			salesCenterVo.setValueByName("priorEnrollSurge", null);
			if(salesCenterVo.getScMap().containsKey("comcast.order.number")){
				salesCenterVo.getScMap().remove("comcast.order.number");
			}
			if(salesCenterVo.getScMap().containsKey("cox.order.number")){
				salesCenterVo.getScMap().remove("cox.order.number");
			}
			if(salesCenterVo.getScMap().containsKey("hughesnet.order.number")){
				salesCenterVo.getScMap().remove("hughesnet.order.number");
			}
			salesCenterVo.setValueByName("consumer.name.prefix", null);
			salesCenterVo.setValueByName("consumer.name.suffix",null);
			salesCenterVo.setValueByName("address.street1", null);
			salesCenterVo.setValueByName("address.city", null);
			salesCenterVo.setValueByName("address.state", null);
			salesCenterVo.setValueByName("address.zip", null);
			salesCenterVo.setValueByName("unit.type", null);
			salesCenterVo.setValueByName("unit.number", null);
			salesCenterVo.setValueByName("unitType", null);
			salesCenterVo.setValueByName("unitNum", null);
			salesCenterVo.setValueByName("rentOwnVal", null);
			salesCenterVo.setValueByName("dwellingType", null);
			salesCenterVo.setValueByName("moveInDate", null);
			salesCenterVo.setValueByName("isMoveInValue", null);
			salesCenterVo.setValueByName("moveInDate", null);
			salesCenterVo.setValueByName("customer.bestEmailContact", null);
			salesCenterVo.setValueByName("customer.telePhoneNum", null);
		}
		if (request.getSession().getAttribute("salescontextDt") != null) {
			ConsumerVO consumerVO = (ConsumerVO) request.getSession().getAttribute("salescontextDt");
			consumerVO.setDtPartner(null);
		}
		session.setAttribute("RapidResponsecustomer", "no");
		session.setAttribute("GUID", null);
		session.setAttribute("preDiscoveryTransfer", null);
		session.setAttribute("questionList", null);
		session.setAttribute("orderId", null);
		session.setAttribute("SALES_CONTEXT", null);
		session.setAttribute("addressId", null);
		session.setAttribute("address", null);
		session.setAttribute("lastLineItem", null);
		session.setAttribute("dispositionList", null);
		session.setAttribute("order", null);
		session.setAttribute("customerID", null);
		session.setAttribute("pesistFilterOptions",  null);
		session.setAttribute("salesSessionId", null);
		session.setAttribute("billingInfoExternalId", null);
		session.setAttribute("offer", null);
		session.setAttribute("callTimeBeforeUtility", null);
		session.setAttribute("notepadValue", null);
		session.setAttribute("productFeatureNameMap", null);
		session.setAttribute("salescontextVDN", null);
		session.setAttribute("dataFieldMap", null);
		session.setAttribute("sendEmail", null);
		session.setAttribute("Confirm_NonRR_4", null);
		session.setAttribute("gotoRecommondation", null);
		session.setAttribute("isServiceChecked", null);
		session.setAttribute("fromQualification", null);
		session.setAttribute("fromUtility", null);
		session.setAttribute("existingService", null);
		session.setAttribute("newService", null);
		session.setAttribute("LineItemVo", null);
		session.setAttribute("promoMap", null);
		session.setAttribute("lineItemList", null);
		session.setAttribute("lineitems", null);
		session.setAttribute("rrService", null);

		session.setAttribute("partnerSpecificDataMap", null);
		session.setAttribute("isPartnerSpecificMatchReqForUtility", null);
		session.setAttribute("requiresCSAT", null);
		session.setAttribute("tpvLineItems", null);
		session.setAttribute("closeCall", null);
		session.setAttribute("confirmedSecurity", null);
		session.setAttribute("isProductSaleInFlow",null);
		session.setAttribute("referrerFlow",null);

		session.setAttribute("lineItemIds", null);
		session.setAttribute("providerIds", null);
		session.setAttribute("productSrcs", null);
		session.setAttribute("utilityOffer", null);
		session.setAttribute("customerDiscoveryMap", null);
		session.setAttribute("pecoSuppliersMap", null);
		session.setAttribute("fromQualificationMoveInDelta",null);
		session.setAttribute("moveInDeltaService", null);
		session.setAttribute("isMoveInDelta",  null);
		session.setAttribute("pauseAndResumeUrl",  null);
		session.setAttribute("isPAREnabled",  null);
		session.setAttribute("siftFileVersion", null);	
		session.setAttribute("newAttv6LineItemExtID", null);	
		session.setAttribute("removedExistingProvidersMapAfterAuth", null);	
		session.setAttribute("selectedExistingProvidersAfterAuthentication",null);
		session.setAttribute("selectedExistingProviders",null);
		session.setAttribute("helpDialoguesFromDrupal",null);
		session.setAttribute("showObjectionBusters",null);
		session.setAttribute("showProviderHints",null);
		session.setAttribute("hintProviders",null);
		session.setAttribute("totalPoints", "0.0");
		session.setAttribute("closingOfferName",null);
		session.setAttribute("populatedVerizonSmartCartOrderId", null);
		session.setAttribute("MAMproductsUpdated",null);
		if(session.getAttribute("orderExternalId") !=  null)
		{
			session.removeAttribute("orderExternalId");
		}
		if( session.getAttribute("displaySaversButton")!=null )
		{
			session.removeAttribute("displaySaversButton");
		}
		if( session.getAttribute("isFromRecommendation")!=null )
		{
			session.removeAttribute("isFromRecommendation");
		}
		if( session.getAttribute("isRecommendation")!=null )
		{
			session.removeAttribute("isRecommendation");
		}
		if( session.getAttribute("isAllProductsSubmitted")!=null )
		{
			session.removeAttribute("isAllProductsSubmitted");
		}
		if( session.getAttribute("isUtilityOfferExist")!=null )
		{
			session.removeAttribute("isUtilityOfferExist");
		}
		if( session.getAttribute("isConfirmReferrerForUtility")!=null )
		{
			session.removeAttribute("isConfirmReferrerForUtility");
		}
		if( session.getAttribute("customer")!=null )
		{
			session.setAttribute("customer", null);
		}	


		logger.info("::cleared Previous Session Info::");
	}


	public String parseHtmlTags(String dataFieldText) {
		dataFieldText = dataFieldText.replaceAll("&amp;", "&");
		dataFieldText = dataFieldText.replaceAll("&lt;", "<");
		dataFieldText = dataFieldText.replaceAll("&gt;", ">");
		return dataFieldText;
	}

	public boolean confirmUtilityOffer(SalesCenterVO salesCenterVo, ProductResultsManager productResultManager, HttpServletRequest httpRequest, 
			boolean isConfirmReferrerForUtility) {
		HttpSession session = httpRequest.getSession();
		String sameCall = (String) session.getAttribute("sameCall");
		boolean isUtilityOfferExist = true;
		String contextIndicator = salesCenterVo.getValueByName("salesFlow.contextId");

		if(Utils.isBlank(contextIndicator)){
			contextIndicator="NA";
		}

		if((! isConfirmReferrerForUtility && (contextIndicator.equals("00")) || (contextIndicator.equals("05"))) || (!Utils.isBlank(sameCall) && sameCall.equals("true"))){
			isUtilityOfferExist = false;
		}
		else{
			boolean isPartnerSpecificMatchReqForUtility = (Boolean) httpRequest.getSession().getAttribute("isPartnerSpecificMatchReqForUtility");
			if (isPartnerSpecificMatchReqForUtility)
			{
				if((productResultManager.getUtilityOffersMap()!= null) 
						&& (productResultManager.getUtilityOffersMap().size()>0))
				{
					Map<String, String> partnerSpecificDataMap = (Map<String, String>)httpRequest.getSession().getAttribute("partnerSpecificDataMap");
					partnerSpecificDataMap = sortByComparator(partnerSpecificDataMap);
					logger.info("partnerSpecificDataMap="+partnerSpecificDataMap);
					List<ProductSummaryVO> psVOList = new ArrayList<ProductSummaryVO>();

					for (Entry<String,String> entry: partnerSpecificDataMap.entrySet())
					{
						for (ProductSummaryVO psVO :productResultManager.getUtilityOffersMap().get("UTILITY"))
						{
							String psMetaData = null;
							List<String> metaDataList = psVO.getPromotionMetaDataList();
							for(String metaData : metaDataList)
							{
								String[] productDataVal = null;
								if (metaData != null &&  metaData.contains("="))
								{
									productDataVal = metaData.split("=");
									if (productDataVal[0] != null && productDataVal[0].toString().equalsIgnoreCase("PS_PRODUCT"))
									{
										psMetaData = productDataVal[1];            
									}
								}
							}
							if (entry.getKey().equalsIgnoreCase(psMetaData))
							{
								psVOList.add(psVO);
								productResultManager.getUtilityOffersMap().clear();
								productResultManager.getUtilityOffersMap().put("UTILITY", psVOList);
								break;
							}
						}
					}
				}
			}
			logger.info("productResultManager_getUtilityOffersMap()_size()="+productResultManager.getUtilityOffersMap().size());
			if(productResultManager.getUtilityOffersMap().size() == 0){
				isUtilityOfferExist = false;
			}else{
				List<ProductSummaryVO> utilityoffer =productResultManager.getUtilityOffersMap().get("UTILITY");
				if(utilityoffer.get(0)!=null){
				httpRequest.getSession().setAttribute("utilityOfferName" ,utilityoffer.get(0).getName());
				}
			}
				
		}
		return isUtilityOfferExist;
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

	public String toJson(Object obj){
		Gson gson = new Gson();
		return gson.toJson(obj);
	}

	/** To check base monthly promotion is available
	 * @param product
	 * @return
	 */
	public ProductPromotionType isBaseMonthlyAvailable(ProductSummaryVO product){
		for(ProductPromotionType promotion : product.getPromotions()) {
			if (Constants.BASE_MONTHLY_DISCOUNT.equalsIgnoreCase(promotion.getType())){
				return promotion;
			}
		}
		return null;
	}

	/** sort promotions based on base monthly discount
	 * @param normalPromotions
	 * @return
	 */
	public List<ProductPromotionType> sortPromotions(List<ProductPromotionType> normalPromotions) {
		List<ProductPromotionType> baseMonthlyPromationList = new ArrayList<ProductPromotionType>();
		List<ProductPromotionType> nonBaseMonthlyPromationList = new ArrayList<ProductPromotionType>();
		List<ProductPromotionType> allPromationList = new ArrayList<ProductPromotionType>();
		for(ProductPromotionType promotion :normalPromotions){
			if (Constants.BASE_MONTHLY_DISCOUNT.equalsIgnoreCase(promotion.getType())){
				baseMonthlyPromationList.add(promotion);
			}else{
				nonBaseMonthlyPromationList.add(promotion);
			}
		}
		allPromationList.addAll(baseMonthlyPromationList);
		allPromationList.addAll(nonBaseMonthlyPromationList);
		return allPromationList;
	}

	/**
	 * @param product
	 * @return
	 */
	public String getInformationalPromoShortDescription(ProductSummaryVO product){
		if(product.getPromotions() != null
				&& !product.getPromotions().isEmpty()){
			for(ProductPromotionType promo : product.getPromotions()) {
				if(promo != null 
						&& !Utils.isBlank(promo.getType()) 
						&& Constants.INFORMATIONAL_PROMOTION.equalsIgnoreCase(promo.getType())){
					return promo.getShortDescription();
				}
			}
		}
		return "";
	}


	public String escapeSpecialCharacters(String str) {
		if (str != null) {
			str = str.replaceAll("&amp;", "&");
			str = str.replaceAll("'", "&#39;");
			str = str.replaceAll("&quot;", "&#34;");

			str = str.replaceAll("&#10;", "&nbsp;");
			str = str.replaceAll("\u00a0", "&nbsp;");
			// this is for - mark
			str = str.replaceAll("\u2013", "&#8211;");
			// this is for trademark
			str = str.replaceAll("\u2122", "&#8482;");

			// this is for Copyright mark
			str = str.replaceAll("\u00a9", "&#169;");
			// this is for Registered trade mark
			str = str.replaceAll("\u00ae", "&#174;");

			// this is for bullet point
			str = str.replaceAll("\u2022", "&#8226;");
			// this is for exclamation point
			str = str.replaceAll("\u0021", "&#33;");
			// this is for colon
			str = str.replaceAll("\u003a", "&#58;");
			// this is for inverted question mark
			str = str.replaceAll("\u00bf", "&#191;");

			// this is for right single quotation mark
			str = str.replaceAll("\u2019", "&#8217;");
			// this is for left single quotation mark
			str = str.replaceAll("\u2018", "&#8216;");
			// this is for left double quotation mark
			str = str.replaceAll("\u201C", "&#8220;");
			// this is for right double quotation mark
			str = str.replaceAll("\u201D", "&#8221;");

			str = str.replaceAll("&amp;", "&");

			str = str.replaceAll("&lt;", "<");

			str = str.replaceAll("&gt;", ">");

			str = str.replaceAll("&#13;", "<br>");
		}
		return str;
	}
public String getClientIPAddress(HttpServletRequest request){
		
		String clientIpAddr = request.getHeader("X-Forwarded-For");  
		String unknownIp = "unknown";
		logger.info("getting_client_IPAddress_in_AuthenticationController");
		if (clientIpAddr == null || clientIpAddr.length() == 0 ||  unknownIp.equalsIgnoreCase(clientIpAddr)) {
			clientIpAddr = request.getHeader("Proxy-Client-IP");
		}		
		if (clientIpAddr == null || clientIpAddr.length() == 0 || unknownIp.equalsIgnoreCase(clientIpAddr)) {
			clientIpAddr = request.getHeader("WL-Proxy-Client-IP");
		}
		if (clientIpAddr == null || clientIpAddr.length() == 0 || unknownIp.equalsIgnoreCase(clientIpAddr)) {
			clientIpAddr = request.getHeader("HTTP_CLIENT_IP");
		}
		if (clientIpAddr == null || clientIpAddr.length() == 0 || unknownIp.equalsIgnoreCase(clientIpAddr)) {
			clientIpAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (clientIpAddr == null || clientIpAddr.length() == 0 || unknownIp.equalsIgnoreCase(clientIpAddr)) {
			clientIpAddr = request.getRemoteAddr();
		}
		return clientIpAddr;
	}
}
