package com.AL.op.mapper;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.AL.op.util.AddressUtil;
import com.AL.op.util.ProvisionHandlerUtil;
import com.AL.V.beans.entity.BillingInformation;
import com.AL.V.beans.entity.Consumer;
import com.AL.V.beans.entity.CustomerAddressAssociation;
import com.AL.Vdao.dao.CustomerDao;
import com.AL.xml.v4.AddressListType;
import com.AL.xml.v4.BillingInfoList;
import com.AL.xml.v4.CustAddress;
import com.AL.xml.v4.CustBillingInfoType;
import com.AL.xml.v4.CustomerManagementRequestResponseDocument;
import com.AL.xml.v4.CustomerType;
import com.AL.xml.v4.EMailAddressType;
import com.AL.xml.v4.PhoneNumberType;
import com.AL.xml.v4.RoleType;
import com.AL.xml.v4.CustomerManagementRequestResponseDocument.CustomerManagementRequestResponse;
import com.AL.xml.v4.CustomerManagementRequestResponseDocument.CustomerManagementRequestResponse.Request;
import com.AL.xml.v4.orderProvisioning.OpCustomerInfo;
import com.AL.xml.v4.orderProvisioning.OrderProvisioningRequestDocument.OrderProvisioningRequest;

public enum CustomerInfoMapper {
	
	INSTANCE;
	
	private static final Logger logger = Logger.getLogger(CustomerInfoMapper.class);
	
	enum CustPhoneNumberType {
		CELL("cell"), HOME("home"), WORK("work");
		String desc;
		CustPhoneNumberType(String desc) {
			this.desc = desc;
		}
		public String getDesc() {
			return this.desc;
		}
	}
	
	public CustomerType mapCustomerDemographicInfo(OpCustomerInfo opCustomerInfo) {
		logger.info("Started mapping demographic info");
		CustomerType customer = CustomerType.Factory.newInstance();
		customer.setExternalId(opCustomerInfo.getExternalId());
		customer.setFirstName(opCustomerInfo.getFirstName());
		customer.setLastName(opCustomerInfo.getLastName());
		if(StringUtils.isNotEmpty(opCustomerInfo.getMiddleName())) {
			customer.setMiddleName(opCustomerInfo.getMiddleName());
		}
		if(StringUtils.isNotEmpty(opCustomerInfo.getTitle())) {
			customer.setTitle(opCustomerInfo.getTitle());
		}
		if(StringUtils.isNotEmpty(opCustomerInfo.getNameSuffix())) {
			customer.setNameSuffix(opCustomerInfo.getNameSuffix());
		}
		if(StringUtils.isNotEmpty(opCustomerInfo.getBestPhoneContact())) {
			customer.setBestPhoneContact(opCustomerInfo.getBestPhoneContact());
		}
		if(StringUtils.isNotEmpty(opCustomerInfo.getSecondPhone())) {
			customer.setSecondPhone(opCustomerInfo.getSecondPhone());
		}
		if(StringUtils.isNotEmpty(opCustomerInfo.getCellPhoneNumber())) {
			customer.setCellPhoneNumber(mapPhoneNumber(opCustomerInfo.getCellPhoneNumber(), CustPhoneNumberType.CELL));
		}
		if(StringUtils.isNotEmpty(opCustomerInfo.getHomePhoneNumber())) {
			customer.setHomePhoneNumber(mapPhoneNumber(opCustomerInfo.getHomePhoneNumber(), CustPhoneNumberType.HOME));
		}
		if(StringUtils.isNotEmpty(opCustomerInfo.getWorkPhoneNumber())) {
			customer.setWorkPhoneNumber(mapPhoneNumber(opCustomerInfo.getWorkPhoneNumber(), CustPhoneNumberType.WORK));
		}
		if(StringUtils.isNotEmpty(opCustomerInfo.getBestEmailContact())) {
			customer.setBestEmailContact(opCustomerInfo.getBestEmailContact());
		}
		if(StringUtils.isNotEmpty(opCustomerInfo.getPrimaryEmailAddress())) {
			EMailAddressType homeEmail = EMailAddressType.Factory.newInstance();
			homeEmail.setValue(opCustomerInfo.getPrimaryEmailAddress());
			customer.setHomeEMail(homeEmail);
		}
		if(StringUtils.isNotEmpty(opCustomerInfo.getSecondaryEmailAddress())) {
			EMailAddressType workEmail = EMailAddressType.Factory.newInstance();
			workEmail.setValue(opCustomerInfo.getSecondaryEmailAddress());
			customer.setWorkEMail(workEmail);
		}
		if((opCustomerInfo.getDob() != null)) {
			customer.setDob(opCustomerInfo.getDob());
		}
		if(StringUtils.isNotEmpty(opCustomerInfo.getSsn())) {
			customer.setSsn(opCustomerInfo.getSsn());
		}
		if(opCustomerInfo.getDriversLicense() != null) {
			customer.setDriverLicense(opCustomerInfo.getDriversLicense());
		}
		return customer;
	}
	
