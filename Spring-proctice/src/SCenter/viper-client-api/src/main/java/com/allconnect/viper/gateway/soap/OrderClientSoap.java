package com.A.V.gateway.soap;

import javax.xml.ws.Holder;
import org.apache.log4j.Logger;
import com.A.V.gateway.ClientService;
import com.A.V.gateway.OrderClient;
import com.A.V.gateway.soap.client.OrderWebService;
import com.A.V.gateway.util.JaxbUtil;
import com.A.xml.v4.OrderManagementRequestResponse;
import com.A.xml.v4.OrderManagementWS;

public class OrderClientSoap extends
		BaseClientSoap<OrderManagementRequestResponse> implements ClientService<OrderManagementRequestResponse>,OrderClient {

	public JaxbUtil<OrderManagementRequestResponse> jaxbUtil = new JaxbUtil<OrderManagementRequestResponse>();
	private static final Logger logger = Logger.getLogger(OrderClientSoap.class);

	public OrderManagementRequestResponse send(
			OrderManagementRequestResponse order) {

		try {
		OrderWebService srv = new OrderWebService();
		OrderManagementWS port = srv.getOrderManagementWSPort();

		Holder<OrderManagementRequestResponse> holder = new Holder<OrderManagementRequestResponse>();
		holder.value = order;

		String response = port.processRequest(jaxbUtil.toString(holder.value,
				OrderManagementRequestResponse.class));

		return jaxbUtil.toObject(extract(response),
				OrderManagementRequestResponse.class);
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		
		return null;
	}

	public String extract(String orderRR) {

		int indexStart = orderRR.indexOf("<ac:orderManagementRequestResponse>");
		int indexEnd = orderRR.indexOf("</ac:payload>");

		if ((indexStart != -1) && (indexEnd != -1)) {

			return orderRR.substring(indexStart, indexEnd);
		}

		throw new IllegalArgumentException("invalid AC Response");

	}

	
	/**
     * This method will send OME request asynchronously. So use this method only if you dont need res back from
     * OME. This method can be used in Salescenter to send aynch request like at the end of the call to update
     * session status completed flag. Or adding home depot lineitem(Savers offers) etc.
     *
     *
     */
     @Override
     public void sendAsync(OrderManagementRequestResponse req) {

          


     }

}
