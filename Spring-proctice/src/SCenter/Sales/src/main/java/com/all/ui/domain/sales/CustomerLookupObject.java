package com.AL.ui.domain.sales;

import java.util.LinkedList;
import java.util.List;
/**
 * 
 */
public class CustomerLookupObject {
	
	private List<CustomerLookupItem> customerLookupItems = new LinkedList<CustomerLookupItem>();
	private String id;

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public List<CustomerLookupItem> getLineItems() {
		return customerLookupItems;
	}

	public void setLineItems(List<CustomerLookupItem> lineItems) {
		this.customerLookupItems = lineItems;
	}

}
