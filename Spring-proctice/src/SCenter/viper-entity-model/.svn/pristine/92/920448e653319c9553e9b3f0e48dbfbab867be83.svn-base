/**
 *
 */
package com.A.V.beans.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.GenerationType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.validator.Length;

import com.A.V.Constants;
//import com.A.V.beans.ContactSuperClass;
import com.A.V.crypt.DefaultDecryptListener;
import com.A.V.interfaces.CommonBeanInterface;

/**
 * @author ebaugh
 *
 */

@Entity
@Table(name = "CM_CONSUMER")
public class Consumer implements CommonBeanInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1357824341448331957L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "consumerBeanSequence")
	@SequenceGenerator(name = "consumerBeanSequence", sequenceName = "CM_CONSUMER_BEAN_SEQ",allocationSize = 1)
	@Column(name = "CONSUMER_ID")
	private long id;

	@Column(name = "EXTERNAL_ID")
	private Long externalId;

	@Column(name = "REFERRER_ID")
	private Long referrerId;

	@Column(name = "PARTNER_ID")
	private String partnerId;

	@Column(name = "CUSTOMER_CREATE_DT")
	private Calendar customerCreateDate;

	@Column(name = "REFERRER_GENERAL_NAME")
	private String referrerGeneralName;

	@Column(name = "DT_AGENT_ID")
	private String dtAgentId;

	@Column(name = "DT_CREATED")
	private Calendar dtCreated;

	@Column(name = "PARTNER_ACCT_ID")
	private String partnerAccountId;

	@Column(name = "AGENT_ID")
	private String agentId;

	@Column(name = "BIRTH_INFO")
	private String birthInfo;

	@Transient
	private Calendar dob;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "SSN_LAST_FOUR")
	private String ssnLastFour;

	@Column(name = "A_CUST_NUM")
	private String ACustomerNumber;

	private Boolean directMailOptIn;

	private Boolean EMailOptIn;

	@Column(name = "NON_BUYER_OPT_IN")
	private Boolean nonBuyerWebOptIn;

	@Column(name = "MARKETING_OPT_IN")
	private Boolean marketingOptIn;

	@Column(name = "PRIMARY_LANGUAGE")
	private int primaryLanguage;

	@Column(name = "BEST_TIME_TO_CALL1")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar bestTimeToCall1;

	@Column(name = "BEST_TIME_TO_CALL2")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar bestTimeToCall2;

	@Column(name = "BEST_TIME_TO_CALL_PHN")
	private String bestTimeToCallPhone;

	@Column(name = "BEST_EMAIL_CONTACT")
	private String bestEmailContact;

	@Column(name = "BEST_PHONE_CONTACT")
	private String bestPhoneContact;

	@Column(name = "SECOND_PHONE")
	private String secondPhone;

	@Column(name = "PARTNER_NAME")
	private String partnerName;

	@Column(name = "PARTNER_SSN")
	private String partnerSSN;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "consumer", cascade = CascadeType.PERSIST)
	private Set<BillingInformation> billingInfoList;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.consumer", cascade = CascadeType.PERSIST)
	private Set<CustomerAddressAssociation> addresses = new HashSet<CustomerAddressAssociation>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "consumer", cascade = CascadeType.PERSIST)
	private Set<Account> accounts;

	@Transient
	private List<AddressBean> consumerAddressList = new ArrayList<AddressBean>();

	@Transient
	private PhoneContactChannel homePhone;

	@Transient
	private PhoneContactChannel cellPhone;

	@Transient
	private PhoneContactChannel workPhone;

	@Transient
	private EMailContactChannel homeEMail;

	@Transient
	private EMailContactChannel workEMail;

	@Column(name = "HOME_PHONE_VALUE")
	private String homePhoneValue;

	@Column(name = "CUSTOMER_TYPE")
	private String customerType;

	@Column(name = "CELL_PHONE_VALUE")
	private String cellPhoneValue;

	@Column(name = "WORK_PHONE_VALUE")
	private String workPhoneValue;

	@Column(name = "WORK_PHONE_EXTN")
	private Integer workPhoneExtn;
	
	@Column(name = "HOME_EMAIL_VALUE")
	private String homeEmailValue;

	@Column(name = "WORK_EMAIL_VALUE")
	private String workEmailValue;

	@Column(name = "BILL_DELIVERY_PREF")
	private String billingDeliveryPreference;

	@Column(name = "PIN")
	private String pin;

	@Column(name = "SECURITY_QUESTION")
	private String securityQuestion;

	@Column(name = "SECURITY_ANSWER")
	private String securityAnswer;

	@Column(name = "IS_EMPLOYED")
	private boolean employed;

	@Column(name = "EMPLOYER_BUS_NAME")
	private String employerBusinessName;

	@Column(name = "EMPLOYER_PHONE_NUM")
	private String employerPhoneNumber;

	@Column(name = "OCCUPATION")
	private String occupation;

	@Column(name = "IS_STUDENT")
	private boolean student;

	@Column(name = "IS_RETIRED")
	private boolean retired;

	@Column(name = "BANK")
	private String bankOrMortgageInstitution;

	@Column(name = "CURRENT_EMPLOYED_MONTH")
	private String currentEmployedMonth;

	@Column(name = "CURRENT_EMPLOYED_YEAR")
	private String currentEmployedYear;

	@Column(name = "OTHER_INCOME")
	private String otherIncomeSource;

	@Column(name = "REGION")
	private String region;

	@Column(name = "LANDLORD_NAME")
	private String landlordName;

	@Column(name = "LANDLORD_PHONE_NO")
	private String landlordPhoneNumber;

	@Column(name = "DRIVER_LICENSE_NO")
	private String driverLicenseNo;

	@Column(name = "LIC_EXPIRATION_DATE")
	private Calendar licenseExpirationDate;

	@Column(name = "LIC_STATE")
	private String licenseState;

	@Column(name = "STATE_ID_NO")
	private String stateIdNo;

	@Column(name = "ID_STATE")
	private String idState;

	@Column(name = "CONSUMER_STATUS")
	private String status;

	@Column(name = "PHONE_CONTACT_OPTIN")
	private boolean phoneContactOptIn;

	@Column(name = "EMAIL_PROD_UPDATE_OPTIN")
	private boolean eMailProductUpdatesOptIn;
	
	@Column(name = "CONTRACT_ACC_NUM")
    private String contractAccountNumber;

	//@OneToMany(cascade = CascadeType.ALL)
	@OneToMany
	@JoinColumn(name = "CONSUMER_ID", referencedColumnName = "CONSUMER_ID")
	//@JoinTable
	//@CollectionTable
	//@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<CustomerContext> customerContexts;

	@OneToMany
	@JoinColumn(name = "CONSUMER_ID", referencedColumnName = "CONSUMER_ID")
	private Set<CustomerPaymentEvent> paymentEvents;

	//@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "CONSUMER_ID", referencedColumnName = "CONSUMER_ID")
	//@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Set<CustomerAttribute> customerAttributes;


	@Column(name = "OFFERS_PRESENTED")
	private String offersPresented;

	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "CONSUMER_ID", referencedColumnName = "CONSUMER_ID")
	/*@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)*/
	private Set<CustomerSurvey> customerCsatSurveys;
	
	/* added from contactSuperClass */
	@Column(name="title")
    private String title; 
    @Column(name="firstname")
    private String firstName;
    @Column(name="lastname")
    private String lastName;
    @Column(name="middlename")
    private String middleName;
    @Column(name="namesuffix")
    private String nameSuffix;
    @Column(name="gender")
    private String gender;

    /**
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle( final String title )
    {
        this.title = title;
    }

    @Length( min = Constants.NAME_MIN_LENGTH, max = Constants.NAME_MAX_LENGTH )
    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( final String firstName )
    {
        this.firstName = firstName;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName( final String middleName )
    {
        this.middleName = middleName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName( final String lastName )
    {
        this.lastName = lastName;
    }

    public String getNameSuffix()
    {
        return nameSuffix;
    }

    public void setNameSuffix( final String nameSuffix )
    {
        this.nameSuffix = nameSuffix;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender( final String gender )
    {
        this.gender = gender;
    }
    
	/* end of contactSuper class  */
	/**
     *
     */
	public Consumer() {
	}

	@PostLoad
	public void decryptLoadData() {
		//commenting due to ingrain issue
	   DefaultDecryptListener.INSTANCE.decrypt(this);
	}

	/**
	 * @return id the id of this object
	 * @inheritDoc
	 */
	@Override
	public final long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set for this object
	 * @inheritDoc
	 */
	@Override
	public final void setId(final long id) {
		this.id = id;
	}

	public Calendar getDob() {
		return dob;
	}

	public void setDob(final Calendar dob) {
		this.dob = dob;
	}

	public void setDob(final GregorianCalendar dob) {
		this.dob = dob;
		// this.setDob(dob);
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(final String ssn) {
		this.ssn = ssn;
	}

	public String getACustomerNumber() {
		return ACustomerNumber;
	}

	public void setACustomerNumber(
			final String ACustomerNumber) {
		this.ACustomerNumber = ACustomerNumber;
	}

	public String getHomePhoneValue() {
		return homePhoneValue;
	}

	public void setHomePhoneValue(String homePhoneValue) {

		this.homePhoneValue = replacePhoneChars(homePhoneValue);
	}

	public String getCellPhoneValue() {
		return cellPhoneValue;
	}

	public void setCellPhoneValue(String cellPhoneValue) {
		this.cellPhoneValue = replacePhoneChars(cellPhoneValue);
	}

	public String getWorkPhoneValue() {
		return workPhoneValue;
	}

	public void setWorkPhoneValue(String workPhoneValue) {
		//this.workPhoneValue = replacePhoneChars(workPhoneValue);
	    this.workPhoneValue = workPhoneValue;
	}

	/**
	 * @return the workPhoneExtn
	 */
	public Integer getWorkPhoneExtn() {
		return workPhoneExtn;
	}

	/**
	 * @param workPhoneExtn the workPhoneExtn to set
	 */
	public void setWorkPhoneExtn(Integer workPhoneExtn) {
		this.workPhoneExtn = workPhoneExtn;
	}

	public String getHomeEmailValue() {
		return homeEmailValue;
	}

	public void setHomeEmailValue(String homeEmailValue) {
		this.homeEmailValue = homeEmailValue;
	}

	public String getWorkEmailValue() {
		return workEmailValue;
	}

	public void setWorkEmailValue(String workEmailValue) {
		this.workEmailValue = workEmailValue;
	}

	public PhoneContactChannel getHomePhone() {

		if (homePhone == null) {
			homePhone = new PhoneContactChannel();
			homePhone.setDescription("home");
			homePhone.setPreferenceOrder(0);
		}
		homePhone.setValue(homePhoneValue);

		return homePhone;
	}

	public void setHomePhone(final PhoneContactChannel homePhone) {

		if (homePhone != null) {
			homePhoneValue = homePhone.getValue();
		}

		this.homePhone = homePhone;
	}

	public PhoneContactChannel getCellPhone() {

		if (cellPhone == null) {
			cellPhone = new PhoneContactChannel();
			cellPhone.setDescription("cell");
			cellPhone.setPreferenceOrder(1);
		}
		cellPhone.setValue(cellPhoneValue);

		return cellPhone;
	}

	public void setCellPhone(final PhoneContactChannel cellPhone) {

		if (cellPhone != null) {
			cellPhoneValue = cellPhone.getValue();
		}

		this.cellPhone = cellPhone;
	}

	public PhoneContactChannel getWorkPhone() {
		if (workPhone == null) {
			workPhone = new PhoneContactChannel();
			workPhone.setDescription("work");
			workPhone.setPreferenceOrder(2);
		}
		workPhone.setValue(workPhoneValue);

		return workPhone;
	}

	public void setWorkPhone(final PhoneContactChannel workPhone) {

		if (workPhone != null) {
			workPhoneValue = workPhone.getValue();
		}

		this.workPhone = workPhone;

	}

	public EMailContactChannel getHomeEMail() {

		if (homeEMail == null) {
			homeEMail = new EMailContactChannel();
			homeEMail.setDescription("homeEMail");
			homeEMail.setPreferenceOrder(3);
		}
		homeEMail.setValue(this.homeEmailValue);

		return homeEMail;
	}

	public void setHomeEMail(final EMailContactChannel homeEMail) {

		if (homeEMail != null) {
			homeEmailValue = homeEMail.getValue();
		}

		this.homeEMail = homeEMail;

	}

	public EMailContactChannel getWorkEMail() {

		if (workEMail == null) {
			workEMail = new EMailContactChannel();
			workEMail.setDescription("workEMail");
			workEMail.setPreferenceOrder(4);
		}
		workEMail.setValue(workEmailValue);

		return workEMail;

	}

	public void setWorkEMail(final EMailContactChannel workEMail) {

		if (workEMail != null) {
			workEmailValue = workEMail.getValue();
		}

		this.workEMail = workEMail;

	}

	public boolean isEmployed() {
		return employed;
	}

	public void setEmployed(final boolean employed) {
		this.employed = employed;
	}

	public String getEmployerBusinessName() {
		return employerBusinessName;
	}

	public void setEmployerBusinessName(final String employerBusinessName) {
		this.employerBusinessName = employerBusinessName;
	}

	public String getEmployerPhoneNumber() {
		return employerPhoneNumber;
	}

	public void setEmployerPhoneNumber(final String employerPhoneNumber) {
		this.employerPhoneNumber = employerPhoneNumber;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(final String occupation) {
		this.occupation = occupation;
	}

	public boolean isStudent() {
		return student;
	}

	public void setStudent(final boolean student) {
		this.student = student;
	}

	public boolean isRetired() {
		return retired;
	}

	public void setRetired(final boolean retired) {
		this.retired = retired;
	}

	public String getBankOrMortgageInstitution() {
		return bankOrMortgageInstitution;
	}

	public void setBankOrMortgageInstitution(
			final String bankOrMortgageInstitution) {
		this.bankOrMortgageInstitution = bankOrMortgageInstitution;
	}

	public String getOtherIncomeSource() {
		return otherIncomeSource;
	}

	public void setOtherIncomeSource(final String otherIncomeSource) {
		this.otherIncomeSource = otherIncomeSource;
	}

	public Boolean getDirectMailOptIn() {
		return directMailOptIn;
	}

	public void setDirectMailOptIn(Boolean directMailOptIn) {
		this.directMailOptIn = directMailOptIn;
	}

	public Boolean getEMailOptIn() {
		return EMailOptIn;
	}

	public void setEMailOptIn(Boolean eMailOptIn) {
		this.EMailOptIn = eMailOptIn;
	}

	public Long getExternalId() {
		return externalId;
	}

	public void setExternalId(Long externalId) {
		this.externalId = externalId;
	}

	public Set<CustomerAddressAssociation> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<CustomerAddressAssociation> addresses) {
		this.addresses = addresses;
	}

	public List<AddressBean> getConsumerAddressList() {
		return consumerAddressList;
	}

	public void setConsumerAddressList(List<AddressBean> consumerAddressList) {
		this.consumerAddressList = consumerAddressList;
	}

	public Set<BillingInformation> getBillingInfoList() {
		return billingInfoList;
	}

	public void setBillingInfoList(Set<BillingInformation> billingInfoList) {
		this.billingInfoList = billingInfoList;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDriverLicenseNo() {
		return driverLicenseNo;
	}

	public void setDriverLicenseNo(String driverLicenseNo) {
		this.driverLicenseNo = driverLicenseNo;
	}

	public Calendar getLicenseExpirationDate() {
		return licenseExpirationDate;
	}

	public void setLicenseExpirationDate(Calendar licenseExpirationDate) {
		this.licenseExpirationDate = licenseExpirationDate;
	}

	public String getLicenseState() {
		return licenseState;
	}

	public void setLicenseState(String licenseState) {
		this.licenseState = licenseState;
	}

	public String getStateIdNo() {
		return stateIdNo;
	}

	public void setStateIdNo(String stateIdNo) {
		this.stateIdNo = stateIdNo;
	}

	public String getIdState() {
		return idState;
	}

	public void setIdState(String idState) {
		this.idState = idState;
	}

	public String getLandlordName() {
		return landlordName;
	}

	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}

	public String getLandlordPhoneNumber() {
		return landlordPhoneNumber;
	}

	public void setLandlordPhoneNumber(String landlordPhoneNumber) {
		this.landlordPhoneNumber = landlordPhoneNumber;
	}

	public boolean isPhoneContactOptIn() {
		return phoneContactOptIn;
	}

	public void setPhoneContactOptIn(boolean phoneContactOptIn) {
		this.phoneContactOptIn = phoneContactOptIn;
	}

	public boolean iseMailProductUpdatesOptIn() {
		return eMailProductUpdatesOptIn;
	}

	public void seteMailProductUpdatesOptIn(boolean eMailProductUpdatesOptIn) {
		this.eMailProductUpdatesOptIn = eMailProductUpdatesOptIn;
	}

	public String getBillingDeliveryPreference() {
		return billingDeliveryPreference;
	}

	public void setBillingDeliveryPreference(String billingDeliveryPreference) {
		this.billingDeliveryPreference = billingDeliveryPreference;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public Boolean getNonBuyerWebOptIn() {
		return nonBuyerWebOptIn;
	}

	public void setNonBuyerWebOptIn(Boolean nonBuyerWebOptIn) {
		this.nonBuyerWebOptIn = nonBuyerWebOptIn;
	}

	public Boolean getMarketingOptIn() {
		return marketingOptIn;
	}

	public void setMarketingOptIn(Boolean marketingOptIn) {
		this.marketingOptIn = marketingOptIn;
	}

	public int getPrimaryLanguage() {
		return primaryLanguage;
	}

	public void setPrimaryLanguage(int primaryLanguage) {
		this.primaryLanguage = primaryLanguage;
	}

	public Long getReferrerId() {
		return referrerId;
	}

	public void setReferrerId(Long referrerId) {
		this.referrerId = referrerId;
	}

	public String getPartnerAccountId() {
		return partnerAccountId;
	}

	public void setPartnerAccountId(String partnerAccountId) {
		this.partnerAccountId = partnerAccountId;
	}

	public List<CustomerContext> getCustomerContexts() {
		return customerContexts;
	}

	public void setCustomerContexts(List<CustomerContext> customerContexts) {
		this.customerContexts = customerContexts;
	}

	public String getCurrentEmployedMonth() {
		return currentEmployedMonth;
	}

	public void setCurrentEmployedMonth(String currentEmployedMonth) {
		this.currentEmployedMonth = currentEmployedMonth;
	}

	public String getCurrentEmployedYear() {
		return currentEmployedYear;
	}

	public void setCurrentEmployedYear(String currentEmployedYear) {
		this.currentEmployedYear = currentEmployedYear;
	}

	public Calendar getBestTimeToCall1() {
		return bestTimeToCall1;
	}

	public void setBestTimeToCall1(Calendar bestTimeToCall1) {
		this.bestTimeToCall1 = bestTimeToCall1;
	}

	public Calendar getBestTimeToCall2() {
		return bestTimeToCall2;
	}

	public void setBestTimeToCall2(Calendar bestTimeToCall2) {
		this.bestTimeToCall2 = bestTimeToCall2;
	}

	public String getBestTimeToCallPhone() {
		return bestTimeToCallPhone;
	}

	public void setBestTimeToCallPhone(String bestTimeToCallPhone) {
		this.bestTimeToCallPhone = bestTimeToCallPhone;
	}

	public String getBestEmailContact() {
		return bestEmailContact;
	}

	public void setBestEmailContact(String bestEmailContact) {
		this.bestEmailContact = bestEmailContact;
	}

	public Set<CustomerPaymentEvent> getPaymentEvents() {
		return paymentEvents;
	}

	public void setPaymentEvents(Set<CustomerPaymentEvent> paymentEvents) {
		this.paymentEvents = paymentEvents;
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	public String getDtAgentId() {
		return dtAgentId;
	}

	public void setDtAgentId(String dtAgentId) {
		this.dtAgentId = dtAgentId;
	}

	public Calendar getDtCreated() {
		return dtCreated;
	}

	public void setDtCreated(Calendar dtCreated) {
		this.dtCreated = dtCreated;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public Set<CustomerAttribute> getCustomerAttributes() {
		return customerAttributes;
	}

	public void setCustomerAttributes(Set<CustomerAttribute> customerAttributes) {
		this.customerAttributes = customerAttributes;
	}

	public String getBestPhoneContact() {
		return bestPhoneContact;
	}

	public void setBestPhoneContact(String bestPhoneContact) {
		this.bestPhoneContact = replacePhoneChars(bestPhoneContact);
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getPartnerSSN() {
		return partnerSSN;
	}

	public void setPartnerSSN(String partnerSSN) {
		this.partnerSSN = partnerSSN;
	}

	public String getSecondPhone() {
		return secondPhone;
	}

	public void setSecondPhone(String secondPhone) {
	    this.secondPhone = replacePhoneChars(secondPhone);
	    //this.secondPhone = secondPhone;
	}

	public String getSsnLastFour() {
		return ssnLastFour;
	}

	public void setSsnLastFour(String ssnLastFour) {
		this.ssnLastFour = ssnLastFour;
	}

	public String getReferrerGeneralName() {
		return referrerGeneralName;
	}

	public void setReferrerGeneralName(String referrerGeneralName) {
		this.referrerGeneralName = referrerGeneralName;
	}

	public String getBirthInfo() {
		return birthInfo;
	}

	public void setBirthInfo(String birthInfo) {
		this.birthInfo = birthInfo;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public Calendar getCustomerCreateDate() {
		return customerCreateDate;
	}

	public void setCustomerCreateDate(Calendar customerCreateDate) {
		this.customerCreateDate = customerCreateDate;
	}

	/**
	 * Replaces any characters other than numeric values
	 * @param phone
	 * @return
	 */
	private static String replacePhoneChars(String phone) {
	    String p = phone;

	    p = phone.replaceAll("[\\D]", "");
	    return p;
	}

	public String getOffersPresented() {
	    return offersPresented;
	}

	public void setOffersPresented(String offersPresented) {
	    this.offersPresented = offersPresented;
	}

	public Set<CustomerSurvey> getCustomerCsatSurveys() {
	    return customerCsatSurveys;
	}

	public void setCustomerCsatSurveys(Set<CustomerSurvey> customerSurveys) {
	    this.customerCsatSurveys = customerSurveys;
	}

	public String getContractAccountNumber() {
        return contractAccountNumber;
	}
	
	public void setContractAccountNumber(String contractAccountNumber) {
	        this.contractAccountNumber = contractAccountNumber;
	}

}
