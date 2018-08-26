/**
 * 
 */
package com.A.ui.service.V;

import java.util.List;

import org.apache.log4j.Logger;

import com.A.ui.service.V.impl.DetailsCacheService;
import com.A.ui.template.DetailsTemplateConstant;
import com.A.ui.transport.TransportConfig;
import com.A.V.gateway.DetailManagementClient;
import com.A.V.gateway.jms.DetailManagementClientJMS;
import com.A.V.gateway.util.JaxbUtil;
import com.A.xml.dtl.v4.DetailElementType;
import com.A.xml.dtl.v4.DetailObjectType;
import com.A.xml.dtl.v4.DetailsRequestResponse;
import com.A.xml.dtl.v4.ObjectFactory;
import com.A.xml.dtl.v4.OrderSourceRequestElementType;
import com.A.xml.dtl.v4.DetailElementType.DetailType;
import com.A.xml.dtl.v4.DetailsRequestResponse.Request;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;




/**
 * @author kamesh
 *
 */
public enum DetailService {
	
	INSTANCE;
	
	private static final Logger logger = Logger.getLogger(DetailService.class);
	
	public DetailsRequestResponse getAllOrderSources(){
		ObjectFactory oFactory = new ObjectFactory();
		DetailsRequestResponse drr = oFactory.createDetailsRequestResponse();
		drr.setTransactionType(VOperation.getAllOrderSources.toString());
		drr.setCorrelationId("12345");
		Request req  = oFactory.createDetailsRequestResponseRequest();
		OrderSourceRequestElementType or = oFactory.createOrderSourceRequestElementType();
		//TODO: uncomment the below code when impl is added
		req.getOrderSourceRequestElement().add(or);
		drr.setRequest(req);
		DetailsRequestResponse response = TransportConfig.INSTANCE.getDetailClient().send(drr);
		return response;
	}
	
    public DetailsRequestResponse getAllOrderSources(String correlationId) {

    	String request = DetailsTemplateConstant.INSTANCE.getDetailsRequest(correlationId);
    	DetailManagementClient<String> client = new DetailManagementClientJMS();
    	DetailsRequestResponse response = client.send(request);
    	
    	return response;
	}	
    
    public DetailsRequestResponse getAllOrderSources(String correlationId, long detailsCacheTimeout) {
        
        DetailsRequestResponse response = DetailsCacheService.INSTANCE.get(correlationId); 
        logger.info("Details Service --> after getting details from the cache  "+response);
        if (response != null) {
         logger.info("Details Service --> details response from cache is not empty");
      return response;
     }
        logger.info("Details Service --> details from cache is null");
        String request = DetailsTemplateConstant.INSTANCE.getDetailsRequest(correlationId);
        DetailManagementClient<String> client = new DetailManagementClientJMS();
        logger.info("Details Service --> before DetailService Call");
        response = client.send(request);
        if(response == null){
        	//response = ownDetailService();
        }
        if (response != null) {
         logger.info("Details Service --> setting details service response in cache");
         DetailsCacheService.INSTANCE.store(response, correlationId, Long.valueOf(detailsCacheTimeout));
        }
        
        return response;
    }
    
    

	//To fetch all details from Detail Service
    public DetailsRequestResponse getAllDetails(List<String> businessPartyId, String correlationId){
    	String request = DetailsTemplateConstant.INSTANCE.getDetailsRequestByBusinessId(businessPartyId, correlationId);
    	DetailManagementClient<String> client = new DetailManagementClientJMS();
    	DetailsRequestResponse response = client.send(request);
    	return response;
    }
    
    private DetailsRequestResponse ownDetailService() {
    	DetailsRequestResponse detailsResponse = null;
    	try{
    	
    		JaxbUtil<DetailsRequestResponse> util = new JaxbUtil<DetailsRequestResponse>();	
    		logger.info("In OwnDetailService : ");

    		File file = new File("D:\\config\\DetailsRequestResponse.xml");
    		BufferedReader br = new BufferedReader(new FileReader(file));
    		String line;
    		StringBuilder sb = new StringBuilder();
    		

    		while((line=br.readLine())!= null){
    		    sb.append(line.trim());
    		}
    		
    		String xml2String = sb.toString();
            
    		br.close();
    		
    		detailsResponse = util.toObject(
    				xml2String, DetailsRequestResponse.class);
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return detailsResponse;
	}

}
