package com.A.V.jms;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.A.ui.service.V.DialogService;
import com.A.ui.service.V.impl.DialogCacheService;
import com.A.ui.template.DialogTemplateConstant;
import com.A.V.domain.SalesContext;
import com.A.V.factory.SalesContextFactory;
import com.A.V.gateway.jms.DialogClientJMS;
import com.A.V.mo.dialog.DialogMotherObject;
import com.A.xml.di.v4.DialogueResponseType;
import com.A.xml.di.v4.EnterpriseResponseDocumentType;

/**
 *
 */

/**
 * @author ebthomas
 * 
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/V-client-api-app-context.xml" })
public class JMSDialogTest {

	@Test
	public void testGetDialogWithCache() throws Exception {

		SalesContext salesContext = SalesContextFactory.INSTANCE
				.getSalesContext(DialogMotherObject.INSTANCE.getData());

		String dialogRequestTemplate = DialogTemplateConstant.INSTANCE
				.getDialogRequest(salesContext);

		DialogClientJMS jmsClient = new DialogClientJMS();

		EnterpriseResponseDocumentType response = jmsClient
				.send(dialogRequestTemplate);

		assertNotNull(response);
		assertNotNull(response.getGUID());

		DialogueResponseType dialogFromCache = DialogCacheService.INSTANCE
				.getFromCache(salesContext.toString());
		assertNull(dialogFromCache);

		DialogueResponseType dialogResponse = (DialogueResponseType) response
				.getResponse();

		DialogCacheService.INSTANCE.store(dialogResponse,
				salesContext.toString());
		assertNotNull(dialogResponse.getResults().getDialogueList()
				.getDialogue());

		dialogFromCache = DialogCacheService.INSTANCE.getFromCache(salesContext
				.toString());
		assertNotNull(dialogFromCache);

	}

	@Test
	public void testGetDialogUsingServiceSalesContext() throws Exception {

		SalesContext salesContext = SalesContextFactory.INSTANCE
				.getSalesContext(DialogMotherObject.INSTANCE.getData());

		DialogueResponseType dialogResponse = DialogService.INSTANCE
				.getDialogue(salesContext);
		assertNotNull(dialogResponse);
	}

	@Test
	public void testGetDialogUsingServiceMap() throws Exception {

		DialogueResponseType dialogResponse = DialogService.INSTANCE
				.getDialogue(DialogMotherObject.INSTANCE.getData());
		assertNotNull(dialogResponse);
		
 
	}

}
