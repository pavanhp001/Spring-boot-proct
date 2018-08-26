package com.A.V.gateway.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;

public class JaxbUtil<T> {

	private static final Map<String, JAXBContext> contextMap = new HashMap<String, JAXBContext>();

	public JAXBContext getContext(Class<T> clazz) {
		String clazzName = clazz.getName();
		JAXBContext jaxbContext = contextMap.get(clazzName);

		if (jaxbContext == null) {
			try {
				jaxbContext = JAXBContext.newInstance(clazz);
			} catch (JAXBException e) {
				e.printStackTrace();
			}
			contextMap.put(clazzName, jaxbContext);
		}

		return jaxbContext;

	}

	public JAXBContext getContextWithBoundClass(Class<T> clazz, Class... classesToBeBound) {
		JAXBContext jaxbContext = null;
		try {
			if (classesToBeBound == null) {
				jaxbContext = JAXBContext.newInstance(clazz);
			} else {
				Class[] temp = new Class[classesToBeBound.length + 1];
				System.arraycopy(classesToBeBound, 0, temp, 0, classesToBeBound.length);
				temp[classesToBeBound.length] = clazz;
				jaxbContext = JAXBContext.newInstance(temp);
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return jaxbContext;
	}

	public String toCleanString(T object, String namespace, Class<T> clazz) {

		JAXBContext jaxbContext = getContext(clazz);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		String result = "";

		try {

			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

			jaxbMarshaller.marshal(object, baos);

			String namespaceReplace = namespace + ":";
			result = StringUtils.replace(baos.toString(), namespaceReplace, "");
			result = StringUtils.replaceOnce(result,
					"xmlns:ns2=\"http://schemas.microsoft.com/intellisense/html-5\"", "");

			try {
				result = new String(result.getBytes(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String toString(T object, Class<T> clazz) {
		return toString(object, clazz, Boolean.TRUE);
	}

	public String toStringWithBoundClass(T object, Class<T> clazz, Class... classesToBeBound) {
		return toStringWithBoundClass(object, clazz, Boolean.TRUE, classesToBeBound);
	}

	public String toStringWithBoundClass(T object, Class<T> clazz, Boolean includeXML,
			Class... classesToBeBound) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String result = "";
		try {
			JAXBContext jaxbContext = getContextWithBoundClass(clazz, classesToBeBound);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			jaxbMarshaller.setProperty("com.sun.xml.bind.xmlDeclaration", includeXML);
			jaxbMarshaller.marshal(object, baos);
			result = baos.toString();
			try {
				result = new String(result.getBytes(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String toString(T object, Class<T> clazz, Boolean includeXML) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		String result = "";

		try {

			JAXBContext jaxbContext = getContext(clazz);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			jaxbMarshaller.setProperty("com.sun.xml.bind.xmlDeclaration", includeXML);

			// Object thisObj = (Object) object;
			// jaxbMarshaller.marshal(new JAXBElement<Object>(new
			// QName("uri","local"), Object.class, thisObj), baos);
			jaxbMarshaller.marshal(object, baos);

			result = baos.toString();

			try {
				result = new String(result.getBytes(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public JAXBElement<T> toObject(String objAsString, String packageName) {

		JAXBElement<T> obj = null;

		String cleaned = objAsString.replaceAll("<ac:orderManagementRequestResponse>",
				"<ac:orderManagementRequestResponse xmlns:ac=\"http://xml.A.com/v4\" >");

		try {

			JAXBContext jaxbContext = contextMap.get("JAXBElement" + packageName);
			InputStream is = new ByteArrayInputStream(cleaned.getBytes("UTF-8"));
			if (jaxbContext == null) {
				try {
					jaxbContext = JAXBContext.newInstance(packageName);
				} catch (JAXBException e) {
					e.printStackTrace();
				}
				contextMap.put("JAXBElement" + packageName, jaxbContext);
			}

			Unmarshaller jaxbMarshaller = jaxbContext.createUnmarshaller();

			obj = (JAXBElement<T>) jaxbMarshaller.unmarshal(is);

		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return obj;
	}

	public String toString(Object object, String packageName) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		String result = "";

		try {

			JAXBContext jaxbContext = contextMap.get("JAXBElement");

			if (jaxbContext == null) {
				try {
					jaxbContext = JAXBContext.newInstance(packageName);
				} catch (JAXBException e) {
					e.printStackTrace();
				}
				contextMap.put("JAXBElement", jaxbContext);
			}

			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);

			// Object thisObj = (Object) object;
			// jaxbMarshaller.marshal(new JAXBElement<Object>(new
			// QName("uri","local"), Object.class, thisObj), baos);

			jaxbMarshaller.marshal(object, baos);

			result = baos.toString();

			try {
				result = new String(result.getBytes(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public T toObject(String objAsString, Class<T> clazz) {

		T obj = null;

		String cleaned = objAsString.replaceAll("<ac:orderManagementRequestResponse>",
				"<ac:orderManagementRequestResponse xmlns:ac=\"http://xml.A.com/v4\" >");
		try {
			InputStream is = new ByteArrayInputStream(cleaned.getBytes("UTF-8"));
			JAXBContext jaxbContext = getContext(clazz);
			Unmarshaller jaxbMarshaller = jaxbContext.createUnmarshaller();
			obj = (T) jaxbMarshaller.unmarshal(is);

		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	public T toObjectWithBoundClass(String objAsString, Class<T> clazz, Class... classesToBeBound) {

		T obj = null;

		String cleaned = objAsString.replaceAll("<ac:orderManagementRequestResponse>",
				"<ac:orderManagementRequestResponse xmlns:ac=\"http://xml.A.com/v4\" >");
		try {
			InputStream is = new ByteArrayInputStream(cleaned.getBytes("UTF-8"));
			JAXBContext jaxbContext = getContextWithBoundClass(clazz, classesToBeBound);
			Unmarshaller jaxbMarshaller = jaxbContext.createUnmarshaller();
			obj = (T) jaxbMarshaller.unmarshal(is);

		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return obj;
	}
}
