package com.A.Vdao.query;

import java.util.Calendar;

import org.jooq.SQLDialect;
import org.jooq.conf.RenderNameStyle;
import org.jooq.conf.Settings;
import org.jooq.conf.StatementType;
import org.jooq.impl.Factory;

import com.A.Vdao.dao.impl.SearchCriteria;


public class SearchQueryBuilder {

	public static String getCustomerSearchQuery(SearchCriteria criteria){
		return CustomerSearchBuilder.getCustSearchQuery(criteria,getFactory(criteria));
	}

	public static String getOrderSearchQuery(SearchCriteria criteria){
		return OrderSearchBuilder.getOrderSearchQuery(criteria, getFactory(criteria));
	}
	
	public static String getLocateCustomerSearchQuery(SearchCriteria criteria, boolean selectAllCustFields){
		return CustomerSearchBuilder.generateSelectQuery(criteria, getFactory(criteria), selectAllCustFields);
	}

	private static Factory getFactory(SearchCriteria criteria) {
		Factory create = new Factory(SQLDialect.POSTGRES);
		Settings s = create.getSettings();
		s.setRenderNameStyle(RenderNameStyle.AS_IS);
		s.setRenderFormatted(true);
		s.setStatementType(StatementType.STATIC_STATEMENT);
//		if(!StringUtils.isBlank(criteria.getStreetAddress())) {
//		    create.setData("ADDRESS_SEARCH", criteria.getStreetAddress());
//		}
		return create;
	}

	public static void main(String[] args) {
		SearchCriteria criteria = new SearchCriteria();
		criteria.setAgentId("jshaik");
		//criteria.setProviderId("27010360");
		//criteria.setDtPartnerId("26040");

		Calendar startCal = Calendar.getInstance();
		startCal.add(Calendar.DATE, -7);
		criteria.setOrderStarDate(startCal);


		Calendar endCal = Calendar.getInstance();
		endCal.add(Calendar.DATE, 0);
		criteria.setOrderEndDate(endCal);

		System.out.println(SearchQueryBuilder.getOrderSearchQuery(criteria));
		//System.out.println(SearchQueryBuilder.getCustomerSearchQuery(criteria));

//		SearchCriteria criteria = new SearchCriteria();
//
//		// Search based on customer fields
//		criteria.setFirstName("CHANDRA");
//		criteria.setLastName("SEKHAR");
//		// criteria.setSsn("6589");
//		//criteria.setCustomerNo("133505");
//
//		// Search based on address fields
//		//criteria.setStreetAddress("1965LONGHOLLOW");
//		//criteria.setCity("ALPHARETTA");
//		//criteria.setState("GA");
//		criteria.setZipCode("29201");
//
//		// Search based on Contact info
//		//criteria.setPhoneNo("4042025298");
//		//criteria.setProviderId("27101896");
//		System.out.println(SearchQueryBuilder.getCustomerSearchQuery(criteria));
	}

	public static String getAccountHolderSearchQuery(SearchCriteria criteria) {
		return AccountHolderSearchBuilder.getAcctHolderSearchQuery(criteria,getFactory(criteria));
	}

	public static String getCustomerSearchQuerywithAccntHolderDetails(SearchCriteria criteria) {
		return CustomerSearchBuilder.getCustSearchQuerywithAccntHolderDetails(criteria,getFactory(criteria));
	}

	public static String getOrderSearchQuerywithAccntHolderDetails(SearchCriteria criteria) {
		return OrderSearchBuilder.getOrderSearchQuerywithAccntHolderDetails(criteria,getFactory(criteria));
	}
}
