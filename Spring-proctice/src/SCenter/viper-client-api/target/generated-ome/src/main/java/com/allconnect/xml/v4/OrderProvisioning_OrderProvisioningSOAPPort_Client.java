
package com.A.xml.v4;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.6.0
 * 2018-03-21T19:20:50.675+05:30
 * Generated source version: 2.6.0
 * 
 */
public final class OrderProvisioning_OrderProvisioningSOAPPort_Client {

    private static final QName SERVICE_NAME = new QName("http://xml.A.com/v4/OrderProvisioning/", "OrderProvisioningWebServiceClient");

    private OrderProvisioning_OrderProvisioningSOAPPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = OrderProvisioningWebServiceClient.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        OrderProvisioningWebServiceClient ss = new OrderProvisioningWebServiceClient(wsdlURL, SERVICE_NAME);
        OrderProvisioning port = ss.getOrderProvisioningSOAPPort();  
        
        {
        System.out.println("Invoking processRequest...");
        com.A.xml.v4.OpRequest _processRequest_parameters = null;
        com.A.xml.v4.OpResponse _processRequest__return = port.processRequest(_processRequest_parameters);
        System.out.println("processRequest.result=" + _processRequest__return);


        }

        System.exit(0);
    }

}
