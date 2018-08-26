package com.AL.task.broadcast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.SalesOrder;


public enum AddProviderIdToContextForBroadcast {

	INSTANCE;

	private static final Logger logger = Logger.getLogger(AddProviderIdToContextForBroadcast.class);
	private static final String BROADCAST_DELIMITER = "#";
	private static final String JMS_PROVIDER_NAME = "providers";

	@SuppressWarnings("unchecked")
	public   void execute(final OrchestrationContext params,
			final SalesOrder so) {
		logger.info("start execute");
		if ((so==null) || (params==null)) {
			return;
		}

		Object headerInfo = params.get(TaskContextParamEnum.headerInfo.name());

		Map<String, String> map = null;
		if (headerInfo != null) {
			map = (Map<String, String>) headerInfo;
		} else {
			map = new HashMap<String, String>();
		}

		List<LineItem> liCollection = so.getLineItems();
		StringBuilder sb = new StringBuilder();
		List<String> uniqueLiExtIds = new ArrayList<String>();
		for (LineItem li : liCollection) {
			
			if(!uniqueLiExtIds.contains(li.getProviderExternalId())){
				sb.append(BROADCAST_DELIMITER).append(li.getProviderExternalId());
				uniqueLiExtIds.add(li.getProviderExternalId());
			}
		}

		if (sb.toString().length() > 0) {
			sb.append(BROADCAST_DELIMITER);
		}

		map.put(JMS_PROVIDER_NAME, sb.toString());
		logger.debug(JMS_PROVIDER_NAME+"="+sb.toString());

		params.add(TaskContextParamEnum.headerInfo.name(), map);
		logger.info("end execute");
	}
}
