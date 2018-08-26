package com.A.Vdao.query;

import static org.jooq.impl.Factory.concat;
import static org.jooq.impl.Factory.fieldByName;
import static org.jooq.impl.Factory.tableByName;
import static org.jooq.impl.Factory.val;

import org.jooq.SelectQuery;
import org.jooq.impl.Factory;
import org.jooq.tools.StringUtils;

import com.A.Vdao.dao.impl.SearchCriteria;

public class AccountHolderSearchBuilder extends SearchBuilder {

	public static String getAcctHolderSearchQuery(SearchCriteria criteria,Factory create) {
		SelectQuery query = create.selectQuery();

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
		query.addFrom(tableByName(TAB_ACCT_HOLDER));

		// join
		query.addJoin(
				tableByName(TAB_LI),
				fieldByName(TAB_ACCT_HOLDER, C_ID).equal(
						fieldByName(TAB_LI, C_ACCT_HOLDER_ID)));
		query.addJoin(
				tableByName(TAB_ORDER),
				fieldByName(TAB_ACCT_HOLDER, C_ORDER_EXT_ID).equal(
						fieldByName(TAB_ORDER, EXTERNAL_ID)));
		query.addJoin(
				tableByName(TAB_CUST),
				fieldByName(TAB_ORDER, C_CUST_EXT_ID).equal(
						fieldByName(TAB_CUST, EXTERNAL_ID)));
		query.addJoin(
				tableByName(TAB_ADDR),
				fieldByName(TAB_LI, C_SRVC_ADDR_EXT_ID).equal(
						fieldByName(TAB_ADDR, EXTERNAL_ID)));
		query.addJoin(
				tableByName(TAB_LI_SR),
				fieldByName(TAB_LI, C_ID).equal(
						fieldByName(TAB_LI_SR, C_OM_LI_ID)));
		query.addJoin(
				tableByName(TAB_STAT_REC),
				fieldByName(TAB_LI_SR, C_HISTORIC_STATUS_ID).equal(
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
		query.addJoin(
				tableByName(TAB_CUST_ADDR),
				fieldByName(TAB_CUST, C_CONSUMER_ID).equal(
						fieldByName(TAB_CUST_ADDR, C_CONSUMER_ID)));
		
		whereClause(criteria, query);

		query.addConditions(fieldByName(TAB_OM_LI_ATTR, C_SOURCE)
				.equal(PROVIDER_NAME));
		query.addConditions(fieldByName(TAB_OM_LI_ATTR1, C_SOURCE)
				.equal(PRODUCT_NAME));
		query.addConditions(fieldByName(TAB_CUST_ADDR, C_ADDRESS_ROLE)
				.equal(SERVICE_ADDRESS));
		query.addConditions(fieldByName(TAB_STAT_REC, C_STATUS)
				.notIn(CANCELLED_REMOVED,SUBMIT_FAILED));
		
		SelectQuery query1 = create.selectQuery();
		
		query1.addSelect(
				fieldByName(TAB_LI_SR, C_LIST_ORDER).max(),
				fieldByName(TAB_LI, EXTERNAL_ID)
				);
		
		// from
		query1.addFrom(tableByName(TAB_ACCT_HOLDER));

		// join
		query1.addJoin(
				tableByName(TAB_LI),
				fieldByName(TAB_ACCT_HOLDER, C_ID).in(
						fieldByName(TAB_LI, C_ACCT_HOLDER_ID)));
		
		query1.addJoin(
				tableByName(TAB_LI_SR),
				fieldByName(TAB_LI, C_ID).equal(
						fieldByName(TAB_LI_SR, C_OM_LI_ID)));
		
		whereClause(criteria, query1);
		
		query1.addGroupBy(fieldByName(TAB_LI, EXTERNAL_ID));
		
		query.addConditions(fieldByName(COLUMN_CONCAT).in(query1));
		
		return query.getSQL();
		
		
	}

	private static void whereClause(SearchCriteria criteria, SelectQuery query) {
		if (!StringUtils.isBlank(criteria.getFirstName())) {
			query.addConditions(fieldByName(TAB_ACCT_HOLDER, C_FIRST_NAME).upper().like(
					criteria.getFirstName().toUpperCase()));
		}

		if (!StringUtils.isBlank(criteria.getLastName())) {
			query.addConditions(fieldByName(TAB_ACCT_HOLDER, C_LAST_NAME).upper().like(
					criteria.getLastName().toUpperCase()));
		}

		if (!StringUtils.isBlank(criteria.getPhoneNo())) {
			query.addConditions(fieldByName(TAB_ACCT_HOLDER, C_BEST_CONTACT)
					.equal(criteria.getPhoneNo()));
		}

		if (!StringUtils.isBlank(criteria.getLastSSN())) {
			query.addConditions(fieldByName(TAB_ACCT_HOLDER, C_ACCNT_SSN_LAST_FOUR)
					.equal(criteria.getLastSSN()));
		}

		if (!StringUtils.isBlank(criteria.getEmailId())) {
			query.addConditions(fieldByName(TAB_ACCT_HOLDER, C_BEST_EMAIL)
					.equalIgnoreCase(criteria.getEmailId()));
		}

		if (!StringUtils.isBlank(criteria.getProviderId()) && !criteria.getProviderId().equalsIgnoreCase("0")) {
		    query.addConditions(fieldByName(TAB_LI, C_PROVIDER_EXT_ID).equal(criteria.getProviderId()));
		}
	}

}
