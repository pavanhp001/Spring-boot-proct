package com.A.Vdao.dao;

import java.util.List;
import java.util.Map;

import com.A.V.beans.entity.AddressBean;
import com.A.V.beans.entity.Consumer;
import com.A.Vdao.dao.impl.SearchCriteria;

public interface CustomerDao {

	void saveConsumerAndAddress(Consumer consumer);

	List<AddressBean> addCustomerAddress(Consumer consumerBean);

	void deleteCustomer(Long externalId);

	void updateCustomer(Consumer consumer);

	String getNextCustomerNo();

	Consumer findCustomerByExternalId(long externalId);

	Consumer findCustomerByTaskLogId(long taskLogId);

	Consumer findCustomerByConfirmationNumber(String confirmationNumber);

	List<Consumer> findCustomerByContactInfo(Map<String,String> contactInfo, int offSet, int totalRows);

	public List<Consumer> locateCustomer(SearchCriteria searchCriteria, int offSet, int totalRows);
}
