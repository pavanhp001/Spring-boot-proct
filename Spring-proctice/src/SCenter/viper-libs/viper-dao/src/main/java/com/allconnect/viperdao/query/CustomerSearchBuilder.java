package com.A.Vdao.query;

import static org.jooq.impl.Factory.concat;
import static org.jooq.impl.Factory.fieldByName;
import static org.jooq.impl.Factory.tableByName;
import static org.jooq.impl.Factory.val;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.jooq.JoinType;
import org.jooq.SelectQuery;
import org.jooq.impl.Factory;

import com.A.util.StringUtils;
import com.A.Vdao.dao.impl.SearchCriteria;

public class CustomerSearchBuilder extends SearchBuilder {

	private static final Logger logger = Logger.getLogger(CustomerSearchBuilder.class);
    private static final String ZERO = "0";
    
	public static String getCustSearchQuery(SearchCriteria criteria,Factory create) {
		SelectQuery query = create.selectQuery();

		// Distinct
		query.setDistinct(true);

		// selected fields
		query.addSelect(
				fieldByName(TAB_CUST, EXTERNAL_ID),
				fieldByName(TAB_CUST, C_FIRSTNAME),
				fieldByName(TAB_CUST, C_LASTNAME),
				concat(
					fieldByName(TAB_ADDR, C_STREET_NUM),
					val(" "),
					fieldByName(TAB_ADDR, C_STREET_NAME),
					val(" "),
					//add coalesce if address do not return in search
					fieldByName(TAB_ADDR, C_STREET_TYPE),
					val(", "),
					fieldByName(TAB_ADDR, C_CITY),
					val(", "),
					fieldByName(TAB_ADDR, C_STATE_OR_PROV),
					val(" "),
					fieldByName(TAB_ADDR, C_POSTAL_CODE)
					).as("ADDRESS"),
				fieldByName(TAB_ORDER, EXTERNAL_ID).as("ORDER_ID"),
				fieldByName(TAB_ORDER, C_AGENT_ID),
				fieldByName(TAB_ORDER, C_ORDER_DATE)

		);

		// from
		query.addFrom(tableByName(TAB_CUST));

		// join
		query.addJoin(
				tableByName(TAB_CUST_ADDR),
				fieldByName(TAB_CUST, C_CONSUMER_ID).equal(
						fieldByName(TAB_CUST_ADDR, C_CONSUMER_ID)));
		query.addJoin(
				tableByName(TAB_ADDR),
				fieldByName(TAB_CUST_ADDR, C_ADDRESS_ID).equal(
						fieldByName(TAB_ADDR, C_ADDRESS_ID)));
		query.addJoin(
				tableByName(TAB_ORDER),
				fieldByName(TAB_CUST, EXTERNAL_ID).equal(
						fieldByName(TAB_ORDER, C_CUST_EXT_ID)));

		//add lineitem and detail join if it is provider specific basic customer search
		if(criteria.getProviderId() != null && !criteria.getProviderId().equalsIgnoreCase("0") ) {
		    query.addJoin(tableByName(TAB_SO_LI), fieldByName(TAB_ORDER, C_ID).equal(fieldByName(TAB_SO_LI, C_OM_SALES_ORDER_ID)));
		    query.addJoin(tableByName(TAB_LI), fieldByName(TAB_SO_LI, C_LINEITEMS_ID).equal(fieldByName(TAB_LI, C_ID)));
		}

		// where

		if (!StringUtils.isBlank(criteria.getCustomerNo())) {
			query.addConditions(fieldByName(TAB_CUST, C_A_CUST_NUM)
					.equal(criteria.getCustomerNo()));
		} else {
			whereClause(criteria, query);
		}
		// order by
		query.addOrderBy(fieldByName(TAB_CUST, EXTERNAL_ID).desc());

		return query.getSQL();
	}

