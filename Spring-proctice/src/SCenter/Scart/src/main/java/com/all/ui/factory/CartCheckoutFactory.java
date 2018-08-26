package com.AL.ui.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.AL.productResults.managers.ProductResultsManager;
import com.AL.productResults.util.Utils;
import com.AL.productResults.vo.ProductSummaryVO;
import com.AL.ui.constants.Constants;
import com.AL.ui.constants.ControllerConstants;
import com.AL.ui.dao.ProviderDao;
import com.AL.ui.domain.PivotAssist;
import com.AL.ui.domain.Provider;
import com.AL.ui.util.CartLineItemUtil;
import com.AL.ui.util.CartUtil;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;

public enum CartCKOFactory {
	
	INSTANCE;
	
	private static final Logger logger = Logger.getLogger(CartCKOFactory.class);
	
	private final String KEY = "ABC123";
	private final String URL = "http://localhost:9999/att/att/CKO";
	
	public Map<String,Object> getCKOMavMap(String orderId, String customerId, HttpServletRequest request, ProviderDao providerDao)
	{
		
		Map<String,Object> modelAndViewMap = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		String lineItems = CartLineItemUtil.getRequestParmeterValue(ControllerConstants.LINEITEMIDS, request);
		String providerIds = CartLineItemUtil.getRequestParmeterValue(ControllerConstants.PROVIDERIDS, request);
		String productSrcs = CartLineItemUtil.getRequestParmeterValue(ControllerConstants.PRODUCTSRCS, request);
		String providerNames = CartLineItemUtil.getRequestParmeterValue(ControllerConstants.PROVIDERNAMES, request);
		String utilityOffer = CartLineItemUtil.getRequestParmeterValue(ControllerConstants.UTILITYOFFER, request);
		String isRecommendation = CartLineItemUtil.getRequestParmeterValue(ControllerConstants.ISRECOMMENDATION, request);
		String productExternalIds = CartLineItemUtil.getRequestParmeterValue(ControllerConstants.PRODUCTIDS, request);
		ProductResultsManager productResultManager = (ProductResultsManager)session.getAttribute("productResultManager");
		String agentId = CartUtil.INSTANCE.getAgentId(session);
		SalesContextType salesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);
		OrderType order = CartUtil.INSTANCE.getOrder(orderId, agentId, salesContext);
		SalesCenterVO salesCenterVo = (SalesCenterVO) session.getAttribute("salescontext");
		String keys = "";
		String urls = "";
		String receiverMatches = "";
		String planPoints = "";
		String[] providerIdsArr = providerIds.split(",");
		String[] productSrcsArr = productSrcs.split(",");
		String[] productExtIdArr = productExternalIds.split(",");
		List<String> providerIdList = new ArrayList<String>();
		providerIdList.add("2314635");
		providerIdList.add("32946482");
		providerIdList.add("32952482");
		providerIdList.add("15498481");
		
