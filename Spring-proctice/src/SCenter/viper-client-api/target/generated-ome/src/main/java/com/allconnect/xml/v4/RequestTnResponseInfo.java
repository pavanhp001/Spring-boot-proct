
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestTnResponseInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestTnResponseInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="masterOrderNumberResponse" type="{http://xml.A.com/v4}MasterOrderNumberResponseInfo" minOccurs="0"/>
 *         &lt;element name="requestTn" type="{http://xml.A.com/v4}RequestTnInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestTnResponseInfo", propOrder = {
    "masterOrderNumberResponse",
    "requestTn"
})
public class RequestTnResponseInfo {

    protected MasterOrderNumberResponseInfo masterOrderNumberResponse;
    protected RequestTnInfo requestTn;

    /**
     * Gets the value of the masterOrderNumberResponse property.
     * 
     * @return
     *     possible object is
     *     {@link MasterOrderNumberResponseInfo }
     *     
     */
    public MasterOrderNumberResponseInfo getMasterOrderNumberResponse() {
        return masterOrderNumberResponse;
    }

    /**
     * Sets the value of the masterOrderNumberResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link MasterOrderNumberResponseInfo }
     *     
     */
    public void setMasterOrderNumberResponse(MasterOrderNumberResponseInfo value) {
        this.masterOrderNumberResponse = value;
    }

    /**
     * Gets the value of the requestTn property.
     * 
     * @return
     *     possible object is
     *     {@link RequestTnInfo }
     *     
     */
    public RequestTnInfo getRequestTn() {
        return requestTn;
    }

    /**
     * Sets the value of the requestTn property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestTnInfo }
     *     
     */
    public void setRequestTn(RequestTnInfo value) {
        this.requestTn = value;
    }

}
