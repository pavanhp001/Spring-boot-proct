
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for lineItemType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="lineItemType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element ref="{http://xml.A.com/v4}customer" minOccurs="0"/>
 *         &lt;element name="partnerName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="partnerExternalId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="productDatasource" type="{http://xml.A.com/v4}providerSourceType"/>
 *         &lt;element name="providerConfirmationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="providerCustomerAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reasons" type="{http://xml.A.com/v4}reasons"/>
 *         &lt;element name="coachings" type="{http://xml.A.com/v4}coachings"/>
 *         &lt;element name="lineItemNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="lineItemCreateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="lineItemStatus" type="{http://xml.A.com/v4}lineItemStatusType"/>
 *         &lt;element name="lineItemStatusHistory" type="{http://xml.A.com/v4}lineItemStatusHistoryType"/>
 *         &lt;element name="lineItemDetail" type="{http://xml.A.com/v4}lineItemDetailType"/>
 *         &lt;element name="lineItemPriceInfo" type="{http://xml.A.com/v4}lineItemPriceInfoType"/>
 *         &lt;element name="externalId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="svcAddressExtId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="svcAddressRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billingInfoExtId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billingInfoRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="newPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="eventType" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="isTransfer" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="isEventSelected" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="isEventCompleted" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="isNew" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="SchedulingInfo" type="{http://xml.A.com/v4}SchedulingInfoType" minOccurs="0"/>
 *         &lt;element name="selectedFeatures" type="{http://xml.A.com/v4}selectedFeaturesType"/>
 *         &lt;element name="activeDialogs" type="{http://xml.A.com/v4}selectedDialogsType"/>
 *         &lt;element name="customSelections" type="{http://xml.A.com/v4}customSelectionsType"/>
 *         &lt;element name="service" type="{http://xml.A.com/v4}serviceType" minOccurs="0"/>
 *         &lt;element name="creditStatus" type="{http://xml.A.com/v4}creditStatusType" minOccurs="0"/>
 *         &lt;element name="notificationEvents" type="{http://xml.A.com/v4}notificationEventCollectionType" minOccurs="0"/>
 *         &lt;element name="leadId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="lineItemAttributes" type="{http://xml.A.com/v4}lineItemAttributeType" minOccurs="0"/>
 *         &lt;element name="transientRequestContainer" type="{http://xml.A.com/v4}transientRequestContainerType" minOccurs="0"/>
 *         &lt;element name="transientResponseContainer" type="{http://xml.A.com/v4}transientResponseContainerType" minOccurs="0"/>
 *         &lt;element name="accountHolderExternalId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="payments" type="{http://xml.A.com/v4}paymentsType" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "lineItemType", propOrder = {

})
public class LineItemType {

    protected Customer customer;
    @XmlElement(required = true)
    protected String partnerName;
    @XmlElement(required = true)
    protected String partnerExternalId;
    @XmlElement(required = true)
    protected ProviderSourceType productDatasource;
    protected String providerConfirmationNumber;
    protected String providerCustomerAccountNumber;
    @XmlElement(required = true)
    protected Reasons reasons;
    @XmlElement(required = true)
    protected Coachings coachings;
    protected int lineItemNumber;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lineItemCreateDate;
    @XmlElement(required = true)
    protected LineItemStatusType lineItemStatus;
    @XmlElement(required = true)
    protected LineItemStatusHistoryType lineItemStatusHistory;
    @XmlElement(required = true)
    protected LineItemDetailType lineItemDetail;
    @XmlElement(required = true)
    protected LineItemPriceInfoType lineItemPriceInfo;
    protected long externalId;
    protected String svcAddressExtId;
    protected String svcAddressRef;
    protected String billingInfoExtId;
    protected String billingInfoRef;
    protected String newPhone;
    protected Integer eventType;
    protected Integer isTransfer;
    @XmlElement(defaultValue = "false")
    protected Boolean isEventSelected;
    @XmlElement(defaultValue = "false")
    protected Boolean isEventCompleted;
    @XmlElement(defaultValue = "true")
    protected Boolean isNew;
    @XmlElement(defaultValue = "0")
    protected Integer state;
    @XmlElement(name = "SchedulingInfo")
    protected SchedulingInfoType schedulingInfo;
    @XmlElement(required = true)
    protected SelectedFeaturesType selectedFeatures;
    @XmlElement(required = true)
    protected SelectedDialogsType activeDialogs;
    @XmlElement(required = true)
    protected CustomSelectionsType customSelections;
    protected ServiceType service;
    protected CreditStatusType creditStatus;
    protected NotificationEventCollectionType notificationEvents;
    protected Long leadId;
    protected LineItemAttributeType lineItemAttributes;
    protected TransientRequestContainerType transientRequestContainer;
    protected TransientResponseContainerType transientResponseContainer;
    protected Long accountHolderExternalId;
    protected PaymentsType payments;

    /**
     * Gets the value of the customer property.
     * 
     * @return
     *     possible object is
     *     {@link Customer }
     *     
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the value of the customer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Customer }
     *     
     */
    public void setCustomer(Customer value) {
        this.customer = value;
    }

    /**
     * Gets the value of the partnerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerName() {
        return partnerName;
    }

    /**
     * Sets the value of the partnerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnerName(String value) {
        this.partnerName = value;
    }

    /**
     * Gets the value of the partnerExternalId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerExternalId() {
        return partnerExternalId;
    }

    /**
     * Sets the value of the partnerExternalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnerExternalId(String value) {
        this.partnerExternalId = value;
    }

    /**
     * Gets the value of the productDatasource property.
     * 
     * @return
     *     possible object is
     *     {@link ProviderSourceType }
     *     
     */
    public ProviderSourceType getProductDatasource() {
        return productDatasource;
    }

    /**
     * Sets the value of the productDatasource property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProviderSourceType }
     *     
     */
    public void setProductDatasource(ProviderSourceType value) {
        this.productDatasource = value;
    }

    /**
     * Gets the value of the providerConfirmationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProviderConfirmationNumber() {
        return providerConfirmationNumber;
    }

    /**
     * Sets the value of the providerConfirmationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProviderConfirmationNumber(String value) {
        this.providerConfirmationNumber = value;
    }

    /**
     * Gets the value of the providerCustomerAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProviderCustomerAccountNumber() {
        return providerCustomerAccountNumber;
    }

    /**
     * Sets the value of the providerCustomerAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProviderCustomerAccountNumber(String value) {
        this.providerCustomerAccountNumber = value;
    }

    /**
     * Gets the value of the reasons property.
     * 
     * @return
     *     possible object is
     *     {@link Reasons }
     *     
     */
    public Reasons getReasons() {
        return reasons;
    }

    /**
     * Sets the value of the reasons property.
     * 
     * @param value
     *     allowed object is
     *     {@link Reasons }
     *     
     */
    public void setReasons(Reasons value) {
        this.reasons = value;
    }

    /**
     * Gets the value of the coachings property.
     * 
     * @return
     *     possible object is
     *     {@link Coachings }
     *     
     */
    public Coachings getCoachings() {
        return coachings;
    }

    /**
     * Sets the value of the coachings property.
     * 
     * @param value
     *     allowed object is
     *     {@link Coachings }
     *     
     */
    public void setCoachings(Coachings value) {
        this.coachings = value;
    }

    /**
     * Gets the value of the lineItemNumber property.
     * 
     */
    public int getLineItemNumber() {
        return lineItemNumber;
    }

    /**
     * Sets the value of the lineItemNumber property.
     * 
     */
    public void setLineItemNumber(int value) {
        this.lineItemNumber = value;
    }

    /**
     * Gets the value of the lineItemCreateDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLineItemCreateDate() {
        return lineItemCreateDate;
    }

    /**
     * Sets the value of the lineItemCreateDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLineItemCreateDate(XMLGregorianCalendar value) {
        this.lineItemCreateDate = value;
    }

    /**
     * Gets the value of the lineItemStatus property.
     * 
     * @return
     *     possible object is
     *     {@link LineItemStatusType }
     *     
     */
    public LineItemStatusType getLineItemStatus() {
        return lineItemStatus;
    }

    /**
     * Sets the value of the lineItemStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link LineItemStatusType }
     *     
     */
    public void setLineItemStatus(LineItemStatusType value) {
        this.lineItemStatus = value;
    }

    /**
     * Gets the value of the lineItemStatusHistory property.
     * 
     * @return
     *     possible object is
     *     {@link LineItemStatusHistoryType }
     *     
     */
    public LineItemStatusHistoryType getLineItemStatusHistory() {
        return lineItemStatusHistory;
    }

    /**
     * Sets the value of the lineItemStatusHistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link LineItemStatusHistoryType }
     *     
     */
    public void setLineItemStatusHistory(LineItemStatusHistoryType value) {
        this.lineItemStatusHistory = value;
    }

    /**
     * Gets the value of the lineItemDetail property.
     * 
     * @return
     *     possible object is
     *     {@link LineItemDetailType }
     *     
     */
    public LineItemDetailType getLineItemDetail() {
        return lineItemDetail;
    }

    /**
     * Sets the value of the lineItemDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link LineItemDetailType }
     *     
     */
    public void setLineItemDetail(LineItemDetailType value) {
        this.lineItemDetail = value;
    }

    /**
     * Gets the value of the lineItemPriceInfo property.
     * 
     * @return
     *     possible object is
     *     {@link LineItemPriceInfoType }
     *     
     */
    public LineItemPriceInfoType getLineItemPriceInfo() {
        return lineItemPriceInfo;
    }

    /**
     * Sets the value of the lineItemPriceInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link LineItemPriceInfoType }
     *     
     */
    public void setLineItemPriceInfo(LineItemPriceInfoType value) {
        this.lineItemPriceInfo = value;
    }

    /**
     * Gets the value of the externalId property.
     * 
     */
    public long getExternalId() {
        return externalId;
    }

    /**
     * Sets the value of the externalId property.
     * 
     */
    public void setExternalId(long value) {
        this.externalId = value;
    }

    /**
     * Gets the value of the svcAddressExtId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSvcAddressExtId() {
        return svcAddressExtId;
    }

    /**
     * Sets the value of the svcAddressExtId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSvcAddressExtId(String value) {
        this.svcAddressExtId = value;
    }

    /**
     * Gets the value of the svcAddressRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSvcAddressRef() {
        return svcAddressRef;
    }

    /**
     * Sets the value of the svcAddressRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSvcAddressRef(String value) {
        this.svcAddressRef = value;
    }

    /**
     * Gets the value of the billingInfoExtId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingInfoExtId() {
        return billingInfoExtId;
    }

    /**
     * Sets the value of the billingInfoExtId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingInfoExtId(String value) {
        this.billingInfoExtId = value;
    }

    /**
     * Gets the value of the billingInfoRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingInfoRef() {
        return billingInfoRef;
    }

    /**
     * Sets the value of the billingInfoRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingInfoRef(String value) {
        this.billingInfoRef = value;
    }

    /**
     * Gets the value of the newPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewPhone() {
        return newPhone;
    }

    /**
     * Sets the value of the newPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewPhone(String value) {
        this.newPhone = value;
    }

    /**
     * Gets the value of the eventType property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEventType() {
        return eventType;
    }

    /**
     * Sets the value of the eventType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEventType(Integer value) {
        this.eventType = value;
    }

    /**
     * Gets the value of the isTransfer property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIsTransfer() {
        return isTransfer;
    }

    /**
     * Sets the value of the isTransfer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIsTransfer(Integer value) {
        this.isTransfer = value;
    }

    /**
     * Gets the value of the isEventSelected property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsEventSelected() {
        return isEventSelected;
    }

    /**
     * Sets the value of the isEventSelected property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsEventSelected(Boolean value) {
        this.isEventSelected = value;
    }

    /**
     * Gets the value of the isEventCompleted property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsEventCompleted() {
        return isEventCompleted;
    }

    /**
     * Sets the value of the isEventCompleted property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsEventCompleted(Boolean value) {
        this.isEventCompleted = value;
    }

    /**
     * Gets the value of the isNew property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsNew() {
        return isNew;
    }

    /**
     * Sets the value of the isNew property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsNew(Boolean value) {
        this.isNew = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setState(Integer value) {
        this.state = value;
    }

    /**
     * Gets the value of the schedulingInfo property.
     * 
     * @return
     *     possible object is
     *     {@link SchedulingInfoType }
     *     
     */
    public SchedulingInfoType getSchedulingInfo() {
        return schedulingInfo;
    }

    /**
     * Sets the value of the schedulingInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SchedulingInfoType }
     *     
     */
    public void setSchedulingInfo(SchedulingInfoType value) {
        this.schedulingInfo = value;
    }

    /**
     * Gets the value of the selectedFeatures property.
     * 
     * @return
     *     possible object is
     *     {@link SelectedFeaturesType }
     *     
     */
    public SelectedFeaturesType getSelectedFeatures() {
        return selectedFeatures;
    }

    /**
     * Sets the value of the selectedFeatures property.
     * 
     * @param value
     *     allowed object is
     *     {@link SelectedFeaturesType }
     *     
     */
    public void setSelectedFeatures(SelectedFeaturesType value) {
        this.selectedFeatures = value;
    }

    /**
     * Gets the value of the activeDialogs property.
     * 
     * @return
     *     possible object is
     *     {@link SelectedDialogsType }
     *     
     */
    public SelectedDialogsType getActiveDialogs() {
        return activeDialogs;
    }

    /**
     * Sets the value of the activeDialogs property.
     * 
     * @param value
     *     allowed object is
     *     {@link SelectedDialogsType }
     *     
     */
    public void setActiveDialogs(SelectedDialogsType value) {
        this.activeDialogs = value;
    }

    /**
     * Gets the value of the customSelections property.
     * 
     * @return
     *     possible object is
     *     {@link CustomSelectionsType }
     *     
     */
    public CustomSelectionsType getCustomSelections() {
        return customSelections;
    }

    /**
     * Sets the value of the customSelections property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomSelectionsType }
     *     
     */
    public void setCustomSelections(CustomSelectionsType value) {
        this.customSelections = value;
    }

    /**
     * Gets the value of the service property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceType }
     *     
     */
    public ServiceType getService() {
        return service;
    }

    /**
     * Sets the value of the service property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceType }
     *     
     */
    public void setService(ServiceType value) {
        this.service = value;
    }

    /**
     * Gets the value of the creditStatus property.
     * 
     * @return
     *     possible object is
     *     {@link CreditStatusType }
     *     
     */
    public CreditStatusType getCreditStatus() {
        return creditStatus;
    }

    /**
     * Sets the value of the creditStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditStatusType }
     *     
     */
    public void setCreditStatus(CreditStatusType value) {
        this.creditStatus = value;
    }

    /**
     * Gets the value of the notificationEvents property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationEventCollectionType }
     *     
     */
    public NotificationEventCollectionType getNotificationEvents() {
        return notificationEvents;
    }

    /**
     * Sets the value of the notificationEvents property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationEventCollectionType }
     *     
     */
    public void setNotificationEvents(NotificationEventCollectionType value) {
        this.notificationEvents = value;
    }

    /**
     * Gets the value of the leadId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getLeadId() {
        return leadId;
    }

    /**
     * Sets the value of the leadId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setLeadId(Long value) {
        this.leadId = value;
    }

    /**
     * Gets the value of the lineItemAttributes property.
     * 
     * @return
     *     possible object is
     *     {@link LineItemAttributeType }
     *     
     */
    public LineItemAttributeType getLineItemAttributes() {
        return lineItemAttributes;
    }

    /**
     * Sets the value of the lineItemAttributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link LineItemAttributeType }
     *     
     */
    public void setLineItemAttributes(LineItemAttributeType value) {
        this.lineItemAttributes = value;
    }

    /**
     * Gets the value of the transientRequestContainer property.
     * 
     * @return
     *     possible object is
     *     {@link TransientRequestContainerType }
     *     
     */
    public TransientRequestContainerType getTransientRequestContainer() {
        return transientRequestContainer;
    }

    /**
     * Sets the value of the transientRequestContainer property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransientRequestContainerType }
     *     
     */
    public void setTransientRequestContainer(TransientRequestContainerType value) {
        this.transientRequestContainer = value;
    }

    /**
     * Gets the value of the transientResponseContainer property.
     * 
     * @return
     *     possible object is
     *     {@link TransientResponseContainerType }
     *     
     */
    public TransientResponseContainerType getTransientResponseContainer() {
        return transientResponseContainer;
    }

    /**
     * Sets the value of the transientResponseContainer property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransientResponseContainerType }
     *     
     */
    public void setTransientResponseContainer(TransientResponseContainerType value) {
        this.transientResponseContainer = value;
    }

    /**
     * Gets the value of the accountHolderExternalId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAccountHolderExternalId() {
        return accountHolderExternalId;
    }

    /**
     * Sets the value of the accountHolderExternalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAccountHolderExternalId(Long value) {
        this.accountHolderExternalId = value;
    }

    /**
     * Gets the value of the payments property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentsType }
     *     
     */
    public PaymentsType getPayments() {
        return payments;
    }

    /**
     * Sets the value of the payments property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentsType }
     *     
     */
    public void setPayments(PaymentsType value) {
        this.payments = value;
    }

}
