package com.A.Vdao.transactional.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.A.comm.manager.jms.util.StringUtil;
import com.A.V.beans.entity.CustomerInteraction;
import com.A.V.beans.job.Job;
import com.A.V.beans.job.SchedulePhase;
import com.A.Vdao.dao.CustomerInteractionDao;
import com.A.Vdao.dao.JobDao;
import com.A.Vdao.transactional.dao.library.SqlLibrary;

@Component
public class JobDaoImpl extends BaseTransactionalJpaDao implements JobDao {

    private static final Logger logger = Logger.getLogger(JobDaoImpl.class);

    @Autowired
    CustomerInteractionDao customerInteractionDao;

    private final String UNKNOWN_PROVIDERS_JOB = "delete from om_job where context='6401'";

    String sql = "DELETE FROM OM_JOB WHERE id =?";
    /*public Long getNextExternalId() {
	Query q = getEntityManager().createNativeQuery(NEXT_VAL);
	Object obj = q.getResultList().get(0);

	return DataTypeConverter.INSTANCE.objToLong(obj);
    }*/

    private static final String NEXT_VAL = "SELECT nextval('OM_JOB_SEQ')";
    private  String CLEAR_LOCKS = SqlLibrary.INSTANCE.getClearLocks();
    private final String DELETE_COMPLETED = SqlLibrary.INSTANCE.deleteCompletedJob();

    private static final Object LOCK = new Object();
    private static long LAST_EXECUTION = System.currentTimeMillis();

    public Long getNextExternalId() {
    	  Session session = (Session)getEntityManager().getDelegate();
    	  Connection conn = ((SessionImpl)session).connection();
    	  PreparedStatement ps = null;
    	  ResultSet rs = null;
    	  long externalId = 0;
    	  try {
    	   ps = conn.prepareStatement(NEXT_VAL);
    	   rs = ps.executeQuery();
    	   rs.next();
    	   externalId = rs.getLong("NEXTVAL");
    	  } catch (SQLException e) {
    	   // TODO Auto-generated catch block
    	   e.printStackTrace();
    	  } finally {
    	   if(rs != null) {
    	          try {
    	           rs.close();
    	          } catch(SQLException e) {
    	           e.printStackTrace();
    	          }
    	      }
    	   
    	   if(ps != null) {
    	          try {
    	           ps.close();
    	          } catch(SQLException e) {
    	           e.printStackTrace();
    	          }
    	      }
    	  }
    	  return externalId;
    }
    
    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    @Scheduled(fixedDelay = 30000)
    public void clearLocks() {

	synchronized (LOCK) {

	    if ((System.currentTimeMillis() - LAST_EXECUTION) > 30000) {

		Query queryDelete = getEntityManager().createNativeQuery(DELETE_COMPLETED);
		int removedJobs = queryDelete.executeUpdate();
		Query unknownProviderJobCleanUp = getEntityManager().createNativeQuery(UNKNOWN_PROVIDERS_JOB);
		int removedUnknownJob = unknownProviderJobCleanUp.executeUpdate();
		
		CLEAR_LOCKS = SqlLibrary.INSTANCE.getClearLocks();
		logger.debug("Unlock timed-out orders: "+CLEAR_LOCKS);
		Query query = getEntityManager().createNativeQuery(CLEAR_LOCKS);
		int success = query.executeUpdate();
		logger.debug("Unkown provider jobs ="+ removedUnknownJob +" Removed jobs = " + removedJobs + " Cleared locks = " + success);
		LAST_EXECUTION = System.currentTimeMillis();
	    }
	}
    }

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public void save(Job job) {
	logger.info("Saving job");

	long start = System.currentTimeMillis();

	if (job == null) return;

	if (job.getId() == 0) {
	    job.setExternalId("" + getNextExternalId().longValue());

	}
	getEntityManager().persist(job);

	logger.info("Saving job [JobId="+job.getId()+"] took : " + (System.currentTimeMillis() - start) +"ms");
    }

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public void update(Job job) {

	logger.info("Updating job");

	long start = System.currentTimeMillis();
	if (job == null) {
	    return;
	}

	try {
	    if (job.getId() == 0) {
		save(job);

	    }
	    else {

		getEntityManager().merge(job);
	    }
	    getEntityManager().flush();
	    logger.info("Updating job[JobId="+job.getId()+"] took : " + (System.currentTimeMillis() - start) +"ms");
	}
	catch (Exception e) {
	    logger.error("Exception thrown while updating job", e);
	}
    }

