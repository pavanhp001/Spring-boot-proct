package com.AL.op.util;

import static com.AL.op.ProvisionConstants.EMPTY_STRING;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.AL.xml.common.OpMessage;
import com.AL.xml.common.OpResponseStatus;
import com.AL.xml.common.OpStatusType;
import com.AL.xml.v4.AcMessageDocument;
import com.AL.xml.v4.AcMessageType;
import com.AL.xml.v4.CustomerContextEntityType;
import com.AL.xml.v4.CustomerContextType;
import com.AL.xml.v4.NameValuePairType;
import com.AL.xml.v4.AcMessageType.Payload;
import com.AL.xml.v4.CustomerManagementRequestResponseDocument.CustomerManagementRequestResponse.Request;
import com.AL.xml.v4.ProcessingMessage;
import com.AL.xml.v4.SalesContextEntityType;
import com.AL.xml.v4.SalesContextType;
import static com.AL.op.ProvisionConstants.*;

public class ProvisionHandlerUtil {
	
	private static Logger logger = Logger.getLogger(ProvisionHandlerUtil.class); 
	
	public static Map<String, OpStatusType.Enum> ResponseStatusMap = new HashMap<String, OpStatusType.Enum>();
	public static Map<OpStatusType.Enum, String> ResponseCodeMap = new HashMap<OpStatusType.Enum, String>();
	
	static {
		ResponseStatusMap.put(INFO, OpStatusType.INFO);
		ResponseStatusMap.put(SUCCESS, OpStatusType.SUCCESS);
		ResponseStatusMap.put(ERROR, OpStatusType.ERROR);
		ResponseStatusMap.put(WARN, OpStatusType.WARN);
		ResponseStatusMap.put(FATAL, OpStatusType.FATAL);
		ResponseCodeMap.put(OpStatusType.INFO, SUCCESS_CODE);
		ResponseCodeMap.put(OpStatusType.SUCCESS, SUCCESS_CODE);
		ResponseCodeMap.put(OpStatusType.ERROR, ERROR_CODE);
		ResponseCodeMap.put(OpStatusType.WARN, ERROR_CODE);
		ResponseCodeMap.put(OpStatusType.FATAL, ERROR_CODE);
	}
	
	public static String extractPayload(final String inputXml) {
		if (inputXml == null) {
			logger.debug("input xml is empty");
			return EMPTY_STRING;
		}
		String payloadData;
		try {
			logger.debug("parsing input to extract payload");
			AcMessageDocument ac = AcMessageDocument.Factory.parse(inputXml);
			AcMessageType ty = ac.getAcMessage();
			Payload payload = ty.getPayload();
			payloadData = payload.xmlText();
		} catch (Exception e) {
			logger.debug("Exception occured while parsing input request. Returning input xml as payload");
			payloadData = inputXml;
		}
		return payloadData;
	}
	
	public static void setCustomerContext(Request request, SalesContextType salesContext) {
		if(salesContext != null) {
			CustomerContextType context = request.addNewCustomerContext();
			for(SalesContextEntityType contextEntity : salesContext.getEntityList()) {
				CustomerContextEntityType cmEntity = context.addNewEntity();
				cmEntity.setName(contextEntity.getName());
				for(NameValuePairType contextPair : contextEntity.getAttributeList()) {
					NameValuePairType nameValuePair = cmEntity.addNewAttribute();
					nameValuePair.setName(contextPair.getName());
					nameValuePair.setValue(contextPair.getValue());
				}
			}
		}
	}
	
	public static OpResponseStatus getOpResponseStatus(com.AL.xml.v4.StatusType statusType) {
		OpResponseStatus opStatus = OpResponseStatus.Factory.newInstance();
		opStatus.setStatus(ResponseStatusMap.get(statusType.getStatusMsg()));
		opStatus.setStatusCode(ResponseCodeMap.get(opStatus.getStatus()));
		if((statusType.getProcessingMessages() != null) && (statusType.getProcessingMessages().getMessageList().size() > 0)) {
			for(ProcessingMessage message : statusType.getProcessingMessages().getMessageList()) {
				OpMessage opMsg = opStatus.addNewOpMessage();
				opMsg.setText(message.getText());
				opMsg.setCode(String.valueOf(message.getCode()));
			}
		} else {
			OpMessage opMsg = opStatus.addNewOpMessage();
			opMsg.setText("Error occured");
			opMsg.setCode(ResponseCodeMap.get(OpStatusType.ERROR));
			opStatus.getOpMessageList().add(opMsg);
		}
		return opStatus;
	}
}
