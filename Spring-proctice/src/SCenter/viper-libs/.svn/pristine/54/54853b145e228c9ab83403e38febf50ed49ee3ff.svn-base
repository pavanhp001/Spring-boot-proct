package com.A.validation.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.A.task.context.impl.ResponseItemOme;
import com.A.validation.Message;
import com.A.validation.ValidationReport;
import com.A.V.beans.entity.AddressBean;
import com.A.V.beans.entity.BillingInformation;
import com.A.V.beans.entity.Consumer;
import com.A.V.beans.entity.CustomerAddressAssociation;
import com.A.xml.v4.AddressListType;
import com.A.xml.v4.BillingInfoList;
import com.A.xml.v4.CustAddress;
import com.A.xml.v4.CustBillingInfoType;
import com.A.xml.v4.CustomerManagementRequestResponseDocument;
import com.A.xml.v4.CustomerType;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.OrderType;
import com.A.xml.v4.ProcessingMessage;
import com.A.xml.v4.RoleType;
import com.A.xml.v4.StatusType;
import com.A.xml.v4.StatusType.ProcessingMessages;

public class CustomerValidationHelper {

	private static final String SERVICE_ADDRESS = "ServiceAddress";
	private static final Logger logger = Logger
			.getLogger(CustomerValidationHelper.class);

	private CustomerValidationHelper() {
		super();
	}

	/**
	 * Helper method to check fatal error message in the status in customer
	 * service response. If it exist then those messages are populated in
	 * response and it will return boolean value as true to be processed later
	 * in OME
	 *
	 * @param response
	 * @param rio
	 * @return
	 */
	public static Boolean isFatalResponse(
			CustomerManagementRequestResponseDocument response,
			ResponseItemOme rio) {
		Boolean errorExist = Boolean.FALSE;
		if (response.getCustomerManagementRequestResponse().getStatus() != null) {
			StatusType status = response.getCustomerManagementRequestResponse()
					.getStatus();
			if (status.getStatusMsg().trim().equalsIgnoreCase("FATAL")) {
				ValidationReport report = rio.getValidationReport();
				if (status.getProcessingMessages() != null) {
					List<ProcessingMessage> messages = status
							.getProcessingMessages().getMessageList();
					for (ProcessingMessage msg : messages) {
						report.addErrorMessage(3L, msg.getText());
					}
				}
				populateProcessingStatus(status, report);
				errorExist = Boolean.TRUE;
			}
		}
		return errorExist;
	}

	/**
	 * @param status
	 *            status
	 * @param validationReport
	 *            validation report
	 */
	public static void populateProcessingStatus(final StatusType status,
			final ValidationReport validationReport) {
		ProcessingMessages processingMessages = status
				.addNewProcessingMessages();

		Set<Message> messages = validationReport.getMessages();
		for (Message message : messages) {
			ProcessingMessage serviceFeedback = processingMessages
					.addNewMessage();
			double code = new Double(message.getType().getCode() + "."
					+ message.getMessageCode());
			serviceFeedback.setCode(code);
			serviceFeedback.setText(message.getMessageKey());

		}
	}

	/**
	 * @param doc
	 *            Document that will contain errors
	 * @param validationReport
	 * @return Status
	 */
	public static StatusType determineStatus(final Object doc,
			final ValidationReport validationReport) {
		if ((doc != null)
				&& (doc instanceof CustomerManagementRequestResponseDocument)) {
			return getStatus((CustomerManagementRequestResponseDocument) doc,
					validationReport);
		}

		return null;
	}

	private static StatusType getStatus(
			final CustomerManagementRequestResponseDocument doc,
			final ValidationReport validationReport) {
		StatusType status = doc.getCustomerManagementRequestResponse()
				.addNewStatus();

		if (validationReport.contains(Message.Type.FATAL)) {
			updateStatusCode(status, Message.Type.FATAL);
		} else if (validationReport.contains(Message.Type.ERROR)) {
			updateStatusCode(status, Message.Type.ERROR);
		} else if (validationReport.contains(Message.Type.WARNING)) {
			updateStatusCode(status, Message.Type.WARNING);
		} else if (validationReport.contains(Message.Type.INFO)) {
			updateStatusCode(status, Message.Type.INFO);
		}

		return status;
	}