    /**
     * This method is introduced to save the list of jobs in a scenario where the customer
     * first name and/or last name is updated from Encore<br><br>
     * Changes introduced for ticket #114887, #115861
     * 
     * @param jobList
     */
    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public void update(List<Job> jobList) {

		logger.info("Updating job list");
	
		long start = System.currentTimeMillis();
		try {
			for(Job job : jobList){
				getEntityManager().merge(job);
			}
			getEntityManager().flush();
			logger.info("Updating job list took : " + (System.currentTimeMillis() - start) +"ms");
		}
		catch (Exception e) {
		    logger.error("Exception thrown while updating job list", e);
		}
    }
    
    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public void deleteAll() {
	logger.info("Deleting all jobs");

	long start = System.currentTimeMillis();
	List<Job> list = findAll();

	for (Job job : list) {
	    getEntityManager().remove(job);
	}
	logger.info("Deleting all jobs took : " + (System.currentTimeMillis() - start) +"ms");
    }

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public void delete(Job job) {
	logger.info("Deleting single job");

	long start = System.currentTimeMillis();
	Query q = getEntityManager().createNativeQuery(sql);
	q.setParameter(1, job.getId());
	int i = q.executeUpdate();
	logger.debug("Deleted record : " + i);
	//getEntityManager().remove(job);
	//getEntityManager().flush();
	logger.info("Deleting single job [JobId="+job.getId()+"] took : " + (System.currentTimeMillis() - start) +"ms");
    }

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public boolean lock(final String context, final String userId, final boolean isAdmin, final String extId) {
	logger.info("Locking job");

	long start = System.currentTimeMillis();

	Job jobDbVersion = find(extId);

	if (jobDbVersion == null) {
	    jobDbVersion = Job.create(context, userId, extId);
	}

	if (jobDbVersion.isLocked()) {
	    return Boolean.FALSE;
	}

	Job.lock(jobDbVersion, userId, extId);

	if (jobDbVersion.getId() == 0) {
	    getEntityManager().persist(jobDbVersion);
	}
	else {
	    getEntityManager().merge(jobDbVersion);
	}

	logger.info("Locking job took : " + (System.currentTimeMillis() - start) +"ms");
	return Boolean.TRUE;
    }

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public boolean isLocked(final String userId, final boolean isAdmin, final String extId) {
	logger.info("Checking job is locked");

	long start = System.currentTimeMillis();
	Job jobDbVersion = find(extId);
	logger.info("IsLocked method took : " + (System.currentTimeMillis() - start) +"ms");
	return ((jobDbVersion != null) && (jobDbVersion.isLocked()));
    }

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public boolean unlock(final String userId, final boolean isAdmin, final String extId) {
	logger.info("Unlocking job");

	long start = System.currentTimeMillis();
	Job jobDbVersion = find(extId);

	if ((jobDbVersion != null) && (!isAdmin) && (jobDbVersion.getLoginId() != userId)) {
	    return Boolean.FALSE;
	}

	if ((jobDbVersion == null) || (!jobDbVersion.isLocked())) {
	    return Boolean.FALSE;
	}

	Job.unlock(jobDbVersion, userId, extId);

	if (jobDbVersion.getId() == 0) {
	    getEntityManager().persist(jobDbVersion);
	}
	else {
	    getEntityManager().merge(jobDbVersion);
	}

	logger.info("Unlocking job [JobId="+jobDbVersion.getId()+"] took : " + (System.currentTimeMillis() - start) +"ms");
	return Boolean.TRUE;
    }

