package com.A.Vdao.util;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import com.A.util.StringUtils;
import com.A.util.convert.SafeConvert;
import com.A.Vdao.dao.impl.SearchCriteria;
import com.truemesh.squiggle.Criteria;
import com.truemesh.squiggle.FunctionCall;
import com.truemesh.squiggle.SelectQuery;
import com.truemesh.squiggle.Selectable;
import com.truemesh.squiggle.Table;
import com.truemesh.squiggle.criteria.MatchCriteria;
import com.truemesh.squiggle.criteria.OR;
import com.truemesh.squiggle.literal.StringLiteral;

public class QueryBuilderUtil {

    	private static final String OM_SALES_ORDER = "om_sales_order";

	//Postgres pattern match operator
	private static final String PPATERN_MATCH_TYPE = "~*";

	private static final String SSN_LAST_FOUR = "ssn_last_four";
	private static final String STATE_OR_PROV = "state_or_prov";
	private static final String STREET_NAME = "street_name";
	private static final String CITY = "city";
	private static final String CM_CONSUMER_ADDRESS = "cm_consumer_address";
	private static final String CM_ADDRESS = "cm_address";
	private static final String CONSUMER_ID = "consumer_id";
	private static final String ADDRESS_ID = "address_id";
	private static final String POSTAL_CODE = "postal_code";
	private static final String DT_CREATED = "dt_created";
	private static final String A_CUST_NUM = "A_cust_num";
	private static final String WORK_EMAIL_VALUE = "work_email_value";
	private static final String HOME_EMAIL_VALUE = "home_email_value";
	private static final String SECOND_PHONE = "second_phone";
	private static final String BEST_PHONE_CONTACT = "best_phone_contact";
	private static final String CELL_PHONE_VALUE = "cell_phone_value";
	private static final String WORK_PHONE_VALUE = "work_phone_value";
	private static final String HOME_PHONE_VALUE = "home_phone_value";
	private static final String LASTNAME = "lastname";
	private static final String FIRSTNAME = "firstname";
	private static final String EXTERNAL_ID = "external_id";
	private static final String ADDRESS_ROLE = "address_role";
	private static final String SERVICEADDRESS = "ServiceAddress";
	private static final String CM_CONSUMER = "cm_consumer";
	private static final Logger logger = Logger.getLogger(QueryBuilderUtil.class);

	/**
	 * Generates select query based on search criteria
	 * @param criteria
	 * @return
	 */
	public static String generateSelectQuery(SearchCriteria criteria, boolean selectAllCustFields) {

		logger.debug("Building select query for searchCustomer with criteria : " +criteria.toString());
		// base query
		SelectQuery select = new SelectQuery();

		select.setDistinct(true);

		// all table in the query
		Table consumer_tab = new Table(CM_CONSUMER);

		Table order_tab = new Table(OM_SALES_ORDER);

		if (selectAllCustFields) {
		    select.addColumn(consumer_tab, "*");
		}
		else {
		    select.addColumn(consumer_tab, EXTERNAL_ID);
		    select.addColumn(consumer_tab, FIRSTNAME);
		    select.addColumn(consumer_tab, LASTNAME);
		    select.addColumn(consumer_tab, HOME_PHONE_VALUE);
		    select.addColumn(consumer_tab, WORK_PHONE_VALUE);
		    select.addColumn(consumer_tab, CELL_PHONE_VALUE);
		    select.addColumn(consumer_tab, BEST_PHONE_CONTACT);
		    select.addColumn(consumer_tab, SECOND_PHONE);
		    select.addColumn(consumer_tab, HOME_EMAIL_VALUE);
		    select.addColumn(consumer_tab, WORK_EMAIL_VALUE);
		    select.addColumn(consumer_tab, A_CUST_NUM);
		    select.addColumn(consumer_tab, DT_CREATED);
		    select.addColumn(order_tab, "agent_id");
		    select.addColumn(order_tab, "order_date");
		    select.addColumn(order_tab, "external_id");

		}

		addAddressTablesAndWhere(criteria, select, consumer_tab);

		addCustomerWhere(criteria, select, consumer_tab,order_tab);
		int i = NumberUtils.toInt(criteria.getProviderId());
		if(i > 0) {
		    addOrderTablesAndWhere(criteria,select,consumer_tab);
		}

		select.addOrder(consumer_tab, EXTERNAL_ID, Boolean.FALSE);

		logger.debug(select.toString());


		return select.toString();
	}


