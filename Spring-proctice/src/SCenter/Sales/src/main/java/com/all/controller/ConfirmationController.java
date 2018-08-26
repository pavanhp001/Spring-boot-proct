package com.AL.controller;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.webflow.execution.Action;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.AL.html.Fieldset;
import com.AL.productResults.managers.ProductResultsManager;
import com.AL.ui.builder.HtmlBuilder;
import com.AL.ui.constants.ControllerConstants;
import com.AL.ui.domain.dynamic.dialogue.DataGroup;
import com.AL.ui.domain.dynamic.dialogue.Dialogue;
import com.AL.ui.factory.SalesUtilsFactory;
import com.AL.ui.factory.WarmTransferFactory;
import com.AL.ui.service.V.CustomerService;
import com.AL.ui.service.V.DialogServiceUI;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.util.HtmlFactory;
import com.AL.ui.util.SalesUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.Address;
import com.AL.ui.vo.CartError;
import com.AL.ui.vo.ErrorList;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.ui.vo.SalesDialogueVO;
import com.AL.V.exception.BaseException;
import com.AL.V.exception.UnRecoverableException;
import com.AL.V.factory.CustomerFactory;
import com.AL.xml.cm.v4.CustomerContextType;
import com.AL.xml.cm.v4.CustomerType;
import com.AL.xml.cm.v4.EMailAddressType;
import com.AL.xml.cm.v4.NotificationEventCollectionType;
import com.AL.xml.cm.v4.NotificationEventType;
import com.AL.xml.v4.OrderType;

@Controller("ConfirmationController")
public class ConfirmationController extends BaseController implements Action {
	private static final Logger logger = Logger.getLogger(ConfirmationController.class);
	public Event execute(RequestContext request) throws UnRecoverableException {
		HttpServletRequest httpRequest =(HttpServletRequest)request.getExternalContext().getNativeRequest();
		StopWatch timer = new StopWatch();
		long startTimer = 0;
		timer.start();
		HttpSession session = httpRequest.getSession();
		try{
			logger.info("showConfirmation_begin");
			SalesCenterVO salesCenterVo = (SalesCenterVO) httpRequest.getSession().getAttribute("salescontext");
			logger.info("Get_Dialog_Service_started_in_showConfirmation");
			Map<String, Map<String, String>> contextMap = (Map<String, Map<String, String>>)httpRequest.getSession().getAttribute("dynamicFlowContextMap");
			Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");
			dynamicFlow.put("dynamicFlow.page", "Confirmation");
			dynamicFlow.put("GUID", salesCenterVo.getValueByName("GUID"));
			startTimer=timer.getTime();
			if (salesCenterVo.getValueByName("drupalContentUrl") != null 
					&& salesCenterVo.getValueByName("dispDrupalDailgVal") != null ) {
				String dialoguesFromDrupal = SalesUtil.getDialoguesFormDrupalContent(contextMap,salesCenterVo);
				if (Utils.isBlank(dialoguesFromDrupal)){
					generateDialoguesFromService(contextMap,salesCenterVo,request);
				}else{
					request.getFlashScope().put("referrerFlow", (String) httpRequest.getSession().getAttribute("referrerFlowAgentGroup"));	
					request.getFlowScope().put("dialogue" , dialoguesFromDrupal);	
				}
			}else{
				generateDialoguesFromService(contextMap,salesCenterVo,request);
			}
			logger.info("TimeTakenForDialougeServiceCall="+(timer.getTime()-startTimer));
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
	        session.setAttribute("isUtilityOfferExist" , isUtilityOfferExist);
			request.getFlowScope().put("Confirm_NonRR_4",salesCenterVo.getValueByName("consumer.email"));
			OrderType order =(OrderType)httpRequest.getSession().getAttribute("order");
			com.AL.xml.v4.CustAddress custAdr = SalesUtil.INSTANCE.getAddress(order.getCustomerInformation().getCustomer(),
						com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString());
			request.getFlowScope().put("address",getAddress(custAdr));
			logger.info("timeTakenForShowConfirmation="+timer.getTime());
			logger.info("showConfirmation_end");
			return new Event(this, "confirmationEvent");
		}catch (Exception e) {
			request.getFlowScope().put("message", e.getMessage());
			request.getFlowScope().put("pageTitle",httpRequest.getParameter("pageTitle")!=null?httpRequest.getParameter("pageTitle"):"");
			logger.error(e);
			throw new UnRecoverableException(e.getMessage());
		}finally{
			timer.stop();
		}
	}
	
