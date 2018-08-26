package com.AL.ome.ie.arbiter;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.AL.ie.service.strategy.ArbiterFlowManagerDefault;

/**
 * @author ebthomas
 * 
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/spring/arbiterContext.xml" })
@Configurable
public class ArbiterFlowStringTest implements ApplicationContextAware {

	@Autowired
	ArbiterFlowManagerDefault arbiter;

	private ApplicationContext applicationContext;

	@Before
	public void setUp() {

		System.out.println("Test Setup");
	}

	@Test
	public void testFlow() throws Exception {
		
		//arbiter.dispatch("Hello111 World222 Hello3333 World444"); 
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

}
