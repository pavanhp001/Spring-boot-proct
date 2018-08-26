package com.A.vm.util.converter.marshall;

import java.util.List;

import org.apache.log4j.Logger;

import com.A.Vdao.util.OrderDataBean;
import com.A.xml.v4.OrderData;
import com.A.xml.v4.OrderSearch;

public class MarshallOrderSearch {

	private static final Logger logger = Logger.getLogger(MarshallOrderSearch.class);

	public static void marshallSearchResult(List<OrderDataBean> dataList,OrderSearch search){
		logger.info("Marshalling Order search results.");
		for(OrderDataBean bean : dataList){
			OrderData data = search.addNewSearchResult();
			data.setCustomerId(bean.getCustomerId());
			data.setOrderId(bean.getOrderId());
			data.setLineItemId(bean.getLineItemId());
			data.setProductId(bean.getProductId());
			data.setProductName(bean.getProductName());
			data.setProviderExternalId(bean.getProviderExternalId());
			data.setScheduledInstallDate(bean.getScheduledInstallDate());
			data.setOrderDate(bean.getOrderDate());
			data.setLineItemCreateDate(bean.getLineItemCreateDate());
		}
	}
}
