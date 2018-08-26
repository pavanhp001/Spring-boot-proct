package com.AL.ome.rules;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.AL.rules.core.RuleService;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.validation.Message;
import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.entity.StatusRecordBean;

/**
 * @author ebthomas
 * 
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContextTest.xml"  })
@Configurable
public class RuleValidationTest  
{ 
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private RuleService ruleService;
    
	
	@Test
    public void testItTest()
    {

        // Setup Entity Object 
        Set<Object> facts = getFacts();
        
        
         
		final  OrchestrationContext context = OrchestrationContext.Factory
				.create();

		context.add("facts", facts);

		ruleService.execute(context);
		processResults(context);
       

        

    }
    
    public static final void processResults(OrchestrationContext outcome) {

    	for(int i=0; i<10; i++)
			System.out.println("***********-----------*************");
		 
    	
		Set<com.AL.validation.Message> messages = outcome
				.getValidationReport().getMessages();

		for (com.AL.validation.Message msg : messages) {
			for(int i=0; i<2; i++)
				System.out.println("***********-----------*************");
			
			
			System.out.println(msg.getMessageKey().toLowerCase() + ".code."
					+ msg.getType().getCode());
			for(int i=0; i<2; i++)
				System.out.println("***********-----------*************");
			
		}

		Set< Message> outcomes = outcome.getValidationReport().getOutcome();

		for (Message mgs : outcomes) {
			for(int i=0; i<10; i++)
				System.out.println("***********-----------*************");
			 	System.out.println("outcome:" + mgs.getMessageCode());
		}
	}
    
    public static final Set<Object> getFacts() {

		Set<Object> factList = new HashSet<Object>();
		SalesOrder sob = new SalesOrder();
		sob.setExternalId(null);
		sob.setALAccountNumber("12345");
		sob.setALConfirmNumber("67890");
		//sob.setAgentId( 101L );

		List<LineItem > lineItemList = new ArrayList<LineItem >();

		// lineItemList.add(getLineItemBean("1"));
		// lineItemList.add(getLineItemBean("2"));
		// lineItemList.add(getLineItemBean("3"));
		// lineItemList.add(getLineItemBean("4"));
		// lineItemList.add(getLineItemBean("5"));

		sob.setLineItems(lineItemList);

		StatusRecordBean status = new StatusRecordBean();
		status.setAgent(null);
		status.setDateTimeStamp(Calendar.getInstance());
		status.setId(1L);

		List<String> listOfReasons = new ArrayList<String>();
//		listOfReasons.add("0-->a.reason.to.change<--");
//		listOfReasons.add("1-->a.reason.to.change<--");
//		listOfReasons.add("2-->a.reason.to.change<--");
//		listOfReasons.add("3-->a.reason.to.change<--");
		status.setReasons(listOfReasons);
		sob.setCurrentStatus(status);
		
		if (sob.getHistoricStatus() == null)
		{
			sob.setHistoricStatus(new ArrayList<StatusRecordBean>());
		}
		sob.getHistoricStatus().add(status);

		factList.add(sob);
		factList.add(status);

		return factList;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
    
    
}
