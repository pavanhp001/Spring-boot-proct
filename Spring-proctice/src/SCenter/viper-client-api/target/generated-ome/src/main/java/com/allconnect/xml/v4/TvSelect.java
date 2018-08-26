
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TvSelect complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TvSelect">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tvConfiguration" type="{http://xml.A.com/v4}TvConfiguration" maxOccurs="unbounded"/>
 *         &lt;element name="SlingAdapterRequired" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="LogitechRevueRequired" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TvSelect", propOrder = {
    "tvConfiguration",
    "slingAdapterRequired",
    "logitechRevueRequired"
})
public class TvSelect {

    @XmlElement(required = true)
    protected List<TvConfiguration> tvConfiguration;
    @XmlElement(name = "SlingAdapterRequired")
    protected boolean slingAdapterRequired;
    @XmlElement(name = "LogitechRevueRequired")
    protected boolean logitechRevueRequired;

    /**
     * Gets the value of the tvConfiguration property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tvConfiguration property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTvConfiguration().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TvConfiguration }
     * 
     * 
     */
    public List<TvConfiguration> getTvConfiguration() {
        if (tvConfiguration == null) {
            tvConfiguration = new ArrayList<TvConfiguration>();
        }
        return this.tvConfiguration;
    }

    /**
     * Gets the value of the slingAdapterRequired property.
     * 
     */
    public boolean isSlingAdapterRequired() {
        return slingAdapterRequired;
    }

    /**
     * Sets the value of the slingAdapterRequired property.
     * 
     */
    public void setSlingAdapterRequired(boolean value) {
        this.slingAdapterRequired = value;
    }

    /**
     * Gets the value of the logitechRevueRequired property.
     * 
     */
    public boolean isLogitechRevueRequired() {
        return logitechRevueRequired;
    }

    /**
     * Sets the value of the logitechRevueRequired property.
     * 
     */
    public void setLogitechRevueRequired(boolean value) {
        this.logitechRevueRequired = value;
    }

}
