
package com.A.xml.di.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * Denotes whether a provider is realtime or internal,
 * 				and provides field to indentify the realtime datasource ie "ATTSTI".
 * 				Datasource is optional incase of internal provider data
 * 			
 * 
 * <p>Java class for providerSourceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="providerSourceType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://xml.A.com/v4>providerSourceBaseType">
 *       &lt;attribute name="datasource" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "providerSourceType", propOrder = {
    "value"
})
public class ProviderSourceType {

    @XmlValue
    protected ProviderSourceBaseType value;
    @XmlAttribute(name = "datasource")
    protected String datasource;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link ProviderSourceBaseType }
     *     
     */
    public ProviderSourceBaseType getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProviderSourceBaseType }
     *     
     */
    public void setValue(ProviderSourceBaseType value) {
        this.value = value;
    }

    /**
     * Gets the value of the datasource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatasource() {
        return datasource;
    }

    /**
     * Sets the value of the datasource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatasource(String value) {
        this.datasource = value;
    }

}
