package com.A.vm.util.converter.unmarshall;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.A.enums.AddressStatusEnum;
import com.A.util.XmlUtil;
import com.A.V.beans.entity.AddressBean;
import com.A.V.beans.entity.Consumer;
import com.A.V.beans.entity.CustomerAddressAssociation;
import com.A.V.beans.entity.User;
import com.A.vm.service.CustomerAgentService;
import com.A.xml.v4.AddressType;
import com.A.xml.v4.CustAddress;
import com.A.xml.v4.CustomerManagementRequestResponseDocument.CustomerManagementRequestResponse.Request;
import com.A.xml.v4.CustomerType;
import com.A.xml.v4.RoleType;

/**
 * @author ebthomas
 *
 */
@Component
public final class UnmarshallConsumer {

    private static final Logger logger = Logger.getLogger(UnmarshallConsumer.class);

    @Autowired(required = false)
    private CustomerAgentService agentService;

    private static final String FIN_INFO = "financialInfo";

    public Consumer build(final Request request, boolean isUpdateRequest) {
	logger.info("Unmarshalling customer");
	if (request == null) {
	    throw new IllegalArgumentException("invalid.null.request");
	}

	return doBuildConsumer(request, isUpdateRequest);

    }

    public Consumer updateConsumer(final Request originalMessage, Consumer dest, boolean isUpdateRequest) {
	CustomerType customerType = originalMessage.getCustomerInfo();
	if (customerType == null) {
	    return dest;
	}

	//copyCustomerContext(originalMessage, dest);
	UnmarshallCustomerContext.Builder.copyCustomerContext(originalMessage, dest);
	dest = copyConsumerInfo(customerType, dest, isUpdateRequest);
	return dest;
    }

    private Consumer doBuildConsumer(final Request originalMessage, boolean isUpdateRequest) {

	CustomerType customerType = originalMessage.getCustomerInfo();
	Consumer consumerBean = new Consumer();

	//copyCustomerContext(originalMessage, consumerBean);
	UnmarshallCustomerContext.Builder.copyCustomerContext(originalMessage, consumerBean);

	if (customerType == null) {
	    return consumerBean;
	}

	return copyConsumerInfo(customerType, consumerBean, isUpdateRequest);

    }

    /*private void copyCustomerContext(Request request, Consumer consumer) {

	List<CustomerContext> existingContextList = consumer.getCustomerContexts();
	List<CustomerContext> newContextList = new ArrayList<CustomerContext>();
	if (request.getCustomerContext() != null) {
	    CustomerContextType contextType = request.getCustomerContext();
	    if (contextType.getEntityList() != null) {
		List<CustomerContextEntityType> contextList = contextType.getEntityList();
		for (CustomerContextEntityType entityType : contextList) {

		    List<NameValuePairType> nvPairList = entityType.getAttributeList();
		    for (NameValuePairType nvPairType : nvPairList) {
			CustomerContext destContext = new CustomerContext();
			destContext.setEntityName(entityType.getName());
			destContext.setName(nvPairType.getName());
			destContext.setValue(nvPairType.getValue());
			newContextList.add(destContext);
		    }
		}
	    }
	}

	//Filter old context attributes which is being passed in new request
	if(existingContextList != null && !existingContextList.isEmpty() && newContextList != null && !newContextList.isEmpty()) {
	    Iterator<CustomerContext> extContextIter = existingContextList.iterator();
	    while(extContextIter.hasNext()) {
		CustomerContext extContext = extContextIter.next();
		for(CustomerContext newContext : newContextList) {
		    if(newContext.getEntityName().equalsIgnoreCase(extContext.getEntityName()) && newContext.getName().equalsIgnoreCase(extContext.getName())) {
			extContextIter.remove();
			break;
		    }
		}
	    }
	}

	List<CustomerContext> finalContextList = new ArrayList<CustomerContext>();

	if(existingContextList != null) {
	    finalContextList.addAll(existingContextList);
	}
	finalContextList.addAll(newContextList);
	consumer.setCustomerContexts(finalContextList);
    }
*/
    /**
     * This method will be used by UnmarshallOrder to unmarshall consumer information from XML
     *
     * @param src
     * @param isUpdateRequest
     * @return
     */
    public Consumer copyConsumerInfo(CustomerType src, boolean isUpdateRequest) {
	Consumer dest = null;
	if (!isUpdateRequest) {
	    dest = new Consumer();
	}
	return copyConsumerInfo(src, dest, isUpdateRequest);
    }

