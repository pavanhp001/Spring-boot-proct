package com.AL;

import java.util.ArrayList;
import java.util.List;

import com.AL.V.beans.entity.LineItem;

public class OrderMotherObject {

	public static List<LineItem> getLineItem(int numOfLineItems) {
		List<LineItem> list = new ArrayList<LineItem>();

		for (int i = 0; i < numOfLineItems; i++) {
			LineItem lib = new LineItem();
			lib.setId(i);
			lib.setLineItemNumber(i);
			lib.setExternalId(Long.valueOf(i));

			list.add(lib);
		}

		return list;
	}
}
