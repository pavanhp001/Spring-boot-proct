package com.AL.service;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;

import com.AL.thread.WebOrderThread;

public class WebOrderRejectedExecutionHandler implements RejectedExecutionHandler {

	private static final Logger logger = Logger.getLogger(WebOrderRejectedExecutionHandler.class);

	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		if(r instanceof WebOrderThread) {
			WebOrderThread wot = (WebOrderThread)r;
			logger.error(wot.getRejectRefKey() + " is rejected by threadpool.");
		}
	}
}
