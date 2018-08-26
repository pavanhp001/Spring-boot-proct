package com.A.ui.service.event;

public enum NotificationEventIdentifier {

	processByProvider("Process by Provider", 1);

	private String description;
	private int code;

	private NotificationEventIdentifier(final String value, final int code) {
		this.description = value;
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String value) {
		this.description = value;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}