    @SuppressWarnings("unchecked")
    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public List<Job> findAll() {
	logger.info("finding all jobs");
	long start = System.currentTimeMillis();

	List<Job> jobDbVersionList = null;

	try {

	    jobDbVersionList = (List) getEntityManager().createQuery("select j FROM Job as j  ").getResultList();
	    logger.info("Finding all jobs took : " + (System.currentTimeMillis() - start) + "ms");
	}
	catch (Exception e) {
	    // if not found
	    e.printStackTrace();
	}

	return jobDbVersionList;

    }

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public List<Job> findByContextMultipleStatus(final List<String> reasons, final String context, final String statusQueued, int channelType) {
	logger.info("Executing findByContaxtMultipleStatus");
	long start = System.currentTimeMillis();
	List<Job> jobList = new ArrayList<Job>();
	if ((context == null) || (context.length() == 0) || (statusQueued == null) || (statusQueued.length() == 0)) {
	    return null;
	}

	StringBuilder sb = new StringBuilder();

	String sbQueue = toStatusString(statusQueued);
	String reasonAsString = null;

	if ((reasons != null) && (reasons.size() > 0)) {
	    reasonAsString = toReasonString(reasons);
	}
	try {

	    sb.append("select j FROM Job as j ");
	    sb.append(" WHERE j.statusQueued IN (" + sbQueue + ") ");

	    if ((reasonAsString != null) && (reasonAsString.length() > 0)) {
		sb.append(reasonAsString);
	    }
	    if(channelType > 0){
	    	sb.append(" AND (j.channelType="+channelType+") ");
	    }
	    sb.append("  AND  j.context = :context  ");
	    sb.append(" ORDER BY j.dateEffectiveFrom DESC");

	    Query query = getEntityManager().createQuery(sb.toString());
	    query.setParameter("context", context);

	    jobList = (List<Job>) query.getResultList();

	    //commented the below to code not to include interactions, this
	    // is to improve the performance of service calls
	    //addCustomerInteraction(jobList);
	}
	catch (Exception e) {
	    // if not found
	    e.printStackTrace();
	}
	logger.info("findByContextMultipleStatus took : " + (System.currentTimeMillis() - start) + "ms");
	return jobList;

    }

    public static void main(String[] arg) {
	List<String> list = new ArrayList<String>();
	list.add("1");
	list.add("2");
	list.add("3");

    }

    public static String toReasonString(final List<String> reasonList) {

	StringBuilder sbQueue = new StringBuilder(" AND ( ");
	String clause = " (";
	for (String reason : reasonList) {

	    sbQueue.append(clause);
	    sbQueue.append(" reason like ");
	    sbQueue.append("'%[").append(reason).append("]%')");

	    clause = " OR (";

	}

	sbQueue.append(" ) ");

	return sbQueue.toString();

    }

    private String toStatusString(final String statusQueued) {

	String[] queues = StringUtil.tokenize(statusQueued, ",");

	StringBuilder sbQueue = new StringBuilder();
	for (int i = 0; i < queues.length; i++) {
	    sbQueue.append("'");
	    sbQueue.append(queues[i]);
	    sbQueue.append("'");

	    if (i != queues.length - 1) {
		sbQueue.append(",");
	    }
	}

	return sbQueue.toString();

    }

