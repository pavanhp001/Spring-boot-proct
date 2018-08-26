package com.AL.order.status;

import com.AL.enums.OrderStatus;

public enum Events {

	processByProvider("Process by Provider", 1);

	private String description;
	private int id;

	public static String resolveStatus(final String status) {

		if (status == null) {
			return processByProvider.name();
		}

		try {

			OrderStatus resolvedStatus = OrderStatus.valueOf(status
					.toLowerCase());
			return resolvedStatus.name();
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage()
					+ "--order status invalid:" + status);

		}
	}

	private Events(final String value, final int id) {
		this.description = value;
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String value) {
		this.description = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
