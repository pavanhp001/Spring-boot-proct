
package com.A.xml.di.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dataFieldDependencyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dataFieldDependencyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="enabledDataFields" type="{http://xml.A.com/v4}dependentDataFieldsType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="externalId" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="type" use="required" type="{http://xml.A.com/v4}dataFieldTypeType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataFieldDependencyType", propOrder = {
    "enabledDataFields"
})
public class DataFieldDependencyType {

    @XmlElement(required = true)
    protected List<DependentDataFieldsType> enabledDataFields;
    @XmlAttribute(name = "externalId", required = true)
    protected String externalId;
    @XmlAttribute(name = "type", required = true)
    protected DataFieldTypeType type;

    /**
     * Gets the value of the enabledDataFields property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the enabledDataFields property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEnabledDataFields().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DependentDataFieldsType }
     * 
     * 
     */
    public List<DependentDataFieldsType> getEnabledDataFields() {
        if (enabledDataFields == null) {
            enabledDataFields = new ArrayList<DependentDataFieldsType>();
        }
        return this.enabledDataFields;
    }

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
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link DataFieldTypeType }
     *     
     */
    public DataFieldTypeType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataFieldTypeType }
     *     
     */
    public void setType(DataFieldTypeType value) {
        this.type = value;
    }

}
