/**
 *
 */
package com.A.ui.context.impl;

import com.A.validation.Message;
import com.A.validation.ValidationReport;
import com.A.xml.v4.StatusType;

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
//        ProcessingMessages processingMessages = status.addNewProcessingMessages();
//
//        Set<Message> messages = validationReport.getMessages();
//        for ( Message message : messages )
//        {
//            ProcessingMessage serviceFeedback = processingMessages.addNewMessage();
//            double code = new Double( message.getType().getCode() + "." + message.getMessageCode() );
//            serviceFeedback.setCode( code );
//            serviceFeedback.setText( message.getMessageKey() );
//            
//        }
    }

    /**
     * @param doc Document that will contain errors
     * @param validationReport
     * @return Status
     */
    public static StatusType determineStatus( final Object  doc, final ValidationReport validationReport )
    {
//       if (( doc != null ) &&  ( doc instanceof OrderManagementRequestResponseDocument ))
//        {
//            return getStatus( (OrderManagementRequestResponseDocument)  doc,  validationReport );
//        }
//         
        
        return null; 
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
