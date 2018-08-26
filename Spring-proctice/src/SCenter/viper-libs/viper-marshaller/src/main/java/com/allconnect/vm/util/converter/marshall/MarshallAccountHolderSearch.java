package com.A.vm.util.converter.marshall;

import java.util.List;

import org.apache.log4j.Logger;

import com.A.Vdao.util.AccountHolderDataBean;
import com.A.xml.v4.AccountHolderData;
import com.A.xml.v4.AccountHolderSearch;

public class MarshallAccountHolderSearch {

	private static final Logger logger = Logger.getLogger(MarshallAccountHolderSearch.class);

	public static void marshallSearchResult(List<AccountHolderDataBean> dataList, AccountHolderSearch search){
		logger.info("Marshalling Account Holder search results.");
		for(AccountHolderDataBean bean : dataList){
			AccountHolderData data = search.addNewSearchResult();
			
			data.setCustomerId(bean.getCustomerId());
			data.setCustomerName(bean.getCustomerName());
			if((bean.getAddress() != "null") && !(bean.getAddress()).isEmpty()){
				data.setAddress(bean.getAddress());
			} else {
				data.setAddress("");
			}
			data.setOrderId(bean.getOrderId());
			data.setAgentId(bean.getAgentId());
			data.setLineItemId(bean.getLineItemId());
			if((bean.getAccountName() != "null") && !(bean.getAccountName()).isEmpty()){
				data.setAccountName(bean.getAccountName());
			} else {
				data.setAccountName("");
			}
			if((bean.getAcctHolderId() != "null") && !(bean.getAcctHolderId()).isEmpty()){
				data.setAccntHolderId(bean.getAcctHolderId());
			} else {
				data.setAccntHolderId("");
			}
			data.setProductName(bean.getProductDetails());
			if((bean.getCustomerSsn() != "null") && !(bean.getCustomerSsn()).isEmpty()){
				data.setCustomerSsn(bean.getCustomerSsn());
			} else {
				data.setCustomerSsn("");
			}
			data.setStatus(bean.getStatus());
			data.setProviderId(bean.getProviderId());
			if((bean.getSsn() != "null") && !(bean.getSsn()).isEmpty()){
				data.setSsn(bean.getSsn());
			} else {
				data.setSsn("");
			}
		}
	}
}
