package com.AL.ui.validation;
import java.util.Hashtable;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

//import com.AL.common.formatters.InvalidFormatException;
import com.AL.ui.exception.InvalidDataException;
import com.AL.ui.exception.InvalidFormatException;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */


public class EmailValidator {

	public EmailValidator() {
	}

	public boolean validate(Object obj) throws InvalidDataException
	{
		return isTextValid((String)obj);
	}

	private boolean isTextValid(String emailStr)  throws InvalidDataException
	{
		int atIndex = emailStr.indexOf( "@" );

		//Treat Customer email addresses as optional
		if ( !emailStr.equals("") ) {
			// if no '@' then no valid...
			if ( atIndex <= 0 || (atIndex + 1) >= emailStr.length() )
				throw new InvalidDataException("E-mail not valid, missing or missplaced \"@\" symbol.");

			// parse off everything before and including the '@'
			String url = emailStr.substring( atIndex + 1 );
			String address = emailStr.substring( 0, atIndex );

			// verify that there is a '.' and it isn't the last character
			if (!isProperAddress(url, true) || !isProperAddress(address, false))
				throw new InvalidDataException("E-mail not valid.");

			if (!isValidDNS(url)) {
				throw new InvalidDataException("Domain (" + url + ") does not have E-mail server.");
			}
		}

		return true;
	}

	private boolean isProperAddress( String str, boolean mustHaveDot )
	{
		if (str == null || str.length() == 0)
			return false;

		// check for dots
		// allow as many dots as they want,
		// just don't let them be adjacent to each other, or on the start or end.
		// loop over characters and check all of them

		boolean hasDot = false;
		boolean previousCharWasDot = false;
		int lastIndex = str.length()-1;

		for(int i=0; i<=lastIndex; i++)
		{
			char x = str.charAt(i);

			if(x == '.')
			{
				// check for first or last
				if(i == 0 || i == lastIndex)
				{
					return false;
				}

				// if 2 dots in a row, it's no good
				if(previousCharWasDot)
				{
					return false;
				}

				hasDot = true;
				previousCharWasDot = true;
			}
			else
			{
				if(!(Character.isLetterOrDigit(x) || x == '-' || x == '_'))
				{
					return false;
				}

				previousCharWasDot = false;
			}
		}

		return (mustHaveDot ? hasDot : true);

	}

	public Object normalizeFormat(Object obj) throws InvalidFormatException
	{
		if (obj instanceof String) {
			String str = ((String)obj);
			return str;
		} else {
			throw new InvalidFormatException("Expected e-mail string, but found: " + obj);
		}

		// return not reachable
	}

	private boolean isValidDNS(String url) throws InvalidDataException {
		try {
			Hashtable <String, String> env = new Hashtable <String, String>();
			env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");

			DirContext ictx = new InitialDirContext(env);
			Attributes mxAttr = ictx.getAttributes(url,
					new String[] {"MX"});
			if (mxAttr == null || mxAttr.size() == 0 || mxAttr.get("MX") == null) {
				return false;
			} else {
				return true;
			}
		} catch (NamingException e) {
			return false;
		}
	}

} // end class EMailValidator