	/**
	 * 
	 * This query is built for searching customer and retrieving data of Account Holder Information
	 * 
	 * @param criteria
	 * @param factory
	 * @return
	 */
	public static String getCustSearchQuerywithAccntHolderDetails(SearchCriteria criteria, Factory factory) {
		SelectQuery query = factory.selectQuery();

		// Distinct
		query.setDistinct(true);

		// selected fields
		query.addSelect(
				fieldByName(TAB_CUST, EXTERNAL_ID),
				concat(
						fieldByName(TAB_CUST, C_FIRSTNAME),
						val(" "),
						fieldByName(TAB_CUST, C_LASTNAME)
						).as("CUSTOMER_NAME"),
				fieldByName("concat(CM_ADDRESS.STREET_NUM,' ',CM_ADDRESS.STREET_NAME,' ', "
						+ "CM_ADDRESS.STREET_TYPE,', ',CM_ADDRESS.CITY,', ',CM_ADDRESS.STATE_OR_PROV,' ',"
						+ "CM_ADDRESS.POSTAL_CODE)").as("ADDRESS"),
				fieldByName(TAB_ORDER, EXTERNAL_ID).as("ORDER_ID"),
				fieldByName(TAB_ORDER, C_AGENT_ID),
				fieldByName(TAB_LI, EXTERNAL_ID).as("LINE_ITEM_ID"),
				concat(
					fieldByName(TAB_ACCT_HOLDER, C_FIRST_NAME),
					val(" "),
					fieldByName(TAB_ACCT_HOLDER, C_LAST_NAME)
					).as("ACCOUNT_HOLDER_NAME"),
				fieldByName(TAB_ACCT_HOLDER, C_ID).as("ACCT_HOLDER_ID"),
				concat(
					fieldByName(TAB_OM_LI_ATTR, C_VALUE),
					val(", "),
					fieldByName(TAB_OM_LI_ATTR1, C_VALUE)
					).as("PRODUCT_DETAILS"),
				fieldByName(TAB_ACCT_HOLDER, C_ACCNT_SSN_LAST_FOUR),
				fieldByName(TAB_STAT_REC, C_STATUS),
				fieldByName(TAB_LI, C_PROVIDER_EXT_ID),
				fieldByName(TAB_CUST, C_SSN_LAST_FOUR).as("Customer_SSN")
		);
		
		
		// from
		query.addFrom(tableByName(TAB_CUST));

		// join
		query.addJoin(
				tableByName(TAB_CUST_ADDR),
				fieldByName(TAB_CUST, C_CONSUMER_ID).equal(
						fieldByName(TAB_CUST_ADDR, C_CONSUMER_ID)));
		query.addJoin(
				tableByName(TAB_ADDR),
				fieldByName(TAB_CUST_ADDR, C_ADDRESS_ID).equal(
						fieldByName(TAB_ADDR, C_ADDRESS_ID)));
		query.addJoin(
				tableByName(TAB_ORDER),
				fieldByName(TAB_CUST, EXTERNAL_ID).equal(
						fieldByName(TAB_ORDER, C_CUST_EXT_ID)));
	    query.addJoin(
	    		tableByName(TAB_SO_LI), 
	    		fieldByName(TAB_ORDER, C_ID).equal(
	    				fieldByName(TAB_SO_LI, C_OM_SALES_ORDER_ID)));
		query.addJoin(
				tableByName(TAB_LI), 
				fieldByName(TAB_SO_LI, C_LINEITEMS_ID).equal(
						fieldByName(TAB_LI, C_ID)));
		query.addJoin(tableByName(TAB_ACCT_HOLDER), JoinType.LEFT_OUTER_JOIN, fieldByName(TAB_LI, C_ACCT_HOLDER_ID).equal(
				fieldByName(TAB_ACCT_HOLDER, C_ID)));

		query.addJoin(
				tableByName(TAB_STAT_REC),
				fieldByName(TAB_LI, C_CURRENTSTATUS_ID).equal(
						fieldByName(TAB_STAT_REC, C_ID)));
		query.addJoin(
				tableByName(TAB_OM_LI_OM_LI_ATTR),
				fieldByName(TAB_LI, C_ID).equal(
						fieldByName(TAB_OM_LI_OM_LI_ATTR, C_OM_LI_ID)));
		query.addJoin(
				tableByName(TAB_OM_LI_ATTR),
				fieldByName(TAB_OM_LI_OM_LI_ATTR, C_LI_ATTR_ID).equal(
						fieldByName(TAB_OM_LI_ATTR, C_ID)));
		query.addJoin(
				tableByName(TAB_OM_LI_OM_LI_ATTR).as(TAB_OM_LI_OM_LI_ATTR1),
				fieldByName(TAB_LI, C_ID).equal(
						fieldByName(TAB_OM_LI_OM_LI_ATTR1, C_OM_LI_ID)));
		query.addJoin(
				tableByName(TAB_OM_LI_ATTR).as(TAB_OM_LI_ATTR1),
				fieldByName(TAB_OM_LI_OM_LI_ATTR1, C_LI_ATTR_ID).equal(
						fieldByName(TAB_OM_LI_ATTR1, C_ID)));
		
		whereClause(criteria, query);

		query.addConditions(fieldByName(TAB_OM_LI_ATTR, C_SOURCE)
				.equal(PROVIDER_NAME));
		query.addConditions(fieldByName(TAB_OM_LI_ATTR1, C_SOURCE)
				.equal(PRODUCT_NAME));
		query.addConditions(fieldByName(TAB_STAT_REC, C_STATUS)
				.notIn(CANCELLED_REMOVED, SUBMIT_FAILED));
		

		
		SelectQuery query1 = factory.selectQuery();
		
		query1.addSelect(fieldByName(TAB_LI, EXTERNAL_ID));
		
		// from
		query1.addFrom(tableByName(TAB_CUST));

		// join
		query1.addJoin(
				tableByName(TAB_CUST_ADDR),
				fieldByName(TAB_CUST, C_CONSUMER_ID).equal(
						fieldByName(TAB_CUST_ADDR, C_CONSUMER_ID)));
		query1.addJoin(
				tableByName(TAB_ADDR),
				fieldByName(TAB_CUST_ADDR, C_ADDRESS_ID).equal(
						fieldByName(TAB_ADDR, C_ADDRESS_ID)));
		query1.addJoin(
				tableByName(TAB_ORDER),
				fieldByName(TAB_CUST, EXTERNAL_ID).equal(
						fieldByName(TAB_ORDER, C_CUST_EXT_ID)));
		query1.addJoin(
	    		tableByName(TAB_SO_LI), 
	    		fieldByName(TAB_ORDER, C_ID).equal(
	    				fieldByName(TAB_SO_LI, C_OM_SALES_ORDER_ID)));
		query1.addJoin(
				tableByName(TAB_LI), 
				fieldByName(TAB_SO_LI, C_LINEITEMS_ID).equal(
						fieldByName(TAB_LI, C_ID)));

		query1.addJoin(
				tableByName(TAB_STAT_REC),
				fieldByName(TAB_LI, C_CURRENTSTATUS_ID).equal(
						fieldByName(TAB_STAT_REC, C_ID)));
		
		whereClause(criteria, query1);
		
		query1.addGroupBy(fieldByName(TAB_LI, EXTERNAL_ID));
		
		query.addConditions(fieldByName(TAB_LI, EXTERNAL_ID).in(query1));
		
		if(!StringUtils.isBlank(criteria.getProviderId()) && !criteria.getProviderId().equalsIgnoreCase(ZERO)){
			return query.getSQL();
		} else {
			SelectQuery unionQuery = (SelectQuery) factory.selectQuery(); 
			unionQuery = buildQueryForOrders(criteria, unionQuery, factory);
			return query.union(unionQuery).getSQL();
		}
	}

