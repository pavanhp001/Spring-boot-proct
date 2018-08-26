package com.A.thread;

import org.apache.log4j.Logger;

import com.A.ui.constants.Constants;
import com.A.ui.service.V.JobService;
import com.A.xml.v4.JobType;

public class JobUpdateThread implements Runnable {

	private static final Logger logger = Logger.getLogger(JobUpdateThread.class);
	
	final String agentId;
	final String job_ext_id;
	final String customerId;
	final String orderId;
	final Boolean lock;
	final String typeOfLock;
	final Boolean clear;

	public JobUpdateThread(final String agentId, final String job_ext_id, final String customerId,
			final String orderId, final Boolean lock, final String typeOfLock, final Boolean clear) {
		this.agentId = agentId;
		this.job_ext_id = job_ext_id;
		this.customerId = customerId;
		this.orderId = orderId;
		this.lock = lock;
		this.typeOfLock = typeOfLock;
		this.clear = clear;
	}
	
	public void run() {
		if(typeOfLock != null && typeOfLock.equals(Constants.CUST_LOCK_TYPE)){
			if(lock){
				boolean jobStatus = JobService.INSTANCE.lockCustomer(agentId, customerId, orderId);
				logger.info("LockCustomer, jobStatus --> "+jobStatus);
			} else {
				boolean jobStatus = JobService.INSTANCE.unLockCustomer(agentId, customerId, orderId);
				logger.info("UnLockCustomer, jobStatus --> "+jobStatus);
			}
		} else {
			if(lock){
				JobType job = JobService.INSTANCE.lock(agentId, job_ext_id);
				logger.info("LockJob, Id --> "+job.getExternalId());
			} else {
				JobType job = JobService.INSTANCE.unlock(agentId, job_ext_id, clear);
				logger.info("UnLockJob, Id --> "+job.getExternalId());
			}
		}
	}

}
