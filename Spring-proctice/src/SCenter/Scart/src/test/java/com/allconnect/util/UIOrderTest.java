package com.AL.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.AL.controller.ajax.CartFeedbackOnStartController;
import com.AL.ui.service.V.UICartOrderService;
import com.AL.ui.vo.ErrorList;
import com.AL.V.gateway.util.JaxbUtil;
import com.AL.xml.v4.OrderType;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml" })
public class UIOrderTest {

	@Autowired
	private UICartOrderService orderService;
	
	private static final Logger logger = Logger.getLogger(UIOrderTest.class);
	
	@Test
	public void getOrder() {
		
		OrderType order = orderService.get("default", "19697", new HashMap<String, Object>(), "*", new ArrayList<String>(), new ErrorList());
		
		JaxbUtil<OrderType> utilOrder = new JaxbUtil<OrderType>();

		logger.info("Customer Response ---->>>>>>"+ utilOrder.toString(order, OrderType.class));
		
	}
}
