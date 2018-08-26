
package com.A.xml.me.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ccpEventNotificationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ccpEventNotificationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="updateEmailInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bounceDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="bounceType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="hardBounce"/>
 *               &lt;enumeration value="softBounce"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="clickDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="clickUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="emailID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="emailStatus">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="emailStatus1"/>
 *               &lt;enumeration value="emailStatus2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="emailType">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
 *               &lt;enumeration value="4"/>
 *               &lt;enumeration value="5"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="emailSegmentID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="emailStatusIDEncrypt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="openDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="returnCode" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="returnMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sendDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="custExternalID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="custExternalIDEncrypt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ccpEventNotificationType", propOrder = {
    "updateEmailInfo",
    "orderID",
    "bounceDate",
    "bounceType",
    "clickDate",
    "clickUrl",
    "emailID",
    "emailStatus",
    "emailType",
    "emailSegmentID",
    "emailStatusIDEncrypt",
    "openDate",
    "returnCode",
    "returnMsg",
    "sendDate",
    "custExternalID",
    "custExternalIDEncrypt"
})
public class CcpEventNotificationType {

    protected String updateEmailInfo;
    protected String orderID;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar bounceDate;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String bounceType;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar clickDate;
    protected String clickUrl;
    protected int emailID;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String emailStatus;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String emailType;
    protected Integer emailSegmentID;
    protected String emailStatusIDEncrypt;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar openDate;
    protected Integer returnCode;
    protected String returnMsg;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar sendDate;
    protected Long custExternalID;
    protected String custExternalIDEncrypt;

    /**
     * Gets the value of the updateEmailInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdateEmailInfo() {
        return updateEmailInfo;
    }

    /**
     * Sets the value of the updateEmailInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateEmailInfo(String value) {
        this.updateEmailInfo = value;
    }

    /**
     * Gets the value of the orderID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderID() {
        return orderID;
    }

    /**
     * Sets the value of the orderID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderID(String value) {
        this.orderID = value;
    }

    /**
     * Gets the value of the bounceDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBounceDate() {
        return bounceDate;
    }

    /**
     * Sets the value of the bounceDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBounceDate(XMLGregorianCalendar value) {
        this.bounceDate = value;
    }

    /**
     * Gets the value of the bounceType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBounceType() {
        return bounceType;
    }

    /**
     * Sets the value of the bounceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBounceType(String value) {
        this.bounceType = value;
    }

    /**
     * Gets the value of the clickDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getClickDate() {
        return clickDate;
    }

    /**
     * Sets the value of the clickDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setClickDate(XMLGregorianCalendar value) {
        this.clickDate = value;
    }

    /**
     * Gets the value of the clickUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClickUrl() {
        return clickUrl;
    }

    /**
     * Sets the value of the clickUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClickUrl(String value) {
        this.clickUrl = value;
    }

    /**
     * Gets the value of the emailID property.
     * 
     */
    public int getEmailID() {
        return emailID;
    }

    /**
     * Sets the value of the emailID property.
     * 
     */
    public void setEmailID(int value) {
        this.emailID = value;
    }

    /**
     * Gets the value of the emailStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailStatus() {
        return emailStatus;
    }

    /**
     * Sets the value of the emailStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailStatus(String value) {
        this.emailStatus = value;
    }

    /**
     * Gets the value of the emailType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailType() {
        return emailType;
    }

    /**
     * Sets the value of the emailType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailType(String value) {
        this.emailType = value;
    }

    /**
     * Gets the value of the emailSegmentID property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEmailSegmentID() {
        return emailSegmentID;
    }

    /**
     * Sets the value of the emailSegmentID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEmailSegmentID(Integer value) {
        this.emailSegmentID = value;
    }

    /**
     * Gets the value of the emailStatusIDEncrypt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailStatusIDEncrypt() {
        return emailStatusIDEncrypt;
    }

    /**
     * Sets the value of the emailStatusIDEncrypt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailStatusIDEncrypt(String value) {
        this.emailStatusIDEncrypt = value;
    }

    /**
     * Gets the value of the openDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOpenDate() {
        return openDate;
    }

    /**
     * Sets the value of the openDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOpenDate(XMLGregorianCalendar value) {
        this.openDate = value;
    }

    /**
     * Gets the value of the returnCode property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getReturnCode() {
        return returnCode;
    }

    /**
     * Sets the value of the returnCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setReturnCode(Integer value) {
        this.returnCode = value;
    }

    /**
     * Gets the value of the returnMsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnMsg() {
        return returnMsg;
    }

    /**
     * Sets the value of the returnMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnMsg(String value) {
        this.returnMsg = value;
    }

    /**
     * Gets the value of the sendDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSendDate() {
        return sendDate;
    }

    /**
     * Sets the value of the sendDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSendDate(XMLGregorianCalendar value) {
        this.sendDate = value;
    }

    /**
     * Gets the value of the custExternalID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCustExternalID() {
        return custExternalID;
    }

    /**
     * Sets the value of the custExternalID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCustExternalID(Long value) {
        this.custExternalID = value;
    }

    /**
     * Gets the value of the custExternalIDEncrypt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustExternalIDEncrypt() {
        return custExternalIDEncrypt;
    }

    /**
     * Sets the value of the custExternalIDEncrypt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustExternalIDEncrypt(String value) {
        this.custExternalIDEncrypt = value;
    }

}