	/**
	 * This method is used to fetch Orders which are not having LineItems.
	 * @param criteria
	 * @param unionQuery
	 * @param factory 
	 * @return SelectQuery
	 */
	private static SelectQuery buildQueryForOrders(SearchCriteria criteria, SelectQuery unionQuery, Factory factory) {
		
		// Distinct
		unionQuery.setDistinct(true);

		// selected fields
		unionQuery.addSelect(
			fieldByName(TAB_CUST, EXTERNAL_ID),
			concat(
					fieldByName(TAB_CUST, C_FIRSTNAME),
					val(" "),
					fieldByName(TAB_CUST, C_LASTNAME)
					).as("CUSTOMER_NAME"),
			fieldByName("concat(CM_ADDRESS.STREET_NUM,' ',CM_ADDRESS.STREET_NAME,' ', "
					+ "CM_ADDRESS.STREET_TYPE,', ',CM_ADDRESS.CITY,', ',CM_ADDRESS.STATE_OR_PROV,' ',"
					+ "CM_ADDRESS.POSTAL_CODE)").as("ADDRESS"),
			fieldByName(TAB_ORDER, EXTERNAL_ID).as("ORDER_ID"),
			fieldByName(TAB_ORDER, C_AGENT_ID),
			val(0).as("LINE_ITEM_ID"),
			val("").as("ACCOUNT_HOLDER_NAME"),
			val(0).as("ACCT_HOLDER_ID"),
			val("").as("PRODUCT_DETAILS"),
			val(""),
			val(""),
			val(""),
			fieldByName(TAB_CUST, C_SSN_LAST_FOUR).as("Customer_SSN")
		);
		
		
		// from
		unionQuery.addFrom(tableByName(TAB_CUST));

		// join
		unionQuery.addJoin(
				tableByName(TAB_CUST_ADDR),
				fieldByName(TAB_CUST, C_CONSUMER_ID).equal(
						fieldByName(TAB_CUST_ADDR, C_CONSUMER_ID)));
		unionQuery.addJoin(
				tableByName(TAB_ADDR),
				fieldByName(TAB_CUST_ADDR, C_ADDRESS_ID).equal(
						fieldByName(TAB_ADDR, C_ADDRESS_ID)));
		unionQuery.addJoin(
				tableByName(TAB_ORDER),
				fieldByName(TAB_CUST, EXTERNAL_ID).equal(
						fieldByName(TAB_ORDER, C_CUST_EXT_ID)));
		whereClause(criteria, unionQuery);
		
		SelectQuery conditionQuery = factory.selectQuery();
		conditionQuery.addSelect(fieldByName(TAB_SO_LI, C_OM_SALES_ORDER_ID));
		conditionQuery.addFrom(tableByName(TAB_SO_LI));
		conditionQuery.addConditions(fieldByName(TAB_ORDER, C_ID).equal(fieldByName(TAB_SO_LI, C_OM_SALES_ORDER_ID)));
		
		unionQuery.addConditions(Factory.notExists(conditionQuery));
		
		return unionQuery;
	}

