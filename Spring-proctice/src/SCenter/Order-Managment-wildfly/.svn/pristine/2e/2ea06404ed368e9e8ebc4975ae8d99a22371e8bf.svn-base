package com.AL.ws.impl;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import org.jboss.ws.api.annotation.WebContext;
import com.AL.ws.OrderManagementWSRemote;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

/**
 * 
 * @author ethomas
         * 
 */

@Stateless
@WebService(name = "orderManagementWS", serviceName = "OrderManagement", targetNamespace = "http://xml.AL.com/v4")
@WebContext( contextRoot = "/ome" , urlPattern="/OrderManagementWS" )
public class OrderManagementWSImpl implements
		OrderManagementWSRemote {
	
 private static Logger logger = Logger.getLogger(OrderManagementWSImpl.class);

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public String processRequest(final String inputXml) {
		logger.debug("web service process request");
		return AbstractOmeHandler.baseProcessRequest(inputXml);
		 
	}

    public OrderManagementRequestResponseDocument getOrderManagementRequestResponseDocument( String inputXml )
    {
        return AbstractOmeHandler.getOrderManagementRequestResponseDocument( inputXml );
    }

}
