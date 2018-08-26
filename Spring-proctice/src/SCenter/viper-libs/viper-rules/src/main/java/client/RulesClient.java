package client;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.A.V.beans.entity.LineItem;
import com.A.V.beans.entity.SalesOrder;
import com.A.V.beans.entity.StatusRecordBean;

/**
 * @author ebthomas
 * 
 */
public class RulesClient {

	public static LineItem  getLineItem(Long id) {
		LineItem  lib = new LineItem();
		lib.setExternalId(id);
		lib.getLineitemScheduleInfo().setScheduledStartDate(Calendar.getInstance());
		//lib.setActualStartDate(Calendar.getInstance());

		return lib;

	}

	public static final Set<Object> getFacts() {

		Set<Object> factList = new HashSet<Object>();
		SalesOrder sob = new SalesOrder();
		sob.setExternalId(-1l);
		sob.setAAccountNumber("12345");
		sob.setAConfirmNumber("67890");
		//sob.setAgentId( 101L );

		List<LineItem > lineItemList = new ArrayList<LineItem >();

		// lineItemList.add(getLineItemBean("1"));
		// lineItemList.add(getLineItemBean("2"));
		// lineItemList.add(getLineItemBean("3"));
		// lineItemList.add(getLineItemBean("4"));
		// lineItemList.add(getLineItemBean("5"));

		sob.setLineItems(lineItemList);

		StatusRecordBean status = new StatusRecordBean();
		status.setAgent(null);
		status.setDateTimeStamp(Calendar.getInstance());
		status.setId(1L);

		List<String> listOfReasons = new ArrayList<String>();
		// listOfReasons.add("-->a.reason.to.change<--");
		status.setReasons(listOfReasons);

		factList.add(sob);
		factList.add(status);

		return factList;
	}

	

	public static void main(String[] args) {
		

	}
}
