package com.A.Vdao.util;

import org.apache.log4j.Logger;

import com.A.Vdao.dao.impl.SearchCriteria;

public class QueryBuilder {
	private static final Logger logger = Logger.getLogger(QueryBuilder.class);
	private static final String fromClause = " FROM CM_CONSUMER C ";
	private static final String WHERE = " WHERE ";
	private static final String EQUAL_OPERATOR = "=";
	private static final String LIKE_OPERATOR = " LIKE ";
	private static final String QUOTE = "'";
	private static final String SEPARATOR = " AND ";
	private static final String SEPARATOR_OR = " OR ";
	private static final String ADDRESS_JOIN = " INNER JOIN CM_ADDRESS A ON C.EXTERNAL_ID = A.CONSUMER_EXTERNAL_ID ";
	private static final String CONTACT_JOIN = "   ";
	private static final String ORDER_BY = " ORDER BY C.EXTERNAL_ID DESC ";

	private StringBuilder query = null;
	private StringBuilder selectClause = null;// new StringBuilder("SELECT ");
	private StringBuilder joinClause = null;
	private StringBuilder whereClause = null;
	private Boolean isWhereClauseExist = Boolean.FALSE;
	private Boolean isAddressJoinRequired = Boolean.FALSE;
	private Boolean isContactJoinRequired = Boolean.FALSE;

	// TODO fix the following code with list of columns instead of returning all
	// fields
	static {
		// selectClause.append(
		// "\"C\".\"CONSUMER_ID\" ," +
		// "\"C\".\"EXTERNAL_ID\" " +
		// "\"C\".\"A_CUST_NUM\"," +
		// "\"C\".\"TITLE\" ," +
		// "\"C\".\"FIRSTNAME\" " +
		// "\"C\".\"MIDDLENAME\"," +
		// "\"C\".\"LASTNAME\"," +
		// "\"C\".\"DOB\"," +
		// "\"C\".\"SSN\"," +
		// "\"C\".\"GENDER\" " +
		// " C.* ");
	}

	private   void buildBaseQuery() {
		query = new StringBuilder();
		selectClause = new StringBuilder("SELECT C.* ");
		joinClause = new StringBuilder();
		whereClause = new StringBuilder();
		isWhereClauseExist = Boolean.FALSE;
		isAddressJoinRequired = Boolean.FALSE;
		isContactJoinRequired = Boolean.FALSE;
	}

	public String createQuery(SearchCriteria criteria) {
		logger.info("Building query based on search parameter...");

		// Build base query
		buildBaseQuery();

		// add search fields provided from client
		addSeachFields(criteria);

		// If address fields are added in search query then add join
		if (isAddressJoinRequired) {
			addAddressJoin();
		}

		// If fields from Contact channel table added in search query then add
		// the join
		if (isContactJoinRequired) {
			addContactJoin();
		}

		// prepare final query
		String q = getFinalQuery();
		return q;
	}

	private   void addSeachFields(SearchCriteria criteria) {

		// Customer based search
		addCustomerFields(criteria);

		// Address based search
		addAddressFields(criteria);

		// Contact info based search
		appendPhone(query, criteria);

		// Contact info based search
		appendEmail(query, criteria);
	}

	private   void addCustomerFields(SearchCriteria criteria) {
		appendFirstName(selectClause, criteria);
		appendLastName(selectClause, criteria);
		appendSSN(selectClause, criteria);
		appendCustomerNo(selectClause, criteria);
	}

	private   void addAddressFields(SearchCriteria criteria) {
		appendStreetAddress(selectClause, criteria);
		appendCity(selectClause, criteria);
		appendState(selectClause, criteria);
		appendZipcode(selectClause, criteria);
	}

	private   String getFinalQuery() {
		query.append(selectClause.toString());
		query.append(fromClause);
		query.append(joinClause.toString());
		query.append(whereClause.toString());
		query.append(ORDER_BY);
		return query.toString();
	}

