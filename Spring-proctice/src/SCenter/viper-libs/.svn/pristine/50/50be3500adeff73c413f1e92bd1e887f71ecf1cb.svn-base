package com.A.Vdao.dao;

import java.util.Set;

import com.A.V.beans.entity.Consumer;
import com.A.V.beans.entity.CustomerPaymentEvent;

public interface PaymentEventDao {

	/**
	 * Find consumer by external id
	 */
	public Set<CustomerPaymentEvent> findCustomerPaymentEventByCustomerId(
			Long id);

	/**
	 * @param em
	 *            Entity Manager
	 * @param CustomerPaymentEvent
	 *            phone contact channel
	 */
	public void persist(final Consumer consumerBean, final CustomerPaymentEvent customerPaymentEvent);

	/**
	 * @param em
	 *            Entity Manager
	 * @param CustomerPaymentEvent
	 *            phone contact channel
	 */
	public void remove(final CustomerPaymentEvent customerPaymentEvent);
	
	public void remove(final Set<CustomerPaymentEvent> customerPaymentEvent);

	public void merge(final Consumer consumerBean, final Set<CustomerPaymentEvent> customerPaymentEventList);

	public void persist(final Consumer consumerBean, 
			final Set<CustomerPaymentEvent> customerPaymentEventList);

	/**
	 * @param em
	 *            Entity Manager
	 * @param CustomerPaymentEvent
	 *            phone contact channel
	 */
	public void merge(final Consumer consumerBean, final CustomerPaymentEvent customerPaymentEvent);
	
	public void persistLineItemPaymentEvents(Set<CustomerPaymentEvent> customPaymentEvents);
	
	public Set<CustomerPaymentEvent> findPaymentEventsByLineItemId(
			Long id);

}
