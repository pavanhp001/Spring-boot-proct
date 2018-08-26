package com.AL.task.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.AL.rules.core.RuleService;
import com.AL.task.LocalTaskJob;
import com.AL.task.TaskBase;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.task.response.ResponseBuilder;
import com.AL.util.CalendarUtil;
import com.AL.util.messaging.Broadcastable;
import com.AL.util.messaging.impl.HttpBroadcastable;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.job.Job;
import com.AL.Vdao.dao.JobDao;
import com.AL.Vdao.dao.OrderManagementDao;
import com.AL.vm.service.OrderManagementService;
import com.AL.vm.util.converter.marshall.MarshallOrder;
import com.AL.vm.util.converter.unmarshall.UnmarshallJob;
import com.AL.vm.util.converter.unmarshall.UnmarshallOrder;
import com.AL.xml.v4.JobCollectionType;
import com.AL.xml.v4.JobType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

/**
 * @author ebthomas
 *
 */

@Component("taskJob")
public class TaskJob extends TaskBase<OrderManagementRequestResponseDocument> implements LocalTaskJob<OrderManagementRequestResponseDocument>, Broadcastable {

    private Logger logger = Logger.getLogger(TaskJob.class);

    @Autowired
    private UnmarshallJob unmarshallJob;

    @Autowired
    private OrderManagementService omeService;

    @Autowired
    private UnmarshallOrder unmarshallOrder;

    @Autowired
    private MarshallOrder<SalesOrder> marshallOrder;

    @Autowired
    private OrderManagementDao orderManagementDao;

    @Autowired
    private ResponseBuilder responseBuilder;

    @Autowired
    private JobDao jobDao;

    @Autowired
    public RuleService ruleService;

    /**
     * Constructor.
     */
    public TaskJob() {
	super();
    }

    /**
     * {@inheritDoc}
     */
    public OrchestrationContext processRequest(final OrderManagementRequestResponseDocument orderDocument) {

	logger.info("Processing request taskJob");
	Request request = orderDocument.getOrderManagementRequestResponse().getRequest();

	String agentId = request.getAgentId();

	JobCollectionType jobCollection = orderDocument.getOrderManagementRequestResponse().getRequest().getJobs();

	List<JobType> jobTypeList = jobCollection.getJobList();

	List<Job> joblist = unmarshallJob.build(jobTypeList);
	logger.trace("Processing JobList : " + joblist.toString());

	OrchestrationContext params = createLoadContext(orderDocument);

	List<String> reasons = request.getReasonList();

	if ((reasons != null) && (reasons.size() > 0)) {
	    params.add(TaskContextParamEnum.reason.name(), reasons);
	}

	try {

	    params.add(TaskContextParamEnum.jobList.name(), joblist);
	    params.add(TaskContextParamEnum.request.name(), request);
	    params.add(TaskContextParamEnum.agentId.name(), agentId);
	}
	catch (Exception e) {
	    return OrchestrationContext.Factory.createOme(e);
	}
	return params;

    }

    /**
     * {@inheritDoc}
     */

