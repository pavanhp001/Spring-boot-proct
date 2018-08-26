package com.AL.ie.service.strategy;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.support.MessageBuilder;
import com.AL.ie.service.ArbiterFlowManager;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;

public class ArbiterFlowManagerDefault implements ArbiterFlowManager {

	@Autowired
	@Qualifier("processChannel")
	private MessageChannel channel;

	public static long TIME_OUT = 500000;
	private static int DELTA_INCREASE = 100;
	private static int MAX_CHECKS = 10;

	ExecutorService pool = newArbiterCachedThreadPool();
	public static final ConcurrentHashMap<String, ArbiterFlow> activeSessionsList = new ConcurrentHashMap<String, ArbiterFlow>();
	private static final Logger logger = Logger
			.getLogger(ArbiterFlowManagerDefault.class);

	public ArbiterFlowManagerDefault() {
	}

	public static ExecutorService newArbiterCachedThreadPool() {
		return new ThreadPoolExecutor(10, // core size
				150, // max size
				10 * 60, // idle timeout
				TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100)); // queue
																			// with
																			// a
																			// size

	}

	public ArbiterFlow<OrderManagementRequestResponseDocument, OrderManagementRequestResponseDocument> createFlow(
			final OrderManagementRequestResponseDocument payload) {
		ArbiterFlow<OrderManagementRequestResponseDocument, OrderManagementRequestResponseDocument> aFlow = new ArbiterFlow<OrderManagementRequestResponseDocument, OrderManagementRequestResponseDocument>(
				payload, TIME_OUT);

		return aFlow;
	}

	public ArbiterFlow<String, String> createFlow(final String payload) {
		ArbiterFlow<String, String> aFlow = new ArbiterFlow<String, String>(
				payload, TIME_OUT);

		logger.debug("generated flow with key:" + aFlow.getGuid());

		return aFlow;
	}

	private void addTransactionIdToFlow(
			ArbiterFlow<OrderManagementRequestResponseDocument, OrderManagementRequestResponseDocument> aFlow,
			int transactionId) {

		aFlow.setGuid(aFlow.getGuid() + "#" + String.valueOf(transactionId));
	}

	@SuppressWarnings("unused")
	public ArbiterFlow<OrderManagementRequestResponseDocument, OrderManagementRequestResponseDocument> dispatch(
			final OrderManagementRequestResponseDocument payload) {

		final ArbiterFlow<OrderManagementRequestResponseDocument, OrderManagementRequestResponseDocument> aFlow = createFlow(payload);
		int transactionId = payload.getOrderManagementRequestResponse()
				.getTransactionId();
		// add transaction to flow to ensure that you can track/associate order
		// with lock

		addTransactionIdToFlow(aFlow, transactionId);
		// Place Lock in Cache[ArbiterFlow]
		activeSessionsList.put(aFlow.getGuid(), aFlow);
		logger.debug("Place Lock in Cache[ArbiterFlow]");
		
		Callable<String> callable = new Callable<String>() {

			public String call() throws Exception {
				Map<String, String> headers = new HashMap<String, String>();
				headers.put("FLOW_KEY", aFlow.getGuid());
				headers.put(MessageHeaders.CORRELATION_ID, getUniqueId());

				Message<OrderManagementRequestResponseDocument> msg = MessageBuilder
						.withPayload(payload).copyHeaders(headers).build();

				// Ensure that the main thread is waiting before starting to
				// process
				// first wait 100ms then 200ms then 300ms up to 1000ms = 5.5s
				int waitAmount = 0;
				wait: for (int i = 0; i < MAX_CHECKS; i++) {
					if (!aFlow.isReadyToProcess()) {
						logger.debug("isReadyToProcess FALSE");
						waitAmount = waitAmount + DELTA_INCREASE;
						Thread.sleep(waitAmount);
					} else {
						break wait;
					}
				}

				if (aFlow.isReadyToProcess()) {
					channel.send(msg);
					logger.debug("sent message to the channel: msg.getPayload(): " + msg.getPayload().xmlText() 
							+ " :msg Headers: " + msg.getHeaders().toString());
				} else {
					throw new IllegalArgumentException(
							"unable to process flow. cannot wait before starting arbiter process flow "
									+ aFlow.getGuid());
				}
				return "";
			}

		};

		pool.submit(callable);

		try {
			logger.debug("capturing lock..." + aFlow.getGuid());

			if (aFlow == null) {
				throw new IllegalArgumentException(
						"unable to retrieve Arbiter Flow");
			}

			logger.debug(aFlow.getRequest().toString());

			synchronized (aFlow.getLock()) {

				logger.debug("signal wait start......" + aFlow.getGuid());
				aFlow.waitIsReady();
				logger.debug("wait start......" + aFlow.getGuid());
				aFlow.getLock().wait(TIME_OUT);
				logger.debug("wait completed......" + aFlow.getGuid());
				ArbiterFlowManagerDefault.activeSessionsList.remove(aFlow
						.getGuid());
				logger.debug("releasing..." + aFlow.getGuid());
				return aFlow;

			}

		} catch (InterruptedException e) {
			// TODO ERROR HANDLING
			logger.warn(e.getMessage());

		}

		throw new IllegalArgumentException("arbiter processing error");
	}

	public String getUniqueId() {
		UUID idOne = UUID.randomUUID();

		return idOne.toString();
	}

}
