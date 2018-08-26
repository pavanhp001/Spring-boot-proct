package com.A.Vdao.dao;

import java.util.List;

import com.A.V.beans.job.Job;

public interface JobDao {
	
	 

	public Job findByExternalId(final String extId);
	
	public List<Job> findAll();

	public void deleteAll();

	public void save(Job job);

	public void update(Job job);

	public void delete(Job job);

	public boolean lock(final String context, final String userId,
			final boolean isAdmin, final String extId);

	public boolean isLocked(final String userId, final boolean isAdmin,
			final String extId);
	
	public Job findLineItemEntry(final String orderId, final String lineItemId);
	
	public List<Job> findCustomerItemEntry(final String customerId, final String orderId);
	
	public Job findLineItemEntry(final String orderId, final String lineItemId, final String context);

	public boolean unlock(final String userId, final boolean isAdmin,
			final String extId);

	public Job find(final String extId);
	
	public List<Job> findByContextStatus(  final String context, final String statusQueued, int channelType);
	
	public List<Job> findNextByPriority(final String context, final String statusQueued, final int ttl, final String agentId, int channelType);
	 
	public List<Job> findByContextMultipleStatus(final List<String> reasons,
			final String context, final String statusQueued, int channelType);
	 
	public Job findByContextAndExtId(final String context, final String extId);

	public Job failed(final String context, String userId, String extId);

	public Job cancel(String context, String userId, String extId);

	public Job complete(String context, String userId, String extId);

	public Job activate(String context, String userId, String extId);

	public Job schedule(String context, String userId, String extId);
	
	public Job create(String context, String userId, String resourceExtId, String parentExtId);
	
	public List<Job> findByContext(final String context, final int limit);
	
	public List<Job> findByStatusAndContext(final String context,
			final long status, int limit) ;

	public List<Job> findByStatus(final long status, int limit);
	
	
	public String beginTransaction(String context,String taskId);
	
	public String commitTransaction(String transactionId);
	
	public String rollbackTransaction(String transactionId);
	
	public void clearLocks();
	
	/**
     * This method is introduced to save the list of jobs in a scenario where the customer
     * first name and/or last name is updated from Encore<br><br>
     * Changes introduced for ticket #114887, #115861
     * 
     * @param jobList
     */
	public void update(List<Job> jobList);
	
	/**
	 * Updated the scheduled order's status and transaction status to be SUCCESS(0)/FAILURE(1)
	 * 
	 * @param taskJoId
	 * @param status
	 * @return boolean
	 */
	public boolean updateTransactionStatus(long taskJoId,boolean status);
	
	/**
	 * Used to retrieve list of jobs/records for batch line item status update process <br>
	 * @param transactionId
	 * @return job list
	 */
	public List<Job> retrieveJobsByTransactionId(String transactionId);
}
