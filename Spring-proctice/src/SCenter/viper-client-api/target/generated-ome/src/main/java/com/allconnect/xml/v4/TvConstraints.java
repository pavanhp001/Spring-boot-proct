
package com.A.xml.v4;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * This element lists the constraints as to how a
 *                 customer may select TV's.
 * 
 * <p>Java class for TvConstraints complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TvConstraints">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="maxNumTvs" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *         &lt;element name="slingSupported" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="wholeHomeOption" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="hdProgrammingPresent" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="tvLabels">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Default" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="Standard" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="receiverOptions" type="{http://xml.A.com/v4}ReceiverOptions" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TvConstraints", propOrder = {
    "maxNumTvs",
    "slingSupported",
    "wholeHomeOption",
    "hdProgrammingPresent",
    "tvLabels",
    "receiverOptions"
})
public class TvConstraints {

    @XmlElement(required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger maxNumTvs;
    protected boolean slingSupported;
    protected Boolean wholeHomeOption;
    protected Boolean hdProgrammingPresent;
    @XmlElement(required = true)
    protected TvConstraints.TvLabels tvLabels;
    protected ReceiverOptions receiverOptions;

    /**
     * Gets the value of the maxNumTvs property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxNumTvs() {
        return maxNumTvs;
    }

    /**
     * Sets the value of the maxNumTvs property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxNumTvs(BigInteger value) {
        this.maxNumTvs = value;
    }

    /**
     * Gets the value of the slingSupported property.
     * 
     */
    public boolean isSlingSupported() {
        return slingSupported;
    }

    /**
     * Sets the value of the slingSupported property.
     * 
     */
    public void setSlingSupported(boolean value) {
        this.slingSupported = value;
    }

    /**
     * Gets the value of the wholeHomeOption property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isWholeHomeOption() {
        return wholeHomeOption;
    }

    /**
     * Sets the value of the wholeHomeOption property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setWholeHomeOption(Boolean value) {
        this.wholeHomeOption = value;
    }

    /**
     * Gets the value of the hdProgrammingPresent property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHdProgrammingPresent() {
        return hdProgrammingPresent;
    }

    /**
     * Sets the value of the hdProgrammingPresent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHdProgrammingPresent(Boolean value) {
        this.hdProgrammingPresent = value;
    }

    /**
     * Gets the value of the tvLabels property.
     * 
     * @return
     *     possible object is
     *     {@link TvConstraints.TvLabels }
     *     
     */
    public TvConstraints.TvLabels getTvLabels() {
        return tvLabels;
    }

    /**
     * Sets the value of the tvLabels property.
     * 
     * @param value
     *     allowed object is
     *     {@link TvConstraints.TvLabels }
     *     
     */
    public void setTvLabels(TvConstraints.TvLabels value) {
        this.tvLabels = value;
    }

    /**
     * Gets the value of the receiverOptions property.
     * 
     * @return
     *     possible object is
     *     {@link ReceiverOptions }
     *     
     */
    public ReceiverOptions getReceiverOptions() {
        return receiverOptions;
    }

    /**
     * Sets the value of the receiverOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceiverOptions }
     *     
     */
    public void setReceiverOptions(ReceiverOptions value) {
        this.receiverOptions = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Default" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="Standard" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "_default",
        "standard"
    })
    public static class TvLabels {

        @XmlElement(name = "Default", required = true)
        protected String _default;
        @XmlElement(name = "Standard")
        protected List<String> standard;

        /**
         * Gets the value of the default property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDefault() {
            return _default;
        }

        /**
         * Sets the value of the default property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDefault(String value) {
            this._default = value;
        }

        /**
         * Gets the value of the standard property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the standard property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getStandard().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getStandard() {
            if (standard == null) {
                standard = new ArrayList<String>();
            }
            return this.standard;
        }

    }

}
