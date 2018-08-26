package com.A.Vdao.dao;

import java.util.List;

import com.A.V.beans.entity.PhoneContactChannel;

public interface PhoneDao {

	/**
	 * @param em
	 *            Entity Manager
	 * @param phoneContactChannel
	 *            phone contact channel
	 */
	void persist(final PhoneContactChannel phoneContactChannel);

	/**
	 * @param em
	 *            Entity Manager
	 * @param phoneContactChannel
	 *            phone contact channel
	 */
	void remove(final PhoneContactChannel phoneContactChannel);

	/**
	 * @param em
	 *            Entity Manager
	 * @param phoneContactChannel
	 *            phone contact channel
	 */
	void merge(final PhoneContactChannel phoneContactChannel);

	List<PhoneContactChannel> findPhoneByCustomerId(Long id);

}
