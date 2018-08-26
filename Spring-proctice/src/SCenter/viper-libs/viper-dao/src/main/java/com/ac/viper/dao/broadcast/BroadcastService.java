package com.ac.V.dao.broadcast;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.A.Vdao.util.SystemPropertiesRepo;

public class BroadcastService {

    private static final String COMMON_BROADCAST_MAX_THREADPOOL_SIZE = "COMMON.broadcast.max.threadpool.size";

    private static final String COMMON_BROADCAST_MIN_THREADPOOL_SIZE = "COMMON.broadcast.min.threadpool.size";

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
	int minPoolSize = SystemPropertiesRepo.getSystemPropertyValue(COMMON_BROADCAST_MIN_THREADPOOL_SIZE);
	int maxPoolSize = SystemPropertiesRepo.getSystemPropertyValue(COMMON_BROADCAST_MAX_THREADPOOL_SIZE);
	if (minPoolSize == 10000) {
	    logger.info("Property[broadcast.min.threadpool.size] not set for context[COMMON]. Using default min pool size 25");
	    minPoolSize = 25;
	}

	if (maxPoolSize == 10000) {
	    logger.info("Property[broadcast.max.threadpool.size] not set for context[COMMON]. Using default max pool size 50");
	    minPoolSize = 50;
	}

	logger.info("Min pool = " + minPoolSize);
	threadFactory = Executors.defaultThreadFactory();
	executorPool = new ThreadPoolExecutor(minPoolSize, maxPoolSize, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(50), threadFactory, rejectionHandler);
	logger.info("Starting all threads");
	int i = executorPool.prestartAllCoreThreads();
	logger.info("Started " + i + " threads");

	monitor = new BroadcastMonitorThread(executorPool, 3);
	monitorThread = new Thread(monitor);
	monitorThread.start();

    }

    public static void sendBroadcast(BroadcastMessage broadcast, String tName) {
	logger.info("Broadcasting msg : " + broadcast.getId());
	BroadcastThread r = new BroadcastThread(broadcast, tName);
	executorPool.execute(r);
    }

    public static void shutdown() {
	logger.info("Shutting down thread pool and monitor thread");
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
	logger.info("Finished all threads.");
    }
}
