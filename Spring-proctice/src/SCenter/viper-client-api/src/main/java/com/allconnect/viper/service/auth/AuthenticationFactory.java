package com.A.V.service.auth;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import com.A.V.domain.User;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.A.vo.UserAuthorization;
import com.A.util.FileUtil;

import com.A.vo.UserGroup;

public class AuthenticationFactory {

	private static final Object OBJECT_BLOCK = new Object();
	public static final String STRING_BLOCK = new String("GROUP");
	public static final String LONG_BLOCK = new String("CONTEXT");
	public static final String NAMESPACE = "v4:";

	public static final String CODE = NAMESPACE + "code";
	public static final String RESOURCE_NAME = NAMESPACE + "resourceName";
	public static final String USER_NAME = NAMESPACE + "userName";
	public static final String USER_LOGIN = NAMESPACE + "userLogin";
	public static final String USER_LEVEL = NAMESPACE + "userLevel";
	public static final String USER_ACTIVE = NAMESPACE + "userActive";
	public static final String USER_TYPE = NAMESPACE + "userType";
	public static final String USER_GROUP = NAMESPACE + "userGroup";
	
	public static final Logger logger = Logger.getLogger(AuthenticationFactory.class);

	public static void main(String[] arg) {

		String response = FileUtil
				.getStringContent("src\\test\\resources\\xml\\authenticate_user_response.xml");

		User userFromDB = AuthenticationService.INSTANCE.getUser("default");

		UserAuthorization factory = AuthenticationFactory.create(userFromDB,
				response);
		logger.info(factory.toString());

	}

	public static UserAuthorization create(final User user,
			final String soapResponse) {

		final UserAuthorization auth = new UserAuthorization();

		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				boolean code = false;
				boolean text = false;
				boolean resourceName = false;
				boolean metaDataName = false;
				boolean roleName = false;

				boolean userNameB = false;
				boolean userLoginB = false;
				boolean userLevelB = false;
				boolean userActiveB = false;
				boolean userTypeB = false;
				boolean userGroupB = false;
				boolean impactedAreaB = false;

				public static final String CODE = NAMESPACE + "code";
				public static final String TEXT = NAMESPACE + "text";
				public static final String RESOURCE_NAME = NAMESPACE + "resource";
				public static final String METADATA_NAME = NAMESPACE + "metadata";
				public static final String ROLE_NAME = NAMESPACE + "roleName";
				public static final String USER_NAME = NAMESPACE + "userName";
				public static final String USER_LOGIN = NAMESPACE + "userLogin";
				public static final String USER_LEVEL = NAMESPACE + "userLevel";
				public static final String USER_ACTIVE = NAMESPACE
						+ "userActive";
				public static final String USER_TYPE = NAMESPACE + "userType";
				public static final String USER_GROUP = NAMESPACE + "userGroup";
				public static final String IMPACTED_AREA = NAMESPACE + "impactedArea";
				
				UserGroup userGroup = null;
			    List<UserGroup> userGroups = new ArrayList<UserGroup>();

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {
					if (qName.equalsIgnoreCase(CODE)) {
						code = true;
					}

					if (qName.equalsIgnoreCase(TEXT)) {
						text = true;
					}

					if (qName.equalsIgnoreCase(RESOURCE_NAME)) {
						resourceName = true;
						auth.getPermissions().add(attributes.getValue("name"));
					}
					
					if (qName.equalsIgnoreCase(METADATA_NAME)) {
						metaDataName = true;
						auth.getMetaDataMap().put(attributes.getValue("name"), attributes.getValue("value"));
					}

					if (qName.equalsIgnoreCase(USER_NAME)) {
						userNameB = true;
					}
					if (qName.equalsIgnoreCase(USER_LOGIN)) {
						userLoginB = true;
					}
					if (qName.equalsIgnoreCase(USER_LEVEL)) {
						userLevelB = true;
					}
					if (qName.equalsIgnoreCase(USER_ACTIVE)) {
						userActiveB = true;
					}
					if (qName.equalsIgnoreCase(USER_TYPE)) {
						userTypeB = true;
					}
					if (qName.equalsIgnoreCase(USER_GROUP)) {
						userGroup = new UserGroup();
						userGroup.setGroupName(attributes.getValue("name"));
						userGroupB = true;
					}
					if (qName.equalsIgnoreCase(IMPACTED_AREA)) {
						userGroup.getImpactedAreas().put(attributes.getValue("name"), "true");
						impactedAreaB = true;
					}
					if (qName.equalsIgnoreCase(ROLE_NAME)) {
						roleName = true;
					}
				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {
					if (qName.equalsIgnoreCase(USER_GROUP)) {
						auth.getUserGroups().add(userGroup);
			        }

				}

				public void characters(char ch[], int start, int length)
						throws SAXException {

					if (code) {
						auth.getCodes().put(new String(ch, start, length), OBJECT_BLOCK);
						code = false;
					}

					if (text) {
						auth.getTexts().put(new String(ch, start, length), OBJECT_BLOCK);
						text = false;
					}

					if (resourceName) {
						resourceName = false;
					}

					if (metaDataName) {
						metaDataName = false;
					}

					if (roleName) {
						String key = new String(ch, start, length);

						Scanner scanner = new Scanner(key);
						if (scanner.hasNextLong()) {
							auth.getRoles().put(key, LONG_BLOCK);
						} else {
							auth.getRoles().put(key, STRING_BLOCK);
						}

						roleName = false;

					}

					if (userNameB) {
						auth.setUserName(new String(ch, start, length));
						userNameB = false;
					}
					if (userLoginB) {
						auth.setUserLogin(new String(ch, start, length));
						userLoginB = false;
					}
					if (userLevelB) {
						auth.setUserLevel(new String(ch, start, length));
						userLevelB = false;
					}
					if (userActiveB) {
						userActiveB = false;
					}
					if (userTypeB) {
						auth.setUserType(new String(ch, start, length));
						userTypeB = false;
					}
					if (userGroupB) {
						userGroupB = false;
					}					
				}

			};

			if ((soapResponse != null) && (soapResponse.length() > 0)) {
				InputStream in = new ByteArrayInputStream(
						soapResponse.getBytes());

				saxParser.parse(in, handler);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (auth.authenticated()) {
			auth.setUser(user);
			return auth;
		} else {
			auth.getRoles().clear();
			return auth;
		}

	}

}
