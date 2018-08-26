/**
 *
 */
package com.AL.ie.factory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.AL.enums.LineItemStatus;
import com.AL.enums.OrderStatus;
import com.AL.V.beans.entity.StatusRecordBean;
import com.AL.V.beans.entity.User;

/**
 * @author ebthomas
 *
 */
public enum ArbiterStatusFactory {
	INSTANCE;

	public StatusRecordBean create(
			final LineItemStatus lineItemStatus) {

		return create(getDefaultUser(), lineItemStatus);
	}

	public StatusRecordBean create(final User agent,
			final LineItemStatus lineItemStatus) {
		Calendar calNow = Calendar.getInstance();
		StatusRecordBean statusRecord = new StatusRecordBean();
		statusRecord.setStatus(lineItemStatus.name());
		statusRecord.setAgentExternalId(agent.getUserLogin());
		statusRecord.setAgent(agent);
		statusRecord.setDateTimeStamp(calNow);

		List<String> reasons = new ArrayList<String>();
		reasons.add(String.valueOf(lineItemStatus.getId()));
		statusRecord.setReasons(reasons);

		return statusRecord;
	}

	public StatusRecordBean create(final OrderStatus orderStatus) {

		return create(getDefaultUser(), orderStatus);
	}

	public StatusRecordBean create(final User agent,
			final OrderStatus orderStatus) {
		Calendar calNow = Calendar.getInstance();
		StatusRecordBean statusRecord = new StatusRecordBean();
		statusRecord.setStatus(orderStatus.name());
		statusRecord.setAgentExternalId(agent.getUserLogin());
		statusRecord.setAgent(agent);
		statusRecord.setDateTimeStamp(calNow);

		List<String> reasons = new ArrayList<String>();
		reasons.add(orderStatus.name());
		statusRecord.setReasons(reasons);

		return statusRecord;
	}

	public User getDefaultUser() {
		User user = new User();
		user.setUserId(1);
		user.setUserLogin("default");
		user.setUserUpdatedDate(Calendar.getInstance().getTime());

		user.setUserName("default");

		return user;

	}
}
