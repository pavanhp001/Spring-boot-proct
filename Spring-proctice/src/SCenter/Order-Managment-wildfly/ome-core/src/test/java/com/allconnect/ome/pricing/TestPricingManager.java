package com.AL.ome.pricing;


public class TestPricingManager {

	private static final String OME_SERVICE_URL = "http://sgdv3vm3:8080/pricing/PricingWS";
	/*private static JaxWsProxyFactoryBean factory;
	private static PricingWS client;*/

	/**
	 * A method to send request to pricing via apache cxf client and return response. This class used to test pricing via OME in junit testcases.
	 * @param pricingRequest
	 * @return
	 */
	/*public static String sendToPricing(String pricingRequest){
		factory = new JaxWsProxyFactoryBean();
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		factory.setServiceClass(PricingWS.class);
		factory.setAddress(OME_SERVICE_URL);
		client = (PricingWS) factory.create();
		String response = client.processRequest(pricingRequest);
		return response;
	}*/
}