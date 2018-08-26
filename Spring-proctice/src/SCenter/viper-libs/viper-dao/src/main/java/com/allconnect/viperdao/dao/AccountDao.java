package com.A.Vdao.dao;

 



import com.A.V.beans.entity.Account;

public interface AccountDao {

	   void merge(  final Account account );
	  	/**
		 * @param em
		 *            Entity Manager
		 * @param billingBean
		 *            Billing Information Bean
		 */
		  void persist( final Account account);

		/**
		 * @param statusRecordHistoryList
		 *            Status history list with reasons to be removed
		 * @param em
		 *            Entity Manager that will perform remove operation
		 */
		public void remove(final Account account ) ;
		public long getNextExternalId();
	
}