    public static AddressBean getAddressByRef(Consumer dest, String addressRef) {

	AddressBean address = null;

	// List<CustomerAddressAssociation> addrList = new
	// ArrayList<CustomerAddressAssociation>(dest.getAddresses());

	// for (CustomerAddressAssociation caa : addrList) {
	// if ((caa != null) && (caa.getAddress() != null)
	// && (caa.getAddress().getAddressUniqueId() != null)) {
	// if (addressRef.equals(caa.getAddress().getAddressUniqueId())) {
	// return caa.getAddress();
	// }
	// }
	// }

	List<AddressBean> addressList = dest.getConsumerAddressList();
	for (AddressBean addr : addressList) {
	    if (addr.getAddressUniqueId().equalsIgnoreCase(addressRef)) {
		address = addr;
		break;
	    }
	}

	return address;
    }

    private Consumer copyConsumerInfo(CustomerType xmlCustomerSrc, Consumer domainCustomerdest, boolean isUpdateRequest) {

	// DT customer information
	if (xmlCustomerSrc.getReferrerId() > 0) {
	    domainCustomerdest.setReferrerId(xmlCustomerSrc.getReferrerId());
	}

	if (xmlCustomerSrc.getPartnerId() != null && xmlCustomerSrc.getPartnerId().trim().length() > 0) {
	    domainCustomerdest.setPartnerId(xmlCustomerSrc.getPartnerId());
	}

	if (xmlCustomerSrc.getPartnerAccountId() != null && xmlCustomerSrc.getPartnerAccountId().trim().length() > 0) {
	    domainCustomerdest.setPartnerAccountId(xmlCustomerSrc.getPartnerAccountId());
	}

	if (xmlCustomerSrc.getReferrerGeneralName() != null && xmlCustomerSrc.getReferrerGeneralName().trim().length() > 0) {
	    domainCustomerdest.setReferrerGeneralName(xmlCustomerSrc.getReferrerGeneralName());
	}

	if (xmlCustomerSrc.getDtAgentId() != null && xmlCustomerSrc.getDtAgentId().trim().length() > 0) {
	    domainCustomerdest.setDtAgentId(xmlCustomerSrc.getDtAgentId());
	}

	if (null != xmlCustomerSrc.getContractAccountNumber() && xmlCustomerSrc.getContractAccountNumber().trim().length() > 0){
        domainCustomerdest.setContractAccountNumber(xmlCustomerSrc.getContractAccountNumber());
	}
	
	if (!isUpdateRequest) {
	    domainCustomerdest.setACustomerNumber(xmlCustomerSrc.getACustomerNumber());
	}

	if (!isUpdateRequest) {

	    // dtCreated date
	    if (xmlCustomerSrc.getDtCreated() != null) {
		domainCustomerdest.setDtCreated(xmlCustomerSrc.getDtCreated());
	    }
	    else {
		domainCustomerdest.setDtCreated(Calendar.getInstance());
	    }

	    // Customer create date
	    if (xmlCustomerSrc.getCustomerCreateDate() != null) {
		domainCustomerdest.setCustomerCreateDate(xmlCustomerSrc.getCustomerCreateDate());
	    }
	    else {
		domainCustomerdest.setCustomerCreateDate(Calendar.getInstance());
	    }
	}

	logger.debug("Copying Agent Info");
	copyAgentInfo(xmlCustomerSrc, domainCustomerdest);

	logger.debug("Copying Consumer demographic information...");
	UnmarshallConsumerDemographicInfo.copy(xmlCustomerSrc, domainCustomerdest, isUpdateRequest);

	logger.debug("Copying Consumer contact information...");
	UnmarshallConsumerContactInfo.copyConsumerContactInfo(xmlCustomerSrc, domainCustomerdest, isUpdateRequest);

	logger.debug("Copying Consumer account information...");
	UnmarshallAccount.Builder.copy(xmlCustomerSrc, domainCustomerdest, isUpdateRequest);

	logger.debug("Copying Consumer financial information...");
	if (xmlCustomerSrc != null) {
	    if (!XmlUtil.isElementNull(xmlCustomerSrc.newCursor(), FIN_INFO)) UnmarshallConsumerFinancialInfo.copy(xmlCustomerSrc.getFinancialInfo(), domainCustomerdest,
		    isUpdateRequest);
	}

	logger.debug("Copying addresses...");
	copyConsumerAddress(xmlCustomerSrc, domainCustomerdest, isUpdateRequest);

	logger.debug("Copying Billing Information...");
	UnmarshallBillingInformation.copy(xmlCustomerSrc, domainCustomerdest, isUpdateRequest);

	logger.debug("Copying Payment Events...");
	UnmarshallPaymentEvent.Builder.copy(xmlCustomerSrc, domainCustomerdest, isUpdateRequest);

	UnmarshallCustomerAttribute.Builder.copy(xmlCustomerSrc, domainCustomerdest, isUpdateRequest);

	return domainCustomerdest;
    }