	public void saveConfirmationEmailOnOrder(RequestContext requestContext) throws UnRecoverableException{
		HttpServletRequest request =  (HttpServletRequest)requestContext.getExternalContext().getNativeRequest();
		StopWatch timer = new StopWatch();
		timer.start();
		long startTimer = 0;
		try{
			ErrorList errorList = new ErrorList();
			logger.info("saveConfirmation_begin");
		    HttpSession session = request.getSession();
		    String emailAddress = request.getParameter("Confirm_NonRR_4");
			String sendEmail = request.getParameter("sendEmail");
			Long orderId =(Long)request.getSession().getAttribute("orderId");
			Long customerID =(Long)request.getSession().getAttribute("customerID");
			logger.info("emailAddress=" +emailAddress);
			SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
			String agentId= salesCenterVo.getValueByName("Agent");
			OrderType order =(OrderType)request.getSession().getAttribute("order");
			if(Utils.isBlank(emailAddress)&&Utils.isBlank(order.getCustomerInformation().getCustomer().getBestEmailContact())){
				requestContext.getFlowScope().put("isEmptyEmail",true);
			}
			com.AL.xml.cm.v4.ObjectFactory oFactory = new com.AL.xml.cm.v4.ObjectFactory();
			CustomerType customer = oFactory.createCustomerType();
			customer.setExternalId(customerID);
			logger.info("best_contact_phone="+order.getCustomerInformation().getCustomer().getBestPhoneContact());
			customer.setBestPhoneContact(order.getCustomerInformation().getCustomer().getBestPhoneContact());
			
			boolean isEmailAddressChanged = false;			
			if(Utils.isBlank(emailAddress) && !Utils.isBlank(order.getCustomerInformation().getCustomer().getBestEmailContact())){
				emailAddress = order.getCustomerInformation().getCustomer().getBestEmailContact();
			}
			else if(! Utils.isBlank(emailAddress)){
				String dtCreated = salesCenterVo.getValueByName("dtCreated");
				if( !Utils.isBlank( dtCreated ) )
				{
					isEmailAddressChanged = WarmTransferFactory.INSTANCE.isEmailAddressUpdated(emailAddress, salesCenterVo);
				}
			}
			customer.setBestEmailContact(emailAddress);
			
			if(!Utils.isBlank(emailAddress)){
				EMailAddressType email = new EMailAddressType();
				email.setValue(emailAddress);
				customer.setHomeEMail(email);
			}
			if(!Utils.isBlank(sendEmail)  && sendEmail.equalsIgnoreCase("on")){
				customer.setEMailOptIn(true);
			}
			else{
				customer.setEMailOptIn(false);
			}

			QName NAME = new QName("http://xml.AL.com/v4", "offersPresented");
			JAXBElement<String> offerPresented = new JAXBElement<String>(NAME, String.class, "yes");
			customer.setOffersPresented(offerPresented);
			if(session.getAttribute("primaryLanguage")!=null){
				customer.setPrimaryLanguage(Integer.valueOf(session.getAttribute("primaryLanguage").toString()));
			}

			String GUID = (String)request.getSession().getAttribute("GUID");
			Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
			Map<String, String> sourceEntity = new HashMap<String, String>();
			sourceEntity.put("source", "salescenter");
			sourceEntity.put("GUID", GUID);
			data.put("source", sourceEntity);
			CustomerContextType customerContext = CustomerFactory.INSTANCE.buildCustomerContext(data);	
			Map<String, Map<String, String>> contextMap = (Map<String, Map<String, String>>)request.getSession().getAttribute("dynamicFlowContextMap");
			Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");
			if(isEmailAddressChanged && dynamicFlow.get("dynamicFlow.flowType")!= null 
					&& !dynamicFlow.get("dynamicFlow.flowType").contains("customerLookup")){
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
				startTimer=timer.getTime();
				customer = CustomerService.INSTANCE.submitCustomerType(agentId, customerID.toString(), "updateCustomer", customer, notifEventColl,customerContext, errorList);
				logger.info("TimeTakenforCustomerServiceCall="+(timer.getTime()-startTimer)); 
			
				startTimer=timer.getTime();
				order = OrderService.INSTANCE.getOrderByOrderNumber(salesCenterVo.getValueByName("order.id"), agentId, null,null,false, null);
				logger.info("TimeTakenforOrderServicecall="+(timer.getTime()-startTimer));
				request.getSession().setAttribute(ControllerConstants.ORDER, order);
				logger.info("timeTakenForSaveConfirmation="+timer.getTime());
				
			}else if(customer.isEMailOptIn()){
				startTimer=timer.getTime();
				customer = CustomerService.INSTANCE.submitCustomerType(agentId, customerID.toString(),"updateCustomer", customer, null, customerContext, errorList);
				startTimer=timer.getTime();
				order = OrderService.INSTANCE.getOrderByOrderNumber(salesCenterVo.getValueByName("order.id"), agentId, null,null,false, null);
				logger.info("TimeTakenforOrderServicecall="+(timer.getTime()-startTimer));
				request.getSession().setAttribute(ControllerConstants.ORDER, order);
				logger.info("TimeTakenforCustomerServiceCall="+(timer.getTime()-startTimer)); 
			}
			if(errorList != null && errorList.size() > 0){
				for(CartError cartError: errorList){
				    logger.info("UpdateCustomer_Error_Code" + cartError.getCode());
				    logger.info("UpdateCustomer_Error_Message" + cartError.getMessage());
				    logger.info("UpdateCustomer_Error_Description" + cartError.getDescription());
				}
				throw new UnRecoverableException(errorList.get(0).getMessage());
			}
		}catch (Exception e) {
			requestContext.getFlowScope().put("message", e.getMessage());
			requestContext.getFlowScope().put("pageTitle",request.getParameter("pageTitle")!=null?request.getParameter("pageTitle"):"");
			logger.error("Error_in_ConfirmationController",e);
			throw new UnRecoverableException(e.toString());
		}finally{
			timer.stop();
		}
	}
	
	


