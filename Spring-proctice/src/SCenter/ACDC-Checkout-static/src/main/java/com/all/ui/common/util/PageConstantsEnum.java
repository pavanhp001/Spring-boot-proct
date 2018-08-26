package com.AL.ui.common.util;

import com.AL.ui.constants.Constants;

public enum PageConstantsEnum {
	/* Page id constants declaration here */
	PRODUCT_INFO(Constants.PRODUCT_INFO_PAGE_ID, Constants.PRODUCT_INFO_PAGE_VAL),
	OQ_DEMO_CONTENTS(Constants.OQ_DEMO_CONTENTS_ID, Constants.OQ_DEMO_CONTENTS_VAL),
	
	/*CREDIT_CHECK_PAGE(Constants.CREDIT_CHECK_PAGE_ID, Constants.CREDIT_CHECK_PAGE_VAL),
	UPDATE_LINEITEM_FAIL(Constants.UPDATE_LINEITEM_FAIL_KEY, Constants.UPDATE_LINEITEM_FAIL_VAL),
	ORDERQUAL_DISPALY_PAGE(Constants.ORDERQUAL_DISPALY_PAGE_ID, Constants.ORDERQUAL_DISPALY_PAGE_VAL),
	ORDERQUAL_DUEDATE_PAGE(Constants.ORDERQUAL_DUEDATE_PAGE_ID, Constants.ORDERQUAL_DUEDATE_PAGE_VAL),
	ORDERSUBMIT_PAGE(Constants.ORDERSUBMIT_PAGE_ID, Constants.ORDERSUBMIT_PAGE_VAL),
	PAYMENT_PAGE(Constants.PAYMENT_PAGE_ID, Constants.PAYMENT_PAGE_VAL),*/
	
	/* Error code constants declaration here */
	SUCCESS_ST_CODE(Constants.SUCCESS_ST_CODE_KEY, Constants.SUCCESS_ST_CODE_VAL),
	ERROR_ST_CODE(Constants.ERROR_ST_CODE_KEY, Constants.ERROR_ST_CODE_VAL),
	WARNING_ST_CODE(Constants.WARNING_ST_CODE_KEY, Constants.WARNING_ST_CODE_VAL);
	
	private PageConstantsEnum(String name, String id) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.id = id;
	}
	
	String name;
	String id;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	
}  