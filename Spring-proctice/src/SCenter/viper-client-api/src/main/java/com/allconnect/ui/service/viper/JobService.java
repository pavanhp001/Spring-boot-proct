package com.A.ui.service.V;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.A.ui.repository.SessionCache;
import com.A.ui.transport.TransportConfig;
import com.A.util.DateUtil;
import com.A.xml.v4.JobCollectionType;
import com.A.xml.v4.JobType;
import com.A.xml.v4.ObjectFactory;
import com.A.xml.v4.OrderManagementRequestResponse;

public enum JobService {

	INSTANCE;
	
	private static final Logger logger = Logger.getLogger(JobService.class);

	@Deprecated
	public JobType UnLockByOrderContextLineItem(final String agentId,
			final String orderId, final String lineItemId, final boolean clear) {

		JobType job = JobService.INSTANCE.getJobByOrderContextLineItem(orderId,
				lineItemId, agentId);

		if (job != null) {
			job = JobService.INSTANCE.unlock(agentId, job.getExternalId(),
					clear);
		}

		return job;
	}

	@Deprecated
	public JobType LockByOrderContextLineItem(final String agentId,
			final String orderId, final String lineItemId) {

		JobType job = JobService.INSTANCE.getJobByOrderContextLineItem(orderId,
				lineItemId, agentId);

		if (job != null) {
			job = JobService.INSTANCE.lock(agentId, job.getExternalId());
		}

		return job;
	}
	
	@Deprecated
	public JobType getJobByOrderContextLineItem(final String orderId,
			final String lineItemId, final String context) {
		return getJobByOrderContextLineItem("default", orderId, lineItemId, context);
	}

