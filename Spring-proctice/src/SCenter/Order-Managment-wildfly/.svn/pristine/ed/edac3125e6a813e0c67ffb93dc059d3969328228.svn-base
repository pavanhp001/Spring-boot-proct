package com.AL.ie.service.strategy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.apache.xmlbeans.XmlException;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Response;
import com.AL.xml.v4.orderFulfillment.OrderFulfillmentResponse;

public class MotherObjectArbiter {

	public static final String path = "C:\\projects\\ome-trunk\\ome-core\\";
	
	public static void populateTestStructureOnly(OrderFulfillmentResponse oFullfillment) {

		OrderManagementRequestResponse omRR = oFullfillment
				.addNewOrderManagementRequestResponse();
		Response response = omRR.addNewResponse();

		
		String order =  getXMLFromFile(path+"src//main//resources//xml//arbiter//arbiter-order-snip-1.xml");

		OrderType ot;
		try {
			ot = OrderType.Factory.parse(order);
			OrderType[] otArray = { ot };
			response.setOrderInfoArray(otArray);
		} catch (XmlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