	private   void appendPhone(StringBuilder query2, SearchCriteria c) {

		if (c.getPhoneNo() != null && c.getPhoneNo().length() > 0) {

			addBeginGroup();

			addWhereCondition(  "C.HOME_PHONE_VALUE", EQUAL_OPERATOR,c.getPhoneNo() );
			addWhereOR();
			addWhereCondition(  "C.CELL_PHONE_VALUE", EQUAL_OPERATOR,c.getPhoneNo() );
			addWhereOR();
			addWhereCondition(  "C.WORK_PHONE_VALUE", EQUAL_OPERATOR,c.getPhoneNo() );

			addWhereOR();
			addWhereCondition(  "C.BEST_PHONE_CONTACT", EQUAL_OPERATOR,c.getPhoneNo() );

			addWhereOR();
			addWhereCondition(  "C.SECOND_PHONE", EQUAL_OPERATOR,c.getPhoneNo() );

			addEndGroup();

			isContactJoinRequired = Boolean.TRUE;
		}
	}

	private   void appendEmail(StringBuilder query2, SearchCriteria c) {

		if (c.getEmailId() != null && c.getEmailId().length() > 0) {

			addBeginGroup();

			addWhereCondition(  "C.HOME_EMAIL_VALUE", EQUAL_OPERATOR,
					c.getEmailId() );
			addWhereOR();
			addWhereCondition(  "C.WORK_EMAIL_VALUE", EQUAL_OPERATOR,
					c.getEmailId() );

			addEndGroup();

			isContactJoinRequired = Boolean.TRUE;
		}
	}

	// StringBuffer sb = new StringBuffer(100);
	// sb.append("UPPER(");
	// //sb.append(ALIAS);
	// sb.append("C.FIRSTNAME)");
	// buildQueryString(criteria,sb.toString(),EQUAL_OPERATOR,criteria.getFirstName().toUpperCase());
	//

	private   void appendCustomerNo(StringBuilder query2,
			SearchCriteria criteria) {
		if (criteria.getCustomerNo() != null
				&& criteria.getCustomerNo().length() > 0) {
			buildQueryString(criteria, "A_CUST_NUM", EQUAL_OPERATOR,
					criteria.getCustomerNo().trim());
		}
	}

	private   void appendStreetAddress(StringBuilder query2, SearchCriteria criteria) {
		if (criteria.getStreetAddress() != null && criteria.getStreetAddress().length() > 0) {

			addBeginGroup();

			addWhereCondition(  "A.STREET_NAME", EQUAL_OPERATOR,
					criteria.getStreetAddress().toUpperCase() );
			//addWhereOR();
			//addWhereCondition(  "A.LINE2", EQUAL_OPERATOR,criteria.getStreetAddress().toUpperCase() );

			addEndGroup();
			isAddressJoinRequired = Boolean.TRUE;
		}

	}

	private   void appendCity(StringBuilder query2, SearchCriteria criteria) {
		if (criteria.getCity() != null && criteria.getCity().length() > 0) {
			buildJoinQueryString(criteria, "A.CITY", EQUAL_OPERATOR,
					criteria.getCity().toUpperCase(), ADDRESS_JOIN);
			isAddressJoinRequired = Boolean.TRUE;
		}

	}

	private   void appendState(StringBuilder query2,
			SearchCriteria criteria) {
		if (criteria.getState() != null && criteria.getState().length() > 0) {
			buildJoinQueryString(criteria, "A.STATE_OR_PROV", EQUAL_OPERATOR,
					criteria.getState().toUpperCase(), ADDRESS_JOIN);
			isAddressJoinRequired = Boolean.TRUE;
		}

	}

	private   void appendZipcode(StringBuilder query2,
			SearchCriteria criteria) {
		if (criteria.getZipCode() != null && criteria.getZipCode().length() > 0) {
			//buildJoinQueryString(criteria, "A.POSTAL_CODE", EQUAL_OPERATOR,
			//		criteria.getZipCode(), ADDRESS_JOIN);
			String val =  criteria.getZipCode()+"%";
			buildQueryString(criteria, "A.POSTAL_CODE", LIKE_OPERATOR, val);
			isAddressJoinRequired = Boolean.TRUE;
		}

	}

