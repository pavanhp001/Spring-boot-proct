package com.A.ui.vo;

import java.text.DateFormat;
import java.util.Date;

import com.A.util.DateUtil;
import com.A.xml.v4.JobType;

public class JobVO extends JobType {

	public JobVO(final JobType jt) {

		this.setExternalId(jt.getExternalId());
		this.setCustomerId(jt.getCustomerId());
		this.setId(jt.getId());
		this.setContext(jt.getContext());
		this.setLocked(jt.isLocked());
		this.setTtl(jt.getTtl());
		this.setParentExternalId(jt.getParentExternalId());
		this.setResourceExternalId(jt.getResourceExternalId());
		this.setStatus(jt.getStatus());
		this.setFlowState(jt.getFlowState());
		this.setLoginId(jt.getLoginId());
		this.setDateEffectiveFrom(jt.getDateEffectiveFrom());
		this.setDateEffectiveTo(jt.getDateEffectiveTo());
		this.setLockedAt(jt.getLockedAt());
		this.setPriority(jt.getPriority());
		this.setTransactionId(jt.getTransactionId());
		this.setTransactionStatus(jt.getTransactionStatus());
		this.setStatusQueued(jt.getStatusQueued());
		this.setDesc1(jt.getDesc1());
		this.setDesc2(jt.getDesc2());
		this.setOrderDate(jt.getOrderDate());
		this.setFirstName(jt.getFirstName());
		this.setLastName(jt.getLastName());
	}
	
	public String getDateTimeString() {
		return DateUtil.toDateString(this.getOrderDate()) +" " + DateUtil.toTimeString(this.getOrderDate());
	}

	public String getCreatedOn() {

		if (dateEffectiveFrom != null) {

			return DateFormat.getTimeInstance(DateFormat.SHORT).format(
					dateEffectiveFrom);
		}

		return "";

	}

	public String getLockedAtVo() {
		Date date = new Date();

		{

			return DateFormat.getTimeInstance(DateFormat.MEDIUM).format(date);
		}
	}

}
