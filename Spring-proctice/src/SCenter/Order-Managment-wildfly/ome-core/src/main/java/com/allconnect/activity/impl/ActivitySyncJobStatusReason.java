package com.AL.activity.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.AL.enums.LineItemStatus;
import com.AL.util.CollectionUtil;
import com.AL.V.beans.job.Job;
import com.AL.Vdao.dao.JobDao;
import com.AL.vm.vo.OrderChangeValueObject;

public enum ActivitySyncJobStatusReason {

	INSTANCE;

	private static final Logger logger = Logger
			.getLogger(ActivitySyncJobStatusReason.class);

	public boolean execute(final JobDao jobDao,
			final OrderChangeValueObject orderChangeValueObject) {

	    logger.info("Processing job record");

		String parentExtId = orderChangeValueObject.getOrderId();
		List<Integer> reasonList = orderChangeValueObject.getReasonList();
		List<String> extIdList = orderChangeValueObject.getLineItemExternalIds();
		String newStatus = orderChangeValueObject.getStatus().trim();

		if ((parentExtId == null) || (extIdList == null) || (extIdList.isEmpty())) {
			return Boolean.FALSE;
		}

		String reasonsAsCommaList ="";
		if(reasonList != null) {
		    reasonsAsCommaList = CollectionUtil.INSTANCE
				.getAsCommaDelimitedString(reasonList);
		}

		try {
		    for(String extId : extIdList) {
			Job existingJob = jobDao.findLineItemEntry(parentExtId, extId);
			Boolean shoudRemoveJob = checkJobForRemoval(newStatus,existingJob);
			logger.info("Should job removed based on status: " + shoudRemoveJob);
			if(existingJob != null && shoudRemoveJob ) {
			    logger.info("Changing job status to completed(1000) so that is can be removed : JobId [" + existingJob.getId()+"]");
			    existingJob.setStatus(1000);
			}else if(existingJob != null){
			    existingJob.setReason(reasonsAsCommaList);

			}
			jobDao.update(existingJob);
		    }

		} catch (Exception e) {

			logger.warn(e.getMessage());
			return Boolean.FALSE;
		}

		return Boolean.TRUE;
	}

	/**
	 * If we are changing lineitem status to one of this then scheduled job should be removed from om_job
	 * table as per ticket no. 100295
	 * @param newStatus
	 * @param existingJob
	 * @return
	 */
	private Boolean checkJobForRemoval(String newStatus, Job existingJob) {
	    boolean shoudRemoveJob = false;
	    if(null == newStatus || newStatus.length() == 0 || existingJob == null)
		return shoudRemoveJob;

	    logger.info("Checking for job removal by status : " + newStatus);

	    if(newStatus.equalsIgnoreCase(LineItemStatus.processing_schedule_confirmed.name()) ||
		    newStatus.equalsIgnoreCase(LineItemStatus.processing_cancelled.name()) ||
		    newStatus.equalsIgnoreCase(LineItemStatus.cancelled_removed.name()) ||
		    newStatus.equalsIgnoreCase(LineItemStatus.processing_connected.name()) ||
		    newStatus.equalsIgnoreCase(LineItemStatus.processing_disconnected.name()) ||
		    newStatus.equalsIgnoreCase(LineItemStatus.test_order.name())) {
		shoudRemoveJob = true;
	    }
	    return shoudRemoveJob;
	}
}
