package com.A.V.beans.entity;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.IndexColumn;

import com.A.V.beans.LineItemPriceInfo;
import com.A.V.beans.LineitemScheduleInfo;
import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author ebaugh
 *
 */

@Entity
@Table(name = "OM_LINE_ITEM")
public class LineItem implements CommonBeanInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2417142652663660081L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "lineItemBeanSequence")
	@SequenceGenerator(name = "lineItemBeanSequence", sequenceName = "OM_LINE_ITEM_BEAN_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "EXTERNAL_ID")
	private Long externalId;

	@Column(name = "LINE_ITEM_NUMBER")
	private int lineItemNumber;

	@Transient
	private BusinessParty provider;

	@Column(name = "PROVIDER_CONFIRM_NUM")
	private String providerConfirmationNumber;

	@Column(name = "PROVIDER_EXT_ID")
	private String providerExternalId;

	@Column(name = "PROVIDER_CUST_ACCT_NUM")
	private String providerCustomerAccountNumber;

	@Column(name = "SVC_ADDRESS_EXT_ID")
	private String serviceAddressExtId;

	private transient String srviceAddressRef;

	@Column(name = "ADDR_OCCUPANCY_DURATION")
	private String howLongAtAddress;

	@Column(name = "PARTNER_EXT_ID")
	private String partnerExternalId;

	@Column(name = "LI_OWNER_EXT_ID")
	private String lineItemOwnerExtId;



	@Column(name = "BILLING_INFO_EXT_ID")
	private String billingInfoExtId;

	@Column(name = "PRICE")
	private LineItemPriceInfo price;

	@OneToOne
	@ForeignKey(name = "FK_LINE_ITEM_CURR_STATUS")
	private StatusRecordBean currentStatus;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "OM_LINE_ITEM_OM_STAT_REC", joinColumns = @JoinColumn(name = "OM_LINE_ITEM_ID"))
	@IndexColumn(name = "listOrder", base = 0)
	private List<StatusRecordBean> historicStatus;

	@OneToOne
	@ForeignKey(name = "FK_LINEITEM_LINEITEM_DTL")
	@JoinColumn(name = "LINEITEM_DTL_ID")
	private LineItemDetail lineItemDetail;

	//@ElementCollection(fetch = FetchType.LAZY)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinTable(name = "OM_LINE_ITEM_OM_SEL_FEATURE", joinColumns = @JoinColumn(name = "OM_LINE_ITEM_ID"))
	//@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Set<SelectedFeatureValue> selectedFeatureValues;

	//@ElementCollection(fetch = FetchType.LAZY)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinTable(name = "OM_LINE_ITEM_OM_SEL_DIALOGUE", joinColumns = @JoinColumn(name = "OM_LINE_ITEM_ID"))
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Set<SelectedDialogue> dialogues;

	//@ElementCollection(fetch = FetchType.LAZY)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinTable(name = "OM_LINE_ITEM_OM_CUST_SELECTION", joinColumns = @JoinColumn(name = "OM_LINE_ITEM_ID"))
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Set<CustomSelection> selections;
	
	//@OneToMany
	//@JoinColumn(name = "LINEITEM_ID")
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinTable(name = "OM_LINE_ITEM_CM_PAYEVENT", joinColumns = @JoinColumn(name = "OM_LINE_ITEM_ID"))
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Set<CustomerPaymentEvent> paymentEvents;

	private LineitemScheduleInfo lineitemScheduleInfo;

	@Column(name = "LEAD_ID")
	private Long leadId;

	@Column(name = "PRODUCT_DATASOURCE")
	private String productDatasource;

	@Column(name = "NEW_PHONE")
	private String newPhone;

	@Column(name = "IS_NEW")
	private int isNew;

	@Column(name = "LI_STATE")
	private int state;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "OM_LINE_ITEM_OM_LI_ATTRIBUTE", joinColumns = @JoinColumn(name = "OM_LINE_ITEM_ID"))
	private Set<LineItemAttribute> lineItemAttribute;

	@Column(name = "SERVICE_TYPE")
	private String serviceType;

	@Column(name = "CREDIT_STATUS")
	private String creditStatus;

	@Column(name = "LI_CREATE_DATE")
	private Calendar lineItemCreationDate;

	@Column(name = "IS_EVENT_SELECTED")
	private Boolean isEventSelected;

	@Column(name = "IS_EVENT_COMPLETED")
	private Boolean isEventCompleted;

	@Column(name ="EVENT_TYPE")
	private int eventType;

	@Column(name ="IS_TRANSFER")
	private int isTransfer;


	/*@OneToOne
	@ForeignKey(name = "FK_LI_ACCNT_HOLDER")
	@JoinColumn(name = "ACCT_HOLDER_ID")
	@Transient
	private AccountHolder accountHolder;
	*/
	
	@Column(name = "ACCT_HOLDER_ID")
	private Long accountHolderExternalId;
	
	@Transient
	private Set<ReasonBean> reasons = new HashSet<ReasonBean>();
	
	@Transient
	private Set<CoachingBean> coachings = new HashSet<CoachingBean>();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final long getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setId(final long id) {
		this.id = id;
	}

	public Long getExternalId() {
		return externalId;
	}

	public void setExternalId(Long externalId) {
		this.externalId = externalId;
	}

	public BusinessParty getProvider() {
		return provider;
	}

	public void setProvider(final BusinessParty provider) {
		this.provider = provider;

		if (this.provider != null) {
			this.setProviderExternalId(provider.getExternalId());
		}
	}

	public String getProviderConfirmationNumber() {
		return providerConfirmationNumber;
	}

	public void setProviderConfirmationNumber(
			final String providerConfirmationNumber) {
		this.providerConfirmationNumber = providerConfirmationNumber;
	}

	public String getProviderCustomerAccountNumber() {
		return providerCustomerAccountNumber;
	}

	public void setProviderCustomerAccountNumber(
			final String providerCustomerAccountNumber) {
		this.providerCustomerAccountNumber = providerCustomerAccountNumber;
	}

	/*
	 * public final BillingInformation getBilling() { return billing; }
	 *
	 * public final void setBilling(final BillingInformation billing) {
	 * this.billing = billing; }
	 */

	public String getBillingInfoExtId() {
		return billingInfoExtId;
	}

	public void setBillingInfoExtId(String billingInfoExtId) {
		this.billingInfoExtId = billingInfoExtId;
	}

	/*
	 * public final MarketItemBean getItem() { return marketItemBean; } public
	 * final void setItem(final MarketItemBean marketItemBean) {
	 * this.marketItemBean = marketItemBean; }
	 */

	public LineItemPriceInfo getPrice() {
		return price;
	}

	public void setPrice(LineItemPriceInfo price) {
		this.price = price;
	}

	/*
	 * public final AddressBean getServiceOrDeliveryAddress() { return
	 * serviceOrDeliveryAddress; }
	 *
	 * public final void setServiceOrDeliveryAddress( final AddressBean
	 * serviceOrDeliveryAddress ) { this.serviceOrDeliveryAddress =
	 * serviceOrDeliveryAddress; }
	 */

	public String getServiceAddressExtId() {
		return serviceAddressExtId;
	}

	public void setServiceAddressExtId(String serviceAddressExtId) {
		this.serviceAddressExtId = serviceAddressExtId;
	}

	public String getSrviceAddressRef() {
		return srviceAddressRef;
	}

	public void setSrviceAddressRef(String srviceAddressRef) {
		this.srviceAddressRef = srviceAddressRef;
	}

	public String getHowLongAtAddress() {
		return howLongAtAddress;
	}

	public void setHowLongAtAddress(final String howLongAtAddress) {
		this.howLongAtAddress = howLongAtAddress;
	}

	public StatusRecordBean getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(final StatusRecordBean currentStatus) {
		this.currentStatus = currentStatus;
	}

	public List<StatusRecordBean> getHistoricStatus() {
		return historicStatus;
	}

	public void setHistoricStatus(final List<StatusRecordBean> historicStatus) {
		this.historicStatus = historicStatus;
	}

	public LineItemDetail getLineItemDetailBean() {
		return lineItemDetail;
	}

	public void setLineItemDetailBean(LineItemDetail lineItemDetail) {
		this.lineItemDetail = lineItemDetail;
	}

	public Set<SelectedFeatureValue> getSelectedFeatureValues() {
		return selectedFeatureValues;
	}

	public void setSelectedFeatureValues(
			Set<SelectedFeatureValue> selectedFeatureValues) {
		this.selectedFeatureValues = selectedFeatureValues;
	}

	public int getLineItemNumber() {
		return lineItemNumber;
	}

	public void setLineItemNumber(int lineItemNumber) {
		this.lineItemNumber = lineItemNumber;
	}

	public void setLineItemNumber(Integer lineItemNumber) {
		this.lineItemNumber = lineItemNumber;
	}

	public LineitemScheduleInfo getLineitemScheduleInfo() {
		return lineitemScheduleInfo;
	}

	public void setLineitemScheduleInfo(
			LineitemScheduleInfo lineitemScheduleInfo) {
		this.lineitemScheduleInfo = lineitemScheduleInfo;
	}

	public String getProviderExternalId() {
		return providerExternalId;
	}

	public void setProviderExternalId(String providerExternalId) {
		this.providerExternalId = providerExternalId;
	}

	public LineItemDetail getLineItemDetail() {
		return lineItemDetail;
	}

	public void setLineItemDetail(LineItemDetail lineItemDetail) {
		this.lineItemDetail = lineItemDetail;
	}

	public Long getLeadId() {
		return leadId;
	}

	public void setLeadId(Long leadId) {
		this.leadId = leadId;
	}

	public String getProductDatasource() {
		return productDatasource;
	}

	public void setProductDatasource(String productDatasource) {
		this.productDatasource = productDatasource;
	}

	public Set<SelectedDialogue> getDialogues() {
		return dialogues;
	}

	public void setDialogues(Set<SelectedDialogue> dialogues) {
		this.dialogues = dialogues;
	}

	public Set<CustomSelection> getSelections() {
		return selections;
	}

	public void setSelections(Set<CustomSelection> selections) {
	    this.selections = selections;
	}

	public String getNewPhone() {
		return newPhone;
	}

	public void setNewPhone(String newPhone) {
		this.newPhone = newPhone;
	}

	public Set<LineItemAttribute> getLineItemAttribute() {
		return lineItemAttribute;
	}

	public void setLineItemAttribute(Set<LineItemAttribute> lineItemAttribute) {
		this.lineItemAttribute = lineItemAttribute;
	}

	public int getIsNew() {
		return isNew;
	}

	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getCreditStatus() {
		return creditStatus;
	}

	public void setCreditStatus(String creditStatus) {
		this.creditStatus = creditStatus;
	}

	public String getPartnerExternalId() {
		return partnerExternalId;
	}

	public void setPartnerExternalId(String partnerExternalId) {
		this.partnerExternalId = partnerExternalId;
	}

	public String getLineItemOwnerExtId() {
		return lineItemOwnerExtId;
	}

	public void setLineItemOwnerExtId(String lineItemOwnerExtId) {
		this.lineItemOwnerExtId = lineItemOwnerExtId;
	}

	public Calendar getLineItemCreationDate() {
		return lineItemCreationDate;
	}

	public void setLineItemCreationDate(Calendar lineItemCreationDate) {
		if(lineItemCreationDate == null){
			this.lineItemCreationDate  = Calendar.getInstance();
		}else{
			this.lineItemCreationDate = lineItemCreationDate;
		}
	}

	public int getEventType() {
	    return eventType;
	}

	public void setEventType(int eventType) {
	    this.eventType = eventType;
	}

	public int getIsTransfer() {
		return isTransfer;
	}

	public void setIsTransfer(int isTransfer) {
		this.isTransfer = isTransfer;
	}

	public Boolean getIsEventSelected() {
	    return isEventSelected;
	}

	public void setIsEventSelected(Boolean isEventSelected) {
	    this.isEventSelected = isEventSelected;
	}

	public Boolean getIsEventCompleted() {
	    return isEventCompleted;
	}

	public void setIsEventCompleted(Boolean isEventCompleted) {
	    this.isEventCompleted = isEventCompleted;
	}

	public Long getAccountHolderExternalId() {
		return accountHolderExternalId;
	}

	public void setAccountHolderExternalId(Long accountHolderExternalId) {
		this.accountHolderExternalId = accountHolderExternalId;
	}

	public Set<CustomerPaymentEvent> getPaymentEvents() {
		if(paymentEvents == null) {
			paymentEvents = new HashSet<CustomerPaymentEvent>();
		}
		return paymentEvents;
	}

	public void setPaymentEvents(Set<CustomerPaymentEvent> paymentEvents) {
		this.paymentEvents = paymentEvents;
	}
	
	public Set<ReasonBean> getReasons() {
		return reasons;
	}

	public void setReasons(Set<ReasonBean> reasons) {
		this.reasons = reasons;
	}

	public Set<CoachingBean> getCoachings() {
		return coachings;
	}

	public void setCoachings(Set<CoachingBean> coachings) {
		this.coachings = coachings;
	}
}
