package com.A.V.gateway;

import java.util.Map;

public interface HttpClient {

	  String execute(String locationUri, String request, Map<String, String> headers,
			String requestMethod ) ;

	/**
	 * Shut down the client and release the resources associated with the
	 * HttpClient
	 */
	public void shutdown();
}
