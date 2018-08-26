package com.AL.filter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.AL.managers.ApplicationContextProvider;
import com.AL.ui.constants.ControllerConstants;
import com.AL.ui.dao.SalesCallDao;
import com.AL.ui.dao.SalesSessionDao;
import com.AL.ui.domain.SalesSession;
import com.AL.ui.factory.CartSalesContextFactory;
import com.AL.ui.service.V.LineItemService;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.util.LineItemUtil;
import com.AL.ui.util.SalesUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.ErrorList;
import com.AL.ui.vo.SalesCenterVO;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.v4.LineItemStatusCodesType;
import com.AL.xml.v4.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;

public enum UpdateInvalidOrdersFactory {

	INSTANCE;
	private final Logger logger = Logger.getLogger(UpdateInvalidOrdersFactory.class);

	public void updateSalesContextBySession(HttpSession session, OrderManagementRequestResponse requestResponse) {

		try{
			logger.info("updateSalesContextBySession_begin_in_UpdateInvalidOrdersFactory");
			OrderType order = requestResponse.getResponse().getOrderInfo().get(0);
			String agentId="default";
			if(order!=null && !Utils.isBlank(order.getAgentId())){
				agentId = order.getAgentId();
			}

			if(order !=null)
			{
				String GUID = (String)session.getAttribute("GUID");
				if(GUID == null)
				{
					GUID = UUID.randomUUID().toString();
				}
				int zipOnlySearch = 0;
				SalesCenterVO salesCenterVo = (SalesCenterVO) session.getAttribute("salescontext");
				if(salesCenterVo != null && salesCenterVo.getValueByName("isZipOnlySearch") != null && salesCenterVo.getValueByName("isZipOnlySearch").equalsIgnoreCase("YES")){
					zipOnlySearch = 1;
				}
				updateSalesSession(session,requestResponse);
				SalesContextType salesContext = requestResponse.getResponse().getSalesContext(); 
				if( order.getLineItems().getLineItem().size() == 0)
				{
					OrderService.INSTANCE.updateSalesContext(agentId, String.valueOf(order.getExternalId()), CartSalesContextFactory.INSTANCE.getStatusCompleteSalesContext(salesContext), SalesContextFactory.INSTANCE.getAttribute(salesContext, "GUID"), zipOnlySearch);
				}
				else
				{
					List<String> listOflineItemIds = LineItemUtil.containsUtilityOffers(order);
					logger.info("utilityOfferLineItemIds=" + listOflineItemIds);
					if (!listOflineItemIds.isEmpty())
					{
						List<Integer> reasons = new ArrayList<Integer>();
						reasons.add(ControllerConstants.REASON_CODE);

						LineItemService.INSTANCE.updateLineItemStatus(agentId, String.valueOf(order.getExternalId()),
								listOflineItemIds, LineItemStatusCodesType.CANCELLED_REMOVED.value(),
								LineItemStatusCodesType.CANCELLED_REMOVED.value(), reasons, salesContext,new ErrorList());
					}

					List<String> lineitem_id_list =  LineItemUtil.containsSaversOffers(order);
					logger.info("saversLineItemId=" + lineitem_id_list);
					if( !lineitem_id_list.isEmpty())
					{
						LineItemService.INSTANCE.submitMultipleLineItem( agentId, String.valueOf(order.getExternalId()), lineitem_id_list,
								CartSalesContextFactory.INSTANCE.getStatusCompleteSalesContext(salesContext), zipOnlySearch);	
					}
					else
					{
						if(salesContext != null)
						{
							OrderService.INSTANCE.updateSalesContext(agentId, String.valueOf(order.getExternalId()), CartSalesContextFactory.INSTANCE.getStatusCompleteSalesContext(salesContext), SalesContextFactory.INSTANCE.getAttribute(salesContext, "GUID"), zipOnlySearch);
						}
					}
				}
			}
			logger.info("updateSalesContextBySession_end_in_UpdateInvalidOrdersFactory");
		}catch(Exception e){
			logger.warn("error_while_updating_sales_context_session_status",e);
		}

	}

	private void updateSalesSession(HttpSession session,
			OrderManagementRequestResponse requestResponse) {
		try{
			if(ApplicationContextProvider.getApplicationContext() != null 
					&& ApplicationContextProvider.getApplicationContext().getBean(SalesSessionDao.class) != null
					       && ApplicationContextProvider.getApplicationContext().getBean(SalesCallDao.class) != null){
				logger.info("start_updateSalesSession");
				String ambDisconnectDateTime = (String)session.getAttribute("ambDisconnectdatetime");
				OrderType order = requestResponse.getResponse().getOrderInfo().get(0);
				SalesSessionDao salesSessionDao = ApplicationContextProvider.getApplicationContext().getBean(SalesSessionDao.class);
				SalesCallDao salesCallDao = ApplicationContextProvider.getApplicationContext().getBean(SalesCallDao.class);
				SalesSession salesSession = new SalesSession();
				Long orderId = (Long)order.getExternalId();
				Long customerId = (Long)order.getCustomerInformation().getCustomer().getExternalId();
				Calendar disconnectCal = Calendar.getInstance();
				if(customerId != null && orderId != null){
					salesSession = salesSessionDao.getSalesSession(orderId, customerId);
				}
				salesSession.setDispositionId(11295L);
				salesSession.setEndTime(Calendar.getInstance());
				if(customerId != null){
					salesSessionDao.updateSalesSession(salesSession);
				}else{
					salesSessionDao.insertSalesSession(salesSession);
				}
				if(!Utils.isBlank(ambDisconnectDateTime)){
					String ambDisconnectDateTime2 = ambDisconnectDateTime.substring(0, 31);
					String ambDisconnectDateTime3 = ambDisconnectDateTime.substring(31, ambDisconnectDateTime.length());
					ambDisconnectDateTime = ambDisconnectDateTime2 + ":" + ambDisconnectDateTime3;
					disconnectCal.setTime(SalesUtil.INSTANCE.sdf.parse(ambDisconnectDateTime));
					salesCallDao.updateSalesCallWithAmbDisconnectDateTime((Long)session.getAttribute("salesCallId"), disconnectCal);
				}
				logger.info("end_updateSalesSession");
			}else{
				logger.info("no_updateSalesSession");
			}
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
