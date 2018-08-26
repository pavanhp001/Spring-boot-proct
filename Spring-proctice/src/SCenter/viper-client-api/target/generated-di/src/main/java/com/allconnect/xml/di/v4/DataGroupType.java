
package com.A.xml.di.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dataGroupType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dataGroupType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="displayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tags" type="{http://xml.A.com/v4}tagListType" minOccurs="0"/>
 *         &lt;element name="dataFieldList">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="dataField" type="{http://xml.A.com/v4}dataFieldType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{http://xml.A.com/v4}dataFieldMatrix"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataGroupType", propOrder = {
    "displayName",
    "tags",
    "dataFieldList",
    "dataFieldMatrix"
})
public class DataGroupType {

    protected String displayName;
    protected TagListType tags;
    @XmlElement(required = true)
    protected DataGroupType.DataFieldList dataFieldList;
    @XmlElement(required = true)
    protected DataFieldMatrixType dataFieldMatrix;
    @XmlAttribute(name = "name")
    protected String name;

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

    /**
     * Gets the value of the tags property.
     * 
     * @return
     *     possible object is
     *     {@link TagListType }
     *     
     */
    public TagListType getTags() {
        return tags;
    }

    /**
     * Sets the value of the tags property.
     * 
     * @param value
     *     allowed object is
     *     {@link TagListType }
     *     
     */
    public void setTags(TagListType value) {
        this.tags = value;
    }

    /**
     * Gets the value of the dataFieldList property.
     * 
     * @return
     *     possible object is
     *     {@link DataGroupType.DataFieldList }
     *     
     */
    public DataGroupType.DataFieldList getDataFieldList() {
        return dataFieldList;
    }

    /**
     * Sets the value of the dataFieldList property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataGroupType.DataFieldList }
     *     
     */
    public void setDataFieldList(DataGroupType.DataFieldList value) {
        this.dataFieldList = value;
    }

    /**
     * Gets the value of the dataFieldMatrix property.
     * 
     * @return
     *     possible object is
     *     {@link DataFieldMatrixType }
     *     
     */
    public DataFieldMatrixType getDataFieldMatrix() {
        return dataFieldMatrix;
    }

    /**
     * Sets the value of the dataFieldMatrix property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataFieldMatrixType }
     *     
     */
    public void setDataFieldMatrix(DataFieldMatrixType value) {
        this.dataFieldMatrix = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
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
     *         &lt;element name="dataField" type="{http://xml.A.com/v4}dataFieldType" maxOccurs="unbounded"/>
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
        "dataField"
    })
    public static class DataFieldList {

        @XmlElement(required = true)
        protected List<DataFieldType> dataField;

        /**
         * Gets the value of the dataField property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the dataField property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDataField().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DataFieldType }
         * 
         * 
         */
        public List<DataFieldType> getDataField() {
            if (dataField == null) {
                dataField = new ArrayList<DataFieldType>();
            }
            return this.dataField;
        }

    }

}
