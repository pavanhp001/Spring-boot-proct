package com.A.enums;

import com.A.xml.v4.LineItemStatusCodesType;




public enum LineItemStatus {

	//Sales
	sales_new_order("sales new order",0, LineItemStatusCodesType.SALES_NEW_ORDER), //
	sales_defer("sales convert",0, LineItemStatusCodesType.SALES_DEFER), //
	sales_submitted("sales submitted",0, LineItemStatusCodesType.SALES_SUBMITTED), //

	//New Provision Ready
	sales_pre_order("sales pre order",0, LineItemStatusCodesType.SALES_PRE_ORDER),//
	provision_ready("provision ready",0, LineItemStatusCodesType.PROVISION_READY),//
	provision_problem("provision problem",0, LineItemStatusCodesType.PROVISION_PROBLEM),//



	//Scheduled
	processing_schedule_confirmed("schedule confirmed",3,LineItemStatusCodesType.PROCESSING_SCHEDULE_CONFIRMED), //

	//Completed
	processing_connected("connected",5,LineItemStatusCodesType.PROCESSING_CONNECTED), //
	processing_disconnected("disconnected",6,LineItemStatusCodesType.PROCESSING_DISCONNECTED), //
	processing_aged("aged",7,LineItemStatusCodesType.PROCESSING_AGED), //

	//hold
	processing_schedule_pending("schedule pending",2,LineItemStatusCodesType.PROCESSING_SCHEDULE_PENDING), //
	hold_order_pending_problem("order pending problem",9,LineItemStatusCodesType.HOLD_ORDER_PENDING_PROBLEM), //
	processing_problem_ordered_vru_failed("problem ordered vru failed",4,LineItemStatusCodesType.PROCESSING_PROBLEM_ORDERED_VRU_FAILED), //
	hold_customer_action( "hold customer action",10,LineItemStatusCodesType.HOLD_CUSTOMER_ACTION), //
	hold_authorization_pending( "hold authorization pending",11,LineItemStatusCodesType.HOLD_AUTHORIZATION_PENDING), //
	hold_provider("hold provider",12,LineItemStatusCodesType.HOLD_PROVIDER), //
	hold_order( "hold order",13,LineItemStatusCodesType.HOLD_ORDER), //
	submit_failed("submit failed",14,LineItemStatusCodesType.SUBMIT_FAILED),//

	//cancelled
	processing_cancelled("cancelled",8,LineItemStatusCodesType.PROCESSING_CANCELLED), //
	failed("failed",14,LineItemStatusCodesType.SUBMIT_FAILED),//
	cancelled_removed("removed",15,LineItemStatusCodesType.CANCELLED_REMOVED),
	failed_test("test",999,LineItemStatusCodesType.CANCELLED_REMOVED),

	//Future
	reverse_schedule_pending("reverse schedule pending",51,LineItemStatusCodesType.PROCESSING_SCHEDULE_PENDING), //
	reverse_schedule_confirmed("reverse schedule confirmed",52,LineItemStatusCodesType.PROCESSING_SCHEDULE_CONFIRMED), //
	reverse_connected("reverse connected",53,LineItemStatusCodesType.PROCESSING_CONNECTED), //
	reverse_disconnected("reverse disconnected",54,LineItemStatusCodesType.PROCESSING_DISCONNECTED), //
	reverse_aged("reverse aged",55,LineItemStatusCodesType.PROCESSING_AGED), //

	//TEST
	test_order("test_order",56, LineItemStatusCodesType.TEST_ORDER); //


	private String description;
    private int id;
    private LineItemStatusCodesType.Enum transportType;

    public LineItemStatus getById(int id) {
    	LineItemStatus[] list = LineItemStatus.values();

    	for (LineItemStatus lis:list) {
    		if (lis.getId() == id) {
    			return lis;
    		}
    	}

    	return LineItemStatus.sales_new_order;
    }

	private LineItemStatus(final String value, final int id, final LineItemStatusCodesType.Enum codetype) {
		this.description = value;
		this.id = id;
		this.transportType = codetype;
	}

	public String getValue() {
		return description;
	}

	public void setValue(String value) {
		this.description = value;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LineItemStatusCodesType.Enum getTransportType() {
		return transportType;
	}

	public void setTransportType(LineItemStatusCodesType.Enum transportType) {
		this.transportType = transportType;
	}



}
