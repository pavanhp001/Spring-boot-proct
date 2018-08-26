package com.A.ui.service.V;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

public class ConcurrentExecution {

	public static final Logger logger = Logger.getLogger(ConcurrentExecution.class);
	private static final ExecutorService pool = Executors
			.newFixedThreadPool(15);

	public ConcurrentExecution() {

	}

	public void execute(final String agentId, final String orderId, final String[] lineitem_id_arr) {
		final List<Integer> reasonList = new ArrayList<Integer>();
		reasonList.add(777);

		for (int i = 0; i < lineitem_id_arr.length; i++) {
			logger.debug("Removing LineItem :::: "
					+ lineitem_id_arr[i]);

			pool.execute(new ConcurrentRequestDelete(agentId,orderId,
					lineitem_id_arr[i], "cancelled_removed", "deleted order",
					reasonList));

		}

	}
	
	public void submitLineItem(final String agentId, final String orderId, final List<String> lineitem_id_list) {
		for (int i = 0; i < lineitem_id_list.size(); i++) {
			logger.debug("Suubmitting LineItem :::: " + lineitem_id_list.get(i));
			pool.execute(new ConcurrentRequestSubmit(agentId, orderId, lineitem_id_list.get(i)));
		}
	}
}
