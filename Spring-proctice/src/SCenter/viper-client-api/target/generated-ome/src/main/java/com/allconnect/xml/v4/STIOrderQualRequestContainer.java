
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for STIOrderQualRequestContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="STIOrderQualRequestContainer">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}ProviderRequestContainer">
 *       &lt;sequence>
 *         &lt;element name="verificationAnswer" type="{http://xml.A.com/v4}verificationAnswer" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "STIOrderQualRequestContainer", propOrder = {
    "verificationAnswer"
})
public class STIOrderQualRequestContainer
    extends ProviderRequestContainer
{

    protected List<VerificationAnswer> verificationAnswer;

    /**
     * Gets the value of the verificationAnswer property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the verificationAnswer property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVerificationAnswer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VerificationAnswer }
     * 
     * 
     */
    public List<VerificationAnswer> getVerificationAnswer() {
        if (verificationAnswer == null) {
            verificationAnswer = new ArrayList<VerificationAnswer>();
        }
        return this.verificationAnswer;
    }

}
