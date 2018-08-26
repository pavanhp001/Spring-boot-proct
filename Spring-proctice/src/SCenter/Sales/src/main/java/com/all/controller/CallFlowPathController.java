package com.AL.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.webflow.execution.RequestContext;

import com.AL.ui.constants.Constants;
import com.AL.ui.dao.CallflowDao;
import com.AL.ui.dao.ConfigDao;
import com.AL.ui.dao.DiscoveryExclusionReferrersDao;
import com.AL.ui.dao.DrupalDialogueDao;
import com.AL.ui.dao.WebflowDao;
import com.AL.ui.domain.Callflow;
import com.AL.ui.domain.DrupalDialogueEntity;
import com.AL.ui.domain.SystemConfig;
import com.AL.ui.service.config.ConfigRepo;
import com.AL.ui.service.V.DetailService;
import com.AL.ui.util.FlowType;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.DiscoveryExclusionReferrersVO;
import com.AL.V.exception.UnRecoverableException;
import com.AL.xml.dtl.v4.DetailsRequestResponse;
import com.AL.xml.dtl.v4.MetaData;
import com.AL.xml.dtl.v4.OrderSourceResultType;
import com.AL.xml.dtl.v4.TelephonyType;

@Component
public class CallFlowPathController {

	@Autowired
	private CallflowDao callFlowDao;

	@Autowired
	private WebflowDao webflowDao;

	@Autowired
	protected ConfigDao systemPropertiesDao;
	
	@Autowired
	protected DrupalDialogueDao drupalDialogueDao;
	
	@Autowired
	private DiscoveryExclusionReferrersDao discoveryExclusionReferrersDao;

	private static final Logger logger = Logger.getLogger(CallFlowPathController.class);
	
	
	private static final String SIMPLE_CHOICE = "simplechoice";
	private static final String SIMPLE_CHOICE_DOT = "simpleChoice.";
	private static final String FIRST_ENERGY = "firstenergy";
	private static final String FIRST_ENERGY_DOT = "firstEnergy.";
	private static final String PECO = "peco";
	private static final String PECO_DOT = "peco.";
	private static final String RESIDENTIAL = "residential";
	private static final String RESIDENTIAL_DOT = "residential.";
	private static final String MOVERS = "movers";
	private static final String NON_MOVERS = "nonMovers";
	private static final String COMMERCIAL = "commercial";
	

