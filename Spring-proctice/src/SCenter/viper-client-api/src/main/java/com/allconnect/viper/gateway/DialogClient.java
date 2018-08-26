package com.A.V.gateway;

import java.util.Map;

import com.A.xml.di.v4.EnterpriseResponseDocumentType;

 
 
public interface DialogClient<T> {

	EnterpriseResponseDocumentType send(T dialogRequest,Map<String,String> headers);
 
	 
}