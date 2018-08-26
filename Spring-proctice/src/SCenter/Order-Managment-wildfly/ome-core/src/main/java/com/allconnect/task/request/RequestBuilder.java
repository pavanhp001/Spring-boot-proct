package com.AL.task.request;

import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;


public interface RequestBuilder<T>
{
	public T buildRequest(final OrderManagementRequestResponseDocument orderDoc);
	public T buildRequestForAddLineitem(final LineItemCollectionType lineItems, String correlationId);
}
