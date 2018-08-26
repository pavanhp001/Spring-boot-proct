package com.A.vm.util.converter.marshall;

import java.util.List;

import org.apache.log4j.Logger;

import com.A.Vdao.util.CustomerDataBean;
import com.A.xml.v4.CustomerData;
import com.A.xml.v4.CustomerSearch;

public class MarshallCustomerSearch {

	private static final Logger logger = Logger.getLogger(MarshallCustomerSearch.class);

	public static void marshallSearchResult(List<CustomerDataBean> dataList,CustomerSearch search){
		logger.info("Marshalling customer search results.");
		for(CustomerDataBean bean : dataList){
			CustomerData data = search.addNewSearchResult();
			data.setExternalId(bean.getCustExtId());
			data.setFirstName(bean.getFirstName());
			data.setLastName(bean.getLastName());
			data.setAddress(bean.getAddress());
			data.setOrderId(bean.getOrderExtId());
			data.setAgentId(bean.getAgentId());
			data.setOrderDate(bean.getOrderDate());
		}
	}
}
