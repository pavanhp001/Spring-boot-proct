package com.A.Vdao.util;


import java.util.HashMap;
import java.util.Map;

public enum RulesSetSelectionBuilder {

	INSTANCE;

	public static final String CONTEXT = "context";
	public static final String PROVIDER = "provider";
	public static final String SOURCE = "source";
	public static final String SOURCE_CONTEXT = "sourceContext";
	public static final String LINE_ITEM_DETAIL_EX_ID = "lineItemDetailExternalId";
	public static final String NAMESPACE = "name";
	public static final String IS_ENABLED = "enabled";
	public static final String EFFECTIVE_FROM_ON = "dateEffectiveFrom";
	public static final String EFFECTIVE_TO_ON = "dateEffectiveTo";

	public String getWhere(Map<String, String> criteria) {

		StringBuilder sb = new StringBuilder();

		addToWhere(CONTEXT, criteria, sb);
		addToWhere(PROVIDER, criteria, sb);
		addToWhere(SOURCE, criteria, sb);
		addToWhere(SOURCE_CONTEXT, criteria, sb);
		addToWhere(LINE_ITEM_DETAIL_EX_ID, criteria, sb);
		addEnabled(sb);
	    addDateToFrom(sb);

		return sb.toString();

	}

	private String getCurrentDatabaseDate() {
		// Oracle
		//return " SYSDATE ";
		
		// Postgresql
		return " now() ";
	}

	public void addEnabled(final StringBuilder sb) {

		sb.append(" AND (enabled IS TRUE) ");

	}

	public void addDateToFrom(final StringBuilder sb) {

		sb.append(" AND (" + getCurrentDatabaseDate()
				+ " BETWEEN rs.dateEffectiveFrom AND rs.dateEffectiveTo)   ");

	}

	public void addToWhere(final String key, Map<String, String> criteria,
			final StringBuilder sb) {

		if (sb.length() > 0) {
			sb.append(" AND ");
		}

		if (criteria.containsKey(key)) {
			sb.append("(");
			String value = criteria.get(key);
			String[] results = value.split(",");
			int resultSize = results.length;
			for (int i = 0; i < resultSize; i++) {
				sb.append("(" + key + " = '" + results[i] + "') OR ");
			}
			sb.append(" (" + key + " = '*') ");

			sb.append(")");
		} else {

			sb.append(" (" + key + " = '*' )");
		}

	}

	public static void main(String[] arg) {

		Map<String, String> criteria = new HashMap<String, String>();

		
		
		
		criteria.put("context", "context,one,two");
		 criteria.put("provider", "provider1, provider2");
		String where = RulesSetSelectionBuilder.INSTANCE.getWhere(criteria);

		System.out.println(where);

	}
}
