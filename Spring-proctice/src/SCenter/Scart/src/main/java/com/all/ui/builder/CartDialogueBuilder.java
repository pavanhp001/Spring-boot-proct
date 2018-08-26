package com.AL.ui.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.AL.productResults.util.Utils;
import com.AL.ui.factory.CartDialogueFactory;
import com.AL.ui.vo.DataFieldVO;
import com.AL.V.exception.BaseException;
import com.AL.xml.di.v4.DataFieldType;
import com.AL.xml.di.v4.DataGroupType;
import com.AL.xml.di.v4.DialogueListType;
import com.AL.xml.di.v4.DialogueResponseType;
import com.AL.xml.di.v4.DialogueType;
import com.AL.xml.di.v4.DataGroupType.DataFieldList;
import com.AL.xml.di.v4.DialogueType.DataGroupList;
import com.AL.xml.v4.OrderType;

/**
 * @author preetam
 *
 */
public enum CartDialogueBuilder {

	INSTANCE;

	private static final Logger logger = Logger.getLogger(CartDialogueBuilder.class);

	/**
	 *	Getting the Dialogues from Dialogue Service and Building the DataFieldList
	 *
	 *
	 * @param dataFieldList
	 * @param value
	 * @param GUID 
	 * @throws UnRecoverableException 
	 * @throws RecoverableException 
	 */
	public DataFieldList  getDialogue(String value, String GUID) throws BaseException{
		
		Map<String, Map<String, String>> dataMap = createContextMap(value,GUID);
		DialogueResponseType dialogueResponseType = CartDialogueFactory.INSTANCE.getDialoguesByContext(dataMap);
		
		return buildDataFieldList(dialogueResponseType);
	}
	
	

	/**
	 * Creates the DataFieldList
	 * 
	 * 
	 * @param dialogueResponseType
	 * @return
	 */
	public DataFieldList buildDataFieldList(
			DialogueResponseType dialogueResponseType) {

		DataFieldList dataFieldList = new DataFieldList();

		if(dialogueResponseType !=null && dialogueResponseType.getResults()!=null){
			DialogueListType dialogueListType = dialogueResponseType.getResults().getDialogueList();
			for (DialogueType dialogueType : dialogueListType.getDialogue()){
				DataGroupList dataGroupList = dialogueType.getDataGroupList();
				for (DataGroupType dataGroupType :dataGroupList.getDataGroup()){
					dataFieldList.getDataField().addAll(dataGroupType.getDataFieldList().getDataField());
				}
			}
			return dataFieldList;
		}
		return null;
	}

	/**
	 *	Building scMap for CloseCall Page
	 * @param scMap
	 * @param order
	 */
	public void scMapForCloseCallPage( Map<String, String> scMap,OrderType order){
		scMap.put("EmailAddress", order.getCustomerInformation().getCustomer().getBestEmailContact());
		scMap.put("ContactPhoneNumber", getFormattedDtTelephoneNum(order.getCustomerInformation().getCustomer().getBestPhoneContact()));
		scMap.put("consumer.ALCustomerNumber", order.getCustomerInformation().getCustomer().getALCustomerNumber());
	}

	/**
	 * Replacing Static values with Dynamic values 
	 * @param input
	 * @return
	 */
	public void replaceNamesWithValues( DataFieldType dataFieldType,Map<String, String> scMap) {
		String input = dataFieldType.getText();

		for(String name: scMap.keySet()) {
			String formattedName = "{"+name+"}";
			String formattedName1 = "["+name+"]";
			if(input.contains(formattedName) || input.contains(formattedName1)){
				input = input.replaceAll(name, scMap.get(name));
			}
		}
		input = input.replaceAll("\\{", "").replaceAll("\\}", "");
		input = input.replaceAll("\\[", "").replaceAll("\\]", "");
		logger.debug("dialogue="+input);
		dataFieldType.setText(input);
	}
	
	/**
	 * Replacing Static values with Dynamic values 
	 * @param 
	 * @return void
	 */
	public void replaceNamesWithValues(DataFieldVO dataFieldVo,	Map<String, String> scMap) {
		String input = dataFieldVo.getText();

		for(String name: scMap.keySet()) 
		{
			String formattedName = "{"+name+"}";
			String formattedName1 = "["+name+"]";
			if(input.contains(formattedName) || input.contains(formattedName1))
			{
				input = input.replaceAll(name, scMap.get(name));
			}
		}
		input = input.replaceAll("\\{", "").replaceAll("\\}", "");
		input = input.replaceAll("\\[", "").replaceAll("\\]", "");
		logger.debug("dialogue="+input);
		dataFieldVo.setText(input);
	}
	
	/**
	 * Builds the Map for Dialogue Service Request
	 * 
	 * @param externalId
	 * @return
	 */
	public Map<String, Map<String, String>> createContextMap(String externalId,String GUID) {
		Map<String, Map<String, String>> context = new HashMap<String, Map<String, String>>();
		Map<String, String> salesFlow = new HashMap<String, String>();
		salesFlow.put("salesFlow.dialogueType", externalId);
		salesFlow.put("GUID", GUID);
		
		context.put("salesFlow", salesFlow);
		return context;
	}
	
	public String getFormattedDtTelephoneNum(String dtTelephoneNum) {
		if(!Utils.isBlank(dtTelephoneNum)){
			dtTelephoneNum = dtTelephoneNum.replaceAll("-", "");
			dtTelephoneNum = dtTelephoneNum.trim();
			dtTelephoneNum = dtTelephoneNum.substring(0,3)+"-"+dtTelephoneNum.substring(3,6)+"-"+dtTelephoneNum.substring(6);
		}
		return dtTelephoneNum;
	}
	
	
	/**
	 * Preparing DataFiledVO list from DataFieldType list to avoid Serializable exception.
	 * @param dataFields
	 * @return dataFieldVOs
	 */
	public List<DataFieldVO> prepareDataFiledVOList(List<DataFieldType> dataFields) 
	{
		List<DataFieldVO> dataFieldVOs = new ArrayList<DataFieldVO>();
		
		if(dataFields != null && !dataFields.isEmpty())
		{
			for (DataFieldType dataFieldType : dataFields) 
			{
				DataFieldVO dataFieldVO = new DataFieldVO();
				
				dataFieldVO.setExternalId(dataFieldType.getExternalId());
				dataFieldVO.setText(dataFieldType.getText());
				dataFieldVO.setDescription(dataFieldType.getDescription());
				
				dataFieldVOs.add(dataFieldVO);
			}
		}
		
		return dataFieldVOs;
	}

	
}
