
package com.A.xml.uam.v4;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for CreateUserRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateUserRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userLogin" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userLevel" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="userActive" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userType" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="userUpdated" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="userResources" type="{http://xml.A.com/v4}UserResourcesType" minOccurs="0"/>
 *         &lt;element name="userRoles" type="{http://xml.A.com/v4}UserRolesType" minOccurs="0"/>
 *         &lt;element name="metadataList" type="{http://xml.A.com/v4}metaDataListType" minOccurs="0"/>
 *         &lt;element name="userGroups" type="{http://xml.A.com/v4}userGroupListType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateUserRequest", propOrder = {
    "userName",
    "userLogin",
    "userLevel",
    "userActive",
    "userType",
    "userUpdated",
    "userResources",
    "userRoles",
    "metadataList",
    "userGroups"
})
public class CreateUserRequest {

    @XmlElement(required = true)
    protected String userName;
    @XmlElement(required = true)
    protected String userLogin;
    protected BigInteger userLevel;
    protected String userActive;
    protected BigInteger userType;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar userUpdated;
    protected UserResourcesType userResources;
    protected UserRolesType userRoles;
    protected MetaDataListType metadataList;
    protected UserGroupListType userGroups;

    /**
     * Gets the value of the userName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserName(String value) {
        this.userName = value;
    }

    /**
     * Gets the value of the userLogin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserLogin() {
        return userLogin;
    }

    /**
     * Sets the value of the userLogin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserLogin(String value) {
        this.userLogin = value;
    }

    /**
     * Gets the value of the userLevel property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getUserLevel() {
        return userLevel;
    }

    /**
     * Sets the value of the userLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setUserLevel(BigInteger value) {
        this.userLevel = value;
    }

    /**
     * Gets the value of the userActive property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserActive() {
        return userActive;
    }

    /**
     * Sets the value of the userActive property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserActive(String value) {
        this.userActive = value;
    }

    /**
     * Gets the value of the userType property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getUserType() {
        return userType;
    }

    /**
     * Sets the value of the userType property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setUserType(BigInteger value) {
        this.userType = value;
    }

    /**
     * Gets the value of the userUpdated property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUserUpdated() {
        return userUpdated;
    }

    /**
     * Sets the value of the userUpdated property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUserUpdated(XMLGregorianCalendar value) {
        this.userUpdated = value;
    }

    /**
     * Gets the value of the userResources property.
     * 
     * @return
     *     possible object is
     *     {@link UserResourcesType }
     *     
     */
    public UserResourcesType getUserResources() {
        return userResources;
    }

    /**
     * Sets the value of the userResources property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserResourcesType }
     *     
     */
    public void setUserResources(UserResourcesType value) {
        this.userResources = value;
    }

    /**
     * Gets the value of the userRoles property.
     * 
     * @return
     *     possible object is
     *     {@link UserRolesType }
     *     
     */
    public UserRolesType getUserRoles() {
        return userRoles;
    }

    /**
     * Sets the value of the userRoles property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserRolesType }
     *     
     */
    public void setUserRoles(UserRolesType value) {
        this.userRoles = value;
    }

    /**
     * Gets the value of the metadataList property.
     * 
     * @return
     *     possible object is
     *     {@link MetaDataListType }
     *     
     */
    public MetaDataListType getMetadataList() {
        return metadataList;
    }

    /**
     * Sets the value of the metadataList property.
     * 
     * @param value
     *     allowed object is
     *     {@link MetaDataListType }
     *     
     */
    public void setMetadataList(MetaDataListType value) {
        this.metadataList = value;
    }

    /**
     * Gets the value of the userGroups property.
     * 
     * @return
     *     possible object is
     *     {@link UserGroupListType }
     *     
     */
    public UserGroupListType getUserGroups() {
        return userGroups;
    }

    /**
     * Sets the value of the userGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserGroupListType }
     *     
     */
    public void setUserGroups(UserGroupListType value) {
        this.userGroups = value;
    }

}
