
package com.A.V.beans.job;
 

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.A.V.beans.entity.CustomerInteraction;
 

@Entity
@Table(name = "OM_JOB")
public class Job implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6459172354578836311L;


	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "jobSequence")
	@SequenceGenerator(name = "jobSequence", sequenceName = "OM_JOB_SEQ" ,allocationSize = 1)
	private long id;
	
	
	@Column(name = "EXTERNAL_ID")
	private String externalId;

	@Column(name = "CONTEXT")
	private String context;

	
	@Column(name = "IS_LOCKED")
	private boolean locked;

	@Column(name = "TTL")
	private long ttl;
	
	@Column(name = "CUSTOMER_ID")
	private String customerId;
	

	@Column(name = "RESOURCE_PARENT_ID")
	private String parentExternalId;
	
	@Column(name = "RESOURCE_EXT_ID")
	private String resourceExternalId;

	@Column(name = "STATUS")
	private long status;
	
	@Column(name = "FLOW_STATE")
	private long flowState;

	@Column(name = "LOGIN_ID")
	private String loginId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFFECTIVE_FROM_ON")
	private Calendar dateEffectiveFrom;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFFECTIVE_TO_ON")
	private Calendar dateEffectiveTo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOCKED_AT")
	private Calendar lockedAt;

	@Column(name = "PRIORITY")
	private int priority;

	@Column(name = "TRANSACTION_ID")
	private String transactionId;
	
	@Column(name = "TRANS_STATUS")
	private String transactionStatus;
	
	@Column(name = "STATUS_QUEUED")
	private String statusQueued;
	
	@Column(name = "DESC1")
	private String description1;
	
	@Column(name = "DESC2")
	private String description2;
	
	@Column(name = "REASON")
	private String reason;
	
	// New fields added for the ticket #115861 -- START 
	@Column(name = "ORDER_DATE")
    private Calendar orderDate;
	
	@Column(name = "FIRSTNAME")
	private String firstName;

	@Column(name = "LASTNAME")
	private String lastName;
	// New fields added for the ticket #115861 -- END
	
	@Column(name = "CHANNEL_TYPE")
	private int channelType;
	
	@Transient
	private Set<CustomerInteraction> customerInteractionList;

	public static Job failed(String userId, Job job) {

		Calendar calDateEffectiveTo = Calendar.getInstance();
		calDateEffectiveTo.add(Calendar.MONTH, 12);
		  
		job.setDateEffectiveTo(calDateEffectiveTo);
		job.setLoginId(userId);
		job.setPriority(1000);
		job.setStatus(SchedulePhase.failed.getStatus());
		job.setTtl(60 * 24 * 365); // available to run for 1 year
		job.setLocked(Boolean.FALSE);

		return job;
	}
	
	
	public static Job cancel(String userId, Job job) {

		Calendar calDateEffectiveTo = Calendar.getInstance();
		  
		job.setDateEffectiveTo(calDateEffectiveTo);
		job.setLoginId(userId);
		job.setPriority(100);
		job.setStatus(SchedulePhase.cancelled.getStatus());
		job.setTtl(0);  
		job.setLocked(Boolean.FALSE);

		return job;
	}
	
	
	public static Job complete(String userId, Job job) {

		Calendar calDateEffectiveTo = Calendar.getInstance();
		  
		job.setDateEffectiveTo(calDateEffectiveTo);
		job.setLoginId(userId);
		job.setPriority(100);
		job.setStatus(SchedulePhase.completed.getStatus());
		job.setTtl(0);  
		job.setLocked(Boolean.FALSE);

		return job;
	}
	
	
	public static Job activate(String userId, Job job) {

		// TODO:get effective to from Database
		Calendar calDateEffectiveTo = Calendar.getInstance();
		calDateEffectiveTo.add(Calendar.MINUTE, 30);

		job.setDateEffectiveTo(calDateEffectiveTo);
		job.setLoginId(userId);
		job.setPriority(100);
		job.setStatus(SchedulePhase.active.getStatus());
		job.setTtl(30); // available to run for 30 Minutes
		job.setLocked(Boolean.TRUE);

		return job;
	}
	
	
	
	public static Job schedule(String userId, Job job) {

		// TODO:get effective to from Database
		Calendar calDateEffectiveTo = Calendar.getInstance();
		calDateEffectiveTo.add(Calendar.MINUTE, 30);

		job.setDateEffectiveTo(calDateEffectiveTo);
		job.setLoginId(userId);
		job.setPriority(10);
		job.setStatus(SchedulePhase.scheduled.getStatus());
		job.setTtl(60 * 24 * 365); // available to run for 1 year
		job.setLocked(Boolean.FALSE);

		return job;
	}

	public static Job create(String context, String userId,  String extId) {

		// TODO:get effective to from Database
		Calendar calDateEffectiveTo = Calendar.getInstance();
		calDateEffectiveTo.add(Calendar.MONTH, 12);

		Job job = new Job();
		job.setResourceExternalId(extId);
		job.setDateEffectiveFrom(Calendar.getInstance());
		job.setDateEffectiveTo(calDateEffectiveTo);
		job.setContext(context);
		job.setLoginId(userId);
		job.setPriority(10);
		job.setStatus(SchedulePhase.created.getStatus());
		job.setTtl(-1);  
		job.setLocked(Boolean.FALSE);
		job.setExternalId("0");
		job.setTransactionStatus("S");
		job.setTransactionId(System.currentTimeMillis()+extId);
		return job;
	}
	
	
	public static Job create(String context, String userId,  String resourceExtId, String parentExtId) {

		// TODO:get effective to from Database
		Calendar calDateEffectiveTo = Calendar.getInstance();
		calDateEffectiveTo.add(Calendar.MONTH, 12);

		Job job = new Job();
		job.setResourceExternalId(resourceExtId);
		job.setParentExternalId(parentExtId);
		job.setDateEffectiveFrom(Calendar.getInstance());
		job.setDateEffectiveTo(calDateEffectiveTo);
		job.setContext(context);
		job.setLoginId(userId);
		job.setPriority(10);
		job.setStatus(SchedulePhase.created.getStatus());
		job.setTtl(60 * 24 * 365); // available to run for 1 year
		job.setLocked(Boolean.FALSE);
		job.setExternalId("0");
		return job;
	}

	public static void unlock(Job job, String userId, String extId) {

		// TODO:get effective to from Database
		Calendar calDateEffectiveTo = Calendar.getInstance();
		calDateEffectiveTo.add(Calendar.MINUTE, 30);

		job.setResourceExternalId(extId);
		job.setDateEffectiveFrom(Calendar.getInstance());
		job.setDateEffectiveTo(calDateEffectiveTo);
		job.setContext("*");
		job.setLoginId(userId);
		job.setPriority(10);
		job.setStatus(SchedulePhase.completed.getStatus());
		job.setTtl(-1);
		job.setLocked(Boolean.FALSE);

	}

	public static void lock(Job job, String userId, String extId) {

		// TODO:get effective to from Database
		Calendar calDateEffectiveTo = Calendar.getInstance();
		calDateEffectiveTo.add(Calendar.MINUTE, 30);

		job.setResourceExternalId(extId);
		job.setDateEffectiveFrom(Calendar.getInstance());
		job.setDateEffectiveTo(calDateEffectiveTo);
		job.setContext("*");
		job.setLoginId(userId);
		job.setPriority(10);
		job.setStatus(SchedulePhase.active.getStatus());
		job.setTtl(30);
		job.setLocked(Boolean.TRUE);

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public long getTtl() {
		return ttl;
	}

	public void setTtl(long ttl) {
		this.ttl = ttl;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public Calendar getDateEffectiveFrom() {
		return dateEffectiveFrom;
	}

	public void setDateEffectiveFrom(Calendar dateEffectiveFrom) {
		this.dateEffectiveFrom = dateEffectiveFrom;
	}

	public Calendar getDateEffectiveTo() {
		return dateEffectiveTo;
	}

	public void setDateEffectiveTo(Calendar dateEffectiveTo) {
		this.dateEffectiveTo = dateEffectiveTo;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getResourceExternalId() {
		return resourceExternalId;
	}

	public void setResourceExternalId(String resourceExternalId) {
		this.resourceExternalId = resourceExternalId;
	}

	public String getLoginId() {
		return loginId;
	}


	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}


	public long getFlowState() {
		return flowState;
	}


	public void setFlowState(long flowState) {
		this.flowState = flowState;
	}


	public String getParentExternalId() {
		return parentExternalId;
	}


	public void setParentExternalId(String parentExternalId) {
		this.parentExternalId = parentExternalId;
	}

	
	public String getTransactionStatus() {
		return transactionStatus;
	}


	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}


	public String getStatusQueued() {
		return statusQueued;
	}


	public void setStatusQueued(String statusQueued) {
		this.statusQueued = statusQueued;
	}
	
	public String getTransactionId() {
		return transactionId;
	}


	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}


	public Calendar getLockedAt() {
		return lockedAt;
	}


	public void setLockedAt(Calendar lockedAt) {
		this.lockedAt = lockedAt;
	}


	public String getDescription1() {
		return description1;
	}


	public void setDescription1(String description1) {
		this.description1 = description1;
	}


	public String getDescription2() {
		return description2;
	}


	public void setDescription2(String description2) {
		this.description2 = description2;
	}

	

	public Set<CustomerInteraction> getCustomerInteractionList() {
		return customerInteractionList;
	}


	public void setCustomerInteractionList(
			Set<CustomerInteraction> customerInteractionList) {
		this.customerInteractionList = customerInteractionList;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public Calendar getOrderDate() {
		return orderDate;
	}


	public void setOrderDate(Calendar orderDate) {
		this.orderDate = orderDate;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	@Override
	public String toString() {
		return "Job [id=" + id + ", context=" + context + ", locked=" + locked
				+ ", ttl=" + ttl + ", parentExternalId=" + parentExternalId
				+ ", resourceExternalId=" + resourceExternalId + ", status="
				+ status + ", flowState=" + flowState + ", loginId=" + loginId
				+ ", dateEffectiveFrom=" + dateEffectiveFrom
				+ ", dateEffectiveTo=" + dateEffectiveTo + ", priority="
				+ priority + ", transactionId=" + transactionId
				+ ", transactionStatus=" + transactionStatus
				+ ", statusQueued=" + statusQueued + ", orderDate=" 
				+ orderDate + ", firstName=" + firstName
				+ ", lastName=" + lastName + "]";
	}


	public String getExternalId() {
		return externalId;
	}


	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}


	public String getCustomerId() {
		return customerId;
	}


	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public int getChannelType() {
		return channelType;
	}


	public void setChannelType(int channelType) {
		this.channelType = channelType;
	}

}
