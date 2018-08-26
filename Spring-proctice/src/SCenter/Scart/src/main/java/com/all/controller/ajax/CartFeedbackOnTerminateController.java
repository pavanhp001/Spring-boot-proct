package com.AL.controller.ajax;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.AL.ui.dao.MetricDao;
import com.AL.ui.util.MetricsUtil;
import com.AL.ui.vo.SalesCenterVO;
 

@Controller
public class CartFeedbackOnTerminateController extends AbstractController  {

	private static final Logger logger = Logger.getLogger(CartFeedbackOnTerminateController.class);
	
	@Autowired
    private MetricDao metric;
	
	@RequestMapping(value = "/on_terminate" )
	public @ResponseBody String onTerminate(HttpServletRequest request) {
		
		String data = request.getParameter("data");
		logger.debug("data="+data);
		try {
			JSONObject feedback = new JSONObject(data);
			JSONObject CKO = feedback.getJSONObject("CKO");
			
			String orderId = CKO.getString("orderId");
			logger.debug("orderId="+orderId);
			
			String errorCode = CKO.getString("errorCode");
			logger.debug("errorCode="+errorCode);
			
			String customerId = CKO.getString("customerId");
			logger.debug("customerId="+customerId);
			
			//TODO:GET JSON STRING THAT WAS PASSED
			//TODO:CONVERT JSON STRING TO JAVA OBJECT
			//TODO:CREATE METRICS ENTRY...each metric has a different Factory class.
			JSONArray feedbackJson = CKO.getJSONObject("params").getJSONArray("string");
			
			//TODO: use the below code when latest json is used
			//MetricsUtil.INSTANCE.updateMetrics(feedbackJson);
			request.getSession().setAttribute("pivotAssistJson","");
			Long salesSessionId = (Long)request.getSession().getAttribute("salesSessionId");
			SalesCenterVO salesCenterVo = (SalesCenterVO) request.getSession().getAttribute("salescontext");
			String agentId = salesCenterVo.getValueByName("agent.id");
			MetricsUtil.INSTANCE.updateMetrics(metric,feedbackJson, salesSessionId, agentId, request);
		} catch (Exception e) {
			logger.error("error_in_CartFeedbackOnTerminateController",e);
		}
		logger.debug("data="+data);
		
		return data;
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {

		return new ModelAndView();
	}
}