    public void addCustomerInteraction(List<Job> jobList) {
	logger.info("executing addCustomerInteraction()");
	long start = System.currentTimeMillis();

	for (Job job : jobList) {

	    if ((job.getResourceExternalId() != null)) {
		Long lineItemExtId = Long.valueOf(job.getResourceExternalId());
		Long orderExtId = Long.valueOf(job.getParentExternalId());
		List<CustomerInteraction> ciList = customerInteractionDao.findConsumerInteractionByLineItemId(lineItemExtId, 0, 100);

		if ((ciList == null) || (ciList.size() == 0)) {
		    ciList = customerInteractionDao.findConsumerInteractionByOrderId(orderExtId, 0, 100);
		}

		Set<CustomerInteraction> listConsumerInteraction = new HashSet<CustomerInteraction>(ciList);
		job.setCustomerInteractionList(listConsumerInteraction);
	    }

	}
	logger.info("addCustomerInteraction took : " + (System.currentTimeMillis() - start) + "ms");
    }

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public List<Job> findNextByPriority(final String context, final String statusQueued, int ttl, final String agentId, int channelType) {

	logger.info("Executing findNextByPriority");
	long start = System.currentTimeMillis();

	List<Job> jobList = new ArrayList<Job>();
	if ((context == null) || (context.length() == 0) || (statusQueued == null) || (statusQueued.length() == 0)) {
	    return null;
	}

	StringBuilder sb = new StringBuilder();

	String[] queues = StringUtil.tokenize(statusQueued, ",");

	StringBuilder sbQueue = new StringBuilder();
	for (int i = 0; i < queues.length; i++) {
	    sbQueue.append("'");
	    sbQueue.append(queues[i]);
	    sbQueue.append("'");

	    if (i != queues.length - 1) {
		sbQueue.append(",");
	    }
	}

	try {

	    sb.append("select j FROM Job as j ");
	    sb.append(" WHERE ");
	    sb.append(" 1=1 ");
	    sb.append("  AND   (j.statusQueued IN (" + sbQueue.toString() + ")) ");
	    sb.append("  AND  (j.context = :context)  ");
	    sb.append("  AND  (j.locked = FALSE)   ");
	    sb.append("  AND  (j.ttl <= 0)  ");
	    sb.append("  AND  (j.lockedAt is NULL)  ");
	    if(channelType > 0){
	    	sb.append(" AND (j.channelType="+channelType+") ");
	    }
	    sb.append(" ORDER BY j.orderDate ASC"); // Modified order by clause for ticket #117855

	    Query query = getEntityManager().createQuery(sb.toString());
	    query.setParameter("context", context);
	    query.setMaxResults(1);

	    // Retrieve Single Job
	    jobList = (List<Job>) query.getResultList();

	    // Update and Lock Offer
	    if (jobList.size() > 0) {
		Calendar cal = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(cal.getTime());
		cal2.add(Calendar.MINUTE, (int) ttl);

		for (Job job : jobList) {

		    job.setLocked(Boolean.TRUE);
		    job.setLockedAt(cal);
		    job.setLoginId(agentId);
		    job.getLockedAt().setTime(cal.getTime());

		    job.setDateEffectiveFrom(cal);

		    job.setDateEffectiveTo(cal2);

		    job.setTtl(ttl * 60);
		    
		    logger.debug("Locking time- "+cal.getTime()+" Effective Date/time from: "+job.getDateEffectiveFrom()+" Effective Date/time to:"+job.getDateEffectiveTo()+" TTL:"+job.getTtl());
		    getEntityManager().merge(job);
		    getEntityManager().flush();
		}
	    }
	}
	catch (Exception e) {
	    // if not found
	    e.printStackTrace();
	}

	logger.info("findNextByPriority took : " + (System.currentTimeMillis() - start) + "ms");
	return jobList;

    }

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public List<Job> findByContextStatus(final String context, final String statusQueued, int channelType) {
	logger.info("Executing findByContextStatus");
	long start = System.currentTimeMillis();

	List<Job> jobList = new ArrayList<Job>();
	if ((context == null) || (context.length() == 0) || (statusQueued == null) || (statusQueued.length() == 0)) {
	    return null;
	}

	StringBuilder sb = new StringBuilder();

	try {

	    sb.append("select j FROM Job as j ");
	    sb.append(" WHERE j.statusQueued =  :statusQueued");
	    if(channelType > 0){
	    	sb.append(" AND (j.channelType="+channelType+") ");
	    }
	    sb.append("  AND  j.context = :context  ");
	    sb.append(" ORDER BY j.dateEffectiveFrom DESC");

	    Query query = getEntityManager().createQuery(sb.toString());
	    query.setParameter("statusQueued", statusQueued);
	    query.setParameter("context", context);

	    jobList = (List<Job>) query.getResultList();

	    //commented the below to code not to include interactions, this
	    // is to improve the performance of service calls
	    //addCustomerInteraction(jobList);
	}
	catch (Exception e) {
	    // if not found
	    e.printStackTrace();
	}
	logger.info("findByContextStatus took : " + (System.currentTimeMillis() - start) + "ms");
	return jobList;

    }

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public Job findLineItemEntry(final String orderId, final String lineItemId) {

	logger.info("Executing findLineItemEntry");
	long start = System.currentTimeMillis();

	if ((orderId == null) || (orderId.length() == 0) || (lineItemId == null) || (lineItemId.length() == 0)) {
	    return null;
	}

	Job jobDbVersion = null;
	StringBuilder sb = new StringBuilder();

	try {

	    sb.append("select j FROM Job as j ");
	    sb.append(" WHERE j.parentExternalId =  :orderId");
	    sb.append("  AND  j.resourceExternalId =  :lineItemId ");

	    Query query = getEntityManager().createQuery(sb.toString());
	    query.setParameter("orderId", orderId);
	    query.setParameter("lineItemId", lineItemId);
	    query.setMaxResults(1);
	    jobDbVersion = (Job) query.getSingleResult();
	}
	catch (Exception e) {
	    // if not found
	    logger.warn("Exception thrown while finding lineitem entry");
	}
	logger.info("findLineItemEntry took : " + (System.currentTimeMillis() - start) + "ms");
	return jobDbVersion;

    }

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public List<Job> findCustomerItemEntry(final String customerId, final String orderId) {
	logger.info("Executing findCustomerItemEntry");
	long start = System.currentTimeMillis();

	if ((customerId == null) || (customerId.length() == 0)) {
	    return null;
	}

	List<Job> jobsDbVersion = null;
	StringBuilder sb = new StringBuilder();

	try {

	    sb.append("select j FROM Job as j ");
	    sb.append(" WHERE j.customerId =  :customerId");
	    if ((orderId != null) && (orderId.length() > 0)) {
		sb.append("  AND  j.parentExternalId =  :orderId ");
	    }

	    Query query = getEntityManager().createQuery(sb.toString());
	    query.setParameter("customerId", customerId);
	    if ((orderId != null) && (orderId.length() > 0)) {
		query.setParameter("orderId", orderId);
	    }
	    jobsDbVersion = (List<Job>) query.getResultList();
	}
	catch (Exception e) {
	    // if not found
	    logger.warn("Exception thrown while finding customer entry",e);
	}

	logger.info("findCustomerItemEntry took : " + (System.currentTimeMillis() - start) + "ms");
	return jobsDbVersion;

    }

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public Job findLineItemEntry(final String orderId, final String lineItemId, final String context) {

	logger.info("Executing findLineItemEntry by orderId,LineItemId and Context");
	long start = System.currentTimeMillis();

	if ((orderId == null) || (orderId.length() == 0) || (lineItemId == null) || (lineItemId.length() == 0)) {
	    return null;
	}

	Job jobDbVersion = null;
	StringBuilder sb = new StringBuilder();

	try {

	    sb.append("select j FROM Job as j ");
	    sb.append(" WHERE j.parentExternalId =  :orderId");
	    sb.append("  AND  j.resourceExternalId =  :lineItemId ");
	    sb.append("  AND  j.context = :context  ");
	    sb.append(" ORDER BY j.dateEffectiveFrom DESC");

	    Query query = getEntityManager().createQuery(sb.toString());
	    query.setParameter("orderId", orderId);
	    query.setParameter("lineItemId", lineItemId);
	    query.setParameter("context", context);
	    query.setMaxResults(1);
	    jobDbVersion = (Job) query.getSingleResult();
	}catch(NoResultException nre) {
	    logger.warn("No scheduled job found for order["+orderId+"] lineitem["+lineItemId+"] and context["+context+"] !!!");
	}catch (Exception e) {
	    // if not found
	    logger.warn("Exception thrown while finding lineitem entry");
	}
	logger.info("findLineItemEntry[orderid,lineitemId,context] took : " + (System.currentTimeMillis() - start) + "ms");
	return jobDbVersion;

    }

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public Job findByExternalId(final String extId) {
	logger.info("findByExternalId");
	long start = System.currentTimeMillis();

	if ((extId == null) || (extId.length() == 0)) {
	    return null;
	}

	Job jobDbVersion = null;

	try {

	    jobDbVersion = (Job) getEntityManager().createQuery("select j FROM Job as j where j.externalId ='" + extId + "'").getSingleResult();
	}
	catch(NoResultException nre) {
	    logger.debug("No entity found forjob [" + extId+"]");
	}
	catch (Exception e) {
	    logger.error("Exception thrown while searching forjob [" + extId+"]",e);
	}

	logger.info("findByExternalId took : " + (System.currentTimeMillis() - start) + "ms");
	return jobDbVersion;

    }

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public Job find(final String extId) {
	logger.info("Executing fine");
	long start = System.currentTimeMillis();

	if ((extId == null) || (extId.length() == 0)) {
	    return null;
	}

	Job jobDbVersion = null;

	try {

	    jobDbVersion = (Job) getEntityManager().createQuery("select j FROM Job as j where j.ttl != -1 AND   j.resourceExternalId = '" + extId + "'").getSingleResult();
	}
	catch (NoResultException e) {
	    logger.debug(e.getMessage() + " job id:" + extId);
	}
	logger.info("find took : " + (System.currentTimeMillis() - start) + "ms");
	return jobDbVersion;

    }

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public Job findByContextAndExtId(final String context, final String extId) {
	logger.info("Executing findByContextAndExtId");
	long start = System.currentTimeMillis();

	if ((extId == null) || (extId.length() == 0)) {
	    return null;
	}

	Job jobDbVersion = (Job) getEntityManager().createQuery(
		"select j FROM Job as j where j.ttl != -1 AND j.resourceExternalId = '" + extId + "' AND context = '" + context + "'").getSingleResult();

	logger.info("findByContextAndExtId took : " + (System.currentTimeMillis() - start) + "ms");
	return jobDbVersion;

    }

