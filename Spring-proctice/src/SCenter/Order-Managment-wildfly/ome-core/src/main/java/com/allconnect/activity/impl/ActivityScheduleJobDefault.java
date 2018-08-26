package com.AL.activity.impl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.activity.ActivityScheduleJob;
import com.AL.enums.LineItemStatus;
import com.AL.V.beans.entity.BusinessParty;
import com.AL.V.beans.job.Job;
import com.AL.Vdao.dao.JobDao;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderType;
import com.AL.Vdao.dao.LogicalProgramDao;

@Component
public class ActivityScheduleJobDefault implements ActivityScheduleJob {

	private static final String _5004 = "5004";

	private static final Logger logger = Logger
			.getLogger(ActivityScheduleJobDefault.class);

	@Autowired
	private JobDao jobDao;

	private static final String DEFAULT_TRANSACTION_STATUS = "S";

	@Autowired
	private LogicalProgramDao logicalProgramDao;


	public boolean scheduleJob(String userId, BusinessParty businessParty,
			OrderType order, LineItemType lineItemType) {

		boolean isProcessed = validJobInput(userId, businessParty, order,
				lineItemType);
		    if (isProcessed) {

			try {
			    String context = businessParty.getExternalId();

			    String extId = String.valueOf(lineItemType.getExternalId());
			    String parentExtId = String.valueOf(order.getExternalId());

			    Job existingJob = jobDao.findLineItemEntry(parentExtId, extId, context);

			    if (existingJob == null) {

					Job job = Job.create(context, userId, extId, parentExtId);
	
					if ((lineItemType != null) && (lineItemType.getLineItemStatus() != null) && (lineItemType.getLineItemStatus().getStatusCode() != null)) {
	
					    job.setTtl(-1);
					    job.setLocked(Boolean.FALSE);
					    job.setExternalId("0");
					    job.setTransactionStatus(DEFAULT_TRANSACTION_STATUS);
					    job.setTransactionId(extId + System.currentTimeMillis());
					    job.setStatusQueued(LineItemStatus.provision_ready.name());
					    job.setDescription1(lineItemType.getLineItemDetail().getDetail().getProductLineItem().getName());
					    job.setDescription2(userId);
					    job.setReason(_5004);//
					    
					    // New field added for the ticket #115861 -- order date to om_job 
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
					}
					jobDao.save(job);
			    }
			    else {
					if ((lineItemType != null) && (lineItemType.getLineItemStatus() != null) && (lineItemType.getLineItemStatus().getStatusCode() != null)) {
	
					    existingJob.setTtl(-1);
					    existingJob.setLocked(Boolean.FALSE);
					    existingJob.setTransactionStatus(DEFAULT_TRANSACTION_STATUS);
					    existingJob.setStatusQueued(LineItemStatus.provision_ready.name());
					    existingJob.setStatus(0);
					    if ((order.getCustomerInformation() != null) && (order.getCustomerInformation().getCustomer() != null)) {
						existingJob.setCustomerId(String.valueOf(order.getCustomerInformation().getCustomer().getExternalId()));
					    }
					    existingJob.setReason(_5004); //
					}
					jobDao.update(existingJob);
			    }
			}
			catch (Exception e) {

			    logger.warn(e.getMessage());
			    isProcessed = Boolean.FALSE;
			}
		    }
		    else {
		    	isProcessed = Boolean.FALSE;
		    }
		return isProcessed;
	}

	public boolean scheduleJob(BusinessParty businessParty,
			OrderType newOrderInfoResponse, LineItemType lineItemType) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean validJobInput(String user, BusinessParty businessParty,
			OrderType order, LineItemType lineItemType) {
		return ((user != null) && (businessParty != null) && (order != null)
				&& (lineItemType != null) && (user != null) && (businessParty
				.getExternalId() != null));
	}

	public JobDao getJobDao() {
		return jobDao;
	}

	public void setJobDao(JobDao jobDao) {
		this.jobDao = jobDao;
	}

}
