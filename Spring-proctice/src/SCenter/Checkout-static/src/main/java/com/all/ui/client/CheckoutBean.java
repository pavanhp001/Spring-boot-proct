/**
 * 
 */
package com.AL.ui.client;

import java.util.List;

/**
 * @author spatil
 *
 */
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("CKO")
public class CKOBean {

	private String orderId;
	//private List<String> lineItems;
	private String status;
	private String message;
	

	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/*public List<String> getLineItems() {
		return lineItems;
	}
	public void setLineItems(List<String> lineItems) {
		this.lineItems = lineItems;
	}*/
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
