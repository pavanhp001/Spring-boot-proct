package com.AL.ome.ie.arbiter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import junit.framework.TestCase;
import org.apache.xmlbeans.XmlOptions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import com.AL.vm.util.converter.marshall.MarshallOrder;
import com.AL.ie.service.strategy.ArbiterFlowManagerDefault;
import com.AL.ie.service.strategy.MotherObjectArbiter;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.orderFulfillment.OrderFulfillmentRequestDocument;

/**
 * @author ebthomas
 * 
 */
// @RunWith(value = SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = {
// "classpath:**/applicationContextTest.xml",
// "classpath:**/arbiterContext.xml" })
// @Configurable
public class XMLValidatorTest extends TestCase // implements
													// ApplicationContextAware {

{
	private AtomicInteger counter = new AtomicInteger(10);

	@Autowired
	ArbiterFlowManagerDefault arbiter;

	MarshallOrder<SalesOrder> marshallOrder;

	@Autowired
	private ApplicationContext applicationContext;

	@Before
	public void setUp() {

 
	}

	

	@Test
	public void testFlow() throws Exception {

		// Set up the validation error listener.
		ArrayList validationErrors = new ArrayList();
		XmlOptions validationOptions = new XmlOptions();
		validationOptions.setErrorListener(validationErrors);
		
		String path = MotherObjectArbiter.path;
//		String arbiterResponse = getXMLFromFile(path
//				+ "src//main//resources//xml//arbiter//arbiter-0-response.xml");

		String arbiterResponse = getXMLFromFile("C:\\projects\\ome-trunk\\ome-core\\src\\main\\resources\\xml\\ome-submit.xml");
		
		//String result =  ArbiterMarshaller.INSTANCE.extractLineItemsResponseContainer(arbiterResponse,"v4");
		
		//LineItemCollectionType t = LineItemCollectionType.Factory.parse(result);
		 
		OrderManagementRequestResponseDocument t = OrderManagementRequestResponseDocument.Factory.parse(arbiterResponse);
		
		
//		System.out.println(t.xmlText(
//			     new XmlOptions().setSaveOuter() ));
		
//		t.getOrderFulfillmentRequest();
//		t.getOrderFulfillmentRequest().getContext();
//		t.getOrderFulfillmentRequest().getContext().getAffiliateName();
//		RtimRequestResponse rrr =    x.addNewRtimRequestResponse();
//		RtimResponse rr = rrr.addNewResponse();
//		  
//		System.out.println(x.toString());
//		
//		System.out.println(x.toString());
		
		
//		
//		 
//		RtimRequestResponseDocument t = RtimRequestResponseDocument.Factory.parse(arbiterResponse);
		
		System.out.println("validate:"+t.validate(validationOptions));
		 
		
		// Print the errors if the XML is invalid.
	  
		    Iterator iter = validationErrors.iterator();
		    while (iter.hasNext())
		    {
		        System.out.println(">> " + iter.next() + "\n");
		    }
//		 
//		    System.out.println(t.getLineItemList().get(0).getLineItemNumber());
//	 	System.out.println(t.getLineItemList().get(0).getExternalId());
//	 	System.out.println(t.getLineItemList().get(0).getTransientResponseContainer().toString());
	}

	

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	/**
	 * @param fileName
	 *            name to get FileName
	 * @return String contents
	 */
	public static String getXMLFromFile(final String fileName) {
		File file = new File(fileName);
		StringBuffer contents = new StringBuffer();
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));
			String text = null;

			// repeat until all lines is read
			while ((text = reader.readLine()) != null) {
				contents.append(text).append(
						System.getProperty("line.separator"));
			}

			return contents.toString();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
}
