package com.A.Vdao.dao;

import java.util.List;

import com.A.V.beans.entity.AddressBean;

public interface AddressDao {

	void persist(final AddressBean addressBean);

	/**
	 * @param em
	 *            Entity Manager
	 * @param addressBean
	 *            Address Bean
	 */
	void merge(final AddressBean addressBean);

	/**
	 * @param em
	 *            Entity Manager
	 * @param addressBean
	 *            Address Bean
	 */
	void remove(final AddressBean addressBean);

	public long getNextExternalId();

	AddressBean findAddressById(String id);
	
    List<AddressBean> findAddressByCustomerId(String id);

}
