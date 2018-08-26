/**
 *
 */
package com.A.validation.impl;

import java.util.Set;

import com.A.validation.Message;
import com.A.validation.ValidationReport;
import com.A.xml.v4.OrderManagementRequestResponseDocument;
import com.A.xml.v4.ProcessingMessage;
import com.A.xml.v4.StatusType;
import com.A.xml.v4.StatusType.ProcessingMessages;

/**
 * @author ebthomas
 *
 */
public final class OmeValidationHelper
{

    /**
     * Private Constructor creating utility class.
     */
    private OmeValidationHelper()
    {
        super();
    }
    /**
     * @param status status
     * @param validationReport validation report
     */
    public static void populateProcessingStatus( final StatusType status, final ValidationReport validationReport )
    {
        ProcessingMessages processingMessages = status.addNewProcessingMessages();

        Set<Message> messages = validationReport.getMessages();
        for ( Message message : messages )
        {
            ProcessingMessage serviceFeedback = processingMessages.addNewMessage();
            String msgCode = message.getType().getCode() + "." + message.getMessageCode();
            Boolean isValid = isValidDouble(msgCode);
            double code = 0.0;
            if(isValid){
            	code = Double.parseDouble(msgCode);
            }
            serviceFeedback.setCode( code );
            serviceFeedback.setText( message.getMessageKey() );

        }
    }

    private static Boolean isValidDouble(String value){
    	try{
    		Double.valueOf(value);
    		return true;
    	}catch(NumberFormatException nfe){
    		return false;
    	}
    }

    /**
     * @param doc Document that will contain errors
     * @param validationReport
     * @return Status
     */
    public static StatusType determineStatus( final Object  doc, final ValidationReport validationReport )
    {
       if (( doc != null ) &&  ( doc instanceof OrderManagementRequestResponseDocument ))
        {
            return getStatus( (OrderManagementRequestResponseDocument)  doc,  validationReport );
        }


        return null;
    }

    private static StatusType getStatus( final OrderManagementRequestResponseDocument  doc, final ValidationReport validationReport )
    {
        StatusType status = doc.getOrderManagementRequestResponse().addNewStatus();

        if ( validationReport.contains( Message.Type.FATAL ) )
        {
            updateStatusCode( status, Message.Type.FATAL);
        }
        else if ( validationReport.contains( Message.Type.ERROR ) )
        {
            updateStatusCode( status, Message.Type.ERROR);
        }
        else if ( validationReport.contains( Message.Type.WARNING ) )
        {
            updateStatusCode( status, Message.Type.WARNING);
        }
        else if ( validationReport.contains( Message.Type.INFO ) )
        {
            updateStatusCode( status, Message.Type.INFO);
        }

        return status;
    }



    /**
     * @param status status
     * @param type type
     */
    public static void updateStatusCode( final StatusType status, final Message.Type type)
    {
        status.setStatusCode( type.getCode() );
        status.setStatusMsg( type.name() );
    }

}
