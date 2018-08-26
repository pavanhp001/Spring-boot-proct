
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
 *                 order. Response
 *             
 * 
 * <p>Java class for transientResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="transientResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="providerResponseContainer" type="{http://xml.A.com/v4}ProviderResponseContainer" minOccurs="0"/>
 *         &lt;element name="orderNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="suggestedPhone" type="{http://xml.A.com/v4}valuelist" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="confirmedPhone" type="{http://xml.A.com/v4}valuelist" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="product" type="{http://xml.A.com/v4}orderLineItemDetailTypeType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="qualificationFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="programmingValidFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="customerType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dishPromotionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="conversationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="productInfo" type="{http://xml.A.com/v4}ProductInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="tvConstraints" type="{http://xml.A.com/v4}TvConstraints" minOccurs="0"/>
 *         &lt;element name="receiver" type="{http://xml.A.com/v4}Receiver" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="priceInfo" type="{http://xml.A.com/v4}PriceInfo" minOccurs="0"/>
 *         &lt;element name="dISHConfiguration" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="creditQualResponse" type="{http://xml.A.com/v4}CreditQualResponseInfo" minOccurs="0"/>
 *         &lt;element name="orderQualResponse" type="{http://xml.A.com/v4}OrderQualResponseInfo" minOccurs="0"/>
 *         &lt;element name="submitOrderResponse" type="{http://xml.A.com/v4}SubmitOrderResponseInfo" minOccurs="0"/>
 *         &lt;element name="scheduleDate" type="{http://xml.A.com/v4}ScheduleDateType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="providerLineItemStatus" type="{http://xml.A.com/v4}providerLineItemStatusType"/>
 *         &lt;element name="termsAndConditions" type="{http://xml.A.com/v4}TermsAndConditionsType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="customerOrderInfo" type="{http://xml.A.com/v4}CustomerOrderInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="equipmentBundleInfo" type="{http://xml.A.com/v4}EquipmentBundleInfo" minOccurs="0"/>
 *         &lt;element name="receiversSolution" type="{http://xml.A.com/v4}ReceiversSolution" maxOccurs="unbounded"/>
 *         &lt;element name="equipmentSolutionInfo" type="{http://xml.A.com/v4}EquipmentSolutionInfo" maxOccurs="unbounded"/>
 *         &lt;element name="productBundleInfo" type="{http://xml.A.com/v4}ProductBundleInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="authenticateCustomerResponse" type="{http://xml.A.com/v4}AuthenticateCustomerResponseInfo" minOccurs="0"/>
 *         &lt;element name="rtimErrorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="alternatePromotions" type="{http://xml.A.com/v4}AlternatePromotion" minOccurs="0"/>
 *         &lt;element name="leadId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="promotionInfo" type="{http://xml.A.com/v4}PromoDescription" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="energyProduct" type="{http://xml.A.com/v4}EnergyProduct" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transientResponseType", propOrder = {
    "providerResponseContainer",
    "orderNumber",
    "suggestedPhone",
    "confirmedPhone",
    "product",
    "qualificationFlag",
    "programmingValidFlag",
    "customerType",
    "dishPromotionId",
    "conversationId",
    "productInfo",
    "tvConstraints",
    "receiver",
    "priceInfo",
    "dishConfiguration",
    "creditQualResponse",
    "orderQualResponse",
    "submitOrderResponse",
    "scheduleDate",
    "providerLineItemStatus",
    "termsAndConditions",
    "customerOrderInfo",
    "equipmentBundleInfo",
    "receiversSolution",
    "equipmentSolutionInfo",
    "productBundleInfo",
    "authenticateCustomerResponse",
    "rtimErrorCode",
    "alternatePromotions",
    "leadId",
    "promotionInfo",
    "energyProduct"
})
public class TransientResponseType {

    protected ProviderResponseContainer providerResponseContainer;
    protected String orderNumber;
    @XmlElementRef(name = "suggestedPhone", namespace = "http://xml.A.com/v4", type = JAXBElement.class)
    protected List<JAXBElement<List<String>>> suggestedPhone;
    @XmlElementRef(name = "confirmedPhone", namespace = "http://xml.A.com/v4", type = JAXBElement.class)
    protected List<JAXBElement<List<String>>> confirmedPhone;
    protected List<OrderLineItemDetailTypeType> product;
    protected Boolean qualificationFlag;
    protected Boolean programmingValidFlag;
    protected String customerType;
    protected String dishPromotionId;
    protected String conversationId;
    protected List<ProductInfo> productInfo;
    protected TvConstraints tvConstraints;
    protected List<Receiver> receiver;
    protected PriceInfo priceInfo;
    @XmlElement(name = "dISHConfiguration")
    protected String dishConfiguration;
    protected CreditQualResponseInfo creditQualResponse;
    protected OrderQualResponseInfo orderQualResponse;
    protected SubmitOrderResponseInfo submitOrderResponse;
    protected List<ScheduleDateType> scheduleDate;
    @XmlElement(required = true)
    protected ProviderLineItemStatusType providerLineItemStatus;
    protected List<TermsAndConditionsType2> termsAndConditions;
    protected List<CustomerOrderInfo> customerOrderInfo;
    protected EquipmentBundleInfo equipmentBundleInfo;
    @XmlElement(required = true)
    protected List<ReceiversSolution> receiversSolution;
    @XmlElement(required = true)
    protected List<EquipmentSolutionInfo> equipmentSolutionInfo;
    protected List<ProductBundleInfo> productBundleInfo;
    protected AuthenticateCustomerResponseInfo authenticateCustomerResponse;
    protected String rtimErrorCode;
    protected AlternatePromotion alternatePromotions;
    protected String leadId;
    protected List<PromoDescription> promotionInfo;
    protected EnergyProduct energyProduct;

    /**
     * Gets the value of the providerResponseContainer property.
     * 
     * @return
     *     possible object is
     *     {@link ProviderResponseContainer }
     *     
     */
    public ProviderResponseContainer getProviderResponseContainer() {
        return providerResponseContainer;
    }

    /**
     * Sets the value of the providerResponseContainer property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProviderResponseContainer }
     *     
     */
    public void setProviderResponseContainer(ProviderResponseContainer value) {
        this.providerResponseContainer = value;
    }

    /**
     * Gets the value of the orderNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * Sets the value of the orderNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderNumber(String value) {
        this.orderNumber = value;
    }

    /**
     * Gets the value of the suggestedPhone property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the suggestedPhone property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSuggestedPhone().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link List }{@code <}{@link String }{@code >}{@code >}
     * 
     * 
     */
    public List<JAXBElement<List<String>>> getSuggestedPhone() {
        if (suggestedPhone == null) {
            suggestedPhone = new ArrayList<JAXBElement<List<String>>>();
        }
        return this.suggestedPhone;
    }

    /**
     * Gets the value of the confirmedPhone property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the confirmedPhone property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConfirmedPhone().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link List }{@code <}{@link String }{@code >}{@code >}
     * 
     * 
     */
    public List<JAXBElement<List<String>>> getConfirmedPhone() {
        if (confirmedPhone == null) {
            confirmedPhone = new ArrayList<JAXBElement<List<String>>>();
        }
        return this.confirmedPhone;
    }

    /**
     * Gets the value of the product property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the product property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProduct().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrderLineItemDetailTypeType }
     * 
     * 
     */
    public List<OrderLineItemDetailTypeType> getProduct() {
        if (product == null) {
            product = new ArrayList<OrderLineItemDetailTypeType>();
        }
        return this.product;
    }

    /**
     * Gets the value of the qualificationFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isQualificationFlag() {
        return qualificationFlag;
    }

    /**
     * Sets the value of the qualificationFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setQualificationFlag(Boolean value) {
        this.qualificationFlag = value;
    }

    /**
     * Gets the value of the programmingValidFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isProgrammingValidFlag() {
        return programmingValidFlag;
    }

    /**
     * Sets the value of the programmingValidFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setProgrammingValidFlag(Boolean value) {
        this.programmingValidFlag = value;
    }

    /**
     * Gets the value of the customerType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerType() {
        return customerType;
    }

    /**
     * Sets the value of the customerType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerType(String value) {
        this.customerType = value;
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
     * Gets the value of the productInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the productInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProductInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProductInfo }
     * 
     * 
     */
    public List<ProductInfo> getProductInfo() {
        if (productInfo == null) {
            productInfo = new ArrayList<ProductInfo>();
        }
        return this.productInfo;
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
     * Gets the value of the receiver property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the receiver property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReceiver().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Receiver }
     * 
     * 
     */
    public List<Receiver> getReceiver() {
        if (receiver == null) {
            receiver = new ArrayList<Receiver>();
        }
        return this.receiver;
    }

    /**
     * Gets the value of the priceInfo property.
     * 
     * @return
     *     possible object is
     *     {@link PriceInfo }
     *     
     */
    public PriceInfo getPriceInfo() {
        return priceInfo;
    }

    /**
     * Sets the value of the priceInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceInfo }
     *     
     */
    public void setPriceInfo(PriceInfo value) {
        this.priceInfo = value;
    }

    /**
     * Gets the value of the dishConfiguration property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDISHConfiguration() {
        return dishConfiguration;
    }

    /**
     * Sets the value of the dishConfiguration property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDISHConfiguration(String value) {
        this.dishConfiguration = value;
    }

    /**
     * Gets the value of the creditQualResponse property.
     * 
     * @return
     *     possible object is
     *     {@link CreditQualResponseInfo }
     *     
     */
    public CreditQualResponseInfo getCreditQualResponse() {
        return creditQualResponse;
    }

    /**
     * Sets the value of the creditQualResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditQualResponseInfo }
     *     
     */
    public void setCreditQualResponse(CreditQualResponseInfo value) {
        this.creditQualResponse = value;
    }

    /**
     * Gets the value of the orderQualResponse property.
     * 
     * @return
     *     possible object is
     *     {@link OrderQualResponseInfo }
     *     
     */
    public OrderQualResponseInfo getOrderQualResponse() {
        return orderQualResponse;
    }

    /**
     * Sets the value of the orderQualResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderQualResponseInfo }
     *     
     */
    public void setOrderQualResponse(OrderQualResponseInfo value) {
        this.orderQualResponse = value;
    }

    /**
     * Gets the value of the submitOrderResponse property.
     * 
     * @return
     *     possible object is
     *     {@link SubmitOrderResponseInfo }
     *     
     */
    public SubmitOrderResponseInfo getSubmitOrderResponse() {
        return submitOrderResponse;
    }

    /**
     * Sets the value of the submitOrderResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubmitOrderResponseInfo }
     *     
     */
    public void setSubmitOrderResponse(SubmitOrderResponseInfo value) {
        this.submitOrderResponse = value;
    }

    /**
     * Gets the value of the scheduleDate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the scheduleDate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getScheduleDate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ScheduleDateType }
     * 
     * 
     */
    public List<ScheduleDateType> getScheduleDate() {
        if (scheduleDate == null) {
            scheduleDate = new ArrayList<ScheduleDateType>();
        }
        return this.scheduleDate;
    }

    /**
     * Gets the value of the providerLineItemStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ProviderLineItemStatusType }
     *     
     */
    public ProviderLineItemStatusType getProviderLineItemStatus() {
        return providerLineItemStatus;
    }

    /**
     * Sets the value of the providerLineItemStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProviderLineItemStatusType }
     *     
     */
    public void setProviderLineItemStatus(ProviderLineItemStatusType value) {
        this.providerLineItemStatus = value;
    }

    /**
     * Gets the value of the termsAndConditions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the termsAndConditions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTermsAndConditions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TermsAndConditionsType2 }
     * 
     * 
     */
    public List<TermsAndConditionsType2> getTermsAndConditions() {
        if (termsAndConditions == null) {
            termsAndConditions = new ArrayList<TermsAndConditionsType2>();
        }
        return this.termsAndConditions;
    }

    /**
     * Gets the value of the customerOrderInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the customerOrderInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustomerOrderInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CustomerOrderInfo }
     * 
     * 
     */
    public List<CustomerOrderInfo> getCustomerOrderInfo() {
        if (customerOrderInfo == null) {
            customerOrderInfo = new ArrayList<CustomerOrderInfo>();
        }
        return this.customerOrderInfo;
    }

    /**
     * Gets the value of the equipmentBundleInfo property.
     * 
     * @return
     *     possible object is
     *     {@link EquipmentBundleInfo }
     *     
     */
    public EquipmentBundleInfo getEquipmentBundleInfo() {
        return equipmentBundleInfo;
    }

    /**
     * Sets the value of the equipmentBundleInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link EquipmentBundleInfo }
     *     
     */
    public void setEquipmentBundleInfo(EquipmentBundleInfo value) {
        this.equipmentBundleInfo = value;
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
     * Gets the value of the equipmentSolutionInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the equipmentSolutionInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEquipmentSolutionInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EquipmentSolutionInfo }
     * 
     * 
     */
    public List<EquipmentSolutionInfo> getEquipmentSolutionInfo() {
        if (equipmentSolutionInfo == null) {
            equipmentSolutionInfo = new ArrayList<EquipmentSolutionInfo>();
        }
        return this.equipmentSolutionInfo;
    }

    /**
     * Gets the value of the productBundleInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the productBundleInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProductBundleInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProductBundleInfo }
     * 
     * 
     */
    public List<ProductBundleInfo> getProductBundleInfo() {
        if (productBundleInfo == null) {
            productBundleInfo = new ArrayList<ProductBundleInfo>();
        }
        return this.productBundleInfo;
    }

    /**
     * Gets the value of the authenticateCustomerResponse property.
     * 
     * @return
     *     possible object is
     *     {@link AuthenticateCustomerResponseInfo }
     *     
     */
    public AuthenticateCustomerResponseInfo getAuthenticateCustomerResponse() {
        return authenticateCustomerResponse;
    }

    /**
     * Sets the value of the authenticateCustomerResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthenticateCustomerResponseInfo }
     *     
     */
    public void setAuthenticateCustomerResponse(AuthenticateCustomerResponseInfo value) {
        this.authenticateCustomerResponse = value;
    }

    /**
     * Gets the value of the rtimErrorCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRtimErrorCode() {
        return rtimErrorCode;
    }

    /**
     * Sets the value of the rtimErrorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRtimErrorCode(String value) {
        this.rtimErrorCode = value;
    }

    /**
     * Gets the value of the alternatePromotions property.
     * 
     * @return
     *     possible object is
     *     {@link AlternatePromotion }
     *     
     */
    public AlternatePromotion getAlternatePromotions() {
        return alternatePromotions;
    }

    /**
     * Sets the value of the alternatePromotions property.
     * 
     * @param value
     *     allowed object is
     *     {@link AlternatePromotion }
     *     
     */
    public void setAlternatePromotions(AlternatePromotion value) {
        this.alternatePromotions = value;
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

}