    @SuppressWarnings("unchecked")
    public OrchestrationContext processAgenda(final OrchestrationContext params) {

	Request request = (Request) params.get(TaskContextParamEnum.request.name());
	List<Job> jobList = (List<Job>) params.get(TaskContextParamEnum.jobList.name());
	Job job = null;

	String agentId = (String) params.get(TaskContextParamEnum.agentId.name());

	List<Job> jobResultList = null;

	if ((jobList != null) && (jobList.size() > 0)) {
	    job = jobList.get(0);

	}

	if ((job != null) && (request.getJobAction() != null)) {
	    logger.info("Job Action : " + request.getJobAction().toString());
	    if ((Request.JobAction.FIND_BY_EXTERNAL_ID.toString().equalsIgnoreCase(request.getJobAction().toString()))) {

		jobResultList = this.findByExternalId(job);

		params.add(TaskContextParamEnum.jobList.name(), jobResultList);
	    }
	    else if ((Request.JobAction.FIND_BY_CONTEXT_STATUS.toString().equalsIgnoreCase(request.getJobAction().toString()))) {

		List<String> reasons = (List) params.get(TaskContextParamEnum.reason.name());

		jobResultList = this.findByContextStatus(reasons, job);

		params.add(TaskContextParamEnum.jobList.name(), jobResultList);
	    }
	    else if ((Request.JobAction.FIND_BY_ORDER_LINE_ITEM.toString().equalsIgnoreCase(request.getJobAction().toString()))) {

		jobResultList = this.findByOrderLineItem(job);

		params.add(TaskContextParamEnum.jobList.name(), jobResultList);
	    }
	    else if ((Request.JobAction.FIND_NEXT_AND_OFFER.toString().equalsIgnoreCase(request.getJobAction().toString()))) {

		jobResultList = this.findNextAndOffer(job);

		params.add(TaskContextParamEnum.jobList.name(), jobResultList);
	    }
	    else if ((Request.JobAction.FIND_NEXT_AND_PROCESS.toString().equalsIgnoreCase(request.getJobAction().toString()))) {

		jobResultList = this.findNextAndProcess(job);

		params.add(TaskContextParamEnum.jobList.name(), jobResultList);
	    }

	    else if ((Request.JobAction.IS_LOCKED.toString().equalsIgnoreCase(request.getJobAction().toString()))) {

		params.add(TaskContextParamEnum.jobList.name(), jobResultList);
	    }

	    else if ((Request.JobAction.UPDATE.toString().equalsIgnoreCase(request.getJobAction().toString()))) {
		if (job.getExternalId() != null && !job.getExternalId().equals("")) {
		    Job jobFromDatabase = jobDao.findByExternalId(job.getExternalId());
			Calendar cal = Calendar.getInstance();

		    if ((jobFromDatabase != null) && (job != null)) {

			if (job.isLocked() && (!jobFromDatabase.isLocked())) {

			    if (job.getLockedAt() != null) {
				jobFromDatabase.setLockedAt(cal);
				jobFromDatabase.setDateEffectiveFrom(Calendar.getInstance());
				
				cal.add(Calendar.MINUTE, (int) job.getTtl());
				jobFromDatabase.setDateEffectiveTo(cal);

				jobFromDatabase.setTtl(job.getTtl() * 60);
			    }
			    else {
				jobFromDatabase.setLockedAt(cal);
				jobFromDatabase.setDateEffectiveFrom(Calendar.getInstance());

				cal.add(Calendar.MINUTE, (int) job.getTtl());

				jobFromDatabase.setDateEffectiveTo(cal);

				jobFromDatabase.setTtl(job.getTtl() * 60);

			    }
			    
			    logger.debug("Locking time: "+cal.getTime()+" Effective Date/time from: "+jobFromDatabase.getDateEffectiveFrom()+" Effective Date/time to:"+jobFromDatabase.getDateEffectiveTo()+" TTL:"+jobFromDatabase.getTtl());

			}

			if (!job.isLocked()) {
			    jobFromDatabase.setLockedAt(null);
			    jobFromDatabase.setDateEffectiveFrom(null);
			    jobFromDatabase.setDateEffectiveTo(null);
			    jobFromDatabase.setLocked(Boolean.FALSE);
			}

			jobFromDatabase.setLocked(job.isLocked());
			int multipler = 60;
			if (job.getTtl() < 0) {
			    multipler = 1;
			}
			jobFromDatabase.setTtl(job.getTtl() * multipler);
			jobFromDatabase.setLoginId((agentId != null) && (agentId.length() > 0) ? agentId : "default");
			logger.debug("Locking time:: "+cal.getTime()+" Effective Date/time from: "+jobFromDatabase.getDateEffectiveFrom()+" Effective Date/time to:"+jobFromDatabase.getDateEffectiveTo()+" TTL:"+jobFromDatabase.getTtl());

			jobDao.update(jobFromDatabase);
		    }
		}
		else {
		    // job external id as empty and set job customerId and job parentExternalId(i.e order id) & taskJob:update.
		    // get all the related lineitems & lock all of them
		    /*
		     * <v4:job> <v4:externalId>null</v4:externalId> <v4:ttl>5min/-1</v4:ttl> <v4:loginId>mnagineni</v4:loginId> <v4:lockedAt>2013-05-09</v4:lockedAt>
		     * <v4:parentExternalId>12345</v4:parentExternalId> <v4:customerId>12345</v4:customerId> </v4:job> 1 customer 1...many orders 1..many lineItems
		     */
		    boolean updateNeeded = true;
		    List<Job> jobsFromDatabase = jobDao.findCustomerItemEntry(job.getCustomerId(), job.getParentExternalId());
		    // 1-order-multipleLineItems OR MANY orders--multipleLineItems
		    if ((jobsFromDatabase != null) && (jobsFromDatabase.size() > 0) && (job != null)) {
			for (Job jb : jobsFromDatabase) {
			    if (jb.isLocked() && job.getTtl() > 0) {
				// return single result - Cannot edit customer information at this time as [agent x] is working on an order
				// NO UPDATE NEEDED
				updateNeeded = false;
				break;
			    }
			}

			if (updateNeeded == false) {
			    // When there is at least one row which is already locked
			    job.setCustomerId("-1");
			}
			else {
			    for (Job jb : jobsFromDatabase) {
				// **** Fine Tune This
				// lock all orders related to customer and say Cannot lock line item at this time as [agent x] is updating customer information.
				// UPDATE ALL RELATED RECORDS
//				jb.setLockedAt(CalendarUtil.INSTANCE.getGMTCalendar());
//				jb.setDateEffectiveFrom(CalendarUtil.INSTANCE.getGMTCalendar());
//				Calendar cal = CalendarUtil.INSTANCE.getGMTCalendar();
//				cal.add(Calendar.MINUTE, (int) job.getTtl());
//				jb.setDateEffectiveTo(cal);
				jb.setLoginId((agentId != null) && (agentId.length() > 0) ? agentId : "default");

				Calendar cal = Calendar.getInstance();
				if (job.getTtl() > 0) {
					jb.setLocked(job.isLocked());
			         jb.setTtl(job.getTtl() * 60);
			         jb.setLockedAt(cal);
			         jb.setDateEffectiveFrom(Calendar.getInstance());
			         
			         cal.add(Calendar.MINUTE, (int) job.getTtl());
			         jb.setDateEffectiveTo(cal);
				}
				else {
				    jb.setLocked(false);
				    jb.setTtl(-1);
				    jb.setLockedAt(null);
				    jb.setDateEffectiveTo(null);
				    jb.setDateEffectiveFrom(null);
				    
				}
				
				logger.debug("Locking time: "+cal.getTime()+" Effective Date/time from: "+jb.getDateEffectiveFrom()+" Effective Date/time to:"+jb.getDateEffectiveTo()+" TTL:"+jb.getTtl());

				jobDao.update(jb);
			    }
			    params.add(TaskContextParamEnum.jobList.name(), jobsFromDatabase);
			}
		    }
		    else {
			// when customer/order doesn't exist in om_job table
			if (job != null) {
			    job.setCustomerId("0");
			}
		    }
		}
	    }
	    else if ((Request.JobAction.CREATE.toString().equalsIgnoreCase(request.getJobAction().toString()))) {

		for (Job jobToSave : jobList) {

		    if (jobToSave != null) {
			jobToSave.setExternalId("0");
			jobToSave.setLoginId((agentId != null) && (agentId.length() > 0) ? agentId : "default");

			jobDao.save(jobToSave);
		    }
		}

	    }
	}

	return params;
    }

