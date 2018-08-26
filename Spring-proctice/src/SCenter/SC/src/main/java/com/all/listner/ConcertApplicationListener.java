package com.AL.listner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ConcertApplicationListener implements ApplicationListener<ApplicationContextEvent>
{
	private static final Logger logger = Logger.getLogger(ConcertApplicationListener.class);

	private static final ExecutorService pool = Executors.newFixedThreadPool(1);
	
	LoadS3DataManager getDataFromS3 = new LoadS3DataManager();
	public void onApplicationEvent(ApplicationContextEvent event) 
	{
		if( event instanceof ContextRefreshedEvent 
				&& !event.getApplicationContext().getDisplayName().contains("Flow ApplicationContext"))
		{
			try {
				logger.info("DataLoad_Started");
				pool.execute(getDataFromS3);
				logger.info("DataLoad_Done");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
}