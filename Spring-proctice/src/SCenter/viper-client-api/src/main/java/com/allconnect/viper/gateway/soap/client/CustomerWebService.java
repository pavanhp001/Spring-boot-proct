package com.A.V.gateway.soap.client;

 
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

import com.A.V.gateway.util.PropertyUtil;
import com.A.xml.cm.v4.CustomerManagementWS;


@WebServiceClient(name = "CustomerManagementWebServiceClient", 
                  targetNamespace = "http://xml.A.com/v4") 
public class CustomerWebService extends Service {

    public final static URL WSDL_LOCATION;
    public final static String SCHEMA_VERSION = "http://xml.A.com/v4";
    private final static String CUSTOMER_WSDL_NAME = "customer.wsdl";

    public final static QName SERVICE = new QName(SCHEMA_VERSION, "CustomerManagementWebServiceClient");
    public final static QName CustomerManagementWSPort = new QName(SCHEMA_VERSION, "customerManagementWSPort");
    Properties orderWSDLProperties = null;
    static {
    	    Properties orderWSDLProperties = PropertyUtil.load("wsdl");
    	
    	String orderWSDL = "";
		URL url = null;
        try {
        	orderWSDL = orderWSDLProperties.getProperty(CUSTOMER_WSDL_NAME);
        	
        	url = new URL(orderWSDL);
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(CustomerWebService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}",  orderWSDL);
        }
        WSDL_LOCATION = url;
    }

    public CustomerWebService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public CustomerWebService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CustomerWebService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns CustomerManagementWS
     */
    @WebEndpoint(name = "customerManagementWSPort")
    public CustomerManagementWS getCustomerManagementWSPort() {
        return super.getPort(CustomerManagementWSPort, CustomerManagementWS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CustomerManagementWS
     */
    @WebEndpoint(name = "customerManagementWSPort")
    public CustomerManagementWS getCustomerManagementWSPort(WebServiceFeature... features) {
        return super.getPort(CustomerManagementWSPort, CustomerManagementWS.class, features);
    }

}
