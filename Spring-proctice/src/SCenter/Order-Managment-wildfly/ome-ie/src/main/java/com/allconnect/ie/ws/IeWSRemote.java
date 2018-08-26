package com.AL.ie.ws;

import javax.ejb.Remote;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

/**
 * 
 * @author ethomas
 * 
 */
@Remote
public interface IeWSRemote
{

    /**
     * Method that allows web service to make requests to a ServiceabiltyManager.
     * 
     * @param inputXml
     *            AC Message Document XML
     * @return String of AC Message Document XML.
     */
    String processRequest( final String inputXml );

    /**
     * @param inputXml
     *            xml to be transformed
     * @return Order Management Request Response Document
     */
    OrderManagementRequestResponseDocument getOrderManagementRequestResponseDocument( final String inputXml );
}
