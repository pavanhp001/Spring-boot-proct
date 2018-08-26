package com.AL.ie.service.strategy;

import java.util.ArrayList;
import java.util.List;
import com.AL.xml.v4.LineItemCollectionType;

public class ArbiterFlow<T, U> {

	private Object lock = new Object();
	private String guid = null;
	private T request = null;
	private U response = null;
	private long timeStarted;
	private long timeToLive;
	private boolean readyToProcess = false;
	List<LineItemCollectionType> lineItemCollectionTypeList = new ArrayList<LineItemCollectionType>();

	public ArbiterFlow(T request, long timeToLive) {
		this.guid = ArbiterFlowKey.nextVal();
		this.request = request;
		this.timeStarted = System.currentTimeMillis();
		this.timeToLive = timeToLive;
		this.response = null;
	}

	public Object getLock() {
		return lock;
	}

	public void setLock(Object lock) {
		this.lock = lock;
	}

	public long getTimeStarted() {
		return timeStarted;
	}

	public void setTimeStarted(long timeStarted) {
		this.timeStarted = timeStarted;
	}

	public long getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(long timeToLive) {
		this.timeToLive = timeToLive;
	}

	public T getRequest() {
		return request;
	}

	public void setRequest(T request) {
		this.request = request;
	}

	public U getResponse() {
		return response;
	}

	public void setResponse(U response) {
		this.response = response;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	 

	 

	public List<LineItemCollectionType> getLineItemCollectionTypeList() {
		return lineItemCollectionTypeList;
	}

	public void setLineItemCollectionTypeList(
			List<LineItemCollectionType> lineItemCollectionTypeList) {
		this.lineItemCollectionTypeList = lineItemCollectionTypeList;
	}
	
	
	
	public void waitIsReady() {
		this.setReadyToProcess(Boolean.TRUE);
	}
	

	public boolean isReadyToProcess() {
		return readyToProcess;
	}

	public void setReadyToProcess(boolean readyToProcess) {
		this.readyToProcess = readyToProcess;
	}

	@Override
	public String toString() {
		return "ArbiterFlow [lock=" + lock + ", guid=" + guid + ", request="
				+ request + ", response=" + response + ", timeStarted="
				+ timeStarted + ", timeToLive=" + timeToLive + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((guid == null) ? 0 : guid.hashCode());
		result = prime * result + ((lock == null) ? 0 : lock.hashCode());
		result = prime * result + ((request == null) ? 0 : request.hashCode());
		result = prime * result
				+ ((response == null) ? 0 : response.hashCode());
		result = prime * result + (int) (timeStarted ^ (timeStarted >>> 32));
		result = prime * result + (int) (timeToLive ^ (timeToLive >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArbiterFlow other = (ArbiterFlow) obj;
		if (guid == null) {
			if (other.guid != null)
				return false;
		} else if (!guid.equals(other.guid))
			return false;
		if (lock == null) {
			if (other.lock != null)
				return false;
		} else if (!lock.equals(other.lock))
			return false;
		if (request == null) {
			if (other.request != null)
				return false;
		} else if (!request.equals(other.request))
			return false;
		if (response == null) {
			if (other.response != null)
				return false;
		} else if (!response.equals(other.response))
			return false;
		if (timeStarted != other.timeStarted)
			return false;
		if (timeToLive != other.timeToLive)
			return false;
		return true;
	}

}