	/**
	 * @param status
	 *            status
	 * @param type
	 *            type
	 */
	public static void updateStatusCode(final StatusType status,
			final Message.Type type) {
		status.setStatusCode(type.getCode());
		status.setStatusMsg(type.name());
	}

	/**
	 * Helper method to validate Address Reference used in BillingInfo list is
	 * actually being provided in AddressList.
	 *
	 * @param customerSrc
	 * @param customerAction
	 * @return
	 */
	public static boolean validateAddressRef(CustomerType customerSrc,
			String customerAction) {
		logger.info("Validating address reference in billing info");
		boolean isAddressRefExist = Boolean.FALSE;
		if (customerAction.equalsIgnoreCase("createCustomer")) {
			// First get the billing info which is using address ref
			if (customerSrc.getBillingInfoList() != null) {
				BillingInfoList billingInfoList = customerSrc
						.getBillingInfoList();

				if (billingInfoList == null) {
					return  Boolean.TRUE;
				}

				if (billingInfoList != null) {
					List<CustBillingInfoType> custBillingList = billingInfoList
							.getBillingInfoList();

					// Get the address list from xml
					AddressListType addressListType = customerSrc
							.getAddressList();
					List<CustAddress> custAddrList = null;
					if (addressListType != null) {
						custAddrList = addressListType.getCustomerAddressList();
					}

					// Get the address ref used in billing info and check it
					// exist
					// as AddressUniqueId in address list
					// Otherwise billing is referencing to address which does
					// not
					// exist
					if (custBillingList != null && custAddrList != null) {
						for (CustBillingInfoType custBillingInfo : custBillingList) {
							String addressRef = custBillingInfo.getAddressRef()
									.trim();
							if (custAddrList != null) {
								for (CustAddress custAddr : custAddrList) {

									if ((custAddr != null)
											&& (custAddr.getAddressUniqueId() != null)) {
										String addressUniqueId = custAddr
												.getAddressUniqueId().trim();
										if ((addressRef != null)
												&& (addressRef
														.equalsIgnoreCase(addressUniqueId))) {
											isAddressRefExist = Boolean.TRUE;
											break;
										}
									}
								}
							}
						}
					}
				}
			}
		} else {
			isAddressRefExist = Boolean.TRUE;
		}
		logger.info("Is address ref valid : " + isAddressRefExist);
		return isAddressRefExist;
	}