		if (providerIds != null) 
		{
			for(int p=0; p < providerIdsArr.length; p++)
			{
				String providerId = providerIdsArr[p];
				String pivotProviderId = providerIdsArr[p];
				String productSrc = productSrcsArr[p];
				logger.info("providerId " +providerId);
				if(productSrc != null && !productSrc.equals("") && productSrc.equals("INTERNAL") 
						&& providerId != null  && !providerIdList.contains(providerId))
				{
					providerId = "1";
				}

				if(providerId != null && !providerId.trim().equals(""))
				{
					try
					{
						Provider provider = providerDao.get(Long.valueOf(providerId));
						PivotAssist pivotAsst = new PivotAssist();
						if(provider != null){
							logger.info("Provider_data[ProividerId="+provider.getProviderId()+"]" +
									"[URL="+provider.getUrl()+"]_for_orderId_[Id="+orderId+"]");
							keys = keys + provider.getCertificate() + "," ;
							urls = urls + provider.getUrl()+ "," ;
							logger.info("DISH"+provider.getProviderId()+" Constants.DISH "+Constants.DISH);
							List<String> telcoProvidersList = productResultManager.getTelcoProvidersList();
							if(Constants.DISH.equalsIgnoreCase(pivotProviderId)){
								boolean isPivotAsstFound = false;
								logger.info("DISH ExtId"+pivotProviderId);
								pivotAsst.setProviderId(pivotProviderId);
								pivotAsst.setProviderName(Constants.Dish);
								pivotAsst.setPivotAsstName(Constants.Dish);
								if(salesCenterVo!= null && salesCenterVo.getValueByName("fallback.telcoProviderName")!= null 
										&& salesCenterVo.getValueByName("fallback.telcoProviderId") != null){
									if(salesCenterVo!= null && salesCenterVo.getValueByName("fallback.telcoProviderName")!= null){
										pivotAsst.setPivotAsstProviderName(salesCenterVo.getValueByName("fallback.telcoProviderName"));
									}
									if(salesCenterVo!= null && salesCenterVo.getValueByName("fallback.telcoProviderId")!= null){
										pivotAsst.setPivotAsstProviderId(salesCenterVo.getValueByName("fallback.telcoProviderId"));
									}
									session.setAttribute("pivotAssistJson", pivotAsst.getPivotAssistJson());
									isPivotAsstFound = true;
								}
								if(salesCenterVo!= null && salesCenterVo.getValueByName("fallback.cableProviderName")!= null 
										&& salesCenterVo.getValueByName("fallback.cableProviderId") != null){
									if(! isPivotAsstFound){
										pivotAsst.setPivotAsstProviderName(salesCenterVo.getValueByName("fallback.cableProviderName"));
										pivotAsst.setPivotAsstProviderId(salesCenterVo.getValueByName("fallback.cableProviderId"));
										session.setAttribute("pivotAssistJson", pivotAsst.getPivotAssistJson());
									}
									else{
										pivotAsst.setPivotAsstOtherProviderName(salesCenterVo.getValueByName("fallback.cableProviderName"));
										pivotAsst.setPivotAsstOtherProviderId(salesCenterVo.getValueByName("fallback.cableProviderId"));
										session.setAttribute("pivotAssistJson", pivotAsst.getPivotAssistJson());
									}
									isPivotAsstFound = true;
								}else if(! isPivotAsstFound){
									session.setAttribute("pivotAssistJson", "");
								}
							}else if(telcoProvidersList != null && telcoProvidersList.contains(pivotProviderId)){
								logger.info("telco ExtId"+pivotProviderId);
								pivotAsst.setProviderId(pivotProviderId);
								if(salesCenterVo!= null && salesCenterVo.getValueByName("fallback.cableProviderName")!= null 
										&& salesCenterVo.getValueByName("fallback.cableProviderId") != null){
										pivotAsst.setPivotAsstProviderName(salesCenterVo.getValueByName("fallback.cableProviderName"));
										pivotAsst.setPivotAsstProviderId(salesCenterVo.getValueByName("fallback.cableProviderId"));
										session.setAttribute("pivotAssistJson", pivotAsst.getPivotAssistJson());
										logger.info("telcoProvidersList ExtIds"+telcoProvidersList);
										pivotAsst.setPivotAsstName(Constants.TELCO);
								}else{
									session.setAttribute("pivotAssistJson", "");
								}
							}else{
								session.setAttribute("pivotAssistJson", "");
							}
							String dominionProductExtIds = (String)session.getAttribute("dominionProductExtIds");
							String inumVal = (String)session.getAttribute("inumVal");
							
							if(Utils.isBlank(dominionProductExtIds) ){
								session.setAttribute("dominionProductExtIds", "NA");
							}
							if(Utils.isBlank(inumVal) ){
								session.setAttribute("inumVal", "12345");
							}
							
						}
					} catch (Exception e)
					{
						logger.error("error_while_getting_provider_CKO_url", e);
						keys = keys + KEY + "," ;
						urls = urls + URL+ "," ;
					}
				} 
				else 
				{
					keys = keys + ",";
					urls = urls + ",";
				}
				logger.info(providerId);
				logger.info(urls);
			}
		}
		
