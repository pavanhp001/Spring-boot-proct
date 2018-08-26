package com.AL.ie.activity.impl;

import java.util.ArrayList;

import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.StatusRecordBean;

public enum ActivityUpdateStatusHistory {

	INSTANCE;
	
	public void addStatusRecordHistory(LineItem li ) {
		if (li.getHistoricStatus() == null) {
			li.setHistoricStatus(new ArrayList<StatusRecordBean>());
		}

		StatusRecordBean current = li.getCurrentStatus();
		
		if (current == null) {
			throw new IllegalArgumentException("missing current status on lineitem.  Illegal Order.");
		}

		Boolean isAlreadyExist = Boolean.FALSE;
		alreadyExistTest: for (StatusRecordBean srb : li.getHistoricStatus()) {
			if (srb.getId() == current.getId()) {
				isAlreadyExist = Boolean.TRUE;
				break alreadyExistTest;
			}
		}

		if (!isAlreadyExist) {
			li.getHistoricStatus().add(current);
		} 
	}
	
	public void addStatusRecordHistory(LineItem li, StatusRecordBean newStatus) {
		if (li.getHistoricStatus() == null) {
			li.setHistoricStatus(new ArrayList<StatusRecordBean>());
		}

		//***********************************
		//ENSURE CURRENT STATUS IS IN HISTORY
		//***********************************
		StatusRecordBean current = li.getCurrentStatus();

		Boolean isAlreadyExist = Boolean.FALSE;
		alreadyExistTest: for (StatusRecordBean srb : li.getHistoricStatus()) {
			if (srb.getId() == current.getId()) {
				isAlreadyExist = Boolean.TRUE;
				break alreadyExistTest;
			}
		}

		if (!isAlreadyExist) {
			li.getHistoricStatus().add(current);
		}

		//***********************************
		//ENSURE NEW STATUS IS IN HISTORY
		//***********************************
		isAlreadyExist = Boolean.FALSE;
		alreadyExistTest: for (StatusRecordBean srb : li.getHistoricStatus()) {
			if (srb.getId() == newStatus.getId()) {
				isAlreadyExist = Boolean.TRUE;
				break alreadyExistTest;
			}
		}

		if (!isAlreadyExist) {
			li.getHistoricStatus().add(newStatus);
		}
	}
}
