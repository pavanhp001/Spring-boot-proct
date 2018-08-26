package com.A.common.validation;

import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.apache.xmlbeans.XmlException;

import com.A.ome.system.BaseATestX;
import com.A.validation.Message;
import com.A.validation.ValidationReport;
import com.A.validation.impl.DefaultValidationReport;
import com.A.validation.impl.PricingValidationHelper;
import com.A.xml.v4.PricingRequestResponseDocument;

public class PricingTest {

	public static void main(String[] args) throws XmlException {
		String xml = BaseATestX.getXMLFromFile("/Users/ethomas/work/V-libs/V-common/src/test/resources/xml/pricing.xml");
		
		PricingRequestResponseDocument pricing = PricingRequestResponseDocument.Factory.parse(xml);
		assertNotNull(pricing);
		
		
		String status = PricingValidationHelper.getPricingStatus(pricing);
		System.out.println(status);
		
		System.out.println(xml);
		boolean isFatal = PricingValidationHelper.isFatalErrorExist(pricing);
		System.out.println("is Fatal:"+isFatal);
		
		ValidationReport validationReport =     new DefaultValidationReport();
		PricingValidationHelper.populateStatusMsg(pricing,validationReport);
		
		Set<Message> msgList = validationReport.getMessages();
		
		for (Message msg:msgList) {
			System.out.println(msg.getMessageKey());
			
		} 
	}
}
