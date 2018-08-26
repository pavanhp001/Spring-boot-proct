package com.A.V.gateway.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.log4j.Logger;

public class UnmarshallPool extends ObjectPool<Unmarshaller> {

	private static final String NAMESPACE = "com.A.xml.v4";
	private static final Logger logger = Logger.getLogger(UnmarshallPool.class);
	private static JAXBContext jaxbContext = null;

	static {

		try {
			jaxbContext = JAXBContext.newInstance(NAMESPACE);

		} catch (JAXBException e) {
			logger.error(e);
		}
	}

	public UnmarshallPool() {

	}

	@Override
	protected Unmarshaller create() {
		Unmarshaller um = null;
		try {
			um = jaxbContext.createUnmarshaller();
		} catch (JAXBException e) {
			logger.error(e);
		}

		return um;
	}

	@Override
	public void expire(Unmarshaller o) {
		o = null;
	}

	@Override
	public boolean validate(Unmarshaller o) {
		return (o != null);
	}
}