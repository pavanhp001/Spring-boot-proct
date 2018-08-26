package com.A.ui.service.transform;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

 

public class XSLTTransform {

	public static String transform(final String xmlFile, final String xsltFile)
			throws TransformerException {

		StringReader readerXML = new StringReader(xmlFile);
		StringReader readerXSLT = new StringReader(xsltFile);

		StringWriter writer = new StringWriter();
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer(new StreamSource(
				readerXSLT,String.valueOf(System.nanoTime())));

		transformer.transform(new javax.xml.transform.stream.StreamSource(
				readerXML), new StreamResult(writer));

		String result = writer.toString();
 

		return result;

	}
}