	private static void addOrderTablesAndWhere(SearchCriteria criteria,
			SelectQuery select, Table consumer_tab) {
	    //Table sales_order_tab = new Table(OM_SALES_ORDER);
	    Table soli = new Table("om_sales_order_om_line_item");
	    Table li = new Table("om_line_item");
	    //select.addJoin(sales_order_tab, "id", soli, "om_sales_order_id");
	    select.addJoin(soli, "lineitems_id", li, "id");
	    //select.addJoin(sales_order_tab, "cust_ext_id", consumer_tab, "external_id");
	    select.addCriteria(new MatchCriteria(li, "provider_ext_id",MatchCriteria.EQUALS, criteria.getProviderId().trim()));
	}


	/**
	 * Generates count query based on provided search criteria
	 * @param criteria
	 * @return
	 */
	public static String generateCountQuery(SearchCriteria criteria) {

		logger.debug("Building select query for searchCustomer with criteria : " +criteria.toString());
		// base query
		SelectQuery select = new SelectQuery();

		//Order table
		Table order_tab = new Table(OM_SALES_ORDER);


		// all table in the query
		Table consumer_tab = new Table(CM_CONSUMER);

		// add count(*)
		//select.addToSelection(new FunctionCall("count", new FunctionCall("distinct",new StringLiteral("*"))));
		select.addToSelection(new FunctionCall("count", new FunctionCall("distinct",consumer_tab.getColumn("external_id"))));

		addCustomerWhere(criteria, select, consumer_tab, order_tab);
		addAddressTablesAndWhere(criteria, select, consumer_tab);
		if(NumberUtils.toInt(criteria.getProviderId()) > 0) {
		    addOrderTablesAndWhere(criteria, select, consumer_tab);
		}
		logger.debug(select.toString());

		return select.toString();
	}



	/**
	 * Helper method to add address tables and where clause columns
	 * @param criteria
	 * @param select
	 * @param consumer_tab
	 */
	private static void addAddressTablesAndWhere(SearchCriteria criteria,
			SelectQuery select, Table consumer_tab) {
		// table joins
		if (isAddressFieldsExist(criteria)) {
			Table cons_addr_tab = new Table(CM_CONSUMER_ADDRESS);
			Table address_tab = new Table(CM_ADDRESS);
			select.addJoin(consumer_tab, CONSUMER_ID, cons_addr_tab,
					CONSUMER_ID);
			select.addJoin(address_tab, ADDRESS_ID, cons_addr_tab,
					ADDRESS_ID);

			if (StringUtils.isNotNullNotEmptyNotWhiteSpace(criteria.getStreetAddress())) {
				select.addCriteria(new MatchCriteria(address_tab,STREET_NAME, PPATERN_MATCH_TYPE, criteria.getStreetAddress().toUpperCase()));
			}

			if (StringUtils.isNotNullNotEmptyNotWhiteSpace(criteria.getCity())) {
				select.addCriteria(new MatchCriteria(address_tab, CITY,
						MatchCriteria.EQUALS, criteria.getCity().toUpperCase()));
			}

			if (StringUtils.isNotNullNotEmptyNotWhiteSpace(criteria
					.getZipCode())) {
				select.addCriteria(new MatchCriteria(address_tab,POSTAL_CODE, MatchCriteria.LIKE, criteria.getZipCode()+"%"));

			}

			if (StringUtils.isNotNullNotEmptyNotWhiteSpace(criteria.getState())) {
				select.addCriteria(new MatchCriteria(address_tab,
						STATE_OR_PROV, MatchCriteria.EQUALS, criteria
								.getState().toUpperCase()));
			}

			select.addCriteria(new MatchCriteria(cons_addr_tab, ADDRESS_ROLE, MatchCriteria.EQUALS,SERVICEADDRESS));
		}
	}


