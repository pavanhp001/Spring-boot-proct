package com.A.V.dao;

import com.A.Vdao.dao.impl.SearchCriteria;
import com.A.Vdao.util.QueryBuilderUtil;

public class SearchCustomerDaoImplTest {

	public static void main(String[] args) {
		String sql1 = QueryBuilderUtil.generateSelectQuery(getCriteria(),true);
		System.out.println(sql1);

		//String sql2 = QueryBuilderUtil.generateCountQuery(getCriteria());
		//System.out.println(sql2);
	}

	public static SearchCriteria getCriteria(){
		SearchCriteria criteria = new SearchCriteria();
		//criteria.setFirstName("");
		//criteria.setLastName("patel");
		//criteria.setLastSSN("1111");
		criteria.setPhoneNo("7866604159");
		//criteria.setEmailId("test@test.com");
		//criteria.setStreetAddress("Beaver Ruin Rd");
		//criteria.setZipCode("30093");
		//criteria.setCity("norcross");
		//criteria.setState("ga");
		//criteria.setCustomerNo("123");
		//criteria.setProviderId("1");
		return criteria;
	}
}