    @SuppressWarnings("unchecked")
    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public List<Job> findByStatusAndContext(final String context, final long status, int limit) {
	logger.info("Executing findByStatusAndContext");
	long start = System.currentTimeMillis();

	if ((context == null) || (context.trim().length() == 0)) {
	    throw new IllegalArgumentException("Status and Context values are invalid");
	}

	String sql = "select j FROM Job as j where j.ttl != -1 AND j.context = '" + context.trim() + "' AND j.status=" + status;
	Query query = getEntityManager().createQuery(sql);
	query.setMaxResults(limit);
	List<Job> jobList = (List<Job>) query.getResultList();
	logger.info("findByStatusAndContext took : " + (System.currentTimeMillis() - start) + "ms");
	return jobList;

    }

    @SuppressWarnings("unchecked")
    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public List<Job> findByStatus(final long status, int limit) {
	logger.info("Executing findByStatus");
	long start = System.currentTimeMillis();

	String sql = "select j FROM Job as j where j.ttl != -1 AND j.status=" + status;
	Query query = getEntityManager().createQuery(sql);
	query.setMaxResults(limit);
	List<Job> jobList = (List<Job>) query.getResultList();

	logger.info("findByStatus took : " + (System.currentTimeMillis() - start) + "ms");
	return jobList;

    }

