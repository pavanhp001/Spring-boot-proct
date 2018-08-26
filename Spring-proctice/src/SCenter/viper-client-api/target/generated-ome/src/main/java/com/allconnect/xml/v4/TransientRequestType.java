
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 Data used to process an order but not part of an
 *                 order. Request.
 *             
 * 
 * <p>Java class for transientRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="transientRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="providerRequestContainer" type="{http://xml.A.com/v4}ProviderRequestContainer" minOccurs="0"/>
 *         &lt;element name="action" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lineItemNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="requestedPhone" type="{http://xml.A.com/v4}valuelist" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="promotionId" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="tvSelect" type="{http://xml.A.com/v4}TvSelect" minOccurs="0"/>
 *         &lt;element name="securityInfo" type="{http://xml.A.com/v4}SecurityInfo" minOccurs="0"/>
 *         &lt;element name="availableConnections" type="{http://xml.A.com/v4}AvailableConnections" minOccurs="0"/>
 *         &lt;element name="workOrderNote" type="{http://xml.A.com/v4}WorkOrderNote" minOccurs="0"/>
 *         &lt;element name="accountNote" type="{http://xml.A.com/v4}AccountNote" minOccurs="0"/>
 *         &lt;element name="billLanguage" type="{http://xml.A.com/v4}BillLanguage" minOccurs="0"/>
 *         &lt;element name="webClaimId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sellerId" type="{http://xml.A.com/v4}SellerId" minOccurs="0"/>
 *         &lt;element name="billingCycleDay" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="dISHCinemaSelected" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="conversationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="completeCreditCheck" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="NewsletterSelected" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="scheduleDate" type="{http://xml.A.com/v4}ScheduleDateType" minOccurs="0"/>
 *         &lt;element name="customerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="termsAndConditions" type="{http://xml.A.com/v4}TermsAndConditionsType" minOccurs="0"/>
 *         &lt;element name="dishEquipmentSolutionSelectionName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dishPromotionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dishProgrammingValidFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dishConfigurationName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tvConstraints" type="{http://xml.A.com/v4}TvConstraints" minOccurs="0"/>
 *         &lt;element name="receiversSolution" type="{http://xml.A.com/v4}ReceiversSolution" maxOccurs="unbounded"/>
 *         &lt;element name="creditQualificationFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="leadId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="promotionInfo" type="{http://xml.A.com/v4}PromoDescription" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="energyProduct" type="{http://xml.A.com/v4}EnergyProduct" minOccurs="0"/>
 *         &lt;element name="lineItems" type="{http://xml.A.com/v4}lineItemCollectionType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transientRequestType", propOrder = {
    "providerRequestContainer",
    "action",
    "lineItemNumber",
    "requestedPhone",
    "promotionId",
    "tvSelect",
    "securityInfo",
    "availableConnections",
    "workOrderNote",
    "accountNote",
    "billLanguage",
    "webClaimId",
    "sellerId",
    "billingCycleDay",
    "dishCinemaSelected",
    "conversationId",
    "completeCreditCheck",
    "newsletterSelected",
    "scheduleDate",
    "customerCode",
    "termsAndConditions",
    "dishEquipmentSolutionSelectionName",
    "dishPromotionId",
    "dishProgrammingValidFlag",
    "dishConfigurationName",
    "tvConstraints",
    "receiversSolution",
    "creditQualificationFlag",
    "leadId",
    "promotionInfo",
    "energyProduct",
    "lineItems"
})
public class TransientRequestType {

    protected ProviderRequestContainer providerRequestContainer;
    protected String action;
    protected Integer lineItemNumber;
    @XmlElementRef(name = "requestedPhone", namespace = "http://xml.A.com/v4", type = JAXBElement.class)
    protected List<JAXBElement<List<String>>> requestedPhone;
    protected List<String> promotionId;
    protected TvSelect tvSelect;
    protected SecurityInfo securityInfo;
    protected AvailableConnections availableConnections;
    protected String workOrderNote;
    protected String accountNote;
    protected String billLanguage;
    protected String webClaimId;
    protected String sellerId;
    protected Integer billingCycleDay;
    @XmlElement(name = "dISHCinemaSelected")
    protected Boolean dishCinemaSelected;
    protected String conversationId;
    protected Boolean completeCreditCheck;
    @XmlElementRef(name = "NewsletterSelected", namespace = "http://xml.A.com/v4", type = JAXBElement.class)
    protected JAXBElement<Boolean> newsletterSelected;
    protected ScheduleDateType scheduleDate;
    protected String customerCode;
    protected TermsAndConditionsType2 termsAndConditions;
    protected String dishEquipmentSolutionSelectionName;
    protected String dishPromotionId;
    protected String dishProgrammingValidFlag;
    protected String dishConfigurationName;
    protected TvConstraints tvConstraints;
    @XmlElement(required = true)
    protected List<ReceiversSolution> receiversSolution;
    protected Boolean creditQualificationFlag;
    protected String leadId;
    protected List<PromoDescription> promotionInfo;
    protected EnergyProduct energyProduct;
    @XmlElement(required = true)
    protected LineItemCollectionType lineItems;

    /**
     * Gets the value of the providerRequestContainer property.
     * 
     * @return
     *     possible object is
     *     {@link ProviderRequestContainer }
     *     
     */
    public ProviderRequestContainer getProviderRequestContainer() {
        return providerRequestContainer;
    }

    /**
     * Sets the value of the providerRequestContainer property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProviderRequestContainer }
     *     
     */
    public void setProviderRequestContainer(ProviderRequestContainer value) {
        this.providerRequestContainer = value;
    }

    /**
     * Gets the value of the action property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAction(String value) {
        this.action = value;
    }

    /**
     * Gets the value of the lineItemNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLineItemNumber() {
        return lineItemNumber;
    }

    /**
     * Sets the value of the lineItemNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLineItemNumber(Integer value) {
        this.lineItemNumber = value;
    }

    /**
     * Gets the value of the requestedPhone property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requestedPhone property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequestedPhone().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link List }{@code <}{@link String }{@code >}{@code >}
     * 
     * 
     */
    public List<JAXBElement<List<String>>> getRequestedPhone() {
        if (requestedPhone == null) {
            requestedPhone = new ArrayList<JAXBElement<List<String>>>();
        }
        return this.requestedPhone;
    }

    /**
     * Gets the value of the promotionId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the promotionId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPromotionId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPromotionId() {
        if (promotionId == null) {
            promotionId = new ArrayList<String>();
        }
        return this.promotionId;
    }

    /**
     * Gets the value of the tvSelect property.
     * 
     * @return
     *     possible object is
     *     {@link TvSelect }
     *     
     */
    public TvSelect getTvSelect() {
        return tvSelect;
    }

    /**
     * Sets the value of the tvSelect property.
     * 
     * @param value
     *     allowed object is
     *     {@link TvSelect }
     *     
     */
    public void setTvSelect(TvSelect value) {
        this.tvSelect = value;
    }

    /**
     * Gets the value of the securityInfo property.
     * 
     * @return
     *     possible object is
     *     {@link SecurityInfo }
     *     
     */
    public SecurityInfo getSecurityInfo() {
        return securityInfo;
    }

    /**
     * Sets the value of the securityInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecurityInfo }
     *     
     */
    public void setSecurityInfo(SecurityInfo value) {
        this.securityInfo = value;
    }

    /**
     * Gets the value of the availableConnections property.
     * 
     * @return
     *     possible object is
     *     {@link AvailableConnections }
     *     
     */
    public AvailableConnections getAvailableConnections() {
        return availableConnections;
    }

    /**
     * Sets the value of the availableConnections property.
     * 
     * @param value
     *     allowed object is
     *     {@link AvailableConnections }
     *     
     */
    public void setAvailableConnections(AvailableConnections value) {
        this.availableConnections = value;
    }

    /**
     * Gets the value of the workOrderNote property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkOrderNote() {
        return workOrderNote;
    }

    /**
     * Sets the value of the workOrderNote property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkOrderNote(String value) {
        this.workOrderNote = value;
    }

    /**
     * Gets the value of the accountNote property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountNote() {
        return accountNote;
    }

    /**
     * Sets the value of the accountNote property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountNote(String value) {
        this.accountNote = value;
    }

    /**
     * Gets the value of the billLanguage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillLanguage() {
        return billLanguage;
    }

    /**
     * Sets the value of the billLanguage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillLanguage(String value) {
        this.billLanguage = value;
    }

    /**
     * Gets the value of the webClaimId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWebClaimId() {
        return webClaimId;
    }

    /**
     * Sets the value of the webClaimId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebClaimId(String value) {
        this.webClaimId = value;
    }

    /**
     * Gets the value of the sellerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSellerId() {
        return sellerId;
    }

    /**
     * Sets the value of the sellerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSellerId(String value) {
        this.sellerId = value;
    }

    /**
     * Gets the value of the billingCycleDay property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBillingCycleDay() {
        return billingCycleDay;
    }

    /**
     * Sets the value of the billingCycleDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBillingCycleDay(Integer value) {
        this.billingCycleDay = value;
    }

    /**
     * Gets the value of the dishCinemaSelected property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDISHCinemaSelected() {
        return dishCinemaSelected;
    }

    /**
     * Sets the value of the dishCinemaSelected property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDISHCinemaSelected(Boolean value) {
        this.dishCinemaSelected = value;
    }

    /**
     * Gets the value of the conversationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConversationId() {
        return conversationId;
    }

    /**
     * Sets the value of the conversationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConversationId(String value) {
        this.conversationId = value;
    }

    /**
     * Gets the value of the completeCreditCheck property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCompleteCreditCheck() {
        return completeCreditCheck;
    }

    /**
     * Sets the value of the completeCreditCheck property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCompleteCreditCheck(Boolean value) {
        this.completeCreditCheck = value;
    }

    /**
     * Gets the value of the newsletterSelected property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getNewsletterSelected() {
        return newsletterSelected;
    }

    /**
     * Sets the value of the newsletterSelected property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setNewsletterSelected(JAXBElement<Boolean> value) {
        this.newsletterSelected = ((JAXBElement<Boolean> ) value);
    }

    /**
     * Gets the value of the scheduleDate property.
     * 
     * @return
     *     possible object is
     *     {@link ScheduleDateType }
     *     
     */
    public ScheduleDateType getScheduleDate() {
        return scheduleDate;
    }

    /**
     * Sets the value of the scheduleDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScheduleDateType }
     *     
     */
    public void setScheduleDate(ScheduleDateType value) {
        this.scheduleDate = value;
    }

    /**
     * Gets the value of the customerCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerCode() {
        return customerCode;
    }

    /**
     * Sets the value of the customerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerCode(String value) {
        this.customerCode = value;
    }

    /**
     * Gets the value of the termsAndConditions property.
     * 
     * @return
     *     possible object is
     *     {@link TermsAndConditionsType2 }
     *     
     */
    public TermsAndConditionsType2 getTermsAndConditions() {
        return termsAndConditions;
    }

    /**
     * Sets the value of the termsAndConditions property.
     * 
     * @param value
     *     allowed object is
     *     {@link TermsAndConditionsType2 }
     *     
     */
    public void setTermsAndConditions(TermsAndConditionsType2 value) {
        this.termsAndConditions = value;
    }

    /**
     * Gets the value of the dishEquipmentSolutionSelectionName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDishEquipmentSolutionSelectionName() {
        return dishEquipmentSolutionSelectionName;
    }

    /**
     * Sets the value of the dishEquipmentSolutionSelectionName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDishEquipmentSolutionSelectionName(String value) {
        this.dishEquipmentSolutionSelectionName = value;
    }

    /**
     * Gets the value of the dishPromotionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDishPromotionId() {
        return dishPromotionId;
    }

    /**
     * Sets the value of the dishPromotionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDishPromotionId(String value) {
        this.dishPromotionId = value;
    }

    /**
     * Gets the value of the dishProgrammingValidFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDishProgrammingValidFlag() {
        return dishProgrammingValidFlag;
    }

    /**
     * Sets the value of the dishProgrammingValidFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDishProgrammingValidFlag(String value) {
        this.dishProgrammingValidFlag = value;
    }

    /**
     * Gets the value of the dishConfigurationName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDishConfigurationName() {
        return dishConfigurationName;
    }

    /**
     * Sets the value of the dishConfigurationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDishConfigurationName(String value) {
        this.dishConfigurationName = value;
    }

    /**
     * Gets the value of the tvConstraints property.
     * 
     * @return
     *     possible object is
     *     {@link TvConstraints }
     *     
     */
    public TvConstraints getTvConstraints() {
        return tvConstraints;
    }

    /**
     * Sets the value of the tvConstraints property.
     * 
     * @param value
     *     allowed object is
     *     {@link TvConstraints }
     *     
     */
    public void setTvConstraints(TvConstraints value) {
        this.tvConstraints = value;
    }

    /**
     * Gets the value of the receiversSolution property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the receiversSolution property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReceiversSolution().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReceiversSolution }
     * 
     * 
     */
    public List<ReceiversSolution> getReceiversSolution() {
        if (receiversSolution == null) {
            receiversSolution = new ArrayList<ReceiversSolution>();
        }
        return this.receiversSolution;
    }

    /**
     * Gets the value of the creditQualificationFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCreditQualificationFlag() {
        return creditQualificationFlag;
    }

    /**
     * Sets the value of the creditQualificationFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCreditQualificationFlag(Boolean value) {
        this.creditQualificationFlag = value;
    }

    /**
     * Gets the value of the leadId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLeadId() {
        return leadId;
    }

    /**
     * Sets the value of the leadId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLeadId(String value) {
        this.leadId = value;
    }

    /**
     * Gets the value of the promotionInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the promotionInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPromotionInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PromoDescription }
     * 
     * 
     */
    public List<PromoDescription> getPromotionInfo() {
        if (promotionInfo == null) {
            promotionInfo = new ArrayList<PromoDescription>();
        }
        return this.promotionInfo;
    }

    /**
     * Gets the value of the energyProduct property.
     * 
     * @return
     *     possible object is
     *     {@link EnergyProduct }
     *     
     */
    public EnergyProduct getEnergyProduct() {
        return energyProduct;
    }

    /**
     * Sets the value of the energyProduct property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnergyProduct }
     *     
     */
    public void setEnergyProduct(EnergyProduct value) {
        this.energyProduct = value;
    }

    /**
     * Gets the value of the lineItems property.
     * 
     * @return
     *     possible object is
     *     {@link LineItemCollectionType }
     *     
     */
    public LineItemCollectionType getLineItems() {
        return lineItems;
    }

    /**
     * Sets the value of the lineItems property.
     * 
     * @param value
     *     allowed object is
     *     {@link LineItemCollectionType }
     *     
     */
    public void setLineItems(LineItemCollectionType value) {
        this.lineItems = value;
    }

}
