
package com.A.xml.cm.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for csatSurveysType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="csatSurveysType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="csatSurvey" type="{http://xml.A.com/v4}csatSurveyType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "csatSurveysType", propOrder = {
    "csatSurvey"
})
public class CsatSurveysType {

    protected List<CsatSurveyType> csatSurvey;

    /**
     * Gets the value of the csatSurvey property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the csatSurvey property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCsatSurvey().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CsatSurveyType }
     * 
     * 
     */
    public List<CsatSurveyType> getCsatSurvey() {
        if (csatSurvey == null) {
            csatSurvey = new ArrayList<CsatSurveyType>();
        }
        return this.csatSurvey;
    }

}
