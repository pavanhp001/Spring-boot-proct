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
import com.A.xml.v4.OrderManagementWS;

@WebServiceClient(name = "OrderManagementWebServiceClient", targetNamespace = "http://xml.A.com/v4")
public class OrderWebService extends Service {

	public final static URL WSDL_LOCATION;
	public final static String SCHEMA_VERSION = "http://xml.A.com/v4";
	private final static String OME_WSDL_NAME = "ome.wsdl";

	public final static QName SERVICE = new QName(SCHEMA_VERSION,
			"OrderManagementWebServiceClient");
	public final static QName OrderManagementWSPort = new QName(SCHEMA_VERSION,
			"orderManagementWSPort");
	Properties orderWSDLProperties = null;
    static {
    	    Properties orderWSDLProperties = PropertyUtil.load("wsdl");
		String orderWSDL = "";
		URL url = null;
		try {
			orderWSDL = orderWSDLProperties.getProperty(OME_WSDL_NAME);

			url = new URL(orderWSDL);
		} catch (MalformedURLException e) {
			java.util.logging.Logger.getLogger(OrderWebService.class.getName())
					.log(java.util.logging.Level.INFO,
							"Can not initialize the default wsdl from {0}",
							orderWSDL);
		}
		WSDL_LOCATION = url;
	}

	public OrderWebService(URL wsdlLocation) {
		super(wsdlLocation, SERVICE);
	}

	public OrderWebService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	public OrderWebService() {
		super(WSDL_LOCATION, SERVICE);
	}

	/**
	 * 
	 * @return returns OrderManagementWS
	 */
	@WebEndpoint(name = "orderManagementWSPort")
	public OrderManagementWS getOrderManagementWSPort() {
		return super.getPort(OrderManagementWSPort, OrderManagementWS.class);
	}

	/**
	 * 
	 * @param features
	 *            A list of {@link javax.xml.ws.WebServiceFeature} to configure
	 *            on the proxy. Supported features not in the
	 *            <code>features</code> parameter will have their default
	 *            values.
	 * @return returns OrderManagementWS
	 */
	@WebEndpoint(name = "orderManagementWSPort")
	public OrderManagementWS getOrderManagementWSPort(
			WebServiceFeature... features) {
		return super.getPort(OrderManagementWSPort, OrderManagementWS.class,
				features);
	}

}
