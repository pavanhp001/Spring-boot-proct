package com.A.ui.template;

import java.io.IOException;
import java.io.StringWriter;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import com.A.V.domain.SalesContext;
import com.A.V.gateway.util.JaxbUtil;
import com.A.xml.pr.v4.AddressType;
import com.A.xml.pr.v4.ProductRequestType.ProviderList;

public enum ProductTemplateConstant {

	INSTANCE;

	private static final JaxbUtil<SalesContext> util = new JaxbUtil<SalesContext>();
	
	private static JAXBContext providerListContext = null;
	private static JAXBContext addressContext = null;
	
	static {
		
		try {
			providerListContext = JAXBContext.newInstance(ProviderList.class);
			addressContext = JAXBContext.newInstance(AddressType.class);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}	

	public final String SOAP_ENVELOPE_TEMPLATE_START = "<soapenv:envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:header/><soapenv:body>";

	public final String SOAP_ENVELOPE_TEMPLATE_END = "</soapenv:body></soapenv:envelope>";

	public final String GET_PRODUCT_DETAILS_REQUEST_TEMPLATE =
		"<productEnterpriseRequest xmlns=\"http://xml.A.com/v4\" "
		 +"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"> "
		  +"<GUID>#!GUID!#</GUID> "
		  +"<transactionType xsi:type=\"v4:productTransactionTypeType\" xmlns:v4=\"http://xml.A.com/v4\"> "
		  +" <productTransactionType>getProductDetails</productTransactionType> "
		  +" </transactionType> "
		  +" #!SALES!# "
		  +"<request xsi:type=\"v4:productRequestType\" xmlns:v4=\"http://xml.A.com/v4\"> "	 
		  +"	<requestedDetails> " 
		  +" <detail level=\"basic\"/> "
		  +" <detail level=\"features\"/> "
		  +" <detail level=\"customizations\"/> "
		  +" <detail level=\"promotions\"/> "
		+" <detail level=\"descriptiveInfo\"/> "
		+" <detail level=\"marketingHighlights\"/> "
		+" <detail level=\"metadata\"/> "
		+" </requestedDetails>"
		+" <productList>"
		+" <productId externalId=\"#!PRODUCT!#\" providerExternalId=\"#!PROVIDER!#\"/> "
		+" </productList> " 
		+" </request> "
		+" </productEnterpriseRequest> ";
	
	public final String GET_PRODUCT_DETAILS_WITH_META_REQUEST_TEMPLATE =
		"<productEnterpriseRequest xmlns=\"http://xml.A.com/v4\" "
		 +"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"> "
		  +"<GUID>#!GUID!#</GUID> "
		  +"<transactionType xsi:type=\"v4:productTransactionTypeType\" xmlns:v4=\"http://xml.A.com/v4\"> "
		  +" <productTransactionType>getProductDetails</productTransactionType> "
		  +" </transactionType> "
		  +" #!SALES!# "
		  +"<request xsi:type=\"v4:productRequestType\" xmlns:v4=\"http://xml.A.com/v4\"> "	 
		  +"	<requestedDetails> " 
		  +" <detail level=\"basic\"/> "
		  +" <detail level=\"features\"/> "
		  +" <detail level=\"customizations\"/> "
		  +" <detail level=\"promotions\"/> "
		  +" <detail level=\"metadata\"/> "
		+" <detail level=\"descriptiveInfo\"/> "
		+" <detail level=\"marketingHighlights\"/> "
		+" </requestedDetails>"
		+" <productList>"
		+" <productId externalId=\"#!PRODUCT!#\" providerExternalId=\"#!PROVIDER!#\"/> "
		+" </productList> " 
		+" </request> "
		+" </productEnterpriseRequest> ";
	
	
	public final String GET_PRODUCTS_REQUEST_TEMPLATE =
		"<productEnterpriseRequest xmlns=\"http://xml.A.com/v4\" "
		 +"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"> "
		  +"<GUID>#!GUID!#</GUID> "
		  +"<transactionType xsi:type=\"v4:productTransactionTypeType\" xmlns:v4=\"http://xml.A.com/v4\"> "
		  +" <productTransactionType>getProducts</productTransactionType> "
		  +" </transactionType> "
		  +" #!SALES!# "
		  +"<request xsi:type=\"v4:productRequestType\" xmlns:v4=\"http://xml.A.com/v4\"> "	 
		  +"  #!ADDRESS!#"
		  +"<requestedDetails> " 
		  +" <detail level=\"basic\"/> "
		  +" <detail level=\"promotions\"/> "
		  +" <detail level=\"descriptiveInfo\"/> "
		  +" <detail level=\"marketingHighlights\"/> "
		  +" <detail level=\"metadata\"/> "
		  +" </requestedDetails>"
		  +" #!PROVIDERS!#" 
		  +" </request> "
		  +" </productEnterpriseRequest> "; 
	
	public final String GET_PRODUCTS_REQUEST_TEMPLATE_2 =
		"<productEnterpriseRequest xmlns=\"http://xml.A.com/v4\" "
		 +"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"> "
		  +"<GUID>#!GUID!#</GUID> "
		  +"<transactionType xsi:type=\"v4:productTransactionTypeType\" xmlns:v4=\"http://xml.A.com/v4\"> "
		  +" <productTransactionType>getProducts</productTransactionType> "
		  +" </transactionType> "
		  +" #!SALES!# "
		  +"<request xsi:type=\"v4:productRequestType\" xmlns:v4=\"http://xml.A.com/v4\"> "	 
		  +"  #!ADDRESS!#"
		  +"<serviceabilityTransactionType>#!SERVICEABILITYTRANSACTIONTYPE!#</serviceabilityTransactionType> "
		  +"<requestedDetails> " 
		  +" <detail level=\"basic\"/> "
		  +" <detail level=\"promotions\"/> "
		  +" <detail level=\"descriptiveInfo\"/> "
		  +" <detail level=\"marketingHighlights\"/> "
		  +" <detail level=\"metadata\"/> "
		  +" </requestedDetails>"
		  +" #!PROVIDERS!#" 
		  +" </request> "
		  +" </productEnterpriseRequest> "; 
	
	public final String GET_PRODUCTS_REQUEST_TEMPLATE_3 =
		"<productEnterpriseRequest xmlns=\"http://xml.A.com/v4\" "
		 +"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"> "
		  +"<GUID>#!GUID!#</GUID> "
		  +"<transactionType xsi:type=\"v4:productTransactionTypeType\" xmlns:v4=\"http://xml.A.com/v4\"> "
		  +" <productTransactionType>getProducts</productTransactionType> "
		  +" </transactionType> "
		  +" #!SALES!# "
		  +"<request xsi:type=\"v4:productRequestType\" xmlns:v4=\"http://xml.A.com/v4\"> "	 
		  +"  #!ADDRESS!#"
		  +"<serviceabilityTransactionType>#!SERVICEABILITYTRANSACTIONTYPE!#</serviceabilityTransactionType> "
		  +"<requestedDetails> " 
		  +" <detail level=\"basic\"/> "
		  +" <detail level=\"features\"/> "
		  +" <detail level=\"promotions\"/> "
		  +" <detail level=\"descriptiveInfo\"/> "
		  +" <detail level=\"marketingHighlights\"/> "
		  +" <detail level=\"metadata\"/> "
		  +" <detail level=\"descriptiveInfo\"/> "
		  +" </requestedDetails>"
		  +" #!PROVIDERS!#" 
		  +" </request> "
		  +" </productEnterpriseRequest> "; 
	 
	public final String PRODUCT_JMS_HEADER_START = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" 
	+ "<ac:acMessage xmlns:ac=\"http://xml.A.com/v4\" " 
	+ "xmlns:xsi=\"http://www.w3.org/2001/" +
			"XMLSchema-instance\" "
	+ "xsi:schemaLocation=\"http://xml.A.com/v4/acMessageWrapper.xsd \"> "
	+ "<ac:source>source</ac:source> "
	+ "<ac:msgType>request</ac:msgType> "
	+ "<ac:actionType>query</ac:actionType> " 
	+ "<ac:payloadType>ProductRequestDocument</ac:payloadType> "
	+ "<ac:payload> ";
	
	public final String GET_PRODUCT_CATALOG_DETAILS_REQUEST_TEMPLATE =
		"<productEnterpriseRequest xmlns=\"http://xml.A.com/v4\" "
		 +"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"> "
		  +"<GUID>#!GUID!#</GUID> "
		  +"<transactionType xsi:type=\"v4:productTransactionTypeType\" xmlns:v4=\"http://xml.A.com/v4\"> "
		  +" <productTransactionType>getProductCatalogDetails</productTransactionType> "
		  +" </transactionType> "
		  +" <salesContext> "
		  +" </salesContext> "
		  +"<request xsi:type=\"v4:productRequestType\" xmlns:v4=\"http://xml.A.com/v4\"> "	 
		  +"	<requestedDetails> " 
		  +" <detail level=\"basic\"/> "
		  +" <detail level=\"features\"/> "
		  +" <detail level=\"customizations\"/> "
		  +" <detail level=\"promotions\"/> "
		  +" <detail level=\"metadata\"/> "
		+" <detail level=\"descriptiveInfo\"/> "
		+" <detail level=\"marketingHighlights\"/> "
		+" </requestedDetails>"
		+" <productCatalogList>#!CATALOGLIST!#</productCatalogList> " 
		+" </request> "
		+" </productEnterpriseRequest> ";
		  
		  

	public final String PRODUCT_JMS_HEADER_END = "</ac:payload>"
			+ "</ac:acMessage>";

	public final String PRODUCT_DETAILS_JMS = PRODUCT_JMS_HEADER_START
			+ GET_PRODUCT_DETAILS_REQUEST_TEMPLATE + PRODUCT_JMS_HEADER_END;
	
	public final String PRODUCT_WITH_META_DETAILS_JMS = PRODUCT_JMS_HEADER_START
	+ GET_PRODUCT_DETAILS_WITH_META_REQUEST_TEMPLATE + PRODUCT_JMS_HEADER_END;
	
	
	public final String GET_PRODUCTS_JMS = PRODUCT_JMS_HEADER_START
			+ GET_PRODUCTS_REQUEST_TEMPLATE + PRODUCT_JMS_HEADER_END;
	
	public final String GET_PRODUCTS_JMS_2 = PRODUCT_JMS_HEADER_START
	+ GET_PRODUCTS_REQUEST_TEMPLATE_2 + PRODUCT_JMS_HEADER_END;
	
	public final String GET_PRODUCTS_JMS_3 = PRODUCT_JMS_HEADER_START
	+ GET_PRODUCTS_REQUEST_TEMPLATE_3 + PRODUCT_JMS_HEADER_END;
	
	public final String PRODUCT_SOAP = SOAP_ENVELOPE_TEMPLATE_START
			+ GET_PRODUCT_DETAILS_REQUEST_TEMPLATE + SOAP_ENVELOPE_TEMPLATE_END;
	
	public final String GET_PRODUCT_CATALOG_DETAILS_JMS = PRODUCT_JMS_HEADER_START
			+ GET_PRODUCT_CATALOG_DETAILS_REQUEST_TEMPLATE + PRODUCT_JMS_HEADER_END;

	public String getProductDetailsRequest(String provider, String product, String GUID,
			SalesContext salesContext) {
		//TODO create jaxb object for productEnterpriseRequest
		//TODO remove string constants of product request template
		String salesContextAsString = util.toString(salesContext,
				SalesContext.class, Boolean.FALSE);

		String productTemplate = StringUtils.replace(
				ProductTemplateConstant.INSTANCE.PRODUCT_DETAILS_JMS, "#!GUID!#", GUID);

		productTemplate = StringUtils.replace(productTemplate, "#!PRODUCT!#",
				product);
		productTemplate = StringUtils.replace(productTemplate, "#!PROVIDER!#",
				provider);
		productTemplate = StringUtils.replace(productTemplate, "#!SALES!#",
				salesContextAsString);
		
		return productTemplate;
	}
	
	public String getProductsRequest(ProviderList providers, AddressType address, String GUID,
			SalesContext salesContext) {
		//TODO create jaxb object for productEnterpriseRequest
		//TODO remove string constants of product request template
		String salesContextAsString = util.toString(salesContext,
				SalesContext.class, Boolean.FALSE);
		String productTemplate = StringUtils.replace(
				ProductTemplateConstant.INSTANCE.GET_PRODUCTS_JMS, "#!GUID!#", GUID);
		productTemplate = StringUtils.replace(productTemplate, "#!ADDRESS!#", toString(address));
		productTemplate = StringUtils.replace(productTemplate, "#!PROVIDERS!#", toString(providers));
		productTemplate = StringUtils.replace(productTemplate, "#!SALES!#",
				salesContextAsString);
		
		return productTemplate;
	}
	
	public String getProductsRequest(ProviderList providers, AddressType address, String GUID,
			SalesContext salesContext,String serviceabilityTransactionType) {
		//TODO create jaxb object for productEnterpriseRequest
		//TODO remove string constants of product request template
		String salesContextAsString = util.toString(salesContext,
				SalesContext.class, Boolean.FALSE);
		String productTemplate = StringUtils.replace(
				ProductTemplateConstant.INSTANCE.GET_PRODUCTS_JMS_2, "#!GUID!#", GUID);
		productTemplate = StringUtils.replace(productTemplate, "#!ADDRESS!#", toString(address));
		productTemplate = StringUtils.replace(productTemplate, "#!PROVIDERS!#", toString(providers));
		productTemplate = StringUtils.replace(productTemplate, "#!SALES!#",salesContextAsString);
		productTemplate = StringUtils.replace(productTemplate, "#!SERVICEABILITYTRANSACTIONTYPE!#",serviceabilityTransactionType);

		
		return productTemplate;
	}
	
	public String getProductsRequestWithFeatures(ProviderList providers, AddressType address, String GUID,
			SalesContext salesContext,String serviceabilityTransactionType) {
		//TODO create jaxb object for productEnterpriseRequest
		//TODO remove string constants of product request template
		String salesContextAsString = util.toString(salesContext,
				SalesContext.class, Boolean.FALSE);
		String productTemplate = StringUtils.replace(
				ProductTemplateConstant.INSTANCE.GET_PRODUCTS_JMS_3, "#!GUID!#", GUID);
		productTemplate = StringUtils.replace(productTemplate, "#!ADDRESS!#", toString(address));
		productTemplate = StringUtils.replace(productTemplate, "#!PROVIDERS!#", toString(providers));
		productTemplate = StringUtils.replace(productTemplate, "#!SALES!#",salesContextAsString);
		productTemplate = StringUtils.replace(productTemplate, "#!SERVICEABILITYTRANSACTIONTYPE!#",serviceabilityTransactionType);

		
		return productTemplate;
	}

	public String getSoapProductRequest(String provider, String product,
			SalesContext salesContext) {

		String salesContextAsString = util.toString(salesContext,
				SalesContext.class, Boolean.FALSE);

		String productTemplate = StringUtils.replace(
				ProductTemplateConstant.INSTANCE.PRODUCT_SOAP, "#!GUID!#", UUID
						.randomUUID().toString());

		productTemplate = StringUtils.replace(productTemplate, "#!PRODUCT!#",
				product);
		productTemplate = StringUtils.replace(productTemplate, "#!PROVIDER!#",
				provider);
		productTemplate = StringUtils.replace(productTemplate, "#!SALES!#",
				salesContextAsString);

		return productTemplate;

	}

	public final String TEMPLATE = SOAP_ENVELOPE_TEMPLATE_START
			+ GET_PRODUCT_DETAILS_REQUEST_TEMPLATE + SOAP_ENVELOPE_TEMPLATE_END;
	
	
	public static String toString(ProviderList provider) {
		StringWriter sw = new StringWriter();
		String temp = null;
		try {
			JAXBElement<ProviderList> jxb = new JAXBElement<ProviderList>(new QName("providerList"), 
					ProviderList.class, provider);
			Marshaller marshaller = providerListContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty("com.sun.xml.bind.xmlDeclaration",
					false);
			marshaller.marshal(jxb, sw);
            temp = sw.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch(Exception e) {
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
	
	public static String toString(AddressType address) {
		StringWriter sw = new StringWriter();
		String temp = null;
		try {
			JAXBElement<AddressType> jxb = new JAXBElement<AddressType>(new QName("correctedAddress"), 
					AddressType.class, address);
			Marshaller marshaller = addressContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty("com.sun.xml.bind.xmlDeclaration",
					false);
			marshaller.marshal(jxb, sw);
            temp = sw.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch(Exception e) {
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
	public String getProductDetailsWithMETARequest(String provider, String product, String GUID,
			SalesContext salesContext) {
		//TODO create jaxb object for productEnterpriseRequest
		//TODO remove string constants of product request template
		String salesContextAsString = util.toString(salesContext,
				SalesContext.class, Boolean.FALSE);

		String productTemplate = StringUtils.replace(
				ProductTemplateConstant.INSTANCE.PRODUCT_WITH_META_DETAILS_JMS, "#!GUID!#", GUID);

		productTemplate = StringUtils.replace(productTemplate, "#!PRODUCT!#",
				product);
		productTemplate = StringUtils.replace(productTemplate, "#!PROVIDER!#",
				provider);
		productTemplate = StringUtils.replace(productTemplate, "#!SALES!#",
				salesContextAsString);
		
		return productTemplate;
	}

	public String getProductCatalogDetailsRequest(String[] productCatalogIds, String GUID) {

		String productTemplate = StringUtils.replace(
				ProductTemplateConstant.INSTANCE.GET_PRODUCT_CATALOG_DETAILS_JMS, "#!GUID!#", GUID);

		String catalogString = "";
		for (String catalogId : productCatalogIds) {
			catalogString = "<catalogId>"+catalogId+"</catalogId>"+catalogString;
		}
		
		productTemplate = StringUtils.replace(productTemplate, "#!CATALOGLIST!#",
				catalogString);
		
		return productTemplate;
	}	
}
