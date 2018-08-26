package com.AL.controller;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.AL.productResults.managers.ProductResultsManager;
import com.AL.productResults.managers.RESTClientForDrupal;
import com.AL.productResults.util.Utils;
import com.AL.productResults.vo.ProductSummaryVO;
import com.AL.service.ClosingOfferService;
import com.AL.ui.constants.Constants;
import com.AL.ui.constants.ControllerConstants;
import com.AL.ui.domain.CustomerTrackerDetails;
import com.AL.ui.factory.CartLineItemFactory;
import com.AL.ui.service.config.ConfigRepo;
import com.AL.ui.util.LineItemUtil;
import com.AL.V.exception.BaseException;
import com.AL.V.exception.UnRecoverableException;
import com.AL.xml.v4.OrderType;



/**
 * @author Sravan Kumar Nalajala
 *
 */
@Controller("ClosingOfferController")
public class ClosingOfferController {
	private static final Logger logger = Logger.getLogger(ClosingOfferController.class);

	@Autowired
	ClosingOfferService closingOfferService;

	public Event isClosingOfferShow(RequestContext requestContext)throws BaseException {
		StopWatch timer = new StopWatch();
		timer.start();
		try {
			logger.info("begin_ClosingOfferController");
			HttpServletRequest httpRequest = (HttpServletRequest) requestContext.getExternalContext().getNativeRequest();
			ProductResultsManager productResultManager = (ProductResultsManager) httpRequest.getSession().getAttribute("productResultManager");
			logger.info("isShowClosingOffer_callView="+requestContext.getFlowScope().get("callView"));
			String isClosingOfferFromCKO = requestContext.getFlowScope().getString("isClosingOfferFromCKO");
			OrderType orderType = (OrderType)httpRequest.getSession().getAttribute(ControllerConstants.ORDER);
			String dominionProviders = ConfigRepo.getString("*.dominion_provider") == null ? null : ConfigRepo.getString("*.dominion_provider");
			if(Utils.isBlank(isClosingOfferFromCKO) && orderType != null){
				
				ProductSummaryVO salesProductDetailsVO = null;
				List<ProductSummaryVO> allClosingOfferProducts = closingOfferService.buildClosingOfferList(orderType, productResultManager, httpRequest);
				if(allClosingOfferProducts != null && !allClosingOfferProducts.isEmpty()){
					//sort by name
					Collections.sort(allClosingOfferProducts, new Comparator<ProductSummaryVO>() {
						public int compare(ProductSummaryVO vO1, ProductSummaryVO vO2) {
							return vO1.getName().compareTo(vO2.getName());
						}
					});
					//sort by score
					productResultManager.context.sortProducts(allClosingOfferProducts, "score");
					salesProductDetailsVO = allClosingOfferProducts.get(0);
					setDominionClosingOfferProductExternalIdsIntoSession(httpRequest, allClosingOfferProducts, dominionProviders,salesProductDetailsVO);
				}
				if(salesProductDetailsVO != null){
					String providerIds = salesProductDetailsVO.getProviderExternalId();
					String productExternalId  = salesProductDetailsVO.getExternalId();
					String providerSourceBaseType = salesProductDetailsVO.getSource();
					String productSrcs = CartLineItemFactory.INSTANCE.getProviderSourceBaseType(providerSourceBaseType).toString() ;
					logger.info("lineItemIds=0,providerIds="+providerIds+",ProductExternalId="+productExternalId+",productSrcs="+productSrcs);
					httpRequest.getSession().setAttribute("lineItemIds", "0");
					httpRequest.getSession().setAttribute("providerIds", providerIds);
					httpRequest.getSession().setAttribute("productSrcs", productSrcs);
					httpRequest.getSession().setAttribute("productExtIds", productExternalId);
					httpRequest.getSession().setAttribute("utilityOffer" , "true");
					httpRequest.getSession().setAttribute("offer" , "true");
					httpRequest.getSession().setAttribute("callTimeBeforeUtility", Double.valueOf(Calendar.getInstance().getTimeInMillis()));
					requestContext.getFlowScope().put("isClosingOfferFlow" , "true");
					requestContext.getFlowScope().put("customerId" , orderType.getCustomerInformation().getCustomer().getExternalId());
					requestContext.getFlowScope().put("orderId" , orderType.getExternalId());
					requestContext.getFlashScope().put("CKOCompletedLineItems" , LineItemUtil.prepareCKOCompletedLinItemsListFromOrder(orderType));
					logger.info("dominionProviders  ="+dominionProviders);
					if(dominionProviders != null && providerIds!= null && dominionProviders.contains(providerIds)&&httpRequest.getSession().getAttribute("pauseAndResumeURL")!= null && httpRequest.getSession().getAttribute("phoneId")!= null){
						String inumVal = RESTClientForDrupal.INSTANCE.getInum(String.valueOf(httpRequest.getSession().getAttribute("phoneId")), String.valueOf(httpRequest.getSession().getAttribute("pauseAndResumeURL")));
						logger.info("getInumVal= "+inumVal);
						if(Utils.isBlank(inumVal) || (Constants.INUM_NOT_FOUND.equalsIgnoreCase(inumVal) || Constants.INTERNAL_ERROR.equalsIgnoreCase(inumVal))){
							inumVal="12345";
							logger.info("inumVal= "+inumVal);
						}
						httpRequest.getSession().setAttribute("inumVal", inumVal);
					}
					else{
						httpRequest.getSession().setAttribute("inumVal", "12345");
					}
					try{
						CustomerTrackerDetails customerTrackerDetails = (CustomerTrackerDetails)httpRequest.getSession().getAttribute("customerTrackerDetails");
						if(customerTrackerDetails != null){
							int utilityPitchedCount = customerTrackerDetails.getUtilityPitchedCount() + 1;
							customerTrackerDetails.setUtilityPitchedCount(utilityPitchedCount);
							httpRequest.getSession().setAttribute("customerTrackerDetails",customerTrackerDetails);
						}
					
					}
					catch (Exception e)
					{
						logger.warn("error updating utilityOfferPitched "+e.getMessage());
					}
					logger.info("timeTakenToDisplayCloseCallOfferController="+timer.getTime());
					return new Event(this, "CKOViewEvent");
				}
			}
		}catch (Exception e) {
			logger.error("error_while_isShowClosingOfferr",e);
			throw new UnRecoverableException(e.toString());
		}
		finally{
			timer.stop();
		}
		return new Event(this,requestContext.getFlowScope().getString("callView"));
	}
	
