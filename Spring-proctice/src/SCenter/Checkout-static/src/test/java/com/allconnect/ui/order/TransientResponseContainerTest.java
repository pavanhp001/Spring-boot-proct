package com.AL.ui.order;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import org.junit.Test;
import com.AL.xml.v4.TransientResponseContainerType;

import com.AL.ui.service.V.OrderServiceUI;
import com.AL.ui.service.V.ProductServiceUI;
import com.AL.V.gateway.util.JaxbUtil;

public class TransientResponseContainerTest {

	public static final QName TRANSIENT_RESP_QNAME = new QName("uri", TransientResponseContainerType.class.getSimpleName());
	@Test
	public void testCreateTransientResponseContainer() {
		getTransientResponseContainer();
		/*TransientResponseContainerType response = new TransientResponseContainerType();
		response.setLineItemId("1");
		JaxbUtil<TransientResponseContainerType> util = new JaxbUtil<TransientResponseContainerType>();
		System.out.println(util.toString(response, TransientResponseContainerType.class));*/
		
	}
	
	public TransientResponseContainerType getTransientResponseContainer() {
		String xmlInput =  ProductServiceUI.INSTANCE.readFileToString(new File(".\\src\\main\\resources\\xml\\uverse3016.xml"));
		TransientResponseContainerType transientResponse = toTransientContainerObject(xmlInput);
		//System.out.println(convertTransientToxmlString(transientResponse));
		return transientResponse;
	}
	
	public TransientResponseContainerType toTransientContainerObject(String xmlInput)  {
		TransientResponseContainerType transientResponse = null;
		StringReader sr = null;
		try {
			JAXBContext transientContainerJxbContext = JAXBContext.newInstance(TransientResponseContainerType.class);
			sr = new StringReader( xmlInput );
			Unmarshaller unmarshaller = transientContainerJxbContext.createUnmarshaller();
			JAXBElement<TransientResponseContainerType> b = unmarshaller.unmarshal(new StreamSource(sr), 
					TransientResponseContainerType.class);
			transientResponse = b.getValue();
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sr.close();
		}
		return transientResponse;
	}
	
	public static String convertTransientToxmlString(TransientResponseContainerType container) {
		StringWriter sw = new StringWriter();
		String temp = null;
		try {
			JAXBElement<TransientResponseContainerType> jxb = new JAXBElement<TransientResponseContainerType>
				(TRANSIENT_RESP_QNAME, TransientResponseContainerType.class, container);
			JAXBContext transientContainerJxbContext = JAXBContext.newInstance(TransientResponseContainerType.class);
			Marshaller marshaller = transientContainerJxbContext.createMarshaller();
			marshaller.marshal(jxb, sw);
            temp = sw.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		} finally {
			try {
				sw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return temp;
	}
}
