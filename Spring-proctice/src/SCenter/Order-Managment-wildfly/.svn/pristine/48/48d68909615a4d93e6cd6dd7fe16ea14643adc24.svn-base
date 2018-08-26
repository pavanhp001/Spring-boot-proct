package com.AL.activity.impl;

import java.util.List;

import com.AL.xml.v4.AttributeDetailType;
import com.AL.xml.v4.AttributeEntityType;
import com.AL.xml.v4.LineItemType;

public enum ActivityFalloutOrderValidation {

    INSTANCE;

    private static final String CKO = "CKO";
    private static final String TRUE = "true";
    private static final String IS_FALLOUT_ORDER = "isFalloutOrder";

    public Boolean isFalloutOrder(LineItemType liType) {
	Boolean isFallout = false;

	if(liType != null && liType.getLineItemAttributes() != null && liType.getLineItemAttributes().getEntityList() != null) {
	    List<AttributeEntityType> attribsEntList = liType.getLineItemAttributes().getEntityList();
	    if(attribsEntList != null) {
		for(AttributeEntityType entType : attribsEntList) {
		    if(entType.getSource().equalsIgnoreCase(CKO)){
			List<AttributeDetailType> attrDetailList = entType.getAttributeList();
			if(attrDetailList != null && !attrDetailList.isEmpty()) {
			    for(AttributeDetailType dtlType : attrDetailList) {
				if(dtlType.getName().equalsIgnoreCase(IS_FALLOUT_ORDER) && dtlType.getValue().equalsIgnoreCase(TRUE)) {
				    isFallout = Boolean.TRUE;
				    break;
				}
			    }
			}
		    }
		}
	    }
	}

	return isFallout;
    }
}
