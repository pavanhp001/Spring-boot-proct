package com.AL.ui.util;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.AL.ui.dao.MetricDao;
import com.AL.ui.domain.Metric;
import com.edb.core.Logger;

public enum MetricsUtil {
	
	INSTANCE;
	
	
	
	public void updateMetrics( MetricDao metric,JSONObject feedbackJson, HttpServletRequest request) {

		try {
			HttpSession session = request.getSession();
			String previousPageTitle = "";
			if(session.getAttribute("previousPageTitle") != null){
				previousPageTitle = (String) session.getAttribute("previousPageTitle");
			}
			
			String agent = feedbackJson.getString("agent");
			if(agent == null){
				agent = "1";
			}
			String currentPageTitle = "";
			if(!Utils.isBlank((String)request.getParameter("CKO_pageTitle"))){
				currentPageTitle = (String)request.getParameter("CKO_pageTitle");
			}else{
				currentPageTitle = feedbackJson.getString("page");
			}
			if(! previousPageTitle.equalsIgnoreCase(currentPageTitle)){	
				Double webMetricStartTime = (Double)session.getAttribute("webMetricStartTime");
				Double webMetricEndTime = Double.valueOf(Calendar.getInstance().getTimeInMillis());
				Double webMetricMValue = webMetricEndTime - webMetricStartTime;
				Long provider = feedbackJson.getLong("provider");
				session.setAttribute("webMetricStartTime",webMetricEndTime);
				JSONArray metrics = feedbackJson.getJSONArray("metrics");
				for(int k=0; k < metrics.length(); k++){
					String met = metrics.getString(k);
					String name = (met.split("_")[0]);
					Double mValue = Double.valueOf(webMetricMValue);
					
					Metric m = new Metric();
					m.setAgent(agent);
					m.setDateEffectiveFrom(Calendar.getInstance());
					m.setmValue(mValue);
					m.setName(name);
					m.setPage(currentPageTitle);
					m.setProvider(provider);
	
					metric.put(m);
					session.setAttribute("previousPageTitle",currentPageTitle);
				}
			}else if((!Utils.isBlank(currentPageTitle))){
				Double webMetricStartTime = Double.valueOf(Calendar.getInstance().getTimeInMillis());
				session.setAttribute("previousPageTitle",currentPageTitle);
				session.setAttribute("webMetricStartTime",webMetricStartTime);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void updateMetrics( MetricDao metric,JSONArray feedbackJsonArr, Long salesSessionId, String agentId, HttpServletRequest request) {

		try {
			HttpSession session = request.getSession();
			String previousPageTitle = "";
			if(session.getAttribute("previousPageTitle") != null){
				previousPageTitle = (String) session.getAttribute("previousPageTitle");
			}
			String currentPageTitle = (String)request.getParameter("CKO_pageTitle");
			if(! previousPageTitle.equalsIgnoreCase(currentPageTitle)){	
				Double webMetricStartTime = (Double)session.getAttribute("webMetricStartTime");
				Double webMetricEndTime = Double.valueOf(Calendar.getInstance().getTimeInMillis());
				Double webMetricMValue = webMetricEndTime - webMetricStartTime;
				session.setAttribute("webMetricStartTime",webMetricEndTime);
				Metric m = new Metric();
				m.setDateEffectiveFrom(Calendar.getInstance());
				m.setSalesSessionId(salesSessionId);
				m.setmValue(webMetricMValue);
				m.setName("Elapsed Time");
				m.setAgent(agentId);
				if(!Utils.isBlank(currentPageTitle)){
					m.setPage(currentPageTitle);
				}
				for(int i=0; i < feedbackJsonArr.length(); i++){
					updateMetricsObject(m, feedbackJsonArr.getString(i));
				}
				session.setAttribute("previousPageTitle",currentPageTitle);
				
				//updateSampleMetrics(m);
				metric.put(m);
			}else if((!Utils.isBlank(currentPageTitle))){
				Double webMetricStartTime = Double.valueOf(Calendar.getInstance().getTimeInMillis());
				session.setAttribute("previousPageTitle",currentPageTitle);
				session.setAttribute("webMetricStartTime",webMetricStartTime);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	private void updateMetricsObject(Metric m, String object) {
		
		if(object.indexOf("page_id=") != -1){
			if(Utils.isBlank(m.getPage())){
			    m.setPage((object.split("=")[1]));
			}
		} else if(object.indexOf("provider_id=") != -1){
			m.setProvider(Long.valueOf(object.split("=")[1]));
		} else if(object.indexOf("agent=") != -1){
			m.setAgent((object.split("=")[1]));
		}
		
	}
	
	private void updateSampleMetrics(Metric m) {
		
		if(m.getAgent() == null){
			m.setAgent("1");
		}
		
		if(m.getmValue() == null){
			m.setmValue(Double.valueOf(1));
		}
		
    	if(m.getName() == null){
			m.setName("2");
		}
    	
    	if(m.getPage() == null){
    		m.setPage("3");
		}
    	
    	if(m.getProvider() == null){
    		m.setProvider(4L);
		}
    	
    	if(m.getSalesSessionId() == null){
    		m.setSalesSessionId(5L);
		}
		
	}
}
