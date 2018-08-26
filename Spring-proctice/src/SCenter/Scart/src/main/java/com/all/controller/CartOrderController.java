package com.AL.controller;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.AL.ui.activity.order.SaveOrderAndCustomerActivity;
import com.AL.ui.factory.WarmTransferFactory;
import com.AL.ui.service.V.CustomerService;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.util.CartUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.CartError;
import com.AL.ui.vo.ConsumerVO;
import com.AL.ui.vo.ErrorList;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.util.DateUtil;
import com.AL.V.exception.UnRecoverableException;
import com.AL.V.factory.CustomerFactory;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.cm.v4.CustomerContextType;
import com.AL.xml.cm.v4.CustomerType;
import com.AL.xml.cm.v4.EMailAddressType;
import com.AL.xml.cm.v4.NotificationEventCollectionType;
import com.AL.xml.cm.v4.NotificationEventType;
import com.AL.xml.v4.Customer;
import com.AL.xml.v4.ObjectFactory;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;


/**
 * @author 
 *
 */
@Controller
public class CartOrderController extends BaseController {

	/**
	 * Auto wiring the SaveOrderAndCustomerActivity
	 * 
	 */
	@Autowired
	SaveOrderAndCustomerActivity saveOrderAndCustomerActivity;

	/**
	 * Logger Initialization
	 * 
	 */
	private static final Logger logger = Logger.getLogger(CartOrderController.class);


	/**
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/scart/editCustomerInfo")
	public @ResponseBody String updateOrder(HttpServletRequest request) throws Exception
	{
		StopWatch timer=new StopWatch();
		long startTimer = 0;
		timer.start();
		logger.info("updating_customer_info");
		try{
			String bestEmailContact = request.getParameter("bestEmailContact");
			String moveDate = request.getParameter("moveDate");
			String bestPhoneContact = request.getParameter("bestPhoneContact");
			String customerId = request.getParameter("customerId");
			String orderIdForMoveDate = request.getParameter("orderIdForMoveDate");
			HttpSession session = request.getSession();
	
			String agentId = CartUtil.INSTANCE.getAgentId(session);
			/*	
			 * Getting SalesContext from the session				 
			 */
			SalesContextType salesContext = SalesContextFactory.INSTANCE.getContextFromSession(session);
	
			boolean isCustomerUpdated = false;
			OrderType orderType =(OrderType)session.getAttribute("order");
			
			String bestPhoneNumber = null;
			if( !Utils.isBlank(bestPhoneContact) )
			{
				bestPhoneNumber = bestPhoneContact.replaceAll("-", "");
			}
			
			if( ( !Utils.isBlank(bestEmailContact) 
				&& !bestEmailContact.equals(orderType.getCustomerInformation().getCustomer().getBestEmailContact()) ) 
				|| ( !Utils.isBlank(bestPhoneNumber) 
				&& !bestPhoneNumber.equals(orderType.getCustomerInformation().getCustomer().getBestPhoneContact()) ) )
			{
				updateCustomerInfo(request,bestEmailContact,bestPhoneContact,customerId,orderType.getCustomerInformation().getCustomer(),timer);
				isCustomerUpdated = true;
			}
	
			boolean isOrderUpdated = false;
			ObjectFactory oFactory = new ObjectFactory();
			OrderType  order = oFactory.createOrderType();
			if(!moveDate.isEmpty() )
			{
				Date mDate = DateUtil.fromStringToDate(moveDate);
				if(mDate != null)
				{
					XMLGregorianCalendar updatedMoveDate = DateUtil.asXMLGregorianCalendar(mDate);
					boolean isEqualDates = equalsXMLGregorianCalendarDates(updatedMoveDate,orderType.getMoveDate());
					if( updatedMoveDate != null && !isEqualDates )
					{
						order.setMoveDate(updatedMoveDate);
						order.setExternalId(orderType.getExternalId());
						
						startTimer=timer.getTime();
						order = OrderService.INSTANCE.updateOrder(agentId, orderIdForMoveDate, order,salesContext,String.valueOf(session.getAttribute("GUID")));
						logger.info("TimeTakenforUpdateOrderServiceCall="+(timer.getTime() - startTimer));
						
						isOrderUpdated =true;
						if(order!=null)
						{
							logger.info("order_updated_successfully");
							request.getSession().setAttribute("order", order);
							
							return "Success";
						}
					}
				}
			}
			
