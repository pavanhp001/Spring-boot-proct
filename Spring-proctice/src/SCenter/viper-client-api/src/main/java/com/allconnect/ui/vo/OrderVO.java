package com.A.ui.vo;

import com.A.xml.v4.OrderType;

public class OrderVO  {

	private boolean isAccord = Boolean.FALSE;
	private OrderType orderType = null;
	
	public OrderVO(OrderType orderType, boolean isAccord) {
		this.orderType = orderType;
		this.isAccord = isAccord;
	}

	public boolean isAccord() {
		return isAccord;
	}

	public void setAccord(boolean isAccord) {
		this.isAccord = isAccord;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	@Override
	public String toString() {
		return "OrderVO [isAccord=" + isAccord + ", orderType=" + orderType
				+ "]";
	}
	
	
	
}
