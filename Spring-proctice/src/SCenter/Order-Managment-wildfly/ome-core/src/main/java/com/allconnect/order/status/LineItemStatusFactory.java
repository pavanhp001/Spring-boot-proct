package com.AL.order.status;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import com.AL.enums.LineItemStatus;
import com.AL.factory.StatusFactory;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.entity.StatusRecordBean;
import com.AL.V.beans.entity.User;
import com.AL.vm.service.OrderAgentService;

public enum LineItemStatusFactory {

	INSTANCE;

	public final String UPDATED_BY_PROVIDER = "updated by provider";

	public StatusRecordBean createProviderStatus(String agentId, String status) {

		List<String> reasons = new ArrayList<String>();
		reasons.add(UPDATED_BY_PROVIDER);
		StatusRecordBean srb = new StatusRecordBean();
		srb.setAgentExternalId(agentId);
		srb.setDateTimeStamp(Calendar.getInstance());
		srb.setReasons(reasons);
		srb.setStatus(status);

		return srb;

	}

	public StatusRecordBean createStatusRecordBean(final User agent,
			final LineItemStatus lineItemStatus) {
		Calendar calNow = Calendar.getInstance();
		StatusRecordBean statusRecord = new StatusRecordBean();
		statusRecord.setStatus(lineItemStatus.name());
		statusRecord.setAgentExternalId(agent.getUserLogin());
		statusRecord.setAgent(agent);
		statusRecord.setDateTimeStamp(calNow);

		return statusRecord;
	}

	public StatusRecordBean createNewStatus(final OrderAgentService agentService,
			final SalesOrder orderBean, final LineItemStatus liStatus,
			List<Integer> reasons, final User agent) {

		StatusRecordBean statusRecord = createStatusRecordBean(agent, liStatus);

		if (reasons == null) {
			statusRecord.setReasons(Collections.EMPTY_LIST);
		} else {

			List<String> reasonAsString = new ArrayList<String>();

			for (Integer reason : reasons) {
				reasonAsString.add(String.valueOf(reason));
			}

			statusRecord.setReasons(reasonAsString);
		}

		return statusRecord;

	}
	
	public StatusRecordBean createNewStatus(final OrderAgentService agentService,
			final SalesOrder orderBean, final String status,
			List<Integer> reasons, final User agent) {
		LineItemStatus liStatus = StatusFactory.INSTANCE.convert(status);
		return createNewStatus(agentService, orderBean, liStatus, reasons, agent);
	}
}
