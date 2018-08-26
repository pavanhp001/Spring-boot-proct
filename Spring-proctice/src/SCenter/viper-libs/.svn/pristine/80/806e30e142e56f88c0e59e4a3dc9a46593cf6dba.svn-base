package com.A.util;

public enum NotificationEvents {

	processByProvider("Process by Provider", 1);

	private String description;
	private int id;

	public static String resolveStatus(final String status) {

		if (status == null) {
			return processByProvider.name();
		}

		try {

			NotificationEvents resolvedStatus = NotificationEvents
					.valueOf(status.toLowerCase());
			return resolvedStatus.name();
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage()
					+ "--order status invalid:" + status);

		}
	}

	private NotificationEvents(final String value, final int id) {
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
