package com.A.ui.domain;

 


import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author hestacio
 * 
 */

@Entity
@Table(name = "CUSTOMER_DISCOVERY")
public class CustomerDiscovery implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private long id;
	
	@Column(name = "ORDERID")
	private Long orderId;

	@Column(name = "CUSTOMERID")
	private Long customerId;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "VALUE")
	private String value;	
	
	@Column(name = "DATE")
	private Calendar date;
	
	public static CustomerDiscovery create(Long orderId, Long customerId, String name, String value) {

		CustomerDiscovery customerDiscovery = new CustomerDiscovery();
		customerDiscovery.setOrderId(orderId);
		customerDiscovery.setCustomerId(customerId);
		customerDiscovery.setName(name);
		customerDiscovery.setValue(value);
		customerDiscovery.setDate(Calendar.getInstance());
		return customerDiscovery;
	}

	@Override
	public String toString() {
		
		return "customerDiscovery [Id=" + id + ", orderId=" + orderId + ", customerId="
				+ customerId + ", name=" + name + ", value=" + value + "]";
	}	
		
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}	

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}	
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}
}