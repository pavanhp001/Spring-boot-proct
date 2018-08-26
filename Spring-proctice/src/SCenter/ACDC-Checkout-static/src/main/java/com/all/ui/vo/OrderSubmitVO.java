package com.AL.ui.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;
import com.AL.xml.cm.v4.PaymentEventType;

public class OrderSubmitVO {
	private XMLGregorianCalendar installDate;
	private XMLGregorianCalendar startTime;
	private XMLGregorianCalendar endTime;
	private String orderQualStatusCode;
	private List<PaymentEventType> paymentEvents;
	private CreditCardVO newCardInfo;

	public XMLGregorianCalendar getInstallDate() {
		return installDate;
	}

	public void setInstallDate(XMLGregorianCalendar installDate) {
		this.installDate = installDate;
	}

	public XMLGregorianCalendar getStartTime() {
		return startTime;
	}

	public void setStartTime(XMLGregorianCalendar startTime) {
		this.startTime = startTime;
	}

	public XMLGregorianCalendar getEndTime() {
		return endTime;
	}

	public void setEndTime(XMLGregorianCalendar endTime) {
		this.endTime = endTime;
	}

	public String getOrderQualStatusCode() {
		return orderQualStatusCode;
	}

	public void setOrderQualStatusCode(String orderQualStatusCode) {
		this.orderQualStatusCode = orderQualStatusCode;
	}

	public List<PaymentEventType> getPaymentEvents() {
		if(paymentEvents == null) {
			paymentEvents = new ArrayList<PaymentEventType>();
		}
		return paymentEvents;
	}

	public void setPaymentEvents(List<PaymentEventType> paymentEvents) {
		this.paymentEvents = paymentEvents;
	}
	
	/**
	 * @return the newCardInfo
	 */
	public CreditCardVO getNewCardInfo() {
		return newCardInfo;
	}

	/**
	 * @param newCardInfo the newCardInfo to set
	 */
	public void setNewCardInfo(CreditCardVO newCardInfo) {
		this.newCardInfo = newCardInfo;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(installDate != null) {
			sb.append("Install Date: "+installDate.toString());
		}
		if(startTime != null) {
			sb.append("\nStart Time: "+startTime.toString());
		}
		if(endTime != null) {
			sb.append("\nEnd Time: "+endTime.toString());
		} 
		
		for(PaymentEventType event : this.getPaymentEvents())  {
			sb.append("\nPayment Event: "+event.getEvent().name());
		}
		
		return sb.toString();
	}
}