    private void copyConsumerAddress(CustomerType xmlCustomerSrc, Consumer domainCustomerdest, boolean isUpdateRequest) {

	if (xmlCustomerSrc.getAddressList() != null) {
	    List<CustAddress> addressList = xmlCustomerSrc.getAddressList().getCustomerAddressList();
	    if (addressList != null) {
		for (CustAddress address : addressList) {
		    AddressBean destAddr = copyAddress(domainCustomerdest, address, isUpdateRequest);

		    // Allow Unique Address to change only when creating
		    if (!isUpdateRequest) {
			destAddr.setAddressUniqueId(address.getAddressUniqueId());
		    }

		    // NULL external ID defaults to value 0
		    if ((destAddr != null) && (destAddr.getExternalId() == null)) {
			destAddr.setExternalId(0L);
		    }

		    if ((destAddr != null) && (destAddr.getExternalId() != null) && (destAddr.getExternalId().longValue() == 0)) {
			domainCustomerdest.getConsumerAddressList().add(destAddr);
		    }
		}
	    }

	}
    }

    private void copyAgentInfo(CustomerType src, Consumer dest) {

		String agentId = src.getAgentId() == null ? "-1" : src.getAgentId();
		if (agentId != null) {
	
			User agentBean = agentService.findAgentById(agentId.trim());
			if (agentBean == null) {
			    logger.debug("No agent info found.");
			    throw new IllegalArgumentException("Agent Id not found : " + agentId);
			}
		    dest.setAgentId(agentId);
		}
    }

    private static void updateAddressRoles(CustAddress address, AddressBean destAddress) {

	List<RoleType.Enum> roleList = address.getAddressRoles().getRoleList();
	List<String> addrRoles = new ArrayList<String>(roleList.size());
	for (int i = 0; i < roleList.size(); i++) {
	    RoleType.Enum role = roleList.get(i);
	    addrRoles.add(role.toString());

	}

	destAddress.setAddressRoles(addrRoles);

    }

    private static AddressBean copyAddress(Consumer dest, CustAddress customerAddressType, boolean isUpdateRequest) {
	logger.debug("Unmarshalling address information...");
	AddressBean destDomainAddress = null;
	boolean isNewAddress = (customerAddressType.getAddress() != null) && ((customerAddressType.getAddress().getExternalId() == 0));
	isUpdateRequest = !isNewAddress;
	Map<String, String> updateMonitor = new HashMap<String, String>();

	if (isNewAddress) {
	    destDomainAddress = new AddressBean();
	    AddressType xmlSrcAddress = customerAddressType.getAddress();
	    destDomainAddress = UnmarshallAddressBean.copyAddressInfo(xmlSrcAddress, destDomainAddress, isUpdateRequest);

	    updateAddressRoles(customerAddressType, destDomainAddress);

	    destDomainAddress.setStatus(AddressStatusEnum.ACTIVE.name());
	}
	else {

	    List<CustomerAddressAssociation> custAddrList = new ArrayList<CustomerAddressAssociation>(dest.getAddresses());

	    if (custAddrList != null) {
		for (CustomerAddressAssociation ca : custAddrList) {

		    boolean isEqual = isSameCustomerAddress(ca, customerAddressType);

		    if ((isEqual) && (!updateMonitor.containsKey(ca.getUniqueId()))) {

			destDomainAddress = UnmarshallAddressBean.copyAddressInfo(customerAddressType.getAddress(), ca.getAddress(), isUpdateRequest);

			logger.debug(customerAddressType.getStatus().toString());
			updateAddressRoles(customerAddressType, destDomainAddress);

			if (destDomainAddress != null) {
			    destDomainAddress.setStatus(customerAddressType.getStatus().toString().toUpperCase());
			    if ((customerAddressType.getAddress() != null) && (customerAddressType.getAddress().getExpiration() != null)) {
				destDomainAddress.setExpiration(customerAddressType.getAddress().getExpiration());
			    }

			}

			updateMonitor.put(ca.getUniqueId(), ca.getUniqueId());
		    }
		}
	    }
	}
	if (isNewAddress) {
	    destDomainAddress.setAddressUniqueId(customerAddressType.getAddressUniqueId());
	}

	addNewRoles(dest, customerAddressType, destDomainAddress);
	return destDomainAddress;

    }

