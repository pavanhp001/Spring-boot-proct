package com.A.ui.template;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringUtils;

import com.A.xml.dtl.v4.DetailsRequestResponse;
/**
 * @author spatil
 *
 */
public enum DetailsTemplateConstant {
	
	INSTANCE;

	private static JAXBContext jxbRequestContext = null;
	private static JAXBContext jxbResponseContext = null;
	
	static {
		
		try {
			jxbRequestContext = JAXBContext.newInstance(DetailsRequestResponse.class, 
					DetailsRequestResponse.class);
			jxbResponseContext = JAXBContext.newInstance(DetailsRequestResponse.class, 
					DetailsRequestResponse.class);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public final String SOAP_ENVELOPE_TEMPLATE_START = "<soapenv:envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:header/><soapenv:body>";

	public final String SOAP_ENVELOPE_TEMPLATE_END = "</soapenv:body></soapenv:envelope>";

	public final String DTL_REQUEST_TEMPLATE =
		"<v4:detailsRequestResponse xsi:schemaLocation=\"http://xml.A.com/v4 detailManagement.xsd\" "
		+"xmlns:ac=\"http://xml.A.com/v4\" "
		  +"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"> "
		  +" <v4:correlationId>#!CORRELATIONID!#</v4:correlationId>"
		  +"<v4:transactionType>getAllOrderSources</v4:transactionType>"
		  +" <v4:request> "
		  +" <v4:orderSourceRequestElement/>"
		  +" </v4:request> "
		  +" </v4:detailsRequestResponse> "; 

	public final String DTL_JMS_HEADER_START = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" 
		+ "<v4:acMessage xmlns:v4=\"http://xml.A.com/v4\" " 
		+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
		+ "xsi:schemaLocation=\"http://xml.A.com/v4/acMessageWrapper.xsd \"> "
		+ "<v4:source>source</v4:source> "
		+ "<v4:msgType>request</v4:msgType> "
		+ "<v4:actionType>query</v4:actionType> " 
		+ "<v4:payloadType>DetailsRequestResponseDocument</v4:payloadType> "
		+ "<v4:payload> ";

	public final String DTL_JMS_HEADER_END = "</v4:payload>"
			+ "</v4:acMessage>";

	public final String DTL_JMS = DTL_JMS_HEADER_START
			+ DTL_REQUEST_TEMPLATE + DTL_JMS_HEADER_END;
	public final String DTL_SOAP = SOAP_ENVELOPE_TEMPLATE_START
			+ DTL_REQUEST_TEMPLATE + SOAP_ENVELOPE_TEMPLATE_END;

	public String getDetailsRequest(String correlationId) {
		//TODO create jaxb object for productEnterpriseRequest
		//TODO remove string constants of product request template
		String dtlTemplate = StringUtils.replace(
				DetailsTemplateConstant.INSTANCE.DTL_JMS, "#!CORRELATIONID!#", correlationId);
		return dtlTemplate;
	}
	
	public final String ALL_DTLS_TRAN_REQUEST_TEMPLATE_FIRST = 
	  "<v4:detailsRequestResponse xsi:schemaLocation=\"http://xml.A.com/v4 detailManagement.xsd\" "
	  +"xmlns:ac=\"http://xml.A.com/v4\" "
	  +"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"> "
	  +" <v4:correlationId>#!CORRELATIONID!#</v4:correlationId>"
	  +"<v4:transactionType>getAllDetails</v4:transactionType>"
	  +" <v4:request> ";
	
	 public final String ALL_DTLS_TRAN_REQUEST_TEMPLATE_MIDLE = 
	  " <v4:detailRequestElement>"
	  +" <v4:detailType externalId=#!EXTERNALID!#>businessParty</v4:detailType>"
	  +" </v4:detailRequestElement>";
	 
	 public final String ALL_DTLS_TRAN_REQUEST_TEMPLATE_LAST = " </v4:request> "+" </v4:detailsRequestResponse> ";

	 public final String DTL_JMS_ALL_DTL_TRAN = DTL_JMS_HEADER_START + ALL_DTLS_TRAN_REQUEST_TEMPLATE_FIRST;

	 public String getDetailsRequestByBusinessId(List<String> businessPartyIds, String correlationId) {
	  String dtlTemplate = StringUtils.replace(
	    DetailsTemplateConstant.INSTANCE.DTL_JMS_ALL_DTL_TRAN , "#!CORRELATIONID!#", correlationId);
	  for(String businessPartyId : businessPartyIds){
	   dtlTemplate = StringUtils.replace(dtlTemplate + ALL_DTLS_TRAN_REQUEST_TEMPLATE_MIDLE, "#!EXTERNALID!#", '"'+businessPartyId+'"');
	  }  
	  return dtlTemplate + ALL_DTLS_TRAN_REQUEST_TEMPLATE_LAST + DTL_JMS_HEADER_END;
	 }

	
	public final String TEMPLATE = SOAP_ENVELOPE_TEMPLATE_START
			+ DTL_REQUEST_TEMPLATE + SOAP_ENVELOPE_TEMPLATE_END;
	
	public static String toXmlString(DetailsRequestResponse doc) {
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
	
	public DetailsRequestResponse toObject(String doc) {
		StringReader sr = null;
		DetailsRequestResponse response = null;
		try {
			Unmarshaller unmarshaller = jxbResponseContext.createUnmarshaller();
			sr = new StringReader(doc);
			response = (DetailsRequestResponse)unmarshaller.unmarshal(
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
	
}
