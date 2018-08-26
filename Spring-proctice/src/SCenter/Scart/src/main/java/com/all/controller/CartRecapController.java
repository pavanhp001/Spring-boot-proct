package com.AL.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.AL.ui.builder.CartDialogueBuilder;
import com.AL.ui.constants.ControllerConstants;
import com.AL.ui.dao.CustomerTrackerDao;
import com.AL.ui.factory.CartDialogueFactory;
import com.AL.ui.factory.CartModelAndViewFactory;
import com.AL.ui.factory.CartPlaceOrderFactory;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.util.CartUtil;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.V.exception.UnRecoverableException;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.di.v4.DialogueResponseType;
import com.AL.xml.di.v4.DataGroupType.DataFieldList;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;

/**
 * @author 
 *
 */
@Controller
public class CartRecapController extends BaseController  {

	/**
	 * Logger Initialization
	 * 
	 */
	private static final Logger logger = Logger.getLogger(CartRecapController.class);
	
	@Autowired
	private CustomerTrackerDao customerTrackerDao;
	
	/**
	 * 
	 * @param id(OrderType externalId)
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/recap/{id}", method = RequestMethod.POST)
	public ModelAndView orderRecap(@PathVariable String id, HttpServletRequest request) throws Exception,UnRecoverableException
	{
		logger.info("orderRecap_begin_in_CartRecapController");

		HttpSession session = request.getSession();
		String agentId = CartUtil.INSTANCE.getAgentId(session);

		/*	
		 * Getting SalesContext from the session				 
		 */
		SalesContextType salesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);
		/*	
		 *	Order excluding lineItems which are in CANCELLED_REMOVED status				 
		 */
		OrderType order = OrderService.INSTANCE.getOrderByOrderNumber(id,agentId,new HashMap<String,Object>(),"*",Boolean.TRUE,salesContext);

		logger.info("Building_Order_Recap_View");
		Double mavStartTime = Double.valueOf(System.currentTimeMillis());

		SalesCenterVO salesCenterVo = (SalesCenterVO) session.getAttribute("salescontext");
		String refferId = salesCenterVo.getValueByName("referrer.businessParty.referrerId");
		/*
		 * If we want to GUID on Sales Context then last parameter of below method is true otherwise false.
		 */
		Map<String, Map<String, String>> context = CartDialogueFactory.INSTANCE.updateContextMapWithReferrerAndCallType("OrderRecap",salesCenterVo,false);
		
		/*
		 * Making service call to get dialogues from DialogService..
		 */
		DialogueResponseType dialogueResponseType = CartDialogueFactory.INSTANCE.getDialoguesByContext(context);
		
		if(dialogueResponseType == null || dialogueResponseType.getResults() == null 
				|| dialogueResponseType.getResults().getDialogueList() == null 
				|| dialogueResponseType.getResults().getDialogueList().getDialogue() == null 
				|| dialogueResponseType.getResults().getDialogueList().getDialogue().isEmpty())
		{
			
			throw new UnRecoverableException("Dialogues Were not found.");
		}
		
		DataFieldList dataFieldList = CartDialogueBuilder.INSTANCE.buildDataFieldList(dialogueResponseType);
		
		ModelAndView mav = CartModelAndViewFactory.INSTANCE.createOrderView(agentId, order, ORDER_RECAP_VIEW, ORDER_RECAP_TITLE, request);
		mav.addObject("refferIdValue", refferId);
		
		mav.addObject(ControllerConstants.DATAFIELDLIST, dataFieldList.getDataField());
		logger.info("orderRecap_end_in_CartRecapController");
		return mav;
	}

	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/closeCallSale", method = RequestMethod.POST )
	public ModelAndView orderRecapSave(HttpServletRequest request) throws Exception,UnRecoverableException
	{
		logger.info("closeCallSale_begin_in_CartRecapController");
		HttpSession session = request.getSession();

		/**	
		 * Placing the Order	
		 * 2nd parameter in below method is true when we coming from simple choice flow otherwise false.			 
		 */
		OrderType order = CartPlaceOrderFactory.INSTANCE.placeOrder(null,false, customerTrackerDao);
		session.setAttribute("isAllProductsSubmitted", "Yes");
		ModelAndView view = CartModelAndViewFactory.INSTANCE.createCloseCallSaleView(session, order, request);
		logger.info("closeCallSale_end_in_CartRecapController");
		return view;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception
			{
		return new ModelAndView();
			}
}
