package com.AL.ui.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class JsonUtil<T> {

	private static final XStream xstream = new XStream(
			new JettisonMappedXmlDriver());

	/**
	 * converts the CKOinput json to CKOVO Object
	 * @param jsonString
	 * @param alias
	 * @param clazz
	 * @return
	 */
	public T convert(String jsonString, String alias, Class<?> clazz) {

		xstream.alias(alias, clazz);
		T vo = (T) xstream.fromXML(jsonString);

		return vo;
	}

	
	public String convert(T objInstance, String alias, Class<?> clazz) {

		xstream.alias(alias, clazz);
		String objAsString =  xstream.toXML(objInstance) ;

		return objAsString;
	}
}