    @SuppressWarnings("unchecked")
    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public List<Job> findByContext(final String context, int limit) {
	logger.info("Executing findByContext ");
	long start = System.currentTimeMillis();

	if ((context == null) || (context.trim().length() == 0)) {
	    return null;
	}

	String sql = "select j FROM Job as j where j.ttl != -1 AND j.context = '" + context.trim() + "' AND j.locked = 0 AND j.status=" + SchedulePhase.scheduled.getStatus();
	Query query = getEntityManager().createQuery(sql);
	query.setMaxResults(limit);
	List<Job> jobList = (List<Job>) query.getResultList();
	logger.info("findByContext took : " + (System.currentTimeMillis() - start) + "ms");
	return jobList;

    }

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public Job failed(final String context, String userId, String extId) {
	logger.info("Executing failed() method");
	long start = System.currentTimeMillis();

	Job job = findByContextAndExtId(context, extId);
	if (!job.isLocked()) {
	    Job.failed(userId, job);
	    update(job);
	}
	else {
	    new IllegalArgumentException("job locked");
	}
	logger.info("Failed() method took : " + (System.currentTimeMillis() - start) + "ms");
	return job;

    }

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public Job cancel(String context, String userId, String extId) {
	logger.info("Canceling job");
	long start = System.currentTimeMillis();

	Job job = findByContextAndExtId(context, extId);
	if (!job.isLocked()) {
	    Job.cancel(userId, job);
	    update(job);
	}
	else {
	    new IllegalArgumentException("job locked");
	}
	logger.info("Cancelling job took : " + (System.currentTimeMillis() - start) + "ms");
	return job;
    }

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public Job complete(String context, String userId, String extId) {
	logger.info("Executing complete()");
	long start = System.currentTimeMillis();

	Job job = findByContextAndExtId(context, extId);

	if (!job.isLocked()) {
	    Job.complete(userId, job);
	    update(job);
	}
	else {
	    new IllegalArgumentException("job locked");
	}
	logger.info("Complete() method took : " + (System.currentTimeMillis() - start) + "ms");
	return job;
    }

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public Job activate(String context, String userId, String extId) {
	logger.info("Executing activate job");
	long start = System.currentTimeMillis();
	Job job = findByContextAndExtId(context, extId);

	if (!job.isLocked()) {
	    Job.activate(userId, job);
	    update(job);
	}
	else {
	    new IllegalArgumentException("job locked");
	}
	logger.info("Activate job took : " + (System.currentTimeMillis() - start) + "ms");
	return job;
    }

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public Job schedule(String context, String userId, String extId) {
	logger.info("Executing schedule");
	long start = System.currentTimeMillis();

	Job job = findByContextAndExtId(context, extId);

	if (!job.isLocked()) {
	    Job.schedule(userId, job);
	    update(job);
	}
	else {
	    new IllegalArgumentException("job locked");
	}
	logger.info("Scheduling job took : " + (System.currentTimeMillis() - start) + "ms");
	return job;
    }

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public Job create(String context, String userId, String resourceExtId, String parentExtId) {
	logger.info("Executing create job");
	long start = System.currentTimeMillis();

	Job jobDbVersion = find(resourceExtId);

	if (jobDbVersion == null) {
	    jobDbVersion = Job.create(context, userId, resourceExtId, parentExtId);
	}

	logger.debug("Created job :" + jobDbVersion.toString());

	if (jobDbVersion.isLocked()) {
	    return jobDbVersion;
	}

	if (jobDbVersion.getId() == 0) {
	    getEntityManager().persist(jobDbVersion);
	}
	else {
	    getEntityManager().merge(jobDbVersion);
	}
	logger.info("Creating job took : " + (System.currentTimeMillis() - start) + "ms");
	return jobDbVersion;
    }

