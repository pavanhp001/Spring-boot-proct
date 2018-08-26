package com.A.Vdao.broadcast;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.A.Vdao.util.SystemPropertiesRepo;

public class BroadcastService {

    private static final String BROADCAST_MONITOR_DELAY_SECONDS = "broadcast.monitor.delay.seconds";

    private static final String COMMON_BROADCAST_MAX_THREADPOOL_SIZE = "COMMON.broadcast.max.threadpool.size";

    private static final String COMMON_BROADCAST_MIN_THREADPOOL_SIZE = "COMMON.broadcast.min.threadpool.size";

    private static final int DEFAULT_MIN_POOL_SIZE = 20;

    private static final int DEFAULT_MAX_POOL_SIZE = 40;

    private static final int MONITOR_DELAY = 15;

    private static final Logger logger = Logger.getLogger(BroadcastService.class);

    // RejectedExecutionHandler implementation
    static RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();

    // Get the ThreadFactory implementation to use
    static ThreadFactory threadFactory = null;
    // creating the ThreadPoolExecutor
    static ThreadPoolExecutor executorPool = null;

    // Monitor thread for thread pool
    static BroadcastMonitorThread monitor =null;
    static Thread monitorThread = null;

    static {
	int minPoolSize = SystemPropertiesRepo.getSystemPropertyValue(COMMON_BROADCAST_MIN_THREADPOOL_SIZE,DEFAULT_MIN_POOL_SIZE);
	int maxPoolSize = SystemPropertiesRepo.getSystemPropertyValue(COMMON_BROADCAST_MAX_THREADPOOL_SIZE,DEFAULT_MAX_POOL_SIZE);
	int monitorDelay = SystemPropertiesRepo.getSystemPropertyValue(BROADCAST_MONITOR_DELAY_SECONDS,MONITOR_DELAY);

	logger.info("Min pool = " + minPoolSize + ", Max Pool Size = " + maxPoolSize);

	threadFactory = Executors.defaultThreadFactory();
	executorPool = new ThreadPoolExecutor(minPoolSize, maxPoolSize, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(50), threadFactory, rejectionHandler);

	logger.debug("Starting all threads");

	//Pre start all threads in pool
	int i = executorPool.prestartAllCoreThreads();

	logger.debug("Started " + i + " threads");

	//Start monitor thread
	monitor = new BroadcastMonitorThread(executorPool, monitorDelay);
	monitorThread = new Thread(monitor);
	monitorThread.start();

    }

    /**
     * Method to send broadcast in separate thread
     * @param broadcast
     * @param tName
     */
    public static void sendBroadcast(BroadcastMessage broadcast, String tName) {
	logger.info("Broadcasting msg : " + broadcast.getId());
	BroadcastThread r = new BroadcastThread(broadcast, tName);
	executorPool.execute(r);
    }

    /**
     * Method to shutdown thread pool and monitor thread
     */
    public static void shutdown() {
	try {
	    Thread.sleep(10000);
	}
	catch (InterruptedException e) {
	    logger.error("Exception while shutting down broadcast threadpool",e);
	}
	executorPool.shutdown();
	while (!executorPool.isTerminated()) {
	    // logger.info("Shutting down thread pool");
	}

	// shut down the monitor thread
	try {
	    Thread.sleep(5000);
	}
	catch (InterruptedException e) {
	    logger.error("Exception while shutting down broadcast monitor thread",e);
	}
	monitor.shutdown();
	logger.debug("Finished all threads.");
    }
}
