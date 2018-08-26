package com.AL.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.AL.ui.builder.LineItemBuilder;
import com.AL.ui.constants.Constants;
import com.AL.ui.constants.ErrorConstants;
import com.AL.ui.exception.InvalidTokenException;
import com.AL.ui.exception.SessionTimeOutException;
import com.AL.ui.factory.CKOLineItemFactory;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.service.V.OrderServiceUI;
import com.AL.ui.util.JsonUtil;
import com.AL.ui.util.OrderUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.util.ViewUtil;
import com.AL.ui.vo.CKOInitialVo;
import com.AL.ui.vo.OrderQualVO;
import com.AL.V.exception.RecoverableException;
import com.AL.V.exception.UnRecoverableException;
import com.AL.xml.v4.AttributeEntityType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;

@Controller
public class BaseController extends AbstractController {
	private static final JsonUtil<CKOInitialVo> util = new JsonUtil<CKOInitialVo>();
	private static final String redirectToExceptionPage = "redirect_to_exception_page";
	private static final String redirectToHomePage = "redirect_to_home";
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Handles the UnRecoverable Exception in the Concert application
	 * 
	 * 
	 * @param UnRecoverableException
	 * @return ModelAndView
	 */
	@SuppressWarnings("deprecation")
	@ExceptionHandler(RecoverableException.class)
	public ModelAndView handleException(RecoverableException re, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		
		/*
		 * setting the model name
		 */
		String channelType = (String)request.getSession().getAttribute("channelType");
		String channelCss = (String)request.getSession().getAttribute("channelCss");
		modelAndView = ViewUtil.setDigitalView(modelAndView, "acdc_error_view", "error_view", channelType, channelCss);
		
		//modelAndView.setViewName("error_view");
		modelAndView.addObject("redirectionType", redirectToExceptionPage);
		modelAndView.addObject("errorMessage", re.getMessage());
		modelAndView.addObject("recoverableError", true);
		logger.info("RecoverableException_occured_in_static_CKO",re);
		try{
			if(request.getSession().getAttribute("lineItemNumber") != null){
				Long lineIemExternalID = (Long)request.getSession().getAttribute("lineItemExternalID");
				OrderManagementRequestResponse orderRequestResponse = OrderServiceUI.INSTANCE
				.getOrderManagementRequestResponseByOrderNumber(String.valueOf(request.getSession().getAttribute("orderID")));

				OrderType order = OrderUtil.INSTANCE.getOrder(orderRequestResponse);

				OrderQualVO orderQualVO = new OrderQualVO();
				SalesContextType context = OrderService.INSTANCE.getSalesContext(String.valueOf(request.getSession().getAttribute("orderID")));
				orderQualVO.setLineItemExternalId((Long)request.getSession().getAttribute("lineItemExternalID"));
				orderQualVO.setLineItemNumber((Integer)request.getSession().getAttribute("lineItemNumber"));
				orderQualVO.setAgentId(order.getAgentId());
				orderQualVO.setOrderId((String) request.getSession().getAttribute("orderID"));
				OrderServiceUI.INSTANCE.updateLineItemAttribute(Constants.CKO_ERROR, orderQualVO, lineIemExternalID , context);

			}
			CKOInitialVo CKOVO = util.convert((String)request.getSession().getAttribute("CKOInput"), "CKO", CKOInitialVo.class);

			String jsonAsString = ViewUtil.returnIData(CKOVO, Constants.CKO_ERROR, ErrorConstants.CKO_ERROR);
			modelAndView.addObject("iData", jsonAsString);
		}catch(Exception e){
			logger.error("Error_occured_while_handling_RecoverableExceptionType",re);
		}
		MDC.clear();
		return modelAndView;
	}

