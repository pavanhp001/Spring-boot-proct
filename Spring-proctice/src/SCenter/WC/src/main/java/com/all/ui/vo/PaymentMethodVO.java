package com.A.ui.vo;

import java.util.List;

import org.apache.log4j.Logger;

import com.A.xml.v4.CustAddress;
import com.A.xml.v4.CustBillingInfoType;
import com.A.xml.v4.Customer;
import com.A.xml.v4.LineItemType;

public class PaymentMethodVO {

	private Logger logger = Logger.getLogger(PaymentMethodVO.class);
	private Customer customer;
	private LineItemType lit;

	public PaymentMethodVO(Customer customer, LineItemType lit) {
		this.customer = customer;
		this.lit = lit;
	}

	public List<CustBillingInfoType> getBillingInformation() {

		List<CustBillingInfoType> billingInfoList = null;

		if ((customer != null) && (customer.getBillingInfoList() != null)
				&& (customer.getBillingInfoList().getBillingInfo() != null)) {

			billingInfoList = customer.getBillingInfoList().getBillingInfo();
		}

		return billingInfoList;
	}

	public CustBillingInfoType getLineItemPaymentMethod() {

		CustBillingInfoType resultPaymentOption = null;

		List<CustBillingInfoType> billingInfoList = getBillingInformation();
		String lineItemPaymentMethod = lit.getBillingInfoExtId();
		long lineItemPaymentMethodAsLong = 0;

		if ((lineItemPaymentMethod != null)
				&& (lineItemPaymentMethod.length() > 0)) {

			try {
				lineItemPaymentMethodAsLong = Long
						.valueOf(lineItemPaymentMethod);
			} catch (Exception e) {
				logger.warn(e.getMessage());
			}
			
			if (billingInfoList != null) {
			
			for (CustBillingInfoType paymentOption : billingInfoList) {

				if (paymentOption.getExternalId() == lineItemPaymentMethodAsLong) {
					resultPaymentOption = paymentOption;
					break;
				}
			}
			}
		}
		
		if ((resultPaymentOption == null) &&   (billingInfoList != null) && (billingInfoList.size() > 0)) {
			 resultPaymentOption = billingInfoList.get(0);
		}

		return resultPaymentOption;
	}

	public CustAddress getLineItemPaymentAddress() {

		CustBillingInfoType paymentMethod = getLineItemPaymentMethod();
		String paymentMethodAddressReference = "";
		CustAddress address = null;

		if (paymentMethod != null) {

			paymentMethodAddressReference = paymentMethod.getAddressRef();

			if ((paymentMethodAddressReference != null) && (customer != null)
					&& (customer.getAddressList() != null)
					&& (customer.getAddressList().getCustomerAddress() != null)) {

				address = AddressCollectionVo.getAddressByRef(customer
						.getAddressList().getCustomerAddress(),
						paymentMethodAddressReference);
			}

		}

		return address;
	}

}
