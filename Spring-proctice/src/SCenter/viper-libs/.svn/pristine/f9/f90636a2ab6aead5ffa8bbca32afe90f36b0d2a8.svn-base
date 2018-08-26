package com.A.Vdao.broadcast;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
public class ContextCloseHandler implements ApplicationListener<ContextClosedEvent> {

    private static final Logger logger = Logger.getLogger(ContextCloseHandler.class);

    public void onApplicationEvent(ContextClosedEvent event) {
	logger.info("Shutting down broadcast thread pool.....");
	BroadcastService.shutdown();
	logger.info("Broadcast thread pool shutdown sucessfully.....");
    }

}