	public Event decideViewAfterConfirmationView(RequestContext requestContext) throws UnRecoverableException{
		HttpServletRequest request =  (HttpServletRequest)requestContext.getExternalContext().getNativeRequest();
		try{
		    HttpSession session = request.getSession();
		    ProductResultsManager productResultManager = (ProductResultsManager) session.getAttribute("productResultManager");
		    if (productResultManager.getSaversOfferMap().size() != 0){
		    	request.getSession().setAttribute("offer" , "true");
		    	return new Event(this, "saversOfferEvent");
		    } 
            if (productResultManager.getSaversOfferMap().size() == 0 && productResultManager.getUtilityOffersMap().size() != 0){
            	request.getSession().setAttribute("utilityOffer" , "true");
            	request.getSession().setAttribute("offer" , "false");
            	return new Event(this, "utilityOfferEvent");
		    }
		    if (productResultManager.getSaversOfferMap().size() == 0 && productResultManager.getUtilityOffersMap().size() == 0){
				request.getSession().setAttribute("utilityOffer" , "false");
				request.getSession().setAttribute("offer" , "false");
				return new Event(this, "qualificationEvent");
			}
		}catch (Exception e) {
			requestContext.getFlowScope().put("message", e.getMessage());
			requestContext.getFlowScope().put("pageTitle",request.getParameter("pageTitle")!=null?request.getParameter("pageTitle"):"");
			logger.fatal(e);
			throw new UnRecoverableException(e.toString());
		}
		return new Event(this, ""); 
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
	
	private void generateDialoguesFromService(Map<String, Map<String, String>> contextMap, SalesCenterVO salesCenterVo, RequestContext request) 
			throws UnRecoverableException {
		try {
			SalesDialogueVO dialogueVO = DialogServiceUI.INSTANCE.getDialoguesByContext(contextMap);
			StringBuilder events = new StringBuilder();
			List<Fieldset> fieldsetList = new ArrayList<Fieldset>();
			List<DataGroup> dgList = null;
			for (Dialogue dialogue : dialogueVO.getDialogueList()){
				dgList = dialogue.getDataGroupList();
				for(DataGroup dGroup : dgList){
					fieldsetList = HtmlFactory.INSTANCE.dialogueToFieldSet(events, dialogueVO.getDataFieldMap().get(dialogue.getExternalId()).get(dGroup.getName()), null, false);
					for (Fieldset fieldset : fieldsetList) {
						String element = HtmlBuilder.INSTANCE.toString(fieldset);
						element = salesCenterVo.replaceNamesWithValues(element);
						events.append(element);
					}				
				}
			}  
			request.getFlowScope().put("dialogue" , events.toString());
		} catch (BaseException e) {
			logger.error("Exception_in_ConfirmationController_getDialogues",e);
			throw new UnRecoverableException(e.getMessage());
		}
	}
}