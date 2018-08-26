package com.AL;

import java.util.ArrayList;
import java.util.List;
import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.LineItemDetail;
import com.AL.V.beans.entity.StatusRecordBean;

public class LineItemMotherObject {
	
	

   
    
   
	public static List<LineItem> getLineItem(int numOfLineItems) {
		List<LineItem> list = new ArrayList<LineItem>();

		for (int i = 0; i < numOfLineItems; i++) {
			LineItem lib = new LineItem();
			lib.setId(i);
			lib.setLineItemNumber(i);
			lib.setExternalId(Long.valueOf(i));
			lib.setLineItemNumber(i);
			
			LineItemDetail  detail = new LineItemDetail ();
			detail.setAppliesTo("-1");
			detail.setLineItemDetailExternalId("DTL-EXT-"+i);
			lib.setLineItemDetailBean(detail);
			detail.setType("marketItem");
			 
			
			StatusRecordBean srb = new StatusRecordBean();
			srb.setId(1);
			srb.setStatus("ACTIVE");
			lib.setCurrentStatus(srb);

			list.add(lib);
		}

		return list;
	}
}
