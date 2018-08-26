
package com.A.xml.di.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dialogueType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dialogueType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="externalId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tags" type="{http://xml.A.com/v4}tagListType" minOccurs="0"/>
 *         &lt;element name="dataGroupList">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="dataGroup" type="{http://xml.A.com/v4}dataGroupType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
@XmlType(name = "dialogueType", propOrder = {
    "externalId",
    "tags",
    "dataGroupList"
})
public class DialogueType {

    @XmlElement(required = true)
    protected String externalId;
    protected TagListType tags;
    @XmlElement(required = true)
    protected DialogueType.DataGroupList dataGroupList;
    @XmlAttribute(name = "name")
    protected String name;

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
     * Gets the value of the dataGroupList property.
     * 
     * @return
     *     possible object is
     *     {@link DialogueType.DataGroupList }
     *     
     */
    public DialogueType.DataGroupList getDataGroupList() {
        return dataGroupList;
    }

    /**
     * Sets the value of the dataGroupList property.
     * 
     * @param value
     *     allowed object is
     *     {@link DialogueType.DataGroupList }
     *     
     */
    public void setDataGroupList(DialogueType.DataGroupList value) {
        this.dataGroupList = value;
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
     *         &lt;element name="dataGroup" type="{http://xml.A.com/v4}dataGroupType" maxOccurs="unbounded" minOccurs="0"/>
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
        "dataGroup"
    })
    public static class DataGroupList {

        protected List<DataGroupType> dataGroup;

        /**
         * Gets the value of the dataGroup property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the dataGroup property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDataGroup().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DataGroupType }
         * 
         * 
         */
        public List<DataGroupType> getDataGroup() {
            if (dataGroup == null) {
                dataGroup = new ArrayList<DataGroupType>();
            }
            return this.dataGroup;
        }

    }

}
