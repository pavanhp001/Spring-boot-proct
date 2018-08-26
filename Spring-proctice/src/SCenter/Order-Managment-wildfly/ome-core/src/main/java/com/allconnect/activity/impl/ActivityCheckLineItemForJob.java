/**
 * 
 */
package com.AL.activity.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.enums.LineItemStatus;
import com.AL.ie.activity.impl.ActivityGetProvider;
import com.AL.util.CollectionUtil;
import com.AL.V.beans.entity.BusinessParty;
import com.AL.V.beans.job.Job;
import com.AL.V.beans.job.SchedulePhase;
import com.AL.Vdao.dao.BusinessPartyDao;
import com.AL.Vdao.dao.JobDao;
import com.AL.Vdao.dao.LogicalProgramDao;
import com.AL.vm.vo.OrderChangeValueObject;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderType;

/**
 * @author karteek
 *
 */
@Component
public class ActivityCheckLineItemForJob {

    private static final String VERIZON_PROVIDER = "4353598";
    private static final String CENTURY_LINK_PROVIDER = "32416075";
    private static final String MONITRONICS_PROVIDER = "32946482";

    @Autowired
    private BusinessPartyDao businessPartyDao;

	@Autowired
	private LogicalProgramDao logicalProgramDao;
    
    @Autowired
    private JobDao jobDao;

    private static final String PROVIDER_DATA_MANAGER_SUBMISSION = " is eligible for submission; however, the Data Manager will handle submission via the broadcast";
    private static final String DEFAULT_PROVIDER_EXTERNAL_ID = "100";
    final int USE_RTIM_AS_REAL_TIME_PROCESSOR = 1;
    final int SCHEDULE_NON_RTIM_PROVIDER_INTEGRATION = 2;

    private static final Logger logger = Logger.getLogger(ActivityCheckLineItemForJob.class);
    