    /**
     * Updates transaction id and transaction status for all the scheduled orders for given provider(context).
     */
    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public String beginTransaction(String context, String taskId) {
	logger.info("Executing beginTransaction");
	long start = System.currentTimeMillis();

	if ((context == null) || (context.trim().length() == 0)) {
	    throw new IllegalArgumentException("Context cannot be null or empty!!");
	}
	String transId = UUID.randomUUID().toString();
	String sql = "UPDATE Job j SET j.transactionId='" + transId + "' , j.transactionStatus='P' WHERE j.status=" + SchedulePhase.scheduled.getStatus() + " AND j.context='"
		+ context + "'";
	int i = getEntityManager().createQuery(sql).executeUpdate();
	logger.info("Updated transaction id for context [" + context + "] : " + i);
	logger.info("beginTransaction took : " + (System.currentTimeMillis() - start) + "ms");
	if (i > 0) return transId;
	else
	    return "NoDataFound";
    }

    /**
     * Updated the scheduled order's status and transaction status to be completed and commited so that in next batch run, these orders will not be picked
     */
    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public String commitTransaction(String transactionId) {
	logger.info("Executing commitTransaction");
	long start = System.currentTimeMillis();
	if (transactionId == null || transactionId.trim().length() == 0) {
	    throw new IllegalArgumentException("Invalid transaction id to commit transaction!!");
	}
	String sql = "UPDATE Job j SET j.status=" + SchedulePhase.completed.getStatus() + " , j.transactionStatus='C' ,j.locked=0 WHERE j.transactionId='" + transactionId + "'";
	int i = getEntityManager().createQuery(sql).executeUpdate();
	logger.info("Commited transaction for transaction id [ " + transactionId + " ] : " + i);
	logger.info("commitTransaction took : " + (System.currentTimeMillis() - start) + "ms");
	if (i > 0) return "success";
	else
	    return "failed";
    }

