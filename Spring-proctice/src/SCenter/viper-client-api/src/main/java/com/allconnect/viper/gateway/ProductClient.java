package com.A.V.gateway;

import com.A.xml.pr.v4.EnterpriseResponseDocumentType;
import java.util.Map;

public interface ProductClient<T> {

	EnterpriseResponseDocumentType send(T order, Map<String,String> headers);

}