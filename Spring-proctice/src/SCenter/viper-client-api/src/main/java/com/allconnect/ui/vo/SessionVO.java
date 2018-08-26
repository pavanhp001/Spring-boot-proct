package com.A.ui.vo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SessionVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8930572604282879120L;
	private Map<String, Object> data = new HashMap<String, Object>();
	private String sessionId;

	@SuppressWarnings("unused")
	private SessionVO() {
		super();
	}
	
	public static SessionVO create(final String key) {
		SessionVO sessionVO = new SessionVO(key);
		 
		return sessionVO;
	}
	

	public SessionVO(final String sessionId) {
		this.sessionId = sessionId;
	}

	public Object get(final String key) {
		return getData().get(key);
	}

	public String getAsString(final String key) {
		if(getData().get(key) != null) {
			return (String) getData().get(key);
		} else {
			return null;
		}
	}

	public Long getAsLong(final String key) {
		return (Long) getData().get(key);
	}

	public Calendar getAsCalendar(final String key) {
		return (Calendar) getData().get(key);
	}

	public void put(final String key, final Object value) {

		getData().put(key, value);
	}

	public Map<String, Object> getData() {

		if (data == null) {
			data = new HashMap<String, Object>();
		}
		return data;
	}

	public String getSessionId() {
		return sessionId;
	}

}