	public String getWebflow(HttpServletRequest request,
			HttpServletResponse response, RequestContext context)
			throws UnRecoverableException {
		try {

			String referalId = request.getParameter("referralDet");
			request.getSession().setAttribute("referralDet", referalId);

			systemPropertiesDao.sync();
			String customerTracker= ConfigRepo.getString("*.display_customer_tracker");
			request.getSession().setAttribute("customerTracker", Boolean.valueOf(customerTracker));
			
			logger.info("storing_value_in_systemConfig");

			
			logger.info("referalId=" + referalId);
			String xmlInput = request.getParameter("dtxml");
			request.getSession().setAttribute("dtxml", xmlInput);
			String sameCallValue = request.getParameter("sameCall");
			logger.info("sameCallValue=" + sameCallValue);
			request.getSession().setAttribute("sameCall", sameCallValue);

			Callflow callFlow = null;
			String webflowPath = "";
			String agentGroup = (String) request.getSession().getAttribute("userGroup");
            String agentProductGroup = (String) request.getSession().getAttribute("userGroupProduct");
			String[] refArray = referalId.split("\\|");
			referalId = refArray[0];
			String referrerName = refArray[1];
			String vdnValue = refArray[2];
			String referrerFlow = getReferrerDetails(referalId, vdnValue);
			callFlow = callFlowDao.get(Long.parseLong(referalId));
			String logCallFlow = callFlow != null ? "successfully" : "empty callflow";
			logger.info("retrievedCallFlow=" + logCallFlow);
			logger.info("referrerFlow=" + referrerFlow);
			boolean dispDrupalDailgVal = false;
			
			if(Utils.isBlank(agentGroup)) {
				agentGroup = "defaultUserGroup";
			}
			if(Utils.isBlank(agentProductGroup)) {
				agentProductGroup = "defaultUserGroup";
			}
			
			if (callFlow == null) 
			{
				dispDrupalDailgVal = getDisplayDrupalDailougeValue(referrerFlow);
				request.getSession().setAttribute("dispDrupalDailgVal",dispDrupalDailgVal);	
			}
			
			if (callFlow != null) 
			{
				webflowPath = webflowDao.getPath(callFlow.getWebflowId());
				logger.info("webflowPath="+webflowPath);
				
				if(Utils.isBlank(webflowPath))
				{
					return "mvcFlow";
				}
				
				String[] flowPathValues = webflowPath.split("/");
				
				StringBuilder builder = new StringBuilder();
				boolean isResidential = false;
				
				for(String value : flowPathValues)
				{
					if(value.equalsIgnoreCase(SIMPLE_CHOICE))
					{
						builder.append(SIMPLE_CHOICE_DOT);
					}
					else if(value.equalsIgnoreCase(FIRST_ENERGY))
					{
						builder.append(FIRST_ENERGY_DOT);
					}
					else if(value.equalsIgnoreCase(PECO))
					{
						builder.append(PECO_DOT);
					}
					else if(value.equalsIgnoreCase(RESIDENTIAL))
					{
						isResidential = true;
					}
					else if(value.equalsIgnoreCase(MOVERS))
					{
						if(isResidential)
						{
							builder.append(RESIDENTIAL_DOT);
						}
						builder.append(MOVERS);
					}
					else if(value.equalsIgnoreCase(NON_MOVERS))
					{
						builder.append(NON_MOVERS);
					}
					else if(value.equalsIgnoreCase(COMMERCIAL))
					{
						builder.append(COMMERCIAL);
					}
				}
				if (builder != null && builder.toString() != null && builder.toString().contains("peco.commercial")){
					request.getSession().setAttribute("customerType","commercial");
				}
				else if (builder != null && builder.toString() != null && builder.toString().contains("peco.movers")){
					request.getSession().setAttribute("customerType","residential");
				}
				else if (builder != null && builder.toString() != null && builder.toString().contains("peco.nonMovers")){
					request.getSession().setAttribute("customerType","residential");
				}
				String appVersion = ( !Utils.isBlank( ConfigRepo.getString("dialogue.dynamicflow_version") ) ) ? ConfigRepo.getString("dialogue.dynamicflow_version") : "1.0";
				logger.debug("appVersion="+appVersion);
				
				Map<String, String> dynamicFlow = new HashMap<String, String>();
				dynamicFlow.put("dynamicFlow.enabled", "true");
				dynamicFlow.put("dynamicFlow.appVersion", appVersion);
				dynamicFlow.put("dynamicFlow.flowType", builder.toString());
				dynamicFlow.put("dynamicFlow.userGroup", agentGroup);
				
				Map<String, Map<String, String>> contextMap = new HashMap<String, Map<String, String>>();
			    contextMap.put("dynamicFlow", dynamicFlow);
			    request.getSession().setAttribute("referrerFlow",webflowPath);
			    request.getSession().setAttribute("dynamicFlowContextMap",contextMap);
			    request.getSession().setAttribute("webflowPath",webflowPath);
			    dispDrupalDailgVal = getDisplayDrupalDailougeValue(builder.toString());
			    request.getSession().setAttribute("dispDrupalDailgVal",dispDrupalDailgVal);	
			    request.getSession().setAttribute("referrerFlowAgentGroup",webflowPath + "-" + agentGroup + "-" + agentProductGroup);
				return webflowPath;
			}
			else if(!Utils.isBlank(referrerFlow) && (referrerFlow.equalsIgnoreCase("webReferrer")||
					referrerFlow.equalsIgnoreCase("webMicro")||referrerFlow.equalsIgnoreCase("webCCP"))){
				
				String appVersion = ( !Utils.isBlank( ConfigRepo.getString("dialogue.dynamicflow_version") ) ) ? ConfigRepo.getString("dialogue.dynamicflow_version") : "1.0";
				logger.debug("appVersion="+appVersion);
				webflowPath = "webCallFlow";
				Map<String, String> dynamicFlow = new HashMap<String, String>();
				dynamicFlow.put("dynamicFlow.enabled", "true");
				dynamicFlow.put("dynamicFlow.appVersion", appVersion);
				dynamicFlow.put("dynamicFlow.flowType", referrerFlow);
				dynamicFlow.put("dynamicFlow.userGroup", agentGroup);
				
				Map<String, Map<String, String>> contextMap = new HashMap<String, Map<String, String>>();
			    contextMap.put("dynamicFlow", dynamicFlow);
			    request.getSession().setAttribute("referrerFlow",referrerFlow);	
			    request.getSession().setAttribute("dynamicFlowContextMap",contextMap);
			    request.getSession().setAttribute("webflowPath",webflowPath);
			    request.getSession().setAttribute("referrerFlowAgentGroup",referrerFlow + "-" + agentGroup + "-" + agentProductGroup);
				return webflowPath;
			}
			else if(!Utils.isBlank(referrerFlow) && (referrerFlow.equalsIgnoreCase("confirm"))){
				
				String appVersion = ( !Utils.isBlank( ConfigRepo.getString("dialogue.dynamicflow_version") ) ) ? ConfigRepo.getString("dialogue.dynamicflow_version") : "1.0";
				logger.debug("appVersion="+appVersion);
				
				webflowPath = "confirm";
				Map<String, String> dynamicFlow = new HashMap<String, String>();
				dynamicFlow.put("dynamicFlow.enabled", "true");
				dynamicFlow.put("dynamicFlow.appVersion", appVersion);
				dynamicFlow.put("dynamicFlow.flowType", referrerFlow);
				dynamicFlow.put("dynamicFlow.userGroup", agentGroup);
				
				Map<String, Map<String, String>> contextMap = new HashMap<String, Map<String, String>>();
			    contextMap.put("dynamicFlow", dynamicFlow);
			    request.getSession().setAttribute("referrerFlow",referrerFlow);	
			    request.getSession().setAttribute("dynamicFlowContextMap",contextMap);
			    request.getSession().setAttribute("webflowPath",webflowPath);
			    request.getSession().setAttribute("referrerFlowAgentGroup",referrerFlow + "-" + agentGroup + "-" + agentProductGroup);
				return webflowPath;
			}
            else if(!Utils.isBlank(referrerFlow) && (referrerFlow.equalsIgnoreCase("nonConfirm"))){
				
				String appVersion = ( !Utils.isBlank( ConfigRepo.getString("dialogue.dynamicflow_version") ) ) ? ConfigRepo.getString("dialogue.dynamicflow_version") : "1.0";
				logger.debug("appVersion="+appVersion);
				
				webflowPath = "nonConfirm";
				Map<String, String> dynamicFlow = new HashMap<String, String>();
				dynamicFlow.put("dynamicFlow.enabled", "true");
				dynamicFlow.put("dynamicFlow.appVersion", appVersion);
				dynamicFlow.put("dynamicFlow.flowType", referrerFlow);
				dynamicFlow.put("dynamicFlow.userGroup", agentGroup);
				
				Map<String, Map<String, String>> contextMap = new HashMap<String, Map<String, String>>();
			    contextMap.put("dynamicFlow", dynamicFlow);
			    request.getSession().setAttribute("referrerFlow",referrerFlow);	
			    request.getSession().setAttribute("dynamicFlowContextMap",contextMap);
			    request.getSession().setAttribute("webflowPath",webflowPath);
			    request.getSession().setAttribute("referrerFlowAgentGroup",referrerFlow + "-" + agentGroup + "-" + agentProductGroup);
				return webflowPath;
			}
			else if(!Utils.isBlank(referrerFlow) && (referrerFlow.equalsIgnoreCase("referrerSpecificConfirm"))){
				
				String appVersion = ( !Utils.isBlank( ConfigRepo.getString("dialogue.dynamicflow_version") ) ) ? ConfigRepo.getString("dialogue.dynamicflow_version") : "1.0";
				logger.debug("appVersion="+appVersion);
				
				webflowPath = "referrerSpecificConfirm";
				Map<String, String> dynamicFlow = new HashMap<String, String>();
				dynamicFlow.put("dynamicFlow.enabled", "true");
				dynamicFlow.put("dynamicFlow.appVersion", appVersion);
				dynamicFlow.put("dynamicFlow.flowType", referrerFlow);
				dynamicFlow.put("dynamicFlow.userGroup", agentGroup);
				
				Map<String, Map<String, String>> contextMap = new HashMap<String, Map<String, String>>();
			    contextMap.put("dynamicFlow", dynamicFlow);
			    Map<String, String> orderSource = new HashMap<String, String>();
				orderSource.put("orderSource.referrer", referalId);
				contextMap.put("orderSource", orderSource);
				
			    request.getSession().setAttribute("referrerFlow",referrerFlow);	
			    request.getSession().setAttribute("dynamicFlowContextMap",contextMap);
			    request.getSession().setAttribute("webflowPath",webflowPath);
			    request.getSession().setAttribute("referrerFlowAgentGroup",referrerFlow + "-" + agentGroup + "-" + agentProductGroup);
				return webflowPath;
			}
			else if(!Utils.isBlank(referrerFlow) && (referrerFlow.equalsIgnoreCase("referrerSpecificNonConfirm"))){
				DiscoveryExclusionReferrersVO discoveryExclusionReferrersVO = discoveryExclusionReferrersDao.getDiscoveryExclusionReferrers(Long.parseLong(referalId));
				logger.info("discoveryExclusionReferrersVO="+discoveryExclusionReferrersVO);
				if(discoveryExclusionReferrersVO == null){
					webflowPath = "referrerSpecificNonConfirm";
				}
				else{
					webflowPath = "discoveryExclusionReferrerSpecificNonConfirm";
				}
				logger.info("webflowPath="+webflowPath);
				String appVersion = ( !Utils.isBlank( ConfigRepo.getString("dialogue.dynamicflow_version") ) ) ? ConfigRepo.getString("dialogue.dynamicflow_version") : "1.0";
				logger.debug("appVersion="+appVersion);

				Map<String, String> dynamicFlow = new HashMap<String, String>();
				dynamicFlow.put("dynamicFlow.enabled", "true");
				dynamicFlow.put("dynamicFlow.appVersion", appVersion);
				dynamicFlow.put("dynamicFlow.flowType", referrerFlow);
				dynamicFlow.put("dynamicFlow.userGroup", agentGroup);

				Map<String, Map<String, String>> contextMap = new HashMap<String, Map<String, String>>();
				contextMap.put("dynamicFlow", dynamicFlow);
				Map<String, String> orderSource = new HashMap<String, String>();
				orderSource.put("orderSource.referrer", referalId);
				contextMap.put("orderSource", orderSource);
				request.getSession().setAttribute("referrerFlow",referrerFlow);	
				request.getSession().setAttribute("dynamicFlowContextMap",contextMap);
				request.getSession().setAttribute("webflowPath",webflowPath);
				request.getSession().setAttribute("referrerFlowAgentGroup",referrerFlow + "-" + agentGroup + "-" + agentProductGroup);
				return webflowPath;
			}
            else if(!Utils.isBlank(referrerFlow) && (referrerFlow.equalsIgnoreCase("agentTransfer"))){
				
				String appVersion = ( !Utils.isBlank( ConfigRepo.getString("dialogue.dynamicflow_version") ) ) ? ConfigRepo.getString("dialogue.dynamicflow_version") : "1.0";
				logger.debug("appVersion="+appVersion);
				
				webflowPath = "agentTransfer";
				Map<String, String> dynamicFlow = new HashMap<String, String>();
				dynamicFlow.put("dynamicFlow.enabled", "true");
				dynamicFlow.put("dynamicFlow.appVersion", appVersion);
				dynamicFlow.put("dynamicFlow.flowType", referrerFlow);
				dynamicFlow.put("dynamicFlow.userGroup", agentGroup);
				
				Map<String, Map<String, String>> contextMap = new HashMap<String, Map<String, String>>();
			    contextMap.put("dynamicFlow", dynamicFlow);
			    request.getSession().setAttribute("referrerFlow",referrerFlow);	
			    request.getSession().setAttribute("dynamicFlowContextMap",contextMap);
			    request.getSession().setAttribute("webflowPath",webflowPath);
			    request.getSession().setAttribute("referrerFlowAgentGroup",referrerFlow + "-" + agentGroup + "-" + agentProductGroup);
				return webflowPath;
			}
             else if(!Utils.isBlank(referrerFlow) && (referrerFlow.equalsIgnoreCase("MDU"))){
				String appVersion = ( !Utils.isBlank( ConfigRepo.getString("dialogue.dynamicflow_version") ) ) ? ConfigRepo.getString("dialogue.dynamicflow_version") : "1.0";
				logger.debug("appVersion="+appVersion);
				
				webflowPath = "mdu";
				Map<String, String> dynamicFlow = new HashMap<String, String>();
				dynamicFlow.put("dynamicFlow.enabled", "true");
				dynamicFlow.put("dynamicFlow.appVersion", appVersion);
				dynamicFlow.put("dynamicFlow.flowType", referrerFlow);
				dynamicFlow.put("dynamicFlow.userGroup", agentGroup);
				
				Map<String, Map<String, String>> contextMap = new HashMap<String, Map<String, String>>();
			    contextMap.put("dynamicFlow", dynamicFlow);
			    dynamicFlow.put("dynamicFlow.flowType", "mdu");
			    request.getSession().setAttribute("dynamicFlowContextMap",contextMap);
			    request.getSession().setAttribute("webflowPath",webflowPath);
			    request.getSession().setAttribute("referrerFlowAgentGroup", referrerFlow + "-" + agentGroup + "-" + agentProductGroup);
				return webflowPath;
			}
            else if(!Utils.isBlank(referrerFlow) && (referrerFlow.equalsIgnoreCase("USAA"))){
				
				String appVersion = ( !Utils.isBlank( ConfigRepo.getString("dialogue.dynamicflow_version") ) ) ? ConfigRepo.getString("dialogue.dynamicflow_version") : "1.0";
				logger.debug("appVersion="+appVersion);
				
				webflowPath = "usaa";
				Map<String, String> dynamicFlow = new HashMap<String, String>();
				dynamicFlow.put("dynamicFlow.enabled", "true");
				dynamicFlow.put("dynamicFlow.appVersion", appVersion);
				dynamicFlow.put("dynamicFlow.flowType", referrerFlow);
				dynamicFlow.put("dynamicFlow.userGroup", agentGroup);
				
				Map<String, Map<String, String>> contextMap = new HashMap<String, Map<String, String>>();
			    contextMap.put("dynamicFlow", dynamicFlow);
			    request.getSession().setAttribute("referrerFlow",referrerFlow);	
			    request.getSession().setAttribute("dynamicFlowContextMap",contextMap);
			    request.getSession().setAttribute("webflowPath",webflowPath);
			    request.getSession().setAttribute("referrerFlowAgentGroup",referrerFlow + "-" + agentGroup + "-" + agentProductGroup);
				return webflowPath;
			}
            else if(!Utils.isBlank(referrerFlow) && (referrerFlow.equalsIgnoreCase("customerLookup"))){
				
				String appVersion = ( !Utils.isBlank( ConfigRepo.getString("dialogue.dynamicflow_version") ) ) ? ConfigRepo.getString("dialogue.dynamicflow_version") : "1.0";
				logger.debug("appVersion="+appVersion);
				
				webflowPath = "customerLookup";
				Map<String, String> dynamicFlow = new HashMap<String, String>();
				dynamicFlow.put("dynamicFlow.enabled", "true");
				dynamicFlow.put("dynamicFlow.appVersion", appVersion);
				dynamicFlow.put("dynamicFlow.flowType", referrerFlow);
				dynamicFlow.put("dynamicFlow.userGroup", agentGroup);
				
				Map<String, Map<String, String>> contextMap = new HashMap<String, Map<String, String>>();
			    contextMap.put("dynamicFlow", dynamicFlow);
			    Map<String, String> orderSource = new HashMap<String, String>();
				orderSource.put("orderSource.referrer", referalId);
				contextMap.put("orderSource", orderSource);
			    request.getSession().setAttribute("referrerFlow",referrerFlow);	
			    request.getSession().setAttribute("dynamicFlowContextMap",contextMap);
			    request.getSession().setAttribute("webflowPath",webflowPath);
			    request.getSession().setAttribute("referrerFlowAgentGroup",referrerFlow + "-" + agentGroup + "-" + agentProductGroup);
				return webflowPath;
			}
            else if(!Utils.isBlank(referrerFlow) && (referrerFlow.equalsIgnoreCase("consumersInteractions"))){
				
				String appVersion = ( !Utils.isBlank( ConfigRepo.getString("dialogue.dynamicflow_version") ) ) ? ConfigRepo.getString("dialogue.dynamicflow_version") : "1.0";
				logger.debug("appVersion="+appVersion);
				
				webflowPath = "consumersInteractions";
				Map<String, String> dynamicFlow = new HashMap<String, String>();
				dynamicFlow.put("dynamicFlow.enabled", "true");
				dynamicFlow.put("dynamicFlow.appVersion", appVersion);
				dynamicFlow.put("dynamicFlow.flowType", referrerFlow);
				dynamicFlow.put("dynamicFlow.userGroup", agentGroup);
				
				Map<String, Map<String, String>> contextMap = new HashMap<String, Map<String, String>>();
			    contextMap.put("dynamicFlow", dynamicFlow);
			    request.getSession().setAttribute("referrerFlow",referrerFlow);	
			    request.getSession().setAttribute("dynamicFlowContextMap",contextMap);
			    request.getSession().setAttribute("webflowPath",webflowPath);
			    request.getSession().setAttribute("referrerFlowAgentGroup",referrerFlow + "-" + agentGroup + "-" + agentProductGroup);
				return webflowPath;
			}
			else if(!Utils.isBlank(referrerFlow) && (referrerFlow.equalsIgnoreCase("homeserve"))){
				
				String appVersion = ( !Utils.isBlank( ConfigRepo.getString("dialogue.dynamicflow_version") ) ) ? ConfigRepo.getString("dialogue.dynamicflow_version") : "1.0";
				logger.debug("appVersion="+appVersion);
				
				webflowPath = "homeserve";
				Map<String, String> dynamicFlow = new HashMap<String, String>();
				dynamicFlow.put("dynamicFlow.enabled", "true");
				dynamicFlow.put("dynamicFlow.appVersion", appVersion);
				dynamicFlow.put("dynamicFlow.flowType", referrerFlow);
				dynamicFlow.put("dynamicFlow.userGroup", agentGroup);
				
				Map<String, Map<String, String>> contextMap = new HashMap<String, Map<String, String>>();
			    contextMap.put("dynamicFlow", dynamicFlow);
			    request.getSession().setAttribute("referrerFlow",referrerFlow);	
			    request.getSession().setAttribute("dynamicFlowContextMap",contextMap);
			    request.getSession().setAttribute("webflowPath",webflowPath);
			    request.getSession().setAttribute("referrerFlowAgentGroup",referrerFlow + "-" + agentGroup + "-" + agentProductGroup);
			    request.getSession().setAttribute("sameCall", null);
				return webflowPath;
			}

            else{
				request.getSession().setAttribute("referrerFlow",referrerFlow);	
			}
			
			return "mvcFlow";

		} catch (Exception e) {
			logger.error(e);
			logger.warn("Error_in_CallFlowPathController",e);
			throw new UnRecoverableException(e.getMessage());
		}
	}
	

