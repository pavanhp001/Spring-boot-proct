package com.AL.activity.lineitem.impl;

import java.util.List;

import com.AL.util.convert.SafeConvert;
import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.SalesOrder;

public enum ActivityLocateLineItem {

	INSTANCE;
	
	public LineItem execute(final String requestLineItemId,
			final SalesOrder order) {

		if (order != null) {

			List<LineItem> liList = order.getLineItems();

			if (liList != null) {

				for (LineItem li : liList) {
					long requestLineItemIdAsLong = SafeConvert
							.convertLong(requestLineItemId);
					if (li.getExternalId().equals(requestLineItemIdAsLong)) {
						return li;
					}
				}
			}
		}

		return null;
	}
}