    public static void addNewRoles(Consumer dest, CustAddress customerAddressType, AddressBean destDomainAddress) {

	String addrId = destDomainAddress.getAddressUniqueId();

	// get current addresses
	List<CustomerAddressAssociation> custAddrList = new ArrayList<CustomerAddressAssociation>(dest.getAddresses());

	Map<String, String> existingRoles = new HashMap<String, String>();
	Map<String, CustomerAddressAssociation> toRemoveRoles = new HashMap<String, CustomerAddressAssociation>();

	for (CustomerAddressAssociation caa : custAddrList) {
	    if (customerAddressType.getAddressUniqueId().equals(caa.getUniqueId())) {
		String key = caa.getAddressRole().toLowerCase() + "-->" + addrId;

		existingRoles.put(key, caa.getAddressRole().toLowerCase());

		toRemoveRoles.put(key, caa);
	    }
	}

	List<RoleType.Enum> desiredRoleList = customerAddressType.getAddressRoles().getRoleList();

	for (int i = 0; i < desiredRoleList.size(); i++) {
	    RoleType.Enum desiredRole = desiredRoleList.get(i);

	    if (!existingRoles.containsKey(desiredRole.toString().toLowerCase() + "-->" + addrId)) {
		CustomerAddressAssociation newAssn = new CustomerAddressAssociation();
		newAssn.setAddress(destDomainAddress);
		newAssn.setConsumer(dest);
		newAssn.setUniqueId(customerAddressType.getAddressUniqueId());
		newAssn.setAddressRole(desiredRole.toString());
		newAssn.setNewAssn(Boolean.TRUE);

		custAddrList.add(newAssn);
	    }

	    // Left in to Remove are those that are not DESIRED but in the
	    // current
	    toRemoveRoles.remove(desiredRole.toString().toLowerCase() + "-->" + addrId);
	}

	dest.setAddresses(new HashSet<CustomerAddressAssociation>(custAddrList));

	for (CustomerAddressAssociation removingAddressAssociation : toRemoveRoles.values()) {

	    for (CustomerAddressAssociation caaFinal : dest.getAddresses()) {
		if ((caaFinal.getAddressRole().equals(removingAddressAssociation.getAddressRole())) && (caaFinal.getUniqueId().equals(removingAddressAssociation.getUniqueId()))) {

		    caaFinal.setDeleted(Boolean.TRUE);
		}

	    }

	}

    }

    public static boolean isSameCustomerAddress(CustomerAddressAssociation ca, CustAddress customerAddressType) {

	boolean result = false;

	if ((ca.getAddress() != null) && (ca.getAddress().getExternalId() != null) && (customerAddressType.getAddress() != null)
		&& (ca.getAddress().getExternalId().equals(customerAddressType.getAddress().getExternalId()))) {
	    // Matching External Id
	    return Boolean.TRUE;
	}

	if ((ca.getUniqueId() != null) && (customerAddressType.getAddressUniqueId() != null) && (ca.getUniqueId().equals(customerAddressType.getAddressUniqueId()))) {
	    // Matching addressUniqueId
	    return Boolean.TRUE;
	}

	return result;

    }

}