	public String  getReferrerDetails(String refId, String vdnValue) throws Exception
	{
		StopWatch timer=new StopWatch();
		timer.start();
		int detailsCacheTimeout = ConfigRepo.getInt("*.details_cache_time_out") == 0 ? 7200000 : ConfigRepo.getInt("*.details_cache_time_out");
		long startTimer = timer.getTime();
		DetailsRequestResponse drr = DetailService.INSTANCE.getAllOrderSources("12345", Long.valueOf(detailsCacheTimeout)); 
		logger.info("TimeTakenforgetAllOrderSources=" +(timer.getTime() - startTimer));
		timer.stop();
		String referrerFlow = "confirm";
		if(drr != null && !(drr.equals(""))){
			List<OrderSourceResultType> osrtList =  drr.getResponse().getOrderSourceResultElement();
			for(OrderSourceResultType orst: osrtList){
				String referrerId = orst.getOrderSource().getBusinessParty().getExternalId();

				List<TelephonyType> telephonyList = orst.getOrderSource().getTelephonyList().getTelephony();
				for(TelephonyType telephonyType : telephonyList){
					if(Constants.SALES.equalsIgnoreCase(telephonyType.getCallType())){
						//String vdn = orst.getOrderSource().getTelephonyList().getTelephonies()
						// ideally it should be when refid equals referrerId and when vdn equals vdnuec
						if(refId.equals(referrerId) && vdnValue.equals(telephonyType.getVdn()) ){
							if(orst.getOrderSource().getProgram().getMetadataList() != null &&
									orst.getOrderSource().getProgram().getMetadataList().getMetadata()!= null){
								for(MetaData metaData:orst.getOrderSource().getProgram().getMetadataList().getMetadata()){
									if(metaData.getName().equalsIgnoreCase("FLOW")){
										referrerFlow =  metaData.getValue();
									}
								}
							}
						}
					}
				}
			}
		}
		return referrerFlow;
	}
	
