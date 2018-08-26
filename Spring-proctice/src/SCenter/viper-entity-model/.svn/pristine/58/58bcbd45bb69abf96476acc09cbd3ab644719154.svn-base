package com.A.V.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LineitemScheduleInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "DESIRED_START_ON")
	private Calendar desiredStartDate;

	//**************************************
	@Column(name = "desired_start_ee_on")
	private Calendar desiredStartEEDate;
	
	@Column(name = "scheduled_start_ee_on")
	private Calendar scheduledStartEEDate;

	@Column(name = "actual_start_ee_on")
	private Calendar actualStartEEDate;
	
	@Column(name = "disconnect_ee_on")
	private Calendar disconnectEEDate;
	
	@Column(name = "DESIRED_START_REQ")
	private String desiredStartReq;
	
	 

	@Column(name = "SCHEDULED_START_ON")
	private Calendar scheduledStartDate;

	@Column(name = "ACTUAL_START_ON")
	private Calendar actualStartDate;
	
	@Column(name = "DISCONNECT_ON")
	private Calendar disconnectDate;
	
	@Column(name = "ORDERED_ON")
	private Calendar orderDate;
	
	@Column(name = "SCH_AS_SOON_AS_POSSIBLE")
	private boolean scheduleAsSoonAsPossible;
	
	@Column(name = "APPT_COMMENT")
	private String appointmentComment;
	
	@Column(name = "BILLING_INSTALLMENT")
	private boolean billingInstallments = Boolean.FALSE;
	
	@Column(name = "EARLY_APPT_DATE")
	private boolean earlierAppointmentDate = Boolean.FALSE;

	@Column(name = "INSTALLATION_FEE")
	private BigDecimal installationFee;
	
	@Column(name = "RESIDENCE_TYPE")
	private String residenceType;
	
	public Calendar getDesiredStartDate() {
		return desiredStartDate;
	}

	public void setDesiredStartDate(final Calendar desiredStartDate) {
		this.desiredStartDate = desiredStartDate;
	}

	public Calendar getScheduledStartDate() {
		return scheduledStartDate;
	}

	public void setScheduledStartDate(final Calendar scheduledStartDate) {
		this.scheduledStartDate = scheduledStartDate;
	}

	public Calendar getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(final Calendar actualStartDate) {
		this.actualStartDate = actualStartDate;
	}
	
	public boolean isScheduleAsSoonAsPossible() {
		return scheduleAsSoonAsPossible;
	}

	public void setScheduleAsSoonAsPossible(boolean scheduleAsSoonAsPossible) {
		this.scheduleAsSoonAsPossible = scheduleAsSoonAsPossible;
	}

	public String getAppointmentComment() {
		return appointmentComment;
	}

	public void setAppointmentComment(String appointmentComment) {
		this.appointmentComment = appointmentComment;
	}

	public boolean isBillingInstallments() {
		return billingInstallments;
	}

	public void setBillingInstallments(boolean billingInstallments) {
		this.billingInstallments = billingInstallments;
	}

	public boolean isEarlierAppointmentDate() {
		return earlierAppointmentDate;
	}

	public void setEarlierAppointmentDate(boolean earlierAppointmentDate) {
		this.earlierAppointmentDate = earlierAppointmentDate;
	}

	public String getResidenceType() {
		return residenceType;
	}

	public void setResidenceType(String residenceType) {
		this.residenceType = residenceType;
	}

	public BigDecimal getInstallationFee() {
		return installationFee;
	}

	public void setInstallationFee(BigDecimal installationFee) {
		this.installationFee = installationFee;
	}

	public Calendar getDisconnectDate() {
		return disconnectDate;
	}

	public void setDisconnectDate(Calendar disconnectDate) {
		this.disconnectDate = disconnectDate;
	}

	public Calendar getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Calendar orderDate) {
		this.orderDate = orderDate;
	}

	public Calendar getDesiredStartEEDate() {
		return desiredStartEEDate;
	}

	public void setDesiredStartEEDate(Calendar desiredStartEEDate) {
		this.desiredStartEEDate = desiredStartEEDate;
	}

	public Calendar getScheduledStartEEDate() {
		return scheduledStartEEDate;
	}

	public void setScheduledStartEEDate(Calendar scheduledStartEEDate) {
		this.scheduledStartEEDate = scheduledStartEEDate;
	}

	public Calendar getActualStartEEDate() {
		return actualStartEEDate;
	}

	public void setActualStartEEDate(Calendar actualStartEEDate) {
		this.actualStartEEDate = actualStartEEDate;
	}

	public Calendar getDisconnectEEDate() {
		return disconnectEEDate;
	}

	public void setDisconnectEEDate(Calendar disconnectEEDate) {
		this.disconnectEEDate = disconnectEEDate;
	}

	public String getDesiredStartReq() {
		return desiredStartReq;
	}

	public void setDesiredStartReq(String desiredStartReq) {
		this.desiredStartReq = desiredStartReq;
	}

	 
	
	
	
}