    public void checkValidJob(String user, OrderChangeValueObject orderChangeValueObj, boolean doAllowAllProviders, OrderType order) {
	
		try {
		    if (order != null && orderChangeValueObj.getOrderId().equals(String.valueOf(order.getExternalId()))) {
		    	logger.info("ActivityCheckLineItemForJob:checkValidJob():Order ID: " + order.getExternalId());
	
			for (LineItemType lineItem : order.getLineItems().getLineItemList()) {
	
			    LineItemType lineItemType = null;
			    for(String lineItemNumber : orderChangeValueObj.getLineItemExternalIds()){
			    	
			    if (lineItemNumber.equals(String.valueOf(lineItem.getExternalId()))) {
	
			    	lineItemType = lineItem;
				}
	
			    }
	
			    if (lineItemType != null) {
	
				logger.info("ActivityCheckLineItemForJob:checkValidJob():lineItemType: " + lineItemType.getExternalId());
	
				String providerExternalId = ActivityGetProvider.INSTANCE.getProvider(lineItemType);
	
				BusinessParty businessParty = businessPartyDao.findBusinessPartyById(providerExternalId);
	
				Boolean isFallBackOrder = Boolean.FALSE;
				if (providerExternalId != null ) {
				    logger.info("Validating  fall out order [Provider : " + providerExternalId +"]");
				    isFallBackOrder = ActivityFalloutOrderValidation.INSTANCE.isFalloutOrder(lineItemType);
				    logger.info("Is fall out order : " + isFallBackOrder);
				}
				
				Job job = jobDao
						.findLineItemEntry(orderChangeValueObj.getOrderId(), String.valueOf(lineItemType.getExternalId()), providerExternalId);
				
				String reasonsAsCommaList ="";
				List<Integer> reasonList = orderChangeValueObj.getReasonList();
				String newStatus = orderChangeValueObj.getStatus().trim();
				
				if(job == null) {
					job = new Job();

					if(reasonList != null) {
					    reasonsAsCommaList = CollectionUtil.INSTANCE
							.getAsCommaDelimitedString(reasonList);
					}
					
					Calendar calDateEffectiveTo = Calendar.getInstance();
					calDateEffectiveTo.add(Calendar.MONTH, 12);
					
					job.setResourceExternalId(String.valueOf(lineItemType.getExternalId()));
					job.setParentExternalId(String.valueOf(order.getExternalId()));
					job.setDateEffectiveFrom(Calendar.getInstance());
					job.setDateEffectiveTo(calDateEffectiveTo);
					job.setContext(String.valueOf(providerExternalId));
					job.setLoginId(orderChangeValueObj.getAgentId());
					job.setPriority(10);
					job.setStatus(SchedulePhase.created.getStatus());
					job.setTtl(-1);
					job.setLocked(Boolean.FALSE);
					job.setExternalId("0");
					job.setTransactionStatus("S");
					job.setTransactionId(System.currentTimeMillis()+String.valueOf(lineItemType.getExternalId()));
					job.setStatusQueued(newStatus);
				    job.setDescription1(lineItemType.getLineItemDetail().getDetail().getProductLineItem().getName());
				    job.setDescription2(orderChangeValueObj.getAgentId());
					job.setReason(reasonsAsCommaList);
				    job.setOrderDate(order.getOrderDate());

				    if ((order.getCustomerInformation() != null) && (order.getCustomerInformation().getCustomer() != null)) {
				    	job.setCustomerId(String.valueOf(order.getCustomerInformation().getCustomer().getExternalId()));
					
						//customer first name and last name to om_job table
						job.setFirstName(order.getCustomerInformation().getCustomer().getFirstName());
						job.setLastName(order.getCustomerInformation().getCustomer().getLastName());
				    }
				
				    // AM-2025 New field to filter online and sales center orders in Encore
				    if(logicalProgramDao.isOnline(order.getProgramExternalId())){
				    	job.setChannelType(1); //Online
				    }
				    else{
				    	job.setChannelType(2); // Sales Center
				    }
	
				logger.info("ActivityCheckLineItemForJob:checkValidJob():lineItemType: " + businessParty.isRealtimeProvider()
					+ " :doAllowAllProviders: " + doAllowAllProviders +" :providerExternalId: " + providerExternalId + " :isVZFalloutOrder : " + isFallBackOrder);
	
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
				    logger.info("ActivityCheckLineItemForJob:checkValidJob():lineItemType: "
					    + lineItemType.getLineItemStatus().getStatusCode());
	
				   // createProviderCommunicationEvent(lineItemType, false);
				    lineItemType.setPartnerExternalId(businessParty.getExternalId());
				    lineItemType.setPartnerName(businessParty.getName());
	
				    // Setting AgentId that came from Request
				    logger.info("ActivityCheckLineItemForJob:checkValidJob():setAgentId: " + user);
				    lineItemType.getLineItemStatus().setAgentId(user);
	
				    logger.info("ActivityCheckLineItemForJob:checkValidJob(): " + lineItemType.toString());
	
				}
				else if ((businessParty != null)
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
				    logger.info("ActivityCheckLineItemForJob:checkValidJob():Inside updateScheduler, JOb ID:"+job.getId());
				    jobDao.update(job);
				}
				else if(isFallBackOrder && (businessParty.getExternalId().equalsIgnoreCase(VERIZON_PROVIDER)) 
						|| (businessParty.getExternalId().equalsIgnoreCase(CENTURY_LINK_PROVIDER))
						|| (businessParty.getExternalId().equalsIgnoreCase(MONITRONICS_PROVIDER))) {
				    logger.info("Scheduling "+businessParty.getExternalId()+" fallout order to FA : " + lineItemType.getExternalId()+", JOb ID:"+job.getId());
				    jobDao.update(job);
				}
				else {
				    logger.info("Not attaching event notification for submit.  providerExternalId:" + providerExternalId + PROVIDER_DATA_MANAGER_SUBMISSION);
				}
			    }
	
			    }
	
			}
		    }
	
		}
		catch (Exception e) {
		    logger.warn("invalid line item number" + e.getMessage());
		}
	}

}
