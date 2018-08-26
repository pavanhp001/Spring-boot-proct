/**
 * 
 */
package com.AL.ui.dialogue;

import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.AL.ui.builder.DialogueVOBuilder;
import com.AL.ui.domain.dynamic.dialogue.Dialogue;
import com.AL.ui.mapper.DialogueMapper;
import com.AL.ui.service.V.DialogService;
import com.AL.ui.service.V.DialogServiceUI;
import com.AL.ui.service.V.impl.DialogCacheService;
import com.AL.ui.vo.DialogueVO;
import com.AL.ui.vo.OrderQualVO;
import com.AL.V.domain.SalesContext;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.di.v4.DialogueListType;
import com.AL.xml.di.v4.DialogueResponseType;

/**
 * @author ganesh
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-CKO-att-sti-app-context.xml" })
public class DialogueServiceTest {
	
	@Ignore @Test
	public void testGetSampleDialogues(){
		//DialogueListType dialogues = DialogServiceUI.INSTANCE.getSampleDialoguesByProductId("");
		String externalId = "ATTSTI-UV-DBL-INT-VOICE";
		Map<String, Map<String, String>> dataMap = DialogServiceUI.INSTANCE.getData(externalId);
		
		SalesContext salesContext = SalesContextFactory.INSTANCE.getSalesContext(dataMap);
		
		DialogueResponseType dialogResponse = DialogService.INSTANCE.getDialogue(salesContext);
		
		//DialogCacheService.INSTANCE.store(dialogResponse, productId);
		List<Dialogue> dialogueList = DialogueMapper.processResponse(dialogResponse);
		DialogueVO dialogueVO = DialogueVOBuilder.buildDialogues(dialogueList,new DialogueVO(),externalId);

		assertNotNull(dialogueVO.getDataFieldMap().size() == 0 == true);
	}
	
	@Test
	public void testGetDialoguesByProductId(){
		String externalId = "ATTSTI-UV-DBL-INT-VOICE";
		//String externalId = "ATTSTI-UV-DBL-INT-VID";
		
		DialogueVO dialogueVO  = DialogServiceUI.INSTANCE.getDialoguesByProductId(externalId);
		assertNotNull(dialogueVO.getDataFieldMap().size() == 0 == true);
		assertNotNull(dialogueVO.getDataFieldMatrixMap().size() == 0 == true);
		assertNotNull(dialogueVO);
	}


}
