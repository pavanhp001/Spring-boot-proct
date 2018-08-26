package com.AL.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.AL.beans.SearchOrderBean;
import com.AL.dao.WebOrderDao;
import com.AL.domain.WebOrder;
import com.AL.service.RepositoryService;
import com.AL.ui.service.V.CustomerService;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.vo.FileStream;
import com.AL.util.CompressorUtil;
import com.AL.util.DateUtil;
import com.AL.util.OrderUtil;
import com.AL.util.StreamUtil;
import com.AL.xml.cm.v4.CustomerData;
import com.AL.xml.cm.v4.CustomerSearch;
import com.AL.xml.v4.Customer;
import com.AL.xml.v4.OrderType;
import com.google.gson.Gson;

/**
 * @author mnagineni@AL.com
 *
 */
@Controller
public class SalesOrderCaptureController 
{

	/**
	 * 
	 */
	private static final Logger logger = Logger.getLogger(SalesOrderCaptureController.class);

	/**
	 * 
	 */
	@Autowired
	WebOrderDao webOrderDao;

	/**
	 * 
	 */
	@Autowired
	RepositoryService s3Service;

	/**
	 * Request Mapping method to access Sales Order View page
	 * 
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/home")
	public ModelAndView getSalesOrder(HttpServletRequest request)
	{
		logger.info("---------------- Going to Sales Order View --------------------------------");

		ModelAndView mav = new ModelAndView();
		mav.setViewName("sales.ordercapture");
		mav.addObject("searchResults", "no");
		return mav;
	}

	/**
	 * Ajax Mapping method to search Sales Order using given input
	 * 
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/salesOrderSearch", method=RequestMethod.POST)
	public @ResponseBody String getSalesOrderSearch(HttpServletRequest request)
	{
		JSONObject response = new JSONObject();
		List<WebOrder> orderList = new ArrayList<WebOrder>();
		Map<String, String> customerMap = new HashMap<String, String>();
		List<SearchOrderBean> searchData = null;
		String searchType = request.getParameter("searchType");

		logger.info("---------------- Sales Order Search Type:["+searchType+"] ----------------");

		if(StringUtils.isNotBlank(searchType))
		{
			if(searchType.equals("getWebOrdersWithAgent"))
			{
				String agentId = request.getParameter("agentId");
				String orderStartDate = request.getParameter("cal1");
				String orderEndDate = request.getParameter("cal2");

				logger.info("---------------- Building Customer Map for AgentId Search ----------------");

				if(StringUtils.isNotBlank(agentId))
				{
					customerMap.put("agentId", agentId);
				}
				if(StringUtils.isNotBlank(orderStartDate))
				{
					customerMap.put("orderStartDate", orderStartDate);
				}
				if(StringUtils.isNotBlank(orderEndDate))
				{
					customerMap.put("orderEndDate", orderEndDate);
				}

				logger.debug("---------------- Customer Map for AgentId Search:"+customerMap.toString()+" ----------------");
			} 
			else if(searchType.equals("getWebOrdersWithOrder"))
			{
				logger.info("---------------- Fetch WebOrders by Order Id ----------------");
				String orderId = request.getParameter("orderId");
				orderList = webOrderDao.getWebOrders(orderId);
			} 
			else if(searchType.equals("agentWithOrderId"))
			{
				logger.info("---------------- Fetch WebOrders by Order Id for AgentId ----------------");
				String agentOrderId = request.getParameter("agentOrderId");
				orderList = webOrderDao.getWebOrders(agentOrderId);
			} 
			else if(searchType.equals("getWebOrdersWithUcid"))
			{
				logger.info("---------------- Fetch WebOrders by Ucid ----------------");
				String Ucid = request.getParameter("ucid");
				List<String> orderIds = webOrderDao.getOrderIdsWithUCID(Ucid);

				if(orderIds != null && orderIds.size() == 1)
				{
					//If the UCID search has only one record show the screens directly
					logger.info("---------------- Single Order Id found Fetching Web Orders ----------------");
					orderList = webOrderDao.getWebOrders(orderIds.get(0));
				}
				else
				{
					logger.info("---------------- Multiple Order Ids found building Customer search results ----------------");
					//Maximum Orders will be 2 for UCID
					searchData = getSearchData(orderIds);
				}
			}
			/*else if(searchType.equals("getWebOrdersWithGuid"))
			{
				logger.info("---------------- Fetch WebOrders by GUID ----------------");
				String guid = request.getParameter("guid");
				List<String> orderIds = webOrderDao.getOrderIdsWithGUID(guid);
				if(orderIds != null && orderIds.size() == 1)
				{
					//If the GUID search has only one record show the screens directly
					logger.info("---------------- Single Order Id found Fetching Web Orders ----------------");
					orderList = webOrderDao.getWebOrders(orderIds.get(0));
				}
				else
				{
					logger.info("---------------- Multiple Order Ids found building Customer search results ----------------");
					//Maximum Orders will be 2 for GUID
					searchData = getSearchData(orderIds);
				}
			}*/else if(searchType.equals("getWebOrdersWithLineItemId"))
			{
				logger.info("---------------- Fetch WebOrders by LineItemId ----------------");
				String lineItemId = request.getParameter("lineItemId");
				List<String> orderIds = webOrderDao.getOrderIdsWithLineItemId(lineItemId);
				
				if(orderIds != null && orderIds.size() == 1)
				{
					//If the LineItemId search has only one record show the screens directly
					logger.info("---------------- Single Order Id found Fetching Web Orders ----------------");
					orderList = webOrderDao.getWebOrders(orderIds.get(0));
				}
				else
				{
					logger.info("---------------- Multiple Order Ids found building Customer search results ----------------");
					//Maximum Orders will be 2 for GUID
					searchData = getSearchData(orderIds);
				}
			}
			else if(searchType.equals("getWebOrdersWithCustomer"))
			{
				logger.info("---------------- Building Customer Map for Customer Search ----------------");

				if(StringUtils.isNotBlank(request.getParameter("firstName")))
				{
					customerMap.put("firstname", request.getParameter("firstName"));
				}
				if(StringUtils.isNotBlank(request.getParameter("lastName")))
				{
					customerMap.put("lastname", request.getParameter("lastName"));
				}
				if(StringUtils.isNotBlank(request.getParameter("zipCode")))
				{
					customerMap.put("zipcode", request.getParameter("zipCode"));
				}
				if(StringUtils.isNotBlank(request.getParameter("phoneNo")))
				{
					customerMap.put("phone", request.getParameter("phoneNo"));
				}
				if(StringUtils.isNotBlank(request.getParameter("emailId")))
				{
					customerMap.put("email", request.getParameter("emailId"));
				}
				if(StringUtils.isNotBlank(request.getParameter("address")))
				{
					customerMap.put("streetName", request.getParameter("address"));
				}

				logger.debug("---------------- Customer Map for Customer Search:"+customerMap.toString()+" ----------------");
			}
		}

		if(searchData == null)
		{
			searchData = getSearchData(customerMap);
		}

		try 
		{
			Gson gson = new Gson();
			logger.info("response="+response);
			if(response!=null){
			response.put("orderList",  new JSONArray(gson.toJson(orderList)));
			response.put("searchCustomerData",  new JSONArray(gson.toJson(searchData)));
			}else{
				logger.info("Error retrieving order");
			}
		} 
		catch (JSONException e)
		{
			logger.error(" Error while building response "+e);
		}

		return response.toString();
	}


	/**
	 * Method to get the List of Orders using customer inputs
	 * 
	 * @param customerMap
	 * @return List<SearchOrderBean>
	 */ 
	private List<SearchOrderBean> getSearchData(Map<String, String> customerMap) 
	{
		List<SearchOrderBean> searchData = new ArrayList<SearchOrderBean>();

		if(customerMap.size() > 0)
		{
			logger.info("---------------- Fetching Customer Data from CM ----------------");
			CustomerSearch custSearchResult = CustomerService.INSTANCE.searchCustomer(customerMap, 0);
			
			if(custSearchResult != null &&
			   custSearchResult.getSearchResult() != null)
			{
				logger.info("---------------- Building Customer Data ----------------");
				for(CustomerData customerData: custSearchResult.getSearchResult())
				{
					SearchOrderBean bean = new SearchOrderBean();
					bean.setAddress(checkNull(customerData.getAddress()));
					bean.setAgentId(checkNull(customerData.getAgentId()));
					bean.setCustomerName(checkNull(customerData.getFirstName()+" "+customerData.getLastName()));
					bean.setOrderDate(checkNull(DateUtil.toDateString(customerData.getOrderDate()) +" " + DateUtil.toTimeString(customerData.getOrderDate())));
					bean.setOrderId(customerData.getOrderId());
					searchData.add(bean);
				}
			}
		}
		return searchData;
	}


	/**
	 * Method to get List of orders using order id's
	 * 
	 * @param orderIds
	 * @return List<SearchOrderBean>
	 */
	private List<SearchOrderBean> getSearchData(List<String> orderIds) 
	{
		List<SearchOrderBean> searchData = null;

		if(orderIds.size() > 0)
		{
			searchData = new ArrayList<SearchOrderBean>();
			for(String id : orderIds)
			{
				logger.info("---------------- Fetching order from OME ----------------");

				OrderType order = OrderService.INSTANCE.getOrderByOrderNumber(id,
						"default", null, "*", Boolean.TRUE, null);
				if(order != null &&  
						order.getCustomerInformation() != null &&
						order.getCustomerInformation().getCustomer() != null)
				{
					logger.info("---------------- Building Cutomer Search result from Order ----------------");
					Customer customer = order.getCustomerInformation().getCustomer();
					SearchOrderBean bean = new SearchOrderBean();

					bean.setAddress(checkNull(OrderUtil.INSTANCE.getAddress(customer)));
					bean.setAgentId(checkNull(order.getAgentId()));
					bean.setCustomerName(checkNull(customer.getFirstName()+" "+customer.getLastName()));
					bean.setOrderDate(checkNull(DateUtil.toDateString(order.getOrderDate()) +" " + DateUtil.toTimeString(order.getOrderDate())));
					bean.setOrderId(order.getExternalId());
					searchData.add(bean);
				}
			}
		}
		
		return searchData;
	}

	/**
	 * Ajax Mapping method to search Sales Order using given id
	 * 
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/getSalesOrderPage")
	public @ResponseBody String getSalesOrderPage(HttpServletRequest request) 
	{
		
		String page_id = request.getParameter("page_id");
		String compressedString = "";
		logger.info("---------------- Key for S3: ["+ page_id +"] ----------------");
		FileStream fs;
		try {
			if(page_id!=null){
				fs = s3Service.getObjectByName(page_id);
				compressedString = StreamUtil.getStringFromInputStream(fs.getInputStream());
				if(compressedString != null)
				{
					compressedString = escapeSpecialCharacters(CompressorUtil.uncompressString(compressedString));
					return compressedString;
				}
			}
		}
		catch (IOException e) {
			logger.info("error="+e);
		}
		return compressedString;
	}

	private String checkNull(String value)
	{
		return (StringUtils.isNotBlank(value) && !value.equalsIgnoreCase("null")) ? value : "--";
	}
	
	/**
	 * @param str
	 * @return
	 */
	public String escapeSpecialCharacters(String str){
		if(str!=null){
			//this is for - mark
			str = str.replaceAll("\u2013", "&#8211;");
			//this is for right single quotation mark
			str = str.replaceAll("\u2019", "&#8217;");
			//this is for left single quotation mark
			str = str.replaceAll("\u2018", "&#8216;");
			//this is for left double quotation mark
			str = str.replaceAll("\u201C", "&#8220;");
			//this is for right double quotation mark
			str = str.replaceAll("\u201D", "&#8221;");
			//this is for ellipsis 
			str = str.replaceAll("\u2026","&#8230;");
			//replace SM special character
			str = str.replaceAll("℠","<span class='superScript'>SM</span>");
		}
		return str;
	}

}
