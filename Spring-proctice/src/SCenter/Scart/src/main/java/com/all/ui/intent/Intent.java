package com.AL.ui.intent;

import java.util.HashMap;
import java.util.Map;

import com.AL.ui.domain.WebLookupCollection;
import com.AL.xml.cm.v4.AddressType;
import com.AL.xml.cm.v4.CustAddress;
import com.AL.xml.cm.v4.CustomerType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderType;

public class Intent {

	private IntentAction step;
	private Map<String, Object> extras;

	public Intent(IntentAction step) {
		this.step = step;
	}

	public IntentAction getStep() {
		return step;
	}

	public void setStep(IntentAction step) {
		this.step = step;
	}

	public Map<String, Object> getExtras() {

		if (extras == null) {
			extras = new HashMap<String, Object>();
		}
		return extras;
	}

	public void setExtras(Map<String, Object> extras) {
		this.extras = extras;
	}
	
	public String[] getStringArrayExtra(final String key) {
		return (String[])this.getExtras().get(key);
	}

	public String getStringExtra(final String key) {
		return (String)this.getExtras().get(key);
	}
	
	public String getAsString(final String key) {
		return (String)this.getExtras().get(key);
	}
	
	public String[] getAsStringArray(final String key) {
		return (String[])this.getExtras().get(key);
	}
	
	public AddressType getAsAddressType(final String key){
		return (AddressType)this.getExtras().get(key);
	}
	
	public CustAddress getAsCustAddress(final String key){
		return (CustAddress)this.getExtras().get(key);
	}
	
	public CustomerType getAsCustomerType(final String key){
		return (CustomerType)this.getExtras().get(key);
	}
	
	public WebLookupCollection getAsWebLookupCollection(final String key){
		return (WebLookupCollection)this.getExtras().get(key);
	}
	
	public CustomerType getCustomer( ) {
		return (CustomerType)this.getExtras().get("customer");
	}

	
	public OrderType getOrder() {
		return (OrderType)this.getExtras().get("order");
	}
	
	
	public LineItemType getLineItem() {
		return (LineItemType)this.getExtras().get("lineitem");
	}
	
	public void put(final CustomerType customer ) {
		 this.getExtras().put( "customer",customer);
	}
	
	public void put(final OrderType order ) {
		 this.getExtras().put( "order",order);
	}
	
	public void put(final LineItemType lineitem ) {
		 this.getExtras().put("lineitem", lineitem);
	}
	
	
 

}