    private List<Job> findNextAndOffer(Job job) {
	logger.info("Executing findNextAndOffer()");
	List<Job> jobResultList = new ArrayList<Job>();

	if ((job.getContext() != null) && (job.getContext().length() > 0) && (job.getStatusQueued() != null) && (job.getStatusQueued().length() > 0)) {
		logger.info("Searching job Context="+job.getContext()+",StatusQueued="+job.getStatusQueued()+",Ttl="+job.getTtl()+",LoginId="+job.getLoginId()+",channelType="+job.getChannelType());
	    jobResultList = jobDao.findNextByPriority(job.getContext(), job.getStatusQueued(), (int) job.getTtl(), job.getLoginId(), job.getChannelType());
	}

	return jobResultList;
    }

    private List<Job> findByContextStatus(List<String> reasons, Job job) {
	logger.info("Executing findByContextStatus()");
	List<Job> jobResultList = new ArrayList<Job>();

	if ((job.getStatusQueued() != null) && (job.getStatusQueued().indexOf(",") != -1)) {
		logger.info("findByContextMultipleStatus -- context="+job.getContext()+", reasons="+ reasons +", StatusQueued="+job.getStatusQueued()+",channelType="+job.getChannelType());
	    jobResultList = jobDao.findByContextMultipleStatus(reasons, job.getContext(), job.getStatusQueued(), job.getChannelType());
	}
	else {
		logger.info("findByContextStatus -- context="+ job.getContext() + ", StatusQueued=" + job.getStatusQueued()+",channelType="+job.getChannelType());
	    jobResultList = jobDao.findByContextStatus(job.getContext(), job.getStatusQueued(), job.getChannelType());
	}

	return jobResultList;
    }

