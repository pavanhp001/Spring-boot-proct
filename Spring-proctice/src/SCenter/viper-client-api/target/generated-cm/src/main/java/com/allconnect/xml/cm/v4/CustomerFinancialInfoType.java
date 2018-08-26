
package com.A.xml.cm.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for customerFinancialInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="customerFinancialInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="employed" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="businessName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                     &lt;element name="businessPhoneNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                     &lt;element name="occupation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                     &lt;element name="howLongWithCurrentEmployerMonth" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                     &lt;element name="howLongWithCurrentEmployerYear" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="unemployed" type="{http://xml.A.com/v4}emptyElementType" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;element name="student" type="{http://xml.A.com/v4}emptyElementType" minOccurs="0"/>
 *         &lt;element name="retired" type="{http://xml.A.com/v4}emptyElementType" minOccurs="0"/>
 *         &lt;element name="bankOrMortgageInstitution" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="otherIncomeSource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "customerFinancialInfoType", propOrder = {
    "employed",
    "unemployed",
    "student",
    "retired",
    "bankOrMortgageInstitution",
    "otherIncomeSource"
})
public class CustomerFinancialInfoType {

    protected CustomerFinancialInfoType.Employed employed;
    protected EmptyElementType unemployed;
    protected EmptyElementType student;
    protected EmptyElementType retired;
    protected String bankOrMortgageInstitution;
    protected String otherIncomeSource;

    /**
     * Gets the value of the employed property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerFinancialInfoType.Employed }
     *     
     */
    public CustomerFinancialInfoType.Employed getEmployed() {
        return employed;
    }

    /**
     * Sets the value of the employed property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerFinancialInfoType.Employed }
     *     
     */
    public void setEmployed(CustomerFinancialInfoType.Employed value) {
        this.employed = value;
    }

    /**
     * Gets the value of the unemployed property.
     * 
     * @return
     *     possible object is
     *     {@link EmptyElementType }
     *     
     */
    public EmptyElementType getUnemployed() {
        return unemployed;
    }

    /**
     * Sets the value of the unemployed property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmptyElementType }
     *     
     */
    public void setUnemployed(EmptyElementType value) {
        this.unemployed = value;
    }

    /**
     * Gets the value of the student property.
     * 
     * @return
     *     possible object is
     *     {@link EmptyElementType }
     *     
     */
    public EmptyElementType getStudent() {
        return student;
    }

    /**
     * Sets the value of the student property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmptyElementType }
     *     
     */
    public void setStudent(EmptyElementType value) {
        this.student = value;
    }

    /**
     * Gets the value of the retired property.
     * 
     * @return
     *     possible object is
     *     {@link EmptyElementType }
     *     
     */
    public EmptyElementType getRetired() {
        return retired;
    }

    /**
     * Sets the value of the retired property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmptyElementType }
     *     
     */
    public void setRetired(EmptyElementType value) {
        this.retired = value;
    }

    /**
     * Gets the value of the bankOrMortgageInstitution property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankOrMortgageInstitution() {
        return bankOrMortgageInstitution;
    }

    /**
     * Sets the value of the bankOrMortgageInstitution property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankOrMortgageInstitution(String value) {
        this.bankOrMortgageInstitution = value;
    }

    /**
     * Gets the value of the otherIncomeSource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherIncomeSource() {
        return otherIncomeSource;
    }

    /**
     * Sets the value of the otherIncomeSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherIncomeSource(String value) {
        this.otherIncomeSource = value;
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
     *         &lt;element name="businessName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="businessPhoneNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="occupation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="howLongWithCurrentEmployerMonth" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="howLongWithCurrentEmployerYear" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "businessName",
        "businessPhoneNum",
        "occupation",
        "howLongWithCurrentEmployerMonth",
        "howLongWithCurrentEmployerYear"
    })
    public static class Employed {

        protected String businessName;
        protected String businessPhoneNum;
        protected String occupation;
        @XmlElement(defaultValue = "0")
        protected String howLongWithCurrentEmployerMonth;
        @XmlElement(defaultValue = "0")
        protected String howLongWithCurrentEmployerYear;

        /**
         * Gets the value of the businessName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBusinessName() {
            return businessName;
        }

        /**
         * Sets the value of the businessName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBusinessName(String value) {
            this.businessName = value;
        }

        /**
         * Gets the value of the businessPhoneNum property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBusinessPhoneNum() {
            return businessPhoneNum;
        }

        /**
         * Sets the value of the businessPhoneNum property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBusinessPhoneNum(String value) {
            this.businessPhoneNum = value;
        }

        /**
         * Gets the value of the occupation property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOccupation() {
            return occupation;
        }

        /**
         * Sets the value of the occupation property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOccupation(String value) {
            this.occupation = value;
        }

        /**
         * Gets the value of the howLongWithCurrentEmployerMonth property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHowLongWithCurrentEmployerMonth() {
            return howLongWithCurrentEmployerMonth;
        }

        /**
         * Sets the value of the howLongWithCurrentEmployerMonth property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHowLongWithCurrentEmployerMonth(String value) {
            this.howLongWithCurrentEmployerMonth = value;
        }

        /**
         * Gets the value of the howLongWithCurrentEmployerYear property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHowLongWithCurrentEmployerYear() {
            return howLongWithCurrentEmployerYear;
        }

        /**
         * Sets the value of the howLongWithCurrentEmployerYear property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHowLongWithCurrentEmployerYear(String value) {
            this.howLongWithCurrentEmployerYear = value;
        }

    }

}
