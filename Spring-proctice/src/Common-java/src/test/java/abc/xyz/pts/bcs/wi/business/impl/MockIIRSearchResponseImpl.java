/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008
 * All rights reserved.
 */
package abc.xyz.pts.bcs.wi.business.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import abc.xyz.pts.bcs.wi.dto.TargetItem;
import abc.xyz.pts.bcs.wi.iir.search.IIRSearchResponse;

public class MockIIRSearchResponseImpl implements IIRSearchResponse 
{
	private List<TargetItem> list = new ArrayList<TargetItem>();
	
	@Override
	public List<TargetItem> getTargetList()	{
		setList();
		setList(getList());
		return list;
	}
	
	private void setList(final List<TargetItem> list) {	
		this.list.addAll(list);
	}
	
	private void setList() {
	    TargetItem ti = new TargetItem();
	    ti.setId(264L);
	    ti.setForename("MOHAMED");
	    ti.setLastName("RAJI");
	    ti.setDocNo("IJ0NS1GT");
	    ti.setGender("M");
	    ti.setDocType("P");
	    ti.setBirthDate(Calendar.getInstance());
	    ti.setMatchScore(58d);
	    ti.setActcCode("ACTC-CODE-1"); 
	    ti.setAppActionCode("APP-ACTION-CODE"); 
	    ti.setAutoQualify("AUTO-QUALIFY-IND");
	    ti.setSeverityLevel(1L);
	    ti.setRescCodeDesc("SEVERITY-DESC");
	    ti.setWatlDesc("WL-DESC");
	    list.add(ti);
	}
	
	private List<TargetItem> getList() {	
		return list;
	}
}
