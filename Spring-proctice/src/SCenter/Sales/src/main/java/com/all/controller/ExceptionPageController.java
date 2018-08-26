package com.AL.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.webflow.execution.Action;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.AL.html.Fieldset;
import com.AL.ui.builder.HtmlBuilder;
import com.AL.ui.domain.dynamic.dialogue.DataGroup;
import com.AL.ui.domain.dynamic.dialogue.Dialogue;
import com.AL.ui.service.V.DialogServiceUI;
import com.AL.ui.util.HtmlFactory;
import com.AL.ui.util.LineItemUtil;
import com.AL.ui.util.SalesUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.Address;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.ui.vo.SalesDialogueVO;
import com.AL.V.exception.BaseException;
import com.AL.V.exception.UnRecoverableException;
import com.AL.xml.v4.OrderType;

@Controller("ExceptionPageController")
public class ExceptionPageController implements Action{

	private static final Logger logger = Logger.getLogger(ExceptionPageController.class);
	
	
	public Event execute(RequestContext request) throws UnRecoverableException {
		HttpServletRequest httpRequest =(HttpServletRequest)request.getExternalContext().getNativeRequest();
		StopWatch timer = new StopWatch();
		timer.start();
		long startTimer = 0;
		try{
			SalesCenterVO salesCenterVo = (SalesCenterVO) httpRequest.getSession().getAttribute("salescontext");
			Map<String, Map<String, String>> contextMap = (Map<String, Map<String, String>>)httpRequest.getSession().getAttribute("dynamicFlowContextMap");
			Map<String, String> dynamicFlow = contextMap.get("dynamicFlow");
			dynamicFlow.put("dynamicFlow.page", "Exception");
			dynamicFlow.put("GUID", salesCenterVo.getValueByName("GUID"));
			startTimer=timer.getTime();
			if (salesCenterVo.getValueByName("drupalContentUrl") != null 
					&& salesCenterVo.getValueByName("dispDrupalDailgVal") != null ){
				String dialoguesFromDrupal = SalesUtil.getDialoguesFormDrupalContent(contextMap,salesCenterVo);
				if (Utils.isBlank(dialoguesFromDrupal)){
					generateDialoguesFromService(contextMap,salesCenterVo,request);
				}
				else{
					request.getFlashScope().put("referrerFlow", (String) httpRequest.getSession().getAttribute("referrerFlowAgentGroup"));	
					request.getFlowScope().put("dialogue" , dialoguesFromDrupal);
				}
			}
			else{
				generateDialoguesFromService(contextMap,salesCenterVo,request);
			}
			logger.info("TimeTakenforDialougeServicecall="+(timer.getTime()-startTimer));
			OrderType order =(OrderType)httpRequest.getSession().getAttribute("order");
			com.AL.xml.v4.CustAddress custAdr = SalesUtil.INSTANCE.getAddress(order.getCustomerInformation().getCustomer(),
						com.AL.xml.v4.RoleType.SERVICE_ADDRESS.toString());

			request.getFlowScope().put("address",getAddress(custAdr));
			request.getFlashScope().put("CKOCompletedLineItems" , LineItemUtil.prepareCKOCompletedLinItemsListFromOrder(order));
			logger.info("showExceptionPageData_end");
			logger.info("TimeTakenforshowExceptionPageData="+timer.getTime());
			return new Event(this, "exceptionPageEvent");
		}catch(Exception e){
			request.getFlowScope().put("message", e.getMessage());
			request.getFlowScope().put("pageTitle",httpRequest.getParameter("pageTitle")!=null?httpRequest.getParameter("pageTitle"):"");
			logger.error(e);
			throw new UnRecoverableException(e.getMessage());
		}finally{
			timer.stop();
		}

	}
	
	private void generateDialoguesFromService(
			Map<String, Map<String, String>> contextMap,
			SalesCenterVO salesCenterVo, RequestContext request) throws UnRecoverableException {

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
			logger.error("Exception_in_ExceptionController_getDialogues",e);
			throw new UnRecoverableException(e.getMessage());
		}
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
}
