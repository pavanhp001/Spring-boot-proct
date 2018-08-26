package com.A.V.client;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ReadXMLFile {
	
	public static final Logger logger = Logger.getLogger(ReadXMLFile.class);
	public static void main(String argv[]) {

		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				boolean code = false;
				boolean resourceName = false;

				boolean userNameB = false;
				boolean userLoginB = false;
				boolean userLevelB = false;
				boolean userActiveB = false;
				boolean userTypeB = false;
				boolean userUpdatedB = false;
				boolean providerEntityIdB = false;
				boolean userRefIdB = false;

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {

					// logger.info("Start Element :" + qName);

					if (qName.equalsIgnoreCase("v4:code")) {
						code = true;
					}

					if (qName.equalsIgnoreCase("v4:resourceName")) {
						resourceName = true;
					}

					if (qName.equalsIgnoreCase("v4:userName")) {
						userNameB = true;
					}
					if (qName.equalsIgnoreCase("v4:userLogin")) {
						userLoginB = true;
					}
					if (qName.equalsIgnoreCase("v4:userLevel")) {
						userLevelB = true;
					}
					if (qName.equalsIgnoreCase("v4:userActive")) {
						userActiveB = true;
					}
					if (qName.equalsIgnoreCase("v4:userType")) {
						userTypeB = true;
					}
					if (qName.equalsIgnoreCase("v4:userUpdated")) {
						userUpdatedB = true;
					}
					if (qName.equalsIgnoreCase("v4:providerEntityID")) {
						providerEntityIdB = true;
					}
					if (qName.equalsIgnoreCase("v4:userRefID")) {
						userRefIdB = true;
					}

				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {

					// logger.info("End Element :" + qName);

				}

				public void characters(char ch[], int start, int length)
						throws SAXException {

					if (code) {
						logger.info("code : "
								+ new String(ch, start, length));
						code = false;
					}

					if (resourceName) {
						logger.info("resource : "
								+ new String(ch, start, length));
						resourceName = false;
					}

					if (userNameB) {
						logger.info("userName : "
								+ new String(ch, start, length));
						userNameB = false;
					}
					if (userLoginB) {
						logger.info("userLogin : "
								+ new String(ch, start, length));
						userLoginB = false;
					}
					if (userLevelB) {
						logger.info("userLevel : "
								+ new String(ch, start, length));
						userLevelB = false;
					}
					if (userActiveB) {
						logger.info("userActive : "
								+ new String(ch, start, length));
						userActiveB = false;
					}
					if (userTypeB) {
						logger.info("userType : "
								+ new String(ch, start, length));
						userTypeB = false;
					}
					if (userUpdatedB) {
						logger.info("userUpdated : "
								+ new String(ch, start, length));
						userUpdatedB = false;
					}
					if (providerEntityIdB) {
						logger.info("providerEntityId : "
								+ new String(ch, start, length));
						providerEntityIdB = false;
					}
					if (userRefIdB) {
						logger.info("userRefId : "
								+ new String(ch, start, length));
						userRefIdB = false;
					}

				}

			};

			saxParser
					.parse("src\\test\\resources\\xml\\authenticate_user_response.xml",
							handler);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}