	private String getReferrerFlowName(String referrerFlow,String referrerName){
		if(!Utils.isBlank(referrerFlow)){
			if(referrerFlow.equalsIgnoreCase("webMicro")){
			   return FlowType.WEBMICRO.getRefferName();
			}
            if(referrerFlow.equalsIgnoreCase("webReferrer")){
				return FlowType.WEBREFRRER.getRefferName();
			}
             if(referrerFlow.equalsIgnoreCase("webCCP")){
                  return FlowType.WEBCCP.getRefferName();
			}
            if(referrerFlow.equalsIgnoreCase("confirm")){
            	 return FlowType.CONFIRM.getRefferName();
 			}
            if(referrerFlow.equalsIgnoreCase("nonConfirm")){
            	 return FlowType.NONCONFIRM.getRefferName();
 			}
            if(referrerFlow.equalsIgnoreCase("agentTransfer")){
            	 return FlowType.AGENTTRANSFER.getRefferName();
 			}
            if(referrerFlow.equalsIgnoreCase("referrerSpecificConfirm")||referrerFlow.equalsIgnoreCase("referrerSpecificNonConfirm")){
            	if(referrerName.contains("Dominion")){
            		referrerName = "Dominion";
            	}
            	else if(referrerName.contains("Nicor")){
            		referrerName = "Nicor";
            	}
            	else if(referrerName.contains("NSTAR")){
            		referrerName = "NSTAR";
            	}
            	else if(referrerName.contains("Pacific Gas and Electric")){
            		referrerName = "PG&E";
            	}
            	else if(referrerName.contains("San Diego Gas and Electric")){
            		referrerName = "SDG&E";
            	}
            	else if(referrerName.contains("Southern California Gas Company")){
            		referrerName = "SoCal";
            	}
            	else if(referrerName.contains("Xcel")){
            		referrerName = "Xcel";
            	}
           	     return referrerName;
			}
		}
       return "";
	}
  
	private boolean getDisplayDrupalDailougeValue(String referrerFlow) {
		// TODO Auto-generated method stub
		boolean drupalDisplayVal =  false;
		if(!Utils.isBlank(referrerFlow)){
			DrupalDialogueEntity drupalDialogueEntity = drupalDialogueDao.get(referrerFlow);
			if(drupalDialogueEntity != null){
				drupalDisplayVal = Boolean.valueOf(drupalDialogueEntity.getDisplayValue());
			}
		}
		logger.info("DrupalDisplayVal="+drupalDisplayVal);
		return drupalDisplayVal;
	}

}
