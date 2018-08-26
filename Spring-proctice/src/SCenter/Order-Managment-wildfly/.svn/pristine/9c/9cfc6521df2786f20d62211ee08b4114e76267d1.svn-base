package com.AL.ome.ie.arbiter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
import com.AL.xml.common.RtimError;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Response;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.ProviderLineItemStatusType;
import com.AL.xml.v4.ProviderLineItemStatusType.ProcessingStatusCode;
import com.AL.xml.v4.TransientResponseContainerType;
import com.AL.xml.v4.TransientResponseType;
import com.AL.xml.v4.orderFulfillment.OrderFulfillmentResponse;
import com.AL.xml.v4.rtimRequestResponse.RtimRequestResponseDocument;

/**
 * @author ebthomas
 * 
 */
// @RunWith(value = SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = {
// "classpath:**/applicationContextTest.xml",
// "classpath:**/arbiterContext.xml" })
// @Configurable
public class RtimErrorParsingTest extends TestCase // implements
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
		// String arbiterResponse = getXMLFromFile(path
		// + "src//main//resources//xml//arbiter//arbiter-0-response.xml");

		String arbiterResponse = getXMLFromFile(path
				+ "src//main//resources//xml//arbiter//arbiter-0-response.xml");

		RtimRequestResponseDocument t = RtimRequestResponseDocument.Factory
				.parse(arbiterResponse);
		//
		// System.out.println("validate:" + t.validate(validationOptions));
		//
		// Iterator iter = validationErrors.iterator();
		// while (iter.hasNext()) {
		// System.out.println(">> " + iter.next() + "\n");
		// }

		System.out.println(t.getRtimRequestResponse().getResponse()
				.getRtimErrors().getRtimErrorList().get(0).getErrorType());

		TransientResponseContainerType container = createResponseTransient(t);

		System.out.println("container--> " + container.toString());

	}

	public static TransientResponseContainerType retrieveTransientFromDocument(
			RtimRequestResponseDocument doc) {

		TransientResponseContainerType transientContainerType = null;

		if (doc != null) {

			if ((doc.getRtimRequestResponse() != null)) {
				OrderFulfillmentResponse response = (OrderFulfillmentResponse) doc
						.getRtimRequestResponse().getResponse();

				if (response != null) {
					OrderManagementRequestResponse omeResponse = response
							.getOrderManagementRequestResponse();

					if ((omeResponse != null)
							&& (omeResponse.getResponse() != null)
							&& (omeResponse.getResponse().getOrderInfoList() != null)) {
						List<OrderType> orderList = omeResponse.getResponse()
								.getOrderInfoList();

						OrderType order = orderList.get(0);

						if ((order != null) && //
								(order.getLineItems() != null) && //
								(order.getLineItems().getLineItemList() != null)) {

							LineItemType lit = order.getLineItems()
									.getLineItemList().get(0);

							transientContainerType = lit
									.getTransientResponseContainer();
						}
					}
				}
			}

		}

		if (transientContainerType == null) {
			transientContainerType = TransientResponseContainerType.Factory
					.newInstance();

		}

		return transientContainerType;

	}

	public static TransientResponseContainerType createResponseTransient(
			RtimRequestResponseDocument doc) {

		TransientResponseContainerType transientContainerType = retrieveTransientFromDocument(doc);

		if (hasErrors(doc)) {

			processErrors(doc, transientContainerType);
		}
		return transientContainerType;

	}

	public static void processErrors(RtimRequestResponseDocument doc,
			TransientResponseContainerType transientContainerType) {

		List<RtimError> rtimErrors = doc.getRtimRequestResponse().getResponse()
				.getRtimErrors().getRtimErrorList();
		
		TransientResponseType response = transientContainerType
		.addNewTransientResponse();

		for (RtimError error : rtimErrors) {
			ProviderLineItemStatusType lineItemStatus = response.addNewProviderLineItemStatus();

			lineItemStatus.setProcessingStatusCode(ProcessingStatusCode.ERROR);
			lineItemStatus.setDateTimeStamp(Calendar.getInstance());
			String errorMsg = " provider:" + error.getProvider() + //
					" code:" + error.getErrorCode() + //
					" type:" + error.getErrorType() + //
					" msg:" + error.getErrorMessage();//
			lineItemStatus
					.setLineItemStatusCode(ProviderLineItemStatusType.LineItemStatusCode.FAILED);
			lineItemStatus.addReason(errorMsg);
		}

	}

	public static boolean hasErrors(RtimRequestResponseDocument doc) {

		if ((doc != null)
				&& (doc.getRtimRequestResponse() != null)
				&& (doc.getRtimRequestResponse().getResponse() != null)
				&& (doc.getRtimRequestResponse().getResponse().getRtimErrors() != null)) {

			return Boolean.TRUE;

		}

		return Boolean.FALSE;

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