	/**
	 * @param criteria
	 * @param query
	 */
	private static void whereClause(SearchCriteria criteria, SelectQuery query) {
		if (!StringUtils.isBlank(criteria.getFirstName())) {
			query.addConditions(fieldByName(TAB_CUST, C_FIRSTNAME).like(
					criteria.getFirstName().toUpperCase()));
		}

		if (!StringUtils.isBlank(criteria.getLastName())) {
			query.addConditions(fieldByName(TAB_CUST, C_LASTNAME).like(
					criteria.getLastName().toUpperCase()));
		}

		if (!StringUtils.isBlank(criteria.getZipCode())) {
			query.addConditions(fieldByName(TAB_ADDR, C_POSTAL_CODE).like(
					criteria.getZipCode() + "%"));
		}

		if (!StringUtils.isBlank(criteria.getPhoneNo())) {
			query.addConditions(fieldByName(TAB_CUST, C_BEST_PHONE_CONTACT)
					.equal(criteria.getPhoneNo()).or(
							fieldByName(TAB_CUST,C_HOME_PHONE_VALUE).equal(criteria.getPhoneNo()).or(
								fieldByName(TAB_CUST,C_CELL_PHONE_VALUE).equal(criteria.getPhoneNo()).or(
									fieldByName(TAB_CUST,C_WORK_PHONE_VALUE).equal(criteria.getPhoneNo()).or(
										fieldByName(TAB_CUST,C_SECOND_PHONE).equal(criteria.getPhoneNo())
										)
									)
								)
						)
					);
		}

		if (!StringUtils.isBlank(criteria.getLastSSN())) {
			query.addConditions(fieldByName(TAB_CUST, C_SSN_LAST_FOUR)
					.equal(criteria.getLastSSN()));
		}

		if (!StringUtils.isBlank(criteria.getEmailId())) {
			query.addConditions(fieldByName(TAB_CUST, C_BEST_EMAIL_CONTACT)
					.equalIgnoreCase(criteria.getEmailId()));
		}

		if (!StringUtils.isBlank(criteria.getState())) {
			query.addConditions(fieldByName(TAB_ADDR, C_STATE_OR_PROV)
					.equal(criteria.getState().toUpperCase()));
		}

		if(!StringUtils.isBlank(criteria.getStreetAddress())) {
		    //query.addConditions(new PGPosixCustomCondition());
		    query.addConditions(fieldByName(TAB_ADDR,C_STREET_NAME).likeRegex(criteria.getStreetAddress().toUpperCase()));
		}

		if (!StringUtils.isBlank(criteria.getProviderId()) && !criteria.getProviderId().equalsIgnoreCase("0")) {
		  
			
			query.addConditions(fieldByName(TAB_LI, C_PROVIDER_EXT_ID).equal(criteria.getProviderId()));
		}

		query.addConditions(fieldByName(TAB_CUST_ADDR, C_ADDRESS_ROLE)
				.equal(SERVICE_ADDRESS));
	}
	
