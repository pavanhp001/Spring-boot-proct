package com.AL.controller;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.webflow.execution.Action;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.AL.productResults.managers.ProductResultsManager;
import com.AL.productResults.util.Utils;
import com.AL.productResults.vo.ProductSummaryVO;
import com.AL.ui.domain.CustomerTrackerDetails;
import com.AL.ui.factory.CartLineItemFactory;
import com.AL.ui.factory.SalesUtilsFactory;
import com.AL.ui.util.LineItemUtil;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.V.exception.UnRecoverableException;
import com.AL.xml.v4.OrderType;

@Controller("UtilityOfferController")
public class UtilityOfferController  implements Action {

	private static final Logger logger = Logger.getLogger(UtilityOfferController.class);

	public Event execute(RequestContext request) throws Exception {
		HttpServletRequest httpRequest =(HttpServletRequest)request.getExternalContext().getNativeRequest();
		StopWatch timer = new StopWatch();
		timer.start();
		try{
			logger.info("showUtilityOffer=");
			HttpSession session =httpRequest.getSession();
			if(session.getAttribute("offersFromRecommendations") != null && (Boolean)session.getAttribute("offersFromRecommendations")) {
				return new Event(this,"recommendationsViewEvent");
			}				
			boolean isFromRecomandationPage = false;
			if (session.getAttribute("gotoofferpage") != null ){

				isFromRecomandationPage = true;

				session.removeAttribute("gotoofferpage");
				session.setAttribute("gotoRecommondation", "yes");

			}else{
				isFromRecomandationPage = false;
			}
			SalesCenterVO salesCenterVo = (SalesCenterVO) session.getAttribute("salescontext");
			boolean isUtilityOfferExist = false;
			if(session.getAttribute("isUtilityOfferExist")!= null){
				isUtilityOfferExist = (Boolean) session.getAttribute("isUtilityOfferExist");
			}
			if(! isUtilityOfferExist){
				boolean isConfirmReferrerForUtility = false;
				if(session.getAttribute("isConfirmReferrerForUtility")!= null){
					isConfirmReferrerForUtility = (Boolean) session.getAttribute("isConfirmReferrerForUtility");
				}
				ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");
				isUtilityOfferExist = SalesUtilsFactory.INSTANCE.confirmUtilityOffer(salesCenterVo, productResultManager, httpRequest, isConfirmReferrerForUtility);
			}

			if(! isUtilityOfferExist)
			{
				logger.info("Utility_offer_is_not_available");
				session.setAttribute("utilityOffer" , "false");
				session.setAttribute("offer" , "true");
				logger.info("timeTakenToDisplayNextView="+timer.getTime());
				if (isFromRecomandationPage){
					return new Event(this, "recommendationsViewEvent");
				}else{
					return new Event(this, "qualificationViewEvent");
				}
			}
			else
			{
				ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");
				OrderType order =(OrderType)httpRequest.getSession().getAttribute("order");
				
				ProductSummaryVO salesProductDetailsVO = productResultManager.getUtilityOffersMap().get("UTILITY").get(0);
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
				request.getFlowScope().put("customerId" , order.getCustomerInformation().getCustomer().getExternalId());
				request.getFlowScope().put("orderId" , order.getExternalId());
				request.getFlashScope().put("CKOCompletedLineItems" , LineItemUtil.prepareCKOCompletedLinItemsListFromOrder(order));
				session.setAttribute("dominionProductExtIds", "NA");
				session.setAttribute("inumVal", "12345");
				try{
					String utilityOfferPitched = request.getFlowScope().getString("isUtilityOfferPitched");
					if(Utils.isBlank(utilityOfferPitched) || ! utilityOfferPitched.equalsIgnoreCase("yes")){
						request.getFlowScope().put("isUtilityOfferPitched" , "yes");
						CustomerTrackerDetails customerTrackerDetails = (CustomerTrackerDetails)session.getAttribute("customerTrackerDetails");
						if(customerTrackerDetails != null){
							int utilityPitchedCount = customerTrackerDetails.getUtilityPitchedCount() + 1;
							customerTrackerDetails.setUtilityPitchedCount(utilityPitchedCount);
							session.setAttribute("customerTrackerDetails",customerTrackerDetails);
						}
					}
				}
				catch (Exception e)
				{
					logger.warn("error updating utilityOfferPitched "+e.getMessage());
				}
				logger.info("timeTakenToDisplayUtilityOffer="+timer.getTime());
				return new Event(this, "CKOViewEvent");
			}
		}
		catch (Exception e)
		{
			request.getFlowScope().put("message", e.getMessage());
			request.getFlowScope().put("pageTitle",httpRequest.getParameter("pageTitle")!=null?httpRequest.getParameter("pageTitle"):"");
			logger.error(e);
			throw new UnRecoverableException(e.getMessage());
		}
		finally
		{
			timer.stop();
		}
	}

}