	@RequestMapping(value="/getClosingOfferFlag")
	public @ResponseBody String getClosingOfferFlag(HttpServletRequest httpRequest){
		String res = "";
		try{
			Map<String, Map<String, String>> contextMap = (Map<String, Map<String, String>>)httpRequest.getSession().getAttribute("dynamicFlowContextMap");
			Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");
			logger.info("flowType="+dynamicFlow.get("dynamicFlow.flowType"));
			if(dynamicFlow != null 
					&& dynamicFlow.get("dynamicFlow.flowType") != null 
					     && !dynamicFlow.get("dynamicFlow.flowType").contains("simpleChoice")){
				ProductResultsManager productResultManager = (ProductResultsManager) httpRequest.getSession().getAttribute("productResultManager");
				if(productResultManager != null){
					OrderType orderType = (OrderType)httpRequest.getSession().getAttribute(ControllerConstants.ORDER);
					List<ProductSummaryVO> allClosingOfferProducts = closingOfferService.buildClosingOfferList(orderType, productResultManager, httpRequest);
					if(allClosingOfferProducts != null && !allClosingOfferProducts.isEmpty()){
						//res = "clossingOfferAvail";
						res = "closing offer available"+" - "+allClosingOfferProducts.get(0).getName();
					}
				}
			}
		}catch (Exception e) {
			logger.error("error_occured_while_get_closingOffer_flag",e);
		}
		return res; 
	}
	public void setDominionClosingOfferProductExternalIdsIntoSession(HttpServletRequest httpRequest, List<ProductSummaryVO> allClosingOfferProducts,
			String dominionProviders, ProductSummaryVO salesProductDetailsVO){
		try{
			httpRequest.getSession().setAttribute("isDominionOffer", "false");
			if (dominionProviders!= null && dominionProviders.contains(salesProductDetailsVO.getProviderExternalId())){
				httpRequest.getSession().setAttribute("isDominionOffer", "true");
			}
			StringBuffer productExtIds = new StringBuffer();
			logger.info("dominionProviders == "+dominionProviders);
			for(ProductSummaryVO productVO: allClosingOfferProducts){
				logger.info("productVO.getProviderExternalId() == "+productVO.getProviderExternalId());
				if(dominionProviders!= null && dominionProviders.contains(productVO.getProviderExternalId())){
					if(productExtIds.length()>0){
						productExtIds = productExtIds.append("|").append(productVO.getExternalId());
					}
					else{
						productExtIds = productExtIds.append(productVO.getExternalId());
					}
				}
			}
			if(!Utils.isBlank(productExtIds.toString())){
				httpRequest.getSession().setAttribute("dominionProductExtIds", productExtIds.toString());
				logger.info("productExtIds.toString == "+productExtIds.toString());
			}
			else{
				httpRequest.getSession().setAttribute("dominionProductExtIds", "NA");
			}
		}catch (Exception e) {
			logger.error("error_occured_while_get_closingOffer_flag",e);
		}
	}
}
