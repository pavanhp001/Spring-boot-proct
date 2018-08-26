
package com.A.xml.uam.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ValidateUserResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValidateUserResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="validatedUsersList" type="{http://xml.A.com/v4}ValidatedUsers" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidateUserResponse", propOrder = {
    "validatedUsersList"
})
public class ValidateUserResponse {

    protected List<ValidatedUsers> validatedUsersList;

    /**
     * Gets the value of the validatedUsersList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the validatedUsersList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValidatedUsersList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ValidatedUsers }
     * 
     * 
     */
    public List<ValidatedUsers> getValidatedUsersList() {
        if (validatedUsersList == null) {
            validatedUsersList = new ArrayList<ValidatedUsers>();
        }
        return this.validatedUsersList;
    }

}
