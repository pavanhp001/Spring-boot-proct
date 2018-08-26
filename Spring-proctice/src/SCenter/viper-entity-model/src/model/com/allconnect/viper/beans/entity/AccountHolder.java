package com.A.V.beans.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.A.V.crypt.DefaultDecryptListener;
import com.A.V.crypt.DefaultEncryptListener;
import com.A.V.interfaces.CommonBeanInterface;

@Entity
@Table(name = "OM_LI_ACCNT_HOLDER")
public class AccountHolder implements CommonBeanInterface {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5567008446379901579L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "liAccntHolderSequence" )
	@SequenceGenerator(name = "liAccntHolderSequence", sequenceName = "OM_LI_ACCNT_HOLDER_SEQ",allocationSize = 1)
	private long id;

	@Column(name = "order_external_id")
	private long orderExternalId;
	
	@Column(name = "first_name")
	private String firstName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "name_suffix")
	private String nameSuffix;

	private String ssn;
	
	@Column(name = "ssn_last_four")
	private String ssnLastFour;

	@Column(name = "best_contact")
	private String bestContact;
	
	@Column(name = "best_contact_phone_type")
	private String bestContactPhoneType;

	@Column(name = "best_email")
	private String bestEmail;
	
	private String dob;

	@Column(name = "driver_license_number")
	private String driverLicenseNumber;

	@Column(name = "driver_license_state")
	private String driverLicenseState;

	@Column(name = "driver_license_exp_date")
	private Calendar driverLicenseExpDate;
	
	@Column(name = "security_question")
	private String securityQuestion;
	
	@Column(name = "security_answer")
	private String securityAnswer;
	
	@Column(name = "security_pin")
	private String securityPin;
	
	@Column(name = "is_dt_customer")
	private boolean dtCustomer;
	
	@Column(name = "second_contact")
	private String secondContact;
	
	@Column(name = "second_contact_phone_type")
	private String secondContactPhoneType;
	
	@Column(name="acct_holder_unique_id")
	private String accountHolderUniqueId;

	public String getFirstName() {
		return firstName;
	}
	
	@PostLoad
	@PostPersist
	@PostUpdate
	public void decryptData() {
		//commenting due to ingrain issue
	   DefaultDecryptListener.INSTANCE.decrypt(this);
	} 
	
	@PrePersist
	@PreUpdate
	public void encryptData() {
		//commenting due to ingrain issue
		DefaultEncryptListener.INSTANCE.encrypt(this);
	} 

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getBestContact() {
		return bestContact;
	}

	public void setBestContact(String bestContact) {
		this.bestContact = bestContact;
	}

	public String getBestEmail() {
		return bestEmail;
	}

	public void setBestEmail(String bestEmail) {
		this.bestEmail = bestEmail;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getDriverLicenseNumber() {
		return driverLicenseNumber;
	}

	public void setDriverLicenseNumber(String driverLicenseNumber) {
		this.driverLicenseNumber = driverLicenseNumber;
	}

	public String getDriverLicenseState() {
		return driverLicenseState;
	}

	public void setDriverLicenseState(String driverLicenseState) {
		this.driverLicenseState = driverLicenseState;
	}

	public Calendar getDriverLicenseExpDate() {
		return driverLicenseExpDate;
	}

	public void setDriverLicenseExpDate(Calendar driverLicenseExpDate) {
		this.driverLicenseExpDate = driverLicenseExpDate;
	}

	public long getOrderExternalId() {
		return orderExternalId;
	}

	public void setOrderExternalId(long orderExternalId) {
		this.orderExternalId = orderExternalId;
	}

	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}
	
	public String getSecurityQuestion() {
		return securityQuestion;
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public String getSecurityAnswer() {
		return securityAnswer;
	}

	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	public String getSecurityPin() {
		return securityPin;
	}

	public void setSecurityPin(String securityPin) {
		this.securityPin = securityPin;
	}
	
	public String getNameSuffix() {
		return nameSuffix;
	}

	public void setNameSuffix(String nameSuffix) {
		this.nameSuffix = nameSuffix;
	}

	public boolean isDtCustomer() {
		return dtCustomer;
	}

	public void setDtCustomer(boolean dtCustomer) {
		this.dtCustomer = dtCustomer;
	}

	public String getSecondContact() {
		return secondContact;
	}

	public void setSecondContact(String secondContact) {
		this.secondContact = secondContact;
	}
	
	public String getAccountHolderUniqueId() {
		return accountHolderUniqueId;
	}

	public void setAccountHolderUniqueId(String accountHolderUniqueId) {
		this.accountHolderUniqueId = accountHolderUniqueId;
	}
	
	public String getBestContactPhoneType() {
		return bestContactPhoneType;
	}

	public void setBestContactPhoneType(String bestContactPhoneType) {
		this.bestContactPhoneType = bestContactPhoneType;
	}

	public String getSecondContactPhoneType() {
		return secondContactPhoneType;
	}

	public void setSecondContactPhoneType(String secondContactPhoneType) {
		this.secondContactPhoneType = secondContactPhoneType;
	}
	

	public String getSsnLastFour() {
		return ssnLastFour;
	}

	public void setSsnLastFour(String ssnLastFour) {
		this.ssnLastFour = ssnLastFour;
	}

	@Override
	public String toString() {
		return "AccountHolder [id=" + id + ", accountHolderUniqueId=" + accountHolderUniqueId
				+ ", firstName=" + firstName + ", middleName=" + middleName
				+ ", lastName=" + lastName + ", ssn=" + ssn + ", bestContact="
				+ bestContact + ", bestEmail=" + bestEmail + ", dob=" + dob
				+ ", driverLicenseNumber=" + driverLicenseNumber
				+ ", driverLicenseState=" + driverLicenseState
				+ ", driverLicenseExpDate=" + driverLicenseExpDate 
				+ ", securityPin=" + securityPin
				+ ", securityQuestion=" + securityQuestion 
				+ ", securityAnswer=" + securityAnswer+"]";
	}
	
	

}
