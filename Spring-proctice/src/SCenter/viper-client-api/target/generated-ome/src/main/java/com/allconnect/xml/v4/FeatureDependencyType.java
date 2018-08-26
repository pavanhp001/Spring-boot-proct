
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for featureDependencyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="featureDependencyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dependencyExternalId" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="20"/>
 *       &lt;/sequence>
 *       &lt;attribute name="dependency" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *             &lt;enumeration value="requires"/>
 *             &lt;enumeration value="excludes"/>
 *             &lt;enumeration value="recommends"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "featureDependencyType", propOrder = {
    "dependencyExternalId"
})
public class FeatureDependencyType {

    @XmlElement(required = true)
    protected List<String> dependencyExternalId;
    @XmlAttribute(name = "dependency", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String dependency;

    /**
     * Gets the value of the dependencyExternalId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dependencyExternalId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDependencyExternalId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDependencyExternalId() {
        if (dependencyExternalId == null) {
            dependencyExternalId = new ArrayList<String>();
        }
        return this.dependencyExternalId;
    }

    /**
     * Gets the value of the dependency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDependency() {
        return dependency;
    }

    /**
     * Sets the value of the dependency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDependency(String value) {
        this.dependency = value;
    }

}
