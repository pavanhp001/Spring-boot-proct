package com.A.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.A.xml.cm.v4.CustomerManagementRequestResponse;

public enum PrintUtil {

	INSTANCE;
	
	public void print(Object obj) {
		
		try {
			JAXBContext jc = JAXBContext
					.newInstance(CustomerManagementRequestResponse.class);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(obj, System.out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
