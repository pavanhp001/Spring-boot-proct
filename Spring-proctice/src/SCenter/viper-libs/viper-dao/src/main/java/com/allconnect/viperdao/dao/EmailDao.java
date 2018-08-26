package com.A.Vdao.dao;

import java.util.List;

import com.A.V.beans.entity.EMailContactChannel;

public interface EmailDao {

	List<EMailContactChannel> findEmailByCustomerId(Long id);

	/**
	 * @param em
	 *            Entity Manager
	 * @param email
	 *            Email Contact Bean that will be updated
	 */
	void persist(final EMailContactChannel email);

	/**
	 * @param em
	 *            Entity Manager
	 * @param email
	 *            Email Contact Bean that will be updated
	 */
	void remove(final EMailContactChannel email);

	/**
	 * @param em
	 *            Entity Manager
	 * @param email
	 *            Email Contact Bean that will be updated
	 */
	void merge(final EMailContactChannel email);

}