	private   void appendSSN(StringBuilder query2, SearchCriteria criteria) {
		logger.info("adding search parameter last fouse ssn");
		if (criteria.getSsn() != null && criteria.getSsn().length() > 0) {
		    	String lastFour = criteria.getSsn().trim();
		    	if(criteria.getSsn().trim().length() == 9) {
		    	    lastFour = lastFour.substring(5, 9);

		    	}
			buildQueryString(criteria, "C.SSN_LAST_FOUR", EQUAL_OPERATOR, lastFour);
		}

	}

	private   void appendLastName(StringBuilder query2,
			SearchCriteria criteria) {
		logger.info("adding search parameter lastName");
		if (criteria.getLastName() != null
				&& criteria.getLastName().length() > 0) {
			StringBuffer sb = new StringBuffer(100);
			sb.append("C.LASTNAME");
			buildQueryString(criteria, sb.toString(), EQUAL_OPERATOR, criteria
					.getLastName().toUpperCase());
		}

	}

	private   void appendFirstName(StringBuilder query2,
			SearchCriteria criteria) {
		logger.info("adding search parameter for firstName");
		if (criteria.getFirstName() != null
				&& criteria.getFirstName().length() > 0) {
			StringBuffer sb = new StringBuffer(100);
			sb.append("C.FIRSTNAME");
			buildQueryString(criteria, sb.toString(), EQUAL_OPERATOR, criteria
					.getFirstName().toUpperCase());
		}
	}

	private   void buildQueryString(SearchCriteria criteria,
			String columnName, String operator, String value) {
		addWhereClause();
		addWhereCondition(columnName, operator, value);
	}

	private   void addBeginGroup( ) {

		if (!isWhereClauseExist) {
			whereClause.append(WHERE);
			isWhereClauseExist = Boolean.TRUE;
		} else {
			whereClause.append(" AND ");
			isWhereClauseExist = Boolean.TRUE;
		}


		whereClause.append( " ( ");
	}

	private   void addEndGroup( ) {
		whereClause.append( " ) ");
	}


	private   void addWhereOR( ) {
		whereClause.append( SEPARATOR_OR);
	}

	private   void addWhereCondition(String columnName, String operator,
			String value) {
		whereClause.append(columnName);
		whereClause.append(operator);
		whereClause.append(QUOTE);
		whereClause.append(value);
		whereClause.append(QUOTE);
	}

	private   void buildJoinQueryString(SearchCriteria criteria,
			String columnName, String operator, String value, String join) {
		// addAddressJoin(join);
		// If adding search parameter for first time then add where clause
		addWhereClause();
		addWhereCondition(columnName, operator, value);
	}





	private   void addWhereClause() {
		if (!isWhereClauseExist) {
			whereClause.append(WHERE);
			isWhereClauseExist = Boolean.TRUE;
		} else { // else add 'AND' parameter between diff search parameter
			whereClause.append(SEPARATOR);
		}
	}

	private   void addAddressJoin() {
		joinClause.append(ADDRESS_JOIN);
	}

	private   void addContactJoin() {
		joinClause.append(CONTACT_JOIN);
	}

	public static void main(String[] args) {
		SearchCriteria criteria = new SearchCriteria();

		// Search based on customer fields
		criteria.setFirstName("Eban");
		criteria.setLastName("singh");
		// criteria.setSsn("6589");
		// criteria.setCustomerNo("AC-131072934448933");

		// Search based on address fields
//		criteria.setStreetAddress("101 beaver ruin rd");
//		criteria.setCity("Norcross");
//		criteria.setState("Georgia");
//		criteria.setZipCode("30093");

		// Search based on Contact info
		criteria.setPhoneNo("4044445555");

		QueryBuilder qb = new QueryBuilder();
		String q = qb.createQuery(criteria);
		System.out.println(q);
	}
}
