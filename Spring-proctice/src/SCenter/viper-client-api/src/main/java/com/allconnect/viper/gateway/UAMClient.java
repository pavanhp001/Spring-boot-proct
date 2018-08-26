package com.A.V.gateway;

import com.A.xml.uam.v4.EnterpriseRequestDocumentType;
import com.A.xml.uam.v4.EnterpriseResponseDocumentType;

public interface UAMClient {
	
	EnterpriseResponseDocumentType send(EnterpriseRequestDocumentType uamRequest);
	
}
