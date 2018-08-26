package com.A.Vdao.query;

import static org.jooq.impl.Factory.concat;
import static org.jooq.impl.Factory.fieldByName;
import static org.jooq.impl.Factory.tableByName;
import static org.jooq.impl.Factory.val;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.jooq.JoinType;
import org.jooq.SelectQuery;
import org.jooq.impl.Factory;
import org.jooq.tools.StringUtils;

import com.A.Vdao.dao.impl.SearchCriteria;

public class OrderSearchBuilder extends SearchBuilder{

    private static final String COMMA_SPACE = ", ";
    private static final String SPACE = " ";
    private static final String REFERRER_ID = "REFERRER_ID";
    private static final String END_DF = "yyyy-MM-dd 24:00:00";
    private static final String START_DF = "yyyy-MM-dd 00:00:00";
    private static final String ZERO = "0";

    public static String getOrderSearchQuery(SearchCriteria criteria, Factory create) {
	SelectQuery query = create.selectQuery();

	// Distinct
	query.setDistinct(true);

	// selected fields
	query.addSelect(
		fieldByName(TAB_CUST, EXTERNAL_ID),
		fieldByName(TAB_CUST, C_FIRSTNAME),
		fieldByName(TAB_CUST, C_LASTNAME),
		concat(
			fieldByName(TAB_ADDR, C_STREET_NUM), val(SPACE),
			fieldByName(TAB_ADDR, C_STREET_NAME), val(SPACE),
			//add coalesce if address do not return in search
			fieldByName(TAB_ADDR, C_STREET_TYPE), val(COMMA_SPACE),
			fieldByName(TAB_ADDR, C_CITY), val(COMMA_SPACE),
			fieldByName(TAB_ADDR, C_STATE_OR_PROV), val(SPACE),
			fieldByName(TAB_ADDR, C_POSTAL_CODE)).as("address"),
		fieldByName(TAB_ORDER, "EXTERNAL_ID").as("ORDER_ID"),
		fieldByName(TAB_ORDER, "AGENT_ID"),
		fieldByName(TAB_ORDER, "ORDER_DATE")
		);

	// from
	query.addFrom(tableByName(TAB_CUST));

	// join
	query.addJoin(tableByName(TAB_CUST_ADDR), fieldByName(TAB_CUST, C_CONSUMER_ID).equal(fieldByName(TAB_CUST_ADDR, C_CONSUMER_ID)));
	query.addJoin(tableByName(TAB_ADDR), fieldByName(TAB_CUST_ADDR, C_ADDRESS_ID).equal(fieldByName(TAB_ADDR, C_ADDRESS_ID)));
	query.addJoin(tableByName(TAB_ORDER), fieldByName(TAB_CUST, EXTERNAL_ID).equal(fieldByName(TAB_ORDER, C_CUST_EXT_ID)));

	//add lineitem and detail join if it is provider specific basic customer search
	if(criteria.getProviderId() != null && !criteria.getProviderId().equalsIgnoreCase(ZERO) ) {
	    query.addJoin(tableByName(TAB_SO_LI), fieldByName(TAB_ORDER, C_ID).equal(fieldByName(TAB_SO_LI, C_OM_SALES_ORDER_ID)));
	    query.addJoin(tableByName(TAB_LI), fieldByName(TAB_SO_LI, C_LINEITEMS_ID).equal(fieldByName(TAB_LI, C_ID)));
	}

	// Where
	whereClause(criteria, query);
	
	// order by
	query.addOrderBy(fieldByName(TAB_ORDER, C_ORDER_DATE).desc());

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
	public static String getOrderSearchQuerywithAccntHolderDetails(SearchCriteria criteria, Factory factory) {
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
		if (!StringUtils.isBlank(criteria.getAgentId())) {
		    query.addConditions(fieldByName(TAB_ORDER, "AGENT_ID").equalIgnoreCase(criteria.getAgentId()));
		}

		if (!StringUtils.isBlank(criteria.getProviderId()) && !criteria.getProviderId().equalsIgnoreCase(ZERO)) {
		    query.addConditions(fieldByName(TAB_LI, C_PROVIDER_EXT_ID).equal(criteria.getProviderId()));
		}

		if (!StringUtils.isBlank(criteria.getDtPartnerId())) {
		    query.addConditions(fieldByName(TAB_CUST, REFERRER_ID).equal(criteria.getDtPartnerId()));
		}

		SimpleDateFormat startDateFormat = new SimpleDateFormat(START_DF);
		SimpleDateFormat endDateFormat = new SimpleDateFormat(END_DF);

		Calendar orderStart = criteria.getOrderStarDate();
		Date startdate = orderStart.getTime();

		String startDate = startDateFormat.format(startdate);

		Calendar orderEnd = criteria.getOrderEndDate();
		Date enddate = orderEnd.getTime();
		String endDate = endDateFormat.format(enddate);

		query.addConditions(fieldByName(TAB_ORDER, C_ORDER_DATE).ge(startDate));
		query.addConditions(fieldByName(TAB_ORDER, C_ORDER_DATE).le(endDate));
		query.addConditions(fieldByName(TAB_CUST_ADDR, C_ADDRESS_ROLE).equal(SERVICE_ADDRESS));
	}
}
