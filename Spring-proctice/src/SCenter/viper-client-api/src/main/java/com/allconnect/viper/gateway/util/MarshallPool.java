package com.A.V.gateway.util;

 

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.apache.log4j.Logger;

public class MarshallPool extends ObjectPool<Marshaller> {

	private static final String NAMESPACE = "com.A.xml.v4";
	private static final Logger logger = Logger.getLogger(MarshallPool.class);
	private static JAXBContext jaxbContext = null;

	static {

		try {
			jaxbContext = JAXBContext.newInstance(NAMESPACE);

		} catch (JAXBException e) {
			logger.error(e);
		}
	}

	public MarshallPool() {

	}

	@Override
	protected Marshaller create() {
		Marshaller um = null;
		try {
			um = jaxbContext.createMarshaller();
		} catch (JAXBException e) {
			logger.error(e);
		}

		return um;
	}

	@Override
	public void expire(Marshaller o) {
		o = null;
	}

	@Override
	public boolean validate(Marshaller o) {
		return (o != null);
	}
}