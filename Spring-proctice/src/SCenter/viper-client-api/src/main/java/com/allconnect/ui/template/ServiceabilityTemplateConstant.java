package com.A.ui.template;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringUtils;

import com.A.V.domain.SalesContext;
import com.A.xml.di.v4.SalesContextEntityType;
import com.A.xml.se.v4.AddressType;
import com.A.xml.se.v4.NameValuePairType;
import com.A.xml.se.v4.ProviderCriteriaType2;
import com.A.xml.se.v4.SalesContextType;
import com.A.xml.se.v4.ServiceabilityEnterpriseRequest;
import com.A.xml.se.v4.ServiceabilityEnterpriseResponse;
import com.A.xml.se.v4.ServiceabilityRequest2;
import com.A.xml.se.v4.ServiceabilityRequest2.RtimRequestResponseCriteria;

public enum ServiceabilityTemplateConstant {
	
	INSTANCE;

	private static JAXBContext jxbRequestContext = null;
	private static JAXBContext jxbResponseContext = null;
	
	static {
		
		try {
			jxbRequestContext = JAXBContext.newInstance(ServiceabilityEnterpriseRequest.class, 
					ServiceabilityRequest2.class);
			jxbResponseContext = JAXBContext.newInstance(ServiceabilityEnterpriseResponse.class, 
					ServiceabilityRequest2.class);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public final String SOAP_ENVELOPE_TEMPLATE_START = "<soapenv:envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:header/><soapenv:body>";

	public final String SOAP_ENVELOPE_TEMPLATE_END = "</soapenv:body></soapenv:envelope>";

	public final String SE_REQUEST_TEMPLATE =
		"<serviceabilityEnterpriseRequest xmlns=\"http://xml.A.com/v4\" "
		  +"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"> "
		  +" <GUID>#!GUID!#</GUID> "
		  +" <transactionType/> "
		  +" <request xsi:type=\"v4:serviceabilityRequestType2\" xmlns:v4=\"http://xml.A.com/v4\"> "	 
		  +" <v4:inputAddressString>" 
		  +"#!NADDR!#" 
		  +"</v4:inputAddressString>" 
		  +" <v4:referrerId>0</v4:referrerId>"
		  +" <v4:serviceabilityTransactionType>normal</v4:serviceabilityTransactionType>"
		  +" <v4:rtimRequestResponseCriteria>"
		  +" </v4:rtimRequestResponseCriteria>"
		  +" <v4:channelServiceability>1</v4:channelServiceability>"
		  +" <v4:agentId>0</v4:agentId>"
		  +" <v4:serviceTypeId>0</v4:serviceTypeId>"
		  +" </request> "
		  +" </serviceabilityEnterpriseRequest> "; 
		

	public final String SE_JMS_HEADER_START = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" 
		+ "<ac:acMessage xmlns:ac=\"http://xml.A.com/v4\" " 
		+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
		+ "xsi:schemaLocation=\"http://xml.A.com/v4/acMessageWrapper.xsd \"> "
		+ "<ac:source>source</ac:source> "
		+ "<ac:msgType>request</ac:msgType> "
		+ "<ac:actionType>query</ac:actionType> " 
		+ "<ac:payloadType>ServiceabilityRequestDocument</ac:payloadType> "
		+ "<ac:payload> ";
		  
		  

	public final String SE_JMS_HEADER_END = "</ac:payload>"
			+ "</ac:acMessage>";

	public final String SE_JMS = SE_JMS_HEADER_START
			+ SE_REQUEST_TEMPLATE + SE_JMS_HEADER_END;
	public final String SE_SOAP = SOAP_ENVELOPE_TEMPLATE_START
			+ SE_REQUEST_TEMPLATE + SOAP_ENVELOPE_TEMPLATE_END;

	public String getServiceabilityRequest(String nAddress, String GUID) {
		//TODO create jaxb object for productEnterpriseRequest
		//TODO remove string constants of product request template
		String seTemplate = StringUtils.replace(
				ServiceabilityTemplateConstant.INSTANCE.SE_JMS, "#!GUID!#", UUID
				.randomUUID().toString());

		seTemplate = StringUtils.replace(seTemplate, "#!NADDR!#",
				nAddress);
		
		return seTemplate;
	}

	public String getSoapServiceabilityRequest(String nAddress, String GUID) {
		String seTemplate = StringUtils.replace(
		ProductTemplateConstant.INSTANCE.PRODUCT_SOAP, "#!GUID!#", UUID
				.randomUUID().toString());
		seTemplate = StringUtils.replace(seTemplate, "#!NADDR!#",
				nAddress);
		return seTemplate;
	}
	
	public String getServiceabilityRequest(String inputAddress, 
			SalesContext salesContext, ProviderCriteriaType2 providerCriteria,String se2TransactionType) {
		ServiceabilityEnterpriseRequest request = new ServiceabilityEnterpriseRequest();
		request.setGUID(UUID.randomUUID().toString());
		request.setSalesContext(getSalesContextType(salesContext));
		ServiceabilityRequest2 seRequest = new ServiceabilityRequest2();
		//<v4:speedMode>true</v4:speedMode>
		seRequest.setInputAddressString(inputAddress);
		RtimRequestResponseCriteria criteria = new RtimRequestResponseCriteria();
		criteria.setProviderCriteria(providerCriteria);
		seRequest.setSpeedMode(true);
		seRequest.setServiceabilityTransactionType(se2TransactionType);
		seRequest.setRtimRequestResponseCriteria(criteria);
		request.setRequest(seRequest);
		return (SE_JMS_HEADER_START + toXmlString(request) + SE_JMS_HEADER_END);
	}
	
	public String getServiceabilityRequest(String inputAddress, 
			SalesContext salesContext, ProviderCriteriaType2 providerCriteria,String se2TransactionType, boolean speedMode) {
		ServiceabilityEnterpriseRequest request = new ServiceabilityEnterpriseRequest();
		request.setGUID(UUID.randomUUID().toString());
		request.setSalesContext(getSalesContextType(salesContext));
		ServiceabilityRequest2 seRequest = new ServiceabilityRequest2();
		seRequest.setInputAddressString(inputAddress);
		RtimRequestResponseCriteria criteria = new RtimRequestResponseCriteria();
		criteria.setProviderCriteria(providerCriteria);
		seRequest.setSpeedMode(speedMode);
		seRequest.setServiceabilityTransactionType(se2TransactionType);
		seRequest.setRtimRequestResponseCriteria(criteria);
		request.setRequest(seRequest);
		return (SE_JMS_HEADER_START + toXmlString(request) + SE_JMS_HEADER_END);
	}
	
	
	
	/**This method use SE2 call with corrected address 
	 * @param addressType
	 * @param inputAddress
	 * @param salesContext
	 * @param providerCriteria
	 * @param se2TransactionType
	 * @param speedMode
	 * @return
	 */
	public String getCorrectedAddressServiceabilityRequest(AddressType addressType, String inputAddress,
			SalesContext salesContext, ProviderCriteriaType2 providerCriteria,String se2TransactionType, boolean speedMode) {
		ServiceabilityEnterpriseRequest request = new ServiceabilityEnterpriseRequest();
		request.setGUID(UUID.randomUUID().toString());
		request.setSalesContext(getSalesContextType(salesContext));
		ServiceabilityRequest2 seRequest = new ServiceabilityRequest2();
		seRequest.setInputAddressString(inputAddress);
		seRequest.setCorrectedAddress(addressType);
		RtimRequestResponseCriteria criteria = new RtimRequestResponseCriteria();
		criteria.setProviderCriteria(providerCriteria);
		seRequest.setSpeedMode(speedMode);
		seRequest.setServiceabilityTransactionType(se2TransactionType);
		seRequest.setRtimRequestResponseCriteria(criteria);
		request.setRequest(seRequest);
		return (SE_JMS_HEADER_START + toXmlString(request) + SE_JMS_HEADER_END);
	}
	
	public String getServiceabilityRequest(String inputAddress, 
			SalesContext salesContext, ProviderCriteriaType2 providerCriteria) {
		ServiceabilityEnterpriseRequest request = new ServiceabilityEnterpriseRequest();
		request.setGUID(UUID.randomUUID().toString());
		request.setSalesContext(getSalesContextType(salesContext));
		ServiceabilityRequest2 seRequest = new ServiceabilityRequest2();
		seRequest.setInputAddressString(inputAddress);
		RtimRequestResponseCriteria criteria = new RtimRequestResponseCriteria();
		criteria.setProviderCriteria(providerCriteria);
		seRequest.setRtimRequestResponseCriteria(criteria);
		seRequest.setServiceabilityTransactionType("normal");
		request.setRequest(seRequest);
		return (SE_JMS_HEADER_START + toXmlString(request) + SE_JMS_HEADER_END);
	}
	
	public final String TEMPLATE = SOAP_ENVELOPE_TEMPLATE_START
			+ SE_REQUEST_TEMPLATE + SOAP_ENVELOPE_TEMPLATE_END;
	
	public static String toXmlString(ServiceabilityEnterpriseRequest doc) {
		StringWriter sw = new StringWriter();
		String temp = null;
		try {
			
			Marshaller marshaller = jxbRequestContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty("com.sun.xml.bind.xmlDeclaration",
					false);
			marshaller.marshal(doc, sw);
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
	
	public ServiceabilityEnterpriseResponse toObject(String doc) {
		StringReader sr = null;
		ServiceabilityEnterpriseResponse response = null;
		try {
			Unmarshaller unmarshaller = jxbResponseContext.createUnmarshaller();
			sr = new StringReader(doc);
			response = (ServiceabilityEnterpriseResponse)unmarshaller.unmarshal(
					new StreamSource(sr));
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sr.close();
		}
		return response;
	}
	
	public SalesContextType getSalesContextType(SalesContext salesContext) {
		SalesContextType salesContextType = new SalesContextType();
		if(salesContext != null) {
			salesContextType.setOrderSource(salesContext.getOrderSource());
			for(SalesContextEntityType entityType : salesContext.getEntity()) {
				com.A.xml.se.v4.SalesContextEntityType entity = 
					new com.A.xml.se.v4.SalesContextEntityType();
				entity.setName(entityType.getName());
				for(com.A.xml.di.v4.NameValuePairType pair : entityType.getAttribute()) {
					NameValuePairType pairType = new NameValuePairType();
					pairType.setName(pair.getName());
					pairType.setValue(pair.getValue());
					entity.getAttributes().add(pairType);
				}
				salesContextType.getEntities().add(entity);
			}
		}
		return salesContextType;
	}
	
	
}