    /**
     * Updated the scheduled order's status and transaction status to be scheduled and rollbacked so that in next batch run, these orders will be picked
     */
    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public String rollbackTransaction(String transactionId) {
	logger.info("Executing rollbackTransaction");
	long start = System.currentTimeMillis();

	if (transactionId == null || transactionId.trim().length() == 0) {
	    throw new IllegalArgumentException("Invalid transaction id to rollback transaction!!");
	}
	String sql = "UPDATE Job j SET j.status=" + SchedulePhase.scheduled.getStatus() + " , j.transactionStatus='R' ,j.locked=0 WHERE j.transactionId='" + transactionId + "'";
	int i = getEntityManager().createQuery(sql).executeUpdate();
	logger.info("Commited transaction for transaction id [ " + transactionId + " ] : " + i);
	logger.info("rollbackTransaction took : " + (System.currentTimeMillis() - start) + "ms");
	if (i > 0) return "success";
	else
	    return "failed";
    }
    
    /* (non-Javadoc)
     * @see com.A.Vdao.dao.JobDao#rollbackTransaction(long)
     */
    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public boolean updateTransactionStatus(long taskJoId, boolean status){
		logger.info("Executing rollbackTransaction");
		long start = System.currentTimeMillis();
		
		String sql = "UPDATE Job j SET j.status=";
		if(status){
			sql = sql + "0 WHERE j.id='" + taskJoId + "'";
		}
		else{
			sql = sql + "2 WHERE j.id='" + taskJoId + "'";
		}
		int i = getEntityManager().createQuery(sql).executeUpdate();
		
		logger.info("Commited transaction for transaction id [ " + taskJoId + " ] : " + i);
		logger.info("updateTransactionStatus took : " + (System.currentTimeMillis() - start) + "ms");
		if (i > 0) 
			return true;
		else
		    return false;
    }

    /* (non-Javadoc)
     * @see com.A.Vdao.dao.JobDao#retrieveJobsByTransactionId(java.lang.String)
     */
    public List<Job> retrieveJobsByTransactionId(String transactionId){
    	logger.info("executing retrieveJobsByTransactionId");
    	long startTime = System.currentTimeMillis();
    	
    	String sql = "select j.id,j.parentExternalId,j.resourceExternalId FROM Job as j where j.transactionId='" + transactionId + "'";
    	Query query = getEntityManager().createQuery(sql);
    	//query.setMaxResults(limit);
    	List<Object[]> jobListAry = query.getResultList();
    	List<Job> jobList = null;
    	if(null != jobListAry){
    		jobList = new ArrayList<Job>();
    		for(Object[] jobAry : jobListAry){
    			Job job = new Job();
    			job.setId((Long)jobAry[0]);
    			job.setParentExternalId((String)jobAry[1]);
    			job.setResourceExternalId((String)jobAry[2]);
    			jobList.add(job);
    		}
    	}
    	
    	logger.info("retrieveJobsByTransactionId completed in "+(System.currentTimeMillis() - startTime)+" ms ");
    	return jobList;
    }

}
