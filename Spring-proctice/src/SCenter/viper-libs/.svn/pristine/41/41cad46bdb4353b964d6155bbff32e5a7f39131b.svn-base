package com.A.Vdao.dao;

 

 
import com.A.V.beans.entity.BillingInformation;

public interface BillingDao {

	   void merge(  final BillingInformation billingBean );
	  	/**
		 * @param em
		 *            Entity Manager
		 * @param billingBean
		 *            Billing Information Bean
		 */
		  void persist( final BillingInformation billingBean);

		/**
		 * @param statusRecordHistoryList
		 *            Status history list with reasons to be removed
		 * @param em
		 *            Entity Manager that will perform remove operation
		 */
		public void remove(final BillingInformation billingInformation ) ;
		public long getNextExternalId();
	
}