	/**
	 * Get Job using context, order and lineitem id
	 * 
	 * @param agentId
	 * @param orderId
	 * @param lineItemId
	 * @param context
	 * @return
	 */
	public JobType getJobByOrderContextLineItem(final String agentId, final String orderId,
			final String lineItemId, final String context) {
		
		logger.info("getJobByOrderContextLineItem, orderId: "+orderId+", lineItemId: "+lineItemId+", context: "+context);

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse cmrr = oFactory
				.createOrderManagementRequestResponse();
		cmrr.setRequest(oFactory.createOrderManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		cmrr.setTransactionType("taskJob");

		cmrr.getRequest().setJobAction("findByOrderLineItem");
		cmrr.getRequest().setAgentId(agentId);

		JobCollectionType jc = oFactory.createJobCollectionType();
		JobType jt = oFactory.createJobType();
		jc.getJob().add(jt);

		cmrr.getRequest().setJobs(jc);

		jt.setContext(context);
		jt.setResourceExternalId(lineItemId);
		jt.setParentExternalId(orderId);

		OrderManagementRequestResponse cmrrR = TransportConfig.INSTANCE
				.getOrderClient().send(cmrr);

		if ((cmrrR != null) && (cmrrR.getResponse() != null)
				&& (cmrrR.getResponse().getJobs() != null)) {
			List<JobType> jobTypeList = cmrrR.getResponse().getJobs().getJob();

			if ((jobTypeList != null) && (jobTypeList.size() > 0)) {
				return jobTypeList.get(0);
			}
		}

		return null;
	}

	public JobType unlockAll(final String externalId) {

		// UPDATE OM_JOB set TTL = -1, IS_LOCKED = 0, LOCKED_AT = null;

		return null;
	}

	public JobType offer(final String agentId, final String externalId) {

		return updateLock(externalId, Boolean.TRUE, agentId,
				SessionCache.INSTANCE.getOfferTTL(), Boolean.FALSE);
	}

	/**
	 * Method to lock the Job
	 * 
	 * @param agentId
	 * @param externalId
	 * @return
	 */
	public JobType lock(final String agentId, final String externalId) {

		return updateLock(externalId, Boolean.TRUE, agentId,
				SessionCache.INSTANCE.getLockTTL(), Boolean.FALSE);
	}

	/**
	 * Method to unlock the Job
	 * 
	 * @param agentId
	 * @param externalId
	 * @param clear
	 * @return
	 */
	public JobType unlock(final String agentId, final String externalId,
			boolean clear) {

		return updateLock(externalId, Boolean.FALSE, agentId,
				SessionCache.INSTANCE.getUnLockTTL(), clear);
	}

	/**
	 * Method to update Job
	 * 
	 * @param externalId
	 * @param locked
	 * @param loginId
	 * @param ttl
	 * @param clear
	 * @return
	 */
	public JobType updateLock(final String externalId, final boolean locked,
			final String loginId, final long ttl, final boolean clear) {
		
		logger.debug("updateLock: JobId : "+externalId+", lock : "+locked+", clear: "+clear);

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse cmrr = oFactory
				.createOrderManagementRequestResponse();
		cmrr.setRequest(oFactory.createOrderManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		cmrr.setTransactionType("taskJob");

		cmrr.getRequest().setJobAction("update");
		cmrr.getRequest().setAgentId(loginId);

		JobCollectionType jc = oFactory.createJobCollectionType();
		JobType jt = oFactory.createJobType();
		jc.getJob().add(jt);

		jt.setLocked(locked);
		jt.setTtl(ttl);
		jt.setLoginId(loginId);
		jt.setLockedAt(DateUtil.getCurrentXMLDate());

		if (clear) {
			jt.setStatus(1000);
		}

		cmrr.getRequest().setJobs(jc);

		jt.setExternalId(externalId);

		OrderManagementRequestResponse cmrrR = TransportConfig.INSTANCE
				.getOrderClient().send(cmrr);

		if ((cmrrR != null) && (cmrrR.getResponse() != null)
				&& (cmrrR.getResponse().getJobs() != null)) {
			List<JobType> jobTypeList = cmrrR.getResponse().getJobs().getJob();

			if ((jobTypeList != null) && (jobTypeList.size() > 0)) {
				return jobTypeList.get(0);
			}
		}

		return null;

	}

	/**
	 * Method to get Job using its external id
	 * 
	 * @param externalId
	 * @param loginId
	 * @return
	 */
	public JobType getJobByExternalId(final String externalId, final String loginId) {

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse cmrr = oFactory
				.createOrderManagementRequestResponse();
		cmrr.setRequest(oFactory.createOrderManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		cmrr.setTransactionType("taskJob");

		cmrr.getRequest().setJobAction("findByExternalId");
		cmrr.getRequest().setAgentId(loginId);

		JobCollectionType jc = oFactory.createJobCollectionType();
		JobType jt = oFactory.createJobType();
		jc.getJob().add(jt);

		cmrr.getRequest().setJobs(jc);

		jt.setExternalId(externalId);

		OrderManagementRequestResponse cmrrR = TransportConfig.INSTANCE
				.getOrderClient().send(cmrr);

		if ((cmrrR != null) && (cmrrR.getResponse() != null)
				&& (cmrrR.getResponse().getJobs() != null)) {
			List<JobType> jobTypeList = cmrrR.getResponse().getJobs().getJob();

			if ((jobTypeList != null) && (jobTypeList.size() > 0)) {
				return jobTypeList.get(0);
			}
		}

		return null;

	}

	/**
	 * Method to get Jobs using Provider, Status and status reason codes 
	 * 
	 * @param context
	 * @param loginId
	 * @param status
	 * @param reasons
	 * @return
	 */
	public List<JobType> getJobByContextStatus(final String context, final String loginId,
			final String status, final String[] reasons, final int channelType) {
		
		logger.debug("getJobByContextStatus, agent: "+loginId+", status: "+status);

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse cmrr = oFactory
				.createOrderManagementRequestResponse();
		cmrr.setRequest(oFactory.createOrderManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		cmrr.setTransactionType("taskJob");

		cmrr.getRequest().setJobAction("findByContextStatus");
		cmrr.getRequest().setAgentId(loginId);

		JobCollectionType jc = oFactory.createJobCollectionType();
		JobType jt = oFactory.createJobType();
		jc.getJob().add(jt);

		cmrr.getRequest().setJobs(jc);

		if ((reasons != null) && (reasons.length > 0)) {

			for (String reason : reasons) {

				if ((reason != null) && (reason.length() > 0)) {

					Integer asInteger = Integer.parseInt(reason);

					if (asInteger > 0) {
						cmrr.getRequest().getReason().add(reason);
					}
				}
			}
		}

		jt.setExternalId("0");
		jt.setStatusQueued(status);
		jt.setContext(context);
		jt.setChannelType(channelType);

		OrderManagementRequestResponse cmrrR = TransportConfig.INSTANCE
				.getOrderClient().send(cmrr);
		
		List<JobType> jobTypeList = new ArrayList<JobType>();
		if ((cmrrR != null) && (cmrrR.getResponse() != null)
				&& (cmrrR.getResponse().getJobs() != null) && (cmrrR.getResponse().getJobs().getJob() != null)) {
			jobTypeList = cmrrR.getResponse().getJobs().getJob();
		}
		return jobTypeList;
	}

	/**
	 * Method to get job for processing
	 * 
	 * @param context
	 * @param loginId
	 * @param status
	 * @return JobType
	 */
	public JobType getNext(final String context, final String loginId, final String status, final int channelType) {

		logger.debug("getNext, context: "+context+", agent: "+loginId+", status: "+status);
		
		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse cmrr = oFactory
				.createOrderManagementRequestResponse();
		cmrr.setRequest(oFactory.createOrderManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		cmrr.setTransactionType("taskJob");

		cmrr.getRequest().setJobAction("findNextAndOffer");
		cmrr.getRequest().setAgentId(loginId);

		JobCollectionType jc = oFactory.createJobCollectionType();
		JobType jt = oFactory.createJobType();
		jc.getJob().add(jt);

		cmrr.getRequest().setJobs(jc);

		jt.setExternalId("0");
		jt.setStatusQueued(status);
		jt.setContext(context);
		jt.setTtl(1L);
		jt.setLoginId(loginId);
		jt.setChannelType(channelType);

		OrderManagementRequestResponse cmrrR = TransportConfig.INSTANCE
				.getOrderClient().send(cmrr);

		JobType jobType = null;
		List<JobType> jobTypeList = cmrrR.getResponse().getJobs().getJob();

		if ((jobTypeList != null) && (jobTypeList.size() > 0)) {
			jobType = jobTypeList.get(0);
		}

		logger.debug("getNext, jobType: "+jobType);
		
		return jobType;

	}

	public JobType getNextLock(final String context, final String loginId, final String status, final int channelType) {

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse cmrr = oFactory
				.createOrderManagementRequestResponse();
		cmrr.setRequest(oFactory.createOrderManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		cmrr.setTransactionType("taskJob");

		cmrr.getRequest().setJobAction("findNextAndProcess");
		cmrr.getRequest().setAgentId(loginId);

		JobCollectionType jc = oFactory.createJobCollectionType();
		JobType jt = oFactory.createJobType();
		jc.getJob().add(jt);

		cmrr.getRequest().setJobs(jc);

		jt.setExternalId("-1");
		jt.setStatusQueued(status);
		jt.setContext(context);
		jt.setChannelType(channelType);

		OrderManagementRequestResponse cmrrR = TransportConfig.INSTANCE
				.getOrderClient().send(cmrr);

		JobType jobType = null;
		List<JobType> jobTypeList = cmrrR.getResponse().getJobs().getJob();

		if ((jobTypeList != null) && (jobTypeList.size() > 0)) {
			jobType = jobTypeList.get(0);
		}

		return jobType;

	}
	
	/**
	 * To Lock Customer while editing
	 * 
	 * @param agentId
	 * @param customerId
	 * @param orderId
	 * @return
	 */
	public boolean lockCustomer(final String agentId, final String customerId, final String orderId) {

		return updateCustomerLock(customerId, orderId, Boolean.TRUE, agentId,
				SessionCache.INSTANCE.getLockCustTTL());
	}

	/**
	 * To unlock Customer after done with editing
	 * 
	 * @param agentId
	 * @param customerId
	 * @param orderId
	 * @return
	 */
	public boolean unLockCustomer(final String agentId, final String customerId, final String orderId) {
		return updateCustomerLock(customerId, orderId, Boolean.FALSE, agentId,
				SessionCache.INSTANCE.getUnLockTTL());
	}
	
	/**
	 * To update lock on Customer, this will lock all the orders for this customer
	 * 
	 * @param customerId
	 * @param orderId
	 * @param locked
	 * @param loginId
	 * @param ttl
	 * @return
	 */
	public boolean updateCustomerLock(final String customerId, final String orderId, 
			final boolean locked, final String loginId, final long ttl) {

		ObjectFactory oFactory = new ObjectFactory();
		OrderManagementRequestResponse cmrr = oFactory
				.createOrderManagementRequestResponse();
		cmrr.setRequest(oFactory.createOrderManagementRequestResponseRequest());
		cmrr.setCorrelationId(UUID.randomUUID().toString());
		cmrr.setTransactionId((int) (System.nanoTime() % Integer.MAX_VALUE));
		cmrr.setTransactionType("taskJob");

		cmrr.getRequest().setJobAction("update");
		cmrr.getRequest().setAgentId(loginId);

		JobCollectionType jc = oFactory.createJobCollectionType();
		JobType jt = oFactory.createJobType();
		jc.getJob().add(jt);

		jt.setCustomerId(customerId);
		if(StringUtils.isNotBlank(orderId)){
			jt.setParentExternalId(orderId);
		}
		jt.setLocked(locked);
		jt.setTtl(ttl);
		jt.setLoginId(loginId);
		jt.setLockedAt(DateUtil.getCurrentXMLDate());

		cmrr.getRequest().setJobs(jc);

		OrderManagementRequestResponse cmrrR = TransportConfig.INSTANCE
				.getOrderClient().send(cmrr);

		if ((cmrrR != null) && (cmrrR.getResponse() != null)
				&& (cmrrR.getResponse().getJobs() != null)) {
			List<JobType> jobTypeList = cmrrR.getResponse().getJobs().getJob();

			if ((jobTypeList != null) && (jobTypeList.size() > 0)) {
				if(locked){
					if(jobTypeList.size() == 1 && StringUtils.equals(jobTypeList.get(0).getCustomerId(), "-1")){
						return false; //failed to lock order
					} else if(jobTypeList.size() == 1 && StringUtils.equals(jobTypeList.get(0).getCustomerId(), "0")){
						return true; //customer doesn't exist in om_job table
					} else {
						return true; //locked order successfully
					}
				} else {
					return true;
				}
			}
		}
		return true;
	}

}
