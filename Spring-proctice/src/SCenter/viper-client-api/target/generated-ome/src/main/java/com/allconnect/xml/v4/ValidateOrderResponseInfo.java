
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ValidateOrderResponseInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValidateOrderResponseInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dueDateCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="getDueDatesResponse" type="{http://xml.A.com/v4}ProductSchedule" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="providerAttributes" type="{http://xml.A.com/v4}ProviderAttributes" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="estimateFirstBillResponse" type="{http://xml.A.com/v4}EstimateFirstBillResponseInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidateOrderResponseInfo", propOrder = {
    "status",
    "orderType",
    "dueDateCode",
    "getDueDatesResponse",
    "providerAttributes",
    "estimateFirstBillResponse"
})
public class ValidateOrderResponseInfo {

    protected String status;
    protected String orderType;
    protected String dueDateCode;
    protected List<ProductSchedule> getDueDatesResponse;
    protected List<ProviderAttributes> providerAttributes;
    protected EstimateFirstBillResponseInfo estimateFirstBillResponse;

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the orderType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * Sets the value of the orderType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderType(String value) {
        this.orderType = value;
    }

    /**
     * Gets the value of the dueDateCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDueDateCode() {
        return dueDateCode;
    }

    /**
     * Sets the value of the dueDateCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDueDateCode(String value) {
        this.dueDateCode = value;
    }

    /**
     * Gets the value of the getDueDatesResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the getDueDatesResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGetDueDatesResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProductSchedule }
     * 
     * 
     */
    public List<ProductSchedule> getGetDueDatesResponse() {
        if (getDueDatesResponse == null) {
            getDueDatesResponse = new ArrayList<ProductSchedule>();
        }
        return this.getDueDatesResponse;
    }

    /**
     * Gets the value of the providerAttributes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the providerAttributes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProviderAttributes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProviderAttributes }
     * 
     * 
     */
    public List<ProviderAttributes> getProviderAttributes() {
        if (providerAttributes == null) {
            providerAttributes = new ArrayList<ProviderAttributes>();
        }
        return this.providerAttributes;
    }

    /**
     * Gets the value of the estimateFirstBillResponse property.
     * 
     * @return
     *     possible object is
     *     {@link EstimateFirstBillResponseInfo }
     *     
     */
    public EstimateFirstBillResponseInfo getEstimateFirstBillResponse() {
        return estimateFirstBillResponse;
    }

    /**
     * Sets the value of the estimateFirstBillResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link EstimateFirstBillResponseInfo }
     *     
     */
    public void setEstimateFirstBillResponse(EstimateFirstBillResponseInfo value) {
        this.estimateFirstBillResponse = value;
    }

}
