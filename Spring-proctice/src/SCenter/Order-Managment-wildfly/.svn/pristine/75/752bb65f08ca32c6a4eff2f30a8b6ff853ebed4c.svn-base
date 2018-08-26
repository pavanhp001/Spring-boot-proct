package com.AL.products.misc;

import org.apache.xmlbeans.XmlException;
import com.AL.xml.v4.AcMessageDocument;
import com.AL.xml.v4.AcMessageType;
import com.AL.xml.v4.ProductEnterpriseRequestDocument;
import com.AL.xml.v4.ProductEnterpriseResponseDocument;

public class DummyProductsService
{
	private static String prodResponseXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<v4:acMessage xmlns:v4=\"http://xml.AL.com/v4\">\n    <v4:msgType>response</v4:msgType>\n    <v4:actionType>query</v4:actionType>\n    <v4:payloadType>ProductResponseDocument</v4:payloadType>\n    <v4:payload>\n        <v4:productEnterpriseResponse>\n            <v4:GUID>12345</v4:GUID>\n            <v4:status>\n                <v4:processingMessages/>\n            </v4:status>\n            <v4:response xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"v4:productResponseType\">\n                <v4:catalogedProductList>\n                    <v4:productCatalogEntry providerExternalId=\"2314635\" externalId=\"DIRECTSTAR-DTV-1-WEB\" instanceId=\"0\" catalogId=\"131\">\n                        <v4:status success=\"true\"/>\n                    </v4:productCatalogEntry>\n                </v4:catalogedProductList>\n            </v4:response>\n        </v4:productEnterpriseResponse>\n    </v4:payload>\n</v4:acMessage>";

	public static ProductEnterpriseResponseDocument getProducts( ProductEnterpriseRequestDocument productRequest )
	{
		ProductEnterpriseResponseDocument response = null;
		try
		{
			// Parse the incoming XML into the doc...
			AcMessageDocument requestDocument = AcMessageDocument.Factory.parse( prodResponseXml );

			// Then pull out the ServiceabiltyRequest/Response doc...
			AcMessageType message = requestDocument.getAcMessage();
			String payloadType = message.getPayloadType();

			if ( payloadType != null && payloadType.equalsIgnoreCase( "ProductResponseDocument" ) )
			{
				AcMessageType.Payload msgPayload = message.getPayload();
				String temp = msgPayload.xmlText();

				response = ProductEnterpriseResponseDocument.Factory.parse( temp );
			}
		}
		catch ( XmlException e )
		{
			e.printStackTrace();
		}
		
		return response;
	}
}
