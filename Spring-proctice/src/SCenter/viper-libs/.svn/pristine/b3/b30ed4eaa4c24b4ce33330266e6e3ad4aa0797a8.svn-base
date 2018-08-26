package com.A.vm.util.converter.unmarshall;

//import com.A.xml.v4.CustomerFinancialInfoType;

public class UnmarshallCustomerFinancialInfo {
    
    /*private static final Logger logger = Logger.getLogger(UnmarshallCustomerFinancialInfo.class);
    
    public static ConsumerBean copy(final CustomerFinancialInfoType src,
	    final UnmarshallValidationEnum level, boolean isUpdateRequest) {
	
	final ConsumerBean consumerBean = new ConsumerBean();

	copy(src, consumerBean, isUpdateRequest);

	return consumerBean;
    }

    *//**
     * @param isUpdateRequest
     *            TODO
     * @param financialInfo
     *            Source Financial Information
     * @param dest
     *            Domain Financial Information
     *//*
    public static void copy(final CustomerFinancialInfoType src,
	    final ConsumerBean consumerBean,
	    boolean isUpdateRequest) {
	
	if (src == null) {
	    return;
	}

	copy(consumerBean, src, isUpdateRequest);

    }

    public static void copy(final ConsumerBean consumerBean,
	    final CustomerFinancialInfoType.Employed src,
	    final UnmarshallValidationEnum level, boolean isUpdateRequest) {
	if (consumerBean == null) {
	    return;
	}

	DynamicBuilder<CustomerFinancialInfoType.Employed, ConsumerBean> builder = new DynamicBuilder<CustomerFinancialInfoType.Employed, ConsumerBean>(
		level);

	try {
	    builder.copyInstanceAttributes(src, consumerBean,
		    CustomerFinancialInfoMapper.employed, false);
	} catch (Exception e) {
	    logger.error("Exception thrown while copying Customer Financial Info...",e);
	    e.printStackTrace();
	    throw new IllegalArgumentException("unable.to.unmarshall.customer.financial.info");
	}
    }

    *//**
     * @param src
     *            source
     * @return destination
     *//*
    public static void copy(final ConsumerBean consumerBean,
	    final CustomerFinancialInfoType src,
	    boolean isUpdateRequest) {
	if (consumerBean == null) {
	    return;
	}

	DynamicBuilder<CustomerFinancialInfoType, ConsumerBean> builder = new DynamicBuilder<CustomerFinancialInfoType, ConsumerBean>(
		null);

	try {
	    builder.copyInstanceAttributes(src, consumerBean,
		    CustomerFinancialInfoMapper.financial, false);
	    copy(consumerBean, src.getEmployed(), null, isUpdateRequest);

	} catch (Exception e) {
	    logger.error("Exception thrown while copying Customer Financial Information",e);
	    e.printStackTrace();
	    throw new IllegalArgumentException(
		    "unable.to.unmarshall.customer.financial.info.type");
	}
    }
*/
}
