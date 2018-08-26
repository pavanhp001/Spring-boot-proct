package com.AL.comm;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.AL.comm.manager.CommunicationManager;
import com.AL.comm.manager.jms.util.JMSConfigManager;

/**
 *
 */

/**
 * @author ebthomas
 * 
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
public class SenderSyncTest {

	String namespace = "pricing";
	CommunicationManager<Message, MessageListener> communicationManager = JMSConfigManager.INSTANCE
			.createCommunicationManager(namespace);

	private static String logicalQueueName = "endpoint.verizon.in";

	@Test
	public void testMessageDeliverytest() throws Exception {
		// Start sending messages
		Runnable basic = new RunBasicThread();
		for (int i = 0; i < 3; i++) {
			
			new Thread(basic).start();  
		}
		System.out.println("completed sending all messages");
		System.out.println("sleeping");
		while(true)
		{
			System.out.println("start sleeping");
			Thread.sleep(1000000);
			System.out.println("finished sleeping");
		}
		 
	}

	class RunBasicThread implements Runnable {

		RunBasicThread() {

		}

		// override run() method in interface
		public void run() {
			try {
				loopForever();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void loopForever() throws JMSException, Exception {

			boolean go = true;
			int i = 0;
			long now = System.currentTimeMillis();
			while ((go) && (i++ < 5000)) {
				Message msgReply = communicationManager.sendSync(
						logicalQueueName,
						i + ":information-->" + System.nanoTime(), 20000);

				System.out.println("RESPONSE---->>>>>>" + msgReply);
			}

			System.out.println(System.currentTimeMillis() - now);
		}
	}

}
