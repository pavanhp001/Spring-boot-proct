package com.A.V.beans.job;

public enum SchedulePhase {

	created(0), scheduled(1), active(2), cancelled(-55), failed(-99), completed(1000);
	
	private int status;
	
	private SchedulePhase(final int status) {
	
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