			if( !isOrderUpdated && isCustomerUpdated )
			{
				startTimer=timer.getTime();
				order = OrderService.INSTANCE.getOrderByOrderNumber(orderIdForMoveDate,agentId,new HashMap<String,Object>(),"*",Boolean.TRUE,salesContext);
				logger.info("TimeTakenforGetOrderServiceCall="+(timer.getTime() - startTimer));
				request.getSession().setAttribute("order", order);
				
				return "Success";
			}
			else
			{
				
				return "error";
			}
		}finally
		{
			timer.stop();
		}
	}

	private boolean equalsXMLGregorianCalendarDates(XMLGregorianCalendar updatedMoveDatem,XMLGregorianCalendar moveDate)
	{
		if(moveDate!=null)
		{
			if( updatedMoveDatem.getYear() == moveDate.getYear() 
					&& updatedMoveDatem.getMonth() == moveDate.getMonth() 
					&& updatedMoveDatem.getDay() == moveDate.getDay())
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * @param request 
	 * @param bestEmailContact
	 * @param bestPhoneContact
	 * @param customerId
	 * @param orderCustomer 
	 */
	private void updateCustomerInfo(HttpServletRequest request, String bestEmailContact,
			String bestPhoneContact, String customerId, Customer orderCustomer, StopWatch timer) throws Exception
			{
		logger.info("updateCustomerInfo_begin_in_CartOrderController");
		com.AL.xml.cm.v4.ObjectFactory oFactory = new com.AL.xml.cm.v4.ObjectFactory();
		ErrorList errorList = new ErrorList();
		CustomerType customer = oFactory.createCustomerType();

		customer.setExternalId(Long.parseLong(customerId));
		customer.setBestEmailContact(bestEmailContact);
		customer.setBestPhoneContact(bestPhoneContact);
		customer.setEMailOptIn(orderCustomer.isEMailOptIn());
		customer.setMarketingOptIn(orderCustomer.isMarketingOptIn());
		customer.setNonBuyerWebOptIn(orderCustomer.isNonBuyerWebOptIn());
		customer.setDirectMailOptIn(orderCustomer.isDirectMailOptIn());
		customer.setPhoneContactOptIn(orderCustomer.isPhoneContactOptIn());
		customer.setEMailProductUpdatesOptIn(orderCustomer.isEMailProductUpdatesOptIn());

		EMailAddressType email = new EMailAddressType();
		String hemail = "";
		boolean isEmailAddressChanged = false;
		SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
		if(!Utils.isBlank(bestEmailContact))
		{
			hemail = bestEmailContact;
			email.setValue(hemail);
			customer.setHomeEMail(email);
			
			String dtCreated = salesCenterVo.getValueByName("dtCreated");
			if( !Utils.isBlank( dtCreated ) )
			{
				isEmailAddressChanged = WarmTransferFactory.INSTANCE.isEmailAddressUpdated(bestEmailContact, salesCenterVo);
			}
		}
		HttpSession session = request.getSession();

		String agentId = CartUtil.INSTANCE.getAgentId(session);
		String GUID = (String)request.getSession().getAttribute("GUID");

		Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
		Map<String, String> sourceEntity = new HashMap<String, String>();
		sourceEntity.put("source", "salescenter");
		sourceEntity.put("GUID", GUID);
		data.put("source", sourceEntity);
		CustomerContextType customerContext = CustomerFactory.INSTANCE.buildCustomerContext(data);
		Map<String, Map<String, String>> contextMap = (Map<String, Map<String, String>>)request.getSession().getAttribute("dynamicFlowContextMap");
		Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");
		if(isEmailAddressChanged   && dynamicFlow.get("dynamicFlow.flowType")!= null 
				&& !dynamicFlow.get("dynamicFlow.flowType").contains("customerLookup"))
		{

			NotificationEventType notifEventType = oFactory.createNotificationEventType();
			notifEventType.getReason().add(50);
			notifEventType.setCode(100);
			NotificationEventCollectionType notifEventColl = oFactory.createNotificationEventCollectionType();
			DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
			GregorianCalendar gc = new GregorianCalendar();
			XMLGregorianCalendar dateTime = datatypeFactory.newXMLGregorianCalendar(gc);
			notifEventType.setDateTimeStamp(dateTime);
			notifEventType.setComment("Customer Info Updated");
			notifEventColl.getEvent().add(notifEventType);
			long startTimer = timer.getTime();	
			customer = CustomerService.INSTANCE.submitCustomerType(agentId, String.valueOf(orderCustomer.getExternalId()),
					"updateCustomer", customer, notifEventColl,customerContext, errorList);
			logger.info("TimeTakenforUpdateCusomerServiceCall="+(timer.getTime() - startTimer));
		}
		else{
			long startTimer = timer.getTime();
			customer = CustomerService.INSTANCE.submitCustomerType(agentId,customerId,"updateCustomer", customer, null, customerContext, errorList);
			logger.info("TimeTakenforUpdateCusomerServiceCall="+(timer.getTime() - startTimer));
		}
		if(errorList != null && errorList.size() > 0){
			for(CartError cartError: errorList){
				logger.info("UpdateCustomer Error Code:"+cartError.getCode());
				logger.info("UpdateCustomer Error Message:"+cartError.getMessage());
				logger.info("UpdateCustomer Error Description:"+cartError.getDescription());
			}
			throw new UnRecoverableException(errorList.get(0).getMessage());
		}
		logger.info("updateCustomerInfo_end_in_CartOrderController");
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
