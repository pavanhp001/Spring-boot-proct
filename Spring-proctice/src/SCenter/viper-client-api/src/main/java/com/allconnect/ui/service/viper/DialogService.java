package com.A.ui.service.V;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.A.ui.template.DialogTemplateConstant;
import com.A.V.domain.SalesContext;
import com.A.V.exception.RecoverableException;
import com.A.V.exception.UnRecoverableException;
import com.A.V.factory.SalesContextFactory;
import com.A.V.gateway.jms.DialogClientJMS;
import com.A.xml.di.v4.DialogueResponseType;
import com.A.xml.di.v4.EnterpriseResponseDocumentType;
import com.A.xml.di.v4.NameValuePairType;
import com.A.xml.di.v4.ProcessingMessage;
import com.A.xml.di.v4.SalesContextEntityType;

public enum DialogService {

	INSTANCE;

	public DialogueResponseType getDialogue(final SalesContext salesContext) throws RecoverableException, UnRecoverableException {
		
		DialogueResponseType dialogResponse = null;
		
		String dialogRequestTemplate = DialogTemplateConstant.INSTANCE.getDialogRequest(salesContext);
		DialogClientJMS jmsClient = new DialogClientJMS();
		String guid = UUID.randomUUID().toString();
		if(salesContext.getEntity() != null){
			for(SalesContextEntityType entityType : salesContext.getEntity()){
				boolean isGuidFind = false;
				if(entityType != null){
					for(NameValuePairType nameValuePairType : entityType.getAttribute()){
						if(nameValuePairType.getName().equals("GUID")){
							guid = nameValuePairType.getValue();
							isGuidFind = true;
							break;
						}
					}
				}
				if(isGuidFind){
					break;
				}
			}
		}
		  Map<String,String> headers = new HashMap<String,String>();
		  headers.put("GUID",guid);
		EnterpriseResponseDocumentType response = jmsClient.send(dialogRequestTemplate,headers);
		
		if(response.getStatus() != null && response.getStatus().getProcessingMessages() != null && response.getStatus().getProcessingMessages().getMessage() != null){
			List<ProcessingMessage> errorResponse = response.getStatus().getProcessingMessages().getMessage();
			for(ProcessingMessage processingMessage : errorResponse){
				if(processingMessage.getCode() != 0.0){
					for(SalesContextEntityType salesContextEntity : salesContext.getEntity()){
						if(salesContextEntity.getName().equalsIgnoreCase("provisioning") || salesContextEntity.getName().equalsIgnoreCase("WarmTransfer")){
							throw new RecoverableException(processingMessage.getText());
						}
						else{
							throw new UnRecoverableException(processingMessage.getText());
						}
						/*if(salesContextEntity.getAttribute() != null)	{
							for(NameValuePairType nameValueType : salesContextEntity.getAttribute()){
								if(nameValueType.getName().equals("salesFlow.dialogueType") && (nameValueType.getValue().equals("WarmTransfer") || nameValueType.getValue().equals("Provisioning"))){
									throw new RecoverableException(processingMessage.getText());
								}
								else{
									throw new UnRecoverableException(processingMessage.getText());
								}
							}
						}*/
					}
				}
			}
		}
		
		if (response != null) {

			dialogResponse = (DialogueResponseType) response.getResponse();

		}

		return dialogResponse;
	}
	
	
	
public DialogueResponseType getDialogue(final SalesContext salesContext, String namespace) throws RecoverableException, UnRecoverableException {
		
		DialogueResponseType dialogResponse = null;
		
		String dialogRequestTemplate = DialogTemplateConstant.INSTANCE.getDialogRequest(salesContext);
		DialogClientJMS jmsClient = new DialogClientJMS();
		String guid = UUID.randomUUID().toString();
		if(salesContext.getEntity() != null){
			for(SalesContextEntityType entityType : salesContext.getEntity()){
				boolean isGuidFind = false;
				if(entityType != null){
					for(NameValuePairType nameValuePairType : entityType.getAttribute()){
						if(nameValuePairType.getName().equals("GUID")){
							guid = nameValuePairType.getValue();
							isGuidFind = true;
							break;
						}
					}
				}
				if(isGuidFind){
					break;
				}
			}
		}
		  Map<String,String> headers = new HashMap<String,String>();
		  headers.put("GUID",guid);
		EnterpriseResponseDocumentType response = jmsClient.send(dialogRequestTemplate,headers,namespace);
		
		if(response.getStatus() != null && response.getStatus().getProcessingMessages() != null && response.getStatus().getProcessingMessages().getMessage() != null){
			List<ProcessingMessage> errorResponse = response.getStatus().getProcessingMessages().getMessage();
			for(ProcessingMessage processingMessage : errorResponse){
				if(processingMessage.getCode() != 0.0){
					for(SalesContextEntityType salesContextEntity : salesContext.getEntity()){
						if(salesContextEntity.getName().equalsIgnoreCase("provisioning") || salesContextEntity.getName().equalsIgnoreCase("WarmTransfer")){
							throw new RecoverableException(processingMessage.getText());
						}
						else{
							throw new UnRecoverableException(processingMessage.getText());
						}
						/*if(salesContextEntity.getAttribute() != null)	{
							for(NameValuePairType nameValueType : salesContextEntity.getAttribute()){
								if(nameValueType.getName().equals("salesFlow.dialogueType") && (nameValueType.getValue().equals("WarmTransfer") || nameValueType.getValue().equals("Provisioning"))){
									throw new RecoverableException(processingMessage.getText());
								}
								else{
									throw new UnRecoverableException(processingMessage.getText());
								}
							}
						}*/
					}
				}
			}
		}
		
		if (response != null) {

			dialogResponse = (DialogueResponseType) response.getResponse();

		}

		return dialogResponse;
	}

	public DialogueResponseType getDialogue(
			final Map<String, Map<String, String>> data) throws RecoverableException, UnRecoverableException {

		SalesContext salesContext = SalesContextFactory.INSTANCE
				.getSalesContext(data);

		return getDialogue(salesContext);
	}

}
