package com.AL.ome.pricing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.xmlbeans.XmlException;
import org.junit.Before;
import org.junit.Test;

import com.AL.ome.system.BaseALTestX;
import com.AL.xml.v4.AcMessageDocument;
import com.AL.xml.v4.AcMessageType;
import com.AL.xml.v4.PricingRequestResponseDocument;

public class PricingTest {
	
	private String pricingRequest;

	@Before
	public void setup(){
		pricingRequest = BaseALTestX.getXMLFromFile("src\\test\\resources\\xml\\pricingRequest.xml");
	}

	@Test
	public void testPricing() throws XmlException{
		/*assertNotNull(pricingRequest);
		String response = TestPricingManager.sendToPricing(pricingRequest);
		assertNotNull(response);
		AcMessageDocument acMessage = AcMessageDocument.Factory.parse(response);
		assertNotNull(acMessage);
		assertNotNull(acMessage.getAcMessage().getPayload());
		AcMessageType.Payload pld = acMessage.getAcMessage().getPayload();
		PricingRequestResponseDocument priceDoc = PricingRequestResponseDocument.Factory.parse(pld.toString());
		assertEquals(priceDoc.getPricingRequestResponse().getStatus().getStatusMsg(),"INFO_ORDER_PRICED_SUCCESSFULLY");
		assertNotNull(priceDoc);
		System.out.println("********Response************");
		System.out.println(response);
		System.out.println("********************");*/
	}
}