	public PhoneNumberType mapPhoneNumber(String number, CustPhoneNumberType custPhoneNumberType) {
		PhoneNumberType phoneNumber = PhoneNumberType.Factory.newInstance();
		phoneNumber.setValue(number);
		phoneNumber.setDesc(custPhoneNumberType.getDesc());
		return phoneNumber;
	}
	
	public CustomerManagementRequestResponseDocument createCMRequestDoc(final OrderProvisioningRequest opRequest, 
			final CustomerDao customerDao, final OpCustomerInfo opCustomerInfo) {
		CustomerManagementRequestResponseDocument doc = CustomerManagementRequestResponseDocument.Factory.newInstance();
		CustomerManagementRequestResponse cmRequestResponse = doc.addNewCustomerManagementRequestResponse();
		cmRequestResponse.setCorrelationId(opRequest.getCorrelationId());
		cmRequestResponse.setTransactionType(com.AL.xml.v4.CustomerManagementRequestResponseDocument.
				CustomerManagementRequestResponse.TransactionType.UPDATE_CUSTOMER);
		cmRequestResponse.setTransactionId(123456);
		Request request = cmRequestResponse.addNewRequest();
		CustomerType customer = CustomerInfoMapper.INSTANCE.mapCustomerDemographicInfo(opCustomerInfo); 
		Consumer consumer = null;
		if((opCustomerInfo.getBillingInfo() != null) && 
				StringUtils.isNotEmpty(opCustomerInfo.getBillingInfo().getCreditCardNumber())) {
			logger.info("Setting billing info");
			CustBillingInfoType custBillInfo = opCustomerInfo.getBillingInfo();
			if(custBillInfo.getExternalId() == 0) {
				consumer = customerDao.findCustomerByExternalId(opCustomerInfo.getExternalId());
				//Check if there is any credit card in database with credit card number
				for(BillingInformation billInfo : consumer.getBillingInfoList()) {
					if(StringUtils.equals(billInfo.getCreditCardNumber(), custBillInfo.getCreditCardNumber())) {
						logger.info("Updating existing billing info");
						custBillInfo.setExternalId(billInfo.getExternalId());
						break;
					}
				}
			}
			BillingInfoList billingInfoList = customer.addNewBillingInfoList();
			billingInfoList.getBillingInfoList().add(custBillInfo);
		}
		logger.info("Setting customer addresses");
		AddressListType addressList = customer.addNewAddressList();
		for(CustAddress custAddress : opCustomerInfo.getAddressList()) {
			if(custAddress.getAddress().getExternalId() == 0) {
				//If it is only service address then don't save or update
				if((custAddress.getAddressRoles().getRoleList().size() == 1) && 
						custAddress.getAddressRoles().getRoleList().contains(RoleType.SERVICE_ADDRESS)) {
					logger.info("skipping service address");
					continue;
				}
				if(consumer == null) {
					consumer = customerDao.findCustomerByExternalId(opCustomerInfo.getExternalId());
				}
				//check addresses are already there in database
				String custAddressBlock = AddressUtil.convertAddressTypeToString(custAddress.getAddress());
				for(CustomerAddressAssociation consumerAddr : consumer.getAddresses()) {
					String consumerAddrBlock = AddressUtil.convertAddressBeanToString(consumerAddr.getAddress());
					if(StringUtils.equalsIgnoreCase(custAddressBlock, consumerAddrBlock)) {
						logger.info("Updating existing address");
						custAddress.getAddress().setExternalId(consumerAddr.getAddress().getExternalId());
						break;
					}
				}
			}
			addressList.getCustomerAddressList().add(custAddress);
		}
		customer.setAgentId(opRequest.getAgentId());
		request.setCustomerInfo(customer);
		request.setCustomerId(String.valueOf(opCustomerInfo.getExternalId()));
		request.setAgentId(opRequest.getAgentId());
		ProvisionHandlerUtil.setCustomerContext(request, opRequest.getOpContext());
		return doc;
	}
}
