
package com.A.xml.me.v4;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for paymentEventType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paymentEventType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="externalId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="billingInfoId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Event" type="{http://xml.A.com/v4}paymentEventTypeType"/>
 *         &lt;element name="CVV" type="{http://xml.A.com/v4}CVVType" minOccurs="0"/>
 *         &lt;element name="Amount" type="{http://xml.A.com/v4}currencyType" minOccurs="0"/>
 *         &lt;element name="ConfirmNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transactionDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="paymentStatus" type="{http://xml.A.com/v4}paymentStatusWithTypeType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="OrderId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LineItemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="custAgreedCCDisclosure" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paymentEventType", propOrder = {
    "externalId",
    "billingInfoId",
    "event",
    "cvv",
    "amount",
    "confirmNumber",
    "transactionDate",
    "paymentStatus",
    "orderId",
    "lineItemId"
})
public class PaymentEventType {

    @XmlElement(required = true)
    protected String externalId;
    @XmlElement(required = true)
    protected String billingInfoId;
    @XmlElement(name = "Event", required = true)
    protected PaymentEventTypeType event;
    @XmlElement(name = "CVV")
    protected String cvv;
    @XmlElement(name = "Amount")
    protected BigDecimal amount;
    @XmlElement(name = "ConfirmNumber")
    protected String confirmNumber;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar transactionDate;
    protected List<PaymentStatusWithTypeType> paymentStatus;
    @XmlElement(name = "OrderId", required = true)
    protected String orderId;
    @XmlElement(name = "LineItemId")
    protected String lineItemId;
    @XmlAttribute(name = "custAgreedCCDisclosure")
    protected String custAgreedCCDisclosure;

    /**
     * Gets the value of the externalId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalId() {
        return externalId;
    }

    /**
     * Sets the value of the externalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalId(String value) {
        this.externalId = value;
    }

    /**
     * Gets the value of the billingInfoId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingInfoId() {
        return billingInfoId;
    }

    /**
     * Sets the value of the billingInfoId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingInfoId(String value) {
        this.billingInfoId = value;
    }

    /**
     * Gets the value of the event property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentEventTypeType }
     *     
     */
    public PaymentEventTypeType getEvent() {
        return event;
    }

    /**
     * Sets the value of the event property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentEventTypeType }
     *     
     */
    public void setEvent(PaymentEventTypeType value) {
        this.event = value;
    }

    /**
     * Gets the value of the cvv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCVV() {
        return cvv;
    }

    /**
     * Sets the value of the cvv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCVV(String value) {
        this.cvv = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmount(BigDecimal value) {
        this.amount = value;
    }

    /**
     * Gets the value of the confirmNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfirmNumber() {
        return confirmNumber;
    }

    /**
     * Sets the value of the confirmNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfirmNumber(String value) {
        this.confirmNumber = value;
    }

    /**
     * Gets the value of the transactionDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTransactionDate() {
        return transactionDate;
    }

    /**
     * Sets the value of the transactionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTransactionDate(XMLGregorianCalendar value) {
        this.transactionDate = value;
    }

    /**
     * Gets the value of the paymentStatus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paymentStatus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaymentStatus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentStatusWithTypeType }
     * 
     * 
     */
    public List<PaymentStatusWithTypeType> getPaymentStatus() {
        if (paymentStatus == null) {
            paymentStatus = new ArrayList<PaymentStatusWithTypeType>();
        }
        return this.paymentStatus;
    }

    /**
     * Gets the value of the orderId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Sets the value of the orderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderId(String value) {
        this.orderId = value;
    }

    /**
     * Gets the value of the lineItemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLineItemId() {
        return lineItemId;
    }

    /**
     * Sets the value of the lineItemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLineItemId(String value) {
        this.lineItemId = value;
    }

    /**
     * Gets the value of the custAgreedCCDisclosure property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustAgreedCCDisclosure() {
        return custAgreedCCDisclosure;
    }

    /**
     * Sets the value of the custAgreedCCDisclosure property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustAgreedCCDisclosure(String value) {
        this.custAgreedCCDisclosure = value;
    }

}
