package com.A.util;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility class to handle all string related functionality.
 *
 * @author ssathiyanarayanan
 *
 */
public final class StringUtils {
	/**
	 * Default Constructor.
	 */
	private StringUtils() {
		// Do nothing.
	}

	public static List<Integer> tokenize(final String input, final String delims) {

		String[] tokens = input.split(delims);
		List<Integer> tokenList = new ArrayList<Integer>();
		for (String token : tokens) {
			tokenList.add(Integer.valueOf(token));
		}

		return tokenList;
	}

	/**
	 *
	 * @param str
	 *            the String to check, may be null
	 * @return true if the String is null, empty or whitespace
	 *
	 */
	public static boolean isBlank(final String str) {
		if (str == null) {
			return true;
		}
		if (str.length() == 0) {
			return true;
		}
		for (int i = 0; i < str.length(); i++) {
			if ((!Character.isWhitespace(str.charAt(i))))

			{
				return false;
			}
		}
		return true;
	}

	/**
	 *
	 * @param str
	 *            the String to check, may be null
	 * @return true if the String is not empty and not null and not whitespace
	 *
	 */
	public static boolean isNotBlank(final String str) {
		return !StringUtils.isBlank(str);
	}

	public static boolean isNotNullNotEmptyNotWhiteSpace(
			final String input) {
		return input != null && !input.isEmpty() && !input.trim().isEmpty();
	}
}
