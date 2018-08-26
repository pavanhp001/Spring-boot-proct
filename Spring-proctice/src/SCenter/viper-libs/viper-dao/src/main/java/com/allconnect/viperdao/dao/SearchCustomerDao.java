package com.A.Vdao.dao;

import java.util.Map;

import com.A.Vdao.dao.impl.SearchCriteria;

public interface SearchCustomerDao {

	public Map<String,Object> searchCustomer(SearchCriteria criteria,int offset,int totalRows);

	public Map<String,Object> searchOrderByCustomer(SearchCriteria criteria);

	public Map<String,Object> advanceOrderSearch(SearchCriteria criteria);

	public Map<String, Object> searchAccountHolder(SearchCriteria criteria);

	public Map<String, Object> searchCustomerDetails(SearchCriteria criteria);

	public Map<String, Object> searchOrderByAccntHolderDetails(SearchCriteria criteria);
}
