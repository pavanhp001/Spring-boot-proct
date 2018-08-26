
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for STIOrderQualResponseContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="STIOrderQualResponseContainer">
 *   &lt;complexContent>
 *     &lt;extension base="{http://xml.A.com/v4}ProviderResponseContainer">
 *       &lt;sequence>
 *         &lt;element name="verificationQuestion" type="{http://xml.A.com/v4}verificationQuestion" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="providerAttributes" type="{http://xml.A.com/v4}ProviderAttributes" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "STIOrderQualResponseContainer", propOrder = {
    "verificationQuestion",
    "providerAttributes"
})
public class STIOrderQualResponseContainer
    extends ProviderResponseContainer
{

    protected List<VerificationQuestion> verificationQuestion;
    protected List<ProviderAttributes> providerAttributes;

    /**
     * Gets the value of the verificationQuestion property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the verificationQuestion property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVerificationQuestion().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VerificationQuestion }
     * 
     * 
     */
    public List<VerificationQuestion> getVerificationQuestion() {
        if (verificationQuestion == null) {
            verificationQuestion = new ArrayList<VerificationQuestion>();
        }
        return this.verificationQuestion;
    }

    /**
     * Gets the value of the providerAttributes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the providerAttributes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProviderAttributes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProviderAttributes }
     * 
     * 
     */
    public List<ProviderAttributes> getProviderAttributes() {
        if (providerAttributes == null) {
            providerAttributes = new ArrayList<ProviderAttributes>();
        }
        return this.providerAttributes;
    }

}
