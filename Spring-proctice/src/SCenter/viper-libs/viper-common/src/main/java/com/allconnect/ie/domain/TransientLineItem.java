package com.A.ie.domain;

import com.A.xml.v4.LineItemType;

public class TransientLineItem {

	private int number;
	private LineItemType lineitem;
	
	
	public TransientLineItem(LineItemType lineItem, int number)
	{
		this.number = number;
		this.lineitem = lineItem;
	}


	public int getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
	}


	public LineItemType getLineitem() {
		return lineitem;
	}


	public void setLineitem(LineItemType lineitem) {
		this.lineitem = lineitem;
	}

	
}