	/**
	 * Helper method to validate Service address reference used at lineitem
	 * level exist in customer details as Address unique id. It will also
	 * validate Billing reference used at lineitem level exist in customer
	 * details as Billing Uniqie Id.
	 *
	 * @param customerSrc
	 * @param orderSrc
	 * @return
	 */
	public static boolean validateLineItemRef(CustomerType customerSrc,
			OrderType orderSrc, String customerAction) {

		logger.info("Validating service address reference for lineitems in provided customer details");
		boolean isAllRefExist = Boolean.FALSE;

		boolean isValidBillRef = Boolean.FALSE;
		boolean isValidAddrRef = Boolean.FALSE;
		String billingRef = "";
		String svcAddrRef = "";
		if (customerAction.equalsIgnoreCase("createCustomer")
				&& orderSrc.getLineItems() != null) {
			// First retrieve lineitems from order
			List<LineItemType> liList = orderSrc.getLineItems()
					.getLineItemList();
			// Get the address list from customer details
			if (customerSrc == null) {
				throw new IllegalArgumentException("customer source is null");
			}
			AddressListType addressListType = customerSrc.getAddressList();
			List<CustAddress> custAddrList = null;
			if (addressListType != null) {
				custAddrList = addressListType.getCustomerAddressList();
			}
			// Get the billing info from customer details
			BillingInfoList billingInfoList = customerSrc.getBillingInfoList();


			if (billingInfoList != null) {
			List<CustBillingInfoType> custBillingList = billingInfoList
					.getBillingInfoList();
			if (liList != null && !liList.isEmpty()) {
				for (LineItemType liType : liList) {

					billingRef = liType.getBillingInfoRef();
					svcAddrRef = liType.getSvcAddressRef();

					// For each lineitem get the billing ref and check it exist
					// in customer billing info list
					if (custBillingList != null) {

						for (CustBillingInfoType custBillingInfo : custBillingList) {
							String billingUniqueId = custBillingInfo
									.getBillingUniqueId().trim();
							if (billingRef.equalsIgnoreCase(billingUniqueId)) {
								isValidBillRef = Boolean.TRUE;
								break;
							}
						}

					}
				}

					// For each lineitem get the service address ref and check
					// it exist in customer address list
					if (custAddrList != null) {
						for (CustAddress custAddr : custAddrList) {
							String addressUniqueId = custAddr
									.getAddressUniqueId().trim();
							// String[] roleList =
							// (String[])custAddr.getAddressRoles().getRoleList().toArray();
							List<RoleType.Enum> roleList = custAddr
									.getAddressRoles().getRoleList();
							for (int i = 0; i < roleList.size(); i++) {
								RoleType.Enum role = roleList.get(i);
								// logger.info(role.toString());
								if (svcAddrRef
										.equalsIgnoreCase(addressUniqueId)
										&& role.toString().equalsIgnoreCase(
												SERVICE_ADDRESS)) {
									isValidAddrRef = Boolean.TRUE;
									break;
								}
							}
							/*
							 * for(String role : roleList){ if
							 * (svcAddrRef.equalsIgnoreCase(addressUniqueId) &&
							 * role.equalsIgnoreCase( "serviceAddress" )) {
							 * isValidAddrRef = Boolean.TRUE; break; } }
							 */

						}
					}

				}
			} else {
				// This will allow to create empty order without lineitems
				isAllRefExist = Boolean.TRUE;
			}
		} else {
			isAllRefExist = Boolean.TRUE;
		}

		if (isValidAddrRef && isValidBillRef) {
			isAllRefExist = Boolean.TRUE;
		}
		logger.debug("Are all refrence for lineitems are valid : " + isAllRefExist);
		return isAllRefExist;
	}

	/**
	 * Helper method to check service address external id referenced at lineitem
	 * level matches the service address external id for consumer.
	 *
	 * @param consumer
	 * @return
	 */
	public static boolean isServiceAddressExist(Consumer consumer,
			OrderType orderSrc) {

		logger.info("Validating service address reference for lineitems");
		Boolean isAllRefExist = Boolean.FALSE;
		Boolean isValidBillRef = Boolean.FALSE;
		Boolean isValidAddrRef = Boolean.FALSE;
		long billingExtId = 0L;
		long svcAddrExtId = 0L;

		if (orderSrc != null && orderSrc.getLineItems() != null) {
			List<LineItemType> liList = orderSrc.getLineItems()
					.getLineItemList();

			Set<BillingInformation> billingSet = consumer.getBillingInfoList();

			List<BillingInformation> billingList = new ArrayList<BillingInformation>(
					billingSet);

			List<CustomerAddressAssociation> custAddList = new ArrayList<CustomerAddressAssociation>(
					consumer.getAddresses());

			if (liList != null && !liList.isEmpty()) {
				for (LineItemType liType : liList) {
					billingExtId = Long
							.valueOf(liType.getBillingInfoExtId() == null ? "0"
									: liType.getBillingInfoExtId());
					svcAddrExtId = Long
							.valueOf(liType.getSvcAddressExtId() == null ? "0"
									: liType.getSvcAddressExtId());

					// Check lineitem's billing ext id matches with consumer's
					// billing ext id
					for (BillingInformation billing : billingList) {
						logger.info(billing.getExternalId());
						if (billing.getExternalId().longValue() == billingExtId) {
							isValidBillRef = Boolean.TRUE;
							break;
						}
					}

					// Check lineitem's service address ext id matches with
					// consumer's service address id
					AddressBean svcAddress = null;
					for (CustomerAddressAssociation custAddAssociation : custAddList) {
						if (custAddAssociation.getAddressRole()
								.equalsIgnoreCase("serviceAddress")) {
							svcAddress = custAddAssociation.getAddress();
							if (svcAddress != null
									&& svcAddress.getExternalId().longValue() == svcAddrExtId) {
								isValidAddrRef = Boolean.TRUE;
								break;
							}
						}
					}
				}
			} else {
				isAllRefExist = Boolean.TRUE;
			}
			if (isValidAddrRef && isValidBillRef) {
				isAllRefExist = Boolean.TRUE;
			}
		}
		return isAllRefExist;
	}

