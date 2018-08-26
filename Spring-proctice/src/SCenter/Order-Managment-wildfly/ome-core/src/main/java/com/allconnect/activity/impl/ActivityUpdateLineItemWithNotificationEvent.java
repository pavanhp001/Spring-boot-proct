package com.AL.activity.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.AL.activity.ActivityScheduleJob;
import com.AL.enums.LineItemStatus;
import com.AL.factory.NotificationEventFactory;
import com.AL.ie.activity.Activity;
import com.AL.ie.activity.impl.ActivityGetProvider;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.V.beans.entity.BusinessParty;
import com.AL.Vdao.dao.BusinessPartyDao;
import com.AL.xml.v4.LineItemDetailType.DetailType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderType;

public class ActivityUpdateLineItemWithNotificationEvent implements Activity {

	/* ACDC-1056 commented as per generic logic for FallOut Orders
     * 
     * private static final String VERIZON_PROVIDER = "4353598";
    private static final String VERIZON_COFE_PROVIDER = "15498701";
    private static final String CENTURY_LINK_PROVIDER = "32416075";
    private static final String MONITRONICS_PROVIDER = "32946482";*/

    @Autowired
    private BusinessPartyDao businessPartyDao;

    @Autowired
    private ActivityScheduleJob activityScheduleJob;

    private static final String PROVIDER_DATA_MANAGER_SUBMISSION = " is eligible for submission; however, the Data Manager will handle submission via the broadcast";
    private static final String DEFAULT_PROVIDER_EXTERNAL_ID = "100";
    final int USE_RTIM_AS_REAL_TIME_PROCESSOR = 1;
    final int SCHEDULE_NON_RTIM_PROVIDER_INTEGRATION = 2;

    private static final Logger logger = Logger.getLogger(ActivityUpdateLineItemWithNotificationEvent.class);

    public void process(OrchestrationContext params) {

    }

