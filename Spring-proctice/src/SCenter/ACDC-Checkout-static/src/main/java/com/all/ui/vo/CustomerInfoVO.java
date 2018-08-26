/**
 * 
 */
package com.AL.ui.vo;

import javax.xml.datatype.XMLGregorianCalendar;

import com.AL.xml.v4.LineItemSelectionType;
import com.AL.xml.v4.SelectionChoiceType;

/**
 * @author prabhu
 *
 */
public class CustomerInfoVO {
	
    protected String btn;
    protected String firstName;
    protected String lastName;
    protected String middleName;
    protected String title;
	protected String nameSuffix;
    protected String driverLicense;
    protected String state;
    protected String financialInstitutionName;
    protected String employerName;
    protected String emailAddress;
    protected String employerTelephone;
    protected String occupation;
    protected String retired;
    protected String rentOrOwn;
    protected String fullTimeStudent;
    protected String otherIncomeSource;
    protected String homePhoneNumber;
    protected String workPhoneNumber;
    protected String workExtensionNumber;
    protected String cellPhoneNumber;
    protected String addressUniqueId;
    protected String status;
    protected String externalId;
    protected String countryId;
    protected String customerType;
    protected String tn;
    protected String tpvHoldStatus;
    protected String validationLevel;
    protected String lineProdCatgValidationLevel;
    protected String lineNumber;
    protected String productType;
	protected String streetNumber;
    protected String streetName;
    protected String city;
    protected String stateOrProvince;
    protected String postalCode;
    protected String dob;
    protected XMLGregorianCalendar xmlGregDOB;
    protected String ssn;
    protected boolean isEmployed;
    protected String checkPoint;
    protected boolean isBillingAddressSame;
    protected String servAddrRef;
    protected String howLongAtPreviousAddress;
    protected String creditCardNumber;
    protected com.AL.xml.v4.CreditCardTypeType creditCardType;
    protected String moveInDate;
	protected String creditCardExpiration;
	protected AddressFields addrFields;
	protected String phoneNumberType;
	protected boolean isPhoneContactOptIn;
	protected boolean isMarketingOptIn;
	protected String contractAccountNumber;
	/**
	 * @return the btn
	 */
	public String getBtn() {
		return btn;
	}
	/**
	 * @param btn the btn to set
	 */
	public void setBtn(String btn) {
		this.btn = btn;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}
	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the nameSuffix
	 */
	public String getNameSuffix() {
		return nameSuffix;
	}
	/**
	 * @param nameSuffix the nameSuffix to set
	 */
	public void setNameSuffix(String nameSuffix) {
		this.nameSuffix = nameSuffix;
	}
	/**
	 * @return the driverLicense
	 */
	public String getDriverLicense() {
		return driverLicense;
	}
	/**
	 * @param driverLicense the driverLicense to set
	 */
	public void setDriverLicense(String driverLicense) {
		this.driverLicense = driverLicense;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the financialInstitutionName
	 */
	public String getFinancialInstitutionName() {
		return financialInstitutionName;
	}
	/**
	 * @param financialInstitutionName the financialInstitutionName to set
	 */
	public void setFinancialInstitutionName(String financialInstitutionName) {
		this.financialInstitutionName = financialInstitutionName;
	}
	/**
	 * @return the employerName
	 */
	public String getEmployerName() {
		return employerName;
	}
	/**
	 * @param employerName the employerName to set
	 */
	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}
	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}
	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	/**
	 * @return the employerTelephone
	 */
	public String getEmployerTelephone() {
		return employerTelephone;
	}
	/**
	 * @param employerTelephone the employerTelephone to set
	 */
	public void setEmployerTelephone(String employerTelephone) {
		this.employerTelephone = employerTelephone;
	}
	/**
	 * @return the occupation
	 */
	public String getOccupation() {
		return occupation;
	}
	/**
	 * @param occupation the occupation to set
	 */
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	/**
	 * @return the retired
	 */
	public String getRetired() {
		return retired;
	}
	/**
	 * @param retired the retired to set
	 */
	public void setRetired(String retired) {
		this.retired = retired;
	}
	/**
	 * @return the rentOrOwn
	 */
	public String getRentOrOwn() {
		return rentOrOwn;
	}
	/**
	 * @param rentOrOwn the rentOrOwn to set
	 */
	public void setRentOrOwn(String rentOrOwn) {
		this.rentOrOwn = rentOrOwn;
	}
	/**
	 * @return the fullTimeStudent
	 */
	public String getFullTimeStudent() {
		return fullTimeStudent;
	}
	/**
	 * @param fullTimeStudent the fullTimeStudent to set
	 */
	public void setFullTimeStudent(String fullTimeStudent) {
		this.fullTimeStudent = fullTimeStudent;
	}
	/**
	 * @return the otherIncomeSource
	 */
	public String getOtherIncomeSource() {
		return otherIncomeSource;
	}
	/**
	 * @param otherIncomeSource the otherIncomeSource to set
	 */
	public void setOtherIncomeSource(String otherIncomeSource) {
		this.otherIncomeSource = otherIncomeSource;
	}
	/**
	 * @return the contactNumber
	 */
	public String getHomePhoneNumber() {
		return homePhoneNumber;
	}
	/**
	 * @param contactNumber the contactNumber to set
	 */
	public void setHomePhoneNumber(String homePhoneNumber) {
		this.homePhoneNumber = homePhoneNumber;
	}
	/**
	 * @return the workContactNumber
	 */
	public String getWorkPhoneNumber() {
		return workPhoneNumber;
	}
	/**
	 * @param workPhoneNumber the workContactNumber to set
	 */
	public void setWorkPhoneNumber(String workPhoneNumber) {
		this.workPhoneNumber = workPhoneNumber;
	}
	/**
	 * @return the cellContactNumber
	 */
	public String getCellPhoneNumber() {
		return cellPhoneNumber;
	}
	/**
	 * @param cellPhoneNumber the cellContactNumber to set
	 */
	public void setCellPhoneNumber(String cellPhoneNumber) {
		this.cellPhoneNumber = cellPhoneNumber;
	}
	/**
	 * @return the addressUniqueId
	 */
	public String getAddressUniqueId() {
		return addressUniqueId;
	}
	/**
	 * @param addressUniqueId the addressUniqueId to set
	 */
	public void setAddressUniqueId(String addressUniqueId) {
		this.addressUniqueId = addressUniqueId;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the externalId
	 */
	public String getExternalId() {
		return externalId;
	}
	/**
	 * @param externalId the externalId to set
	 */
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	/**
	 * @return the countryId
	 */
	public String getCountryId() {
		return countryId;
	}
	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	/**
	 * @return the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}
	/**
	 * @param customerType the customerType to set
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	/**
	 * @return the tn
	 */
	public String getTn() {
		return tn;
	}
	/**
	 * @param tn the tn to set
	 */
	public void setTn(String tn) {
		this.tn = tn;
	}
	/**
	 * @return the tpvHoldStatus
	 */
	public String getTpvHoldStatus() {
		return tpvHoldStatus;
	}
	/**
	 * @param tpvHoldStatus the tpvHoldStatus to set
	 */
	public void setTpvHoldStatus(String tpvHoldStatus) {
		this.tpvHoldStatus = tpvHoldStatus;
	}
	/**
	 * @return the validationLevel
	 */
	public String getValidationLevel() {
		return validationLevel;
	}
	/**
	 * @param validationLevel the validationLevel to set
	 */
	public void setValidationLevel(String validationLevel) {
		this.validationLevel = validationLevel;
	}
	/**
	 * @return the lineProdCatgValidationLevel
	 */
	public String getLineProdCatgValidationLevel() {
		return lineProdCatgValidationLevel;
	}
	/**
	 * @param lineProdCatgValidationLevel the lineProdCatgValidationLevel to set
	 */
	public void setLineProdCatgValidationLevel(String lineProdCatgValidationLevel) {
		this.lineProdCatgValidationLevel = lineProdCatgValidationLevel;
	}
	/**
	 * @return the lineNumber
	 */
	public String getLineNumber() {
		return lineNumber;
	}
	/**
	 * @param lineNumber the lineNumber to set
	 */
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}
	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	} 
    
	/**
	 * @return the streetNumber
	 */
	public String getStreetNumber() {
		return streetNumber;
	}
	/**
	 * @param streetNumber the streetNumber to set
	 */
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}
	/**
	 * @return the streetName
	 */
	public String getStreetName() {
		return streetName;
	}
	/**
	 * @param streetName the streetName to set
	 */
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the stateOrProvince
	 */
	public String getStateOrProvince() {
		return stateOrProvince;
	}
	/**
	 * @param stateOrProvince the stateOrProvince to set
	 */
	public void setStateOrProvince(String stateOrProvince) {
		this.stateOrProvince = stateOrProvince;
	}
	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}
	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	/**
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}
	/**
	 * @param dob the dob to set
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}
	/**
	 * @return the ssn
	 */
	public String getSsn() {
		return ssn;
	}
	/**
	 * @param ssn the ssn to set
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	
	/**
	 * @return the isEmployed
	 */
	public boolean isEmployed() {
		return isEmployed;
	}
	/**
	 * @param isEmployed the isEmployed to set
	 */
	public void setEmployed(boolean isEmployed) {
		this.isEmployed = isEmployed;
	}
	
	/**
	 * @return the checkPoint
	 */
	public String getCheckPoint() {
		return checkPoint;
	}
	/**
	 * @param checkPoint the checkPoint to set
	 */
	public void setCheckPoint(String checkPoint) {
		this.checkPoint = checkPoint;
	}
	public boolean isBillingAddressSame() {
		return isBillingAddressSame;
	}
	public void setBillingAddressSame(boolean isBillingAddressSame) {
		this.isBillingAddressSame = isBillingAddressSame;
	}
	public String getServAddrRef() {
		return servAddrRef;
	}
	public void setServAddrRef(String servAddrRef) {
		this.servAddrRef = servAddrRef;
	}
	public String getWorkExtensionNumber() {
		return workExtensionNumber;
	}
	public void setWorkExtensionNumber(String workExtensionNumber) {
		this.workExtensionNumber = workExtensionNumber;
	}
	
	
	
	
	public String getHowLongAtPreviousAddress() {
		return howLongAtPreviousAddress;
	}
	public void setHowLongAtPreviousAddress(String howLongAtPreviousAddress) {
		this.howLongAtPreviousAddress = howLongAtPreviousAddress;
	}
	
	/**
	 * @return the xmlGregDOB
	 */
	public XMLGregorianCalendar getXmlGregDOB() {
		return xmlGregDOB;
	}
	/**
	 * @param xmlGregDOB the xmlGregDOB to set
	 */
	public void setXmlGregDOB(XMLGregorianCalendar xmlGregDOB) {
		this.xmlGregDOB = xmlGregDOB;
	}
	
	/**
	 * @return the creditCardNumber
	 */
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	/**
	 * @param creditCardNumber the creditCardNumber to set
	 */
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	
	/**
	 * @return the creditCardType
	 */
	public com.AL.xml.v4.CreditCardTypeType getCreditCardType() {
		return creditCardType;
	}
	/**
	 * @param creditCardType the creditCardType to set
	 */
	public void setCreditCardType(
			com.AL.xml.v4.CreditCardTypeType creditCardType) {
		this.creditCardType = creditCardType;
	}
	/**
	 * @return the creditCardExpiration
	 */
	public String getCreditCardExpiration() {
		return creditCardExpiration;
	}
	/**
	 * @param creditCardExpiration the creditCardExpiration to set
	 */
	public void setCreditCardExpiration(String creditCardExpiration) {
		this.creditCardExpiration = creditCardExpiration;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("FirstName: " + this.firstName);
		sb.append("\nLastName: " + this.lastName);
		sb.append("\nMiddleName: " + this.middleName);
		sb.append("\nTitle: " + this.title);
		sb.append("\nNameSuffix: " + this.nameSuffix);
		sb.append("\nDriverLicense: " + this.driverLicense);
		sb.append("\nState: " + this.state);
		sb.append("\nFinancialInstitutionName: " + this.financialInstitutionName);
		sb.append("\nEmployerName: " + this.employerName);
		sb.append("\nEmailAddress: " + this.emailAddress);
		sb.append("\nEmployerTelephone: " + this.employerTelephone);
		sb.append("\nOccupation: " + this.occupation);
		sb.append("\nRetired: " + this.retired);
		sb.append("\nRentOrOwn: " + this.rentOrOwn);
		sb.append("\nFullTimeStudent: " + this.fullTimeStudent);
		sb.append("\nOtherIncomeSource: " + this.otherIncomeSource);
		sb.append("\nContactNumber: " + this.homePhoneNumber);
		sb.append("\nWorkContactNumber: " + this.workPhoneNumber);
		sb.append("\nWorkContactNumber: " + this.workExtensionNumber);
		sb.append("\nCellContactNumber: " + this.cellPhoneNumber);
		sb.append("\nAddressUniqueId: " + this.addressUniqueId);
		sb.append("\nStatus: " + this.status);
		sb.append("\nExternalId: " + this.externalId);
		sb.append("\nCountryId: " + this.countryId);
		sb.append("\nCustomerType: " + this.customerType);
		sb.append("\nTN: " + this.tn);
		sb.append("\nTPVHoldStatus: " + this.tpvHoldStatus);
		sb.append("\nValidationLevel: " + this.validationLevel);
		sb.append("\nLineProdCatgValidationLevel: " + this.lineProdCatgValidationLevel);
		sb.append("\nLineNumber: " + this.lineNumber);
		sb.append("\nStreetNumber: " + this.streetNumber);
		sb.append("\nStreetName: " + this.streetName);
		sb.append("\nCity: " + this.city);
		sb.append("\nStateOrProvince: " + this.stateOrProvince);
		sb.append("\nPostalCode: " + this.postalCode);		
		return sb.toString();
	}
	/**
	 * @return the moveInDate
	 */
	public String getMoveInDate() {
		return moveInDate;
	}
	/**
	 * @param moveInDate the moveInDate to set
	 */
	public void setMoveInDate(String moveInDate) {
		this.moveInDate = moveInDate;
	}
	/**
	 * @return the addrFields
	 */
	public AddressFields getAddrFields() {
		return addrFields;
	}
	/**
	 * @param addrFields the addrFields to set
	 */
	public void setAddrFields(AddressFields addrFields) {
		this.addrFields = addrFields;
	}
	public String getPhoneNumberType() {
		return phoneNumberType;
	}
	public void setPhoneNumberType(String phoneNumberType) {
		this.phoneNumberType = phoneNumberType;
	}
	public boolean isPhoneContactOptIn() {
		return isPhoneContactOptIn;
	}
	public void setPhoneContactOptIn(boolean isPhoneContactOptIn) {
		this.isPhoneContactOptIn = isPhoneContactOptIn;
	}
	public boolean isMarketingOptIn() {
		return isMarketingOptIn;
	}
	public void setMarketingOptIn(boolean isMarketingOptIn) {
		this.isMarketingOptIn = isMarketingOptIn;
	}  
	public String getContractAccountNumber() {
		return contractAccountNumber;
	}
	public void setContractAccountNumber(String contractAccountNumber) {
		this.contractAccountNumber = contractAccountNumber;
	}
}
