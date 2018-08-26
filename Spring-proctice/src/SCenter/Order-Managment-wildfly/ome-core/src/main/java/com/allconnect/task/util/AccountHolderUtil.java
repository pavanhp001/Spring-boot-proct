package com.AL.task.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.AL.task.context.impl.OrchestrationContext;
import com.AL.V.beans.entity.AccountHolder;
import com.AL.vm.util.converter.mapper.AccountHolderMapper;
import com.AL.xml.v4.AccountHolderType;

public class AccountHolderUtil {
	
	public static final String lineItemAccountHolderMapKey = "lineItemAccountHolderMap";
	public static final String inputAccountHoldersKey = "inputAccountHolders";
	public static final String includeAccountHoldersKey = "includeAccountHolders";
	
	private static Logger logger = Logger.getLogger(AccountHolderUtil.class);

	/*
	 * This method will set all accountHolders Info and returns a 
	 * list of accountHolders to be persisted in database
	 */
	public static List<AccountHolder> setAccountHolderInfo(
			List<AccountHolder> savedAccountHolders,
			List<AccountHolderType> accountHolderTypes,
			OrchestrationContext params, long orderId) {
		//Map of lineItemExternalId and AccountHolder
		Map<Long, AccountHolder> lineItemAccountHolderMap = new HashMap<Long, AccountHolder>();
		List<AccountHolder> accountHolders = new ArrayList<AccountHolder>();
		for (AccountHolderType accountHolderType : accountHolderTypes) {
			AccountHolder accHolder = new AccountHolder();
			accHolder.setOrderExternalId(orderId);
			if (accountHolderType.getExternalId() != 0) {
				for (AccountHolder savedAccHolder : savedAccountHolders) {
					if (savedAccHolder.getId() == accountHolderType
							.getExternalId()) {
						accHolder = savedAccHolder;
						break;
					}
				}
			} 
			AccountHolderMapper.copyAccountHolderType(accountHolderType,
					accHolder);
			String liExternalIds = "";
			if (accountHolderType.getLineItemExternalIdList().size() > 0) {
				for (long lineItemExternalId : accountHolderType
						.getLineItemExternalIdList()) {
					lineItemAccountHolderMap.put(lineItemExternalId, accHolder);
					liExternalIds = lineItemExternalId + "," + liExternalIds;
				}
			}
			if ((accountHolderType.getExternalId() == 0) && (accountHolderType.getDtCustomer() == false)) {
				logger.info("Adding new accountHolder to lineItem="+liExternalIds);
			}
			accountHolders.add(accHolder);
		}
		logger.debug("input account holders size: " + accountHolders.size());
		params.add(lineItemAccountHolderMapKey, lineItemAccountHolderMap);
		return accountHolders;
	}
}
