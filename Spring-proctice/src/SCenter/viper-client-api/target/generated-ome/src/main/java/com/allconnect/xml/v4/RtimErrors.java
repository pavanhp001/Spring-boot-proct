
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RtimErrors complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RtimErrors">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rtimError" type="{http://xml.A.com/common}RtimError" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RtimErrors", namespace = "http://xml.A.com/common", propOrder = {
    "rtimError"
})
public class RtimErrors {

    @XmlElement(required = true)
    protected List<RtimError> rtimError;

    /**
     * Gets the value of the rtimError property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rtimError property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRtimError().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RtimError }
     * 
     * 
     */
    public List<RtimError> getRtimError() {
        if (rtimError == null) {
            rtimError = new ArrayList<RtimError>();
        }
        return this.rtimError;
    }

}
