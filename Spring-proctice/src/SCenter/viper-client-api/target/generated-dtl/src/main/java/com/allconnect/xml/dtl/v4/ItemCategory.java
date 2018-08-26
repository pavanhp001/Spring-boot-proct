
package com.A.xml.dtl.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * 
 * 						Current possible values are:
 * 						
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;OL&gt;&lt;LI&gt;Bundles&lt;/LI&gt;&lt;LI&gt;Electricity&lt;/LI&gt;&lt;LI&gt;Local Phone&lt;/LI&gt;&lt;LI&gt;Long Distance Phone&lt;/LI&gt;&lt;LI&gt;Home Wire Protection&lt;/LI&gt;&lt;LI&gt;Wireless Phone&lt;/LI&gt;&lt;LI&gt;Cable TV&lt;/LI&gt;&lt;LI&gt;Satellite TV&lt;/LI&gt;&lt;LI&gt;High Speed Internet&lt;/LI&gt;&lt;LI&gt;Dial Up Internet&lt;/LI&gt;&lt;LI&gt;Local News Paper&lt;/LI&gt;&lt;LI&gt;National News Paper&lt;/LI&gt;&lt;LI&gt;Personal Checks&lt;/LI&gt;&lt;LI&gt;Washer / Dryer Rental&lt;/LI&gt;&lt;LI&gt;Warranty&lt;/LI&gt;&lt;LI&gt;Home Security&lt;/LI&gt;&lt;LI&gt;Waste Removal&lt;/LI&gt;&lt;LI&gt;Natural Gas&lt;/LI&gt;&lt;LI&gt;Energy Conservation&lt;/LI&gt;&lt;LI&gt;Offers&lt;/LI&gt;&lt;LI&gt;Second Call&lt;/LI&gt;&lt;LI&gt;Water&lt;/LI&gt;&lt;LI&gt;Appliance Protection&lt;/LI&gt;&lt;/OL&gt;
 * </pre>
 * 
 * 
 * <p>Java class for itemCategory complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="itemCategory">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="displayName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "itemCategory", propOrder = {
    "value"
})
public class ItemCategory {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "displayName")
    protected String displayName;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the displayName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the value of the displayName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayName(String value) {
        this.displayName = value;
    }

}
