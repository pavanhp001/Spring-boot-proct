package com.A.interceptor;

import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.A.productResults.util.Utils;
import com.A.ui.dao.MetricDao;
import com.A.ui.dao.SalesSessionDao;
import com.A.ui.domain.Metric;
import com.A.ui.domain.SalesSession;
import com.A.ui.vo.SessionVO;

@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = Logger.getLogger(RequestInterceptor.class);
	@Autowired
	MetricDao metricDao;
	
	@Autowired
	private SalesSessionDao salesSessionDao;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		String url = request.getRequestURI();
		
		logger.info("RequestInterceptor preHandle called");
		
		if(request.getAttribute("isSessionTimeOut")!=null){
			boolean istimedout = (Boolean)request.getAttribute("isSessionTimeOut");
			if(istimedout){
				updateTimeOutIntoSalesSession(request);
			}
		}
		if(url.indexOf("login.htm") == -1 && url.indexOf("vdnShow") == -1){
			HttpSession session = request.getSession();
			if(session != null){
				String previousPageTitle = "";
				if(session.getAttribute("previousPageTitle") != null){
					previousPageTitle = (String) session.getAttribute("previousPageTitle");
				}
				String currentPageTitle = request.getParameter("pageTitle");
				String fromUtility = (String)session.getAttribute("fromUtility");
				if(!Utils.isBlank(currentPageTitle)){
					if(currentPageTitle.contains("/")){
						currentPageTitle = getDiscPageHeader(currentPageTitle);
					}
				}
				if((previousPageTitle.equalsIgnoreCase("offers")|| previousPageTitle.equalsIgnoreCase("Recommendations")) && Utils.isBlank(currentPageTitle) && session.getAttribute("callTimeBeforeUtility")!=null 
						&& fromUtility!=null && "yes".equalsIgnoreCase(fromUtility)){
					try{
						Metric metric = new Metric();
						Double webMetricEndTime = Double.valueOf(Calendar.getInstance().getTimeInMillis());
						currentPageTitle = "Utility Offer";
						Double webMetricStartTime = (Double)session.getAttribute("callTimeBeforeUtility");
						Double webMetricMValue = webMetricEndTime - webMetricStartTime;
						metric.setmValue(webMetricMValue);
						metric.setPage(currentPageTitle);
						metric.setName("Elapsed Time");
						if(!Utils.isBlank(request.getParameter("agentId"))){
							metric.setAgent(request.getParameter("agentId"));
							session.setAttribute("agentId",request.getParameter("agentId"));
						}
						else if(session.getAttribute("agentId")!= null){
							metric.setAgent((String)session.getAttribute("agentId"));
						}
						if(!Utils.isBlank(request.getParameter("providerIdVal"))){
							String providerIdVal = request.getParameter("providerIdVal");
							metric.setProvider(Long.valueOf(providerIdVal));
							session.setAttribute("providerIdVal",providerIdVal);
						}
						else if(session.getAttribute("providerIdVal")!=null){
							String providerIdVal = (String)session.getAttribute("providerIdVal");
							metric.setProvider(Long.valueOf(providerIdVal));
						}
						metric.setSalesSessionId((Long)session.getAttribute("salesSessionId"));
						metric.setDateEffectiveFrom(Calendar.getInstance());
						metricDao.put(metric);
						session.setAttribute("callTimeBeforeUtility",null);
						session.setAttribute("fromUtility",null);
						session.setAttribute("previousPageTitle",currentPageTitle);
						session.setAttribute("webMetricStartTime",webMetricEndTime);
					}
					catch(Exception e){
						logger.warn("Error_in_preHandle",e);
					}

				
				}else if(!Utils.isBlank(previousPageTitle) && !Utils.isBlank(currentPageTitle) && (! currentPageTitle.equalsIgnoreCase("IdlePage")) 
						&& (! currentPageTitle.equalsIgnoreCase("RecommendationsByCategory"))){
					if(! previousPageTitle.equalsIgnoreCase(currentPageTitle)){	
						try{
							Metric metric = new Metric();
							Double webMetricEndTime = Double.valueOf(Calendar.getInstance().getTimeInMillis());
							Double webMetricStartTime = (Double)session.getAttribute("webMetricStartTime");
							Double webMetricMValue = webMetricEndTime - webMetricStartTime;
							metric.setmValue(webMetricMValue);
							metric.setPage(currentPageTitle);
							metric.setName("Elapsed Time");
							if(!Utils.isBlank(request.getParameter("agentId"))){
								metric.setAgent(request.getParameter("agentId"));
								session.setAttribute("agentId",request.getParameter("agentId"));
							}
							else if(session.getAttribute("agentId")!= null){
								metric.setAgent((String)session.getAttribute("agentId"));
							}
							if(!Utils.isBlank(request.getParameter("providerIdVal"))){
								String providerIdVal = request.getParameter("providerIdVal");
								metric.setProvider(Long.valueOf(providerIdVal));
								session.setAttribute("providerIdVal",providerIdVal);
							}
							else if(session.getAttribute("providerIdVal")!=null){
								String providerIdVal = (String)session.getAttribute("providerIdVal");
								metric.setProvider(Long.valueOf(providerIdVal));
							}
							metric.setSalesSessionId((Long)session.getAttribute("salesSessionId"));
							metric.setDateEffectiveFrom(Calendar.getInstance());
							metricDao.put(metric);
							session.setAttribute("previousPageTitle",currentPageTitle);
							session.setAttribute("webMetricStartTime",webMetricEndTime);
						}
						catch(Exception e){
							logger.warn("Error_in_preHandle",e);
						}

					}
				}else if(((!Utils.isBlank(request.getParameter("CategoryName")) 
						&& !request.getParameter("CategoryName").equalsIgnoreCase("PP")) 
						|| !Utils.isBlank(request.getParameter("aidval")))	
							|| ((!Utils.isBlank(request.getParameter("page_id"))) 
						 	&& (request.getParameter("page_id").equalsIgnoreCase("Order Summary")
							|| request.getParameter("page_id").equalsIgnoreCase("Recommendations")))){
					try{
						Metric metric = new Metric();
						if((!Utils.isBlank(request.getParameter("page_id"))) 
								&& (request.getParameter("page_id").equalsIgnoreCase("Order Summary")
								|| request.getParameter("page_id").equalsIgnoreCase("Recommendations"))){
								metric.setPage((String)request.getParameter("page_id"));
								currentPageTitle = request.getParameter("page_id");
						}
						else if(!Utils.isBlank(request.getParameter("CategoryName"))){
							metric.setPage((String)request.getParameter("CategoryName"));
							currentPageTitle = request.getParameter("CategoryName");
						}
					/*	else if(!Utils.isBlank(request.getParameter("aidval"))){
							String anchor = request.getParameter("aidval");
							JSONObject feedback = new JSONObject(anchor);
							String productname = feedback.getString("productname");
							productname = productname.substring(0, Math.min(productname.length(), 20));
							metric.setProvider(Long.valueOf(feedback.getString("partnerExternalId")));
							metric.setPage(productname);
							currentPageTitle = productname;
						}*/
						
						else if(!Utils.isBlank(request.getParameter("aidval"))){
						       
						       String anchor = request.getParameter("aidval");
						       if(anchor.startsWith("[{")){
						        JSONArray SyntheticBdfeedback = new JSONArray(anchor);

						        for (int k = 0; k < SyntheticBdfeedback.length() ; k++) {

						         JSONObject feedback = SyntheticBdfeedback.getJSONObject(k);
						         
						         String productname = feedback.getString("productname");
						         productname = productname.substring(0, Math.min(productname.length(), 20));
						         metric.setProvider(Long.valueOf(feedback.getString("partnerExternalId")));
						         metric.setPage(productname);
						         currentPageTitle = productname;
						        }
						        
						       }else{
						        JSONObject feedback = new JSONObject(anchor);
						        
						        String productname = feedback.getString("productname");
						        productname = productname.substring(0, Math.min(productname.length(), 20));
						        metric.setProvider(Long.valueOf(feedback.getString("partnerExternalId")));
						        metric.setPage(productname);
						        currentPageTitle = productname;
						       }
						       
						       
						      }
						if(! previousPageTitle.equalsIgnoreCase(currentPageTitle)){	
							//insertWebMetrixData(request, );
							Double webMetricEndTime = Double.valueOf(Calendar.getInstance().getTimeInMillis());
							Double webMetricStartTime = (Double)session.getAttribute("webMetricStartTime");
							Double webMetricMValue = webMetricEndTime - webMetricStartTime;
							metric.setmValue(webMetricMValue);
							metric.setName("Elapsed Time");
							if(!Utils.isBlank(request.getParameter("agentId"))){
								metric.setAgent(request.getParameter("agentId"));
								session.setAttribute("agentId",request.getParameter("agentId"));
							}
							else if(session.getAttribute("agentId")!= null){
								metric.setAgent((String)session.getAttribute("agentId"));
							}
							if(!Utils.isBlank(request.getParameter("providerIdVal"))){
								String providerIdVal = request.getParameter("providerIdVal");
								if(metric.getProvider() == null){
									metric.setProvider(Long.valueOf(providerIdVal));
								}
								session.setAttribute("providerIdVal",providerIdVal);
							}
							else if(session.getAttribute("providerIdVal")!=null){
								String providerIdVal = (String)session.getAttribute("providerIdVal");
								if(metric.getProvider() == null){
									metric.setProvider(Long.valueOf(providerIdVal));
								}
							}
							metric.setSalesSessionId((Long)session.getAttribute("salesSessionId"));
							metric.setDateEffectiveFrom(Calendar.getInstance());
							metricDao.put(metric);
							session.setAttribute("previousPageTitle",currentPageTitle);
							session.setAttribute("webMetricStartTime",webMetricEndTime);
						}
					}
					catch(Exception e){
						logger.warn("Error_in_preHandle",e);
						e.printStackTrace();
					}
				}else if((!Utils.isBlank(currentPageTitle))){
					Double webMetricStartTime = Double.valueOf(Calendar.getInstance().getTimeInMillis());
					session.setAttribute("previousPageTitle",currentPageTitle);
					session.setAttribute("webMetricStartTime",webMetricStartTime);
				}
			}
		}
		return true;
	}
	
	private String getDiscPageHeader(String discPageURL) {
		String disconPageHeader = null;
		if(discPageURL != null){
			if(discPageURL.toUpperCase().contains("GREETING")){
				disconPageHeader = "Greetings";
			}
			else if(discPageURL.toUpperCase().contains("BASICINFO")){
				disconPageHeader = "Basic Information";
			}
			else if(discPageURL.toUpperCase().contains("COFIRMATION")){
				disconPageHeader = "Confirmation";
			}
			else if(discPageURL.toUpperCase().contains("UTILITYOFFER")){
				disconPageHeader = "Utility Offer";
			}
			else if(discPageURL.toUpperCase().contains("OFFER")){
				disconPageHeader = "Offers";
			}
			else if(discPageURL.toUpperCase().contains("QUALIFICATION")){
				disconPageHeader = "Qualification";
			}
			else if(discPageURL.toUpperCase().contains("DISCOVERYNEW")){
				disconPageHeader = "Discovery Questions: New Services";
			}
			else if(discPageURL.toUpperCase().contains("DISCOVERYTRANSFER")){
				disconPageHeader = "Discovery Questions: Transfer Services";
			}
			else if(discPageURL.toUpperCase().contains("DISCOVERY")){
				disconPageHeader = "Discovery";
			}
			else if(discPageURL.toUpperCase().contains("CLOSINGCALL")){
				disconPageHeader = "Close Call";
			}
			else if(discPageURL.toUpperCase().contains("RECOMMENDATIONS")){
				disconPageHeader = "Recommendations";
			}
			else if(discPageURL.toUpperCase().contains("DISCOVERY")){
				disconPageHeader = "Discovery";
			}
			else if(discPageURL.toUpperCase().contains("RECAP")){
				disconPageHeader = "Order Recap";
			}
			else if(discPageURL.toUpperCase().contains("SUMMARY")){
				disconPageHeader = "Order Summary";
			}
			else if(discPageURL.toUpperCase().contains("CKO")){
				disconPageHeader = "CKO";
			}
		}
		return disconPageHeader;
	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler,ModelAndView model ) throws Exception {
		HttpSession session = request.getSession();
		SessionVO sessionVO = (SessionVO)session.getAttribute("sessionVO");
		if(sessionVO==null)
			sessionVO = new SessionVO(request.getSession().getId());
		if(sessionVO.get("callStartTime") != null){
			long callStartTime = (Long) session.getAttribute("callStartTime");
			long currentTime = Calendar.getInstance().getTimeInMillis();
			long time_diff = currentTime -  callStartTime ;
			
			sessionVO.getData().put("time_diff", time_diff);
		}else{
			sessionVO.getData().put("time_diff", 0);
		}
		request.getSession().setAttribute("sessionVO", sessionVO);
		//return true;
	}
	public void updateTimeOutIntoSalesSession(HttpServletRequest request) {
		SalesSession salesSession = new SalesSession();
		Long dispositionId = 11293L;
		if(request.getParameter("customerIdVal")!= null && request.getParameter("orderId")!=null ){
			salesSession = salesSessionDao.getSalesSession(Long.valueOf(request.getParameter("orderId")), Long.valueOf(request.getParameter("customerIdVal")));
			salesSession.setEndTime(Calendar.getInstance());
			salesSession.setDispositionId(dispositionId);
			salesSessionDao.updateSalesSession(salesSession);
		}

	}
}

