package com.AL.service;

import java.util.List;

import com.AL.task.context.impl.OrchestrationContext;
import com.AL.V.beans.job.Job;

public interface OrderManagementRedefinedService {

	/**
	 * Redefined update line item status API used for external process for huge chunk of order processing. It will
	 * be used for the normal real-time call once its stabelized 
	 * @param params
	 * @return status
	 */
	 public Boolean updateLineitemStatus(final OrchestrationContext params);
	
	/**
	 * Used to retrieve list of job entities from om job table based on transaction id
	 * @param pTransactionId
	 * @return job list
	 */
	public List<Job> retrieveJobsByTransactionId(String pTransactionId);
}
