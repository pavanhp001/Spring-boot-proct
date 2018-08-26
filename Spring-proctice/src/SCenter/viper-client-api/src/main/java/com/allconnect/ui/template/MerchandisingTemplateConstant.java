package com.A.ui.template;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.xml.namespace.QName;

import com.A.V.domain.SalesContext;
import com.A.V.gateway.util.JaxbUtil;
import com.A.xml.cm.v4.CustomerType;
import com.A.xml.me.v4.MerchandisingRequestType;
import com.A.xml.me.v4.MerchandisingRequestType.ProductList;
import com.A.xml.me.v4.MerchandisingResponseType;

public enum MerchandisingTemplateConstant {

	INSTANCE;
	
	public static final Logger logger = Logger.getLogger(MerchandisingTemplateConstant.class);
    private static JAXBContext productListContext = null;
    private static QName qname = new QName("productList");
    
    static {
    	
    	try {
    		productListContext = JAXBContext.newInstance(ProductList.class);
    	} catch(JAXBException e) {
    		e.printStackTrace();
    	}
    }

	private static final JaxbUtil<SalesContext> utiltSalesCtx = new JaxbUtil<SalesContext>();

	private static final JaxbUtil<ProductList> utilProdType = new JaxbUtil<ProductList>();
	
	public final String SOAP_ENVELOPE_TEMPLATE_START = "<soapenv:envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:header/><soapenv:body>";

	public final String SOAP_ENVELOPE_TEMPLATE_END = "</soapenv:body></soapenv:envelope>";

	public final String MERCHANDISING_REQUEST_TEMPLATE =
		"<merchandisingEnterpriseRequest xmlns=\"http://xml.A.com/v4\" "
		 +"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"> "
		  +"<GUID>#!GUID!#</GUID> "
		  +"<transactionType xsi:type=\"v4:merchandisingTransactionTypeType\" xmlns:v4=\"http://xml.A.com/v4\"> "
		  +" <merchandisingTransactionType>processAlgorithm</merchandisingTransactionType> "
		  +" </transactionType> "
		  +" #!SALES!# "
		  +"<request xsi:type=\"v4:merchandisingRequestType\" xmlns:v4=\"http://xml.A.com/v4\"> "	 
		  +"#!PRODUCTLIST!#"
		  +" </request> "
		  +" </merchandisingEnterpriseRequest> "; 
	
	 

	public final String MERCHANDISING_JMS_HEADER_START = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" 
	+ "<ac:acMessage xmlns:ac=\"http://xml.A.com/v4\" " 
	+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
	+ "xsi:schemaLocation=\"http://xml.A.com/v4/acMessageWrapper.xsd \"> "
	+ "<ac:source>source</ac:source> "
	+ "<ac:msgType>request</ac:msgType> "
	+ "<ac:actionType>query</ac:actionType> " 
	+ "<ac:payloadType>MerchandisingRequestDocument</ac:payloadType> "
	+ "<ac:payload> ";
		  
		  

	public final String MERCHANDISING_JMS_HEADER_END = "</ac:payload>"
			+ "</ac:acMessage>";

	public final String MERCHANDISING_JMS = MERCHANDISING_JMS_HEADER_START
			+ MERCHANDISING_REQUEST_TEMPLATE + MERCHANDISING_JMS_HEADER_END;
	public final String MERCHANDISING_SOAP = SOAP_ENVELOPE_TEMPLATE_START
			+ MERCHANDISING_REQUEST_TEMPLATE + SOAP_ENVELOPE_TEMPLATE_END;
	
	
	
	public String getMerchandisingRequest(ProductList productList, String GUID,
			SalesContext salesContext) {
		//TODO create jaxb object for merchandisingEnterpriseRequest
		//TODO remove string constants of merchandising request template
		String salesContextAsString = utiltSalesCtx.toString(salesContext,
				SalesContext.class, Boolean.FALSE);

		String merchandisingTemplate = StringUtils.replace(
				MerchandisingTemplateConstant.INSTANCE.MERCHANDISING_JMS, "#!GUID!#", GUID);

		logger.info("productList::"+productList);
		
		String productListAsString = toString(productList);
		
		merchandisingTemplate = StringUtils.replace(merchandisingTemplate, "#!PRODUCTLIST!#",
				productListAsString);

		merchandisingTemplate = StringUtils.replace(merchandisingTemplate, "#!SALES!#",
				salesContextAsString);
		
		return merchandisingTemplate;

	}
	
	public String toString(ProductList productList)  {
		StringWriter sw = new StringWriter();
		String temp = null;
		try {
			Marshaller marshaller = productListContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty("com.sun.xml.bind.xmlDeclaration",
					false);
			JAXBElement<ProductList> element = new JAXBElement<ProductList>(qname, ProductList.class, productList);
			marshaller.marshal(element, sw);
			temp = sw.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		} finally {
			try {
				sw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return temp;
	}

	
	public String getSoapMerchandisingRequest(ProductList productList, String GUID,
			SalesContext salesContext) {

		String salesContextAsString = utiltSalesCtx.toString(salesContext,
				SalesContext.class, Boolean.FALSE);
		
		String productListAsString = utilProdType.toString(productList,
				ProductList.class, Boolean.FALSE);		

		String merchandisingTemplate = StringUtils.replace(
				MerchandisingTemplateConstant.INSTANCE.MERCHANDISING_SOAP, "#!GUID!#", GUID);

		merchandisingTemplate = StringUtils.replace(merchandisingTemplate, "#!PRODUCTLIST!#",
				productListAsString);

		merchandisingTemplate = StringUtils.replace(merchandisingTemplate, "#!SALES!#",
				salesContextAsString);

		return merchandisingTemplate;

	}

	public final String TEMPLATE = SOAP_ENVELOPE_TEMPLATE_START
			+ MERCHANDISING_REQUEST_TEMPLATE + SOAP_ENVELOPE_TEMPLATE_END;

}
