package com.A.xml.se.v4;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.6.0
 * 2018-03-21T19:20:44.057+05:30
 * Generated source version: 2.6.0
 * 
 */
@WebServiceClient(name = "ServiceabilityWebServiceClient", 
                  wsdlLocation = "file:/D:/E/Digital-05-01/V-client-api/src/wsdl/Serviceability2V4.wsdl",
                  targetNamespace = "http://xml.A.com/v4") 
public class ServiceabilityWebServiceClient extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://xml.A.com/v4", "ServiceabilityWebServiceClient");
    public final static QName WebServiceProxyServicePort = new QName("http://xml.A.com/v4", "WebServiceProxyServicePort");
    static {
        URL url = null;
        try {
            url = new URL("file:/D:/E/Digital-05-01/V-client-api/src/wsdl/Serviceability2V4.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(ServiceabilityWebServiceClient.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:/D:/E/Digital-05-01/V-client-api/src/wsdl/Serviceability2V4.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public ServiceabilityWebServiceClient(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ServiceabilityWebServiceClient(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ServiceabilityWebServiceClient() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public ServiceabilityWebServiceClient(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public ServiceabilityWebServiceClient(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public ServiceabilityWebServiceClient(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns WebServiceProxyServicePortType
     */
    @WebEndpoint(name = "WebServiceProxyServicePort")
    public WebServiceProxyServicePortType getWebServiceProxyServicePort() {
        return super.getPort(WebServiceProxyServicePort, WebServiceProxyServicePortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WebServiceProxyServicePortType
     */
    @WebEndpoint(name = "WebServiceProxyServicePort")
    public WebServiceProxyServicePortType getWebServiceProxyServicePort(WebServiceFeature... features) {
        return super.getPort(WebServiceProxyServicePort, WebServiceProxyServicePortType.class, features);
    }

}
