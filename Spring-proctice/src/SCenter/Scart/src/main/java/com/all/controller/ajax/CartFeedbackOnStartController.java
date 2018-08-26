package com.AL.controller.ajax;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.AL.ui.dao.MetricDao;
import com.AL.ui.util.MetricsUtil;
import com.AL.ui.vo.SalesCenterVO;
 
@Controller
public class CartFeedbackOnStartController extends AbstractController  {

	private static final Logger logger = Logger.getLogger(CartFeedbackOnStartController.class);
	
	@Autowired
    private MetricDao metric;
	
	@RequestMapping(value = "/on_start" )
	public ResponseEntity<String> onStart(HttpServletRequest request) {
		
		 String data = request.getParameter("data");
		 try {
			JSONObject feedback = new JSONObject(data);
			JSONObject CKO = feedback.getJSONObject("CKO");
			JSONArray feedbackJson = CKO.getJSONObject("params").getJSONArray("string");
			Long salesSessionId = (Long)request.getSession().getAttribute("salesSessionId");
			SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
			String agentId = salesCenterVo.getValueByName("agent.id");
			logger.debug(feedbackJson);
			
			//TODO: use the below code when latest json is used
			//MetricsUtil.INSTANCE.updateMetrics(feedbackJson);
			
			//MetricsUtil.INSTANCE.updateMetrics(metric, feedbackJson, salesSessionId, agentId, request);
		} catch (Exception e) {
			logger.error("error_in_CartFeedbackOnStartController",e);
		}
		 
		return null;
	}
	
	@RequestMapping(value = "/on_submit" )
	public ResponseEntity<String> onSubmit(HttpServletRequest request) {
		
		 String data = request.getParameter("data");
		 try {
			JSONObject feedback = new JSONObject(data);
			JSONObject CKO = feedback.getJSONObject("CKO");
			JSONArray feedbackJson = CKO.getJSONObject("params").getJSONArray("string");
			Long salesSessionId = (Long)request.getSession().getAttribute("salesSessionId");
			SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
			String agentId = salesCenterVo.getValueByName("agent.id");
			//TODO: use the below code when latest json is used
			//MetricsUtil.INSTANCE.updateMetrics(feedbackJson);
			
			MetricsUtil.INSTANCE.updateMetrics(metric,feedbackJson, salesSessionId, agentId, request);
		} catch (JSONException e) {
			logger.error("error_in_CartFeedbackOnStartController",e);
		}
		 
		return null;
	}

	@RequestMapping(value = "/warmtransfer_event" )
	public ResponseEntity<String> saveWarmTransferEvent(HttpServletRequest request) {
		//logger.info("isWarmTransferEnabled=false");
		String wtDisabledProviderID = request.getParameter("wtDisabledProviderID");
		logger.info("wtDisabledProviderID"+wtDisabledProviderID);
		if(wtDisabledProviderID != null){
			if(request.getSession().getAttribute("wtDisabledProviderIDList")!= null){
				List<String> wtDisabledProviderIDList = (List<String>) request.getSession().getAttribute("wtDisabledProviderIDList");
				wtDisabledProviderIDList.add(wtDisabledProviderID);
				request.getSession().setAttribute("wtDisabledProviderIDList", wtDisabledProviderIDList);
				logger.info("wtDisabledProviderIDList="+wtDisabledProviderIDList);
			}
			else{
				List<String> wtDisabledProviderIDList = new ArrayList<String>();
				wtDisabledProviderIDList.add(wtDisabledProviderID);
				request.getSession().setAttribute("wtDisabledProviderIDList", wtDisabledProviderIDList);
				logger.info("wtDisabledProviderIDList="+wtDisabledProviderIDList);
			}
		}
		return null;
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {

		return new ModelAndView();
	}
}
