package com.AL.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GreetingAMBController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(GreetingAMBController.class);

	private Map<String, Customer> custData = new HashMap<String,Customer>();


	@PostConstruct
	public void init() throws Exception{
		logger.info("Initializing_dummy_customer_data");

		Customer c = createDummyCust(1001,"Pritesh","Patel");
		custData.put("1001", c);

		c = createDummyCust(1002,"Eban","Thomas");
		custData.put("1002", c);

		c = createDummyCust(1003,"Kevin","Lyons");
		custData.put("1003", c);

		c = createDummyCust(1004,"Raj","Porumalla");
		custData.put("1004", c);
		
		c = createDummyCust(1005,"Tracy","Martin");
		custData.put("1005", c);
		
		c = createDummyCust(1006,"Kamesh","Garimella");
		custData.put("1006", c);
	}

	private Customer createDummyCust(int i,String fName, String lName) throws Exception{
		Customer c = new Customer();
		c.setFirstName(fName);
		c.setLastName(lName);
		c.setId(i);

		return c;
	}

	@RequestMapping(value = "/amb_greeting", method = RequestMethod.POST)
	public @ResponseBody Customer greeting(String agentPhoneId, String calledAddress, String callingAddress, String ucid, String eventType,Model model) throws Exception{

		logger.info("Greeting_Amb_Controller" );

		logger.info("Received_Data : AgentPhoneId [ " + agentPhoneId  + "] CalledAddress ["+ calledAddress +"] CallingAddress [" + callingAddress + "] UCID [" +ucid +"]") ;

		Customer c = custData.get(agentPhoneId);

		if(c != null){
			return c;
		}else{
			return null;
		}
	}
}
