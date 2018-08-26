
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateRoleRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateRoleRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="roleName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="roleDesc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="roleOwnerExternalId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="roleResources" type="{http://xml.A.com/v4}RoleResourcesType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateRoleRequest", propOrder = {
    "roleName",
    "roleDesc",
    "roleOwnerExternalId",
    "roleResources"
})
public class CreateRoleRequest {

    @XmlElement(required = true)
    protected String roleName;
    @XmlElement(required = true)
    protected String roleDesc;
    @XmlElement(required = true)
    protected String roleOwnerExternalId;
    protected RoleResourcesType roleResources;

    /**
     * Gets the value of the roleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Sets the value of the roleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoleName(String value) {
        this.roleName = value;
    }

    /**
     * Gets the value of the roleDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoleDesc() {
        return roleDesc;
    }

    /**
     * Sets the value of the roleDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoleDesc(String value) {
        this.roleDesc = value;
    }

    /**
     * Gets the value of the roleOwnerExternalId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoleOwnerExternalId() {
        return roleOwnerExternalId;
    }

    /**
     * Sets the value of the roleOwnerExternalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoleOwnerExternalId(String value) {
        this.roleOwnerExternalId = value;
    }

    /**
     * Gets the value of the roleResources property.
     * 
     * @return
     *     possible object is
     *     {@link RoleResourcesType }
     *     
     */
    public RoleResourcesType getRoleResources() {
        return roleResources;
    }

    /**
     * Sets the value of the roleResources property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoleResourcesType }
     *     
     */
    public void setRoleResources(RoleResourcesType value) {
        this.roleResources = value;
    }

}
