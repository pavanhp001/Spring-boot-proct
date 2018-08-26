package com.A.V.gateway;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.log4j.Logger;
import com.A.V.gateway.util.MarshallPool;
import com.A.V.gateway.util.UnmarshallPool;

public abstract class BaseClientService<T> {

	private static final Logger logger = Logger
			.getLogger(BaseClientService.class);

	private static final UnmarshallPool poolUM = new UnmarshallPool();
	private static final MarshallPool poolM = new MarshallPool();

	public abstract InputStream getInputStream(final String url);

	public abstract OutputStream getOutputStream(final String url);

	public void send(final String url, final T obj, final String provider,
			final String product) {

		Marshaller marshaller = null;
		OutputStream oStream = getOutputStream(url);

		try {
			marshaller = poolM.CKO();

			marshaller.marshal(obj, oStream);

		} catch (Exception fnfe) {
			logger.error(fnfe);
		} finally {

			if (oStream != null) {
				try {
					oStream.close();
					oStream = null;
				} catch (IOException e) {
					logger.error(e);
				}
			}

			if (marshaller != null) {
				poolM.checkIn(marshaller);
			}
		}

	}

	@SuppressWarnings("unchecked")
	public T getByProviderProduct(final String url, final String provider,
			final String product) {

		Unmarshaller um = null;
		JAXBElement<T> jxbElem = null;
		try {
			um = poolUM.CKO();

			Object unmarshalledObject = um.unmarshal(getInputStream(url));

			if (!(um instanceof JAXBElement)) {
				return (T) unmarshalledObject;
			}
			jxbElem = (JAXBElement<T>) um.unmarshal(getInputStream(url));
		} catch (Exception fnfe) {
			logger.error(fnfe);
		} finally {

			if (um != null) {
				poolUM.checkIn(um);
			}
		}

		T omRR = null;

		if (jxbElem != null) {
			omRR = jxbElem.getValue();
		}

		return omRR;

	}

	@SuppressWarnings("unchecked")
	public T convert(final String objAsString) {

		Unmarshaller um = null;
		JAXBElement<T> jxbElem = null;
		try {
			um = poolUM.CKO();

			Object unmarshalledObject = um
					.unmarshal(getInputStream(objAsString));

			if (!(um instanceof JAXBElement)) {
				return (T) unmarshalledObject;
			}
			jxbElem = (JAXBElement<T>) um
					.unmarshal(getInputStream(objAsString));
		} catch (Exception fnfe) {
			logger.error(fnfe);
		} finally {

			if (um != null) {
				poolUM.checkIn(um);
			}
		}

		T omRR = jxbElem.getValue();

		return omRR;

	}

}