	/**
	 * Handles the UnRecoverable Exception in the Concert application
	 * 
	 * 
	 * @param UnRecoverableException
	 * @return ModelAndView
	 */
	@SuppressWarnings("deprecation")
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception ure, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		
		String channelType = (String)request.getSession().getAttribute("channelType");
		String channelCss = (String)request.getSession().getAttribute("channelCss");
		modelAndView = ViewUtil.setDigitalView(modelAndView, "acdc_error_view", "error_view", channelType, channelCss);
		modelAndView.addObject("redirectionType", redirectToExceptionPage);
		modelAndView.addObject("recoverableError", true);
		modelAndView.addObject("errorMessage", ure.getMessage());
		logger.error("Exception_occured_in_static_CKO",ure);
		try{
			CKOInitialVo CKOVO = util.convert((String)request.getSession().getAttribute("CKOInput"), "CKO", CKOInitialVo.class);
			String jsonAsString = ViewUtil.returnIData(CKOVO, Constants.CKO_ERROR, ErrorConstants.CKO_ERROR);
			modelAndView.addObject("iData", jsonAsString);
			
			if(request.getSession().getAttribute("lineItemNumber") != null){
				Long lineIemExternalID = (Long)request.getSession().getAttribute("lineItemExternalID");
				OrderManagementRequestResponse orderRequestResponse = OrderServiceUI.INSTANCE
				.getOrderManagementRequestResponseByOrderNumber(String.valueOf(request.getSession().getAttribute("orderID")));

				OrderType order = OrderUtil.INSTANCE.getOrder(orderRequestResponse);

				SalesContextType context = OrderService.INSTANCE.getSalesContext(String.valueOf(request.getSession().getAttribute("orderID")));

				OrderQualVO orderQualVO = new OrderQualVO();

				orderQualVO.setLineItemExternalId((Long)request.getSession().getAttribute("lineItemExternalID"));
				orderQualVO.setLineItemNumber((Integer)request.getSession().getAttribute("lineItemNumber"));
				orderQualVO.setAgentId(order.getAgentId());
				orderQualVO.setOrderId((String) request.getSession().getAttribute("orderID"));
				OrderServiceUI.INSTANCE.updateLineItemAttribute(Constants.CKO_ERROR, orderQualVO, lineIemExternalID, context);
			}
			
		}catch(Exception e){
			logger.error("Error_occured_while_handling_ExceptionType",ure);
		}
		MDC.clear();
		return modelAndView;
	}

	/**
	 * Handles the UnRecoverable Exception in the Concert application
	 * 
	 * 
	 * @param UnRecoverableException
	 * @return ModelAndView
	 */
	@SuppressWarnings("deprecation")
	@ExceptionHandler(UnRecoverableException.class)
	public ModelAndView handleException(UnRecoverableException ure,HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		String channelType = (String)request.getSession().getAttribute("channelType");
		String channelCss = (String)request.getSession().getAttribute("channelCss");
		modelAndView = ViewUtil.setDigitalView(modelAndView, "acdc_error_view", "error_view", channelType, channelCss);
		modelAndView.addObject("redirectionType", redirectToExceptionPage);
		modelAndView.addObject("errorMessage", ure.getMessage());
		modelAndView.addObject("unrecoverableError", true);
		logger.error("UnRecoverableException_occured_in_static_CKO",ure);
		try{
			if(request.getSession().getAttribute("lineItemNumber") != null){
				Long lineIemExternalID = (Long)request.getSession().getAttribute("lineItemExternalID");
				OrderManagementRequestResponse orderRequestResponse = OrderServiceUI.INSTANCE
				.getOrderManagementRequestResponseByOrderNumber(String.valueOf(request.getSession().getAttribute("orderID")));

				OrderType order = OrderUtil.INSTANCE.getOrder(orderRequestResponse);

				SalesContextType context = OrderService.INSTANCE.getSalesContext(String.valueOf(request.getSession().getAttribute("orderID")));

				OrderQualVO orderQualVO = new OrderQualVO();

				orderQualVO.setLineItemExternalId((Long)request.getSession().getAttribute("lineItemExternalID"));
				orderQualVO.setLineItemNumber((Integer)request.getSession().getAttribute("lineItemNumber"));
				orderQualVO.setAgentId(order.getAgentId());
				orderQualVO.setOrderId((String) request.getSession().getAttribute("orderID"));
				OrderServiceUI.INSTANCE.updateLineItemAttribute(Constants.CKO_ERROR, orderQualVO, lineIemExternalID, context);
			}
			CKOInitialVo CKOVO = util.convert((String)request.getSession().getAttribute("CKOInput"), "CKO", CKOInitialVo.class);

			String jsonAsString = ViewUtil.returnIData(CKOVO, Constants.CKO_ERROR, ErrorConstants.CKO_ERROR);
			modelAndView.addObject("iData", jsonAsString);
		}catch(Exception e){
			logger.error("Error_occured_while_handling_UnRecoverableExceptionType",ure);
		}
		MDC.clear();
		return modelAndView;
	}
	
	/**
	 * Handles the Invalid Token Exception in static CKO and redirecting to the digital home page
	 * 
	 * @param InvalidTokenException
	 * @return ModelAndView
	 */
	@ExceptionHandler(InvalidTokenException.class)
	public ModelAndView handleException(InvalidTokenException ite, HttpServletRequest request) {
//		ModelAndView modelAndView = new ModelAndView("error");
		ModelAndView modelAndView = new ModelAndView("acdc_error_view");
		CKOInitialVo CKOVO = util.convert((String)request.getSession().getAttribute("CKOInput"), "CKO", CKOInitialVo.class);
		String jsonAsString = ViewUtil.returnIData(CKOVO, Constants.CKO_ERROR, ErrorConstants.CKO_ERROR);
		modelAndView.addObject("iData", jsonAsString);
		modelAndView.addObject("redirectionType", redirectToHomePage);
		modelAndView.addObject("errorMessage", ite.getMessage());
		return modelAndView;
	}

	@ExceptionHandler(SessionTimeOutException.class)
	public ModelAndView handleSessionTimeOutException(SessionTimeOutException sessionTimeOutException, HttpServletRequest request){
		logger.info("In_handleSessionTimeOutException");
		ModelAndView modelAndView = new ModelAndView();
		String channelType = (String)request.getSession().getAttribute("channelType");
		String channelCss = (String)request.getSession().getAttribute("channelCss");
		modelAndView = ViewUtil.setDigitalView(modelAndView, "acdc_error_view", "error_view", channelType, channelCss);
		modelAndView.addObject("redirectionType", redirectToExceptionPage);
		modelAndView.addObject("recoverableError", true);
		modelAndView.addObject("errorMessage", sessionTimeOutException.getMessage());
		logger.error("Exception_occured_in_static_CKO",sessionTimeOutException);
		try{
			Long lineIemExternalID = (Long)request.getSession().getAttribute("lineItemExternalID");
			String orderID = (String)request.getSession().getAttribute("orderID");
			OrderManagementRequestResponse orderRequestResponse = OrderServiceUI.INSTANCE.getOrderManagementRequestResponseByOrderNumber(orderID);
			OrderType order = OrderUtil.INSTANCE.getOrder(orderRequestResponse);
			SalesContextType context = OrderService.INSTANCE.getSalesContext(String.valueOf(request.getSession().getAttribute("orderID")));
			OrderQualVO orderQualVO = new OrderQualVO();
			orderQualVO.setLineItemExternalId((Long)request.getSession().getAttribute("lineItemExternalID"));
			orderQualVO.setAgentId(order.getAgentId());
			orderQualVO.setOrderId((String) request.getSession().getAttribute("orderID"));
			LineItemType liType = LineItemBuilder.INSTANCE.returnLineItemByLineItemID(order,lineIemExternalID);
			if(Utils.isBlank((String)request.getSession().getAttribute("lineItemNumber"))){
				orderQualVO.setLineItemNumber(liType.getLineItemNumber());
			}else{
				orderQualVO.setLineItemNumber(Integer.valueOf((String)request.getSession().getAttribute("lineItemNumber")));
			}
			if( liType.getLineItemAttributes() != null && liType.getLineItemAttributes().getEntity() != null && !liType.getLineItemAttributes().getEntity().isEmpty() ){
				 for(AttributeEntityType entityType : liType.getLineItemAttributes().getEntity()){
					 if( "PARENT_ID".equalsIgnoreCase(entityType.getSource()) )
				     {
				      modelAndView.addObject("providerExternalID",CKOLineItemFactory.INSTANCE.getLineItemAttributeValueBasedOnName(entityType, "id"));
				     }else if( "PRODUCT_NAME".equalsIgnoreCase(entityType.getSource()) )
				     {
				      modelAndView.addObject("product_name",CKOLineItemFactory.INSTANCE.getLineItemAttributeValueBasedOnName(entityType, "name"));
				     }else if( "CKO".equalsIgnoreCase(entityType.getSource()) )
				     {
				      modelAndView.addObject("product_base_recurring_price",CKOLineItemFactory.INSTANCE.getLineItemAttributeValueBasedOnName(entityType, "baseMonthlyFee"));
				     }
				 }
			}
			modelAndView.addObject("errorMessage","session timeout");
			modelAndView.addObject("redirectionType","redirect_to_exception_page");
			OrderServiceUI.INSTANCE.updateLineItemAttribute(Constants.CKO_ERROR, orderQualVO, lineIemExternalID, context);
			CKOInitialVo CKOVO = util.convert((String)request.getSession().getAttribute("CKOInput"), "CKO", CKOInitialVo.class);
			String jsonAsString = ViewUtil.returnIData(CKOVO, Constants.CKO_ERROR, ErrorConstants.CKO_ERROR);
			modelAndView.addObject("iData", jsonAsString);
	}catch(Exception e){
		logger.error("Error_occured_while_handling_SessionTimeOutException",e);
	}
	logger.info("Out_handleSessionTimeOutException");
	return modelAndView;

}

}