		keys = keys.substring(0, keys.length()-1);
		urls = urls.substring(0, urls.length()-1);
		
		logger.debug("LineItems_in_CKO[orderId="+orderId+"][LineItemIds="+lineItems+"]");
		
		if (productExtIdArr != null) 
		{
			for(int p=0; p < productExtIdArr.length; p++)
			{
				String productExtId = productExtIdArr[p];

				logger.info("productExtId " +productExtId);

				if(productExtId != null && !productExtId.trim().equals(""))
				{
						receiverMatches = receiverMatches + getReceiverMatchMetaData(productExtId, productResultManager) + "," ;
				} 
				else 
				{
					receiverMatches = receiverMatches + "false,";
				}
				if(productResultManager != null){
					String points =(String)productResultManager.getProductPoints(productExtIdArr[p]);
					if(!Utils.isBlank(points)){
						planPoints = planPoints + points + "," ;
					}else{
						planPoints = planPoints + "NA" + ",";
					}
				}
				logger.info(receiverMatches);
			}
		}		
		
		receiverMatches = receiverMatches.substring(0, receiverMatches.length()-1);
		planPoints = planPoints.substring(0, planPoints.length()-1);
		
		modelAndViewMap.put(ControllerConstants.PLAN_POINTS, planPoints);
		modelAndViewMap.put(ControllerConstants.RECEIVER_MATCHES, receiverMatches);
		modelAndViewMap.put(ControllerConstants.RECEIVER_MATCHES, receiverMatches);
		modelAndViewMap.put(ControllerConstants.ORDERID, orderId);
		modelAndViewMap.put(ControllerConstants.CUSTOMERID, customerId);
		modelAndViewMap.put(ControllerConstants.LINEITEMID, lineItems);
		modelAndViewMap.put(ControllerConstants.PROVIDERID, providerIds);
		modelAndViewMap.put(ControllerConstants.PRODUCTIDS, productExternalIds);
		modelAndViewMap.put(ControllerConstants.PROVIDERNAMES, providerNames);
		modelAndViewMap.put(ControllerConstants.UTILITYOFFER, utilityOffer);
		modelAndViewMap.put(ControllerConstants.KEY, keys);
		modelAndViewMap.put(ControllerConstants.URL, urls);
		modelAndViewMap.put(ControllerConstants.ORDER, order);
		modelAndViewMap.put(ControllerConstants.LINEITEMLIST, CartLineItemFactory.INSTANCE.sortLineItemBasedOnStatus(order));
		modelAndViewMap.put(ControllerConstants.ADDRESS, CartLineItemUtil.getAddress(order.getCustomerInformation().getCustomer(),
				com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString()));
		modelAndViewMap.put(ControllerConstants.ISRECOMMENDATION, isRecommendation);
		
		return modelAndViewMap;
	}
	
	private boolean getReceiverMatchMetaData(String productExtId, ProductResultsManager productResultManager) {
		boolean isReceiverMatch = false;
		if(productResultManager != null && productResultManager.getProductByIconMap() != null){
			List<ProductSummaryVO> allProductSummaryVOList = new ArrayList<ProductSummaryVO>();
			for(Entry<String, List<ProductSummaryVO>> productEntry:productResultManager.getProductByIconMap().entrySet()){
				allProductSummaryVOList.addAll(productEntry.getValue());
			}
			for(ProductSummaryVO summaryVO:allProductSummaryVOList){
				if(!Utils.isBlank(productExtId) 
						&& productExtId.equals(summaryVO.getExternalId()) 
						 && summaryVO.getMetadata() != null 
						  && summaryVO.getMetadata().get(ControllerConstants.RECEIVER_MATCH) != null){
							logger.info("Receiver match is true");
						   isReceiverMatch = true;
				}
			}
		}
		return isReceiverMatch;		
	}

}
