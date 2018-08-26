
package com.A.xml.dtl.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for energyPriceInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="energyPriceInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rate" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>double">
 *                 &lt;attribute name="tier" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="energyUnit" type="{http://xml.A.com/v4}energyUnitType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "energyPriceInfoType", propOrder = {
    "rate"
})
public class EnergyPriceInfoType {

    @XmlElement(required = true)
    protected List<EnergyPriceInfoType.Rate> rate;
    @XmlAttribute(name = "energyUnit")
    protected EnergyUnitType energyUnit;

    /**
     * Gets the value of the rate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EnergyPriceInfoType.Rate }
     * 
     * 
     */
    public List<EnergyPriceInfoType.Rate> getRate() {
        if (rate == null) {
            rate = new ArrayList<EnergyPriceInfoType.Rate>();
        }
        return this.rate;
    }

    /**
     * Gets the value of the energyUnit property.
     * 
     * @return
     *     possible object is
     *     {@link EnergyUnitType }
     *     
     */
    public EnergyUnitType getEnergyUnit() {
        return energyUnit;
    }

    /**
     * Sets the value of the energyUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnergyUnitType }
     *     
     */
    public void setEnergyUnit(EnergyUnitType value) {
        this.energyUnit = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>double">
     *       &lt;attribute name="tier" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Rate {

        @XmlValue
        protected double value;
        @XmlAttribute(name = "tier")
        protected String tier;

        /**
         * Gets the value of the value property.
         * 
         */
        public double getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         */
        public void setValue(double value) {
            this.value = value;
        }

        /**
         * Gets the value of the tier property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTier() {
            return tier;
        }

        /**
         * Sets the value of the tier property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTier(String value) {
            this.tier = value;
        }

    }

}
