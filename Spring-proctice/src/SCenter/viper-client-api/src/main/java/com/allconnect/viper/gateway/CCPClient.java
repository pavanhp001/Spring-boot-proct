package com.A.V.gateway;

import com.A.xml.v4.CcpRequestResponse;

public interface CCPClient {

	CcpRequestResponse send(CcpRequestResponse ccprr);
	
	String extract(String ccprr);
}