	/**
	 * Generates select query based on search criteria
	 * @param criteria
	 * @return
	 */
	public static String generateSelectQuery(SearchCriteria criteria, Factory create, boolean selectAllCustFields) {

		logger.debug("Building select query for searchCustomer with criteria : " +criteria.toString());
		
		// base query
		SelectQuery select = create.selectQuery();

		// Distinct
		select.setDistinct(true);
		select.addFrom(tableByName(TAB_CUST));

		// selected fields
		if (selectAllCustFields) {
			select.addSelect(fieldByName(TAB_CUST, "*"));
		}

		addAddressTablesAndWhere(criteria, select);

		addCustomerWhere(criteria, select);
		int i = NumberUtils.toInt(criteria.getProviderId());
		if(i > 0) {
			addOrderTablesAndWhere(criteria,select);
		}

		if (StringUtils.isNotNullNotEmptyNotWhiteSpace(criteria.getPhoneNo())) {
			// base account holder query
			SelectQuery selectAcctHolder = create.selectQuery();
			// Distinct
			selectAcctHolder.setDistinct(true);

			// selected fields
			if (selectAllCustFields) {
				selectAcctHolder.addSelect(fieldByName(TAB_CUST, "*"));
			} 
			
			selectAcctHolder.addFrom(tableByName(TAB_ACCT_HOLDER));
			selectAcctHolder.addConditions(fieldByName(TAB_ACCT_HOLDER, C_BEST_CONTACT).equal(criteria.getPhoneNo()));
			selectAcctHolder.addJoin(tableByName(TAB_ORDER), fieldByName(TAB_ACCT_HOLDER, C_ORDER_EXT_ID).equal(
					fieldByName(TAB_ORDER, EXTERNAL_ID)));
			selectAcctHolder.addJoin(tableByName(TAB_CUST), fieldByName(TAB_ORDER, "cust_ext_id").equal(
					fieldByName(TAB_CUST, EXTERNAL_ID)));
			select.union(selectAcctHolder).getSQL();
			
			logger.debug(select.union(selectAcctHolder).getSQL().replaceAll("\"", ""));
			
			return select.union(selectAcctHolder).getSQL().replaceAll("\"", "");
		}else{
			select.addOrderBy(fieldByName(TAB_CUST, EXTERNAL_ID).desc());
		}
		logger.debug(select.getSQL().replaceAll("\"", ""));

		return select.getSQL().replaceAll("\"", "");
	}
	
