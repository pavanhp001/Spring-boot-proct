package com.A.V.gateway;

import com.A.xml.dtl.v4.DetailsRequestResponse;

public interface DetailManagementClient<T> {
	
	DetailsRequestResponse send(T order);
	
}
