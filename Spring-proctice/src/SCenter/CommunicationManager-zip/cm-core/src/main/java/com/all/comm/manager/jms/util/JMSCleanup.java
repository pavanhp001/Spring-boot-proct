package com.AL.comm.manager.jms.util;

 
import javax.jms.JMSException;
import org.apache.log4j.Logger;

 

public class JMSCleanup {
	private static final Logger logger = Logger.getLogger(JMSCleanup.class);
 
 
 //@PostConstruct
 public void init() {
  logger.debug("jms.cleanup.started.and.waiting.for.server.shutdown");
 } 
 
 
 //@PreDestroy
 public void destroy() {
	  logger.debug("cleaningup.jms.connection.and.connection.factory");
 
	  try {
		JMSConfigManager.INSTANCE.stop();
	} catch (JMSException e) {
		// TODO Auto-generated catch block
		logger.error(e);
	}
 }
 
}
