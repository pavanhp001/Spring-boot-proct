package com.A.Vdao.dao;

import java.util.List;

import com.A.V.beans.entity.AccountHolder;

public interface AccountHolderDao {

	public List<AccountHolder> getAllAccountHoldersByOrderExternalId(long orderExtternalId);
	
	public void persist(AccountHolder accountHolder);
	
	public void persistAll(List<AccountHolder> accountHolders);
	
	public AccountHolder getAccountHolder(long externalId);
}
