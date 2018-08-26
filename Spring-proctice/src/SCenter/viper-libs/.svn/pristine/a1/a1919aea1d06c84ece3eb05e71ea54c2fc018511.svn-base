package com.A.Vdao.broadcast;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;

public class RejectedExecutionHandlerImpl implements RejectedExecutionHandler {

    private static final Logger logger = Logger.getLogger(RejectedExecutionHandlerImpl.class);

    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
	if(r instanceof BroadcastThread) {
	    BroadcastThread msg = (BroadcastThread)r;
	    logger.error(msg.getBroadcastMessage().toString() + " is rejected by threadpool.");
	}

    }

}
