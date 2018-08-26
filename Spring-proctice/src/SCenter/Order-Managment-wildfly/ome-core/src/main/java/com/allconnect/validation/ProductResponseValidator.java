package com.AL.validation;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.AL.validation.Validator;
import com.AL.xml.v4.ProcessingMessage;
import com.AL.xml.v4.ProductEnterpriseResponseDocument;
import com.AL.xml.v4.StatusType;
import com.AL.xml.v4.StatusType.ProcessingMessages;

@Component
public class ProductResponseValidator implements Validator<ProductEnterpriseResponseDocument>
{

	private static final Logger logger  = Logger.getLogger(ProductResponseValidator.class);

	/**
	 * This method will check whether Product Response document contains any fatal error message or not
	 *
	 */
	public boolean hasFatalError( ProductEnterpriseResponseDocument productResponseDoc )
	{
		logger.info("Validating product service response");
		Boolean hasErrors = Boolean.FALSE;
		if(productResponseDoc != null)
		{
			 StatusType statusType = productResponseDoc.getProductEnterpriseResponse().getStatus();
			 if(statusType.getProcessingMessages() != null)
			 {
				 ProcessingMessages procMsgs = statusType.getProcessingMessages();
				 if(procMsgs != null && !procMsgs.isNil())
				 {
					 List<ProcessingMessage> msgList = procMsgs.getMessageList();
					 if ( msgList != null && !msgList.isEmpty() )
					{
						for ( ProcessingMessage msg : msgList )
						{
							if ( msg.getText().contains( "FATAL" ) || msg.getCode() == 3.0 || msg.getText().contains( "Error" ))
							{
								logger.warn("Product servive response contains fatal errors");
								hasErrors = Boolean.TRUE;
								break;
							}

						}
					}
				 }
			 }
		}else{
			hasErrors = Boolean.TRUE;
		}
		return hasErrors;
	}

}
