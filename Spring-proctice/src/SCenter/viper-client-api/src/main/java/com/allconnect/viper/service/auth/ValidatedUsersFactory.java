package com.A.V.service.auth;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.A.util.FileUtil;
import com.A.V.domain.User;
import com.A.xml.uam.v4.ValidatedUsers;

public class ValidatedUsersFactory {

	public static final String NAMESPACE = "v4:";
	public static final String USER_LOGIN = NAMESPACE + "userLogin";
	public static final String IS_VALID = NAMESPACE + "isValid";
	public static final String VALIDATE_USER_LIST = NAMESPACE + "validatedUsersList";

	public static final Logger logger = Logger.getLogger(ValidatedUsersFactory.class);

	public static void main(String[] arg) {

		String response = FileUtil
				.getStringContent("src\\test\\resources\\xml\\authenticate_user_response.xml");

		User userFromDB = AuthenticationService.INSTANCE.getUser("default");

		List<ValidatedUsers> factory = ValidatedUsersFactory.create(userFromDB,
				response);
		logger.info(factory.toString());

	}

	public static List<ValidatedUsers> create(final User user,
			final String soapResponse) {

		final List<ValidatedUsers> validatedUsers = new ArrayList<ValidatedUsers>();
		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {
				boolean userLoginB = false;
				boolean isValid = false;
				boolean validateUser = false;

				public static final String USER_LOGIN = NAMESPACE + "userLogin";
				public static final String IS_VALID = NAMESPACE + "isValid";

				ValidatedUsers validatedUser = null;

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
								throws SAXException {

					if (qName.equalsIgnoreCase(VALIDATE_USER_LIST)) {
						validatedUser = new ValidatedUsers();
						validateUser = true;
					}
					if (qName.equalsIgnoreCase(USER_LOGIN)) {
						userLoginB = true;
					}
					if (qName.equalsIgnoreCase(IS_VALID)) {
						isValid = true;
					}
				}
				
				public void endElement(String uri, String localName,
						String qName) throws SAXException {
					if (qName.equalsIgnoreCase(VALIDATE_USER_LIST)) {
						validatedUsers.add(validatedUser);
			        }

				}

				public void characters(char ch[], int start, int length)
						throws SAXException {
					if (userLoginB) {
						validatedUser.setUserLogin(new String(ch, start, length));
						userLoginB = false;
					}
					if (isValid) {
						String valid = new String(ch, start, length);
						if(valid.equalsIgnoreCase("true")){
							validatedUser.setIsValid(Boolean.TRUE);
						} else {
							validatedUser.setIsValid(Boolean.FALSE);
						}
						isValid = false;
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

		/*validatedUsers.add(validatedUser);*/
		
		return validatedUsers;
		/*validatedUser.getRoles().clear();
		return validatedUsers;*/
	}
}

