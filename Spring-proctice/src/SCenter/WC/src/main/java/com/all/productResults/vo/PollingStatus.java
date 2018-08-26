package com.A.productResults.vo;
/**
 * 
 * Represents Provider Polling Status when fetching Products/Plans 
 * for realtime and static Providers.
 * 
 * Initially the Status is Pending
 * When Products are retrieved, Status is Success
 * If wait time exceeds a predefined interval, Status is Abort
 * Any Failure in fetching the products, Status is Failed
 *
 */
public class PollingStatus {

	public static final String PENDING = "Pending";
	public static final String SUCCESS = "Sell";
	public static final String FAILED = "Not Available";
	public static final String ABORT = "TimeOut";
	public static final String SYSTEM_ERROR = "System Error";
	public static final String MULTIADDRISSUE = "Multi_Addr_Issue";
	
	private String extId;
	private String name;
	private String status;
	private String statusMsg;
	public PollingStatus(String extId, String name, String status,
			String statusMsg) {
		this.extId = extId;
		this.name = name;
		this.status = status;
		this.statusMsg = statusMsg;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	public String getExtId() {
		return extId;
	}
	public String getName() {
		return name;
	}
	public boolean isPending() {
		return PENDING.equals(status);
	}

}