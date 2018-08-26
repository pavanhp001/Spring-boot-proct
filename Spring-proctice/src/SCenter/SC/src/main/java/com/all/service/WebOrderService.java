package com.AL.service;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.AL.bean.WebOrderBean;
import com.AL.thread.WebOrderMonitorThread;
import com.AL.thread.WebOrderThread;
import com.AL.ui.dao.WebOrderDao;
import com.AL.ui.service.config.ConfigRepo;

public class WebOrderService {

	private static final Logger logger = Logger.getLogger(WebOrderService.class);

	private static final int DEFAULT_POOL_SIZE = 50;
	private static final int MONITOR_DELAY = 60;
	private static final String THREAD_POOL_SIZE = "score_thread_pool_size";
	private static final String THREAD_POOL_DELAY = "score_thread_monitor_delay";

	private static ThreadPoolExecutor pool = null;
	private static WebOrderMonitorThread monitor = null;
	static WebOrderRejectedExecutionHandler rejectionHandler = new WebOrderRejectedExecutionHandler();

	static {
		int pool_size = ConfigRepo.getInt(THREAD_POOL_SIZE);
		int monitor_delay = ConfigRepo.getInt(THREAD_POOL_DELAY);
		if(pool_size <= 0){
			pool_size = DEFAULT_POOL_SIZE;
		}

		if(monitor_delay <= 0){
			monitor_delay = MONITOR_DELAY;
		}

		logger.info("Pool Size : "+pool_size+", Monitor Delay : "+monitor_delay);

		pool = new ThreadPoolExecutor(pool_size, pool_size,
				0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(), rejectionHandler);

		monitor = new WebOrderMonitorThread(pool, monitor_delay);
		new Thread(monitor).start();
	}

	/**
	 * @param order
	 * @param webOrderDao
	 * @param s3Service
	 */
	public static void sendWebOrder(WebOrderBean order, WebOrderDao webOrderDao, RepositoryService s3Service){
		String wotName = Thread.currentThread().getName();
		WebOrderThread wot = new WebOrderThread(order, webOrderDao, s3Service, wotName);
		try {
			pool.execute(wot);
		} catch(RejectedExecutionException ree) {
			logger.error("Task is Rejected, Error : "+ree.getMessage());
		}
	}
}
