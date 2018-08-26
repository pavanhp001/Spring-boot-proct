package com.A.V.beans.entity;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.A.V.interfaces.CommonBeanInterface;

@Entity
@Table( name = "orderSource" )
public class OrderSource implements CommonBeanInterface{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8717674349637330578L;

	@Id
    @GeneratedValue( generator = "orderSourceSequence" )
    @SequenceGenerator( name = "orderSourceSequence", sequenceName = "ORDER_SOURCE_SEQ" )
    private long id;

	private String name;
	
	private String externalId;
	
	@ManyToOne
	private SalesProgram program;
	
	@ManyToOne
	private BusinessParty businessParty;
	
	@ManyToOne
	private SalesChannel salesChannel;
	
	private String imageUrl;
	
	@OneToMany
	@JoinTable(
    name="OrderSource_Telephony",
    joinColumns = @JoinColumn( name="ordersource_id"),
    inverseJoinColumns = @JoinColumn( name="telephony_id")
	)
	private List<Telephony> telephonyList;
	
	private Boolean expired;
	private Calendar expirationDate;
	private Boolean reportingOnlyFlag;
	private String concertCallbackNum;
	private String coBrandName;
	private String emailCallbackNum;
	
	@Column(name="IS_DEFAULT")
	private boolean isDefault;
	
	@Column(name = "is_property")
	private Boolean property;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public SalesProgram getProgram() {
		return program;
	}

	public void setProgram(SalesProgram program) {
		this.program = program;
	}

	public BusinessParty getBusinessParty() {
		return businessParty;
	}

	public void setBusinessParty(BusinessParty businessParty) {
		this.businessParty = businessParty;
	}

	public SalesChannel getSalesChannel() {
		return salesChannel;
	}

	public void setSalesChannel(SalesChannel salesChannel) {
		this.salesChannel = salesChannel;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<Telephony> getTelephonyList() {
		return telephonyList;
	}

	public void setTelephonyList(List<Telephony> telephonyList) {
		this.telephonyList = telephonyList;
	}

	public Boolean getExpired() {
		return expired;
	}

	public void setExpired(Boolean expired) {
		this.expired = expired;
	}

	public Calendar getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Calendar expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Boolean getReportingOnlyFlag() {
		return reportingOnlyFlag;
	}

	public void setReportingOnlyFlag(Boolean reportingOnlyFlag) {
		this.reportingOnlyFlag = reportingOnlyFlag;
	}
	
	public boolean isExpired() {
		boolean result = false;
		Calendar now = Calendar.getInstance();
		result = expired || (expirationDate != null && expirationDate.compareTo(now) <= 0 );
		return result;
	}

	public Boolean isProperty() {
		return property;
	}

	public void setProperty(Boolean property) {
		this.property = property;
	}

	public String getConcertCallbackNum() {
		return concertCallbackNum;
	}

	public void setConcertCallbackNum(String concertCallbackNum) {
		this.concertCallbackNum = concertCallbackNum;
	}

	public String getCoBrandName() {
		return coBrandName;
	}

	public void setCoBrandName(String coBrandName) {
		this.coBrandName = coBrandName;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	public boolean getIsDefault() {
		return isDefault;
	}
	
	public String getEmailCallbackNum() {
		return emailCallbackNum;
	}

	public void setEmailCallbackNum(String emailCallbackNum) {
		this.emailCallbackNum = emailCallbackNum;
	}
}