    public void addProviderCommunicationEvent(String user, Request request, OrderType newOrderInfoResponse, boolean doAllowAllProviders, boolean updateScheduler) {

	List<String> lineItemSubmitList = request.getLineitemIdList();
	
	// Tweaking the code related to lineItemId or lineitemId list
	if (request.getLineItemId() != null && !request.getLineItemId().equals("")) {
		if (lineItemSubmitList != null) {
			lineItemSubmitList.add(request.getLineItemId());
		} else {
			lineItemSubmitList = new ArrayList<String>();
			lineItemSubmitList.add(request.getLineItemId());
		}
	}

	try {
	    if ((lineItemSubmitList != null) && (newOrderInfoResponse != null) && (newOrderInfoResponse.getLineItems() != null)
		    && (newOrderInfoResponse.getLineItems().getLineItemList() != null)) {

		for (String lineItemNumber : lineItemSubmitList) {

		    LineItemType lineItemType = null;
		    if ((newOrderInfoResponse != null) && (newOrderInfoResponse.getLineItems() != null) && (newOrderInfoResponse.getLineItems().getLineItemList() != null)) {

			List<LineItemType> lineItemTypeList = newOrderInfoResponse.getLineItems().getLineItemList();

			searchloop: for (LineItemType searchForLineItemType : lineItemTypeList) {
			    if (searchForLineItemType.getExternalId() == Long.valueOf(lineItemNumber)) {
				lineItemType = searchForLineItemType;
				break searchloop;
			    }
			}

		    }

		    if (lineItemType != null) {

			logger.debug("ActivityUpdateLineItemWithNotificationEvent:addProviderCommunicationEvent():lineItemType: " + lineItemType.getExternalId());

			String providerExternalId = ActivityGetProvider.INSTANCE.getProvider(lineItemType);

			BusinessParty businessParty = businessPartyDao.findBusinessPartyById(providerExternalId);

			Boolean isFallBackOrder = Boolean.FALSE;
			if (providerExternalId != null ) {
			    logger.info("Validating  fall out order [Provider : " + providerExternalId +"]");
			    isFallBackOrder = ActivityFalloutOrderValidation.INSTANCE.isFalloutOrder(lineItemType);
			    logger.info("Is fall out order : " + isFallBackOrder);
			}

			logger.debug("ActivityUpdateLineItemWithNotificationEvent:addProviderCommunicationEvent():lineItemType: " + businessParty.isRealtimeProvider()
				+ " :doAllowAllProviders: " + doAllowAllProviders + " :providerExternalId: " + providerExternalId + " :isVZFalloutOrder : " + isFallBackOrder);

			if ((businessParty != null) && !isFallBackOrder && ((doAllowAllProviders) || (businessParty.isRealtimeProvider() == USE_RTIM_AS_REAL_TIME_PROCESSOR)) // Configured
																			  // to
																			  // use
																			  // RTIM
																			  // to
																			  // communicate
																			  // with
																			  // provider
				&& (lineItemType.getLineItemStatus() != null) // Line
									      // Item
									      // Status
									      // exists
									      // and
									      // is
									      // not
									      // DELETED
				&& (lineItemType.getLineItemStatus().getStatusCode() != LineItemStatus.cancelled_removed.getTransportType())) {
			    logger.debug("ActivityUpdateLineItemWithNotificationEvent:addProviderCommunicationEvent():lineItemType: "
				    + lineItemType.getLineItemStatus().getStatusCode());

			    createProviderCommunicationEvent(lineItemType);
			    lineItemType.setPartnerExternalId(businessParty.getExternalId());
			    lineItemType.setPartnerName(businessParty.getName());

			    // Setting AgentId that came from Request
			    logger.debug("ActivityUpdateLineItemWithNotificationEvent:addProviderCommunicationEvent:setAgentId: " + user);
			    lineItemType.getLineItemStatus().setAgentId(user);

			    logger.debug("ActivityUpdateLineItemWithNotificationEvent:addProviderCommunicationEvent: " + lineItemType.toString());

			}
			else if ((updateScheduler) && (businessParty != null)
				&& ((doAllowAllProviders) || (businessParty.isRealtimeProvider() == SCHEDULE_NON_RTIM_PROVIDER_INTEGRATION))
				&& (lineItemType.getLineItemStatus() != null) // Line
									      // Item
									      // Status
									      // exists
									      // and
									      // is
									      // not
									      // DELETED
				&& (lineItemType.getLineItemStatus().getStatusCode() != LineItemStatus.cancelled_removed.getTransportType())) {
			    logger.debug("ActivityUpdateLineItemWithNotificationEvent:addProviderCommunicationEvent():Inside updateScheduler");
			    activityScheduleJob.scheduleJob(user, businessParty, newOrderInfoResponse, lineItemType);
			}
			/* ACDC-1056 commented as per generic logic for FallOut Orders
			 * 
			 * else if(isFallBackOrder && businessParty.getExternalId().equalsIgnoreCase(VERIZON_PROVIDER)) {
			    logger.debug("Scheduling VZ fallout order to FA : " + lineItemType.getExternalId());
			    activityScheduleJob.scheduleJob(user, businessParty, newOrderInfoResponse, lineItemType);
			}
			else if(isFallBackOrder && businessParty.getExternalId().equalsIgnoreCase(VERIZON_COFE_PROVIDER)) {
			    logger.debug("Scheduling VZ-CTG fallout order to FA : " + lineItemType.getExternalId());
			    activityScheduleJob.scheduleJob(user, businessParty, newOrderInfoResponse, lineItemType);
			}
			else if(isFallBackOrder && businessParty.getExternalId().equalsIgnoreCase(CENTURY_LINK_PROVIDER)) {
			    logger.debug("Scheduling CL fallout order to FA : " + lineItemType.getExternalId());
			    activityScheduleJob.scheduleJob(user, businessParty, newOrderInfoResponse, lineItemType);
			}
			else if(isFallBackOrder && businessParty.getExternalId().equalsIgnoreCase(MONITRONICS_PROVIDER)) {
			    logger.debug("Scheduling MONITRONICS_PROVIDER fallout order to FA : " + lineItemType.getExternalId());
			    activityScheduleJob.scheduleJob(user, businessParty, newOrderInfoResponse, lineItemType);
			}*/
			else if( isFallBackOrder ) {
			    logger.info("Scheduling fallout order to FA : " + lineItemType.getExternalId());
			    activityScheduleJob.scheduleJob(user, businessParty, newOrderInfoResponse, lineItemType);
			}
			else {
			    logger.debug("Not attaching event notification for submit.  providerExternalId:" + providerExternalId + PROVIDER_DATA_MANAGER_SUBMISSION);
			}

		    }

		}
	    }

	}
	catch (Exception e) {
	    logger.warn("invalid line item number" + e.getMessage());
	}

    }

    final static String PRODUCT_PROMOTION = DetailType.PRODUCT_PROMOTION.toString();

    public void createProviderCommunicationEvent(LineItemType lineItemType) {

	if (lineItemType.getNotificationEvents() == null) {
	    lineItemType.addNewNotificationEvents();
	}

	NotificationEventFactory.createProcessByProviderEvent(lineItemType);

    }

}
