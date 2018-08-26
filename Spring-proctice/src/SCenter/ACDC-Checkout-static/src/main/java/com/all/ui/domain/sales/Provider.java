package com.AL.ui.domain.sales;

import java.util.ArrayList;
import java.util.List;

public class Provider {
	private String externalId;
	private String name;
	private List<Product> products = new ArrayList<Product>();
	private List<String> capabilities = new ArrayList<String>();
	private Provider parent;
	private boolean realtime;

	public Provider() {
	}

	public void addProduct(Product p) {
		if (p == null) {
			return;
		}
 		p.setProvider(this);
		products.add(p);
	}
	
	public List<Product> getProducts() {
           if (products == null) {
              products = new ArrayList<Product>();
           }
           return products;
	}

	public void setProducts(List<Product> arg) {
           getProducts().addAll(arg);
	}

	public List<String> getCapabilities() {
		if (capabilities == null) {
			capabilities = new ArrayList<String>();
		}
		return capabilities;
	}

	public void setCapabilities(List<String> list) {
		getCapabilities().addAll(list);
	}
	
	public void addCapability(String arg) {
		getCapabilities().add(arg);
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Provider getParent() {
		return parent;
	}

	public void setParent(Provider parent) {
		this.parent = parent;
	}

	public boolean isRealtime() {
		return realtime;
	}

	public void setRealtime(boolean realtime) {
		this.realtime = realtime;
	}
   
}