    private List<Job> findNextAndProcess(Job job) {
	logger.info("Executing findNextAndProcess()");
	List<Job> jobResultList = new ArrayList<Job>();

	if ((job.getContext() != null) && (job.getContext().length() > 0) && (job.getStatusQueued() != null) && (job.getStatusQueued().length() > 0)) {
		logger.info("Searching job : Context="+job.getContext()+",StatusQueued="+job.getStatusQueued()+",Ttl="+job.getTtl()+",LoginId="+job.getLoginId()+",channelType="+job.getChannelType());
	    jobResultList = jobDao.findNextByPriority(job.getContext(), job.getStatusQueued(), (int) job.getTtl(), job.getLoginId(), job.getChannelType());

	}

	return jobResultList;
    }

    private List<Job> findByOrderLineItem(Job job) {
	logger.info("Executing findByOrderLineItem()");
	List<Job> jobResultList = new ArrayList<Job>();

	if ((job.getResourceExternalId() != null) && (job.getResourceExternalId().length() > 0) && (job.getParentExternalId() != null) && (job.getParentExternalId().length() > 0)
		&& (job.getContext() != null) && (job.getContext().length() > 0)

	) {

	    final String orderId = job.getParentExternalId();
	    final String lineItemId = job.getResourceExternalId();
	    final String context = job.getContext();
	    logger.info("Searching for job by OrderId="+ orderId + ", LineItemId="+ lineItemId + ",Context=" + context);
	    Job jobResult = jobDao.findLineItemEntry(orderId, lineItemId, context);
	    if (jobResult != null) {
		logger.debug("Found job=" + jobResult);
		jobResultList.add(jobResult);
	    }

	}

	return jobResultList;
    }

    private List<Job> findByExternalId(Job job) {
	logger.info("executing findByExternalId()" );
	List<Job> jobResultList = null;

	if ((job.getExternalId() != null) && (job.getExternalId().length() > 0) && (!job.getExternalId().equals("0"))) {
	    logger.info("Job ExternalId = " + job.getExternalId());
	    jobResultList = new ArrayList<Job>();
	    Job jobResult = jobDao.findByExternalId(job.getExternalId());
	    if (jobResult != null) {
		logger.debug("Found job for ExternalId="+jobResult);
		jobResultList.add(jobResult);
	    }

	}

	return jobResultList;
    }

    /**
     * {@inheritDoc}
     */
    public OrderManagementRequestResponseDocument processResponse(final OrchestrationContext params) {
	return responseBuilder.buildResponse(params);
    }

    public OrderManagementDao getOrderManagementDao() {
	return orderManagementDao;
    }

    public void setOrderManagementDao(OrderManagementDao orderManagementDao) {
	this.orderManagementDao = orderManagementDao;
    }

    /**
     * {@inheritDoc}
     */
    public OrderManagementRequestResponseDocument handleFault(final Exception e, final OrchestrationContext params, final OrderManagementRequestResponseDocument orderDocument) {

	logger.debug(e);
	return orderDocument;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void broadcast(String strToBroadcastOriginal, Map<String, String> map) {

	//Broadcastable broadcast = HttpBroadcastable.createDefault();
	//broadcast.broadcast(strToBroadcastOriginal, map);
    }

    public OrderManagementService getOmeService() {
	return omeService;
    }

    public void setOmeService(OrderManagementService omeService) {
	this.omeService = omeService;
    }

    public UnmarshallOrder getUnmarshallOrder() {
	return unmarshallOrder;
    }

    public void setUnmarshallOrder(UnmarshallOrder unmarshallOrder) {
	this.unmarshallOrder = unmarshallOrder;
    }

    public MarshallOrder<SalesOrder> getMarshallOrder() {
	return marshallOrder;
    }

    public void setMarshallOrder(MarshallOrder<SalesOrder> marshallOrder) {
	this.marshallOrder = marshallOrder;
    }

}
