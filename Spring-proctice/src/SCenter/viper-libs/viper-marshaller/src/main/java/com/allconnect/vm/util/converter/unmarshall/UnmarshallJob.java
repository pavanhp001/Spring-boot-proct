package com.A.vm.util.converter.unmarshall;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.A.V.beans.job.Job;
import com.A.xml.v4.JobType;

/**
 * @author ebthomas
 * 
 */
@Component
public class UnmarshallJob {

	/**
	 * @param job
	 *            Source
	 * @return indicator if valid
	 */
	public Boolean validate(final JobType job) {

		return Boolean.TRUE;
	}

	/**
	 * @param job
	 *            Source
	 * @return Domain Object representing Price Info
	 */
	public Job build(final JobType jobType) {
		if (validate(jobType)) {
			Job job = new Job();

			job.setId(jobType.getId());
			job.setExternalId(jobType.getExternalId());
			job.setContext(jobType.getContext());
			job.setDateEffectiveFrom(jobType.getDateEffectiveFrom());
			job.setDateEffectiveTo(jobType.getDateEffectiveTo());
			job.setFlowState(jobType.getFlowState());
			job.setLocked(jobType.getLocked());
			job.setLoginId(jobType.getLoginId());
			job.setParentExternalId(jobType.getParentExternalId());
			job.setPriority(jobType.getPriority());
			job.setResourceExternalId(jobType.getResourceExternalId());
			job.setStatus(jobType.getStatus());
			job.setStatusQueued(jobType.getStatusQueued());
			job.setTransactionId(jobType.getTransactionId());
			job.setTransactionStatus(jobType.getTransactionStatus());
			job.setTtl(jobType.getTtl());
			job.setLockedAt(jobType.getLockedAt());
			
			job.setDescription1(jobType.getDesc1());
			job.setDescription2(jobType.getDesc2());
			
			job.setReason(jobType.getReason());
			
			job.setCustomerId(jobType.getCustomerId());
			job.setOrderDate(jobType.getOrderDate());
			job.setFirstName(jobType.getFirstName());
			job.setLastName(jobType.getLastName());
			job.setChannelType(jobType.getChannelType());

			return job;
		}

		throw new IllegalArgumentException("unable to copy job information");
	}

	/**
	 * @param job
	 *            Source
	 * @return Domain Object representing Price Info
	 */
	public List<Job> build(final List<JobType> jobTypeList) {

		List<Job> jobList = new ArrayList<Job>();

		for (JobType jobType : jobTypeList) {

			jobList.add(build(jobType));
		}

		return jobList;

	}

}
