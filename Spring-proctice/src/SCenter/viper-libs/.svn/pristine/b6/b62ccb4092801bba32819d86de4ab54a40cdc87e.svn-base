package com.A.vm.util.converter.marshall;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.A.V.beans.job.Job;
import com.A.xml.v4.JobCollectionType;
import com.A.xml.v4.JobType;

/**
 * @author ebthomas
 * 
 */
@Component
public class MarshallJob {

	@Autowired
	MarshallCustomerInteraction marshallCI;
	/**
	 * @param job
	 *            Source
	 * @return indicator if valid
	 */
	public Boolean validate(final Job job) {

		return Boolean.TRUE;
	}

	/**
	 * @param job
	 *            Source
	 * @return Domain Object representing Price Info
	 */
	public JobType build(final Job job, final JobType jobType) {

		if (validate(job)) {

			jobType.setId(job.getId());
			jobType.setExternalId(job.getExternalId());
			jobType.setContext(job.getContext());
			jobType.setDateEffectiveFrom(job.getDateEffectiveFrom());
			jobType.setDateEffectiveTo(job.getDateEffectiveTo());
			jobType.setFlowState(job.getFlowState());
			jobType.setLocked(job.isLocked());
			jobType.setLoginId(job.getLoginId());
			jobType.setParentExternalId(job.getParentExternalId());
			jobType.setPriority(job.getPriority());
			jobType.setResourceExternalId(job.getResourceExternalId());
			jobType.setStatus((int) job.getStatus());
			jobType.setStatusQueued(job.getStatusQueued());
			jobType.setTransactionId(job.getTransactionId());
			jobType.setTransactionStatus(job.getTransactionStatus());
			jobType.setTtl(job.getTtl());
			jobType.setLockedAt(job.getLockedAt());
			jobType.setDesc1(job.getDescription1());
			jobType.setDesc2(job.getDescription2());
			jobType.setReason(job.getReason());
			jobType.setCustomerId(job.getCustomerId());
			jobType.setOrderDate(job.getOrderDate());
			jobType.setFirstName(job.getFirstName());
			jobType.setLastName(job.getLastName());
			jobType.setChannelType(job.getChannelType());
			
			marshallCI.buildCIResponse(job.getCustomerInteractionList(), jobType);

			return jobType;
		}

		throw new IllegalArgumentException("unable to copy job information");
	}

	/**
	 * @param job
	 *            Source
	 * @return Domain Object representing Price Info
	 */
	public JobType build(final Job job) {
		JobType jobType = JobType.Factory.newInstance();

		return build(job, jobType);
	}

	/**
	 * @param job
	 *            Source
	 * @return Domain Object representing Price Info
	 */
	public void build(final List<Job> jobList,
			final JobCollectionType jobTypeList) {

		for (Job job : jobList) {
			JobType jobType = jobTypeList.addNewJob();

			build(job, jobType);
		}

	}

	/**
	 * @param job
	 *            Source
	 * @return Domain Object representing Price Info
	 */
	public List<JobType> build(final List<Job> jobList) {

		List<JobType> jobTypeList = new ArrayList<JobType>();

		for (Job job : jobList) {

			jobTypeList.add(build(job));
		}

		return jobTypeList;

	}

}