	/**
	 * Helper method to check unique ids provided for billing Info and Address
	 * are all unique in the request. It should not be unique sytem wide but
	 * should be unique for that request.
	 *
	 * @param customerSrc
	 * @return
	 */
	public static boolean checkDuplicateUniqueIdsExist(CustomerType customerSrc) {

		logger.info("Checking duplicate unique ids");
		Boolean isDuplicateExist = Boolean.FALSE;
		List<String> biUniqueList = new ArrayList<String>();
		List<String> addrUniqueList = new ArrayList<String>();

		if ((customerSrc != null)
				&& (customerSrc.getBillingInfoList() != null)
				&& (customerSrc.getBillingInfoList().getBillingInfoList() != null)) {
			List<CustBillingInfoType> biList = customerSrc.getBillingInfoList()
					.getBillingInfoList();

			// Check billing unique id is same as address unique id
			for (CustBillingInfoType biType : biList) {
				String biUniqueId = biType.getBillingUniqueId();
				biUniqueList.add(biUniqueId);

				if ((customerSrc != null)
						&& (customerSrc.getAddressList() != null)
						&& (customerSrc.getAddressList()
								.getCustomerAddressList() != null)) {
					List<CustAddress> addrList = customerSrc.getAddressList()
							.getCustomerAddressList();
					for (CustAddress addr : addrList) {
						String addrUniqueId = addr.getAddressUniqueId() != null ? addr
								.getAddressUniqueId() : "";
						if (biUniqueId.trim().equalsIgnoreCase(addrUniqueId)) {
							isDuplicateExist = Boolean.TRUE;
							return isDuplicateExist;
						}
					}
				}

			}
		}

		// Check all billing unque ids are unique
		if (biUniqueList.size() > 0) {
			Set<String> biUnqieSet = new HashSet<String>(biUniqueList);
			if (biUnqieSet.size() < biUniqueList.size()) {
				return true;
			}
		}

		// Check all address unique ids are unique
		isDuplicateExist = checkAddressUniqueIdDuplicates(customerSrc,
				addrUniqueList);

		logger.info("Duplicate unique id exist : " + isDuplicateExist);
		return isDuplicateExist;
	}

	private static Boolean checkAddressUniqueIdDuplicates(
			CustomerType customerSrc, List<String> addrUniqueList) {
		for (CustAddress addr : customerSrc.getAddressList()
				.getCustomerAddressList()) {
			String addrUniqueId = addr.getAddressUniqueId() != null ? addr
					.getAddressUniqueId() : "";
			addrUniqueList.add(addrUniqueId);
		}
		if (addrUniqueList.size() > 0) {
			Set<String> addrUniqueSet = new HashSet<String>(addrUniqueList);
			if (addrUniqueSet.size() < addrUniqueList.size()) {
				return true;
			}
		}
		return false;
	}
}
