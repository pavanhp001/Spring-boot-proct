package com.A.V.gateway;

import com.A.xml.me.v4.EnterpriseResponseDocumentType;

public interface MerchandisingClient<T> {

	EnterpriseResponseDocumentType send(T order);

}