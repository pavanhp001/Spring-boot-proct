package com.A.vm.util.converter.unmarshall;

import org.springframework.stereotype.Component;
//import com.A.xml.v4.CustomerType;

@Component
public class UnmarshallCustomer {

    /*private static final Logger logger = Logger
	    .getLogger(UnmarshallCustomer.class);
    private static final String PREV_ADDRESS = "ac:previousAddress";
    private static final String CURR_ADDRESS = "ac:address";
    private static final String FIN_INFO = "ac:financialInfo";

    private static ConsumerBean copyCustomerInfo(CustomerType src, ConsumerBean dest,
	    boolean isUpdateRequest) {

	logger.info("copyCustomerInfo()...");

	// Demographic info
	UnmarshallCustomerDemographicInfo.copy(src, dest, isUpdateRequest);

	// Previous address
	if (isUpdateRequest) {

	    if (src != null) {
		if (!XmlUtil.isElementNull(src.newCursor(), PREV_ADDRESS)) {
		    dest.setPreviousAddress(UnmarshallAddressBean.copy(
			    src.getPreviousAddress(),
			    dest.getPreviousAddress(), null, isUpdateRequest));
		} else {
		    logger.info("Skipped updating PREVIOUS ADDRESS because there is no element in xml...............");
		}
	    }
	} else {
	    dest.setPreviousAddress(UnmarshallAddressBean.copy(
		    src.getPreviousAddress(), null, isUpdateRequest));
	}

	// Current Address
	if (isUpdateRequest) {

	    if (src != null) {
		if (!XmlUtil.isElementNull(src.newCursor(), CURR_ADDRESS)) {

		    dest.setAddress(UnmarshallAddressBean.copy(
			    src.getAddress(), dest.getAddress(), null,
			    isUpdateRequest));

		} else {
		    logger.info("Skipped updating ADDRESS because there is no element in xml...............");
		}
	    }
	} else {
	    // In case of CREATE ORDER REQUEST
	    dest.setAddress(UnmarshallAddressBean.copy(src.getAddress(), null,
		    isUpdateRequest));
	}

	if (src != null) {
	    // financial info
	    if (!XmlUtil.isElementNull(src.newCursor(), FIN_INFO))
		UnmarshallCustomerFinancialInfo.copy(src.getFinancialInfo(),
			dest, isUpdateRequest);
	}
	// Contact info
	UnmarshallCustomerContactInfo.copyCustomerContactInfo(src, dest, null,
		isUpdateRequest);
	return dest;
    }

    public ConsumerBean build(final Request request, boolean isUpdateRequest) {
	if (request == null) {
	    throw new IllegalArgumentException("invalid.null.request");
	}

	return doBuildCustomer(request, isUpdateRequest);

    }

    private ConsumerBean doBuildCustomer(final Request originalMessage,
	    boolean isUpdateRequest) {
	CustomerType customerType = originalMessage.getCustomerInfo();

	//TODO add check for update request, if it is updateRequest then use the existing one
	ConsumerBean consumerBean = new ConsumerBean();

	if (customerType == null) {
	    return consumerBean;
	}

	return copyCustomerInfo(customerType, consumerBean, isUpdateRequest);
    }

*/
}
