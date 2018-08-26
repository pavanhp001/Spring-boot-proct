package com.A.ui.service.V;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.JAXBElement;

import org.apache.commons.io.FileUtils;

import com.A.ui.template.MerchandisingTemplateConstant;
import com.A.V.domain.SalesContext;
import com.A.V.factory.SalesContextFactory;
import com.A.V.gateway.MerchandisingClient;
import com.A.V.gateway.jms.MerchandisingClientJMS;
import com.A.V.gateway.util.JaxbUtil;
import com.A.xml.me.v4.MerchandisedProductType;
import com.A.xml.pr.v4.ProductInfoType;
import com.A.xml.me.v4.EnterpriseResponseDocumentType;
import com.A.xml.me.v4.MerchandisingRequestType.ProductList;


public enum MerchandisingService {
	
	INSTANCE;
	
	private static final JaxbUtil<EnterpriseResponseDocumentType> util = new JaxbUtil<EnterpriseResponseDocumentType>();
	
	
    public EnterpriseResponseDocumentType getMerchandisedProduct(ProductList productList, String GUID,
			SalesContext salesContext) {

    	String request = MerchandisingTemplateConstant.INSTANCE.getMerchandisingRequest(productList, GUID, salesContext);
    	MerchandisingClient<String> client = new MerchandisingClientJMS();
    	EnterpriseResponseDocumentType response = client.send(request);
    	return response;
	}
	

}
