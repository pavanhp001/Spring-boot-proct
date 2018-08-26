package com.AL.ie.ws.impl;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import org.jboss.ws.api.annotation.WebContext;
import com.AL.ie.ws.IeWSRemote;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

/**
 * 
 * @author ethomas
         * 
 */

@Stateless
@WebService(name = "integrationEngineWS", serviceName = "IntegrationEngine", targetNamespace = "http://xml.AL.com/v4")
@WebContext( contextRoot = "/ie" , urlPattern="/IntegrationEngineWS" )
public class IeWSImpl implements
IeWSRemote {
	
 private static Logger logger = Logger.getLogger(IeWSImpl.class);

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public String processRequest(final String inputXml) {
		logger.debug("web service process request");
		return IeAbstractHandler.baseProcessRequest(inputXml);
		 
	}

    public OrderManagementRequestResponseDocument getOrderManagementRequestResponseDocument( String inputXml )
    {
        return IeAbstractHandler.getOrderManagementRequestResponseDocument( inputXml );
    }

}
