package com.A.V.gateway;

import com.A.xml.se.v4.ServiceabilityEnterpriseResponse;

public interface ServiceabilityClient<T> {
	
	ServiceabilityEnterpriseResponse send(T order);
	
}
