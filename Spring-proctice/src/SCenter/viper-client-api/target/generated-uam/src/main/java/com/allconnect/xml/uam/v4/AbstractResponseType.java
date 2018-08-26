
package com.A.xml.uam.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for abstractResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="abstractResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "abstractResponseType")
@XmlSeeAlso({
    DetachRoleFromUserResponseType.class,
    DetachResourceFromUserResponseType.class,
    CreateUserResponseType.class,
    AttachResourceToRoleResponseType.class,
    AttachResourceToUserResponseType.class,
    CreateResourceResponseType.class,
    ValidateUserResponseType.class,
    AttachRoleToUserResponseType.class,
    DetachResourceFromRoleResponseType.class,
    CreateRoleResponseType.class,
    AuthenticateUserResponseType.class
})
public abstract class AbstractResponseType {


}
