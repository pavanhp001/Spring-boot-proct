package com.A.Vdao.dao;

import java.util.List;
import com.A.V.beans.entity.StatusRecordBean;

public interface StatusDao {

	void remove(final StatusRecordBean statusRecord);

	void remove(final List<StatusRecordBean> statusRecordHistoryList);

	public Boolean merge(final StatusRecordBean statusRecordBean);

	/**
	 * @param em
	 *            Entity Manager
	 * @param statusRecordBean
	 *            Status Record Bean
	 */
	public void merge(  final List<StatusRecordBean> statusRecordBeanList);

	/**
	 * @param em
	 *            Entity Manager
	 * @param statusRecordBean
	 *            Status Record Bean
	 */
	public void persist( final List<StatusRecordBean> statusRecordBeanList);

	/**
	 * @param em
	 *            Entity Manager
	 * @param statusRecordBean
	 *            Status Record Bean
	 */
	public void persist( final StatusRecordBean statusRecordBean);
}
