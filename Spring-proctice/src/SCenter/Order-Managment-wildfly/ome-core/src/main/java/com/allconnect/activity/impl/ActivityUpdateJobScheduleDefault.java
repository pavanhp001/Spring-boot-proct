package com.AL.activity.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.AL.activity.ActivityUpdateJobSchedule;
import com.AL.V.beans.entity.BusinessParty;
import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.entity.StatusRecordBean;
import com.AL.V.beans.job.Job;
import com.AL.Vdao.dao.JobDao;
import com.AL.xml.v4.OrderType;

@Component
public class ActivityUpdateJobScheduleDefault implements
		ActivityUpdateJobSchedule {

	private static final Logger logger = Logger
			.getLogger(ActivityScheduleJobDefault.class);

	@Autowired
	private JobDao jobDao;

	public void updateStatus(String userId, StatusRecordBean status,
			String businessPartyId, SalesOrder order,
			LineItem lineItem)  {

		boolean isProcessed = validJobInput(userId, businessPartyId, order,
				lineItem);

		if (isProcessed) {

			try {
				String context = businessPartyId  ;
				String orderId = String.valueOf(order.getExternalId());
				String lineItemId = String
						.valueOf(lineItem.getExternalId());

				Job job = jobDao
						.findLineItemEntry(orderId, lineItemId, context);

				if ((job != null) &&
						(lineItem != null)
						&& (lineItem.getCurrentStatus()   != null)
						&& (lineItem.getCurrentStatus().getStatus() != null)) {

					 
					if ((lineItem != null)
							&& (lineItem.getCurrentStatus() != null)
							&& (lineItem.getCurrentStatus()
									.getStatus() != null)) {
						//long stateCount = job.getFlowState()+1;
						String newStatus = lineItem
						.getCurrentStatus().getStatus().toString();
 
						job.setStatusQueued(newStatus);
						
						Long customerExtId = order.getConsumerExternalId();
						if (customerExtId != null) {
							job.setCustomerId(String.valueOf(customerExtId));
						}
						jobDao.update(job);
					}
				}
			} catch (Exception e) {

				logger.warn(e.getMessage());
				isProcessed = Boolean.FALSE;
			}
		} else {
			isProcessed = Boolean.FALSE;
		}

	}

	public boolean scheduleJob(BusinessParty businessParty,
			OrderType newOrderInfoResponse, LineItem lineItemType) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean validJobInput(String user, String businessParty,
			SalesOrder order, LineItem  lineItem ) {
		return ((user != null) && (businessParty != null) && (order != null)
				&& (lineItem != null) && (user != null)  );
	}

	public JobDao getJobDao() {
		return jobDao;
	}

	public void setJobDao(JobDao jobDao) {
		this.jobDao = jobDao;
	}

}
