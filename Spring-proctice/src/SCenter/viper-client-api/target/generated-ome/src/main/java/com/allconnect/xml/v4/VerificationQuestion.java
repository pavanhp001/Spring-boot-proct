
package com.A.xml.v4;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for verificationQuestion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="verificationQuestion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="questionId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="questionText" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="answerOption" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="answerId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="answerText" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "verificationQuestion", propOrder = {
    "questionId",
    "questionText",
    "answerOption"
})
public class VerificationQuestion {

    @XmlElement(required = true)
    protected String questionId;
    @XmlElement(required = true)
    protected String questionText;
    @XmlElement(required = true)
    protected List<VerificationQuestion.AnswerOption> answerOption;

    /**
     * Gets the value of the questionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuestionId() {
        return questionId;
    }

    /**
     * Sets the value of the questionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuestionId(String value) {
        this.questionId = value;
    }

    /**
     * Gets the value of the questionText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * Sets the value of the questionText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuestionText(String value) {
        this.questionText = value;
    }

    /**
     * Gets the value of the answerOption property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the answerOption property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAnswerOption().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VerificationQuestion.AnswerOption }
     * 
     * 
     */
    public List<VerificationQuestion.AnswerOption> getAnswerOption() {
        if (answerOption == null) {
            answerOption = new ArrayList<VerificationQuestion.AnswerOption>();
        }
        return this.answerOption;
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
     *         &lt;element name="answerId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="answerText" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "answerId",
        "answerText"
    })
    public static class AnswerOption {

        @XmlElement(required = true)
        protected String answerId;
        @XmlElement(required = true)
        protected String answerText;

        /**
         * Gets the value of the answerId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAnswerId() {
            return answerId;
        }

        /**
         * Sets the value of the answerId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAnswerId(String value) {
            this.answerId = value;
        }

        /**
         * Gets the value of the answerText property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAnswerText() {
            return answerText;
        }

        /**
         * Sets the value of the answerText property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAnswerText(String value) {
            this.answerText = value;
        }

    }

}