	/**
	 * @param criteria
	 * @param select
	 */
	private static void addAddressTablesAndWhere(SearchCriteria criteria, SelectQuery select) {
		
		if (isAddressFieldsExist(criteria)) {
			// table joins
			select.addJoin(tableByName(TAB_CUST_ADDR),fieldByName(TAB_CUST, C_CONSUMER_ID).equal(
							fieldByName(TAB_CUST_ADDR, C_CONSUMER_ID)));
			
			select.addJoin(tableByName(TAB_ADDR),fieldByName(TAB_CUST_ADDR, C_ADDRESS_ID).equal(
					fieldByName(TAB_ADDR, C_ADDRESS_ID)));

			if (StringUtils.isNotNullNotEmptyNotWhiteSpace(criteria.getStreetAddress())) {
				select.addConditions(fieldByName(TAB_ADDR,C_STREET_NAME).likeRegex(criteria.getStreetAddress().toUpperCase()));
			}

			if (StringUtils.isNotNullNotEmptyNotWhiteSpace(criteria.getCity())) {
				select.addConditions(fieldByName(TAB_ADDR, C_CITY).equal(criteria.getCity().toUpperCase()));
			}

			if (StringUtils.isNotNullNotEmptyNotWhiteSpace(criteria.getZipCode())) {
				select.addConditions(fieldByName(TAB_ADDR, C_POSTAL_CODE).like(criteria.getZipCode() + "%"));
			}

			if (StringUtils.isNotNullNotEmptyNotWhiteSpace(criteria.getState())) {
				select.addConditions(fieldByName(TAB_ADDR, C_STATE_OR_PROV).equal(criteria.getState().toUpperCase()));
			}

			select.addConditions(fieldByName(TAB_CUST_ADDR, C_ADDRESS_ROLE).equal(SERVICE_ADDRESS));
		}
	}
	
	/**
	 * @param criteria
	 * @param select
	 */
	private static void addCustomerWhere(SearchCriteria criteria, SelectQuery select) {
		if (StringUtils.isNotNullNotEmptyNotWhiteSpace(criteria.getFirstName())) {
			select.addConditions(fieldByName(TAB_CUST, C_FIRSTNAME).like(criteria.getFirstName().toUpperCase()));
		}

		if (StringUtils.isNotNullNotEmptyNotWhiteSpace(criteria.getLastName())) {
			select.addConditions(fieldByName(TAB_CUST, C_LASTNAME).like(criteria.getLastName().toUpperCase()));
		}

		if (StringUtils.isNotNullNotEmptyNotWhiteSpace(criteria.getLastSSN())) {
			select.addConditions(fieldByName(TAB_CUST, C_SSN_LAST_FOUR).equal(criteria.getLastSSN()));
		}

		if (StringUtils.isNotNullNotEmptyNotWhiteSpace(criteria.getCustomerNo())) {
			select.addConditions(fieldByName(TAB_CUST, C_A_CUST_NUM).equal(criteria.getCustomerNo()));
		}

		if (StringUtils.isNotNullNotEmptyNotWhiteSpace(criteria.getPhoneNo())) {
			
			select.addConditions(fieldByName(TAB_CUST, C_BEST_PHONE_CONTACT)
					.equal(criteria.getPhoneNo()).or(fieldByName(TAB_CUST,C_HOME_PHONE_VALUE).equal(criteria.getPhoneNo()).or(
								fieldByName(TAB_CUST,C_CELL_PHONE_VALUE).equal(criteria.getPhoneNo()).or(
									fieldByName(TAB_CUST,C_WORK_PHONE_VALUE).equal(criteria.getPhoneNo()).or(
										fieldByName(TAB_CUST,C_SECOND_PHONE).equal(criteria.getPhoneNo()))))));
		}

		if (StringUtils.isNotNullNotEmptyNotWhiteSpace(criteria.getEmailId())) {
			select.addConditions(fieldByName(TAB_CUST, C_HOME_EMAIL_VALUE)
					.equal(criteria.getEmailId()).or(fieldByName(TAB_CUST,C_WORK_EMAIL_VALUE).equal(criteria.getEmailId())));			
			
		}

		select.addJoin(tableByName(TAB_ORDER),fieldByName(TAB_CUST, EXTERNAL_ID).equal(
				fieldByName(TAB_ORDER, C_CUST_EXT_ID)));
	}
	
	/**
	 * @param criteria
	 * @param select
	 */
	private static void addOrderTablesAndWhere(SearchCriteria criteria,SelectQuery select) {
		select.addFrom(tableByName(TAB_LI));
		select.addJoin(tableByName(TAB_SO_LI),fieldByName(TAB_LI, C_ID)
				.equal(fieldByName(TAB_SO_LI, C_LINEITEMS_ID)));

		select.addConditions(fieldByName(TAB_LI, C_PROVIDER_EXT_ID).equal(criteria.getProviderId().trim()));
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

}