	/**
	 * Helper method to add where clause for customer table cols
	 * @param criteria
	 * @param select
	 * @param consumer_tab
	 */
	private static void addCustomerWhere(SearchCriteria criteria,
			SelectQuery select, Table consumer_tab, Table order_tab) {
		if (StringUtils.isNotNullNotEmptyNotWhiteSpace(criteria.getFirstName())) {
			select.addCriteria(new MatchCriteria(consumer_tab, FIRSTNAME, MatchCriteria.LIKE,criteria.getFirstName().toUpperCase()));
		}

		if (StringUtils.isNotNullNotEmptyNotWhiteSpace(criteria.getLastName())) {
			select.addCriteria(new MatchCriteria(consumer_tab, LASTNAME, MatchCriteria.LIKE,criteria.getLastName().toUpperCase()));
		}

		if (StringUtils.isNotNullNotEmptyNotWhiteSpace(criteria.getLastSSN())) {
			select.addCriteria(new MatchCriteria(consumer_tab, SSN_LAST_FOUR,	MatchCriteria.EQUALS, criteria.getLastSSN()));
		}

		if (StringUtils.isNotNullNotEmptyNotWhiteSpace(criteria.getCustomerNo())) {
			select.addCriteria(new MatchCriteria(consumer_tab, A_CUST_NUM,	MatchCriteria.EQUALS, criteria.getCustomerNo()));
		}

		if (StringUtils.isNotNullNotEmptyNotWhiteSpace(criteria.getPhoneNo())) {

			Criteria hp = new MatchCriteria(consumer_tab, HOME_PHONE_VALUE, MatchCriteria.EQUALS, criteria.getPhoneNo());
			Criteria wp = new MatchCriteria(consumer_tab, WORK_PHONE_VALUE, MatchCriteria.EQUALS, criteria.getPhoneNo());
			Criteria cp = new MatchCriteria(consumer_tab, CELL_PHONE_VALUE, MatchCriteria.EQUALS, criteria.getPhoneNo());
			Criteria bpc = new MatchCriteria(consumer_tab, BEST_PHONE_CONTACT, MatchCriteria.EQUALS, criteria.getPhoneNo());
			Criteria sp = new MatchCriteria(consumer_tab, SECOND_PHONE, MatchCriteria.EQUALS, criteria.getPhoneNo());
			select.addCriteria(new OR(hp, new OR(wp,new OR(cp, new OR(bpc, sp)))));
		}

		if (StringUtils.isNotNullNotEmptyNotWhiteSpace(criteria.getEmailId())) {
			Criteria hemail = new MatchCriteria(consumer_tab, HOME_EMAIL_VALUE, MatchCriteria.EQUALS,criteria.getEmailId());
			Criteria wemail = new MatchCriteria(consumer_tab, WORK_EMAIL_VALUE, MatchCriteria.EQUALS,criteria.getEmailId());
			select.addCriteria(new OR(hemail,wemail));
		}

		select.addJoin(order_tab, "cust_ext_id", consumer_tab, "external_id");
	}

	/**
	 * Helper method to check search criteria contains address table cols for search
	 * @param criteria
	 * @return
	 */
	private static Boolean isAddressFieldsExist(SearchCriteria criteria){
		Boolean exist = Boolean.FALSE;
		if(StringUtils.isNotNullNotEmptyNotWhiteSpace(criteria.getCity()) ||
				StringUtils.isNotNullNotEmptyNotWhiteSpace(criteria.getState()) ||
				StringUtils.isNotNullNotEmptyNotWhiteSpace(criteria.getStreetAddress()) ||
				StringUtils.isNotNullNotEmptyNotWhiteSpace(criteria.getZipCode())){
			exist = Boolean.TRUE;
		}
		logger.debug("SearchCriteria inlcudes address fields : " + exist);
		return exist;
	}

	public static void main(String[] args) {
	    SearchCriteria criteria = new SearchCriteria();

		// Search based on customer fields
		criteria.setFirstName("PRITESH");
		criteria.setLastName("PATEL");
		// criteria.setSsn("6589");
		//criteria.setCustomerNo("200007411");

		// Search based on address fields
		criteria.setStreetAddress("1965LONGHOLLOW");
		//criteria.setCity("GRAND RAPIDS");
		//criteria.setState("MI");
		criteria.setZipCode("30004");

		// Search based on Contact info
		criteria.setPhoneNo("4042025298");
		//criteria.setProviderId("27101896");

		String q = QueryBuilderUtil.generateSelectQuery(criteria,false);
		System.out.println(q);
//		String cq = QueryBuilderUtil.generateCountQuery(criteria);
//		System.out.println(cq);

	}
}

