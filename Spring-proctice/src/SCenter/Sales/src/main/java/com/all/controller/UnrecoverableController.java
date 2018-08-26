package com.AL.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.AL.filter.UpdateInvalidOrdersFactory;
import com.AL.ui.dao.CustomerTrackerDao;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.util.Utils;
import com.AL.xml.v4.NameValuePairType;
import com.AL.xml.v4.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextEntityType;
import com.AL.xml.v4.SalesContextType;

@Controller
public class UnrecoverableController {
	
	private static final Logger logger = Logger.getLogger(UnrecoverableController.class);
	private long startTimer=0;
	
	@Autowired
	private CustomerTrackerDao customerTrackerDao;
	
	@RequestMapping(value="/updateSalesContext")
	protected ModelAndView sessionDestroyed(HttpServletRequest request,	HttpServletResponse response) {
		StopWatch timer = new StopWatch();
		timer.start();
		// Get the session that was invalidated
		HttpSession session = request.getSession();
		String absoluteURL = (String) session.getAttribute("urlPath");
		logger.info("Order Id Is ::::::::::::: "+ session.getAttribute("orderId"));
		// Log a message
		if (session.getAttribute("orderId") != null){
			startTimer = timer.getTime();
			OrderManagementRequestResponse requestResponse = OrderService.INSTANCE.getOrderManagementRequestResponseByOrderNumber(String.valueOf(session.getAttribute("orderId")));
			logger.info("TimeTakenforgetOrderManagementRequestResponseByOrderNumber="+(timer.getTime()-startTimer));
			timer.stop();
			OrderType order = requestResponse.getResponse().getOrderInfo().get(0);
			SalesContextType updateSalesContext = requestResponse.getResponse().getSalesContext();
			List<SalesContextEntityType> list = updateSalesContext.getEntity();
			boolean isSessionStatusFlag = false;
			for (SalesContextEntityType entry : list){
				if (entry.getName() != null && entry.getName().equalsIgnoreCase("SYP")){
					List<NameValuePairType> nvpList =  entry.getAttribute();
					for (NameValuePairType nvpData : nvpList){
						if (nvpData.getName() != null && nvpData.getName().equalsIgnoreCase("sessionStatus")){
							isSessionStatusFlag = true;
							break;
						}
					}
				}
			}
			String populatedCustomerTrackerOrderId = String.valueOf((Long)request.getSession().getAttribute("populatedCustomerTrackerOrderId"));
			String populatedVerizonSmartCartOrderId = (String)request.getSession().getAttribute("populatedVerizonSmartCartOrderId"); 
			if(Utils.isBlank(populatedCustomerTrackerOrderId) || !populatedCustomerTrackerOrderId.equals(String.valueOf(order.getExternalId())))
			{
				UpdateInvalidOrdersFactory.INSTANCE.updateSalesContextBySession(session,requestResponse);
				try{
					Utils.insertAndUpdateCustomerTrackerData(order, session, customerTrackerDao);
					}catch(Exception e){
						logger.info("error occured while inserting the data "+e.getMessage());
					}
				try{
					if(Utils.isBlank(populatedVerizonSmartCartOrderId) || !populatedVerizonSmartCartOrderId.equals(Long.toString(order.getExternalId()))){
						Utils.postVZSaveSmartCartProduct(order, session);
					}
				}catch(Exception e){
					logger.info("error occured while post VZ SaveSmartCartProduct "+e.getMessage());
				}
			}
		}
		timer.stop();
		logger.info(getTime() + " Session invalidated: Destroyed:ID=" 
				+ session.getId());
		
		return new ModelAndView("redirect:" + absoluteURL
				+ "/salescenter/login_process");
	}
	private String getTime()
	{
		return new Date(System.currentTimeMillis()).toString();
	}
}
