
package com.A.xml.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for coachingList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="coachingList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="coachingType" type="{http://xml.A.com/v4}coachingType"/>
 *         &lt;element name="coachingDesc" type="{http://xml.A.com/v4}coachingDesc"/>
 *         &lt;element name="agentId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "coachingList", propOrder = {
    "coachingType",
    "coachingDesc",
    "agentId"
})
public class CoachingList {

    @XmlElement(required = true)
    protected CoachingType coachingType;
    @XmlElement(required = true)
    protected CoachingDesc coachingDesc;
    @XmlElement(required = true)
    protected String agentId;

    /**
     * Gets the value of the coachingType property.
     * 
     * @return
     *     possible object is
     *     {@link CoachingType }
     *     
     */
    public CoachingType getCoachingType() {
        return coachingType;
    }

    /**
     * Sets the value of the coachingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CoachingType }
     *     
     */
    public void setCoachingType(CoachingType value) {
        this.coachingType = value;
    }

    /**
     * Gets the value of the coachingDesc property.
     * 
     * @return
     *     possible object is
     *     {@link CoachingDesc }
     *     
     */
    public CoachingDesc getCoachingDesc() {
        return coachingDesc;
    }

    /**
     * Sets the value of the coachingDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link CoachingDesc }
     *     
     */
    public void setCoachingDesc(CoachingDesc value) {
        this.coachingDesc = value;
    }

    /**
     * Gets the value of the agentId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentId() {
        return agentId;
    }

    /**
     * Sets the value of the agentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentId(String value) {
        this.agentId = value;
    }

}
